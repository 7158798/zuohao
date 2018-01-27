package com.otc.facade.trade.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.util.Date;

public class TradeLog extends BasePo implements Serializable {



    /**
     * 交易id，对应表字段为：t_trade_log.trade_id
     */
    private Long tradeId;

    /**
     * 操作用户id，对应表字段为：t_trade_log.user_id
     */
    private Long userId;

    /**
     * 操作类型：01:用户操作  02：后台操作  03：系统操作，对应表字段为：t_trade_log.operation_type
     */
    private Integer operationType;

    /**
     * 操作前状态，对应表字段为：t_trade_log.before_status
     */
    private Integer beforeStatus;

    /**
     * 操作后状态，对应表字段为：t_trade_log.after_status
     */
    private Integer afterStatus;

    /**
     * 创建时间，对应表字段为：t_trade_log.createtime
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

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Integer getBeforeStatus() {
        return beforeStatus;
    }

    public void setBeforeStatus(Integer beforeStatus) {
        this.beforeStatus = beforeStatus;
    }

    public Integer getAfterStatus() {
        return afterStatus;
    }

    public void setAfterStatus(Integer afterStatus) {
        this.afterStatus = afterStatus;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}