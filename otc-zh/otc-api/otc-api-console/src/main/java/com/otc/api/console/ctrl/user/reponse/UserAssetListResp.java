package com.otc.api.console.ctrl.user.reponse;

import com.jucaifu.common.pojo.vo.BaseResp;
import com.jucaifu.common.util.DateHelper;
import com.otc.facade.user.pojo.poex.UserAssetRecord;

import java.util.Date;
import java.util.List;

/**
 * Created by a123 on 17-5-3.
 */
public class UserAssetListResp extends BaseResp{
    private Long id;
    private String emailAddress;
    private String realName;
    private Date createDate;


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    private List<UserAssetRecord> records;


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

    public List<UserAssetRecord> getRecords() {
        return records;
    }

    public void setRecords(List<UserAssetRecord> records) {
        this.records = records;
    }
}
