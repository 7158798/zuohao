package com.szzc.job;

import com.jucaifu.common.log.LOG;
import com.szzc.common.utils.BTCInfo;
import com.szzc.common.utils.BTCMessage;
import com.szzc.common.utils.BTCUtils;
import com.szzc.facade.wallet.pojo.po.VirtualCapitaloperation;
import com.szzc.facade.wallet.pojo.po.VirtualCoinType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * BTC\LTC\ZEC　钱包同步定时任务
 * Created by lx on 17-5-27.
 */
@Component
public class BTCRecharge extends ABSRecharge {
    Long step = 1000l;
    /**
     * 
     */
    @Scheduled(cron = "30 3/3 * * * ?")
    public void syncRecharge(){
        synchronized (this) {
            syncRecord();
        }
    }

    @Override
    List<String> getCoinType() {
        List<String> names = new ArrayList<>();
        names.add(BTC);
        names.add(LTC);
        names.add(ZEC);
        names.add(TIC);
        return names;
    }

    @Override
    void syncInfo(VirtualCoinType virtualCoinType, BTCMessage btcMessage) throws Exception {
        BTCUtils btcUtils = new BTCUtils(btcMessage);
        Boolean flag = Boolean.TRUE;
        // 索引不需要数据库查询
        //Long begin = super.getBlock(virtualCoinType,btcMessage,Boolean.FALSE);
        // 从零开始查询充值记录
        Long begin = 0l;
        List<BTCInfo> btcInfos;
        while (flag){
            btcInfos = btcUtils.listtransactionsValue(step.intValue(), begin.intValue());
            if(btcInfos == null || btcInfos.size() == 0){
                LOG.d(this,virtualCoinType.getFname() + "->没有获取到钱包的数据");
                // 没有新的记录,退出循环
                flag = false;
            } else {
                // 获取的数据大小
                int size = btcInfos.size() == step.intValue()?step.intValue():btcInfos.size();
                // 增加索引标记
                begin += size;
            }
            LOG.d(this,virtualCoinType.getFname() + "->索引是：" + begin);
            if (btcInfos != null && btcInfos.size() > 0){
                for (BTCInfo btcInfo : btcInfos){
                    String txId = btcInfo.getTxid().trim();
                    String address = btcInfo.getAddress().trim();
                    Double amount = btcInfo.getAmount();
                    // 新增充值记录
                    addFvirtualcaptualoperation(address,amount,txId,virtualCoinType,Boolean.FALSE);
                }
                //putBlock(virtualCoinType.getFid(),begin);
            }
        }
    }

    @Override
    Long getBlock(BTCMessage btcMessage, VirtualCapitaloperation capitaloperation) throws Exception {
        return null;
    }
}
