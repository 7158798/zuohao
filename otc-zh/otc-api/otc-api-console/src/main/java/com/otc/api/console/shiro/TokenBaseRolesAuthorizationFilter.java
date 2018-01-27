package com.otc.api.console.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by zhaiyz on 15-11-3.
 */
public class TokenBaseRolesAuthorizationFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;

        if (rolesArray == null || rolesArray.length == 0) {
            //no roles specified, so nothing to check - allow access.
            return true;
        }

        Set<String> roles = CollectionUtils.asSet(rolesArray);
        return subject.hasAllRoles(roles);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        return false;
    }

    @Override
    protected Subject getSubject(ServletRequest request, ServletResponse response) {
        Serializable sessionId = WebUtils.getCleanParam(request, "token");
        Subject subject = new Subject.Builder().sessionId(sessionId).buildSubject();
        return subject;
    }
}
