package com.otc.api.web.ctrl.user.response;

/**
 * Created by fenggq on 17-4-19.
 */
public class UserAddressResp {
    // 地址
    private String address;
    // 充值说明
    private String rechargeDes;
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRechargeDes() {
        return rechargeDes;
    }

    public void setRechargeDes(String rechargeDes) {
        this.rechargeDes = rechargeDes;
    }
}
