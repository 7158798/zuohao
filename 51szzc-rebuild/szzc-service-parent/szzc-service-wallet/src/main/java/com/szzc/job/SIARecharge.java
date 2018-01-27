package com.szzc.job;

import com.jucaifu.common.log.LOG;
import com.szzc.common.utils.BTCMessage;

import com.szzc.common.utils.siac.SIACUtils;
import com.szzc.common.utils.siac.resp.*;
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
public class SIARecharge extends ABSRecharge {
    int i = 0;


    @Scheduled(cron = "3 2/2 * * * ?")
    public void syncRecharge(){
        synchronized (this) {
            syncRecord();
        }
    }

    @Override
    List<String> getCoinType() {
        List<String> names = new ArrayList<>();
        names.add(SC);
        return names;
    }

    @Override
    void syncInfo(VirtualCoinType virtualCoinType, BTCMessage btcMessage) throws Exception {
        LOG.i(this,"开始sc同步钱包数据");
        // 同步钱包数据开始
        Long height = super.getBlock(virtualCoinType, btcMessage,Boolean.TRUE);
        btcMessage.setPASSWORD("rEj*mBs3zA*L5vpo");
      //  btcMessage.setPASSWORD("fenggq123");
        SIACUtils siacUtils = new SIACUtils(btcMessage);
        siacUtils.unlock();
        Consensus consensus = siacUtils.getConsensus();
        //为什么要减去10？？？？SI

//        if(i == 0){
//            height = 110928l;
//            i++;
//        }
        try{
            if(consensus!= null&&consensus.isSynced() && consensus.getHeight()-11 > height) {
                LOG.d(this, virtualCoinType.getFname() + "-> 同步区块的高度:" + height);
                Transactions resp = siacUtils.getTransactions(height,Long.parseLong(String.valueOf(consensus.getHeight())));
                List<Transaction> transactions = resp.getConfirmedtransactions();
                if(transactions != null){
                    for (Transaction transaction : transactions) {
                        int num = transaction.getConfirmationheight();
                        if (num == 0)
                            continue;
                        String txId = transaction.getTransactionid();
                        if(transaction.getOutputs().size() == 2) {
                            OutputsBean outputsBean1 = transaction.getOutputs().get(0);
                            OutputsBean outputsBean2 = transaction.getOutputs().get(1);
                            if(outputsBean1.isWalletaddress() && outputsBean2.isWalletaddress()){
                                //过滤提币返回钱包的数据
                                continue;
                            }
                        }
                        for (OutputsBean outputsBean : transaction.getOutputs()) {
                            if(!StringUtils.equals(outputsBean.getFundtype(),SIACUtils.SIAC_TYPE)){
                                continue;
                            }
                            if(!outputsBean.isWalletaddress()){
                                continue;
                            }
                            String address = outputsBean.getRelatedaddress();
                            LOG.i(this,"充值地址"+address+"===========================");

                            if (StringUtils.isNotEmpty(address) && StringUtils.isNotEmpty(txId)) {
                                BigDecimal amount = siacUtils.receiveFormat(new BigDecimal(outputsBean.getValue()));
                                addFvirtualcaptualoperation(address,amount.doubleValue(),txId,virtualCoinType,Boolean.FALSE);
                            }
                        }
                    }
                }
                height = consensus.getHeight()-11;
                putBlock(virtualCoinType.getFid(),height);
            }
        }catch (Exception e){
            e.printStackTrace();
            LOG.e(this.getClass(),"出错");
        }finally {
            siacUtils.lock();
        }
        LOG.i(this,"开始sc同步钱包数据结束");

    }

    @Override
    Long getBlock(BTCMessage btcMessage, VirtualCapitaloperation capitaloperation) throws Exception {
        Long block = 1l;
        SIACUtils lskUtils = new SIACUtils(btcMessage);
        TransactionId resp = lskUtils.getTransactionById(capitaloperation.getFtradeuniquenumber());
        block = resp.getTransaction().getConfirmationheight();
        return block;
    }


}
