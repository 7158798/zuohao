package com.szzc.fentrustLog.biz;

import com.facade.core.wallet.cache.CacheHelper;
import com.facade.core.wallet.cache.key.CacheKey;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.facade.fentrustLog.pojo.po.CoinShorNameAndFtrademappingId;
import com.szzc.facade.virtualCoin.pojo.po.VirtualCoinType;
import com.szzc.fentrustLog.dao.VirtualCoinTypeMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zygong on 17-7-19.
 */
@Service
public class VirtualCoinTypeBiz {
    @Autowired
    private VirtualCoinTypeMapper virtualCoinTypeMapper;

    /**
     * 获取可用币种
     * @return
     */
    public List<VirtualCoinType> getActiveCoin(){
        List<VirtualCoinType> activeCoinDetail = virtualCoinTypeMapper.getActiveCoinDetail();
        return activeCoinDetail;
    }

    /**
     * 获取可用虚拟币
     *
     * @return
     */
    public Map<String, Long> getActionCoinShortNameAndFtId() {
        String str = CacheHelper.getObj(CacheKey.COIN_SHORTNAME_FBID);
        Map<String, Long> map = new HashMap<>();
        if(StringUtils.isNotBlank(str)){
            map = JsonHelper.jsonStr2MapObj(str, Long.class);
            return map;
        }
        List<CoinShorNameAndFtrademappingId> activeCoin = virtualCoinTypeMapper.getActiveCoin();
        if(activeCoin != null || !activeCoin.isEmpty()){
            for(CoinShorNameAndFtrademappingId coinShorNameAndFtrademappingId : activeCoin){
                map.put(coinShorNameAndFtrademappingId.getShortName().toLowerCase(), coinShorNameAndFtrademappingId.getId());
            }
            CacheHelper.saveObj(CacheKey.COIN_SHORTNAME_FBID, JsonHelper.obj2JsonStr(map), 5*60);
        }
        return map;
    }
}
