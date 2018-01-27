package com.otc.facade.advertisement.service.console;

import com.otc.facade.advertisement.pojo.po.PriceFormula;
import com.otc.facade.advertisement.pojo.vo.PriceFormulaVo;

/**
 * Created by zygong on 17-4-28.
 */
public interface PriceFormulaConsoleFacade {
    boolean savePriceFormual(PriceFormula priceFormula);
    int delete(Long id);
    boolean updatePriceFormula(PriceFormula priceFormula);
    PriceFormula detail(Long id);
    PriceFormulaVo queryCountByConditionPage(PriceFormulaVo vo);
}
