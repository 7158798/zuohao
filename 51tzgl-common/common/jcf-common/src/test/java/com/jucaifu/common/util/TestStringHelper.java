package com.jucaifu.common.util;

import com.base.BaseTest;
import com.jucaifu.common.log.LOG;

/**
 * Created by scofieldcai-dev on 15/9/1.
 */
public class TestStringHelper extends BaseTest {

    @Override
    public void doTest() {

        String str = StringHelper.mergeMutableArgs(":", "aaa", "bbb", "ccc");
        LOG.d(this, str);


        LOG.d(this, StringHelper.isChinese("123"));
        LOG.d(this, StringHelper.isChinese("测试"));

        LOG.d(this, StringHelper.isInteger("123"));
        LOG.d(this, StringHelper.isInteger("测试"));

        LOG.d(this, StringHelper.isDouble("123"));
        LOG.d(this, StringHelper.isDouble("测试"));

        LOG.d(this, StringHelper.isNumeric("123"));
        LOG.d(this, StringHelper.isNumeric("测试"));

    }
}
