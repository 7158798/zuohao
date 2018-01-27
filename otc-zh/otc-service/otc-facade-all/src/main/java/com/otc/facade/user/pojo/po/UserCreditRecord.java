package com.otc.facade.user.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;
import java.io.Serializable;
import java.util.Date;

public class UserCreditRecord extends BasePo implements Serializable {

    /**
     * id，对应表字段为：t_user_credit_record.id
     */
    private Long id;

    /**
     * 用户id，对应表字段为：t_user_credit_record.user_id
     */
    private Long userId;

    /**
     * 交易总次数，对应表字段为：t_user_credit_record.trade_all_times
     */
    private Integer tradeAllTimes;

    /**
     * 交易完成次数，对应表字段为：t_user_credit_record.trade_sucess_times
     */
    private Integer tradeSucessTimes;

    /**
     * 卖出总次数，对应表字段为：t_user_credit_record.sell_all_times
     */
    private Integer sellAllTimes;

    /**
     * 卖出成功次数，对应表字段为：t_user_credit_record.sell_sucess_times
     */
    private Integer sellSucessTimes;

    /**
     * 买入总次数，对应表字段为：t_user_credit_record.buy_all_times
     */
    private Integer buyAllTimes;

    /**
     * 买入成功次数，对应表字段为：t_user_credit_record.buy_sucess_times
     */
    private Integer buySucessTimes;

    /**
     * 申诉次数，对应表字段为：t_user_credit_record.appeal_times
     */
    private Integer appealTimes;

    /**
     * 被申诉次数，对应表字段为：t_user_credit_record.be_appealed_times
     */
    private Integer beAppealedTimes;

    /**
     * 评价次数，对应表字段为：t_user_credit_record.judge_times
     */
    private Integer judgeTimes;

    /**
     * 好评次数，对应表字段为：t_user_credit_record.good_judge_times
     */
    private Integer goodJudgeTimes;

    /**
     * 差评次数，对应表字段为：t_user_credit_record.bad_judge_times
     */
    private Integer badJudgeTimes;

    /**
     * 中评次数，对应表字段为：t_user_credit_record.common_judge_times
     */
    private Integer commonJudgeTimes;

    /**
     * 放行时间，对应表字段为：t_user_credit_record.confirm_minute
     */
    private Integer confirmMinute;

    /**
     * 创建时间，对应表字段为：t_user_credit_record.create_time
     */
    private Date createTime;

    /**
     * 创建时间，对应表字段为：t_user_credit_record.update_time
     */
    private Date updateTime;

    /**
     * 创建时间，对应表字段为：t_user_credit_record.last_trade_time
     */
    private Date lastTradeTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getTradeAllTimes() {
        return tradeAllTimes;
    }

    public void setTradeAllTimes(Integer tradeAllTimes) {
        this.tradeAllTimes = tradeAllTimes;
    }

    public Integer getTradeSucessTimes() {
        return tradeSucessTimes;
    }

    public void setTradeSucessTimes(Integer tradeSucessTimes) {
        this.tradeSucessTimes = tradeSucessTimes;
    }

    public Integer getSellAllTimes() {
        return sellAllTimes;
    }

    public void setSellAllTimes(Integer sellAllTimes) {
        this.sellAllTimes = sellAllTimes;
    }

    public Integer getSellSucessTimes() {
        return sellSucessTimes;
    }

    public void setSellSucessTimes(Integer sellSucessTimes) {
        this.sellSucessTimes = sellSucessTimes;
    }

    public Integer getBuyAllTimes() {
        return buyAllTimes;
    }

    public void setBuyAllTimes(Integer buyAllTimes) {
        this.buyAllTimes = buyAllTimes;
    }

    public Integer getBuySucessTimes() {
        return buySucessTimes;
    }

    public void setBuySucessTimes(Integer buySucessTimes) {
        this.buySucessTimes = buySucessTimes;
    }

    public Integer getAppealTimes() {
        return appealTimes;
    }

    public void setAppealTimes(Integer appealTimes) {
        this.appealTimes = appealTimes;
    }

    public Integer getBeAppealedTimes() {
        return beAppealedTimes;
    }

    public void setBeAppealedTimes(Integer beAppealedTimes) {
        this.beAppealedTimes = beAppealedTimes;
    }

    public Integer getJudgeTimes() {
        return judgeTimes;
    }

    public void setJudgeTimes(Integer judgeTimes) {
        this.judgeTimes = judgeTimes;
    }

    public Integer getGoodJudgeTimes() {
        return goodJudgeTimes;
    }

    public void setGoodJudgeTimes(Integer goodJudgeTimes) {
        this.goodJudgeTimes = goodJudgeTimes;
    }

    public Integer getBadJudgeTimes() {
        return badJudgeTimes;
    }

    public void setBadJudgeTimes(Integer badJudgeTimes) {
        this.badJudgeTimes = badJudgeTimes;
    }

    public Integer getCommonJudgeTimes() {
        return commonJudgeTimes;
    }

    public void setCommonJudgeTimes(Integer commonJudgeTimes) {
        this.commonJudgeTimes = commonJudgeTimes;
    }

    public Integer getConfirmMinute() {
        return confirmMinute;
    }

    public void setConfirmMinute(Integer confirmMinute) {
        this.confirmMinute = confirmMinute;
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

    public Date getLastTradeTime() {
        return lastTradeTime;
    }

    public void setLastTradeTime(Date lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }
}