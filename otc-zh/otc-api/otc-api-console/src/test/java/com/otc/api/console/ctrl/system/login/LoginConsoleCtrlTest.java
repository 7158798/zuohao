package com.otc.api.console.ctrl.system.login;

import com.base.BaseSpringTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.console.ctrl.system.login.request.LoginReq;
import com.otc.common.api.packet.web.WebApiResponse;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

/** 
* LoginConsoleCtrl Tester. 
* 
* @author <Authors name> 
* @since <pre>Apr 28, 2017</pre> 
* @version 1.0 
*/ 
public class LoginConsoleCtrlTest extends BaseSpringTest {

    @Autowired
    private LoginConsoleCtrl loginConsoleCtrl;
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
* Method: login(@RequestBody LoginReq req) 
* 
*/ 
@Test
public void testLogin() throws Exception { 
//TODO: Test goes here...
    LoginReq req = new LoginReq();
    req.setUserName("liuxun");
    req.setPassword("qqq");
    String str = JsonHelper.obj2JsonStr(req);
    System.out.println(str);
    WebApiResponse response = loginConsoleCtrl.login(req);
    LOG.d(this,response);
}

/** 
* 
* Method: logout(@PathVariable String queryJson) 
* 
*/ 
@Test
public void testLogout() throws Exception { 
//TODO: Test goes here...

} 


} 
