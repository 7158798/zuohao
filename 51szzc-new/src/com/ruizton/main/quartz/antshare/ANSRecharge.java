package com.ruizton.main.quartz.antshare;

import com.ruizton.main.Enum.CoinType;
import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationInStatusEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationTypeEnum;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.*;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.main.service.front.UtilsService;
import com.ruizton.util.Utils;
import com.ruizton.util.antshare.*;
import com.ruizton.util.antshare.bean.Block;
import com.ruizton.util.antshare.bean.Transaction;
import com.ruizton.util.antshare.bean.TxOutInfo;
import com.ruizton.util.antshare.resp.BlockResp;
import com.ruizton.util.antshare.resp.TransactionResp;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lx on 17-4-5.
 */
@Component
public class ANSRecharge {
    @Autowired
    private VirtualCoinService virtualCoinService;
    @Autowired
    private FrontVirtualCoinService frontVirtualCoinService ;
    @Autowired
    private UtilsService utilsService;
    // 区块ID
    private Map<Integer, Long> map = new HashMap<Integer, Long>();

    public void putMap(Integer key,Long value){
        synchronized (map) {
            map.put(key, value) ;
        }
    }

    public Long getMap(Integer key){
        synchronized (key) {
            return map.get(key) ;
        }
    }

    @PostConstruct
    public void init(){
        String filter = "where fstatus=1 and fShortName = '" + CoinType.ANS +"' and fisrecharge=1 and ftype <>"+ CoinTypeEnum.FB_CNY_VALUE;
        List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
        //获取钱包中新数据
        for (Fvirtualcointype fvirtualcointype : coins) {
            List<Fvirtualcaptualoperation> fvirtualcaptualoperations = this.utilsService.list(0, 1, " where ftype=? and fvirtualcointype.fid=? order by fid desc ", true, Fvirtualcaptualoperation.class, VirtualCapitalOperationTypeEnum.COIN_IN,fvirtualcointype.getFid()) ;
            Long block = 0L ;
            if(fvirtualcaptualoperations.size() > 0 ){
                try {
                    Fvirtualcaptualoperation fvirtualcaptualoperation = fvirtualcaptualoperations.get(0) ;
                    BTCMessage msg = new BTCMessage();
                    msg.setACCESS_KEY(fvirtualcointype .getFaccess_key());
                    msg.setIP(fvirtualcointype.getFip());
                    msg.setPORT(fvirtualcointype .getFport());
                    msg.setSECRET_KEY(fvirtualcointype .getFsecrt_key());
                    ANSUtils ansUtils = new ANSUtils(msg);
                    TransactionResp resp = ansUtils.ans_getrawtransaction(fvirtualcaptualoperation.getFtradeUniqueNumber());
                    Transaction transaction = resp.getResult();
                    BlockResp blockResp = ansUtils.ans_getblock(transaction.getBlockhash());
                    Block tBlock = blockResp.getResult();
                    block = tBlock.getIndex();
                    if (block == null){
                        block = Long.valueOf(fvirtualcointype.getStartBlockId());
                    }
                } catch (Exception e) {
                    LOG.e(this,fvirtualcointype.getFname() + "钱包链接失败，请到后台修改后重启",e);
                }
            }else{
                block = (long)fvirtualcointype.getStartBlockId() ;
            }
            putMap(fvirtualcointype.getFid(), block) ;
        }
    }


    @Scheduled(cron = "30 1/3 * * * ?")
    public void syncRecharge(){
        synchronized (this) {
            LOG.i(this,"同步小蚁的充值记录开始");
            String filter = "where fstatus=1 and fShortName = '" + CoinType.ANS +"' and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
            List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
            for (Fvirtualcointype fvirtualcointype : coins) {
                try {
                    BTCMessage btcMessage = new BTCMessage() ;
                    btcMessage.setACCESS_KEY(fvirtualcointype.getFaccess_key()) ;
                    btcMessage.setIP(fvirtualcointype.getFip()) ;
                    btcMessage.setPORT(fvirtualcointype.getFport()) ;
                    btcMessage.setSECRET_KEY(fvirtualcointype.getFsecrt_key()) ;

                    ANSUtils ansUtils = new ANSUtils(btcMessage);
                    Long block = getMap(fvirtualcointype.getFid());
                    while(ansUtils.ans_blockNumberValue() > block){
                        BlockResp resp = ansUtils.ans_getblock(block);
                        Block temp = resp.getResult();
                        if (temp.getTx() != null && temp.getTx().size() > 1){
                            // 每个区块第一个交易必定是 MinerTransaction 在遍历交易时第一个交易可以忽略或跳过。
                            for (Transaction tx : temp.getTx()){
                                String txId = tx.getTxid().trim();
                                if (StringUtils.equals(tx.getType(),ANSUtils.CONTRACTTRANSACTION)){
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
                                            int c = this.utilsService.count(" where ftradeUniqueNumber='" + txId + "' and recharge_virtual_address = '" + address + "' ", Fvirtualcaptualoperation.class) ;
                                            if(c > 0){
                                                // 存在相同的交易记录
                                                continue;
                                            }
                                            List<Fvirtualaddress> fvirtualaddresses = this.frontVirtualCoinService.findFvirtualaddress(fvirtualcointype, address);
                                            if(fvirtualaddresses.size()==1){
                                                Fvirtualcaptualoperation fvirtualcaptualoperation = new Fvirtualcaptualoperation() ;
                                                Fvirtualaddress fvirtualaddress = fvirtualaddresses.get(0) ;
                                                Fuser fuser = fvirtualaddress.getFuser() ;
                                                fvirtualcaptualoperation.setFuser(fuser) ;
                                                fvirtualcaptualoperation.setFhasOwner(true) ;
                                                // 充值的数量
                                                fvirtualcaptualoperation.setFamount(outInfo.getValue().doubleValue()) ;
                                                fvirtualcaptualoperation.setFcreateTime(Utils.getTimestamp()) ;
                                                fvirtualcaptualoperation.setFfees(0F) ;
                                                fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp()) ;

                                                fvirtualcaptualoperation.setFconfirmations(0) ;
                                                fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.WAIT_0) ;

                                                fvirtualcaptualoperation.setFtradeUniqueNumber(txId) ;
                                                fvirtualcaptualoperation.setRecharge_virtual_address(address) ;
                                                fvirtualcaptualoperation.setFtype(VirtualCapitalOperationTypeEnum.COIN_IN);
                                                fvirtualcaptualoperation.setFvirtualcointype(fvirtualcointype);
                                                this.frontVirtualCoinService.addFvirtualcaptualoperation(fvirtualcaptualoperation) ;
                                            }
                                        }
                                    }

                                }
                            }
                        }
                        block = block+1 ;
                        putMap(fvirtualcointype.getFid(), block);
                    }
                } catch (Exception e) {
                    LOG.e(this,"同步Ans充值记录失败",e);
                }
            }
            LOG.i(this,"同步小蚁的充值记录结束");
        }
    }

   // @Scheduled(cron = "0/120 * * * * ?")
    public void updateRecharge(){
        synchronized (this) {
            LOG.i(this,"更新小蚁的充值记录开始");
            try{
                String filter = "where fstatus=1 and fShortName = '" + CoinType.ANS +"' and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
                List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
                for (Fvirtualcointype fvirtualcointype : coins) {
                    try {
                        BTCMessage msg = new BTCMessage();
                        msg.setACCESS_KEY(fvirtualcointype .getFaccess_key());
                        msg.setIP(fvirtualcointype.getFip());
                        msg.setPORT(fvirtualcointype .getFport());
                        msg.setSECRET_KEY(fvirtualcointype .getFsecrt_key());
                        ANSUtils ansUtils = new ANSUtils(msg) ;

                        List<Fvirtualcaptualoperation> fvirtualcaptualoperations = this.utilsService.list(0, 1, " where ftype=? and fvirtualcointype.fid=? and fstatus<>? order by fid desc ", true, Fvirtualcaptualoperation.class,VirtualCapitalOperationTypeEnum.COIN_IN,fvirtualcointype.getFid(),VirtualCapitalOperationInStatusEnum.SUCCESS) ;
                        for (Fvirtualcaptualoperation fvirtualcaptualoperation : fvirtualcaptualoperations) {
                            try {
                                String txid = fvirtualcaptualoperation.getFtradeUniqueNumber() ;
                                TransactionResp resp = ansUtils.ans_getrawtransaction(txid);
                                Transaction transaction = resp.getResult();
                                if (transaction.getConfirmations() >= 0){
                                    fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.SUCCESS) ;
                                }
                                fvirtualcaptualoperation.setFconfirmations(transaction.getConfirmations());
                                fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp()) ;
                                this.frontVirtualCoinService.updateFvirtualcaptualoperationCoinIn(fvirtualcaptualoperation);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e) {
                LOG.e(this,"更新小蚁的充值记录失败",e);
            }
            LOG.i(this,"更新小蚁的充值记录结束");
        }
    }
}
