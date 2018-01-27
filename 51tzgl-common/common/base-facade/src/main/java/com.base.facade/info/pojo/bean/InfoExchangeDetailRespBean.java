package com.base.facade.info.pojo.bean;

import com.base.facade.info.pojo.po.InfoExchangeDetail;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yangyy on 16-8-23.
 */
public class InfoExchangeDetailRespBean {
    // 现汇卖出价
    private BigDecimal se_sell;
    // 现汇买入价
    private BigDecimal se_buy;
    // 现钞卖出价
    private BigDecimal cn_sell;
    // 现钞买入价
    private BigDecimal cn_buy;
    // 中行折算价
    private BigDecimal middle;
    // 数据更新时间
    private Date upddate;

    public BigDecimal getSe_sell() {
        return se_sell;
    }

    public void setSe_sell(BigDecimal se_sell) {
        this.se_sell = se_sell;
    }

    public BigDecimal getSe_buy() {
        return se_buy;
    }

    public void setSe_buy(BigDecimal se_buy) {
        this.se_buy = se_buy;
    }

    public BigDecimal getCn_sell() {
        return cn_sell;
    }

    public void setCn_sell(BigDecimal cn_sell) {
        this.cn_sell = cn_sell;
    }

    public BigDecimal getCn_buy() {
        return cn_buy;
    }

    public void setCn_buy(BigDecimal cn_buy) {
        this.cn_buy = cn_buy;
    }

    public BigDecimal getMiddle() {
        return middle;
    }

    public void setMiddle(BigDecimal middle) {
        this.middle = middle;
    }

    public Date getUpddate() {
        return upddate;
    }

    public void setUpddate(Date upddate) {
        this.upddate = upddate;
    }

    /**
     * 复制利率详细数据
     * @param source
     * @param traget
     */
    public static void copyInfoExchangeDetail(InfoExchangeDetailRespBean source,InfoExchangeDetail traget){
        traget.setfBuyPri(zeroToNull(source.getSe_buy()));
        traget.setfSellPri(zeroToNull(source.getSe_sell()));
        traget.setmBuyPri(zeroToNull(source.getCn_buy()));
        traget.setmSellPri(zeroToNull(source.getCn_sell()));
        traget.setBankConversionPri(source.getMiddle());
        traget.setUpdateDatetime(source.getUpddate());
        // 交易单位
        traget.setTradeUnit(new BigDecimal(100));
    }

    /**
     * 零转空格
     * @param value
     * @return
     */
    private static BigDecimal zeroToNull(BigDecimal value){

        if (value != null && value.compareTo(BigDecimal.ZERO) == 0){

            return null;
        }
        return value;
    }
}
