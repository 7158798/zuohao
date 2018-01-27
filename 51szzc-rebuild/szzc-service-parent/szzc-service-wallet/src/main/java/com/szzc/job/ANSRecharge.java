package com.szzc.job;

import com.jucaifu.common.log.LOG;
import com.szzc.common.utils.BTCMessage;
import com.szzc.common.utils.ans.ANSUtils;
import com.szzc.common.utils.ans.bean.Block;
import com.szzc.common.utils.ans.bean.Transaction;
import com.szzc.common.utils.ans.bean.TxOutInfo;
import com.szzc.common.utils.ans.resp.BlockResp;
import com.szzc.common.utils.ans.resp.TransactionResp;
import com.szzc.facade.wallet.pojo.po.VirtualCapitaloperation;
import com.szzc.facade.wallet.pojo.po.VirtualCoinType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 17-4-5.
 */
@Component
public class ANSRecharge extends ABSRecharge {

    /**
     * 同步小蚁的定时任务
     */
    @Scheduled(cron = "20 2/3 * * * ?")
    public void syncRecharge(){
        synchronized (this) {
            syncRecord();
        }
    }

    @Override
    List<String> getCoinType() {
        List<String> names = new ArrayList<>();
        names.add(ANS);
        return names;
    }

    @Override
    void syncInfo(VirtualCoinType virtualCoinType, BTCMessage btcMessage) throws Exception {
        // 同步钱包数据开始
        Long block = super.getBlock(virtualCoinType, btcMessage,Boolean.TRUE);
        ANSUtils ansUtils = new ANSUtils(btcMessage);
        while(ansUtils.ans_blockNumberValue() > block){
            LOG.d(this, virtualCoinType.getFname() + "-> 同步区块的高度:" + block);
            BlockResp resp = ansUtils.ans_getblock(block);
            Block temp = resp.getResult();
            if (temp.getTx() != null && temp.getTx().size() > 1){
                // 每个区块第一个交易必定是 MinerTransaction 在遍历交易时第一个交易可以忽略或跳过。
                for (Transaction tx : temp.getTx()){
                    String txId = tx.getTxid().trim();
                    if (StringUtils.equals(tx.getType(), ANSUtils.CONTRACTTRANSACTION)){
                        // 正常的交易
                        List<TxOutInfo> txInfos = tx.getVout();
                        if (txInfos != null && txInfos.size() > 0){
                            for (TxOutInfo outInfo : txInfos){
                                String assetId = outInfo.getAsset();
                                if (StringUtils.isEmpty(assetId)){
                                    continue;
                                }
                                if (!StringUtils.equals(assetId,ANSUtils.ANS_ASSET_ID)){
                                    // 非小蚁股的资产
                                    continue;
                                }
                                String address = outInfo.getAddress().trim();
                                Double amount = outInfo.getValue().doubleValue();
                                addFvirtualcaptualoperation(address,amount,txId,virtualCoinType,Boolean.FALSE);
                            }
                        }

                    }
                }
            }
            block = block+1 ;
            putBlock(virtualCoinType.getFid(), block);
        }
    }

    @Override
    Long getBlock(BTCMessage btcMessage, VirtualCapitaloperation capitaloperation) throws Exception {
        Long block;
        ANSUtils ansUtils = new ANSUtils(btcMessage);
        TransactionResp resp = ansUtils.ans_getrawtransaction(capitaloperation.getFtradeuniquenumber());
        Transaction transaction = resp.getResult();
        BlockResp blockResp = ansUtils.ans_getblock(transaction.getBlockhash());
        Block tBlock = blockResp.getResult();
        block = tBlock.getIndex();
        return block;
    }


}
