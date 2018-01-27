package com.otc.facade.message.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lx on 17-4-21.
 */
public enum MessageType {

    SYSTEM("00","系统消息"),
    TRADE("01","交易消息"),
    CHAT("02","聊天消息"),
    EVALUATE("03","评价消息"),
    FINANCE("04","财务消息");

    private String code;

    private String desc;

    MessageType(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public static Map<String,MessageType> typeMap = new HashMap<>();

    static {
        for (MessageType messageType : MessageType.values()){
            typeMap.put(messageType.getCode(),messageType);
        }
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
