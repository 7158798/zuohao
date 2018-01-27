package com.szzc.common.utils.other.chbtc;


import com.szzc.common.utils.other.okcoin.ABSCoinUtils;
import com.szzc.common.utils.other.okcoin.TickerResponse;

/**
 * 中国比特币网
 * Created by lx on 17-2-10.
 */
public class ChBtcUtils extends ABSCoinUtils implements ChBtcConstant {

    /**
     * 行情
     * @param symbol btc_usd:比特币    ltc_usd :莱特币
     * @return
     */
    public static TickerResponse ticker(String symbol){
        return get(TICKER_URL + "?symbol=" + bulid(symbol),TickerResponse.class);
    }


    public static void main(String[] args) {
        TickerResponse response = ticker("btc");
        System.out.println("dfads");
    }

}
