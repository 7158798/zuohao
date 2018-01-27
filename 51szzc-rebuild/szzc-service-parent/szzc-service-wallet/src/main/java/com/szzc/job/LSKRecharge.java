package com.szzc.job;

import com.jucaifu.common.log.LOG;
import com.szzc.common.utils.BTCMessage;
import com.szzc.common.utils.lsk.LSKUtils;
import com.szzc.common.utils.lsk.bean.Block;
import com.szzc.common.utils.lsk.bean.Transaction;
import com.szzc.common.utils.lsk.resp.BlockHeightResp;
import com.szzc.common.utils.lsk.resp.BlocksResp;
import com.szzc.common.utils.lsk.resp.TransactionResp;
import com.szzc.common.utils.lsk.resp.TransactionsResp;
import com.szzc.facade.wallet.pojo.po.VirtualCapitaloperation;
import com.szzc.facade.wallet.pojo.po.VirtualCoinType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 17-4-5.
 */
@Component
public class LSKRecharge extends ABSRecharge {

    @Scheduled(cron = "40 1/3 * * * ?")
    public void syncRecharge(){
        synchronized (this) {
            syncRecord();
        }
    }

    @Override
    List<String> getCoinType() {
        List<String> names = new ArrayList<>();
        names.add(LSK);
        return names;
    }

    @Override
    void syncInfo(VirtualCoinType virtualCoinType, BTCMessage btcMessage) throws Exception {
        // 同步钱包数据开始
        Long height = super.getBlock(virtualCoinType, btcMessage,Boolean.TRUE);
        LSKUtils lskUtils = new LSKUtils(btcMessage);
        BlockHeightResp heightResp = lskUtils.get_height();
        while(heightResp.getHeight() > height) {
            LOG.d(this, virtualCoinType.getFname() + "-> 同步区块的高度:" + height);
            BlocksResp resp = lskUtils.get_blocks(height);
            List<Block> blocks = resp.getBlocks();
            for (Block block : blocks) {
                Long num = block.getNumberOfTransactions();
                if (num == 0)
                    continue;
                TransactionsResp transactions = lskUtils.transactions(block.getId(), num, 0l);
                for (Transaction transaction : transactions.getTransactions()) {
                    String address = transaction.getRecipientId();
                    String txId = transaction.getId();
                    if (StringUtils.isNotEmpty(address) && StringUtils.isNotEmpty(txId)) {
                        BigDecimal amount = lskUtils.receiveFormat(transaction.getAmount());
                        addFvirtualcaptualoperation(address,amount.doubleValue(),txId,virtualCoinType,Boolean.FALSE);
                    }
                }
            }
            height = height+1;
            putBlock(virtualCoinType.getFid(),height);
        }
    }

    @Override
    Long getBlock(BTCMessage btcMessage, VirtualCapitaloperation capitaloperation) throws Exception {
        Long block;
        LSKUtils lskUtils = new LSKUtils(btcMessage);
        TransactionResp resp = lskUtils.transactions(capitaloperation.getFtradeuniquenumber());
        Transaction transaction = resp.getTransaction();
        block = transaction.getHeight();
        return block;
    }


}
