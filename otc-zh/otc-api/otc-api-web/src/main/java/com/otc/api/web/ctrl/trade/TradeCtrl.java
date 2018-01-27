package com.otc.api.web.ctrl.trade;

import com.jucaifu.common.constants.TimeConstant;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.pojo.vo.BaseResp;
import com.jucaifu.common.util.*;
import com.otc.api.web.base.BaseWebCtrl;
import com.otc.api.web.constants.WebApiTrade;
import com.otc.api.web.ctrl.trade.req.JudgeReq;
import com.otc.api.web.ctrl.trade.req.TradeConfirmReq;
import com.otc.api.web.ctrl.trade.req.TradeListReq;
import com.otc.api.web.ctrl.trade.req.TradeReq;
import com.otc.api.web.ctrl.trade.response.TradeInfoResp;
import com.otc.api.web.ctrl.trade.response.TradeJudgeResp;
import com.otc.api.web.ctrl.virtual.request.VirtualRecordReq;
import com.otc.api.web.ctrl.virtual.response.VirtualRecordResp;
import com.otc.api.web.exceptions.WebBizException;
import com.otc.api.web.utils.WebTokenValidate;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.CacheHelper;
import com.otc.core.cache.UserCacheManager;
import com.otc.facade.advertisement.pojo.po.Advertisement;
import com.otc.facade.cache.CacheKey;
import com.otc.facade.trade.enums.TradeJudgeLevelEnum;
import com.otc.facade.trade.enums.TradeSideEnum;
import com.otc.facade.trade.enums.TradeStatusEnum;
import com.otc.facade.trade.enums.TradeTypeEnum;
import com.otc.facade.trade.pojo.po.Trade;
import com.otc.facade.trade.pojo.po.TradeJudge;
import com.otc.facade.trade.pojo.vo.TradeVo;
import com.otc.facade.user.enums.UserAutStatusEnum;
import com.otc.facade.user.pojo.po.User;
import com.otc.facade.user.pojo.po.UserAuthentication;
import com.otc.facade.user.pojo.poex.CacheUserInfo;
import com.otc.facade.virtual.enums.VirtualRecordOutStatus;
import com.otc.facade.virtual.enums.VirtualRecordType;
import com.otc.facade.virtual.pojo.po.VirtualCoin;
import com.otc.facade.virtual.pojo.po.VirtualRecord;
import com.otc.facade.virtual.pojo.vo.VirtualRecordVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by fenggq on 17-5-11.
 */
@Controller
public class TradeCtrl extends BaseWebCtrl implements WebApiTrade{

    /**
     * 发起交易
     */
    @RequestMapping(value = WEB_LAUNCH_TRADE_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse launchTrade(@RequestBody TradeReq req) {
        LOG.dStart(this,"发起交易");
        if(!ValueHelper.checkParam(req.getAdvertId(),req.getTradeNumber())){
            throw new WebBizException("请求参数不正确");
        }
        Long tradeId = otc.tradeWebFacade.createTrade(req.getToken(),req.getAdvertId(),req.getTradeNumber(),req.getRemark());
        LOG.dEnd(this, "发起交易");
        TradeInfoResp resp = new TradeInfoResp();
        resp.setTradeId(tradeId);

        return buildWebApiResponse(SUCCESS, resp);
    }

    /**
     * 买家完成支付
     */
    @RequestMapping(value = WEB_BUYER_PAYED_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse buyerPayed(@RequestBody WebApiBaseReq req) {
        LOG.dStart(this,"买家完成支付");
        if(!ValueHelper.checkParam(req.getId())){
            throw new WebBizException("请求参数不正确");
        }
        otc.tradeWebFacade.buyerPayed(req.getToken(),req.getId());
        LOG.dEnd(this, "买家完成支付");
        return buildWebApiResponse(SUCCESS, null);
    }

    /**
     * 卖家确认交易 == 完成交易
     */
    @RequestMapping(value = WEB_SELLER_CONFIRM_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse sellerConfirm(@RequestBody TradeConfirmReq req) {
        LOG.dStart(this,"卖家确认");
        String vrCode = req.getVrCode();
        if(!ValueHelper.checkParam(req.getId())){
            throw new WebBizException("请求参数不正确");
        }
        if(StringUtils.isBlank(vrCode)){
            throw new WebBizException("验证码不能为空");
        }
        otc.tradeWebFacade.sellerConfirm(req.getToken(),req.getId(),vrCode);
        LOG.dEnd(this, "卖家确认");
        return buildWebApiResponse(SUCCESS, null);
    }


    /**
     * 卖家申诉
     */
    @RequestMapping(value = WEB_SELLER_APPEAL_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse sellerAppeal(@RequestBody WebApiBaseReq req) {
        LOG.dStart(this,"卖家申诉");
        if(!ValueHelper.checkParam(req.getId())){
            throw new WebBizException("请求参数不正确");
        }
        otc.tradeWebFacade.sellerAppeal(req.getToken(),req.getId());
        LOG.dEnd(this, "卖家申诉");
        return buildWebApiResponse(SUCCESS, null);
    }


    /**
     * 买家取消交易
     */
    @RequestMapping(value = WEB_BUYER_CANCEL_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse buyerCancel(@RequestBody WebApiBaseReq req) {
        LOG.dStart(this,"买家取消交易");
        if(!ValueHelper.checkParam(req.getId())){
            throw new WebBizException("请求参数不正确");
        }
        otc.tradeWebFacade.buyerCancel(req.getToken(),req.getId());
        LOG.dEnd(this, "买家取消交易");
        return buildWebApiResponse(SUCCESS, null);
    }


    /**
     * 评价交易
     */
    @RequestMapping(value = WEB_JUDGE_TRADE_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse judge(@RequestBody JudgeReq req) {
        LOG.dStart(this,"评价交易");
        if(!ValueHelper.checkParam(req.getTradeId(),req.getLevel())){
            throw new WebBizException("请求参数不正确");
        }
        Long userId = UserCacheManager.getUidWithToken(req.getToken());
        otc.tradeJudgeWebFacade.addJudage(userId,req.getTradeId(),req.getLevel(),req.getContext());
        LOG.dEnd(this, "评价交易");
        return buildWebApiResponse(SUCCESS, null);
    }


    /**
     * 获取交易详情
     * @return
     */
    @RequestMapping(value = WEB_TRADE_INFO_API, method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse tradeInfo(@PathVariable String queryJson) throws Exception{
        WebApiBaseReq req = JsonHelper.jsonStr2Obj(queryJson, WebApiBaseReq.class);
        LOG.dStart(this,"获取交易详情信息");
        Long tradeId = req.getId();
        if(!ValueHelper.checkParam(tradeId)){
            throw new WebBizException("请求参数不正确");
        }
        Trade trade = otc.tradeWebFacade.selectById(tradeId);
        if(trade == null){
            throw new WebBizException("交易信息不存在");
        }
        //获取广告内容
        Advertisement advert = otc.advertisementWebFacade.getById(trade.getAdvertId());
        if(advert == null){
            throw  new WebBizException("广告信息不存在");
        }

        Map<Long,VirtualCoin> coinMap = otc.virtualCoinWebFacade.getAllVirtualCoin();
        VirtualCoin coin = coinMap == null?null:coinMap.get(trade.getCoinId());

        Long userId = WebTokenValidate.validateToken(req);
        User user = otc.userWebFacade.selectById(userId);
        if(user == null){
            throw new WebBizException("用户不存在");
        }
        User other = null; //对方信息
        int ownerSide = 0;
        if(trade.getBuyUserId() == userId){
            ownerSide = TradeSideEnum.BUY.getCode(); //买
            other = otc.userWebFacade.selectById(trade.getSellUserId());
        }else if(trade.getSellUserId() == userId){
            ownerSide = TradeSideEnum.SELL.getCode(); //卖
            other = otc.userWebFacade.selectById(trade.getBuyUserId());
        }else{
            throw new WebBizException("不能查看非本人交易信息");
        }
        Date now = new Date();
        TradeInfoResp resp = new TradeInfoResp();
        ReflectHelper.injectAttrFromSrcObj(trade,resp);
        resp.setTradeId(tradeId);
        resp.setCoinName(coin == null?"":coin.getShortName());
        resp.setOtherId(other.getId());
        resp.setOtherLoginName(other.getLoginName());
        resp.setOwnerSide(ownerSide);
        resp.setPayRemark(advert.getPayTypeRemark());
        resp.setPayType(advert.getPayTypeName());
        resp.setStatusName(TradeStatusEnum.getMap().get(trade.getTradeStatus()));
        resp.setJudgeStatus(trade.getJudgeStatus());

        resp.setTradeNo(trade.getTradeNo());
        resp.setTradeMoney(trade.getTradeAmount());
        resp.setTradeNum(trade.getTradeAccount());
        resp.setTradePrice(trade.getTradePrice());
        resp.setStatus(trade.getTradeStatus());
        resp.setLeftTimes((trade.getPayEndTime().getTime() - now.getTime())/ TimeConstant.ONE_SECOND_UNIT_MILLISECONDS);

        UserAuthentication otherAu = otc.userAuWebFacade.getUserAuthentication(other.getId());
        resp.setOtherKyc(otherAu == null?false: UserAutStatusEnum.isKyc(otherAu.getStatus()));
        resp.setOtherReal(otherAu == null?false: UserAutStatusEnum.isRealName(otherAu.getStatus()));
        resp.setPublisher(userId == advert.getUserId());

        CacheUserInfo cacheUserInfo = UserCacheManager.getCacheObj(other.getId());


        String ip = cacheUserInfo == null?"":cacheUserInfo.getLoginIp();
        String ipAddress ;
        if(cacheUserInfo != null && StringUtils.isNotBlank(cacheUserInfo.getLoginAddress())){
            ipAddress = IpHelper.getAddress(cacheUserInfo.getLoginAddress());
        }else{
            ipAddress = "未知";
        }

        resp.setPageStatus(trade.getTradeStatus(),ownerSide);
        resp.setCity(ipAddress);
        resp.setFee(trade.getTradeFee().toPlainString());

        LOG.dEnd(this, "获取交易详情信息");
        return buildWebApiResponse(SUCCESS, resp);
    }


    /**
     * 获取交易记录
     * @param queryJson
     * @return
     */
    @RequestMapping(value = WEB_TRADE_LIST_API,method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse getTradeList(@PathVariable String queryJson){
        LOG.dStart(this,"获取交易记录");
        TradeListReq req = JsonHelper.jsonStr2Obj(queryJson, TradeListReq.class);
        if (req == null){
            throw new WebBizException("请求参数不能为空");
        }
        Integer tradeType = req.getTradeType();
        Long coinId = req.getCoinId();
        if(tradeType == null || coinId == null){
            throw new WebBizException("请求参数不能为空");
        }
        if(TradeTypeEnum.getMap().get(tradeType) == null){
            throw new WebBizException("请求参数不正确");
        }


        Long userId = UserCacheManager.getUidWithToken(req.getToken());
        TradeVo vo = new TradeVo();
        vo.setPageShow(req.fetchPageFilterSize());
        vo.setNowPage(req.fetchPageFilterPage());
        vo.setUserId(userId);
        vo.setTradeType(tradeType);
        vo.setCoinId(coinId);
        vo = otc.tradeWebFacade.queryByConditionPage(vo);
        List<Trade> list = vo.fatchTransferList();

        List<TradeInfoResp> respList = new ArrayList<>();
        TradeInfoResp resp;

        Map<Long,VirtualCoin> coinMap = otc.virtualCoinWebFacade.getAllVirtualCoin();

        for (Trade trade : list){
            resp = new TradeInfoResp();
            resp.setTradeNo(trade.getTradeNo());
            resp.setCreateTime(trade.getCreateTime());
            resp.setTradeId(trade.getId());
            if(trade.getBuyUserId() == userId){
                resp.setOwnerSide(TradeSideEnum.BUY.getCode());//买
            }else{
                resp.setOwnerSide(TradeSideEnum.SELL.getCode());//卖
            }
            VirtualCoin coin =coinMap == null?null:coinMap.get(trade.getCoinId());
            resp.setCoinName(coin == null?"":coin.getShortName());
            resp.setTradeNum(trade.getTradeAccount());
            resp.setTradeMoney(trade.getTradeAmount());
            Advertisement advertisement = otc.advertisementWebFacade.getById(trade.getAdvertId());
            if(advertisement != null && advertisement.getUserId() == userId){
                resp.setFee(trade.getTradeFee().toPlainString());
            }else{
                resp.setFee("0.00000000");
            }

            resp.setStatus(trade.getTradeStatus());
            resp.setStatusName(TradeStatusEnum.getMap().get(trade.getTradeStatus()));
            respList.add(resp);
        }
        LOG.d(this, respList);
        LOG.dEnd(this,"获取交易记录");
        return buildWebApiPageResponse(vo,respList);
    }


    /**
     * 获取交易评价内容
     * @param queryJson
     * @return
     */
    @RequestMapping(value = WEB_JUDGE_DETAIL_API,method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse getJudgeDetail(@PathVariable String queryJson){
        LOG.dStart(this, "查询console交易状态列表开始");
        WebApiBaseReq req = JsonHelper.jsonStr2Obj(queryJson, WebApiBaseReq.class);
        if (req == null) {
            throw new WebBizException("请求参数为空");
        }
        Long userId = UserCacheManager.getUidWithToken(req.getToken());

        Long tradeId = req.getId();
        Trade trade = otc.tradeWebFacade.selectById(tradeId);
        if(trade == null){
            throw new WebBizException("交易不存在");
        }
        Long otherId;
        if(userId == trade.getBuyUserId()){
            otherId = trade.getSellUserId();
        }else if(userId == trade.getSellUserId()){
            otherId = trade.getBuyUserId();
        }else{
            throw new WebBizException("非本人交易内容");
        }
        User other = otc.userWebFacade.selectById(otherId);
        if(other == null){
            throw new WebBizException("对方信息获取异常");
        }
        List<TradeJudge> list =otc.tradeJudgeWebFacade.getByTradeId(tradeId);
        TradeJudgeResp resp = new TradeJudgeResp();

        resp.setOtherName(other.getLoginName());

        for(TradeJudge judge:list){
            if(judge.getUserId() == userId){
                resp.setOwnerLevel(TradeJudgeLevelEnum.getMap().get(judge.getJudgeLevel()));
                resp.setOwnerContent(judge.getJudgeContext());
            }else if(judge.getUserId() == otherId){
                resp.setOtherLevel(TradeJudgeLevelEnum.getMap().get(judge.getJudgeLevel()));
                resp.setOtherContent(judge.getJudgeContext());
            }
        }
        LOG.dEnd(this, "查询console交易列表结束");
        return buildWebApiResponse(SUCCESS, resp);
    }


    /**
     * 交易超时系统单条取消交易
     */
    @RequestMapping(value = WEB_SYSTEM_CANCEL_API, method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse sysCancel(@PathVariable String queryJson){
        WebApiBaseReq req = JsonHelper.jsonStr2Obj(queryJson, WebApiBaseReq.class);
        LOG.dStart(this,"======================单条交易取消开始======================");
        Long tradeId = req.getId();
        LOG.dStart(this,"交易id"+tradeId);
        if(!ValueHelper.checkParam(req.getId())){
            throw new WebBizException("请求参数不正确");
        }
        otc.tradeWebFacade.sysCancel(req.getToken(),tradeId);
        LOG.dEnd(this, "======================取消交易结束======================");
        return buildWebApiResponse(SUCCESS, null);
    }

}
