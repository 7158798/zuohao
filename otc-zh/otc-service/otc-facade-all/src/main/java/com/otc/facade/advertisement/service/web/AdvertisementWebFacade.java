package com.otc.facade.advertisement.service.web;

import com.otc.facade.advertisement.pojo.dto.CalculatePricedto;
import com.otc.facade.advertisement.pojo.po.Advertisement;
import com.otc.facade.advertisement.pojo.vo.AdvertisementVo;
import com.otc.facade.advertisement.pojo.vo.AdvertisementWebVo;
import com.otc.facade.advertisement.pojo.vo.CalculatePriceVo;
import com.otc.facade.advertisement.pojo.vo.OtherPlatformPriceVo;
import com.otc.facade.advertisement.service.AdvertisementFacade;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zygong on 17-4-25.
 */
public interface AdvertisementWebFacade extends AdvertisementFacade {

    boolean saveAdvertisement(Advertisement advertisement);
    Advertisement detail(Long userId, Long id);
    AdvertisementWebVo indexQueryCountByConditionPage(AdvertisementWebVo vo);
    Advertisement getLast();
    List<Advertisement> getList(AdvertisementVo vo);
    BigDecimal calculatePrice(Long advertisementId);
    CalculatePricedto calculatePriceDto(CalculatePriceVo vo);

    /**
     * 获取用户其他交易广告
     * @param number
     * @return
     */
    List<Advertisement> getUserOtherList(Long id, Long userId, Integer number);

    /**
     * 人民币和币种价格转换
     * @param type      rmb  coin
     * @param number    金额or数量
     * @param id        广告id
     * @return
     */
    String transform(String type, BigDecimal number, Long id);

    /**
     * 判断用户币的数量
     * @param userId
     * @param coinId
     * @return
     */
    BigDecimal userCoinNumber(Long userId, Long coinId);

    /**
     * 获取最新价格
     * @param adId
     * @return
     */
    String newPrice(Long adId);
}
