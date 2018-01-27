package com.otc.service.announcement.impl.web;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.announcement.pojo.dto.AnnouncementIndexDto;
import com.otc.facade.announcement.pojo.po.Announcement;
import com.otc.facade.announcement.service.web.AnnouncementWebFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.announcement.impl.AnnouncementFacadeImpl;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zygong on 17-4-28.
 */
@Component
@Service
public class AnnouncementWebFacadeImpl extends AnnouncementFacadeImpl implements AnnouncementWebFacade {
    @Override
    public List<AnnouncementIndexDto> getIndexList(Integer number) {
        return OTCBizPool.getInstance().announcementBiz.getIndexList(number);
    }
}
