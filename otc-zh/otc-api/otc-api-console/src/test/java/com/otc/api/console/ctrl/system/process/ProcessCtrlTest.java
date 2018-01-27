package com.otc.api.console.ctrl.system.process;

import com.base.BaseSpringTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.console.ctrl.system.process.request.ProcessDetailReq;
import com.otc.api.console.ctrl.system.process.request.ProcessReq;
import com.otc.api.console.ctrl.system.process.request.StatusListReq;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.CacheHelper;
import com.otc.core.cache.UserCacheManager;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

/** 
* ProcessCtrl Tester. 
* 
* @author <Authors name> 
* @since <pre>May 8, 2017</pre> 
* @version 1.0 
*/ 
public class ProcessCtrlTest extends BaseSpringTest {

    @Autowired
    private ProcessCtrl processCtrl;

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
}


    @Override
    public void doTest() {

    }

    /**
* 
* Method: getProcessList(@PathVariable String queryJson) 
* 
*/ 
@Test
public void testGetProcessList() throws Exception { 
//TODO: Test goes here...
    WebApiBaseReq req = new WebApiBaseReq();
    req.setPageFilter(1,3);
    String token = CacheHelper.buildTestToken("1");
    req.setToken(token);
    LOG.d(this, req);
    String reqStr = JsonHelper.obj2JsonStr(req);
    WebApiResponse response = processCtrl.getProcessList(reqStr);
    LOG.d(this,response);
}

/** 
* 
* Method: updateProcess(ProcessReq req) 
* 
*/ 
@Test
public void testUpdateProcess() throws Exception { 
//TODO: Test goes here...
   /**/ ProcessReq req = new ProcessReq();
    req.setRoleId1(10l);
    req.setRoleId2(10l);
    req.setRoleId3(10l);
    req.setIsNeedPwd(Boolean.FALSE);
    req.setId(2l);
    //String token = CacheHelper.buildTestToken("1");
    req.setToken("750cc997-a47f-4ff7-9668-b0e316d1b044");
    LOG.d(this, req);
    WebApiResponse response = processCtrl.updateProcess(req);
    LOG.d(this,response);

    //750cc997-a47f-4ff7-9668-b0e316d1b044
    /*Long uid = UserCacheManager.getUidWithToken("750cc997-a47f-4ff7-9668-b0e316d1b044");
    LOG.d(this,"获取到的用户id 等于：　" + uid);*/

}

    @Test
    public void testGetDetail() throws Exception {
        ProcessDetailReq req = new ProcessDetailReq();
        req.setType("01");
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        LOG.d(this, req);
        String reqStr = JsonHelper.obj2JsonStr(req);
        WebApiResponse response = processCtrl.getProcessDetail(reqStr);
        LOG.d(this,response);
    }


    @Test
    public void test1() throws Exception{
        StatusListReq req = new StatusListReq();
        req.setToken(CacheHelper.buildTestToken("1"));
        req.setType(6);
        LOG.d(this, req);
        String reqStr = JsonHelper.obj2JsonStr(req);
        WebApiResponse response = processCtrl.getTradeStatusList(reqStr);
        LOG.d(this,response);
        req.setType(5);

    }



} 
