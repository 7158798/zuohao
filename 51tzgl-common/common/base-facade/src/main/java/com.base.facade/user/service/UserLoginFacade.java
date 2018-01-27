package com.base.facade.user.service;

import com.base.facade.user.pojo.po.UserLogin;

/**
 * @author luwei
 * @Date 12/27/16 5:47 PM
 */
public interface UserLoginFacade {


    /**
     * 添加登录日志
     * @param userId
     * @param loginPlatform
     * @param adressIP
     * @param status
     */
    void addUserLogin(Long userId, String loginPlatform, String adressIP, String status);

    /**
     * 将第三方用户登录的id，记录为用户id,仅在合并账户时触发
     * @param userId
     * @param thirdId
     * @return
     */
    int changeUserId(Long userId, Long thirdId);


    /**
     * 获取用户最后一次登录信息
     * @param userId
     * @return
     */
    UserLogin getLastLogin(Long userId);

    /**
     * 查询是否app端登录过
     * @param userId
     * @return true 已登录过  false未登录
     */
    Boolean queryIsAppLogin(Long userId);
}
