package com.szzc.api.three.ctrl;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.api.three.ctrl.request.CancelOrderReq;
import com.szzc.api.three.ctrl.request.TradeOrderReq;
import com.szzc.api.three.ctrl.request.TradeReq;
import com.szzc.api.three.pojo.BaseApiReq;
import com.szzc.api.three.utils.api.SignHelper;
import com.szzc.base.BaseSpringTest;
import com.szzc.common.api.packet.web.WebApiResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.TreeMap;

/** 
* TradeApiCtrl Tester. 
* 
* @author <Authors name> 
* @since <pre>Jul 22, 2017</pre> 
* @version 1.0 
*/ 
public class TradeApiCtrlTest extends BaseSpringTest {
    @Autowired
    private TradeApiCtrl tradeApiCtrl;
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
* Method: accountInfo(@RequestBody BaseApiReq req) 
* 
*/ 
@Test
public void testAccountInfo() throws Exception { 
//TODO: Test goes here...
    BaseApiReq req = new BaseApiReq();
    req.setApi_key("0015a69e4b354afc8e32dfb1fcadbccf");
    req.setNonce(System.currentTimeMillis());
    TreeMap<String,String> treeMap = new TreeMap<>();
    treeMap.put("api_key",req.getApi_key());
    treeMap.put("nonce",req.getNonce().toString());
    String sign = SignHelper.getSign(treeMap, "1334433a1f7b40e5bbf51bfa47c40b57");
    LOG.d(this,"生成的sign=" + sign);
    req.setSign(sign);
    WebApiResponse response = tradeApiCtrl.accountInfo(req);
    LOG.i(this,response);
} 

/** 
* 
* Method: trade(@RequestBody TradeReq req) 
* 
*/ 
@Test
public void testTrade() throws Exception { 
//TODO: Test goes here...
    TradeReq req = new TradeReq();
    req.setSymbol("btc_cny");
    req.setApi_key("0015a69e4b354afc8e32dfb1fcadbccf");
    req.setPrice(new BigDecimal("7"));
    req.setAmount(new BigDecimal("2"));
    req.setType("buy");
    req.setNonce(System.currentTimeMillis());
    TreeMap<String,String> treeMap = new TreeMap<>();
    treeMap.put("api_key",req.getApi_key());
    treeMap.put("nonce",req.getNonce().toString());
    treeMap.put("symbol",req.getSymbol());
    treeMap.put("price",req.getPrice().toString());
    treeMap.put("amount",req.getAmount().toString());
    treeMap.put("type",req.getType());
    String sign = SignHelper.getSign(treeMap, "1334433a1f7b40e5bbf51bfa47c40b57");
    LOG.d(this,"生成的sign=" + sign);
    req.setSign(sign);
    LOG.d(this,req);
    WebApiResponse response = tradeApiCtrl.trade(req);
    LOG.i(this,response);
} 

/** 
* 
* Method: cancel(@RequestBody TradeOrderReq req) 
* 
*/ 
@Test
public void testCancel() throws Exception { 
//TODO: Test goes here...
    CancelOrderReq req = new CancelOrderReq();
    req.setSymbol("tic_cny");
    req.setOrderId(833265l);
    req.setApi_key("0015a69e4b354afc8e32dfb1fcadbccf");
    req.setNonce(System.currentTimeMillis());
    TreeMap<String,String> treeMap = new TreeMap<>();
    treeMap.put("api_key",req.getApi_key());
    treeMap.put("nonce",req.getNonce().toString());
    treeMap.put("symbol",req.getSymbol());
    treeMap.put("orderId",req.getOrderId().toString());
    String sign = SignHelper.getSign(treeMap, "1334433a1f7b40e5bbf51bfa47c40b57");
    LOG.d(this,"生成的sign=" + sign);
    req.setSign(sign);
    LOG.d(this,req);
    WebApiResponse response = tradeApiCtrl.cancel(req);
    LOG.i(this,response);
} 

/** 
* 
* Method: order(@RequestBody TradeOrderReq req) 
* 
*/ 
@Test
public void testOrder() throws Exception { 
//TODO: Test goes here...
    TradeOrderReq req = new TradeOrderReq();
    req.setSymbol("btc_cny");
    req.setApi_key("0015a69e4b354afc8e32dfb1fcadbccf");
    req.setCurrent_page(1);
    req.setNonce(System.currentTimeMillis());
    TreeMap<String,String> treeMap = new TreeMap<>();
    treeMap.put("api_key",req.getApi_key());
    treeMap.put("nonce",req.getNonce().toString());
    treeMap.put("symbol","btc_cny");
    treeMap.put("current_page",req.getCurrent_page().toString());
    LOG.d(this,treeMap);
    String test = JsonHelper.obj2JsonStr(treeMap);
    //String sign = tradeApiCtrl.getSign(test,"1334433a1f7b40e5bbf51bfa47c40b57");
    String sign = SignHelper.getSign(treeMap, "1334433a1f7b40e5bbf51bfa47c40b57");
    LOG.d(this,"生成的sign=" + sign);
    req.setSign(sign);
    WebApiResponse response = tradeApiCtrl.order(req);
    LOG.i(this,response);
    /*LOG.i(this,System.currentTimeMillis());
    LOG.i(this,System.currentTimeMillis());*/
}




/** 
* 
* Method: getFbIdByShortName(String symbolStr) 
* 
*/ 
@Test
public void testGetFbIdByShortName() throws Exception { 
//TODO: Test goes here... 
}



} 
