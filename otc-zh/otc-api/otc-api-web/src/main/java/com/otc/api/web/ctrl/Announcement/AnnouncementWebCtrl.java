package com.otc.api.web.ctrl.Announcement;

import com.jucaifu.common.annotation.token.TokenValidateAnno;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.web.base.BaseWebCtrl;
import com.otc.api.web.constants.WebApiAnnouncement;
import com.otc.api.web.ctrl.Announcement.request.AnnouncementIndexListReq;
import com.otc.api.web.ctrl.Announcement.request.AnnouncementListReq;
import com.otc.api.web.exceptions.WebBizException;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.facade.announcement.pojo.dto.AnnouncementIndexDto;
import com.otc.facade.announcement.pojo.po.Announcement;
import com.otc.facade.announcement.pojo.vo.AnnouncementVo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zygong on 17-4-28.
 */
@RestController
public class AnnouncementWebCtrl extends BaseWebCtrl implements WebApiAnnouncement {

    /**
     * 获取公告列表
     * @param req
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = LIST_ANNOUNCEMENT_WEB_API,method = RequestMethod.POST)
    public WebApiResponse getList(@RequestBody AnnouncementListReq req){
        LOG.dStart(this, "获取公告列表");
        if(req == null){
            throw new WebBizException("请求参数不能为空");
        }
        AnnouncementVo vo = new AnnouncementVo();
        vo.setTitle(req.getTitle());
        vo.setNowPage(req.fetchPageFilterPage());
        vo.setPageShow(req.fetchPageFilterSize());

        vo = otc.announcementWebFacade.queryCountByConditionPage(vo);
        List<Announcement> respList = vo.fatchTransferList();
        LOG.dEnd(this, "获取公告列表");

        return buildWebApiPageResponse(vo,respList);
    }


    /**
     * 获取首页公告
     * @param queryJson
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = INDEXLIST_ANNOUNCEMENT_WEB_API,method = RequestMethod.GET)
    public WebApiResponse getIndexList(@PathVariable String queryJson){
        LOG.dStart(this, "获取首页公告");
        AnnouncementIndexListReq req = JsonHelper.jsonStr2Obj(queryJson, AnnouncementIndexListReq.class);
        Integer number = 3;
        if(req != null && req.getNumber() != null){
            number = req.getNumber();
        }
        List<AnnouncementIndexDto> indexList = otc.announcementWebFacade.getIndexList(number);
        LOG.dEnd(this, "获取首页公告");
        return buildWebApiResponse(SUCCESS, indexList);
    }

    /**
     * 获取详情
     * @param queryJson
     * @return
     */
    @TokenValidateAnno(skip = true)
    @RequestMapping(value = DETAIL_ANNOUNCEMENT_WEB_API, method = RequestMethod.GET)
    public WebApiResponse detail(@PathVariable String queryJson) {
        LOG.dStart(this, "获取详情");
        WebApiBaseReq req = encodeJsonStr2Obj(queryJson, WebApiBaseReq.class);
        Long id = req.getId();
        if (req == null || id == null || id == 0) {
            throw new WebBizException("请求参数不能为空");
        }
        Announcement detail = otc.announcementWebFacade.detail(id);
        LOG.dEnd(this, "获取详情");
        return buildWebApiResponse(SUCCESS, detail);
    }
}
