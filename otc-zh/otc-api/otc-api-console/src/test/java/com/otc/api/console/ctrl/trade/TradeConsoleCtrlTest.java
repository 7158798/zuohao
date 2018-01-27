package com.otc.api.console.ctrl.trade; 

import com.base.BaseSpringTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.console.ctrl.trade.req.TradeListReq;
import com.otc.api.console.ctrl.trade.req.TradeReq;
import com.otc.api.console.utils.DateUtil;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.CacheHelper;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/** 
* TradeConsoleCtrl Tester. 
* 
* @author <Authors name> 
* @since <pre>五月 15, 2017</pre> 
* @version 1.0 
*/ 
public class TradeConsoleCtrlTest extends BaseSpringTest{


    @Autowired
    private TradeConsoleCtrl tradeConsoleCtrl;

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getTradeListByConditionPage(@PathVariable String queryJson) 
* 
*/ 
@Test
public void testGetTradeListByConditionPage() throws Exception { 
//TODO: Test goes here... 
}

    //申诉中
    @Test
    public void testGetUserListByConditionPage() throws Exception {
        TradeListReq req = new TradeListReq();
        req.setToken(CacheHelper.buildTestToken("123"));
        Date end = new Date();
//        Date start = DateUtil.addDay(end, -10);
//        req.setDateFilter(start, end);
        req.setPageFilter(1, 10);
        req.setType(3);
//        req.setUserInfo("33");
//        req.setStatus(1);
        LOG.d(this,req);
        String query = JsonHelper.obj2JsonStr(req);
        WebApiResponse resp = this.tradeConsoleCtrl.getTradeListByConditionPage(query);
        LOG.d(this, resp);
    }

    //已完成
    @Test
    public void test2() throws Exception {
        TradeListReq req = new TradeListReq();
        req.setToken(CacheHelper.buildTestToken("123"));
        Date end = new Date();
        Date start = DateUtil.addDay(end, -10);
        //req.setDateFilter(start, end);
        req.setPageFilter(1, 10);
        req.setType(4);
        req.setUserInfo("豆胜伟");
        // req.setStatus(1);
        LOG.d(this,req);
        String query = JsonHelper.obj2JsonStr(req);
        WebApiResponse resp = this.tradeConsoleCtrl.getTradeListByConditionPage(query);
        LOG.d(this, resp);
    }


    @Test
    public void test3() throws Exception{
        TradeReq req = new TradeReq();
        req.setToken(CacheHelper.buildTestToken("1"));
        req.setTradeId(24L);
        req.setRemark("123");
        req.setConfirmPwd("qqq");
        LOG.d(this,req);
        this.tradeConsoleCtrl.adminConfirm(req);
    }

    @Test
    public void test4() throws Exception{
        TradeReq req = new TradeReq();
        String s = CacheHelper.buildTestToken("1");
        req.setToken(CacheHelper.buildTestToken("1"));
        req.setTradeId(24L);
        req.setRemark("444");
        req.setConfirmPwd("qqq");
        LOG.d(this,req);
        this.tradeConsoleCtrl.adminCancel(req);
    }


    @Test
    public void test5() throws Exception{
        TradeReq req = new TradeReq();
        req.setToken(CacheHelper.buildTestToken("1"));
        req.setTradeId(24L);
        req.setRemark("123");
        req.setConfirmPwd("qqq");
        LOG.d(this,req);
        this.tradeConsoleCtrl.adminConfirmForRun(req);
    }



    @Test
    public void test7()throws Exception{
        WebApiBaseReq req = new WebApiBaseReq();
        req.setId(24l);
        req.setToken(CacheHelper.buildTestToken("1"));
        // req.setStatus(1);
        LOG.d(this,req);
        String query = JsonHelper.obj2JsonStr(req);
        WebApiResponse resp = this.tradeConsoleCtrl.getTradeJudgeDetail(query);
        LOG.d(this, resp);
    }


    @Override
    public void doTest() {

    }
}
