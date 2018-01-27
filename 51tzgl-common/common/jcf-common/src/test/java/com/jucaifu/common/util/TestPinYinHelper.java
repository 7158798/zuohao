package com.jucaifu.common.util;

import com.base.BaseTest;
import com.jucaifu.common.log.LOG;

/**
 * Created by scofieldcai-dev on 15/9/2.
 */
public class TestPinYinHelper extends BaseTest {

    @Override
    public void doTest() {
        LOG.d(this, PinYinHelper.str2Pinyin("我是最棒的!", " "));
        LOG.d(this, PinYinHelper.strFirst2Pinyin("我是最棒的!"));
    }
}
