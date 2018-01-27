package com.otc.job;

import com.jucaifu.common.log.LOG;
import com.otc.facade.trade.pojo.po.Trade;
import com.otc.pool.OTCBizPool;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * 交易相关定时任务
 * Created by fenggq on 17-5-23.
 */
@Component
public class TradeJob {

    /**
     * 清理超时交易
     */
    @Scheduled(cron = "0 2/2 * * * ? ")
    private void tradeCancel(){
        LOG.dStart(this,"取消交易---------------------");
        List<Trade> trades = OTCBizPool.getInstance().tradeBiz.getTimeOutList();
        LOG.i(this,"共需要取消"+trades.size()+"条记录");
        for(Trade trade:trades){
            try{
                OTCBizPool.getInstance().tradeBiz.cancelForTimeOut(trade);
            }catch (Exception e){
                LOG.e(this.getClass(),"取消交易出错,交易编号："+trade.getTradeNo());
                e.printStackTrace();
           }
        }
        OTCBizPool.getInstance().tradeBiz.cancelForTimeOut();

        LOG.dEnd(this,"取消交易-----------------------");
    }


    /**
     * 超期自动好评
     */
    @Scheduled(cron = "0 1/10 * * * ? ")
    private void userAssetRunning(){
        LOG.dStart(this,"------------自动好评---------------------");
        List<Trade> trades = OTCBizPool.getInstance().tradeBiz.getJudgeTimeOutList();
        LOG.i(this,"共有"+trades.size()+"条交易未完成评价");
        for(Trade trade:trades){
            try{
                OTCBizPool.getInstance().tradeJudgeBiz.addJudageBySystem(trade.getId());
            }catch (Exception e){
                LOG.e(this.getClass(),"取消交易出错,交易编号："+trade.getTradeNo());
                e.printStackTrace();
            }
        }
        OTCBizPool.getInstance().tradeBiz.cancelForTimeOut();

        LOG.dEnd(this,"------------自动好评---------------------");
    }
}
