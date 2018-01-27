package com.otc.api.console.ctrl.user.req;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by fenggq on 17-4-26.
 */
public class UserListReq extends WebApiBaseReq{

    //会员信息
    private String userInfo ;

    //会员状态  00：全部  01：正常  02：禁用  03：已通过KYC  04:已拒绝

    //kyc审核状态 00：全部  02：已经提交KYC 03：KYC审核通过 04：kyc被拒绝
    private String status;

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
