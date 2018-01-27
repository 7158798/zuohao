package com.otc.ctrl;

import com.base.BaseSpringTest;
import com.jucaifu.common.log.LOG;
import com.otc.api.web.ctrl.user.SendValidateWebCtrl;
import com.otc.api.web.ctrl.user.UserWebCtrl;
import com.otc.api.web.ctrl.user.request.EmailVeriCodeReq;
import com.otc.api.web.ctrl.user.request.SendMailCodeReq;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.CacheHelper;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

/** 
* SendValidateWebCtrl Tester. 
* 
* @author <Authors name> 
* @since <pre>四月 24, 2017</pre> 
* @version 1.0 
*/ 
public class SendValidateWebCtrlTest extends BaseSpringTest{

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
}

@Autowired
private SendValidateWebCtrl sendValidateWebCtrl;

/** 
* 
* Method: getKaptchaImage(@PathVariable String queryJson, HttpServletResponse response) 
* 
*/ 
@Test
public void testGetKaptchaImage() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: sendSmsForRegist(@RequestBody EmailVeriCodeReq req) 
* 
*/ 
@Test
public void testSendSmsForRegist() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: sendSmsForResetPWD(@RequestBody EmailVeriCodeReq req) 
* 
*/ 
@Test
public void testSendSmsForResetPWD() throws Exception {
    EmailVeriCodeReq req = new EmailVeriCodeReq();
    req.setEmailAddress("feng.ganqi@jucaifu.com");
    req.setAuthCodeCache("123456");
    req.setVeryCode("ab34");
    sendValidateWebCtrl.sendSmsForResetPWD(req);
    LOG.d(this,req);
}


@Test
public void test1() throws Exception{
    WebApiBaseReq req = new WebApiBaseReq();
    req.setToken(CacheHelper.buildTestToken("33"));
    WebApiResponse response = sendValidateWebCtrl.sendPWDCode(req);
    LOG.d(this,response);
}


    @Test
    public void test5() throws Exception{
        SendMailCodeReq req = new SendMailCodeReq();
        req.setToken(CacheHelper.buildTestToken("33"));
        req.setMailType("21");
        WebApiResponse response = sendValidateWebCtrl.sendEamilCode(req);
        LOG.d(this,response);
    }


/** 
* 
* Method: sendMessage(String email, SmsType smsType) 
* 
*/ 
@Test
public void testSendMessage() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = SendValidateWebCtrl.getClass().getMethod("sendMessage", String.class, SmsType.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: getAllSendTimes(String email) 
* 
*/ 
@Test
public void testGetAllSendTimes() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = SendValidateWebCtrl.getClass().getMethod("getAllSendTimes", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: getSendMaxForAll() 
* 
*/ 
@Test
public void testGetSendMaxForAll() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = SendValidateWebCtrl.getClass().getMethod("getSendMaxForAll"); 
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
