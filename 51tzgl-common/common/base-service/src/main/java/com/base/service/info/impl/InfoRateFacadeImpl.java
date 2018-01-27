package com.base.service.info.impl;

import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.info.pojo.po.InfoBank;
import com.base.facade.info.pojo.po.InfoRate;
import com.base.facade.info.pojo.poex.InfoRateDetailEx;
import com.base.facade.info.service.InfoRateFacade;
import com.base.service.pool.BaseServiceBizPool;

import java.util.List;

/**
 * Created by zh on 16-8-22.
 */
public class InfoRateFacadeImpl implements InfoRateFacade {
    public List<InfoBank> selectInfoBankForAll()  throws BaseCommonBizException{
        return BaseServiceBizPool.getInstance().infoBankBiz.selectAll();
    }

    @Override
    public List<InfoRateDetailEx> viewInfoRate(String rateId) throws BaseCommonBizException {
        return BaseServiceBizPool.getInstance().infoRateDetailBiz.selectByRateId(rateId);
    }

    @Override
    public List<InfoRate> selectInfoRateByBankId(String bankId) throws BaseCommonBizException {
        return BaseServiceBizPool.getInstance().infoRateBiz.selectByBankIdAndFinish(bankId);
    }

    @Override
    public InfoBank getInfoBankDetail(String uuid) throws BaseCommonBizException {
        return BaseServiceBizPool.getInstance().infoBankBiz.select(uuid);
    }

    @Override
    public List<InfoBank> getInfoBankForRelease(String type) throws BaseCommonBizException {
        return BaseServiceBizPool.getInstance().infoBankBiz.getInfoBankForRelease(type);
    }
}
