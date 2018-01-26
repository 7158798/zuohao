package com.ruizton.main.controller.front;

import cn.jpush.api.utils.StringUtils;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import com.ruizton.main.Enum.*;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.controller.front.response.FintegralActivityResp;
import com.ruizton.main.controller.front.response.FtradeMappingResp;
import com.ruizton.main.controller.front.response.UserGradeResp;
import com.ruizton.main.controller.front.response.UserIntegralDetailResp;
import com.ruizton.main.model.*;
import com.ruizton.main.model.integral.Fintegralactivity;
import com.ruizton.main.model.integral.Fusergrade;
import com.ruizton.main.model.integral.Fuserintegraldetail;
import com.ruizton.util.Constant;
import com.ruizton.util.DateHelper;
import com.ruizton.util.PaginUtil;
import com.ruizton.util.Utils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class FrontUserController extends BaseController {

    //每页显示多少条数据
    private int numPerPage = 10;

    //绑定银行卡上限数量
    private final int bankCount = 5;

    /*
     * @param type:0登陆，1引导
     * */
    @RequestMapping(value = "/user/login")
    public ModelAndView login(
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "") String forwardUrl,
            @RequestParam(required = false, defaultValue = "0") String type
    ) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        //推荐注册
        try {
            Fuser intro = null;
            Cookie cs[] = request.getCookies();
            for (Cookie cookie : cs) {
                if (cookie.getName().endsWith("r")) {
                    intro = this.frontUserService.findById(Integer.parseInt(cookie.getValue()));
                    break;
                }
            }
            if (intro != null) {
                modelAndView.addObject("intro", intro.getFid());
            }
        } catch (Exception e) {
        }

        //服务中心
        try {
            List<Fuser> services = null;
            Cookie cs[] = request.getCookies();
            for (Cookie cookie : cs) {
                if (cookie.getName().endsWith("sn")) {
                    services = this.frontUserService.findUserByProperty("fuserNo", cookie.getValue());
                    break;
                }
            }
            if (services != null && services.size() == 1) {
                modelAndView.addObject("service", services.get(0).getFuserNo().trim());
            }
        } catch (Exception e) {
        }

        if (GetSession(request) == null) {
            modelAndView.addObject("forwardUrl", forwardUrl);
        } else {
            Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
            if (fuser.getFstatus() == UserStatusEnum.FORBBIN_VALUE) {
                CleanSession(request);
            }
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }

        int isIndex = 1;
        modelAndView.addObject("isIndex", isIndex);

        if (StringUtils.isNotEmpty(forwardUrl) && (forwardUrl.startsWith("/financial/index") || forwardUrl.startsWith("/user/security"))) {
            modelAndView.setViewName("front/user/sub_user_login");
        } else if (type.equals("0")) {
            modelAndView.setViewName("front/user/sub_user_login");
        } else {
            modelAndView.setViewName("front/user/user_link");
        }
        modelAndView.addObject("login_desc1", i18nMsg("login_desc1"));
        modelAndView.addObject("login_pwd", i18nMsg("login_pwd"));
        modelAndView.addObject("comm_error_tips_59", i18nMsg("comm.error.tips.59"));

        return modelAndView;
    }


    //注册
    @RequestMapping(value = "/user/register")
    public ModelAndView register(HttpServletRequest request,
                                      HttpServletResponse resp) {
        ModelAndView modelAndView = new ModelAndView();
        //已经登录跳回首页
        if (GetSession(request) != null) {
            Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
            if (fuser.getFstatus() == UserStatusEnum.FORBBIN_VALUE) {
                CleanSession(request);
            }
        }
        if (GetSession(request) != null) {
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }

        //推荐注册
        try {
            Fuser intro = null;
            Cookie cs[] = request.getCookies();
            for (Cookie cookie : cs) {
                if (cookie.getName().endsWith("r")) {
                    intro = this.frontUserService.findById(Integer.parseInt(cookie.getValue()));
                    break;
                }
            }
            if (intro != null) {
                modelAndView.addObject("intro", intro.getFid());
            }
        } catch (Exception e) {
        }

        //服务中心
        try {
            List<Fuser> services = null;
            Cookie cs[] = request.getCookies();
            for (Cookie cookie : cs) {
                if (cookie.getName().endsWith("sn")) {
                    services = this.frontUserService.findUserByProperty("fuserNo", cookie.getValue());
                    break;
                }
            }
            if (services != null && services.size() == 1) {
                modelAndView.addObject("service", services.get(0).getFuserNo().trim());
            }
        } catch (Exception e) {
        }

        int isIndex = 1;
        modelAndView.addObject("isIndex", isIndex);

        modelAndView.setViewName("front/user/sub_user_register");
        modelAndView.addObject("comm_error_tips_6", i18nMsg("comm.error.tips.6"));
        modelAndView.addObject("email_address", i18nMsg("email_address"));
        modelAndView.addObject("ver_code", i18nMsg("ver_code"));
        modelAndView.addObject("sms_ver_code", i18nMsg("sms_ver_code"));
        modelAndView.addObject("email_ver_code", i18nMsg("email_ver_code"));
        modelAndView.addObject("pwd_desc1", i18nMsg("pwd_desc1"));
        modelAndView.addObject("invitation_code", i18nMsg("invitation_code"));
        modelAndView.addObject("required_desc", i18nMsg("required_desc"));
        modelAndView.addObject("optional_desc", i18nMsg("optional_desc"));
        return modelAndView;

    }


    //后台注册
    //注册
//	@RequestMapping(value="/new_user/register")
//	public ModelAndView register(
//			HttpServletRequest request,
//			HttpServletResponse resp
//			) throws Exception{
//		ModelAndView modelAndView = new ModelAndView() ;
//
//		//已经登录跳回首页
//		if(GetSession(request) != null){
//			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
//			if(fuser.getFstatus()==UserStatusEnum.FORBBIN_VALUE){
//				CleanSession(request) ;
//			}
//		}
//		if(GetSession(request) != null){
//			modelAndView.setViewName("redirect:/") ;
//			return modelAndView;
//		}
//
//		//推荐注册
//		try{
//			Fuser intro = null ;
//			Cookie cs[] = request.getCookies() ;
//			for (Cookie cookie : cs) {
//				if(cookie.getName().endsWith("r")){
//					intro = this.frontUserService.findById(Integer.parseInt(cookie.getValue())) ;
//					break ;
//				}
//			}
//			if(intro!=null){
//				modelAndView.addObject("intro", intro.getFid()) ;
//			}
//		}catch(Exception e){}
//
//		//服务中心
//		try{
//			List<Fuser> services = null ;
//			Cookie cs[] = request.getCookies() ;
//			for (Cookie cookie : cs) {
//				if(cookie.getName().endsWith("sn")){
//					services = this.frontUserService.findUserByProperty("fuserNo", cookie.getValue());
//					break ;
//				}
//			}
//			if(services!=null && services.size() ==1){
//				modelAndView.addObject("service", services.get(0).getFuserNo().trim()) ;
//			}
//		}catch(Exception e){}
//
//		int isIndex = 1;
//		modelAndView.addObject("isIndex", isIndex) ;
//
//		modelAndView.setViewName("front/user/sub_user_register") ;
//		return modelAndView ;
//	}


    @RequestMapping(value = "/user/logout")
    public ModelAndView logout(HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        this.CleanSession(request);
        modelAndView.setViewName("redirect:/index.html");
        return modelAndView;
    }

    /**
     * 用户基本信息
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/security")
    public ModelAndView security(HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
        int level = fuser.getFscore().getFlevel();
        modelAndView.addObject("level", level);

        String device_name = new Constant().GoogleAuthName + ":" + fuser.getFloginName();

        boolean isBindGoogle = fuser.getFgoogleBind();
        boolean isBindTelephone = fuser.isFisTelephoneBind();
        boolean isBindEmail = fuser.getFisMailValidate();
        boolean isTradePassword = false;
        boolean isLoginPassword = true;
        boolean isKyc = fuser.getKyclevel() == ValidateKycLevelEnum.COMPLETE.getKey();
        int isReg = 0;
        //是否注册
        if (StringUtils.isNotEmpty(request.getParameter("isreg"))) {
            isReg = Integer.parseInt(request.getParameter("isreg"));
        }
        String telNumber = "";
        String email = "";
        if (fuser.getFtradePassword() != null && fuser.getFtradePassword().trim().length() > 0) {
            isTradePassword = true;
        }
        int bindType = 0;
//		if(fuser.getFhasRealValidate()){
//			bindType = bindType+1 ;
//		}
        if (isBindGoogle) {
            bindType = bindType + 1;
        }
        if (isBindTelephone) {
            bindType = bindType + 1;
            telNumber = fuser.getFtelephone().substring(0, fuser.getFtelephone().length() - 5) + "****";
        }
        if (isBindEmail) {
            bindType = bindType + 1;
            String[] args = fuser.getFemail().split("@");
            email = args[0].substring(0, args[0].length() - (args[0].length() >= 3 ? 3 : 1)) + "****" + args[1];
        }
        if (isTradePassword) {
            bindType = bindType + 1;
        }
        if (isLoginPassword) {
            bindType = bindType + 1;
        }
        if(isKyc){
            bindType = bindType + 1;
        }

        String loginName = "";
        if (fuser.getFregType() != RegTypeEnum.EMAIL_VALUE) {
            loginName = fuser.getFloginName().substring(0, fuser.getFloginName().length() - 5) + "****";
        } else {
            String[] args = fuser.getFloginName().split("@");
            loginName = args[0].substring(0, args[0].length() - (args[0].length() >= 3 ? 3 : 1)) + "****" + args[1];
        }

        //获取银行卡列表
        String filter = "where fuser.fid=" + fuser.getFid() + " and fbankType >0";
        List<FbankinfoWithdraw> fbankinfoWithdraws = this.frontUserService.findFbankinfoWithdrawByFuser(0, 0, filter, false);
        for (FbankinfoWithdraw fbankinfoWithdraw : fbankinfoWithdraws) {
            fbankinfoWithdraw.setFbankNumber(getBankNumber(fbankinfoWithdraw.getFbankNumber()));
            String logUrl = BankTypeEnum.getBankUrl(fbankinfoWithdraw.getFbankType());
            fbankinfoWithdraw.setUrl(logUrl);
        }

        if (fbankinfoWithdraws.size() > 0) {
            bindType = bindType + 1;
        }


        //添加银行卡列表
        Map<Integer, String> bankTypes = new HashMap<Integer, String>();
        for (int i = 1; i <= BankTypeEnum.QT; i++) {

            if (BankTypeEnum.getEnumString_i18n(i) != null && BankTypeEnum.getEnumString_i18n(i).trim().length() > 0) {
                bankTypes.put(i, BankTypeEnum.getEnumString_i18n(i));
            }
        }

        //邀请好友活动是否开启
        Fintegralactivity fintegralactivity = this.integralService.getIntegralActivy(IntegralTypeEnum.INVITE_FRIENDS);
        boolean isOpenIntrol = fintegralactivity == null ? false : true;

        //查询系统安全问题
        String question_str = this.constantMap.get("safequestion").toString();
        if(org.apache.commons.lang.StringUtils.isNotBlank(question_str)) {
            String[] question_arr = question_str.split("#");
            modelAndView.addObject("question_arr", question_arr);
        }
        //查询用户是否已进行安全问题验证
        boolean isValidateQuestion = this.fquestionvalidateService.isQuestionVal(fuser.getFid());
        if(isValidateQuestion) {
            bindType = bindType + 1;
        }

        modelAndView.addObject("isQuestionValidate", isValidateQuestion);
        modelAndView.addObject("isOpenIntrol", isOpenIntrol);
        modelAndView.addObject("bankTypes", bankTypes);
        modelAndView.addObject("loginName", loginName);
        modelAndView.addObject("telNumber", telNumber);
        modelAndView.addObject("email", email);
        modelAndView.addObject("isBindGoogle", isBindGoogle);
        modelAndView.addObject("isBindTelephone", isBindTelephone);
        modelAndView.addObject("isBindEmail", isBindEmail);
        modelAndView.addObject("isTradePassword", isTradePassword);
        modelAndView.addObject("isLoginPassword", isLoginPassword);
        modelAndView.addObject("isKyc",isKyc);

        modelAndView.addObject("device_name", device_name);
        modelAndView.addObject("fuser", fuser);
        modelAndView.addObject("fbankinfoWithdraws", fbankinfoWithdraws);
        modelAndView.addObject("bankCount", bankCount);
        modelAndView.addObject("isreg", isReg);
        modelAndView.addObject("bank_account_address1", i18nMsg("bank_account_address1"));
        modelAndView.addObject("fill_real_name", i18nMsg("fill_real_name"));
        modelAndView.addObject("kyclevel",fuser.getKyclevel());
        modelAndView.setViewName("/front/user/user_security");
        modelAndView.addObject("bindType", bindType);
        return modelAndView;
    }

    /**
     * 我的会员
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/myMember")
    public ModelAndView myMember(HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());

        //等级特权
        List<Fusergrade> fusergrades = this.utilsService.list(0, 10, "", true, Fusergrade.class);
        List<UserGradeResp> usergradeList = new ArrayList<>();
        int difference = 0;
        for (Fusergrade fusergrade : fusergrades) {
            UserGradeResp grade = new UserGradeResp();
            grade.setLevel(fusergrade.getFtitle());
            grade.setIntegral(fusergrade.getFneedintegral().toString());

            //
            if (fusergrade.getFoutfee().doubleValue() == 0.0) {
                grade.setOutFee(i18nMsg("free")); //免费
            } else {
                grade.setOutFee(fusergrade.getFoutfee().multiply(new BigDecimal(100)).setScale(2).toString()+"%");
            }
            //人民币充值
            if (fusergrade.getFcapitalinfee().doubleValue() == 0.0) {
                grade.setInCNY(i18nMsg("free"));
            } else {
                grade.setInCNY(fusergrade.getFcapitalinfee().multiply(new BigDecimal(100)).setScale(2).toString()+"%");
            }

            //比特币充值
            if (fusergrade.getVirtualinfee().doubleValue() == 0.0) {
                grade.setInBtc(i18nMsg("free"));
            } else {
                grade.setInBtc(fusergrade.getVirtualinfee().multiply(new BigDecimal(100)).setScale(2).toString()+"%");
            }

            //交易手续费
            if (fusergrade.getTradefee().doubleValue() == 0.0) {
                grade.setTrade(i18nMsg("free"));
            } else {
                grade.setTrade(fusergrade.getTradefee().multiply(new BigDecimal(100)).setScale(2).toString()+"%");
            }
            usergradeList.add(grade);

            if (difference == 0 && fuser.getFscore().getFlevel() + 1 == fusergrade.getFid()) {
                difference = fusergrade.getFneedintegral() - fuser.getIntegral();
            }
        }
        modelAndView.addObject("gradeList", usergradeList);

        //积分规则
        List<Fintegralactivity> activityList = this.fintegralactivityService.findOpenActivity();
        modelAndView.addObject("activityList", activityList);

        List<FintegralActivityResp> respList = new ArrayList<>();
        for (Fintegralactivity fintegral : activityList) {
            FintegralActivityResp resp = new FintegralActivityResp();
            //String s = IntegralTypeEnum.getMap().get(1);
            resp.setIntegralType(IntegralTypeEnum.getI18nMsg(fintegral.getType()));
            resp.setIntegral(fintegral.getIntegral());
            resp.setRemark(fintegral.getContent());
            resp.setUrl(IntegralTypeEnum.getUrlmap().get(fintegral.getType()));
            respList.add(resp);
        }

        modelAndView.addObject("activityList", respList);

        //积分记录
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String begindate = request.getParameter("begindate");
        if(org.apache.commons.lang.StringUtils.isBlank(begindate)) {
            begindate = Utils.getAfterDay(7);
        }

        String enddate = request.getParameter("enddate");
        if(org.apache.commons.lang.StringUtils.isBlank(enddate)) {
            enddate = sdf.format(new Date());
        }

        //当前页
        int currentPage = 1;
        if(request.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(request.getParameter("currentPage"));
        }

        StringBuffer filter = new StringBuffer();
        filter.append("where fuser.fid="+fuser.getFid()+" \n");
        filter.append("and  DATE_FORMAT(createDate,'%Y-%m-%d') >= '"+begindate+"' \n");
        filter.append("and  DATE_FORMAT(createDate,'%Y-%m-%d') <= '"+enddate+"' \n");
        filter.append(" order by id desc \n");
        int firstResult = (currentPage -1) * numPerPage;
        List<Fuserintegraldetail> list = this.userintegraldetailService.list(firstResult, numPerPage, filter.toString(), true);

        int totalCount = this.adminService.getAllCount("Fuserintegraldetail", filter.toString());
//        String url = "/user/getIntegralDetail.html?start="+begindate+"&end="+enddate+"&";
        String url = "/user/myMember.html?begindate=" + begindate + "&enddate=" + enddate+"&";
        String pagin = PaginUtil.generatePagin(totalCount / 10 + ((totalCount % 10) == 0 ? 0 : 1), currentPage, url);

        List<UserIntegralDetailResp> detailResps = new ArrayList<>();
        for(Fuserintegraldetail fuserintegraldetail:list){
            UserIntegralDetailResp resp = new UserIntegralDetailResp();
            resp.setCreateTime(DateHelper.date2String(fuserintegraldetail.getCreateDate(),DateHelper.DateFormatType.YearMonthDay_HourMinute));
            if(fuserintegraldetail.getOperateAmount() > 0){
                resp.setIntegral("+"+fuserintegraldetail.getOperateAmount().toString());
            }else{
                resp.setIntegral(fuserintegraldetail.getOperateAmount().toString());
            }

            resp.setOperate(IntegralTypeEnum.getI18nMsg(fuserintegraldetail.getType()));
            detailResps.add(resp);
        }
        modelAndView.addObject("integrallist",detailResps);
        modelAndView.addObject("pagin",pagin);
        modelAndView.addObject("currentPage_page",currentPage);
        modelAndView.addObject("totalCount",totalCount);
        modelAndView.addObject("begindate",begindate);
        modelAndView.addObject("enddate",enddate);


        modelAndView.addObject("fuser", fuser);
        modelAndView.addObject("fscore", fuser.getFscore());
        modelAndView.addObject("difference", difference);
        modelAndView.addObject("bank_account_address1", i18nMsg("bank_account_address1"));
        modelAndView.addObject("fill_real_name", i18nMsg("fill_real_name"));
        modelAndView.setViewName("/front/user/sub_user_member");
        return modelAndView;
    }


    public static void main(String[] args) {
        Map s = IntegralTypeEnum.getMap();
        String b = IntegralTypeEnum.getMap().get(4);
        System.out.print(b);
    }


    /**
     * 查看银行卡列表
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/viewFbankinfoWithdraw")
    public ModelAndView viewFbankinfoWithdraw(HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());

        boolean isBindTelephone = fuser.isFisTelephoneBind();
        String telNumber = "";
        if (isBindTelephone) {
            telNumber = fuser.getFtelephone().substring(0, fuser.getFtelephone().length() - 5) + "****";
        }

        String loginName = "";
        if (fuser.getFregType() != RegTypeEnum.EMAIL_VALUE) {
            loginName = fuser.getFloginName().substring(0, fuser.getFloginName().length() - 5) + "****";
        } else {
            String[] args = fuser.getFloginName().split("@");
            loginName = args[0].substring(0, args[0].length() - (args[0].length() >= 3 ? 3 : 1)) + "****" + args[1];
        }

        //获取银行卡列表
        String filter = "where fuser.fid=" + fuser.getFid() + " and fbankType >0";
        List<FbankinfoWithdraw> fbankinfoWithdraws = this.frontUserService.findFbankinfoWithdrawByFuser(0, 0, filter, false);
        for (FbankinfoWithdraw fbankinfoWithdraw : fbankinfoWithdraws) {
            int l = fbankinfoWithdraw.getFbankNumber().length();
            fbankinfoWithdraw.setFbankNumber(fbankinfoWithdraw.getFbankNumber().substring(l - 4, l));
        }

        modelAndView.addObject("loginName", loginName);
        modelAndView.addObject("telNumber", telNumber);

        modelAndView.addObject("fuser", fuser);
        modelAndView.addObject("fbankinfoWithdraws", fbankinfoWithdraws);
        modelAndView.addObject("fill_real_name", i18nMsg("fill_real_name"));
        modelAndView.setViewName("/front/user/user_security");
        return modelAndView;
    }


    /**
     * 实名认证跳转界面
     *
     * @param request
     * @return
     * @throws Exception
     */
   // @RequestMapping("/user/realCertification")
    public ModelAndView realCertification(HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
        modelAndView.addObject("fuser", fuser);
        if (fuser.getFpostRealValidate() && fuser.getFstatus() == UserStatusEnum.NORMAL_VALUE) {
            modelAndView.setViewName("front/user/realCertification2");
        } else {
            modelAndView.setViewName("front/user/realCertification");
        }
        modelAndView.addObject("fill_real_name", i18nMsg("fill_real_name"));
        return modelAndView;
    }



    /**
     * 升级认证界面
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/user/goUpCertification")
    public ModelAndView goUpCertification(HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
        modelAndView.addObject("fuser", fuser);
        if (fuser.getFpostRealValidate()) {
            modelAndView.setViewName("front/user/realCertification2");
        } else {
            modelAndView.setViewName("front/user/realCertification");
        }
        return modelAndView;
    }


    @RequestMapping("/user/userloginlog")
    public ModelAndView userloginlog(
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "1") int currentPage,
            @RequestParam(required = false, defaultValue = "1") int type
    ) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        if (type != 1 && type != 2) {
            type = 1;
        }

        Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
        modelAndView.addObject("fuser", fuser);

        String filter = null;
        String url = "";
        if (type == 1) {
            url = "userloginlog";
            filter = "where fkey1=" + fuser.getFid() + " and ftype=" + LogTypeEnum.User_LOGIN + " order by fid desc";
        } else {
            url = "usersettinglog";
            filter = "where fkey1=" + fuser.getFid() + " and ftype <>" + LogTypeEnum.User_LOGIN + " order by fid desc";
        }

        List<Flog> logs = this.logService.list((currentPage - 1) * Constant.RecordPerPage, Constant.RecordPerPage, filter, true);
        int total = this.adminService.getAllCount("Flog", filter);
        String pagin = PaginUtil.generatePagin(total / Constant.RecordPerPage + (total % Constant.RecordPerPage == 0 ? 0 : 1), currentPage, "/user/userloginlog.html?type=" + type + "&");
        modelAndView.addObject("pagin", pagin);
        modelAndView.addObject("logs", logs);

        modelAndView.setViewName("front/user/" + url);
        return modelAndView;
    }


    @RequestMapping(value = "/link/qq/call")
    public ModelAndView qqCall(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        if (GetSession(request) != null) {
            Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
            if (fuser.getFstatus() == UserStatusEnum.FORBBIN_VALUE) {
                CleanSession(request);
            }
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }

        response.setContentType("text/html;charset=utf-8");
        try {
            response.sendRedirect(new Oauth().getAuthorizeURL(request));
        } catch (QQConnectException e) {
            e.printStackTrace();
        }
        modelAndView.setViewName("/front/user/call");
        return modelAndView;

    }

    @RequestMapping(value = "/link/wx/callback")
    public ModelAndView AfterWeiXinLogin(HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        if (GetSession(request) != null) {
            Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
            if (fuser.getFstatus() == UserStatusEnum.FORBBIN_VALUE) {
                CleanSession(request);
            }
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }

        try {
            String code = request.getParameter("code");
            String openID = null;
            String access_token = null;
            if ("".equals(code) || code == null || code.trim().length() == 0) {
                System.out.print("没有获取到响应参数");
            } else {
                String APPID = this.map.get("weixinAppID").toString().trim();
                String SECRET = this.map.get("weixinSECRET").toString().trim();
                String nickName = null;
                try {
                    String u = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID + "&secret=" + SECRET + "&code=" + code + "&grant_type=authorization_code";
                    URL url = new URL(u);
                    BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                    StringBuffer sb = new StringBuffer();
                    String tmp = null;
                    while ((tmp = br.readLine()) != null) {
                        sb.append(tmp);
                    }
                    openID = JSONObject.fromObject(sb.toString()).getString("openid");
                    access_token = JSONObject.fromObject(sb.toString()).getString("access_token");
                } catch (Exception e1) {
                }
                if (openID != null && openID.trim().length() > 0) {
                    Fuser fuser = this.frontUserService.findByQQlogin(openID);
                    if (fuser == null) {
                        //推广
                        Fuser intro = null;
                        try {
                            Cookie cs[] = request.getCookies();
                            for (Cookie cookie : cs) {
                                if (cookie.getName().endsWith("r")) {
                                    intro = this.frontUserService.findById(Integer.parseInt(cookie.getValue()));
                                    break;
                                }
                            }
                        } catch (Exception e) {
                        }

                        fuser = new Fuser();
                        if (intro != null) {
                            fuser.setfIntroUser_id(intro);
                        }

                        String xx = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openID + "&lang=zh_CN";
                        URL url = new URL(xx);
                        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                        StringBuffer sb = new StringBuffer();
                        String tmp = null;
                        while ((tmp = br.readLine()) != null) {
                            sb.append(tmp);
                        }
                        nickName = HtmlUtils.htmlEscape(JSONObject.fromObject(sb.toString()).getString("nickname").trim());
                        fuser.setSalt(Utils.getUUID());
                        fuser.setQqlogin(openID);
                        fuser.setFnickName(nickName);
                        fuser.setFloginName(nickName + "_" + Utils.getRandomString(6));

                        fuser.setFregType(RegTypeEnum.WX_VALUE);
                        fuser.setFisMailValidate(false);
                        String ip = getIpAddr(request);
                        fuser.setFregIp(ip);
                        fuser.setFlastLoginIp(ip);

                        fuser.setFregisterTime(Utils.getTimestamp());
                        fuser.setFloginPassword(Utils.MD5(openID, fuser.getSalt()));
                        fuser.setFtradePassword(null);
                        fuser.setFstatus(UserStatusEnum.NORMAL_VALUE);
                        fuser.setFlastLoginTime(Utils.getTimestamp());
                        fuser.setFlastUpdateTime(Utils.getTimestamp());
                        boolean saveFlag = false;
                        try {
                            saveFlag = this.frontUserService.saveRegister(fuser);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (saveFlag) {
                            this.SetSession(fuser, request);
                        }

                    } else {
                        //登陆
                        if (fuser.getFstatus() != UserStatusEnum.FORBBIN_VALUE) {
                            String ip = getIpAddr(request);
                            fuser.setFlastLoginIp(ip);
                            fuser.setFlastLoginTime(Utils.getTimestamp());
                            this.frontUserService.updateFUser(fuser, null, LogTypeEnum.User_LOGIN, ip);
                            this.SetSession(fuser, request);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        modelAndView.setViewName("redirect:/index.html");
        return modelAndView;
    }

    @RequestMapping(value = "/link/qq/callback")
    public ModelAndView AfterQQLogin(HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        if (GetSession(request) != null) {
            Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
            if (fuser.getFstatus() == UserStatusEnum.FORBBIN_VALUE) {
                CleanSession(request);
            }
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }

        try {
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
            String accessToken = null,
                    openID = null;

            if (accessTokenObj.getAccessToken().equals("")) {
                System.out.print("没有获取到响应参数");
            } else {
                accessToken = accessTokenObj.getAccessToken();

                // 利用获取到的accessToken 去获取当前用的openid -------- start
                OpenID openIDObj = new OpenID(accessToken);
                openID = openIDObj.getUserOpenID();

                UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
                UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                String nickName = HtmlUtils.htmlEscape(userInfoBean.getNickname().trim());

                if (openID != null && openID.trim().length() > 0) {
                    Fuser fuser = this.frontUserService.findByQQlogin(openID);
                    if (fuser == null) {
                        //推广
                        Fuser intro = null;
                        try {
                            Cookie cs[] = request.getCookies();
                            for (Cookie cookie : cs) {
                                if (cookie.getName().endsWith("r")) {
                                    intro = this.frontUserService.findById(Integer.parseInt(cookie.getValue()));
                                    break;
                                }
                            }
                        } catch (Exception e) {
                        }

                        fuser = new Fuser();
                        if (intro != null) {
                            fuser.setfIntroUser_id(intro);
                        }
                        fuser.setSalt(Utils.getUUID());
                        fuser.setQqlogin(openID);
                        fuser.setFnickName(nickName);
                        fuser.setFloginName(nickName + "_" + Utils.getRandomString(6));

                        fuser.setFregType(RegTypeEnum.QQ_VALUE);
                        fuser.setFisMailValidate(false);
                        String ip = getIpAddr(request);
                        fuser.setFregIp(ip);
                        fuser.setFlastLoginIp(ip);

                        fuser.setFregisterTime(Utils.getTimestamp());
                        fuser.setFloginPassword(Utils.MD5(openID, fuser.getSalt()));
                        fuser.setFtradePassword(null);
                        fuser.setFstatus(UserStatusEnum.NORMAL_VALUE);
                        fuser.setFlastLoginTime(Utils.getTimestamp());
                        fuser.setFlastUpdateTime(Utils.getTimestamp());
                        boolean saveFlag = false;
                        try {
                            saveFlag = this.frontUserService.saveRegister(fuser);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (saveFlag) {
                            this.SetSession(fuser, request);
                        }

                    } else {
                        //登陆
                        if (fuser.getFstatus() == UserStatusEnum.NORMAL_VALUE) {
                            String ip = getIpAddr(request);
                            fuser.setFlastLoginIp(ip);
                            fuser.setFlastLoginTime(Utils.getTimestamp());
                            this.frontUserService.updateFUser(fuser, null, LogTypeEnum.User_LOGIN, ip);
                            this.SetSession(fuser, request);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        modelAndView.setViewName("redirect:/index.html");
        return modelAndView;
    }


    private String getBankNumber(String bankNumber) {
        if (StringUtils.isEmpty(bankNumber) || bankNumber.length() < 10) {
            return bankNumber;
        }
        StringBuffer bank = new StringBuffer();
        bank.append(bankNumber.substring(0, 4));
        bank.append("******");
        bank.append(bankNumber.substring(bankNumber.length() - 4));
        return bank.toString();
    }


    /**
     * 好友邀请界面
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/invite/invite")
    public ModelAndView user_invite(HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        int introId = 0;
        //推广注册
        try {
            introId = Integer.parseInt(request.getParameter("r"));
            if (introId != 0) {
                Fuser intro = this.frontUserService.findById(introId);
                if (intro == null) {
                    introId = 0;
                }
            }
        } catch (Exception e) {
        }

        String type = request.getParameter("type");
        if(StringUtils.isNotEmpty(type)){
            modelAndView.setViewName("/front/invite/invite_m");
        }else{
            modelAndView.setViewName("/front/invite/invite");
        }

        Fintegralactivity fintegralactivity = this.integralService.getIntegralActivy(IntegralTypeEnum.INVITE_FRIENDS);
        int inviteIntegral = fintegralactivity == null?0:fintegralactivity.getIntegral();
        modelAndView.addObject("inviteIntegral", inviteIntegral) ;

        //获取最低价、最高价
        List<Ftrademapping> list = ftradeMappingService.findActiveTradeMappingByLazy();
        List<FtradeMappingResp> resps = new ArrayList<>();
        for(Ftrademapping ftrademapping:list){
            FtradeMappingResp resp = new FtradeMappingResp();
            Fvirtualcointype fvirtualcointype = ftrademapping.getFvirtualcointypeByFvirtualcointype2();
            resp.setvName(fvirtualcointype.getFname());
            resp.setUrl(fvirtualcointype.getFurl());
            Date d = new Date();
            String end = DateHelper.date2String(d, DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond);
            Date s = new Date(d.getTime() - (long)24 * 60 * 60 * 1000);
            String start = DateHelper.date2String(s, DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond);

            FentrustlogVo vo = this.frontOtherService.queryLowAndHigh(start,end,ftrademapping.getFid());

            if(vo.getAmount().compareTo(BigDecimal.ZERO) == 0){
                resp.setLowPrice(String.valueOf(this.realTimeData.getLatestDealPrize(ftrademapping.getFid())));
                resp.setHiPrice(String.valueOf(this.realTimeData.getLatestDealPrize(ftrademapping.getFid())));
            }else{
                resp.setHiPrice(vo.getAmount().setScale(ftrademapping.getFcount1()).toString());
                resp.setLowPrice(vo.getFees().setScale(ftrademapping.getFcount1()).toString());
            }
            resps.add(resp);

        }

        modelAndView.addObject("trapmaps",resps);


        modelAndView.addObject("introId", introId);
        return modelAndView;
    }
}
