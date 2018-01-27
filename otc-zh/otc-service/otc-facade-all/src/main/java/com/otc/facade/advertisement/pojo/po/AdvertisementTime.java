package com.otc.facade.advertisement.pojo.po;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.util.Date;

public class AdvertisementTime extends BasePo implements Serializable {

    /**
     * 用户，对应表字段为：t_advertisement_time.user_id
     */
    private Long userId;

    /**
     * 1：在线购买   2：在线出售，对应表字段为：t_advertisement_time.type
     */
    private Integer type;

    /**
     * 1：全部日期   2：仅工作日  3：仅周末节假日，对应表字段为：t_advertisement_time.status
     */
    private Integer status;

    /**
     * 开始时间1，对应表字段为：t_advertisement_time.start_time1
     */
    private String startTime1;

    /**
     * 结束时间1，对应表字段为：t_advertisement_time.end_time1
     */
    private String endTime1;

    /**
     * 开始时间2，对应表字段为：t_advertisement_time.start_time2
     */
    private String startTime2;

    /**
     * 结束时间2，对应表字段为：t_advertisement_time.end_time2
     */
    private String endTime2;

    /**
     * 创建时间，对应表字段为：t_advertisement_time.create_time
     */
    private Date createTime;

    /**
     * 更新时间，对应表字段为：t_advertisement_time.update_time
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStartTime1() {
        return startTime1;
    }

    public void setStartTime1(String startTime1) {
        this.startTime1 = startTime1 == null ? null : startTime1.trim();
    }

    public String getEndTime1() {
        return endTime1;
    }

    public void setEndTime1(String endTime1) {
        this.endTime1 = endTime1 == null ? null : endTime1.trim();
    }

    public String getStartTime2() {
        return startTime2;
    }

    public void setStartTime2(String startTime2) {
        this.startTime2 = startTime2 == null ? null : startTime2.trim();
    }

    public String getEndTime2() {
        return endTime2;
    }

    public void setEndTime2(String endTime2) {
        this.endTime2 = endTime2 == null ? null : endTime2.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}