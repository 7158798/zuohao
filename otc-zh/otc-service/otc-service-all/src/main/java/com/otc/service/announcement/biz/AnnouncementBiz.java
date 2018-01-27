package com.otc.service.announcement.biz;

import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.facade.announcement.pojo.dto.AnnouncementIndexDto;
import com.otc.facade.announcement.pojo.po.Announcement;
import com.otc.facade.announcement.pojo.vo.AnnouncementVo;
import com.otc.service.announcement.dao.AnnouncementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zygong on 17-4-28.
 */
@Service
@Transactional
public class AnnouncementBiz extends AbsBaseBiz<Announcement, AnnouncementVo, AnnouncementMapper> {
    @Autowired
    private AnnouncementMapper announcementMapper;
    @Override
    public AnnouncementMapper getDefaultMapper() {
        return announcementMapper;
    }

    public boolean updateDing(Long id, Boolean isDing){
        boolean flag = false;
        int result = announcementMapper.updateDing(id, isDing);
        if(result != 0){
            flag = true;
        }
        return flag;
    }

    public AnnouncementVo queryCountByConditionPage(AnnouncementVo vo){
        List<Announcement> announcements = announcementMapper.queryCountByConditionPage(vo);
        if(announcements != null){
            vo.setResultList(announcements);
        }
        return vo;
    }

    public List<AnnouncementIndexDto> getIndexList(Integer number){
        return announcementMapper.getIndexList(number);
    }
}
