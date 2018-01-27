package com.jucaifu.common.mail; 

import com.base.BaseSpringTest;
import com.jucaifu.common.constants.ApiBasePathConstant;
import com.jucaifu.common.log.LOG;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** 
* MailSendHelper Tester. 
* 
* @author scofieldcai-dev 
* @since  十二月 30, 2015 
* @version 1.0 
*/ 
public class MailSendHelperTest extends BaseSpringTest{ 

    @Before
    public void before() throws Exception { 
        super.before();
    } 

    @After
    public void after() throws Exception {
        super.after(); 
    } 
    
    @Override
    public void doTest() {
        MailSendInfo sendInfo = new MailSendInfo();
        sendInfo.setTo("scofield.cai@jucaifu.com");
        sendInfo.setSubject("测试");
        sendInfo.setContent("发送测试邮件！");

        MailSendHelper.getInstance().send(sendInfo);

        testSend();
    }

    /**
     * 
     * Method: send(final MailSendInfo sendInfo) 
     * 
     */ 
    @Test
    public void testSend() {
        LOG.d(this,MailSendHelper.getSender());
    }

    @Test
    public void testVar(){
        String api = ApiBasePathConstant.APP_API;
        LOG.d(this, api);
        Boolean result = Boolean.valueOf("true");
        LOG.d(this,result);
        LOG.d(this,Boolean.valueOf("yes"));
    }




} 
