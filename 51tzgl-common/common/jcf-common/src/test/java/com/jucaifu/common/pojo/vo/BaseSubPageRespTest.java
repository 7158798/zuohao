package com.jucaifu.common.pojo.vo;

import java.util.ArrayList;

import com.base.BaseTest;
import com.jucaifu.common.log.LOG;
import org.junit.Test;

/** 
* BaseSubPageResp Tester. 
* 
* @author scofieldcai-dev 
* @since  十二月 3, 2015 
* @version 1.0 
*/ 
public class BaseSubPageRespTest extends BaseTest {


    @Override
    public void doTest() {

        BaseSubPageResp subPageResp = new BaseSubPageResp<String>();
        BasePageVo basePageVo = new BasePageVo();

        ArrayList list = new ArrayList();
        for (int i = 0;i<2;i++) {
            list.add("" + i + i + i);
        }
        subPageResp.updatePage(basePageVo, list);

        LOG.d(this,subPageResp);
    } 

    /** 
     * 
     * Method: updatePage(BaseVo baseVo, List<Result> list) 
     * 
     */ 
    @Test
    public void testUpdatePage() throws Exception { 
        //TODO: Test goes here... 
    } 

    /** 
     * 
     * Method: getPage() 
     * 
     */ 
    @Test
    public void testGetPage() throws Exception { 
        //TODO: Test goes here... 
    } 




} 
