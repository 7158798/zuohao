package com.otc.service;

import com.base.BaseSpringTest;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.console.service.ConsoleService;
import com.otc.facade.base.CountVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by zygong on 17-5-12.
 */
public class UserServiceTest extends BaseSpringTest {

    @Autowired
    private ConsoleService service;

    @Test
    public void countUserTest(){
        List<CountVo> countVos = service.userConsoleFacade.countUser();
        System.out.println(JsonHelper.obj2JsonStr(countVos));
    }

    @Override
    public void doTest() {

    }
}
