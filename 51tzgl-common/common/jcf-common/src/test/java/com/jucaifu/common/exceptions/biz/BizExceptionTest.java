package com.jucaifu.common.exceptions.biz;

import com.base.BaseTest;
import com.jucaifu.common.log.LOG;
import org.junit.Test;

/** 
* BizException Tester. 
* 
* @author scofieldcai-dev 
* @since  十一月 16, 2015 
* @version 1.0 
*/ 
public class BizExceptionTest extends BaseTest {

    @Override
    public void doTest() {
        //TODO: Test goes here...
    } 

    /** 
     * 
     * Method: getMsg() 
     * 
     */ 
    @Test
    public void testGetMsg() throws Exception { 
        //TODO: Test goes here... 
    } 

    /** 
     * 
     * Method: getCode() 
     * 
     */ 
    @Test
    public void testGetCode() throws Exception { 
        //TODO: Test goes here... 
    } 

    /** 
     * 
     * Method: newInstance(Class<T> tClazz, Integer code, String msgFormat, Object... args) 
     * 
     */ 
    @Test
    public void testNewInstance() throws Exception {

        BizException ex  = BizException.newInstance(BizException.class, 10001002, "测试内容====");
        LOG.d(this,ex);

//        RpcException rpcEx  = BizException.newInstance(RpcException.class, 10001001, "错误测试内容:%s", new Object[]{"=====message======"});
//        LOG.d(this,rpcEx);
    }

    /**
     *
     * Method: lock() 
     *
     */
    @Test
    public void testLock() throws Exception {
        //TODO: Test goes here... 
    }

    /**
     * Method: println(Object var1)
     */
    @Test
    public void testPrintln() throws Exception {
        //TODO: Test goes here... 
    } 




} 
