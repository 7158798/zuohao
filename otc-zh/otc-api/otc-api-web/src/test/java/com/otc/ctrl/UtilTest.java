package com.otc.ctrl;

import com.jucaifu.common.util.StringHelper;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by zygong on 17-7-12.
 */
public class UtilTest {
    @Test
    public void test1(){
        double v = 1.0 / 10.0;
        BigDecimal bigDecimal = new BigDecimal(StringHelper.percent(v, 2));
        System.out.println(bigDecimal.toString());
    }
}
