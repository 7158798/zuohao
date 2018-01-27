package com.otc.service.sys.biz;

import com.jucaifu.common.log.LOG;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.facade.sys.exceptions.SysBizException;
import com.otc.facade.sys.pojo.po.Resource;
import com.otc.facade.sys.pojo.po.Role;
import com.otc.facade.sys.pojo.po.RoleResource;
import com.otc.facade.sys.pojo.poex.RoleEx;
import com.otc.facade.sys.pojo.vo.RoleVo;
import com.otc.service.sys.dao.RoleMapper;
import com.otc.service.sys.dao.RoleResourceMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lx on 17-4-26.
 */
@Service
@Transactional
public class RoleBiz extends AbsBaseBiz<Role,RoleVo,RoleMapper> {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleResourceMapper roleResourceMapper;
    @Override
    public RoleMapper getDefaultMapper() {
        return roleMapper;
    }


    public void addRole(Role role,List<Resource> resourceList) {
        LOG.dStart(this, "新增角色开始");
        validateRole(resourceList,role,"1");
        //判断角色名称是否存在
        int exists_num = roleMapper.queryExistsNameByAdd(role.getRoleName());
        if(exists_num > 0){
            throw new SysBizException("角色名称已经存在");
        }
        Date createDate = new Date();
        role.setCreateDate(createDate);
        role.setModifiedDate(createDate);
        role.setDeleted(Boolean.FALSE);
        //插入角色信息
        roleMapper.insert(role);

        List<RoleResource> roleResourceList = new ArrayList<>();
        RoleResource roleResource;
        //插入角色菜单信息
        for(Resource resource : resourceList){
            roleResource = new RoleResource();
            roleResource.setDeleted(Boolean.FALSE);
            roleResource.setResourceId(resource.getId());
            roleResource.setRoleId(role.getId());
            roleResource.setCreateDate(createDate);
            roleResource.setCreateUser(role.getCreateUserId());
            roleResource.setModifiedDate(role.getModifiedDate());
            roleResourceList.add(roleResource);
        }
        // 批量增加
        roleResourceMapper.saveList(roleResourceList);
        LOG.dEnd(this, "新增角色结束");
    }


    public void updateRole(Role role,List<Resource> resourceList) {
        LOG.dStart(this, "修改角色开始");
        validateRole(resourceList,role, "2");
        //判断角色名称是否存在
        int exists_num = roleMapper.queryExistsNameByUpdate(role.getRoleName(), role.getId());
        if(exists_num > 0){
            throw new SysBizException("角色名称已经存在");
        }
        //根据id查询角色信息
        Role vo = roleMapper.selectById(role.getId());
        if(vo == null){
            throw new SysBizException("根据角色id查询信息为空，产生脏读，请核对数据后再查询");
        }

        //修改信息
        vo.setRoleName(role.getRoleName());
        vo.setRoleMark(role.getRoleMark());
        vo.setDescription(role.getDescription());
        Date createDate = new Date();
        vo.setModifiedDate(createDate);
        vo.setModifiedUserId(role.getModifiedUserId());
        //修改角色信息
        roleMapper.update(vo);

        //删除修改时，取消勾选的角色信息
        List<Long> resourceIds = new ArrayList<>();
        for(Resource resource : resourceList){
            if(resource != null){
                resourceIds.add(resource.getId());
            }
        }
        roleResourceMapper.deleteByRoleIdAndResource(vo.getId(), resourceIds);
        //删除该角色下的菜单信息
        //roleMenuMapper.deleteByRoleCode(vo.getRoleCode());

        //插入角色菜单信息
        for(Resource resource : resourceList){
            //根据role_code和menuCode判断是否存在，存在则修改，不存在则插入
            RoleResource roleResource = roleResourceMapper.queryByRoleIdAndResourceId(vo.getId(), resource.getId());
            if(roleResource != null){
                roleResource.setModifiedDate(createDate);
                roleResource.setDeleted(Boolean.FALSE);
                roleResource.setModifiedUser(vo.getModifiedUserId());
                roleResourceMapper.update(roleResource);
            }else{
                roleResource = new RoleResource();
                roleResource.setRoleId(vo.getId());
                roleResource.setResourceId(resource.getId());
                roleResource.setCreateDate(createDate);
                roleResource.setModifiedDate(createDate);
                roleResource.setDeleted(Boolean.FALSE);
                roleResource.setCreateUser(role.getModifiedUserId());
                roleResource.setModifiedUser(role.getModifiedUserId());
                roleResourceMapper.insert(roleResource);
            }
        }

        LOG.dEnd(this, "修改角色结束");
    }


    public void deleteById(Long id) {
        LOG.dStart(this, "根据id删除角色信息开始");
        //根据id查询角色数据
        Role role = roleMapper.selectById(id);
        if(role == null){
            throw new SysBizException("根据角色id查询角色详细信息时，查询为空，产生脏读，请核对数据后再查询");
        }
        //先删外键表
        roleResourceMapper.deleteByRoleId(role.getId());
        //后删主表
        roleMapper.deleteById(id);
        LOG.dEnd(this, "根据id删除角色信息结束");
    }


    public RoleEx queryRoleById(Long id) {
        RoleEx roleEx = new RoleEx();
        Role role = roleMapper.selectById(id);
        if(role != null && !role.getDeleted()){
            List<RoleResource> list = roleResourceMapper.queryRoleResourceByRoleId(role.getId());
            roleEx.setRole(role);
            roleEx.setResourceList(list);
        }
        return roleEx;
    }


    public RoleVo queryRoleByConditionPage(RoleVo vo) {
        List<Role> list = roleMapper.queryRolePageListByConditionPage(vo);
        vo.setResultList(list);
        return vo;
    }

    /**
     * 根据员工id获取员工可访问的菜单
     * @param employeeId
     * @return
     */
    public List<RoleResource> queryRoleResourceByEmpId(Long employeeId) {
        return roleResourceMapper.queryRoleResourceByEmpId(employeeId);
    }

    /**
     * 根据员工id、菜单code访问员工能访问该模块的权限信息
     * @param menuCode
     * @param employeeId
     * @return true表示有操作权限
     */
    public Boolean queryPermissionByEmpIdAndMenuCode(String menuCode, Long employeeId) {
        //根据employeeId判断是否是超级管理员,是则表示有权限，不用再详细查询
       /* Employee employee = TzglBizPool.getInstance().employeeConsoleFacade.select(employeeId);
        if(employee == null){
            throw new SysBizException("根据用户id查询用户信息错误！");
        }

        if(StringUtils.isNoneBlank(employee.getLoginName()) && employee.getLoginName().equals("admin")){
            return true;
        }

        RoleMenu roleMenu = roleMenuMapper.queryPermissionByEmpIdAndMenuCode(menuCode, employeeId);
        if(roleMenu != null && roleMenu.getOperation() != null && roleMenu.getOperation().intValue() == 0){
            return true;
        }else{
            return false;
        }*/

        return false;

    }


    public void validateRole(List<Resource> resourceList,Role role, String type){
        if(role == null){
            throw new SysBizException("角色不能为空");
        }

        if(StringUtils.isNotBlank(type) && type.equals("2")){
            if(role.getId() == null){
                throw new SysBizException("角色主键id不能为空");
            }
        }

        if(StringUtils.isBlank(role.getRoleName())){
            throw new SysBizException("角色名称不能为空");
        }

        if(StringUtils.isBlank(role.getDescription())){
            throw new SysBizException("角色描述不能为空");
        }

        if(resourceList == null || resourceList.size() == 0){
            throw new SysBizException("角色菜单权限不能为空");
        }
    }
}
