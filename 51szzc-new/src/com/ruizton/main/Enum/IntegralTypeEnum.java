package com.ruizton.main.Enum;

import com.ruizton.util.zuohao.LanguageUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by a123 on 17-3-1.
 */
public enum IntegralTypeEnum {
    ALL(0,"总积分","total_integral"),
    LOGIN(1, "登录","login","signIn"),
    RECHARGE_RMB(2, "充值人民币","rechargeRMB","regc_rmb"),
    RECHARGE_BTC(3, "充值数字货币","rechargeBTC","regc_cncy"),
    TRADE(5, "交易","deal","trade"),
    EVERDAY_ASSES(6,"每日净资产","dayAsset", "day_asset"),
    INVITE_FRIENDS(7,"邀请好友","inviteFriends","invite_friends"),

    //新手任务
    PHONE_FIRST(10,"手机认证","phone_ver"),
    EMAIL_FIRST(11,"邮箱认证","email_ver"),
    REALNAME_FIRST(12,"身份认证","authentication"),
    INFOBANK_FIRST(13,"绑定银行卡","bind_bank"),
    TRADEPWD_FIRST(14,"设置交易密码","set_trade_pwd"),
    GA_FIRST(15,"设置GA","set_ga"),
    CAPTITALIN_FIRST(16,"首次充值","first_regc"),
    TRADE_FIRST(17,"首次交易","first_trade");

    private static Map<Integer, String> map = new HashMap<>();

    private static Map<Integer, String> urlmap = new HashMap<>();

    static {
        for (IntegralTypeEnum userLimitType : IntegralTypeEnum.values()) {
            map.put(userLimitType.code, userLimitType.desc);
        }
    }

    static {
        for (IntegralTypeEnum userLimitType : IntegralTypeEnum.values()) {
            urlmap.put(userLimitType.code, userLimitType.url);
        }
    }

    private int code;
    private String desc;
    private String url;
    private String i18nKey;

    IntegralTypeEnum(int code, String desc,String url, String i18nKey) {
        this.code = code;
        this.desc = desc;
        this.url = url;
        this.i18nKey = i18nKey;
    }

    IntegralTypeEnum(int code, String desc, String i18nKey) {
        this.code = code;
        this.desc = desc;
        this.i18nKey = i18nKey;
    }

    IntegralTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    //根据code返回app、console显示的状态名
    public static String getI18nMsg(int code){
        String msg = "";
        for (IntegralTypeEnum value : IntegralTypeEnum.values()){
            if (code == value.code){
                msg = LanguageUtil.i18nMsg(value.getI18nKey());
                break;
            }
        }

        return msg;
    }



    public static Map<Integer, String> getMap() {
        return map;
    }

    public static void setMap(Map<Integer, String> map) {
        IntegralTypeEnum.map = map;
    }


    public static Map<Integer, String> getUrlmap() {
        return urlmap;
    }

    public static void setUrlmap(Map<Integer, String> urlmap) {
        IntegralTypeEnum.urlmap = urlmap;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getI18nKey() {
        return i18nKey;
    }

    public void setI18nKey(String i18nKey) {
        this.i18nKey = i18nKey;
    }
}
