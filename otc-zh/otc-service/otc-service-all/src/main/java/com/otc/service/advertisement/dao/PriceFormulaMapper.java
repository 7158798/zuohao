package com.otc.service.advertisement.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.advertisement.pojo.po.Advertisement;
import com.otc.facade.advertisement.pojo.po.PriceFormula;
import com.otc.facade.advertisement.pojo.vo.AdvertisementVo;
import com.otc.facade.advertisement.pojo.vo.PriceFormulaVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PriceFormulaMapper extends BaseMapper<PriceFormula, PriceFormulaVo> {
    List<PriceFormula> queryCountByConditionPage(PriceFormulaVo vo);

    PriceFormula detail(@Param("id") Long id, @Param("symbol") Long symbol);
}