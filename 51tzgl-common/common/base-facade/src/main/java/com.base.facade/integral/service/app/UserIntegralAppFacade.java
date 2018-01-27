package com.base.facade.integral.service.app;

import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.integral.pojo.bean.CreditConsumeResult;
import com.base.facade.integral.pojo.vo.UserIntegralDetailVo;
import com.base.facade.integral.service.UserIntegralFacade;

import java.math.BigDecimal;

/**
 * Created by zh on 16-10-23.
 */
public interface UserIntegralAppFacade extends UserIntegralFacade{
    UserIntegralDetailVo getUserIntegralListByConditionPage(UserIntegralDetailVo vo);


    /**
     * 生成兑吧兑换预扣积分订单
     *
     * @param userId
     * @param appKey
     * @param orderNum
     * @param credits
     * @param actualPrice
     * @param facePrice
     * @param params
     * @param ipAddr
     * @param type
     * @param description
     */
    CreditConsumeResult addUserIntegralOrders(Long userId, String appKey, String orderNum, BigDecimal credits, BigDecimal actualPrice,
                                              BigDecimal facePrice, String params, String ipAddr, String type, String description) throws BaseCommonBizException;


    /**
     * 兑吧发起兑换成功通知处理
     *
     * @param orderNum
     * @param appKey
     */
    boolean exchangeSuccessInformHandle(String orderNum, String appKey);

    /**
     * 兑吧发起兑换失败通知处理
     *
     * @param orderNum
     * @param appKey
     */
    boolean exchangeFailInformHandle(String orderNum, String appKey);
}
