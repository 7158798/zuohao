package com.szzc.job;

import com.facade.core.wallet.cache.CacheHelper;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.facade.fentrustLog.pojo.dto.TradeVo;
import com.szzc.facade.fentrustLog.pojo.dto.TradeListDto;
import com.szzc.facade.fentrustLog.pojo.po.CoinShorNameAndFtrademappingId;
import com.szzc.pool.ThreeBizPool;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zygong on 17-5-22.
 */
public class TradeOtherTask {

    public void work() {
        LOG.i(this,"交易历史记录接口获取数据开始");

        List<CoinShorNameAndFtrademappingId> activeCoin = ThreeBizPool.getInstance().fentrustLogBiz.getActiveCoin();
        if (activeCoin == null || activeCoin.isEmpty()) {
            return;
        }
        HashMap<String, TradeListDto> map = new HashMap<>();
        TradeListDto tradeDto = null;
        for (CoinShorNameAndFtrademappingId coin : activeCoin) {
            List<TradeVo> tradeDtos = ThreeBizPool.getInstance().fentrustLogBiz.tradeOther(coin.getId(), 600l);
            tradeDto = new TradeListDto();
            tradeDto.setList(tradeDtos);
            StringBuffer buffer = new StringBuffer();
            buffer.append(coin.getShortName());
            buffer.append("TRADE_OTHERPLATFORM_CACHE_KEY");
            String key = buffer.toString();
            LOG.i(this,key);
            CacheHelper.deleteObj(key);
            boolean flag = CacheHelper.saveObj(key, JsonHelper.obj2JsonStr(tradeDto));
            if (!flag) {
                LOG.e(TradeOtherTask.class, "保存数据进缓存失败");
            }
        }

    }
}
