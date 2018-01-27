package com.jucaifu.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.jucaifu.common.constants.StringPool;
import com.jucaifu.common.constants.TimeConstant;

/**
 * DateHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/1.
 */
public final class DateHelper implements TimeConstant {
    private DateHelper() {
    }

    /**
     * ****************************************
     *
     * @author : scofieldcai@126.com
     * @ClassName : DateFormatType
     * @date : 2013-6-22 下午10:58:34
     * @Description: 日期格式化的类型
     * ****************************************
     **/
    public enum DateFormatType {
        YearMonthDay_HourMinuteSecond_MESC("yyyyMMddHHmmssSSS"),
        YearMonthDay_HourMinuteSecond_Custom("yyyy/MM/dd HH:mm:ss"),
        YearMonthDay_HourMinuteSecond_Log("yyyy-MM-dd HH_mm_ss"),
        YearMonthDay_HourMinuteSecond("yyyy-MM-dd HH:mm:ss"),
        YearMonthDayHourMinuteSecond("yyyyMMddHHmmss"),
        YearMonthDay_HourMinute("yyyy-MM-dd HH:mm"),
        YearMonthDay_Hour("yyyy-MM-dd HH"),
        YearMonthDay("yyyy-MM-dd"),
        YearMonthDay_Log("yyyyMMdd"),
        YearMonth("yyyy-MM"),
        Year("yyyy"),
        HourMinuteSecond("HH:mm:ss"),
        HourMinute("HH:mm"),
        MonthDay_HourMinute("MM-dd HH:mm");


        private final String dateFormat;

        private SimpleDateFormat sdf;

        DateFormatType(String dateFormat) {
            this.dateFormat = dateFormat;
        }

        public SimpleDateFormat getDateFormat() {
            if (sdf == null) {
                sdf = new SimpleDateFormat(dateFormat);
            }
            return sdf;
        }

        public String getFormat() {
            return dateFormat;
        }
    }


    /*****************************************
     * @param strDate
     * @param dateFormatType
     * @return
     * @author : scofieldcai@126.com
     * @Title : string2Date
     * @returnType : Date
     * @Description: 根据字符串日期格式化时间。
     * 主要功能：用于比较，时间的排序。
     *****************************************/
    public static final Date string2Date(String strDate, DateFormatType dateFormatType) {
        SimpleDateFormat sdf = dateFormatType.getDateFormat();
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
//            LOG.e(DateHelper.class,"",e);
        }
        return date;
    }

    /*****************************************
     * @param date
     * @param dateFormatType
     * @return
     * @author : scofieldcai@126.com
     * @Title : date2String
     * @returnType : String
     * @Description: 将Date对象转化为指定格式的日期字符串。
     *****************************************/
    public static final String date2String(Date date, DateFormatType dateFormatType) {
        SimpleDateFormat sdf = dateFormatType.getDateFormat();
        String strDate = StringPool.BLANK;
        if (date != null) {
            strDate = sdf.format(date);
        }
        return strDate;
    }


    /*****************************************
     * @param second
     * @param dateFormatType
     * @return
     * @author : scofieldcai@126.com
     * @Title : second2DateString
     * @returnType : String
     * @Description: 秒格式化为日期字符串。
     *****************************************/
    public static final String second2DateString(int second, DateFormatType dateFormatType) {
        Date date = new Date(second * 1000L);
        return date2String(date, dateFormatType);
    }

    /*****************************************
     * @param dateFormatType
     * @return
     * @author : scofieldcai@126.com
     * @Title : getCurrentStrDate
     * @returnType : String
     * @Description: 获取当前时间，可以指定日期格式。
     *****************************************/
    public static final String getCurrentStrDate(DateFormatType dateFormatType) {
        Date date = new Date(System.currentTimeMillis());
        return date2String(date, dateFormatType);
    }


    /*****************************************
     * @param millisUntilFinished
     * @return
     * @author : scofieldcai@126.com
     * @Title : formatTime
     * @returnType : String
     * @Description: 根据毫秒数，格式化 00:00:10的格式。 主要是用于计时器。
     * 注意最大的时间标准就是小时。小时上面的时间单位不再分割。
     *****************************************/
    public static final String formatTime(long millisUntilFinished) {
        // <1>格式化秒钟
        final long _second = (millisUntilFinished % ONE_MINUTE_UNIT_MILLISECONDS) / ONE_SECOND_UNIT_MILLISECONDS; // 秒
        final String second;
        if (_second < 10) {
            second = "0" + _second;
        } else {
            second = String.valueOf(_second);
        }

        //<2>格式化分钟
        final long _minute = (millisUntilFinished % ONE_HOUR_UNIT_MILLISECONDS) / ONE_MINUTE_UNIT_MILLISECONDS;
        String minute;
        if (_minute < 10) {
            minute = "0" + _minute;
        } else {
            minute = String.valueOf(_minute);
        }

        //<3>格式化小时
        final long _hour = millisUntilFinished / ONE_HOUR_UNIT_MILLISECONDS;
        String hour;
        if (_hour < 10) {
            hour = "0" + _hour;
        } else {
            hour = String.valueOf(_hour);
        }

        // 最终格式： 00:01:05
        String time = hour + ":" + minute + ":" + second;

        return time;
    }

    /**
     * 获取当前时间，时分设置成传过来的时分
     *
     * @param hourMinute
     * @return
     */
    public static final Date getCurrentDateByHHSS(String hourMinute) {
        return retryAssignDateByHHSS(null, hourMinute);
    }

    /**
     * 把指定时间的时分设置成传过来的时分
     *
     * @param date       指定时间
     * @param hourMinute 重新设置的时分
     * @return
     */
    public static final Date retryAssignDateByHHSS(Date date, String hourMinute) {

        Date tempDate = date;
        if (tempDate == null) {
            tempDate = new Date(System.currentTimeMillis());
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(tempDate);

        Date temp = string2Date(hourMinute, DateFormatType.HourMinute);
        if (temp == null) {
            return null;
        }
        Calendar calTemp = Calendar.getInstance();
        calTemp.setTime(temp);

        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), calTemp.get(Calendar.HOUR_OF_DAY), calTemp.get(Calendar.MINUTE), 0);

        return cal.getTime();
    }

    public static final int getDayOfWeek(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekDay =  calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if(weekDay == 0){
            weekDay = 7;
        }
        return weekDay;
    }

    public static  final int compareForHHMMSS(Date date1,Date date2){
         String str1 = date2String(date1,DateFormatType.HourMinuteSecond);
         String str2 = date2String(date2,DateFormatType.HourMinuteSecond);

        Date datehms1 = string2Date(str1, DateFormatType.HourMinuteSecond);
        Date datehms2 = string2Date(str2,DateFormatType.HourMinuteSecond);

        return  datehms1.compareTo(datehms2);
    }

    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    public static Date getDateForYYYYMMDD(Date date){
        if(date == null){
            return  null;
        }
        String str = date2String(date, DateFormatType.YearMonthDay);
        return string2Date(str,DateFormatType.YearMonthDay);
    }

    /**
     * 比较开始日期和结束日期相处的秒钟
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Long getDifferSecond(Date beginDate, Date endDate){
        try {
            Calendar end = Calendar.getInstance();
            end.setTime(endDate);
            long endMillis = end.getTimeInMillis();

            Calendar begin = Calendar.getInstance();
            begin.setTime(beginDate);
            long beginMillis = begin.getTimeInMillis();

            return beginMillis - endMillis;

        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {


        Date date1 = new Date();
        Date date2 = string2Date("2016-09-14 19:22:14",DateFormatType.YearMonthDay_HourMinuteSecond);
        int i = compareForHHMMSS(date1,date2);

      //  int day = getDayOfWeek(new Date());

        System.out.println(new Date().getTime());

    }
}
