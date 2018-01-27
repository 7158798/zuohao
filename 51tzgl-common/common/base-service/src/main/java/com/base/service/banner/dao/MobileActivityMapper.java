package com.base.service.banner.dao;

import com.base.facade.banner.pojo.po.Activity;
import com.base.facade.banner.pojo.vo.ActivityVo;
import com.jucaifu.core.dao.BaseMapper;


import java.util.List;

public interface MobileActivityMapper extends BaseMapper<Activity, ActivityVo> {

    /**
     * 通过显示位置，查询已启用活动列表
     *
     * @param showPosition
     * @return
     */
    List<Activity> queryActivityByPosition(int showPosition);

    /**
     * 分页查询所有已启动活动
     *
     * @param vo
     * @return
     */
    List<Activity> queryAllActivityByConditionPage(ActivityVo vo);
}
