package com.szzc.base;

import com.jucaifu.common.configs.GlobalConfig;
import com.jucaifu.common.log.LOG;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * BaseSpringTest
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/13.
 */
@ContextConfiguration(locations = GlobalConfig.SPRING_XML_SCAN_EXPRESSION)
public abstract class BaseSpringTest extends AbstractJUnit4SpringContextTests {

    @Test
    public abstract void doTest();

    public <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
    }

    public <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    protected ApplicationContext getContext() {
        return applicationContext;
    }


    public void printAllBeans() {

        LOG.dStart(this, "printAllBeans");

        String[] allBeans = getContext().getBeanDefinitionNames();

        LOG.d(this, "allBeans count=" + allBeans.length);

        for (String beanName : allBeans) {
            System.out.println(beanName);
        }

        LOG.dEnd(this, "printAllBeans");
    }
}
