package com.otc.facade.virtual.pojo.vo;

import com.jucaifu.common.pojo.vo.BasePageVo;

/**
 * Created by lx on 17-4-19.
 */
public class PoolVo extends BasePageVo {

    private Long coinId;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }
}
