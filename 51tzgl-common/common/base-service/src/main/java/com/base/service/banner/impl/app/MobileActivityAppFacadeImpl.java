package com.base.service.banner.impl.app;


import com.base.facade.banner.app.MobileActivityAppFacade;
import com.base.facade.banner.pojo.po.Activity;
import com.base.facade.banner.pojo.vo.ActivityVo;
import com.base.facade.exception.BaseCommonBizException;
import com.base.service.banner.impl.MobileActivityFacadeImpl;
import com.base.service.pool.BaseServiceBizPool;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangyy on 15-12-5.
 */
@Service("mobileActivityAppFacade")
public class MobileActivityAppFacadeImpl extends MobileActivityFacadeImpl implements MobileActivityAppFacade {

    @Override
    public List<Activity> queryActivitysByPosition(int position) throws BaseCommonBizException {
        return BaseServiceBizPool.getInstance().mobileActivityBiz.queryActivityByPosition(position);
    }

    @Override
    public ActivityVo queryAllActivityByPage(ActivityVo vo) throws BaseCommonBizException {
        List<Activity> activities = BaseServiceBizPool.getInstance().mobileActivityBiz.queryAllActivityByPage(vo);
        vo.setResultList(activities);
        return vo;
    }
}
