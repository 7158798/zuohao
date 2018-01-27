package com.szzc.api.three.ctrl;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.api.three.utils.api.SignHelper;
import com.szzc.base.BaseSpringTest;
import com.szzc.common.api.packet.web.WebApiResponse;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.TreeMap;

/** 
* TickerApiCtrl Tester. 
* 
* @author <Authors name> 
* @since <pre>Jul 23, 2017</pre> 
* @version 1.0 
*/ 
public class TickerApiCtrlTest extends BaseSpringTest {

    @Autowired
    private TickerApiCtrl tickerApiCtrl;
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
* Method: trades(@RequestParam String symbol, @RequestParam(defaultValue = "600") Long since) 
* 
*/ 
@Test
public void testTrades() throws Exception { 
//TODO: Test goes here...
    WebApiResponse response = tickerApiCtrl.trades("btc_cny", 1l);
    LOG.d(this,response);

} 

/** 
* 
* Method: ticker(String symbol) 
* 
*/ 
@Test
public void testTicker() throws Exception { 
//TODO: Test goes here...
    WebApiResponse response = tickerApiCtrl.ticker("tic_cny");
    LOG.i(this,response);
} 

/** 
* 
* Method: getDepth(@RequestParam(defaultValue = "btc_cny") String symbol, @RequestParam(defaultValue = "20") int size, @RequestParam(defaultValue = "0.01") String merge) 
* 
*/ 
@Test
public void testGetDepth() throws Exception { 
//TODO: Test goes here...
    WebApiResponse response = tickerApiCtrl.getDepth("btc_cny", 1, "0.01");
    LOG.i(this,response);
}

    @Test
    public void testGetSign(){
        TreeMap<String,String> treeMap = new TreeMap<>();
        treeMap.put("api_key","f709d94ec6b34324ae6bb77bc566bf02");
        treeMap.put("nonce","1");
        treeMap.put("amount","0.671");
        treeMap.put("price","120");
        treeMap.put("type","sell");
        treeMap.put("symbol","ans_cny");
        //String str = JsonHelper.obj2JsonStr(treeMap);
        String str = SignHelper.getSign(treeMap, "3582bf8181d14fe284a02b1a8c02fe2b");
        //String result = tickerApiCtrl.getSign(str,"BBB");
        System.out.println(str);
    }


}


