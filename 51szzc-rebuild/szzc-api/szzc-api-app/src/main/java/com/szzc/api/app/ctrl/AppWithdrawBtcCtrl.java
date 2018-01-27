package com.szzc.api.app.ctrl;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.api.app.constants.ApiConstants;
import com.szzc.api.app.ctrl.request.VirtualcaptualoperationReq;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * app端提币等一系列操作
 * Created by luwei on 17-5-26.
 */
@Controller
public class AppWithdrawBtcCtrl implements ApiConstants {


    //取消提币
    @ResponseBody
    @RequestMapping(value = APP_CANCEL_WITHDRAW_BTC, method = RequestMethod.POST, produces=JsonEncode_Text)
    public String cancelWithdrawBtc(@RequestBody VirtualcaptualoperationReq req) throws Exception {
        LOG.dStart(this, "取消提币");
        String url = BASE_URL + APP_CANCEL_WITHDRAW_BTC + ".html";
        String result = HttpClientHelper.postJsonParams(url, JsonHelper.obj2JsonStr(req));
        LOG.d(this,result);
        LOG.dEnd(this,"取消提币");
        return result;
    }

}
