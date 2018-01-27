package com.base.service.info.biz;

import com.base.facade.exception.BaseCommonBizException;
import com.base.service.info.dao.InfoBankProductMapper;
import com.base.service.info.utils.InfoImportExcelUtil;
import com.base.service.pool.BaseServiceBizPool;
import com.jucaifu.common.dfs.FastDFSHelper;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.poi.WDWUtil;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.base.facade.info.enums.InfoBankProductPeriod;
import com.base.facade.info.enums.InfoRateStatus;
import com.base.facade.info.pojo.po.InfoBank;
import com.base.facade.info.pojo.po.InfoBankProduct;
import com.base.facade.info.pojo.vo.InfoBankProductVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zh on 16-9-21.
 */
@Service
public class InfoBankProductBiz extends AbsBaseBiz<InfoBankProduct,InfoBankProductVo,InfoBankProductMapper>{

    @Autowired
    InfoBankProductMapper infoBankProductMapper;
    @Override
    public InfoBankProductMapper getDefaultMapper() {
        return infoBankProductMapper;
    }


    /**
     * 解析上传的银行利率
     *
     * @param bytes
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void parseUploadFile(byte[] bytes, String url) throws Exception{
        LOG.dStart(this, "导入银行理财产品开始");
        InputStream inputStream = new ByteArrayInputStream(bytes);
        List<InfoBankProduct> products = InfoImportExcelUtil.importStreamExcelForBankProduct(inputStream, WDWUtil.isExcel2003(url));
        if (products == null || products.size() == 0) {
            throw new BaseCommonBizException("解析列表为空");
        }
        String batchNo;
        Integer period;
        String bankId;
        String bankName = "";
        InfoBankProduct tempProduct;
        for(InfoBankProduct product:products){
            bankId = product.getBankId();
            period = product.getPeriod();
            batchNo = product.getBatchNo();
            List<InfoBankProduct> list = infoBankProductMapper.getByCondition(batchNo, bankId, period,null);
            if(list.size() != 0){
                tempProduct = list.get(0);
                if(StringUtils.equals(tempProduct.getStatus(),InfoRateStatus.FINISH_RELEASE.getCode())){
                    InfoBank bank = BaseServiceBizPool.getInstance().infoBankBiz.select(tempProduct.getBankId());
                    if(bank != null){
                        bankName = bank.getBankName();
                    }
                    throw new BaseCommonBizException(bankName+" 期限"+product.getPeriod().toString()+"天 已存在发布产品,请检查。");
                }
                this.delete(tempProduct.getUuid());
            }
            this.addBankProduct(product);
        }
        LOG.dEnd(this, "导入银行理财产品开始");
    }


    /**
     * 增加银行理财产品
     * @param product
     */
    public void addBankProduct(InfoBankProduct product){
        //验证参数
        this.validParam(product);
        Date now = new Date();
        product.setCreateDate(now);
        product.setModifedDate(now);
        product.setStatus(InfoRateStatus.INITIAL_RELEASE.getCode());
        this.insert(product);
    }


    /**
     * 修改理财产品
     * @param uuid
     * @param period
     * @param expectedInterestRate
     * @param minInvestmentAmount
     */
    public void editBankProduct(String uuid,Integer period,BigDecimal expectedInterestRate,BigDecimal minInvestmentAmount){
        LOG.dStart(this, "开始修改银行理财产品");
        //验证参数
        if(StringUtils.isBlank(uuid)){
            throw new BaseCommonBizException("产品uuid不能为空");
        }
        if(period == null){
            throw new BaseCommonBizException("产品期限不能为空");
        }
        if(expectedInterestRate == null){
            throw new BaseCommonBizException("产品预期收益率不能为空");
        }
        if(minInvestmentAmount == null){
            throw new BaseCommonBizException("产品可购金额不能为空");
        }
        InfoBankProduct product = this.select(uuid);
        if(product == null){
            throw new BaseCommonBizException("要修改的银行理财产品不存在");
        }

        product.setPeriod(period);
        product.setExpectedInterestRate(expectedInterestRate);
        product.setMinInvestmentAmount(minInvestmentAmount);
        product.setModifedDate(new Date());

        update(product);
        LOG.dEnd(this, "修改银行理财产品结束");
    }


    /**
     * 删除银行理财产品
     * @param uuid
     */
    public void deleteBankProduct(String uuid){
        if(StringUtils.isBlank(uuid)){
            throw new BaseCommonBizException("银行理财产品id不能为空");
        }
        InfoBankProduct product = select(uuid);
        if(product == null){
            throw new BaseCommonBizException("银行理财产品不存在");
        }
        if(StringUtils.equals(product.getStatus(),InfoRateStatus.FINISH_RELEASE.getCode())){
            throw new BaseCommonBizException("已发布产品不能删除");
        }
        this.delete(uuid);
    }


    /**
     * 修改银行状态
     * @param uuid
     * @param status
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void updateBankProductStatus(String uuid,String status){
        LOG.dStart(this,"修改理财产品状态开始");
        if(StringUtils.isBlank(uuid)){
            throw new BaseCommonBizException("银行理财产品id不能为空");
        }
        if(StringUtils.isBlank(status)){
            throw new BaseCommonBizException("需要修改的状态不能为空");
        }
        InfoBankProduct product = select(uuid);
        if(product == null){
            throw new BaseCommonBizException("银行理财产品不存在");
        }
        Date now = new Date();
        if(StringUtils.equals(status, InfoRateStatus.FINISH_RELEASE.getCode())){
            //发布
            if(!StringUtils.equals(product.getStatus(),InfoRateStatus.INITIAL_RELEASE.getCode())){
                throw new BaseCommonBizException("只有草稿状态下才可以发布！");
            }
            List<InfoBankProduct> lists = infoBankProductMapper.getByCondition(null,
                    product.getBankId(),product.getPeriod(),InfoRateStatus.FINISH_RELEASE.getCode());
            //修改同银行同期限产品状态为失效
            for(InfoBankProduct pro:lists){
                pro.setStatus(InfoRateStatus.LOSE_VALID.getCode());
                updateEnhance(infoBankProductMapper,pro);
            }
            product.setPushDate(now);
        }else if(StringUtils.equals(status, InfoRateStatus.INITIAL_RELEASE.getCode())){
            //下架
            if(!StringUtils.equals(product.getStatus(),InfoRateStatus.FINISH_RELEASE.getCode())){
                throw new BaseCommonBizException("发布状态下产品才能下架！");
            }
        }else{
            throw new BaseCommonBizException("需要变更的状态不正确");
        }
        product.setModifedDate(now);
        product.setStatus(status);
        updateEnhance(infoBankProductMapper,product);
        LOG.d(this, "修改理财产品状态结束");
    }


    /**
     *
     * @param batch
     * @param status
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void pushBatch(String batch,String status) {
        LOG.dStart(this, "批量上架下架银行理财产品开始");
        if(StringUtils.isBlank(batch)){
            throw new BaseCommonBizException("批量修改批次号不能为空");
        }
        if(StringUtils.isBlank(status)){
            throw new BaseCommonBizException("状态不能为空");
        }
        List<InfoBankProduct> list = infoBankProductMapper.getByCondition(batch,null,null,null);
        if(list.size() == 0){
            throw new BaseCommonBizException("不存在该批次的银行理财产品");
        }
        for (InfoBankProduct product : list) {
            updateBankProductStatus(product.getUuid(), status);
        }
        LOG.dEnd(this, "批量上架下架银行理财产品结束");
    }

    /**
     * 批量删除银行理财产品
     * @param list
     */
    public void deleteBatch(List<String> list){
        LOG.dStart(this, "批量删除银行理财产品开始");
        if (list == null || list.size() == 0) {
            throw new BaseCommonBizException("发布批次不能为空");
        }
        for (String uuid : list) {
            deleteBankProduct(uuid);
        }
        LOG.dEnd(this, "批量删除银行理财产品结束");
    }


    /**
     * 根据银行id查找银行理财产品
     * @param bankId
     * @return
     */
    public List<InfoBankProduct> getByBankId(String bankId){
        return infoBankProductMapper.getByCondition(null,bankId,null,null);
    }

    /**
     * 验证参数
     * @param product
     */
    public void validParam(InfoBankProduct product){
        if(product == null){
            throw new BaseCommonBizException("银行理财产品不能为空");
        }
        if(StringUtils.isBlank(product.getBankId())){
            throw new BaseCommonBizException("银行id不能为空");
        }
        if(StringUtils.isBlank(product.getBatchNo())){
            throw new BaseCommonBizException("批次号不能为空");
        }
        if(product.getExpectedInterestRate() == null){
            throw new BaseCommonBizException("预期年化收益率不能为空");
        }
        if(product.getMinInvestmentAmount() == null){
            throw new BaseCommonBizException("起购金额不能为空");
        }
        if(product.getPeriod() == null){
            throw new BaseCommonBizException("产品期限不能为空");
        }
    }


    /**
     * 分页获取银行产品数据
     * @param vo
     * @return
     */
    public InfoBankProductVo getProductByConditionPage(InfoBankProductVo vo){

        if (StringUtils.isNotEmpty(vo.getPeriod())){
            InfoBankProductPeriod period = InfoBankProductPeriod.periodMap.get(vo.getPeriod());
            if (period != null){
                // 获取到期限的查询条件
                vo.setStartPeriod(period.getStartPeriod());
                vo.setEndPeriod(period.getEndPeriod());
            }
        }

        List<InfoBankProduct> list = infoBankProductMapper.getProductByConditionPage(vo);
        vo.setResultList(list);
        return vo;
    }

    /**
     * 最大的发布时间
     * @return
     */
    public Date getProductMaxPushDate(){
        return infoBankProductMapper.getProductMaxPushDate();
    }


}
