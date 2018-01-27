package com.otc.service.holiday.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.holiday.pojo.po.Holiday;
import com.otc.facade.holiday.pojo.vo.HolidayVo;

import java.util.List;

public interface HolidayMapper extends BaseMapper<Holiday, HolidayVo> {

    /**
     * 批量保存
     * @return
     */
    int saveList(List<String> list);

    /**
     * 删除全部
     * @return
     */
    int deleteAll();

    /**
     * 获取本月节假日
     * @return
     */
    List<String> getHoliday();
}