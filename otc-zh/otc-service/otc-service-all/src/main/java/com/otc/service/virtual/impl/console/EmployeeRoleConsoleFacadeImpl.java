package com.otc.service.virtual.impl.console;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.sys.service.console.EmployeeRoleConsoleFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.virtual.impl.EmployeeRoleFacadeImpl;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by lx on 17-4-27.
 */
@Component
@Service
public class EmployeeRoleConsoleFacadeImpl extends EmployeeRoleFacadeImpl implements EmployeeRoleConsoleFacade {

    @Override
    public Set<String> getEmployeeRolesCodeByEmployeeId(Long employeeId) {
        return OTCBizPool.getInstance().employeeRoleBiz.getEmployeeRolesCodeByEmployeeId(employeeId);
    }
}
