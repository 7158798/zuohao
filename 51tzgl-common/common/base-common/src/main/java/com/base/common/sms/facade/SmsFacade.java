package com.base.common.sms.facade;

import com.base.common.sms.facade.request.SmsBeanReq;
import com.base.common.sms.facade.request.SmsParamBean;
import com.base.common.sms.facade.response.SmsResultResp;

/**
 * @author luwei
 * @Date 16-9-29 上午10:08
 */
public interface SmsFacade {

    SmsFacade initParam(SmsParamBean req);

    /**短信发送**/
    SmsResultResp sendMsg(SmsBeanReq req);
}
