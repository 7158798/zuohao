package com.otc.service.advertisement.impl.web;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.advertisement.pojo.po.AdvertisementTime;
import com.otc.facade.advertisement.service.web.AdvertisementTimeWebFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.advertisement.impl.AdvertisementTimeFacadeImpl;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zygong on 17-4-25.
 */
@Component
@Service
public class AdvertisementTimeWebFacadeImpl extends AdvertisementTimeFacadeImpl implements AdvertisementTimeWebFacade {

    @Override
    public boolean saveOrUpdate(AdvertisementTime advertisementTime) {
        return OTCBizPool.getInstance().advertisementTimeBiz.saveOrUpdate(advertisementTime);
    }

    @Override
    public List<AdvertisementTime> getByUserId(Long userId) {
        return OTCBizPool.getInstance().advertisementTimeBiz.getByUserId(userId);
    }

    @Override
    public boolean deleteById(Long id) {
        return OTCBizPool.getInstance().advertisementTimeBiz.deleteById(id);
    }
}



