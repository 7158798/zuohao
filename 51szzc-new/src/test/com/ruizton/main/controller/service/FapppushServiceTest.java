package test.com.ruizton.main.controller.service;

import com.ruizton.main.controller.app.request.AppPushReq;
import com.ruizton.main.controller.app.request.PriceRange;
import com.ruizton.main.dao.FuserDAO;
import com.ruizton.main.dao.zuohao.FapppushDAO;
import com.ruizton.main.model.Fapppush;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.vo.FapppushPhone;
import com.ruizton.main.service.app.FapppushService;
import com.ruizton.util.JsonHelper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.com.ruizton.base.BaseSpringTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zygong on 17-4-13.
 */
public class FapppushServiceTest extends BaseSpringTest {
    @Autowired
    private FapppushService fapppushService;
    @Autowired
    private FuserDAO fuserDAO;

    @Override
    public void doTest() {}

    @Test
    public void test(){
//        fapppushService.findPushList(1, 4000d);
    }

    @Test
    public void test2(){
        Fapppush fapppush = new Fapppush();
        fapppush.setFuser(170);
        fapppushService.saveObj(fapppush);
    }

    @Test
    public void test3(){
        fapppushService.updateSendTime(2, 30, Timestamp.valueOf(LocalDateTime.now()));
    }

    @Test
    public void test4(){
        List<FapppushPhone> pushList = fapppushService.findPushList(2, 30, Timestamp.valueOf(LocalDateTime.now()));
        System.out.println(JsonHelper.obj2JsonStr(pushList));
    }

    @Test
    public void test5(){
        // 短信
        PriceRange priceRange = null;
        Fuser fuser = fuserDAO.findById(170);
        AppPushReq appPushReq = new AppPushReq();
        List<PriceRange> priceRangeList = new ArrayList<PriceRange>();
        priceRange = new PriceRange();
        priceRange.setHighPrice(9000);
        priceRange.setId(1);
        priceRange.setLowPrice(8000);
        priceRangeList.add(priceRange);

        priceRange = new PriceRange();
        priceRange.setHighPrice(40);
        priceRange.setId(2);
        priceRange.setLowPrice(36);
        priceRangeList.add(priceRange);

        appPushReq.setPhoneCode("3937094116774967004");
        appPushReq.setPhoneType(1);
        appPushReq.setSendType(2);


        fapppushService.updateSendSMS(fuser, appPushReq);

    }
}

