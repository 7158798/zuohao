package com.otc.api.console.shiro;

import com.alibaba.dubbo.config.annotation.Reference;
import com.otc.api.console.service.ConsoleService;
import com.otc.facade.sys.enums.EmployeeStatus;
import com.otc.facade.sys.pojo.po.Employee;
import com.otc.facade.sys.service.console.EmployeeConsoleFacade;
import com.otc.facade.sys.service.console.EmployeeRoleConsoleFacade;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhaiyz on 15-10-27.
 */
@Service("otcShiroRealm")
public class OTCShiroRealm extends AuthorizingRealm {

    @Override
    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ConsoleService consoleService = ConsoleService.getInstance();
        String loginName = (String) principals.fromRealm(getName()).iterator().next();

        Employee employee = consoleService.employeeConsoleFacade.getByLoginName(loginName);

        if (employee != null) {
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

            simpleAuthorizationInfo.setRoles(consoleService.employeeRoleConsoleFacade.getEmployeeRolesCodeByEmployeeId(employee.getId()));

            return simpleAuthorizationInfo;
        }

        return null;
    }

    @Override
    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        ConsoleService consoleService = ConsoleService.getInstance();
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

        String userName = (String) usernamePasswordToken.getPrincipal();

        String password = new String((char[]) usernamePasswordToken.getCredentials());

        Employee employee = consoleService.employeeConsoleFacade.getByLoginName(userName);

        if (employee != null) {
            if (StringUtils.equals(password, employee.getPassword())) {
                if (EmployeeStatus.ENABLED.getCode().equals(employee.getStatus())) {
                    return new SimpleAuthenticationInfo(userName, password, getName());
                } else {
                    throw new LockedAccountException("用户名被锁定！");
                }
            } else {
                throw new AuthenticationException("登录密码有误!");
            }
        } else {
            throw new AuthenticationException("用户不存在!");
        }
    }
}
