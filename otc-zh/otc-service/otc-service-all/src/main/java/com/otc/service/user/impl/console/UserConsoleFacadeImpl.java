package com.otc.service.user.impl.console;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.base.CountVo;
import com.otc.facade.user.enums.UserStatusEnum;
import com.otc.facade.user.pojo.po.User;
import com.otc.facade.user.pojo.vo.UserVo;
import com.otc.facade.user.service.console.UserConsoleFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.user.impl.UserFacadeImpl;

import java.util.Date;
import java.util.List;

/**
 * Created by a123 on 17-4-20.
 */
@Service
public class UserConsoleFacadeImpl extends UserFacadeImpl implements UserConsoleFacade{
    @Override
    public UserVo selectUserListByConditionPage(UserVo vo) {
        return OTCBizPool.getInstance().userBiz.selectUserListByConditionPage(vo);
    }

    @Override
    public void forbiddenUser(Long userId) {
        OTCBizPool.getInstance().userBiz.updateUserStatus(userId, UserStatusEnum.FORBIDDEN.getCode());
    }

    @Override
    public void LiftForbiddenUser(Long userId) {
        OTCBizPool.getInstance().userBiz.updateUserStatus(userId, UserStatusEnum.COMMON.getCode());
    }

    @Override
    public String resetUserPwd(Long userId, String adminToken) {
        return OTCBizPool.getInstance().userBiz.resetPwdByAdmin(userId,adminToken);
    }

    @Override
    public UserVo selectUserAddressList(UserVo vo) {
        return OTCBizPool.getInstance().userBiz.getUserAddressListByConditionPage(vo);
    }

    @Override
    public UserVo selectUserOperationList(UserVo vo) {
        return OTCBizPool.getInstance().userBiz.getUserOperationListByConditionPage(vo);
    }

    @Override
    public UserVo selectUserAssetList(UserVo vo) {
        return OTCBizPool.getInstance().userBiz.getUserAssetListByConditionPage(vo);
    }

    @Override
    public List<CountVo> countUser(Date dayTime) {
        return OTCBizPool.getInstance().userBiz.countUser(dayTime);
    }

    @Override
    public List<CountVo> countUser() {
        return OTCBizPool.getInstance().userBiz.countUser();
    }


}
