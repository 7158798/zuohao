package com.otc.api.console.ctrl.user.req;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by fenggq on 17-4-26.
 */
public class UserAdressListReq extends WebApiBaseReq{

    //会员信息
    private String userInfo ;

    //币种id  null为全部
    private Long coinId;

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }
}
