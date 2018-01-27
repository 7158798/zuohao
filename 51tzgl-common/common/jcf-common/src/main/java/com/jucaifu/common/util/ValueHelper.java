package com.jucaifu.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * The type Value helper.
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/1.
 */
public class ValueHelper {

    private ValueHelper() {
    }

    /**
     * Check list is empty.
     *
     * @param dataList the data list
     * @return the boolean
     */
    public static boolean checkListIsEmpty(List<?> dataList) {
        return (null == dataList || dataList.size() == 0);
    }

    /**
     * Check string is empty.
     *
     * @param value the value
     * @return the boolean
     */
    public static boolean checkStringIsEmpty(String value) {
        return StringUtils.isEmpty(value);
    }

    /**
     * 判断是否为空
     * @param paramArray
     * @return
     */
    public static boolean checkParam(Object... paramArray){
        boolean flag = false;
        for(Object param : paramArray){
            if(null == param){
                return flag;
            }
            if(param instanceof String){
                if(((String) param).trim().length() == 0){
                    return flag;
                }
            }
            if(param instanceof ArrayList){
                if(((ArrayList) param).size() < 1){
                    return flag;
                }
            }
            if(param instanceof Map){
                if(((Map) param).isEmpty()){
                    return flag;
                }
            }
        }
        return true;
    }




}
