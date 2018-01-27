package com.otc.service.virtual.impl.web;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.virtual.pojo.po.VirtualRecord;
import com.otc.facade.virtual.service.web.VirtualRecordWebFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.virtual.impl.VirtualRecordFacadeImpl;
import org.springframework.stereotype.Component;

/**
 * Created by lx on 17-4-17.
 */
@Component
@Service
public class VirtualRecordWebFacadeImpl extends VirtualRecordFacadeImpl implements VirtualRecordWebFacade {

    @Override
    public boolean saveVirtualRecord(VirtualRecord record) {
        return OTCBizPool.getInstance().virtualRecordBiz.saveVirtualRecord(record);
    }
}
