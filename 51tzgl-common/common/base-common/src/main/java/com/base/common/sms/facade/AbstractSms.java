package com.base.common.sms.facade;

import com.base.common.sms.facade.request.SmsBeanReq;
import com.base.common.sms.facade.response.SmsResultResp;

/**
 * @author luwei
 * @Date 16-9-30 上午10:45
 */
public abstract  class AbstractSms implements SmsFacade{

    protected SmsResultResp resp = new SmsResultResp();
    @Override
    public SmsResultResp sendMsg(SmsBeanReq req) {
        this.vlidate(req);
        if (resp.isResult()){
            this.send();
        }
        return resp;
    }

    public abstract void vlidate(SmsBeanReq req);

    public abstract SmsResultResp send();

    protected void getERROR(String error){

        resp.setResult(Boolean.FALSE);
        resp.setMsg(error);
    }
}
