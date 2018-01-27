package com.otc.service.advertisement.impl.web;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.advertisement.pojo.dto.CalculatePricedto;
import com.otc.facade.advertisement.pojo.po.Advertisement;
import com.otc.facade.advertisement.pojo.vo.AdvertisementVo;
import com.otc.facade.advertisement.pojo.vo.AdvertisementWebVo;
import com.otc.facade.advertisement.pojo.vo.CalculatePriceVo;
import com.otc.facade.advertisement.pojo.vo.OtherPlatformPriceVo;
import com.otc.facade.advertisement.service.web.AdvertisementWebFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.advertisement.impl.AdvertisementFacadeImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zygong on 17-4-25.
 */
@Component
@Service
public class AdvertisementWebFacadeImpl extends AdvertisementFacadeImpl implements AdvertisementWebFacade {
    @Override
    public boolean saveAdvertisement(Advertisement advertisement) {
        return OTCBizPool.getInstance().advertisementBiz.saveAdvertisement(advertisement);
    }

    @Override
    public Advertisement detail(Long userId, Long id) {
        return OTCBizPool.getInstance().advertisementBiz.detail(userId, id);
    }

    @Override
    public AdvertisementWebVo indexQueryCountByConditionPage(AdvertisementWebVo vo) {
        return OTCBizPool.getInstance().advertisementBiz.indexQueryCountByConditionPage(vo);
    }

    @Override
    public Advertisement getLast() {
        return OTCBizPool.getInstance().advertisementBiz.getLast();
    }

    @Override
    public List<Advertisement> getList(AdvertisementVo vo) {
        return OTCBizPool.getInstance().advertisementBiz.getList(vo);
    }

    @Override
    public BigDecimal calculatePrice(Long advertisementId) {
        CalculatePriceVo vo = new CalculatePriceVo();
        vo.setAdvertisementId(advertisementId);
        CalculatePricedto calculatePricedto = OTCBizPool.getInstance().advertisementBiz.calculatePrice(vo);
        if(StringUtils.isNotBlank(calculatePricedto.getPrice())){
            return new BigDecimal(calculatePricedto.getPrice());
        }else{
            return null;
        }
    }

    @Override
    public CalculatePricedto calculatePriceDto(CalculatePriceVo vo) {
        return OTCBizPool.getInstance().advertisementBiz.calculatePrice(vo);
    }

    @Override
    public List<Advertisement> getUserOtherList(Long id, Long userId, Integer number) {
        return OTCBizPool.getInstance().advertisementBiz.getUserOtherList(id, userId, number);
    }

    @Override
    public String transform(String type, BigDecimal number, Long id) {
        return OTCBizPool.getInstance().advertisementBiz.transform(type, number, id);
    }

    @Override
    public BigDecimal userCoinNumber(Long userId, Long coinId) {
        return OTCBizPool.getInstance().advertisementBiz.userCoinNumber(userId, coinId);
    }

    @Override
    public String newPrice(Long adId) {
        return OTCBizPool.getInstance().advertisementBiz.newPrice(adId);
    }
}



