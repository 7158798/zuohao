package com.otc.service.sys.biz;

import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.facade.sys.pojo.cond.EmployeeRoleCond;
import com.otc.facade.sys.pojo.po.EmployeeRole;
import com.otc.facade.sys.pojo.vo.EmployeeRoleVo;
import com.otc.service.sys.dao.EmployeeRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lx on 17-4-27.
 */
@Service
public class EmployeeRoleBiz extends AbsBaseBiz<EmployeeRole,EmployeeRoleVo,EmployeeRoleMapper> {

    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;
    @Override
    public EmployeeRoleMapper getDefaultMapper() {
        return employeeRoleMapper;
    }


    public Set<String> getEmployeeRolesCodeByEmployeeId(Long employeeId) {
        EmployeeRoleCond cond = new EmployeeRoleCond();
        cond.setEmployeeId(employeeId);
        List<EmployeeRole> list = employeeRoleMapper.queryByCondition(cond);
        Set<String> result = new HashSet<>();
        if (list != null){
            for (EmployeeRole employeeRole:list){
                result.add(String.valueOf(employeeRole.getRoleId()));
            }
        }
        return result;
    }

    public List<EmployeeRole> queryEmployeeRoleByCondition(EmployeeRoleCond cond){
        return employeeRoleMapper.queryByCondition(cond);
    }
}
