package com.szzc.api.app.ctrl;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.api.app.constants.ApiConstants;
import com.szzc.api.app.ctrl.request.AlipayReq;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by luwei on 17-5-24.
 */
@Controller
public class AppAlipayCtrl implements ApiConstants {


    /**
     * 支付宝充值
     * @return
     */
    @ResponseBody
    @RequestMapping(value = APP_ALIPAY_ORDER, method = RequestMethod.POST, produces=JsonEncode_Text)
    public String alipayorder(@RequestBody AlipayReq req) throws Exception{
        LOG.dStart(this, "支付宝充值开始");
        String url = BASE_URL + APP_ALIPAY_ORDER + ".html";
        String result = HttpClientHelper.postJsonParams(url, JsonHelper.obj2JsonStr(req));
        LOG.d(this,result);
        LOG.dEnd(this,"支付宝充值结束");
        return result;
    }





}
