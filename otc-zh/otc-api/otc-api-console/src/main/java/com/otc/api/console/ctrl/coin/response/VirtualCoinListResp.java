package com.otc.api.console.ctrl.coin.response;

import com.jucaifu.common.util.DateHelper;
import com.otc.api.console.base.BaseListResp;
import com.otc.facade.virtual.enums.VirtualCoinStatus;
import com.otc.facade.virtual.pojo.po.VirtualCoin;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lx on 17-5-1.
 */
public class VirtualCoinListResp extends BaseListResp {

    private String name;
    private String shortName;
    private String accessKey;
    private String secretKey;
    private String ip;
    private String port;
    // 是否可以提现
    private String isWithDraw;
    // 是否可以充值
    private String isRecharge;
    // 是否自动到帐
    private String isAuto;
    // 状态
    private String status;

    private String statusCode;

    private String createDate;

    private String modifiedDate;

    private Long startBlock;

    private String mainAddress;

    private String iconUrl;

    private String tradeFees;

    private String lowTradeFees;

    private String rechargeFees;

    private String lowRechargeFees;

    private String withdrawFees;

    private String lowWithdrawFees;

    private String singleLowRecharge;
    private String singleHighRecharge;
    private String dayHighRecharge;
    private String singleLowWithdraw;
    private String singleHighWithdraw;
    private String dayHighWithdraw;
    private String iconUrl2;
    private String withdrawDes;
    private String rechargeDes;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getTradeFees() {
        return tradeFees;
    }

    public void setTradeFees(String tradeFees) {
        this.tradeFees = tradeFees;
    }

    public String getLowTradeFees() {
        return lowTradeFees;
    }

    public void setLowTradeFees(String lowTradeFees) {
        this.lowTradeFees = lowTradeFees;
    }

    public String getRechargeFees() {
        return rechargeFees;
    }

    public void setRechargeFees(String rechargeFees) {
        this.rechargeFees = rechargeFees;
    }

    public String getLowRechargeFees() {
        return lowRechargeFees;
    }

    public void setLowRechargeFees(String lowRechargeFees) {
        this.lowRechargeFees = lowRechargeFees;
    }

    public String getWithdrawFees() {
        return withdrawFees;
    }

    public void setWithdrawFees(String withdrawFees) {
        this.withdrawFees = withdrawFees;
    }

    public String getLowWithdrawFees() {
        return lowWithdrawFees;
    }

    public void setLowWithdrawFees(String lowWithdrawFees) {
        this.lowWithdrawFees = lowWithdrawFees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getStartBlock() {
        return startBlock;
    }

    public void setStartBlock(Long startBlock) {
        this.startBlock = startBlock;
    }

    public String getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(String mainAddress) {
        this.mainAddress = mainAddress;
    }

    public String getIsWithDraw() {
        return isWithDraw;
    }

    public void setIsWithDraw(String isWithDraw) {
        this.isWithDraw = isWithDraw;
    }

    public String getIsRecharge() {
        return isRecharge;
    }

    public void setIsRecharge(String isRecharge) {
        this.isRecharge = isRecharge;
    }

    public String getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(String isAuto) {
        this.isAuto = isAuto;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getSingleLowRecharge() {
        return singleLowRecharge;
    }

    public void setSingleLowRecharge(String singleLowRecharge) {
        this.singleLowRecharge = singleLowRecharge;
    }

    public String getSingleHighRecharge() {
        return singleHighRecharge;
    }

    public void setSingleHighRecharge(String singleHighRecharge) {
        this.singleHighRecharge = singleHighRecharge;
    }

    public String getDayHighRecharge() {
        return dayHighRecharge;
    }

    public void setDayHighRecharge(String dayHighRecharge) {
        this.dayHighRecharge = dayHighRecharge;
    }

    public String getSingleLowWithdraw() {
        return singleLowWithdraw;
    }

    public void setSingleLowWithdraw(String singleLowWithdraw) {
        this.singleLowWithdraw = singleLowWithdraw;
    }

    public String getSingleHighWithdraw() {
        return singleHighWithdraw;
    }

    public void setSingleHighWithdraw(String singleHighWithdraw) {
        this.singleHighWithdraw = singleHighWithdraw;
    }

    public String getDayHighWithdraw() {
        return dayHighWithdraw;
    }

    public void setDayHighWithdraw(String dayHighWithdraw) {
        this.dayHighWithdraw = dayHighWithdraw;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getIconUrl2() {
        return iconUrl2;
    }

    public void setIconUrl2(String iconUrl2) {
        this.iconUrl2 = iconUrl2;
    }

    public String getWithdrawDes() {
        return withdrawDes;
    }

    public void setWithdrawDes(String withdrawDes) {
        this.withdrawDes = withdrawDes;
    }

    public String getRechargeDes() {
        return rechargeDes;
    }

    public void setRechargeDes(String rechargeDes) {
        this.rechargeDes = rechargeDes;
    }

    public void copy(VirtualCoin coin){
        this.id = coin.getId();
        this.name = coin.getName();
        this.shortName = coin.getShortName();
        if (coin.getCreateDate() != null)
            this.createDate = DateHelper.date2String(coin.getCreateDate(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond);
        if (coin.getModifiedDate() != null)
            this.modifiedDate = DateHelper.date2String(coin.getModifiedDate(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond);
        this.ip = coin.getIp();
        this.port = coin.getPort();
        this.statusCode = coin.getStatus();
        this.status = VirtualCoinStatus.getDescByCode(coin.getStatus());
        this.startBlock = coin.getStartBlock();
        this.mainAddress = coin.getMainAddress();
        this.isAuto = coin.getIsAuto()?"是":"否";
        this.isRecharge = coin.getIsRecharge()?"是":"否";
        this.isWithDraw = coin.getIsWithDraw()?"是":"否";
        this.iconUrl = coin.getIconUrl();
        this.secretKey = coin.getSecretKey();
        this.accessKey = coin.getAccessKey();
        this.iconUrl2 = coin.getIconUrl2();
        this.withdrawDes = coin.getWithdrawDes();
        this.rechargeDes = coin.getRechargeDes();
        if (coin.getTradeFees() != null)
            this.tradeFees = coin.getTradeFees().toString();
        if (coin.getLowTradeFees() != null)
            this.lowTradeFees = coin.getLowTradeFees().toString();
        if (coin.getRechargeFees() != null)
            this.rechargeFees = coin.getRechargeFees().toString();
        if (coin.getLowRechargeFees() != null)
            this.lowRechargeFees = coin.getLowRechargeFees().toString();
        if (coin.getWithdrawFees() != null)
            this.withdrawFees = coin.getWithdrawFees().toString();
        if (coin.getLowWithdrawFees() != null)
            this.lowWithdrawFees = coin.getLowWithdrawFees().toString();
        if (coin.getSingleHighRecharge()  != null)
            this.singleHighRecharge = coin.getSingleHighRecharge().toString();
        if (coin.getSingleLowRecharge() != null)
            this.singleLowRecharge = coin.getSingleLowRecharge().toString();
        if (coin.getDayHighRecharge() != null)
            this.dayHighRecharge = coin.getDayHighRecharge().toString();
        if (coin.getSingleLowWithdraw() != null)
            this.singleLowWithdraw = coin.getSingleLowWithdraw().toString();
        if (coin.getSingleHighWithdraw() != null)
            this.singleHighWithdraw = coin.getSingleHighWithdraw().toString();
        if (coin.getDayHighWithdraw() != null)
            this.dayHighWithdraw = coin.getDayHighWithdraw().toString();
    }
}
