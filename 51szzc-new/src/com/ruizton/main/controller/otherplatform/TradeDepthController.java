package com.ruizton.main.controller.otherplatform;

import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.comm.KeyValues;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fentrust;
import com.ruizton.main.model.Fentrustlog;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.util.Utils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mibook3 on 17-4-18.
 */


@Controller
public class TradeDepthController extends BaseController {

    @ResponseBody
    @RequestMapping(value = "/api/trade/depth", produces = JsonEncode)
    public String getDepth(@RequestParam(defaultValue = "btc_cny") String symbol, @RequestParam(defaultValue = "20") int size, @RequestParam(defaultValue = "0.01") float merge) {
        JSONObject jsonObject = new JSONObject();


        symbol=symbol.substring(0,symbol.indexOf("_"));

        /*   int int_symbol = 0;
        if (symbol.equals("btc_cny")) {
            int_symbol = 1;
        }
        if (symbol.equals("ltc_cny")) {
            int_symbol = 2;
        }

        if (symbol.equals("eth_cny")) {
            int_symbol = 3;
        }

        if (symbol.equals("ans_cny")) {
            int_symbol = 4;
        }*/

        Ftrademapping ftrademapping=null;
        List<Ftrademapping> activetradeMappingList = (List<Ftrademapping>) ConstantMap.get("activetradeMapping");
        if (activetradeMappingList != null && activetradeMappingList.size() > 0) {
            for (Ftrademapping ftrademapping1 : activetradeMappingList) {
                if (symbol.equals(ftrademapping1.getFvirtualcointypeByFvirtualcointype2().getfShortName().toLowerCase())) {
                    ftrademapping = this.ftradeMappingService.findFtrademapping(ftrademapping1.getFid());
                    break;
                }
            }
        }

        if (ftrademapping == null) {
            JSONObject jsonObject1=new JSONObject();
            jsonObject1.accumulate("code","-1");
            jsonObject1.accumulate("msg", "无此币种");
            return jsonObject1.toString();
        }


        Object[] buyEntrusts = null;
        Object[] sellEntrusts = null;
        Object[] successEntrusts = this.realTimeData.getEntrustSuccessMap(ftrademapping.getFid(), size);
        if (merge == 0.01F) {
            buyEntrusts = this.realTimeData.getBuyDepthMap(ftrademapping.getFid(), size);
            sellEntrusts = this.realTimeData.getSellDepthMap(ftrademapping.getFid(), size);
        }

        if (merge == 0.1F || merge == 1F) {
            //取出所有精度为0.01的深度集合
            buyEntrusts = this.realTimeData.getBuyDepthMap(ftrademapping.getFid(), Integer.MAX_VALUE);
            sellEntrusts = this.realTimeData.getSellDepthMap(ftrademapping.getFid(), Integer.MAX_VALUE);
            buyEntrusts = transferByDepth(merge, buyEntrusts, size, RealTimeData.prizeComparatorDESC);
            sellEntrusts = transferByDepth(merge, sellEntrusts, size, RealTimeData.prizeComparatorASC);
        }


        JSONArray sellDepthList = new JSONArray();
        for (int i = 0; i < sellEntrusts.length; i++) {
            Fentrust fentrust = (Fentrust) sellEntrusts[i];
            if (fentrust.getFleftCount() < Math.pow(10, -8)) {
                continue;
            }
            JSONArray js1 = new JSONArray();
            js1.add(fentrust.getFprize());
            js1.add(Utils.getDoubleS(fentrust.getFleftCount(), ftrademapping.getFcount2()));
            //   js1.accumulate("id",i+1) ;
           // js1.accumulate("price", fentrust.getFprize());
          //  js1.accumulate("amount", Utils.getDoubleS(fentrust.getFleftCount(), ftrademapping.getFcount2()));
            sellDepthList.add(js1);
        }
        jsonObject.accumulate("sells", sellDepthList);


        JSONArray buyDepthList = new JSONArray();
        for (int i = 0; i < buyEntrusts.length; i++) {
            JSONArray js1 = new JSONArray();
            Fentrust fentrust = (Fentrust) buyEntrusts[i];
            if (fentrust.getFleftCount() < Math.pow(10, -8)) {
                continue;
            }
            //  js1.accumulate("id",i+1) ;
            js1.add(fentrust.getFprize());
            js1.add(Utils.getDoubleS(fentrust.getFleftCount(), ftrademapping.getFcount2()));
          //  js1.accumulate("price", fentrust.getFprize());
          //  js1.accumulate("amount", Utils.getDoubleS(fentrust.getFleftCount(), ftrademapping.getFcount2()));

            buyDepthList.add(js1);
        }
        jsonObject.accumulate("buys", buyDepthList);


        return jsonObject.toString();
    }



    //转换交易深度
    private Object[] transferByDepth(float depth, Object[] buyEntrusts, int count, Comparator<Fentrust> comparator) {
        //截取小数位
        for (int i = 0; i < buyEntrusts.length; i++) {
            Fentrust fentrust = (Fentrust) buyEntrusts[i];
            if (depth == 0.1f) {
                fentrust.setFprize(toDouble(1, BigDecimal.ROUND_DOWN, fentrust.getFprize()));
            } else {
                fentrust.setFprize(toDouble(0, BigDecimal.ROUND_DOWN, fentrust.getFprize()));
            }

            // buyEntrusts[i]= fentrust;
        }


        //累加重复值
        Map<String, KeyValues> map = new HashMap<String, KeyValues>();
        for (Fentrust fentrust : (Fentrust[]) buyEntrusts) {
            if (fentrust == null) {
                continue;
            }
            String key = String.valueOf(fentrust.getFprize());
            if (!map.containsKey(key)) {
                KeyValues keyValues = new KeyValues();
                keyValues.setKey(fentrust.getFprize());
                keyValues.setValue(fentrust.getFleftCount());
                map.put(key, keyValues);
            } else {
                map.get(key).setValue((Double) map.get(key).getValue() + fentrust.getFleftCount());
            }
        }

        //排序
        TreeSet<Fentrust> fentrusts = new TreeSet<Fentrust>(comparator);

        for (Map.Entry<String, KeyValues> entry : map.entrySet()) {
            Fentrust fentrust = new Fentrust();
            fentrust.setFprize((Double) entry.getValue().getKey());
            fentrust.setFleftCount((Double) entry.getValue().getValue());
            fentrusts.add(fentrust);
        }


        Fentrust[] newObjs = new Fentrust[0];
        int realCount = 0;
        if (fentrusts != null) {
            realCount = count > fentrusts.size() ? fentrusts.size() : count;
            newObjs = new Fentrust[realCount];
            Iterator<Fentrust> iterator = fentrusts.iterator();
            int index = 0;
            while (realCount > 0) {
                newObjs[index++] = iterator.next();
                realCount--;
            }
        }

        return newObjs;

    }


    private double toDouble(int scale, int mode, Double d) {

        BigDecimal bigDecimal = new BigDecimal(d.toString());
        bigDecimal = bigDecimal.setScale(scale, mode);//设置小数位
        return bigDecimal.doubleValue();

    }

}
