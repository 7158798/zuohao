package com.jucaifu.common.util;

import java.util.ArrayList;

import com.base.BaseTest;
import com.jucaifu.common.log.LOG;

/**
 * Created by scofieldcai-dev on 15/9/1.
 */
public class TestValueHelper extends BaseTest {

    @Override
    public void doTest() {

        LOG.d(this, ValueHelper.checkStringIsEmpty(""));
        LOG.d(this, ValueHelper.checkStringIsEmpty("1"));

        LOG.d(this, ValueHelper.checkListIsEmpty(new ArrayList<String>()));

    }
}
