package com.otc.service.announcement.impl.console;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.announcement.pojo.po.Announcement;
import com.otc.facade.announcement.pojo.vo.AnnouncementVo;
import com.otc.facade.announcement.service.console.AnnouncementConsoleFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.announcement.impl.AnnouncementFacadeImpl;
import org.springframework.stereotype.Component;

/**
 * Created by zygong on 17-4-28.
 */
@Component
@Service
public class AnnouncementConsoleFacadeImpl extends AnnouncementFacadeImpl implements AnnouncementConsoleFacade {
    @Override
    public boolean updateDing(Long id, Boolean isDing) {
        return OTCBizPool.getInstance().announcementBiz.updateDing(id, isDing);
    }

    @Override
    public int save(Announcement announcement) {
        return OTCBizPool.getInstance().announcementBiz.insert(announcement);
    }

    @Override
    public int update(Announcement announcement) {
        return OTCBizPool.getInstance().announcementBiz.update(announcement);
    }

    @Override
    public int delete(Long id) {
        return OTCBizPool.getInstance().announcementBiz.delete(id);
    }
}
