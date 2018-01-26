package com.ruizton.main.model.OtherPlatform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zygong on 17-3-23.
 */
public class Btc {
    private OtherPlatform redundancy;// 为了解析数据设得冗余对象
    private OtherPlatform bitstamp;
    private OtherPlatform btcchina;
    private OtherPlatform okcoin;
    private OtherPlatform fxbtc;
    private OtherPlatform huobi;
    private OtherPlatform chbtc;
    private OtherPlatform btce;
    private OtherPlatform btctrade;

    public OtherPlatform getBitstamp() {
        return bitstamp;
    }

    public void setBitstamp(OtherPlatform bitstamp) {
        this.bitstamp = bitstamp;
    }

    public OtherPlatform getBtcchina() {
        return btcchina;
    }

    public void setBtcchina(OtherPlatform btcchina) {
        this.btcchina = btcchina;
    }

    public OtherPlatform getOkcoin() {
        return okcoin;
    }

    public void setOkcoin(OtherPlatform okcoin) {
        this.okcoin = okcoin;
    }

    public OtherPlatform getFxbtc() {
        return fxbtc;
    }

    public void setFxbtc(OtherPlatform fxbtc) {
        this.fxbtc = fxbtc;
    }

    public OtherPlatform getHuobi() {
        return huobi;
    }

    public void setHuobi(OtherPlatform huobi) {
        this.huobi = huobi;
    }

    public OtherPlatform getChbtc() {
        return chbtc;
    }

    public void setChbtc(OtherPlatform chbtc) {
        this.chbtc = chbtc;
    }

    public OtherPlatform getBtce() {
        return btce;
    }

    public void setBtce(OtherPlatform btce) {
        this.btce = btce;
    }

    public OtherPlatform getBtctrade() {
        return btctrade;
    }

    public void setBtctrade(OtherPlatform btctrade) {
        this.btctrade = btctrade;
    }

    public Map<String, OtherPlatform> getAll(){
        Map<String, OtherPlatform> map = new HashMap<String, OtherPlatform>();
        map.put("bitstamp", bitstamp);
        map.put("btce", btce);
        map.put("btcchina", btcchina);
        map.put("huobi", huobi);
        map.put("okcoin", okcoin);
        map.put("btctrade", btctrade);
        map.put("chbtc", chbtc);
        return map;
    }
}
