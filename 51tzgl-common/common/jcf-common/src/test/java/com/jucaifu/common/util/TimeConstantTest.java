package com.jucaifu.common.util;

import com.base.BaseTest;
import com.jucaifu.common.constants.TimeConstant;

/** 
* TimeConstantTest Tester.
* 
* @author scofieldcai-dev 
* @since  十一月 19, 2015 
* @version 1.0 
*/ 
public class TimeConstantTest extends BaseTest {

    @Override
    public void doTest() {

        try {
            ReflectHelper.printAllStaticFieldValue(this, TimeConstant.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


} 
