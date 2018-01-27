package com.facade.core.wallet.cache;

import com.szzc.facade.out.pojo.TickerDateDto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zygong on 17-3-23.
 */

public class OtherPlatformTickerCache<T extends Serializable> implements Serializable {

    private Map<String, TickerDateDto> map;

    public Map<String, TickerDateDto> getMap() {
        return map;
    }

    public void setMap(Map<String, TickerDateDto> map) {
        this.map = map;
    }

    public static OtherPlatformTickerCache newInstance(){
        OtherPlatformTickerCache cache = new OtherPlatformTickerCache();
        cache.map = new HashMap<String, TickerDateDto>();
        return cache;
    }
}

