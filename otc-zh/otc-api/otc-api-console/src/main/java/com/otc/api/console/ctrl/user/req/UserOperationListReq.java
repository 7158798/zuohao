package com.otc.api.console.ctrl.user.req;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by a123 on 17-5-3.
 */
public class UserOperationListReq extends WebApiBaseReq{
    //会员信息
    private String userInfo ;

    private String operationType;


    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}
