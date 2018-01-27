package com.otc.ctrl;

import com.base.BaseSpringTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.console.ctrl.user.UserConsoleCtrl;
import com.otc.api.console.ctrl.user.req.UserListReq;
import com.otc.api.console.ctrl.user.req.UserOperationListReq;
import com.otc.api.console.utils.DateUtil;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.CacheHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * UserConsoleCtrl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>四月 27, 2017</pre>
 */
public class UserConsoleCtrlTest extends BaseSpringTest {

    @Autowired
    private UserConsoleCtrl userConsoleCtrl;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getUserOrderListByConditionPage(@PathVariable String queryJson)
     */
    @Test
    public void testGetUserListByConditionPage() throws Exception {
        UserListReq req = new UserListReq();
        req.setToken(CacheHelper.buildTestToken("123"));
     //   Date end = new Date();
      //  Date start = DateUtil.addDay(end, -10);
       // req.setDateFilter(start, end);
        req.setPageFilter(4, 10);
        req.setStatus("02");
      //  req.setUserInfo("31");
        String query = JsonHelper.obj2JsonStr(req);
        WebApiResponse resp = this.userConsoleCtrl.getUserListByConditionPage(query);
        LOG.d(this, resp);
    }


    @Test
    public void testGetUserOperationListByConditionPage() throws Exception {
        UserOperationListReq req = new UserOperationListReq();
        req.setToken(CacheHelper.buildTestToken("123"));
        Date end = new Date();
        Date start = DateUtil.addDay(end, -10);
        req.setDateFilter(start, end);
        req.setPageFilter(1, 10);
        req.setOperationType("04");
        req.setUserInfo("33");
        String query = JsonHelper.obj2JsonStr(req);
        WebApiResponse resp = this.userConsoleCtrl.getUserOperationListByConditionPage(query);
        LOG.d(this, resp);
    }

    @Test
    public void test1() throws Exception {
        WebApiBaseReq req = new WebApiBaseReq();
        req.setId(31L);
        req.setToken(CacheHelper.buildTestToken("1"));
        LOG.d(this, req);
        this.userConsoleCtrl.forbiddenUser(req);
    }

    @Test
    public void test2() throws Exception {
        WebApiBaseReq req = new WebApiBaseReq();
        req.setId(31L);
        req.setToken(CacheHelper.buildTestToken("1"));
        this.userConsoleCtrl.relieveUser(req);
    }

    @Test
    public void test3() throws Exception {
        WebApiBaseReq req = new WebApiBaseReq();
        req.setId(31L);
        req.setToken(CacheHelper.buildTestToken("1"));
        this.userConsoleCtrl.resetUserPwd(req);
    }

    @Test
    public void test4() throws Exception{
        UserListReq req = new UserListReq();
        req.setToken(CacheHelper.buildTestToken("1"));
        Date end = new Date();
        Date start = DateUtil.addDay(end, -10);
        req.setDateFilter(start, end);
        req.setPageFilter(1, 10);
        req.setUserInfo("33");
        String query = JsonHelper.obj2JsonStr(req);

        WebApiResponse response = this.userConsoleCtrl.getUserAssetListByConditionPage(query);
        LOG.d(this,response);
    }


    /**
     * Method: getUserStatus(String status)
     */
    @Test
    public void testGetUserStatus() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = UserConsoleCtrl.getClass().getMethod("getUserStatus", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getKycStatus(String status)
     */
    @Test
    public void testGetKycStatus() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = UserConsoleCtrl.getClass().getMethod("getKycStatus", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    @Override
    public void doTest() {

    }
}
