package com.ruizton.main.controller.front;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ruizton.main.Enum.*;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fentrust;
import com.ruizton.main.model.Fentrustlog;
import com.ruizton.main.model.Flimittrade;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.service.front.FrontTradeService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.main.service.front.FtradeMappingService;
import com.ruizton.util.Utils;
import sun.nio.cs.ext.IBM1097;

@Controller
public class FrontTradeJsonController extends BaseController {


	/**
	 * 买入虚拟币
	 * @param request
	 * @param limited
	 * @param symbol
	 * @param tradeAmount
	 * @param tradeCnyPrice
	 * @param tradePwd
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/trade/buyBtcSubmit",produces={JsonEncode})
	public String buyBtcSubmit(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int limited,//是否按照市场价买入
			@RequestParam(required=true)int symbol,//币种
			@RequestParam(required=true)double tradeAmount,//数量
			@RequestParam(required=true)double tradeCnyPrice,//单价
			@RequestParam(required=false,defaultValue="")String tradePwd
			) throws Exception{
		
		JSONObject jsonObject = new JSONObject() ;

		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		if(ftrademapping==null  ||ftrademapping.getFstatus()==TrademappingStatusEnum.FOBBID){
			jsonObject.accumulate("resultCode", -100) ;
//			jsonObject.accumulate("msg", "该币暂时不能交易");
			jsonObject.accumulate(MSG, i18nMsg("cncy_unable_trade"));
			return jsonObject.toString() ;
		}
		
		//限制法币为人民币和比特币
		if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_CNY_VALUE
				&& ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_COIN_VALUE){
			jsonObject.accumulate("resultCode", -1) ;
//			jsonObject.accumulate("msg", "网络错误");
			jsonObject.accumulate(MSG, i18nMsg("network_error"));
			return jsonObject.toString() ;
		
		}
		
		Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
		Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;
		double minBuyCount = Utils.getDouble(ftrademapping.getFminBuyCount() ,ftrademapping.getFcount2()) ;
		double minBuyPrice = Utils.getDouble(ftrademapping.getFminBuyPrice(),ftrademapping.getFcount1()) ;
		double minBuyAmount = Utils.getDouble(ftrademapping.getFminBuyAmount(),ftrademapping.getFcount1()) ;
		
		//是否开放交易
		if(Utils.openTrade(ftrademapping,Utils.getTimestamp())==false){
			jsonObject.accumulate("resultCode", -400) ;
//			jsonObject.accumulate("msg", "现在不是交易时间");
			jsonObject.accumulate(MSG, i18nMsg("now_not_trade_time"));
			return jsonObject.toString() ;
		}
		
		tradeAmount = Utils.getDouble(tradeAmount, ftrademapping.getFcount2()) ;
		tradeCnyPrice = Utils.getDouble(tradeCnyPrice, ftrademapping.getFcount1()) ;
		
		//定价单
		if(limited == 0 ){
			
			if(tradeAmount<minBuyCount){
				jsonObject.accumulate("resultCode", -1) ;
//				jsonObject.accumulate("msg", "最小交易数量："+coin1.getfSymbol()+minBuyCount);
				jsonObject.accumulate(MSG, i18nMsg("min_trade_num") + coin1.getfSymbol()+minBuyCount);
				return jsonObject.toString() ;
			}
			
			if(tradeCnyPrice < minBuyPrice){
				jsonObject.accumulate("resultCode", -3) ;
//				jsonObject.accumulate("msg", "最小挂单价格："+coin1.getfSymbol()+minBuyPrice);
				jsonObject.accumulate(MSG, i18nMsg("min_entruts_price") + coin1.getfSymbol()+minBuyPrice);
				return jsonObject.toString() ;
			}

			double total = Utils.getDouble(Utils.mul(tradeAmount, tradeCnyPrice), ftrademapping.getFcount1());
			if(total < minBuyAmount){
				jsonObject.accumulate("resultCode", -3) ;
//				jsonObject.accumulate("msg", "最小挂单金额："+coin1.getfSymbol()+minBuyAmount);
				jsonObject.accumulate(MSG, i18nMsg("min_entruts_amount") + coin1.getfSymbol()+minBuyAmount);
				return jsonObject.toString() ;
			}
			
			
		}else{  //市价单
			if(tradeCnyPrice <minBuyAmount){
				jsonObject.accumulate("resultCode", -33) ;
//				jsonObject.accumulate("msg", "最小交易金额："+minBuyAmount+coin1.getFname());
				jsonObject.accumulate(MSG, i18nMsg("min_trade_amount") + minBuyAmount+coin1.getFname());
				return jsonObject.toString() ;
			}
		}
		
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;

		if(!ValidateKycLevelEnum.validateKYC1(fuser.getKyclevel())){
			jsonObject.accumulate("resultCode", -5) ;
			jsonObject.accumulate(MSG,  i18nMsg("no_kyc1_validate"));
			return jsonObject.toString() ;
		}
		double totalTradePrice = 0F ;
		if(limited==0){
			totalTradePrice = Utils.mul(tradeAmount, tradeCnyPrice);
		}else{
			totalTradePrice = tradeAmount ;
		}
		Fvirtualwallet fwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(),coin1.getFid());
		if(fwallet.getFtotal()<totalTradePrice){
			jsonObject.accumulate("resultCode", -4) ;
//			jsonObject.accumulate("msg", coin1.getFname()+"余额不足");
			jsonObject.accumulate(MSG, coin1.getFname() + i18nMsg("insuff_balance_desc"));
			return jsonObject.toString() ;
		}
		
		if(isNeedTradePassword(request)){
			if(tradePwd == null || tradePwd.trim().length() == 0){
				jsonObject.accumulate("resultCode", -50) ;
//				jsonObject.accumulate("msg", "交易密码错误");
				jsonObject.accumulate(MSG, i18nMsg("trade_pwd_error_desc"));
				 return jsonObject.toString() ;
			}
			
			if(fuser.getFtradePassword() == null){
				 jsonObject.accumulate("resultCode", -5) ;
//				 jsonObject.accumulate("msg", "您还没有设置交易密码，请到安全中心设置<a class='text-danger' href='/user/security.html'>安全中心&gt;&gt;</a>");
				jsonObject.accumulate(MSG, i18nMsg("s_pwd_desc"));
				 return jsonObject.toString() ;
		    }
			if(!Utils.MD5(tradePwd,fuser.getSalt()).equals(fuser.getFtradePassword())){
				jsonObject.accumulate("resultCode", -2) ;
//				jsonObject.accumulate("msg", "交易密码错误");
				jsonObject.accumulate(MSG, i18nMsg("trade_pwd_error_desc"));
				return jsonObject.toString() ;
			}
		}
		
		
		boolean flag = false ;
		Fentrust fentrust = null ;
		try {
			fentrust = this.frontTradeService.updateEntrustBuy(symbol, tradeAmount, tradeCnyPrice, fuser, limited==1,request) ;
			flag = true ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(flag && fentrust != null){
			fentrust = this.frontTradeService.findFentrustById(fentrust.getFid()) ;
			if (limited==1) {
				this.realTimeData.addEntrustLimitBuyMap(symbol, fentrust);
			} else {
				this.realTimeData.addEntrustBuyMap(symbol, fentrust);
			}
			
			jsonObject.accumulate("resultCode", 0) ;
//			jsonObject.accumulate("msg", "下单成功");
			jsonObject.accumulate(MSG, i18nMsg("entruts_success"));
			setNoNeedPassword(request) ;
		}else{
			jsonObject.accumulate("resultCode", -200) ;
//			jsonObject.accumulate("msg", "网络错误，请稍后再试");
			jsonObject.accumulate(MSG, i18nMsg("newword_error_try_again"));
		}
		
		return jsonObject.toString() ;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/trade/sellBtcSubmit",produces={JsonEncode})
	public String sellBtcSubmit(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int limited,//是否按照市场价买入
			@RequestParam(required=true)int symbol,//币种
			@RequestParam(required=true)double tradeAmount,//数量
			@RequestParam(required=true)double tradeCnyPrice,//单价
			@RequestParam(required=false,defaultValue="")String tradePwd
			) throws Exception{

		JSONObject jsonObject = new JSONObject() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		if(ftrademapping==null  || ftrademapping.getFstatus()!=TrademappingStatusEnum.ACTIVE){
			jsonObject.accumulate("resultCode", -100) ;
//			jsonObject.accumulate("msg", "该币暂时不能交易");
			jsonObject.accumulate(MSG, i18nMsg("cncy_unable_trade"));
			return jsonObject.toString() ;
		}
		
		//限制法币为人民币和比特币
		if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_CNY_VALUE
				&& ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_COIN_VALUE){
			jsonObject.accumulate("resultCode", -1) ;
//			jsonObject.accumulate("msg", "网络错误");
			jsonObject.accumulate(MSG, i18nMsg("network_error"));
			return jsonObject.toString() ;
		
		}
		
		tradeAmount = Utils.getDouble(tradeAmount, ftrademapping.getFcount2()) ;
		tradeCnyPrice = Utils.getDouble(tradeCnyPrice, ftrademapping.getFcount1()) ;
		Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
		Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;
		double minBuyCount = ftrademapping.getFminBuyCount() ;
		double minBuyPrice = ftrademapping.getFminBuyPrice() ;
		double minBuyAmount = ftrademapping.getFminBuyAmount() ;
		
		//是否开放交易
		if(Utils.openTrade(ftrademapping,Utils.getTimestamp())==false){
			jsonObject.accumulate("resultCode", -400) ;
//			jsonObject.accumulate("msg", "现在不是交易时间");
			jsonObject.accumulate(MSG, i18nMsg("now_not_trade_time"));
			return jsonObject.toString() ;
		}
		
		if(limited == 0 ){
			
			if(tradeAmount<minBuyCount){
				jsonObject.accumulate("resultCode", -1) ;
//				jsonObject.accumulate("msg", "最小交易数量："+coin1.getfSymbol()+minBuyCount);
				jsonObject.accumulate(MSG, i18nMsg("min_trade_num") + coin1.getfSymbol() + minBuyCount);
				return jsonObject.toString() ;
			}
			
			if(tradeCnyPrice < minBuyPrice){
				jsonObject.accumulate("resultCode", -3) ;
//				jsonObject.accumulate("msg", "最小挂单价格："+coin1.getfSymbol()+minBuyPrice);
				jsonObject.accumulate(MSG, i18nMsg("min_entruts_price") + coin1.getfSymbol() + minBuyPrice);
				return jsonObject.toString() ;
			}
			double total = Utils.getDouble(tradeAmount*tradeCnyPrice,ftrademapping.getFcount1());
			if(total < minBuyAmount){
				jsonObject.accumulate("resultCode", -3) ;
//				jsonObject.accumulate("msg", "最小挂单金额："+coin1.getfSymbol()+minBuyAmount);
				jsonObject.accumulate(MSG, i18nMsg("min_entruts_amount") + coin1.getfSymbol() + minBuyAmount);
				return jsonObject.toString() ;
			}
			
		}else{
			if(tradeAmount <minBuyCount){
				jsonObject.accumulate("resultCode", -33) ;
//				jsonObject.accumulate("msg", "最小交易数量："+minBuyCount+coin2.getFname());
				jsonObject.accumulate(MSG, i18nMsg("min_trade_num") + minBuyCount + coin2.getFname());
				return jsonObject.toString() ;
			}
		}
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		if(!ValidateKycLevelEnum.validateKYC1(fuser.getKyclevel())){
			jsonObject.accumulate("resultCode", -5) ;
			jsonObject.accumulate(MSG, i18nMsg("no_kyc1_validate"));
			return jsonObject.toString() ;
		}

		Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), coin2.getFid()) ;
		if(fvirtualwallet==null){
			jsonObject.accumulate("resultCode", -200) ;
//			jsonObject.accumulate("msg", "系统错误，请联系管理员");
			jsonObject.accumulate(MSG, i18nMsg("please_contact_admin"));
			return jsonObject.toString() ;
		}
		if(fvirtualwallet.getFtotal()<tradeAmount){
			jsonObject.accumulate("resultCode", -4) ;
//			jsonObject.accumulate("msg", coin2.getFname()+"余额不足");
			jsonObject.accumulate(MSG, coin2.getFname() + i18nMsg("insuff_balance_desc"));
			return jsonObject.toString() ;
		}
		
		if(isNeedTradePassword(request)){
			if(tradePwd == null || tradePwd.trim().length() == 0){
				jsonObject.accumulate("resultCode", -50) ;
//				jsonObject.accumulate("msg", "交易密码错误");
				jsonObject.accumulate(MSG, i18nMsg("trade_pwd_error_desc"));
				 return jsonObject.toString() ;
			}
			
			if(fuser.getFtradePassword() == null){
				 jsonObject.accumulate("resultCode", -5) ;
//				 jsonObject.accumulate("msg", "您还没有设置交易密码，请到安全中心设置<a class='text-danger' href='/user/security.html'>安全中心&gt;&gt;</a>");
				jsonObject.accumulate(MSG, i18nMsg("s_pwd_desc"));
				 return jsonObject.toString() ;
		    }
			if(!Utils.MD5(tradePwd,fuser.getSalt()).equals(fuser.getFtradePassword())){
				jsonObject.accumulate("resultCode", -2) ;
//				jsonObject.accumulate("msg", "交易密码错误");
				jsonObject.accumulate(MSG, i18nMsg("trade_pwd_error_desc"));
				return jsonObject.toString() ;
			}
		}
		
		
		boolean flag = false ;
		Fentrust fentrust = null ;
		try {
			fentrust = this.frontTradeService.updateEntrustSell(symbol, tradeAmount, tradeCnyPrice, fuser, limited==1,request) ;
			flag = true ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(flag && fentrust != null){
			fentrust = this.frontTradeService.findFentrustById(fentrust.getFid()) ;
			if (limited==1) {
				this.realTimeData.addEntrustLimitSellMap(symbol, fentrust);
			} else {
				this.realTimeData.addEntrustSellMap(symbol, fentrust);
			}
			
			
			jsonObject.accumulate("resultCode", 0) ;
//			jsonObject.accumulate("msg", "下单成功");
			jsonObject.accumulate(MSG, i18nMsg("entruts_success"));
			setNoNeedPassword(request);
		}else{
			jsonObject.accumulate("resultCode", -200) ;
//			jsonObject.accumulate("msg", "网络错误，请稍后再试");
			jsonObject.accumulate(MSG, i18nMsg("newword_error_try_again"));
		}
		
		return jsonObject.toString() ;
	}
	
	@ResponseBody
	@RequestMapping(value="/trade/cancelEntrust",produces=JsonEncode)
	public String cancelEntrust(
			HttpServletRequest request,
			@RequestParam(required=true)int id
			) throws Exception{
		
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fentrust fentrust = this.frontTradeService.findFentrustById(id) ;
		if(fentrust!=null
				&&(fentrust.getFstatus()==EntrustStatusEnum.Going || fentrust.getFstatus()==EntrustStatusEnum.PartDeal )
				&&fentrust.getFuser().getFid() == fuser.getFid() ){
			boolean flag = false ;
			try {
				this.frontTradeService.updateCancelFentrust(fentrust, fuser) ;
				flag = true ;
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(flag==true){
				if(fentrust.getFentrustType()==EntrustTypeEnum.BUY){
					
					//买
					if(fentrust.isFisLimit()){
						this.realTimeData.removeEntrustLimitBuyMap(fentrust.getFtrademapping().getFid(), fentrust) ;
					}else{
						this.realTimeData.removeEntrustBuyMap(fentrust.getFtrademapping().getFid(), fentrust) ;
					}
				}else{
					
					//卖
					if(fentrust.isFisLimit()){
						this.realTimeData.removeEntrustLimitSellMap(fentrust.getFtrademapping().getFid(), fentrust) ;
					}else{
						this.realTimeData.removeEntrustSellMap(fentrust.getFtrademapping().getFid(), fentrust) ;
					}
					
				}
			}
		}
		
		jsonObject.accumulate("code", 0) ;
//		jsonObject.accumulate("msg", "取消成功") ;
		jsonObject.accumulate(MSG, i18nMsg("cancel_success"));
		return jsonObject.toString() ;
	}

	/**
	 * 一键撤销
	 * @param request
	 * @param id  法币匹配id
	 * @param type  0 全部撤销  1撤销买单   2撤销卖单
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/trade/cancelAllEntrust",produces=JsonEncode)
	public String cancelAllEntrust(
			HttpServletRequest request,
			@RequestParam(required=true)int id,
			@RequestParam(required = false, defaultValue = "0") int type
			) throws Exception{
		
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		String filter = "where fuser.fid="+fuser.getFid()+" and ftrademapping.fid="+id+" and fstatus in(1,2)";
		if (type == 1) {  //撤销买单
			filter += " and fentrustType = 0";
		} else if (type == 2) {  //撤销卖单
			filter += " and fentrustType = 1";
		}
		List<Fentrust> fentrusts = this.frontTradeService.findFentrustByParam(0, 0, filter, false);
		for (Fentrust fentrust : fentrusts) {
			if(fentrust!=null
					&&(fentrust.getFstatus()==EntrustStatusEnum.Going || fentrust.getFstatus()==EntrustStatusEnum.PartDeal )
					&&fentrust.getFuser().getFid() == fuser.getFid() ){
				boolean flag = false ;
				try {
					this.frontTradeService.updateCancelFentrust(fentrust, fuser) ;
					flag = true ;
				} catch (Exception e) {
//					e.printStackTrace();
				}
				if(flag==true){
					if(fentrust.getFentrustType()==EntrustTypeEnum.BUY){
						
						//买
						if(fentrust.isFisLimit()){
							this.realTimeData.removeEntrustLimitBuyMap(fentrust.getFtrademapping().getFid(), fentrust) ;
						}else{
							this.realTimeData.removeEntrustBuyMap(fentrust.getFtrademapping().getFid(), fentrust) ;
						}
					}else{
						
						//卖
						if(fentrust.isFisLimit()){
							this.realTimeData.removeEntrustLimitSellMap(fentrust.getFtrademapping().getFid(), fentrust) ;
						}else{
							this.realTimeData.removeEntrustSellMap(fentrust.getFtrademapping().getFid(), fentrust) ;
						}
						
					}
				}
			}
		}
		
		jsonObject.accumulate("code", 0) ;
//		jsonObject.accumulate("msg", "取消成功") ;
		jsonObject.accumulate(MSG, i18nMsg("cancel_success"));
		return jsonObject.toString() ;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/trade/entrustLog",produces=JsonEncode)
	public String entrustLog(
			HttpServletRequest request,
			@RequestParam(required=true)int id
			) throws Exception{
		
		JSONObject jsonObject = new JSONObject() ;
		
		if(GetSession(request) == null){
			jsonObject.accumulate("result", false) ;
		}
		
		
		Fentrust fentrust = this.frontTradeService.findFentrustById(id) ;
		if(fentrust==null){
			jsonObject.accumulate("result", false) ;
		}else{
			List<Fentrustlog> fentrustlogs = this.frontTradeService.findFentrustLogByFentrust(fentrust) ;
			
			jsonObject.accumulate("result", true) ;
//			jsonObject.accumulate("title", "详细成交情况[委托ID:"+id+"]") ;
			jsonObject.accumulate(MSG, i18nMsg("detail_entruts"));
			
			StringBuffer content = new StringBuffer() ;
//			content.append("<div> <table class=\"table\"> " +
//					"<tr> " +
//					"<td>成交时间</td> " +
//					"<td>委托类别</td> " +
//					"<td>委托价格</td> " +
//					"<td>成交价格</td> " +
//					"<td>成交数量</td> " +
//					"<td>成交金额</td> " +
//					"</tr>") ;
			content.append("<div> <table class=\"table\"> " +
					"<tr> " +
					"<td>" + i18nMsg("trade_success_time") + "</td> " +
					"<td>" + i18nMsg("entrust_type") + "</td> " +
					"<td>" + i18nMsg("entrust_price_title") + "</td> " +
					"<td>" + i18nMsg("trade_success_price") + "</td> " +
					"<td>" + i18nMsg("success_num_title") + "</td> " +
					"<td>" + i18nMsg("success_amount_title") + "</td> " +
					"</tr>");
			
			if(fentrustlogs.size()==0){
//				content.append("<tr><td colspan='6' class='no-data-tips'><span>暂无委托</span></td></tr>") ;
				content.append("<tr><td colspan='6' class='no-data-tips'><span>"+i18nMsg("no_entrust")+"</span></td></tr>") ;
			}
			
			Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(fentrust.getFtrademapping().getFid()) ;
			Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
			Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;
			
			for (Fentrustlog fentrustlog : fentrustlogs) {
				content.append("<tr> " +
									"<td class='gray'>"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fentrustlog.getFcreateTime())+"</td> " +
									"<td class='"+(fentrust.getFentrustType()==EntrustTypeEnum.BUY?"text-success":"text-danger")+"'>"+fentrust.getFentrustType_en()+"</td>" +
									"<td>" + (fentrust.isFisLimit() == true ? i18nMsg("market_price") : coin1.getfSymbol()+Utils.number2String(fentrust.getFprize())) + "</td>" +
									"<td>"+coin1.getfSymbol()+Utils.number2String(fentrustlog.getFprize())+"</td>" +
									"<td>"+Utils.number2String(fentrustlog.getFcount())+coin2.getfSymbol()+"</td>" +
									"<td>"+coin1.getfSymbol()+Utils.number2String(fentrustlog.getFamount())+"</td>" +
								"</tr>") ;
			}

			content.append("</table> </div>") ;
			jsonObject.accumulate("content", content.toString()) ;
		}
		return jsonObject.toString() ;
	}
}
