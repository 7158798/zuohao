package com.otc.service.advertisement.impl.console;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.advertisement.pojo.po.PriceFormula;
import com.otc.facade.advertisement.pojo.vo.PriceFormulaVo;
import com.otc.facade.advertisement.service.console.PriceFormulaConsoleFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.advertisement.impl.PriceFormulaFacadeImpl;
import org.springframework.stereotype.Component;

/**
 * Created by zygong on 17-4-28.
 */
@Component
@Service
public class PriceFormulaConsoleFacadeImpl extends PriceFormulaFacadeImpl implements PriceFormulaConsoleFacade {
    @Override
    public int delete(Long id) {
        return OTCBizPool.getInstance().priceFormulaBiz.delete(id);
    }

    @Override
    public boolean updatePriceFormula(PriceFormula priceFormula) {
        return OTCBizPool.getInstance().priceFormulaBiz.updatePriceFormula(priceFormula);
    }

    @Override
    public PriceFormula detail(Long id) {
        return OTCBizPool.getInstance().priceFormulaBiz.select(id);
    }

    @Override
    public PriceFormulaVo queryCountByConditionPage(PriceFormulaVo vo) {
        return OTCBizPool.getInstance().priceFormulaBiz.queryCountByConditionPage(vo);
    }

    @Override
    public boolean savePriceFormual(PriceFormula priceFormula) {
        return OTCBizPool.getInstance().priceFormulaBiz.savePriceFormual(priceFormula);
    }
}
