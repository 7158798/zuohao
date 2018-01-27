package com.otc.service.sys.impl.console;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.sys.pojo.po.Resource;
import com.otc.facade.sys.service.console.ResourceConsoleFacade;
import com.otc.pool.OTCBizPool;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lx on 17-4-28.
 */
@Component
@Service
public class ResourceConsoleFacadeImpl implements ResourceConsoleFacade {

    @Override
    public List<Resource> queryResourceByEmpId(Long empId) {
        return OTCBizPool.getInstance().resourceBiz.queryResourceByEmpId(empId);
    }

    @Override
    public List<Resource> queryAll() {
        return OTCBizPool.getInstance().resourceBiz.selectAll();
    }
}
