package com.ruizton.main.controller;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.jpush.api.utils.StringUtils;
import com.google.gson.Gson;
import com.ruizton.main.Enum.*;
import com.ruizton.main.model.*;
import com.ruizton.main.log.LOG;
import com.ruizton.main.service.BaseService;
import com.ruizton.util.*;
import com.ruizton.util.antshare.ANSUtils;
import com.ruizton.util.antshare.resp.BalanceResp;
import com.ruizton.util.zuohao.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ruizton.main.auto.OneDayData;
import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.auto.TaskList;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.comm.MessageValidate;
import com.ruizton.main.comm.ValidateMap;
import com.ruizton.main.service.admin.LimittradeService;
import com.ruizton.main.service.admin.SystemArgsService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontValidateService;
import com.ruizton.main.service.front.FtradeMappingService;
import org.springframework.web.servlet.ModelAndView;

public class BaseController extends BaseServiceCtrl{

	public final static String Result = "result" ;
	public final static String ErrorCode = "code" ;
	public final static String Value = "value" ;
	public final static String LoginToken = "loginToken" ;
	public final static String CurrentPage = "currentPage" ;
	public final static String TotalPage = "totalPage" ;
	public final static String TotalCount = "totalCount";
	public final static String LastUpdateTime = "lastUpdateTime" ;
	public final static String Fid = "Fid" ;
	public final static String List = "list";
	public final static String PageSize = "pageSize";


	private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

	private static final String CLASS_NAME = BaseController.class.getSimpleName();

	public static final String JsonEncode = "application/json;charset=UTF-8" ;

	//返回消息提示的key
	public static final String MSG = "msg";

	public static final String CODE = "code";

	public static final String RESULT = "result";

	public static final String LIST = "list";

	public static final String TOTALPAGE = "totalPage";

	
	public static final int maxResults = Constant.RecordPerPage ;
	
	public HttpSession getSession(HttpServletRequest request){
		return request.getSession() ;
	}
	
	public void setAdminSession(HttpServletRequest request,Fadmin fadmin){
		getSession(request).setAttribute("login_admin", fadmin) ;
		this.CleanSession(request) ;
	}
	
	public void removeAdminSession(HttpServletRequest request){
		getSession(request).removeAttribute("login_admin") ;
	}
	
	//获得管理员session
	public Fadmin getAdminSession(HttpServletRequest request){
		Object session = getSession(request).getAttribute("login_admin") ;
		Fadmin fadmin = null ;
		if(session!=null){
			fadmin = (Fadmin)session ;
		}
		return fadmin ;
	}
	
	//获得session中的用户
	public Fuser GetSession(HttpServletRequest request){
		Fuser fuser = null ;
		HttpSession session = getSession(request) ;
		Object session_user = session.getAttribute("login_user") ;
		if(session_user!=null){
			fuser = (Fuser)session_user;
			if(fuser.getFstatus() == UserStatusEnum.FORBBIN_VALUE) return null;
		}
		return fuser ;
	}
	
	public Fuser GetSecondLoginSession(HttpServletRequest request){
		HttpSession session = getSession(request) ;
		return (Fuser) session.getAttribute("second_login_user") ;
	}
	
	public void SetSecondLoginSession(HttpServletRequest request,Fuser fuser){
		HttpSession session = getSession(request) ;
		session.setAttribute("second_login_user", fuser) ;
	}
	public void RemoveSecondLoginSession(HttpServletRequest request){
		HttpSession session = getSession(request) ;
		session.setAttribute("second_login_user", null) ;
	}
	
	public void SetSession(Fuser fuser,HttpServletRequest request){
		HttpSession session = getSession(request) ;
		session.setAttribute("login_user", fuser) ;
	}
	
	public void CleanSession(HttpServletRequest request){
		try {
			HttpSession session = getSession(request) ;
			String key = GetSession(request).getFid()+"trade" ;
			getSession(request).removeAttribute(key);
			session.setAttribute("login_user", null) ;
		} catch (Exception e) {}
	}
	
	public boolean isNeedTradePassword(HttpServletRequest request){
		if(GetSession(request) == null) return true;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String key = GetSession(request).getFid()+"trade" ;
		Object obj = getSession(request).getAttribute(key) ;
		
		if(obj==null){
			return true ;
		}else{
			try {
				double hour = Double.valueOf(this.systemArgsService.getValue("tradePasswordHour"));
				double lastHour = Utils.getDouble((sdf.parse(obj.toString()).getTime()-new Date().getTime())/1000/60/60, 2);
				if(lastHour >= hour){
					getSession(request).removeAttribute(key);
					return true ;
				}else{
					return false ;
				}
			} catch (ParseException e) {
				return false ;
			}
		}
	}
	
	
	public void setNoNeedPassword(HttpServletRequest request){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String key = GetSession(request).getFid()+"trade" ;
		getSession(request).setAttribute(key,sdf.format(new Date())) ;
	}
	
	
	//for menu
	@ModelAttribute
	public void menuSelect(HttpServletRequest request){
		//banner菜单
		String uri = request.getRequestURI() ;

		String selectMenu = null ;
		if(uri.startsWith("/trade")
				||uri.startsWith("/user")
				||uri.startsWith("/coinage")
				||uri.startsWith("/balance")
				||uri.startsWith("/question")){
			selectMenu = "trade";
		}else if(uri.startsWith("/service")){
			selectMenu = "service" ;
		}else if(uri.startsWith("/financial")
				||uri.startsWith("/introl")
				||uri.startsWith("/shop/sell")
				||uri.startsWith("/shop/myorder")
				||uri.startsWith("/account")){
			selectMenu = "financial" ;
		}else if(uri.startsWith("/divide")){
			selectMenu = "divide" ;
		}else if(uri.startsWith("/crowd/")){
			selectMenu = "crowd" ;
		}else if(uri.startsWith("/market.html")){
			selectMenu = "market" ;
		}else if(uri.startsWith("/about/index.html")) {
			selectMenu = "about";
		}else{
			selectMenu = "index";
		}
		request.setAttribute("selectMenu", selectMenu) ;
		//左侧菜单
		if(uri.startsWith("/trade")
				||uri.startsWith("/user")
				||uri.startsWith("/shop/")
				||uri.startsWith("/divide/")
				||uri.startsWith("/crowd/")
				||uri.startsWith("/balance/")
				||uri.startsWith("/introl/mydivide")
				||uri.startsWith("/account")
				||uri.startsWith("/financial")
				||uri.startsWith("/coinage/")
				||uri.startsWith("/free/")//融资融币
				||uri.startsWith("/question")){
			String leftMenu = null ;
			int selectGroup = 1 ;
			
			if(uri.startsWith("/question/questionColumn")){
				leftMenu = "questionColumn";
				selectGroup = 4 ;
			}else if(uri.startsWith("/question/question")){
				leftMenu = "question";
				selectGroup = 4 ;
			}else if(uri.startsWith("/user/apply")){
				leftMenu = "apply";
				selectGroup = 4 ;
			}if(uri.startsWith("/question/message")){
				leftMenu = "message";
				selectGroup = 4 ;
			}else if(uri.startsWith("/user/")){
				leftMenu = "userinfo" ;
				selectGroup = 3 ;
			}else if(uri.startsWith("/user/security")){
				leftMenu = "security" ;
				selectGroup = 3 ;
			}else if(uri.startsWith("/user/api")){
				leftMenu = "api" ;
				selectGroup = 3 ;
			}else if(uri.startsWith("/account/record")){
				leftMenu = "record" ;
				selectGroup = 2 ;
			}else if(uri.startsWith("/financial/accountalipay") 
					||uri.startsWith("/financial/accountbank")
					||uri.startsWith("/financial/accountcoin")){
				leftMenu = "accountAdd" ;
				selectGroup = 2 ;
			}else if(uri.startsWith("/crowd/mylogs") 
					||uri.startsWith("/crowd/logs")){
				leftMenu = "mylogs" ;
				selectGroup = 2 ;
			}else if(uri.startsWith("/account/deduct")){
				leftMenu = "record" ;
				selectGroup = 2 ;
			}else if(uri.startsWith("/account/rechargeCny")
					||uri.startsWith("/account/proxyCode")
					||uri.startsWith("/account/payCode")){
				leftMenu = "recharge" ;
				selectGroup = 2 ;
			}else if(uri.startsWith("/account/withdrawCny")){
				leftMenu = "withdraw" ;
				selectGroup = 2 ;
			}else if(uri.startsWith("/account/rechargeBtc")){
				leftMenu = "recharge" ;
				selectGroup = 2 ;
			}else if(uri.startsWith("/account/withdrawBtc")){
				leftMenu = "withdraw" ;
				selectGroup = 2 ;
			}else if(uri.startsWith("/account/btcTransport")){
				leftMenu = "btcTransport" ;
				selectGroup = 2 ;
			}else if(uri.startsWith("/trade/entrust")){
				leftMenu = "entrust" ;
				selectGroup = 1 ;
			}else if(uri.startsWith("/divide/")){
				leftMenu = "divide" ;
				selectGroup = 6 ;
			}else if(uri.startsWith("/financial/")){
				leftMenu = "financial" ;
				selectGroup = 2 ;
			}else if(uri.startsWith("/introl/mydivide")){
				leftMenu = "introl" ;
				selectGroup = 2 ;
			}
			request.setAttribute("selectGroup", selectGroup) ;
			request.setAttribute("leftMenu", leftMenu) ;
		}
	}
	
	@ModelAttribute
	public void addConstant(HttpServletRequest request){//此方法会在每个controller前执行
		Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("locale_language", locale);
		//前端常量
		request.setAttribute("constant", constantMap.getMap()) ;
		String ossURL = OSSPostObject.URL;
		if(new Constant().IS_OPEN_OSS.equals("false")){
			ossURL = "";
		}
		request.setAttribute("oss_url", ossURL) ;

//		
//		String uri = request.getRequestURI() ;
//		if(uri.startsWith("/trade/")){
//			//最新成交价格
//			List<Fvirtualcointype> realTimePrize = new ArrayList<Fvirtualcointype>() ;
//			List<Fvirtualcointype> fvirtualcointypes = (List)this.constantMap.get("virtualCoinType");
//			for (Fvirtualcointype fvirtualcointype : fvirtualcointypes) {
//				fvirtualcointype.setLastDealPrize(this.realTimeData.getLatestDealPrize(fvirtualcointype.getFid())) ;
//				fvirtualcointype.setHigestBuyPrize(this.realTimeData.getHighestBuyPrize(fvirtualcointype.getFid())) ;
//				fvirtualcointype.setLowestSellPrize(this.realTimeData.getLowestSellPrize(fvirtualcointype.getFid())) ;
//				realTimePrize.add(fvirtualcointype) ;
//				
//			}
//			request.setAttribute("realTimePrize", realTimePrize) ;
//		}
		
		if(GetSession(request)==null) return ;//用户没登陆不需执行以下内容
		
		if(this.realTimeData.isBalckUser(GetSession(request).getFid())){
			this.CleanSession(request) ;
			return ;
		}
		//用户钱包
		Fvirtualwallet fwallet = this.frontUserService.findWalletByUser(GetSession(request).getFid()) ;
		request.setAttribute("fwallet", fwallet) ;
		//虚拟钱包
		Map<Integer,Fvirtualwallet> fvirtualwallets = this.frontUserService.findVirtualWallet(GetSession(request).getFid()) ;
		request.setAttribute("fvirtualwallets", fvirtualwallets) ; 
		//估计总资产
		//CNY+冻结CNY+（币种+冻结币种）*最高买价
		double totalCapital = 0F ;
		totalCapital+=Utils.add(fwallet.getFtotal(), fwallet.getFfrozen());
		Map<Integer,Integer> tradeMappings = (Map)this.constantMap.get("tradeMappings");
		for (Map.Entry<Integer, Fvirtualwallet> entry : fvirtualwallets.entrySet()) {
			if(entry.getValue().getFvirtualcointype().getFtype() == CoinTypeEnum.FB_CNY_VALUE) continue;
			try {
				//2017-04-07 转换计算方式
				double frozen = entry.getValue().getFfrozen();
				double total = entry.getValue().getFtotal();
				double sum = Utils.add(frozen,total);
				double lates_price = this.realTimeData.getLatestDealPrize(tradeMappings.get(entry.getValue().getFvirtualcointype().getFid()));
				totalCapital += Utils.mul(sum, lates_price) ;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		request.setAttribute("totalNet", Utils.getDouble(totalCapital, 2)) ;
		request.setAttribute("totalCapital", Utils.getDouble(totalCapital,2)) ;
		
		request.setAttribute("totalNetTrade", Utils.getDouble(totalCapital, 2)) ;
		request.setAttribute("totalCapitalTrade", Utils.getDouble(totalCapital,2)) ;
		
	}
	
	public Map<String, Object> setRealTimeData(int id){
		Map<String, Object> map = new HashMap<String, Object>() ;

		map.put("BuyMap", this.realTimeData.getBuyDepthMap(id,10) ) ;
		map.put("SellMap", this.realTimeData.getSellDepthMap(id,10) ) ;
		map.put("BuySuccessMap", this.realTimeData.getEntrustSuccessMap(id,10)) ;
		
		map.put("LatestDealPrize", this.realTimeData.getLatestDealPrize(id)) ;
		map.put("LowestSellPrize", this.realTimeData.getLowestSellPrize(id)) ;
		map.put("HighestBuyPrize", this.realTimeData.getHighestBuyPrize(id)) ;
		
		map.put("OneDayLowest", this.oneDayData.getLowest(id)) ;
		map.put("OneDayHighest", this.oneDayData.getHighest(id)) ;
		map.put("OneDayTotal", this.oneDayData.getTotal(id)) ;
		return map ;
	}



	public boolean validateMessageCode(Fuser fuser,String areaCode,String phone,int type,String code){
		boolean match = true ;
		MessageValidate messageValidate = this.validateMap.getMessageMap(fuser.getFid()+"_"+type) ;
		if(messageValidate==null){
			match = false ;
		}else{
			if(/*!messageValidate.getAreaCode().equals(areaCode)
					||*/!messageValidate.getPhone().equals(phone)
					||!messageValidate.getCode().equals(code)){
				match = false ;
			}else{
				match = true ;
//				this.validateMap.removeMessageMap(fuser.getFid()+"_"+type);
			}
		}

		return match ;
	}

	//发送短信验证码，已登录用户
	public boolean SendMessage(Fuser fuser,int fuserid,String areaCode,String phone,int type){
		boolean canSend = true ;
		MessageValidate messageValidate = this.validateMap.getMessageMap(fuserid+"_"+type) ;
		LOGGER.info(CLASS_NAME + " SendMessage 根据用户id+短信类型从validateMap查找是否存在,返回结果集messageValidate:{}", new Gson().toJson(messageValidate));
		if(messageValidate!=null && Utils.timeMinus(Utils.getTimestamp(), messageValidate.getCreateTime())<120){
			LOGGER.info(CLASS_NAME + " SendMessage 距离上一次操作时间,小于120s");
			canSend = false ;
		}
		if(canSend){
			try {
				MessageValidate messageValidate2 = new MessageValidate() ;
				messageValidate2.setAreaCode(areaCode) ;
				messageValidate2.setCode(Utils.randomInteger(6)) ;
				messageValidate2.setPhone(phone) ;
				messageValidate2.setCreateTime(Utils.getTimestamp()) ;
				this.validateMap.putMessageMap(fuserid + "_" + type, messageValidate2) ;
				LOGGER.info(CLASS_NAME + " SendMessage 将验证信息存储到validateMap中，key:{},value:{}", fuserid+"_"+type, new Gson().toJson(messageValidate2));
				Fvalidatemessage fvalidatemessage = new Fvalidatemessage() ;
//			fvalidatemessage.setFcontent(this.constantMap.getString(ConstantKeys.VALIDATE_MESSAGE_CONTENT).replace("#code#", messageValidate2.getCode())) ;
				fvalidatemessage.setFcontent(messageValidate2.getCode()) ;
				fvalidatemessage.setFcreateTime(Utils.getTimestamp()) ;
				fvalidatemessage.setFphone(phone) ;
				fvalidatemessage.setFstatus(ValidateMessageStatusEnum.Not_send) ;
				LOGGER.info(CLASS_NAME + " SendMessage 将短信发送信息存储到fvalidatemessage表,fvalidatemessage:{}", new Gson().toJson(fvalidatemessage));
				fvalidatemessage.setFuser(fuser) ;
				fvalidatemessage.setVersion(1);
				Timestamp tm = Timestamp.valueOf(DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
				fvalidatemessage.setFsendTime(tm);  //2017-05-11 增加，避免因为空值，保存失败，收不到验证码
				this.frontValidateService.addFvalidateMessage(fvalidatemessage) ;

				LOGGER.info(CLASS_NAME + " SendMessage 将短信发送记录主键id存储到taskList,id:{}", fvalidatemessage.getFid());
				this.taskList.returnMessageList(fvalidatemessage.getFid()) ;
			}catch (Exception e) {
				canSend = false;
				LOG.e(this, "发短信，保存到数据库失败", e);
				e.printStackTrace();
			}
		}
		return canSend ;
	}

	
	//发送短信验证码，未登录用户
	public boolean SendMessage(String ip,String areaCode,String phone,int type){
		LOGGER.info(CLASS_NAME + "SendMessage 未登录用户发送短信验证码,参数ip:{},areaCode:{},phone:{},type:{}", ip, areaCode, phone, type);
		String key1 = ip+"_"+type ;
		String key2 = ip+"_"+phone+"_"+type ;
		LOGGER.info(CLASS_NAME + "SendMessage key1:{},key2:{}", key1,key2);
		//限制ip120秒发送
		MessageValidate messageValidate = this.validateMap.getMessageMap(key1) ;
		LOGGER.info(CLASS_NAME + "SendMessage 根据ip地址+验证码类型，从数据库中查询限制信息,messageValidate:{}", new Gson().toJson(messageValidate));
		if(messageValidate!=null && Utils.timeMinus(Utils.getTimestamp(), messageValidate.getCreateTime())<120){
			LOGGER.info(CLASS_NAME + " SendMessage 验证码限制，此类型:{}距离上一次发送低于120s", key1);
			return false ;
		}
		
		
		messageValidate = this.validateMap.getMessageMap(key2) ;
		LOGGER.info(CLASS_NAME + "SendMessage 根据ip地址+手机号+验证码类型，从数据库中查询限制信息,messageValidate:{}", new Gson().toJson(messageValidate));
		if(messageValidate!=null && Utils.timeMinus(Utils.getTimestamp(), messageValidate.getCreateTime())<120){
			LOGGER.info(CLASS_NAME + " SendMessage 验证码限制，此类型:{}距离上一次发送低于120s", key2);
			return false ;
		}
		
		MessageValidate messageValidate2 = new MessageValidate() ;
		messageValidate2.setAreaCode(areaCode) ;
		messageValidate2.setCode(Utils.randomInteger(6)) ;
		messageValidate2.setPhone(phone) ;
		messageValidate2.setCreateTime(Utils.getTimestamp()) ;
		this.validateMap.putMessageMap(key2, messageValidate2) ;
		this.validateMap.putMessageMap(key1, messageValidate2) ;
		LOGGER.info(CLASS_NAME + " SendMessage 将messageValidate2:{}存储到validateMap中，key分别为key1:{},key2:{}", new Gson().toJson(messageValidate2), key1, key2);
		Fvalidatemessage fvalidatemessage = new Fvalidatemessage() ;
		fvalidatemessage.setFcontent(messageValidate2.getCode()) ;
//		fvalidatemessage.setFcontent(this.constantMap.getString(ConstantKeys.VALIDATE_MESSAGE_CONTENT).replace("#code#", messageValidate2.getCode())) ;
		fvalidatemessage.setFcreateTime(Utils.getTimestamp()) ;
		fvalidatemessage.setFphone(phone) ;
		fvalidatemessage.setFstatus(ValidateMessageStatusEnum.Not_send) ;
		LOGGER.info(CLASS_NAME + " SendMessage 生成验证码，存储记录到fvalidatemessage表:{}", new Gson().toJson(fvalidatemessage));
		this.frontValidateService.addFvalidateMessage(fvalidatemessage) ;
		
		this.taskList.returnMessageList(fvalidatemessage.getFid()) ;
		LOGGER.info(CLASS_NAME + " SendMessage 将主键id:{}存储到taskList", fvalidatemessage.getFid());
		return true ;
	}

    /**
     * 发送短信
     * @param userId
     * @param content
     * @return
     */
	public boolean sendMessage(int userId,String content){
		Fuser fuser = this.frontUserService.queryById(userId);
        return  sendMessage(fuser,content);
    }

    /**
     * 发送短信
     * @param fuser
     * @param content
     * @return
     */
    public boolean sendMessage(Fuser fuser,String content){
        if(fuser == null || StringUtils.isEmpty(fuser.getFtelephone()) || StringUtils.isEmpty(content)){
            return false;
        }
        Fvalidatemessage fvalidatemessage = new Fvalidatemessage() ;
        fvalidatemessage.setFcontent(content) ;
        fvalidatemessage.setFuser(fuser);
        fvalidatemessage.setFcreateTime(Utils.getTimestamp()) ;
        fvalidatemessage.setFphone(fuser.getFtelephone()) ;
        fvalidatemessage.setFstatus(ValidateMessageStatusEnum.Not_send) ;
		fvalidatemessage.setVersion(1);
		Timestamp tm = Timestamp.valueOf(DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
		fvalidatemessage.setFsendTime(tm);  //2017-05-11 增加，避免因为空值，保存失败，收不到验证码
        this.frontValidateService.addFvalidateMessage(fvalidatemessage);
		this.taskList.returnMessageList(fvalidatemessage.getFid()) ;
        return true;
    }
	
	public boolean validateMessageCode(String ip,String areaCode,String phone,int type,String code){
		if(type!=MessageTypeEnum.REG_CODE&&type!=MessageTypeEnum.FIND_PASSWORD){
			System.out.println("调用方法错误");
			return false ;
		}
		
//		String key1 = ip+"message_"+type ;
		String key2 = ip+"_"+phone+"_"+type ;
		boolean match = true ;
		MessageValidate messageValidate = this.validateMap.getMessageMap(key2) ;
		if(messageValidate==null){
			match = false ;
		}else{
			if(!messageValidate.getAreaCode().equals(areaCode)
					||!messageValidate.getPhone().equals(phone)
					||!messageValidate.getCode().equals(code)){
				match = false ;
			}else{
				match = true ;
//				this.validateMap.removeMessageMap(key1);
//				this.validateMap.removeMessageMap(key2);
			}
		}
		
		return match ;
	}
	
	//发送邮件验证码，未登录用户
	public boolean SendMail(String ip,String mail,int type){
		String key1 = ip+"mail_"+type ;
		String key2 = ip+"_"+mail+"_"+type ;
		//限制ip120秒发送
		Emailvalidate mailValidate = this.validateMap.getMailMap(key1) ;
		if(mailValidate!=null && Utils.timeMinus(Utils.getTimestamp(), mailValidate.getFcreateTime())<120){
			return false ;
		}
		
		
		mailValidate = this.validateMap.getMailMap(key2) ;
		if(mailValidate!=null && Utils.timeMinus(Utils.getTimestamp(), mailValidate.getFcreateTime())<120){
			return false ;
		}
		
		Emailvalidate mailValidate2 = new Emailvalidate() ;
		mailValidate2.setCode(Utils.randomInteger(6)) ;
		mailValidate2.setFcreateTime(Utils.getTimestamp()) ;
		mailValidate2.setFmail(mail) ;
		
		this.validateMap.putMailMap(key1, mailValidate2) ;
		this.validateMap.putMailMap(key2, mailValidate2) ;
		
		Fvalidateemail fvalidateemail = new Fvalidateemail() ;
		fvalidateemail.setEmail(mail) ;
		fvalidateemail.setFcontent(this.constantMap.getString(ConstantKeys.regmailContent).replace("#code#", mailValidate2.getCode())) ;
		fvalidateemail.setFcreateTime(Utils.getTimestamp()) ;
		fvalidateemail.setFstatus(ValidateMailStatusEnum.Not_send) ;
		if(type == SendMailTypeEnum.FindPassword) {  //找回登录密码
			fvalidateemail.setFtitle(this.constantMap.getString(ConstantKeys.WEB_NAME)+"找回登录密码");
		} else{
			fvalidateemail.setFtitle(this.constantMap.getString(ConstantKeys.WEB_NAME)+"注册验证码");
		}

		this.frontValidateService.addFvalidateemail(fvalidateemail) ;
		
		this.taskList.returnMailList(fvalidateemail.getFid()) ;
		
		return true ;
	}

	//发送邮件验证码，未登录用户
	public boolean SendMail(String ip,String mail,int type, String emailTemplate, String title){
		String key1 = ip+"mail_"+type ;
		String key2 = ip+"_"+mail+"_"+type ;
		//限制ip120秒发送
		Emailvalidate mailValidate = this.validateMap.getMailMap(key1) ;
		if(mailValidate!=null && Utils.timeMinus(Utils.getTimestamp(), mailValidate.getFcreateTime())<120){
			return false ;
		}


		mailValidate = this.validateMap.getMailMap(key2) ;
		if(mailValidate!=null && Utils.timeMinus(Utils.getTimestamp(), mailValidate.getFcreateTime())<120){
			return false ;
		}

		Emailvalidate mailValidate2 = new Emailvalidate() ;
		mailValidate2.setCode(Utils.randomInteger(6)) ;
		mailValidate2.setFcreateTime(Utils.getTimestamp()) ;
		mailValidate2.setFmail(mail) ;

		this.validateMap.putMailMap(key1, mailValidate2) ;
		this.validateMap.putMailMap(key2, mailValidate2) ;

		Fvalidateemail fvalidateemail = new Fvalidateemail() ;
		fvalidateemail.setEmail(mail) ;
		fvalidateemail.setFcontent(this.constantMap.getString(emailTemplate).replace("#code#", mailValidate2.getCode())) ;
		fvalidateemail.setFcreateTime(Utils.getTimestamp()) ;
		fvalidateemail.setFstatus(ValidateMailStatusEnum.Not_send) ;
		fvalidateemail.setFtitle(this.constantMap.getString(ConstantKeys.WEB_NAME)+title);
		this.frontValidateService.addFvalidateemail(fvalidateemail) ;

		this.taskList.returnMailList(fvalidateemail.getFid()) ;

		return true ;
	}
	
	public boolean validateMailCode(String ip ,String mail,int type,String code){
		String key2 = ip+"_"+mail+"_"+type ;
		boolean match = true ;
		Emailvalidate emailvalidate = this.validateMap.getMailMap(key2) ;
		if(emailvalidate==null){
			match = false ;
		}else{
			if(!emailvalidate.getFmail().equals(mail)
					||!emailvalidate.getCode().equals(code)){
				match = false ;
			}else{
				match = true ;
//				this.validateMap.removeMailMap(key1);
//				this.validateMap.removeMailMap(key2);
			}
		}
		
		return match ;
	}
	

	public int totalPage(int totalCount,int maxResults){
		return totalCount/maxResults + (totalCount%maxResults==0?0:1) ;
	}

	
	public Flimittrade isLimitTrade(int vid) {
		Flimittrade flimittrade = null;
		String filter = "where ftrademapping.fid="+vid;
		List<Flimittrade> flimittrades = this.limittradeService.list(0, 0, filter, false);
		if(flimittrades != null && flimittrades.size() >0){
			flimittrade = flimittrades.get(0);
		}
		return flimittrade;
	}
	
	
	//图片验证码
	public boolean vcodeValidate(HttpServletRequest request,String vcode){
		Object session_code = request.getSession().getAttribute("checkcode") ;
		if(session_code==null || !vcode.equalsIgnoreCase((String)session_code)){
			return false ;
		}else{
			getSession(request).removeAttribute("checkcode") ;
			return true ;
		}
	}
	
	public static String getIpAddr(HttpServletRequest request) {
		 try {
			 String ip = request.getHeader("X-Forwarded-For");  
			 try {
				if(ip != null && ip.trim().length() >0){
					 return ip.split(",")[0];
				 }
			} catch (Exception e) {}
			 
			try {
				ip = request.getHeader("X-Real-IP");
				if ((ip != null && ip.trim().length() >0) && (!"unKnown".equalsIgnoreCase(ip))) {
				  return ip;
				}
			} catch (Exception e) {}
			 
			 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			  ip = request.getHeader("http_client_ip");  
			 }  
			 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			  ip = request.getRemoteAddr();  
			 }  
			 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			  ip = request.getHeader("Proxy-Client-IP");  
			 }  
			 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			  ip = request.getHeader("WL-Proxy-Client-IP");  
			 }  
			 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			  ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
			 }  
			 // 如果是多级代理，那么取第一个ip为客户ip   
			 if (ip != null && ip.indexOf(",") != -1) {  
			  ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();  
			 }  
			 return ip;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return request.getRemoteAddr();  
		}  
	}


	/**
	 * 计算时间差文本
	 * @param SysDate
	 * @param busDate
	 * @return
	 */
	public String returnTimeBetweenText(Date SysDate, Date busDate) {
		String text = "";
		long between = (SysDate.getTime() - busDate.getTime())/1000;  //相差秒数

		long min = between/60;  //相差分钟数

		long hour = between/60/60;  //相差小数数

		if(hour < 24 ) {
			if (hour >= 1 && hour < 24) {
				text = hour + "小时前";
			} else if (min >= 3 && min < 60) {
				text = min + "分钟前";
			} else if (min >= 0 && min < 3) {
				text = "刚刚更新";
			}
		}else{  //超过1天显示yyyy-mm-dd
			text =  DateHelper.date2String(busDate, DateHelper.DateFormatType.YearMonthDay);
		}
		return text;
	}


	public static boolean validateLoginPassword(String content) {
		String regexString  = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{6,16}$";
		if(StringUtils.isEmpty(content) || StringUtils.isEmpty(regexString)){
			return false;
		}
		Pattern pattern = Pattern.compile(regexString);
		Matcher matcher = pattern.matcher(content);
		return !matcher.matches();
	}

	/**
	 * 根据用户选定的语言，获取国际化文件，指定key的value
	 * @param msgKey
	 * @return
	 * 不带格式化参数
	 */
	public String i18nMsg(String msgKey) {
		String message = "";
		try {
			Locale locale = LocaleContextHolder.getLocale();
			message = messageSource.getMessage(msgKey, null, "Default", locale);
			return  message;
		}catch (Exception e) {
			LOG.i(this, "国际化信息读取失败，"+e.getMessage());
			LOG.e(this , "国际化信息读取失败", e);
		}
		return message;
	}

	/**
	 * 根据用户选定的语言，获取国际化文件，指定key的value
	 * @param msgKey
	 * @return
	 * 带格式化参数
	 */
	public String i18nMsg(String msgKey, Object[] args) {
		String message = "";
		try {
			Locale locale = LocaleContextHolder.getLocale();
			message = messageSource.getMessage(msgKey, args, "Default", locale);
			return  message;
		}catch (Exception e) {
			LOG.i(this, "国际化信息读取失败，"+e.getMessage());
			LOG.e(this , "国际化信息读取失败", e);
		}
		return message;
	}

	/**
	 * 获取当前选定语言的所有key-value
	 * @return
	 */
	public Map<String, String> loadi18nPro() {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.loadi18nPro(locale);
	}

    /**
	 *  判断审核权限
	 * @param fstatus
	 * @param fadmin
     * @return
     */
	protected int getAuditProcess(int fstatus,Fadmin fadmin,AuditProcessEnum auditProcessEnum,Fauditprocess fauditprocess){
		int result = -1;
		if(fauditprocess == null){
			return -1;  //没有配置权限
		}
		int role1 = fauditprocess.getFrole1() == null?0:fauditprocess.getFrole1().getFid();
		int role2 = fauditprocess.getFrole2() == null?0:fauditprocess.getFrole2().getFid();
		int role3 = fauditprocess.getFrole3() == null?0:fauditprocess.getFrole3().getFid();
        int role = 0;

		if(auditProcessEnum == AuditProcessEnum.VIRTUAL_OUT
				|| auditProcessEnum == AuditProcessEnum.CAPITAL_IN
				|| auditProcessEnum == AuditProcessEnum.CAPITAL_OUT){ //虚拟币提币审核
             switch (fstatus){
				 case VirtualCapitalOperationOutStatusEnum.OperationLock://一级审核
					 role = role1;
					 if(role2 == 0){
						 result = VirtualCapitalOperationOutStatusEnum.OperationSuccess;
					 }else{
					 	 result = VirtualCapitalOperationOutStatusEnum.OneTimeAudit;
					 }

					 break;
				 case 10://二级审核
					 role = role2;
					 if(role3 == 0){
						 result = VirtualCapitalOperationOutStatusEnum.OperationSuccess;;
					 }else{
						 result =  VirtualCapitalOperationOutStatusEnum.TwoTimeAudit;;
					 }
					 break;
				 case 11://三级审核
					 role = fauditprocess.getFrole3().getFid();
					 result = VirtualCapitalOperationOutStatusEnum.OperationSuccess;
					 break;
				 default:
				 	 result = -2;//状态不正确
			 }
		}else if(auditProcessEnum == AuditProcessEnum.VIRTUAL_IN){  //虚拟币充值审核
			switch (fstatus){
				case VirtualCapitalOperationInStatusEnum.WAIT_1://一级审核
					role = role1;
					if(role2 == 0){
						result = VirtualCapitalOperationInStatusEnum.SUCCESS;
					}else{
						result = VirtualCapitalOperationOutStatusEnum.OneTimeAudit;
					}

					break;
				case VirtualCapitalOperationInStatusEnum.WAIT_2://一级审核
					role = role1;
					if(role2 == 0){
						result = VirtualCapitalOperationInStatusEnum.SUCCESS;
					}else{
						result = VirtualCapitalOperationOutStatusEnum.OneTimeAudit;
					}

					break;
				case VirtualCapitalOperationOutStatusEnum.OneTimeAudit://二级审核
					role = role2;
					if(role3 == 0){
						result = VirtualCapitalOperationInStatusEnum.SUCCESS;;
					}else{
						result =  VirtualCapitalOperationOutStatusEnum.TwoTimeAudit;;
					}
					break;
				case VirtualCapitalOperationOutStatusEnum.TwoTimeAudit://三级审核
					role = fauditprocess.getFrole3().getFid();
					result = VirtualCapitalOperationOutStatusEnum.OperationSuccess;
					break;
				default:
					result = -2;//状态不正确
					break;
			}
		}else if(auditProcessEnum == AuditProcessEnum.GOUP){
			switch (fstatus){
				case UserStatusEnum.FREEZE_VALIDATE://一级审核
					role = role1;
					if(role2 == 0){
						result = VirtualCapitalOperationInStatusEnum.SUCCESS;
					}else{
						result = VirtualCapitalOperationOutStatusEnum.OneTimeAudit;
					}
					break;
				case VirtualCapitalOperationOutStatusEnum.OneTimeAudit://二级审核
					role = role2;
					if(role3 == 0){
						result = VirtualCapitalOperationInStatusEnum.SUCCESS;;
					}else{
						result =  VirtualCapitalOperationOutStatusEnum.TwoTimeAudit;;
					}
					break;
				case VirtualCapitalOperationOutStatusEnum.TwoTimeAudit://三级审核
					role = fauditprocess.getFrole3().getFid();
					result = VirtualCapitalOperationOutStatusEnum.OperationSuccess;
					break;
				default:
					result = -2;//状态不正确
					break;
			}
		}

		if(result >=0  && role != fadmin.getFrole().getFid()){
			result =  -3;//没有审核权限
		}

		return result;
	}

    /**
	 * 返回web提示错误信息  请求参数不正确
	 * @param modelAndView
	 * @param message
	 * @return
     */
	public ModelAndView getWebRequestError(ModelAndView modelAndView,String message){
		modelAndView.addObject("statusCode",-1);
		modelAndView.addObject("message",message);
		return modelAndView;
	}

	/**
	 * 判断部分数据类型是否为空
	 * @param paramArray
	 * @return
	 */
	public boolean checkParam(Object... paramArray){
		boolean flag = false;
		for(Object param : paramArray){
			if(null == param){
				return flag;
			}
			if(param instanceof String){
				if(((String) param).trim().length() == 0){
					return flag;
				}
			}
			if(param instanceof ArrayList){
				if(((ArrayList) param).size() < 1){
					return flag;
				}
			}
		}
		return true;
	}


	/**
	 * 提币公共部分
	 * @param modelAndView  返回视图的异常信息
	 * @param virtualWalletInfo  用户钱包信息
	 * @param password  密码
	 * @param virtualcaptualoperation  提币流水信息
	 * @param fvirtualcointype  币种信息
	 * @param userId 用户id
	 * @return
	 * @throws Exception
	 */
	public ModelAndView withdraw(ModelAndView modelAndView, Fvirtualwallet virtualWalletInfo, String password,
								 Fvirtualcaptualoperation virtualcaptualoperation, Fvirtualcointype fvirtualcointype,
								 int userId) throws Exception{

		double amount = Utils.getDouble(Utils.add(virtualcaptualoperation.getFamount(),virtualcaptualoperation.getFfees()), 4);


		StringBuilder builder = new StringBuilder(password);
		if (fvirtualcointype.getIsSplitPassword()!= null && fvirtualcointype.getIsSplitPassword()){
			// 启用密码拆分
			builder.reverse();
			byte[] salt = PasswordUtils.decryptBASE64(fvirtualcointype.getSalt());
			String ascii = PasswordUtils.stringToAscii(fvirtualcointype.getSalt());
			byte[] data = PasswordUtils.decryptBASE64(fvirtualcointype.getPassword());
			builder.append(new String(PasswordUtils.decrypt(data,ascii,salt)));
		}
		BTCMessage btcMsg = new BTCMessage();
		btcMsg.setACCESS_KEY(fvirtualcointype.getFaccess_key());
		btcMsg.setSECRET_KEY(fvirtualcointype.getFsecrt_key());
		btcMsg.setIP(fvirtualcointype.getFip());
		btcMsg.setPASSWORD(builder.toString());
		btcMsg.setPORT(fvirtualcointype.getFport());
		btcMsg.setCOIN_TYPE(fvirtualcointype.getfShortName().toUpperCase());

		IWalletUtil iWalletUtil = null ;
		if(fvirtualcointype.isFisEth()) {//以太坊
			ETHUtils ethUtils = new ETHUtils(btcMsg);
			iWalletUtil = ethUtils;
			try {
				double balance = ethUtils.getbalanceValue(fvirtualcointype.getMainAddr());
				if (balance < amount + virtualcaptualoperation.getFfees()) {
					modelAndView.addObject("statusCode", 300);
					modelAndView.addObject("message", "审核失败，主钱包地址余额：" + balance + "小于"
							+ amount);
					return modelAndView;
				}

				boolean isTrue = ethUtils.validateaddress(virtualcaptualoperation.getWithdraw_virtual_address());
				if (!isTrue) {
					modelAndView.addObject("statusCode", 300);
					modelAndView.addObject("message", "提现地址无效");
					return modelAndView;
				}
			} catch (Exception e1) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "审核失败，钱包连接失败");
				return modelAndView;
			}
		} else if (org.apache.commons.lang.StringUtils.equals(fvirtualcointype.getfShortName(),CoinType.ANS)){
			// 小蚁股
			ANSUtils ansUtils = new ANSUtils(btcMsg);
			iWalletUtil = ansUtils;
			try {
				BalanceResp resp = ansUtils.ans_getbalance(ANSUtils.ANS_ASSET_ID);
				double balance = resp.getResult().getBalance().doubleValue();
				if (balance < amount + virtualcaptualoperation.getFfees()) {
					modelAndView.addObject("statusCode", 300);
					modelAndView.addObject("message", "审核失败，钱包余额：" + balance + "小于"
							+ amount);
					return modelAndView;
				}
				/*boolean isTrue = ethUtils.validateaddress(virtualcaptualoperation.getWithdraw_virtual_address());
				if (!isTrue) {
					modelAndView.addObject("statusCode", 300);
					modelAndView.addObject("message", "提现地址无效");
					return modelAndView;
				}*/
			} catch (Exception e1) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "审核失败，钱包连接失败");
				return modelAndView;
			}
		}else{
			BTCUtils btcUtils = new BTCUtils(btcMsg);
			iWalletUtil = btcUtils ;
			try {
				double balance = btcUtils.getbalanceValue();
				if (balance < amount+virtualcaptualoperation.getFfees()) {
					modelAndView.addObject("statusCode", 300);
					modelAndView.addObject("message", "审核失败，钱包余额：" + balance + "小于"
							+ amount);
					return modelAndView;
				}

				boolean isTrue = btcUtils.validateaddress(virtualcaptualoperation.getWithdraw_virtual_address());
				if(!isTrue){
					modelAndView.addObject("statusCode", 300);
					modelAndView.addObject("message", "提现地址无效");
					return modelAndView;
				}
			} catch (Exception e1) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "审核失败，钱包连接失败");
				return modelAndView;
			}
		}


		if(virtualcaptualoperation.getFtradeUniqueNumber() != null &&
				virtualcaptualoperation.getFtradeUniqueNumber().trim().length() >0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "非法操作！请检查钱包！");
			return modelAndView;
		}

		// 提币操作
		virtualcaptualoperation
				.setFstatus(VirtualCapitalOperationOutStatusEnum.OperationSuccess);
		virtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp());

		// 钱包操作
		virtualWalletInfo.setFlastUpdateTime(Utils.getTimestamp());
		virtualWalletInfo.setFfrozen(Utils.sub(virtualWalletInfo.getFfrozen() , amount));
		LOGGER.info(CLASS_NAME + " virtualCapitalOutAudit,虚拟币提现审核，提现后的冻结金额是:{}", virtualWalletInfo.getFfrozen());
		try {
			this.virtualCapitaloperationService.updateCapital(
					virtualcaptualoperation, virtualWalletInfo, iWalletUtil);
		} catch (Exception e) {
			LOG.e(this,e.getMessage(),e);
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", e.getMessage());
			return modelAndView;
		}

		//发送短信通知
		try{
			StringBuffer content = new StringBuffer( "您于"+ DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
			content.append("成功提取").append(virtualcaptualoperation.getFvirtualcointype().getFname()).append(amount);
			content.append("个，手续费为");
			content.append(Utils.getDoubleS(virtualcaptualoperation.getFfees(),4));
			content.append(",到账时间视网络而定，若有疑问请致电400-900-6615");
			sendMessage(userId,content.toString());
		}catch (Exception e){
			LOG.e(this,e.getMessage(),e);
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核通过，短信发送失败");
			return modelAndView;
		}

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "审核成功");
		return modelAndView;
	}


	/**
	 * 邮箱转格式
	 * @param email
	 * @return
	 */
	public String emailFormat(String email) {
		String[] args = email.split("@");
		email = args[0].substring(0, args[0].length() - (args[0].length() >= 3 ? 3 : 1)) + "****" + args[1];
		return email;
	}
}
