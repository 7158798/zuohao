package com.szzc.api.app.ctrl;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.api.app.constants.ApiConstants;
import com.szzc.api.app.ctrl.request.CapitaloperationReq;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 人民币充值系列操作接口
 * Created by luwei on 17-5-26.
 */
@Controller
public class AppRechargeCnyCtrl  implements ApiConstants {


    @ResponseBody
    @RequestMapping( value = APP_CANCEL_RECHARGE, method = RequestMethod.POST, produces=JsonEncode_Text)
    public String cancelRechargeCnySubmit(@RequestBody CapitaloperationReq req) throws Exception{
        LOG.dStart(this, "取消充值");
        String url = BASE_URL + APP_CANCEL_RECHARGE + ".html";
        String result = HttpClientHelper.postJsonParams(url, JsonHelper.obj2JsonStr(req));
        LOG.d(this,result);
        LOG.dEnd(this,"取消充值");
        return result;

    }


}
