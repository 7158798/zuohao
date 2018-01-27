package com.otc.facade.advertisement.pojo.poex;

import java.util.List;

/**
 * Created by zygong on 17-5-2.
 */
public class Datas {
    private List<EthCoins> tradeAPIResult;

    private List<EthCoins> ethCoins;

    public List<EthCoins> getTradeAPIResult() {
        return tradeAPIResult;
    }

    public void setTradeAPIResult(List<EthCoins> tradeAPIResult) {
        this.tradeAPIResult = tradeAPIResult;
    }

    public List<EthCoins> getEthCoins() {
        return ethCoins;
    }

    public void setEthCoins(List<EthCoins> ethCoins) {
        this.ethCoins = ethCoins;
    }
}
