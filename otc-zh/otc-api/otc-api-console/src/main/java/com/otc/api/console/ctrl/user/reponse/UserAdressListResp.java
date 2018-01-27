package com.otc.api.console.ctrl.user.reponse;

import com.jucaifu.common.pojo.vo.BaseResp;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fenggq on 17-4-26.
 */
public class UserAdressListResp extends BaseResp {

    private Long id;
    private String emailAddress;
    private String realName;

    private String address;  //充值地址
    private String coinname; //币种名称
    private Date createDate; //创建时间



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoinname() {
        return coinname;
    }

    public void setCoinname(String coinname) {
        this.coinname = coinname;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
