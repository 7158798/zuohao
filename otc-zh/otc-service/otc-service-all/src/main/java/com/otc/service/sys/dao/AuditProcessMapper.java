package com.otc.service.sys.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.sys.pojo.po.AuditProcess;
import com.otc.facade.sys.pojo.po.Employee;
import com.otc.facade.sys.pojo.poex.AuditProcessEx;
import com.otc.facade.sys.pojo.vo.AuditProcessVo;
import com.otc.facade.sys.pojo.vo.EmployeeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuditProcessMapper extends BaseMapper<AuditProcess,AuditProcessVo> {

    List<AuditProcessEx> queryAuditProcessByConditionPage(AuditProcessVo vo);

    int updateAuditProcess(AuditProcess auditProcess);

    AuditProcess queryAuditProcessByType(@Param("type")String type);
}