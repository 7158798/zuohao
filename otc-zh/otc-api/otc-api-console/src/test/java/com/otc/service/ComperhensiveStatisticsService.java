package com.otc.service;

import com.base.BaseSpringTest;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.console.service.ConsoleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by zygong on 17-5-15.
 */
public class ComperhensiveStatisticsService extends BaseSpringTest {

    @Autowired
    private ConsoleService service;

    @Test
    public void test1(){
//        List<CountVo> countVos = service.userConsoleFacade.countUser(new Date());
//        System.out.println(JsonHelper.obj2JsonStr(countVos));
        Map<String, Object> stringObjectMap = service.comprehensiveStatisticsConsoleFacade.totalCount();
        System.out.println(JsonHelper.obj2JsonStr(stringObjectMap));
    }

    @Override
    public void doTest() {

    }
}
