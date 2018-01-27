package com.jucaifu.common.util.model;

import com.jucaifu.common.pojo.po.BasePo;

/**
 * Created by scofieldcai-dev on 15/8/25.
 */
public class UserPo extends BasePo {

    protected String uuid;
    private String name;
    private String pwd;

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
