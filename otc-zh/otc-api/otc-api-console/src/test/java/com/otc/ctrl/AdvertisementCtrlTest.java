package com.otc.ctrl;

import com.base.BaseSpringTest;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.console.ctrl.advertisement.AdvertisementConsoleCtrl;
import com.otc.api.console.ctrl.advertisement.PriceFormulaConsoleCtrl;
import com.otc.api.console.ctrl.advertisement.TransactionDesConsoleCtrl;
import com.otc.api.console.ctrl.advertisement.request.*;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.CacheHelper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zygong on 17-4-26.
 */
public class AdvertisementCtrlTest extends BaseSpringTest {

    @Autowired
    private AdvertisementConsoleCtrl advertisementConsoleCtrl;
    @Autowired
    private TransactionDesConsoleCtrl transactionDesConsoleCtrl;
    @Autowired
    private PriceFormulaConsoleCtrl priceFormulaConsoleCtrl;

    @Test
    public void getListTest(){
        AdvertisementListReq req = new AdvertisementListReq();
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        req.setFilter("31");
        req.setStatus(1);
        req.setPageFilter(1,3);
        WebApiResponse list = advertisementConsoleCtrl.getList(req);
        System.out.println(JsonHelper.obj2JsonStr(list));
    }

    @Test
    public void updateStatusTest(){
        AdvertisementCloseReq req = new AdvertisementCloseReq();
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        req.setId(1L);
        WebApiResponse webApiResponse = advertisementConsoleCtrl.updateStatus(req);
        System.out.println(JsonHelper.obj2JsonStr(webApiResponse));
    }

    @Test
    public void saveTest(){
        TransactionDesSaveReq req = new TransactionDesSaveReq();
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        req.setType(2);
        req.setTitle("aaaaa");
        req.setContent("bbbbbbbcccccccdddddd");
        WebApiResponse save = transactionDesConsoleCtrl.save(req);
        System.out.println(JsonHelper.obj2JsonStr(save));
    }

    @Test
    public void detailTest(){
        TransactionDesDetailReq req = new TransactionDesDetailReq();
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        req.setId(3l);
        WebApiResponse detail = transactionDesConsoleCtrl.detail(JsonHelper.obj2JsonStr(req));
        System.out.println(JsonHelper.obj2JsonStr(detail));
    }

    @Test
    public void deleteTest(){
        TransactionDesDetailReq req = new TransactionDesDetailReq();
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        req.setId(1l);
        WebApiResponse detail = transactionDesConsoleCtrl.delete(JsonHelper.obj2JsonStr(req));
        System.out.println(JsonHelper.obj2JsonStr(detail));
    }

    @Test
    public void getListTest2(){
        PriceFormulaListReq req = new PriceFormulaListReq();
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        req.setCoinId(1);
        req.setPageFilter(1,3);
        WebApiResponse list = priceFormulaConsoleCtrl.getList(req);
        System.out.println(JsonHelper.obj2JsonStr(list));
    }

    @Test
    public void savePriceFormula(){
        PriceFormulaSaveReq req = new PriceFormulaSaveReq();
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        req.setCoinId(1L);
        req.setPriceFormula("P*(1+0.001*C)");
        req.setPlatformName("aaaa/bbbb/ccc");

        WebApiResponse save = priceFormulaConsoleCtrl.save(req);
        System.out.println(JsonHelper.obj2JsonStr(save));
    }

    @Test
    public void detailPriceFormulaTest(){
        WebApiBaseReq req = new WebApiBaseReq();
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        req.setId(1l);

        WebApiResponse detail = priceFormulaConsoleCtrl.detail(JsonHelper.obj2JsonStr(req));
        System.out.println(JsonHelper.obj2JsonStr(detail));
    }

    @Override
    public void doTest() {

    }
}
