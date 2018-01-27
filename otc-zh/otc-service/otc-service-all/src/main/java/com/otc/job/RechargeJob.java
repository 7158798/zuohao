package com.otc.job;

import com.base.wallet.utils.BTCInfo;
import com.base.wallet.utils.BTCMessage;
import com.base.wallet.utils.BTCUtils;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.core.cache.CacheHelper;
import com.otc.facade.cache.CacheKey;
import com.otc.facade.virtual.enums.VirtualRecordInStatus;
import com.otc.facade.virtual.enums.VirtualRecordType;
import com.otc.facade.virtual.pojo.cond.UserAddressCond;
import com.otc.facade.virtual.pojo.cond.VirtualRecordCond;
import com.otc.facade.virtual.pojo.po.UserAddress;
import com.otc.facade.virtual.pojo.po.VirtualCoin;
import com.otc.facade.virtual.pojo.po.VirtualRecord;
import com.otc.pool.OTCBizPool;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 充值定时任务
 * Created by lx on 17-4-17.
 */
@Component
public class RechargeJob {
    private int step = 1000;

    @Scheduled(cron = "20/120 * * * * ? ")
    public void recharge(){
        synchronized (this){
            Map<Long,VirtualCoin> coinMap = CacheHelper.getObj(CacheKey.VIRTUAL_COIN_KEY);
            if (coinMap == null || coinMap.size() == 0){
                LOG.d(this,"没有配置虚拟币");
                return;
            }
            for (Map.Entry<Long,VirtualCoin> entry : coinMap.entrySet()){
                VirtualCoin coin = entry.getValue();
                BTCMessage btcMessage = new BTCMessage() ;
                btcMessage.setACCESS_KEY(coin.getAccessKey()) ;
                btcMessage.setIP(coin.getIp()) ;
                btcMessage.setPORT(coin.getPort()) ;
                btcMessage.setSECRET_KEY(coin.getSecretKey()) ;

                BTCUtils btcUtils = new BTCUtils(btcMessage) ;
                boolean is_continue = true;
                int begin = 0;
                List<BTCInfo> btcInfos;
                while (is_continue){
                    try {
                        btcInfos = btcUtils.listtransactionsValue(step, begin);
                        if(btcInfos == null || btcInfos.size() == 0){
                            // 没有新的记录,退出循环
                            is_continue = false;
                        } else {
                            // 获取的数据大小
                            int size = btcInfos.size() == step?step:btcInfos.size();
                            // 增加索引标记
                            begin += size;
                        }
                    } catch (Exception e) {
                        LOG.e(this,"获取交易记录发生异常" + coin.getName(),e);
                        break;
                    }
                    if (btcInfos != null && btcInfos.size() > 0){
                        for (BTCInfo btcInfo : btcInfos) {
                            String txid = btcInfo.getTxid().trim() ;
                            String address = btcInfo.getAddress().trim();
                            List<VirtualRecord> list = OTCBizPool.getInstance().virtualRecordBiz.queryListByTxId(txid, address);
                            if (list != null && list.size() > 0){
                                continue;
                            }
                            VirtualRecord record = new VirtualRecord();
                            UserAddressCond uCond = new UserAddressCond();
                            uCond.setAddress(address);
                            uCond.setCoinId(coin.getId());
                            UserAddress userAddress = OTCBizPool.getInstance().userAddressBiz.queryUserAddress(uCond);
                            if (userAddress == null){
                                LOG.i(this,"存在充值记录,但是地址不存在.数据为:" + JsonHelper.obj2JsonStr(btcInfo));
                                continue;
                            }

                            // 查询交易记录是否存在
                            String txId = btcInfo.getTxid();
                            VirtualRecordCond rCond = new VirtualRecordCond();
                            rCond.setTxId(txId);
                            rCond.setAddress(address);
                            List<VirtualRecord> rList = OTCBizPool.getInstance().virtualRecordBiz.queryListVirtualRecord(rCond);
                            if (rList == null || rList.size() == 0){
                                record.setAddress(address);
                                record.setUserId(userAddress.getUserId());
                                record.setCoinId(coin.getId());
                                record.setFees(BigDecimal.ZERO);
                                record.setConfirmations(0);
                                record.setTxId(btcInfo.getTxid());
                                BigDecimal amount = new BigDecimal(String.valueOf(btcInfo.getAmount()));
                                record.setAmount(amount);
                                record.setType(VirtualRecordType.RECHARGE.getCode());
                                record.setStatus(VirtualRecordInStatus.WAIT.getCode());
                                record.setModifiedDate(new Date());
                                OTCBizPool.getInstance().virtualRecordBiz.saveVirtualRecord(record);
                            }
                        }
                    }
                }
            }

        }
    }

    @Scheduled(cron = "0/120 * * * * ? ")
    public void sync(){
        synchronized (this){
            LOG.dStart(this,"同步虚拟币的充值记录开始");
            Map<Long,VirtualCoin> coinMap = CacheHelper.getObj(CacheKey.VIRTUAL_COIN_KEY);
            if (coinMap == null || coinMap.size() == 0){
                LOG.d(this,"没有配置虚拟币");
                return;
            }
            for (Map.Entry<Long,VirtualCoin> entry : coinMap.entrySet()){
                VirtualCoin coin = entry.getValue();
                BTCMessage btcMessage = new BTCMessage();
                btcMessage.setACCESS_KEY(coin.getAccessKey());
                btcMessage.setIP(coin.getIp());
                btcMessage.setPORT(coin.getPort());
                btcMessage.setSECRET_KEY(coin.getSecretKey());

                BTCUtils btcUtils = new BTCUtils(btcMessage);
                VirtualRecordCond cond = new VirtualRecordCond();
                cond.setStatus(VirtualRecordInStatus.WAIT.getCode());
                cond.setType(VirtualRecordType.RECHARGE.getCode());
                List<VirtualRecord> list = OTCBizPool.getInstance().virtualRecordBiz.queryListVirtualRecord(cond);
                if (list != null && list.size() > 0){
                    BTCInfo btcInfo;
                    for (VirtualRecord record : list){
                        try {
                            btcInfo = btcUtils.gettransactionValue(record.getTxId(),"receive");
                        } catch (Exception e) {
                            LOG.e(this,"获取虚拟币充值记录失败,交易id = " + record.getId(),e);
                            continue;
                        }
                        if (btcInfo.getConfirmations() > 0){
                            record.setConfirmations(btcInfo.getConfirmations());
                            if (coin.getIsAuto() && record.getConfirmations() >= 3){
                                record.setStatus(VirtualRecordInStatus.SUCCESS.getCode());
                            }
                            // 更新充值记录和同步钱包
                            OTCBizPool.getInstance().virtualRecordBiz.autoRecharge(record);
                        }
                    }
                }
            }
            LOG.dEnd(this, "同步虚拟币的充值记录结束");
        }
    }
/*
    public class RechargeThread extends Thread{

        private VirtualCoin temp;

        public RechargeThread(VirtualCoin coin){
            this.temp = coin;
        }

        public void run(){
            // 缓存key
            String key = CacheKey.RECHARGE_KEY + temp.getId();
            RechargePo cache = CacheHelper.getObj(key);
            if (cache == null){
                cache = new RechargePo();
                cache.setBlock(0l);
            }
            Boolean flag = Boolean.TRUE;
            while (flag){

            }
        }
    }*/
}
