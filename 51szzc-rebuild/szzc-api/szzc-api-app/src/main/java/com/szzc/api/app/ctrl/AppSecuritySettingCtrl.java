package com.szzc.api.app.ctrl;

import com.jucaifu.common.annotation.token.TokenValidateAnno;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.api.app.constants.ApiConstants;
import com.szzc.api.app.ctrl.request.SecuritySettingReq;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 安全设置
 * Created by zygong on 17-4-1.
 */
@RestController
public class AppSecuritySettingCtrl implements ApiConstants {

    /**
     * 重置交易密码(MessageTypeEnum 20)
     * @param req
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = APP_SECURITYSETTING_RESETTRADEPWD, method = RequestMethod.POST, produces=JsonEncode_Text)
    @ResponseBody
    public String resetTradePwd(@RequestBody SecuritySettingReq req) throws IOException {
        LOG.dStart(this, "重置交易密码");
        String url = BASE_URL + APP_SECURITYSETTING_RESETTRADEPWD + ".html";
        String result = HttpClientHelper.postJsonParams(url, JsonHelper.obj2JsonStr(req));
        LOG.d(this,result);
        LOG.dEnd(this,"重置交易密码");
        return result;
    }

    /**
     * 修改手机号(MessageTypeEnum 2：绑定手机   3：解绑手机)
     * @param req
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = APP_SECURITYSETTING_CHANGEPHONE, method = RequestMethod.POST, produces=JsonEncode_Text)
    @ResponseBody
    public String changePhone(@RequestBody SecuritySettingReq req) throws IOException {
        LOG.dStart(this, "修改手机号");
        String url = BASE_URL + APP_SECURITYSETTING_CHANGEPHONE + ".html";
        String result = HttpClientHelper.postJsonParams(url, JsonHelper.obj2JsonStr(req));
        LOG.d(this,result);
        LOG.dEnd(this,"修改手机号");
        return result;
    }


}
