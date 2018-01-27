package com.szzc.core.wallet.pool;

import com.jucaifu.common.context.ApplicationContextHelper;
import com.szzc.core.wallet.biz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lx on 17-5-25.
 */
@Component
public class WalletBizPool {

    @Autowired
    public VirtualAddressBiz virtualAddressBiz;
    @Autowired
    public VirtualWalletBiz virtualWalletBiz;
    @Autowired
    public VirtualCoinTypeBiz virtualCoinTypeBiz;
    @Autowired
    public VirtualCapitaloperationBiz virtualCapitaloperationBiz;

    //平台余额
    @Autowired
    public PlatformBalanceBiz platformBalanceBiz;

    public static WalletBizPool getInstance() {
        return ApplicationContextHelper.getInstance().getBean(WalletBizPool.class);
    }

}
