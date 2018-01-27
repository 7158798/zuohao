package com.otc.api.web.ctrl.advertisement.response;

import com.otc.facade.advertisement.pojo.po.Advertisement;
import com.otc.facade.user.pojo.poex.UserWebLoginInfo;

/**
 * Created by zygong on 17-5-31.
 */
public class DetailResp {

    private Advertisement advertisement;
    private UserWebLoginInfo userInfo;

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public UserWebLoginInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserWebLoginInfo userInfo) {
        this.userInfo = userInfo;
    }
}
