package com.otc.service.user.impl.console;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.user.enums.UserAutStatusEnum;
import com.otc.facade.user.service.console.UserAuConsoleFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.user.impl.UserAuFacadeImpl;

/**
 * Created by a123 on 17-4-25.
 */
@Service
public class UserAuConsoleFacadeImpl extends UserAuFacadeImpl implements UserAuConsoleFacade{
    @Override
    public void passKycAudtiing(Long id, Long AdminId) {
        OTCBizPool.getInstance().userAuBiz.updateUserKycStatus(id,AdminId, UserAutStatusEnum.KYC_PASS.getCode(),null);
    }

    @Override
    public void noPassKycAuditing(Long id, Long AdminId,String rejectReason) {
        OTCBizPool.getInstance().userAuBiz.updateUserKycStatus(id,AdminId, UserAutStatusEnum.KYC_NO_PASS.getCode(),rejectReason);
    }
}
