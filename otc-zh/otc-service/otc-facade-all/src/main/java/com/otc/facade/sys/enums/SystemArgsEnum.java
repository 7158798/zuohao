package com.otc.facade.sys.enums;

/**
 * Created by fenggq on 17-5-10.
 */
public enum SystemArgsEnum {

    TRADE_TIME_LIMIT("01","TRADE_TIME_LIMIT","交易期限"), //Long

    TRADE_ALL_TIMES("02","TRADE_ALL_LIMIT_TIMES","交易总次数"),

    VALIDATE_ALL_TIMES("03","VALIDATE_ALL_TIMES","验证码总次数"),

    VALIDATE_ONETYPE_TIMES("04","VALIDATE_ONETYPE_TIMES","验证码单次限制"),

    OPERN_REALNAME("05","OPERN_REALNAME","实名认证开关")




    ;

    private String code;
    private String key;
    private String desc;

    SystemArgsEnum(String code,String key, String desc){
        this.code = code;
        this.desc = desc;
        this.key = key;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
