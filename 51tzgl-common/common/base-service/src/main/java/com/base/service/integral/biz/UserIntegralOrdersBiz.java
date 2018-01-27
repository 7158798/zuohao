package com.base.service.integral.biz;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.base.facade.exception.BaseCommonBizException;
import com.base.service.pool.BaseServiceBizPool;
import com.jucaifu.common.configs.TzglBusinessConfig;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.base.facade.integral.enums.IntegralOrderStatus;
import com.base.facade.integral.enums.IntegralStatus;
import com.base.facade.integral.enums.IntegralType;
import com.base.facade.integral.pojo.bean.CreditConsumeResult;
import com.base.facade.integral.pojo.bean.IntegeralOrderListBean;
import com.base.facade.integral.pojo.po.UserIntegralAccount;
import com.base.facade.integral.pojo.po.UserIntegralOrders;
import com.base.facade.integral.pojo.vo.UserIntegralOrdersVo;
import com.base.service.integral.dao.UserIntegralOrdersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by yangyy on 16-4-29.
 */
@Service
public class UserIntegralOrdersBiz extends AbsBaseBiz<UserIntegralOrders, UserIntegralOrdersVo, UserIntegralOrdersMapper> {

    private static final Logger _logger = LoggerFactory.getLogger(UserIntegralOrdersBiz.class);

    @Autowired
    private UserIntegralOrdersMapper userIntegralOrdersMapper;

    @Override
    public UserIntegralOrdersMapper getDefaultMapper() {
        return userIntegralOrdersMapper;
    }



    /**
     * 生成兑吧兑换预扣积分订单2
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
    public CreditConsumeResult addUserIntegralOrders(Long userId, String appKey, String orderNum, BigDecimal credits, BigDecimal actualPrice,
                                                     BigDecimal facePrice, String params, String ipAddr, String type, String description) throws BaseCommonBizException {

        CreditConsumeResult result = new CreditConsumeResult(false);

//        User user = BaseServiceBizPool.getInstance().userBiz.select(userId);
//
//        if (user == null) {
//            _logger.info("userId=" + userId + "的用户用户不存在");
//            result.setErrorMessage("用户不存在");
//            return result;
//        }

        UserIntegralAccount account = BaseServiceBizPool.getInstance().userIntegralAccountBiz.getByUserId(userId);

        if (account == null) {
            _logger.info("用户无可用积分");
            result.setErrorMessage("对不起，您无可用积分");
            return result;
        }

        if (account.getUsableAmount() == null || account.getUsableAmount().compareTo(credits) == -1) {
            _logger.info("用户可用积分不足");
            result.setErrorMessage("积分余额不足，赚足积分再来吧");
            return result;
        }

        Long usableAmt = account.getUsableAmount().longValue();

        String appKeyConfig = TzglBusinessConfig.DUIBA_APP_KEY == null ? "" : TzglBusinessConfig.DUIBA_APP_KEY;
        if (!appKey.equals(appKeyConfig)) {
            _logger.info("appKey不匹配");
            result.setErrorMessage("请求参数不正确");
            result.setCredits(usableAmt);
            return result;
        }

        if (StringUtils.isBlank(orderNum)) {
            _logger.info("兑吧订单号为空");
            result.setErrorMessage("请求参数不正确");
            result.setCredits(usableAmt);
            return result;
        }

        try {
            //积分账户预扣
            IntegralType integralType = IntegralType.getTypeFromCode(type);
            account = BaseServiceBizPool.getInstance().userIntegralAccountBiz.deductUserIntegral(userId, credits, integralType);
        } catch (Exception e) {
            _logger.info(e.getMessage());
            result.setErrorMessage(e.getMessage());
            result.setCredits(usableAmt);
            return result;
        }

        //生成积分兑换订单
        UserIntegralOrders order = new UserIntegralOrders();

        Date now = new Date();

        order.setCreateDate(now);
        order.setModifiedDate(now);
        order.setType(type);
        order.setUserId(userId);
        order.setActualPrice(actualPrice);
        order.setAppKey(appKey);
        order.setCredits(credits);
        order.setCreditsStatus(IntegralStatus.INIT.getStatus());
        order.setDescription(description);
        order.setFacePrice(facePrice);
        order.setIpAddr(ipAddr);
        order.setOrderNum(orderNum);
        order.setOrderStatus(IntegralOrderStatus.INIT.getStatus());
        order.setParams(params);

        this.insert(order);

        result.setBizId(order.getUuid());
        result.setCredits(account.getUsableAmount().longValue());
        result.setSuccess(true);
        return result;
    }

    /**
     * 兑吧发起兑换成功通知处理
     *
     * @param orderNum
     * @param appKey
     */
    public boolean exchangeSuccessInformHandle(String orderNum, String appKey) {
        UserIntegralOrders order = userIntegralOrdersMapper.getUserIntegralOrderByOrderNumAndAppKey(orderNum, appKey);
        if (order == null) {
            _logger.info("兑吧订单号orderNum=" + orderNum + ",的订单不存在");
            return false;
        }

        order.setModifiedDate(new Date());
        order.setOrderStatus(IntegralOrderStatus.SUCCESS.getStatus());
        order.setCreditsStatus(IntegralStatus.SUCCESS.getStatus());

        this.update(order);

        return true;
    }

    /**
     * 兑吧发起兑换失败通知处理
     *
     * @param orderNum
     * @param appKey
     */
    public boolean exchangeFailInformHandle(String orderNum, String appKey) {
        UserIntegralOrders order = userIntegralOrdersMapper.getUserIntegralOrderByOrderNumAndAppKey(orderNum, appKey);
        if (order == null) {
            _logger.info("兑吧订单号orderNum=" + orderNum + ",的订单不存在");
            return false;
        }
        if (order.getOrderStatus() == IntegralOrderStatus.INIT.getStatus() && order.getCreditsStatus() == IntegralStatus.INIT.getStatus()) {
            try {
              //  UserIntegralAccount account = userIntegralAccountMapper.select(order.getUserId());
                BaseServiceBizPool.getInstance().userIntegralAccountBiz.addUserIntegral(order.getUserId(),
                        order.getCredits(),IntegralType.DUIBA_BUY_PRODUCT_FAIL,order.getId());

            } catch (Exception e) {
                _logger.info(e.getMessage());
                return false;
            }

            //修改订单状态和积分状态
            order.setModifiedDate(new Date());
            order.setOrderStatus(IntegralOrderStatus.FAIL.getStatus());
            order.setCreditsStatus(IntegralStatus.ROLLBACK.getStatus());
            this.update(order);
            return true;
        } else {
            return true;
        }
    }

    public List<IntegeralOrderListBean> getListByConditionPage(UserIntegralOrdersVo vo){
        return userIntegralOrdersMapper.getListByConditionPage(vo);
    }
}
