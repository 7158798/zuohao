package com.ruizton.main.controller.otherplatform;

import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fentrust;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.util.Utils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created by zygong on 17-4-18.
 */
@Controller
public class TickerController extends BaseController{


    @RequestMapping(value = "/api/ticker", method = RequestMethod.GET, produces = JsonEncode)
    @ResponseBody
    public String ticker(String symbol) {
        if (StringUtils.isBlank(symbol)) {
            return null;
        }

        JSONObject jsonObject = new JSONObject();
        JSONObject ticker = new JSONObject();
        int symbolId = 0;

        List<Ftrademapping> activetradeMappingList = (List<Ftrademapping>) ConstantMap.get("activetradeMapping");
        if (activetradeMappingList != null && activetradeMappingList.size() > 0) {
            for (Ftrademapping ftrademapping : activetradeMappingList) {
                if (symbol.equals(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getfShortName().toLowerCase())) {
                    symbolId = ftrademapping.getFid();
                    break;
                }
            }
            if(symbolId != 0){
                double total = this.oneDayData.getTotal(symbolId);
                double highest = this.oneDayData.getHighest(symbolId);
                double lowest = this.oneDayData.getLowest(symbolId);
                double latestDealPrize = this.realTimeData.getLatestDealPrize(symbolId);
                Fentrust[] buyDepthMap = this.realTimeData.getBuyDepthMap(symbolId, 1);
                Fentrust[] sellDepthMap = this.realTimeData.getSellDepthMap(symbolId, 1);
                if (buyDepthMap.length > 0) {
                    ticker.accumulate("buy", Utils.decimalFormat(buyDepthMap[0].getFprize(), 2));
                } else {
                    ticker.accumulate("buy", Utils.decimalFormat(0d, 2));
                }
                if (sellDepthMap.length > 0) {
                    ticker.accumulate("sell", Utils.decimalFormat(sellDepthMap[0].getFprize(), 2));
                } else {
                    ticker.accumulate("sell", Utils.decimalFormat(0d, 2));
                }
                ticker.accumulate("vol", Utils.decimalFormat(total, 4));
                ticker.accumulate("high", Utils.decimalFormat(highest, 2));
                ticker.accumulate("low", Utils.decimalFormat(lowest, 2));
                ticker.accumulate("last", Utils.decimalFormat(latestDealPrize, 2));
            }

        }
        if(symbolId == 0){
            ticker.accumulate("buy", Utils.decimalFormat(0d, 2));
            ticker.accumulate("sell", Utils.decimalFormat(0d, 2));
            ticker.accumulate("vol", Utils.decimalFormat(0d, 4));
            ticker.accumulate("high", Utils.decimalFormat(0d, 2));
            ticker.accumulate("low", Utils.decimalFormat(0d, 2));
            ticker.accumulate("last", Utils.decimalFormat(0d, 2));
        }

        jsonObject.accumulate("ticker", ticker);
        jsonObject.accumulate("date", new Date().getTime());

        return jsonObject.toString();
    }
}
