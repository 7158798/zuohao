package com.szzc.common.utils.other.okcoin;

import java.math.BigDecimal;

/**
 * Created by lx on 17-2-13.
 */
public class OrderInfo {
    // 委托数量
    private BigDecimal amount;
    // 平均成交价
    private BigDecimal avg_price;
    // 委托时间
    private Long create_date;
    // 成交数量
    private BigDecimal deal_amount;
    // 订单ID
    private String order_id;
    // 订单ID(不建议使用)
    private String orders_id;
    // 委托价格
    private BigDecimal price;
    // -1:已撤销  0:未成交  1:部分成交  2:完全成交 4:撤单处理中
    private String status;
    // buy_market:市价买入 / sell_market:市价卖出
    private String type;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(BigDecimal avg_price) {
        this.avg_price = avg_price;
    }

    public Long getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Long create_date) {
        this.create_date = create_date;
    }

    public BigDecimal getDeal_amount() {
        return deal_amount;
    }

    public void setDeal_amount(BigDecimal deal_amount) {
        this.deal_amount = deal_amount;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(String orders_id) {
        this.orders_id = orders_id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
