package com.ruizton.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DateHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/1.
 */
public final class DateHelper {
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
        String strDate = "";
        if (date != null) {
            strDate = sdf.format(date);
        }
        return strDate;
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

    /**
     * 获取系统日期的前几天
     * @param day  前几天，按日倒推
     * @return
     */
    public static String getHistoryDate(int day) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, day);//当前时间减N天
        String first = DateFormatType.YearMonthDay.getDateFormat().format(c.getTime());
        return first;
    }

    /**
     * 获取当前月份的第一天
     * @return yyyy-MM-dd 格式
     */
    public static String getFirstDayByMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        String first = DateFormatType.YearMonthDay.getDateFormat().format(c.getTime());
        return first;
    }


    /**
     * 获取当前月份的最后一天
     * @return yyyy-MM-dd 格式
     */
    public static String getLastDayByMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = DateFormatType.YearMonthDay.getDateFormat().format(c.getTime());
        return last;
    }


    public static void main(String[] args) {


        Date date1 = new Date();
        Date date2 = string2Date("2017-01-19 12:00:00", DateFormatType.YearMonthDay_HourMinuteSecond);

        Long time = getDifferSecond(date1,date2);
        Long min = time / 1000 /60;


      //  int day = getDayOfWeek(new Date());

        System.out.println(min);

    }
}
