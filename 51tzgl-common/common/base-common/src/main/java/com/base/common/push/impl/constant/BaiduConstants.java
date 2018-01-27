package com.base.common.push.impl.constant;


/**
 * Created by liuxun on 16-9-29.
 */
public interface BaiduConstants {

    String KEY_SEND_TIME = "error_code";
    String HOST = "http://api.tuisong.baidu.com/rest/3.0/push/";
    // 全部推送
    String HOST_PUSH_ALL = HOST + "all";
    // 单个设备推送
    String HOST_PUSH_SIGLE = HOST + "single_device";

    String FIELD_MSGID = "msgId";

    String FIELD_MSGTYPE = "msgType";

    String FIELD_PRODUCTID = "productId";

    String FIELD_REDPACKET = "redpactetActivityId";
    String METHOD_POST = "POST";
    String USER_AGENT = "BCCS_SDK/3.0 (linux; linux 14.04 LTS: Fri Sep 19 00:26:44 PDT 2014; root:xnu-2782.1.97~2/RELEASE_X86_64; x86_64) JAVA/1.8 (Baidu Push Server SDK V3.0.1 and so on..) cli/Unknown ZEND/2.6.0";

    String MSG_NOT_BAIDUMESSAGE = "消息类型不正确";

    // 取值如下：0：消息；1：通知。默认为0
    Integer MSG_TYPE_PASS_THROUGH = 0;
    Integer MSG_TYPE_NOTIFICATION = 1;


}
