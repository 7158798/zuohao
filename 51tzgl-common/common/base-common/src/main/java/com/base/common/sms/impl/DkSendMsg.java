package com.base.common.sms.impl;

import com.base.common.sms.facade.AbstractSms;
import com.base.common.sms.facade.SmsFacade;
import com.base.common.sms.facade.request.SmsBeanReq;
import com.base.common.sms.facade.request.SmsParamBean;
import com.base.common.sms.facade.response.SmsResultResp;

import com.base.common.sms.impl.request.DkBeanReq;
import com.base.common.sms.impl.request.DkParamBean;
import com.jucaifu.common.network.HttpClientHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author luwei
 * @Date 16-9-29 上午10:41
 */
public class DkSendMsg extends AbstractSms implements SmsFacade{


    private static final Logger LOGGER = LoggerFactory.getLogger(DkSendMsg.class);

    private final String CLASS_NAME = DkSendMsg.class.getSimpleName();

    private final String HOSTURL = "http://175.102.15.131:80/msg/HttpSendSM";

    private String ACCOUNT = null;

    private String PWD = null;

    private Boolean DISABLED = Boolean.TRUE;

    private String phoneNumber;

    private String content;



    @Override
    public SmsFacade initParam(SmsParamBean req) {
        DkParamBean initReq = (DkParamBean) req;
        ACCOUNT = initReq.getAccount();
        PWD = initReq.getPwd();
        DISABLED = initReq.getDisabled();
        return this;
    }



    @Override
    public SmsResultResp send() {
        resp.setResult(Boolean.FALSE);
        if (DISABLED) {
            resp.setResult(Boolean.TRUE);
            LOGGER.info("手机号码：{}，短信内容：{}", phoneNumber, content);
            return resp;
        }

        Map<String, String> requestParams = new HashMap<>();

        requestParams.put("account", ACCOUNT);
        requestParams.put("pswd", PWD);
        requestParams.put("mobile", phoneNumber);
        requestParams.put("msg", content);
        requestParams.put("needstatus", Boolean.TRUE.toString());

        try {
            String result = HttpClientHelper.post(HOSTURL, requestParams);
            String[] lines = result.split("\\n");
            if (lines.length == 2) {
                resp.setResult(Boolean.TRUE);
                resp.setBody(result);
            } else {
                LOGGER.error("点客通道短信发送错误，手机号码[" + phoneNumber + "]，短信内容[" + content + "]，返回字符串[" + result + "]");
            }
        } catch (Exception e) {
            LOGGER.error("点客通道短信发送失败，手机号码[" + phoneNumber + "]，短信内容[" + content + "]", e);
        }

        return resp;
    }


    @Override
    public void vlidate(SmsBeanReq req) {
        DkBeanReq dkBean = (DkBeanReq)req;
        phoneNumber = dkBean.getPhoneNumber();
        content = dkBean.getContent();
        if(StringUtils.isBlank(phoneNumber)){
            LOGGER.error(CLASS_NAME + "参数错误,phoneNumber 不能为空");
            getERROR("手机号为空");
            return;
        }

        // 手机号格式检查
        String regexString = "1\\d{10}";
        Pattern pattern = Pattern.compile(regexString);
        Matcher matcher = pattern.matcher(phoneNumber);
        if(!matcher.matches()){
            LOGGER.warn("手机号格式错误,phoneNumber:{}", phoneNumber);
             getERROR("手机号格式错误");
             return;
        }

        // 内容校验
        if (StringUtils.isBlank(content)) {
            LOGGER.warn("短信发送内容为空，手机号码：{}", phoneNumber);
            getERROR("短信发送内容为空");
            return;
        }
    }
}
