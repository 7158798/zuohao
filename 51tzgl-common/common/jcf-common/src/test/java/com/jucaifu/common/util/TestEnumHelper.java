package com.jucaifu.common.util;

import java.util.List;
import java.util.Map;

import com.base.BaseTest;
import com.jucaifu.common.enums.EnumExceptionLevel;
import com.jucaifu.common.enums.EnumMonth;
import com.jucaifu.common.log.LOG;

/**
 * Created by scofieldcai-dev on 15/8/31.
 */
public class TestEnumHelper extends BaseTest {

    //    @Override
    public void doTest() {

        Map<Integer, EnumMonth> monthMap = EnumHelper.buildIEnumMap(EnumMonth.class);
        LOG.d(this, monthMap);

        Map<Integer, EnumExceptionLevel> exceptionLevelMap = EnumHelper.buildIEnumMap(EnumExceptionLevel.class);
        LOG.d(this, exceptionLevelMap);

        List<String> descValueList = EnumHelper.extractEnumPropValueList(EnumMonth.class, "desc");
        LOG.d(this, descValueList);

        List<Integer> valueList = EnumHelper.extractEnumPropValueList(EnumMonth.class, "value");
        LOG.d(this, valueList);
    }
}
