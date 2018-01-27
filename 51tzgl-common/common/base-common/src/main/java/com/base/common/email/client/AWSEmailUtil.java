package com.base.common.email.client;

import com.base.common.email.facade.SendEmailFacade;
import com.base.common.email.facade.request.SendEmailReq;
import com.base.common.email.facade.response.SendEmailResultResp;
import com.base.common.email.impl.AWSEmailFacadeImpl;
import com.base.common.email.impl.request.AWSEmailParamBean;
import com.jucaifu.common.property.PropertiesUtils;

/**
 * aws 亚马逊云 发送邮件工具包
 * Created by luwei on 17-6-16.
 */
public class AWSEmailUtil {


    private static String accessKey = PropertiesUtils.getProperty("aws.accessKey", "");

    private static String secretKey = PropertiesUtils.getProperty("aws.secretKey", "");

    private static String emailRegion = PropertiesUtils.getProperty("aws.email.region", "");

    private static String fromAddress = PropertiesUtils.getProperty("email.from.address", "");

    private static SendEmailFacade emailFacade;



    public static SendEmailResultResp sendEmail(SendEmailReq req) {
        emailFacade = new AWSEmailFacadeImpl();
        AWSEmailParamBean paramBean = new AWSEmailParamBean();
        paramBean.setAccessKey(accessKey);
        paramBean.setSecretKey(secretKey);
        paramBean.setEmailRegion(emailRegion);
        emailFacade.initParam(paramBean);
        SendEmailResultResp resp = emailFacade.sendEmail(req);
        return resp;
    }


    //为兼容原aws邮件迁移而重写
    public static boolean sendEmail(String toEmail, String subject, String content) {
        SendEmailReq req = new SendEmailReq(subject, content, toEmail, fromAddress);
        SendEmailResultResp resp = sendEmail(req);
        return resp.isResult();
    }
}
