package com.otc.api.console.ctrl.coin.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

import java.math.BigDecimal;

/**
 * Created by lx on 17-4-24.
 */
public class VirtualCoinAddReq extends WebApiBaseReq {

    /**
     * 对应表字段为：t_virtual_coin.name
     */
    private String name;

    /**
     * 对应表字段为：t_virtual_coin.short_name
     */
    private String shortName;

    /**
     * 对应表字段为：t_virtual_coin.access_key
     */
    private String accessKey;

    /**
     * 对应表字段为：t_virtual_coin.secret_key
     */
    private String secretKey;

    /**
     * 对应表字段为：t_virtual_coin.ip
     */
    private String ip;

    /**
     * 对应表字段为：t_virtual_coin.port
     */
    private String port;

    /**
     * 对应表字段为：t_virtual_coin.is_with_draw
     */
    private String isWithDraw;

    /**
     * 对应表字段为：t_virtual_coin.is_recharge
     */
    private String isRecharge;

    /**
     * 对应表字段为：t_virtual_coin.is_auto
     */
    private String isAuto;

    /**
     * 对应表字段为：t_virtual_coin.start_block
     */
    private Long startBlock;

    /**
     * 对应表字段为：t_virtual_coin.main_address
     */
    private String mainAddress;

    /**
     * 对应表字段为：t_virtual_coin.icon_url
     */
    private String iconUrl;

    /**
     * 对应表字段为：t_virtual_coin.trade_fees
     */
    private BigDecimal tradeFees;

    /**
     * 对应表字段为：t_virtual_coin.low_trade_fees
     */
    private BigDecimal lowTradeFees;

    /**
     * 对应表字段为：t_virtual_coin.recharge_fees
     */
    private BigDecimal rechargeFees;

    /**
     * 对应表字段为：t_virtual_coin.low_recharge_fees
     */
    private BigDecimal lowRechargeFees;

    /**
     * 对应表字段为：t_virtual_coin.withdraw_fees
     */
    private BigDecimal withdrawFees;

    /**
     * 对应表字段为：t_virtual_coin.low_withdraw_fees
     */
    private BigDecimal lowWithdrawFees;

    /**
     * 对应表字段为：t_virtual_coin.single_low_recharge
     */
    private BigDecimal singleLowRecharge;

    /**
     * 对应表字段为：t_virtual_coin.single_high_recharge
     */
    private BigDecimal singleHighRecharge;

    /**
     * 对应表字段为：t_virtual_coin.day_high_recharge
     */
    private BigDecimal dayHighRecharge;

    /**
     * 对应表字段为：t_virtual_coin.single_low_withdraw
     */
    private BigDecimal singleLowWithdraw;

    /**
     * 对应表字段为：t_virtual_coin.single_high_withdraw
     */
    private BigDecimal singleHighWithdraw;

    /**
     * 对应表字段为：t_virtual_coin.day_high_withdraw
     */
    private BigDecimal dayHighWithdraw;

    private String iconUrl2;

    /**
     * 提现说明
     */
    private String withdrawDes;
    /**
     * 充值说明
     */
    private String rechargeDes;

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

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public BigDecimal getTradeFees() {
        return tradeFees;
    }

    public void setTradeFees(BigDecimal tradeFees) {
        this.tradeFees = tradeFees;
    }

    public BigDecimal getLowTradeFees() {
        return lowTradeFees;
    }

    public void setLowTradeFees(BigDecimal lowTradeFees) {
        this.lowTradeFees = lowTradeFees;
    }

    public BigDecimal getRechargeFees() {
        return rechargeFees;
    }

    public void setRechargeFees(BigDecimal rechargeFees) {
        this.rechargeFees = rechargeFees;
    }

    public BigDecimal getLowRechargeFees() {
        return lowRechargeFees;
    }

    public void setLowRechargeFees(BigDecimal lowRechargeFees) {
        this.lowRechargeFees = lowRechargeFees;
    }

    public BigDecimal getWithdrawFees() {
        return withdrawFees;
    }

    public void setWithdrawFees(BigDecimal withdrawFees) {
        this.withdrawFees = withdrawFees;
    }

    public BigDecimal getLowWithdrawFees() {
        return lowWithdrawFees;
    }

    public void setLowWithdrawFees(BigDecimal lowWithdrawFees) {
        this.lowWithdrawFees = lowWithdrawFees;
    }

    public BigDecimal getSingleLowRecharge() {
        return singleLowRecharge;
    }

    public void setSingleLowRecharge(BigDecimal singleLowRecharge) {
        this.singleLowRecharge = singleLowRecharge;
    }

    public BigDecimal getSingleHighRecharge() {
        return singleHighRecharge;
    }

    public void setSingleHighRecharge(BigDecimal singleHighRecharge) {
        this.singleHighRecharge = singleHighRecharge;
    }

    public BigDecimal getDayHighRecharge() {
        return dayHighRecharge;
    }

    public void setDayHighRecharge(BigDecimal dayHighRecharge) {
        this.dayHighRecharge = dayHighRecharge;
    }

    public BigDecimal getSingleLowWithdraw() {
        return singleLowWithdraw;
    }

    public void setSingleLowWithdraw(BigDecimal singleLowWithdraw) {
        this.singleLowWithdraw = singleLowWithdraw;
    }

    public BigDecimal getSingleHighWithdraw() {
        return singleHighWithdraw;
    }

    public void setSingleHighWithdraw(BigDecimal singleHighWithdraw) {
        this.singleHighWithdraw = singleHighWithdraw;
    }

    public BigDecimal getDayHighWithdraw() {
        return dayHighWithdraw;
    }

    public void setDayHighWithdraw(BigDecimal dayHighWithdraw) {
        this.dayHighWithdraw = dayHighWithdraw;
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
}
