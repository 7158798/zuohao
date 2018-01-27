package com.base.facade.info.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.util.Date;

public class InfoBank extends BasePo {


    /**
     * 银行名称，对应表字段为：tbl_info_bank.bank_name
     */
    private String bankName;

    /**
     * 银行logo，对应表字段为：tbl_info_bank.bank_logo
     */
    private String bankLogo;

    /**
     * 修改时间，对应表字段为：tbl_info_bank.create_datetime
     */
    private Date createDatetime;

    /**
     * 修改时间，对应表字段为：tbl_info_bank.modifed_datetime
     */
    private Date modifedDatetime;

    private String showStatus;

    private  String bankLogoApp;

    public String getBankLogoApp() {
        return bankLogoApp;
    }

    public void setBankLogoApp(String bankLogoApp) {
        this.bankLogoApp = bankLogoApp;
    }

    private static final long serialVersionUID = 1L;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo == null ? null : bankLogo.trim();
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getModifedDatetime() {
        return modifedDatetime;
    }

    public void setModifedDatetime(Date modifedDatetime) {
        this.modifedDatetime = modifedDatetime;
    }

    public String getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(String showStatus) {
        this.showStatus = showStatus;
    }
}