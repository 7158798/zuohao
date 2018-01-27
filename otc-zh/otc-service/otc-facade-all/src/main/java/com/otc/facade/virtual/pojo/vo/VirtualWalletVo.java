package com.otc.facade.virtual.pojo.vo;

import com.jucaifu.common.pojo.vo.BasePageVo;

/**
 * Created by lx on 17-4-19.
 */
public class VirtualWalletVo extends BasePageVo {

    private String condition;

    private Long coinId;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }
}
