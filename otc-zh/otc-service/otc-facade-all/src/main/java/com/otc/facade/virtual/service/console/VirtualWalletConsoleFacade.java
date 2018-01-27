package com.otc.facade.virtual.service.console;

import com.otc.facade.base.CountVoEx;
import com.otc.facade.virtual.pojo.vo.VirtualWalletVo;
import com.otc.facade.virtual.service.VirtualWalletFacade;

import java.util.List;

/**
 * Created by lx on 17-4-19.
 */
public interface VirtualWalletConsoleFacade extends VirtualWalletFacade {

    VirtualWalletVo queryConsoleByConditionPage(VirtualWalletVo vo);

    /**
     * 综合统计
     * @return
     */
    List<CountVoEx> countVitralWallet();
}
