package com.otc.service.advertisement.impl.console;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.advertisement.pojo.po.Advertisement;
import com.otc.facade.advertisement.pojo.vo.AdvertisementVo;
import com.otc.facade.advertisement.service.console.AdvertisementConsoleFacade;
import com.otc.facade.advertisement.service.web.AdvertisementWebFacade;
import com.otc.facade.base.CountVo;
import com.otc.pool.OTCBizPool;
import com.otc.service.advertisement.impl.AdvertisementFacadeImpl;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by zygong on 17-4-25.
 */
@Component
@Service
public class AdvertisementConsoleFacadeImpl extends AdvertisementFacadeImpl implements AdvertisementConsoleFacade {
    @Override
    public List<CountVo> countAdvertisement() {
        return OTCBizPool.getInstance().advertisementBiz.countAdvertisement();
    }

    @Override
    public List<CountVo> countAdvertisement(Date dayTime) {
        return OTCBizPool.getInstance().advertisementBiz.countAdvertisement(dayTime);
    }
}

