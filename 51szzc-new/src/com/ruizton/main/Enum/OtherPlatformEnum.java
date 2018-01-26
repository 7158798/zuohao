package com.ruizton.main.Enum;

/**
 * Created by zygong on 17-3-23.
 */
public enum OtherPlatformEnum {

    BITSTAMP("bitstamp", "Bitstamp"),
    BTCCHINA("btcchina", "比特币中国"),
    OKCOIN("okcoin", "okcoin"),
    HUOBI("huobi", "火币"),
    CHBTC("chbtc", "中国比特币"),
    BTCE("btce", "BTC-E"),
    BTCTRADE("btctrade", "BTCTrade");

    private String key;
    private String value;

    OtherPlatformEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValueByKey(String key){
        for(OtherPlatformEnum a : OtherPlatformEnum.values()){
            if(a.getKey().equals(key)){
                return a.getValue();
            }
        }
        return null;
    }

    public static OtherPlatformEnum getByKey(String key){
        for(OtherPlatformEnum a : OtherPlatformEnum.values()){
            if(a.getKey().equals(key)){
                return a;
            }
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
