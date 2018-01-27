package com.otc.api.console.ctrl.system.role;

import com.jucaifu.common.log.LOG;
import com.otc.api.console.base.BaseConsoleCtrl;
import com.otc.api.console.constants.ConsoleApiRole;
import com.otc.api.console.ctrl.system.login.response.Menu;
import com.otc.api.console.ctrl.system.role.request.RoleCondReq;
import com.otc.api.console.ctrl.system.role.request.RoleReq;
import com.otc.api.console.ctrl.system.role.response.ResourceResp;
import com.otc.api.console.ctrl.system.role.response.RoleResp;
import com.otc.api.console.exceptions.ConsoleBizException;
import com.otc.api.console.utils.MenuMapInit;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.facade.sys.pojo.po.Resource;
import com.otc.facade.sys.pojo.po.Role;
import com.otc.facade.sys.pojo.po.RoleResource;
import com.otc.facade.sys.pojo.poex.RoleEx;
import com.otc.facade.sys.pojo.vo.RoleVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lx on 17-4-26.
 */
@RestController
public class RoleCtrl extends BaseConsoleCtrl implements ConsoleApiRole {


    @RequestMapping(value = LIST_ROLE_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse getRoleList(@PathVariable String queryJson){
        LOG.dStart(this, "获取角色列表");
        RoleCondReq req = encodeJsonStr2Obj(queryJson, RoleCondReq.class);
        RoleVo vo = new RoleVo();
        if (req.getIsPage()){
            vo.setNowPage(req.fetchPageFilterPage());
            vo.setPageShow(req.fetchPageFilterSize());
        } else {
            vo.setNowPage(1);
            vo.setPageShow(Integer.MAX_VALUE);
        }

        vo = otc.roleConsoleFacade.queryRoleByConditionPage(vo);
        List<Role> list = vo.fatchTransferList();
        List<RoleResp> respList = new ArrayList<>();
        RoleResp resp;
        for (Role role : list){
            resp = new RoleResp();
            resp.setRoleId(role.getId());
            resp.setName(role.getRoleName());
            resp.setDescription(role.getDescription());
            respList.add(resp);
        }
        LOG.d(this, respList);
        LOG.dEnd(this, "获取角色列表");
        if (req.getIsPage()){
            return buildWebApiPageResponse(vo,respList);
        } else {
            return buildWebApiResponse(SUCCESS,respList);
        }
    }

    @RequestMapping(value = ADD_ROLE_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse addRole(@RequestBody RoleReq req){
        LOG.dStart(this, "新增角色");
        filter(req);
        Role role = new Role();
        role.setDescription(req.getDescription());
        role.setRoleName(req.getName());
        List<Resource> resourceList = new ArrayList<>();
        Resource resource;
        for (Long id : req.getResourceIds()){
            resource = new Resource();
            resource.setId(id);
            resourceList.add(resource);
        }
        otc.roleConsoleFacade.addRole(role,resourceList);
        LOG.dEnd(this, "新增角色");
        return buildWebApiResponse(SUCCESS,null);
    }

    @RequestMapping(value = UPDATE_ROLE_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse updateRole(@RequestBody RoleReq req){
        LOG.dStart(this, "更新角色");
        filter(req);
        if (req.getRoleId() == null){
            throw new ConsoleBizException("角色ID不能为空");
        }
        Role role = new Role();
        role.setDescription(req.getDescription());
        role.setRoleName(req.getName());
        role.setId(req.getRoleId());
        List<Resource> resourceList = new ArrayList<>();
        Resource resource;
        for (Long id : req.getResourceIds()){
            resource = new Resource();
            resource.setId(id);
            resourceList.add(resource);
        }
        otc.roleConsoleFacade.updateRole(role, resourceList);
        LOG.dEnd(this, "更新角色");
        return buildWebApiResponse(SUCCESS,null);
    }

    @RequestMapping(value = DETAIL_ROLE_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse detailRole(@PathVariable String queryJson){
        LOG.dStart(this, "获取角色信息");
        WebApiBaseReq req = encodeJsonStr2Obj(queryJson,WebApiBaseReq.class);
        if (req.getId() == null){
            throw new ConsoleBizException("角色ID不能为空");
        }
        RoleEx roleEx = otc.roleConsoleFacade.queryRoleById(req.getId());
        RoleReq resp = new RoleReq();
        if (roleEx != null && roleEx.getRole() != null){
            resp.setDescription(roleEx.getRole().getDescription());
            resp.setName(roleEx.getRole().getRoleName());
            resp.setId(req.getId());
            List<Long> resourceIds = new ArrayList<>();
            for (RoleResource roleResource : roleEx.getResourceList()){
                resourceIds.add(roleResource.getId());
            }
            resp.setResourceIds(resourceIds);
        }
        LOG.dEnd(this, "获取角色信息");
        return buildWebApiResponse(SUCCESS,resp);
    }

    /**
     * 获得权限列表（是否需要登录：是；提交方式：GET）
     *
     * @return 权限列表
     */
    @RequestMapping(value = LIST_PERMISSION_CONSOLE_API, method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse getPermissionList(@PathVariable String queryJson) {
        LOG.dStart(this, "获取权限列表");
        // 查询所有资源
        List<Resource> resourceList = otc.resourceConsoleFacade.queryAll();
        List<Menu> rList = MenuMapInit.getMenuMap(resourceList);
        LOG.dEnd(this, "获取权限列表");
        return buildWebApiResponse(SUCCESS, rList);
    }


    private void filter(RoleReq req){
        if (req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        if (StringUtils.isEmpty(req.getName())){
            throw new ConsoleBizException("角色名称不能为空");
        }
        if (req.getResourceIds() == null || req.getResourceIds().size() == 0){
            throw new ConsoleBizException("资源不能为空");
        }
    }
}
