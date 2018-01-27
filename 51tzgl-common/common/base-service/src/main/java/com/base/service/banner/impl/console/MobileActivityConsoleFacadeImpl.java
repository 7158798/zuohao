package com.base.service.banner.impl.console;

import com.base.facade.banner.console.MobileActivityConsoleFacade;
import com.base.facade.banner.enums.ActivityType;
import com.base.facade.banner.pojo.po.Activity;
import com.base.facade.banner.pojo.vo.ActivityConsoleApiVo;
import com.base.facade.banner.pojo.vo.ActivityVo;
import com.base.facade.exception.BaseCommonBizException;
import com.base.service.banner.impl.MobileActivityFacadeImpl;
import com.base.service.pool.BaseServiceBizPool;
import com.jucaifu.common.log.LOG;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by yangyy on 15-11-26.
 */
@Service("mobileActivityConsoleFacade")
public class MobileActivityConsoleFacadeImpl extends MobileActivityFacadeImpl implements MobileActivityConsoleFacade {

    @Override
    public Activity sortActivity(String uuid, int sortNumber) throws BaseCommonBizException {
        LOG.dStart(this, "修改活动图片排序开始");
        LOG.d(this, "校验参数是否正确");
        if (StringUtils.isBlank(uuid)) {
            throw new BaseCommonBizException(BaseCommonBizException.PARAM_IS_NOT_BLANK, "活动id不能为空");
        }
        Activity activity = BaseServiceBizPool.getInstance().mobileActivityBiz.select(uuid);
        if (activity == null) {
            throw new BaseCommonBizException(BaseCommonBizException.DATA_NOT_EXIST, "该活动不存在");
        }
        activity.setRank(sortNumber);
        activity.setModifiedDate(new Date());
        BaseServiceBizPool.getInstance().mobileActivityBiz.update(activity);
        LOG.dEnd(this, "修改活动图片排序完成");

        return activity;
    }

    @Override
    public Activity addActivity(ActivityConsoleApiVo vo) throws BaseCommonBizException {
        LOG.dStart(this, "添加活动开始-Start");
        LOG.d(this, "校验参数是否正确");
        if (vo == null) {
            throw new BaseCommonBizException(BaseCommonBizException.REQUEST_PARAM_INCORRENT, "请求参数不正确");
        }
        Integer rank = vo.getSortNumber();
        String imgUrl = vo.getImgUrl();
        String linkType = vo.getType();
        String productId = vo.getProductId();
        String detailUrl = vo.getDetailUrl();
        Date startDate = vo.getStartDate();
        Date endDate = vo.getEndDate();

        validateParam(vo);
        if(startDate == null){
            throw new BaseCommonBizException(BaseCommonBizException.PARAM_IS_NOT_BLANK, "开始日期不能为空");
        }
        if(endDate == null){
            throw new BaseCommonBizException(BaseCommonBizException.PARAM_IS_NOT_BLANK, "结束日期不能为空");
        }
        if (StringUtils.isBlank(imgUrl)) {
            throw new BaseCommonBizException(BaseCommonBizException.PARAM_IS_NOT_BLANK, "图片地址不能为空");
        }
        if (rank == null) {
            throw new BaseCommonBizException(BaseCommonBizException.PARAM_IS_NOT_BLANK, "排序字段不能为空");
        }
        Activity activity = new Activity();
        activity.setTitle(vo.getTitle());
        activity.setSummary(vo.getSummary());
        activity.setModifiedDate(new Date());
        activity.setProductId(productId);
        activity.setCreateDate(new Date());
        activity.setRank(rank);
        activity.setDetailUrl(detailUrl);
        activity.setImgUrl(imgUrl);
        activity.setContent(vo.getContent());
        activity.setShowPosition(vo.getShowPosition());
        activity.setActive(vo.isActive());
        activity.setLoginRequired(vo.isLoginRequired());
        activity.setLinkType(linkType);
        activity.setCreateUserId(vo.getCreateUserId());
        activity.setModifiedUserId(vo.getModifiedUserId());
        activity.setStartDate(vo.getStartDate());
        activity.setEndDate(vo.getEndDate());
        activity.setBannerContent(vo.getBannerContent());
        BaseServiceBizPool.getInstance().mobileActivityBiz.insert(activity);

        LOG.d(this, activity);
        LOG.dEnd(this, "添加活动结束-end");

        return activity;
    }

    private void validateParam(ActivityConsoleApiVo activityConsoleApiVo) throws BaseCommonBizException {
        if (ActivityType.PRODUCT.getCode().equals(activityConsoleApiVo.getType())) {
            if (StringUtils.isBlank(activityConsoleApiVo.getProductId())) {
                throw new BaseCommonBizException(BaseCommonBizException.PARAM_IS_NOT_BLANK, "图片类型为产品，产品id不能为空");
            }
        } else if (ActivityType.LINK.getCode().equals(activityConsoleApiVo.getType())) {
            if (StringUtils.isBlank(activityConsoleApiVo.getDetailUrl())) {
                throw new BaseCommonBizException(BaseCommonBizException.PARAM_IS_NOT_BLANK, "图片类型为链接，链接地址不能为空");
            }
        } else if (ActivityType.DUIBA.getCode().equals(activityConsoleApiVo.getType())){
            if (StringUtils.isBlank(activityConsoleApiVo.getDetailUrl())) {
                throw new BaseCommonBizException("图片类型为兑吧，链接地址不能为空");
            }
        } else {
            throw new BaseCommonBizException(BaseCommonBizException.PARAM_FORMAT_INCORRECT, "图片类型参数不正确");
        }
    }

    @Override
    public Activity editActivity(ActivityConsoleApiVo vo) throws BaseCommonBizException {
        LOG.dStart(this, "编辑活动开始-Start");
        LOG.d(this, "校验参数是否正确");
        if (vo == null) {
            throw new BaseCommonBizException(BaseCommonBizException.REQUEST_PARAM_INCORRENT, "请求参数不正确");
        }
        String id = vo.getUuid();
        if (id == null) {
            throw new BaseCommonBizException(BaseCommonBizException.PARAM_IS_NOT_BLANK, "活动id不能为空");
        }
        Activity activity = BaseServiceBizPool.getInstance().mobileActivityBiz.select(id);
        if (activity == null) {
            throw new BaseCommonBizException(BaseCommonBizException.DATA_NOT_EXIST, "活动不存在");
        }
        Integer rank = vo.getSortNumber();
        String imgUrl = vo.getImgUrl();
        String linkType = vo.getType();
        String productId = vo.getProductId();
        String detailUrl = vo.getDetailUrl();
        validateParam(vo);
        if (StringUtils.isBlank(imgUrl)) {
            throw new BaseCommonBizException(BaseCommonBizException.PARAM_IS_NOT_BLANK, "图片地址不能为空");
        }
        if (rank == null) {
            throw new BaseCommonBizException(BaseCommonBizException.PARAM_IS_NOT_BLANK, "排序字段不能为空");
        }
        activity.setTitle(vo.getTitle());
        activity.setSummary(vo.getSummary());
        activity.setModifiedDate(new Date());
        activity.setProductId(productId);
        activity.setRank(rank);
        activity.setDetailUrl(detailUrl);
        activity.setImgUrl(imgUrl);
        activity.setContent(vo.getContent());
        activity.setShowPosition(vo.getShowPosition());
        activity.setActive(vo.isActive());
        activity.setLoginRequired(vo.isLoginRequired());
        activity.setLinkType(linkType);
        activity.setModifiedUserId(vo.getModifiedUserId());
        activity.setBannerContent(vo.getBannerContent());
        activity.setStartDate(vo.getStartDate());
        activity.setEndDate(vo.getEndDate());
        BaseServiceBizPool.getInstance().mobileActivityBiz.update(activity);

        LOG.d(this, activity);
        LOG.dEnd(this, "编辑活动结束-end");

        return activity;
    }

    @Override
    public void deleteActivity(String uuid) throws BaseCommonBizException {
        LOG.dStart(this, "删除活动开始-Start");
        if (StringUtils.isBlank(uuid)) {
            throw new BaseCommonBizException(BaseCommonBizException.PARAM_IS_NOT_BLANK, "活动id不能为空");
        }
        Activity activity = BaseServiceBizPool.getInstance().mobileActivityBiz.select(uuid);
        if (activity == null) {
            throw new BaseCommonBizException(BaseCommonBizException.DATA_NOT_EXIST, "活动不存在");
        }
        BaseServiceBizPool.getInstance().mobileActivityBiz.delete(uuid);
        LOG.dEnd(this, "删除活动结束-End");
    }

    @Override
    public Activity swicthActivity(String uuid, boolean active) throws BaseCommonBizException {
        LOG.dStart(this, "启用/停用活动-Start");
        if (StringUtils.isBlank(uuid)) {
            throw new BaseCommonBizException(BaseCommonBizException.PARAM_IS_NOT_BLANK, "活动id不能为空");
        }
        Activity activity = BaseServiceBizPool.getInstance().mobileActivityBiz.select(uuid);
        if (activity == null) {
            throw new BaseCommonBizException(BaseCommonBizException.DATA_NOT_EXIST, "活动不存在");
        }
        activity.setActive(active);
        activity.setModifiedDate(new Date());
        BaseServiceBizPool.getInstance().mobileActivityBiz.update(activity);

        LOG.d(this, activity);
        LOG.dEnd(this, "启用/停用活动结束-end");
        return activity;
    }

    @Override
    public ActivityVo getActivityList(ActivityVo vo) throws BaseCommonBizException {
        LOG.dStart(this, "查询活动列表-Start");
        List<Activity> activities = BaseServiceBizPool.getInstance().mobileActivityBiz.getByConditionPage(vo);
        vo.setResultList(activities);
        LOG.dEnd(this, "查询活动列表-End");
        return vo;
    }
}
