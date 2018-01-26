package com.ruizton.main.controller.otherplatform;

import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.controller.app.ApiConstant;
import com.ruizton.main.controller.app.ApiConstants;
import com.ruizton.main.model.Fentrustlog;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.vo.TradeVo;
import com.ruizton.main.service.admin.EntrustlogService;
import com.ruizton.util.JsonHelper;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zygong on 17-4-19.
 */
@Controller
public class TradeController extends BaseController{

    @Autowired
    private EntrustlogService entrustlogService;
    @Autowired
    protected RealTimeData realTimeData ;

    @RequestMapping(value = "/api/trade", method = RequestMethod.GET, produces = JsonEncode)
    @ResponseBody
    public String trade2(String symbol, Integer since){
        if(StringUtils.isBlank(symbol)){
            return null;
        }

        JSONObject jsonObject = new JSONObject();
        List<TradeVo> trade = new ArrayList<TradeVo>();
        List<Ftrademapping> activetradeMappingList = (List<Ftrademapping>) ConstantMap.get("activetradeMapping");
        if (activetradeMappingList != null && activetradeMappingList.size() > 0) {
            for (Ftrademapping ftrademapping : activetradeMappingList) {
                if (symbol.equals(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getfShortName().toLowerCase())) {
                    trade = this.entrustlogService.findTrade(ftrademapping.getFid(), since);
                    break;
                }
            }
        }

        jsonObject.accumulate(LIST, trade);
        return jsonObject.toString();
    }

    @RequestMapping(value = "/api/trade2", method = RequestMethod.GET, produces = JsonEncode)
    @ResponseBody
    public String trade(String symbol, Integer since){
        if(StringUtils.isBlank(symbol)){
            return null;
        }
        if(since == null || since.intValue() == 0){
            since = 60;
        }

        JSONObject jsonObject = new JSONObject();
        List<Fentrustlog> fentrustlogs = new ArrayList<Fentrustlog>();
//        List<TradeVo> trade = new ArrayList<TradeVo>();
        List<Ftrademapping> activetradeMappingList = (List<Ftrademapping>) ConstantMap.get("activetradeMapping");
        if (activetradeMappingList != null && activetradeMappingList.size() > 0) {
            for (Ftrademapping ftrademapping : activetradeMappingList) {
                if (symbol.equals(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getfShortName().toLowerCase())) {
                    Fentrustlog[] entrustSuccessMap = this.realTimeData.getEntrustSuccessMap(ftrademapping.getFid(), since);
                    fentrustlogs = Arrays.asList(entrustSuccessMap);
                    break;
                }
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(fentrustlogs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
