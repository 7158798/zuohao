package com.ruizton.main.Enum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fenggq on 17-3-1.
 */
public enum ValidateKycLevelEnum {

    NO_COMMIT(0, "未认证"),
    IDENTITY_VALIDATE(1,"身份认证"),
    BANKCARD_VALIDATE(2,"认证银行卡"),
    AUDITING(3, "KYC认证审核中"),
    COMPLETE(4, "已完成认证"),
    NOT_PASS(5, "审核未通过");

    private int key;
    private String value;

    ValidateKycLevelEnum(int key, String value) {
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


    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 判断是否通过kyc1认证
     * @param userkycLevel 用户kyc等级
     * @return
     */
    public static boolean validateKYC1(int userkycLevel){
        if(userkycLevel == NO_COMMIT.getKey() ||
            userkycLevel == IDENTITY_VALIDATE.getKey()){
            return false;
        }
        return true;
    }

    /**
     *  判断用户是否通过kYC2认证
     * @param userkycLevel 用户kyc等级
     * @return
     */
    public static boolean validateKYC2(int userkycLevel){
        return userkycLevel == COMPLETE.getKey();
    }
}