package com.otc.facade.user.enums;


import com.jucaifu.common.util.EnumHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fenggq on 17-5-2.
 */
public enum UserOperationEnum {

   // REGIST("00","注册"),

    LOGIN("01","登录"),   //

 //   BIND_EMAIL("02","绑定邮箱"),

    MODIFY_LOGIN_PWD("03","修改登录密码"),//

    REAL_NAME_AUT("04","实名认证"),//

    KYC_AUT("05","KYC认证"),//

    RESET_LOGIN_PWD("06","重置登录密码"),  //

    SET_FISH_CODE("07","设置防钓鱼码"); //

  //  UPDATE_FISH_CODE("08","修改防钓鱼码");

    private String code;

    private String desc;

    UserOperationEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
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


    private static Map<String, String> map = new HashMap<>();

    static {
        for (UserOperationEnum userOperationEnum : UserOperationEnum.values()) {
            map.put( userOperationEnum.code,userOperationEnum.desc);
        }
    }

    public static Map<String, String> getMap() {
        return map;
    }
}
