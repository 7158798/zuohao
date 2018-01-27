package com.otc.api.console.ctrl.coin.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

import java.util.List;

/**
 * Created by zygong on 17-7-12.
 */
public class WithdrawfeesAddReq extends WebApiBaseReq {
    private List<WithdrawfeesReq> list;

    public List<WithdrawfeesReq> getList() {
        return list;
    }

    public void setList(List<WithdrawfeesReq> list) {
        this.list = list;
    }
}
