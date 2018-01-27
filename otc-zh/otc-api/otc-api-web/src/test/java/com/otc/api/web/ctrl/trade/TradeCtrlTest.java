package com.otc.api.web.ctrl.trade; 

import com.base.BaseSpringTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.web.ctrl.trade.req.JudgeReq;
import com.otc.api.web.ctrl.trade.req.TradeConfirmReq;
import com.otc.api.web.ctrl.trade.req.TradeListReq;
import com.otc.api.web.ctrl.trade.req.TradeReq;
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

import java.math.BigDecimal;

/** 
* TradeCtrl Tester. 
* 
* @author <Authors name> 
* @since <pre>五月 11, 2017</pre> 
* @version 1.0 
*/ 
public class TradeCtrlTest extends BaseSpringTest{

    @Autowired
    private TradeCtrl tradeCtrl;

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: launchTrade(@RequestBody TradeReq req) 
* 
*/ 
@Test
public void testLaunchTrade() throws Exception {
    TradeReq req = new TradeReq();
    req.setAdvertId(6l);
    req.setToken(CacheHelper.buildTestToken("3"));
    req.setTradeNumber(new BigDecimal(0.8));
    req.setRemark("123456");
    LOG.d(this,req);
    WebApiResponse resp = tradeCtrl.launchTrade(req);
    LOG.d(this,resp);
} 

/** 
* 
* Method: buyerPayed(@RequestBody WebApiBaseReq req) 
* 
*/ 
@Test
public void testBuyerPayed() throws Exception {
    WebApiBaseReq req = new WebApiBaseReq();
    req.setToken(CacheHelper.buildTestToken("31"));
    req.setId(24L);
    tradeCtrl.buyerPayed(req);
} 

/** 
* 
* Method: sellerConfirm(@RequestBody WebApiBaseReq req) 
* 
*/ 
@Test
public void testSellerConfirm() throws Exception {
    TradeConfirmReq req = new TradeConfirmReq();
    req.setToken(CacheHelper.buildTestToken("33"));
    req.setId(24L);
    SmsCodeGet smsCodeGet = SmsVerifyUtils.getVerifyCode("feng.ganqi@jucaifu.com", SmsType.CONFIRM_TRADE, 100L);
    if (smsCodeGet.isStatus()) {
        req.setVrCode(smsCodeGet.getContent());
    } else {
        throw new Exception("验证码获取失败");
    }
    tradeCtrl.sellerConfirm(req);
} 

/** 
* 
* Method: sellerAppeal(@RequestBody WebApiBaseReq req) 
* 
*/ 
@Test
public void testSellerAppeal() throws Exception {
    WebApiBaseReq req = new WebApiBaseReq();
    req.setToken(CacheHelper.buildTestToken("33"));
    req.setId(19L);
    tradeCtrl.sellerAppeal(req);
} 

/** 
* 
* Method: buyerCancel(@RequestBody WebApiBaseReq req) 
* 
*/ 
@Test
public void testBuyerCancel() throws Exception {
    WebApiBaseReq req = new WebApiBaseReq();
    req.setToken(CacheHelper.buildTestToken("31"));
    req.setId(18L);
    tradeCtrl.buyerCancel(req);
}

    @Test
    public void testJudge() throws Exception {
        JudgeReq req = new JudgeReq();
        req.setTradeId(24L);
        req.setContext("22323dsds");
        req.setLevel(1);
        req.setToken(CacheHelper.buildTestToken("33"));
        LOG.d(this,req);
        tradeCtrl.judge(req);
    }

@Test
public void testTradeInfo() throws Exception{
    WebApiBaseReq req = new WebApiBaseReq();
    req.setToken(CacheHelper.buildTestToken("31"));
    req.setId(24L);
    String queryStr = JsonHelper.obj2JsonStr(req);
    WebApiResponse response = tradeCtrl.tradeInfo(queryStr);
    LOG.d(this,response);
}


    @Test
    public void testTradeList() throws Exception{
        TradeListReq req = new TradeListReq();
        req.setToken(CacheHelper.buildTestToken("81"));
        req.setPageFilter(1,10);
        req.setCoinId(1L);
        req.setTradeType(1);
        String queryStr = JsonHelper.obj2JsonStr(req);
        WebApiResponse response = tradeCtrl.getTradeList(queryStr);
        LOG.d(this,response);
    }

    @Test
    public void test1() throws Exception{
        WebApiBaseReq req = new WebApiBaseReq();
        req.setToken(CacheHelper.buildTestToken("31"));
        req.setId(24L);
        LOG.d(this,req);
        WebApiResponse response = tradeCtrl.getJudgeDetail(JsonHelper.obj2JsonStr(req));
        LOG.d(this,response);
    }


    @Override
    public void doTest() {

    }
}
