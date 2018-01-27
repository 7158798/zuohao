package com.jucaifu.common.constants;

/**
 * TimeConstant
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/1.
 */
public interface TimeConstant {

    /**
     * [单位秒] 1秒
     */
    int ONE_SECOND_UNIT_SECONDS = 1;

    /**
     * [单位秒] 1分钟
     */
    int ONE_MINUTE_UNIT_SECONDS = ONE_SECOND_UNIT_SECONDS * 60;

    /**
     * [单位秒] 半个小时
     */
    int HALF_HOUR_UNIT_SECONDS = ONE_MINUTE_UNIT_SECONDS * 30;

    /**
     * [单位秒] 1小时
     */
    int ONE_HOUR_UNIT_SECONDS = ONE_MINUTE_UNIT_SECONDS * 60;

    /**
     * [单位秒] 1天
     */
    int ONE_DAY_UNIT_SECONDS = ONE_HOUR_UNIT_SECONDS * 24;

    /**
     * [单位毫秒] 1周
     */
    int ONE_WEEK_UNIT_SECONDS = ONE_DAY_UNIT_SECONDS * 7;

//////////////////////////////
//// 下面是对应的毫秒数
//////////////////////////////

    /**
     * [单位毫秒] 1秒
     */
    int ONE_SECOND_UNIT_MILLISECONDS = convert2MilliSeconds(ONE_SECOND_UNIT_SECONDS);

    /**
     * [单位毫秒] 1分钟
     */
    int ONE_MINUTE_UNIT_MILLISECONDS = convert2MilliSeconds(ONE_MINUTE_UNIT_SECONDS);

    /**
     * [单位毫秒] 半个小时
     */
    int HALF_HOUR_UNIT_MILLISECONDS = convert2MilliSeconds(HALF_HOUR_UNIT_SECONDS);

    /**
     * [单位毫秒] 1小时
     */
    int ONE_HOUR_UNIT_MILLISECONDS = convert2MilliSeconds(ONE_HOUR_UNIT_SECONDS);

    /**
     * [单位毫秒] 1天
     */
    int ONE_DAY_UNIT_MILLISECONDS = convert2MilliSeconds(ONE_DAY_UNIT_SECONDS);

    /**
     * [单位毫秒] 1周
     */
    int ONE_WEEK_UNIT_MILLISECONDS = convert2MilliSeconds(ONE_WEEK_UNIT_SECONDS);


    /**
     * Convert 2 milli seconds.
     *
     * @param second the second
     * @return the int
     */
    static int convert2MilliSeconds(int second) {
        return second * 1000;
    }

}
