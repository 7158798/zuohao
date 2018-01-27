package com.base.facade.banner.console;


import com.base.facade.banner.MobileActivityFacade;
import com.base.facade.banner.pojo.po.Activity;
import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.banner.pojo.vo.ActivityConsoleApiVo;
import com.base.facade.banner.pojo.vo.ActivityVo;

/**
 * Created by yangyy on 15-11-26.
 */
public interface MobileActivityConsoleFacade extends MobileActivityFacade {

    /**
     * 排序活动图片
     *
     * @param uuid
     * @param sortNumber
     * @return
     * @throws BaseCommonBizException
     */
    Activity sortActivity(String uuid, int sortNumber) throws BaseCommonBizException;

    /**
     * 添加活动
     *
     * @param vo
     * @return
     * @throws BaseCommonBizException
     */
    Activity addActivity(ActivityConsoleApiVo vo) throws BaseCommonBizException;

    /**
     * 编辑活动
     *
     * @param vo
     * @return
     * @throws BaseCommonBizException
     */
    Activity editActivity(ActivityConsoleApiVo vo) throws BaseCommonBizException;

    /**
     * 删除活动
     *
     * @param uuid
     * @throws BaseCommonBizException
     */
    void deleteActivity(String uuid) throws BaseCommonBizException;

    /**
     * 启用/停用活动
     *
     * @param uuid
     * @param active
     * @return
     * @throws BaseCommonBizException
     */
    Activity swicthActivity(String uuid, boolean active) throws BaseCommonBizException;

    /**
     * 查询活动列表
     *
     * @param vo
     * @return
     * @throws BaseCommonBizException
     */
    ActivityVo getActivityList(ActivityVo vo) throws BaseCommonBizException;

}
