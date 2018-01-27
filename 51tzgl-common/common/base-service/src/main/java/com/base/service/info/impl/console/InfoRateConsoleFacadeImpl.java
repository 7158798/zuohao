package com.base.service.info.impl.console;


import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.info.pojo.po.InfoBank;
import com.base.facade.info.pojo.po.InfoRate;
import com.base.facade.info.pojo.po.InfoRateDetail;
import com.base.facade.info.pojo.vo.InfoBankVo;
import com.base.facade.info.pojo.vo.InfoRateVo;
import com.base.facade.info.service.console.InfoRateConsoleFacade;
import com.base.service.info.impl.InfoRateFacadeImpl;
import com.base.service.pool.BaseServiceBizPool;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zh on 16-8-22.
 */
@Service("infoRateConsoleFacade")
public class InfoRateConsoleFacadeImpl extends InfoRateFacadeImpl
        implements InfoRateConsoleFacade {

    @Override
    public int addInfoBank(String bankName, String log,String appLog) throws BaseCommonBizException {
        return BaseServiceBizPool.getInstance().infoBankBiz.addInfoBank(bankName, log,appLog);
    }

    @Override
    public int editInfoBank(String uuid, String bankName, String log,String appLog) throws BaseCommonBizException {
        return BaseServiceBizPool.getInstance().infoBankBiz.editInfoBank(uuid, bankName, log,appLog);
    }

    @Override
    public void dropInfoBank(String uuid) throws BaseCommonBizException {
         BaseServiceBizPool.getInstance().infoBankBiz.deleteBank(uuid);
    }

    @Override
    public InfoBankVo selectInfoBankByConditionPage(InfoBankVo vo) throws BaseCommonBizException {
        return BaseServiceBizPool.getInstance().infoBankBiz.selectInfoBankByConditionPage(vo);
    }

    @Override
    public List<InfoBank> selectAllInfoBankList() throws BaseCommonBizException {
        return BaseServiceBizPool.getInstance().infoBankBiz.selectAll();
    }

    @Override
    public void setInfoBankShowStatus(String uuid, String status) throws BaseCommonBizException {
        BaseServiceBizPool.getInstance().infoBankBiz.setInfoBankShowStatus(uuid,status);
    }

    @Override
    public void addInfoRate(InfoRate rate, List<InfoRateDetail> details) throws BaseCommonBizException {
        BaseServiceBizPool.getInstance().infoRateBiz.addInfoRate(rate, details);
    }

    @Override
    public void editInfoRate(String rateId, List<InfoRateDetail> details) throws BaseCommonBizException {
        BaseServiceBizPool.getInstance().infoRateBiz.editInfoRate(rateId, details);
    }

    @Override
    public void deleteInfoRate(String rateId) throws BaseCommonBizException {
        BaseServiceBizPool.getInstance().infoRateBiz.deleteInfoRate(rateId);
    }

    @Override
    public void updateInfoRateStatus(String rateId, String status) throws BaseCommonBizException {
        BaseServiceBizPool.getInstance().infoRateBiz.updateInfoRateStatus(rateId, status);

    }

    @Override
    public void pushBatchInfoRate(List<String> list) throws BaseCommonBizException {
        BaseServiceBizPool.getInstance().infoRateBiz.pushBatchInfoRate(list);

    }

    @Override
    public InfoRate selectInfoRateByUuid(String uuid) throws BaseCommonBizException {
        return BaseServiceBizPool.getInstance().infoRateBiz.select(uuid);
    }

    @Override
    public InfoRateVo selectInfoRateByConditionPage(InfoRateVo vo) throws BaseCommonBizException {
        List<InfoRate> list = BaseServiceBizPool.getInstance().infoRateBiz.selectInfoRateByConditionPage(vo);
        vo.setResultList(list);
        return vo;
    }

    @Override
    public void parseUploadFile(byte[] bytes, String url) {
        BaseServiceBizPool.getInstance().infoRateBiz.parseUploadFile(bytes, url);
    }

    @Override
    public List<InfoRate> selectInfoRateAllBatch() throws BaseCommonBizException {
        return BaseServiceBizPool.getInstance().infoRateBiz.selectAllBatch();
    }

    @Override
    public void clearInfoRateRecord(Date clearDate) throws BaseCommonBizException {
        BaseServiceBizPool.getInstance().infoRateBiz.clearInfoRate(clearDate);
    }
}
