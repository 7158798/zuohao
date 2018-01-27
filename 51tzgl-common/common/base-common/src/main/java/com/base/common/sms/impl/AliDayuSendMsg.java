package com.base.common.sms.impl;

import com.base.common.sms.facade.AbstractSms;
import com.base.common.sms.facade.SmsFacade;
import com.base.common.sms.facade.request.SmsBeanReq;
import com.base.common.sms.facade.request.SmsParamBean;
import com.base.common.sms.impl.request.AliDayuBeanReq;
import com.base.common.sms.impl.request.AliDayuParamBean;
import com.base.common.sms.facade.response.SmsResultResp;


import com.jucaifu.common.util.JsonHelper;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author luwei
 * @Date 16-9-29 上午11:33
 */
public class AliDayuSendMsg extends AbstractSms implements SmsFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(AliDayuSendMsg.class);

    private final String CLASS_NAME = AliDayuSendMsg.class.getSimpleName();

    private final String HOSTURL = "http://gw.api.taobao.com/router/rest";

    private String SMSTYPE = "normal";

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
        LOGGER.info("阿里大鱼短信发送,templateCode:{},paramJsonStr:{},phoneNumber:{}", templateCode, paramJsonStr, phoneNumber);
        resp.setResult(Boolean.FALSE);
        //发短信请求
        TaobaoClient client = new DefaultTaobaoClient(HOSTURL, APPKEY, SECRET);
        AlibabaAliqinFcSmsNumSendRequest request = new AlibabaAliqinFcSmsNumSendRequest();
        request.setExtend("");
        request.setSmsType(SMSTYPE);
        request.setSmsFreeSignName(FREESIGNNAME);
        request.setSmsParamString(paramJsonStr);
        request.setRecNum(phoneNumber);
        request.setSmsTemplateCode(templateCode);
        AlibabaAliqinFcSmsNumSendResponse rsp = null;
        try {
            rsp = client.execute(request);
        } catch (ApiException e) {
            LOGGER.error("阿里大鱼短信发送失败，手机号码[" + phoneNumber + "]，模板code[" + templateCode + "]", e);
        }
        LOGGER.info("阿里大鱼短信发送结果,response:{}", JsonHelper.obj2JsonStr(rsp));
        //数据响应类型转换
        if (rsp != null) {
            if (StringUtils.isNotBlank(rsp.getSubMsg())) {
                if(rsp.getSubMsg().equals("触发业务流控")){
                    resp.setMsg("您操作频繁，请稍作休息。");
                }else{
                    resp.setMsg(rsp.getSubMsg());
                }
            }
            if (StringUtils.isNoneBlank(rsp.getBody())) {
                resp.setBody(rsp.getBody());
            }
        } else {
            LOGGER.error("短信发送失败，响应结果rsp为空");
            resp.setMsg("短信发送失败");
            resp.setResult(false);
            return resp;
        }
        if (rsp.getResult() != null) {
            resp.setResult(rsp.getResult().getSuccess());
        } else {
            resp.setResult(false);
        }
        LOGGER.info("短信发送结果封装,返回给具体的调用方,resp:{}", JsonHelper.obj2JsonStr(resp));
        return resp;
    }

    @Override
    public void vlidate(SmsBeanReq req) {
        AliDayuBeanReq beanReq = (AliDayuBeanReq) req;
        phoneNumber = beanReq.getPhoneNumber();
        templateCode = beanReq.getTemplateCode();
        paramJsonStr = beanReq.getParamJsonStr();
        if (StringUtils.isBlank(phoneNumber)) {
            LOGGER.error(CLASS_NAME + "参数错误,phoneNumber 不能为空");
            resp.setResult(Boolean.FALSE);
            return;
        }

        // 手机号格式检查
        String regexString = "1\\d{10}";
        Pattern pattern = Pattern.compile(regexString);
        String[] phoneArr = phoneNumber.split(",");
        if (phoneArr.length > 200) {
            LOGGER.warn("手机号数量超过最大限制200");
            getERROR("手机号数量超过最大限制200");
            return;
        }
        for (String number : phoneArr) {
            Matcher matcher = pattern.matcher(number);
            if (!matcher.matches()) {
                LOGGER.warn("手机号格式错误,phoneNumber:{}", number);
                getERROR("手机号格式错误");
                return;
            }
        }

        if (StringUtils.isBlank(templateCode)) {
            LOGGER.error(CLASS_NAME + "参数错误,templateCode 不能为空");
            getERROR("短信模板不能为空");
            return;
        }
    }
}
