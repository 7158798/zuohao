package com.ruizton.main.model;

/**
 * 人民币充值，提现信息流水
 * Created by luwei on 17-1-23.
 */
public class CapitalOperationLogVo {

    //订单id
    private String orderId;

    //登录名
    private String floginName;

    //真实姓名
    private String frealName;

    //提现金额(含手续费)
    private String withdrawAmount;

    //提现手续费
    private String withdrawFees;

    //提现用户收到的金额
    private String withdrawUserGetAmount;

    //手机号
    private String ftelephone;

    //提现到帐的银行卡名
    private String bankName;

    //银行卡帐号
    private String bankCardNo;

    //支行名称
    private String branchName;

    //到帐时间
    private String withdrawDate;

    //充值方式
    private String rechargeType;

    //充值金额
    private String rechargeAmount;

    //充值到帐时间
    private String rechargeDate;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

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

    public String getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(String withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public String getWithdrawFees() {
        return withdrawFees;
    }

    public void setWithdrawFees(String withdrawFees) {
        this.withdrawFees = withdrawFees;
    }

    public String getWithdrawUserGetAmount() {
        return withdrawUserGetAmount;
    }

    public void setWithdrawUserGetAmount(String withdrawUserGetAmount) {
        this.withdrawUserGetAmount = withdrawUserGetAmount;
    }

    public String getFtelephone() {
        return ftelephone;
    }

    public void setFtelephone(String ftelephone) {
        this.ftelephone = ftelephone;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getWithdrawDate() {
        return withdrawDate;
    }

    public void setWithdrawDate(String withdrawDate) {
        this.withdrawDate = withdrawDate;
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType;
    }

    public String getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(String rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public String getRechargeDate() {
        return rechargeDate;
    }

    public void setRechargeDate(String rechargeDate) {
        this.rechargeDate = rechargeDate;
    }
}
