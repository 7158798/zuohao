package com.otc.facade.user.service.console;

import com.otc.facade.user.service.UserAuFacade;

/**
 * Created by a123 on 17-4-25.
 */
public interface UserAuConsoleFacade extends UserAuFacade{
    /**
     * 通过KYC验证
     * @param id
     * @param AdminId
     */
    void passKycAudtiing(Long id,Long AdminId);

    /**
     * 拒绝KYC验证
     * @param id
     * @param AdminId
     */
    void noPassKycAuditing(Long id,Long AdminId,String rejectReason);
}
