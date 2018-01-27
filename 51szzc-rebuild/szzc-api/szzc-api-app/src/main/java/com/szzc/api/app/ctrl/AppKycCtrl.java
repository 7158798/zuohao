package com.szzc.api.app.ctrl;


import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.api.app.constants.ApiConstants;
import com.szzc.api.app.ctrl.request.BindBankReq;
import com.szzc.api.app.ctrl.request.ValidateKycReq;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by fenggq on 17-3-29.
 */
@Controller
public class AppKycCtrl extends BaseCtrl {

    /**
     * KYC验证银行卡
     */
    @ResponseBody
    @RequestMapping(value = APP_BANK_VALIDATE, method = RequestMethod.POST, produces=JsonEncode_Text)
    public String validateKyc(@RequestBody BindBankReq req) throws Exception {
        LOG.dStart(this, "app验证银行卡开始");
        String result = HttpClientHelper.postJsonParams(buildPostUrl(APP_BANK_VALIDATE), JsonHelper.obj2JsonStr(req));
        LOG.dEnd(this,"app验证银行卡开始");
        return result;
    }


    //kcy验证
    @ResponseBody
    @RequestMapping(value=APP_KCY_VALIDATE,method = RequestMethod.POST, produces=JsonEncode_Text)
    public String validateKyc(@RequestBody ValidateKycReq req) throws Exception{
        LOG.dStart(this, "APPkcy验证");
        String result = HttpClientHelper.postJsonParams(buildPostUrl(APP_KCY_VALIDATE),JsonHelper.obj2JsonStr(req));
        LOG.d(this,result);
        LOG.dEnd(this,"Appkcy验证");
        return result;
    }


    //获取KYC认证信息
    //kcy验证
    @ResponseBody
    @RequestMapping(value=APP_KYC_VALIDATE_INFO,method = RequestMethod.GET ,produces=JsonEncode_Text)
    public String validateKyc(String loginToken){
        LOG.dStart(this, "获取KYC认证信息");
        String result = HttpClientHelper.sendGetRequest(buildGetUrl(APP_KYC_VALIDATE_INFO,loginToken), Boolean.FALSE);
        LOG.d(this,result);
        LOG.dEnd(this,"获取KYC认证信息");
        return result;
    }

}
