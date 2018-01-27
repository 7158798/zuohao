package com.base.facade.banner;


import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.banner.pojo.po.Activity;

/**
 * Created by yangyy on 15-11-26.
 */
public interface MobileActivityFacade {

    /**
     * 根据id查询活动详情
     *
     * @param uuid
     * @return
     * @throws BaseCommonBizException
     */
    Activity getActivityById(String uuid) throws BaseCommonBizException;

}
