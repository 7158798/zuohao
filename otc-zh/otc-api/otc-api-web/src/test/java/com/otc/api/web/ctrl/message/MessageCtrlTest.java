package com.otc.api.web.ctrl.message;

import com.base.BaseSpringTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.web.ctrl.message.request.ChatReq;
import com.otc.api.web.ctrl.message.request.MessageReq;
import com.otc.api.web.utils.WebTokenValidate;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.CacheHelper;
import com.otc.facade.message.enums.MessageType;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

/** 
* MessageCtrl Tester. 
* 
* @author <Authors name> 
* @since <pre>May 9, 2017</pre> 
* @version 1.0 
*/ 
public class MessageCtrlTest extends BaseSpringTest {

    @Autowired
    private MessageCtrl messageCtrl;
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
* Method: getTypeList(@PathVariable String queryJson) 
* 
*/ 
@Test
public void testGetTypeList() throws Exception { 
//TODO: Test goes here...
    WebApiBaseReq req = new WebApiBaseReq();
    //String token = CacheHelper.buildTestToken("1");
    req.setToken("d458e4cd449f6863afa49c640b699d2d");
    String str = JsonHelper.obj2JsonStr(req);
    LOG.d(this, req);
    WebApiResponse response = messageCtrl.getTypeList(str);
    LOG.d(this,response);
}

/** 
* 
* Method: getMessageList(@PathVariable String queryJson) 
* 
*/ 
@Test
public void testGetMessageList() throws Exception { 
//TODO: Test goes here...
    MessageReq req = new MessageReq();
    req.setPageFilter(1,3);
    req.setType("01");
    //String token = CacheHelper.buildTestToken("1");
    req.setToken("d458e4cd449f6863afa49c640b699d2d");
    String str = JsonHelper.obj2JsonStr(req);
    WebApiResponse response = messageCtrl.getMessageList(str);
    LOG.d(this,response);
}


    @Test
    public void  testGetChatList() throws Exception {
        MessageReq req = new MessageReq();
        req.setPageFilter(1,18);
        String token = CacheHelper.buildTestToken("33");
        req.setToken(token);
        //req.setIsRead(Boolean.TRUE);
        req.setRelationId(2l);
        LOG.d(this,req);
        String str = JsonHelper.obj2JsonStr(req);
        WebApiResponse response = messageCtrl.getChatList(str);
        LOG.d(this,response);
        /*String token = CacheHelper.buildTestToken("1");
        System.out.println(token);
        token = CacheHelper.buildTestToken("2");
        System.out.println(token);*/

    }

    @Test
    public void  testUpdateChat() throws Exception {
        /**/ChatReq req = new ChatReq();
        req.setId(26l);
        //String token = CacheHelper.buildTestToken("1");
        req.setToken("d458e4cd449f6863afa49c640b699d2d1");
        LOG.d(this,req);
        WebApiResponse response = messageCtrl.updateChat(req);
        LOG.d(this,response);

        //WebTokenValidate("ef98c251ab94556a58d29b667a7dd627001");
        LOG.d(this,"test");
    }


} 
