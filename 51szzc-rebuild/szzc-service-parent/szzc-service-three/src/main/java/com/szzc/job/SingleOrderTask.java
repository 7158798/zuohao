package com.szzc.job;

import com.facade.core.wallet.cache.CacheHelper;
import com.facade.core.wallet.cache.key.CacheKeyHelper;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.property.PropertiesUtils;
import com.jucaifu.common.util.DateHelper;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.common.utils.other.chbtc.ChBtcUtils;
import com.szzc.common.utils.other.okcoin.OkCoinUtils;
import com.szzc.common.utils.other.okcoin.TickerResponse;
import com.szzc.job.bean.Source;
import com.szzc.job.bean.Ticker;
import com.szzc.job.bean.TickerResp;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lx on 17-6-29.
 */
@Component
public class SingleOrderTask {

    //@PostConstruct
    public void init(){
        run();
    }

    @Scheduled(cron = "30/120 * * * * ?")
    public void run(){
        // 获取配置的币中
        String coins = PropertiesUtils.getProperty("order.coins");
        List<Source> list = JsonHelper.jsonArrayStr2List(coins,Source.class);
        // 获取
        Long time = Long.valueOf(PropertiesUtils.getProperty("order.sleep.time"));
        while (true){
            try {
                if (list != null && list.size() > 0){
                    for (Source source : list){
                        TickerResp resp = new TickerResp();
                        Date date = new Date();
                        resp.setDate(DateHelper.date2String(date, DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
                        List<String> rList = source.getOther();
                        if (rList != null && rList.size() > 0){
                            String name = source.getShortName().toLowerCase();
                            List<Ticker> tickers = new ArrayList<>();
                            for (String str : rList){
                                Ticker ticker = new Ticker();
                                ticker.setPlatform(str);
                                try {
                                    String result = HttpClientHelper.sendGetRequest(str, Boolean.FALSE);
                                    result = filter(result);
                                    TickerResponse response = JsonHelper.jsonStr2Obj(result, TickerResponse.class);
                                    if (response != null && response.getTicker() != null
                                            && response.getTicker().getLast().compareTo(BigDecimal.ZERO) == 1){
                                        // 上次成交价格必须大于零
                                        ticker.setLast(response.getTicker().getLast());
                                    }
                                    if (ticker.getLast() != null){
                                        tickers.add(ticker);
                                    }
                                } catch (Exception ex){
                                    LOG.e(this,ex.getMessage() +  str,ex);
                                }
                            }
                            if (tickers.size() > 0){
                                resp.setTicker(tickers);
                                String key = CacheKeyHelper.buildSingleOrderKey(name.toUpperCase());
                                String result = JsonHelper.obj2JsonStr(resp);
                                LOG.d(this,"缓存的数据为：" + result);
                                CacheHelper.saveObj(key,result);
                            }
                        }
                    }
                }
                Thread.sleep(time);
            } catch (Exception ex){
                LOG.e(this,ex.getMessage(),ex);
            }
        }
    }

    private String filter(String result){
        if (result.indexOf("ticker") > 0){
            return result;
        }
        result = "{\"ticker\":" + result + "}";
        return result;
    }

    public static String post(String url,String data) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json, text/plain, */*");
        con.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
        con.setRequestProperty("Authorization", "Basic ZWxhc3RpYzpjaGFuZ2VtZQ==");
        con.setRequestProperty("Connection", "keep-alive");
        con.setRequestProperty("content-type", "application/json");
        con.setRequestProperty("Host", "elastic.cortesexplorer.com:9200");
        con.setRequestProperty("Origin", "https://explorer.lbry.io");
        con.setRequestProperty("Referer", "https://explorer.lbry.io/");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(data);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        if(responseCode != 200){
            return "{\"result\":null,\"error\":"+responseCode+",\"id\":1}";
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        inputLine = in.readLine();
        response.append(inputLine);
        in.close();
        return response.toString();
    }

    public static void main(String[] args) throws IOException {
        String result = HttpClientHelper.post("https://yunbi.com/api/v2/tickers.json", null);

        HashMap<String,String> map = JsonHelper.jsonStr2Obj(result,HashMap.class);

        System.out.println(map.size());
        List<Source> list = new ArrayList<>();
        List<String> platform = new ArrayList<>();
        //platform.add("http://api.chbtc.com/data/v1/ticker?symbol=btc_cny");
        //platform.add("https://www.okcoin.cn/api/v1/ticker.do?symbol=btc_cny");
        //platform.add("https://yunbi.com//api/v2/tickers/sccny.json");
        //platform.add("http://130.252.100.99:8080/api/ticker.html?symbol=sc");
        //platform.add("https://www.jubi.com/api/v1/ticker?coin=lsk");
        /*platform.add("https://www.okcoin.cn/api/v1/ticker.do?symbol=btc_cny");
        platform.add("http://data.bter.com/api/1/ticker/btc_cny");
        platform.add("http://api.chbtc.com/data/v1/ticker?currency=btc_cny");
        platform.add("https://yunbi.com/api/v2/tickers/btccny.json");
        platform.add("https://www.jubi.com/api/v1/ticker?coin=btc");*/
        /*platform.add("https://www.okcoin.cn/api/v1/ticker.do?symbol=ltc_cny");
        platform.add("http://data.bter.com/api/1/ticker/ltc_cny");
        platform.add("http://api.chbtc.com/data/v1/ticker?currency=ltc_cny");
        platform.add("https://www.jubi.com/api/v1/ticker?coin=ltc");*/
        /*platform.add("http://data.bter.com/api/1/ticker/etc_cny");
        platform.add("http://api.chbtc.com/data/v1/ticker?currency=etc_cny");
        platform.add("https://www.jubi.com/api/v1/ticker?coin=etc");
        platform.add("https://yunbi.com/api/v2/tickers/etccny.json");*/
        /*platform.add("http://data.bter.com/api/1/ticker/zec_cny");
        platform.add("https://yunbi.com/api/v2/tickers/zeccny.json");*/
        platform.add("https://yunbi.com/api/v2/tickers/anscny.json");
        platform.add("https://www.jubi.com/api/v1/ticker?coin=ans");
        Source source = new Source();
        //source.setShortName("SC");
        //source.setShortName("BTC");
        //source.setShortName("ETH");
        //source.setShortName("ETC");
        //source.setShortName("ZEC");
        source.setShortName("ANS");
        source.setOther(platform);
        list.add(source);
        System.out.println(JsonHelper.obj2JsonStr(list));
    }
}
