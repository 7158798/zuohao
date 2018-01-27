package com.base.common.email.impl;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.base.common.email.facade.AbstractEmail;
import com.base.common.email.facade.SendEmailFacade;
import com.base.common.email.facade.request.EmailParamBean;
import com.base.common.email.facade.request.SendEmailReq;
import com.base.common.email.facade.response.SendEmailResultResp;
import com.base.common.email.impl.request.AWSEmailParamBean;
import com.jucaifu.common.log.LOG;
import org.apache.commons.lang3.StringUtils;

/**
 * 亚马逊云 邮件发送
 * Created by luwei on 17-6-16.
 */
public class AWSEmailFacadeImpl extends AbstractEmail implements SendEmailFacade {

    private String accessKey;

    private String secretKey;

    private String emailRegion;


    @Override
    public SendEmailFacade initParam(EmailParamBean req) {
        AWSEmailParamBean paramBean = (AWSEmailParamBean) req;
        accessKey = paramBean.getAccessKey();
        secretKey = paramBean.getSecretKey();
        emailRegion = paramBean.getEmailRegion();
        return this;
    }

    @Override
    public SendEmailResultResp send(SendEmailReq req) {
        //创建亚马逊发送邮件对象
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(awsCreds);
        AmazonSimpleEmailServiceClientBuilder emailServiceClientBuilder = AmazonSimpleEmailServiceClientBuilder.standard();
        emailServiceClientBuilder.setRegion(emailRegion);  //注：特殊限制，只有3个选择  US_EAST_1美东（弗吉尼亚州）
        emailServiceClientBuilder.withCredentials(credentialsProvider);
        AmazonSimpleEmailService ses = emailServiceClientBuilder.build();
        //发送邮件
        try {
            Destination destination = new Destination().withToAddresses(new String[]{req.getToAddress()});
            Content subject = new Content().withData(req.getSubject());
            Content textBody = new Content().withData(req.getContent());
            Body body = new Body().withText(textBody);
            Message message = new Message().withSubject(subject).withBody(body);
            SendEmailRequest request = new SendEmailRequest().withSource(req.getFromAddress()).withDestination(destination).withMessage(message);
            SendEmailResult result = ses.sendEmail(request);
            if(StringUtils.isNoneBlank(result.getMessageId())) {
                resp.setResult(Boolean.TRUE);
            }
            //发送完成，关闭流
            ses.shutdown();
        }catch (Exception e) {
            getERROR("亚马逊发送邮件异常");
            LOG.e(AWSEmailFacadeImpl.class, e.getMessage());
        }

        return resp;
    }


}
