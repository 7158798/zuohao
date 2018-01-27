package com.otc.service.advertisement.impl;

import com.otc.facade.advertisement.pojo.po.Advertisement;
import com.otc.facade.advertisement.pojo.vo.AdvertisementVo;
import com.otc.facade.advertisement.pojo.vo.OtherPlatformPriceVo;
import com.otc.facade.advertisement.service.AdvertisementFacade;
import com.otc.pool.OTCBizPool;

import java.util.List;

/**
 * Created by zygong on 17-4-25.
 */

public class AdvertisementFacadeImpl implements AdvertisementFacade {

    @Override
    public boolean updateStatus(Integer status, Long id) {
        return OTCBizPool.getInstance().advertisementBiz.updateStatus(status, id);
    }

    @Override
    public AdvertisementVo queryCountByConditionPage(AdvertisementVo vo) {
        return OTCBizPool.getInstance().advertisementBiz.queryCountByConditionPage(vo);
    }

    @Override
    public Advertisement getById(Long id) {
        return OTCBizPool.getInstance().advertisementBiz.selectById(id);
    }

    @Override
    public Advertisement getDetailAndMostDealCount(Long id, String token) {
        return OTCBizPool.getInstance().advertisementBiz.getDetailAndMostDealCount(id, token);
    }

    @Override
    public List<OtherPlatformPriceVo> otherPlatformPrice(Long coinId) {
        return OTCBizPool.getInstance().advertisementBiz.otherPlatformPrice(coinId);
    }
}