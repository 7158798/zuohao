package com.otc.service.advertisement.impl.web;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.advertisement.service.web.PriceFormulaWebFacade;
import com.otc.service.advertisement.impl.PriceFormulaFacadeImpl;
import org.springframework.stereotype.Component;

/**
 * Created by zygong on 17-5-3.
 */
@Component
@Service
public class PriceFormulaWebFacadeImpl extends PriceFormulaFacadeImpl implements PriceFormulaWebFacade {
}
