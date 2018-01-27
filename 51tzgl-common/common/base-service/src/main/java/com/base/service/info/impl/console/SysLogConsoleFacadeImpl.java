package com.base.service.info.impl.console;

import com.base.service.pool.BaseServiceBizPool;
import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.info.pojo.po.LogModulePo;
import com.base.facade.info.pojo.po.SysLogPo;
import com.base.facade.info.pojo.vo.SysLogVo;
import com.base.facade.info.service.console.SysLogConsoleFacade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysLogConsoleFacade")
public class SysLogConsoleFacadeImpl implements SysLogConsoleFacade {

    @Override
    public SysLogVo getSysLogList(SysLogVo vo) {

        vo = BaseServiceBizPool.getInstance().sysLogBiz.getSysLogList(vo);

        return vo;
    }

    @Override
    public void addSysLog(SysLogPo po) throws BaseCommonBizException {
        BaseServiceBizPool.getInstance().sysLogBiz.insert(po);
    }

    @Override
    public void editSysLog(SysLogPo po) throws BaseCommonBizException {
        BaseServiceBizPool.getInstance().sysLogBiz.editSysLog(po);
    }

    @Override
    public SysLogPo viewSysLogDetail(String uuid,String methordName) throws BaseCommonBizException {

        return BaseServiceBizPool.getInstance().sysLogBiz.selectByUidOrMethordName(uuid,methordName);
    }

    @Override
    public SysLogPo queryByRemart(String remart) {
        return BaseServiceBizPool.getInstance().sysLogBiz.queryByRemart(remart);
    }
}
