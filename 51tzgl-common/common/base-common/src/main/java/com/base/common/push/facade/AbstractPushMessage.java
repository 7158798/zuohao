package com.base.common.push.facade;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import com.base.common.push.facade.constant.PushConstant;
import com.base.common.push.facade.enums.TargetPlatform;
import com.base.common.push.facade.exception.PushBizException;
import com.base.common.push.facade.messsage.PushMessage;
import com.base.common.push.facade.response.PushMessageDetail;
import com.base.common.push.facade.response.PushMessageResp;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;


/**
 * 推送消息抽象类
 * Created by liuxun on 16-9-26.
 */
public abstract class AbstractPushMessage implements PushMessageFactory,PushConstant {

    protected PushMessageResp resp = new PushMessageResp();
    // 推送的参数
    protected Map push_param;
    // 推送的host
    protected String push_host;
    protected String userAgent;

    @Override
    public <T extends PushMessage> PushMessageResp pushMessage(T message) {
        try{
            Integer targetPlatform = message.getTargetPlatform();
            if (TargetPlatform.isAllTargetPlatform(targetPlatform) || TargetPlatform.isAndroidTargetPlatform(targetPlatform)) {
                // 推送android
                wrapByAndroid(message);
                pushMessage(TargetPlatform.ANDROID.getType());
            }
            if (TargetPlatform.isAllTargetPlatform(targetPlatform) || TargetPlatform.isIosTargetPlatform(targetPlatform)) {
                // 推送IOS
                wrapByIos(message);
                pushMessage(TargetPlatform.IOS.getType());
            }
        } catch (Exception ex){
            LOG.e(getClass(),ex.getMessage(),ex);
            if (ex instanceof PushBizException){
                getErrorResp(ex.getMessage());
            } else {
                getErrorResp(MSG_PUSH_ERROR);
            }
        }
        return resp;
    }

    /**
     * 通用推送消息
     * @return
     */
    private PushMessageResp pushMessage(Integer targetPlatform) throws IOException {

        String result;
        result = HttpClientHelper.post(push_host, push_param, userAgent);
        LOG.i(getClass(), "请求接口的结果：" + result);
        PushMessageDetail detail = analyzeResult(result);
        detail.setTargetPlatform(targetPlatform);
        if (!detail.getResult()){
            resp.setResult(Boolean.FALSE);
            resp.setMsg("推送失败");
        }
        detail.setBody(result);
        resp.getBody().add(detail);
        return resp;
    }

    /**
     * 包装android发送信息
     * @param message  消息bean
     * @return
     */
    public abstract  <T extends PushMessage> void wrapByAndroid(T message);

    /**
     * 包装ios发送参数
     * @param message
     * @return
     */
    public abstract <T extends PushMessage> void wrapByIos(T message);

    /**
     * 解析发送的结果
     * @param result
     */
    public abstract PushMessageDetail analyzeResult(String result);


    /**
     * 获取响应错误对象
     * @param errorMsg
     */
    protected PushMessageResp getErrorResp(String errorMsg){

        resp.setResult(Boolean.FALSE);
        resp.setMsg(errorMsg);
        return resp;
    }

   /* @Override
    public <T extends PushMessage> PushMessageResp pushSingleMessage(T message) {
        if (message.getDeviceId() == null || message.getDeviceId().length() == 0){
            return getErrorResp(MSG_DEVICE_ID_EMPTY);
        }
        return pushMessage(message);
    }

    @Override
    public <T extends PushMessage> PushMessageResp delayPushMessage(T message) {
        if (message.getSendDate() == null){
            return getErrorResp(MSG_SENDDATE_EMPTY);
        }
        return pushMessage(message);
    }*/

}
