package com.otc.facade.holiday.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;
import java.io.Serializable;

public class Holiday extends BasePo implements Serializable {

    /**
     * 当月周末节假日时间，对应表字段为：t_holiday.Holiday
     */
    private String holiday;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday == null ? null : holiday.trim();
    }
}