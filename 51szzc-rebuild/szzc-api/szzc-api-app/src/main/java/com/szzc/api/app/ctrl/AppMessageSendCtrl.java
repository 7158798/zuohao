package com.szzc.api.app.ctrl;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.api.app.constants.ApiConstants;
import com.szzc.api.app.ctrl.request.AppPushReq;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 价格提示
 * Created by zygong on 17-4-12.
 */
@Controller
public class AppMessageSendCtrl implements ApiConstants {

    /**
     * 获取设置详情
     * @param loginToken    token
     * @return
     */
    @RequestMapping(value = APP_SENDMESSAGE_GETDETAIL, method = RequestMethod.GET ,produces=JsonEncode_Text)
    @ResponseBody
    public String getDetail(String loginToken) {
        LOG.dStart(this, "获取设置详情");
        String url = BASE_URL + APP_SENDMESSAGE_GETDETAIL + ".html?loginToken=" + loginToken;
        String result = HttpClientHelper.sendGetRequest(url, Boolean.FALSE);
        LOG.d(this,result);
        LOG.dEnd(this,"获取设置详情");
        return result;
    }

    /**
     * 价格提示发送短信
     * @param appPush   价格区间推送
     * @return
     */
    @RequestMapping(value = APP_SENDMESSAGE_SENDSMS, method = RequestMethod.POST, produces=JsonEncode_Text)
    @ResponseBody
    public String sendSMS(@RequestBody AppPushReq appPush) throws IOException {
        LOG.dStart(this, "价格提示发送短信");
        String url = BASE_URL + APP_SENDMESSAGE_SENDSMS + ".html";
        String result = HttpClientHelper.postJsonParams(url, JsonHelper.obj2JsonStr(appPush));
        LOG.d(this,result);
        LOG.dEnd(this,"价格提示发送短信");
        return result;
    }

    /**
     * 价格提示推送
     * @param appPush
     * @return
     */
    @RequestMapping(value = APP_SENDMESSAGE_PUSHMESSAGE, method = RequestMethod.POST, produces=JsonEncode_Text)
    @ResponseBody
    public String pushMessage(@RequestBody AppPushReq appPush) throws IOException {
        LOG.dStart(this, "价格提示推送");
        String url = BASE_URL + APP_SENDMESSAGE_PUSHMESSAGE + ".html";
        String result = HttpClientHelper.postJsonParams(url, JsonHelper.obj2JsonStr(appPush));
        LOG.d(this,result);
        LOG.dEnd(this,"价格提示推送");
        return result;
    }

}
