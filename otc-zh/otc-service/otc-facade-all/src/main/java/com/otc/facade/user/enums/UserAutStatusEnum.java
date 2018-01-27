package com.otc.facade.user.enums;

import com.jucaifu.common.util.EnumHelper;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fenggq on 17-4-21.
 */
public enum UserAutStatusEnum {

    NO_REALNAME("00","未实名"),
    REALNAME_PASS("01","已实名认证"),
    KYC_POST("02","审核中"),
    KYC_PASS("03","审核通过"),
    KYC_NO_PASS("04","审核拒绝");

    private String code;

    private String desc;

    UserAutStatusEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    /**
     * 是否实名
     * @param status
     * @return
     */
    public static boolean isRealName(String status){
        if(status == null){
            return false;
        }
        return !StringUtils.equals(status,NO_REALNAME.getCode());
    }


    /**
     * 是否完成kyc
     * @param status
     * @return
     */
    public static boolean isKyc(String status){
        return StringUtils.equals(status,KYC_PASS.getCode());
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



    public static String getNameByCode(String code) {
        return map.get(code);
    }

    private static Map<String, String> map = new HashMap<>();

    static {
        for (UserAutStatusEnum userAutStatusEnum : UserAutStatusEnum.values()) {
            map.put( userAutStatusEnum.code,userAutStatusEnum.desc);
        }
    }

    public static Map<String, String> getMap() {
        return map;
    }


}
