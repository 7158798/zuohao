package com.ruizton.main.controller.appweb;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.TrademappingStatusEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fentrustlog;
import com.ruizton.main.model.Ftradehistory;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.util.Utils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 移动端网页
 * Created by zh on 2017/3/17.
 */
@Controller
public class FrontMobileController extends BaseController {

    @RequestMapping("/mobile/index")
    public ModelAndView mobileIndex() throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;

        //获取可交易的法币匹配信息
        List<Ftrademapping>  ftrademappingList =  this.ftradeMappingService.findActiveTradeMappingByLazy();
        if(ftrademappingList == null || ftrademappingList.size() == 0) {
            modelAndView.addObject("code", -1) ;
            modelAndView.addObject("msg", "获取法币信息失败");
            return modelAndView;
        }
        //取某个币种价格成交信息
        JSONArray jsonArray = queryCncyTrade(ftrademappingList);
        modelAndView.addObject("code", 0);
        modelAndView.addObject("cncyList", jsonArray.toArray());

        modelAndView.setViewName("front/mobile/index") ;
        return modelAndView ;
    }


    @RequestMapping(value = "mobile/getmarketdata", produces={JsonEncode})
    @ResponseBody
    public String queryMarketData() {
        JSONObject jsonObject = new JSONObject();
        //获取可交易的法币匹配信息
        List<Ftrademapping>  ftrademappingList =  this.ftradeMappingService.findActiveTradeMappingByLazy();
        if(ftrademappingList == null || ftrademappingList.size() == 0) {
            jsonObject.accumulate("code", -1) ;
            jsonObject.accumulate("msg", "获取法币信息失败");
            return jsonObject.toString();
        }

        JSONArray jsonArray  = queryCncyTrade(ftrademappingList);
        jsonObject.accumulate("code", 0);
        jsonObject.accumulate("cncyList", jsonArray.toArray());
        return jsonObject.toString();
    }

    //取某个币种价格成交信息
    public JSONArray queryCncyTrade(List<Ftrademapping>  ftrademappingList) {
        JSONArray jsonArray = new JSONArray();
        for( Ftrademapping ftrademapping : ftrademappingList){
            int id = ftrademapping.getFid();
            JSONObject js = new JSONObject();
            String fname =  ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname();
            String shortName = ftrademapping.getFvirtualcointypeByFvirtualcointype2().getfShortName();
            String logo = ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFurl();
            js.accumulate("cncy_name",fname);
            js.accumulate("shortName",shortName);
            js.accumulate("cncy_logo", logo);
            List<Fentrustlog> fentrustlogList = this.frontTradeService.findNewLatestDeal(ftrademapping.getFid(), 2);
            if(fentrustlogList != null && fentrustlogList.get(0) != null && fentrustlogList.get(1) != null) {
                BigDecimal new_price = new BigDecimal(fentrustlogList.get(0).getFprize()+"");  //最新价
                BigDecimal last_price = new BigDecimal(fentrustlogList.get(1).getFprize()+"");  //上一次的价格
                //涨跌幅度
                BigDecimal rate = (new_price.subtract(last_price)).divide(last_price,4, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN);
                js.accumulate("new_price",new_price) ;
                js.accumulate("last_price",last_price) ;
                js.accumulate("rate",rate) ;
            }
            //最高价、最低价、24H成交
            js.accumulate("OneDayLowest", Utils.decimalFormat(this.oneDayData.getLowest(id), 2)) ;  //最低
            js.accumulate("OneDayHighest", Utils.decimalFormat(this.oneDayData.getHighest(id), 2)) ;  //最高
            js.accumulate("OneDayTotal", Utils.decimalFormat(this.oneDayData.getTotal(id), 4)) ;  //成交量

            jsonArray.add(js);
        }
        return jsonArray;
    }


    @RequestMapping("/mobile/e-active")
    public ModelAndView mobileAcitive(){
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("front/mobile/e-active");
        return modelAndView;
    }
    @RequestMapping("/mobile/apply")
    public ModelAndView mobileApply(){
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("front/mobile/apply");
        return modelAndView;
    }

}
