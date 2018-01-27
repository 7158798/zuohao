package com.base.facade.banner.pojo.vo;

import com.jucaifu.common.pojo.vo.BasePageVo;

import java.util.Date;

/**
 * Created by yangyy on 15-11-26.
 */
public class ActivityVo extends BasePageVo {

    private Integer showPosition;

    private String productId;

    private Boolean active;

    private Date startTime;

    private Date endTime;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean statusCode) {
        this.active = statusCode;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getShowPosition() {
        return showPosition;
    }

    public void setShowPosition(Integer showPosition) {
        this.showPosition = showPosition;
    }
}
