package com.base.service.info.impl.console;

import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.info.pojo.po.InfoBankProduct;
import com.base.facade.info.pojo.vo.InfoBankProductVo;
import com.base.facade.info.service.console.InfoBankProductConsoleFacade;
import com.base.service.info.impl.InfoProductFacadeImpl;
import com.base.service.pool.BaseServiceBizPool;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zh on 16-9-21.
 */
@Service("infoBankProductConsoleFacade")
public class InfoProductConsoleFacadeImpl extends InfoProductFacadeImpl
                                    implements InfoBankProductConsoleFacade{

    @Override
    public void editInfoBankProduct(String uuid, Integer period, BigDecimal expectedInterestRate, BigDecimal minInvestmentAmount) throws BaseCommonBizException {
         BaseServiceBizPool.getInstance().infoBankProductBiz.editBankProduct(uuid, period, expectedInterestRate, minInvestmentAmount);
    }

    @Override
    public void dropInfoBankProduct(String uuid) throws BaseCommonBizException {
        BaseServiceBizPool.getInstance().infoBankProductBiz.deleteBankProduct(uuid);
    }

    @Override
    public InfoBankProductVo selectInfoBankProductByConditionPage(InfoBankProductVo vo) throws BaseCommonBizException {
        List<InfoBankProduct> list =  BaseServiceBizPool.getInstance().infoBankProductBiz.getByConditionPage(vo);
        vo.setResultList(list);
        return  vo;
    }

    @Override
    public void updateInfoProductStatus(String uuid, String status) throws BaseCommonBizException {
        BaseServiceBizPool.getInstance().infoBankProductBiz.updateBankProductStatus(uuid,status);
    }

    @Override
    public void pushBatchInfoProduct(String batch,String status) throws BaseCommonBizException {
        BaseServiceBizPool.getInstance().infoBankProductBiz.pushBatch(batch,status);
    }

    @Override
    public InfoBankProduct selectInfoProductByUuid(String uuid) throws BaseCommonBizException {
        return BaseServiceBizPool.getInstance().infoBankProductBiz.select(uuid);
    }

    @Override
    public void parseUploadFile(byte[] bytes, String url) throws Exception{
        BaseServiceBizPool.getInstance().infoBankProductBiz.parseUploadFile(bytes, url) ;
    }

    @Override
    public void deleteBatchInfoProduct(List<String> uuids) throws BaseCommonBizException {
        BaseServiceBizPool.getInstance().infoBankProductBiz.deleteBatch(uuids);
    }
}
