package com.otc.facade.announcement.service;

import com.otc.facade.announcement.pojo.po.Announcement;
import com.otc.facade.announcement.pojo.vo.AnnouncementVo;

/**
 * Created by zygong on 17-4-28.
 */
public interface AnnouncementFacade {
    Announcement detail(Long id);
    AnnouncementVo queryCountByConditionPage(AnnouncementVo vo);
}
