package com.szzc.api.three.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jucaifu.common.context.ApplicationContextHelper;
import com.szzc.facade.api.service.UserApiFacade;
import com.szzc.facade.fentrustLog.service.FentrustFacade;
import com.szzc.facade.virtualCoin.service.VirtualCoinFacade;
import com.szzc.facade.wallet.service.WalletFacade;
import org.springframework.stereotype.Component;

/**
 * AppService
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/22.
 */
@Component
public class ThreeService {
    //@Reference
    //public UserApiFacade apikeyFacade;
    @Reference
    public UserApiFacade userApiFacade;
    @Reference
    public WalletFacade walletFacade;
    @Reference
    public VirtualCoinFacade virtualCoinFacade;
    @Reference
    public FentrustFacade fentrustFacade;

    public static ThreeService getInstance(){
        return ApplicationContextHelper.getInstance().getBean(ThreeService.class);
    }

}

