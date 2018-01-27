package com.otc.api.console.ctrl.system.login.response;

import java.util.List;

/**
 * Created by zhaiyz on 15-11-2.
 */
public class LoginResp {

    /**
     * 认证token
     */
    private String token;

    private List<Menu> menus;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
