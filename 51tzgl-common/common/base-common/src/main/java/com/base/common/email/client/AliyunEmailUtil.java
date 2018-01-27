package com.base.common.email.client;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.jucaifu.common.property.PropertiesUtils;


/**
 *
 * 阿里发送邮件
 * Created by a123 on 17-7-10.
 */
public class AliyunEmailUtil {

    private static final String NAME = PropertiesUtils.getProperty("alidayu.mailName");

    private static final String PWD = PropertiesUtils.getProperty("alidayu.mailPassword");

    private static final String MailNAME = PropertiesUtils.getProperty("MailName");

    private static final String SMTP = PropertiesUtils.getProperty("alidayu.smtp");

    public static boolean send(String toAddress, String title, String content) {
        boolean flag = false ;

        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", NAME, PWD);
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            request.setAccountName(SMTP);
            request.setFromAlias(MailNAME);
            request.setAddressType(1);
            request.setTagName(null);
            request.setReplyToAddress(true);
            request.setToAddress(toAddress);
            request.setSubject(title);
            request.setHtmlBody(content);
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
            flag = true ;
        } catch (ServerException e) {
            e.printStackTrace();
            flag = false ;
        } catch (ClientException e) {
            e.printStackTrace();
            flag = false ;
        }
        return flag ;
    }

}
