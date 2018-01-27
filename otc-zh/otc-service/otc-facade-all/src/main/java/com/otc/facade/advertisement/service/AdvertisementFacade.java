package com.otc.facade.advertisement.service;

import com.otc.facade.advertisement.pojo.po.Advertisement;
import com.otc.facade.advertisement.pojo.vo.AdvertisementVo;
import com.otc.facade.advertisement.pojo.vo.OtherPlatformPriceVo;

import java.util.List;

/**
 * Created by zygong on 17-4-25.
 */
public interface AdvertisementFacade {
    boolean updateStatus(Integer status, Long id);
    AdvertisementVo queryCountByConditionPage(AdvertisementVo vo);
    Advertisement getById(Long id);

    /**
     * 获取广告详情和最多成交量
     * @param id
     * @param token
     * @return
     */
    Advertisement getDetailAndMostDealCount(Long id, String token);

    /**
     * 获取其他平台接口
     * @param coinId
     * @return
     */
    List<OtherPlatformPriceVo> otherPlatformPrice(Long coinId);
}
