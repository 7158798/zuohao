package com.ruizton.main.Enum;

import java.util.ArrayList;
import java.util.List;

/**
 * banner状态枚举
 * Created by luwei on 17-3-7.
 */
public enum BannerStatusEnum {

    DRAFT(0, "草稿"),
    USE(1, "使用中"),
    DISABLED(2, "停用"),
    DELETE(3, "已删除");

    private Integer code;

    private String name;

    BannerStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static List<BannerStatusEnum> bannerStatusEnumList = new ArrayList<>();

    static {
        bannerStatusEnumList.add(DRAFT);
        bannerStatusEnumList.add(USE);
        bannerStatusEnumList.add(DISABLED);
        bannerStatusEnumList.add(DELETE);
    }

    public static String getNameByCode(Integer code) {
        String result_name = "";
        for(BannerStatusEnum status : BannerStatusEnum.values()) {
            if(status.getCode().intValue() == code.intValue()) {
                result_name = status.getName();
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
