package com.base.facade.banner.app;


import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.banner.MobileActivityFacade;
import com.base.facade.banner.pojo.po.Activity;
import com.base.facade.banner.pojo.vo.ActivityVo;

import java.util.List;

/**
 * Created by yangyy on 15-12-5.
 */
public interface MobileActivityAppFacade extends MobileActivityFacade {

    /**
     * 通过位置code查询活动列表
     *
     * @param position
     * @return
     * @throws BaseCommonBizException
     */
    List<Activity> queryActivitysByPosition(int position) throws BaseCommonBizException;

    /**
     * 分页查询所有活动列表
     *
     * @param vo
     * @return
     * @throws BaseCommonBizException
     */
    ActivityVo queryAllActivityByPage(ActivityVo vo) throws BaseCommonBizException;
}
