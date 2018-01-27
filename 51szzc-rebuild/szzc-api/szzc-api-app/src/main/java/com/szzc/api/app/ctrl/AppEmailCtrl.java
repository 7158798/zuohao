package com.szzc.api.app.ctrl;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.api.app.constants.ApiConstants;
import com.szzc.api.app.ctrl.request.AppEmailReq;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 邮箱
 * Created by zygong on 17-3-30.
 */
@Controller
public class AppEmailCtrl implements ApiConstants {

    /**
     * 绑定邮箱
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = APP_EMAIL_ADDEMAIL, method = RequestMethod.POST, produces=JsonEncode_Text)
    public String addEmail(@RequestBody AppEmailReq req) throws IOException {
        LOG.dStart(this, "绑定邮箱");
        String url = BASE_URL + APP_EMAIL_ADDEMAIL + ".html";
        String result = HttpClientHelper.postJsonParams(url, JsonHelper.obj2JsonStr(req));
        LOG.d(this,result);
        LOG.dEnd(this,"绑定邮箱");
        return result;
    }

    /**
     * 发送邮箱
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = APP_EMAIL_SENDEMAIL, method = RequestMethod.POST, produces=JsonEncode_Text)
    public String sendEmail(@RequestBody AppEmailReq req) throws IOException {
        LOG.dStart(this, "发送邮箱");
        String url = BASE_URL + APP_EMAIL_SENDEMAIL + ".html";
        String result = HttpClientHelper.postJsonParams(url, JsonHelper.obj2JsonStr(req));
        LOG.d(this,result);
        LOG.dEnd(this,"发送邮箱");
        return result;
    }
}