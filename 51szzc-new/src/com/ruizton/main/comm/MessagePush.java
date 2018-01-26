package com.ruizton.main.comm;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushBatchUniMsgRequest;
import com.baidu.yun.push.model.PushBatchUniMsgResponse;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import com.ruizton.main.auto.TaskList;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.vo.FapppushPhone;
import com.ruizton.main.service.app.FapppushService;
import com.ruizton.main.service.front.FrontValidateService;
import com.ruizton.main.service.front.FtradeMappingService;
import com.ruizton.util.MessagesUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zygong on 17-4-13.
 */
@Component
public class MessagePush extends Thread{
    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");

    private final static String title = "51数字资产";
    private final static String smsTemplate = "SMS_49190190";

    @Value("${andrApiKey}")
    private String andrApiKey;
    @Value("${andrSecretKey}")
    private String andrSecretKey;
    @Value("${iosApiKey}")
    private String iosApiKey;
    @Value("${iosSecretKey}")
    private String iosSecretKey;
    @Value("${iosDeployStatus}")
    private String iosDeployStatus;

    @Autowired
    private FapppushService fapppushService;
    @Autowired
    protected ValidateMap validateMap ;
    @Autowired
    protected ConstantMap constantMap ;
    @Autowired
    protected FtradeMappingService ftradeMappingService ;
    @Autowired
    protected FrontValidateService frontValidateService ;
    @Autowired
    protected TaskList taskList ;
    @Autowired
    private ConstantMap map;

    public void start(int coinType, double price){
        ThreadTask task = new ThreadTask(coinType, price);
        task.start();
    }

    public class ThreadTask extends Thread{
        private int coinType;
        private double price;
        private Timestamp time;

        public ThreadTask(int coinType, double price){
            this.coinType = coinType;
            this.price = price;
            this.time = null;
        }

        @Override
        public void run(){
            messgePush(coinType, price);
            // 更新最后一次发送时间
            fapppushService.updateSendTime(coinType, price, time);
        }

        public void messgePush(int coinType, double price) {
            List<String> andrList = new ArrayList<String>();
            Map<Integer, String> smsMap = new HashMap<Integer, String>();
            String description = constantMap.get("push_message_content").toString();

            long l = System.currentTimeMillis();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -30);
            String format = sdf.format(cal.getTime());
            time = Timestamp.valueOf(format);

            List<FapppushPhone> pushList = fapppushService.findPushList(coinType, price, time);
            if(StringUtils.isNotBlank(description)){
                Ftrademapping ftrademapping = ftradeMappingService.findFtrademapping2(coinType);
                description = description.replace("#coinType#", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname());
                description = description.replace("#price#", String.valueOf(price));
            }
            if(null != pushList && pushList.size() > 0) {
                for (FapppushPhone fapppush : pushList) {
                    if (1 == fapppush.getFphonetype()) {
                        andrList.add(fapppush.getFphonecode());
                        if (andrList.size() % 10000 == 0) {
                            try {
                                andrListPush(andrList, coinType, description);
                            } catch (PushClientException e) {
                                e.printStackTrace();
                            } catch (PushServerException e) {
                                e.printStackTrace();
                            }
                            andrList = new ArrayList<String>();
                        }
                    } else if (2 == fapppush.getFphonetype()) {
                        // ios 只能进行单个推送
                        try {
                            iosSinglePush(fapppush.getFphonecode(), coinType, description);
                        } catch (PushClientException e) {
                            e.printStackTrace();
                        } catch (PushServerException e) {
                            e.printStackTrace();
                        }
                    }
                    // 短信发送
                    if(fapppush.getFissms() == 2 && !smsMap.containsKey(fapppush.getFuser())){
                        smsMap.put(fapppush.getFuser(), fapppush.getFtelephone());
                        sendSms(description, fapppush.getFtelephone());
                    }

                }
            }

            // 安卓推送
            if(andrList.size() > 0){
                try {
                    andrListPush(andrList, coinType, description);
                } catch (PushClientException e) {
                    e.printStackTrace();
                } catch (PushServerException e) {
                    e.printStackTrace();
                }
            }
        }

        @Async
        public void sendSms(String content, String phone){
            MessagesUtils.send(
                    map.getString("messageName"),
                    map.getString("messagePassword"),
                    map.getString("messageURL"), smsTemplate,
                    map.getString("webName"),content, phone);
//            Fvalidatemessage fvalidatemessage = new Fvalidatemessage() ;
//            fvalidatemessage.setFcontent(content) ;
//            fvalidatemessage.setFcreateTime(Utils.getTimestamp()) ;
//            fvalidatemessage.setFphone(phone) ;
//            fvalidatemessage.setFstatus(ValidateMessageStatusEnum.Not_send) ;
//            frontValidateService.addFvalidateMessage(fvalidatemessage) ;
//
//            taskList.returnMessageList(fvalidatemessage.getFid()) ;
        }

        /**
         * 安卓推送（群推）
         * @param phoneCodes
         * @throws PushClientException
         * @throws PushServerException
         */
        public void andrListPush(List phoneCodes, int fid, String description) throws PushClientException,
                PushServerException {
            // 1. get apiKey and secretKey from developer console
            String apiKey = andrApiKey;
            String secretKey = andrSecretKey;
            PushKeyPair pair = new PushKeyPair(apiKey, secretKey);

            // 2. build a BaidupushClient object to access released interfaces
            BaiduPushClient pushClient = new BaiduPushClient(pair,
                    BaiduPushConstants.CHANNEL_REST_URL);

            // 3. register a YunLogHandler to get detail interacting information
            // in this request.
            pushClient.setChannelLogHandler(new YunLogHandler() {
                @Override
                public void onHandle(YunLogEvent event) {
                    System.out.println(event.getMessage());
                }
            });

            try {
                // 4. specify request arguments
                //创建 Android的通知
                JSONObject notification = new JSONObject();
                notification.put("title", title);
                notification.put("description", description);
                notification.put("fid", fid);

                String[] phoneCodeArray = (String[])phoneCodes.toArray(new String[0]);
                PushBatchUniMsgRequest request = new PushBatchUniMsgRequest()
                        .addChannelIds(phoneCodeArray)
                        .addMsgExpires(new Integer(3600)). // message有效时间
                        addMessageType(0).// 1：通知,0:透传消息. 默认为0 注：IOS只有通知.
                        addMessage(notification.toString()).// deviceType => 3:android, 4:ios
                        addDeviceType(3).addTopicId("BaiduPush");
                // 5. http request
                PushBatchUniMsgResponse response = pushClient
                        .pushBatchUniMsg(request);
                // Http请求结果解析打印
                LOG.i(this, "msgId: " + response.getMsgId() + ",sendTime: "
                        + response.getSendTime());
            } catch (PushClientException e) {
            /*
			 * ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,'true' 表示抛出, 'false' 表示捕获。
			 */
                if (BaiduPushConstants.ERROROPTTYPE) {
                    throw e;
                } else {
                    e.printStackTrace();
                }
            } catch (PushServerException e) {
                if (BaiduPushConstants.ERROROPTTYPE) {
                    throw e;
                } else {
                    LOG.i(this, String.format(
                            "requestId: %d, errorCode: %d, errorMessage: %s",
                            e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
                }
            }
        }

        /**
         * ios 单独推送
         * @param phoneCode
         * @throws PushClientException
         * @throws PushServerException
         */
        @Async
        public void iosSinglePush(String phoneCode, int fid, String description) throws PushClientException,
                PushServerException {
            // 1. get apiKey and secretKey from developer console
            String apiKey = iosApiKey;
            String secretKey = iosSecretKey;
            PushKeyPair pair = new PushKeyPair(apiKey, secretKey);

            // 2. build a BaidupushClient object to access released interfaces
            BaiduPushClient pushClient = new BaiduPushClient(pair,
                    BaiduPushConstants.CHANNEL_REST_URL);

            // 3. register a YunLogHandler to get detail interacting information
            // in this request.
            pushClient.setChannelLogHandler(new YunLogHandler() {
                @Override
                public void onHandle(YunLogEvent event) {
                    System.out.println(event.getMessage());
                }
            });

            try {
                // 4. specify request arguments
                //创建 ios的通知
                JSONObject notification = new JSONObject();
                JSONObject jsonAPS = new JSONObject();
                jsonAPS.put("alert", description);
                jsonAPS.put("sound", "default"); // 设置通知铃声样式，例如"ttt"，用户自定义。
                notification.put("aps", jsonAPS);
                notification.put("fid", fid);

                PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest()
                        .addChannelId(phoneCode)
                        .addMsgExpires(new Integer(3600)). // message有效时间
                        addMessageType(1).// 1：通知,0:透传消息. 默认为0 注：IOS只有通知.
                        addMessage(notification.toString()).
                                addDeployStatus(Integer.valueOf(iosDeployStatus)) // 仅IOS应用推送时使用，默认值为null， 取值如下：1：开发状态 2：生产状态;
                        .addDeviceType(4);// deviceType => 3:android, 4:ios
                // 5. http request
                PushMsgToSingleDeviceResponse response = pushClient
                        .pushMsgToSingleDevice(request);
                // Http请求结果解析打印
                LOG.i(this, "msgId: " + response.getMsgId() + ",sendTime: "
                        + response.getSendTime());
            } catch (PushClientException e) {
            /*
			 * ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,'true' 表示抛出, 'false' 表示捕获。
			 */
                if (BaiduPushConstants.ERROROPTTYPE) {
                    throw e;
                } else {
                    e.printStackTrace();
                }
            } catch (PushServerException e) {
                if (BaiduPushConstants.ERROROPTTYPE) {
                    throw e;
                } else {
                    LOG.i(this, String.format(
                            "requestId: %d, errorCode: %d, errorMessage: %s",
                            e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
                }
            }
        }
    }


}
