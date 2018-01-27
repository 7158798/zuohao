package com.base.facade.info.pojo.vo;

import com.jucaifu.common.pojo.vo.BasePageVo;

import java.util.Date;

/**
 * 虚拟货币
 */
public class VirtualCurrencyVo extends BasePageVo {


    /**
     * 币种中文名称，对应表字段为：t_virtual_currency.cncy_cn_name
     */
    private String cncyCnName;

    /**
     * 状态
     */
    private Integer status;


    public String getCncyCnName() {
        return cncyCnName;
    }

    public void setCncyCnName(String cncyCnName) {
        this.cncyCnName = cncyCnName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}