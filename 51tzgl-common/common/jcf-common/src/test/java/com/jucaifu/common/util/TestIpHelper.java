package com.jucaifu.common.util;

import com.base.BaseTest;
import com.jucaifu.common.log.LOG;

/**
 * TestIpHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/10.
 */
public class TestIpHelper extends BaseTest {

    @Override
    public void doTest() {

        LOG.d(this, "本机ip地址:" + IpHelper.getLocalIp());
    }
}
