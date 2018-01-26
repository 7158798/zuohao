package com.ruizton.main.controller.app;

import com.ruizton.main.Enum.*;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.controller.app.request.AppBaseReq;
import com.ruizton.main.controller.app.request.BindBankReq;
import com.ruizton.main.controller.app.request.ValidateKycReq;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.FbankinfoWithdraw;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvalidatekyc;
import com.ruizton.util.BankUtil;
import com.ruizton.util.Utils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by fenggq on 17-3-29.
 */
@Controller
public class AppKycCtrl extends BaseController implements ApiConstants{

    /**
     * KYC验证银行卡
     */
    @ResponseBody
    @RequestMapping(value = APP_BANK_VALIDATE, method = RequestMethod.POST, produces = JsonEncode)
    public String validateKyc(@RequestBody BindBankReq req) throws Exception {
        LOG.d(this, "app验证银行卡开始");

        //验证是否登录
        String token = req.getLoginToken();
        if (token.length() == 2) {
          //  token = this.realTimeData.pushTestAppSession(Integer.parseInt(token));
        }
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
        String phone = req.getPhone();
        int openBankType = req.getOpenBankType();

        if (StringUtils.isEmpty(phone)) {
            return ApiConstant.getRequestError("预留手机号不能为空");
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

        int count = this.utilsService.count(" where fuser.fid=" + fuser.getFid() + " and fbankType=" + openBankType + " and fbankNumber='" + account + "' and fstatus=" + BankInfoWithdrawStatusEnum.NORMAL_VALUE + " ", FbankinfoWithdraw.class);

        String ip = getIpAddr(request);
        int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE);

        if (tel_limit <= 0) {
            return ApiConstant.getRequestError("此ip操作频繁，请2小时后再试!");
        } else {

            if (!validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.CNY_ACCOUNT_WITHDRAW, phoneCode)) {
                //手機驗證錯誤
                this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE);
                return ApiConstant.getRequestError("手机验证码错误，您还有" + tel_limit + "次机会");
            } else {
                this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE);
            }
        }

        //校验银行卡
        JSONObject bankValidate = BankUtil.validate(fuser.getFrealName(),fuser.getFidentityNo(),account,phone);
        if(bankValidate.getInt("result") != 0){
            return ApiConstant.getRequestError(bankValidate.getString("msg"));
        }

        //成功
        try {

            if(count == 0){
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

                try{
                    //添加银行卡===WEB首次送积分
                    this.integralService.addUserIntegralFirst(IntegralTypeEnum.INFOBANK_FIRST, fuser.getFid(), 0);
                }catch (Exception e){}
            }
            //获取用户
            fuser = this.frontUserService.findById(fuser.getFid());

            //kyc 认证记录
            Fvalidatekyc fvalidatekyc = this.frontValidateKycService.findByUserId(fuser.getFid());
            if(fvalidatekyc == null) fvalidatekyc = new Fvalidatekyc();
            fvalidatekyc.setCreateTime(Utils.getTimestamp());
            fvalidatekyc.setBankName(bankName);
            fvalidatekyc.setBankNumber(account);
            fvalidatekyc.setFuser(fuser);
            fvalidatekyc.setStatus(0);

            fuser.setKyclevel(ValidateKycLevelEnum.BANKCARD_VALIDATE.getKey());
            this.frontUserService.updateFuser(fuser,fvalidatekyc);
            return ApiConstant.getSuccessResp();
        } catch (Exception e) {
            return ApiConstant.getUnknownError(e);
        } finally {
            this.validateMap.removeMessageMap(fuser.getFid() + "_" + MessageTypeEnum.CNY_ACCOUNT_WITHDRAW);
        }
    }


    //kcy验证
    @ResponseBody
    @RequestMapping(value=APP_KCY_VALIDATE,method = RequestMethod.POST,produces={JsonEncode})
    public String validateKyc(@RequestBody ValidateKycReq req){
        JSONObject jsonObject = new JSONObject() ;

        try{
            //验证是否登录
            String token = req.getLoginToken();
            if (token.length() == 2) {
             //   token = this.realTimeData.pushTestAppSession(Integer.parseInt(token));
            }
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

            if(StringUtils.isEmpty(req.getIdentityUrlOn())){
                return ApiConstant.getRequestError("身份证正面不能为空");
            }
            if(StringUtils.isEmpty(req.getIdentityUrlOn())){
                return ApiConstant.getRequestError("身份证反面面不能为空");
            }
            if(StringUtils.isEmpty(req.getIdentityUrlOn())){
                return ApiConstant.getRequestError("手持身份证不能为空");
            }
            if(StringUtils.isEmpty(req.getIdentityUrlOn())){
                return ApiConstant.getRequestError("视频认证不能为空");
            }

            if(fuser.getKyclevel() == ValidateKycLevelEnum.NO_COMMIT.getKey() || !fuser.getFpostRealValidate()){
                return ApiConstant.getRequestError("请先进行身份认证");
            }

            if(fuser.getKyclevel() == ValidateKycLevelEnum.IDENTITY_VALIDATE.getKey()){
                return ApiConstant.getRequestError("请先进行银行卡认证");
            }

            if(fuser.getKyclevel() == ValidateKycLevelEnum.COMPLETE.getKey() || fuser.getKyclevel() ==ValidateKycLevelEnum.AUDITING.getKey() ){
                return ApiConstant.getRequestError("您已经完成了认证");
            }

            Fvalidatekyc fvalidatekyc = this.frontValidateKycService.findByUserId(fuser.getFid());

            if(fvalidatekyc == null){
                fvalidatekyc = new Fvalidatekyc();
            }
            fvalidatekyc.setStatus(1);
            fvalidatekyc.setIdentityUrlOn(req.getIdentityUrlOn());
            fvalidatekyc.setIdentityUrlOff(req.getIdentityUrlOff());
            fvalidatekyc.setIdentityHoldUrl(req.getIdentityHoldUrl());
            fvalidatekyc.setValidateVideoUrl(req.getValidateVideoUrl());

            fuser.setKyclevel(ValidateKycLevelEnum.AUDITING.getKey());
            this.frontUserService.updateFuser(fuser,fvalidatekyc);

            return ApiConstant.getSuccessResp();
        }catch (Exception e){
            return ApiConstant.getUnknownError(e);
        }
    }


    //获取KYC认证信息
    //kcy验证
    @ResponseBody
    @RequestMapping(value=APP_KYC_VALIDATE_INFO,method = RequestMethod.GET,produces={JsonEncode})
    public String validateKyc(){
        JSONObject jsonObject = new JSONObject() ;
        //验证是否登录
        String token = request.getParameter(LoginToken);
        if (token.length() == 2) {
            token = this.realTimeData.pushTestAppSession(Integer.parseInt(token));
        }
        if (!this.realTimeData.isAppLogin(token, true)) {
            return ApiConstant.getNoLoginError();
        }
        Fuser fuser = this.realTimeData.getAppFuser(token);
        if (fuser == null) {
            return ApiConstant.getNoLoginError();
        }
        Fvalidatekyc kycinfo = this.fvalidateKycService.findByUserId(fuser.getFid());

        jsonObject.accumulate("name",fuser.getFrealName());
        jsonObject.accumulate("identityNo",Utils.getAccount(fuser.getFidentityNo()));
        jsonObject.accumulate("bankName",kycinfo == null?"":kycinfo.getBankName());
        jsonObject.accumulate("bankAccount",kycinfo==null?"":Utils.getAccount(kycinfo.getBankNumber()));

        return jsonObject.toString();
    }

}
