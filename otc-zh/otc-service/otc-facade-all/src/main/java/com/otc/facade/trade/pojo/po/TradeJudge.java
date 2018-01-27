package com.otc.facade.trade.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;
import java.io.Serializable;
import java.util.Date;

public class TradeJudge extends BasePo implements Serializable {

    /**
     * 交易id，对应表字段为：t_trade_judge.trade_id
     */
    private Long tradeId;

    /**
     * 评价人id，对应表字段为：t_trade_judge.user_id
     */
    private Long userId;

    /**
     * 被评价人id，对应表字段为：t_trade_judge.accept_user_id
     */
    private Long acceptUserId;

    /**
     * 交易状态 1:差评 3：中评 5：好评，对应表字段为：t_trade_judge.judge_level
     */
    private Integer judgeLevel;

    /**
     * 评价内容，对应表字段为：t_trade_judge.judge_context
     */
    private String judgeContext;

    /**
     * 创建时间，对应表字段为：t_trade_judge.createtime
     */
    private Date createtime;


    private static final long serialVersionUID = 1L;


    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAcceptUserId() {
        return acceptUserId;
    }

    public void setAcceptUserId(Long acceptUserId) {
        this.acceptUserId = acceptUserId;
    }

    public Integer getJudgeLevel() {
        return judgeLevel;
    }

    public void setJudgeLevel(Integer judgeLevel) {
        this.judgeLevel = judgeLevel;
    }

    public String getJudgeContext() {
        return judgeContext;
    }

    public void setJudgeContext(String judgeContext) {
        this.judgeContext = judgeContext == null ? null : judgeContext.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}