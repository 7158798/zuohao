package com.base.facade.info.service.console;

import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.info.pojo.po.InfoBankProduct;
import com.base.facade.info.pojo.vo.InfoBankProductVo;
import com.base.facade.info.service.InfoBankProductFacade;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zh on 16-9-21.
 */
public interface InfoBankProductConsoleFacade extends InfoBankProductFacade {

    /**
     * 修改银行理财产品
     * @param uuid
     * @param period
     * @param expectedInterestRate
     * @param minInvestmentAmount
     * @return
     * @throws BaseCommonBizException
     */
    void editInfoBankProduct(String uuid, Integer period, BigDecimal expectedInterestRate, BigDecimal minInvestmentAmount) throws BaseCommonBizException;

    /**
     * 删除银行理财产品
     * @param uuid
     * @return
     * @throws BaseCommonBizException
     */
    void dropInfoBankProduct(String uuid) throws BaseCommonBizException;

    /**
     * 分页查询银行理财产品
     * @param vo
     * @return
     * @throws BaseCommonBizException
     */
    InfoBankProductVo selectInfoBankProductByConditionPage(InfoBankProductVo vo) throws BaseCommonBizException;



    /**
     * 修改银行产品发布状态
     * @param uuid
     * @param status
     * @throws BaseCommonBizException
     */
    void updateInfoProductStatus(String uuid, String status) throws BaseCommonBizException;

    /**
     *
     * @param batch
     * @param type
     * @throws BaseCommonBizException
     */
    void pushBatchInfoProduct(String batch, String type) throws BaseCommonBizException;

    /**
     * 通过id查询银行产品详情
     * @param uuid
     * @return
     * @throws BaseCommonBizException
     */
    InfoBankProduct selectInfoProductByUuid(String uuid) throws BaseCommonBizException;




    /**
     * 解析上传的银行利率文件
     * @param bytes
     */
    void parseUploadFile(byte[] bytes, String url) throws Exception;


    /**
     * 批量删除银行理财产品
     * @param uuids
     * @throws BaseCommonBizException
     */
    void deleteBatchInfoProduct(List<String> uuids) throws BaseCommonBizException;
}
