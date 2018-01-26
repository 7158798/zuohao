package com.ruizton.main.controller.front;

import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import cn.jpush.api.utils.StringUtils;
import com.google.gson.Gson;
import com.opensymphony.oscache.util.StringUtil;
import com.ruizton.main.controller.app.ApiConstant;
import com.ruizton.main.controller.front.response.UserIntegralDetailResp;
import com.ruizton.main.log.LOG;
import com.ruizton.main.Enum.*;
import com.ruizton.main.model.*;
import com.ruizton.main.model.integral.Fuserintegraldetail;
import com.ruizton.util.*;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.comm.ValidateMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.SystemArgsService;
import com.ruizton.main.service.admin.UserService;
import com.ruizton.main.service.front.FrontAccountService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontValidateService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.main.service.front.FtradeMappingService;
import com.ruizton.main.service.front.UtilsService;

@Controller
public class FrontUserJsonController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FrontUserJsonController.class);

	private static final String CLASS_NAME = FrontUserJsonController.class.getSimpleName();

	
	/* 邮箱或者和手机号码是否重复是否重复
	 * @param type:0手机，1邮箱
	 * @Return 0重复，1正常
	 * */
	@ResponseBody
	@RequestMapping(value="/user/reg/chcekregname",produces=JsonEncode)
	public String chcekregname(
			@RequestParam(required=false,defaultValue="0") String name,
			@RequestParam(required=false,defaultValue="1") int type,
			@RequestParam(required=false,defaultValue="0") int random
			) throws Exception{		
		JSONObject jsonObject = new JSONObject() ;

		if(type==0){
			//手机账号
			boolean flag = this.frontUserService.isTelephoneExists(name) ;
			if(flag){
				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg", "手机号码错误或已存在");
				jsonObject.accumulate(MSG, i18nMsg("phone_error_or_exists"));
			}else{
				jsonObject.accumulate("code", 0) ;
			}
		}else{
			//邮箱账号
			boolean flag = this.frontUserService.isEmailExists(name) ;
			if(flag){
				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg", "邮箱错误或已存在");
				jsonObject.accumulate(MSG, i18nMsg("email_error_or_exists"));
			}else{
				jsonObject.accumulate("code", 0) ;
			}
		}
		
		
		return jsonObject.toString() ;
	
	}
	
	/* @Param　regType:0手机，1email
	 * @Return 1正常，-2名字重复，-4邮箱格式不对，-5客户端你没打开cookie
	 * */
	@ResponseBody
	@RequestMapping(value="/user/reg/index",produces=JsonEncode)
	public String regIndex(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0") int random,
			@RequestParam(required=false,defaultValue="0") String password,
			@RequestParam(required=false,defaultValue="0") String regName,
			@RequestParam(required=false,defaultValue="0") int regType,//0手机、1邮箱
			@RequestParam(required=false,defaultValue="0") String vcode,
			@RequestParam(required=false,defaultValue="0") String ecode,
			@RequestParam(required=true,defaultValue="0") String phoneCode
			) throws Exception{
		String areaCode = "86" ;
		
		JSONObject jsonObject = new JSONObject() ;
		
		String phone = HtmlUtils.htmlEscape(regName);
		phoneCode = HtmlUtils.htmlEscape(phoneCode);
		String isOpenReg = constantMap.get("isOpenReg").toString().trim();
		if(!isOpenReg.equals("1")){
			jsonObject.accumulate("code", -888) ;
//			jsonObject.accumulate("msg", "暂停注册") ;
			jsonObject.accumulate(MSG, i18nMsg("suspend_regist"));
			return jsonObject.toString() ;
		}
		
		password = HtmlUtils.htmlEscape(password.trim());
		if(validateLoginPassword(password)){
			jsonObject.accumulate("code", -11) ;
//			jsonObject.accumulate("msg", "密码格式不正确") ;
			jsonObject.accumulate(MSG, i18nMsg("pwd_not_format"));
			return jsonObject.toString() ;
		}
		//邮箱
		if(regType==0){
			//手机注册
			
			boolean flag1 = this.frontUserService.isTelephoneExists(regName) ;
			if(flag1){
				jsonObject.accumulate("code", -22) ;
//				jsonObject.accumulate("msg", "手机号码已经被注册") ;
				jsonObject.accumulate(MSG, i18nMsg("phone_exists"));
				return jsonObject.toString() ;
			}
			
			if(!phone.matches(new Constant().PhoneReg)){
				jsonObject.accumulate("code", -22) ;
//				jsonObject.accumulate("msg", "手机格式错误") ;
				jsonObject.accumulate(MSG, i18nMsg("phone_not_format"));
				return jsonObject.toString() ;
			}
			
			boolean mobilValidate = validateMessageCode(getIpAddr(request),areaCode,phone, MessageTypeEnum.REG_CODE, phoneCode) ;
			if(!mobilValidate){
				jsonObject.accumulate("code", -20) ;
//				jsonObject.accumulate("msg", "短信验证码错误") ;
				jsonObject.accumulate(MSG, i18nMsg("sms_code_error"));
				return jsonObject.toString() ;
			}
			
		}else{
			//邮箱注册
			boolean flag = this.frontUserService.isEmailExists(regName) ;
			if(flag){
				jsonObject.accumulate("code", -12) ;
//				jsonObject.accumulate("msg", "邮箱已经存在") ;
				jsonObject.accumulate(MSG, i18nMsg("email_exists"));
				return jsonObject.toString() ;
			}
			
			boolean mailValidate = validateMailCode(getIpAddr(request), phone, SendMailTypeEnum.RegMail, ecode);
			if(!mailValidate){
				jsonObject.accumulate("code", -10) ;
//				jsonObject.accumulate("msg", "邮箱验证码错误") ;
				jsonObject.accumulate(MSG, i18nMsg("email_code_error"));
				return jsonObject.toString() ;
			}
			
			if(!regName.matches(new Constant().EmailReg)){
				jsonObject.accumulate("code", -12) ;
//				jsonObject.accumulate("msg", "邮箱格式错误") ;
				jsonObject.accumulate(MSG, i18nMsg("email_not_format"));
				return jsonObject.toString() ;
			}
			
		}
		
		
		//推广
		Fuser intro = null ;
		String intro_user = request.getParameter("intro_user") ;
		
		try {
			if(intro_user!=null && !"".equals(intro_user.trim())){
				intro = this.frontUserService.findById(Integer.parseInt(intro_user.trim())) ;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(intro==null){
			String isMustIntrol = constantMap.get("isMustIntrol").toString().trim();
			if(isMustIntrol.equals("1")){
				jsonObject.accumulate("code", -200) ;
//				jsonObject.accumulate("msg", "请填写正确的邀请码") ;
				jsonObject.accumulate(MSG, i18nMsg("please_fill_invi_code"));
				return jsonObject.toString() ;
			}
		}
		
		
		Fuser fuser = new Fuser() ;
		if(intro!=null){
			fuser.setfIntroUser_id(intro) ;
		}
		fuser.setFintrolUserNo(null);
		
		if(regType == 0){
			//手机注册
			fuser.setFregType(RegTypeEnum.TEL_VALUE);
			fuser.setFtelephone(phone);
			fuser.setFareaCode(areaCode);
			fuser.setFisTelephoneBind(true);
			
			fuser.setFnickName(phone) ;
			fuser.setFloginName(phone) ;
		}else{
			fuser.setFregType(RegTypeEnum.EMAIL_VALUE);
			fuser.setFemail(regName) ;
			fuser.setFisMailValidate(true) ;
			fuser.setFnickName(regName.split("@")[0]) ;
			fuser.setFloginName(regName) ;
			fuser.setFisMailValidate(true);
		}
		
		fuser.setSalt(Utils.getUUID());
		fuser.setFregisterTime(Utils.getTimestamp()) ;
		fuser.setFloginPassword(Utils.MD5(password,fuser.getSalt())) ;
		fuser.setFtradePassword(null) ;
		String ip = getIpAddr(request) ;
		fuser.setFregIp(ip);
		fuser.setFlastLoginIp(ip);
		fuser.setFstatus(UserStatusEnum.NORMAL_VALUE) ;
		fuser.setFlastLoginTime(Utils.getTimestamp()) ;
		fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
		boolean saveFlag = false ;
		try {
			saveFlag = this.frontUserService.saveRegister(fuser) ;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(regType==0){
				String key1 = ip+"message_"+MessageTypeEnum.REG_CODE ;
				String key2 = ip+"_"+phone+"_"+MessageTypeEnum.REG_CODE ;
				this.validateMap.removeMessageMap(key1);
				this.validateMap.removeMessageMap(key2);
			}else{
				String key1 = ip+"mail_"+SendMailTypeEnum.RegMail ;
				String key2 = ip+"_"+phone+"_"+SendMailTypeEnum.RegMail ;
				this.validateMap.removeMailMap(key1);
				this.validateMap.removeMailMap(key2);
			}
		}
		
		if(saveFlag){
			try{
				//绑定送积分
				if(regType==0){
					this.integralService.addUserIntegralFirst(IntegralTypeEnum.PHONE_FIRST,fuser.getFid(),0);
				}else{
					this.integralService.addUserIntegralFirst(IntegralTypeEnum.EMAIL_FIRST,fuser.getFid(),0);
				}
			}catch (Exception e){
				LOG.e(this.getClass(),"推荐人送积分异常");
			}



			this.SetSession(fuser,request) ;

			jsonObject.accumulate("code", 0) ;
//			jsonObject.accumulate("msg", "注册成功") ;
			jsonObject.accumulate(MSG, i18nMsg("regist_success"));
			return jsonObject.toString() ;
		
		}else{
			jsonObject.accumulate("code", -10) ;
//			jsonObject.accumulate("msg", "网络错误，请稍后再试") ;
			jsonObject.accumulate(MSG, i18nMsg("newword_error_try_again"));
			return jsonObject.toString() ;
		}
	}

	/*@ResponseBody
	@RequestMapping(value="/user/login/indexTest",produces=JsonEncode)
	public String loginIndexTest(
			HttpServletRequest request
	) throws Exception {
		System.out.println("sdfadfasdfas");
		return null;
	}*/
	
	@ResponseBody
	@RequestMapping(value="/user/login/index",produces=JsonEncode)
	public String loginIndex(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int random,
			@RequestParam(required=true) String loginName,
			@RequestParam(required=true) String password,
			@RequestParam(required=false) String googleCode
			) throws Exception {
		LOG.i(this.getClass(),"用户登录开始");

		JSONObject jsonObject = new JSONObject() ;
		
		int longLogin = 0;//0是手机，1是邮箱
		if(loginName.matches(Constant.PhoneReg) == false  ){
			longLogin = -1 ;
		}
		if(loginName.matches(Constant.EmailReg) == true){
			longLogin = 1 ;
		}
		if(longLogin == -1){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "用户名错误") ;
			jsonObject.accumulate(MSG, i18nMsg("user_name_error"));
			return jsonObject.toString() ;
		}
		
		List<Fuser> fusers = this.frontUserService.findUserByProperty("floginName", loginName);
		if(fusers == null || fusers.size() != 1){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "用户名不存在") ;
			jsonObject.accumulate(MSG, i18nMsg("user_name_not_exists"));
			return jsonObject.toString() ;
		}

		Boolean google = fusers.get(0).getFgoogleBind();


		Fuser fuser = new Fuser() ;
		fuser.setFloginName(loginName);
		fuser.setFloginPassword(password) ;
		fuser.setSalt(fusers.get(0).getSalt());
		String ip = getIpAddr(request) ;
		int limitedCount = this.frontValidateService.getLimitCount(ip,CountLimitTypeEnum.LOGIN_PASSWORD) ;
		if(limitedCount>0){
			if(!fusers.get(0).getFgoogleBind()){
				fuser = this.frontUserService.updateCheckLogin(fuser, ip,longLogin==1,true) ;
			}else{
				fuser = this.frontUserService.updateCheckLogin(fuser, ip,longLogin==1,false) ;
			}

			if(fuser!=null){
				if(fuser.getFgoogleBind()){
					if(StringUtils.isEmpty(googleCode)){
						jsonObject.accumulate("code", -3) ;
//						jsonObject.accumulate("msg", "需要输入Google验证码") ;
						jsonObject.accumulate(MSG, i18nMsg("req_google_code"));
						return jsonObject.toString() ;
					}
					int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE ) ;
					if(google_limit<=0){
						jsonObject.accumulate("code", -4) ;
//						jsonObject.accumulate("msg", "此ip操作频繁，请2小时后再试!") ;
						jsonObject.accumulate(MSG, i18nMsg("ip_disable"));
						return jsonObject.toString() ;
					}

					boolean googleValidate = GoogleAuth.auth(Long.parseLong(googleCode.trim()), fuser.getFgoogleAuthenticator()) ;
					if(!googleValidate){
						jsonObject.accumulate("code", -4) ;
//						jsonObject.accumulate("msg", "谷歌验证码有误，您还有"+google_limit+"次机会") ;
						jsonObject.accumulate(MSG, i18nMsg("google_vercode_error", new Object[]{google_limit}));
						this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
						return jsonObject.toString() ;
					}else if(google_limit<new Constant().ErrorCountLimit){
						this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
					}
					this.frontUserService.updateCheckLogin(fuser, ip,longLogin==1,true) ;
				}


				String isCanlogin = constantMap.get("isCanlogin").toString().trim();

				integralService.addUserIntegral(IntegralTypeEnum.LOGIN,fuser);

				if(!isCanlogin.equals("1")){
					boolean isCanLogin = false;
					String[] canLoginUsers = constantMap.get("canLoginUsers").toString().split("#");
					for(int i=0;i<canLoginUsers.length;i++){
						if(canLoginUsers[i].equals(String.valueOf(fuser.getFid()))){
							isCanLogin = true;
							break;
						}
					}
					if(!isCanLogin){
						jsonObject.accumulate("code", -1) ;
//						jsonObject.accumulate("msg", "网站暂时不允许登陆") ;
						jsonObject.accumulate(MSG, i18nMsg("sys_not_login"));
						return jsonObject.toString() ;
					}
				}
				
				if(fuser.getFstatus()==UserStatusEnum.FORBBIN_VALUE){
					jsonObject.accumulate("code", -1) ;
//					jsonObject.accumulate("msg", "账户出现安全隐患被禁止登录，请尽快联系客服") ;
					jsonObject.accumulate(MSG, i18nMsg("account_please_customer_service"));
					return jsonObject.toString() ;
				}else{
					this.frontValidateService.deleteCountLimite(ip,CountLimitTypeEnum.LOGIN_PASSWORD) ;
					if(fuser.getFopenSecondValidate()){
						SetSecondLoginSession(request,fuser);
					}else{
						SetSession(fuser,request) ;
						jsonObject.accumulate("code", 0) ;
//						jsonObject.accumulate("msg", "登陆成功") ;
						jsonObject.accumulate(MSG, i18nMsg("login_success"));
						return jsonObject.toString() ;
					}
				}
			}else{
				//错误的用户名或密码
				if(limitedCount==new Constant().ErrorCountLimit){
					jsonObject.accumulate("code", -2) ;
//					jsonObject.accumulate("msg", "用户名或密码错误<a href=\"/validate/reset.html\">找回密码&gt;&gt;</a>") ;
					jsonObject.accumulate(MSG, i18nMsg("user_error"));
				}else{
					jsonObject.accumulate("code", -2) ;
//					jsonObject.accumulate("msg", "用户名或密码错误，您还有"+limitedCount+"次机会") ;
					jsonObject.accumulate(MSG, i18nMsg("user_name_error_desc", new Object[]{limitedCount}));
				}
				this.frontValidateService.updateLimitCount(ip,CountLimitTypeEnum.LOGIN_PASSWORD) ;
				return jsonObject.toString() ;
			}
		}else{
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "此ip登录频繁，请2小时后再试!") ;
			jsonObject.accumulate(MSG, i18nMsg("ip_login_freq_2h_again"));
			return jsonObject.toString() ;
		}
		LOG.i(this,"用户登录结束");

		jsonObject.accumulate("code", -1) ;
		return null;
	}
	
	/*
	 * 添加谷歌设备
	 * */
	@ResponseBody
	@RequestMapping(value="/user/googleAuth",produces={JsonEncode})
	public String googleAuth(
			HttpServletRequest request
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		if(fuser.getFgoogleBind()){
			//已经绑定机器了，属于非法提交
			jsonObject.accumulate("code", -1) ;
			return jsonObject.toString() ;
		}
		
		Map<String, String> map = GoogleAuth.genSecret(fuser.getFloginName()) ;
		String totpKey = map.get("secret") ;
		String qecode = map.get("url") ;
		
		fuser.setFgoogleAuthenticator(totpKey) ;
		fuser.setFgoogleurl(qecode) ;
		fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
		this.frontUserService.updateFUser(fuser,getSession(request),-1,null) ;
		
		jsonObject.accumulate("qecode", qecode) ;
		jsonObject.accumulate("code", 0) ;
		jsonObject.accumulate("totpKey", totpKey) ;
				
		return jsonObject.toString() ;
	}
	
	/*
	 * 添加设备认证
	 * */
	@ResponseBody
	@RequestMapping(value="/user/validateAuthenticator",produces={JsonEncode})
	public String validateAuthenticator(
			HttpServletRequest request,
			@RequestParam(required=true)String code,
			@RequestParam(required=true)String totpKey
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		String ip = getIpAddr(request) ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;

		boolean b_status = fuser.getFgoogleBind()==false
							&& totpKey.equals(fuser.getFgoogleAuthenticator())
							&& !totpKey.trim().equals("");
		
		if(!b_status){
			//非法提交
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "您已绑定GOOGLE验证器，请勿重复操作") ;
			jsonObject.accumulate(MSG, i18nMsg("bind_google_not_repeat"));
			return jsonObject.toString() ;
		}
		
		int limitedCount = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
		if(limitedCount>0){
			boolean auth = GoogleAuth.auth(Long.parseLong(code), fuser.getFgoogleAuthenticator()) ;
			if(auth){
				jsonObject.accumulate("code", 0) ;//0成功，-1，错误
//				jsonObject.accumulate("msg", "绑定成功") ;
				jsonObject.accumulate(MSG, i18nMsg("bind_success"));
				this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
				
				fuser.setFgoogleBind(true) ;
				fuser.setFgoogleValidate(false) ;
				this.frontUserService.updateFUser(fuser, getSession(request),LogTypeEnum.User_BIND_GOOGLE,ip) ;

				//绑定GOOGLE===WEB首次送积分
				this.integralService.addUserIntegralFirst(IntegralTypeEnum.GA_FIRST,fuser.getFid(),0);

				return jsonObject.toString() ;
			}else{
				this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg", "GOOGLE验证码有误，您还有"+limitedCount+"次机会") ;
				jsonObject.accumulate(MSG, i18nMsg("google_code_error", new Object[]{ limitedCount }));
				return jsonObject.toString() ;
			}
		}else{
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "此ip操作频繁，请2小时后再试!") ;
			jsonObject.accumulate(MSG, i18nMsg("ip_disable"));
			return jsonObject.toString() ;
		}
		
	}
	
	/*
	 * 查看谷歌密匙
	 * */
	@ResponseBody
	@RequestMapping(value="/user/getGoogleTotpKey")
	public String getGoogleTotpKey(
			HttpServletRequest request,
			@RequestParam(required=true) String totpCode
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		String ip = getIpAddr(request) ;
		int limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE ) ;
		if(limit<=0){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "此ip操作频繁，请2小时后再试!") ;
			jsonObject.accumulate(MSG, i18nMsg("ip_disable"));
			return jsonObject.toString() ;
		}else{
			if(fuser.getFgoogleBind()){
				if(GoogleAuth.auth(Long.parseLong(totpCode), fuser.getFgoogleAuthenticator())){
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
					
					jsonObject.accumulate("qecode", fuser.getFgoogleurl()) ;
					jsonObject.accumulate("code", 0) ;
					jsonObject.accumulate("totpKey", fuser.getFgoogleAuthenticator()) ;
//					jsonObject.accumulate("msg", "验证成功") ;
					jsonObject.accumulate(MSG, i18nMsg("ver_success"));
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
					return jsonObject.toString() ;
				}else{
					jsonObject.accumulate("code", -1) ;
//					jsonObject.accumulate("msg", "GOOGLE验证码有误，您还有"+limit+"次机会") ;
					jsonObject.accumulate(MSG, i18nMsg("google_code_error", new Object[]{limit}));
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
					return jsonObject.toString() ;
				}
			}else{
				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg", "您未绑定GOOGLE验证器") ;
				jsonObject.accumulate(MSG, i18nMsg("unbind_google"));
				return jsonObject.toString() ;
			}
		}
	}

	//可以删除
	@ResponseBody
	@RequestMapping(value="/user/sendValidateCode",produces={JsonEncode})
	public String sendValidateCode(
			HttpServletRequest request,
			@RequestParam(required=true,defaultValue="0")String areaCode,
			@RequestParam(required=true,defaultValue="0")String phone
			) throws Exception{
		if(!areaCode.equals("86") || phone.matches("^\\d{10,14}$")){
			boolean isPhoneExists = this.frontUserService.isTelephoneExists(phone) ;
			if(isPhoneExists){
				return String.valueOf(-3) ;//手机账号存在
			}else{
				Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
				super.SendMessage(fuser,fuser.getFid(), areaCode ,phone, MessageTypeEnum.BANGDING_MOBILE) ;
				return String.valueOf(0) ;
			}
		}else{
			return String.valueOf(-2) ;//手机号码格式不正确
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value="/user/validatePhone",produces={JsonEncode})
	public String validatePhone(
			HttpServletRequest request,
			@RequestParam(required=true)int isUpdate,//0是绑定手机，1是换手机号码
			@RequestParam(required=true)String areaCode,
			@RequestParam(required=true)String imgcode,
			@RequestParam(required=true)String phone,
			@RequestParam(required=true)String oldcode,
			@RequestParam(required=true)String newcode,
			@RequestParam(required=false,defaultValue="0")String totpCode
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		areaCode = areaCode.replace("+", "");
		if(!phone.matches("^\\d{10,14}$")){//手機格式不對
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "手机号码格式不正确");
			jsonObject.accumulate(MSG, i18nMsg("phone_not_format2"));
			return jsonObject.toString() ;
		}
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		if(isUpdate ==0){
			if(fuser.isFisTelephoneBind()){
				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg", "您已绑定了手机号码");
				jsonObject.accumulate(MSG, i18nMsg("bind_phone_not_repeat"));
				return jsonObject.toString() ;
			}
		}else{
			if(!fuser.isFisTelephoneBind()){
				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg", "您还未绑定手机号码");
				jsonObject.accumulate(MSG, i18nMsg("unbind_phone"));
				return jsonObject.toString() ;
			}
		}

		
		String ip = getIpAddr(request) ;
		int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
		int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
		if(google_limit<=0){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "此ip操作频繁，请2小时后再试!");
			jsonObject.accumulate(MSG, i18nMsg("ip_disable"));
			return jsonObject.toString() ;
		}
		if(tel_limit<=0){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "此ip操作频繁，请2小时后再试!");
			jsonObject.accumulate(MSG, i18nMsg("ip_disable"));
			return jsonObject.toString() ;
		}
		
		if(fuser.getFgoogleBind()){
			boolean googleAuth = GoogleAuth.auth(Long.parseLong(totpCode),fuser.getFgoogleAuthenticator()) ;
			if(!googleAuth){
				//谷歌驗證失敗
				this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg", "GOOGLE验证码有误，您还有"+google_limit+"次机会");
				jsonObject.accumulate(MSG, i18nMsg("google_code_error", new Object[]{google_limit}));
				return jsonObject.toString() ;
			}else{
				this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
			}
		}

		if(isUpdate ==1){
			if(!validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.JIEBANG_MOBILE, oldcode)){
				//手機驗證錯誤
				 this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg", "旧手机验证码有误，您还有"+tel_limit+"次机会");
				jsonObject.accumulate(MSG, i18nMsg("old_phone_code_error", new Object[]{tel_limit}));
				return jsonObject.toString() ;
			}else{
				this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
			}
		}
		
		if(validateMessageCode(fuser, areaCode, phone, MessageTypeEnum.BANGDING_MOBILE, newcode)){
			//判斷手機是否被綁定了
			List<Fuser> fusers = this.frontUserService.findUserByProperty("ftelephone", phone) ;
			if(fusers.size()>0){//手機號碼已經被綁定了
				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg", "手机号码已存在");
				jsonObject.accumulate(MSG, i18nMsg("phone_exists2"));
				return jsonObject.toString() ;
			}
			
			
			if(vcodeValidate(request, imgcode) == false ){
				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg", "请输入正确的图片验证码");
				jsonObject.accumulate(MSG, i18nMsg("comm.error.tips.139"));
				return jsonObject.toString() ;
			}
			
			fuser.setFareaCode(areaCode) ;
			fuser.setFtelephone(phone) ;
			if(fuser.getFregType() == RegTypeEnum.TEL_VALUE){
				fuser.setFloginName(phone);
			}
			fuser.setFisTelephoneBind(true) ;
			fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
			try {
				this.frontUserService.updateFUser(fuser, getSession(request),LogTypeEnum.User_BIND_PHONE,ip) ;

				if(isUpdate == 0){
					//绑定手机===WEB首次送积分
					this.integralService.addUserIntegralFirst(IntegralTypeEnum.PHONE_FIRST,fuser.getFid(),0);
					LOGGER.info(CLASS_NAME + " validateIdentity,修改用户钱包信息成功，增加了奖励金额");
				}
			} catch (Exception e) {
				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg", "网络异常");
				jsonObject.accumulate(MSG, i18nMsg("s_network_anomaly"));
				return jsonObject.toString() ;
			}finally{
				//成功
				this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.BANGDING_MOBILE);
				this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.JIEBANG_MOBILE);
			}
			

			this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
			jsonObject.accumulate("code", 0) ;
//			jsonObject.accumulate("msg", "绑定成功");
			jsonObject.accumulate(MSG, i18nMsg("bind_success"));
			return jsonObject.toString() ;
		}else{
			//手機驗證錯誤
			 this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "手机验证码有误，您还有"+tel_limit+"次机会");
			jsonObject.accumulate(MSG, i18nMsg("phone_vercode_error", new Object[]{tel_limit}));
			return jsonObject.toString() ;
		}
	}

	//短信发送，验证码发送
	@ResponseBody
	@RequestMapping(value="/user/sendMsg")
	public String sendMsg(
			HttpServletRequest request,
			@RequestParam(required=true) int type
			) throws Exception{
		String areaCode = "86" ;
		String phone = request.getParameter("phone") ;
		String vcode = request.getParameter("vcode") ;
		LOGGER.info(CLASS_NAME + " sendMsg,入参明细phone:{},vcode:{},type:{}", phone, vcode, type);
		//注册类型免登陆可以发送
		JSONObject jsonObject = new JSONObject() ;
		LOGGER.info(CLASS_NAME + " sendMsg,获取请求用户对象GetSession(request)=" + GetSession(request));
		if(type != MessageTypeEnum.REG_CODE &&type != MessageTypeEnum.FIND_PASSWORD && GetSession(request)==null){
			LOGGER.info(CLASS_NAME + " sendMsg,校验不通过");
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "非法提交");
			jsonObject.accumulate(MSG, i18nMsg("illegal_submit"));
			return jsonObject.toString() ;
		}
		
		if(type<MessageTypeEnum.BEGIN || type>MessageTypeEnum.END){
			LOGGER.info(CLASS_NAME + " sendMsg,非法提交");
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "非法提交");
			jsonObject.accumulate(MSG, i18nMsg("illegal_submit"));
			return jsonObject.toString() ;
		}
		
		//注册需要验证码
		if(type == MessageTypeEnum.REG_CODE||type == MessageTypeEnum.FIND_PASSWORD||type == MessageTypeEnum.FIND_TRADE_PWD){
			if(vcodeValidate(request, vcode) == false ){
				LOGGER.info(CLASS_NAME + " sendMsg,图片验证码错误");
				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg", "请输入正确的图片验证码");
				jsonObject.accumulate(MSG, i18nMsg("comm.error.tips.139"));
				return jsonObject.toString() ;
			}
		}
		
		String ip = getIpAddr(request) ;
		LOGGER.info(CLASS_NAME + " sendMsg,根据ip查询是否存在限制ip:{}", ip);
		int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
		if(tel_limit<=0){
			LOGGER.info(CLASS_NAME + " sendMsg,此ip操作频繁，请2小时后再试!");
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "此ip操作频繁，请2小时后再试!");
			jsonObject.accumulate(MSG, i18nMsg("ip_disable"));
			return jsonObject.toString() ;
		}


		
		Fuser fuser = null ;
		if(type !=MessageTypeEnum.REG_CODE){
			if(type == MessageTypeEnum.FIND_PASSWORD){
				List<Fuser> fusers = this.utilsService.list(0, 0, " where ftelephone=? ", false, Fuser.class,new Object[]{phone}) ;
				if(fusers.size()==1){
					fuser = fusers.get(0) ;
				}else{
					LOGGER.info(CLASS_NAME + " sendMsg,手机号码错误");
					jsonObject.accumulate("code", -1) ;
//					jsonObject.accumulate("msg", "手机号码错误");
					jsonObject.accumulate(MSG, i18nMsg("comm.error.tips.27"));
					return jsonObject.toString() ;
				}
			}else{
				fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
				if(type == MessageTypeEnum.BANGDING_MOBILE){
//					if(fuser.isFisTelephoneBind()){
//						jsonObject.accumulate("code", -1) ;
//						jsonObject.accumulate("msg", "您已绑定手机");
//						return jsonObject.toString() ;
//					}
					if(phone == null || phone.trim().length() ==0){
						jsonObject.accumulate("code", -1) ;
//						jsonObject.accumulate("msg", "绑定的手机号码不能为空");
						jsonObject.accumulate(MSG, i18nMsg("bind_phone_isbank"));
						return jsonObject.toString() ;
					}
					if(!phone.matches("^\\d{10,14}$")){//手機格式不對
						jsonObject.accumulate("code", -1) ;
//						jsonObject.accumulate("msg", "手机号码格式不正确");
						jsonObject.accumulate(MSG, i18nMsg("phone_not_format2"));
						return jsonObject.toString() ;
					}
					List<Fuser> fusers = this.utilsService.list(0, 0, " where ftelephone=? ", false, Fuser.class,new Object[]{phone}) ;
					if(fusers.size() >0){
						LOGGER.info(CLASS_NAME + " sendMsg,手机号码已存在");
						jsonObject.accumulate("code", -1) ;
//						jsonObject.accumulate("msg", "手机号码已存在");
						jsonObject.accumulate(MSG, i18nMsg("phone_exists2"));
						return jsonObject.toString() ;
					}
				}else if(type == MessageTypeEnum.CNY_ACCOUNT_WITHDRAW){
					if(fuser.isFisTelephoneBind()){
						if(StringUtils.isEmpty(phone)){
							phone = fuser.getFtelephone() ;
						}
					}else{
						boolean flag = this.frontUserService.isTelephoneExists(phone) ;
						if(flag){
							jsonObject.accumulate("code", -1) ;
//							jsonObject.accumulate("msg", "手机号码已存在");
							jsonObject.accumulate(MSG, i18nMsg("phone_exists2"));
							return jsonObject.toString() ;
						}
					}

				} else {
					if(!fuser.isFisTelephoneBind()){
						jsonObject.accumulate("code", -1) ;
//						jsonObject.accumulate("msg", "您没有绑定手机");
						jsonObject.accumulate(MSG, i18nMsg("not_bind_phone"));
						return jsonObject.toString() ;
					}else{
						phone = fuser.getFtelephone() ;
					}
				}
		}
		}else{
			boolean flag = this.frontUserService.isTelephoneExists(phone) ;
			LOGGER.info(CLASS_NAME + " sendMsg,isTelephoneExists从数据库查询手机号是否存在，返回flag:{}", flag);
			if(flag){
				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg", "手机号码已存在");
				jsonObject.accumulate(MSG, i18nMsg("phone_exists2"));
				return jsonObject.toString() ;
			}
		}


		//查看每日次数限制

		String filter = " where fPhone = '"+phone+"' and date_format(fCreateTime,'%Y-%m-%d') = date_format(now(),'%Y-%m-%d')";
		int todayCount = this.utilsService.count(filter,Fvalidatemessage.class);
		int limitCount = Integer.parseInt(this.constantMap.get("messageOneDay").toString());
		if(limitCount >0 && todayCount >= limitCount){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "验证码次数已超限制");
			jsonObject.accumulate(MSG, i18nMsg("code_desc1"));
			LOGGER.error(CLASS_NAME+"sendMsg,{}验证码次数已超出每日限制",phone);
			return jsonObject.toString() ;
		}


		Boolean flag = false;
		if(MessageTypeEnum.REG_CODE == type ){
			LOGGER.info(CLASS_NAME + " sendMsg,注册发送验证码");
			//注册
			flag = SendMessage(getIpAddr(request), areaCode, phone, type) ;
		}else if(MessageTypeEnum.BANGDING_MOBILE == type || MessageTypeEnum.CNY_ACCOUNT_WITHDRAW == type){
			LOGGER.info(CLASS_NAME + " sendMsg,绑定手机发送验证码");
			flag = SendMessage(fuser,fuser.getFid(), areaCode, phone, type) ;
		}else{
			LOGGER.info(CLASS_NAME + " sendMsg,其它类型发送验证码");
			flag = SendMessage(fuser,fuser.getFid(), fuser.getFareaCode(), fuser.getFtelephone(), type) ;
		}
		LOGGER.info(CLASS_NAME + " sendMsg,调BaseController.SendMessage()完成，flag:{}", flag);
		if(flag){
			jsonObject.accumulate("code", 0) ;
//			jsonObject.accumulate("msg", "验证码已经发送，请查收");
			jsonObject.accumulate(MSG, i18nMsg("ver_code_send_success"));
		}else{
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "发送频繁,请稍后再试");
			jsonObject.accumulate(MSG, i18nMsg("send_frequently_try_again"));
		}
		LOGGER.info(CLASS_NAME + " sendMsg,返回值resultStr:{}", jsonObject.toString());
		return jsonObject.toString() ;
	
	}
	
	//发送邮件验证码
	@ResponseBody
	@RequestMapping(value="/user/sendMailCode")
	public String sendMailCode(
			HttpServletRequest request,
			@RequestParam(required=true) int type
			) throws Exception{
		String email = request.getParameter("email") ;
		String vcode = request.getParameter("vcode") ;
		
		//注册类型免登陆可以发送
		JSONObject jsonObject = new JSONObject() ;
		
		if(type != SendMailTypeEnum.RegMail && GetSession(request)==null){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "非法提交");
			jsonObject.accumulate(MSG, i18nMsg("illegal_submit"));
			return jsonObject.toString() ;
		}
		
		if(type<SendMailTypeEnum.BEGIN || type>SendMailTypeEnum.END){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "非法提交");
			jsonObject.accumulate(MSG, i18nMsg("illegal_submit"));
			return jsonObject.toString() ;
		}
		
		//注册需要验证码
		if(type == SendMailTypeEnum.RegMail){
			if(vcodeValidate(request, vcode) == false ){
				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg", "请输入正确的图片验证码");
				jsonObject.accumulate(MSG, i18nMsg("img_ver_code"));
				return jsonObject.toString() ;
			}
		}


		String ip = getIpAddr(request) ;
		int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.REG_MAIL) ;
		if(tel_limit<=0){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "此ip操作频繁，请2小时后再试!");
			jsonObject.accumulate(MSG, i18nMsg("ip_disable"));
			return jsonObject.toString() ;
		}

		boolean flag = this.frontUserService.isEmailExists(email) ;
		if(flag){
			jsonObject.accumulate("code", -12) ;
//			jsonObject.accumulate("msg", "邮箱已经存在") ;
			jsonObject.accumulate(MSG, i18nMsg("email_exists"));
			return jsonObject.toString() ;
		}

		
		SendMail(getIpAddr(request), email, type) ;
		
		jsonObject.accumulate("code", 0) ;
//		jsonObject.accumulate("msg", "验证码已经发送，请查收");
		jsonObject.accumulate(MSG, i18nMsg("ver_code_send_success"));
		return jsonObject.toString() ;
		
	}
	
	/**
	 * 增加提现支付宝
	 * **/
/*	@ResponseBody
	@RequestMapping("/user/updateOutAlipayAddress")
	public String updateOutAlipayAddress(
			HttpServletRequest request,
			@RequestParam(required=true)String account,
			@RequestParam(required=false,defaultValue="0")String phoneCode,
			@RequestParam(required=false,defaultValue="0")String totpCode,
			@RequestParam(required=true)String payeeAddr//开户姓名
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		boolean isGoogleBind = fuser.getFgoogleBind() ;
		boolean isTelephoneBind = fuser.isFisTelephoneBind() ;
		if(!isGoogleBind && !isTelephoneBind){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "请先绑定GOOGLE验证或手机号码") ;
			return jsonObject.toString();
		}
		
		account = HtmlUtils.htmlEscape(account);
		phoneCode = HtmlUtils.htmlEscape(phoneCode);
		totpCode = HtmlUtils.htmlEscape(totpCode);
		payeeAddr = HtmlUtils.htmlEscape(payeeAddr);
		
		if(fuser.getFrealName().equals(payeeAddr)){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "支付宝姓名必须与您的实名认证姓名一致") ;
			return jsonObject.toString();
		}
		
		if(account.length() >200){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "支付宝帐号有误") ;
			return jsonObject.toString();
		}
		
		String ip = getIpAddr(request) ;
		int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
		int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
		
		if(fuser.isFisTelephoneBind()){
			if(tel_limit<=0){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "手机验证码错误，请稍候再试") ;
				return jsonObject.toString();
			}else{
				if(!validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.CNY_ACCOUNT_WITHDRAW, phoneCode)){
					//手機驗證錯誤
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg", "手机验证码错误，您还有"+(tel_limit-1)+"次机会") ;
					return jsonObject.toString();
				}else{
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
				}
			}
		}
		
		
		if(fuser.getFgoogleBind()){
			if(google_limit<=0){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "GOOGLE验证码错误，请稍候再试") ;
				return jsonObject.toString();
			}else{
				boolean googleAuth = GoogleAuth.auth(Long.parseLong(totpCode),fuser.getFgoogleAuthenticator()) ;
				
				if(!googleAuth){
					//谷歌驗證失敗
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg", "GOOGLE验证码错误，您还有"+(google_limit-1)+"次机会") ;
					return jsonObject.toString();
				}else{
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
				}
			}
			
		}
		
		//成功
		try {
			FbankinfoWithdraw fbankinfoWithdraw = new FbankinfoWithdraw();
			fbankinfoWithdraw.setFbankNumber(account) ;
			fbankinfoWithdraw.setFbankType(0) ;
			fbankinfoWithdraw.setFcreateTime(Utils.getTimestamp()) ;
			fbankinfoWithdraw.setFname("支付宝帐号") ;
			fbankinfoWithdraw.setFstatus(BankInfoStatusEnum.NORMAL_VALUE) ;
			fbankinfoWithdraw.setFaddress(payeeAddr);
			fbankinfoWithdraw.setFothers(null);
			fbankinfoWithdraw.setFuser(fuser);
			this.frontUserService.updateBankInfoWithdraw(fbankinfoWithdraw) ;
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", "操作成功") ;
			this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.CNY_ACCOUNT_WITHDRAW);
		} catch (Exception e) {
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "网络异常") ;
		}
		
		return jsonObject.toString() ;
	}
	*/
	
	/**
	 * 增加提现银行 
	 * */
	@ResponseBody
	@RequestMapping("/user/updateOutAddress")
	public String updateOutAddress(
			HttpServletRequest request,
			@RequestParam(required=true)String account,
			@RequestParam(required=false,defaultValue="0")String phoneCode,
			@RequestParam(required=false,defaultValue="0")String totpCode,
			@RequestParam(required=true)int openBankType,
			@RequestParam(required=true)String address,
			@RequestParam(required=true)String prov,
			@RequestParam(required=true)String city,
			@RequestParam(required=true,defaultValue="0")String dist,
			@RequestParam(required=true)String payeeAddr,//开户姓名,
			@RequestParam(required=false) String phone //手机号
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		if(fuser.isFisTelephoneBind() && StringUtils.isEmpty(phone)){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "请输入手机号") ;
			jsonObject.accumulate(MSG, i18nMsg("please_fill_phone"));
			return jsonObject.toString();
		}


		address = HtmlUtils.htmlEscape(address);
		account = HtmlUtils.htmlEscape(account);
		phoneCode = HtmlUtils.htmlEscape(phoneCode);
		totpCode = HtmlUtils.htmlEscape(totpCode);
		prov = HtmlUtils.htmlEscape(prov);
		city = HtmlUtils.htmlEscape(city);
		dist = HtmlUtils.htmlEscape(dist);
		payeeAddr = fuser.getFrealName();
		
		String last = prov+city;
		if(!dist.equals("0")){
			last = last+dist;
		}
		
		if(account.length() < 10){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "银行帐号不合法") ;
			jsonObject.accumulate(MSG, i18nMsg("bank_account_illegal"));
			return jsonObject.toString();
		}
		
		if(address.length() > 300){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "详细地址太长") ;
			jsonObject.accumulate(MSG, i18nMsg("detailed_address_too_long"));
			return jsonObject.toString();
		}
		
		if(last.length() > 50){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "非法操作") ;
			jsonObject.accumulate(MSG, i18nMsg("s_illegal_operation"));
			return jsonObject.toString();
		}
		
//		if(fuser.getFrealName().equals(payeeAddr)){
//			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "银行卡账号名必须与您的实名认证姓名一致") ;
//			return jsonObject.toString();
//		}
		
		String bankName = BankTypeEnum.getEnumString(openBankType);
		if(bankName == null || bankName.trim().length() ==0){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "开户银行有误") ;
			jsonObject.accumulate(MSG, i18nMsg("open_bank_error"));
			return jsonObject.toString();
		}
		
		//校验银行卡
		try {
			String ret = BankUtil.validate(account) ;
			JSONObject retj = JSONObject.fromObject(ret) ;
			boolean flag = false ;
			if(retj.getInt("error_code")!=0){
				flag = true ;
//				jsonObject.accumulate("msg", "\n" + "银行卡号错误，请联系客服") ;
				jsonObject.accumulate(MSG, i18nMsg("account_error"));
			}else{
				String retBank = retj.getJSONObject("result").getString("bank") ;
				if(retBank.indexOf(bankName)==-1 && openBankType!=BankTypeEnum.QT){
//					jsonObject.accumulate("msg", "银行卡号码或者开户行不匹配") ;
					jsonObject.accumulate(MSG, i18nMsg("account_error_desc1"));
					flag = true ;
				}
			}
			if(flag == true ){
				jsonObject.accumulate("code", -1) ;

				return jsonObject.toString();
			}
		} catch (Exception e1) {
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "银行卡号码或者开户行不匹配") ;
			jsonObject.accumulate(MSG, i18nMsg("account_error_desc1"));
			return jsonObject.toString();
		}
		
		int count = this.utilsService.count(" where fuser.fid="+fuser.getFid()+" and fbankType="+openBankType+" and fbankNumber='"+account+"' and fstatus="+BankInfoWithdrawStatusEnum.NORMAL_VALUE+" ", FbankinfoWithdraw.class) ;
		if(count>0){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "银行卡号码已经存在") ;
			jsonObject.accumulate(MSG, i18nMsg("bank_card_exists"));
			return jsonObject.toString();
		}

		count = this.utilsService.count(" where fuser.fid="+fuser.getFid()+" and fstatus="+BankInfoWithdrawStatusEnum.NORMAL_VALUE+" ", FbankinfoWithdraw.class) ;
		if(count>4){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate(MSG, "绑定银行卡数量已超过5张限制");
			return jsonObject.toString();
		}
		
		String ip = getIpAddr(request) ;
//		int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
		int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
		

		if (tel_limit <= 0) {
			jsonObject.accumulate("code", -1);
//			jsonObject.accumulate("msg", "此ip操作频繁，请2小时后再试!");
			jsonObject.accumulate(MSG, i18nMsg("ip_disable"));
			return jsonObject.toString();
		} else {
			String areaCode = "86";
			if(fuser.isFisTelephoneBind()){
				phone = fuser.getFtelephone();
				areaCode = fuser.getFareaCode();
			}
			if (!validateMessageCode(fuser, areaCode, phone, MessageTypeEnum.CNY_ACCOUNT_WITHDRAW, phoneCode)) {
				//手機驗證錯誤
				this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE);
				jsonObject.accumulate("code", -1);
//				jsonObject.accumulate("msg", "手机验证码错误，您还有" + tel_limit + "次机会");
				jsonObject.accumulate(MSG, i18nMsg("phone_code_error", new Object[]{tel_limit}));
				return jsonObject.toString();
			} else {
				this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE);
			}
		}

		
		
//		if(fuser.getFgoogleBind()){
//			if(google_limit<=0){
//				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg", "此ip操作频繁，请2小时后再试!") ;
//				return jsonObject.toString();
//			}else{
//				boolean googleAuth = GoogleAuth.auth(Long.parseLong(totpCode),fuser.getFgoogleAuthenticator()) ;
//
//				if(!googleAuth){
//					//谷歌驗證失敗
//					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
//					jsonObject.accumulate("code", -1) ;
//					jsonObject.accumulate("msg", "GOOGLE验证码错误，您还有"+google_limit+"次机会") ;
//					return jsonObject.toString();
//				}else{
//					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
//				}
//			}
//
//		}
		
		//成功
		try {
			FbankinfoWithdraw fbankinfoWithdraw = new FbankinfoWithdraw();
			fbankinfoWithdraw.setFbankNumber(account) ;
			fbankinfoWithdraw.setFbankType(openBankType) ;
			fbankinfoWithdraw.setFcreateTime(Utils.getTimestamp()) ;
			fbankinfoWithdraw.setFname(bankName) ;
			fbankinfoWithdraw.setFstatus(BankInfoStatusEnum.NORMAL_VALUE) ;
			fbankinfoWithdraw.setFaddress(last);
			fbankinfoWithdraw.setFothers(address);
			fbankinfoWithdraw.setFuser(fuser);
			this.frontUserService.updateBankInfoWithdraw(fbankinfoWithdraw) ;
            //添加银行卡===WEB首次送积分
			this.integralService.addUserIntegralFirst(IntegralTypeEnum.INFOBANK_FIRST,fuser.getFid(),0);
			if(!fuser.isFisTelephoneBind()){
				fuser = this.frontUserService.findById(fuser.getFid());
				fuser.setFareaCode("86") ;
				fuser.setFtelephone(phone) ;
				fuser.setFisTelephoneBind(true) ;
				fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
				this.frontUserService.updateFUser(fuser, getSession(request),LogTypeEnum.User_BIND_PHONE,ip) ;
				this.integralService.addUserIntegralFirst(IntegralTypeEnum.PHONE_FIRST,fuser.getFid(),0);
			}
			jsonObject.accumulate("code", 0) ;
//			jsonObject.accumulate("msg", "操作成功") ;
			jsonObject.accumulate(MSG, i18nMsg("s_operation_success"));

		} catch (Exception e) {
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "网络异常") ;
			jsonObject.accumulate(MSG, i18nMsg("s_network_anomaly"));
		}finally{
			this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.CNY_ACCOUNT_WITHDRAW);
		}
		
		return jsonObject.toString() ;
	}
	

	@ResponseBody
	@RequestMapping("/user/deleteBankAddress")
	public String deleteBankAddress(
			HttpServletRequest request,
			@RequestParam(required=true)int fid
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		FbankinfoWithdraw fbankinfoWithdraw = this.frontUserService.findByIdWithBankInfos(fid);
		if(fbankinfoWithdraw == null){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "记录不存在") ;
			jsonObject.accumulate(MSG, i18nMsg("record_not_exists"));
			return jsonObject.toString() ;
		}
		if(fuser.getFid() != fbankinfoWithdraw.getFuser().getFid()){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "非法操作") ;
			jsonObject.accumulate(MSG, i18nMsg("s_illegal_operation"));
			return jsonObject.toString() ;
		}
		//成功
		try {
			this.frontUserService.updateDelBankInfoWithdraw(fbankinfoWithdraw);
			jsonObject.accumulate("code", 0) ;
//			jsonObject.accumulate("msg", "操作成功") ;
			jsonObject.accumulate(MSG, i18nMsg("s_operation_success"));
		} catch (Exception e) {
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "网络异常") ;
			jsonObject.accumulate(MSG, i18nMsg("s_network_anomaly"));
		}
		
		return jsonObject.toString() ;
	}
	
	@ResponseBody
	@RequestMapping("/user/detelCoinAddress")
	public String detelCoinAddress(
			HttpServletRequest request,
			@RequestParam(required=true)int fid
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		FvirtualaddressWithdraw virtualaddressWithdraw = this.frontVirtualCoinService.findFvirtualaddressWithdraw(fid);
		if(virtualaddressWithdraw == null){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "记录不存在") ;
			jsonObject.accumulate(MSG, i18nMsg("record_not_exists"));
			return jsonObject.toString() ;
		}
		if(fuser.getFid() != virtualaddressWithdraw.getFuser().getFid()){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "非法操作") ;
			jsonObject.accumulate(MSG, i18nMsg("s_illegal_operation"));
			return jsonObject.toString() ;
		}
		//成功
		try {
			this.frontVirtualCoinService.updateDelFvirtualaddressWithdraw(virtualaddressWithdraw);
			jsonObject.accumulate("code", 0) ;
//			jsonObject.accumulate("msg", "操作成功") ;
			jsonObject.accumulate(MSG, i18nMsg("s_operation_success"));
		} catch (Exception e) {
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "网络异常") ;
			jsonObject.accumulate(MSG, i18nMsg("s_network_anomaly"));
		}
		
		return jsonObject.toString() ;
	}

	@ResponseBody
	@RequestMapping("/user/modifyPwd")
	public String modifyPwd(
			HttpServletRequest request,
			@RequestParam(required=true) String newPwd,
			@RequestParam(required=false,defaultValue="0") String originPwd,
			@RequestParam(required=false,defaultValue="0")String phoneCode,
			@RequestParam(required=false,defaultValue="0")int pwdType,
			@RequestParam(required=true) String reNewPwd,
			@RequestParam(required=false,defaultValue="0")String totpCode
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		if(!newPwd.equals(reNewPwd)){
			jsonObject.accumulate("code", -3) ;
//			jsonObject.accumulate("msg", "两次输入密码不一样") ;
			jsonObject.accumulate(MSG, i18nMsg("two_pwd_diff"));
			return jsonObject.toString() ;//两次输入密码不一样
		}
		
		if(!fuser.isFisTelephoneBind() && !fuser.getFgoogleBind()){
			jsonObject.accumulate("resultCode", -13) ;
//			jsonObject.accumulate("msg", "需要绑定绑定谷歌或者手机号码才能修改密码") ;
			jsonObject.accumulate(MSG, i18nMsg("bind_google_or_phone"));
			return jsonObject.toString() ;//需要绑定绑定谷歌或者电话才能修改密码
		}
		
		if(pwdType==0){
			//修改登陆密码
			if(!fuser.getFloginPassword().equals(Utils.MD5(originPwd,fuser.getSalt()))){
				jsonObject.accumulate("code", -5) ;
//				jsonObject.accumulate("msg", "原始登陆密码错误") ;
				jsonObject.accumulate(MSG, i18nMsg("login_pwd_error"));
				return jsonObject.toString() ;//原始密码错误
			}
			
		}else{
			//修改交易密码
			if(fuser.getFtradePassword()!=null && fuser.getFtradePassword().trim().length() >0){
				if(!fuser.getFtradePassword().equals(Utils.MD5(originPwd,fuser.getSalt()))){
					jsonObject.accumulate("code", -5) ;
//					jsonObject.accumulate("msg", "原始交易密码错误") ;
					jsonObject.accumulate(MSG, i18nMsg("old_trade_pwd_error"));
					return jsonObject.toString() ;//原始密码错误
				}
			}
		}

		if(validateLoginPassword(reNewPwd)){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "新密码格式不正确") ;
			jsonObject.accumulate(MSG, i18nMsg("new_pwd_not_format"));
			return jsonObject.toString() ;
		}
		
		if(pwdType==0){
			//修改交易密码
			if(fuser.getFloginPassword().equals(Utils.MD5(newPwd,fuser.getSalt()))){
				jsonObject.accumulate("code", -10) ;
//				jsonObject.accumulate("msg", "新的登陆密码与原始密码相同，修改失败") ;
				jsonObject.accumulate(MSG, i18nMsg("new_logpwd_update_fail"));
				return jsonObject.toString() ;
			}
		}else{
			//修改交易密码
			if(fuser.getFtradePassword()!=null && fuser.getFtradePassword().trim().length() >0
					&&fuser.getFtradePassword().equals(Utils.MD5(newPwd,fuser.getSalt()))){
				jsonObject.accumulate("code", -10) ;
//				jsonObject.accumulate("msg", "新的交易密码与原始密码相同，修改失败") ;
				jsonObject.accumulate(MSG, i18nMsg("new_tradepwd_update_fail"));
				return jsonObject.toString() ;
			}
		}
		
		String ip = getIpAddr(request) ;
		if(fuser.isFisTelephoneBind()){
			int mobil_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
			if(mobil_limit<=0){
				jsonObject.accumulate("code", -7) ;
//				jsonObject.accumulate("msg", "此ip操作频繁，请2小时后再试!") ;
				jsonObject.accumulate(MSG, i18nMsg("ip_disable"));
				return jsonObject.toString() ;
			}else{
				boolean mobilValidate = false ;
				if(pwdType==0){
					mobilValidate = validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.CHANGE_LOGINPWD, phoneCode) ;
				}else{
					mobilValidate = validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.CHANGE_TRADEPWD, phoneCode) ;
				}
				if(!mobilValidate){
					jsonObject.accumulate("code", -7) ;
//					jsonObject.accumulate("msg", "手机验证码有误，您还有"+mobil_limit+"次机会") ;
					jsonObject.accumulate(MSG, i18nMsg("phone_vercode_error", new Object[]{mobil_limit}));
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
					return jsonObject.toString() ;
				}else{
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
				}
			}
		}
		
		if(fuser.getFgoogleBind()){
			int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
			if(google_limit<=0){
				jsonObject.accumulate("code", -6) ;
//				jsonObject.accumulate("msg", "此ip操作频繁，请2小时后再试!") ;
				jsonObject.accumulate(MSG, i18nMsg("ip_disable"));
				return jsonObject.toString() ;
			}else{
				if(!GoogleAuth.auth(Long.parseLong(totpCode), fuser.getFgoogleAuthenticator())){
					jsonObject.accumulate("code", -6) ;
//					jsonObject.accumulate("msg", "GOOGLE验证码有误，您还有"+google_limit+"次机会") ;
					jsonObject.accumulate(MSG, i18nMsg("google_code_error", new Object[]{google_limit}));
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
					return jsonObject.toString() ;
				}else{
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
				}
			}
		}
		
		try {
			if(pwdType==0){
				//修改登陆密码
				fuser.setFloginPassword(Utils.MD5(newPwd,fuser.getSalt())) ;
				this.frontUserService.updateFUser(fuser, getSession(request),LogTypeEnum.User_UPDATE_LOGIN_PWD,ip) ;
			}else{

				int logType=0;
				if(fuser.getFtradePassword()!=null && fuser.getFtradePassword().trim().length() >0){
					logType = LogTypeEnum.User_UPDATE_TRADE_PWD;
				}else{
					logType = LogTypeEnum.User_SET_TRADE_PWD;
				}
				//修改交易密码
				fuser.setFtradePassword(Utils.MD5(newPwd,fuser.getSalt())) ;
				this.frontUserService.updateFUser(fuser, getSession(request),logType,ip) ;

				//设置交易密码===WEB首次送积分
				this.integralService.addUserIntegralFirst(IntegralTypeEnum.TRADEPWD_FIRST,fuser.getFid(),0);
			}
		} catch (Exception e) {
			jsonObject.accumulate("code", -3) ;
//			jsonObject.accumulate("msg", "网络异常") ;
			jsonObject.accumulate(MSG, i18nMsg("s_network_anomaly"));
			return jsonObject.toString() ;
		}finally{
			this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.CHANGE_LOGINPWD);
			this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.CHANGE_TRADEPWD);
		}
		
		jsonObject.accumulate("code", 0) ;
//		jsonObject.accumulate("msg", "操作成功") ;
		jsonObject.accumulate(MSG, i18nMsg("s_operation_success"));
		return jsonObject.toString() ;
	}



	//通过手机找回交易密码
	@ResponseBody
	@RequestMapping(value = "/user/resetTradePassword")
	public String resetTradePassword(
			HttpServletRequest request,
			@RequestParam String phone,
			@RequestParam String msgcode,
			@RequestParam String idcardno,
			@RequestParam String tradepwd
	) throws Exception{
		String areaCode = "86" ;
		idcardno = idcardno.toLowerCase();
		JSONObject jsonObject = new JSONObject() ;

		if(!phone.matches(Constant.PhoneReg)){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "手机格式不正确") ;
			jsonObject.accumulate(MSG, i18nMsg("phone_not_format1"));
			return jsonObject.toString() ;
		}
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;

		//短信验证码
		boolean validate = validateMessageCode(fuser, areaCode, phone, MessageTypeEnum.FIND_TRADE_PWD, msgcode) ;
		if(validate == false ){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "短信验证码错误") ;
			jsonObject.accumulate(MSG, i18nMsg("sms_code_error"));
			return jsonObject.toString() ;
		}

		if(fuser.getFhasRealValidate() == true ){
			if(idcardno.trim().equals(fuser.getFidentityNo()) == false ){
				jsonObject.accumulate("code", -1) ;
//				jsonObject.accumulate("msg", "证件号码错误") ;
				jsonObject.accumulate(MSG, i18nMsg("id_card_error"));
				return jsonObject.toString() ;
			}
		}

		if(org.apache.commons.lang.StringUtils.isEmpty(fuser.getFtradePassword())){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "交易密码尚未设置") ;
			jsonObject.accumulate(MSG, i18nMsg("trade_pwd_not_set"));
			return jsonObject.toString() ;
		}

		try{
			String ip = getIpAddr(request) ;
			fuser.setFtradePassword(Utils.MD5(tradepwd,fuser.getSalt())) ;
			this.frontUserService.updateFUser(fuser, null,LogTypeEnum.FIND_TRADE_PWD,ip) ;
		}catch (Exception e){
			jsonObject.accumulate("code", -3) ;
//			jsonObject.accumulate("msg", "网络异常") ;
			jsonObject.accumulate(MSG, i18nMsg("s_network_anomaly"));
			return jsonObject.toString() ;
		}

		jsonObject.accumulate("code", 0) ;
//		jsonObject.accumulate("msg", "交易密码重置成功") ;
		jsonObject.accumulate(MSG, i18nMsg("trade_pwd_reset"));
		return jsonObject.toString() ;


	}



	@ResponseBody
	@RequestMapping("/user/modifyWithdrawBtcAddr")
	public String modifyWithdrawBtcAddr(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")String phoneCode,
			@RequestParam(required=false,defaultValue="0")String totpCode,
			@RequestParam(required=true)int symbol,
			@RequestParam(required=true)String withdrawAddr,
			@RequestParam(required=true)String withdrawBtcPass,
			@RequestParam(required=false,defaultValue="REMARK")String withdrawRemark
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		withdrawAddr = HtmlUtils.htmlEscape(withdrawAddr.trim());
		withdrawRemark = HtmlUtils.htmlEscape(withdrawRemark.trim());
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		if(!fuser.getFgoogleBind() && !fuser.isFisTelephoneBind()){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg","请先绑定GOOGLE验证或手机号码") ;
			jsonObject.accumulate(MSG, i18nMsg("please_bind"));
			return jsonObject.toString() ;
		}
		
		if(fuser.getFtradePassword() == null || fuser.getFtradePassword().trim().length() ==0){
			jsonObject.accumulate("code", -4) ;
//			jsonObject.accumulate("msg","请先设置交易密码") ;
			jsonObject.accumulate(MSG, i18nMsg("please_set_trade_pwd"));
			return jsonObject.toString() ;
		}
		
		if(!fuser.getFtradePassword().equals(Utils.MD5(withdrawBtcPass,fuser.getSalt()))){
			jsonObject.accumulate("code", -4) ;
//			jsonObject.accumulate("msg","交易密码不正确") ;
			jsonObject.accumulate(MSG, i18nMsg("trade_pwd_error2"));
			return jsonObject.toString() ;
		}
		
		if(withdrawRemark.length() >100){
			jsonObject.accumulate("code", -4) ;
//			jsonObject.accumulate("msg","备注超出长度") ;
			jsonObject.accumulate(MSG, i18nMsg("remark_tool_long"));
			return jsonObject.toString() ;
		}
		
		if(withdrawAddr.length() < 25 || withdrawAddr.length() > 50){
			jsonObject.accumulate("code", -4) ;
//			jsonObject.accumulate("msg","提现地址格式有误") ;
			jsonObject.accumulate(MSG, i18nMsg("with_address_error"));
			return jsonObject.toString() ;
		}
		
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		if(fvirtualcointype==null 
				|| fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal
				|| !fvirtualcointype.isFIsWithDraw()){
			jsonObject.accumulate("code", -4) ;
//			jsonObject.accumulate("msg","该币种不存在") ;
			jsonObject.accumulate(MSG, i18nMsg("cncy_not_exists"));
			return jsonObject.toString() ;
		}

		//判断同一同用户下提现地址是否存在，如果存在，不让添加
		if(frontVirtualCoinService.isExistsFaddress(withdrawAddr, fuser.getFid() )){
			jsonObject.accumulate("code", -4) ;
			jsonObject.accumulate("msg","不能重复添加相同的提现地址") ;
			return jsonObject.toString();
		}

		if (!CoinType.ANS.equals(fvirtualcointype.getfShortName().toUpperCase())){
			// 钱包校验地址是否合法
			BTCMessage btcMsg = new BTCMessage();
			btcMsg.setACCESS_KEY(fvirtualcointype.getFaccess_key());
			btcMsg.setSECRET_KEY(fvirtualcointype.getFsecrt_key());
			btcMsg.setIP(fvirtualcointype.getFip());
			btcMsg.setPORT(fvirtualcointype.getFport());
			btcMsg.setCOIN_TYPE(fvirtualcointype.getfShortName().toUpperCase());
			try{
				Boolean flag;
				if (fvirtualcointype.isFisEth()){
					ETHUtils ethUtils = new ETHUtils(btcMsg);
					flag = ethUtils.validateaddress(withdrawAddr);
					// eth
				} else {
					// btc ltc zec
					BTCUtils btcUtils = new BTCUtils(btcMsg);
					flag = btcUtils.validateaddress(withdrawAddr);
				}
				if (!flag){
					jsonObject.accumulate("code", -4) ;
					jsonObject.accumulate(MSG, "提现地址非法");
					return jsonObject.toString() ;
				}
			} catch (Exception ex){
				LOG.e(this,ex.getMessage(),ex);
				jsonObject.accumulate("code", -4) ;
				jsonObject.accumulate(MSG, "交易提现地址失败,请联系客服");
				return jsonObject.toString() ;
			}
		}


		List<Fvirtualaddress> list = this.frontVirtualCoinService.findFvirtualaddress(fvirtualcointype,withdrawAddr);
		if (list != null && list.size() > 0){
			jsonObject.accumulate("code" , -4) ;
			jsonObject.accumulate("msg", "提现地址不能是51平台的地址") ;
			return jsonObject.toString() ;
		}

		String ip = getIpAddr(request) ;		
		if(fuser.isFisTelephoneBind()){
			int mobil_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
			if(mobil_limit<=0){
				jsonObject.accumulate("code", -3) ;
//				jsonObject.accumulate("msg","此ip操作频繁，请2小时后再试!") ;
				jsonObject.accumulate(MSG, i18nMsg("ip_disable"));
				return jsonObject.toString() ;
			}else if(!validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.VIRTUAL_WITHDRAW_ACCOUNT, phoneCode)){
				jsonObject.accumulate("code", -3) ;
//				jsonObject.accumulate("msg","手机验证码不正确,您还有"+mobil_limit+"次机会") ;
				jsonObject.accumulate(MSG, i18nMsg("phone_code_error2", new Object[]{mobil_limit}));
				this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
				return jsonObject.toString() ;
			}else if(mobil_limit<new Constant().ErrorCountLimit){
				this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
			}
		}
		
//		if(fuser.getFgoogleBind()){
//			int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
//			if(google_limit<=0){
//				jsonObject.accumulate("code", -2) ;
////				jsonObject.accumulate("msg","此ip操作频繁，请2小时后再试!") ;
//				jsonObject.accumulate(MSG, i18nMsg("ip_disable"));
//				return jsonObject.toString() ;
//			}else if(!GoogleAuth.auth(Long.parseLong(totpCode.trim()), fuser.getFgoogleAuthenticator())){
//				jsonObject.accumulate("code", -2) ;
////				jsonObject.accumulate("msg","GOOGLE验证码不正确,您还有"+google_limit+"次机会") ;
//				jsonObject.accumulate(MSG, i18nMsg("google_code_error2", new Object[]{google_limit}));
//				this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
//				return jsonObject.toString() ;
//			}else if(google_limit<new Constant().ErrorCountLimit){
//				this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
//			}
//		}
		
		FvirtualaddressWithdraw fvirtualaddressWithdraw = new FvirtualaddressWithdraw();
		fvirtualaddressWithdraw.setFadderess(withdrawAddr) ;
		fvirtualaddressWithdraw.setFcreateTime(Utils.getTimestamp());
		fvirtualaddressWithdraw.setFremark(withdrawRemark);
		fvirtualaddressWithdraw.setFuser(fuser);
		fvirtualaddressWithdraw.setFvirtualcointype(fvirtualcointype);
		try {
			this.frontVirtualCoinService.updateFvirtualaddressWithdraw(fvirtualaddressWithdraw) ;
			jsonObject.accumulate("code", 0) ;
//			jsonObject.accumulate("msg","操作成功") ;
			jsonObject.accumulate(MSG, i18nMsg("s_operation_success"));
		} catch (Exception e) {
			jsonObject.accumulate("code", -4) ;
//			jsonObject.accumulate("msg","网络异常") ;
			jsonObject.accumulate(MSG, i18nMsg("s_network_anomaly"));
		}finally{
			this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.VIRTUAL_WITHDRAW_ACCOUNT);
		}
 		
		return jsonObject.toString() ;
	}
	

	@ResponseBody
	@RequestMapping(value="/user/validateIdentity",produces={JsonEncode})
	public String validateIdentity(
			HttpServletRequest request,
			@RequestParam(required=true)String identityNo,
			@RequestParam(required=false,defaultValue="0")int identityType,
			@RequestParam(required=true)String realName
			) throws Exception {
		LOGGER.info(CLASS_NAME + " validateIdentity,identityNo:{},realName:{}", identityNo, realName);
		JSONObject js = new JSONObject();
//		realName = HtmlUtils.htmlEscape(realName);   //新疆人，姓名中带有中文点号，不能进行html转义
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		identityNo = identityNo.toLowerCase();
		 String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",  
	                "3", "2" };  
	        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",  
	                "9", "10", "5", "8", "4", "2" };  
			JSONObject jsonObject = new JSONObject();
			if (identityNo.trim().length() != 15 && identityNo.trim().length() != 18) {
				jsonObject.accumulate("code", 1);
//				jsonObject.accumulate("msg", "身份证号码长度应该为15位或18位!");
				jsonObject.accumulate(MSG, i18nMsg("id_card_lengh_error"));
				LOGGER.info(CLASS_NAME + " validateIdentity,身份证号码长度应该为15位或18位");
				return jsonObject.toString();
			}
			
			String Ai = "";
	        if (identityNo.length() == 18) {  
	            Ai = identityNo.substring(0, 17);  
	        } else if (identityNo.length() == 15) {  
	            Ai = identityNo.substring(0, 6) + "19" + identityNo.substring(6, 15);  
	        }
			LOGGER.info(CLASS_NAME + " validateIdentity,Ai:{}", Ai);
	        if (Utils.isNumeric(Ai) == false) {  
	            jsonObject.accumulate("code", 1);
//				jsonObject.accumulate("msg", "身份证号码有误!");
				jsonObject.accumulate(MSG, i18nMsg("id_card_error2"));
				return jsonObject.toString();
	        }
	        // ================ 出生年月是否有效 ================  
	        String strYear = Ai.substring(6, 10);// 年份  
	        String strMonth = Ai.substring(10, 12);// 月份  
	        String strDay = Ai.substring(12, 14);// 月份  
	        if (Utils.isDate(strYear + "-" + strMonth + "-" + strDay) == false && Constant.OPEN_REAL_ID) {
	        	jsonObject.accumulate("code", 1);
//				jsonObject.accumulate("msg", "身份证号码有误!");
				jsonObject.accumulate(MSG, i18nMsg("id_card_error2"));
				return jsonObject.toString();
	        }  
	        GregorianCalendar gc = new GregorianCalendar();  
	        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
	        if(Constant.OPEN_REAL_ID) {
				try {
					if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
							|| (gc.getTime().getTime() - s.parse(
							strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
						jsonObject.accumulate("code", 1);
//					jsonObject.accumulate("msg", "身份证号码有误!");
						jsonObject.accumulate(MSG, i18nMsg("id_card_error2"));
						return jsonObject.toString();
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
				if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
					jsonObject.accumulate("code", 1);
//				jsonObject.accumulate("msg", "身份证号码有误!");
					jsonObject.accumulate(MSG, i18nMsg("id_card_error2"));
					return jsonObject.toString();
				}
				if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
					jsonObject.accumulate("code", 1);
//				jsonObject.accumulate("msg", "身份证号码有误!");
					jsonObject.accumulate(MSG, i18nMsg("id_card_error2"));
					return jsonObject.toString();
				}
			}
	        // =====================(end)=====================
	  
	        // ================ 地区码时候有效 ================  
	        Hashtable h = Utils.getAreaCode();
			if (Constant.OPEN_REAL_ID) {
				if (h.get(Ai.substring(0, 2)) == null) {
					jsonObject.accumulate("code", 1);
	//				jsonObject.accumulate("msg", "身份证号码有误!");
					jsonObject.accumulate(MSG, i18nMsg("id_card_error2"));
					return jsonObject.toString();
				}
			}
		// ==============================================
	  
	        // ================ 判断最后一位的值 ================  
	        int TotalmulAiWi = 0;
	        for (int i = 0; i < 17; i++) {
	            TotalmulAiWi = TotalmulAiWi
	                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
	                    * Integer.parseInt(Wi[i]);
	        }
	        int modValue = TotalmulAiWi % 11;
	        String strVerifyCode = ValCodeArr[modValue];
	        Ai = Ai + strVerifyCode;

	        if(Constant.OPEN_REAL_ID) {  //开启实名认证
				if (identityNo.length() == 18) {
					if (Ai.equals(identityNo) == false) {
						jsonObject.accumulate("code", 1);
//					jsonObject.accumulate("msg", "身份证号码有误!");
						jsonObject.accumulate(MSG, i18nMsg("id_card_error2"));
						return jsonObject.toString();
					}
				} else {
					return "";
				}
			}

	        
			if (realName.trim().length() > 50) {
				jsonObject.accumulate("code", 1);
//				jsonObject.accumulate("msg", "真实姓名不合法!");
				jsonObject.accumulate(MSG, i18nMsg("real_name_illegal"));
				return jsonObject.toString();
			}
			
			String sql = "where fidentityNo='"+identityNo+"'";
			int count = this.adminService.getAllCount("Fuser", sql);
			LOGGER.info(CLASS_NAME + " validateIdentity,从数据库中查询身份证号，返回结果集count:{}", count);
			if(count >0){
				jsonObject.accumulate("code", 1);
//				jsonObject.accumulate("msg", "身份证号码已存在!");
				jsonObject.accumulate(MSG, i18nMsg("id_card_exists"));
				return jsonObject.toString();
			}

		boolean isTrue = false;
		if(Constant.OPEN_REAL_ID) {  //开启实名认证
			IDCardVerifyUtil verifyUtil = new IDCardVerifyUtil();
			isTrue = verifyUtil.isRealPerson(realName, identityNo);
		}else{
			isTrue = true;  //测试环境关闭实名认证，则直接默认为通过
		}

		LOGGER.info(CLASS_NAME + " validateIdentity,调verifyUtil.isRealPerson进行实名认证，返回结果isTrue:{}", isTrue);
		if(!isTrue){
			jsonObject.accumulate("code", 1);
//			jsonObject.accumulate("msg", "您的姓名与身份证号有误，请核对!");
			jsonObject.accumulate(MSG, i18nMsg("name_id_error"));
			return jsonObject.toString();
		}
		LOGGER.info(CLASS_NAME + " validateIdentity,实名认证通过");
		Fscore fscore = fuser.getFscore();
		Fuser fintrolUser = null;
		Fintrolinfo introlInfo = null;
		Fvirtualwallet fvirtualwallet = null;
		LOGGER.info(CLASS_NAME + " validateIdentity,从系统参数表查询实名认证配置auditSendCoin  begin");
		String[] auditSendCoin = this.systemArgsService.getValue("auditSendCoin").split("#");
		LOGGER.info(CLASS_NAME + " validateIdentity,从系统参数表查询实名认证配置auditSendCoin:{}", new Gson().toJson(auditSendCoin));
		int coinID = Integer.parseInt(auditSendCoin[0]);
		double coinQty = Double.valueOf(auditSendCoin[1]);
		if(!fscore.isFissend() && fuser.getfIntroUser_id() != null){
			fintrolUser = this.frontUserService.findById(fuser.getfIntroUser_id().getFid());
			fintrolUser.setfInvalidateIntroCount(fintrolUser.getfInvalidateIntroCount()+1);
			fscore.setFissend(true);
		}
		LOGGER.info(CLASS_NAME + " validateIdentity,coinQty:{}",coinQty);
		if(coinQty >0){
			fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), coinID);
			fvirtualwallet.setFtotal(Utils.add(fvirtualwallet.getFtotal(), coinQty));  ////2017-04-07 转换计算方式
			introlInfo = new Fintrolinfo();
			introlInfo.setFcreatetime(Utils.getTimestamp());
			introlInfo.setFiscny(false);
			introlInfo.setFqty(coinQty);
			introlInfo.setFuser(fuser);
			introlInfo.setFname(fvirtualwallet.getFvirtualcointype().getFname());
//			introlInfo.setFtitle("实名认证成功，奖励"+fvirtualwallet.getFvirtualcointype().getFname()+coinQty+"个！");
			introlInfo.setFtitle(i18nMsg("real_reward", new Object[]{fvirtualwallet.getFvirtualcointype().getFname(), coinQty}));
		}
		fuser.setFidentityType(0) ;
		fuser.setFidentityNo(identityNo) ;
		fuser.setFrealName(realName) ;
		fuser.setFpostRealValidate(true) ;
		fuser.setFpostRealValidateTime(Utils.getTimestamp()) ;
		fuser.setFhasRealValidate(true);
		fuser.setFhasRealValidateTime(Utils.getTimestamp());
		fuser.setFisValid(true);
		fuser.setKyclevel(ValidateKycLevelEnum.IDENTITY_VALIDATE.getKey());
		try {
			String ip = getIpAddr(request) ;
			LOGGER.info(CLASS_NAME + " validateIdentity,修改用户信息,实名认证成功");
			this.frontUserService.updateFUser(fuser, getSession(request), LogTypeEnum.User_CERT,ip) ;
			this.userService.updateObj(fuser, fscore, fintrolUser, fvirtualwallet, introlInfo);
			LOGGER.info(CLASS_NAME + " validateIdentity,修改用户钱包信息成功，增加了奖励金额");

			//实名认证===WEB首次送积分
			this.integralService.addUserIntegralFirst(IntegralTypeEnum.REALNAME_FIRST,fuser.getFid(),0);

			if(fintrolUser != null){
				//推荐人送积分
				this.integralService.addUserIntegral(IntegralTypeEnum.INVITE_FRIENDS,fintrolUser.getFid(),0,fuser.getFid());
			}


			this.SetSession(fuser,request) ;
		} catch (Exception e) {
			js.accumulate("code", 1);
//			js.accumulate("msg", "证件号码已存在");
			js.accumulate(MSG, i18nMsg("id_card_exists2"));
			return js.toString();
		}
//		js.accumulate("msg", "证件验证成功");
		js.accumulate(MSG, i18nMsg("id_card_ver_success"));
		js.accumulate("code", 0);
		return js.toString();
	}


    /**
	 *  用户升级认证
	 * @param request
	 * @param fIdentityUrlOn
	 * @param fIdentityUrlOff
	 * @param code
	 * @return
     * @throws Exception
     */
	@ResponseBody
	@RequestMapping(value="/user/validateGoUp",produces={JsonEncode})
	public String validateGoUp(
			HttpServletRequest request,
			@RequestParam String  fIdentityUrlOn,
			@RequestParam String  fIdentityUrlOff,
			@RequestParam String  code
	) throws Exception {
		JSONObject js = new JSONObject();
		fIdentityUrlOn = HtmlUtils.htmlEscape(fIdentityUrlOn);
		fIdentityUrlOff = HtmlUtils.htmlEscape(fIdentityUrlOff);
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;

		JSONObject jsonObject = new JSONObject();

		if (StringUtils.isEmpty(fIdentityUrlOn) || StringUtils.isEmpty(fIdentityUrlOff)) {
			jsonObject.accumulate("code", -1);
//			jsonObject.accumulate("msg", "身份证正面和反面照片不能为空!");
			jsonObject.accumulate(MSG, i18nMsg("id_card_error3"));
			return jsonObject.toString();
		}

		String ip = getIpAddr(request) ;
		int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
		if(tel_limit<=0){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "此ip操作频繁，请2小时后再试!");
			jsonObject.accumulate(MSG, i18nMsg("ip_disable"));
			return jsonObject.toString() ;
		}


		if(!validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.GOUP_VALIDATE, code)){
			//手機驗證錯誤
			this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "手机验证码有误，您还有"+tel_limit+"次机会");
			jsonObject.accumulate(MSG, i18nMsg("phone_vercode_error", new Object[]{tel_limit}));
			return jsonObject.toString() ;
		}else{
			this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
		}


		fuser.setfIdentityUrlOn(fIdentityUrlOn);
		fuser.setfIdentityUrlOff(fIdentityUrlOff);
		fuser.setFstatus(UserStatusEnum.FREEZE_VALIDATE);
		fuser.setFgoUpValidateDate(Utils.getTimestamp());
		try {
			this.frontUserService.updateFUser(fuser, getSession(request), LogTypeEnum.User_GOUP_VALIDATE,ip) ;
			this.SetSession(fuser,request) ;
		} catch (Exception e) {
			js.accumulate("code", 1);
//			js.accumulate("msg", "认证失败");
			js.accumulate(MSG, i18nMsg("auth_fail"));
			return js.toString();
		}
//		js.accumulate("msg", "升级认证内容提交成功");
		js.accumulate(MSG, i18nMsg("content_submit"));
		js.accumulate("code", 0);
		return js.toString();
	}


    /**
	 * 删除银行卡地址
	 * @param request
	 * @param uid 银行卡id
	 * @return
	 * @throws Exception
     */
	@ResponseBody
	@RequestMapping("/user/deteleBankInfoWithdraw")
	public String detelBankInfo(
			HttpServletRequest request,
			@RequestParam int uid,
			@RequestParam String code
	) throws Exception{
		JSONObject jsonObject = new JSONObject() ;

		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		FbankinfoWithdraw fbankinfoWithdraw = this.frontUserService.findByIdWithBankInfos(uid);
		if(fbankinfoWithdraw == null){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "记录不存在") ;
			jsonObject.accumulate(MSG, i18nMsg("record_not_exists"));
			return jsonObject.toString() ;
		}
		if(fuser.getFid() != fbankinfoWithdraw.getFuser().getFid()){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "非法操作") ;
			jsonObject.accumulate(MSG, i18nMsg("s_illegal_operation"));
			return jsonObject.toString() ;
		}

		String ip = getIpAddr(request) ;
		int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
		if(tel_limit<=0){
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "此ip操作频繁，请2小时后再试!");
			jsonObject.accumulate(MSG, i18nMsg("ip_disable"));
			return jsonObject.toString() ;
		}


		if(!validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.BANK_DEL, code)){
			//手機驗證錯誤
			this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "手机验证码有误，您还有"+tel_limit+"次机会");
			jsonObject.accumulate(MSG, i18nMsg("phone_vercode_error", new Object[]{tel_limit}));
			return jsonObject.toString() ;
		}else{
			this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
		}
		//成功
		try {
			this.frontUserService.updateDelBankInfoWithdraw(fbankinfoWithdraw);
			jsonObject.accumulate("code", 0) ;
//			jsonObject.accumulate("msg", "操作成功") ;
			jsonObject.accumulate(MSG, i18nMsg("s_operation_success"));
		} catch (Exception e) {
			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "网络异常") ;
			jsonObject.accumulate(MSG, i18nMsg("s_network_anomaly"));
		}finally {
			this.validateMap.removeMessageMap(fuser.getFid()+"_"+ MessageTypeEnum.BANK_DEL);
		}

		return jsonObject.toString() ;
	}
    /**
	 * 上传认证升级图片
	 * @param file
	 * @return
     */
	@ResponseBody
	@RequestMapping(value="/user/uploadFile")
	public String uploadFile(HttpServletRequest request
			,@RequestParam MultipartFile file)throws Exception{
		JSONObject js = new JSONObject();
		String fpictureUrl = "";
		js.accumulate("code",1);
		if(file != null && !file.isEmpty()){
			InputStream inputStream = file.getInputStream() ;
			String fileRealName = file.getOriginalFilename() ;
			if(fileRealName != null && fileRealName.trim().length() >0){
				String[] nameSplit = fileRealName.split("\\.") ;
				String ext = nameSplit[nameSplit.length-1] ;
				if(ext!=null
						&& !ext.trim().toLowerCase().endsWith("jpg")
						&& !ext.trim().toLowerCase().endsWith("png")){
					js.accumulate("code",1);
					js.accumulate("msg","非jpg、png文件格式");
					return js.toString();
				}
				String realPath = request.getSession().getServletContext().getRealPath("/")+new Constant().UserGoUpDirectory;
				String fileName = Utils.getRandomImageName()+"."+ext;
				boolean flag = Utils.saveFile(realPath,fileName, inputStream) ;
				if(flag){
					fpictureUrl = OSSPostObject.URL+"/"+fileName ;
					js.accumulate("img",fpictureUrl);
					js.accumulate("code",0);
				}
			}
		}
		return js.toString();
	}



	//积分获取记录
	@ResponseBody
	@RequestMapping(value="/user/getIntegralDetail")
	public String getIntegralDetail(
			HttpServletRequest request,
			@RequestParam String start,
			@RequestParam String end,
			@RequestParam int currentPage
	) throws Exception{


		JSONObject jsonObject = new JSONObject() ;
		Fuser fuser = this.GetSession(request) ;

		StringBuffer filter = new StringBuffer();
		filter.append("where fuser.fid="+fuser.getFid()+" \n");
		filter.append("and  DATE_FORMAT(createDate,'%Y-%m-%d') >= '"+start+"' \n");
		filter.append("and  DATE_FORMAT(createDate,'%Y-%m-%d') <= '"+end+"' \n");
		filter.append(" order by id desc \n");
		List<Fuserintegraldetail> list = this.userintegraldetailService.list((currentPage-1)*10, 10, filter.toString(), true);

		int totalCount = this.adminService.getAllCount("Fuserintegraldetail", filter.toString());
		String url = "/user/getIntegralDetail.html?start="+start+"&end="+end+"&";
		String pagin = PaginUtil.generatePagin(totalCount/10+( (totalCount%10)==0?0:1), currentPage,url) ;

		List<UserIntegralDetailResp> detailResps = new ArrayList<>();
		for(Fuserintegraldetail fuserintegraldetail:list){
			UserIntegralDetailResp resp = new UserIntegralDetailResp();
			resp.setCreateTime(DateHelper.date2String(fuserintegraldetail.getCreateDate(),DateHelper.DateFormatType.YearMonthDay_HourMinute));
			if(fuserintegraldetail.getOperateAmount() > 0){
				resp.setIntegral("+"+fuserintegraldetail.getOperateAmount().toString());
			}else{
				resp.setIntegral(fuserintegraldetail.getOperateAmount().toString());
			}
			resp.setOperate(IntegralTypeEnum.getMap().get(fuserintegraldetail.getType()));
			detailResps.add(resp);
		}
		jsonObject.accumulate("integrallist",detailResps);
		jsonObject.accumulate("pagin",pagin);
		jsonObject.accumulate("currentPage_page",currentPage);
		jsonObject.accumulate("totalCount",totalCount);

//		jsonObject.accumulate("code", 0) ;
//		jsonObject.accumulate("msg", "验证码已经发送，请查收");
		String result = jsonObject.toString();
		LOG.d(this.getClass(),result);
		return jsonObject.toString() ;

	}

}
