package com.base.common.sms.client;

import com.base.common.sms.facade.SmsFacade;
import com.base.common.sms.facade.response.SmsResultResp;
import com.base.common.sms.impl.AWSSendMsg;
import com.base.common.sms.impl.request.AWSParamBean;
import com.base.common.sms.impl.request.AWSSmsBeanReq;
import com.jucaifu.common.property.PropertiesUtils;

/**
 * Created by luwei on 17-6-21.
 */
public class AWSSmsUtils {

    private static final String accessKey = PropertiesUtils.getProperty("aws.accessKey", "");

    private static final String secretKey = PropertiesUtils.getProperty("aws.secretKey", "");

    private static final String smsRegion = PropertiesUtils.getProperty("aws.sms.region", "");

    private static final String smsSignature =  PropertiesUtils.getProperty("aws.sms.signature", "");

    private static final Boolean disabled = Boolean.valueOf(PropertiesUtils.getProperty("aws.sms.disabled", "true"));

    public static SmsFacade smsFacade;

    public static SmsResultResp sendSms(String phoneNumber, String content) {
        return sendSms(phoneNumber,content, smsSignature);

    }

    public static SmsResultResp sendSms(String phoneNumber, String content, String subject) {
        smsFacade = new AWSSendMsg();
        AWSParamBean paramBean = new AWSParamBean();
        paramBean.setAccessKey(accessKey);
        paramBean.setSecretKey(secretKey);
        paramBean.setSmsRegion(smsRegion);
        paramBean.setSmsSignature(smsSignature);
        smsFacade.initParam(paramBean);

        AWSSmsBeanReq awsSmsBeanReq = new AWSSmsBeanReq();
        awsSmsBeanReq.setContent(content);
        awsSmsBeanReq.setPhoneNumber(phoneNumber);
        awsSmsBeanReq.setSubject(subject);
        SmsResultResp resp = null;
        if(disabled){  //禁用状态,则模拟发送
            resp = new SmsResultResp();
            resp.setResult(true);
        }else{ //非禁用,则真实发送
            resp = smsFacade.sendMsg(awsSmsBeanReq);
        }
        return resp;

    }



}
