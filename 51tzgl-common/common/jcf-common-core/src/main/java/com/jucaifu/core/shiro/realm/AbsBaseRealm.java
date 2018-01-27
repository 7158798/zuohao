package com.jucaifu.core.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * AbsBaseRealm
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/16.
 */
public abstract class AbsBaseRealm extends AuthorizingRealm {

    /**
     * 获取realm的名称
     *
     * @return the ream name
     */
    public String getReamName() {
        return getClass().getSimpleName();
    }

    @Override
    public void setName(String name) {
        super.setName(getReamName());
    }

    @Override
    protected abstract AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException;

    @Override
    protected abstract AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection);

    /**
     * Clear cache.
     */
    public void clearCache() {

        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();

        super.clearCache(principals);
    }

}
