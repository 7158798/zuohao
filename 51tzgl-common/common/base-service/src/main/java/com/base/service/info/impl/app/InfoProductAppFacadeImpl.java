package com.base.service.info.impl.app;


import com.base.facade.info.service.app.InfoBankProductAppFacade;
import com.base.service.info.impl.InfoProductFacadeImpl;
import org.springframework.stereotype.Service;

/**
 * Created by zh on 16-9-21.
 */
@Service("infoBankProductAppFacade")
public class InfoProductAppFacadeImpl extends InfoProductFacadeImpl
        implements InfoBankProductAppFacade {
}
