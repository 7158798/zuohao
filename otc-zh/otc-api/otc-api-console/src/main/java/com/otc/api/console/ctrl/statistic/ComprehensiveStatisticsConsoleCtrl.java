package com.otc.api.console.ctrl.statistic;

import com.jucaifu.common.annotation.token.TokenValidateAnno;
import com.otc.api.console.base.BaseConsoleCtrl;
import com.otc.api.console.constants.ConsoleApiComprehensiveStatistics;
import com.otc.common.api.packet.web.WebApiResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by zygong on 17-5-15.
 */
@RestController
public class ComprehensiveStatisticsConsoleCtrl extends BaseConsoleCtrl implements ConsoleApiComprehensiveStatistics {

    /**
     * 综合统计
     * @return
     */
    @ResponseBody
    @RequestMapping(value = ALLCOUNT_INTEGRATION_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse totalCount(){
        Map<String, Object> stringObjectMap = otc.comprehensiveStatisticsConsoleFacade.totalCount();
        return buildWebApiResponse(SUCCESS, stringObjectMap);
    }
}
