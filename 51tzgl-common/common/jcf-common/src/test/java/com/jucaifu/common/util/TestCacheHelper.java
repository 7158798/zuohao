package com.jucaifu.common.util;

import com.base.BaseTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.cache.GlobalCacheHelper;
import com.jucaifu.common.util.cache.UnitCacheHelper;

/**
 * TestCacheHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/17.
 */
public class TestCacheHelper extends BaseTest {


    @Override
    public void doTest() {
        GlobalCacheHelper.getInstance().put("name", "scofieldcai");
        LOG.d(this, GlobalCacheHelper.getInstance().get("name"));

        String[] array = new String[]{"iphon6s", "iphon6"};
        GlobalCacheHelper.getInstance().put("array", array);
        LOG.d(this, GlobalCacheHelper.getInstance().get("array"));


        UnitCacheHelper unitCacheHelper = new UnitCacheHelper();
        unitCacheHelper.put("k1","v1");
        LOG.d(this, unitCacheHelper.get("k1"));
    }
}
