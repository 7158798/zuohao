package com.base.facade.welcomepage.app;


import com.base.facade.welcomepage.WelcomePageFacade;
import com.base.facade.welcomepage.pojo.po.WelcomePage;

/**
 * Created by zh on 16-9-5.
 */
public interface WelcomePageAppFacade extends WelcomePageFacade {
    WelcomePage getCurrentWelcomePageUrl();
}
