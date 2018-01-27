package com.base.common.sms.client;

import com.base.common.sms.facade.SmsFacade;
import com.base.common.sms.facade.request.SmsParamBean;
import com.base.common.sms.facade.response.SmsResultResp;
import com.base.common.sms.impl.AliDayuSendMsg;
import com.base.common.sms.impl.AliyunSendMsg;
import com.base.common.sms.impl.request.AliDayuBeanReq;
import com.base.common.sms.impl.request.AliDayuParamBean;
import com.jucaifu.common.property.PropertiesUtils;

/**
 * 阿里大于短信发送工具类
 * @author luwei
 * @Date 16-10-27 上午11:19
 */
public class SmsDayuUtils {

    private static final String appkey = PropertiesUtils.getProperty("alidayu.appkey");

    private static final String secret = PropertiesUtils.getProperty("alidayu.secret");

    private static final String freeSignName = PropertiesUtils.getProperty("alidayu.freeSignName");

    private static final String templateCode = PropertiesUtils.getProperty("alidayu.templateCode");

    private static final String templateParam = PropertiesUtils.getProperty("alidayu.templateParam");

    private static final Boolean disabled = Boolean.valueOf(PropertiesUtils.getProperty("alidayu.disabled", "true"));

    private static SmsFacade smsFacade;

    /**
     * 阿里大于，发送短信通知
     * @param phoneNumber
     * @param content
     * @return
     * 注：该模板的变量名称是：content
     */
    public static SmsResultResp sendSms(String phoneNumber, String content) {
        smsFacade = getSmsFacade_Dayu(Boolean.TRUE);
        return send(smsFacade, templateCode, phoneNumber, content);
    }

    /**
     * 阿里大于，发送验证码短信
     * @param phoneNumber
     * @param content
     * @param verTemplateCode
     * @return
     * 注：该模板的变量名称是：content
     */
    public static SmsResultResp sendVerSms(String phoneNumber, String content, String verTemplateCode) {
        smsFacade = getSmsFacade_Dayu(Boolean.TRUE);
        return send(smsFacade, verTemplateCode, phoneNumber, content);
    }

    /**
     * 阿里云发送动态短信(模板，参数随意)
     * @param phoneNumber  手机号
     * @param verTemplateCode  模板
     * @param paramJsonStr  参数值 json串
     * @return
     */
    public static SmsResultResp sendSms(String phoneNumber, String verTemplateCode, String paramJsonStr) {
        smsFacade = getSmsFacade_Dayu(Boolean.FALSE);
        AliDayuBeanReq aliDayuBeanReq = new AliDayuBeanReq();
        aliDayuBeanReq.setTemplateCode(verTemplateCode);
        aliDayuBeanReq.setPhoneNumber(phoneNumber);
        aliDayuBeanReq.setParamJsonStr(paramJsonStr);
        SmsResultResp resp = null;
        if(disabled){  //禁用状态,则模拟发送
            resp = new SmsResultResp();
            resp.setResult(true);
        }else{ //非禁用,则真实发送
            resp = smsFacade.sendMsg(aliDayuBeanReq);
        }
        return resp;
    }

    /**
     * 阿里云发送短信
     * @param phoneNumber
     * @param content
     * @param verTemplateCode  模板code
     * @return
     * 动态传递模板
     */
    public static SmsResultResp sendVerifySms(String phoneNumber, String content, String verTemplateCode) {
        smsFacade = getSmsFacade_Dayu(Boolean.FALSE);
        return send(smsFacade, verTemplateCode, phoneNumber, content);
    }

    /**
     * 阿里云发送短信
     * @param phoneNumber
     * @param content
     * @return
     * 使用配置文件里面的模块
     */
    public static SmsResultResp sendVerifySms(String phoneNumber, String content) {
        smsFacade = getSmsFacade_Dayu(Boolean.FALSE);
        return send(smsFacade, templateCode, phoneNumber, content);
    }


    // 根据手机号、内容发送短信
    private static SmsResultResp send(SmsFacade facade,  String param_templateCode, String phoneNumber, String content) {
        AliDayuBeanReq aliDayuBeanReq = new AliDayuBeanReq();
        aliDayuBeanReq.setTemplateCode(param_templateCode);
        aliDayuBeanReq.setPhoneNumber(phoneNumber);
        aliDayuBeanReq.setParamJsonStr("{" + templateParam + ":'" + content + "'}");
        SmsResultResp resp = null;
        if(disabled){  //禁用状态,则模拟发送
            resp = new SmsResultResp();
            resp.setResult(true);
        }else{ //非禁用,则真实发送
            resp = facade.sendMsg(aliDayuBeanReq);
        }
        return resp;
    }

    //获取的短信发送对象
    private static SmsFacade getSmsFacade_Dayu(boolean isDayu) {
        if(smsFacade != null) {
            return smsFacade;
        }else {
            if(isDayu) {
                smsFacade = new AliDayuSendMsg();
            }else {
                smsFacade = new AliyunSendMsg();
            }
            AliDayuParamBean aliDayuParamBean = new AliDayuParamBean();
            aliDayuParamBean.setAppKey(appkey);
            aliDayuParamBean.setSecret(secret);
            aliDayuParamBean.setFreeSignName(freeSignName);
            smsFacade.initParam(aliDayuParamBean);
        }
        return smsFacade;
    }


    public static void main(String[] args) {

    }


}
