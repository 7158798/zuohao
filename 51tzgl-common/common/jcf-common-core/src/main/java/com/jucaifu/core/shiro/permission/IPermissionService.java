package com.jucaifu.core.shiro.permission;

import java.util.List;

/**
 * 认证授权服务接口
 * <p>
 * The interface I permission service.
 *
 * @param <ActiveUserType> the type parameter
 * @param <UserType>       the type parameter
 * @param <MenuType>       the type parameter
 * @param <PermissionType> the type parameter
 */
public interface IPermissionService<ActiveUserType, UserType, MenuType, PermissionType> {

    /**
     * 根据用户的账户和密码 进行认证，如果认证通过，返回用户身份信息
     * <p>
     * Authentication active user.
     *
     * @param account  the account
     * @param password the password
     * @return the active user
     * @throws Exception the exception
     */
    public ActiveUserType authentication(String account, String password) throws Exception;

    /**
     * 用户授权，返回授权后的用户身份信息
     * <p>
     * Authorization active user.
     *
     * @param activeUser the active user
     * @return the active user
     * @throws Exception the exception
     */
    public ActiveUserType authorization(ActiveUserType activeUser) throws Exception;

    /**
     * 根据用户账号查询用户信息
     * Find user by user account.
     *
     * @param account the account
     * @return the user
     * @throws Exception the exception
     */
    public UserType findUserByUserAccount(String account) throws Exception;

    /**
     * 根据用户id查询权限范围的菜单信息
     * Find menu list by user id.
     *
     * @param uuid the uuid
     * @return the list
     * @throws Exception the exception
     */
    public List<MenuType> findMenuListByUserId(String uuid) throws Exception;

    /**
     * 根据用户id查询权限范围的相关信息
     * Find permission list by user id.
     *
     * @param uuid the uuid
     * @return the list
     * @throws Exception the exception
     */
    public List<PermissionType> findPermissionListByUserId(String uuid) throws Exception;
}
