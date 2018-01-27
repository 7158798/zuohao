package com.otc.ctrl;

import com.base.BaseSpringTest;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.web.ctrl.advertisement.AdvertisementWebCtrl;
import com.otc.api.web.ctrl.advertisement.request.AdvertisementIndexReq;
import com.otc.api.web.ctrl.advertisement.request.AdvertisementPostReq;
import com.otc.api.web.ctrl.advertisement.request.CalculationFormulaReq;
import com.otc.api.web.ctrl.advertisement.request.OtherPlatformPriceReq;
import com.otc.api.web.service.WebService;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.CacheHelper;
import com.otc.facade.advertisement.pojo.po.Advertisement;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * Created by zygong on 17-4-26.
 */
public class AdvertisementCtrlTest extends BaseSpringTest {

    @Autowired
    private AdvertisementWebCtrl advertisementWebCtrl;

    @Autowired
    private WebService otc;

    @Test
    public void saveAdvertisementTest(){
        AdvertisementPostReq req = new AdvertisementPostReq();
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        req.setTransactionType(1);
        req.setTradePlatform("比特矿");
        req.setPriceType(1);
        req.setCity("上海");
        req.setCoinType(1L);
        req.setMaxTransCount(new BigDecimal(111));
        req.setMinTransCount(new BigDecimal(1));
        req.setIsOpenSafetyVeri(false);
        req.setPayType("1,2");
        req.setPayTypeName("微信,支付宝");
        req.setPayTypeRemark("bbbbb");
        req.setPremiumRate(5.1f);
        req.setTransactionPrice("1111");
        advertisementWebCtrl.saveAdvertisement(req);
    }

    @Test
    public void getIndexListTest(){
        AdvertisementIndexReq req = new AdvertisementIndexReq();
        req.setPageFilter(1,10);
        WebApiResponse list = advertisementWebCtrl.getIndexList(JsonHelper.obj2JsonStr(req));
        System.out.println(JsonHelper.obj2JsonStr(list, true));
    }


    @Test
    public void getUserList(){
        WebApiBaseReq req = new WebApiBaseReq();
        String token = CacheHelper.buildTestToken("31");
        req.setToken(token);
        req.setPageFilter(1,10);
        WebApiResponse list = advertisementWebCtrl.getUserList(JsonHelper.obj2JsonStr(req));
        System.out.println(JsonHelper.obj2JsonStr(list));
    }

    @Test
    public void payTypeListTest(){
        WebApiResponse list = advertisementWebCtrl.payTypeList();
        System.out.println(JsonHelper.obj2JsonStr(list));
    }

    @Test
    public void otherPlatformPriceTest(){
        OtherPlatformPriceReq req = new OtherPlatformPriceReq();
        req.setSymbol(1l);
        WebApiResponse webApiResponse = advertisementWebCtrl.otherPlatformPrice(JsonHelper.obj2JsonStr(req));
        System.out.println(JsonHelper.obj2JsonStr(webApiResponse));
    }

    @Test
    public void calculationFormulaTest(){
        CalculationFormulaReq req = new CalculationFormulaReq();
        req.setPremiumRate("6");
        req.setPrice("6666");
        req.setSymbol(1L);
        WebApiResponse webApiResponse = advertisementWebCtrl.calculationFormula(req);
        System.out.println(JsonHelper.obj2JsonStr(webApiResponse));
    }

    @Test
    public void calculationFormulaTest2(){
        BigDecimal bigDecimal = otc.advertisementWebFacade.calculatePrice(1l);
        System.out.println(bigDecimal);
    }

    @Test
    public void detailTest(){
        Advertisement detail = otc.advertisementWebFacade.getById(1l);
        System.out.println(JsonHelper.obj2JsonStr(detail));
    }
    @Override
    public void doTest() {

    }
}
