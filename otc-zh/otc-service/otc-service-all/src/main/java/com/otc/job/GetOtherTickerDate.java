package com.otc.job;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.util.JsonHelper;
import com.otc.core.cache.FquoteCacheManager;
import com.otc.facade.advertisement.pojo.poex.EthCoins;
import com.otc.facade.advertisement.pojo.poex.OtherPlatformBtc123;
import com.otc.facade.advertisement.pojo.poex.TickerBtc123;
import com.otc.facade.quotes.pojo.enums.GetTickerEnum;
import com.otc.facade.quotes.pojo.po.Quotes;
import com.otc.facade.virtual.pojo.po.VirtualCoin;
import com.otc.pool.OTCBizPool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zygong on 17-5-27.
 */
@Component
public class GetOtherTickerDate {
    private static final String urlNameStringBtc123 = "https://www.btc123.com/api/getTicker";

    /**
     * 通过btc123接口，获取其他平台行情数据
     */
    @Scheduled(cron = "0 0/2 * * * ?")
    public void init() {
        String result = "";
        Map<Long, Map<String, String>> coinCacheMap = new HashMap<Long, Map<String, String>>();
        Map<String, Long> idMap = new HashMap<String, Long>();
        try {
            //从其它平台查询数据，返回json字符串

            String symbolAll = GetTickerEnum.SYMBOL.getValue();
            String[] symbolArray = symbolAll.split(",");

            List<Quotes> fquotesList = new ArrayList<Quotes>();
            Quotes fquotes = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
            String dateString = sdf.format(new java.util.Date());
            Timestamp tm = Timestamp.valueOf(dateString);

            Map<Long, VirtualCoin> virtualCoin = OTCBizPool.getInstance().virtualCoinBiz.getVirtualCoin(false);
            if (virtualCoin == null || virtualCoin.isEmpty()) {
                return;
            }
            Map<String, Long> coinType = new HashMap<>();
            for (Map.Entry<Long, VirtualCoin> map : virtualCoin.entrySet()) {
                coinCacheMap.put(map.getKey(), new HashMap<String, String>());
                coinType.put(map.getValue().getShortName().toLowerCase(), map.getKey());
            }

            for (String symbol : symbolArray) {
                result = HttpClientHelper.sendGetRequest(urlNameStringBtc123 + "?symbol=" + symbol, Boolean.FALSE);
                if (StringUtils.isBlank(result)) {
                    continue;
                }
                OtherPlatformBtc123 otherPlatformBtc123 = JsonHelper.jsonStr2Obj(result, OtherPlatformBtc123.class);
                if (otherPlatformBtc123 == null && !otherPlatformBtc123.isSuc()) {
                    continue;
                }

                EthCoins datas = otherPlatformBtc123.getDatas();

//                Map<String, Integer> coinType = (Map) map.getMap().get("coinType");
                // 过滤日元币种
                if (datas.getMoneyType() == 6) {
                    continue;
                }

                filterData(datas, coinType, coinCacheMap, fquotes, fquotesList, tm);
            }

            if (fquotesList == null || fquotesList.size() == 0) {
                return;
            }

            //清除redis
            FquoteCacheManager.deleteCache();

            boolean flag = FquoteCacheManager.saveCache(JsonHelper.obj2JsonStr(coinCacheMap));
            if (!flag) {
                LOG.w(GetOtherTickerDate.class, "保存数据进缓存失败");
            }

            //保存到数据库
            OTCBizPool.getInstance().quotesBiz.saveList(fquotesList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterData(EthCoins datas, Map<String, Long> coinType, Map<Long, Map<String, String>> coinCacheMap, Quotes fquotes, List<Quotes> fquotesList, Timestamp tm) {
        if (coinType.containsKey(datas.getCoinSign())) {
            fquotes = new Quotes();
            fquotes.setFplatform(datas.getCName());
            fquotes.setFvirtualcointypeid(coinType.get(datas.getCoinSign()));
            TickerBtc123 ticker = datas.getTicker();
            fquotes.setFrmb(ticker.getLast());
            fquotes.setFdollar(ticker.getDollar());
            fquotes.setFrate(ticker.getRiseRate());
            fquotes.setFcreatetime(tm);
            if (ticker.getRiseRate().contains("-")) {
                fquotes.setFupordown("2");
            } else {
                fquotes.setFupordown("1");
                fquotes.setFrate("+" + ticker.getRiseRate());
            }
            fquotesList.add(fquotes);
            cacheMap(coinType, coinCacheMap, datas);

        }
    }

    private void cacheMap(Map<String, Long> coinType, Map<Long, Map<String, String>> coinCacheMap, EthCoins data) {
        Long id = coinType.get(data.getCoinSign());
        Map<String, String> map = coinCacheMap.get(id);
        map.put(data.getCName(), data.getTicker().getLast());

        coinCacheMap.put(id, map);
    }
}
