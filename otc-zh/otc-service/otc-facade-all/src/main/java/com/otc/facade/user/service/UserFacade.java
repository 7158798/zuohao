package com.otc.facade.user.service;

import com.otc.facade.user.exceptions.UserBizException;
import com.otc.facade.user.pojo.po.User;
import com.otc.facade.user.pojo.poex.CacheUserInfo;

import java.util.List;

/**
 * Created by fenggq on 17-4-20.
 */
public interface UserFacade{


         /**
         *  按登录名或邮箱查询用户(条件查询使用)
         * @param loginName
         * @return
         */
        User selectByLoginName(String loginName);



        /**
         * 通过用户id获得用户信息
         *
         * @param userId
         * @return
         * @throws UserBizException
         */
        User selectById(Long userId) throws UserBizException;

        /**
         *  通过用户id获得用户信息 不存在时抛异常
         * @param UserId
         * @return
         * @throws Exception
         */
        User selectByIdForException(Long UserId) throws Exception;


        /**
         * 修改用户登录密码
         *
         * @param newPassword the new password
         * @param oldPassword the old password
         * @param token       the token
         * @return console
         * @throws UserBizException the console biz exception
         */
        User updateUserPassword(String newPassword, String oldPassword, String token,String code) throws UserBizException;


        /**
         * 获得缓存中用户信息
         *
         * @param userId the console id
         * @return cache console info
         * @throws UserBizException the console biz exception
         */
        CacheUserInfo getCacheUserInfo(String token, Long userId,String ip) throws UserBizException;

        /**
         * 获得缓存中用户信息
         *
         * @param userId the console id
         * @return cache console info
         * @throws UserBizException the console biz exception
         */
        CacheUserInfo getCacheUserInfo(Long userId) throws UserBizException;



        /**
         * 用户注销登陆
         *
         * @param token
         */
        void logout(String token);

        /**
         * 校验并刷新token
         *
         * @param token
         */
        void validateAndRefreshToken(String token);


    /**
     * 设置防钓鱼码
     * @param userId
     * @param fishCode
     */
    void updateUserFishCode(Long userId,String fishCode);

    /**
     * 通过邮箱、uid查询
     *
     * @param filter
     * @return
     */
    List<Long> selectUserByFilter(String filter);

}
