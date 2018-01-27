package com.otc.service.sys.biz;

import com.jucaifu.common.property.PropertiesUtils;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.facade.sys.enums.AuditProcessEnum;
import com.otc.facade.sys.enums.ProcessNode;
import com.otc.facade.sys.exceptions.SysBizException;
import com.otc.facade.sys.pojo.cond.EmployeeRoleCond;
import com.otc.facade.sys.pojo.po.AuditProcess;
import com.otc.facade.sys.pojo.po.Employee;
import com.otc.facade.sys.pojo.po.EmployeeRole;
import com.otc.facade.sys.pojo.poex.AuditProcessEx;
import com.otc.facade.sys.pojo.vo.AuditProcessVo;
import com.otc.facade.virtual.enums.VirtualRecordOutStatus;
import com.otc.pool.OTCBizPool;
import com.otc.service.sys.dao.AuditProcessMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by lx on 17-5-8.
 */
@Service
public class AuditProcessBiz extends AbsBaseBiz<AuditProcess,AuditProcessVo,AuditProcessMapper> {
    @Autowired
    private AuditProcessMapper auditProcessMapper;
    @Override
    public AuditProcessMapper getDefaultMapper() {
        return auditProcessMapper;
    }

    public AuditProcessVo queryAuditProcessByConditionPage(AuditProcessVo vo){
        List<AuditProcessEx> list = auditProcessMapper.queryAuditProcessByConditionPage(vo);
        if (list != null)
            vo.setResultList(list);
        return vo;
    }

    public Boolean updateAuditProcess(AuditProcess auditProcess){
        Boolean flag = false;
        if (auditProcess == null){
            throw new SysBizException("审核流程不能为空");
        }
        if (auditProcess.getId() == null){
            throw new SysBizException("审核流程ID不能为空");
        }
        AuditProcess temp = auditProcessMapper.select(auditProcess.getId());
        if (temp == null){
            throw new SysBizException("审核流程不存在");
        }
        temp.setIsNeedPwd(auditProcess.getIsNeedPwd());
        temp.setRoleId1(auditProcess.getRoleId1());
        temp.setRoleId2(auditProcess.getRoleId2());
        temp.setRoleId3(auditProcess.getRoleId3());
        temp.setModifiedId(auditProcess.getModifiedId());
        temp.setModifiedDate(new Date());
        int ret = auditProcessMapper.updateAuditProcess(temp);
        if (ret > 0){
            flag = Boolean.TRUE;
        }
        return flag;
    }

    /**
     * 根据类型查询
     * @param type
     * @return
     */
    public AuditProcess getAuditProcess(String type){
        return auditProcessMapper.queryAuditProcessByType(type);
    }

    /**
     * 是否需要确认密码
     * @param node
     * @param processType
     * @return
     */
    public Boolean isNeedPwd(ProcessNode node,String processType){
        Boolean flag = Boolean.FALSE;
        AuditProcess auditProcess = auditProcessMapper.queryAuditProcessByType(processType);
        if (auditProcess == null){
            return Boolean.FALSE;
        }
        // 流程序号
        int no = node.getNodeNo(node);
        Long roleId;
        switch (no){
            case 1:
                roleId=auditProcess.getRoleId1();break;
            case 2:
                roleId=auditProcess.getRoleId2();break;
            case 3:
                roleId=auditProcess.getRoleId3();break;
            default:
                throw new SysBizException("流程节点不正确");
        }
        if (roleId != null){
            flag = auditProcess.getIsNeedPwd();
        }
        return flag;
    }

    /**
     * 是否是最后的节点
     * @param node
     * @param processType
     * @return
     */
    public Boolean isLastNode(ProcessNode node,String processType){
        Boolean flag = Boolean.TRUE;
        AuditProcess auditProcess = auditProcessMapper.queryAuditProcessByType(processType);
        if (auditProcess == null){
            return Boolean.TRUE;
        }
        // 流程序号
        int no = node.getNodeNo(node);
        Long nextId = null;
        switch (no){
            case 1:
                nextId=auditProcess.getRoleId2();break;
            case 2:
                nextId=auditProcess.getRoleId3();break;
            case 3:
                break;
            default:
                throw new SysBizException("流程节点不正确");
        }
        if (nextId != null){
            flag = Boolean.FALSE;
        }
        return flag;
    }


    public String getAuditProcess(ProcessNode node,Employee employee,String processType,String confirmPwd){
        AuditProcess auditProcess = auditProcessMapper.queryAuditProcessByType(processType);
        if (auditProcess == null){
            throw new SysBizException("流程数据不存在");
        }
        if (auditProcess.getIsNeedPwd()){
            // 需要校验密码
            if (StringUtils.isEmpty(confirmPwd)){
                throw new SysBizException("确认密码不能为空");
            }
            String salt = PropertiesUtils.getProperty("console.password.salt", "");
            String password = DigestUtils.md5Hex(confirmPwd + salt);
            if (!password.equals(employee.getPassword())){
                throw new SysBizException("确认密码不正确");
            }
        }
        Long roleId;
        Long nextId = null;
        // 流程序号
        int no = node.getNodeNo(node);
        switch (no){
            case 1:
                roleId=auditProcess.getRoleId1();nextId=auditProcess.getRoleId2();break;
            case 2:
                roleId=auditProcess.getRoleId2();nextId=auditProcess.getRoleId3();break;
            case 3:
                roleId=auditProcess.getRoleId3();break;
            default:
                throw new SysBizException("流程节点不正确");
        }
        // 验证权限
        authority(employee,roleId);
        if (roleId == null || nextId == null){
            return node.getEndNode();
        }
        return node.getNextNode(node);
    }


    private void authority(Employee employee,Long roleId){
        if (roleId == null){
            return;
        }
        EmployeeRoleCond cond = new EmployeeRoleCond();
        cond.setEmployeeId(employee.getId());
        cond.setRoleId(roleId);
        List<EmployeeRole> list = OTCBizPool.getInstance().employeeRoleBiz.queryEmployeeRoleByCondition(cond);
        if (list.size() == 0){
            throw new SysBizException("没有审核的权限");
        }
    }



}
