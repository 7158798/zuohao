package com.szzc.job;

import com.jucaifu.common.log.LOG;
import com.szzc.common.utils.BTCInfo;
import com.szzc.common.utils.BTCMessage;
import com.szzc.common.utils.ETHUtils;
import com.szzc.common.utils.eth.EthAddressUtils;
import com.szzc.common.utils.eth.bean.Debug;
import com.szzc.common.utils.eth.bean.StructLogs;
import com.szzc.common.utils.eth.resp.DebugResp;
import com.szzc.core.wallet.pool.WalletBizPool;
import com.szzc.facade.wallet.enums.VirtualCapitalOperationInStatusEnum;
import com.szzc.facade.wallet.enums.VirtualCapitalOperationTypeEnum;
import com.szzc.facade.wallet.pojo.cond.VirtualCoinTypeCond;
import com.szzc.facade.wallet.pojo.po.VirtualAddress;
import com.szzc.facade.wallet.pojo.po.VirtualCapitaloperation;
import com.szzc.facade.wallet.pojo.po.VirtualCoinType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 以太坊相关充值记录
 * Created by lx on 17-5-25.
 */
@Component
public class ETHRecharge extends ABSRecharge {


    /**
     * Sync recharge.
     */
    @Scheduled(cron = "10 1/3 * * * ?")
    public void syncRecharge(){
        synchronized (this){
            syncRecord();
        }
    }

    @Override
    List<String> getCoinType() {
        List<String> names = new ArrayList<>();
        names.add(ETH);
        names.add(ETC);
        return names;
    }

    @Override
    void syncInfo(VirtualCoinType virtualCoinType, BTCMessage btcMessage) throws Exception {
        ETHUtils ethUtils = new ETHUtils(btcMessage);
        Long block = super.getBlock(virtualCoinType,btcMessage,Boolean.TRUE);
        while(ethUtils.eth_blockNumberValue()>block){
            LOG.d(this,virtualCoinType.getFname() + "-> 同步区块的高度:" + block);
            // 获取所有eth的可用地址
            List<VirtualAddress> list = WalletBizPool.getInstance().virtualAddressBiz.queryListByCoinId(virtualCoinType.getFid());
            Map<String,VirtualAddress> addressMap = packUserAddress(list);
            long count = ethUtils.eth_getBlockTransactionCountByNumberValue(block) ;
            for (int i = 0; i < count; i++) {
                BTCInfo btcInfo = ethUtils.getTransactionByBlockNumberAndIndexValue(block, i);
                String txid = btcInfo.getTxid();
                String address = btcInfo.getAddress();
                if (address != null){
                    address = address.trim();
                }
                VirtualAddress virtualAddress = addressMap.get(address);
                if (virtualAddress == null && !"0x".equals(btcInfo.getInput())){
                    // 有可能是合约转账
                    if (addressMap.size() != 0){
                        // 没有找到,有可能是合约转账
                        String tAddress = getRechargeAddress(addressMap,btcInfo.getInput());
                        if (StringUtils.isNotEmpty(tAddress)){
                            try {
                                // 执行debug指令
                                DebugResp response = ethUtils.debug_traceTransaction(txid);
                                List<StructLogs> structLogs = getStructLogs(response);
                                if (structLogs != null && structLogs.size() > 0){
                                    for (StructLogs struct : structLogs){
                                        List<String> stackList = struct.getStack();
                                        if (stackList != null && stackList.size() > 2){
                                            int index = stackList.size();
                                            // 获取地址
                                            String value = stackList.get(index-2);
                                            value = EthAddressUtils.toHexStringWithPrefixZeroPadded(EthAddressUtils.toBigInt(value));
                                            virtualAddress = addressMap.get(value);
                                            if (virtualAddress != null){
                                                // 地址核对成功,充值金额
                                                String tAmount = stackList.get(index-3);
                                                double t = EthAddressUtils.parseAmount(tAmount);
                                                // 合同转账
                                                addFvirtualcaptualoperation(virtualAddress,t,txid,virtualCoinType,Boolean.TRUE);
                                            }
                                        }
                                    }
                                }
                            } catch (Exception ex){
                                LOG.e(this,"合约转账处理失败" + ex.getMessage(),ex);
                            }
                        }
                    }
                } else {
                    if (virtualAddress != null){
                        // 正常充值的
                        addFvirtualcaptualoperation(virtualAddress,btcInfo.getAmount(),txid,virtualCoinType,Boolean.FALSE);
                    }
                }
            }
            block = block+1;
            // 保存区块索引
            putBlock(virtualCoinType.getFid(), block);
        }
    }

    @Override
    Long getBlock(BTCMessage btcMessage, VirtualCapitaloperation capitaloperation) throws Exception {
        ETHUtils ethUtils = new ETHUtils(btcMessage) ;
        BTCInfo btcInfo = ethUtils.eth_getTransactionByHashValue(capitaloperation.getFtradeuniquenumber()) ;
        Long block = btcInfo.getBlockNumber() ;
        return block;
    }


    /**
     * Update recharge.
     */
//@Scheduled(cron = "0/120 * * * * ?")
    public void updateRecharge(){
        synchronized (this) {
            try{
                /*List<CoinShorNameAndFtrademappingId> coins = this.queryCoinTypes();
                for (CoinShorNameAndFtrademappingId virtualCoinType : coins) {
                    try {
                        BTCMessage msg = new BTCMessage();
                        msg.setACCESS_KEY(virtualCoinType.getFaccessKey());
                        msg.setIP(virtualCoinType.getFip());
                        msg.setPORT(virtualCoinType .getFport());
                        msg.setSECRET_KEY(virtualCoinType.getFsecrtKey());
                        ETHUtils ethUtils = new ETHUtils(msg) ;

                        List<VirtualCapitaloperation> rList = queryWaitCapitaloperation(virtualCoinType.getFid().intValue());
                        for (VirtualCapitaloperation temp : rList) {
                            try {
                                String txid = temp.getFtradeuniquenumber();
                                if (StringUtils.isEmpty(txid)){
                                    LOG.i(this,virtualCoinType.getFname() + "-> 交易id为空,ID为: " + temp.getFid());
                                    continue;
                                }
                                BTCInfo btcInfo = ethUtils.eth_getTransactionByHashValue(txid);
                                if (temp.getIsContract() && temp.getFconfirmations() >= 3){
                                    // 不需要同步确认数
                                    continue;
                                }
                                if (btcInfo.getConfirmations() >= 3 && !temp.getIsContract()){
                                    temp.setFstatus(VirtualCapitalOperationInStatusEnum.SUCCESS) ;
                                } else if (btcInfo.getConfirmations() > 0){
                                    temp.setFstatus(VirtualCapitalOperationInStatusEnum.WAIT_1);
                                }
                                temp.setFconfirmations(btcInfo.getConfirmations());
                                temp.setFlastupdatetime(new Date());
                                // 更新充值记录
                                WalletBizPool.getInstance().virtualCapitaloperationBiz.updateFvirtualcaptualoperationCoinIn(temp);
                            } catch (Exception e) {
                                LOG.e(this,virtualCoinType.getFname() + "-> 同步区块数据异常,ID为: " + temp.getFid(),e);
                            }
                        }
                    } catch (Exception e) {
                        LOG.e(this,virtualCoinType.getFname() + "-> 同步区块数据异常",e);
                    }
                }*/
            }catch (Exception e) {
                LOG.e(this,"同步区块数据异常", e);
            }
        }
    }


    /**
     * 获取debug中的stack
     *
     * @param resp the resp
     * @return list
     */
    public List<StructLogs> getStructLogs(DebugResp resp){

        Debug debug = resp.getResult();
        if (debug == null){
            return null;
        }
        List<StructLogs> structLogsList = debug.getStructLogs();
        if (structLogsList == null){
            return null;
        }
        List<StructLogs> rList = new ArrayList<>();
        for (StructLogs structLogs : structLogsList){
            if ("CALL".equals(structLogs.getOp())){
                rList.add(structLogs);
            }
        }
        return rList;

    }

    /**
     * 查找充值地址
     *
     * @param map   the map
     * @param input the input
     * @return string
     */
    public String getRechargeAddress(Map<String,VirtualAddress> map,String input){
        String address = null;
        for (Map.Entry<String,VirtualAddress> entry:map.entrySet()){
            String temp = entry.getKey().substring(2);
            if (input.indexOf(temp) > 0){
                address = entry.getKey();
                break;
            }
        }
        return address;
    }

    /**
     * 包装用户地址
     *
     * @param list the list
     * @return map
     */
    public Map<String,VirtualAddress> packUserAddress(List<VirtualAddress> list){
        Map<String,VirtualAddress> map = new HashMap();
        for (VirtualAddress virtualAddress : list){
            map.put(virtualAddress.getFadderess().toLowerCase(),virtualAddress);
        }
        return map;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        String test = "0xAE7B68755F3b45a0618CC13CFe3169d96daEACeB".toUpperCase();
        System.out.println(test);
        System.out.println(test.toLowerCase());
    }
}
