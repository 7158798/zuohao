package com.base.service.banner.biz;

import com.base.facade.banner.pojo.po.Activity;
import com.base.facade.banner.pojo.vo.ActivityVo;
import com.base.facade.exception.BaseCommonBizException;
import com.base.service.banner.dao.MobileActivityMapper;
import com.jucaifu.core.biz.AbsBaseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yangyy on 15-11-26.
 */
@Service
@Transactional
public class MobileActivityBiz extends AbsBaseBiz<Activity, ActivityVo, MobileActivityMapper> {

    @Autowired
    private MobileActivityMapper mobileActivityMapper;

    /**
     * 通过显示位置，查询活动列表
     *
     * @param showPosition
     * @return
     */
    public List<Activity> queryActivityByPosition(int showPosition) throws BaseCommonBizException {
        return mobileActivityMapper.queryActivityByPosition(showPosition);
    }


    /**
     * 分页查询所有已启动活动
     *
     * @param vo
     * @return
     */
    public List<Activity> queryAllActivityByPage(ActivityVo vo) throws BaseCommonBizException {
        return mobileActivityMapper.queryAllActivityByConditionPage(vo);
    }

    @Override
    public MobileActivityMapper getDefaultMapper() {
        return mobileActivityMapper;
    }
}
