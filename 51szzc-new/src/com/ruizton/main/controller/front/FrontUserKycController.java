package com.ruizton.main.controller.front;

import cn.jpush.api.utils.StringUtils;
import com.ruizton.main.Enum.*;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.controller.app.ApiConstant;
import com.ruizton.main.model.FbankinfoWithdraw;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvalidatekyc;
import com.ruizton.main.service.BaseService;
import com.ruizton.util.BankUtil;
import com.ruizton.util.Utils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by a123 on 17-3-29.
 */
@Controller
public class FrontUserKycController extends BaseController{

    //KYC认证
    @RequestMapping("user/realCertification")
    public ModelAndView kycCertification(HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
        modelAndView.addObject("fuser", fuser);

        //状态大于等于2，则查询信息   如果是5,则进入认证1页面需要显示拒绝原因，如果是2、或3、则需要显示银行卡名称及卡号信息
        Fvalidatekyc fvalidatekyc = new Fvalidatekyc();
        if(fuser.getKyclevel() >= ValidateKycLevelEnum.BANKCARD_VALIDATE.getKey()) {
            fvalidatekyc = this.frontValidateKycService.findByUserId(fuser.getFid());
            modelAndView.addObject("fvalidatekyc",fvalidatekyc);
        }

        //如果是审核拒绝，需显示拒绝原因
        if(fuser.getKyclevel() == ValidateKycLevelEnum.NOT_PASS.getKey()) {
            if(fvalidatekyc != null) {
                modelAndView.addObject("reject_info", fvalidatekyc.getCancelReason());
            }
        }

        if (fuser.getKyclevel() == ValidateKycLevelEnum.COMPLETE.getKey() ) {
            modelAndView.setViewName("front/user/kycCertification2");
            modelAndView.addObject("bankName",fvalidatekyc.getBankName());
            modelAndView.addObject("bankAccount",Utils.getAccount(fvalidatekyc.getBankNumber()));
        } else {
            //添加银行卡列表
            Map<Integer, String> bankTypes = new HashMap<Integer, String>();
            for (int i = 1; i <= BankTypeEnum.QT; i++) {
                if (BankTypeEnum.getEnumString(i) != null && BankTypeEnum.getEnumString(i).trim().length() > 0) {
                    bankTypes.put(i, BankTypeEnum.getEnumString(i));
                }
            }
            modelAndView.addObject("bankTypes",bankTypes);
            modelAndView.setViewName("front/user/kycCertification");
        }
        return modelAndView;
    }

    //验证银行卡 KYC
    @ResponseBody
    @RequestMapping(value = "/user/validateBank",method = RequestMethod.POST,produces={JsonEncode})
    public String validateBank(
            @RequestParam String account,
            @RequestParam String phoneCode,
            @RequestParam(required=true)int openBankType,
            @RequestParam(required=true)String address,
            @RequestParam(required=true)String prov,
            @RequestParam(required=true)String city,
            @RequestParam(required=true,defaultValue="0")String dist,
            @RequestParam String phone //手机号
    ) throws Exception{
        JSONObject jsonObject = new JSONObject() ;

        Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
       // Fuser fuser = this.frontUserService.findById(11) ;
        if(StringUtils.isEmpty(phone)){
            if(fuser.isFisTelephoneBind()){
                phone = fuser.getFtelephone();
            }else{
                jsonObject.accumulate("code", -1) ;
  //			jsonObject.accumulate("msg", "请输入手机号") ;
                jsonObject.accumulate(MSG, i18nMsg("please_fill_phone"));
                return jsonObject.toString();
            }
        }

        if(fuser.getKyclevel() == ValidateKycLevelEnum.NO_COMMIT.getKey() || !fuser.getFpostRealValidate()){
            jsonObject.accumulate("code", -1) ;
            //jsonObject.accumulate(MSG, "请先完成身份认证");
            jsonObject.accumulate(MSG, i18nMsg("identyti_first"));
            return jsonObject.toString();
        }

        if(fuser.getKyclevel() != ValidateKycLevelEnum.IDENTITY_VALIDATE.getKey()){
            jsonObject.accumulate("code", -1) ;
            //	jsonObject.accumulate(MSG, "您已完成了银行卡认证");
            jsonObject.accumulate(MSG, i18nMsg("bank_validate_ready"));
            return jsonObject.toString();
        }


        address = HtmlUtils.htmlEscape(address);
        account = HtmlUtils.htmlEscape(account);
        phoneCode = HtmlUtils.htmlEscape(phoneCode);
        prov = HtmlUtils.htmlEscape(prov);
        city = HtmlUtils.htmlEscape(city);
        dist = HtmlUtils.htmlEscape(dist);

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

        int count = this.utilsService.count(" where fuser.fid="+fuser.getFid()+" and fbankType="+openBankType+" and fbankNumber='"+account+"' and fstatus="+ BankInfoWithdrawStatusEnum.NORMAL_VALUE+" ", FbankinfoWithdraw.class) ;

        String ip = getIpAddr(request) ;
        int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;

        if (tel_limit <= 0) {
            jsonObject.accumulate("code", -1);
            jsonObject.accumulate(MSG, i18nMsg("ip_disable"));
            return jsonObject.toString();
        } else {
            if (!validateMessageCode(fuser, "86", phone, MessageTypeEnum.CNY_ACCOUNT_WITHDRAW, phoneCode)) {
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

        //校验银行卡
        JSONObject bankValidate = BankUtil.validate(fuser.getFrealName(),fuser.getFidentityNo(),account,phone);
        if(bankValidate.getInt("result") != 0){
            return bankValidate.toString();
        }

        //成功
        try {

            //绑定银行卡
            if(count == 0){
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

            }
            //kyc 认证记录
            Fvalidatekyc fvalidatekyc = this.frontValidateKycService.findByUserId(fuser.getFid());
            if(fvalidatekyc == null) fvalidatekyc = new Fvalidatekyc();
            fvalidatekyc.setCreateTime(Utils.getTimestamp());
            fvalidatekyc.setBankName(bankName);
            fvalidatekyc.setBankNumber(account);
            fvalidatekyc.setFuser(fuser);
            fvalidatekyc.setStatus(0);

            if(!fuser.isFisTelephoneBind()){
                fuser.setFareaCode("86") ;
                fuser.setFtelephone(phone) ;
                fuser.setFisTelephoneBind(true) ;
                fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
            }

            fuser.setKyclevel(ValidateKycLevelEnum.BANKCARD_VALIDATE.getKey());
            this.frontUserService.updateFuser(fuser,fvalidatekyc);
            jsonObject.accumulate("code", 0) ;
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

    /**
     *  kyc2 认证
     * @param identityUrlOn
     * @param identityUrlOff
     * @param identityHoldUrl
     * @param validateVideoUrl
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/user/validateKyc",method = RequestMethod.POST,produces={JsonEncode})
    public String validateKyc(@RequestParam String identityUrlOn,
                              @RequestParam String identityUrlOff,
                              @RequestParam String identityHoldUrl,
                              @RequestParam String validateVideoUrl){
        JSONObject jsonObject = new JSONObject() ;

        try{
            Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
           // Fuser fuser = this.frontUserService.findById(11) ;

            if(fuser.getKyclevel() == ValidateKycLevelEnum.NO_COMMIT.getKey() || !fuser.getFpostRealValidate()){
                jsonObject.accumulate("code", -1) ;
                jsonObject.accumulate(MSG, i18nMsg("identyti_first"));
                return jsonObject.toString();
            }

            if(fuser.getKyclevel() == ValidateKycLevelEnum.IDENTITY_VALIDATE.getKey()){
                jsonObject.accumulate("code", -1) ;
                jsonObject.accumulate(MSG, "尚未完成银行卡认证");
                return jsonObject.toString();
            }

            if(fuser.getKyclevel() == ValidateKycLevelEnum.COMPLETE.getKey()){
                jsonObject.accumulate("code", -1) ;
                jsonObject.accumulate(MSG, "已经通过了KYC认证");
                return jsonObject.toString();
            }

            Fvalidatekyc fvalidatekyc = this.frontValidateKycService.findByUserId(fuser.getFid());
            fvalidatekyc.setStatus(1);
            fvalidatekyc.setIdentityUrlOn(identityUrlOn);
            fvalidatekyc.setIdentityUrlOff(identityUrlOff);
            fvalidatekyc.setIdentityHoldUrl(identityHoldUrl);
            fvalidatekyc.setValidateVideoUrl(validateVideoUrl);

            fuser.setKyclevel(ValidateKycLevelEnum.AUDITING.getKey());
            this.frontUserService.updateFuser(fuser,fvalidatekyc);

            jsonObject.accumulate("code", 0) ;
            jsonObject.accumulate(MSG, i18nMsg("s_operation_success"));
        }catch (Exception e){
            jsonObject.accumulate("code", -1) ;
            jsonObject.accumulate(MSG, i18nMsg("s_network_anomaly"));
        }
        return jsonObject.toString();
    }



}
