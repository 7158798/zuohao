package com.otc.facade.advertisement.pojo.vo;

import com.jucaifu.common.pojo.vo.BasePageVo;

import java.util.Date;

/**
 * Created by zygong on 17-4-28.
 */
public class PriceFormulaVo extends BasePageVo {
    private Integer coinId;

    public Integer getCoinId() {
        return coinId;
    }

    public void setCoinId(Integer coinId) {
        this.coinId = coinId;
    }
}
