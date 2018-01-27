package com.otc.core.validator;

import com.jucaifu.common.enums.IEnum;
import com.jucaifu.common.util.EnumHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fenggq on  17-05-03
 */
public enum SmsType implements IEnum<String> {


    REGISTER("01", "注册", "您的注册验证码为{code}"),

    LOGIN_PWD_RESET("02", "登录密码重置", "您正在进行密码重置，验证码为{code}"),

    SET_MOBILE_NUMBER("03", "绑定邮箱", "您的验证码为{code}"),

    CHANGE_PWD("04", "修改密码", "您正在进行修改密码，验证码为{code}"),

    LOGIN("05", "登录", SmsVerifyUtils.LOGIN),

    CHANGE_EMAIL("06", "修改邮箱", "您正在进行修改邮箱，验证码为{code}"),

    //大于20通过通用接口获取
    CONFIRM_TRADE("21","确认交易","您正在进行交易确认，验证码为{code}"),

    DRAW_COIN("22","提币","您正在进行提币操作，验证码为{code}");







    private String value;
    private String desc;
    private String verTemplateCode;  //阿里大于短信发送模板code

    SmsType(String value, String desc, String verTemplateCode) {
        this.value = value;
        this.desc = desc;
        this.verTemplateCode = verTemplateCode;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    private static Map<String, SmsType> sMap;

    public static SmsType getSmsTypeByType(String code) {
        if (sMap == null) {
            sMap = EnumHelper.buildIEnumMap(SmsType.class);
        }

        if (code != null) {
            return sMap.get(code);
        } else {
            return null;
        }
    }

    // 保存type和name
    private static Map<String, String> map = new HashMap<>();
    // 保存name和type,通过name查到type
    private static Map<String, String> mapType = new HashMap<>();

    static {
        for (SmsType s : SmsType.values()) {
            map.put(s.value, s.desc);
            mapType.put(s.desc, s.value);
        }
    }

    public static Map<String, String> getMap() {
        return map;
    }

    public String getVerTemplateCode() {
        return verTemplateCode;
    }

    public void setVerTemplateCode(String verTemplateCode) {
        this.verTemplateCode = verTemplateCode;
    }


    public static boolean validateType(String code){
        if(StringUtils.isBlank(code)){
            return false;
        }
        try{
            if(Integer.parseInt(code) >= 20){
                return true;
            }
        }catch (Exception e){
        }
        return false;
    }

}
