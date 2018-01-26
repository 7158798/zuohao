package com.ruizton.main.controller.app;

import com.ruizton.main.Enum.*;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.controller.app.request.BindAlipayReq;
import com.ruizton.main.controller.app.request.BindBankReq;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.*;
import com.ruizton.main.model.integral.Fusergrade;
import com.ruizton.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by fenggq on 17-3-21.
 */
@Controller
public class AppAssetsCtrl extends BaseController implements ApiConstants {
    int maxResult = Constant.AppRecordPerPage;

    /**
     * 添加支付宝帐号
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = APP_ALIPAY_ADD_ACCOUNT, method = RequestMethod.POST, produces = JsonEncode)
    public String getAppInfoBankList(@RequestBody BindAlipayReq req) throws Exception {
        LOG.dStart(this, "添加支付宝账户开始");
        String account = req.getAccount();
        String name = req.getName();
        String verificationCode = req.getVerificationCode();

        //验证是否登录
        String token = req.getLoginToken();

//        if (token.length() < 4) {
//            token = this.realTimeData.pushTestAppSession(Integer.parseInt(token));
//        }

        if (!this.realTimeData.isAppLogin(token, true)) {
            return ApiConstant.getNoLoginError();
        }

        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(name)) {
            return ApiConstant.getRequestError("请求参数不能为空");
        }

        if (StringUtils.isEmpty(name)) {
            return ApiConstant.getRequestError("姓名不能为空");
        }

        if (StringUtils.isBlank(account)) {
            return ApiConstant.getRequestError("支付宝帐号不能为空");
        }

        Fuser user = this.realTimeData.getAppFuser(token);
        if (user == null) {
            return ApiConstant.getRequestError("用户不存在");
        }

        //判断数据库中是否存在该帐号
        FalipayBind vo = falipayBindService.findByAccount(account);
        if (vo != null) {
            return ApiConstant.getRequestError("帐号已存在，请核对后再操作");
        }

        //判断支付宝绑定数量是否超过5个
        List<FalipayBind> list = falipayBindService.findListByUserId(user.getFid());
        if (list != null && list.size() > 4) {
            return ApiConstant.getRequestError("绑定支付宝数量超出上限5");
        }

        if(checkParam(verificationCode)) {
            String ip = getIpAddr(request);
            int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE);
            if (tel_limit <= 0) {
                return ApiConstant.getRequestError("此ip操作频繁，请2小时后再试!");
            } else {

                if (!validateMessageCode(user, user.getFareaCode(), user.getFtelephone(), MessageTypeEnum.BANGDING_ADDALIPAY, verificationCode)) {
                    //手機驗證錯誤
                    this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE);
                    return ApiConstant.getRequestError("手机验证码错误，您还有" + tel_limit + "次机会");
                } else {
                    this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE);
                    this.validateMap.removeMessageMap(user.getFid()+"_"+ MessageTypeEnum.BANGDING_ADDALIPAY);
                }
            }
        }

        try {
            Timestamp tm = Timestamp.valueOf(DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));

            FalipayBind install = new FalipayBind();
            install.setFcreateTime(tm);
            install.setFstatus(BankInfoStatusEnum.NORMAL_VALUE);
            install.setVersion(0);
            install.setFname(name);
            install.setFaccount(account);
            install.setFuser(user);
            this.falipayBindService.save(install);

            LOG.d(this.getClass(), "添加支付宝帐号成功");
            return ApiConstant.getSuccessResp();
        } catch (Exception e) {
            return ApiConstant.getUnknownError(e);
        }
    }

    /**
     * 人民币银行卡充值页面初始
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = APP_RECHARGE_CNY_INIT, method = RequestMethod.GET, produces = JsonEncode)
    public String getRechargeInit() throws Exception {
        LOG.dStart(this, "添加支付宝账户开始");

        int type = Integer.parseInt(request.getParameter("type"));
        //验证是否登录
        String token = request.getParameter("loginToken");
        if (token.length() < 4) {
           // token = this.realTimeData.pushTestAppSession(Integer.parseInt(token));
        }
        if (!this.realTimeData.isAppLogin(token, true)) {
            return ApiConstant.getNoLoginError();
        }
        if (type != RemittanceTypeEnum.Type3 && type != RemittanceTypeEnum.Type1) {
            return ApiConstant.getRequestError("类型不正确");
        }
        Fuser user = this.realTimeData.getAppFuser(token);
        if (user == null) {
            return ApiConstant.getRequestError("用户不存在");
        }

        JSONArray jsonArray = new JSONArray();

        if (type == RemittanceTypeEnum.Type3) {
            //用户绑定的支付宝账户信息
            List<FalipayBind> list = falipayBindService.findListByUserId(user.getFid());
            for (FalipayBind falipayBind : list) {
                JSONObject js = new JSONObject();
                js.accumulate("bankId",falipayBind.getFid());
                js.accumulate("account", falipayBind.getFaccount());
                js.accumulate("name", falipayBind.getFname());
                jsonArray.add(js);
            }
        } else if (type == RemittanceTypeEnum.Type1) {
            //用户绑定的银行卡信息
            String filters = "where fuser.fid=" + user.getFid() + " and fbankType >0 order by fid desc ";
            List<FbankinfoWithdraw> fbankinfoWithdraws = this.frontUserService.findFbankinfoWithdrawByFuser(0, 0, filters, false);
            for (FbankinfoWithdraw fbankinfoWithdraw : fbankinfoWithdraws) {
                JSONObject js = new JSONObject();
                int l = fbankinfoWithdraw.getFbankNumber().length();
                js.accumulate("bankId",fbankinfoWithdraw.getFid());
                js.accumulate("account", fbankinfoWithdraw.getFbankNumber().substring(l - 4, l));
                js.accumulate("name", fbankinfoWithdraw.getFname());
                jsonArray.add(js);
            }
        }

        Fusergrade fusergrade = this.userGradeService.findById(user.getFscore().getFlevel());

        //最小充值金额

        String minRecharge = "";
        String maxRecharge = "";
        if(type == RemittanceTypeEnum.Type3){
            minRecharge = this.constantMap.get("minalipayrgc").toString();
            maxRecharge = this.constantMap.get("maxalipayrgc").toString();
        }else{
            minRecharge = this.constantMap.get("minrechargecny").toString();
        }

        String maxwithdrawcny =  this.constantMap.get("maxwithdrawcny").toString().trim();
        String minwithdrawcny =  this.constantMap.get("minwithdrawcny").toString().trim();

        double fee = fusergrade == null?0.0d:fusergrade.getFoutfee().doubleValue();

        int feeLimit = Integer.parseInt(ConstantMap.get("cnyfeeLimit").toString());
        double cnyfeeLimit = feeLimit == 0?0.0d:5.0d;

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("accountlist", jsonArray.toArray());
        jsonObject.accumulate("minRecharge", minRecharge);
        if(type == RemittanceTypeEnum.Type3){
            jsonObject.accumulate("maxRecharge", maxRecharge);
        }
        jsonObject.accumulate("fee",Utils.decimalFormat(fee,4));
        jsonObject.accumulate("cnyfeeLimit",cnyfeeLimit);
        jsonObject.accumulate("maxwithdrawcny",maxwithdrawcny);
        jsonObject.accumulate("minwithdrawcny",minwithdrawcny);
        LOG.d(this.getClass(), jsonObject);
        return ApiConstant.getSuccessResp(jsonObject);
    }


    /**
     * 绑定银行卡
     */
    @ResponseBody
    @RequestMapping(value = APP_BANK_ADD_ACCOUNT, method = RequestMethod.POST, produces = JsonEncode)
    public String updateOutAddress(@RequestBody BindBankReq req) throws Exception {
        LOG.d(this, "app绑定银行卡开始");

        //验证是否登录
        String token = req.getLoginToken();
//        if (token.length() < 4) {
//            token = this.realTimeData.pushTestAppSession(Integer.parseInt(token));
//        }
        if (!this.realTimeData.isAppLogin(token, true)) {
            return ApiConstant.getNoLoginError();
        }
        Fuser fuser = this.realTimeData.getAppFuser(token);
        if (fuser == null) {
            return ApiConstant.getNoLoginError();
        }
        if (req == null) {
            return ApiConstant.getRequestError("请求参数为空");
        }

        String address = req.getAddress();
        String account = req.getAccount();
        String phoneCode = req.getPhoneCode();
        String city = req.getCity();
        int openBankType = req.getOpenBankType();

        if (!fuser.isFisTelephoneBind() && StringUtils.isBlank(req.getPhone())) {
            return ApiConstant.getRequestError("没有绑定手机号码");
        }
        if (account.length() < 10) {
            return ApiConstant.getRequestError("银行帐号不合法");
        }

        if (address.length() > 300) {
            return ApiConstant.getRequestError("详细地址太长");
        }


        String bankName = BankTypeEnum.getEnumString(openBankType);
        if (bankName == null || bankName.trim().length() == 0) {
            return ApiConstant.getRequestError("开户银行有误");
        }

        String validateMsg = "";
        String phone = StringUtils.isBlank(req.getPhone()) ? fuser.getFtelephone() : req.getPhone();

        //校验银行卡
        try {
            String ret = BankUtil.validate(account);
            JSONObject retj = JSONObject.fromObject(ret);
            boolean flag = false;
            if (retj.getInt("error_code") != 0) {
                flag = true;
                validateMsg = "银行卡号错误，请联系客服";
            } else {
                String retBank = retj.getJSONObject("result").getString("bank");
                if (retBank.indexOf(bankName) == -1 && openBankType != BankTypeEnum.QT) {
                    validateMsg = "银行卡号码或者开户行不匹配";
                    flag = true;
                }
            }
            if (flag == true) {
                return ApiConstant.getRequestError(validateMsg);
            }
        } catch (Exception e1) {
            return ApiConstant.getRequestError("银行卡号码或者开户行不匹配");
        }

        int count = this.utilsService.count(" where fuser.fid=" + fuser.getFid() + " and fbankType=" + openBankType + " and fbankNumber='" + account + "' and fstatus=" + BankInfoWithdrawStatusEnum.NORMAL_VALUE + " ", FbankinfoWithdraw.class);
        if (count > 0) {
            return ApiConstant.getRequestError("银行卡号码已经存在");
        }

        count = this.utilsService.count(" where fuser.fid="+fuser.getFid()+" and fstatus="+BankInfoWithdrawStatusEnum.NORMAL_VALUE+" ", FbankinfoWithdraw.class) ;
        if(count>4){
          return ApiConstant.getRequestError("绑定银行卡数量已超过5张限制");
        }

        String ip = getIpAddr(request);
//		int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
        int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE);


        if (tel_limit <= 0) {
            return ApiConstant.getRequestError("此ip操作频繁，请2小时后再试!");
        } else {

            if (!validateMessageCode(fuser, fuser.getFareaCode(), phone, MessageTypeEnum.CNY_ACCOUNT_WITHDRAW, phoneCode)) {
                //手機驗證錯誤
                this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE);
                return ApiConstant.getRequestError("手机验证码错误，您还有" + tel_limit + "次机会");
            } else {
                this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE);
                this.validateMap.removeMessageMap(fuser.getFid()+"_"+ MessageTypeEnum.CNY_ACCOUNT_WITHDRAW);
            }
        }

        //成功
        try {
            FbankinfoWithdraw fbankinfoWithdraw = new FbankinfoWithdraw();
            fbankinfoWithdraw.setFbankNumber(account);
            fbankinfoWithdraw.setFbankType(openBankType);
            fbankinfoWithdraw.setFcreateTime(Utils.getTimestamp());
            fbankinfoWithdraw.setFname(bankName);
            fbankinfoWithdraw.setFstatus(BankInfoStatusEnum.NORMAL_VALUE);
            fbankinfoWithdraw.setFaddress(city);
            fbankinfoWithdraw.setFothers(address);
            fbankinfoWithdraw.setFuser(fuser);
            this.frontUserService.updateBankInfoWithdraw(fbankinfoWithdraw);
            //添加银行卡===WEB首次送积分
            this.integralService.addUserIntegralFirst(IntegralTypeEnum.INFOBANK_FIRST, fuser.getFid(), 0);

            return ApiConstant.getSuccessResp();


        } catch (Exception e) {
            return ApiConstant.getUnknownError(e);
        } finally {
            this.validateMap.removeMessageMap(fuser.getFid() + "_" + MessageTypeEnum.CNY_ACCOUNT_WITHDRAW);
        }

        //  return jsonObject.toString() ;
    }


    //充值提现记录,type:1人民币充值，2人民币提现，3虚拟币充值，4虚拟币提现
    @ResponseBody
    @RequestMapping(value = APP_RECHARGE_RECORD, method = RequestMethod.GET, produces = JsonEncode)
    public String GetAllRecords(
            HttpServletRequest request
    ) throws Exception {
        try {
            Integer type = Integer.parseInt(request.getParameter("type"));
            Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));

            Integer symbol = 0;

            if(type == 3 || type == 4){
                 symbol = Integer.parseInt(request.getParameter("symbol"));
            }

            //验证是否登录
            String token = request.getParameter("loginToken");
//            if (token.length() < 4) {
//                token = this.realTimeData.pushTestAppSession(Integer.parseInt(token));
//            }
            if (!this.realTimeData.isAppLogin(token, true)) {
                return ApiConstant.getNoLoginError();
            }
            Fuser fuser = this.realTimeData.getAppFuser(token);
            if (fuser == null) {
                return ApiConstant.getNoLoginError();
            }
            JSONObject jsonObject = new JSONObject();

            try {
                if (type == 1) {//人民币充值
                    StringBuffer filter = new StringBuffer();
                    filter.append("where fuser.fid=" + fuser.getFid() + " \n");
                    filter.append("and ftype =" + CapitalOperationTypeEnum.RMB_IN + "\n");
                    filter.append(" order by fid desc \n");
                    List<Fcapitaloperation> list = this.capitaloperationService.list((currentPage - 1) * maxResult, maxResult, filter.toString(), true);
                    int totalCount = this.adminService.getAllCount("Fcapitaloperation", filter.toString());
                    int totalPage = totalCount / maxResult + ((totalCount % maxResult) == 0 ? 0 : 1);


                    jsonObject.accumulate("currentPage", currentPage);
                    jsonObject.accumulate("totalPage", totalPage);

                    JSONArray jsonArray = new JSONArray();

                    for (int i = 0; i < list.size(); i++) {
                        Fcapitaloperation fcapitaloperation = list.get(i);

                        JSONObject item = new JSONObject();
                        String bank = fcapitaloperation.getfBank();
                        if (StringUtils.isNotBlank(bank)) {
                            if (bank.equals(RemittanceTypeEnum.getEnumString(3))) {
                                bank = Utils.getAccount(fcapitaloperation.getfAccount());
                            }else{
                                bank += "(尾号"+Utils.getBankEnd(fcapitaloperation.getfAccount())+")";
                            }
                        }
                        item.accumulate("account", bank);
                        item.accumulate("amount", fcapitaloperation.getFamount());
                        item.accumulate("date", Utils.dateFormat(fcapitaloperation.getFcreateTime()));
                        item.accumulate("status",CapitalOperationInStatus.getFrontStatus(fcapitaloperation.getFstatus()));
                        item.accumulate("imgurl", OSSPostObject.URL + "//static/app/images/logo_rmb.png");
                        item.accumulate("operationId",fcapitaloperation.getFid());
                        item.accumulate("payType",RemittanceTypeEnum.getIsAliypay(fcapitaloperation.getFremittanceType()));
                        jsonArray.add(item);
                    }

                    jsonObject.accumulate("list", jsonArray);
                } else if (type == 2) {//人民币提现

                    StringBuffer filter = new StringBuffer();
                    filter.append("where fuser.fid=" + fuser.getFid() + " \n");
                    filter.append("and ftype =" + CapitalOperationTypeEnum.RMB_OUT + "\n");
                    filter.append(" order by fid desc \n");
                    List<Fcapitaloperation> list = this.capitaloperationService.list((currentPage - 1) * maxResult, maxResult, filter.toString(), true);
                    int totalCount = this.adminService.getAllCount("Fcapitaloperation", filter.toString());
                    int totalPage = totalCount / maxResult + ((totalCount % maxResult) == 0 ? 0 : 1);


                    jsonObject.accumulate("currentPage", currentPage);
                    jsonObject.accumulate("totalPage", totalPage);

                    JSONArray jsonArray = new JSONArray();

                    for (int i = 0; i < list.size(); i++) {
                        Fcapitaloperation fcapitaloperation = list.get(i);

                        String account = fcapitaloperation.getfBank();
                        account += "(尾号"+Utils.getBankEnd(fcapitaloperation.getfAccount())+")";

                        JSONObject item = new JSONObject();
                        item.accumulate("account", account);
                        item.accumulate("amount", fcapitaloperation.getFamount());
                        item.accumulate("date", Utils.dateFormat(fcapitaloperation.getFcreateTime()));
                        item.accumulate("status", CapitalOperationOutStatus.getFrontStatus(fcapitaloperation.getFstatus()));
                        item.accumulate("imgurl",OSSPostObject.URL + "//static/app/images/logo_rmb.png");
                        item.accumulate("operationId",fcapitaloperation.getFid());
                        jsonArray.add(item);
                    }

                    jsonObject.accumulate("list", jsonArray);

                } else if (type == 3) {
                    //虚拟币充值
                    Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol);
                    StringBuffer filter = new StringBuffer();
                    filter.append("where fuser.fid=" + fuser.getFid() + " \n");
                    filter.append("and ftype =" + VirtualCapitalOperationTypeEnum.COIN_IN + "\n");
                    filter.append(" and fvirtualcointype.fid = "+fvirtualcointype.getFid()+ "\n");
                    filter.append(" order by fid desc \n");

                    List<Fvirtualcaptualoperation> list =
                            this.utilsService.list(
                                    (currentPage - 1) * maxResult, maxResult, filter.toString(), false, Fvirtualcaptualoperation.class);
                    int totalCount = this.adminService.getAllCount("Fvirtualcaptualoperation", filter.toString());
                    int totalPage = totalCount / maxResult + ((totalCount % maxResult) == 0 ? 0 : 1);


                    jsonObject.accumulate("currentPage", currentPage);
                    jsonObject.accumulate("totalPage", totalPage);

                    JSONArray jsonArray = new JSONArray();

                    for (int i = 0; i < list.size(); i++) {
                        Fvirtualcaptualoperation fvirtualcaptualoperation = list.get(i);
                        JSONObject item = new JSONObject();
                        item.accumulate("account",fvirtualcaptualoperation.getRecharge_virtual_address());
                        item.accumulate("amount", fvirtualcaptualoperation.getFamount());
                        item.accumulate("date", Utils.dateFormat(fvirtualcaptualoperation.getFcreateTime()));
                        item.accumulate("status",  VirtualCapitalOperationInStatusEnum.getFrontStatus(fvirtualcaptualoperation.getFstatus()));
                        item.accumulate("imgurl",fvirtualcointype.getFurl());
                        item.accumulate("operationId",fvirtualcaptualoperation.getFid());
                        jsonArray.add(item);
                    }

                    jsonObject.accumulate("list", jsonArray);
                } else if (type == 4) {

                    //虚拟币提现
                    Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol);
                    StringBuffer filter = new StringBuffer();
                    filter.append("where fuser.fid=" + fuser.getFid() + " \n");
                    filter.append("and ftype =" + VirtualCapitalOperationTypeEnum.COIN_OUT + "\n");
                    filter.append(" and fvirtualcointype.fid = "+fvirtualcointype.getFid()+ "\n");
                    filter.append(" order by fid desc \n");

                    List<Fvirtualcaptualoperation> list =
                            this.utilsService.list(
                                    (currentPage - 1) * maxResult, maxResult, filter.toString(), true, Fvirtualcaptualoperation.class);
                    int totalCount = this.adminService.getAllCount("Fvirtualcaptualoperation", filter.toString());
                    int totalPage = totalCount / maxResult + ((totalCount % maxResult) == 0 ? 0 : 1);


                    jsonObject.accumulate("currentPage", currentPage);
                    jsonObject.accumulate("totalPage", totalPage);

                    JSONArray jsonArray = new JSONArray();

                    for (int i = 0; i < list.size(); i++) {
                        Fvirtualcaptualoperation fvirtualcaptualoperation = list.get(i);

                        JSONObject item = new JSONObject();
                        item.accumulate("account",fvirtualcaptualoperation.getWithdraw_virtual_address());
                        item.accumulate("amount", fvirtualcaptualoperation.getFamount());
                        item.accumulate("date", Utils.dateFormat(fvirtualcaptualoperation.getFcreateTime()));
                        item.accumulate("status", VirtualCapitalOperationOutStatusEnum.getFrontStatus(fvirtualcaptualoperation.getFstatus()));
                        item.accumulate("imgurl",fvirtualcointype.getFurl());
                        item.accumulate("operationId",fvirtualcaptualoperation.getFid());
                        jsonArray.add(item);
                    }

                    jsonObject.accumulate("list", jsonArray);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return ApiConstant.getUnknownError(e);
        }

    }


    //获取充值、提现相关文案   1.支付宝充值  2.银行卡  3.人民币提现  4.虚拟币提现
    @ResponseBody
    @RequestMapping(value = APP_RECHARGE_REMIND, method = RequestMethod.GET, produces = JsonEncode)
    public String getRechargeRemind(@RequestParam int type){
        String code = "";

        switch (type){
            case 1:
                code = "rechageZFBDesc";
                break;
            case 2:
                code = "rechageBankDesc";
                break;
            case 3:
                code = "withdrawCNYDesc";
                break;
            case 4:
                code = "withdrawCOINDesc";
                break;
            default:
                code = "";
        }
        String desc = ConstantMap.getString(code);

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("desc",desc);
        return jsonObject.toString();
    }

    @ResponseBody
    @RequestMapping(value = APP_RECHARGE_DETAIL, method = RequestMethod.GET, produces = JsonEncode)
    public String getRechargeDetail(@RequestParam String loginToken,
                                    @RequestParam int operationId){

        if (StringUtils.isNotBlank(loginToken) && loginToken.length() < 4) {
           // loginToken = this.realTimeData.pushTestAppSession(Integer.parseInt(loginToken));
        }
        Fuser fuser = this.realTimeData.getAppFuser(loginToken);
        if (fuser == null) {
            return ApiConstant.getNoLoginError();
        }
        Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(operationId) ;

        if(fcapitaloperation == null){
            return ApiConstant.getRequestError("充值记录不存在");
        }

        if(fuser.getFid() != fcapitaloperation.getFuser().getFid()){
            return ApiConstant.getRequestError("非本人记录不能查询");
        }

        JSONObject jsonObject = new JSONObject() ;
        jsonObject.accumulate("money", String.valueOf(fcapitaloperation.getFamount())) ;
        if(fcapitaloperation.getSystembankinfo() != null){
            jsonObject.accumulate("fbankName", fcapitaloperation.getSystembankinfo().getFbankName()) ;
            jsonObject.accumulate("fownerName", fcapitaloperation.getSystembankinfo().getFownerName()) ;
            jsonObject.accumulate("fbankAddress", fcapitaloperation.getSystembankinfo().getFbankAddress()) ;
            jsonObject.accumulate("fbankNumber", fcapitaloperation.getSystembankinfo().getFbankNumber()) ;
        }else{
            jsonObject.accumulate("fbankName", "") ;
            jsonObject.accumulate("fownerName", "") ;
            jsonObject.accumulate("fbankAddress", "") ;
            jsonObject.accumulate("fbankNumber", "") ;
        }
        jsonObject.accumulate("account", fcapitaloperation.getfAccount()) ;
        jsonObject.accumulate("remark",fcapitaloperation.getFid());
        return jsonObject.toString() ;

    }




    public void addConstant(HttpServletRequest request){}

}
