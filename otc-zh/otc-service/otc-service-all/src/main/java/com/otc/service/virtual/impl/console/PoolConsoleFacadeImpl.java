package com.otc.service.virtual.impl.console;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.virtual.pojo.vo.PoolVo;
import com.otc.facade.virtual.service.console.PoolConsoleFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.virtual.impl.PoolFacadeImpl;
import org.springframework.stereotype.Component;

/**
 * Created by lx on 17-4-19.
 */
@Component
@Service
public class PoolConsoleFacadeImpl extends PoolFacadeImpl implements PoolConsoleFacade {

    @Override
    public PoolVo queryCountByConditionPage(PoolVo vo) {
        return OTCBizPool.getInstance().poolBiz.queryCountByConditionPage(vo);
    }
}
