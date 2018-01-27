package com.otc.service.sys.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.sys.pojo.po.Employee;
import com.otc.facade.sys.pojo.vo.EmployeeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper extends BaseMapper<Employee,EmployeeVo> {

    int changePwd(@Param("pwd") String pwd, @Param("id") Long id);

    List<Employee> queryEmployeeByConditionPage(EmployeeVo vo);

    Employee getEmployeeByLoginName(String loginName);

    /**
     * 添加时判断登录名是否存在
     * @param loginName
     * @return
     */
    Integer queryExistsLoginNameByAdd(@Param("loginName") String loginName);

    /**
     * 修改时判断登录名是否存在
     * @param loginName
     * @return
     */
    Integer queryExistsLoginNameByUpdate(@Param("loginName") String loginName, @Param("id") Long id);

    void deleteById(Long id);

}