package com.otc.facade.advertisement.service.console;

import com.otc.facade.advertisement.pojo.po.Advertisement;
import com.otc.facade.advertisement.pojo.vo.AdvertisementVo;
import com.otc.facade.advertisement.service.AdvertisementFacade;
import com.otc.facade.base.CountVo;

import java.util.Date;
import java.util.List;

/**
 * Created by zygong on 17-4-27.
 */
public interface AdvertisementConsoleFacade extends AdvertisementFacade {

    /**
     * 综合统计-全部
     * @return
     */
    List<CountVo> countAdvertisement();

    /**
     * 综合统计-一天
     * @param dayTime
     * @return
     */
    List<CountVo> countAdvertisement(Date dayTime);
}
