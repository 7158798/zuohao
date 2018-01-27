package com.szzc.job;

import com.facade.core.wallet.cache.FquoteCacheManager;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.facade.fentrustLog.enums.GetTickerEnum;
import com.szzc.facade.fentrustLog.pojo.po.CoinShorNameAndFtrademappingId;
import com.szzc.facade.fquotes.pojo.dto.EthCoins;
import com.szzc.facade.fquotes.pojo.dto.OtherPlatformBtc123;
import com.szzc.facade.fquotes.pojo.dto.TickerBtc123;
import com.szzc.facade.fquotes.pojo.po.Fquotes;
import com.szzc.pool.ThreeBizPool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zygong on 17-5-27.
 */
@Component
public class GetOtherTickerDate {
    private static final String urlNameStringBtc123 = "https://www.btc123.com/api/getTicker";
    private final static String myPlatform = "_51szzc";

    /**
     * 通过btc123接口，获取其他平台行情数据
     */
    public void init() {
        LOG.i(this, "++++++++++++++获取第三方数据++++++++++++++");
        String result = "";
        try {
            //从其它平台查询数据，返回json字符串

            String symbolAll = GetTickerEnum.SYMBOL.getValue();
            String[] symbolArray = symbolAll.split(",");

            List<Fquotes> fquotesList = new ArrayList<Fquotes>();
            Fquotes fquotes = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
            String dateString = sdf.format(new java.util.Date());
            Timestamp tm = Timestamp.valueOf(dateString);

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
                List<CoinShorNameAndFtrademappingId> activeCoin = ThreeBizPool.getInstance().fentrustLogBiz.getActiveCoin();
                if (activeCoin == null || activeCoin.isEmpty()) {
                    return;
                }
                Map<String, Long> coinType = new HashMap<>();
                for (CoinShorNameAndFtrademappingId coinShorNameAndFtrademappingId : activeCoin) {
                    String[] split = coinShorNameAndFtrademappingId.getShortName().split("_");
                    if(split[1].toLowerCase().equals("cny")){
                        coinType.put(split[0].toLowerCase(), coinShorNameAndFtrademappingId.getId());
                    }
                }
//                Map<String, Integer> coinType = (Map) map.getMap().get("coinType");
                // 过滤日元币种
                if (datas.getMoneyType() == 6) {
                    continue;
                }
                if (coinType.containsKey(datas.getCoinSign()) && !datas.getName().equals(myPlatform)) {
                    fquotes = new Fquotes();
                    fquotes.setFplatform(datas.getCName());
                    fquotes.setFvirtualcointypeid(coinType.get(datas.getCoinSign()).intValue());
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
                }
            }

            if (fquotesList == null || fquotesList.size() == 0) {
                return;
            }

            LOG.i(this, "++++++++++++++获取完成存入缓存++++++++++++++");
            //清除redis
            FquoteCacheManager.deleteCache();

            boolean flag = FquoteCacheManager.saveCache(JsonHelper.obj2JsonStr(fquotesList));
            if (!flag) {
                LOG.w(GetOtherTickerDate.class, "保存数据进缓存失败");
            }

            ThreeBizPool.getInstance().fquotesBiz.saveList(fquotesList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
