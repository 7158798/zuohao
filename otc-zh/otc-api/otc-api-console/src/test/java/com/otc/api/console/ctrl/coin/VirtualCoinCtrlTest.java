package com.otc.api.console.ctrl.coin;

import com.base.BaseSpringTest;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.console.ctrl.coin.request.VirtualAddressReq;
import com.otc.api.console.ctrl.coin.request.VirtualCoinAddReq;
import com.otc.api.console.ctrl.coin.request.VirtualCoinCutReq;
import com.otc.api.console.utils.ConsoleTokenValidate;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.CacheHelper;
import com.otc.facade.cache.CacheKey;
import com.otc.facade.virtual.enums.VirtualCoinStatus;
import com.otc.facade.virtual.pojo.po.VirtualCoin;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Map;

/** 
* VirtualCoinCtrl Tester. 
* 
* @author <Authors name> 
* @since <pre>Apr 28, 2017</pre> 
* @version 1.0 
*/ 
public class VirtualCoinCtrlTest extends BaseSpringTest {

   @Autowired
   private VirtualCoinCtrl virtualCoinCtrl;
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
* Method: getCoinList(@PathVariable String queryJson) 
* 
*/ 
@Test
public void testGetCoinList() throws Exception { 
//TODO: Test goes here...
   /*WebApiBaseReq req = new WebApiBaseReq();
   req.setPageFilter(1,3);
   String token = CacheHelper.buildTestToken("1");
   req.setToken(token);
   LOG.d(this,req);
   String str = JsonHelper.obj2JsonStr(req);
   WebApiResponse response = virtualCoinCtrl.getCoinList(str);
   LOG.d(this,response);*/

    Long userId = ConsoleTokenValidate.validateToken("d12b5d7d-e924-44ef-9479-ebaf834ca358");

    System.out.println("userId " + userId);
    // validateToken


}

   @Test
   public void testGetCoinTypeList() throws Exception {
//TODO: Test goes here...
     /* WebApiBaseReq req = new WebApiBaseReq();
      String token = CacheHelper.buildTestToken("1");
      req.setToken(token);
      String str = JsonHelper.obj2JsonStr(req);
      WebApiResponse response = virtualCoinCtrl.getCoinTypeList(str);
      LOG.d(this,response);*/

      Map<Long,VirtualCoin> map;
         map = CacheHelper.getObj(CacheKey.VIRTUAL_COIN_ALL_KEY);
      Boolean flag = false;
      if (map == null){
         flag = true;
      }
      System.out.println("sdafds = " + flag );
   }

   @Test
   public void testGetCoinTypeDetail() throws Exception {
//TODO: Test goes here...
     /* WebApiBaseReq req = new WebApiBaseReq();
      String token = CacheHelper.buildTestToken("1");
      req.setToken(token);
      String str = JsonHelper.obj2JsonStr(req);
      WebApiResponse response = virtualCoinCtrl.getCoinTypeList(str);
      LOG.d(this,response);*/
      WebApiBaseReq req = new WebApiBaseReq();
      req.setId(2l);
      String token = CacheHelper.buildTestToken("1");
      req.setToken(token);
      LOG.d(this,req);
      String str = JsonHelper.obj2JsonStr(req);
      WebApiResponse response = virtualCoinCtrl.getCoinTypeDetail(str);
      LOG.d(this, response);
   }

   /**
* 
* Method: cutStatus(@RequestBody VirtualCoinCutReq req) 
* 
*/ 
@Test
public void testCutStatus() throws Exception { 
//TODO: Test goes here...
   //CacheHelper.deleteObj(CacheKey.VIRTUAL_COIN_KEY);
   /**/VirtualCoinCutReq cutReq = new VirtualCoinCutReq();
   cutReq.setCoinId(1l);
   cutReq.setStatus(VirtualCoinStatus.ENABLED.getCode());
   String token = CacheHelper.buildTestToken("1");
   cutReq.setToken(token);
    cutReq.setWalletPwd("dsfadsfa");
   LOG.d(this,cutReq);
   WebApiResponse response = virtualCoinCtrl.cutStatus(cutReq);
   LOG.d(this,response);
   //CacheHelper.deleteObj(CacheKey.VIRTUAL_COIN_KEY);

} 

/** 
* 
* Method: addCoin(@RequestBody VirtualCoinAddReq req) 
* 
*/ 
@Test
public void testAddCoin() throws Exception { 
//TODO: Test goes here...
   VirtualCoinAddReq addReq = new VirtualCoinAddReq();
   addReq.setShortName("ETC");
   addReq.setName("以太坊经典");
   addReq.setSecretKey("aa");
   addReq.setAccessKey("bb");
   addReq.setIp("334");
   addReq.setPort("8080");
   addReq.setIconUrl("sdfadsfa");
   addReq.setIsAuto("1");
   addReq.setIsRecharge("1");
   addReq.setIsWithDraw("1");
   addReq.setMainAddress("main");
   addReq.setLowRechargeFees(BigDecimal.ZERO);
   addReq.setRechargeFees(BigDecimal.ZERO);
   addReq.setLowWithdrawFees(BigDecimal.ZERO);
   addReq.setWithdrawFees(BigDecimal.ZERO);
   addReq.setSingleLowRecharge(BigDecimal.ZERO);
   addReq.setSingleHighRecharge(BigDecimal.ZERO);
   addReq.setDayHighRecharge(BigDecimal.ZERO);
   addReq.setSingleLowWithdraw(BigDecimal.ZERO);
   addReq.setSingleHighWithdraw(BigDecimal.ZERO);
   addReq.setDayHighWithdraw(BigDecimal.ZERO);
   addReq.setLowTradeFees(BigDecimal.ZERO);
   addReq.setTradeFees(BigDecimal.ZERO);

   String token = CacheHelper.buildTestToken("1");
   addReq.setToken(token);
   LOG.d(this,addReq);
   WebApiResponse response = virtualCoinCtrl.addCoin(addReq);
   LOG.d(this,response);
}


   @Test
   public void testUpdateCoin() throws Exception {
//TODO: Test goes here...
      VirtualCoinAddReq update = new VirtualCoinAddReq();
      update.setId(1l);
      update.setShortName("btc");
      update.setName("btc");
      update.setSecretKey("btc123456");
      update.setAccessKey("btcabc87654321");
      update.setIp("130.252.100.93");
      update.setPort("8385");
      update.setIconUrl("xx");
      update.setIsWithDraw("");
      update.setIsRecharge("");
      update.setIsAuto("");
      update.setMainAddress("main1");
       update.setLowRechargeFees(new BigDecimal(1.0));
       update.setRechargeFees(new BigDecimal(1.0));
       update.setLowWithdrawFees(new BigDecimal(1.0));
       update.setWithdrawFees(new BigDecimal(1.0));
       update.setSingleLowRecharge(new BigDecimal(1.0));
       update.setSingleHighRecharge(new BigDecimal(1.0));
       update.setDayHighRecharge(new BigDecimal(1.0));
       update.setSingleLowWithdraw(new BigDecimal(1.0));
       update.setSingleHighWithdraw(new BigDecimal(1.0));
       update.setDayHighWithdraw(new BigDecimal(1.0));
       update.setLowTradeFees(new BigDecimal(6.0));
       update.setTradeFees(new BigDecimal(9.0));
      String token = CacheHelper.buildTestToken("1");
      update.setToken(token);
      LOG.d(this,update);
      WebApiResponse response = virtualCoinCtrl. updateCoin(update);
      LOG.d(this,response);
   }

/**
* 
* Method: filter(VirtualCoin coin, VirtualCoinAddReq req) 
* 
*/ 
@Test
public void testFilter() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = VirtualCoinCtrl.getClass().getMethod("filter", VirtualCoin.class, VirtualCoinAddReq.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
}

    @Test
    public void testCreateAddress(){
        VirtualAddressReq req = new VirtualAddressReq();
        req.setId(1l);
        req.setWalletPwd("jcf@2016$8nZ^Fj");
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        WebApiResponse response = virtualCoinCtrl.createAddress(req);
        LOG.d(this,response);
    }

@Test
    public void getTestWallet(){
        WebApiBaseReq req = new WebApiBaseReq();
        req.setId(1l);
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        LOG.d(this,req);
        WebApiResponse response = virtualCoinCtrl.testWallet(JsonHelper.obj2JsonStr(req));
        LOG.d(this,response);
        /*Map<Long,VirtualCoin> coinMap = CacheHelper.getObj(CacheKey.VIRTUAL_COIN_KEY);
        LOG.d(this,coinMap);
    CacheHelper.deleteObj(CacheKey.VIRTUAL_COIN_KEY);*/
    }

} 
