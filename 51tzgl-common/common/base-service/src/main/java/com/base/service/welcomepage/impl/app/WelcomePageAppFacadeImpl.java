package com.base.service.welcomepage.impl.app;


import com.base.facade.welcomepage.app.WelcomePageAppFacade;
import com.base.service.welcomepage.impl.WelcomePageFacadeImpl;
import com.base.facade.welcomepage.pojo.po.WelcomePage;
import com.base.service.pool.BaseServiceBizPool;
import org.springframework.stereotype.Service;

/**
 * Created by zh on 16-9-5.
 */
@Service("welcomePageAppFacade")
public class WelcomePageAppFacadeImpl extends WelcomePageFacadeImpl implements WelcomePageAppFacade {
    @Override
    public WelcomePage getCurrentWelcomePageUrl() {
        return BaseServiceBizPool.getInstance().welcomePageBiz.getCurrentWelcomePage();
    }
}
