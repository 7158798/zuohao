package com.otc.service.advertisement.biz;

import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.facade.advertisement.exceptions.PriceFormulaBizException;
import com.otc.facade.advertisement.pojo.po.PriceFormula;
import com.otc.facade.advertisement.pojo.vo.PriceFormulaVo;
import com.otc.service.advertisement.dao.PriceFormulaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zygong on 17-4-28.
 */
@Service
@Transactional
public class PriceFormulaBiz extends AbsBaseBiz<PriceFormula,PriceFormulaVo,PriceFormulaMapper> {
    @Autowired
    private PriceFormulaMapper priceFormulaMapper;
    @Override
    public PriceFormulaMapper getDefaultMapper() {
        return priceFormulaMapper;
    }

    public boolean savePriceFormual(PriceFormula priceFormula){
        boolean flag = false;
        if(priceFormula.getCoinId() == null || priceFormula.getCoinId() == 0){
            throw new PriceFormulaBizException("请先选择币种");
        }
        PriceFormula detail = priceFormulaMapper.detail(null, priceFormula.getCoinId());
        if(detail != null ){
            throw new PriceFormulaBizException("该币种已配置价格公式");
        }
        int insert = priceFormulaMapper.insert(priceFormula);
        if(insert > 0){
            flag = true;
        }
        return flag;
    }

    public boolean updatePriceFormula(PriceFormula priceFormula){
        boolean flag = false;
        if(priceFormula.getCoinId() == null || priceFormula.getCoinId() == 0){
            throw new PriceFormulaBizException("请先选择币种");
        }
        PriceFormula detail = priceFormulaMapper.detail(null, priceFormula.getCoinId());
        if(detail != null && priceFormula.getId() != detail.getId()){
            throw new PriceFormulaBizException("该币种已配置价格公式");
        }
        int update = priceFormulaMapper.update(priceFormula);
        if(update > 0){
            flag = true;
        }
        return flag;
    }

    public PriceFormulaVo queryCountByConditionPage(PriceFormulaVo vo){
        List<PriceFormula> priceFormulas = priceFormulaMapper.queryCountByConditionPage(vo);
        if(priceFormulas != null){
            vo.setResultList(priceFormulas);
        }
        return vo;
    }

    public PriceFormula detail(Long id, Long symbol){
        PriceFormula priceFormula = priceFormulaMapper.detail(id, symbol);
        return priceFormula;
    }
}
