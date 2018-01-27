package com.otc.api.console.ctrl.coin.response;

/**
 * Created by lx on 17-5-23.
 */
public class VirtualCoinTypeResp {

    private Long coinId;

    private String coinName;

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }
}
