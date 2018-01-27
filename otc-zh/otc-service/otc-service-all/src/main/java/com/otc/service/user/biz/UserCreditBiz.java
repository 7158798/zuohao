package com.otc.service.user.biz;

import com.jucaifu.common.exceptions.biz.BizException;
import com.jucaifu.common.log.LOG;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.core.cache.UserCacheManager;
import com.otc.facade.trade.enums.TradeJudgeLevelEnum;
import com.otc.facade.user.pojo.po.UserCreditRecord;
import com.otc.facade.user.pojo.vo.UserCreditRecordVo;
import com.otc.service.user.dao.UserCreditRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by fenggq on 17-5-11.
 */
@Service
public class UserCreditBiz extends AbsBaseBiz<UserCreditRecord,UserCreditRecordVo,UserCreditRecordMapper>{
    @Autowired
    private  UserCreditRecordMapper userCreditRecordMapper;
    @Override
    public UserCreditRecordMapper getDefaultMapper() {
        return userCreditRecordMapper;
    }

    /**
     * 通过用户id获取记录
     * @param userId
     * @return
     */
    public UserCreditRecord getByUserId(Long userId){
       return this.getDefaultMapper().getByUserId(userId);
    }

    /**
     *  创建记录
     * @param userId
     */
    public void add(Long userId){
        LOG.dStart(this,"创建用户信用记录");
        UserCreditRecord record = new UserCreditRecord();
        record.setUserId(userId);
        this.insert(record);

        UserCacheManager.deleteCacheObj(userId);
        LOG.dStart(this,"创建用户信用记录");
    }

    /**
     * 交易发起时调用 交易总次数、卖出总次数、买入总次数
     * @param buyerId
     * @param sellerId
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addAccountForInit(Long buyerId,Long sellerId){
        UserCreditRecord buy = this.getByUserId(buyerId);
        Date now = new Date();
        if(buy == null){
            buy = new UserCreditRecord();
            buy.setUserId(buyerId);
            buy.setTradeAllTimes(1);
            buy.setBuyAllTimes(1);
            buy.setCreateTime(now);
            this.insert(buy);
        }else{
            buy.setLastTradeTime(now);
            buy.setBuyAllTimes(buy.getBuyAllTimes() == null?1:buy.getBuyAllTimes()+1);
            buy.setTradeAllTimes(buy.getTradeAllTimes() == null?1:buy.getTradeAllTimes()+1);
            this.update(buy);
        }

        UserCreditRecord sell = this.getByUserId(sellerId);
        if(sell == null){
            sell = new UserCreditRecord();
            sell.setUserId(sellerId);
            sell.setTradeAllTimes(1);
            sell.setSellAllTimes(1);
            sell.setCreateTime(now);
            this.insert(sell);
        }else{
            sell.setTradeAllTimes(sell.getTradeAllTimes() == null?1:sell.getTradeAllTimes()+1);
            sell.setSellAllTimes(sell.getSellAllTimes() == null?1:sell.getSellAllTimes()+1);
            sell.setLastTradeTime(now);
            this.update(sell);
        }
        UserCacheManager.deleteCacheObj(buyerId);
        UserCacheManager.deleteCacheObj(sellerId);
    }

    /**
     * 交易确认时调用 交易完成次数、卖出成功次数、买入成功次数、放行时间
     * @param buyerId
     * @param sellerId
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addAccountForConfirm(Long buyerId,Long sellerId,Integer confirmTimes){
        //买家
        UserCreditRecord buy = this.getByUserId(buyerId);
        Date now = new Date();
        if(buy == null){
            throw new BizException("数据异常");
        }else{
            //成功交易总次数
            buy.setBuySucessTimes(buy.getBuySucessTimes() == null?1:buy.getBuySucessTimes()+1);
            //成功购买总次数
            buy.setTradeSucessTimes(buy.getTradeSucessTimes() == null?1:buy.getTradeSucessTimes()+1);
            buy.setLastTradeTime(now);
            this.update(buy);
        }

        UserCreditRecord sell = this.getByUserId(sellerId);
        if(sell == null){
            throw new BizException("数据异常");
        }else{
            //成功交易总次数
            sell.setTradeSucessTimes(sell.getSellSucessTimes() == null?1:sell.getSellSucessTimes()+1);
            //成功卖出次数
            sell.setSellSucessTimes(sell.getSellSucessTimes() == null?1:sell.getSellSucessTimes()+1);
            if(confirmTimes != null && confirmTimes >= 0){
                sell.setConfirmMinute(sell.getConfirmMinute() == null?confirmTimes:((sell.getConfirmMinute()*(sell.getTradeSucessTimes()-1))+confirmTimes)/sell.getTradeSucessTimes());
            }
            sell.setLastTradeTime(now);
            this.update(sell);
        }

        UserCacheManager.deleteCacheObj(buyerId);
        UserCacheManager.deleteCacheObj(sellerId);
    }

    /**
     * 申诉时调用 申诉次数、被申诉次数
     * @param buyerId
     * @param sellerId
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addAccountForAppeal(Long buyerId,Long sellerId){
        //买家
        UserCreditRecord buy = this.getByUserId(buyerId);
        Date now =new Date();
        if(buy == null){
            throw new BizException("数据异常");
        }else{
            //被申诉次数
            buy.setBeAppealedTimes(buy.getBeAppealedTimes() == null?1:buy.getBeAppealedTimes()+1);
            buy.setLastTradeTime(now);
            this.update(buy);
        }

        UserCreditRecord sell = this.getByUserId(sellerId);
        if(sell == null){
            throw new BizException("数据异常");
        }else{
            //申诉次数
            sell.setAppealTimes(sell.getAppealTimes() == null?1:sell.getAppealTimes()+1);
            sell.setLastTradeTime(now);
            this.update(sell);
        }

        UserCacheManager.deleteCacheObj(buyerId);
        UserCacheManager.deleteCacheObj(sellerId);
    }


    /**
     * 评价时调用 被评价次数、好评次数
     */
    public void addAccountForJudge(Long userId,Integer level){
        //买家
        UserCreditRecord user = this.getByUserId(userId);
        Date now =new Date();
        if(user == null){
            throw new BizException("数据异常");
        }
        //被评价次数
        user.setJudgeTimes(user.getJudgeTimes() == null?1:user.getJudgeTimes()+1);
        user.setLastTradeTime(now);
        if(TradeJudgeLevelEnum.isGood(level)){
            user.setGoodJudgeTimes(user.getGoodJudgeTimes() == null?1:user.getGoodJudgeTimes()+1);
        }else if(TradeJudgeLevelEnum.isBad(level)){
            user.setBadJudgeTimes(user.getBadJudgeTimes() == null?1:user.getBadJudgeTimes()+1);
        }else if(TradeJudgeLevelEnum.isCommon(level)){
            user.setCommonJudgeTimes(user.getCommonJudgeTimes() == null?1:user.getCommonJudgeTimes()+1);
        }
        this.update(user);

        UserCacheManager.deleteCacheObj(userId);

    }



}
