package com.otc.service.trade.biz;

import com.jucaifu.common.exceptions.biz.BizException;
import com.jucaifu.common.log.LOG;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.facade.trade.enums.TradeJudgeEnum;
import com.otc.facade.trade.enums.TradeJudgeLevelEnum;
import com.otc.facade.trade.enums.TradeStatusEnum;
import com.otc.facade.trade.pojo.po.Trade;
import com.otc.facade.trade.pojo.po.TradeJudge;
import com.otc.facade.trade.pojo.vo.TradeJudgeVo;
import com.otc.facade.user.enums.UserMessageConstantEnum;
import com.otc.facade.user.pojo.po.User;
import com.otc.pool.OTCBizPool;
import com.otc.service.trade.dao.TradeJudgeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by fenggq on 17-5-10.
 */
@Service
public class TradeJudgeBiz extends AbsBaseBiz<TradeJudge,TradeJudgeVo,TradeJudgeMapper> {
    @Autowired
    private TradeJudgeMapper TradeJudgeMapper;
    @Override
    public TradeJudgeMapper getDefaultMapper() {
        return TradeJudgeMapper;
    }


    /**
     * 交易评价
     * @param userId
     * @param tradeId
     * @param level
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addJudage(Long userId,Long tradeId,Integer level,String context){
        LOG.dStart(this,"评价交易");
        User user = OTCBizPool.getInstance().userBiz.select(userId);
        if(user == null){
            throw new BizException("用户不存在");
        }
        Trade trade = OTCBizPool.getInstance().tradeBiz.select(tradeId);
        if(trade == null){
            throw new BizException("交易不存在");
        }
        if(TradeJudgeLevelEnum.getMap().get(level) == null){
            throw new BizException("评价无效");
        }
        if(!TradeStatusEnum.isComplete(trade.getTradeStatus())){
            throw new BizException("交易未完成，不能评价");
        }
        Integer status = 0;
        Long acceptId = 0L;
        if(trade.getBuyUserId() == userId){
            acceptId = trade.getSellUserId();
            status = TradeJudgeEnum.getJudgeStatus(trade.getJudgeStatus(), TradeJudgeEnum.BUY.getCode());
        }else if (trade.getSellUserId() == userId){
            acceptId = trade.getBuyUserId();
            status = TradeJudgeEnum.getJudgeStatus(trade.getJudgeStatus(),TradeJudgeEnum.SELL.getCode());
        }
        if(status <= 0){
            throw new BizException("当前状态不能评价");
        }

        //新增评价
        TradeJudge judge = new TradeJudge();
        judge.setUserId(userId);
        judge.setTradeId(tradeId);
        judge.setAcceptUserId(acceptId);
        judge.setCreatetime(new Date());
        judge.setJudgeContext(context); //评价内容
        judge.setJudgeLevel(level);

        this.insert(judge);

        //修改交易状态
        trade.setJudgeStatus(status);
        OTCBizPool.getInstance().tradeBiz.update(trade);

        //修改信用记录
        OTCBizPool.getInstance().userCreditBiz.
                addAccountForJudge(acceptId,level);

        //发送消息
        StringBuffer message = new StringBuffer();
        message.append("交易订单号："+trade.getTradeNo());
        message.append("，交易员"+user.getLoginName());
        message.append("对您本次的交易评价为："+TradeJudgeLevelEnum.getMap().get(level));
        message.append("!"+judge.getJudgeContext());

        OTCBizPool.getInstance().messageBiz.sendMessage(acceptId, UserMessageConstantEnum.JUDGE_RESULT,message.toString(),tradeId);

        LOG.dEnd(this,"评价交易完成");
    }



    /**
     * 系统自动好评
     * @param tradeId
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addJudageBySystem(Long tradeId){
        User user = null;
        Trade trade = OTCBizPool.getInstance().tradeBiz.select(tradeId);
        if(trade == null){
            throw new BizException("交易不存在");
        }
        if(!TradeStatusEnum.isComplete(trade.getTradeStatus())){
            return;
        }
        if(trade.getJudgeStatus() == TradeJudgeEnum.BUY.getCode() ||
                trade.getJudgeStatus() == TradeJudgeEnum.INIT.getCode()){
            user = OTCBizPool.getInstance().userBiz.select(trade.getSellUserId());
            //新增卖家评价评价
            TradeJudge judge = new TradeJudge();
            judge.setUserId(trade.getSellUserId());
            judge.setTradeId(tradeId);
            judge.setAcceptUserId(trade.getBuyUserId());
            judge.setCreatetime(new Date());
            judge.setJudgeContext("未在规定时间评价，已由系统默认好评"); //评价内容
            judge.setJudgeLevel(TradeJudgeLevelEnum.GOOD.getCode());
            this.getDefaultMapper().insert(judge);

            //修改信用记录
            OTCBizPool.getInstance().userCreditBiz.
                    addAccountForJudge(trade.getBuyUserId(),TradeJudgeLevelEnum.GOOD.getCode());

            //发送消息
            sendMessage(trade.getTradeNo(), user.getLoginName(), 5, trade.getBuyUserId(), tradeId);
        }
        if(trade.getJudgeStatus() == TradeJudgeEnum.SELL.getCode() ||
                trade.getJudgeStatus() == TradeJudgeEnum.INIT.getCode()){
            user = OTCBizPool.getInstance().userBiz.select(trade.getBuyUserId());
            //新增买家评价
            TradeJudge judge = new TradeJudge();
            judge.setUserId(trade.getBuyUserId());
            judge.setTradeId(tradeId);
            judge.setAcceptUserId(trade.getSellUserId());
            judge.setCreatetime(new Date());
            judge.setJudgeContext("未在规定时间评价，已由系统默认好评"); //评价内容
            judge.setJudgeLevel(TradeJudgeLevelEnum.GOOD.getCode());
            this.getDefaultMapper().insert(judge);

            //修改信用记录
            OTCBizPool.getInstance().userCreditBiz.
                    addAccountForJudge(trade.getSellUserId(),TradeJudgeLevelEnum.GOOD.getCode());
            //发送消息
            sendMessage(trade.getTradeNo(), user.getLoginName(), 5, trade.getSellUserId(), tradeId);
        }


        //修改交易状态
        trade.setJudgeStatus(TradeJudgeEnum.COMPLETE.getCode());
        OTCBizPool.getInstance().tradeBiz.update(trade);


        LOG.dEnd(this,"评价交易完成");
    }

    public void sendMessage(String tradeNo, String userName, Integer level, Long acceptId, Long tradeId){
        StringBuffer message = new StringBuffer();
        message.append("交易订单号："+tradeNo);
        message.append("，交易员"+userName);
        message.append("对您本次的交易评价为："+TradeJudgeLevelEnum.getMap().get(level) + "!");

        OTCBizPool.getInstance().messageBiz.sendMessage(acceptId, UserMessageConstantEnum.JUDGE_RESULT,message.toString(),tradeId);
    }



    /**
     * 按照条件查询评价
     * @param tradeId
     * @return
     */
    public List<TradeJudge> selectByCondition(Long tradeId){
        return getDefaultMapper().selectByCondition(tradeId);
    }


}
