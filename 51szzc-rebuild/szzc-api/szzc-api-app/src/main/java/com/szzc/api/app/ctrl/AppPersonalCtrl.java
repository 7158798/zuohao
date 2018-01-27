package com.szzc.api.app.ctrl;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.api.app.constants.ApiConstants;
import com.szzc.api.app.ctrl.request.PersonalReq;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * 个人信息操作
 * Created by zygong on 17-3-28.
 */
@Controller
public class AppPersonalCtrl implements ApiConstants {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "yyyyMMddHHmmsss");

    /**
     * 头像上传
     * @param req
     * @return
     */
    @RequestMapping(value = APP_PERSONAL_UPLOADAVATAR, method = RequestMethod.POST, produces=JsonEncode_Text)
    @ResponseBody
    public String uploadAvatar(@RequestBody PersonalReq req) throws IOException {
        LOG.dStart(this, "头像上传");
        String url = BASE_URL + APP_PERSONAL_UPLOADAVATAR + ".html";
        String result = HttpClientHelper.postJsonParams(url, JsonHelper.obj2JsonStr(req));
        LOG.d(this,result);
        LOG.dEnd(this,"头像上传");
        return result;
    }


    /**
     * 获取用户信息
     * @param loginToken
     * @return
     */
    @RequestMapping(value = APP_PERSONAL_GETUSERINFO, method = RequestMethod.GET ,produces=JsonEncode_Text)
    @ResponseBody
    public String getUserInfo(String loginToken) {
        LOG.dStart(this, "获取用户信息");
        String url = BASE_URL + APP_PERSONAL_GETUSERINFO + ".html?loginToken=" + loginToken;
        String result = HttpClientHelper.sendGetRequest(url, Boolean.FALSE);
        LOG.d(this,result);
        LOG.dEnd(this,"获取用户信息");
        return result;
    }
}
