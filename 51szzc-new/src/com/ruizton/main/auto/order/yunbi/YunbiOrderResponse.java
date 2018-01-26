package com.ruizton.main.auto.order.yunbi;

import java.math.BigDecimal;

/**
 *
 * Created by lx on 17-3-1.
 */
public class YunbiOrderResponse {

    private Long id;

    private BigDecimal price;

    private BigDecimal volume;

    private String created_at;

    private String side;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
