package com.szzc.job.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by lx on 17-6-29.
 */
public class Ticker implements Serializable {

    // 平台数据
    private String platform;
    // 最新成交价格
    private BigDecimal last;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public BigDecimal getLast() {
        return last;
    }

    public void setLast(BigDecimal last) {
        this.last = last;
    }
}
