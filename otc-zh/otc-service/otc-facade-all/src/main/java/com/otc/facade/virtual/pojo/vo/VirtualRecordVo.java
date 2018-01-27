package com.otc.facade.virtual.pojo.vo;

import com.jucaifu.common.pojo.vo.BasePageVo;

import java.util.Date;
import java.util.List;

/**
 * Created by lx on 17-4-17.
 */
public class VirtualRecordVo extends BasePageVo {

    private Long userId;
    // 充值
    private String type;
    // 状态
    private String status;
    // 指定状态列表
    private List<String> statusList;
    // 条件
    private String condition;

    private Long coinId;

    private Date start;
    private Date end;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
