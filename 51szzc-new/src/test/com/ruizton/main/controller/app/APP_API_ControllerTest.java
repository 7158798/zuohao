package test.com.ruizton.main.controller.app;

import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.controller.app.APP_API_Controller;
import com.ruizton.main.controller.app.AppAlipayCtrl;
import com.ruizton.main.log.LOG;
import org.apache.poi.util.SystemOutLogger;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import test.com.ruizton.base.BaseSpringTest;

/**
 * APP_API_Controller Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>二月 27, 2017</pre>
 */
public class APP_API_ControllerTest extends BaseSpringTest {

    @Autowired
    private APP_API_Controller app_api_controller;

    @Autowired
    private RealTimeData realTimeData;

    @Autowired
    private AppAlipayCtrl appAlipayCtrl;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    public APP_API_ControllerTest() {
        setRequest();
    }

    /**
     * Method: appApi(HttpServletRequest request, @RequestParam(required=false,defaultValue="0")String action)
     */
    @Test
    public void testAppApi() throws Exception {

    }

    /**
     * Method: TradePassword(HttpServletRequest request)
     */
    @Test
    public void testTradePassword() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: BtcTradeSubmit(HttpServletRequest request)
     */
    @Test
    public void testBtcTradeSubmit() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: GetEntrustInfo(HttpServletRequest request)
     */
    @Test
    public void testGetEntrustInfo() throws Exception {
        app_api_controller.setLoginToken(25);
        request.setParameter("type", "1");
        request.setParameter("symbol", "1");
        request.setParameter("currentPage", "1");
        System.out.println("------------------------------------------------------------------");
        String s = app_api_controller.GetEntrustInfo(request);
        LOG.i(this.getClass(), s);
        System.out.println("------------------------------------------------------------------");
    }

    /**
     * Method: CancelEntrust(HttpServletRequest request)
     */
    @Test
    public void testCancelEntrust() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: GetMarketData(HttpServletRequest request)
     */
    @Test
    public void testGetMarketData() throws Exception {
//TODO: Test goes here...
        app_api_controller.setLoginToken(25);
        request.setParameter("id", "1");
        System.out.println("------------------------------------------------------------------");
        String s = app_api_controller.GetMarketData(request);
        LOG.i(this.getClass(), s);
        System.out.println("------------------------------------------------------------------");
    }

    /**
     * Method: GetMarketDepth(HttpServletRequest request)
     */
    @Test
    public void testGetMarketDepth() throws Exception {
        app_api_controller.setLoginToken(11);
        request.setParameter("id", "1");
        String s = app_api_controller.GetMarketDepth(request);
        LOG.i(this.getClass(), s);
    }

    /**
     * Method: GetNews(HttpServletRequest request)
     */
    @Test
    public void testGetNews() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: GetVersion(HttpServletRequest request)
     */
    @Test
    public void testGetVersion() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: GetAbout(HttpServletRequest request)
     */
    @Test
    public void testGetAbout() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: GetBtcRechargeListRecord(HttpServletRequest request)
     */
    @Test
    public void testGetBtcRechargeListRecord() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: GetCoinListInfo(HttpServletRequest request)
     */
    @Test
    public void testGetCoinListInfo() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: GetAccountInfo(HttpServletRequest request)
     */
    @Test
    public void testGetAccountInfo() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: UserRegister(HttpServletRequest request)
     */
    @Test
    public void testUserRegister() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: UserLogin(HttpServletRequest request)
     */
    @Test
    public void testUserLogin() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: buyBtcSubmit(HttpServletRequest request)
     */
    @Test
    public void testBuyBtcSubmit() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: sellBtcSubmit(HttpServletRequest request)
     */
    @Test
    public void testSellBtcSubmit() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: GetIntrolinfo(HttpServletRequest request)
     */
    @Test
    public void testGetIntrolinfo() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: GetIntrolDetail(HttpServletRequest request)
     */
    @Test
    public void testGetIntrolDetail() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: ValidateIdentity(HttpServletRequest request)
     */
    @Test
    public void testValidateIdentity() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: ViewValidateIdentity(HttpServletRequest request)
     */
    @Test
    public void testViewValidateIdentity() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: CnyRecharge(HttpServletRequest request)
     */
    @Test
    public void testCnyRecharge() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: SendValidateCode(HttpServletRequest request)
     */
    @Test
    public void testSendValidateCode() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: bindPhone(HttpServletRequest request)
     */
    @Test
    public void testBindPhone() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: UnbindPhone(HttpServletRequest request)
     */
    @Test
    public void testUnbindPhone() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: GetWithdrawBankList(HttpServletRequest request)
     */
    @Test
    public void testGetWithdrawBankList() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: SetWithdrawCnyBankInfo(HttpServletRequest request)
     */
    @Test
    public void testSetWithdrawCnyBankInfo() throws Exception {
        app_api_controller.setLoginToken(11);
        request.setParameter("account", "6228481698729890079");
        request.setParameter("openBankType", "3");
        request.setParameter("address", "dddd");

        app_api_controller.SetWithdrawCnyBankInfo(request);
    }

    /**
     * Method: WithDrawCny(HttpServletRequest request)
     */
    @Test
    public void testWithDrawCny() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: GetWithdrawBtcAddress(HttpServletRequest request)
     */
    @Test
    public void testGetWithdrawBtcAddress() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: SetWithdrawBtcAddr(HttpServletRequest request)
     */
    @Test
    public void testSetWithdrawBtcAddr() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: WithdrawBtcSubmit(HttpServletRequest request)
     */
    @Test
    public void testWithdrawBtcSubmit() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: changePassword(HttpServletRequest request)
     */
    @Test
    public void testChangePassword() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: GetAllRecords(HttpServletRequest request)
     */
    @Test
    public void testGetAllRecords() throws Exception {
        app_api_controller.setLoginToken(11);
        request.setParameter("type", "3");
        request.setParameter("symbol", "1");
        request.setParameter("currentPage", "1");

        String result = app_api_controller.GetAllRecords(request);
        System.out.println("**************************************************************************");
        LOG.i(this, result);
        System.out.println("**************************************************************************");
    }

    /**
     * Method: SendMessageCode(HttpServletRequest request)
     */
    @Test
    public void testSendMessageCode() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: SendMailCode(HttpServletRequest request)
     */
    @Test
    public void testSendMailCode() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: FindLoginPassword(HttpServletRequest request)
     */
    @Test
    public void testFindLoginPassword() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: RechargeCny(HttpServletRequest request)
     */
    @Test
    public void testRechargeCny() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: main(String args[])
     */
    @Test
    public void testMain() throws Exception {
//TODO: Test goes here... 
    }


    /**
     * Method: getVirtualCoinAsset(Fuser fuser)
     */
    @Test
    public void testGetVirtualCoinAsset() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = APP_API_Controller.getClass().getMethod("getVirtualCoinAsset", Fuser.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    @Test
    public void queryArticle() {
        //app_api_controller.setLoginToken(11);
        request.setParameter("currentPage", "1");

        String result = app_api_controller.queryArticle(request);
        LOG.i(this, result);
    }


    @Test
    public void alipayTransfer() {
//        String str = appAlipayCtrl.alipayorder();
//        LOG.i(this, str);
    }


    @Override
    public void doTest() {

    }
}
