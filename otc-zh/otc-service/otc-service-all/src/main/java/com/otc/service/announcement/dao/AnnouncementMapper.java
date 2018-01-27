package com.otc.service.announcement.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.announcement.pojo.dto.AnnouncementIndexDto;
import com.otc.facade.announcement.pojo.po.Announcement;
import com.otc.facade.announcement.pojo.vo.AnnouncementVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AnnouncementMapper extends BaseMapper<Announcement, AnnouncementVo> {
    int updateDing(@Param("id") Long id, @Param("isDing") Boolean isDing);

    List<Announcement> queryCountByConditionPage(AnnouncementVo vo);

    List<AnnouncementIndexDto> getIndexList(Integer number);
}