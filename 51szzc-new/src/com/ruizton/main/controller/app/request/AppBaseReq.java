package com.ruizton.main.controller.app.request;

import java.io.Serializable;

/**
 * Created by fenggq on 17-3-22.
 */
public class AppBaseReq implements Serializable{
    protected String loginToken;

    protected Integer currentPage;

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
