package com.base.service.info.impl.web;


import com.base.facade.info.service.web.InfoBankProductWebFacade;
import com.base.service.info.impl.InfoProductFacadeImpl;
import org.springframework.stereotype.Service;

/**
 * Created by zh on 16-9-21.
 */
@Service("infoBankProductWebFacade")
public class InfoProductWebFacadeImpl extends InfoProductFacadeImpl
                          implements InfoBankProductWebFacade {
}
