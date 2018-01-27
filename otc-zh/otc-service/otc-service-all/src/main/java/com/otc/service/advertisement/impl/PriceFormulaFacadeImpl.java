package com.otc.service.advertisement.impl;

import com.otc.facade.advertisement.pojo.po.PriceFormula;
import com.otc.facade.advertisement.service.PriceFormulaFacade;
import com.otc.pool.OTCBizPool;

/**
 * Created by zygong on 17-4-28.
 */
public class PriceFormulaFacadeImpl implements PriceFormulaFacade{
    @Override
    public PriceFormula detail(Long id, Long symbol) {
        return OTCBizPool.getInstance().priceFormulaBiz.detail(id, symbol);
    }
}
