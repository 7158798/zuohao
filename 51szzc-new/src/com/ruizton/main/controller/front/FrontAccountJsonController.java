package com.ruizton.main.controller.front;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.ruizton.main.Enum.*;
import com.ruizton.main.aspect.SysLog;
import com.ruizton.main.log.LOG;
import com.ruizton.main.service.admin.BankinfoWithdrawService;
import com.ruizton.main.service.admin.CapitaloperationService;
import com.ruizton.util.*;
import com.ruizton.util.alipayUtil.AlipaySubmit;
import net.chrone.api.ChroneApi;
import net.chrone.model.Order;
import net.chrone.util.SdkUtil;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.comm.ValidateMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.FbankinfoWithdraw;
import com.ruizton.main.model.Fcapitaloperation;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.FvirtualaddressWithdraw;
import com.ruizton.main.model.Fvirtualcaptualoperation;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.model.Fwithdrawfees;
import com.ruizton.main.model.Systembankinfo;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.WithdrawFeesService;
import com.ruizton.main.service.front.FrontAccountService;
import com.ruizton.main.service.front.FrontBankInfoService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontValidateService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import sun.nio.cs.ext.IBM1097;

@Controller
public class FrontAccountJsonController extends BaseController{


	private static final Logger LOGGER = LoggerFactory.getLogger(FrontAccountJsonController.class);

	private static final String CLASS_NAME = FrontAccountJsonController.class.getSimpleName();

	//格式化，保留2未小数
	private DecimalFormat decimalFormat2 = new DecimalFormat("####0.00");


	//会员用户自己充值人民币
	@RequestMapping(value="/account/alipayManual",produces = {JsonEncode})
	@ResponseBody
	public String alipayManual(
			HttpServletRequest request,
			@RequestParam(required=true) double money,
			@RequestParam(required=true) int type,
			@RequestParam(required=true) int sbank
			) throws Exception{
		LOGGER.info(CLASS_NAME + " alipayManual,会员用户自己充值人民币,入参money:{},type:{},sbank:{}", money, type, sbank);
		JSONObject jsonObject = new JSONObject() ;
		money = Utils.getDoubleUp(money, 2);
		double minRecharge = Double.parseDouble(this.map.get("minrechargecny").toString()) ;
		LOGGER.info(CLASS_NAME + " alipayManual,配置最小充值金额为minRecharge:{}", minRecharge);
		if(money < minRecharge){
			//非法
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", i18nMsg("s_min_regc_amt") + minRecharge);
			return jsonObject.toString();
		}
		
		if(type != 0){
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", i18nMsg("s_illegal_operation"));
			return jsonObject.toString();
		}

		Fuser fuser = this.frontUserService.findById(this.GetSession(request).getFid());

		if(fuser == null) {
			jsonObject.accumulate("code", -1);
//			jsonObject.accumulate("msg", "用户信息错误，请重新登录");
			jsonObject.accumulate(MSG, i18nMsg("s_please_login_again"));
			return jsonObject.toString();
		}

		if(!ValidateKycLevelEnum.validateKYC1(fuser.getKyclevel())){
			jsonObject.accumulate("resultCode", -1) ;
			jsonObject.accumulate(MSG, i18nMsg("no_kyc1_validate"));
			return jsonObject.toString() ;
		}
		
		List<Systembankinfo> systembankinfos = this.frontBankInfoService.findAllSystemBankInfo(type != 0 ? 1 : 0) ;
		Systembankinfo systembankinfo;
		if(systembankinfos.size() > 0){
			 systembankinfo = systembankinfos.get(0);
		}else{
			LOGGER.info(CLASS_NAME + " alipayManual,查询银行账户信息为空或账户停用");
			//银行账号停用
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", i18nMsg("s_bank_card_not_exist"));
			return jsonObject.toString();
		}

		FbankinfoWithdraw fbankinfoWithdraw = this.bankinfoWithdrawService.findById(sbank);
		if(fbankinfoWithdraw==null || fbankinfoWithdraw.getFstatus()==SystemBankInfoEnum.ABNORMAL ){
			//银行账号停用
			jsonObject.accumulate("code", -1);
//			jsonObject.accumulate("msg", "银行帐户不存在");
			jsonObject.accumulate("msg", i18nMsg("s_bank_card_not_exist"));
			return jsonObject.toString();
		}
		
		Fcapitaloperation fcapitaloperation = new Fcapitaloperation() ;
		fcapitaloperation.setFamount(money) ;
		fcapitaloperation.setSystembankinfo(systembankinfo) ;
		fcapitaloperation.setFcreateTime(Utils.getTimestamp()) ;
		fcapitaloperation.setFtype(CapitalOperationTypeEnum.RMB_IN) ;
		fcapitaloperation.setFuser(this.GetSession(request)) ;
		fcapitaloperation.setFstatus(CapitalOperationInStatus.WaitForComing) ;
		fcapitaloperation.setFremittanceType(systembankinfo.getFbankName());
		//汇款人银行信息
		fcapitaloperation.setfBank(fbankinfoWithdraw.getFname());
		fcapitaloperation.setfAccount(fbankinfoWithdraw.getFbankNumber());
		fcapitaloperation.setfPayee(this.GetSession(request).getFrealName());
		fcapitaloperation.setFaddress(fbankinfoWithdraw.getFaddress());
		//LOGGER.info(CLASS_NAME + " alipayManual,保存到数据库的人民币充值流水fcapitaloperation:{}", new Gson().toJson(fcapitaloperation));
		this.frontAccountService.addFcapitaloperation(fcapitaloperation) ;
		LOGGER.info(CLASS_NAME + " alipayManual,保存完毕，开始拼装返回对象");
		jsonObject.accumulate("code", 0);
		jsonObject.accumulate("money", String.valueOf(fcapitaloperation.getFamount())) ;
		jsonObject.accumulate("tradeId", fcapitaloperation.getFid()) ;
		jsonObject.accumulate("fbankName", systembankinfo.getFbankName()) ;
		jsonObject.accumulate("fownerName", systembankinfo.getFownerName()) ;
		jsonObject.accumulate("fbankAddress", systembankinfo.getFbankAddress()) ;
		jsonObject.accumulate("fbankNumber", systembankinfo.getFbankNumber()) ;
		LOGGER.info(CLASS_NAME + " alipayManual,返回对象jsonObject:{}", jsonObject.toString());
		return jsonObject.toString() ;  
	}
	
	
	@ResponseBody
	@RequestMapping(value="/account/rechargeCnySubmit",produces={JsonEncode})
	public String rechargeCnySubmit(
			HttpServletRequest request,
			@RequestParam(required=false) String bank,
			@RequestParam(required=false) String account,
			@RequestParam(required=false) String payee,
			@RequestParam(required=false) String phone,
			@RequestParam(required=false) int type,
			@RequestParam(required=true) int desc//记录的id
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(desc) ;
		if(fcapitaloperation==null ||fcapitaloperation.getFuser().getFid() !=GetSession(request).getFid() ){
			jsonObject.accumulate("code", -1);
//			jsonObject.accumulate("msg", "非法操作");
			jsonObject.accumulate("msg", i18nMsg("s_illegal_operation"));
			return jsonObject.toString();
		}
		
		if(fcapitaloperation.getFstatus() != CapitalOperationInStatus.NoGiven
				|| fcapitaloperation.getFtype() != CapitalOperationTypeEnum.RMB_IN){
			jsonObject.accumulate("code", -1);
//			jsonObject.accumulate("msg", "网络异常");
			jsonObject.accumulate(MSG, i18nMsg("s_network_anomaly"));
			return jsonObject.toString();
		}
		
//		fcapitaloperation.setfBank(bank) ;
//		fcapitaloperation.setfAccount(account) ;
//		fcapitaloperation.setfPayee(payee) ;
//		fcapitaloperation.setfPhone(phone) ;
		fcapitaloperation.setFstatus(CapitalOperationInStatus.WaitForComing) ;
		fcapitaloperation.setFischarge(false);
		fcapitaloperation.setfLastUpdateTime(Utils.getTimestamp());
		try {
			this.frontAccountService.updateCapitalOperation(fcapitaloperation) ;
			jsonObject.accumulate("code", 0);
//			jsonObject.accumulate("msg", "操作成功");
			jsonObject.accumulate(MSG, i18nMsg("s_operation_success"));
		} catch (Exception e) {
			jsonObject.accumulate("code", -1);
//			jsonObject.accumulate("msg", "网络异常");
			jsonObject.accumulate(MSG, i18nMsg("s_network_anomaly"));
		}
		
		return jsonObject.toString();
	}
	

	@ResponseBody
	@RequestMapping(value="/account/alipayTransfer",produces={JsonEncode})
	public String alipayTransfer(
			HttpServletRequest request,
			@RequestParam(required=true) double money,
			@RequestParam(required=true) String accounts,
			@RequestParam(required=false) String imageCode,
			@RequestParam(required=true) int type
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		accounts= HtmlUtils.htmlEscape(accounts.trim());
		money = Utils.getDouble(money, 2) ;
		if(type !=3 && type !=4){
			jsonObject.accumulate("code", -1);
//			jsonObject.accumulate("msg", "非法操作");
			jsonObject.accumulate(MSG, i18nMsg("s_illegal_operation"));
			return jsonObject.toString();
		}
		if(accounts.length() >100){
			jsonObject.accumulate("code", -1);
//			jsonObject.accumulate("msg", "账号有误");
			jsonObject.accumulate(MSG, i18nMsg("s_bad_account_number"));
			return jsonObject.toString();
		}

		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
		if(fuser == null) {
			jsonObject.accumulate("code", -1);
//			jsonObject.accumulate("msg", "用户信息错误，请重新登录");
			jsonObject.accumulate(MSG, i18nMsg("s_please_login_again"));
			return jsonObject.toString();
		}

		if(!ValidateKycLevelEnum.validateKYC1(fuser.getKyclevel())){
			jsonObject.accumulate("resultCode", -1) ;
			jsonObject.accumulate(MSG, i18nMsg("no_kyc1_validate"));
			return jsonObject.toString() ;
		}

		//支付宝充值
		if(type == RemittanceTypeEnum.Type3) {
			int alipayrgcnum = Integer.parseInt(this.constantMap.get("alipayrgcnum").toString().trim());
			int user_alipay_num = this.capitaloperationService.queryCountByRemittanceType(RemittanceTypeEnum.getEnumString(type), fuser.getFid());
			if (user_alipay_num >= alipayrgcnum) {
				jsonObject.accumulate("code", -1);
//				jsonObject.accumulate("msg", "充值次数超限，支付宝充值次数为" + alipayrgcnum);
				jsonObject.accumulate(MSG, i18nMsg("s_alipay_regc_limit") + alipayrgcnum);
				return jsonObject.toString();
			}

			double minalipayrgc = Double.parseDouble(this.constantMap.get("minalipayrgc").toString().trim());
			double maxalipayrgc = Double.parseDouble(this.constantMap.get("maxalipayrgc").toString().trim());
			if(money < minalipayrgc) {
				jsonObject.accumulate("code", -1);
//				jsonObject.accumulate("msg", "支付宝最小充值金额为￥"+minalipayrgc);
				jsonObject.accumulate(MSG, i18nMsg("s_alipay_min_regc") + minalipayrgc);
				return jsonObject.toString();
			}

			if(money > maxalipayrgc) {
				jsonObject.accumulate("code", -1);
//				jsonObject.accumulate("msg", "支付宝最大充值金额为￥"+maxalipayrgc);
				jsonObject.accumulate(MSG, i18nMsg("s_alipay_max_regc") + maxalipayrgc);
				return jsonObject.toString();
			}

		}else{
			double minRecharge = Double.parseDouble(this.map.get("minrechargecny").toString()) ;
			if(money < minRecharge){
				//非法
				jsonObject.accumulate("code", -1);
//				jsonObject.accumulate("msg", "最小充值金额为￥"+minRecharge);
				jsonObject.accumulate(MSG, i18nMsg("s_regc_min_amt") + minRecharge);
				return jsonObject.toString();
			}
		}



		Double hasRechargeAmount = this.capitaloperationService.getTotalAmountForUser(GetSession(request));
		double maxRecharge = Double.parseDouble(this.map.get("maxrechargecny").toString()) ;
		if(money + hasRechargeAmount> maxRecharge){
			//非法
			jsonObject.accumulate("code", -1);
//			jsonObject.accumulate("msg", "今日最大充值金额为￥"+maxRecharge);
			jsonObject.accumulate(MSG, i18nMsg("s_today_max_regc_amt"));
			return jsonObject.toString();
		}
		
		/*if(vcodeValidate(request, imageCode) == false ){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "请输入正确的图片验证码");
			return jsonObject.toString() ;
		}*/
		
		double feeRate = Double.parseDouble(SdkUtil.getStringValue("wechatFee")) ;
		if(type==3){
			feeRate = Double.parseDouble(SdkUtil.getStringValue("alipayFee")) ;
		}

		List<Systembankinfo> systembankinfos = this.frontBankInfoService.findAllSystemBankInfo(type != 0 ? 1 : 0) ;
		Systembankinfo systembankinfo;
		if(systembankinfos.size() > 0){
			systembankinfo = systembankinfos.get(0);
		}else{
			LOGGER.info(CLASS_NAME + " alipayTransfer,查询支付宝账户信息为空或账户停用");
			//银行账号停用
			jsonObject.accumulate("code", -1);
//			jsonObject.accumulate("msg", "系统支付宝帐户不存在");
			jsonObject.accumulate(MSG, i18nMsg("s_sys_alipay_not_exist"));
			return jsonObject.toString();
		}

		Fcapitaloperation fcapitaloperation = new Fcapitaloperation();
		fcapitaloperation.setSystembankinfo(systembankinfo) ;
		fcapitaloperation.setFuser(fuser);
		fcapitaloperation.setFamount(money);
		fcapitaloperation.setFremittanceType(RemittanceTypeEnum.getEnumString(type));
		fcapitaloperation.setfBank(RemittanceTypeEnum.getEnumString(type)) ;
		fcapitaloperation.setfAccount(accounts) ;  //存储的就是用户充值的支付宝帐号
		fcapitaloperation.setfPayee(null) ;
		fcapitaloperation.setfPhone(null) ;
		fcapitaloperation.setFstatus(CapitalOperationInStatus.WaitForComing) ;
		fcapitaloperation.setFcreateTime(Utils.getTimestamp());
		fcapitaloperation.setfLastUpdateTime(Utils.getTimestamp());
		fcapitaloperation.setFtype(CapitalOperationTypeEnum.RMB_IN);
		fcapitaloperation.setFfees(0);
		fcapitaloperation.setFischarge(false);
		try {
			this.frontAccountService.updateSaveCapitalOperation(fcapitaloperation) ;
		} catch (Exception e) {
			jsonObject.accumulate("code", -1);
//			jsonObject.accumulate("msg", "网络异常");
			jsonObject.accumulate(MSG, i18nMsg("s_network_anomaly"));
			return jsonObject.toString();
		}

		List<Systembankinfo> alipayList  =  frontBankInfoService.findAllSystemBankInfo(SystemBankTypeEnum.ALIPAY.getCode());
		if(alipayList == null ||  alipayList.size() == 0) {
			//系统没有支付宝帐号
			jsonObject.accumulate("code", -1);
//			jsonObject.accumulate("msg", "网络异常");
			jsonObject.accumulate(MSG, i18nMsg("s_network_anomaly"));
			return jsonObject.toString();
		}
		Systembankinfo sysAlipay = alipayList.get(0);
		//修改AlipayConfig变量值
		AlipayConfig.partner = sysAlipay.getFownerName();
		AlipayConfig.private_key = sysAlipay.getFbankAddress();
		String sys_ali_name = sysAlipay.getFbankName();
		String sys_ali_account = sysAlipay.getFbankNumber();
		jsonObject.accumulate("sys_ali_name", sys_ali_name);
		jsonObject.accumulate("sys_ali_account", sys_ali_account);
		//生成二维码  支付宝直接的方式
		try {
			//把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", AlipayConfig.service);
			sParaTemp.put("partner", sysAlipay.getFownerName());
			sParaTemp.put("seller_id", sysAlipay.getFownerName());
			sParaTemp.put("_input_charset", AlipayConfig.input_charset);
			sParaTemp.put("payment_type", AlipayConfig.payment_type);
			sParaTemp.put("notify_url", AlipayConfig.notify_url);
			sParaTemp.put("return_url", AlipayConfig.return_url);
			sParaTemp.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
			sParaTemp.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
			sParaTemp.put("out_trade_no", fcapitaloperation.getFid()+"");
			sParaTemp.put("subject", i18nMsg("s_rmb_regc_company"));
			sParaTemp.put("total_fee", decimalFormat2.format(money));
			sParaTemp.put("body", constantMap.getString(ConstantKeys.WEB_NAME));
			sParaTemp.put("enable_paymethod", AlipayConfig.enable_paymethod);
//		sParaTemp.put("disable_paymethod", AlipayConfig.disable_paymethod);
			sParaTemp.put("it_b_pay", AlipayConfig.it_b_pay);
			sParaTemp.put("qr_pay_mode", AlipayConfig.qr_pay_mode);
			sParaTemp.put("qrcode_width", AlipayConfig.qrcode_width);

			String aliay_req_url = AlipaySubmit.buildGetRequestUrl(sParaTemp);
			jsonObject.accumulate("code", 0);
			jsonObject.accumulate("aliay_req_url", aliay_req_url);
			jsonObject.accumulate("refreshid", fcapitaloperation.getFid());
			return jsonObject.toString();
		}catch (Exception e) {
			LOG.e(this, "支付宝充值异常", e);
		}
		//生成验证码  三方支付代理方式
		/*try {
			Order order = new Order();
			order.setSource(type==3?1:0);
			order.setAmount((int)(money*100));
			if(type==3){
				order.setAccount(SdkUtil.getStringValue("alipayAccount"));
			}else{
				order.setAccount(SdkUtil.getStringValue("wechatAccounmt"));
			}
			order.setSettleAmt((int)(money*(1-feeRate)*100));//test hank
			order.setTranTp(1);
			order.setOrderNo(fcapitaloperation.getFid()+"");
			String qrcode = ChroneApi.qrpay(order);
			System.out.println(qrcode);
				
			
			if(qrcode==null ){
				jsonObject.accumulate("code", -1);
				jsonObject.accumulate("msg", "获取二维码失败");
				return jsonObject.toString();
			}
			jsonObject.accumulate("code", 0);
			jsonObject.accumulate("refreshid", fcapitaloperation.getFid());
			jsonObject.accumulate("qrcode", qrcode);
			jsonObject.accumulate("msg", "操作成功");
			return jsonObject.toString() ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		jsonObject.accumulate("code", -1);
//		jsonObject.accumulate("msg", "网络异常");
		jsonObject.accumulate(MSG, i18nMsg("s_network_anomaly"));
		return jsonObject.toString();
		
	}
	
	@ResponseBody
	@RequestMapping(value="/account/fcapitaloperationStatus")
	public String fcapitaloperationStatus(@RequestParam(required=true)int id){
		JSONObject jsonObject = new JSONObject() ;
		Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(id) ;
		if(fcapitaloperation.getFstatus()==CapitalOperationInStatus.Come){
			jsonObject.accumulate("code", 0) ;
		}else{
			jsonObject.accumulate("code", -1) ;
		}
		return jsonObject.toString() ;
	}
	/*
	 * var param={tradePwd:tradePwd,withdrawBalance:withdrawBalance,phoneCode:phoneCode,totpCode:totpCode};
	 * 
	 * */
	@ResponseBody
	@RequestMapping("/account/withdrawCnySubmit")
	public String withdrawCnySubmit(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")String tradePwd,
			@RequestParam(required=true)double withdrawBalance,
			@RequestParam(required=false,defaultValue="0")String phoneCode,
			@RequestParam(required=false,defaultValue="0")String totpCode,
			@RequestParam(required=true)int withdrawBlank
			) throws Exception{
		LOGGER.info(CLASS_NAME + " withdrawCnySubmit 会员用户申请提现人民币，提现金额：{}", withdrawBalance);
		JSONObject jsonObject = new JSONObject() ;
		//最大提现人民币
		double max_double = Double.parseDouble(this.map.get("maxwithdrawcny").toString()) ;
		double min_double = Double.parseDouble(this.map.get("minwithdrawcny").toString()) ;
		LOGGER.info(CLASS_NAME + " withdrawCnySubmit 最大提现人民币:{},最小提现人民币:{}", max_double, min_double);
		if(withdrawBalance<min_double){
			//提现金额不能小于10
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg","提现金额不能小于"+min_double) ;
			jsonObject.accumulate(MSG, i18nMsg("s_with_amt_desc1") + min_double);
			return jsonObject.toString() ;
		}
		
		if(withdrawBalance>max_double){
			//提现金额不能大于指定值
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg","提现金额不能大于"+max_double) ;
			jsonObject.accumulate(MSG, i18nMsg("s_with_amt_desc2") + max_double);
			return jsonObject.toString() ;
		}
		
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;





		int kyc_level = 0;
		if(ValidateKycLevelEnum.validateKYC2(fuser.getKyclevel()))
		{ kyc_level = 2; }
		else if(ValidateKycLevelEnum.validateKYC1(fuser.getKyclevel()) )
		{ kyc_level = 1;
		}else{
			jsonObject.accumulate(CODE, -1) ;
		jsonObject.accumulate(MSG, "您还未完成KYC1认证,不能交易");
		return jsonObject.toString() ;
		}


		jsonObject.accumulate("kyc_level", kyc_level);
		long day_count;//每日提现总次数
		double day_limit;//每日提现额度
		double month_limit;//每月提现额度
		Fvirtualcointype fvirtualcointype= 	virtualCoinService.findByProperty("fShortName","CNY").get(0);
		//如果有kyc2权限,通过kyc2配置权限判断用户操作
      if(ValidateKycLevelEnum.validateKYC2(fuser.getKyclevel())){
   //判断单笔提现额度
      	if(fvirtualcointype.getKyc2_single_limit()<withdrawBalance){
			jsonObject.accumulate(CODE, -2) ;
			jsonObject.accumulate(MSG,"单笔提现额度超出限制" );

			return jsonObject.toString() ;
		}
    //获取每日提现总次数
		  day_count=   capitaloperationService.getDayCount(fuser.getFid(),CapitalOperationTypeEnum.RMB_OUT);

		  if(fvirtualcointype.getKyc2_day_count()<=day_count){

		  jsonObject.accumulate(CODE, -2) ;
		  jsonObject.accumulate(MSG,"你当日提现次数已用完" );
		  return jsonObject.toString() ;

	  }
    	//判断每日提现额度是否超
		  day_limit=capitaloperationService.getDayLimit(fuser.getFid(),CapitalOperationTypeEnum.RMB_OUT);

		  if(fvirtualcointype.getKyc2_day_limit()<Utils.add(day_limit,withdrawBalance)){

			  jsonObject.accumulate(CODE, -2) ;
			  jsonObject.accumulate(MSG,"你当前提现额度已用完" );
			  return jsonObject.toString() ;

		  }
		  //判断每月提现额度是否超

		  month_limit=capitaloperationService.getMonthLimit(fuser.getFid(),CapitalOperationTypeEnum.RMB_OUT);
		  if (fvirtualcointype.getKyc2_month_limit()<Utils.add(month_limit,withdrawBalance)){

			  jsonObject.accumulate(CODE, -2) ;
			  jsonObject.accumulate(MSG,"您当月提现额度已用完" );
			  return jsonObject.toString() ;
		  }
	   }else {

		  //如果有kyc1权限,通过kyc1配置权限判断用户操作
		  if (ValidateKycLevelEnum.validateKYC1(fuser.getKyclevel())) {
			  //判断单笔提现额度
			  if (fvirtualcointype.getKyc1_single_limit() < withdrawBalance) {

				  jsonObject.accumulate(CODE, -2);
				  jsonObject.accumulate(MSG, "单笔提现额度超出限制");
				  return jsonObject.toString();
			  }
			  //获取每日提现总次数
			  day_count = capitaloperationService.getDayCount(fuser.getFid(), CapitalOperationTypeEnum.RMB_OUT);

			  if (fvirtualcointype.getKyc1_day_count() <= day_count) {

				  jsonObject.accumulate(CODE, -2);
				  jsonObject.accumulate(MSG, "你当日提现次数已用完");
				  return jsonObject.toString();

			  }
			  //判断每日提现额度是否超
			  day_limit = capitaloperationService.getDayLimit(fuser.getFid(), CapitalOperationTypeEnum.RMB_OUT);

			  if (fvirtualcointype.getKyc1_day_limit() < Utils.add(day_limit,withdrawBalance)) {

				  jsonObject.accumulate(CODE, -2);
				  jsonObject.accumulate(MSG, "你当前提现额度已用完");
				  return jsonObject.toString();

			  }
			  //判断每月提现额度是否超

			  month_limit = capitaloperationService.getMonthLimit(fuser.getFid(), CapitalOperationTypeEnum.RMB_OUT);
			  if (fvirtualcointype.getKyc1_month_limit() < Utils.add(month_limit,withdrawBalance)) {
				  jsonObject.accumulate(CODE, -2);
				  jsonObject.accumulate(MSG, "您当月提现额度已用完");
				  return jsonObject.toString();
			  }
		  }

	  }



		Fvirtualwallet fwallet = this.frontUserService.findWalletByUser(fuser.getFid());
		FbankinfoWithdraw fbankinfoWithdraw = this.frontUserService.findByIdWithBankInfos(withdrawBlank);
		if(fbankinfoWithdraw == null || fbankinfoWithdraw.getFuser().getFid() != fuser.getFid()){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg","提现账号不合法") ;
			jsonObject.accumulate(MSG, i18nMsg("s_with_account_illegal"));
			return jsonObject.toString() ;
		}
		
		if(fwallet.getFtotal()<withdrawBalance){
			//资金不足
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg","您的余额不足") ;
			jsonObject.accumulate(MSG, i18nMsg("your_balance_is_insufficient"));
			return jsonObject.toString() ;
		}
		
		if(fuser.getFtradePassword()==null){
			//没有设置交易密码
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg","请先设置交易密码") ;
			jsonObject.accumulate(MSG, i18nMsg("please_set_trade_pwd"));
			return jsonObject.toString() ;
		}
		
		if(!fuser.getFgoogleBind() && !fuser.isFisTelephoneBind()){
			//没有绑定谷歌或者手机
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg","请先绑定GOOGLE验证或者绑定手机号码") ;
			jsonObject.accumulate(MSG, i18nMsg("please_bind_google_code"));
			return jsonObject.toString() ;
		}
		
		String ip = getIpAddr(request) ;
		if(fuser.getFtradePassword()!=null){
			int trade_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
			if(trade_limit<=0){
				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg","此ip操作频繁，请2小时后再试!") ;
				jsonObject.accumulate(MSG, i18nMsg("ip_disable"));
				return jsonObject.toString() ;
			}else{
				boolean flag = fuser.getFtradePassword().equals(Utils.MD5(tradePwd,fuser.getSalt())) ;
				if(!flag){
					jsonObject.accumulate("code", -1) ;
//					jsonObject.accumulate("msg","交易密码有误，您还有"+trade_limit+"次机会") ;
					jsonObject.accumulate(MSG, i18nMsg("trade_pwd_error", new Object[]{trade_limit}));
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
					return jsonObject.toString() ;
				}else if(trade_limit<new Constant().ErrorCountLimit){
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
				}
			}
		}
		
		if(fuser.getFgoogleBind()){
			int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
			if(google_limit<=0){
				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg","此ip操作频繁，请2小时后再试!") ;
				jsonObject.accumulate(MSG, i18nMsg("ip_disable"));
				return jsonObject.toString() ;
			}else{
				boolean flag = GoogleAuth.auth(Long.parseLong(totpCode.trim()), fuser.getFgoogleAuthenticator()) ;
				if(!flag){
					jsonObject.accumulate("code", -1) ;
//					jsonObject.accumulate("msg","谷歌验证码有误，您还有"+google_limit+"次机会") ;
					jsonObject.accumulate(MSG, i18nMsg("google_vercode_error", new Object[]{google_limit}));
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
					return jsonObject.toString() ;
				}else if(google_limit<new Constant().ErrorCountLimit){
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
				}
			}
		}
		
		if(fuser.isFisTelephoneBind()){
			int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
			if(tel_limit<=0){
				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg","此ip操作频繁，请2小时后再试!") ;
				jsonObject.accumulate(MSG, i18nMsg("ip_disable"));
				return jsonObject.toString() ;
			}else{
				boolean flag = validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.CNY_TIXIAN, phoneCode) ;
				if(!flag){
					jsonObject.accumulate("code", -1) ;
//					jsonObject.accumulate("msg","手机验证码有误，您还有"+tel_limit+"机会") ;
					jsonObject.accumulate(MSG, i18nMsg("phone_vercode_error", new Object[]{tel_limit}));
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
					return jsonObject.toString() ;
				}else if(tel_limit<new Constant().ErrorCountLimit){
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
				}
			}
		}
		
		boolean withdraw = false ;
		try {
			int cnyfeeLimit = Integer.valueOf(this.constantMap.get("cnyfeeLimit").toString());
			withdraw = this.frontAccountService.updateWithdrawCNY(withdrawBalance, fuser,fbankinfoWithdraw, cnyfeeLimit) ;
		} catch (Exception e) {}finally{
			this.messageValidateMap.removeMessageMap(MessageTypeEnum.getEnumString(MessageTypeEnum.CNY_TIXIAN)) ;
			this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.CNY_TIXIAN);
		}
		
		if(withdraw){
			jsonObject.accumulate("code", 0) ;
//			jsonObject.accumulate("msg", "提现成功") ;
			jsonObject.accumulate(MSG, i18nMsg("withdraw_success"));
		}else{
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "网络异常") ;
			jsonObject.accumulate(MSG, i18nMsg("s_network_anomaly"));
		}
		
		return jsonObject.toString();
	}
	


	/**
	 * 虚拟币提现
	 * @param request
	 * @param withdrawAddr
	 * @param withdrawAmount
	 * @param tradePwd
	 * @param totpCode
	 * @param phoneCode
	 * @param symbol
	 * @param level
	 * @return
	 * @throws Exception
	 * @update 2017-05-09 luwei kyc提币、系统自动提币功能修改
	 */
	@ResponseBody
	@RequestMapping(value="/account/withdrawBtcSubmit",produces={JsonEncode})
	public String withdrawBtcSubmit(
			HttpServletRequest request,
			@RequestParam(required=true)int withdrawAddr,
			@RequestParam(required=true)double withdrawAmount,
			@RequestParam(required=true)String tradePwd,
			@RequestParam(required=false,defaultValue="0")String totpCode,
			@RequestParam(required=false,defaultValue="0")String phoneCode,
			@RequestParam(required=true)int symbol,
			@RequestParam(required=true)int level
			) throws Exception{
		LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 虚拟币提现开始");
		LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 入参withdrawAddr:{},withdrawAmount:{},symbol:{}", withdrawAddr, withdrawAmount, symbol);
		JSONObject jsonObject = new JSONObject();
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
		if (fuser == null) {
			jsonObject.accumulate(CODE, -1);
			jsonObject.accumulate(MSG, "用户未登录");
			return jsonObject.toString();
		}
		LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 根据用户id查询用户信息结束");
		//查询虚拟币信息、kyc等级对应的系统配置提币信息、系统自动提币信息
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 根据symbol:{}查询虚拟币信息结束", symbol);
		if(fvirtualcointype==null 
				|| !fvirtualcointype.isFIsWithDraw() 
				|| fvirtualcointype.getFtype()==CoinTypeEnum.FB_CNY_VALUE
				|| fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal){
			LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 查询虚拟币信息、kyc等级对应的系统配置提币信息、系统自动提币信息 为空");
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "非法操作") ;
			jsonObject.accumulate(MSG, i18nMsg("s_illegal_operation"));
			return jsonObject.toString() ;
		}
		Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid()) ;
		LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 查询用户虚拟币的钱包信息结束");
		FvirtualaddressWithdraw fvirtualaddressWithdraw = this.frontVirtualCoinService.findFvirtualaddressWithdraw(withdrawAddr);
		LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 查询用户虚拟币地址结束");
		if(fvirtualaddressWithdraw == null
				|| fvirtualaddressWithdraw.getFuser().getFid() != fuser.getFid()
				|| fvirtualaddressWithdraw.getFvirtualcointype().getFid() != symbol){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "非法操作") ;
			jsonObject.accumulate(MSG, i18nMsg("s_illegal_operation"));
			return jsonObject.toString() ;
		}
		
		if(!fuser.isFisTelephoneBind() && !fuser.getFgoogleBind()){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "请先绑定谷歌验证或者手机号码") ;
			jsonObject.accumulate(MSG, i18nMsg("please_bind_google_code"));
			return jsonObject.toString() ;
		}

		/*if(!ValidateKycLevelEnum.validateKYC2(fuser.getKyclevel())){
			jsonObject.accumulate("resultCode", -1) ;
			jsonObject.accumulate(MSG, i18nMsg("no_kyc2_validate"));
			return jsonObject.toString() ;
		}*/

		int kyc_level = 0;
		if(ValidateKycLevelEnum.validateKYC2(fuser.getKyclevel())) {
			kyc_level = 2;
		}else if(ValidateKycLevelEnum.validateKYC1(fuser.getKyclevel()) ) {
			kyc_level = 1;
		}else{
			jsonObject.accumulate(CODE, -1) ;
			jsonObject.accumulate(MSG, "还未进行KYC认证");
			return jsonObject.toString() ;
		}
		
		String ip = getIpAddr(request) ;
		int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE ) ;
		int mobil_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
		
		if(fuser.getFtradePassword()==null){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "请先设置交易密码") ;
			jsonObject.accumulate(MSG, i18nMsg("please_set_trade_pwd"));
			return jsonObject.toString() ;
		}else{
			int trade_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
			if(trade_limit<=0){
				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg", "此ip操作频繁，请2小时后再试!") ;
				jsonObject.accumulate(MSG, i18nMsg("ip_disable"));
				return jsonObject.toString() ;
			}
			
			if(!fuser.getFtradePassword().equals(Utils.MD5(tradePwd,fuser.getSalt()))){
				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg", "交易密码有误，您还有"+trade_limit+"次机会") ;
				jsonObject.accumulate(MSG, i18nMsg("trade_pwd_error", new Object[]{ trade_limit }));
				this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
				return jsonObject.toString() ;
			}else if(trade_limit<new Constant().ErrorCountLimit){
				this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
			}
		}
		

		double max_double = fvirtualcointype.getFmaxqty();
		double min_double = fvirtualcointype.getFminqty();
		if(withdrawAmount<min_double){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "最小提现数量为："+min_double) ;
			jsonObject.accumulate(MSG, i18nMsg("s_min_withdraw", new Object[]{  min_double }));
			return jsonObject.toString() ;
		}
		
		if(withdrawAmount>max_double){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "最大提现数量为："+max_double) ;
			jsonObject.accumulate(MSG, i18nMsg("s_max_withdraw", new Object[]{ max_double}));
			return jsonObject.toString() ;
		}
		
		//余额不足
		if(fvirtualwallet.getFtotal()<withdrawAmount){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "您的余额不足") ;
			jsonObject.accumulate(MSG, i18nMsg("s_insufficient_balance"));
			return jsonObject.toString() ;
		}
		
		String filter = "where fadderess='"+withdrawAddr+"'";
		int cc = this.adminService.getAllCount("Fvirtualaddress", filter);
		if(cc >0){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "站内会员不允许互转") ;
			jsonObject.accumulate(MSG, i18nMsg("s_member_not_trade"));
			return jsonObject.toString() ;
		}
		
		String sql = "where flevel="+level+" and fvirtualcointype.fid="+symbol;
		List<Fwithdrawfees> alls = this.withdrawFeesService.list(0, 0, sql, false);
		if(alls == null || alls.size() ==0){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "手续费异常") ;
			jsonObject.accumulate(MSG, i18nMsg("s_fee_exception"));
		}
		double ffees = alls.get(0).getFfee();
		if(ffees ==0 && alls.get(0).getFlevel() != 5){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "手续费有误") ;
			jsonObject.accumulate(MSG, i18nMsg("s_fee_error"));
			return jsonObject.toString();
		}
		
		if(withdrawAmount <= ffees){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "提现数量小于等于手续费数量") ;
			jsonObject.accumulate(MSG, i18nMsg("withdraw_num_less_fess_num"));
			return jsonObject.toString();
		}
		
		if(fuser.getFgoogleBind()){
			if(google_limit<=0){
				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg", "此ip操作频繁，请2小时后再试!") ;
				jsonObject.accumulate(MSG, i18nMsg("ip_disable"));
				return jsonObject.toString() ;
			}
			
			boolean googleValidate = GoogleAuth.auth(Long.parseLong(totpCode.trim()), fuser.getFgoogleAuthenticator()) ;
			if(!googleValidate){
				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg", "谷歌验证码有误，您还有"+google_limit+"次机会") ;
				jsonObject.accumulate(MSG, i18nMsg("google_vercode_error", new Object[]{ google_limit }));
				this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
				return jsonObject.toString() ;
			}else if(google_limit<new Constant().ErrorCountLimit){
				this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
			}
		}

		//未进行邮箱验证，则校验手机验证码
		if(!fuser.getFisMailValidate()) {
			if(fuser.isFisTelephoneBind()){  //绑定了手机
				if(mobil_limit<=0){
					jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg", "此ip操作频繁，请2小时后再试!") ;
					jsonObject.accumulate(MSG, i18nMsg("ip_disable"));
					return jsonObject.toString() ;
				}

				boolean mobilValidate = validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.VIRTUAL_TIXIAN, phoneCode) ;
				if(!mobilValidate){
					jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg", "手机验证码有误，您还有"+mobil_limit+"次机会") ;
					jsonObject.accumulate(MSG, i18nMsg("phone_vercode_error",new Object[]{mobil_limit}));
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
					return jsonObject.toString() ;
				}else if(mobil_limit<new Constant().ErrorCountLimit){
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
				}
			}
		}

		//-----------------------------kyc判断  开始---------------------------------------

		//查询当日已提币额度
		Map<String, String> userNum_day_map = this.frontVirtualCoinService.queryUserWithNum (fuser.getFid(), DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay), symbol);
		if (userNum_day_map == null || userNum_day_map.get("amount") == null || userNum_day_map.get("num") == null) {
			LOGGER.info("查询用户当日的提币次数、总额度信息，返回结果为空");
			jsonObject.accumulate(CODE, -1) ;
			jsonObject.accumulate(MSG, "系统异常");
			return jsonObject.toString() ;
		}

		BigDecimal day_quota = new BigDecimal(userNum_day_map.get("amount"));
		int day_num = Integer.valueOf(userNum_day_map.get("num"));

		jsonObject.accumulate("kyc_level", kyc_level);

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

		//判断当日提币是否超过次数  已提币次数+当前次数
		if ((day_num+1) > compare_kyc_day_count) {
			jsonObject.accumulate(CODE, -2);
			jsonObject.accumulate(MSG, "您当日提现次数已用完");
			return jsonObject.toString();
		}

		//判断单笔额度是否超限
		if (withdrawAmount > compare_kyc_single_limit) {
			jsonObject.accumulate(CODE, -2);
			jsonObject.accumulate(MSG, "单笔提现额度超出限制");
			return jsonObject.toString();
		}

		//判断当日额度是否超限
		if(day_quota.add(new BigDecimal(withdrawAmount)).doubleValue() > compare_kyc_day_limit   ) {
			jsonObject.accumulate(CODE, -2);
			jsonObject.accumulate(MSG, "您当前提现额度已用完");
			return jsonObject.toString();
		}


		//查询当月已提币额度
		Map<String, String> userNum_month_map = this.frontVirtualCoinService.queryUserWithNum (fuser.getFid(), DateHelper.getFirstDayByMonth(), DateHelper.getLastDayByMonth(), symbol);
		if (userNum_month_map == null || userNum_month_map.get("amount") == null) {
			LOGGER.info("查询用户当月的提币次数、总额度信息，返回结果为空");
			jsonObject.accumulate(CODE, -1) ;
			jsonObject.accumulate(MSG, "系统异常");
			return jsonObject.toString() ;
		}

		BigDecimal month_quota = new BigDecimal(userNum_month_map.get("amount"));

		//判断当月额度是否超限
		if(month_quota.add(new BigDecimal(withdrawAmount)).doubleValue() > compare_kyc_month_limit   ) {
			jsonObject.accumulate(CODE, -2);
			jsonObject.accumulate(MSG, "您当月提现额度已用完");
			return jsonObject.toString();
		}

		//-----------------------------kyc判断  结束---------------------------------------

		try {
			LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 保存提现流水，修改用户钱包信息开始");
			//未绑邮件，人工审核
			if(!fuser.getFisMailValidate()) {
				this.frontVirtualCoinService.updateWithdrawBtc(fvirtualaddressWithdraw, fvirtualcointype, fvirtualwallet, withdrawAmount, ffees, fuser, false, ip);
				jsonObject.accumulate("code", 0);
				jsonObject.accumulate("msg", "操作成功");
				jsonObject.accumulate("type", "phone");
			}else{  //绑定了邮箱，做自动审核条件判断
				//是新提币地址
				boolean is_new_addr = this.frontVirtualCoinService.isNewWithAddr(fuser.getFid(), fvirtualaddressWithdraw.getFadderess());
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
				if(auto_day_quota_ed.add(new BigDecimal(withdrawAmount)).doubleValue() > fvirtualcointype.getAuto_day_limit().doubleValue()) {
					up_auto_day_limit = true;
				}

				//当日自动提现次数是否超限
				boolean up_auto_day_count = false;
				if(auto_day_count_ed >= fvirtualcointype.getAuto_day_count().intValue()) {
					up_auto_day_count = true;
				}

				//4大自动提币条件，都符合才进行系统自动提币，否则，人工
				if(is_new_addr || up_auto_single_limit || up_auto_day_limit || up_auto_day_count) {
					this.frontVirtualCoinService.updateWithdrawBtc(fvirtualaddressWithdraw, fvirtualcointype, fvirtualwallet, withdrawAmount, ffees, fuser, false, ip);
					jsonObject.accumulate("code", 0);
					jsonObject.accumulate("msg", "操作成功");
					jsonObject.accumulate("type", "phone");
				}else{
					this.frontVirtualCoinService.updateWithdrawBtc(fvirtualaddressWithdraw, fvirtualcointype, fvirtualwallet, withdrawAmount, ffees, fuser, true, ip);
					jsonObject.accumulate("code", 0);
					jsonObject.accumulate("msg", emailFormat(fuser.getFemail()));
					jsonObject.accumulate("type", "email");
				}
			}
			LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 保存提现流水，修改用户钱包信息结束");
		}catch (Exception e) {
			LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 异常exception:{}", e.getMessage());
			e.printStackTrace();
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", "网络异常");
		}finally {
			this.validateMap.removeMessageMap(fuser.getFid() + "_" + MessageTypeEnum.VIRTUAL_TIXIAN);
		}
		LOGGER.info(CLASS_NAME + " withdrawBtcSubmit 虚拟币提现结束");
		return jsonObject.toString() ;
	}

	/**
	 * 人民币取消充值
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
     */
	@ResponseBody
	@RequestMapping("/account/cancelRechargeCnySubmit")
	public String cancelRechargeCnySubmit(
			HttpServletRequest request,
			int id
			) throws Exception{
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(id) ;
		if(fcapitaloperation==null || fcapitaloperation.getFuser() == null) {
			return null ;
		}
		
		if(fcapitaloperation.getFuser().getFid() ==fuser.getFid() 
			&&fcapitaloperation.getFtype()==CapitalOperationTypeEnum.RMB_IN
			&&(fcapitaloperation.getFstatus()==CapitalOperationInStatus.NoGiven || fcapitaloperation.getFstatus()==CapitalOperationInStatus.WaitForComing)){
			fcapitaloperation.setFstatus(CapitalOperationInStatus.Invalidate) ;
			fcapitaloperation.setfLastUpdateTime(Utils.getTimestamp()) ;
			try {
				this.frontAccountService.updateCapitalOperation(fcapitaloperation) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return String.valueOf(0) ;
	}

	@ResponseBody
	@RequestMapping(value = "/account/detailRecharge", produces = {JsonEncode})
	public String detailRecharge(HttpServletRequest request,
								 int id){
		Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(id) ;

		JSONObject jsonObject = new JSONObject();
        if(fcapitaloperation == null){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "操作不存在") ;
			jsonObject.accumulate(MSG, i18nMsg("operation_not_exists"));
		}

		//收款人
		String sysBankOwnerName = fcapitaloperation.getSystembankinfo().getFownerName();
        //收款人帐号
		String sysAccount = fcapitaloperation.getSystembankinfo().getFbankNumber();
		//收款人开户行
		String sysAddress = fcapitaloperation.getSystembankinfo().getFbankName();
		//汇款人姓名
		String userName = fcapitaloperation.getFuser().getFrealName();
		//汇款银行
		String bankName = fcapitaloperation.getfBank();
		if(StringUtils.isBlank(bankName)) {
			bankName = "-";
		}

		jsonObject.accumulate("code", 0);
//		jsonObject.accumulate("msg", "操作成功");
		jsonObject.accumulate(MSG, i18nMsg("s_operation_success"));
		jsonObject.accumulate("sysBankOwnerName", sysBankOwnerName); //收款人
		jsonObject.accumulate("sysAccount", sysAccount);//收款帐号
		jsonObject.accumulate("sysAddress", sysAddress);//收款帐号开户行
		jsonObject.accumulate("amount", fcapitaloperation.getFamount());//付款金额
		jsonObject.accumulate("id", fcapitaloperation.getFid());
		jsonObject.accumulate("userName", userName);//汇款人姓名
		jsonObject.accumulate("bankName", bankName); //汇款银行
		String account = fcapitaloperation.getfAccount();
		if(StringUtils.isNotEmpty(account) && account.length() > 4){
			account = account.substring(account.length()-4,account.length());
		}
		if(StringUtils.isBlank(account))  {
			account = "-";
		}
		jsonObject.accumulate("account",account);//汇款银行尾号
		return  jsonObject.toString();
	}

	
	@ResponseBody
	@RequestMapping("/account/subRechargeCnySubmit")
	public String subRechargeCnySubmit(
			HttpServletRequest request,
			int id
			) throws Exception{
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(id) ;
		if(fcapitaloperation.getFuser().getFid() ==fuser.getFid() 
			&&fcapitaloperation.getFtype()==CapitalOperationTypeEnum.RMB_IN
			&&fcapitaloperation.getFstatus()==CapitalOperationInStatus.NoGiven){
			fcapitaloperation.setFstatus(CapitalOperationInStatus.WaitForComing) ;
			fcapitaloperation.setfLastUpdateTime(Utils.getTimestamp()) ;
			try {
				this.frontAccountService.updateCapitalOperation(fcapitaloperation) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return String.valueOf(0) ;
	}
	
	@ResponseBody
	@RequestMapping("/account/cancelWithdrawcny")
	public String cancelWithdrawcny(
			HttpServletRequest request,
			int id
			) throws Exception{
		Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(id) ;
		if(fcapitaloperation!=null
				&&fcapitaloperation.getFuser().getFid() ==GetSession(request).getFid()
				&&fcapitaloperation.getFtype()==CapitalOperationTypeEnum.RMB_OUT
				&&fcapitaloperation.getFstatus()==CapitalOperationOutStatus.WaitForOperation){
			try {
				this.frontAccountService.updateCancelWithdrawCny(fcapitaloperation, this.frontUserService.findById(GetSession(request).getFid())) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return String.valueOf(0) ;
	}
	
	@ResponseBody
	@RequestMapping("/account/cancelWithdrawBtc")
	public String cancelWithdrawBtc(
			HttpServletRequest request,
			@RequestParam(required=true)int id
			) throws Exception{
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fvirtualcaptualoperation fvirtualcaptualoperation = this.frontVirtualCoinService.findFvirtualcaptualoperationById(id) ;
		if(fvirtualcaptualoperation!=null
				&&fvirtualcaptualoperation.getFuser().getFid() ==fuser.getFid() 
				&&fvirtualcaptualoperation.getFtype()==VirtualCapitalOperationTypeEnum.COIN_OUT
				&&fvirtualcaptualoperation.getFstatus()==VirtualCapitalOperationOutStatusEnum.WaitForOperation
				){
			
			try{
				this.frontAccountService.updateCancelWithdrawBtc(fvirtualcaptualoperation, fuser) ;
			}catch(Exception e){
				e.printStackTrace() ;
			}
			
		}
		return String.valueOf(0) ;
	}


	@RequestMapping(value = "/account/buildAliFormRequest", produces = {JsonEncode})
	@ResponseBody
	public String buildAliFormRequest() {
		JSONObject jsonObject = new JSONObject();

		String fid = request.getParameter("fid");
		if(StringUtils.isBlank(fid)) {
			jsonObject.accumulate("code", -1);
//			jsonObject.accumulate("msg", "参数异常");
			jsonObject.accumulate(MSG, i18nMsg("param_execption"));
			return jsonObject.toString();
		}

		String money_str = request.getParameter("money");
		double money = 0;
		if(StringUtils.isNotBlank(money_str)) {
			money = Double.valueOf(money_str);
		}else{
			jsonObject.accumulate("code", -1);
//			jsonObject.accumulate("msg", "参数异常");
			jsonObject.accumulate(MSG, i18nMsg("param_execption"));
			return jsonObject.toString();
		}

		try {

			List<Systembankinfo> alipayList = frontBankInfoService.findAllSystemBankInfo(SystemBankTypeEnum.ALIPAY.getCode());
			if (alipayList == null || alipayList.size() == 0) {
				//系统没有支付宝帐号
				jsonObject.accumulate("code", -1);
//				jsonObject.accumulate("msg", "网络异常");
				jsonObject.accumulate(MSG, i18nMsg("s_network_anomaly"));
				return jsonObject.toString();
			}
			Systembankinfo sysAlipay = alipayList.get(0);
			//修改AlipayConfig变量值
			AlipayConfig.partner = sysAlipay.getFownerName();
			AlipayConfig.private_key = sysAlipay.getFbankAddress();
			//把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", AlipayConfig.service);
			sParaTemp.put("partner", sysAlipay.getFownerName());
			sParaTemp.put("seller_id", sysAlipay.getFownerName());
			sParaTemp.put("_input_charset", AlipayConfig.input_charset);
			sParaTemp.put("payment_type", AlipayConfig.payment_type);
			sParaTemp.put("notify_url", AlipayConfig.notify_url);
			sParaTemp.put("return_url", AlipayConfig.return_url);
			sParaTemp.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
			sParaTemp.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
			sParaTemp.put("out_trade_no", fid);
			sParaTemp.put("subject", i18nMsg("s_rmb_regc_company"));
			sParaTemp.put("total_fee", decimalFormat2.format(money));
			sParaTemp.put("body", constantMap.getString(ConstantKeys.WEB_NAME));
			sParaTemp.put("enable_paymethod", AlipayConfig.enable_paymethod);
//		sParaTemp.put("disable_paymethod", AlipayConfig.disable_paymethod);
//			sParaTemp.put("it_b_pay", AlipayConfig.it_b_pay);
//			sParaTemp.put("qr_pay_mode", AlipayConfig.qr_pay_mode);
//			sParaTemp.put("qrcode_width", AlipayConfig.qrcode_width);

			String aliay_form_url = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
			jsonObject.accumulate("code", 0);
			jsonObject.accumulate("aliay_form_url", aliay_form_url);
			return jsonObject.toString();
		} catch (Exception e) {
			LOG.e(this, "支付宝充值异常", e);
		}

		return jsonObject.toString();
	}


	//再次获取，重新发送邮件
    @RequestMapping(value = "/account/email/resendwith", produces = {JsonEncode})
    @ResponseBody
	public String reSendEmail(HttpServletRequest request, @RequestParam(required = true, defaultValue = "0") int fid) {
        JSONObject jsonObject = new JSONObject();
        if(fid == 0) {
            jsonObject.accumulate("code", -1);
            jsonObject.accumulate("msg", "参数错误");
            return jsonObject.toString();
        }

        Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
        if (fuser == null) {
            jsonObject.accumulate(CODE, -1);
            jsonObject.accumulate(MSG, "用户未登录");
            return jsonObject.toString();
        }

        //根据fid查询提币操作流水
        Fvirtualcaptualoperation fvirtualcaptualoperation = this.frontVirtualCoinService.findFvirtualcaptualoperationById(fid);
        if(fvirtualcaptualoperation == null) {
            jsonObject.accumulate("code", -1);
            jsonObject.accumulate("msg", "非法操作");
            return jsonObject.toString();
        }
        String ip = getIpAddr(request) ;
        this.frontVirtualCoinService.resendWithdrawEmail(fuser, ip, fvirtualcaptualoperation);
        jsonObject.accumulate("code", 0);
        jsonObject.accumulate("msg", "发送成功");

        return jsonObject.toString();
    }


}
