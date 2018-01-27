package com.szzc.api.app.ctrl;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zygong on 17-3-28.
 */
@Controller
public class AppMemberCentreCtrl extends BaseCtrl  {

    /**
     * 获取会员中心数据
     * @param loginToken
     * @return
     */
    @RequestMapping(value = APP_MEMBERCENTRE_DATA, method = RequestMethod.GET ,produces=JsonEncode_Text)
    @ResponseBody
    public String getData(String loginToken){
        LOG.dStart(this, "获取会员中心数据");
        String result = HttpClientHelper.sendGetRequest(buildGetUrl(APP_MEMBERCENTRE_DATA,loginToken), Boolean.FALSE);
        LOG.d(this,result);
        LOG.dEnd(this,"获取会员中心数据");
        return result;
    }
}
