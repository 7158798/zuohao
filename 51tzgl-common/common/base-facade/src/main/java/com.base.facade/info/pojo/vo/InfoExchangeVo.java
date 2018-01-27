package com.base.facade.info.pojo.vo;

import com.jucaifu.common.pojo.vo.BasePageVo;

import java.util.Date;

/**
 * Created by liuxun on 16-8-22.
 */
public class InfoExchangeVo extends BasePageVo {

    // 开始时间（创建日期）
    private Date startDate;
    // 结束时间（创建日期）
    private Date endDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
