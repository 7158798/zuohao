package com.otc.service.sys.impl.console;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.sys.pojo.po.Employee;
import com.otc.facade.sys.pojo.vo.EmployeeVo;
import com.otc.facade.sys.service.console.EmployeeConsoleFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.sys.impl.EmployeeFacadeImpl;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lx on 17-4-26.
 */
@Component
@Service
public class EmployeeConsoleFacadeImpl extends EmployeeFacadeImpl implements EmployeeConsoleFacade {

    @Override
    public Employee addEmployee(Employee employee) {
        return OTCBizPool.getInstance().employeeBiz.addEmployee(employee);
    }

    @Override
    public Employee getByLoginName(String loginName) {
        return OTCBizPool.getInstance().employeeBiz.getByLoginName(loginName);
    }

    @Override
    public EmployeeVo queryEmployeeByConditionPage(EmployeeVo vo) {
        return OTCBizPool.getInstance().employeeBiz.queryEmployeeByConditionPage(vo);
    }

    @Override
    public void editEmployee(Employee employee) {
        OTCBizPool.getInstance().employeeBiz.editEmployee(employee);
    }

    @Override
    public Employee select(Long empId) {
        return OTCBizPool.getInstance().employeeBiz.select(empId);
    }

    @Override
    public void allotRolesToEmployee(Long empId, List<Long> roleIds) {
        OTCBizPool.getInstance().employeeBiz.allotRolesToEmployee(empId, roleIds);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        OTCBizPool.getInstance().employeeBiz.update(employee);
        return employee;
    }
}
