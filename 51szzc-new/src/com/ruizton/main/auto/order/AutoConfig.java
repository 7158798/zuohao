package com.ruizton.main.auto.order;

import com.ruizton.main.model.AutoOrder;

import java.math.BigDecimal;

/**
 * Created by lx on 17-1-24.
 */
public class AutoConfig {

    private BigDecimal xnb;

    private BigDecimal cny;

    public BigDecimal getXnb() {
        return xnb;
    }

    public void setXnb(BigDecimal xnb) {
        this.xnb = xnb;
    }

    public BigDecimal getCny() {
        return cny;
    }

    public void setCny(BigDecimal cny) {
        this.cny = cny;
    }

    /**
     *
     * @param autoOrder
     * @param price
     */
    public void calcLimit(AutoOrder autoOrder,BigDecimal price){
        // 计算虚拟币的下限
        BigDecimal quanity = new BigDecimal(autoOrder.getRandom() + 1);
        BigDecimal tQaun = autoOrder.getRatio().multiply(quanity) ;
        this.xnb = tQaun;
        // 计算人民币的下限
        BigDecimal tRmb = tQaun.multiply(price);
        this.cny = tRmb;
    }

}
