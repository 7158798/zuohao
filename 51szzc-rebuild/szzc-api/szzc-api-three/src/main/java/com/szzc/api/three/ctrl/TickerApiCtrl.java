package com.szzc.api.three.ctrl;

import com.facade.core.wallet.cache.CacheHelper;
import com.facade.core.wallet.cache.OtherPlatformTickerCacheManager;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.api.three.annotation.ApiValidateAnno;
import com.szzc.api.three.base.BaseCtrl;
import com.szzc.api.three.ctrl.response.DepthResp;
import com.szzc.api.three.utils.api.SignHelper;
import com.szzc.common.api.packet.web.WebApiResponse;
import com.szzc.common.utils.Utils;
import com.szzc.facade.fentrustLog.pojo.dto.TradeListDto;
import com.szzc.facade.fentrustLog.pojo.dto.TradeVo;
import com.szzc.facade.out.pojo.TickerDateDto;
import com.szzc.facade.out.pojo.TickerDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by zygong on 17-7-19.
 */
@RestController
public class TickerApiCtrl extends BaseCtrl {

    /**
     * 交易信息 最多获取600条数据
     * @param symbol
     * @param since
     * @return
     */
    @ApiValidateAnno
    @RequestMapping(value = "/api/v1/trades")
    public WebApiResponse trades(@RequestParam String symbol, @RequestParam(defaultValue = "600") Long since){
        if(StringUtils.isBlank(symbol)){
            return null;
        }
        if (since.longValue() > 600){
            since = 600l;
        }
        String result  = CacheHelper.getObj(symbol.toUpperCase() + "TRADE_OTHERPLATFORM_CACHE_KEY");
        TradeListDto trade = JsonHelper.jsonStr2Obj(result, TradeListDto.class);
        List<TradeVo> list = new ArrayList<>();
        if(trade == null || trade.getList() == null || trade.getList().isEmpty()){
            trade = new TradeListDto();
            trade.setList(list);
        }else{
            List<TradeVo> rList = new ArrayList<>();
            for (TradeVo tradeVo : trade.getList()){
                if (rList.size() >= since.intValue()){
                    break;
                }
                rList.add(tradeVo);
            }
            trade.setList(rList);
        }
        return buildWebApiResponse(SUCCESS, trade);
    }

    /**
     * 获取行情
     * @param symbol
     * @return
     */
    @ApiValidateAnno
    @RequestMapping(value = "/api/v1/ticker", method = RequestMethod.GET)
    public WebApiResponse ticker(String symbol) {
        if (StringUtils.isBlank(symbol)) {
            return null;
        }else{
            symbol = "ticker_" + symbol;
        }
        TickerDateDto ticker = OtherPlatformTickerCacheManager.getByKey(symbol);
        if(ticker == null){
            ticker = new TickerDateDto();
            TickerDto tickerDto = new TickerDto();

            tickerDto.setBuy(Utils.decimalFormat(0d, 2));
            tickerDto.setSell(Utils.decimalFormat(0d, 2));
            tickerDto.setVol(Utils.decimalFormat(0d, 4));
            tickerDto.setHigh(Utils.decimalFormat(0d, 2));
            tickerDto.setLow(Utils.decimalFormat(0d, 2));
            tickerDto.setLast(Utils.decimalFormat(0d, 2));
            ticker.setTicker(tickerDto);
        }
        ticker.setDate(new Date().getTime());
        return buildWebApiResponse(SUCCESS,ticker);
    }

    /**
     * 获取深度
     * @param symbol
     * @param size
     * @param merge
     * @return
     */
    @ApiValidateAnno
    @RequestMapping(value = "/api/v1/getDepth")
    public WebApiResponse getDepth(@RequestParam(defaultValue = "btc_cny") String symbol, @RequestParam(defaultValue = "20") int size, @RequestParam(defaultValue = "0.01") String merge) {
        Map<String ,String> map=  CacheHelper.getObj("depth_"+symbol);
        if(map == null || map.isEmpty()){
            return null;
        }
        String rs=null;
        if(map!=null) {
            rs = map.get(merge);
        }
        LOG.d(this,rs);

        DepthResp depth = JsonHelper.jsonStr2Obj(rs,DepthResp.class);
        DepthResp resp = new DepthResp();
        List<List<String>> buys = packDepth(depth.getBuys(),size);
        List<List<String>> sells = packDepth(depth.getSells(),size);
        resp.setBuys(buys);
        resp.setSells(sells);

        return buildWebApiResponse(SUCCESS,resp);
    }

    /**
     * 包装深度
     * @param list
     * @param size
     * @return
     */
    private List<List<String>> packDepth(List<List<String>> list,int size){
        List<List<String>> rList = new ArrayList<>();
        for (List<String> temp: list){
            if (rList.size() >= size){
                break;
            }
            rList.add(temp);
        }
        return rList;
    }


    /*public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
       *//* Field[]  fields = TradeOrderReq.class.getDeclaredFields();
        for (Field field : fields){
            System.out.println(field);
        }*//*
        TradeOrderReq req = new TradeOrderReq();
        req.setApi_key("ssss");
        Set<Field> test =  ReflectHelper.getFieldSet(req);
        for (Field field : test){
            System.out.println(field.getName() + ",value=" + ReflectHelper.getValueByFieldName(req,field.getName()));
        }
    }*/

    public static void main(String[] args) {
        String json = "{\"sells\":[[10000,1.0000],[15261.15,0.1704],[16202.75,0.1422],[16221.91,0.3091],[16501.63,0.1538],[35000,0.7668]],\"buys\":[[9000,0.0200]]}";

        DepthResp resp = JsonHelper.jsonStr2Obj(json,DepthResp.class);
        System.out.println("dsfadsfd");
    }
}
