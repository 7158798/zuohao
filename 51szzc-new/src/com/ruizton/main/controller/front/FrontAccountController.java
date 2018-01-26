package com.ruizton.main.controller.front;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.ruizton.main.Enum.*;
import com.ruizton.main.aspect.SysLog;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.*;
import com.ruizton.main.model.integral.Fusergrade;
import com.ruizton.util.*;
import com.ruizton.util.alipayUtil.Base64;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.comm.KeyValues;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.CapitaloperationService;
import com.ruizton.main.service.admin.FeeService;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.admin.WithdrawFeesService;
import com.ruizton.main.service.front.FrontAccountService;
import com.ruizton.main.service.front.FrontBankInfoService;
import com.ruizton.main.service.front.FrontTradeService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.main.service.front.UtilsService;

@Controller
public class FrontAccountController extends BaseController{


	//充值人民币 初始化页面
	@RequestMapping("account/rechargeCny")
	public ModelAndView rechargeCny(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage,
			@RequestParam(required=false,defaultValue="0")int type
			) throws Exception{
		
		if(type !=0  && type !=3&& type !=4){
			type =0;
		}
		
		ModelAndView modelAndView = new ModelAndView() ;
		
		//人民币随机尾数
		int randomMoney = ((new Random().nextInt(9)+1)*10) ;
		modelAndView.addObject("randomMoney",randomMoney) ;

		//record
		Fuser fuser = this.GetSession(request) ;
		StringBuffer filter = new StringBuffer();
		filter.append("where fuser.fid="+fuser.getFid()+" \n");
		filter.append("and ftype ="+CapitalOperationTypeEnum.RMB_IN+"\n");
//		filter.append("and fstatus <>"+ CapitalOperationInStatus.Invalidate+"\n");
		/*if(type !=0){
			filter.append("and fremittanceType='"+RemittanceTypeEnum.getEnumString(type)+"' \n");
		}else{
			filter.append("and systembankinfo is not null \n");
		}*/
		filter.append("and systembankinfo is not null \n");
		filter.append(" order by fid desc \n");
		List<Fcapitaloperation> list = this.utilsService.list(PaginUtil.firstResult(currentPage, maxResults), maxResults, filter.toString(), true,Fcapitaloperation.class);
		
		
		int totalCount = this.adminService.getAllCount("Fcapitaloperation", filter.toString());
		String url = "/account/rechargeCny.html?type="+type+"&";
		String pagin = PaginUtil.generatePagin(PaginUtil.totalPage(totalCount, maxResults),currentPage,url) ;
		
		//最小充值金额
		double minRecharge = Double.parseDouble(this.constantMap.get("minrechargecny").toString().trim()) ;
		modelAndView.addObject("minRecharge", minRecharge) ;

		//限制支付宝的充值上，下限
		if(type == RemittanceTypeEnum.Type3) {
			double minalipayrgc = Double.parseDouble(this.constantMap.get("minalipayrgc").toString().trim());
			double maxalipayrgc = Double.parseDouble(this.constantMap.get("maxalipayrgc").toString().trim());
			modelAndView.addObject("minalipayrgc", minalipayrgc) ;
			modelAndView.addObject("maxalipayrgc", maxalipayrgc) ;

			//支付宝充值次数限制
			int alipayrgcnum = Integer.parseInt(this.constantMap.get("alipayrgcnum").toString().trim());
			//用户已充值的次数
			int user_alipay_num = this.capitaloperationService.queryCountByRemittanceType(RemittanceTypeEnum.getEnumString(type), fuser.getFid());
			modelAndView.addObject("alipayrgcnum", alipayrgcnum) ;
			modelAndView.addObject("user_alipay_num", user_alipay_num) ;

			//系统支付宝帐号(暂时只有一个)
//			List<Systembankinfo> systembankinfos = this.frontBankInfoService.findAllSystemBankInfo( 1) ;
//			modelAndView.addObject("sysalipay",systembankinfos) ;
		}else if(type == RemittanceTypeEnum.Type1) {
			//系统银行账号
			List<Systembankinfo> systembankinfos = this.frontBankInfoService.findAllSystemBankInfo( 0) ;
			modelAndView.addObject("bankInfo",systembankinfos) ;
		}

		Map<Integer,String> bankTypes = new HashMap<Integer,String>() ;
		for(int i=1;i<=BankTypeEnum.QT;i++){
			if(BankTypeEnum.getEnumString(i) != null && BankTypeEnum.getEnumString(i).trim().length() >0){
				bankTypes.put(i,BankTypeEnum.getEnumString(i)) ;
			}			
		}
		modelAndView.addObject("bankTypes", bankTypes) ;

		//用户绑定的支付宝账户信息
		List<FalipayBind> alipaybind_list = falipayBindService.findListByUserId(fuser.getFid());
		modelAndView.addObject("alipaybind_list",alipaybind_list) ;

		//用户绑定的银行卡信息
		String filters = "where fuser.fid="+fuser.getFid()+" and fbankType >0";
		List<FbankinfoWithdraw> fbankinfoWithdraws =this.frontUserService.findFbankinfoWithdrawByFuser(0, 0, filters, false);
		for (FbankinfoWithdraw fbankinfoWithdraw : fbankinfoWithdraws) {
			int l = fbankinfoWithdraw.getFbankNumber().length();
			fbankinfoWithdraw.setFbankNumber(fbankinfoWithdraw.getFbankNumber().substring(l-4, l));
		}


		
//		boolean isproxy = false;
//		String ss = "where fuser.fid="+fuser.getFid()+" and fstatus=1";
//		int cc = this.adminService.getAllCount("Fproxy", ss);
//		if(cc >0){
//			isproxy = true;
//		}
//		modelAndView.addObject("isproxy", isproxy) ;
		modelAndView.addObject("fbankinfoWithdraws",fbankinfoWithdraws) ;
		modelAndView.addObject("list", list) ;
		modelAndView.addObject("pagin", pagin) ;
		modelAndView.addObject("currentPage", currentPage) ;
		modelAndView.addObject("fuser",GetSession(request)) ;
		modelAndView.addObject("type", type) ;
		modelAndView.addObject("bank_account_address1", i18nMsg("bank_account_address1")) ;
		modelAndView.setViewName("front/account/account_rechargecny"+type) ;
		return modelAndView ;
	}
	
	
	@RequestMapping("account/withdrawCny")
	public ModelAndView withdrawCny(
			@RequestParam(required=false,defaultValue="1")int currentPage,
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		String filter = "where fuser.fid="+fuser.getFid()+" and fbankType >0";
		List<FbankinfoWithdraw> fbankinfoWithdraws =this.frontUserService.findFbankinfoWithdrawByFuser(0, 0, filter, false);
		for (FbankinfoWithdraw fbankinfoWithdraw : fbankinfoWithdraws) {
			int l = fbankinfoWithdraw.getFbankNumber().length();
			fbankinfoWithdraw.setFbankNumber(fbankinfoWithdraw.getFbankNumber().substring(l-4, l));
		}
		
		Map<Integer,String> bankTypes = new HashMap<Integer,String>() ;
		for(int i=1;i<=BankTypeEnum.QT;i++){
			if(BankTypeEnum.getEnumString(i) != null && BankTypeEnum.getEnumString(i).trim().length() >0){
				bankTypes.put(i,BankTypeEnum.getEnumString(i)) ;
			}			
		}
		modelAndView.addObject("bankTypes", bankTypes) ;
		
		Fvirtualwallet fwallet = this.frontUserService.findWalletByUser(GetSession(request).getFid());
		request.getSession().setAttribute("fwallet", fwallet) ;
		
		//double fee = this.withdrawFeesService.findFfee(fwallet.getFvirtualcointype().getFid(),fuser.getFscore().getFlevel()).getFfee();
        //修改交易手续费
		Fusergrade fusergrade = this.userGradeService.findById(fuser.getFscore().getFlevel());
		modelAndView.addObject("fee", fusergrade.getFoutfee()) ;
		
		//獲得千12條交易記錄
		String param = "where fuser.fid="+fuser.getFid()+" and ftype="+CapitalOperationTypeEnum.RMB_OUT+" order by fid desc";
		List<Fcapitaloperation> fcapitaloperations = this.frontAccountService.findCapitalList(PaginUtil.firstResult(currentPage, maxResults),maxResults, param, true) ;
		int totalCount = this.adminService.getAllCount("Fcapitaloperation", param.toString());
		String url = "/account/withdrawCny.html?";
		String pagin = PaginUtil.generatePagin(totalCount/maxResults+( (totalCount%maxResults)==0?0:1), currentPage,url) ;
		
		boolean isBindGoogle = fuser.getFgoogleBind() ;
		boolean isBindTelephone = fuser.isFisTelephoneBind() ;
		modelAndView.addObject("isBindGoogle", isBindGoogle) ;
        modelAndView.addObject("isBindTelephone", isBindTelephone) ;
		
		modelAndView.addObject("pagin", pagin) ;
		modelAndView.addObject("fcapitaloperations", fcapitaloperations) ;
		modelAndView.addObject("fuser",fuser) ;
		modelAndView.addObject("fbankinfoWithdraws",fbankinfoWithdraws) ;
		modelAndView.addObject("bank_account_address1", i18nMsg("bank_account_address1"));
		modelAndView.setViewName("front/account/account_withdrawcny") ;
		return modelAndView ;
	}
	
	@RequestMapping("account/rechargeBtc")
	public ModelAndView rechargeBtc(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage,
			@RequestParam(required=false,defaultValue="0")int symbol
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		if(fvirtualcointype==null 
				|| fvirtualcointype.getFtype()==CoinTypeEnum.FB_CNY_VALUE 
				|| fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal 
				|| !fvirtualcointype.isFisrecharge()){
			String filter = "where fstatus=1 and fisrecharge=1 and ftype <> "+CoinTypeEnum.FB_CNY_VALUE+" order by fid asc";
			List<Fvirtualcointype> alls = this.virtualCoinService.list(0, 1, filter, true);
			if(alls==null || alls.size() ==0){
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
			fvirtualcointype = alls.get(0);
		}
		symbol = fvirtualcointype.getFid();
		Fvirtualaddress fvirtualaddress = this.frontVirtualCoinService.findFvirtualaddress(fuser, fvirtualcointype) ;
		
		//最近十次充值记录
		String filter ="where fuser.fid="+fuser.getFid()+" and ftype="+VirtualCapitalOperationTypeEnum.COIN_IN
				+" and fvirtualcointype.fid="+fvirtualcointype.getFid()+" order by fid desc";
		List<Fvirtualcaptualoperation> fvirtualcaptualoperations = this.utilsService.list(PaginUtil.firstResult(currentPage, maxResults), maxResults, filter, true,Fvirtualcaptualoperation.class);
		int totalCount = this.adminService.getAllCount("Fvirtualcaptualoperation", filter.toString());
		String url = "/account/rechargeBtc.html?symbol="+symbol+"&";
		String pagin = PaginUtil.generatePagin(totalCount/maxResults+( (totalCount%maxResults)==0?0:1), currentPage,url) ;
		
		modelAndView.addObject("pagin", pagin) ;
		modelAndView.addObject("fvirtualcaptualoperations",fvirtualcaptualoperations) ;
		modelAndView.addObject("fvirtualcointype",fvirtualcointype) ;
		modelAndView.addObject("symbol", symbol) ;
		modelAndView.addObject("fvirtualaddress", fvirtualaddress) ;
		modelAndView.setViewName("front/account/account_rechargebtc") ;
		return modelAndView ;
	}
	
	
	
	@RequestMapping("account/withdrawBtc")
	public ModelAndView withdrawBtc(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage,
			@RequestParam(required=false,defaultValue="0")int symbol
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		if(fvirtualcointype==null ||fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal
				 || !fvirtualcointype.isFIsWithDraw() ||fvirtualcointype.getFtype()==CoinTypeEnum.FB_CNY_VALUE){
			String filter = "where fstatus=1 and FIsWithDraw=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE+" order by fid asc";
			List<Fvirtualcointype> alls = this.virtualCoinService.list(0, 1, filter, true);
			if(alls==null || alls.size() ==0){
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
			fvirtualcointype = alls.get(0);
		}
		symbol = fvirtualcointype.getFid();
		
		
		Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid()) ;
		String sql ="where fuser.fid="+fuser.getFid()+" and fvirtualcointype.fid="+symbol;
		List<FvirtualaddressWithdraw> fvirtualaddressWithdraws = this.frontVirtualCoinService.findFvirtualaddressWithdraws(0, 0, sql, false);
		
		//近10条提现记录
		String filter ="where fuser.fid="+fuser.getFid()+" and ftype="+VirtualCapitalOperationTypeEnum.COIN_OUT
				+" and fvirtualcointype.fid="+fvirtualcointype.getFid()+" order by fid desc";
		List<Fvirtualcaptualoperation> fvirtualcaptualoperations = this.utilsService.list(PaginUtil.firstResult(currentPage, maxResults), maxResults, filter, true,Fvirtualcaptualoperation.class);
		int totalCount = this.adminService.getAllCount("Fvirtualcaptualoperation", filter.toString());
		String url = "/account/withdrawBtc.html?symbol="+symbol+"&";
		String pagin = PaginUtil.generatePagin(totalCount/maxResults+( (totalCount%maxResults)==0?0:1), currentPage,url) ;
		
		modelAndView.addObject("pagin", pagin) ;
		
		int isEmptyAuth = 0;
		if(fuser.isFisTelephoneBind() || fuser.getFgoogleBind()){
			isEmptyAuth = 1;
		}
		modelAndView.addObject("isEmptyAuth",isEmptyAuth) ;
		
		boolean isBindGoogle = fuser.getFgoogleBind() ;
		boolean isBindTelephone = fuser.isFisTelephoneBind() ;
		modelAndView.addObject("isBindGoogle", isBindGoogle) ;
        modelAndView.addObject("isBindTelephone", isBindTelephone) ;
        
        String xx = "where fvirtualcointype.fid="+fvirtualcointype.getFid()+" and (ffee >0 or flevel=5)  order by flevel asc";
        List<Fwithdrawfees> withdrawFees = this.withdrawFeesService.list(0, 0, xx, false);
        modelAndView.addObject("withdrawFees",withdrawFees) ;
		
		modelAndView.addObject("symbol",symbol) ;
		modelAndView.addObject("fvirtualcaptualoperations", fvirtualcaptualoperations) ;
		modelAndView.addObject("fvirtualwallet",fvirtualwallet) ;
		modelAndView.addObject("fuser",fuser) ;
		modelAndView.addObject("fvirtualaddressWithdraws", fvirtualaddressWithdraws) ;
		modelAndView.addObject("fvirtualcointype",fvirtualcointype) ;
		modelAndView.setViewName("front/account/account_withdrawbtc") ;
		return modelAndView ;
	}
	
	
	
	@RequestMapping("/account/record")
	public ModelAndView record(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int recordType,
			@RequestParam(required=false,defaultValue="0")int symbol,
			@RequestParam(required=false,defaultValue="1")int currentPage,
			@RequestParam(required=false,defaultValue="2")int datetype,
			@RequestParam(required=false,defaultValue="")String begindate,
			@RequestParam(required=false,defaultValue="")String enddate
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(!(datetype >=1 && datetype <=4)){
			datetype =2;
		}
		if(enddate == null || enddate.trim().length() ==0){
			enddate = sdf.format(new Date());
		}else{
			try {
				enddate = sdf.format(sdf.parse(HtmlUtils.htmlEscape(enddate)));
			} catch (Exception e) {
				enddate = "";
			}
		}
		if(begindate == null || begindate.trim().length() ==0){
			switch (datetype) {
			case 1:
				begindate = sdf.format(new Date());
				break;
	        case 2:
	        	begindate = Utils.getAfterDay(7);
				break;
	        case 3:
	        	begindate = Utils.getAfterDay(15);
	    	    break;
	        case 4:
	        	begindate = Utils.getAfterDay(30);
		       break;
			}
		}else{
			try {
				begindate = sdf.format(sdf.parse(HtmlUtils.htmlEscape(begindate)));
			} catch (Exception e) {
				begindate = "";
			}
		}
		
		
		
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		if(fvirtualcointype==null || fvirtualcointype.getFtype() != CoinTypeEnum.COIN_VALUE){
			fvirtualcointype = this.frontVirtualCoinService.findFirstFirtualCoin() ;
			symbol = fvirtualcointype.getFid() ;
		}
		
		if(recordType>TradeRecordTypeEnum.BTC_WITHDRAW){
			recordType = TradeRecordTypeEnum.BTC_WITHDRAW ;
		}
		
		String filter = "where fstatus=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE+" and (FIsWithDraw=1 or fisrecharge=1)";
		List<Fvirtualcointype> fvirtualcointypes = this.virtualCoinService.list(0, 0, filter, false);
		//过滤器
		List<KeyValues> filters = new ArrayList<KeyValues>() ;
		for (int i = 1; i <= TradeRecordTypeEnum.BTC_WITHDRAW; i++) {
			if(i==1 || i==2){
				KeyValues keyValues = new KeyValues() ;
				String key = "/account/record.html?recordType="+i+"&symbol=0" ;
				String value = TradeRecordTypeEnum.getEnumString(i) ;
				keyValues.setKey(key) ;
				keyValues.setValue(value) ;
				filters.add(keyValues) ;
			}else{
				String key = "/account/record.html?recordType="+i+"&symbol=" ;
				for (int j = 0; j < fvirtualcointypes.size(); j++) {
					String value = TradeRecordTypeEnum.getEnumString(i) ;
					Fvirtualcointype vc = fvirtualcointypes.get(j) ;
					
					if(i==TradeRecordTypeEnum.BTC_WITHDRAW){
						if(!vc.isFIsWithDraw()){
							continue ;
						}
					}
					
					if(i==TradeRecordTypeEnum.BTC_RECHARGE){
						if(!vc.isFisrecharge()){
							continue ;
						}
					}
					
					value = vc.getfShortName()+value ;
					KeyValues keyValues = new KeyValues() ;
					keyValues.setKey(key+vc.getFid()) ;
					keyValues.setValue(value) ;
					filters.add(keyValues) ;
				}
			}
		}
		
		//内容
		List list = new ArrayList() ;
		int totalCount = 0 ;
		String pagin = "" ;
		String param = "";
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		switch (recordType) {
		case TradeRecordTypeEnum.CNY_RECHARGE:
			param = "where fuser.fid="+fuser.getFid()+" and ftype="+CapitalOperationTypeEnum.RMB_IN
			+" and date_format(fcreatetime,'%Y-%m-%d')>='"+begindate+"'"+" and date_format(fcreatetime,'%Y-%m-%d')<='"+enddate+"' order by fid desc";
			list = this.frontAccountService.findCapitalList(PaginUtil.firstResult(currentPage, maxResults),maxResults, param,true) ;
			totalCount = this.adminService.getAllCount("Fcapitaloperation", param);
			break;
			
		case TradeRecordTypeEnum.CNY_WITHDRAW:
			param = "where fuser.fid="+fuser.getFid()+" and ftype="+CapitalOperationTypeEnum.RMB_OUT
					+" and date_format(fcreatetime,'%Y-%m-%d')>='"+begindate+"'"+" and date_format(fcreatetime,'%Y-%m-%d')<='"+enddate+"' order by fid desc";
			list = this.frontAccountService.findCapitalList(PaginUtil.firstResult(currentPage, maxResults),maxResults, param,true) ;
			totalCount = this.adminService.getAllCount("Fcapitaloperation", param);

			break;
		case TradeRecordTypeEnum.BTC_RECHARGE:
			param = "where fuser.fid="+fuser.getFid()+" and fvirtualcointype.fid="+fvirtualcointype.getFid()+" and ftype ="+VirtualCapitalOperationTypeEnum.COIN_IN
					+" and date_format(fcreatetime,'%Y-%m-%d')>='"+begindate+"'"+" and date_format(fcreatetime,'%Y-%m-%d')<='"+enddate+"' order by fid desc";
			list =  this.frontVirtualCoinService.findFvirtualcaptualoperations(PaginUtil.firstResult(currentPage, maxResults),maxResults,param,true) ;
			totalCount = this.adminService.getAllCount("Fvirtualcaptualoperation", param);
			
			break;
		case TradeRecordTypeEnum.BTC_WITHDRAW:
			param = "where fuser.fid="+fuser.getFid()+" and fvirtualcointype.fid="+fvirtualcointype.getFid()+" and ftype ="+VirtualCapitalOperationTypeEnum.COIN_OUT
			+" and date_format(fcreatetime,'%Y-%m-%d')>='"+begindate+"'"+" and date_format(fcreatetime,'%Y-%m-%d')<='"+enddate+"' order by fid desc";
			list =  this.frontVirtualCoinService.findFvirtualcaptualoperations(PaginUtil.firstResult(currentPage, maxResults),maxResults,param,true) ;
			totalCount = this.adminService.getAllCount("Fvirtualcaptualoperation", param);
			
			break;
//		case TradeRecordTypeEnum.BTC_BUY:
//			param = "where fuser.fid="+fuser.getFid()+" and fentrustType="+EntrustTypeEnum.BUY+" and fvirtualcointype.fid="+fvirtualcointype.getFid()
//					+" and date_format(fcreatetime,'%Y-%m-%d')>='"+begindate+"'"+" and date_format(fcreatetime,'%Y-%m-%d')<='"+enddate+"' order by fid desc";
//			list = this.frontTradeService.findFentrustHistory(PaginUtil.firstResult(currentPage, maxResults), param, true);
//			totalCount = this.adminService.getAllCount("Fentrust", param);
//			
//			break;
//		case TradeRecordTypeEnum.BTC_SELL:
//			param = "where fuser.fid="+fuser.getFid()+" and fentrustType="+EntrustTypeEnum.SELL+" and fvirtualcointype.fid="+fvirtualcointype.getFid()
//			+" and date_format(fcreatetime,'%Y-%m-%d')>='"+begindate+"'"+" and date_format(fcreatetime,'%Y-%m-%d')<='"+enddate+"' order by fid desc";
//			list = this.frontTradeService.findFentrustHistory(PaginUtil.firstResult(currentPage, maxResults), param, true);
//			totalCount = this.adminService.getAllCount("Fentrust", param);
//			
//			break;
		}
		
		String url = "/account/record.html?recordType="+recordType+"&symbol="+symbol+"&datetype="+datetype+"&begindate="+begindate+"&enddate="+enddate+"&";
		pagin = PaginUtil.generatePagin(totalCount/maxResults+( (totalCount%maxResults)==0?0:1), currentPage,url) ;
		modelAndView.addObject("datetype", datetype) ;
		modelAndView.addObject("begindate", begindate) ;
		modelAndView.addObject("enddate", enddate) ;
		modelAndView.addObject("list", list) ;
		modelAndView.addObject("pagin",pagin) ;
		modelAndView.addObject("recordType",recordType ) ;
		modelAndView.addObject("symbol" ,symbol) ;
		modelAndView.addObject("filters", filters) ;
		modelAndView.addObject("fvirtualcointype", fvirtualcointype) ;
		if(recordType==1 || recordType==2){
			modelAndView.addObject("select", TradeRecordTypeEnum.getEnumString(recordType)) ;
		}else{
			modelAndView.addObject("select", fvirtualcointype.getfShortName()+TradeRecordTypeEnum.getEnumString(recordType)) ;
		}
		modelAndView.setViewName("front/account/account_record") ;
		return modelAndView ;
	}
	
	
	@RequestMapping(value="/account/refTenbody")
	public ModelAndView refTenbody(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage,
			@RequestParam(required=false,defaultValue="0")int type
			) throws Exception{
		if(type !=0 &&  type !=3 && type !=4){
			type =0;
		}
		
		ModelAndView modelAndView = new ModelAndView() ;
		
		Fuser fuser = this.GetSession(request) ;
		StringBuffer filter = new StringBuffer();
		filter.append("where fuser.fid="+fuser.getFid()+" \n");
		filter.append("and ftype ="+CapitalOperationTypeEnum.RMB_IN+"\n");
		/*if(type !=0){
			filter.append("and fremittanceType='"+RemittanceTypeEnum.getEnumString(type)+"' \n");
		}else{
			filter.append("and systembankinfo is not null \n");
		}*/
		filter.append(" and systembankinfo is not null \n");
		filter.append(" order by fid desc \n");
		List<Fcapitaloperation> list = this.capitaloperationService.list((currentPage-1)*maxResults, maxResults, filter.toString(), true);
		
		int totalCount = this.adminService.getAllCount("Fcapitaloperation", filter.toString());
		String url = "/account/rechargeCny.html?type="+type+"&";
		String pagin = PaginUtil.generatePagin(totalCount/maxResults+( (totalCount%maxResults)==0?0:1), currentPage,url) ;
		
		modelAndView.addObject("list", list) ;
		modelAndView.addObject("pagin", pagin) ;
		modelAndView.addObject("type", type);
		modelAndView.addObject("currentPage_page", currentPage) ;
		modelAndView.setViewName("front/account/reftenbody") ;
		return modelAndView ;
	}
		
		@RequestMapping("financial/assetsrecord")
		public ModelAndView assetsrecord(
				HttpServletRequest request,
				@RequestParam(required=false,defaultValue="1")int currentPage
				)  throws Exception {
			ModelAndView modelAndView = new ModelAndView() ;
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
			
			List<Fvirtualcointype> fvirtualcointypes = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE, 1);
			modelAndView.addObject("fvirtualcointypes", fvirtualcointypes) ;
			
			int maxResults = Constant.RecordPerPage ;
			int firstResult = PaginUtil.firstResult(currentPage, maxResults) ;
			String filter =  " where fuser.fid="+fuser.getFid()+" and status=1 order by fid desc " ;
			List<Fasset> list= this.utilsService.list(firstResult, maxResults,filter, true, Fasset.class) ;
			int total = this.utilsService.count(filter, Fasset.class) ;
			String pagin = PaginUtil.generatePagin(PaginUtil.firstResult(currentPage, maxResults), currentPage, "/financial/assetsrecord.html?") ;
			modelAndView.addObject("list",list) ;
			modelAndView.addObject("pagin",pagin);
			
			//处理json
			for (Fasset fasset : list) {
				fasset.parseJson(fvirtualcointypes);
			}
			
			modelAndView.setViewName("front/financial/assetsrecord") ;
			return modelAndView ;
		}



	//通过邮件链接确认提现虚拟币
	@RequestMapping(value = "/account/email/withdrawvirtual")
	public ModelAndView emailWithdraw(HttpServletRequest request,
								 @RequestParam(required=true)int uid,
								 @RequestParam(required=true)String uuid) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/front/account/with_emailmsg");
		LOG.i(this, "通过邮件进行虚拟币提现，链接确认入参：uid = " + uid + "&&uuid=" + uuid);
		String ip = getIpAddr(request);
		//根据uuid、uid查看提币流水
		String filter = " where fuser.fid=" + uid + " and mailLinkUUID = '"+uuid+"' " ;
		List<Fvirtualcaptualoperation> list = this.frontVirtualCoinService.findFvirtualcaptualoperations(0, 1, filter, false);
		if (list == null || list.size() == 0) {
			modelAndView.addObject(CODE, -1);
			modelAndView.addObject(MSG, "链接已失效");
			return modelAndView;
		}

		//根据uid查询用户信息
		Fuser fuser = this.frontUserService.findByIdCase(uid);
		if (fuser == null) {
			modelAndView.addObject(CODE, -1);
			modelAndView.addObject(MSG, "非法操作");
			return modelAndView;
		}

		int userId = fuser.getFid();

		//对查询出来的数据做二次校验，防止非法操作，逆向修改
		//------------------------------------校验开始------------------------------------
		Fvirtualcaptualoperation fvirtualcaptualoperation = list.get(0);

		//状态判断，防止二次点击
		if(fvirtualcaptualoperation.getFstatus() != VirtualCapitalOperationOutStatusEnum.WaitMailVer) {
			modelAndView.addObject(CODE, -1);
			modelAndView.addObject(MSG, "非法操作");
			return modelAndView;
		}

		int symbol = fvirtualcaptualoperation.getFvirtualcointype().getFid();
		double withdrawAmount = Utils.add(fvirtualcaptualoperation.getFamount(), fvirtualcaptualoperation.getFfees());  //总提币金额=金额+手续费
		String withdraw_address = fvirtualcaptualoperation.getWithdraw_virtual_address();

		//查询虚拟币信息、kyc等级对应的系统配置提币信息、系统自动提币信息
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol);
		LOG.i(this, " withdrawBtcSubmit 根据symbol:{" + symbol + "}查询虚拟币信息结束");
		if(fvirtualcointype==null
				|| !fvirtualcointype.isFIsWithDraw()
				|| fvirtualcointype.getFtype()==CoinTypeEnum.FB_CNY_VALUE
				|| fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal){
			LOG.i(this,  " withdrawBtcSubmit 查询虚拟币信息、kyc等级对应的系统配置提币信息、系统自动提币信息 为空");
			modelAndView.addObject(CODE, -1);
			modelAndView.addObject(MSG, "币种信息为空");
			return modelAndView;
		}

		//查询用户钱包信息
		Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid()) ;
		if(fvirtualwallet == null) {
			modelAndView.addObject(CODE, -1);
			modelAndView.addObject(MSG, "钱包信息为空");
			return modelAndView;
		}
		FvirtualaddressWithdraw fvirtualaddressWithdraw = this.frontVirtualCoinService.findWithdrawAddress(userId, withdraw_address);
		if(fvirtualaddressWithdraw == null
				|| fvirtualaddressWithdraw.getFuser().getFid() != fuser.getFid()
				|| fvirtualaddressWithdraw.getFvirtualcointype().getFid() != symbol){
			modelAndView.addObject(CODE, -1);
			modelAndView.addObject(MSG, "提现地址非法");
			return modelAndView;
		}

		if(!fuser.isFisTelephoneBind() && !fuser.getFgoogleBind()){
			modelAndView.addObject(CODE, -1);
			modelAndView.addObject(MSG, "请先绑定谷歌验证或者手机号码");
			return modelAndView;
		}

		if(ValidateKycLevelEnum.NO_COMMIT.getKey() == fuser.getKyclevel()) {
			modelAndView.addObject(CODE, -1) ;
			modelAndView.addObject(MSG, "还未进行KYC认证");
			return modelAndView;
		}

		int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE ) ;
		int mobil_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;

		if(fuser.getFtradePassword()==null){
			modelAndView.addObject("code", -1) ;
			modelAndView.addObject("msg", "请先设置交易密码") ;
			return modelAndView;
		}else{
			int trade_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
			if(trade_limit<=0){
				modelAndView.addObject("code", -1) ;
				modelAndView.addObject("msg", "此ip操作频繁，请2小时后再试!") ;
				return modelAndView;
			}
		}

		double max_double = fvirtualcointype.getFmaxqty();
		double min_double = fvirtualcointype.getFminqty();
		if (withdrawAmount < min_double) {
			modelAndView.addObject("code", -1) ;
			modelAndView.addObject("msg", "最小提现数量为："+min_double) ;
			return modelAndView;
		}

		if(withdrawAmount>max_double){
			modelAndView.addObject("code", -1) ;
			modelAndView.addObject("msg", "最大提现数量为："+max_double) ;
			return modelAndView;
		}


		double amount = Utils.getDouble(Utils.add(fvirtualcaptualoperation.getFamount(),fvirtualcaptualoperation.getFfees()), 4);
		double frozenRmb = Utils.getDouble(fvirtualwallet.getFfrozen(), 4);
		//余额不足  (申请时，从钱包币种总额判断，确认是从冻结中判断没，因为最后减操作，是减冻结)
		if (frozenRmb-amount < -0.0001) {
			modelAndView.addObject("code", -1) ;
			modelAndView.addObject("msg", "冻结数量小于提现数量") ;
			return modelAndView;
		}


		String filter2 = "where fadderess='"+withdraw_address+"'";
		int cc = this.adminService.getAllCount("Fvirtualaddress", filter2);
		if(cc >0){
			modelAndView.addObject("code", -1) ;
			modelAndView.addObject("msg", "站内会员不允许互转") ;
			return modelAndView;
		}

		String sql = "where flevel="+fuser.getFscore().getFlevel()+" and fvirtualcointype.fid="+symbol;
		List<Fwithdrawfees> alls = this.withdrawFeesService.list(0, 0, sql, false);
		if(alls == null || alls.size() ==0){
			modelAndView.addObject("code", -1) ;
			modelAndView.addObject("msg", "手续费异常") ;
			return modelAndView;
		}
		double ffees = alls.get(0).getFfee();
		if(ffees ==0 && alls.get(0).getFlevel() != 5){
			modelAndView.addObject("code", -1) ;
			modelAndView.addObject("msg", "手续费有误") ;
			return modelAndView;
		}

		if(withdrawAmount <= ffees){
			modelAndView.addObject("code", -1) ;
			modelAndView.addObject("msg", "提现数量小于等于手续费数量") ;
			return modelAndView;
		}

		if(fuser.getFgoogleBind()){
			if(google_limit<=0){
				modelAndView.addObject("code", -1) ;
				modelAndView.addObject("msg", "此ip操作频繁，请2小时后再试!") ;
				return modelAndView;
			}
		}

		//未进行邮箱验证，则进入该接口是非法的
		if(!fuser.getFisMailValidate()) {
			modelAndView.addObject(CODE, -1);
			modelAndView.addObject(MSG, "非法操作，未绑定邮箱");
			return modelAndView;
		}

		int kyc_level = 0;
		if(ValidateKycLevelEnum.validateKYC2(fuser.getKyclevel())) {
			kyc_level = 2;
		}else if(ValidateKycLevelEnum.validateKYC1(fuser.getKyclevel()) ) {
			kyc_level = 1;
		}else{
			modelAndView.addObject(CODE, -1) ;
			modelAndView.addObject(MSG, "还未进行KYC认证");
			return modelAndView;
		}

		//查询当日已提币额度  注：含本次，因为申请完成后，数据已插入数据库
		Map<String, String> userNum_day_map = this.frontVirtualCoinService.queryUserWithNum (fuser.getFid(), DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay), symbol);
		if (userNum_day_map == null || userNum_day_map.get("amount") == null || userNum_day_map.get("num") == null) {
			LOG.i(this, "查询用户当日的提币次数、总额度信息，返回结果为空");
			modelAndView.addObject(CODE, -1) ;
			modelAndView.addObject(MSG, "系统异常");
			return modelAndView;
		}

		BigDecimal day_quota = new BigDecimal(userNum_day_map.get("amount"));
		int day_num = Integer.valueOf(userNum_day_map.get("num"));

		modelAndView.addObject("kyc_level", kyc_level);

		//根据kyc等级，比对kyc权限配置
		int compare_kyc_day_count = 0;
		double compare_kyc_single_limit = 0;
		double compare_kyc_day_limit = 0;
		double compare_kyc_month_limit = 0;
		if(kyc_level == 1) {
			compare_kyc_day_count = fvirtualcointype.getKyc1_day_count();
			compare_kyc_single_limit = fvirtualcointype.getKyc1_single_limit();
			compare_kyc_day_limit = fvirtualcointype.getKyc1_day_limit();
			compare_kyc_month_limit = fvirtualcointype.getKyc1_month_limit();
		}else{
			compare_kyc_day_count = fvirtualcointype.getKyc2_day_count();
			compare_kyc_single_limit = fvirtualcointype.getKyc2_single_limit();
			compare_kyc_day_limit = fvirtualcointype.getKyc2_day_limit();
			compare_kyc_month_limit = fvirtualcointype.getKyc2_month_limit();
		}

		//判断当日提币是否超过次数  注：大于比较
		if (day_num > compare_kyc_day_count) {
			modelAndView.addObject(CODE, -2);
			modelAndView.addObject(MSG, "您当日提现次数已用完");
			return modelAndView;
		}

		//判断单笔额度是否超限
		if (withdrawAmount > compare_kyc_single_limit) {
			modelAndView.addObject(CODE, -2);
			modelAndView.addObject(MSG, "单笔提现额度超出限制");
			return modelAndView;
		}

		//判断当日额度是否超限
		if(day_quota.doubleValue() > compare_kyc_day_limit   ) {
			modelAndView.addObject(CODE, -2);
			modelAndView.addObject(MSG, "您当前提现额度已用完");
			return modelAndView;
		}


		//查询当月已提币额度    注：含本次，因为申请完成后，数据已插入数据库
		Map<String, String> userNum_month_map = this.frontVirtualCoinService.queryUserWithNum (fuser.getFid(), DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay), symbol);
		if (userNum_month_map == null || userNum_month_map.get("amount") == null) {
			LOG.i(this, "查询用户当月的提币次数、总额度信息，返回结果为空");
			modelAndView.addObject(CODE, -1) ;
			modelAndView.addObject(MSG, "系统异常");
			return modelAndView;
		}

		BigDecimal month_quota = new BigDecimal(userNum_month_map.get("amount"));

		//判断当月额度是否超限
		if(month_quota.doubleValue() > compare_kyc_month_limit   ) {
			modelAndView.addObject(CODE, -2);
			modelAndView.addObject(MSG, "您当月提现额度已用完");
			return modelAndView;
		}

		//本邮箱是确认自动提币接口，所以还需继续判断是否符合自动提币的额度限制
		//是新提币地址
		boolean is_new_addr = this.frontVirtualCoinService.isNewWithAddr(fuser.getFid(), withdraw_address);
		//是大于自动提现单笔额度
		boolean up_auto_single_limit = false;
		if(withdrawAmount > fvirtualcointype.getAuto_single_limit().doubleValue()) {
			up_auto_single_limit = true;
		}

		//查询用户当天自动提币的额度、次数
		Map<String, String> auto_with_num_map = this.frontVirtualCoinService.queryUserAutoWithNum(fuser.getFid(), DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay), symbol);

		BigDecimal auto_day_quota_ed = BigDecimal.ZERO;
		int auto_day_count_ed = 0;
		if(auto_with_num_map != null) {
			if(auto_with_num_map.get("amount") != null) {
				auto_day_quota_ed = new BigDecimal(auto_with_num_map.get("amount"));
			}
			if(auto_with_num_map.get("num") != null) {
				auto_day_count_ed = Integer.valueOf(auto_with_num_map.get("num").toLowerCase());
			}
		}
		//是大于自动提现日额度
		boolean up_auto_day_limit = false;
		if(auto_day_quota_ed.doubleValue() > fvirtualcointype.getAuto_day_limit().doubleValue()) {
			up_auto_day_limit = true;
		}

		//当日自动提现次数是否超限
		boolean up_auto_day_count = false;
		if(auto_day_count_ed >= fvirtualcointype.getAuto_day_count().intValue()) {
			up_auto_day_count = true;
		}

		//4大自动提币条件，都符合才进行系统自动提币，否则，人工
		if(is_new_addr || up_auto_single_limit || up_auto_day_limit || up_auto_day_count) {
			modelAndView.addObject(CODE, -1);
			modelAndView.addObject(MSG, "非法操作，不满足系统自动提币要求，进入了自动提币业务");
			return modelAndView;
		}

		//------------------------------------校验结束------------------------------------
		//在service中进行自动提币操作
		LOG.i(this, "自动提币操作开始");
		//修改改币订单状态为2 锁定正在处理
		this.virtualCapitaloperationService.updateVirtualCapitalOperationStatus(VirtualCapitalOperationOutStatusEnum.OperationLock, fvirtualcaptualoperation.getFid(), true);


		String password = fvirtualcointype.getAuto_password();
		password = new String(Base64.decode(password));  //解密
		try {
			modelAndView = withdraw(modelAndView, fvirtualwallet, password, fvirtualcaptualoperation, fvirtualcointype, userId);
			String statusCode = modelAndView.getModel().get("statusCode").toString();
			String message = modelAndView.getModel().get("message").toString();
			if(statusCode.equals("300")) {
				modelAndView.addObject(CODE, -1);
				modelAndView.addObject(MSG, message);
				return modelAndView;
			}

			modelAndView.addObject(CODE, 0);
			modelAndView.addObject(MSG, "操作成功");
		}catch (Exception e) {
			e.printStackTrace();
			LOG.e(this, "系统自动提币异常", e);
			modelAndView.addObject(CODE, -1);
			modelAndView.addObject(MSG, "操作失败");
		}
		return modelAndView;
	}
}
