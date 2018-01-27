package com.otc.facade.user.service;

import com.otc.facade.user.pojo.po.UserAuthentication;

import java.util.List;

/**
 * Created by fenggq on 17-4-25.
 */
public interface UserAuFacade {
    /**
     * 实名认证
     * @param userId
     * @param identityNo
     * @param identityType
     * @return
     */
    UserAuthentication realNameAuth(Long userId,String identityNo,String identityType,String realName);


    /**
     *  KYC认证
     * @param userId
     * @param identityurlOn
     * @param identityurlOff
     * @param identityurlHold
     */
    void kycAuth(Long userId,String identityurlOn,String identityurlOff,String identityurlHold);


    /**
     *  获取用户认证信息
     * @param userId
     * @return
     */
    UserAuthentication getUserAuthentication(Long userId);

    /**
     * 通过姓名
     *
     * @param filter
     * @return
     */
    List<Long> selectUserByFilter(String filter);
}
