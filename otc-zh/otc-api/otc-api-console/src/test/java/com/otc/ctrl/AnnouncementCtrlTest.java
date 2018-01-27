package com.otc.ctrl;

import com.base.BaseSpringTest;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.console.ctrl.announcement.AnnouncementConsoleCtrl;
import com.otc.api.console.ctrl.announcement.request.AnnouncementListReq;
import com.otc.api.console.ctrl.announcement.request.AnnouncementSaveReq;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.CacheHelper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zygong on 17-4-28.
 */
public class AnnouncementCtrlTest extends BaseSpringTest {
    @Autowired
    private AnnouncementConsoleCtrl announcementConsoleCtrl;

    @Test
    public void saveTest(){
        AnnouncementSaveReq req = new AnnouncementSaveReq();
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        req.setTitle("aaaa");
        req.setContent("aaabbbccc");
        req.setDing(false);
        WebApiResponse save = announcementConsoleCtrl.save(req);
        System.out.println(JsonHelper.obj2JsonStr(save));
    }

    @Test
    public void detailTest(){
        WebApiBaseReq req = new WebApiBaseReq();
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        req.setId(1l);
        WebApiResponse detail = announcementConsoleCtrl.detail(JsonHelper.obj2JsonStr(req));
        System.out.println(JsonHelper.obj2JsonStr(detail));
    }

    @Test
    public void updateTest(){
        AnnouncementSaveReq req = new AnnouncementSaveReq();
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        req.setId(1l);
        req.setTitle("bbb");
        req.setContent("aaabbbccc");
        req.setDing(false);
        WebApiResponse save = announcementConsoleCtrl.update(req);
        System.out.println(JsonHelper.obj2JsonStr(save));
    }

    @Test
    public void updateDingTest(){
        AnnouncementSaveReq req = new AnnouncementSaveReq();
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        req.setId(1l);
        req.setDing(true);
        WebApiResponse save = announcementConsoleCtrl.isDing(req);
        System.out.println(JsonHelper.obj2JsonStr(save));
    }

    @Test
    public void getListTest(){
        AnnouncementListReq req = new AnnouncementListReq();
        String token = CacheHelper.buildTestToken("1");
        req.setToken(token);
        req.setTitle("bbb");
        req.setPageFilter(1,3);
        WebApiResponse list = announcementConsoleCtrl.getList(req);
        System.out.println(JsonHelper.obj2JsonStr(list));
    }

    @Override
    public void doTest() {

    }
}
