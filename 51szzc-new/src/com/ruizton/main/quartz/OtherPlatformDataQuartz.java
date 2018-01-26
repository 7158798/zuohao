package com.ruizton.main.quartz;

import com.google.gson.Gson;
import com.ruizton.main.Enum.OtherPlatformEnum;
import com.ruizton.main.cache.biz.FquoteCacheManager;
import com.ruizton.main.cache.biz.GeneralListCache;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.OtherPlatform.*;
import com.ruizton.main.service.front.FquotesService;
import com.ruizton.util.HttpClientHelper;
import com.ruizton.util.JsonHelper;
import com.ruizton.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 推荐上限/每天不同的15个随机标签
 * Created by zygong on 17-3-8.
 */
public class OtherPlatformDataQuartz {
    @Autowired
    protected FquotesService fquotesService;
    private final String urlNameStringHao123 = "http://www.hao123.com/bitcoin/bitcurrent";
//    private final String urlNameStringBtc123 = "https://www.btc123.com/trade";
    private final String urlNameStringBtc123 = "https://www.btc123.com/trades/getTradeData";
    ConstantMap map = new ConstantMap();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
    private final static String myPlatform = "_51szzc";

    /**
     * 通过hao123接口，获取其他平台行情数据
     */
    /*
    public void getData(){
        String result="";
        try {
            // 根据地址获取请求
            HttpGet request = new HttpGet(urlNameStringHao123);//这里发送get请求
            // 获取当前客户端对象
            HttpClient httpClient = new DefaultHttpClient();
            // 通过请求对象获取响应对象
            HttpResponse response = httpClient.execute(request);

            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result= EntityUtils.toString(response.getEntity(),"utf-8");
                // json字符串key值为数字的替换为str方便解析
                result.replace("796", "redundancy");
                List<Fquotes> list = new ArrayList<Fquotes>();

                Fquotes fquotes = null;

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
                String dateString = sdf.format(new java.util.Date());
                Timestamp tm = Timestamp.valueOf(dateString);

                Gson gson = new Gson();
                BtcAndLtc btcAndLtc = gson.fromJson(result, BtcAndLtc.class);
                Btc btc = btcAndLtc.getBtc();
                Map<String, OtherPlatform> btcMap = btc.getAll();
                for(String str : btcMap.keySet()){
                    fquotes = new Fquotes();
                    OtherPlatform otherPlatform = btcMap.get(str);
                    double last = otherPlatform.getLast().getLast();
                    double last1 = otherPlatform.getNow().getLast();
                    if(last > last1){
                        // 下降
                        double v = (last - last1) / last1;
                        fquotes.setFupordown("1");
                        fquotes.setFrate(Utils.decimalFormat(v, 2));
                    }else{
                        // 上升
                        double v = (last1 - last) / last1;
                        fquotes.setFupordown("2");
                        fquotes.setFrate(Utils.decimalFormat(v, 2));
                    }
                    if(otherPlatform.getType().equals("us")){
                        // 最新价格人民币
                        double last2 = otherPlatform.getNow().getLast() * otherPlatform.getUsdcny();
                        // 最新价格美元
                        double last3 = otherPlatform.getNow().getLast();
                        fquotes.setFrmb(Utils.decimalFormat(last2, 2));
                        fquotes.setFdollar(Utils.decimalFormat(last3, 2));
                    }else{
                        // 最新价格人民币
                        double last2 = otherPlatform.getNow().getLast();
                        // 最新价格美元
                        double last3 = otherPlatform.getNow().getLast() * otherPlatform.getUsdcny();
                        fquotes.setFrmb(Utils.decimalFormat(last2, 2));
                        fquotes.setFdollar(Utils.decimalFormat(last3, 2));
                    }
                    // 哪个平台
                    fquotes.setFplatform(OtherPlatformEnum.getValueByKey(str));
                    Map coinType = (Map) map.get("coinType");
                    fquotes.setFvirtualcointypeid((Integer)coinType.get("btc"));
                    fquotes.setFcreatetime(tm);
                    list.add(fquotes);
                }

                Ltc ltc = btcAndLtc.getLtc();
                Map<String, OtherPlatform> ltcMap = ltc.getAll();
                for(String str : ltcMap.keySet()){
                    fquotes = new Fquotes();
                    OtherPlatform otherPlatform = ltcMap.get(str);
                    double last = otherPlatform.getLast().getLast();
                    double last1 = otherPlatform.getNow().getLast();
                    if(last > last1){
                        // 下降
                        double v = ((last - last1) / last1) * 100;
                        fquotes.setFupordown("1");
                        fquotes.setFrate(Utils.decimalFormat(v, 2));
                    }else{
                        // 上升
                        double v = ((last1 - last) / last1) * 100;
                        fquotes.setFupordown("2");
                        fquotes.setFrate(Utils.decimalFormat(v, 2));
                    }
                    if(otherPlatform.getType().equals("us")){
                        // 最新价格人民币
                        double last2 = otherPlatform.getNow().getLast() * otherPlatform.getUsdcny();
                        // 最新价格美元
                        double last3 = otherPlatform.getNow().getLast();
                        fquotes.setFrmb(Utils.decimalFormat(last2, 2));
                        fquotes.setFdollar(Utils.decimalFormat(last3, 2));
                    }else{
                        // 最新价格人民币
                        double last2 = otherPlatform.getNow().getLast();
                        // 最新价格美元
                        double last3 = otherPlatform.getNow().getLast() * otherPlatform.getUsdcny();
                        fquotes.setFrmb(Utils.decimalFormat(last2, 2));
                        fquotes.setFdollar(Utils.decimalFormat(last3, 2));
                    }
                    // 哪个平台
                    fquotes.setFplatform(OtherPlatformEnum.getValueByKey(str));

                    Map coinType = (Map) map.get("coinType");
                    fquotes.setFvirtualcointypeid((Integer)coinType.get("ltc"));
                    fquotes.setFcreatetime(tm);
                    list.add(fquotes);
                }
                fquotesService.commitList(list);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/

    /**
     * 通过btc123接口，获取其他平台行情数据
     */
    public void getData2() {
        String result = "";
        try {
            //从其它平台查询数据，返回json字符串
            result = HttpClientHelper.sendGetRequest(urlNameStringBtc123, Boolean.FALSE);
            List<Fquotes> fquotesList = new ArrayList<Fquotes>();
            Fquotes fquotes = null;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
            String dateString = sdf.format(new java.util.Date());
            Timestamp tm = Timestamp.valueOf(dateString);

            if(StringUtils.isBlank(result)) {
                LOG.i(this, "从btc123获取币种最新价格无返回");
                return;
            }

            //json转换成beanList
            OtherPlatformBtc123 otherPlatformBtc123 = JsonHelper.jsonStr2Obj(result, OtherPlatformBtc123.class);
            Datas datas = otherPlatformBtc123.getDatas();
            List<EthCoins> ethCoinsList = datas.getEthCoins();
            List<EthCoins> tradeAPIResult = datas.getTradeAPIResult();
            final Map<String, Integer> coinType = (Map) map.getMap().get("coinType");
            for (EthCoins data : ethCoinsList) {
                // 过滤日元币种
                if(data.getMoneyType() == 6){
                    continue;
                }
                if (coinType.containsKey(data.getCoinSign()) && !data.getName().equals(myPlatform)) {
                    fquotes = new Fquotes();
                    fquotes.setFplatform(data.getcName());
                    fquotes.setFvirtualcointypeid(coinType.get(data.getCoinSign()));
                    TickerBtc123 ticker = data.getTicker();
                    fquotes.setFrmb(ticker.getLast());
                    fquotes.setFdollar(ticker.getDollar());
                    fquotes.setFrate(ticker.getRiseRate());
                    fquotes.setFcreatetime(tm);
                    if(ticker.getRiseRate().contains("-")){
                        fquotes.setFupordown("2");
                    }else{
                        fquotes.setFupordown("1");
                        fquotes.setFrate("+" + ticker.getRiseRate());
                    }
                    fquotesList.add(fquotes);
                }
            }

            for (EthCoins data : tradeAPIResult) {
                // 过滤日元币种
                if(data.getMoneyType() == 6){
                    continue;
                }
                if (coinType.containsKey(data.getCoinSign()) && !data.getName().equals(myPlatform)) {
                    fquotes = new Fquotes();
                    fquotes.setFplatform(data.getcName());
                    fquotes.setFvirtualcointypeid(coinType.get(data.getCoinSign()));
                    TickerBtc123 ticker = data.getTicker();
                    fquotes.setFrmb(ticker.getLast());
                    fquotes.setFdollar(ticker.getDollar());
                    fquotes.setFrate(ticker.getRiseRate());
                    fquotes.setFcreatetime(tm);
                    if(ticker.getRiseRate().contains("-")){
                        fquotes.setFupordown("2");
                    }else{
                        fquotes.setFupordown("1");
                        fquotes.setFrate("+" + ticker.getRiseRate());
                    }
                    fquotesList.add(fquotes);
                }
            }

            if(fquotesList == null || fquotesList.size() == 0) {
                return;
            }

            //清除redis
            FquoteCacheManager.deleteCache();

            //保存
            GeneralListCache cache = new GeneralListCache();
            cache.setList(fquotesList);

            boolean flag = FquoteCacheManager.saveCache(cache);
            if(!flag) {
                LOG.w(OtherPlatformDataQuartz.class, "保存数据进缓存失败");
            }

//            fquotesService.commitList(fquotesList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
