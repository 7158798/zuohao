package com.ruizton.main.controller.front;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ruizton.main.log.LOG;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.TrademappingStatusEnum;
import com.ruizton.main.auto.KlinePeriodData;
import com.ruizton.main.auto.OneDayData;
import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Ftradehistory;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.main.service.front.FtradeMappingService;
import com.ruizton.main.service.front.UtilsService;
import com.ruizton.util.Utils;

@Controller
public class FrontQuotationsJsonController extends BaseController {

	
	
	@ResponseBody
	@RequestMapping(value="/real/indexmarket",produces={JsonEncode})
	public String indexmarket(){
		JSONObject jsonObject = new JSONObject() ;
		List<Ftrademapping> ftrademappings = this.utilsService.list1(0, 0, " where fstatus=? ", false, Ftrademapping.class,TrademappingStatusEnum.ACTIVE) ;
		for (Ftrademapping ftrademapping : ftrademappings) {
			JSONObject js = new JSONObject() ;
			js.accumulate("symbol", ftrademapping.getFvirtualcointypeByFvirtualcointype1().getfSymbol());
			js.accumulate("price", Utils.getDouble(this.realTimeData.getLatestDealPrize(ftrademapping.getFid()), ftrademapping.getFcount1()));
			js.accumulate("total", Utils.getDouble(this.oneDayData.getTotal(ftrademapping.getFid()), 4));
			js.accumulate("buy1",Utils.getDoubleS(this.realTimeData.getHighestBuyPrize(ftrademapping.getFid()),ftrademapping.getFcount1()));
			js.accumulate("sell1",Utils.getDoubleS(this.realTimeData.getLowestSellPrize(ftrademapping.getFid()),ftrademapping.getFcount1()));
			double s = this.oneDayData.get24Price(ftrademapping.getFid());

			List<Ftradehistory> ftradehistorys = (List<Ftradehistory>)constantMap.get("tradehistory");
			for (Ftradehistory ftradehistory : ftradehistorys) {
				if(ftradehistory.getFtrademapping().getFid().intValue() == ftrademapping.getFid().intValue()){
					s= ftradehistory.getFprice();
					break;
				}
			}
			double last = 0d;
			try {
				last = Utils.getDouble((this.realTimeData.getLatestDealPrize(ftrademapping.getFid())-s)/s*100, 2);
			} catch (Exception e) {}
			js.accumulate("rose", last);
			JSONArray dataArray = new JSONArray();
			try {
				String content = this.klinePeriodData.getJsonString(ftrademapping.getFid(), 5) ;
				JSONArray jsonArray = JSONArray.fromObject(content) ;
				if(jsonArray != null && jsonArray.size() >0){
					for(int i=(jsonArray.size()-72 >=0?jsonArray.size()-72:0);i<jsonArray.size();i++){
						JSONArray retItem = new JSONArray() ;
						JSONArray item = jsonArray.getJSONArray(i) ;
						retItem.add(item.getString(0)) ;
						retItem.add(item.getString(1)) ;
						retItem.add(item.getString(2)) ;
						retItem.add(item.getString(3)) ;
						retItem.add(item.getString(4)) ;
						retItem.add(item.getString(5)) ;
						dataArray.add(retItem);
					}
				}
			} catch (Exception e) {}
			
			js.accumulate("data", dataArray);
			jsonObject.accumulate(String.valueOf(ftrademapping.getFid()), js);
		}
		
		return jsonObject.toString();
	}
	
	
	@ResponseBody
	@RequestMapping("/real/userassets")
	public String userassets(
			HttpServletRequest request,
			@RequestParam(required=true)int symbol
			) throws Exception {
		JSONObject jsonObject = new JSONObject() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping(symbol) ;
		Fuser fuser = GetSession(request) ;
		if(fuser==null){
			//可用
			jsonObject.accumulate("availableCny", 0) ;
			jsonObject.accumulate("availableCoin", 0) ;
			jsonObject.accumulate("frozenCny", 0) ;
			jsonObject.accumulate("frozenCoin", 0) ;
			//借貸明細
			JSONObject leveritem = new JSONObject() ;
			leveritem.accumulate("total", 0) ;
			leveritem.accumulate("asset", 0) ;
			leveritem.accumulate("score", 0) ;
			jsonObject.accumulate("leveritem", leveritem) ;
			//人民幣明細
			JSONObject cnyitem = new JSONObject() ;
			cnyitem.accumulate("total", 0) ;
			cnyitem.accumulate("frozen",0) ;
			cnyitem.accumulate("borrow", 0) ;
			jsonObject.accumulate("cnyitem", cnyitem) ;
			//人民幣明細
			JSONObject coinitem = new JSONObject() ;
			coinitem.accumulate("id", symbol) ;
			coinitem.accumulate("total", 0) ;
			coinitem.accumulate("frozen",0) ;
			coinitem.accumulate("borrow", 0) ;
			jsonObject.accumulate("coinitem", coinitem) ;
		}else{
			fuser = this.frontUserService.findById(fuser.getFid()) ;
			Fvirtualwallet fwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(),ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid());
			Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid()) ;
			
			//可用
			jsonObject.accumulate("availableCny", fwallet.getFtotal()) ;
			jsonObject.accumulate("availableCoin", fvirtualwallet.getFtotal()) ;
			jsonObject.accumulate("frozenCny", fwallet.getFfrozen()) ;
			jsonObject.accumulate("frozenCoin", fvirtualwallet.getFfrozen()) ;
			//借貸明細
			JSONObject leveritem = new JSONObject() ;
			Map<Integer,Fvirtualwallet> fvirtualwallets = this.frontUserService.findVirtualWallet(GetSession(request).getFid()) ;
			//估计总资产
			//CNY+冻结CNY+（币种+冻结币种）*最高买价
			double totalCapital = 0F ;
			totalCapital+=Utils.add(fwallet.getFtotal(), fwallet.getFfrozen() );
			Map<Integer,Integer> tradeMappings = (Map)this.constantMap.get("tradeMappings");
			for (Map.Entry<Integer, Fvirtualwallet> entry : fvirtualwallets.entrySet()) {
				if(entry.getValue().getFvirtualcointype().getFtype() == CoinTypeEnum.FB_CNY_VALUE) continue;
				//2017-04-07 转换计算方式
				double frozen = entry.getValue().getFfrozen();
				double total = entry.getValue().getFtotal();
				double sum = Utils.add(frozen,total);
				double lates_price = this.realTimeData.getLatestDealPrize(tradeMappings.get(entry.getValue().getFvirtualcointype().getFid()));
				totalCapital += Utils.mul(sum, lates_price) ;
			}
			leveritem.accumulate("total", Utils.getDouble(totalCapital,2)) ;
			leveritem.accumulate("asset", 0) ;
			leveritem.accumulate("score", 0) ;
			jsonObject.accumulate("leveritem", leveritem) ;
			//人民幣明細
			JSONObject cnyitem = new JSONObject() ;
			cnyitem.accumulate("total", fwallet.getFtotal()) ;
			cnyitem.accumulate("frozen", fwallet.getFfrozen()) ;
			cnyitem.accumulate("borrow", 0) ;
			jsonObject.accumulate("cnyitem", cnyitem) ;
			//人民幣明細
			JSONObject coinitem = new JSONObject() ;
			coinitem.accumulate("id", fvirtualwallet.getFvirtualcointype().getFid()) ;
			coinitem.accumulate("total", fvirtualwallet.getFtotal()) ;
			coinitem.accumulate("frozen", fvirtualwallet.getFfrozen()) ;
			coinitem.accumulate("borrow", 0) ;
			jsonObject.accumulate("coinitem", coinitem) ;
		}

	    
		
		return jsonObject.toString() ;
		
	}
	
}
