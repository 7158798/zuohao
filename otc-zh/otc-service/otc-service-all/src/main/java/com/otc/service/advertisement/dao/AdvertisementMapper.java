package com.otc.service.advertisement.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.advertisement.pojo.dto.AdvertisementDto;
import com.otc.facade.advertisement.pojo.po.Advertisement;
import com.otc.facade.advertisement.pojo.vo.AdvertisementVo;
import com.otc.facade.advertisement.pojo.vo.AdvertisementWebVo;
import com.otc.facade.base.CountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdvertisementMapper extends BaseMapper<Advertisement, AdvertisementVo> {
    List<Advertisement> queryCountByConditionPage(AdvertisementVo vo);

    int updateStatus(@Param("status")Integer status, @Param("id") Long id);

    Advertisement detail(@Param("userId") Long userId, @Param("id") Long id);

    List<AdvertisementDto> indexQueryCountByConditionPage(AdvertisementWebVo vo);

    Advertisement getLast();

    List<Advertisement> getList(AdvertisementVo vo);

    /**
     * 综合统计
     * @param dayTime
     * @return
     */
    List<CountVo> countAdvertisement(@Param("dayTime")String dayTime);

    /**
     * 获取用户其他交易广告
     * @param number
     * @return
     */
    List<Advertisement> getUserOtherList(@Param("id") Long id, @Param("userId") Long userId, @Param("number") Integer number);
}