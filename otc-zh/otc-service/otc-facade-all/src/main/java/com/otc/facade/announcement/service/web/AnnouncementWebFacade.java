package com.otc.facade.announcement.service.web;

import com.otc.facade.announcement.pojo.dto.AnnouncementIndexDto;
import com.otc.facade.announcement.service.AnnouncementFacade;

import java.util.List;

/**
 * Created by zygong on 17-4-28.
 */
public interface AnnouncementWebFacade extends AnnouncementFacade {
    List<AnnouncementIndexDto> getIndexList(Integer number);
}
