package com.ruizton.main.cache;


import com.ruizton.main.log.LOG;

/**
 * ActionRegisterCacheManager
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/1/27.
 */
public class ActionRegisterCacheManager {

    private static final String ACTION_REGISTER = "ACTION_REGISTER_ACTIVITY";

    /**
     * 开始时间
     */
    public static final String START = "REGISTER_START";

    /**
     * 结束时间
     */
    public static final String END = "REGISTER_END";

    /**
     * Generate key.
     *
     * @param start the start
     * @param end   the end
     * @return the string
     */
    public static String generateKey(String start, String end) {
        StringBuffer sb = new StringBuffer(ACTION_REGISTER);
        sb.append("_");
        sb.append(start);
        sb.append("_");
        sb.append(end);
        return sb.toString();
    }

    private static Object REGISTER_LOCK = new Object();

    /**
     * Gets left amount.
     *
     * @return the left amount
     */
    public static Long getLeftAmount() {
        synchronized (REGISTER_LOCK) {
            String key = generateKey(START, END);

            return CacheHelper.getObj(key);
        }
    }

    /**
     * Sub amount.
     *
     * @param expMoney the expMoney
     * @param surplusMoney the surplusMoney
     * @return the boolean
     */
    public static boolean subAmount(Long expMoney, Long surplusMoney) {

        synchronized (REGISTER_LOCK) {

            String key = generateKey(START, END);
            LOG.d("","key:"+key);
            boolean result = false;

            Long leftAmount = surplusMoney;
            Long curAmount = leftAmount - Math.abs(expMoney);

            if (curAmount >= 0) {
                //正常逻辑保存
                result = CacheHelper.saveObj(key, curAmount);
            }

            return result;
        }
    }

}
