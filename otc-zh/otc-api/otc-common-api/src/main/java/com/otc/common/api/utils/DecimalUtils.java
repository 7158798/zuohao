package com.otc.common.api.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by lx on 17-5-16.
 */
public class DecimalUtils {

    //格式化，保留2未小数
    protected static DecimalFormat decimalFormat = new DecimalFormat("####.####");

    /**
     * 数量格式化方法
     * @param decimal
     * @return
     */
    public static String formatAmount(BigDecimal decimal){
        decimal = decimal.setScale(4,BigDecimal.ROUND_DOWN);
        return decimalFormat.format(decimal);
    }

    /**
     * 格式化零
     * @param decimal
     * @return
     */
    public static String formatZero(BigDecimal decimal){
        return decimalFormat.format(decimal);
    }

    public static void main(String[] args) {

        BigDecimal bb = new BigDecimal("0.00100000");
        String result = formatAmount(bb);
        System.out.println(result);
    }
}
