package com.base.service.integral.impl.app;

import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.integral.pojo.bean.CreditConsumeResult;
import com.base.facade.integral.service.app.UserIntegralAppFacade;
import com.base.service.integral.impl.UserIntegralFacadeImpl;
import com.base.service.pool.BaseServiceBizPool;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by zh on 16-10-23.
 */
@Service("userIntegralAppFacade")
public class UserIntegralAppFacadeImpl extends UserIntegralFacadeImpl implements UserIntegralAppFacade {

    @Override
    public CreditConsumeResult addUserIntegralOrders(Long userId, String appKey, String orderNum, BigDecimal credits, BigDecimal actualPrice, BigDecimal facePrice, String params, String ipAddr, String type, String description) throws BaseCommonBizException {
        return BaseServiceBizPool.getInstance().userIntegralOrdersBiz.addUserIntegralOrders(userId, appKey, orderNum, credits, actualPrice, facePrice, params, ipAddr, type, description);
    }

    @Override
    public boolean exchangeSuccessInformHandle(String orderNum, String appKey) {
        return BaseServiceBizPool.getInstance().userIntegralOrdersBiz.exchangeSuccessInformHandle(orderNum, appKey);
    }

    @Override
    public boolean exchangeFailInformHandle(String orderNum, String appKey) {
        return BaseServiceBizPool.getInstance().userIntegralOrdersBiz.exchangeFailInformHandle(orderNum, appKey);
    }
}

