package com.base.service.info.impl;

import com.base.service.pool.BaseServiceBizPool;
import com.base.facade.info.pojo.vo.InfoBankProductVo;
import com.base.facade.info.service.InfoBankProductFacade;

import java.util.Date;

/**
 * Created by zh on 16-9-21.
 */
public class InfoProductFacadeImpl implements InfoBankProductFacade{

    @Override
    public InfoBankProductVo getProductByConditionPage(InfoBankProductVo vo) {
        return BaseServiceBizPool.getInstance().infoBankProductBiz.getProductByConditionPage(vo);
    }

    @Override
    public Date getProductMaxPushDate() {
        return BaseServiceBizPool.getInstance().infoBankProductBiz.getProductMaxPushDate();
    }
}
