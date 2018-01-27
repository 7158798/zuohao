package com.otc.facade.statistic.service.console;

import com.otc.facade.statistic.service.ComprehensiveStatisticsFacade;

import java.util.Map;

/**
 * Created by zygong on 17-5-15.
 */
public interface ComprehensiveStatisticsConsoleFacade extends ComprehensiveStatisticsFacade {
    /**
     * 综合统计
     * @return
     */
    Map<String, Object> totalCount();
}
