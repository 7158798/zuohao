package com.otc.api.console.ctrl.virtual.response;

import com.jucaifu.common.util.DateHelper;
import com.otc.api.console.base.UserCommonResp;
import com.otc.facade.virtual.pojo.po.VirtualWallet;
import com.otc.facade.virtual.pojo.poex.VirtualWalletEx;

/**
 * Created by lx on 17-5-2.
 */
public class WalletListResp extends UserCommonResp {

    private String coinName;

    private String total;

    private String frozen;

    private String modifiedDate;

    private Long id ;

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFrozen() {
        return frozen;
    }

    public void setFrozen(String frozen) {
        this.frozen = frozen;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void copy(VirtualWalletEx walletEx){
        super.setUserId(walletEx.getUserId());
        super.setRealName(walletEx.getRealName());
        super.setEmailAddress(walletEx.getEmailAddress());
        this.coinName = walletEx.getCoinName();
            total = walletEx.getTotal().toString();
            frozen = walletEx.getFrozen().toString();
            id = walletEx.getId();
            modifiedDate = DateHelper.date2String(walletEx.getModifiedDate(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond);
    }
}
