package com.ruizton.main.Enum;

import java.util.ArrayList;
import java.util.List;

/**
 * banner投放位置枚举
 * Created by luwei on 17-3-7.
 */
public enum BannerSeatEnum {

    WEB(0, "web官网"),
    MOBILE(1, "app交易"),
    IOS(2, "非app交易版本");


    private Integer code;

    private String name;

    BannerSeatEnum(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    public static List<BannerSeatEnum> bannerSeatEnumList = new ArrayList<>();

    static {
        bannerSeatEnumList.add(WEB);
        bannerSeatEnumList.add(MOBILE);
        bannerSeatEnumList.add(IOS);
    }

    public static String getNameByCode(Integer code) {
        String result_name = "";
        for(BannerSeatEnum seat : BannerSeatEnum.values()) {
            if(seat.getCode().intValue() == code.intValue()) {
                result_name = seat.getName();
                break;
            }
        }
        return result_name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
