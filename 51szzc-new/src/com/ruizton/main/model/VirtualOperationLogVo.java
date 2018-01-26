package com.ruizton.main.model;

/**
 * 虚拟币操作流水vo
 * Created by luwei on 17-1-22.
 */
public class VirtualOperationLogVo {

    //登录名
    private String floginName;

    //真实姓名
    private String frealName;

    //手机号
    private String ftelephone;

    //充值虚拟币数量
    private String rechargeNum;

    //充值地址
    private String rechargeAddress;

    //充值到帐时间
    private String rechargeDate;

    //提现虚拟币数量(含手续费)
    private String withdrawNum;

    //提现虚拟币手续费
    private String withdrawFees;

    //提现时，用户收到的虚拟币数量(含手续费)
    private String withdrawUserGetNum;

    //提现虚拟币地址
    private String withdrawAddress;

    //提现到帐时间
    private String withdrawDate;


    public String getFloginName() {
        return floginName;
    }

    public void setFloginName(String floginName) {
        this.floginName = floginName;
    }

    public String getFrealName() {
        return frealName;
    }

    public void setFrealName(String frealName) {
        this.frealName = frealName;
    }

    public String getFtelephone() {
        return ftelephone;
    }

    public void setFtelephone(String ftelephone) {
        this.ftelephone = ftelephone;
    }

    public String getRechargeNum() {
        return rechargeNum;
    }

    public void setRechargeNum(String rechargeNum) {
        this.rechargeNum = rechargeNum;
    }

    public String getRechargeAddress() {
        return rechargeAddress;
    }

    public void setRechargeAddress(String rechargeAddress) {
        this.rechargeAddress = rechargeAddress;
    }

    public String getRechargeDate() {
        return rechargeDate;
    }

    public void setRechargeDate(String rechargeDate) {
        this.rechargeDate = rechargeDate;
    }

    public String getWithdrawNum() {
        return withdrawNum;
    }

    public void setWithdrawNum(String withdrawNum) {
        this.withdrawNum = withdrawNum;
    }

    public String getWithdrawFees() {
        return withdrawFees;
    }

    public void setWithdrawFees(String withdrawFees) {
        this.withdrawFees = withdrawFees;
    }

    public String getWithdrawUserGetNum() {
        return withdrawUserGetNum;
    }

    public void setWithdrawUserGetNum(String withdrawUserGetNum) {
        this.withdrawUserGetNum = withdrawUserGetNum;
    }

    public String getWithdrawAddress() {
        return withdrawAddress;
    }

    public void setWithdrawAddress(String withdrawAddress) {
        this.withdrawAddress = withdrawAddress;
    }

    public String getWithdrawDate() {
        return withdrawDate;
    }

    public void setWithdrawDate(String withdrawDate) {
        this.withdrawDate = withdrawDate;
    }
}
