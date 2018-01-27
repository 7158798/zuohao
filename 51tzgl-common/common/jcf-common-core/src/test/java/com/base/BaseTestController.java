package com.base;

import com.jucaifu.common.configs.GlobalConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * BaseTestController
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/8/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = GlobalConfig.SPRING_XML_SCAN_EXPRESSION)
public abstract class BaseTestController {

    @Test
    public abstract void doTest();

}
