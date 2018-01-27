package com.otc.facade.sys.service.console;

import com.otc.facade.sys.service.EmployeeRoleFacade;

import java.util.List;
import java.util.Set;

/**
 * Created by lx on 17-4-27.
 */
public interface EmployeeRoleConsoleFacade extends EmployeeRoleFacade {

    Set<String> getEmployeeRolesCodeByEmployeeId(Long employeeId);

}
