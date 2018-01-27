package com.base.service.welcomepage.impl.console;


import com.base.facade.welcomepage.console.WelcomePageConsoleFacade;
import com.base.service.welcomepage.impl.WelcomePageFacadeImpl;
import com.base.facade.welcomepage.pojo.po.WelcomePage;
import com.base.facade.welcomepage.pojo.vo.WelcomePageVo;
import com.base.service.pool.BaseServiceBizPool;
import org.springframework.stereotype.Service;

/**
 * Created by zh on 16-9-5.
 */
@Service("welcomePageConsoleFacade")
public class WelcomePageConsoleFacadeImpl extends WelcomePageFacadeImpl implements WelcomePageConsoleFacade {

    /**
     * 添加欢迎页
     * @param welcomePage
     * @return rows受影响的行数
     */
    @Override
    public int addWelcomePage(WelcomePage welcomePage){
        return BaseServiceBizPool.getInstance().welcomePageBiz.addWelcomePage(welcomePage);
    }

    @Override
    public int updateWelcomePage(WelcomePage welcomePage) {
        return BaseServiceBizPool.getInstance().welcomePageBiz.updateWelcomePage(welcomePage);
    }

    @Override
    public WelcomePage queryWelcomePageByUUID(String uuid) {
        return BaseServiceBizPool.getInstance().welcomePageBiz.queryWelcomePageByUUID(uuid);
    }

    @Override
    public int deleteWelcomePage(String uuid) {
        return BaseServiceBizPool.getInstance().welcomePageBiz.deleteWelcomePage(uuid);
    }

    @Override
    public WelcomePageVo queryWelcomePageListByConditionPage(WelcomePageVo vo) {
        return BaseServiceBizPool.getInstance().welcomePageBiz.queryWelcomePageListByConditionPage(vo);
    }

    @Override
    public int updateActiveWelcomePage(String uuid, Boolean active) {
        return BaseServiceBizPool.getInstance().welcomePageBiz.updateActiveWelcomePage(uuid,active);
    }
}
