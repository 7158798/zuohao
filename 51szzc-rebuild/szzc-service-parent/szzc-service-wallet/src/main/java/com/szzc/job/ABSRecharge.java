package com.szzc.job;

import com.facade.core.wallet.cache.CacheHelper;
import com.facade.core.wallet.cache.key.CacheKey;
import com.jucaifu.common.log.LOG;
import com.szzc.common.utils.BTCMessage;
import com.szzc.core.wallet.pool.WalletBizPool;
import com.szzc.facade.wallet.enums.VirtualCapitalOperationTypeEnum;
import com.szzc.facade.wallet.pojo.cond.VirtualCoinTypeCond;
import com.szzc.facade.wallet.pojo.po.VirtualCapitaloperation;
import com.szzc.facade.wallet.pojo.po.VirtualCoinType;

import java.util.List;

/**
 * 钱包同步定时任务抽象类
 * Created by lx on 17-5-26.
 */
public abstract class ABSRecharge implements Recharge {

    /**
     * 获取区块索引
     * @param key
     * @return
     */
    Long getBlock(Long key){
        // 获取key
        String _key = CacheKey.SYNC_COIN_BLOCK_KEY_PREFIX + key;
        Long block = CacheHelper.getObj(_key);
        return block;
    }

    /**
     * 保存区块索引
     * @param key      key
     * @param block    区块索引
     */
    void putBlock(Long key,Long block){
        // 获取key
        String _key = CacheKey.SYNC_COIN_BLOCK_KEY_PREFIX + key;
        CacheHelper.saveObj(_key,block);
    }

    /**
     * 查询需要同步的币种
     * @return
     */
    private List<VirtualCoinType> queryCoinTypes(){
        VirtualCoinTypeCond cond = new VirtualCoinTypeCond();
        List<String> names = getCoinType();
        cond.setShortNameList(names);
        return queryCoinTypeByCondition(cond);
    }

    /**
     * Get block long.
     *
     * @param virtualCoinType the virtual coin type
     * @param btcMessage      the btc message
     * @return the long
     */
    public Long getBlock(VirtualCoinType virtualCoinType,BTCMessage btcMessage,Boolean dbFlag){
        Long temp = getBlock(virtualCoinType.getFid());
        if (temp != null){
            LOG.d(this,virtualCoinType.getFname()+"-> 缓存中的索引标记是：　" + temp);
            return temp;
        }
        LOG.i(this, virtualCoinType.getFname() + "-> 初始化区块高度开始");
        Long block = 0L;
        if (dbFlag){
            // 需要通过数据库查询
            Integer type = VirtualCapitalOperationTypeEnum.COIN_IN;
            VirtualCapitaloperation capitaloperation = WalletBizPool.getInstance().virtualCapitaloperationBiz.queryLastCapitaloperation(virtualCoinType.getFid(),type);
            if (capitaloperation != null){
                try {
                    // 通过交易记录从钱包中获取交易记录
                    block = getBlock(btcMessage,capitaloperation);
                    if (block == null){
                        block = virtualCoinType.getStartblockid().longValue();
                    }
                } catch (Exception e) {
                    LOG.e(this,virtualCoinType.getFname() + "钱包链接失败，请到后台修改后重启",e);
                }
            } else {
                block = (long)virtualCoinType.getStartblockid() ;
            }
        }
        LOG.i(this, virtualCoinType.getFname() +"-> 开始的区块高度为：" + block);
        putBlock(virtualCoinType.getFid(), block);
        LOG.i(this, virtualCoinType.getFname() +"-> 初始化区块高度结束");
        return block;
    }

    /**
     * Sync record.
     */
    public void syncRecord(){
        List<VirtualCoinType> coins = queryCoinTypes();
        for (VirtualCoinType virtualCoinType : coins) {
            LOG.i(this, virtualCoinType.getFname() + "-> 同步区块数据开始");
            try {

                BTCMessage btcMessage = new BTCMessage();
                btcMessage.setACCESS_KEY(virtualCoinType.getFaccessKey());
                btcMessage.setIP(virtualCoinType.getFip());
                btcMessage.setPORT(virtualCoinType.getFport());
                btcMessage.setSECRET_KEY(virtualCoinType.getFsecrtKey());
                btcMessage.setCOIN_TYPE(virtualCoinType.getFshortname().toUpperCase());
                // 子类同步业务
                syncInfo(virtualCoinType, btcMessage);
            } catch (Exception ex){
                LOG.e(this,virtualCoinType.getFname() + "-> 同步钱包充值记录异常" + ex.getMessage(),ex);
            }
            LOG.i(this, virtualCoinType.getFname() + "-> 同步区块数据结束");
        }
    }

    /**
     * Gets coin type.
     *
     * @return the coin type
     */
    abstract List<String> getCoinType();

    /**
     * Sync info.
     *
     * @param virtualCoinType the virtual coin type
     * @param btcMessage      the btc message
     * @throws Exception the exception
     */
    abstract void syncInfo(VirtualCoinType virtualCoinType,BTCMessage btcMessage) throws Exception;

    /**
     * Gets block.
     *
     * @param btcMessage       the btc message
     * @param capitaloperation the capitaloperation
     * @return the block
     * @throws Exception the exception
     */
    abstract Long getBlock(BTCMessage btcMessage,VirtualCapitaloperation capitaloperation) throws Exception;
}
