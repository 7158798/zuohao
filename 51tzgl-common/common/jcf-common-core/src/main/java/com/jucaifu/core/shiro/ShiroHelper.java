package com.jucaifu.core.shiro;

import com.jucaifu.core.shiro.permission.BaseActiveUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 * ShiroHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/20.
 */
public class ShiroHelper {


    /**
     * Gets active user.
     *
     * @return the active user
     */
    public static <ActiveUser extends BaseActiveUser> ActiveUser getActiveUser() {

        Subject subject = SecurityUtils.getSubject();
        ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
        return activeUser;
    }

    /**
     * Gets subject.
     *
     * @return the subject
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * Gets principals.
     *
     * @return the principals
     */
    public static PrincipalCollection getPrincipals() {
        return SecurityUtils.getSubject().getPrincipals();
    }

    /**
     * Login void.
     *
     * @param username the username
     * @param password the password
     */
    public static void login(String username, String password) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
    }

}
