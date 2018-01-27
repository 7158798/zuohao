package com.otc.api.console.ctrl.trade;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.jucaifu.common.util.ValueHelper;
import com.otc.api.console.base.BaseConsoleCtrl;
import com.otc.api.console.constants.ConsoleApiTrade;
import com.otc.api.console.ctrl.trade.req.TradeListReq;
import com.otc.api.console.ctrl.trade.req.TradeReq;
import com.otc.api.console.ctrl.trade.resp.TradeJudgeResp;
import com.otc.api.console.ctrl.trade.resp.TradeListResp;
import com.otc.api.console.exceptions.ConsoleBizException;
import com.otc.api.console.utils.ConsoleTokenValidate;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.facade.trade.enums.TradeCancelEnum;
import com.otc.facade.trade.enums.TradeJudgeLevelEnum;
import com.otc.facade.trade.enums.TradeStatusEnum;
import com.otc.facade.trade.pojo.po.Trade;
import com.otc.facade.trade.pojo.po.TradeJudge;
import com.otc.facade.trade.pojo.vo.TradeVo;
import com.otc.facade.user.pojo.po.UserAuthentication;
import com.otc.facade.user.pojo.poex.UserReportEx;
import com.otc.facade.user.pojo.vo.UserVo;
import com.otc.facade.virtual.pojo.po.VirtualCoin;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 交易后台管理
 * Created by fenggq on 17-5-15.
 */
@Controller
public class TradeConsoleCtrl extends BaseConsoleCtrl implements ConsoleApiTrade{

    /**
     * 条件查询交易列表
     */
    @RequestMapping(value = TRADE_LIST_QUERY_API, method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse getTradeListByConditionPage(@PathVariable String queryJson)throws Exception {
        LOG.dStart(this, "查询console交易列表开始");
        TradeListReq req = JsonHelper.jsonStr2Obj(queryJson, TradeListReq.class);
        if (req == null) {
            throw new ConsoleBizException("请求参数为空");
        }

        Integer status = req.getStatus();
        String userInfo = req.getUserInfo();
        String tradeNo = req.getTradeNo();
        int type = req.getType();
        List<Long> userIds = new ArrayList<>();

        TradeVo vo = new TradeVo();
        vo.setPageShow(req.fetchPageFilterSize());
        vo.setNowPage(req.fetchPageFilterPage());
        //交易单号
        if(StringUtils.isNotBlank(tradeNo)){
            vo.setTradeNo(tradeNo);
        }
        //日期
        vo.setDate(req.fetchDateFilterStart(),req.fetchDateFilterEnd(),type);
        //状态
        vo.setQueryStatus(status,type);
        //排序方式
        vo.setOrderType(type);
        //用户信息
        if(StringUtils.isNotBlank(userInfo)){
            UserVo userVo = new UserVo();
            userVo.setUserInfo(userInfo);
            userVo = otc.userConsoleFacade.selectUserListByConditionPage(userVo);
            List<UserReportEx> userList = userVo.fatchTransferList();
            for(UserReportEx user:userList){
                userIds.add(user.getId());
            }
            if(userList.size() == 0){
                return buildWebApiPageResponse(vo, null);
            }
            vo.setUserIds(userIds);
        }
        vo = otc.tradeConsoleFacade.queryListByConditionPage(vo);
        List<Trade> list = vo.fatchTransferList();
        List<TradeListResp> respList = new ArrayList<>();

        //币种
        Map<Long,VirtualCoin> coinMap = otc.virtualCoinConsoleFacade.getAllVirtualCoin();

        for(Trade trade:list){
            VirtualCoin coin = coinMap == null?null:coinMap.get(trade.getCoinId());
            TradeListResp resp = new TradeListResp();
            resp.setTradeId(trade.getId());
            resp.setTradeNo(trade.getTradeNo());
            resp.setCoinName(coin == null?"":coin.getName());
            resp.setTradeNum(trade.getTradeAccount().toString());
            resp.setTradePrice(trade.getTradePrice().toString());
            resp.setTradeMoney(trade.getTradeAmount().toString());
            resp.setFee(trade.getTradeFee().toString());

            //User buyer = otc.userConsoleFacade.selectById(trade.getBuyUserId());
            UserAuthentication buyer = otc.userAuConsoleFacade.getUserAuthentication(trade.getBuyUserId());
            UserAuthentication seller = otc.userAuConsoleFacade.getUserAuthentication(trade.getSellUserId());

            resp.setBuyerId(trade.getBuyUserId());
            resp.setSellerId(trade.getSellUserId());
            resp.setBuyer(buyer == null?"":buyer.getRealName());
            resp.setSeller(seller == null?"":seller.getRealName());
            resp.setStatus(TradeStatusEnum.getNameByCode(trade.getTradeStatus()));
            resp.setUpdateTime(trade.getUpdateTime());
            resp.setCreateTime(trade.getCreateTime());
            resp.setChartId(trade.getChartId());
            resp.setRemark(trade.getRemarkConsole());
            resp.setCancelReason(TradeCancelEnum.getMap().get(trade.getCancelType()));
            respList.add(resp);
        }
        LOG.d(this, JsonHelper.obj2JsonStr(respList));

        LOG.dEnd(this, "查询console交易列表结束");
        return buildWebApiPageResponse(vo, respList);
    }


    /**
     *  后台确认放币
     */
    @RequestMapping(value = TRADE_CONFIRM_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse adminConfirm(@RequestBody TradeReq req) {
        LOG.dStart(this,"后台确认放币");
        Long tradeId = req.getTradeId();
        String remark = req.getRemark();

        if(!ValueHelper.checkParam(tradeId)){
            throw new ConsoleBizException("请求参数不正确");
        }
        Long empoyeeId = ConsoleTokenValidate.validateToken(req);

        otc.tradeConsoleFacade.adminConfirm(empoyeeId,tradeId,remark,req.getConfirmPwd());

        LOG.dEnd(this, "后台确认放币");
        return buildWebApiResponse(SUCCESS, null);
    }


    /**
     *  后台取消放币
     */
    @RequestMapping(value = TRADE_CANCEL_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse adminCancel(@RequestBody TradeReq req) {
        LOG.dStart(this,"后台取消交易");
        Long tradeId = req.getTradeId();
        String remark = req.getRemark();

        if(!ValueHelper.checkParam(tradeId)){
            throw new ConsoleBizException("请求参数不正确");
        }
        Long empoyeeId = ConsoleTokenValidate.validateToken(req);

        otc.tradeConsoleFacade.cancelTradeByAdmin(empoyeeId,tradeId,remark,req.getConfirmPwd());
        LOG.dEnd(this, "后台取消交易");
        return buildWebApiResponse(SUCCESS, null);
    }


    /**
     *  后台确认放币 == 进行中
     */
    @RequestMapping(value = TRADE_CONFIRM_RUN_API, method = RequestMethod.POST)
    @ResponseBody
    public WebApiResponse adminConfirmForRun(@RequestBody TradeReq req) {
        LOG.dStart(this,"后台确认放币");
        Long tradeId = req.getTradeId();
        String remark = req.getRemark();

        if(!ValueHelper.checkParam(tradeId)){
            throw new ConsoleBizException("请求参数不正确");
        }
        Long empoyeeId = ConsoleTokenValidate.validateToken(req);

        otc.tradeConsoleFacade.adminConfirmForRun(empoyeeId,tradeId,remark,req.getConfirmPwd());
        LOG.dEnd(this, "后台确认放币");
        return buildWebApiResponse(SUCCESS, null);
    }




    /**
     * 查看评价
     */
    @RequestMapping(value = TRADE_JUDGE_DETAIL_API, method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse getTradeJudgeDetail(@PathVariable String queryJson)throws Exception {
        LOG.dStart(this, "查询console交易状态列表开始");
        WebApiBaseReq req = JsonHelper.jsonStr2Obj(queryJson, WebApiBaseReq.class);
        if (req == null) {
            throw new ConsoleBizException("请求参数为空");
        }
        Long tradeId = req.getId();
        Trade trade = otc.tradeConsoleFacade.selectById(tradeId);
        if(trade == null){
            throw new ConsoleBizException("交易不存在");
        }
        List<TradeJudge> list =otc.judgeConsoleFacade.getByTradeId(tradeId);
        TradeJudgeResp resp = new TradeJudgeResp();
        resp.setTradeNo(trade.getTradeNo());
        for(TradeJudge judge:list){
            if(judge.getUserId() == trade.getBuyUserId()){
                resp.setBuyLevel(TradeJudgeLevelEnum.getMap().get(judge.getJudgeLevel()));
                resp.setBuyContent(judge.getJudgeContext());
            }else if(judge.getUserId() == trade.getSellUserId()){
                resp.setSellLevel(TradeJudgeLevelEnum.getMap().get(judge.getJudgeLevel()));
                resp.setSellContent(judge.getJudgeContext());
            }
        }
        LOG.dEnd(this, "查询console交易列表结束");
        return buildWebApiResponse(SUCCESS, resp);
    }









}
