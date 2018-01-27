package com.jucaifu.common.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by zygong on 17-5-3.
 */
public class DigitalHelper {

    //格式化，保留4位小数
    private static DecimalFormat decimalFormat4 = new DecimalFormat("####0.0000");//格式化设置

    //格式化，保留2位小数
    private static DecimalFormat decimalFormat2 = new DecimalFormat("####0.00");

    public static String decimalFormat(double value ,int scale) {
        if(scale == 2) {
            return decimalFormat2.format(value);
        }else if(scale == 4) {
            return decimalFormat4.format(value);
        }
        return getDoubleS(value, scale);
    }

    public static String getDoubleS(double value, int scale) {
        value +=Math.pow(10, -8) ;
        return new BigDecimal(String.valueOf(value)).setScale(scale,BigDecimal.ROUND_DOWN).toString();
    }

    /**
     * 通过计算公式   计算出结果
     * @param str
     * @return
     */
    public static Object calculate(String str){
        Object result = null;
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");

        try {
            result = engine.eval(str);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return result;
    }
}
