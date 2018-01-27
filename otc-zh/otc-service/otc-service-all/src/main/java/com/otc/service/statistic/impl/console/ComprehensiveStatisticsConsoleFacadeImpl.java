package com.otc.service.statistic.impl.console;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.statistic.service.console.ComprehensiveStatisticsConsoleFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.statistic.impl.ComprehensiveStatisticsFacadeImpl;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zygong on 17-5-15.
 */
@Component
@Service
public class ComprehensiveStatisticsConsoleFacadeImpl extends ComprehensiveStatisticsFacadeImpl implements ComprehensiveStatisticsConsoleFacade {

    @Override
    public Map<String, Object> totalCount() {
        return OTCBizPool.getInstance().comprehensiveStatisticsBiz.totalCount();
    }
}
