package com.otc.ctrl;

import com.base.BaseSpringTest;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.web.ctrl.Announcement.AnnouncementWebCtrl;
import com.otc.api.web.ctrl.Announcement.request.AnnouncementIndexListReq;
import com.otc.api.web.ctrl.advertisement.request.AdvertisementPostReq;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.CacheHelper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.Json;

/**
 * Created by zygong on 17-4-28.
 */
public class AnnouncemenWebCtrlTest extends BaseSpringTest {
    @Autowired
    public AnnouncementWebCtrl announcementWebCtrl;

    @Test
    public void getNumber(){
        AnnouncementIndexListReq req = new AnnouncementIndexListReq();
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        WebApiResponse userList = announcementWebCtrl.getIndexList(JsonHelper.obj2JsonStr(req));
        System.out.println(JsonHelper.obj2JsonStr(userList));
    }

    @Test
    public void detailTest(){
        WebApiBaseReq req = new WebApiBaseReq();
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        req.setId(3l);
        WebApiResponse detail = announcementWebCtrl.detail(JsonHelper.obj2JsonStr(req));
        System.out.println(JsonHelper.obj2JsonStr(detail));
    }

    @Override
    public void doTest() {

    }
}
