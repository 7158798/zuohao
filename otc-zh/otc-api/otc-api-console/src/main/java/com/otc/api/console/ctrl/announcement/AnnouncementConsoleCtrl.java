package com.otc.api.console.ctrl.announcement;

import com.jucaifu.common.log.LOG;
import com.otc.api.console.base.BaseConsoleCtrl;
import com.otc.api.console.constants.ConsoleApiAnnouncement;
import com.otc.api.console.ctrl.announcement.request.AnnouncementListReq;
import com.otc.api.console.ctrl.announcement.request.AnnouncementSaveReq;
import com.otc.api.console.ctrl.announcement.response.AnnouncementPageListResp;
import com.otc.api.console.exceptions.ConsoleBizException;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.facade.announcement.pojo.po.Announcement;
import com.otc.facade.announcement.pojo.vo.AnnouncementVo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 公告
 * Created by zygong on 17-4-28.
 */
@RestController
public class AnnouncementConsoleCtrl extends BaseConsoleCtrl implements ConsoleApiAnnouncement {

    /**
     * 获取公告列表
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = LIST_ANNOUNCEMENT_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse getList(@RequestBody AnnouncementListReq req){
        LOG.dStart(this, "获取公告列表");
        if(req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        AnnouncementVo vo = new AnnouncementVo();
        vo.setTitle(req.getTitle());
        vo.setNowPage(req.fetchPageFilterPage());
        vo.setPageShow(req.fetchPageFilterSize());

        vo = otc.announcementConsoleFacade.queryCountByConditionPage(vo);
        List<Announcement> announcementList = vo.fatchTransferList();
        List<AnnouncementPageListResp> respList = new ArrayList<>();
        if(respList != null && !announcementList.isEmpty()){
            AnnouncementPageListResp resp = null;
            for(Announcement announcement : announcementList){
                resp = new AnnouncementPageListResp();
                resp.setContent(announcement.getContent());
                resp.setDing(announcement.getIsDing());
                resp.setId(announcement.getId());
                resp.setLastUpdateTime(announcement.getLastUpdateTime());
                resp.setTitle(announcement.getTitle());
                respList.add(resp);
            }
        }
        LOG.dEnd(this, "获取公告列表");

        return buildWebApiPageResponse(vo,respList);
    }

    /**
     * 获取公告详情
     * @param queryJson
     * @return
     */
    @ResponseBody
    @RequestMapping(value = DETAIL_ANNOUNCEMENT_CONSOLE_API, method = RequestMethod.GET)
    public WebApiResponse detail(@PathVariable String queryJson) {
        LOG.dStart(this, "获取公告详情");
        WebApiBaseReq req = encodeJsonStr2Obj(queryJson, WebApiBaseReq.class);
        Long id = req.getId();
        if (req == null || id == null || id == 0) {
            throw new ConsoleBizException("请求参数不能为空");
        }
        Announcement detail = otc.announcementConsoleFacade.detail(id);
        LOG.dEnd(this, "获取公告详情");
        return buildWebApiResponse(SUCCESS, detail);
    }

    /**
     * 保存公告
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = SAVE_ANNOUNCEMENT_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse save(@RequestBody AnnouncementSaveReq req){
        LOG.dStart(this, "保存公告");
        if(req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        boolean ding = false;
        String content = req.getContent();
        String title = req.getTitle();
        ding = req.getDing();
        if(!checkParam(content, title)){
            throw new ConsoleBizException("请求参数不能为空");
        }

        Announcement announcement = new Announcement();
        announcement.setTitle(title);
        announcement.setContent(content);
        announcement.setLastUpdateTime(new Date());
        announcement.setIsDing(ding);
        int save = otc.announcementConsoleFacade.save(announcement);
        LOG.dEnd(this, "保存公告");
        if(save != 0){
            return buildWebApiResponse(SUCCESS, null);
        }else{
            return buildWebApiResponse(FAILURE, null);
        }
    }

    /**
     * 更新公告
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = UPDATE_ANNOUNCEMENT_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse update(@RequestBody AnnouncementSaveReq req){
        LOG.dStart(this, "更新公告");
        if(req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        Long id = req.getId();
        String content = req.getContent();
        String title = req.getTitle();
        Boolean ding = req.getDing();
        if(!checkParam(content, title)){
            throw new ConsoleBizException("请求参数不能为空");
        }

        Announcement announcement = new Announcement();
        announcement.setId(id);
        announcement.setTitle(title);
        announcement.setContent(content);
        announcement.setLastUpdateTime(new Date());
        announcement.setIsDing(ding);
        int update = otc.announcementConsoleFacade.update(announcement);
        LOG.dEnd(this, "更新公告");
        if(update != 0){
            return buildWebApiResponse(SUCCESS, null);
        }else{
            return buildWebApiResponse(FAILURE, null);
        }
    }

    /**
     * 推荐
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = ISDING_ANNOUNCEMENT_CONSOLE_API,method = RequestMethod.POST)
    public WebApiResponse isDing(@RequestBody AnnouncementSaveReq req){
        LOG.dStart(this, "推荐");
        if(req == null){
            throw new ConsoleBizException("请求参数不能为空");
        }
        Long id = req.getId();
        Boolean ding = req.getDing();
        if(!checkParam(id, ding)){
            throw new ConsoleBizException("请求参数不能为空");
        }

        boolean update = otc.announcementConsoleFacade.updateDing(id, ding);
        LOG.dEnd(this, "推荐");
        if(update){
            return buildWebApiResponse(SUCCESS, null);
        }else{
            return buildWebApiResponse(FAILURE, null);
        }
    }

    /**
     * 删除
     * @param queryJson
     * @return
     */
    @ResponseBody
    @RequestMapping(value = DELETE_ANNOUNCEMENT_CONSOLE_API,method = RequestMethod.GET)
    public WebApiResponse delete(@PathVariable String queryJson){
        LOG.dStart(this, "删除");
        WebApiBaseReq req = encodeJsonStr2Obj(queryJson, WebApiBaseReq.class);
        if(req == null || req.getId() == null){
            throw new ConsoleBizException("请求参数不能为空");
        }

        int del = otc.announcementConsoleFacade.delete(req.getId());
        LOG.dEnd(this, "删除");
        if(del > 0){
            return buildWebApiResponse(SUCCESS, null);
        }else{
            return buildWebApiResponse(FAILURE, null);
        }
    }
}
