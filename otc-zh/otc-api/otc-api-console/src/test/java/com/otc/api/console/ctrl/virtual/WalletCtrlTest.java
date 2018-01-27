package com.otc.api.console.ctrl.virtual;

import com.base.BaseSpringTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.console.ctrl.virtual.request.WalletReq;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.core.cache.CacheHelper;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

/** 
* WalletCtrl Tester. 
* 
* @author <Authors name> 
* @since <pre>May 2, 2017</pre> 
* @version 1.0 
*/ 
public class WalletCtrlTest extends BaseSpringTest {

    @Autowired
    private WalletCtrl walletCtrl;
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
* Method: getWalletList(@PathVariable String queryJson) 
* 
*/ 
@Test
public void testGetWalletList() throws Exception { 
//TODO: Test goes here...
    WalletReq req = new WalletReq();
    req.setCoinId(1l);
    req.setCondition("冯干齐");
    req.setPageFilter(1,10);
    String token = CacheHelper.buildTestToken("1");
    req.setToken(token);
    LOG.d(this, req);
    String reqStr = JsonHelper.obj2JsonStr(req);
    WebApiResponse response = walletCtrl.getWalletList(reqStr);
    LOG.d(this,response);
} 


} 
