package com.jucaifu.test;

import com.base.BaseSpringTest;
import com.jucaifu.common.log.LOG;
import org.junit.After;
import org.junit.Before;

/** 
* TestHelper Tester. 
* 
* @author scofieldcai-dev 
* @since  十二月 20, 2015 
* @version 1.0 
*/ 
public class TestHelperTest extends BaseSpringTest{ 

    @Before
    public void before() throws Exception { 
        super.after();
    } 

    @After
    public void after() throws Exception {
        super.after(); 
    } 
    
    @Override
    public void doTest() {
        LOG.dTag(this,"doTest");
    } 




} 
