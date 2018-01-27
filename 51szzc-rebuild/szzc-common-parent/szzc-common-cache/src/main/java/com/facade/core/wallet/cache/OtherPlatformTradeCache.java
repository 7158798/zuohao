package com.facade.core.wallet.cache;

import com.szzc.facade.fentrustLog.pojo.dto.TradeListDto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zygong on 17-3-23.
 */

public class OtherPlatformTradeCache<T extends Serializable> implements Serializable {

    private Map<String, TradeListDto> map;

    public Map<String, TradeListDto> getMap() {
        return map;
    }

    public void setMap(Map<String, TradeListDto> map) {
        this.map = map;
    }

    public static OtherPlatformTradeCache newInstance(){
        OtherPlatformTradeCache cache = new OtherPlatformTradeCache();
        cache.map = new HashMap<String, TradeListDto>();
        return cache;
    }
}

