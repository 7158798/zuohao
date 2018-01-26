package com.ruizton.main.controller.front;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.EntrustStatusEnum;
import com.ruizton.main.Enum.TrademappingStatusEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fentrust;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.util.DateHelper;
import com.ruizton.util.PaginUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class FrontTradeController extends BaseController {

	
	@RequestMapping("/trade/coin")
	public ModelAndView coin(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int coinType,
			@RequestParam(required=false,defaultValue="0")int tradeType
			) throws Exception{
		
		
		ModelAndView modelAndView = new ModelAndView() ;
		int userid = 0;
		Fuser fuser = null;
		boolean isTelephoneBind =false;
		if(GetSession(request) != null){
			fuser = this.userService.findById(GetSession(request).getFid());
			userid = fuser.getFid();
			isTelephoneBind = fuser.isFisTelephoneBind();
		}
		
		
		tradeType = tradeType < 0? 0: tradeType ;
		tradeType = tradeType > 1? 1: tradeType ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(coinType) ;
		List<Ftrademapping> ftrademappings = null ;
		if(ftrademapping==null ||ftrademapping.getFstatus()==TrademappingStatusEnum.FOBBID ){
			ftrademappings = this.utilsService.list(0, 1, " where fstatus=? order by fid asc ", true, Ftrademapping.class,TrademappingStatusEnum.ACTIVE) ;
			if(ftrademappings.size()>0){
				modelAndView.setViewName("redirect:/trade/coin.html?coinType="+ftrademappings.get(0).getFid()+"&tradeType=0") ;
				return modelAndView ;
			}else{
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
		}
		
		//限制法币为人民币和比特币
		if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_CNY_VALUE
				&& ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_COIN_VALUE){
			modelAndView.setViewName("redirect:/") ;
			return modelAndView ;
		}
		
		ftrademappings = this.ftradeMappingService.findActiveTradeMappingByFB(ftrademapping.getFvirtualcointypeByFvirtualcointype1()) ;
		Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
		Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;
		
		
		boolean isTradePassword = false;
		if(userid !=0 && fuser.getFtradePassword() != null && fuser.getFtradePassword().trim().length() >0){
			isTradePassword = true;
		}
		
		//委托记录
		List<Fentrust> fentrusts = this.frontTradeService.findFentrustHistory(
				userid, coinType,null, 0, 10, " fid desc ", new int[]{EntrustStatusEnum.Going,EntrustStatusEnum.PartDeal}) ;
		
		//是否需要输入交易密码
		modelAndView.addObject("needTradePasswd", super.isNeedTradePassword(request)) ;

		String tradestock = this.klinePeriodData.getJsonString(coinType, 0) ; 
		modelAndView.addObject("tradestock", tradestock) ;
		modelAndView.addObject("fentrusts", fentrusts) ;
		modelAndView.addObject("fuser", fuser) ;
		modelAndView.addObject("userid", userid) ;
		modelAndView.addObject("isTradePassword", isTradePassword) ;
		modelAndView.addObject("isTelephoneBind", isTelephoneBind) ;
		modelAndView.addObject("recommendPrizesell", this.realTimeData.getHighestBuyPrize(coinType)) ;
		modelAndView.addObject("recommendPrizebuy", this.realTimeData.getLowestSellPrize(coinType)) ;
		modelAndView.addAllObjects(this.setRealTimeData(coinType)) ;
		modelAndView.addObject("coin1",coin1) ;
		modelAndView.addObject("coin2",coin2) ;
		modelAndView.addObject("ftrademappings",ftrademappings) ;
		modelAndView.addObject("ftrademapping",ftrademapping) ;
		modelAndView.addObject("coinType", coinType) ;
		modelAndView.addObject("tradeType", tradeType) ;
		modelAndView.setViewName("front/trade/trade_coin") ;
		return modelAndView ;
	}
	
	/*https://www.okcoin.com/trade/entrust.do?symbol=1
	 * */
	@RequestMapping("/trade/entrust")
	public ModelAndView entrust(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int symbol,
			@RequestParam(required=false,defaultValue="0")int status,
			@RequestParam(required=false,defaultValue="1")int currentPage
			)throws Exception{
		
		
		ModelAndView modelAndView = new ModelAndView() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		if(ftrademapping==null){
			modelAndView.setViewName("redirect:/") ;
			return modelAndView ;
		}
		
		List<Ftrademapping> ftrademappings = this.ftradeMappingService.findActiveTradeMappingByFB(ftrademapping.getFvirtualcointypeByFvirtualcointype1()) ;
		
		
		int fstatus[] = null ;
		if(status==0){
			//正在委托
			fstatus = new int[]{EntrustStatusEnum.Going,EntrustStatusEnum.PartDeal} ;
		}else{
			//委托完成
			fstatus = new int[]{EntrustStatusEnum.AllDeal,EntrustStatusEnum.Cancel} ;
		}
		
		List<Fentrust> fentrusts = 
				this.frontTradeService.findFentrustHistory(
						GetSession(request).getFid(), 
						ftrademapping.getFid(),
						null, PaginUtil.firstResult(currentPage, maxResults), 
						maxResults, 
						"id desc ", fstatus) ;
		int total = this.frontTradeService.findFentrustHistoryCount(
				GetSession(request).getFid(), 
				ftrademapping.getFid(),
				null,fstatus) ;
		String pagin = PaginUtil.generatePagin((int)(total/maxResults+(total%maxResults==0?0:1) ), currentPage, "/trade/entrust.html?symbol="+symbol+"&status="+status+"&") ;

		modelAndView.addObject("currentPage", currentPage) ;
		modelAndView.addObject("pagin",pagin) ;
		modelAndView.addObject("fentrusts", fentrusts) ;
		modelAndView.addObject("ftrademapping", ftrademapping) ;
		modelAndView.addObject("symbol", symbol) ;
		modelAndView.addObject("ftrademappings", ftrademappings) ;
		modelAndView.setViewName("front/trade/trade_entrust") ;
		return modelAndView ;
	}
	
	
	/*
	 * @param type:0未成交前十条，1成交前10条
	 * @param symbol:1币种
	 * */
	@RequestMapping("/trade/entrustInfo")
	public ModelAndView entrustInfo(
			HttpServletRequest request,
			@RequestParam(required=true)int type,
			@RequestParam(required=true)int symbol,
			@RequestParam(required=true)int tradeType
			) throws Exception{

		ModelAndView modelAndView = new ModelAndView() ;
		
		int userid = 0;
		if(GetSession(request) != null){
			userid = GetSession(request).getFid();
		}
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		if(ftrademapping==null || ftrademapping.getFstatus()==TrademappingStatusEnum.FOBBID){
			LOG.i(this, "查询ftrademapping为空或状态禁用");
			modelAndView.setViewName("redirect:/") ;
			return modelAndView ;
		}
		Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
		Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;


		List<Fentrust> fentrusts1 = null ;
		fentrusts1 = this.frontTradeService.findFentrustHistory(
				userid, symbol,null, 0, 10, " fid desc ", new int[]{EntrustStatusEnum.Going,EntrustStatusEnum.PartDeal}) ;
		modelAndView.addObject("ftrademapping", ftrademapping) ;
		modelAndView.addObject("coin1", coin1) ;
		modelAndView.addObject("coin2", coin2) ;
		modelAndView.addObject("tradeType", tradeType) ;
		modelAndView.addObject("symbol",symbol) ;
		modelAndView.addObject("type", type) ;
		modelAndView.addObject("fentrusts1", fentrusts1) ;
		modelAndView.setViewName("front/trade/entrust_info") ;
		return modelAndView ;
	}




	/**
	 * 新行情中心>限价委托[当前委托]
	 * @param request
	 * @param type 0未成交前十条，1成交前10条
	 * @param symbol 币种
	 * @param tradeType 委托状态  0查询未成交的(含未成交)，1查询未成交的（含未成交、部分成交），2查询已成交的(完全成交)，3查询已完成（含完全成交、撤销）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/trade/entrustInfo_current", produces = {JsonEncode})
	@ResponseBody
	public String entrustInfoBycount(HttpServletRequest request,
									 @RequestParam(required=true)int type,
									 @RequestParam(required=true)int symbol,
									 @RequestParam(required=true)int tradeType) throws Exception{
		JSONObject resultJson = new JSONObject() ;

		int userid = 0;
		if(GetSession(request) != null){
			userid = GetSession(request).getFid();
		}else{
			resultJson.accumulate("code", -1);
			return resultJson.toString();
		}

		//查询法币类型匹配信息
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		if(ftrademapping==null || ftrademapping.getFstatus()==TrademappingStatusEnum.FOBBID){
			resultJson.accumulate("code", -1);
			return resultJson.toString();
		}

		Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
		Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;

		JSONObject js_coin1 = new JSONObject();
		js_coin1.accumulate("fname",coin1.getFname());
		js_coin1.accumulate("fShortName",coin1.getfShortName());
		js_coin1.accumulate("fSymbol", coin1.getfSymbol());

		JSONObject js_coin2 = new JSONObject();
		js_coin2.accumulate("fname",coin2.getFname());
		js_coin2.accumulate("fShortName",coin2.getfShortName());
		js_coin2.accumulate("fSymbol", coin2.getfSymbol());


		JSONObject js_trademapp = new JSONObject();
		js_trademapp.accumulate("fid", ftrademapping.getFid());
		js_trademapp.accumulate("version", ftrademapping.getVersion());
		js_trademapp.accumulate("ftradeTime", ftrademapping.getFtradeTime());
		js_trademapp.accumulate("fisLimit", ftrademapping.isFisLimit());
		js_trademapp.accumulate("fstatus", ftrademapping.getFstatus());
		js_trademapp.accumulate("fstatus_s", ftrademapping.getFstatus_s());
		js_trademapp.accumulate("fcount1", ftrademapping.getFcount1());
		js_trademapp.accumulate("fcount2", ftrademapping.getFcount2());
		js_trademapp.accumulate("fminBuyCount", ftrademapping.getFminBuyCount());
		js_trademapp.accumulate("fminBuyPrice", ftrademapping.getFminBuyPrice());
		js_trademapp.accumulate("fminBuyAmount", ftrademapping.getFminBuyAmount());
		js_trademapp.accumulate("fprice", ftrademapping.getFprice());

		int entrust_status[] = null;
		if(tradeType == 0) {
			entrust_status = new int[1];
			entrust_status[0] = EntrustStatusEnum.Going;
		}else if(tradeType == 1) {
			entrust_status = new int[2];
			entrust_status[0] = EntrustStatusEnum.Going;
			entrust_status[1] = EntrustStatusEnum.PartDeal;
		}else if(tradeType == 2) {
			entrust_status = new int[1];
			entrust_status[0] = EntrustStatusEnum.AllDeal;
		}else if(tradeType == 3) {
			entrust_status = new int[2];
			entrust_status[0] = EntrustStatusEnum.AllDeal;
			entrust_status[1] = EntrustStatusEnum.Cancel;
		}
		List<Fentrust> fentrusts1 = this.frontTradeService.findFentrustHistory(userid, symbol,null, 0, 10, " fid desc ", entrust_status) ;

		JSONArray fentrust_logs = new JSONArray() ;
		if(fentrusts1 != null && fentrusts1.size() >0) {
			for(Fentrust fentrust : fentrusts1) {
				JSONObject js1 = new JSONObject();
				js1.accumulate("fid",fentrust.getFid());
				js1.accumulate("fcreateTime", DateHelper.date2String(fentrust.getFcreateTime(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
				js1.accumulate("flastUpdatTime",DateHelper.date2String(fentrust.getFlastUpdatTime(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
				js1.accumulate("fentrustType",fentrust.getFentrustType());
				js1.accumulate("fentrustType_s",fentrust.getFentrustType_s());
				js1.accumulate("fprize",fentrust.getFprize());
				js1.accumulate("famount",fentrust.getFamount());
				js1.accumulate("ffees",fentrust.getFfees());
				js1.accumulate("fleftfees",fentrust.getFleftfees());
				js1.accumulate("fsuccessAmount",fentrust.getFsuccessAmount());
				js1.accumulate("fcount",fentrust.getFcount());
				js1.accumulate("fleftCount",fentrust.getFleftCount());
				js1.accumulate("fstatus",fentrust.getFstatus());
				js1.accumulate("fstatus_s",fentrust.getFstatus_s());
				js1.accumulate("fisLimit",fentrust.isFisLimit());
				js1.accumulate("version",fentrust.getVersion());
				js1.accumulate("fhasSubscription",fentrust.isFhasSubscription());

				fentrust_logs.add(js1);
			}
		}


		resultJson.accumulate("code", 0);
		resultJson.accumulate("ftrademapping", js_trademapp);
		resultJson.accumulate("coin1", js_coin1);
		resultJson.accumulate("coin2", js_coin2);
		resultJson.accumulate("type", type);
		resultJson.accumulate("tradeType", tradeType);
		resultJson.accumulate("symbol", symbol);
		resultJson.accumulate("fentrusts1", fentrust_logs);


		return resultJson.toString();
	}

}
