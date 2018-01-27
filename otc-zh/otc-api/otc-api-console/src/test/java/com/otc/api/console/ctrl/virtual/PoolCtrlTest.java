package com.otc.api.console.ctrl.virtual;

import com.base.BaseSpringTest;
import com.base.wallet.utils.IWalletUtil;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.console.ctrl.virtual.request.PoolReq;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.core.cache.CacheHelper;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

/** 
* PoolCtrl Tester. 
* 
* @author <Authors name> 
* @since <pre>Apr 28, 2017</pre> 
* @version 1.0 
*/ 
public class PoolCtrlTest extends BaseSpringTest {
    @Autowired
    private PoolCtrl poolCtrl;

    @Override
    public void doTest() {

    }

    @Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getUsedCount(@PathVariable String queryJson) 
* 
*/ 
@Test
public void testGetUsedCount() throws Exception { 
//TODO: Test goes here...
    /*PoolReq req = new PoolReq();
    req.setCoinId(1l);
    String token = CacheHelper.buildTestToken("1");
    req.setToken(token);
    req.setPageFilter(1,3);
    String str = JsonHelper.obj2JsonStr(req);
    WebApiResponse response = poolCtrl.getUsedCount(str);
    LOG.d(this,response);*/
    IWalletUtil iWalletUtil = CacheHelper.getObj("WITHDRAW_WALLET_UTIL_KEY_499");

    LOG.d(this,iWalletUtil);/**/
} 


} 
