package com.otc.api.console.ctrl.user; 

import com.base.BaseSpringTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.console.ctrl.user.req.UserAdressListReq;
import com.otc.api.console.ctrl.user.req.UserListReq;
import com.otc.api.console.utils.DateUtil;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.core.cache.CacheHelper;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/** 
* UserAdressConsoleCtrl Tester. 
* 
* @author <Authors name> 
* @since <pre>五月 2, 2017</pre> 
* @version 1.0 
*/ 
public class UserAdressConsoleCtrlTest extends BaseSpringTest{

    @Autowired
    private UserAdressConsoleCtrl userAdressConsoleCtrl;

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getUserAddressListByConditionPage(@PathVariable String queryJson) 
* 
*/ 
@Test
public void testGetUserAddressListByConditionPage() throws Exception {
    UserAdressListReq req = new UserAdressListReq();
    req.setToken(CacheHelper.buildTestToken("123"));
    Date end = new Date();
    Date start = DateUtil.addDay(end,-10);
    req.setUserInfo("冯干齐");
    req.setDateFilter(start,end);
    req.setPageFilter(1,10);
    req.setCoinId(1L);
    String query = JsonHelper.obj2JsonStr(req);
    LOG.d(this,req);
    WebApiResponse resp =  this.userAdressConsoleCtrl.getUserAddressListByConditionPage(query);
    LOG.d(this,resp);
}


    @Override
    public void doTest() {

    }
}
