package com.otc.facade.announcement.service.console;

import com.otc.facade.announcement.pojo.po.Announcement;
import com.otc.facade.announcement.pojo.vo.AnnouncementVo;
import com.otc.facade.announcement.service.AnnouncementFacade;

/**
 * Created by zygong on 17-4-28.
 */
public interface AnnouncementConsoleFacade extends AnnouncementFacade {
    boolean updateDing(Long id, Boolean isDing);
    int save(Announcement announcement);
    int update(Announcement announcement);

    /**
     * 删除公告
     * @param id
     * @return
     */
    int delete(Long id);
}
