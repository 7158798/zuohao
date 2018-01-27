package com.otc.api.console.ctrl.system.role;

import com.base.BaseSpringTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.console.ctrl.system.role.request.RoleCondReq;
import com.otc.api.console.ctrl.system.role.request.RoleReq;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.CacheHelper;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/** 
* RoleCtrl Tester. 
* 
* @author <Authors name> 
* @since <pre>Apr 28, 2017</pre> 
* @version 1.0 
*/ 
public class RoleCtrlTest extends BaseSpringTest {

   @Autowired
   private RoleCtrl roleCtrl;
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
* Method: getRoleList(@PathVariable String queryJson) 
* 
*/ 
@Test
public void testGetRoleList() throws Exception { 
//TODO: Test goes here...
   RoleCondReq req = new RoleCondReq();
   req.setPageFilter(1,3);
   req.setIsPage(Boolean.FALSE);
   String token = CacheHelper.buildTestToken("1");
   req.setToken(token);
   LOG.d(this, req);
   String reqStr = JsonHelper.obj2JsonStr(req);
   WebApiResponse response = roleCtrl.getRoleList(reqStr);
   LOG.d(this,response);
} 

/** 
* 
* Method: addRole(RoleReq req) 
* 
*/ 
@Test
public void testAddRole() throws Exception { 
//TODO: Test goes here...
   RoleReq req = new RoleReq();
   req.setName("管理员2");
   req.setDescription("管理员权限2");
   String token = CacheHelper.buildTestToken("1");
   req.setToken(token);
   List<Long> list = new ArrayList<>();
   list.add(1L);
   req.setResourceIds(list);
   LOG.d(this,req);
   WebApiResponse response = roleCtrl.addRole(req);
   LOG.d(this,response);
}


   /**
    *
    * Method: detailRole
    *
    */
   @Test
   public void testDetailRole() throws Exception {
//TODO: Test goes here...
      WebApiBaseReq req = new WebApiBaseReq();
      String token = CacheHelper.buildTestToken("1");
      req.setId(9l);
      req.setToken(token);
      LOG.d(this,req);
      String reqStr = JsonHelper.obj2JsonStr(req);
      WebApiResponse response = roleCtrl.detailRole(reqStr);
      LOG.d(this,response);
   }

   /**
    *
    * Method: getPermissionList
    *
    */
   @Test
   public void testGetPermissionList() throws Exception {
//TODO: Test goes here...
      WebApiBaseReq req = new WebApiBaseReq();
      String token = CacheHelper.buildTestToken("1");
      req.setToken(token);
      LOG.d(this,req);
      String reqStr = JsonHelper.obj2JsonStr(req);
      WebApiResponse response = roleCtrl.getPermissionList(reqStr);
      LOG.d(this,response);
   }

/** 
* 
* Method: updateRole(RoleReq req) 
* 
*/ 
@Test
public void testUpdateRole() throws Exception { 
//TODO: Test goes here...
   RoleReq req = new RoleReq();
   req.setRoleId(9l);
   req.setName("管理员");
   req.setDescription("sssss");
   String token = CacheHelper.buildTestToken("1");
   req.setToken(token);
   List<Long> list = new ArrayList<>();
   list.add(1L);
   list.add(2l);
   req.setResourceIds(list);
   LOG.d(this,req);
   WebApiResponse response = roleCtrl.updateRole(req);
   LOG.d(this,response);
} 


/** 
* 
* Method: filter(RoleReq req) 
* 
*/ 
@Test
public void testFilter() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = RoleCtrl.getClass().getMethod("filter", RoleReq.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
