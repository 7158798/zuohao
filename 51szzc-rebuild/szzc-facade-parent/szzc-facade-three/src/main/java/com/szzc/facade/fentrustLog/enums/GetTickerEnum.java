package com.szzc.facade.fentrustLog.enums;


/**
 * Created by zygong on 17-5-24.
 */
public enum GetTickerEnum {

    SYMBOL("symbol", "bterzeccny,coinbaseethusd,chbtcetccny,bitfinexethusd,yunbietccny,chbtcethcny," +
            "bterethcny,bterethbtc,yunbiethcny,btceethusd,yanbaozec2cny,yanbaoans2cny,krakenethusd," +
            "bichuangetccny,poloniexethbtc,poloniexetcbtc,yanbaobtc2cny" +
            ",poloniexzecbtc,bitfinexetcusd,jubibtccny,jubiltccny,krakenetcusd,yunbizeccny" +
            ",bitekuangethcny,huobibtccny,okcoincnbtccny,huobiltccny,okcoincnltccny,yunbibtccny,chbtcltccny" +
            ",btcchinaltccny,chbtcbtccny,btcchinabtccny,bitekuangbtccny,yuanbaobtccny,btctradebtccny,btc38btccny" +
            ",bterbtccny,bitvcbtccnyfuture,bitekuangltccny,bichuangbtccny,bterltccny" +
            ",yanbaoltc2cny,btc38ltccny,bichuangltccny" +
            ",bitfinexbtcusd,coinbasebtcusd,bitfinexltcusd,bitstampbtcusd,btceltcusd,coinbaseltcusd,btcebtcusd" +
            ",btctradeltccny,bitmexbtcusd,allcoinbtcusd,bithumbbtccny,korbitbtckrw,coinonebtckrw" +
            ",jubiticcny,yunbisccny");

    private String key;
    private String value;

    GetTickerEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValueByKey(String key){
        for(GetTickerEnum a : GetTickerEnum.values()){
            if(a.getKey().equals(key)){
                return a.getValue();
            }
        }
        return null;
    }

    public static GetTickerEnum getByKey(String key){
        for(GetTickerEnum a : GetTickerEnum.values()){
            if(a.getKey().equals(key)){
                return a;
            }
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
