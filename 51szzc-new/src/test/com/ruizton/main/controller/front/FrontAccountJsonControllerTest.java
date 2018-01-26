package test.com.ruizton.main.controller.front; 

import com.ruizton.main.controller.front.FrontAccountJsonController;
import com.ruizton.main.log.LOG;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import test.com.ruizton.base.BaseSpringTest;

/** 
* FrontAccountJsonController Tester. 
* 
* @author <Authors name> 
* @since <pre>二月 28, 2017</pre> 
* @version 1.0 
*/ 
public class FrontAccountJsonControllerTest extends BaseSpringTest{

    @Autowired
    private FrontAccountJsonController frontAccountJsonController;

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: alipayManual(HttpServletRequest request, @RequestParam(required=true) double money, @RequestParam(required=true) int type, @RequestParam(required=true) int sbank) 
* 
*/ 
@Test
public void testAlipayManual() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: rechargeCnySubmit(HttpServletRequest request, @RequestParam(required=false) String bank, @RequestParam(required=false) String account, @RequestParam(required=false) String payee, @RequestParam(required=false) String phone, @RequestParam(required=false) int type, @RequestParam(required=true) int desc) 
* 
*/ 
@Test
public void testRechargeCnySubmit() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: alipayTransfer(HttpServletRequest request, @RequestParam(required=true) double money, @RequestParam(required=true) String accounts, @RequestParam(required=true) String imageCode, @RequestParam(required=true) int type) 
* 
*/ 
@Test
public void testAlipayTransfer() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: fcapitaloperationStatus(@RequestParam(required=true)int id) 
* 
*/ 
@Test
public void testFcapitaloperationStatus() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: withdrawCnySubmit(HttpServletRequest request, @RequestParam(required=false,defaultValue="0")String tradePwd, @RequestParam(required=true)double withdrawBalance, @RequestParam(required=false,defaultValue="0")String phoneCode, @RequestParam(required=false,defaultValue="0")String totpCode, @RequestParam(required=true)int withdrawBlank) 
* 
*/ 
@Test
public void testWithdrawCnySubmit() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: withdrawBtcSubmit(HttpServletRequest request, @RequestParam(required=true)int withdrawAddr, @RequestParam(required=true)double withdrawAmount, @RequestParam(required=true)String tradePwd, @RequestParam(required=false,defaultValue="0")String totpCode, @RequestParam(required=false,defaultValue="0")String phoneCode, @RequestParam(required=true)int symbol, @RequestParam(required=true)int level) 
* 
*/ 
@Test
public void testWithdrawBtcSubmit() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: cancelRechargeCnySubmit(HttpServletRequest request, int id) 
* 
*/ 
@Test
public void testCancelRechargeCnySubmit() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: detailRecharge(HttpServletRequest request, int id) 
* 
*/ 
@Test
public void testDetailRecharge() throws Exception {
    String result = frontAccountJsonController.detailRecharge(request,276);
    LOG.i(this,result);
} 

/** 
* 
* Method: subRechargeCnySubmit(HttpServletRequest request, int id) 
* 
*/ 
@Test
public void testSubRechargeCnySubmit() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: cancelWithdrawcny(HttpServletRequest request, int id) 
* 
*/ 
@Test
public void testCancelWithdrawcny() throws Exception {
}

/** 
* 
* Method: cancelWithdrawBtc(HttpServletRequest request, @RequestParam(required=true)int id) 
* 
*/ 
@Test
public void testCancelWithdrawBtc() throws Exception { 
//TODO: Test goes here... 
}


    @Override
    public void doTest() {

    }
}
