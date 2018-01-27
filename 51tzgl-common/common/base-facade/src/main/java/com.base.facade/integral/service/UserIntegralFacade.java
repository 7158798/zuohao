package com.base.facade.integral.service;

import com.base.facade.integral.enums.IntegralType;
import com.base.facade.integral.pojo.po.UserIntegralAccount;
import com.base.facade.integral.pojo.po.UserIntegralDetail;
import com.base.facade.integral.pojo.po.UserIntegralOrders;
import com.base.facade.integral.pojo.vo.UserIntegralDetailVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zh on 16-10-23.
 */
public interface UserIntegralFacade {
    /**
     * 查询积分账户
     * @param userId
     * @return
     */

    UserIntegralAccount getUserIntegralAccountById(Long userId);


    /**
     * 按分类统计积分
     * @param userId
     * @return
     */
    List<UserIntegralDetail>   getUserIntegerDetailByGroup(Long userId);


    /**
     * 积分明细 list
     * @param vo
     * @return
     */
    UserIntegralDetailVo getUserIntegralListByConditionPage(UserIntegralDetailVo vo);


    /**
     * 添加积分  不指定积分数
     * @param userId
     * @param integralType  积分类型
     * @param relationId    来源id
     * @return
     */
    UserIntegralAccount addByTypeAndRelationId(Long userId, IntegralType integralType, Long relationId);

    /**
     *  根据id查询订单
     * @param id
     * @return
     */
    UserIntegralOrders getIntegralOrdersById(Long id);


    /**
     * 添加积分，指定积分数
     * @param userId
     * @param integralType
     * @param intgeral
     * @param relationId
     * @return
     */
    UserIntegralAccount addUserIntegral(Long userId, IntegralType integralType, BigDecimal intgeral, Long relationId);






}
