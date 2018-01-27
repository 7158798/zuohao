package com.szzc.api.app.ctrl;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.api.app.constants.ApiConstants;
import com.szzc.api.app.ctrl.request.FeedbackReq;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * 问题反馈模块  app接口
 * Created by zygong on 17-3-27.
 */
@Controller
public class AppFeedBackCtrl implements ApiConstants {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");

    int maxResult = 20;

    /**
     * 保存问题反馈
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = APP_FEEDBACK_ADD, method = RequestMethod.POST, produces=JsonEncode_Text)
    public String saveFeedback(@RequestBody FeedbackReq req) throws IOException {
        LOG.dStart(this, "保存问题反馈");
        String url = BASE_URL + APP_FEEDBACK_ADD + ".html";
        String result = HttpClientHelper.postJsonParams(url, JsonHelper.obj2JsonStr(req));
        LOG.d(this,result);
        LOG.dEnd(this,"保存问题反馈");
        return result;
    }

    /**
     * 获取问题反馈类型
     * @param loginToken
     * @return
     */
    @ResponseBody
    @RequestMapping(value = APP_FEEDBACK_TITLE, method = RequestMethod.GET ,produces=JsonEncode_Text)
    public String getTitle(String loginToken){
        LOG.dStart(this, "获取问题反馈类型");
        String url = BASE_URL + APP_FEEDBACK_TITLE + ".html?loginToken=" + loginToken;
        String result = HttpClientHelper.sendGetRequest(url, Boolean.FALSE);
        LOG.d(this,result);
        LOG.dEnd(this,"获取问题反馈类型");
        return result;
    }

    /**
     * 获取问题反馈列表
     * @param loginToken
     * @return
     */
    @ResponseBody
    @RequestMapping(value = APP_FEEDBACK_LIST, method = RequestMethod.GET ,produces=JsonEncode_Text)
    public String getQuestionList(String loginToken, Integer currentPage){
        LOG.dStart(this, "获取问题反馈列表");
        String url = BASE_URL + APP_FEEDBACK_LIST + ".html?loginToken=" + loginToken + "&currentPage=" + currentPage;
        String result = HttpClientHelper.sendGetRequest(url, Boolean.FALSE);
        LOG.d(this,result);
        LOG.dEnd(this,"获取问题反馈列表");
        return result;
    }


}