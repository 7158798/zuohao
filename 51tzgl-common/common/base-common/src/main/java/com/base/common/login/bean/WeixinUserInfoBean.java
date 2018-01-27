package com.base.common.login.bean;

import com.base.common.login.Response.UserResponse;

/**
 * Created by lx on 17-1-3.
 */
public class WeixinUserInfoBean extends UserResponse {

    private String openid;

    private String nickname;
    //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    private String sex;
    // 用户个人资料填写的省份
    private String province;
    // 城市
    private String city;
    // 国家
    private String country;

    private String headimgurl;

    private String[] privilege;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String[] getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String[] privilege) {
        this.privilege = privilege;
    }
}
