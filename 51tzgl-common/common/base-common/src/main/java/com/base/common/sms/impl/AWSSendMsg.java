package com.base.common.sms.impl;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.base.common.sms.facade.AbstractSms;
import com.base.common.sms.facade.SmsFacade;
import com.base.common.sms.facade.request.SmsBeanReq;
import com.base.common.sms.facade.request.SmsParamBean;
import com.base.common.sms.facade.response.SmsResultResp;
import com.base.common.sms.impl.request.AWSParamBean;
import com.base.common.sms.impl.request.AWSSmsBeanReq;
import com.base.common.sms.impl.request.DkBeanReq;
import com.jucaifu.common.log.LOG;
import org.apache.commons.lang3.StringUtils;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by luwei on 17-6-21.
 */
public class AWSSendMsg extends AbstractSms implements SmsFacade{



    private String accessKey;

    private String secretKey;

    private String smsRegion;

    private String smsSignature;

    //发送手机号
    private String phoneNumber;

    //发送内容
    private String content;

    //发送主题
    private String subject;

    @Override
    public SmsFacade initParam(SmsParamBean req) {
        AWSParamBean paramBean = (AWSParamBean) req;
        this.accessKey = paramBean.getAccessKey();
        this.secretKey = paramBean.getSecretKey();
        this.smsRegion = paramBean.getSmsRegion();
        this.smsSignature = paramBean.getSmsSignature();
        return this;
    }

    @Override
    public void vlidate(SmsBeanReq req) {
        AWSSmsBeanReq awsBeanReq = (AWSSmsBeanReq) req;
        // 内容校验
        if (StringUtils.isBlank(content)) {
            LOG.i("短信发送内容为空，手机号码：{}", phoneNumber);
            getERROR("短信发送内容为空");
            return;
        }
        //国际站手机号无法判断，此处不做处理
        phoneNumber = awsBeanReq.getPhoneNumber();
        content = awsBeanReq.getContent();
        subject = awsBeanReq.getSubject();
    }

    @Override
    public SmsResultResp send() {
        BasicAWSCredentials temp_awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        AWSStaticCredentialsProvider temp_credentialsProvider = new AWSStaticCredentialsProvider(temp_awsCreds);
        AmazonSNSClientBuilder snsClientBuilderder = AmazonSNSClientBuilder.standard();
        snsClientBuilderder.setRegion(smsRegion);  //Regions.AP_SOUTHEAST_1.getName()
        snsClientBuilderder.withCredentials(temp_credentialsProvider);
        AmazonSNS sns = snsClientBuilderder.build();
        try {
            LOG.i(AWSSendMsg.class, "短信发送内容：" + content + "，手机号：" + phoneNumber);

            //短信内容为空
            if(StringUtils.isBlank("content")) {
                LOG.e(AWSSendMsg.class, "短信内容为空");
                resp.setMsg("短信内容为空");
                resp.setResult(Boolean.FALSE);
                return resp;
            }

            Map<String, MessageAttributeValue> smsAttributes = new HashMap<String, MessageAttributeValue>();
            /**
             * 发送人id，至少1个字母，最大长度11位，不能有空格。若不指定，则会显示一长串代码作为id，例如：852-64512514
             * 130-0260-6256，某些国家不支持senderId，中国不支持，但其它国家有支持的，所以给值
             */
            smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
                    .withStringValue("iblockex") //The sender ID shown on the device.
                    .withDataType("String"));
            /**
             * sms消息金额，如果超额，则不发送
             */
            smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
                    .withStringValue("0.50") //Sets the max price to 0.50 USD.
                    .withDataType("Number"));

            //消息类型：Promotional 营销信息   Transactional 重要信息
            smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
                    .withStringValue("Promotional") //Sets the type to promotional.
                    .withDataType("String"));

            //publishRequest 请求
            PublishRequest publishRequest = new PublishRequest();

            //判断短信内容是否带签名
            if(content.indexOf("【"+smsSignature+"】") == -1) {
                content = "【"+smsSignature+"】"+" "+content;
            }

            LOG.i(AWSSendMsg.class, "短信发送内容:" + content);

            publishRequest.setMessage(content);
            publishRequest.setPhoneNumber(phoneNumber);
            publishRequest.setSubject(StringUtils.isNotBlank(subject) == true ? subject : smsSignature);  //该字段目前好像没有用
            publishRequest.setMessageAttributes(smsAttributes);
       /* String arn = "arn:aws:iam::526831967977:role/SNSSuccessFeedback ";
        publishRequest.setTopicArn(arn);*/  //topic是根据主题批量发送，phoneNumber是单个发送，二者只能选择一个

            PublishResult result = sns.publish(publishRequest);
            LOG.i(AWSSendMsg.class, "send sms " + result);
            resp.setMsg(result.getMessageId());
            resp.setResult(Boolean.TRUE);
            sns.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            LOG.e(AWSSendMsg.class, e.getMessage());
        }

        return resp;
    }
}
