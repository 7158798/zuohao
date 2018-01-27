package com.otc.facade.sys.service.console;

import com.otc.facade.sys.pojo.po.Employee;
import com.otc.facade.sys.pojo.vo.EmployeeVo;

import java.util.List;

/**
 * Created by lx on 17-4-26.
 */
public interface EmployeeConsoleFacade {

    Employee addEmployee(Employee employee);

    void editEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    Employee getByLoginName(String loginName);

    EmployeeVo queryEmployeeByConditionPage(EmployeeVo vo);

    Employee select(Long empId);

    void allotRolesToEmployee(Long empId,List<Long> roleIds);
}
