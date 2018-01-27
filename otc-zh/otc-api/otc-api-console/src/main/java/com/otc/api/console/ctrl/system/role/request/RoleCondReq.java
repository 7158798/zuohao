package com.otc.api.console.ctrl.system.role.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by lx on 17-4-27.
 */
public class RoleCondReq extends WebApiBaseReq {

    private String name;

    private Boolean isPage = Boolean.TRUE;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsPage() {
        return isPage;
    }

    public void setIsPage(Boolean isPage) {
        this.isPage = isPage;
    }
}
