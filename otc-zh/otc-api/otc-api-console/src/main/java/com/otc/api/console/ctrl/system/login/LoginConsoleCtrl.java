package com.otc.api.console.ctrl.system.login;

import com.jucaifu.common.property.PropertiesUtils;
import com.otc.api.console.base.BaseConsoleCtrl;
import com.otc.api.console.constants.ConsoleApiLogin;
import com.otc.api.console.ctrl.system.login.request.LoginReq;
import com.otc.api.console.ctrl.system.login.response.LoginResp;
import com.otc.api.console.ctrl.system.login.response.Menu;
import com.otc.api.console.utils.ResourceCompare;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.UserCacheManager;
import com.otc.facade.sys.pojo.po.Employee;
import com.otc.facade.sys.pojo.po.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.*;

/**
 * Created by zhaiyz on 15-11-2.
 */
@Controller
public class LoginConsoleCtrl extends BaseConsoleCtrl implements ConsoleApiLogin {

    /**
     * 登录
     *
     * @param req 登录请求参数
     * @return token以及用户菜单
     */
    //@SysLog(code = ModuleConstant.MODULE_USER, method = "员工登录")
    @RequestMapping(value = LOGIN_API, method = {RequestMethod.POST, RequestMethod.OPTIONS})
    @ResponseBody
    public WebApiResponse<LoginResp> login(@RequestBody LoginReq req) {
        String userName = req.getUserName();
        String password = req.getPassword();

        Subject subject = SecurityUtils.getSubject();

        String salt = PropertiesUtils.getProperty("console.password.salt", "");
        password = DigestUtils.md5Hex(password + salt);

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, password);
        subject.login(usernamePasswordToken);

        LoginResp _loginResp = new LoginResp();

        String token = (String) subject.getSession().getId();
        _loginResp.setToken(token);

        Employee employee = otc.employeeConsoleFacade.getByLoginName(userName);

        //TODO:先存储在Redis中
//        SessionUtils.addUserIdToSession(subject.getSession(), employee.getUuid());
        UserCacheManager.bindUserTokenWithObj(token, employee.getId(), null);

        List<Resource> resourceList = otc.resourceConsoleFacade.queryResourceByEmpId(employee.getId());
        Collections.sort(resourceList, new ResourceCompare());

        // key为resourceId，value为parentResource
        Map<Long, Resource> parentResourceMap = new LinkedHashMap<>();

        // key为parentResourceId，value为childResources
        Map<Long, List<Resource>> childResourceMap = new LinkedHashMap<>();

        for (Resource resource : resourceList) {
            if (resource.getParentId() == null) {
                parentResourceMap.put(resource.getId(), resource);
            } else {
                List<Resource> childResources = childResourceMap.get(resource.getParentId());
                if (childResources == null) {
                    childResources = new ArrayList<>();
                }

                childResources.add(resource);

                childResourceMap.put(resource.getParentId(), childResources);
            }
        }

        // 查询出所有父菜单
        List<Menu> nodes = new ArrayList<>();
        for (Map.Entry<Long, Resource> entry : parentResourceMap.entrySet()) {
            Menu parentMenu = new Menu();
            parentMenu.setName(entry.getValue().getName());
            parentMenu.setCode(entry.getValue().getCode());

            List<Resource> childResources = childResourceMap.get(entry.getKey());

            if (childResources == null) {
                nodes.add(parentMenu);
                continue;
            }

            List<Menu> childMenus = new ArrayList<>();

            for (Resource resource : childResources) {
                Menu childMenu = new Menu();
                childMenu.setName(resource.getName());
                childMenu.setCode(resource.getCode());

                childMenus.add(childMenu);
            }

            parentMenu.setNodes(childMenus);

            nodes.add(parentMenu);
        }

        _loginResp.setMenus(nodes);

        return buildWebApiResponse(SUCCESS, _loginResp);
    }

    //@SysLog(code = ModuleConstant.MODULE_USER, method = "员工注销")
    @RequestMapping(value = LOGOUT_API, method = {RequestMethod.GET})
    @ResponseBody
    public WebApiResponse logout(@PathVariable String queryJson) {
        WebApiBaseReq req = encodeJsonStr2Obj(queryJson, WebApiBaseReq.class);
        UserCacheManager.logOutWithToken(req.getToken());

        Serializable sessionId = req.getToken();
        Subject subject = new Subject.Builder().sessionId(sessionId).buildSubject();
        subject.logout();

        return buildWebApiResponse(SUCCESS, null);
    }
}
