package com.ruizton.main.auto;

import com.ruizton.main.controller.front.FrontTradeAutoController;
import com.ruizton.util.HttpUtils;
import com.ruizton.util.JsonHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by lx on 17-1-16.
 */
public class TestTask extends TimerTask{

    public static final Logger logger = LoggerFactory.getLogger(TestTask.class);
    //@Autowired
    private FrontTradeAutoController frontTradeAutoController;

    @Override
    //@Scheduled(cron = "0/60 * * * * ? ")
    public void run() {
        logger.info("执行自动下单开始");
        work();
        logger.info("执行自动下单结束");
    }

    private void work(){
        try{

            Long time = new Date().getTime();
            HttpResponse response = HttpUtils.doGet("https://www.jubi.com/coin/btc/order?t=" + String.valueOf(time),null);

            String json = EntityUtils.toString(response.getEntity(), "UTF-8");

            JBResponse jb = JsonHelper.jsonStr2Obj(json,JBResponse.class);

            System.out.println(jb.getD().size());
            List<String> test = jb.getD();
            Collections.reverse(test);
            for (String t : test){
                String[] temp = parse(t);
                if (temp != null && temp.length != 0){
                    int start = (int)(Math.random()*100);
                    double s = start * 0.001;
                    int end = (int)(Math.random()*100);
                    double e = end * 0.001;
                    /*double buy = new Double(temp[1]) + s;
                    frontTradeAutoController.sellBtcSubmitTest(1,buy,new Double(temp[0]),"ABC123",24);

                    double sell = new Double(temp[1]) + e;
                    frontTradeAutoController.buyBtcSubmit(1,sell,new Double(temp[0]),"ABC123",32);*/
                    System.out.println("挂单成功");
                    Thread.sleep(500);
                    /*if ("buy".equals(temp[2])){
                        // 买入
                        System.out.println("buy");
                        frontTradeAutoController.buyBtcSubmit(1,new Double(temp[1]),new Double(temp[0]),"ABC123",32);
                    } else {
                        frontTradeAutoController.sellBtcSubmitTest(1,new Double(temp[1]),new Double(temp[0]),"ABC123",24);
                        System.out.println("sell");
                    }*/
                }
            }

        } catch (Exception ex){

            logger.error("自动下单发生异常",ex.getMessage(),ex);
        }

    }


    public static void main(String[] args) throws Exception {

        new TestTask().work();

    }


    private static String[] parse(String data){
        if (StringUtils.isEmpty(data)){
            return null;
        }
        String[] temp = data.split(",");
        if (temp.length == 5){
            String[] result = new String[3];
            for (int i = 1;i < 4;i++){
                result[i-1] = temp[i].replaceAll("\"", "");
            }
            return result;
        }
        return null;
    }
}
