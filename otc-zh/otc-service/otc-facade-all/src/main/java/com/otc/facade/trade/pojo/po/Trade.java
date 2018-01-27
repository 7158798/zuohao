package com.otc.facade.trade.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Trade extends BasePo implements Serializable {

    /**
     * 交易编号，对应表字段为：t_trade.trade_no
     */
    private String tradeNo;

    /**
     * 公告id，对应表字段为：t_trade.advert_id
     */
    private Long advertId;

    /**
     * 币种id，对应表字段为：t_trade.coin_id
     */
    private Long coinId;

    /**
     * 买方id，对应表字段为：t_trade.buy_user_id
     */
    private Long buyUserId;

    /**
     * 卖方id，对应表字段为：t_trade.sell_user_id
     */
    private Long sellUserId;

    /**
     * 聊天id，对应表字段为：t_trade.chart_id
     */
    private Long chartId;

    /**
     * 交易数量，对应表字段为：t_trade.trade_account
     */
    private BigDecimal tradeAccount;

    /**
     * 交易单价，对应表字段为：t_trade.trade_price
     */
    private BigDecimal tradePrice;

    /**
     * 交易额，对应表字段为：t_trade.trade_amount
     */
    private BigDecimal tradeAmount;

    /**
     * 交易手续费，对应表字段为：t_trade.trade_fee
     */
    private BigDecimal tradeFee;

    /**
     * 买家支付时间，对应表字段为：t_trade.pay_time
     */
    private Date payTime;

    /**
     * 卖家确认时间-完成，对应表字段为：t_trade.confirm_time
     */
    private Date confirmTime;

    /**
     * 卖家申诉时间，对应表字段为：t_trade.appeal_time
     */
    private Date appealTime;

    /**
     * 最迟付款时间，对应表字段为：t_trade.pay_end_time
     */
    private Date payEndTime;

    /**
     * 交易状态 0：初始 1:已付款 2：申诉中 4：已完成 3：已取消，对应表字段为：t_trade.trade_status
     */
    private Integer tradeStatus;

    /**
     * 交易结束时间，对应表字段为：t_trade.complete_time
     */
    private Date completeTime;

    /**
     * 取消类型：1：买家超时付款 2：买家取消交易 3：申诉判定，对应表字段为：t_trade.cancel_type
     */
    private Integer cancelType;

    /**
     * 备注，对应表字段为：t_trade.remark
     */
    private String remark;

    /**
     * 创建时间，对应表字段为：t_trade.create_time
     */
    private Date createTime;

    /**
     * 更新时间，对应表字段为：t_trade.update_time
     */
    private Date updateTime;

    /**
     * 评价状态 0：初始 1:买家已评价 2：卖家已评价 3：已完成评价，对应表字段为：t_trade.judge_status
     */
    private Integer judgeStatus;

    /**
     * 是否有过申诉，对应表字段为：t_trade.is_appeal
     */
    private Boolean isAppeal;

    /**
     * 实际到帐数
     */
    private BigDecimal realAccount;//实际到帐数


    /**
     * 后台操作备注，对应表字段为：t_trade.remark_console
     */
    private String remarkConsole;

    public String getRemarkConsole() {
        return remarkConsole;
    }

    public void setRemarkConsole(String remarkConsole) {
        this.remarkConsole = remarkConsole;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }

    public Long getAdvertId() {
        return advertId;
    }

    public void setAdvertId(Long advertId) {
        this.advertId = advertId;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public Long getBuyUserId() {
        return buyUserId;
    }

    public void setBuyUserId(Long buyUserId) {
        this.buyUserId = buyUserId;
    }

    public Long getSellUserId() {
        return sellUserId;
    }

    public void setSellUserId(Long sellUserId) {
        this.sellUserId = sellUserId;
    }

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }

    public BigDecimal getTradeAccount() {
        return tradeAccount;
    }

    public void setTradeAccount(BigDecimal tradeAccount) {
        this.tradeAccount = tradeAccount;
    }

    public BigDecimal getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(BigDecimal tradePrice) {
        this.tradePrice = tradePrice;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public BigDecimal getTradeFee() {
        return tradeFee;
    }

    public void setTradeFee(BigDecimal tradeFee) {
        this.tradeFee = tradeFee;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Date getAppealTime() {
        return appealTime;
    }

    public void setAppealTime(Date appealTime) {
        this.appealTime = appealTime;
    }

    public Date getPayEndTime() {
        return payEndTime;
    }

    public void setPayEndTime(Date payEndTime) {
        this.payEndTime = payEndTime;
    }

    public Integer getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public Integer getCancelType() {
        return cancelType;
    }

    public void setCancelType(Integer cancelType) {
        this.cancelType = cancelType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getJudgeStatus() {
        return judgeStatus;
    }

    public void setJudgeStatus(Integer judgeStatus) {
        this.judgeStatus = judgeStatus;
    }

    public Boolean getIsAppeal() {
        return isAppeal;
    }

    public void setIsAppeal(Boolean isAppeal) {
        this.isAppeal = isAppeal;
    }

    public BigDecimal getRealAccount() {
        return realAccount;
    }

    public void setRealAccount(BigDecimal realAccount) {
        this.realAccount = realAccount;
    }
}