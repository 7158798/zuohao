package com.base.common.sms.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import com.base.common.sms.facade.AbstractSms;
import com.base.common.sms.facade.SmsFacade;
import com.base.common.sms.facade.request.SmsBeanReq;
import com.base.common.sms.facade.request.SmsParamBean;
import com.base.common.sms.facade.response.SmsResultResp;
import com.base.common.sms.impl.request.AliDayuBeanReq;
import com.base.common.sms.impl.request.AliDayuParamBean;
import com.jucaifu.common.log.LOG;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by luwei on 17-7-10.
 */
public class AliyunSendMsg extends AbstractSms implements SmsFacade {

    private final String url = "sms.aliyuncs.com";

    private String APPKEY;

    private String SECRET;

    private String FREESIGNNAME;

    private String templateCode;

    private String paramJsonStr = "";

    private String phoneNumber;

    @Override
    public SmsFacade initParam(SmsParamBean req) {
        AliDayuParamBean initReq = (AliDayuParamBean) req;
        this.APPKEY = initReq.getAppKey();
        this.SECRET = initReq.getSecret();
        this.FREESIGNNAME = initReq.getFreeSignName();
        return this;
    }

    @Override
    public SmsResultResp send() {
        resp.setResult(Boolean.FALSE);

        try {
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", APPKEY, SECRET);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms", url);
            IAcsClient client = new DefaultAcsClient(profile);
            SingleSendSmsRequest request = new SingleSendSmsRequest();
            request.setSignName(FREESIGNNAME);
            request.setTemplateCode(templateCode);
            request.setParamString(paramJsonStr);
            request.setRecNum(phoneNumber);
            SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
            resp.setResult(Boolean.TRUE);
        }catch (Exception e) {
            LOG.e(this, "短信发送失败", e);
        }

        return resp;
    }

    @Override
    public void vlidate(SmsBeanReq req) {
        AliDayuBeanReq beanReq = (AliDayuBeanReq) req;
        phoneNumber = beanReq.getPhoneNumber();
        templateCode = beanReq.getTemplateCode();
        paramJsonStr = beanReq.getParamJsonStr();
        if (StringUtils.isBlank(phoneNumber)) {
            LOG.d(this,  "参数错误,phoneNumber 不能为空");
            resp.setResult(Boolean.FALSE);
            return;
        }

        // 手机号格式检查
        String regexString = "1\\d{10}";
        Pattern pattern = Pattern.compile(regexString);
        String[] phoneArr = phoneNumber.split(",");
        if (phoneArr.length > 200) {
            LOG.w(this, "手机号数量超过最大限制200");
            getERROR("手机号数量超过最大限制200");
            return;
        }
        for (String number : phoneArr) {
            Matcher matcher = pattern.matcher(number);
            if (!matcher.matches()) {
                LOG.w(this, "手机号格式错误,phoneNumber:"+number );
                getERROR("手机号格式错误");
                return;
            }
        }

        if (StringUtils.isBlank(templateCode)) {
            LOG.d(this,  "参数错误,templateCode 不能为空");
            getERROR("短信模板不能为空");
            return;
        }
    }


}
