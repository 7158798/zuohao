package com.ruizton.main.model;

import java.math.BigDecimal;

/**
 * Created by luwei on 17-3-6.
 */
public class FentrustlogVo implements java.io.Serializable {

    //虚拟币id
    private Integer fviFid;

    //委托类型
    private  Integer  fEntrustType;

    //成交总金额
    private BigDecimal amount;

    //成交手续费
    private BigDecimal fees;

    //成交数量
    private BigDecimal count;


    public Integer getFviFid() {
        return fviFid;
    }

    public void setFviFid(Integer fviFid) {
        this.fviFid = fviFid;
    }

    public Integer getfEntrustType() {
        return fEntrustType;
    }

    public void setfEntrustType(Integer fEntrustType) {
        this.fEntrustType = fEntrustType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFees() {
        return fees;
    }

    public void setFees(BigDecimal fees) {
        this.fees = fees;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }
}
