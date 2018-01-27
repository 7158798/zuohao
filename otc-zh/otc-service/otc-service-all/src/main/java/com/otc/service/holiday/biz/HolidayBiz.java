package com.otc.service.holiday.biz;

import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.facade.holiday.pojo.po.Holiday;
import com.otc.facade.holiday.pojo.vo.HolidayVo;
import com.otc.service.holiday.dao.HolidayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zygong on 17-6-2.
 */
@Service
public class HolidayBiz extends AbsBaseBiz<Holiday, HolidayVo, HolidayMapper> {

    @Autowired
    private HolidayMapper holidayMapper;

    @Override
    public HolidayMapper getDefaultMapper() {
        return holidayMapper;
    }

    /**
     * 批量保存
     * @param list
     * @return
     */
    @Transactional
    public boolean saveList(List<String> list){
        boolean flag = false;
        int result = 0;
        if(list != null && !list.isEmpty()){
            holidayMapper.deleteAll();
            result = holidayMapper.saveList(list);
            if(result > 0){
                flag = true;
            }
        }

        return flag;
    }

    /**
     * 删除全部
     * @return
     */
    public boolean deleteAll() {
        boolean flag = false;
        int result = holidayMapper.deleteAll();
        if (result > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 获取本月节假日
     * @return
     */
    public List<String> getHoliday(){
        List<String> holiday = holidayMapper.getHoliday();
        return holiday;
    }
}
