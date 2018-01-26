package com.ruizton.main.Enum;

import java.util.ArrayList;
import java.util.List;

/**
 * 资讯状态枚举
 * Created by luwei on 17-3-30.
 */
public enum  FarticleStatusEnum {

    DRAFT(0,"草稿"),
    AUDIT(1,"待审核"),
    PUBLISH(2,"通过"),
    REJECT(3,"拒绝"),
    FREE_AUDIT(4, "后台添加免审核");

    public static List<FarticleStatusEnum> statusList = new ArrayList<>();

    static {
        statusList.add(AUDIT);
        statusList.add(PUBLISH);
        statusList.add(REJECT);
    }

    private int code;

    private String name;


    FarticleStatusEnum(int code, String name) {
        this.code =  code;
        this.name = name;
    }


    public static String getNameByCode(int code) {
        String name = "";
        for (FarticleStatusEnum value : FarticleStatusEnum.values()) {
            if (value.getCode() == code) {
                name = value.getName();
                break;
            }
        }
        return name;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
