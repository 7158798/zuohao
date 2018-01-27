package com.jucaifu.common.util;

import java.util.Date;

import com.base.BaseTest;
import com.jucaifu.common.log.LOG;

/**
 * Created by scofieldcai-dev on 15/9/1.
 */
public class TestDateHelper extends BaseTest {

    @Override
    public void doTest() {

        String currentStrDate = DateHelper.getCurrentStrDate(DateHelper.DateFormatType.YearMonthDay_HourMinute);
        LOG.d(this, currentStrDate);

        Date date = DateHelper.string2Date(currentStrDate, DateHelper.DateFormatType.YearMonthDay);
        LOG.d(this, date);

        String strDate = DateHelper.date2String(date, DateHelper.DateFormatType.YearMonth);
        LOG.d(this, strDate);

        String formatTime = DateHelper.formatTime(1000 * 10 * 1000L);
        LOG.d(this, formatTime);

        //////////////////////////////
        //// 日期毫秒级
        //////////////////////////////
        long timeMillis = new Date().getTime();
        LOG.d(this, timeMillis);
        LOG.d(this, System.currentTimeMillis());
    }
}

