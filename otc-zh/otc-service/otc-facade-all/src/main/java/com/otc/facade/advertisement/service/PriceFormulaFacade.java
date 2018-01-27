package com.otc.facade.advertisement.service;

import com.otc.facade.advertisement.pojo.po.PriceFormula;

/**
 * Created by zygong on 17-4-28.
 */
public interface PriceFormulaFacade {

    PriceFormula detail(Long id, Long symbol);
}
