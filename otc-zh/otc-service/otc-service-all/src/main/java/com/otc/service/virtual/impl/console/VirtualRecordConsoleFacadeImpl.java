package com.otc.service.virtual.impl.console;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.base.CountVo;
import com.otc.facade.base.CountVoEx;
import com.otc.facade.virtual.pojo.po.VirtualRecord;
import com.otc.facade.virtual.pojo.vo.VirtualRecordVo;
import com.otc.facade.virtual.service.console.VirtualRecordConsoleFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.virtual.impl.VirtualRecordFacadeImpl;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by lx on 17-4-17.
 */
@Component
@Service
public class VirtualRecordConsoleFacadeImpl extends VirtualRecordFacadeImpl implements VirtualRecordConsoleFacade {

    @Override
    public VirtualRecordVo queryConsoleByConditionPage(VirtualRecordVo vo) {
        return OTCBizPool.getInstance().virtualRecordBiz.queryConsoleByConditionPage(vo);
    }

    @Override
    public boolean lockWithdraw(Long id) {
        return OTCBizPool.getInstance().virtualRecordBiz.lockWithdraw(id);
    }

    @Override
    public boolean unlockWithdraw(Long id) {
        return OTCBizPool.getInstance().virtualRecordBiz.unlockWithdraw(id);
    }

    @Override
    public boolean auditWithdraw(Long recordId, Long empId, String confirmPwd, String walletPwd) {
        return OTCBizPool.getInstance().virtualRecordBiz.auditWithdraw(recordId, empId, confirmPwd, walletPwd);
    }

    @Override
    public List<CountVoEx> countVitralRecord(String type) {
        return OTCBizPool.getInstance().virtualRecordBiz.countVitralRecord(type);
    }

    @Override
    public List<CountVoEx> countVitralRecord(Date dayTime, String status, String type) {
        return OTCBizPool.getInstance().virtualRecordBiz.countVitralRecord(dayTime, status, type);
    }

    @Override
    public VirtualRecord queryById(Long id) {
        return OTCBizPool.getInstance().virtualRecordBiz.select(id);
    }

    @Override
    public boolean cancelWithdraw(Long id,String reason) {
        return OTCBizPool.getInstance().virtualRecordBiz.cancelWithdraw(id,reason);
    }
}
