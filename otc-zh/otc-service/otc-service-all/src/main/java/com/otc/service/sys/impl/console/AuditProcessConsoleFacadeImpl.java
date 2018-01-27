package com.otc.service.sys.impl.console;

import com.alibaba.dubbo.config.annotation.Service;
import com.jucaifu.common.log.LOG;
import com.otc.facade.sys.enums.ProcessNode;
import com.otc.facade.sys.pojo.po.AuditProcess;
import com.otc.facade.sys.pojo.vo.AuditProcessVo;
import com.otc.facade.virtual.service.console.AuditProcessConsoleFacade;
import com.otc.pool.OTCBizPool;
import com.otc.util.PwdEncryptor;
import org.springframework.stereotype.Component;

/**
 * Created by lx on 17-5-8.
 */
@Component
@Service
public class AuditProcessConsoleFacadeImpl implements AuditProcessConsoleFacade {

    @Override
    public boolean updateAuditProcess(AuditProcess auditProcess) {
        return OTCBizPool.getInstance().auditProcessBiz.updateAuditProcess(auditProcess);
    }

    @Override
    public AuditProcessVo queryAuditProcessByConditionPage(AuditProcessVo vo) {
        return OTCBizPool.getInstance().auditProcessBiz.queryAuditProcessByConditionPage(vo);
    }

    public AuditProcess getAuditProcessByType(String type){
        return OTCBizPool.getInstance().auditProcessBiz.getAuditProcess(type);
    }

    @Override
    public Boolean isNeedPwd(ProcessNode node, String processType) {
        return OTCBizPool.getInstance().auditProcessBiz.isNeedPwd(node,processType);
    }

    @Override
    public Boolean isLastNode(ProcessNode node, String processType) {
        return OTCBizPool.getInstance().auditProcessBiz.isLastNode(node, processType);
    }
}
