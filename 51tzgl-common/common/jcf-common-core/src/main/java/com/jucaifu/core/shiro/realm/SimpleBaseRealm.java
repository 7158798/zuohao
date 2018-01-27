package com.jucaifu.core.shiro.realm;

import java.util.List;

import com.jucaifu.core.shiro.permission.BaseActiveUser;
import com.jucaifu.core.shiro.permission.IPermissionService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * SimpleBaseRealm
 *
 * @param <ActiveUserType> the type parameter
 * @param <UserType>       the type parameter
 * @param <MenuType>       the type parameter
 * @param <PermissionType> the type parameter
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/20.
 */
public abstract class SimpleBaseRealm<ActiveUserType extends BaseActiveUser, UserType, MenuType, PermissionType> extends AbsBaseRealm {

    private IPermissionService permissionService;

    /**
     * Sets permission service.
     *
     * @param permissionService the permission service
     */
    public void setPermissionService(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    //    认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        // UsernamePasswordToken对象用来存放提交的登录信息
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;

        UserType dbUser = null;
        try {
            dbUser = (UserType) permissionService.findUserByUserAccount(userToken.getUsername());
            if (dbUser == null) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        ActiveUserType activeUser = convertUser2ActiveUser(dbUser);

        if (activeUser == null) {
            return null;
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                activeUser,
                activeUser.getDbPwd(),
                ByteSource.Util.bytes(activeUser.getDbSalt()),
                this.getName()
        );

        return simpleAuthenticationInfo;
    }


    //    授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        // 将getPrimaryPrincipal方法返回值转为真实身份类型
        //（在上边的doGetAuthenticationInfo认证通过填充到SimpleAuthenticationInfo中身份类型）
        ActiveUserType activeUser = (ActiveUserType) principalCollection.getPrimaryPrincipal();

        String uuid = activeUser.getUuid();

        //获取拥有权限的菜单
        List<MenuType> menuList = null;
        try {
            menuList = permissionService.findMenuListByUserId(uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        activeUser.setMenuList(menuList);

        //获取权限信息
        List<PermissionType> permissionList = null;
        try {
            permissionList = permissionService.findPermissionListByUserId(uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        activeUser.setPermissionList(permissionList);


        //权限code列表
        List<String> permissions = convertPermissionList(permissionList);

        //////////////////////////////
        //// 特殊情况下：需要讲菜单的codeList合并到权限的codeList中
        //////////////////////////////
        if (needMergeMenuAndPermission()) {
            List<String> menus = convertMenuList(menuList);
            if (permissions != null && menus != null) {
                permissions.addAll(menus);
            }
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermissions(permissions);

        return simpleAuthorizationInfo;
    }


    public abstract List<String> convertMenuList(List<MenuType> menuList);

    public abstract List<String> convertPermissionList(List<PermissionType> permissionList);

    /**
     * Convert user 2 active user.
     *
     * @param dbUser the db user
     * @return the active user type
     */
    public abstract ActiveUserType convertUser2ActiveUser(UserType dbUser);

    /**
     * Need merge menu and permission.
     *
     * @return the boolean
     */
    public boolean needMergeMenuAndPermission() {
        return false;
    }

}
