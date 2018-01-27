package com.otc.service.announcement.impl;


import com.otc.facade.announcement.pojo.po.Announcement;
import com.otc.facade.announcement.pojo.vo.AnnouncementVo;
import com.otc.facade.announcement.service.AnnouncementFacade;
import com.otc.pool.OTCBizPool;

/**
 * Created by zygong on 17-4-28.
 */
public class AnnouncementFacadeImpl implements AnnouncementFacade {

    @Override
    public Announcement detail(Long id) {
        return OTCBizPool.getInstance().announcementBiz.select(id);
    }

    @Override
    public AnnouncementVo queryCountByConditionPage(AnnouncementVo vo) {
        return OTCBizPool.getInstance().announcementBiz.queryCountByConditionPage(vo);
    }
}
