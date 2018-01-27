package com.base.service.integral.impl;

import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.integral.enums.IntegralType;
import com.base.facade.integral.pojo.po.UserIntegralAccount;
import com.base.facade.integral.pojo.po.UserIntegralDetail;
import com.base.facade.integral.pojo.po.UserIntegralOrders;
import com.base.facade.integral.pojo.vo.UserIntegralDetailVo;
import com.base.facade.integral.service.UserIntegralFacade;
import com.base.service.pool.BaseServiceBizPool;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zh on 16-10-23.
 */
public class UserIntegralFacadeImpl implements UserIntegralFacade{
    @Override
    public UserIntegralAccount getUserIntegralAccountById(Long userId) {
        return BaseServiceBizPool.getInstance().userIntegralAccountBiz.getByUserId(userId);
    }

    @Override
    public List<UserIntegralDetail> getUserIntegerDetailByGroup(Long userId) {
        return BaseServiceBizPool.getInstance().userIntegralDetailBiz.getUserIntegerDetailByGroup(userId);
    }

    @Override
    public UserIntegralDetailVo getUserIntegralListByConditionPage(UserIntegralDetailVo vo) {
        List<UserIntegralDetail> list = BaseServiceBizPool.getInstance()
                .userIntegralDetailBiz.getByConditionPage(vo);
        vo.setResultList(list);
        return vo;
    }

    @Override
    public UserIntegralAccount addByTypeAndRelationId(Long userId, IntegralType integralType, Long relationId) {
        return BaseServiceBizPool.getInstance().userIntegralAccountBiz.addByTypeAndRelationId(userId, integralType, relationId);
    }



    @Override
    public UserIntegralOrders getIntegralOrdersById(Long id) {
        return BaseServiceBizPool.getInstance().userIntegralOrdersBiz.select(id);
    }

    @Override
    public UserIntegralAccount addUserIntegral(Long userId, IntegralType integralType, BigDecimal intgeral, Long relationId) {
        return BaseServiceBizPool.getInstance().userIntegralAccountBiz.addUserIntegral(userId,intgeral,integralType,relationId);
    }


}
