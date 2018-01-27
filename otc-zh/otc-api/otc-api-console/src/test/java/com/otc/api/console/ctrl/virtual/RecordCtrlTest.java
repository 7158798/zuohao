package com.otc.api.console.ctrl.virtual;

import com.base.BaseSpringTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.DateHelper;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.console.ctrl.virtual.request.CancelReq;
import com.otc.api.console.ctrl.virtual.request.RecordAuditReq;
import com.otc.api.console.ctrl.virtual.request.RecordReq;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.CacheHelper;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/** 
* RecordCtrl Tester. 
* 
* @author <Authors name> 
* @since <pre>May 2, 2017</pre> 
* @version 1.0 
*/ 
public class RecordCtrlTest extends BaseSpringTest {

   @Autowired
   private RecordCtrl recordCtrl;

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
* Method: getStatusList(@PathVariable String queryJson) 
* 
*/ 
@Test
public void testGetStatusList() throws Exception { 
//TODO: Test goes here...
   RecordReq req = new RecordReq();
   req.setType("00");
   String token = CacheHelper.buildTestToken("1");
   req.setToken(token);
   LOG.d(this, req);
   String reqStr = JsonHelper.obj2JsonStr(req);
   WebApiResponse response = recordCtrl.getStatusList(reqStr);
   LOG.d(this,response);
} 

/** 
* 
* Method: getRechargeList(@PathVariable String queryJson) 
* 
*/ 
@Test
public void testGetRechargeList() throws Exception { 
//TODO: Test goes here...

   RecordReq req = new RecordReq();
   req.setPageFilter(1,10);
   //req.setCondition("fenggq");
   //req.setCoinId(1l);
   String token = CacheHelper.buildTestToken("1");
   //Date start = DateHelper.string2Date("2017-05-05", DateHelper.DateFormatType.YearMonthDay);
   //Date end = DateHelper.string2Date("2017-05-06", DateHelper.DateFormatType.YearMonthDay);
   //req.setDateFilter(start,end);
   req.setToken(token);
   LOG.d(this, req);
   String reqStr = JsonHelper.obj2JsonStr(req);
   WebApiResponse response = recordCtrl.getRechargeList(reqStr);
   LOG.d(this,response);
} 

/** 
* 
* Method: getExamineWithdrawList(@PathVariable String queryJson) 
* 
*/ 
@Test
public void testGetExamineWithdrawList() throws Exception { 
//TODO: Test goes here...
   RecordReq req = new RecordReq();
   //req.setCondition("冯干齐");
   //req.setCoinId(1l);
   Date start = DateHelper.string2Date("2017-06-06 14:00", DateHelper.DateFormatType.YearMonthDay_HourMinute);
   Date end = DateHelper.string2Date("2017-06-06 17:00", DateHelper.DateFormatType.YearMonthDay_HourMinute);
   req.setDateFilter(start,end);
   req.setPageFilter(1,10);
   String token = CacheHelper.buildTestToken("1");
   req.setToken(token);
   LOG.d(this, req);
   String reqStr = JsonHelper.obj2JsonStr(req);
   WebApiResponse response = recordCtrl.getExamineWithdrawList(reqStr);
   LOG.d(this,response);
} 

/** 
* 
* Method: getSuccessWithdrawList(@PathVariable String queryJson) 
* 
*/ 
@Test
public void testGetSuccessWithdrawList() throws Exception { 
//TODO: Test goes here...
   RecordReq req = new RecordReq();
   req.setCondition("冯干齐");
   req.setCoinId(1l);
  /* Date start = DateHelper.string2Date("2017-04-25", DateHelper.DateFormatType.YearMonthDay);
   Date end = DateHelper.string2Date("2017-05-06", DateHelper.DateFormatType.YearMonthDay);
   req.setDateFilter(start,end);*/
   req.setPageFilter(1,10);
   String token = CacheHelper.buildTestToken("1");
   req.setToken(token);
   LOG.d(this, req);
   String reqStr = JsonHelper.obj2JsonStr(req);
   WebApiResponse response = recordCtrl.getSuccessWithdrawList(reqStr);
   LOG.d(this,response);
} 


/** 
* 
* Method: getWithdrawList(VirtualRecordVo vo, RecordReq req) 
* 
*/ 
@Test
public void testGetWithdrawList() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = RecordCtrl.getClass().getMethod("getWithdrawList", VirtualRecordVo.class, RecordReq.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
}


   @Test
   public void testGetRecordDetail(){
      WebApiBaseReq req = new WebApiBaseReq();
      req.setId(2l);
      String token = CacheHelper.buildTestToken("1");
      req.setToken(token);
      LOG.d(this,req);
      String str = JsonHelper.obj2JsonStr(req);
      WebApiResponse response = recordCtrl.getRecordDetail(str);
      LOG.d(this,response);
   }


   @Test
   public void testCancel(){
      CancelReq req = new CancelReq();
      req.setId(55l);
      String token = CacheHelper.buildTestToken("1");
      req.setToken(token);
      req.setReason("sdfadsf");
      WebApiResponse response =recordCtrl.cancel(req);
      LOG.d(this,response);
   }

   @Test
   public void testAuditWithdraw(){
      RecordAuditReq req = new RecordAuditReq();
      req.setId(55l);
      String token = CacheHelper.buildTestToken("1");
      req.setToken(token);
      req.setConfirmPwd("qqq");
      WebApiResponse response =recordCtrl.auditWithdraw(req);
      LOG.d(this,response);
   }

} 
