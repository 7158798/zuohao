package com.otc.service.sys.biz;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.property.PropertiesUtils;
import com.jucaifu.common.validate.ValidateManager;
import com.jucaifu.common.validate.ValidateType;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.facade.sys.exceptions.SysBizException;
import com.otc.facade.sys.pojo.cond.EmployeeRoleCond;
import com.otc.facade.sys.pojo.po.Employee;
import com.otc.facade.sys.pojo.po.EmployeeRole;
import com.otc.facade.sys.pojo.po.Role;
import com.otc.facade.sys.pojo.vo.EmployeeVo;
import com.otc.pool.OTCBizPool;
import com.otc.service.sys.dao.EmployeeMapper;
import com.otc.service.sys.dao.EmployeeRoleMapper;
import org.apache.commons.codec.digest.DigestUtils;
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
public class EmployeeBiz extends AbsBaseBiz<Employee,EmployeeVo,EmployeeMapper> {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;
    @Override
    public EmployeeMapper getDefaultMapper() {
        return employeeMapper;
    }

    public void changePwd(String pwd, Long id) {
        if (StringUtils.isBlank(pwd)) {
            throw new SysBizException("更改密码时，密码不能为空");
        }
        if (id == null) {
            throw new SysBizException("更改密码时，id不能为空");
        }
        String salt = PropertiesUtils.getProperty("console.password.salt", "");
        pwd = DigestUtils.md5Hex(pwd + salt);
        employeeMapper.changePwd(pwd, id);
    }

    /**
     * 密码一致性比对
     *
     * @param pwd
     * @param id
     * @return
     */
    public Boolean pwdCompare(String pwd, Long id) {
        if (StringUtils.isBlank(pwd)) {
            throw new SysBizException("密码一致性比对时，密码不能为空");
        }
        if (id == null) {
            throw new SysBizException("密码一致性比对时，id不能为空");
        }
        Boolean flag = false;
        //根据id查询用户信息
        Employee employee = employeeMapper.select(id);
        if (employee == null) {
            throw new SysBizException("密码一致性比对时，根据id查询不到记录");
        }
        String salt = PropertiesUtils.getProperty("console.password.salt", "");
        pwd = DigestUtils.md5Hex(pwd + salt);
        if (pwd.equals(employee.getPassword())) {
            flag = true;
        }
        return flag;
    }

    /**
     * 分页查询员工信息
     *
     * @param employeeVo
     * @return
     */
    public EmployeeVo queryEmployeeByConditionPage(EmployeeVo employeeVo) {
        LOG.dStart(this, "分页查询员工信息开始");
        List<Employee> list = employeeMapper.queryEmployeeByConditionPage(employeeVo);
        employeeVo.setResultList(list);
        LOG.dEnd(this, "分页查询员工信息结束");
        return employeeVo;
    }

    /**
     * 修改用户最后登录的信息
     *
     * @param employee
     */
    public void updateLastLogin(Employee employee) {
        LOG.dStart(this, "修改用户最后登录信息开始");
        if (employee == null) {
            throw new SysBizException("修改用户登录信息时，参数不能为空");
        }

        Employee vo = employeeMapper.getEmployeeByLoginName(employee.getLoginName());
        if (vo == null) {
            throw new SysBizException("修改用户登录信息时，根据登录名查询不到记录");
        }
        //修改登录信息
       /* vo.setLastLoginDate(employee.getLastLoginDate());
        vo.setLastLoginIp(employee.getLastLoginIp());*/
        employeeMapper.update(vo);
        LOG.dEnd(this, "修改用户最后登录信息结束");
    }

    /**
     * 修改用户状态
     *
     * @param id
     * @param status
     */
    public void updateStatusEmployee(Long id, String status) {
        LOG.dStart(this, "修改用户状态开始");
        if (id == null) {
            throw new SysBizException("修改用户状态时，id不能为空");
        }

        if (StringUtils.isEmpty(status)) {
            throw new SysBizException("修改用户状态时，状态不能为空");
        }
        //根据id查询用户信息
        Employee vo = this.select(id);
        if (vo == null) {
            throw new SysBizException("修改用户状态时，根据id查询不到记录");
        }
        //修改状态
        vo.setModifiedDate(new Date());
        vo.setStatus(status);
        employeeMapper.update(vo);
        LOG.dEnd(this, "修改用户状态结束");
    }

    /**
     * 删除雇员
     *
     * @author luwei
     */
    public void deleteEmployee(Long id) {
        //根据id查询用户信息
        Employee employee = employeeMapper.select(id);
        if (employee == null) {
            throw new SysBizException("根据员工id查询员工详细信息时，查询为空，产生脏读，请核对数据后再查询");
        }
        //先删外键表
        //employeeRoleMapper.deleteByEmployeeId(id);
        //后删除主表
        employeeMapper.deleteById(id);
    }

    /**
     * 通过登录名查询员工
     *
     * @param loginName 　员工登录名
     * @return　员工
     * @update_time luwei
     */
    public Employee getByLoginName(String loginName) {
        return employeeMapper.getEmployeeByLoginName(loginName);
    }

    /**
     * 添加员工
     *
     * @param employee 员工信息
     * @return 添加好的员工
     * @author luwei
     */
    public Employee addEmployee(Employee employee) {
        LOG.dStart(this, "添加员工");

        validateEmployee(employee, "1");
        LOG.dStart(this, "业务逻辑校验");

        //判断登陆名称是否存在
        int exists_num = employeeMapper.queryExistsLoginNameByAdd(employee.getLoginName());
        if (exists_num > 0) {
            throw new SysBizException("登陆名称已经存在");
        }

        LOG.dEnd(this, "业务逻辑校验");

        LOG.dStart(this, "业务处理");

        /*employee.setPasswordReset(Boolean.TRUE);

        employee.setModifiedUserId(employee.getCreateUser());*/

        String salt = PropertiesUtils.getProperty("console.password.salt", "");
        employee.setPassword(DigestUtils.md5Hex(employee.getPassword() + salt));

        Date now = new Date();

        employee.setCreateDate(now);

        employee.setModifiedDate(now);
        employee.setDeleted(Boolean.FALSE);
        employeeMapper.insert(employee);

        LOG.dEnd(this, "业务处理");

        LOG.dEnd(this, "添加员工");

        return employee;
    }

    /**
     * 编辑员工
     *
     * @param employee 员工信息
     * @return 添加好的员工
     */
    public Employee editEmployee(Employee employee) {
        LOG.dStart(this, "编辑员工");

        LOG.dStart(this, "参数格式校验");

        validateEmployee(employee, "2");

        LOG.dEnd(this, "参数格式校验");

        LOG.dStart(this, "业务逻辑校验");

        //判断登陆名称是否存在
        int exists_num = employeeMapper.queryExistsLoginNameByUpdate(employee.getLoginName(), employee.getId());
        if (exists_num > 0) {
            throw new SysBizException("登陆名称已经存在");
        }


        LOG.dEnd(this, "业务逻辑校验");

        LOG.dStart(this, "业务处理");

        Date now = new Date();

        employee.setModifiedDate(now);

        update(employee);

        LOG.dEnd(this, "业务处理");

        LOG.dEnd(this, "修改员工");

        return employee;
    }

    public void validateEmployee(Employee employee, String type) {
        if (employee == null) {
            throw new SysBizException("员工对象为空");
        }

        if (StringUtils.isNoneBlank(type) && type.equals("2")) {
            if (employee.getId() == null) {
                throw new SysBizException("员工主键id不能为空");
            }
        }

        if (StringUtils.isBlank(employee.getName())) {
            throw new SysBizException("员工姓名为空");
        }

        if (StringUtils.isBlank(employee.getLoginName())) {
            throw new SysBizException("员工登录名为空");
        }

        if (StringUtils.isBlank(employee.getPassword())) {
            throw new SysBizException("员工登录密码为空");
        }

        if (!ValidateManager.validateValue(employee.getPassword(), ValidateType.EMPLOYEE_LOGIN_PWD.getValidateRuleRegex())) {
            throw new SysBizException("员工登录密码格式不正确");
        }

    }

    /**
     * 添加时判断登录名是否存在
     *
     * @param loginName
     * @return
     */
    public Integer queryExistsLoginNameByAdd(String loginName) {
        return employeeMapper.queryExistsLoginNameByAdd(loginName);
    }

    /**
     * 修改判断登录名是否存在
     *
     * @param loginName
     * @return
     */
    public Integer queryExistsLoginNameByUpdate(String loginName, Long id) {
        return employeeMapper.queryExistsLoginNameByUpdate(loginName, id);
    }

    /**
     * 给员工分配角色
     *
     * @param empId    员工主键
     * @param roleList 角色列表
     */
    public void allotRolesToEmployee(Long empId, List<Long> roleList) throws SysBizException {
        Employee employee = select(empId);
        //判断员工是否存在
        if (employee == null) {
            throw new SysBizException("员工对象为空");
        }
        EmployeeRoleCond cond = new EmployeeRoleCond();
        cond.setEmployeeId(empId);
        //查询员工已经拥有的角色
        List<EmployeeRole> employeeRoleList = employeeRoleMapper.queryByCondition(cond);
        //删除员工已经取消的角色
        for (EmployeeRole employeeRole : employeeRoleList) {
            if (!roleList.contains(employeeRole.getRoleId())) {
                employeeRoleMapper.delete(employeeRole.getUuid());
            }
        }
        //添加员工新增的角色
        List<Long> employeeRoleIdList = getEmployeeRoleIdList(employeeRoleList);

        for (Long roleId : roleList) {
            if (!employeeRoleIdList.contains(roleId)) {
                if (roleId == null || "".equals(roleId)) {
                    throw new SysBizException("角色id为空");
                }
                Role role = OTCBizPool.getInstance().roleBiz.select(roleId);
                if (role == null) {
                    throw new SysBizException("角色不存在");
                }
                cond = new EmployeeRoleCond();
                cond.setEmployeeId(empId);
                cond.setRoleId(roleId);
                List<EmployeeRole> employeeRoles = employeeRoleMapper.queryByCondition(cond);
                if (employeeRoles != null && employeeRoles.size() > 0) {
                    throw new SysBizException("员工" + employee.getName() + "已含有" + role.getRoleName() + "角色");
                }
                EmployeeRole employeeRole = new EmployeeRole();
                employeeRole.setRoleId(roleId);
                employeeRole.setEmployeeId(empId);
                employeeRole.setCreateUser(empId);
                employeeRole.setCreateDate(new Date());
                employeeRole.setModifiedDate(new Date());
                employeeRole.setDeleted(Boolean.FALSE);
                employeeRoleMapper.insert(employeeRole);
            }
        }

    }

    public Employee getEmployee(Long id){
        Employee employee = employeeMapper.select(id);
        if (employee == null){
            throw new SysBizException("用户信息不存在");
        }
        return employee;
    }


    private List<Long> getEmployeeRoleIdList(List<EmployeeRole> employeeRoleList) {
        List<Long> employeeRoleIdList = new ArrayList<>();
        for (EmployeeRole employeeRole : employeeRoleList) {
            employeeRoleIdList.add(employeeRole.getId());
        }
        return employeeRoleIdList;
    }

}
