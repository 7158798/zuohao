package com.utils;

import com.jucaifu.common.util.JsonHelper;
import com.otc.facade.advertisement.pojo.poex.EthCoins;
import junit.framework.TestCase;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zygong on 17-4-27.
 */
public class UtilTest extends TestCase {

    @Test
    public void test1(){
        String str = "123456a";
        boolean b = validateValue(str, "^[0-9]*$");
        System.out.println(b);
    }

    public static boolean validateValue(String content, String regexString) {
        if(!StringUtils.isEmpty(content) && !StringUtils.isEmpty(regexString)) {
            Pattern pattern = Pattern.compile(regexString);
            Matcher matcher = pattern.matcher(content);
            return matcher.matches();
        } else {
            return false;
        }
    }

    @Test
    public void test2() {
        try {
            String str = "43*(2 + 1.4%)+2*32/(3-2.1)";
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");


            Object result = engine.eval(str);
            System.out.println(JsonHelper.obj2JsonStr(result));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3(){
        String str = "{\"cName\":\"币盈网\",\"coinId\":63,\"coinName\":\"WIN币\",\"coinSign\":\"win\",\"exeByRate\":0,\"isRecomm\":0,\"marketValue\":\"0\",\"moneyType\":2,\"name\":\"etcwin\",\"symbol\":\"etcwinbtcwin\",\"t\":1964,\"ticker\":{\"buy\":\"14.55\",\"buydollar\":\"2.11\",\"dollar\":\"2.11\",\"high\":\"15.32\",\"highdollar\":\"2.22\",\"last\":\"14.55\",\"low\":\"8.60\",\"lowdollar\":\"1.25\",\"riseRate\":\"0.00\",\"sell\":\"8.51\",\"selldollar\":\"1.23\",\"vol\":\"282651.290\"},\"type\":105}";
        EthCoins ethCoins = JsonHelper.jsonStr2Obj(str, EthCoins.class);
        System.out.println(JsonHelper.obj2JsonStr(ethCoins));
    }

    @Test
    public void test4(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        String format = sdf.format(new Date());
        System.out.println(format);
    }

    @Test
    public void test5(){
        String str = "11112222003";
        str = str.substring(str.length() - 3, str.length());
        int strint = Integer.valueOf(str) + 1;
        String format = String.format("%03d", strint);
        System.out.println(format);
    }

    @Test
    public void test6() {
        try {
            String str = "(1 + 0.001*(-1))";
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");


            Object result = engine.eval(str);
            System.out.println(JsonHelper.obj2JsonStr(result));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test7(){
        String str = "P*(1+0.001*C)";
        str = str.substring(2, str.length());
        System.out.println(str);
    }

    @Test
    public void test8(){
        BigDecimal total = new BigDecimal(0);
        total = total.add(new BigDecimal(3));
        assertEquals(new BigDecimal(3), total);
    }

    @Test
    public void test9(){
        BigDecimal total = new BigDecimal(10000);
        total = total.divide(new BigDecimal(12588.00), 8, BigDecimal.ROUND_DOWN);
        System.out.println(total.toString());
    }
}
