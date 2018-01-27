package com.otc.ctrl;

import com.base.BaseSpringTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.console.ctrl.user.UserKycConsoleCtrl;
import com.otc.api.console.ctrl.user.req.UserKycAuditingReq;
import com.otc.api.console.ctrl.user.req.UserListReq;
import com.otc.api.console.utils.DateUtil;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.core.cache.CacheHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/** 
* UserKycConsoleCtrl Tester. 
* 
* @author <Authors name> 
* @since <pre>四月 28, 2017</pre> 
* @version 1.0 
*/ 
public class UserKycConsoleCtrlTest extends BaseSpringTest{

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
}

@Autowired
private UserKycConsoleCtrl userKycConsoleCtrl;

/** 
* 
* Method: getUserListByConditionPage(@PathVariable String queryJson) 
* 
*/ 
@Test
public void testGetUserListByConditionPage() throws Exception {
    UserListReq req = new UserListReq();
    req.setToken(CacheHelper.buildTestToken("123"));
    Date end = new Date();
    Date start = DateUtil.addDay(end,-10);
    req.setDateFilter(start,end);
    req.setPageFilter(1,10);
    req.setStatus("00");
    String query = JsonHelper.obj2JsonStr(req);
    WebApiResponse resp =  this.userKycConsoleCtrl.getUserKycListByConditionPage(query);
    LOG.d(this,resp);
}


@Test
public void test1() throws Exception{
    UserKycAuditingReq req = new UserKycAuditingReq();
    req.setId(3L);
    req.setToken(CacheHelper.buildTestToken("1"));
    this.userKycConsoleCtrl.passUserKyc(req);
}

    @Test
    public void test2() throws Exception{
        UserKycAuditingReq req = new UserKycAuditingReq();
        req.setId(3L);
        req.setRejectReason("22222");
        req.setToken(CacheHelper.buildTestToken("2"));
        this.userKycConsoleCtrl.noPassUserKyc(req);
    }

    @Override
    public void doTest() {

    }
}
