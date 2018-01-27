package com.otc.api.console.ctrl.system.employee;

import com.base.BaseSpringTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.console.ctrl.system.employee.request.*;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.core.cache.CacheHelper;
import com.otc.facade.sys.enums.EmployeeStatus;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/** 
* EmployeeConsoleCtrl Tester. 
* 
* @author <Authors name> 
* @since <pre>Apr 28, 2017</pre> 
* @version 1.0 
*/ 
public class EmployeeConsoleCtrlTest extends BaseSpringTest {

    @Autowired
    private EmployeeConsoleCtrl employeeConsoleCtrl;
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
* Method: addEmployee(@RequestBody @Valid EmployeeAddReq req) 
* 
*/ 
@Test
public void testAddEmployee() throws Exception { 
//TODO: Test goes here...
    EmployeeAddReq req = new EmployeeAddReq();
    req.setLoginName("liuxun");
    req.setEmployeeName("刘勋");
    req.setContactNumber("13509899999");
    req.setPassword("jcf123456");
    String token = CacheHelper.buildTestToken("1");
    req.setToken(token);
    req.setStatus(EmployeeStatus.ENABLED.getCode());
    WebApiResponse response = employeeConsoleCtrl.addEmployee(req);
    LOG.d(this,response);
} 

/** 
* 
* Method: getEmployeeList(@PathVariable String queryJson) 
* 
*/ 
@Test
public void testGetEmployeeList() throws Exception { 
//TODO: Test goes here...
    EmployeeListReq req = new EmployeeListReq();
    req.setPageFilter(1,10);
    String token = CacheHelper.buildTestToken("1");
    req.setToken(token);
    String str = JsonHelper.obj2JsonStr(req);
    employeeConsoleCtrl.getEmployeeList(str);
} 

/** 
* 
* Method: EditEmployee(@RequestBody @Valid EmployeeEditReq req) 
* 
*/ 
@Test
public void testEditEmployee() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: switchEmployee(@RequestBody EmployeeHandleReq req) 
* 
*/ 
@Test
public void testSwitchEmployee() throws Exception { 
//TODO: Test goes here...
    EmployeeHandleReq req = new EmployeeHandleReq();
    req.setEmployeeId(1l);
    req.setStatus("01");
    String token = CacheHelper.buildTestToken("1");
    req.setToken(token);
    LOG.d(this,req);
    WebApiResponse response = employeeConsoleCtrl.switchEmployee(req);
    LOG.d(this,response);
} 

/** 
* 
* Method: changheEmployeePassword(@RequestBody EmployeeHandleReq req) 
* 
*/ 
@Test
public void testChangheEmployeePassword() throws Exception { 
//TODO: Test goes here...
    EmployeeHandleReq req = new EmployeeHandleReq();
    req.setEmployeeId(1l);
    req.setNewPasswd("qqq");
    String token = CacheHelper.buildTestToken("1");
    req.setToken(token);
    LOG.d(this,req);
    WebApiResponse response = employeeConsoleCtrl.changheEmployeePassword(req);
    LOG.d(this,response);

} 

/** 
* 
* Method: allotRolesToEmployee(@RequestBody EmployeeAllotRoleReq req) 
* 
*/ 
@Test
public void testAllotRolesToEmployee() throws Exception { 
//TODO: Test goes here...
    EmployeeAllotRoleReq req = new EmployeeAllotRoleReq();
    req.setEmployeeId(1l);
    String token = CacheHelper.buildTestToken("1");
    req.setToken(token);
    List<Long> ids = new ArrayList<>();
    ids.add(9l);
    req.setRoleIds(ids);
    LOG.d(this,req);
    WebApiResponse response = employeeConsoleCtrl.allotRolesToEmployee(req);
    LOG.d(this,response);
} 

/** 
* 
* Method: updateEmployeePassword(@RequestBody EmployeeSelfReq req) 
* 
*/ 
@Test
public void testUpdateEmployeePassword() throws Exception { 
//TODO: Test goes here...
    EmployeeSelfReq req = new EmployeeSelfReq();
    req.setOldPasswd("jcf123456");
    req.setNewPasswd("test321");
    String token = CacheHelper.buildTestToken("1");
    req.setToken(token);
    LOG.d(this,req);
    WebApiResponse response = employeeConsoleCtrl.updateEmployeePassword(req);
    LOG.d(this,response);

} 


} 
