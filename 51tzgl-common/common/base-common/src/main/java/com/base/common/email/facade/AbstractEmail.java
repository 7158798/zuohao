package com.base.common.email.facade;

import com.base.common.email.facade.request.EmailParamBean;
import com.base.common.email.facade.request.SendEmailReq;
import com.base.common.email.facade.response.SendEmailResultResp;
import org.apache.commons.lang.StringUtils;

/**
 * Created by luwei on 17-6-16.
 */
public abstract class AbstractEmail implements SendEmailFacade{

    protected SendEmailResultResp resp = new SendEmailResultResp();

    @Override
    public SendEmailResultResp sendEmail(SendEmailReq req) {
        this.vlidate(req);
        if(resp.isResult()) {
            this.send(req);
        }
        return resp;
    }

    public  abstract SendEmailResultResp send(SendEmailReq req);

    public void vlidate(SendEmailReq req) {
        if(req ==  null) {
            getERROR("参数不能为空");
            return;
        }
        if(StringUtils.isBlank(req.getFromAddress())) {
            getERROR("发件人地址不能为空");
            return;
        }

        if(StringUtils.isBlank(req.getToAddress())) {
            getERROR("收件人地址不能为空");
            return;
        }

        if(StringUtils.isBlank(req.getSubject())) {
            getERROR("邮件主题不能为空");
            return;
        }

        if(StringUtils.isBlank(req.getContent())) {
            getERROR("邮件内容不能为空");
            return;
        }
    }

    protected void getERROR(String error){

        resp.setResult(Boolean.FALSE);
        resp.setMsg(error);
    }
}
