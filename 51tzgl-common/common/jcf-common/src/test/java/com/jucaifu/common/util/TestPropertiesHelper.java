package com.jucaifu.common.util;

import java.util.Properties;

import com.base.BaseTest;
import com.jucaifu.common.log.LOG;

/**
 * Created by scofieldcai-dev on 15/8/29.
 */
public class TestPropertiesHelper extends BaseTest {


    @Override
    public void doTest() {

        PropertiesHelper.getInstance().printAll("log4j");
        PropertiesHelper.getInstance().printAll("log4j.properties");

        Properties properties = PropertiesHelper.getInstance().load("log4j.properties");
        String value = (String) properties.get("log4j.rootLogger");
        LOG.d(this, value);

    }
}
