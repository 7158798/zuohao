package com.base.common.email.impl;

import com.base.common.email.facade.AbstractEmail;
import com.base.common.email.facade.SendEmailFacade;
import com.base.common.email.facade.request.EmailParamBean;
import com.base.common.email.facade.request.SendEmailReq;
import com.base.common.email.facade.response.SendEmailResultResp;
import com.base.common.email.impl.request.AzureEmailParamBean;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.sendgrid.*;
import org.apache.commons.lang.StringUtils;

/**
 * 微软云 邮件发送
 * Created by luwei on 17-6-16.
 */
public class AzureEmailFacadeImpl extends AbstractEmail implements SendEmailFacade {

    private String apiKey;

    @Override
    public SendEmailFacade initParam(EmailParamBean req) {
        AzureEmailParamBean azureEmailParamBean = (AzureEmailParamBean) req;
        this.apiKey = azureEmailParamBean.getApiKey();
        return this;
    }

    @Override
    public SendEmailResultResp send(SendEmailReq req) {

        Email from = new Email(req.getFromAddress());
        String subject = req.getSubject();
        Email to = new Email(req.getToAddress());
        Content content = new Content("text/plain", req.getContent());
        Mail mail = new Mail(from, subject, to , content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response  =  sg.api(request);
            LOG.e(AzureEmailFacadeImpl.class, "邮件发送返回：" + JsonHelper.obj2JsonStr(response));
            if(response.getStatusCode() == 202) {
                resp.setResult(Boolean.TRUE);
            }
            resp.setBody(response.getBody());
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("邮件发送异常");
        }

        return resp;
    }
}
