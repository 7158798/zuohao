package com.base.service.info.dao;

import com.base.facade.info.pojo.po.InfoBankProduct;
import com.base.facade.info.pojo.vo.InfoBankProductVo;
import com.jucaifu.core.dao.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface InfoBankProductMapper extends BaseMapper<InfoBankProduct,InfoBankProductVo> {

    List<InfoBankProduct> getByCondition(@Param("batchNo") String batchNo, @Param("bankId") String bankId,
                                         @Param("period") Integer period, @Param("status") String status);

    /**
     * 分页获取银行产品
     * @param vo
     * @return
     */
    List<InfoBankProduct> getProductByConditionPage(InfoBankProductVo vo);

    /**
     * 获取最大的发布时间
     * @return
     */
    Date getProductMaxPushDate();
}