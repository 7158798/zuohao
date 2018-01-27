package com.otc.service.advertisement.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.advertisement.pojo.po.AdvertisementTime;
import com.otc.facade.advertisement.pojo.vo.AdvertisementTimeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdvertisementTimeMapper extends BaseMapper<AdvertisementTime, AdvertisementTimeVo> {

    /**
     * 通过用户获取详情
     * @param userId
     * @return
     */
    List<AdvertisementTime> getByUserId(@Param("userId") Long userId);

}