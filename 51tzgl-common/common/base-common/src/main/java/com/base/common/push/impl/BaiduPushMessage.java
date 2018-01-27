package com.base.common.push.impl;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.base.common.push.facade.AbstractPushMessage;
import com.base.common.push.facade.PushMessageFactory;
import com.base.common.push.facade.exception.PushBizException;
import com.base.common.push.facade.messsage.PushBean;
import com.base.common.push.facade.messsage.PushMessage;
import com.base.common.push.facade.response.PushMessageDetail;
import com.base.common.push.facade.response.PushMessageResp;
import com.base.common.push.impl.constant.BaiduConstants;
import com.base.common.push.impl.constant.ContentType;
import com.base.common.push.impl.request.BaiduMessage;
import com.jucaifu.common.constants.StringPool;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.util.JsonHelper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 百度推送
 * Created by liuxun on 16-9-29.
 */
public class BaiduPushMessage extends AbstractPushMessage implements BaiduConstants {

    private String IOS_API_KEY;
    private String IOS_SECRET_KEY;
    private String ANDROID_API_KEY;
    private String ANDROID_SECRET_KEY;
    private Integer DEPLOY_STATUS;

    @Override
    public PushMessageFactory bulid(PushBean bean) {
        this.IOS_API_KEY = bean.getIos_api_key();
        this.IOS_SECRET_KEY = bean.getIos_secret_key();
        this.ANDROID_API_KEY = bean.getAndroid_api_key();
        this.ANDROID_SECRET_KEY = bean.getAndroid_secret_key();
        this.DEPLOY_STATUS = bean.getDeploy_status();
        userAgent = USER_AGENT;
        return this;
    }

    @Override
    public <T extends PushMessage> void wrapByAndroid(T message) {

        if (message instanceof BaiduMessage){
            try {
                wrapByAndroid((BaiduMessage) message);
            } catch (Exception e) {
                throw new PushBizException(MSG_ANDROID_ERROR,e);
            }
        } else {
            throw new PushBizException(MSG_NOT_BAIDUMESSAGE);
        }
    }

    @Override
    public <T extends PushMessage> void wrapByIos(T message) {

        if (message instanceof BaiduMessage){
            try {
                wrapByIos((BaiduMessage) message);
            } catch (Exception e) {
                throw new PushBizException(MSG_IOS_ERROR,e);
            }
        } else {
            throw new PushBizException(MSG_NOT_BAIDUMESSAGE);
        }
    }

    @Override
    public PushMessageDetail analyzeResult(String result) {
        PushMessageDetail detail = new PushMessageDetail();
        if (result.indexOf(KEY_SEND_TIME) > 0){
            detail.setResult(Boolean.FALSE);
        } else {
            detail.setResult(Boolean.TRUE);
        }

        return detail;
        /*if (resp.isResult()){
            if (result.indexOf(KEY_SEND_TIME) > 0){
                resp.setResult(Boolean.FALSE);
            } else {
                resp.setResult(Boolean.TRUE);
            }
        }*/
    }

    /**
     * 包装android发送数据
     * @param message
     * @return
     * @throws Exception
     */
    private void wrapByAndroid(BaiduMessage message) throws Exception {

        getParams(message.getSendDate());
        push_param.put("apikey", ANDROID_API_KEY);
        // 消息
        push_param.put("msg_type",String.valueOf(MSG_TYPE_PASS_THROUGH));
        HashMap<String,Object> msgMap = new HashMap<>();
        msgMap.put("title", message.getTitle());
        msgMap.put("description",message.getContent());

        HashMap<String,Object> cusMap = new HashMap<>();
        push_host = HOST_PUSH_ALL;
        if (StringUtils.isNotEmpty(message.getDeviceId())){
            // 推送单个平台的场合
            push_param.put("channel_id",message.getDeviceId());
            cusMap.put(FIELD_MSGID, message.getMessageId());
            //cusMap.put(FIELD_MSGTYPE, String.valueOf(3));
            cusMap.put(FIELD_MSGTYPE, String.valueOf(message.getContentType()));
            push_host = HOST_PUSH_SIGLE;
        } else {
            // 通用业务参数
            setBusinessParams(cusMap, message);
        }
        msgMap.put("custom_content",cusMap);
        push_param.put("msg",JsonHelper.obj2JsonStr(msgMap));
        push_param.put("sign",getSignValue(push_param,ANDROID_SECRET_KEY,push_host));
    }

    /**
     * 包装IOS发送数据
     * @param message
     * @return
     * @throws Exception
     */
    private void wrapByIos(BaiduMessage message) throws Exception {

        getParams(message.getSendDate());
        push_param.put("apikey", IOS_API_KEY);
        push_param.put("msg_type",String.valueOf(MSG_TYPE_NOTIFICATION));
        push_param.put("deploy_status", String.valueOf(DEPLOY_STATUS));

        HashMap<String,Object> msgMap = new HashMap<>();
        HashMap<String,String> apsMap = new HashMap<>();
        apsMap.put("alert",message.getTitle());
        msgMap.put("aps", apsMap);
        push_host = HOST_PUSH_ALL;
        if (StringUtils.isNotEmpty(message.getDeviceId())){
            push_param.put("channel_id",message.getDeviceId());
            // 通用业务参数
            msgMap.put(FIELD_MSGID, message.getMessageId());
            msgMap.put(FIELD_MSGTYPE, String.valueOf(3));
            push_host = HOST_PUSH_SIGLE;
        } else {
            // 通用业务参数
            setBusinessParams(msgMap, message);
        }

        push_param.put("msg",JsonHelper.obj2JsonStr(msgMap));
        push_param.put("sign",getSignValue(push_param,IOS_SECRET_KEY,push_host));
    }

    /**
     * 获取推送请求的签名
     * @param params        请求的参数
     * @param secret_key    百度推送KEY
     * @param host          请求的链接
     * @return
     * @throws Exception
     */
    public String getSignValue(Map<String,String> params, String secret_key, String host) throws Exception {
        StringBuffer buffer = new StringBuffer();
        buffer.append(METHOD_POST);
        buffer.append(host);

        for(Map.Entry<String,String> entry: params.entrySet()){
            buffer.append(entry.getKey() + "=" + entry.getValue());
        }
        buffer.append(secret_key);
        // 得到签名
        return DigestUtils.md5Hex(URLEncoder.encode(buffer.toString(), StringPool.UTF8));
    }

    /**
     * 获取通用参数
     * @param sendDate
     */
    private void getParams(Date sendDate){
        push_param =  new TreeMap<>();
        push_param.put("msg_type","1");
        push_param.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        if (sendDate != null){
            long delayTime = sendDate.getTime() / 1000;
            if (delayTime - System.currentTimeMillis() / 1000 > 60) {
                // 定时推送的时间
                push_param.put("send_time", String.valueOf(delayTime));
            }
        }
    }

    /**
     * 设置业务参数
     * @param map
     * @param message
     */
    private void setBusinessParams(Map<String, Object> map, BaiduMessage message){

        Integer contentType = message.getContentType();
        /*if(ContentType.isRedPacket(contentType)){
            map.put(FIELD_REDPACKET,message.getProductId());
            contentType = ContentType.TEXT.getType();
        }
        if (ContentType.isProductContentType(contentType)) {
            map.put(FIELD_PRODUCTID, message.getProductId());
        }*/
        map.put(FIELD_MSGID, message.getMessageId());
        map.put(FIELD_MSGTYPE, String.valueOf(contentType));

    }
}
