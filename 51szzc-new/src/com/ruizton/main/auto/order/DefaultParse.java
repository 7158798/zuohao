package com.ruizton.main.auto.order;

import com.ruizton.main.auto.order.bter.BterOrderResponse;
import com.ruizton.main.auto.order.yunbi.YunbiOrderResponse;
import com.ruizton.util.JsonHelper;
import org.apache.shiro.crypto.hash.Hash;

import java.util.*;

/**
 * 默认解析
 * Created by lx on 17-3-1.
 */
public class DefaultParse {


    public static List<OrderResponse> parse(String url,String json) throws Exception {
        if (url.indexOf("yunbi") > 0){
            // yunbi
            return yunBiParse(json);
        } else if (url.indexOf("bter") > 0){
            return bterParse(json);
        } else {
            // default
            return JsonHelper.jsonArrayStr2List(json,OrderResponse.class);
        }
    }


    private static List<OrderResponse> yunBiParse(String json) throws Exception {

        List<OrderResponse> list = new ArrayList<OrderResponse>();
        List<YunbiOrderResponse> rList = JsonHelper.jsonArrayStr2List(json, YunbiOrderResponse.class);
        for (YunbiOrderResponse yunbi : rList){
            OrderResponse response = new OrderResponse();
            response.setTid(yunbi.getId());
            response.setPrice(yunbi.getPrice());
            response.setAmount(yunbi.getVolume());
            response.setDate(yunbi.getCreated_at());
            response.setType(yunbi.getSide());
            list.add(response);
        }
        Collections.reverse(list);

        return list;
    }

    private static List<OrderResponse> bterParse(String json){
        BterOrderResponse response = JsonHelper.jsonStr2Obj(json,BterOrderResponse.class);
        return response.getData();
    }

}
