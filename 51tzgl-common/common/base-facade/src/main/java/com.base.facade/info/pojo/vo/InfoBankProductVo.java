package com.base.facade.info.pojo.vo;

import com.jucaifu.common.pojo.vo.BasePageVo;

import java.util.Date;
import java.util.List;

/**
 * Created by zh on 16-9-21.
 */
public class InfoBankProductVo extends BasePageVo{
    private Date startDate;

    private Date endDate;
    // 期限 或 期限CODE
    private String period;
    // 开始期限
    private String startPeriod;
    // 结束期限
    private String endPeriod;
    // 银行集合
    private List<String> bank;
    // 排序
    private String sortType;

    private String bankId;

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<String> getBank() {
        return bank;
    }

    public void setBank(List<String> bank) {
        this.bank = bank;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(String startPeriod) {
        this.startPeriod = startPeriod;
    }

    public String getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }
}
