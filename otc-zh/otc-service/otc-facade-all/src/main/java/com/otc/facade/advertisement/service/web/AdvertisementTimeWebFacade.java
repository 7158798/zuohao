package com.otc.facade.advertisement.service.web;

import com.otc.facade.advertisement.pojo.po.AdvertisementTime;
import com.otc.facade.advertisement.service.AdvertisementTimeFacade;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 广告时间设置
 * Created by zygong on 17-4-25.
 */
public interface AdvertisementTimeWebFacade extends AdvertisementTimeFacade {

    /**
     * 保存or更新
     * @param advertisementTime
     * @return
     */
    boolean saveOrUpdate(AdvertisementTime advertisementTime);

    /**
     * 获取详情
     * @param userId
     * @return
     */
    List<AdvertisementTime> getByUserId(Long userId);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean deleteById(Long id);
}
