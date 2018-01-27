package com.otc.ctrl;

import com.base.BaseSpringTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.web.ctrl.user.UserAuWebCtrl;
import com.otc.api.web.ctrl.user.request.UserKycPostReq;
import com.otc.api.web.ctrl.user.request.UserRealNameReq;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.CacheHelper;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

/** 
* UserAuWebCtrl Tester. 
* 
* @author <Authors name> 
* @since <pre>四月 25, 2017</pre> 
* @version 1.0 
*/ 
public class UserAuWebCtrlTest extends BaseSpringTest{

    @Autowired
    private UserAuWebCtrl userAuWebCtrl;

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: realName(@RequestBody UserRealNameReq req) 
* 
*/ 
@Test
public void testRealName() throws Exception {
    UserRealNameReq req = new UserRealNameReq();
    req.setToken(CacheHelper.buildTestToken("33"));
    req.setIdCardNumber("341124198704253017");
    req.setIdCardType("01");
    req.setRealName("冯干齐");
    LOG.d(this,req);
    userAuWebCtrl.realName(req);
} 

/** 
* 
* Method: kycPost(@RequestBody UserKycPostReq req) 
* 
*/ 
@Test
public void testKycPost() throws Exception {
    UserKycPostReq req = new UserKycPostReq();
    req.setToken(CacheHelper.buildTestToken("33"));
    req.setIdentityurlOn("123");
    req.setIdentityurlOff("456");
    req.setIdentityurlHold("789");
    LOG.d(this,req);
    userAuWebCtrl.kycPost(req);
}

@Test
public void testRealInfo() throws Exception{
    WebApiBaseReq req = new WebApiBaseReq();
    req.setToken(CacheHelper.buildTestToken("65"));
    String query = JsonHelper.obj2JsonStr(req);
    WebApiResponse resp = userAuWebCtrl.getRealNameInfo(query);
    LOG.d(this,resp);
}
    @Override
    public void doTest() {

    }
}
