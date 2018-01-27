package com.otc.ctrl;

import com.base.BaseSpringTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.web.ctrl.user.UserWebCtrl;
import com.otc.api.web.ctrl.user.request.*;
import com.otc.api.web.ctrl.user.response.NoReadMessageResp;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.CacheHelper;
import com.otc.core.validator.SmsCodeGet;
import com.otc.core.validator.SmsType;
import com.otc.core.validator.SmsVerifyUtils;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

/** 
* UserWebCtrl Tester. 
* 
* @author <Authors name> 
* @since <pre>四月 21, 2017</pre> 
* @version 1.0 
*/ 
public class UserWebCtrlTest extends BaseSpringTest{

    @Autowired
    private UserWebCtrl userWebCtrl;

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

@Test
public void testGetKaptchaImage() throws Exception {
    //userWebCtrl.getKaptchaImage();
}

@Test
public void testSendSmsForRegist() throws Exception{
    EmailVeriCodeReq req = new EmailVeriCodeReq();
    req.setEmailAddress("feng.ganqi@jucaifu.com");
    req.setAuthCodeCache("123456");
    req.setVeryCode("ab34");
    LOG.d(this,req);
    //userWebCtrl.sendSmsForRegist(req);
}

/** 
* 
* Method: registUser(@RequestBody UserRegisterReq req) 
* 
*/ 
@Test
public void testRegistUser() throws Exception {
    UserRegisterReq req = new UserRegisterReq();
    String email = "zly110@yahoo.com";
    req.setEmail(email);
    req.setLoginName("zly110");
    req.setPwd("a123456");
    SmsCodeGet vrcode = SmsVerifyUtils.getVerifyCode(email, SmsType.REGISTER, 100L);
    req.setCode(vrcode.getContent());
    LOG.d(this,req);
    WebApiResponse response = userWebCtrl.registUser(req);
    LOG.d(this,response);


} 

/** 
* 
* Method: userLogin(@RequestBody UserLoginReq req) 
* 
*/ 
@Test
public void testUserLogin() throws Exception {
    UserLoginReq req = new UserLoginReq();
    req.setLoginName("fenggq17");
    req.setPassword("a123456789");

    LOG.d(this,req);
    WebApiResponse resp = this.userWebCtrl.userLogin(req);
    LOG.d(this,resp);
} 



/** 
* 
* Method: refreshUser(@PathVariable String queryJson) 
* 
*/ 
@Test
public void testRefreshUser() throws Exception {
    WebApiBaseReq req =new WebApiBaseReq();
    req.setToken(CacheHelper.buildTestToken("1"));
    String queryStr = JsonHelper.obj2JsonStr(req);
     this.userWebCtrl.refreshUser(queryStr);
}


    @Test
    public void test222() throws Exception {
        UserLoginReq req =new UserLoginReq();
        req.setLoginName("fenggq17");
        String queryStr = JsonHelper.obj2JsonStr(req);
        this.userWebCtrl.refreshUser(queryStr);
    }

    /**
* 
* Method: validateAndRefreshToken(@RequestBody WebApiBaseReq req) 
* 
*/ 
@Test
public void testValidateAndRefreshToken() throws Exception {
    WebApiBaseReq req =new WebApiBaseReq();
    req.setToken(CacheHelper.buildTestToken("29"));
    this.userWebCtrl.validateAndRefreshToken(req);
} 

/** 
* 
* Method: logout(@RequestBody WebApiBaseReq req) 
* 
*/ 
@Test
public void testLogout() throws Exception {
    WebApiBaseReq req =new WebApiBaseReq();
    req.setToken(CacheHelper.buildTestToken("29"));
    this.userWebCtrl.logout(req);
}


    @Test
    public void resetPwd1() throws Exception {
        String eamil = "feng.ganqi@jucaifu.com";
        ResetForgetPwdReq req = new ResetForgetPwdReq();
        req.setStepType("1");
        req.setEmailAddress(eamil);

        SmsCodeGet smsCodeGet = SmsVerifyUtils.getVerifyCode(eamil, SmsType.LOGIN_PWD_RESET, 100L);
        if (smsCodeGet.isStatus()) {
            req.setAuthCode(smsCodeGet.getContent());
        } else {
            throw new Exception("验证码获取失败");
        }
        LOG.d(this, req);
        WebApiResponse resp = userWebCtrl.resetPWD(req);
        LOG.d(this,resp);
    }


    @Test
    public void resetPwd2() throws Exception {
        String phone = "feng.ganqi@jucaifu.com";
        ResetForgetPwdReq req = new ResetForgetPwdReq();
        req.setStepType("2");
        req.setEmailAddress(phone);
        req.setNewPassword("abc12345789");
        req.setAuthCodeCache("c7b95e1c-27e2-405f-9b13-0166cb6e2035");
        LOG.d(this, req);
        userWebCtrl.resetPWD(req);
    }

    @Test
    public void testUpdatePWD() throws Exception{
        UpdatePwdReq req = new UpdatePwdReq();
        req.setToken(CacheHelper.buildTestToken("31"));
        req.setOldPassword("abc12345789");
        req.setNewPassword("a123456789");

        LOG.d(this,req);

        userWebCtrl.updatePWD(req);
//        LOG.d(this,);
    }


    @Test
    public void testSetFishCode() throws Exception{
        UserFishCodeReq req = new UserFishCodeReq();
        req.setToken(CacheHelper.buildTestToken("31"));
        req.setFishCode("abc12345789");
        LOG.d(this,req);

        userWebCtrl.updateUserFish(req);
//        LOG.d(this,);
    }


    @Test
    public void changeEmail1() throws Exception{
        ChangeEmailReq req = new ChangeEmailReq();
        req.setToken(CacheHelper.buildTestToken("33"));
        req.setStepType("1");

        SmsCodeGet smsCodeGet = SmsVerifyUtils.getVerifyCode("feng.ganqi@jucaifu.com", SmsType.CHANGE_EMAIL, 100L);
        if (smsCodeGet.isStatus()) {
            req.setAuthCode(smsCodeGet.getContent());
        } else {
            throw new Exception("验证码获取失败");
        }
        req.setAuthCode(smsCodeGet.getContent());
        LOG.d(this,req);
        WebApiResponse resp = userWebCtrl.changeEmail(req);
        LOG.d(this,resp);
    }

    @Test
    public void changeEmail2() throws Exception{
        ChangeEmailReq req = new ChangeEmailReq();
        req.setToken(CacheHelper.buildTestToken("33"));
        req.setStepType("2");
        String email = "feng33@jucaifu.com";
        req.setEmail(email);

        SmsCodeGet smsCodeGet = SmsVerifyUtils.getVerifyCode(email, SmsType.CHANGE_EMAIL, 100L);
        if (smsCodeGet.isStatus()) {
            req.setAuthCode(smsCodeGet.getContent());
        } else {
            throw new Exception("验证码获取失败");
        }
        req.setAuthCode(smsCodeGet.getContent());
        req.setAuthCodeCache("6b48cea6-ce9d-480f-b0e8-873cddcd29d7");
        LOG.d(this,req);
        WebApiResponse resp = userWebCtrl.changeEmail(req);
        LOG.d(this,resp);
    }

    @Test
    public void getNoMessae(){
        WebApiBaseReq req =new WebApiBaseReq();
        req.setToken(CacheHelper.buildTestToken("31"));
        String queryStr = JsonHelper.obj2JsonStr(req);
        WebApiResponse response = this.userWebCtrl.noReadMessage(queryStr);
        LOG.d(this,response);
    }


/** 
* 
* Method: getUserWebLoginInfo(CacheUserInfo cacheUserInfo, String token) 
* 
*/ 
@Test
public void testGetUserWebLoginInfo() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = UserWebCtrl.getClass().getMethod("getUserWebLoginInfo", CacheUserInfo.class, String.class); 
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
