package com.base.common.email.client;

import com.base.common.email.facade.SendEmailFacade;
import com.base.common.email.facade.request.EmailParamBean;
import com.base.common.email.facade.request.SendEmailReq;
import com.base.common.email.facade.response.SendEmailResultResp;
import com.base.common.email.impl.AzureEmailFacadeImpl;
import com.base.common.email.impl.request.AzureEmailParamBean;
import com.jucaifu.common.property.PropertiesUtils;

/**
 * Azure 微软云 发送邮件工具包
 * Created by luwei on 17-6-16.
 */
public class AzureEmailUtil {

    private static String apiKey = PropertiesUtils.getProperty("auzre.email.apikey", "");

    private static String fromAddress = PropertiesUtils.getProperty("email.from.address", "");

    private static SendEmailFacade emailFacade;

    public static SendEmailResultResp sendEmail(SendEmailReq req) {
        //创建对象，并初始化对象信息
        emailFacade = new AzureEmailFacadeImpl();
        AzureEmailParamBean paramBean = new AzureEmailParamBean(apiKey);
        emailFacade.initParam(paramBean);
        //发送邮件请求
        SendEmailResultResp resp =  emailFacade.sendEmail(req);
        return resp;
    }

    //为兼容otc，从原aws邮件迁移而重写
    public static boolean sendEmail(String toEmail, String subject, String content) {
        SendEmailReq req = new SendEmailReq(subject, content, toEmail, fromAddress);
        SendEmailResultResp resp = sendEmail(req);
        return resp.isResult();
    }

}
