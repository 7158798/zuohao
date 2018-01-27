package com.otc.service.virtual.impl.console;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.base.CountVoEx;
import com.otc.facade.virtual.pojo.vo.VirtualWalletVo;
import com.otc.facade.virtual.service.console.VirtualWalletConsoleFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.virtual.impl.VirtualWalletFacadeImpl;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lx on 17-4-19.
 */
@Component
@Service
public class VirtualWalletConsoleFacadeImpl extends VirtualWalletFacadeImpl implements VirtualWalletConsoleFacade {

    @Override
    public VirtualWalletVo queryConsoleByConditionPage(VirtualWalletVo vo) {
        return OTCBizPool.getInstance().virtualWalletBiz.queryConsoleByConditionPage(vo);
    }

    @Override
    public List<CountVoEx> countVitralWallet() {
        return OTCBizPool.getInstance().virtualWalletBiz.countVitralWallet();
    }
}
