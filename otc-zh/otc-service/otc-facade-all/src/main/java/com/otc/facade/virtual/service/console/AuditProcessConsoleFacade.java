package com.otc.facade.virtual.service.console;

import com.otc.facade.sys.enums.ProcessNode;
import com.otc.facade.sys.pojo.po.AuditProcess;
import com.otc.facade.sys.pojo.vo.AuditProcessVo;

/**
 * Created by lx on 17-5-8.
 */
public interface AuditProcessConsoleFacade {

    AuditProcessVo queryAuditProcessByConditionPage(AuditProcessVo vo);

    boolean updateAuditProcess(AuditProcess auditProcess);

    AuditProcess getAuditProcessByType(String type);

    Boolean isNeedPwd(ProcessNode node,String processType);

    Boolean isLastNode(ProcessNode node,String processType);

}
