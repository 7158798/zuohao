package com.otc.api.web.ctrl.advertisement.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

public class AdvertisementTimeSaveReq extends WebApiBaseReq {
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
        this.startTime1 = startTime1;
    }

    public String getEndTime1() {
        return endTime1;
    }

    public void setEndTime1(String endTime1) {
        this.endTime1 = endTime1;
    }

    public String getStartTime2() {
        return startTime2;
    }

    public void setStartTime2(String startTime2) {
        this.startTime2 = startTime2;
    }

    public String getEndTime2() {
        return endTime2;
    }

    public void setEndTime2(String endTime2) {
        this.endTime2 = endTime2;
    }
}