package com.ruizton.main.controller.admin.robot.request;

import java.math.BigDecimal;

/**
 * Created by lx on 17-3-21.
 */
public class RobotTradeReq {

    private Integer id;

    private BigDecimal amount;

    private BigDecimal cost;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
