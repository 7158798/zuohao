package com.ruizton.util;

import java.io.IOException;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class sendSMS {

	private static final Logger LOGGER = LoggerFactory.getLogger(sendSMS.class);

	private static final String CLASS_NAME = sendSMS.class.getSimpleName();

	/**
	 * @param args
	 * @throws IOException
	 * 
	 */
	public static int send(String appkey,String secret,String url,String sms_template_code,String webName,String code,String tel) throws IOException {
		int result = -1;
		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName(webName);
//			req.setSmsParamString("{\"name\":\""+code+"\"}");
//			req.setRecNum(tel);
//			req.setSmsTemplateCode(sms_template_code);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//
			LOGGER.info(CLASS_NAME + " send,短信发送开始");
	        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",appkey, secret);
	         DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms", url);
	        IAcsClient client = new DefaultAcsClient(profile);
	        SingleSendSmsRequest request = new SingleSendSmsRequest();
	        try {
	            request.setSignName(webName);
				request.setTemplateCode(sms_template_code);
	            request.setParamString("{\"name\":\""+code+"\"}");
	            request.setRecNum(tel);
	            SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
				result = 1;
	        } catch (ServerException e) {
				LOGGER.info(CLASS_NAME + " send,短信发送异常",e);
	            e.printStackTrace();
	        }
	        catch (ClientException e) {
				LOGGER.info(CLASS_NAME + " send,短信发送异常，key,secret,url配置参数",e);
	            e.printStackTrace();
	        }
		} catch (Exception e) {
			LOGGER.info(CLASS_NAME + " send,短信发送异常",e);
			e.printStackTrace();
		}
		return  result;

	}

}
