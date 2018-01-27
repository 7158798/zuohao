package com.base.facade.info.pojo.bean;

/**
 * Created by liuxun on 16-8-23.
 */
public class InfoExchangeRespBean {
    // 货币编号
    private String curno;
    // 货币名称
    private String curnm;
    // 货币利率明细
    private InfoExchangeDetailRespBean BOC = new InfoExchangeDetailRespBean();

    public String getCurno() {
        return curno;
    }

    public void setCurno(String curno) {
        this.curno = curno;
    }

    public String getCurnm() {
        return curnm;
    }

    public void setCurnm(String curnm) {
        this.curnm = curnm;
    }

    public InfoExchangeDetailRespBean getBOC() {
        return BOC;
    }

    public void setBOC(InfoExchangeDetailRespBean BOC) {
        this.BOC = BOC;
    }
}
