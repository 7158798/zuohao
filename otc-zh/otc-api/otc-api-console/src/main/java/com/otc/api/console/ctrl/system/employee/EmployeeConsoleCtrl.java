package com.otc.api.console.ctrl.system.employee;

import com.jucaifu.common.annotation.log.SysLog;
import com.jucaifu.common.constants.StringPool;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.property.PropertiesUtils;
import com.jucaifu.common.validate.ValidateManager;
import com.jucaifu.common.validate.ValidateType;
import com.otc.api.console.base.BaseConsoleCtrl;
import com.otc.api.console.constants.ConsoleApiEmployee;
import com.otc.api.console.ctrl.system.employee.request.*;
import com.otc.api.console.ctrl.system.employee.response.EmployeeListResp;
import com.otc.api.console.exceptions.ConsoleBizException;
import com.otc.api.console.utils.ConsoleTokenValidate;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.facade.sys.enums.EmployeeStatus;
import com.otc.facade.sys.pojo.po.Employee;
import com.otc.facade.sys.pojo.vo.EmployeeVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhaiyz on 15-12-3.
 */
@Controller
public class EmployeeConsoleCtrl extends BaseConsoleCtrl implements ConsoleApiEmployee {

    /**
     * 添加员工
     *
     * @param req
     * @return
     */
    //@SysLog(code = ModuleConstant.MODULE_EMPLOYEE, method = "添加员工")
    @RequestMapping(value = ADD_EMPLOYEE_API, method = {RequestMethod.POST, RequestMethod.OPTIONS})
    @ResponseBody
    public WebApiResponse addEmployee(@RequestBody EmployeeAddReq req) {

        Long uid = ConsoleTokenValidate.validateToken(req);

        Employee employee = new Employee();

        employee.setName(req.getEmployeeName());
        employee.setLoginName(req.getLoginName());
        employee.setPassword(req.getPassword());
        employee.setStatus(req.getStatus());
        employee.setCreateUser(uid);
        employee.setModifiedUser(uid);

        otc.employeeConsoleFacade.addEmployee(employee);

        return buildWebApiResponse(SUCCESS, null);
    }

    /**
     * 查询员工信息列表 （是否需要登录：是；提交方式：GET）
     *
     * @param queryJson 请求参数
     * @return 员工列表
     */
    @RequestMapping(value = LIST_EMPLOYEE_API, method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse getEmployeeList(@PathVariable String queryJson) {
        LOG.dStart(this, "查询员工信息列表");
        EmployeeListReq req = encodeJsonStr2Obj(queryJson, EmployeeListReq.class);

        EmployeeVo vo = new EmployeeVo();
        vo.setPageShow(req.fetchPageFilterSize());
        vo.setNowPage(req.fetchPageFilterPage());

        if (StringUtils.isNotBlank(req.getEmployeeName())) {
            vo.setEmployeeName(req.getEmployeeName());
        }

        vo = otc.employeeConsoleFacade.queryEmployeeByConditionPage(vo);
        List<Employee> list = vo.fatchTransferList();

        List<EmployeeListResp> respList = new ArrayList<>();
        EmployeeListResp respItem;
        for (Employee employee : list) {
            respItem = new EmployeeListResp(employee);
            respList.add(respItem);
        }
        LOG.dEnd(this, "查询员工信息列表");
        LOG.d(this, respList);
        return buildWebApiPageResponse(vo, respList);
    }

    /**
     * 编辑员工
     *
     * @param req
     * @return
     */
    //@SysLog(code = ModuleConstant.MODULE_EMPLOYEE, method = "更新员工")
    @RequestMapping(value = UPDATE_EMPLOYEE_API, method = {RequestMethod.PUT, RequestMethod.OPTIONS})
    @ResponseBody
    public WebApiResponse EditEmployee(@RequestBody @Valid EmployeeEditReq req) throws ConsoleBizException {

        Long uid = ConsoleTokenValidate.validateToken(req);

        Long employeeId = req.getEmployeeId();
        Employee employee = otc.employeeConsoleFacade.select(employeeId);
        if (employee == null) {
            throw new ConsoleBizException("员工不存在");
        }
        employee.setName(req.getEmployeeName());
        employee.setLoginName(req.getLoginName());
        employee.setStatus(req.getStatus());
        employee.setModifiedUser(uid);

        otc.employeeConsoleFacade.editEmployee(employee);

        return buildWebApiResponse(SUCCESS, null);
    }

    /**
     * 启用停用员工状态（是否需要登录：是；提交方式：PUT）
     *
     * @param req 请求参数
     * @return 操作结果
     * @throws ConsoleBizException
     */
    //@SysLog(code = ModuleConstant.MODULE_EMPLOYEE, method = "更新员工启用停用状态")
    @RequestMapping(value = SWITCH_EMPLOYEE_API, method = {RequestMethod.PUT, RequestMethod.OPTIONS})
    @ResponseBody
    public WebApiResponse switchEmployee(@RequestBody EmployeeHandleReq req) throws ConsoleBizException {
        Long uid = ConsoleTokenValidate.validateToken(req);
        if (req == null) {
            throw new ConsoleBizException("请求参数不正确");
        }
        Long employeeId = req.getEmployeeId();
        EmployeeStatus status = EmployeeStatus.statusMap.get(req.getStatus());
        if (status == null) {
            throw new ConsoleBizException("员工状态参数不正确");
        }
        Employee employee = otc.employeeConsoleFacade.select(employeeId);
        if (employee == null) {
            throw new ConsoleBizException("员工不存在");
        }
        employee.setStatus(req.getStatus());
        employee.setModifiedDate(new Date());
        employee.setModifiedUser(uid);
        otc.employeeConsoleFacade.updateEmployee(employee);
        return buildWebApiResponse(SUCCESS, null);
    }

    /**
     * 修改员工密码 （是否需要登录：是；提交方式：PUT）
     *
     * @param req 请求参数
     * @return 操作结果
     * @throws ConsoleBizException
     */
    //@SysLog(code = ModuleConstant.MODULE_EMPLOYEE, method = "修改员工密码")
    @RequestMapping(value = CHANGE_EMPLOYEE_PASSWORD_API, method = {RequestMethod.PUT, RequestMethod.OPTIONS})
    @ResponseBody
    public WebApiResponse changheEmployeePassword(@RequestBody EmployeeHandleReq req) throws
            ConsoleBizException {

        Long uid = ConsoleTokenValidate.validateToken(req);
        if (req == null) {
            throw new ConsoleBizException("请求参数不正确");
        }
        Long employeeId = req.getEmployeeId();
        String newPassword = req.getNewPasswd();
        Employee employee = otc.employeeConsoleFacade.select(employeeId);
        if (employee == null) {
            throw new ConsoleBizException("员工不存在");
        }
        if (!StringUtils.isNotBlank(newPassword)) {
            throw new ConsoleBizException("新密码不能为空");
        }
        String salt = PropertiesUtils.getProperty("console.password.salt", "");
        newPassword = DigestUtils.md5Hex(newPassword + salt);
        employee.setPassword(newPassword);
        employee.setModifiedDate(new Date());
        employee.setModifiedUser(uid);
        otc.employeeConsoleFacade.updateEmployee(employee);
        return buildWebApiResponse(SUCCESS, null);
    }

    /**
     * 给员工分配角色（是否需要登录：是；提交方式：POST）
     *
     * @param req 请求参数
     * @return 操作结果
     * @throws ConsoleBizException
     */
    //@SysLog(code = ModuleConstant.MODULE_EMPLOYEE, method = "给员工分配角色")
    @RequestMapping(value = ALLOT_EMPLOYEE_ROLE_API, method = {RequestMethod.POST, RequestMethod.OPTIONS})
    @ResponseBody
    public WebApiResponse allotRolesToEmployee(@RequestBody EmployeeAllotRoleReq req) throws
            ConsoleBizException {
        if (req == null || req.getEmployeeId() == null) {
            throw new ConsoleBizException("员工id不能为空");
        }
        List<Long> roleIds = req.getRoleIds();

        otc.employeeConsoleFacade.allotRolesToEmployee(req.getEmployeeId(), roleIds);
        return buildWebApiResponse(SUCCESS, null);
    }

    /**
     * 查询员工信息详情 （是否需要登录：是；提交方式：GET）
     *
     * @param queryJson 请求参数
     * @return 员工详情
     * @throws ConsoleBizException
     */
    /*@RequestMapping(value = DETAIL_EMPLOYEE_API, method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse getEmployeeDetail(@PathVariable String queryJson) throws ConsoleBizException {
        EmployeeHandleReq req = encodeJsonStr2Obj(queryJson, EmployeeHandleReq.class);
        if (req == null) {
            throw new ConsoleBizException("请求参数不正确");
        }
        String employeeId = req.getEmployeeId();
        Employee employee = jcf.employeeConsoleFacade.select(employeeId);
        if (employee == null) {
            throw new ConsoleBizException("员工不存在");
        }
        EmployeeDetailResp resp = new EmployeeDetailResp(employee);
        Company company = jcf.companyConsoleFacade.getCompanyById(employee.getCompanyUuid());
        if (company == null) {
            resp.setCompanyName(StringPool.BLANK);
        } else {
            resp.setCompanyName(company.getName());
        }

        Department dept = jcf.companyConsoleFacade.getDepartmentById(employee.getDepartmentUuid());
        if (dept == null) {
            resp.setDeptName(StringPool.BLANK);
        } else {
            resp.setDeptName(dept.getName());
        }

        resp.setCreator(this.getEmployeeNameByEmployeeId(employee.getCreateUserId()));
        resp.setUpdateUser(this.getEmployeeNameByEmployeeId(employee.getModifiedUserId()));
        String position = employee.getPositions() == null ? "" : employee.getPositions();
        resp.setPosition(position);

        // 得到员工所含有的角色
        List<EmployeeRole> employeeRoles = jcf.employeeRoleConsoleFacade.getEmployeeRolesByEmployeeId(employee.getUuid());
        Role role = null;
        for (EmployeeRole employeeRole : employeeRoles) {
            role = jcf.roleConsoleFacade.findRoleInfo(employeeRole.getRoleUuid());
            if (role == null) {
                throw new ConsoleBizException("角色不存在");
            }
            resp.getHasRoles().add(role.getName());
        }
        return buildWebApiResponse(SUCCESS, resp);
    }

    private String getEmployeeNameByEmployeeId(String employeeId) throws ConsoleBizException {
        String employeeName = "";
        Employee employee = jcf.employeeConsoleFacade.select(employeeId);
        if (employee != null) {
            employeeName = employee.getName();
        }
        return employeeName;
    }*/

    /**
     * 查询员工编辑详情 （是否需要登录：是；提交方式：GET）
     *
     * @param queryJson 请求参数
     * @return 员工详情
     * @throws ConsoleBizException
     */
    /*@RequestMapping(value = EDIT_EMPLOYEE_DETAIL_API, method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse getEditEmployeeDetail(@PathVariable String queryJson) throws ConsoleBizException {
        LOG.dStart(this, "查询员工编辑详情");
        LOG.dStart(this, "校验请求参数");
        EmployeeHandleReq req = encodeJsonStr2Obj(queryJson, EmployeeHandleReq.class);
        if (req == null) {
            throw new ConsoleBizException("请求参数不正确");
        }
        LOG.dEnd(this, "校验请求参数");
        LOG.dStart(this, "处理业务逻辑");
        String employeeId = req.getEmployeeId();
        Employee employee = jcf.employeeConsoleFacade.select(employeeId);
        if (employee == null) {
            throw new ConsoleBizException("员工不存在");
        }
        EmployeeEditDetailResp resp = new EmployeeEditDetailResp();
        resp.setEmployeeId(employee.getUuid());
        resp.setEmployeeCode(employee.getCode());
        resp.setEmployeeName(employee.getName());
        resp.setLoginName(employee.getLoginName());
        resp.setContactNumber(employee.getContactNumber());
        resp.setPosition(employee.getPositions());
        resp.setEmail(employee.getEmailAddress());
        resp.setCompanyId(employee.getCompanyUuid());
        resp.setDeptId(employee.getDepartmentUuid());
        resp.setGender(employee.getGender());
        resp.setOnJob(employee.getOnJob());
        resp.setStatus(employee.getActive());
        LOG.dEnd(this, "处理业务逻辑");
        LOG.d(this, resp);
        LOG.dEnd(this, "查询员工编辑详情");
        return buildWebApiResponse(SUCCESS, resp);
    }*/

    /**
     * 员工修改登录密码（是否需要登录：是；提交方式：PUT）
     *
     * @param req 请求参数
     * @return 操作结果
     * @throws ConsoleBizException
     */
    //@SysLog(code = ModuleConstant.MODULE_EMPLOYEE, method = "员工修改登录密码")
    @RequestMapping(value = UPDATE_PASSWORD_SELF_API, method = {RequestMethod.PUT, RequestMethod.OPTIONS})
    @ResponseBody
    public WebApiResponse updateEmployeePassword(@RequestBody EmployeeSelfReq req) throws
            ConsoleBizException {
        LOG.dStart(this, "员工修改登录密码");
        LOG.dStart(this, "校验参数");
        Long uid = ConsoleTokenValidate.validateToken(req);
        if (req == null) {
            throw new ConsoleBizException("请求参数不正确");
        }
        String oldPassword = req.getOldPasswd();
        String newPassword = req.getNewPasswd();
        Employee employee = otc.employeeConsoleFacade.select(uid);
        if (employee == null) {
            throw new ConsoleBizException("员工未登录，请登录后再试");
        }
        if (!StringUtils.isNotBlank(oldPassword)) {
            throw new ConsoleBizException("旧密码不能为空");
        }
        if (!StringUtils.isNotBlank(newPassword)) {
            throw new ConsoleBizException("新密码不能为空");
        }
        LOG.dEnd(this, "校验参数");
        LOG.dStart(this, "业务逻辑");
        String salt = PropertiesUtils.getProperty("console.password.salt", "");
        oldPassword = DigestUtils.md5Hex(oldPassword + salt);
        if (!employee.getPassword().equalsIgnoreCase(oldPassword)) {
            throw new ConsoleBizException("旧密码不正确");
        }

        newPassword = DigestUtils.md5Hex(newPassword + salt);
        employee.setPassword(newPassword);
        employee.setModifiedDate(new Date());
        employee.setModifiedUser(uid);
        otc.employeeConsoleFacade.updateEmployee(employee);
        LOG.dEnd(this, "业务逻辑");
        return buildWebApiResponse(SUCCESS, null);
    }

    /**
     * 员工修改联系电话（是否需要登录：是；提交方式：PUT）
     *
     * @param req 请求参数
     * @return 操作结果
     * @throws ConsoleBizException
     */
    /*@SysLog(code = ModuleConstant.MODULE_EMPLOYEE, method = "员工修改联系电话")
    @RequestMapping(value = UPDATE_PHONE_NUMBER_API, method = {RequestMethod.PUT, RequestMethod.OPTIONS})
    @ResponseBody
    public WebApiResponse updateEmployeePhone(@RequestBody EmployeeSelfReq req) throws
            ConsoleBizException {
        LOG.dStart(this, "员工修改联系电话");
        LOG.dStart(this, "校验参数");
        String uid = ConsoleTokenValidate.validateToken(req);
        if (req == null) {
            throw new ConsoleBizException("请求参数不正确");
        }
        Employee employee = jcf.employeeConsoleFacade.select(uid);
        if (employee == null) {
            throw new ConsoleBizException("员工未登录，请登录后再试");
        }
        String phoneNumber = req.getPhoneNumber();
        if (StringUtils.isBlank(phoneNumber)) {
            throw new ConsoleBizException("手机号不能为空");
        }
        LOG.dEnd(this, "校验参数");
        LOG.dStart(this, "业务逻辑");
        employee.setContactNumber(phoneNumber);
        employee.setModifiedDate(new Date());
        employee.setModifiedUserId(uid);
        jcf.employeeConsoleFacade.updateEmployee(employee);
        LOG.dEnd(this, "业务逻辑");
        return buildWebApiResponse(SUCCESS, null);
    }*/

    /**
     * 员工修改邮箱地址（是否需要登录：是；提交方式：PUT）
     *
     * @param req 请求参数
     * @return 操作结果
     * @throws ConsoleBizException
     */
   /* @SysLog(code = ModuleConstant.MODULE_EMPLOYEE, method = "员工修改邮箱地址")
    @RequestMapping(value = UPDATE_EMAIL_API, method = {RequestMethod.PUT, RequestMethod.OPTIONS})
    @ResponseBody
    public WebApiResponse updateEmployeeEmail(@RequestBody EmployeeSelfReq req) throws
            ConsoleBizException {
        LOG.dStart(this, "员工修改邮箱地址");
        LOG.dStart(this, "校验参数");
        String uid = ConsoleTokenValidate.validateToken(req);
        if (req == null) {
            throw new ConsoleBizException("请求参数不正确");
        }
        Employee employee = jcf.employeeConsoleFacade.select(uid);
        if (employee == null) {
            throw new ConsoleBizException("员工未登录，请登录后再试");
        }
        String email = req.getEmail();
        if (StringUtils.isNotBlank(email)) {
            if (!ValidateManager.validateValue(email, ValidateType.EMAIL_ADDRESS.getValidateRuleRegex())) {
                throw new ConsoleBizException("邮箱地址格式不正确");
            }
        }
        LOG.dEnd(this, "校验参数");
        LOG.dStart(this, "业务逻辑");
        employee.setEmailAddress(email);
        employee.setModifiedDate(new Date());
        employee.setModifiedUserId(uid);
        jcf.employeeConsoleFacade.updateEmployee(employee);
        LOG.dEnd(this, "业务逻辑");
        return buildWebApiResponse(SUCCESS, null);
    }
*/
    /**
     * 获取员工详细信息（是否需要登录：是；提交方式：GET）
     *
     * @param queryJson 请求参数
     * @return 员工详情
     * @throws ConsoleBizException
     */
    /*@RequestMapping(value = DETAIL_EMPLOYEE_SELF_API, method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse getEmployeeDetailBySelf(@PathVariable String queryJson) throws ConsoleBizException {
        LOG.dStart(this, "获取员工详细信息");
        LOG.dStart(this, "校验参数");
        EmployeeHandleReq req = encodeJsonStr2Obj(queryJson, EmployeeHandleReq.class);
        String uid = ConsoleTokenValidate.validateToken(req);
        if (req == null) {
            throw new ConsoleBizException("请求参数不正确");
        }
        Employee employee = jcf.employeeConsoleFacade.select(uid);
        if (employee == null) {
            throw new ConsoleBizException("员工未登录，请登录后再试");
        }
        LOG.dEnd(this, "校验参数");
        LOG.dStart(this, "业务逻辑");
        EmployeeSelfDetailResp resp = new EmployeeSelfDetailResp(employee);
        Company company = jcf.companyConsoleFacade.getCompanyById(employee.getCompanyUuid());
        if (company == null) {
            resp.setCompanyName(StringPool.BLANK);
        } else {
            resp.setCompanyName(company.getName());
        }

        Department dept = jcf.companyConsoleFacade.getDepartmentById(employee.getDepartmentUuid());
        if (dept == null) {
            resp.setDeptName(StringPool.BLANK);
        } else {
            resp.setDeptName(dept.getName());
        }
        String position = employee.getPositions() == null ? "" : employee.getPositions();
        resp.setPosition(position);

        LOG.d(this, resp);
        LOG.dEnd(this, "业务逻辑");
        return buildWebApiResponse(SUCCESS, resp);
    }*/
}
