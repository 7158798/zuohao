package com.szzc.api.app.ctrl;

import com.jucaifu.common.annotation.token.TokenValidateAnno;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import com.szzc.api.app.constants.ApiConstants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by lx on 17-5-22.
 */
@RestController
public class Tests implements ApiConstants{

    @TokenValidateAnno(skip = true)
    @ResponseBody
    @RequestMapping(value = APP_TEST, method = RequestMethod.GET, produces = JsonEncode)
    public String test(String loginToken){
        LOG.dStart(this, "获取问题反馈类型");
        String url = BASE_URL + APP_FEEDBACK_TITLE + ".html?loginToken=" + loginToken;
        String result = HttpClientHelper.sendGetRequest(url, Boolean.FALSE);
        LOG.d(this,result);
        LOG.dEnd(this,"获取问题反馈类型");
        return result;
    }
}
