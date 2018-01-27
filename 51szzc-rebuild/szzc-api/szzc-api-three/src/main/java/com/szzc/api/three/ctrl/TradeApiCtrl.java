package com.szzc.api.three.ctrl;

import com.facade.core.wallet.enums.TradeTypeEnum;
import com.jucaifu.common.context.SpringPropReaderHelper;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.api.three.annotation.ApiValidateAnno;
import com.szzc.api.three.base.BaseCtrl;
import com.szzc.api.three.ctrl.request.CancelOrderReq;
import com.szzc.api.three.ctrl.request.TradeOrderReq;
import com.szzc.api.three.ctrl.request.TradeReq;
import com.szzc.api.three.ctrl.request.szzc.CancelReq;
import com.szzc.api.three.ctrl.request.szzc.SzzcTradeReq;
import com.szzc.api.three.ctrl.response.OrderApiResp;
import com.szzc.api.three.ctrl.response.szzc.SzzcBaseResp;
import com.szzc.api.three.exceptions.ThreeBizException;
import com.szzc.api.three.pojo.BaseApiReq;
import com.szzc.api.three.utils.api.SignHelper;
import com.szzc.common.api.packet.web.WebApiResponse;
import com.szzc.facade.api.pojo.po.UserApi;
import com.szzc.facade.fentrustLog.pojo.dto.FentrustDto;
import com.szzc.facade.fentrustLog.pojo.vo.FentrustVo;
import com.szzc.facade.wallet.pojo.dto.TradeAccountInfoTotalMoney;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by zygong on 17-7-19.
 */
@RestController
public class TradeApiCtrl extends BaseCtrl{

    /**
     * 获取用户资产
     * @return
     */
    @ApiValidateAnno
    @RequestMapping(value = "/api/v1/accountInfo", method = RequestMethod.POST)
    public WebApiResponse accountInfo(@RequestBody BaseApiReq req){
        UserApi userApi = validation(req);
        TradeAccountInfoTotalMoney info = three.walletFacade.getUserAccountInfo(userApi.getUserId());
        return buildWebApiResponse(SUCCESS,info);
    }

    /**
     * 买卖
     * @return
     */
    @ApiValidateAnno
    @RequestMapping(value = "/api/v1/trade", method = RequestMethod.POST)
    public WebApiResponse trade(@RequestBody TradeReq req) throws IOException {
        LOG.dStart(this, "下单");
        UserApi userApi = validation(req);
        String url;
        String result;
        // 获取币种对应的法币id
        Long symbol = getFbIdByShortName(req.getSymbol());
        if(symbol == null || symbol == 0){
            throw new ThreeBizException("该币暂时不能交易");
        }
        SzzcTradeReq szzcReq = new SzzcTradeReq();
        TradeTypeEnum byName = TradeTypeEnum.getByName(req.getType());
        if(byName == null){
            throw new ThreeBizException("type输入有误");
        }
        szzcReq.setSymbol(symbol.intValue());
        szzcReq.setTradeAmount(req.getAmount());
        szzcReq.setTradeCnyPrice(req.getPrice());
        szzcReq.setUserId(userApi.getUserId());

        if(byName.getName().contains("buy")){
            if(req.getType().equals(TradeTypeEnum.BUY_MARKET.getName())){
                szzcReq.setLimited(1);
            }else{
                szzcReq.setLimited(0);
            }
            url = getBaseUrl() + "/buy" + ".html";
            result = HttpClientHelper.postJsonParams(url, JsonHelper.obj2JsonStr(szzcReq));
        }else{
            if(req.getType().equals(TradeTypeEnum.SELL_MARKET.getName())){
                szzcReq.setLimited(1);
            }else{
                szzcReq.setLimited(0);
            }
            url = getBaseUrl() + "/sell" + ".html";
            result = HttpClientHelper.postJsonParams(url, JsonHelper.obj2JsonStr(szzcReq));
        }
        SzzcBaseResp szzc = JsonHelper.jsonStr2Obj(result,SzzcBaseResp.class);
        if (szzc.getResultCode() == null || szzc.getResultCode().intValue() != 0){
            throw new ThreeBizException(szzc.getMsg());
        }
        OrderApiResp orderApi = new OrderApiResp();
        orderApi.setOrderId(szzc.getOrderId());
        // 响应下单
        return buildWebApiResponse(SUCCESS,orderApi);
    }

    /**
     * 撤单
     * @return
     */
    @ApiValidateAnno
    @RequestMapping(value = "/api/v1/cancel", method = RequestMethod.POST)
    public WebApiResponse cancel(@RequestBody CancelOrderReq req) throws IOException {
        UserApi userApi = validation(req);
        CancelReq szzcReq = new CancelReq();
        // 获取币种对应的法币id
        Long symbol = getFbIdByShortName(req.getSymbol());
        if(symbol == null || symbol == 0){
            throw new ThreeBizException("symbol输入不正确");
        }
        szzcReq.setUserId(userApi.getUserId());
        szzcReq.setOrderId(req.getOrderId());
        szzcReq.setSymbol(symbol.intValue());
        String url = getBaseUrl() + "/cancel" + ".html";
        String result = HttpClientHelper.postJsonParams(url, JsonHelper.obj2JsonStr(szzcReq));
        SzzcBaseResp szzc = JsonHelper.jsonStr2Obj(result,SzzcBaseResp.class);
        if (szzc.getResultCode() == null || szzc.getResultCode().intValue() != 0){
            throw new ThreeBizException(szzc.getMsg());
        }
        return buildWebApiResponse(SUCCESS,szzc);
    }

    /**
     * 订单
     * @return
     */
    @ApiValidateAnno
    @RequestMapping(value = "/api/v1/order", method = RequestMethod.POST)
    public WebApiResponse order(@RequestBody TradeOrderReq req) throws IOException {
        //　最大的条数
        LOG.dStart(this, "查询用户订单历史");
        UserApi userApi = validation(req);
        Long symbol;
        // 获取币种对应的法币id
        symbol = getFbIdByShortName(req.getSymbol());
        if(symbol == null){
            throw new ThreeBizException("symbol输入不正确");
        }
        FentrustVo vo = new FentrustVo();
        vo.setNowPage(req.getCurrent_page());
        vo.setPageShow(200);
        vo.setSymbol(symbol);
        vo.setUserId(userApi.getUserId());
        vo = three.fentrustFacade.getFentrustByConditionPage(vo);
        List<FentrustDto> list = vo.fatchTransferList();
        LOG.dEnd(this,"查询用户订单历史");
        return buildWebApiPageResponse(vo,list);
    }

    /*@RequestMapping(value = "/api/v1/getSing")
    public String getSign(@RequestParam String req,@RequestParam String key){
        TreeMap treeMap = JsonHelper.jsonStr2Obj("{" + req + "}", TreeMap.class);
        String sign = SignHelper.getSign(treeMap, key);
        return sign;
    }*/


    private String getBaseUrl(){
        return SpringPropReaderHelper.getProperty("szzc_api_url");
    }
    /**
     * 通过币种简称获取法币id
     * @param symbolStr
     * @return
     */
    public Long getFbIdByShortName(String symbolStr){
        Long symbol = null;
        Map<String, Long> actionCoinShortNameAndFtId = three.virtualCoinFacade.getActionCoinShortNameAndFtId();
        if(actionCoinShortNameAndFtId != null && !actionCoinShortNameAndFtId.isEmpty()){
            symbol = actionCoinShortNameAndFtId.get(symbolStr);
        }
        return symbol;
    }
}
