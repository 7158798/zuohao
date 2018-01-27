package com.base.facade.info.pojo.poex;

import com.base.facade.info.pojo.po.InfoRateDetail;

import java.util.List;

/**
 * Created by zh on 16-8-25.
 */
public class InfoRateExcelEx {
    private String bankName;

    private String type;

    private String batchNo;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    private List<InfoRateDetail> detailList;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<InfoRateDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<InfoRateDetail> detailList) {
        this.detailList = detailList;
    }
}
