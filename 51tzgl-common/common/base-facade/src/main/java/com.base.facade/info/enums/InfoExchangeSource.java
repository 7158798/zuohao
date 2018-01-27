package com.base.facade.info.enums;

/**
 * Created by yangyy on 16-8-23.
 */
public enum InfoExchangeSource {

    ZG_BACK("0","中国银行");
    private String key;
    private String value;

    InfoExchangeSource(String key,String value){
        this.key = key;
        this.value = value;
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
