package com.base.facade.info.pojo.vo;

import com.jucaifu.common.pojo.vo.BasePageVo;

import java.util.Date;

public class SysLogVo extends BasePageVo {

    // 按code查询
    private String code;
    private String method;
    // 起始时间 如：yyyy-MM-dd HH:mm:ss
    private Date startTime;
    // 结束时间 如：yyyy-MM-dd HH:mm:ss
    private Date endTime;

    //按ip地址查询
    private String ip;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
