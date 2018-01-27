package com.jucaifu.common.util;

import com.base.BaseTest;
import com.jucaifu.common.enums.EnumHttpStatusCode;
import com.jucaifu.common.log.LOG;

/**
 * Created by scofieldcai-dev on 15/9/2.
 */
public class TestHttpStatusCode extends BaseTest {

    @Override
    public void doTest() {
        LOG.d(this, EnumHttpStatusCode.getByStatusCode(201));
    }
}
