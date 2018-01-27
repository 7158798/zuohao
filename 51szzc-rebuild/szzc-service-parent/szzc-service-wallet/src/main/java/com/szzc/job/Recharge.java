package com.szzc.job;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.common.utils.CoinType;
import com.szzc.core.wallet.pool.WalletBizPool;
import com.szzc.facade.wallet.enums.CoinTypeEnum;
import com.szzc.facade.wallet.enums.VirtualCapitalOperationInStatusEnum;
import com.szzc.facade.wallet.enums.VirtualCapitalOperationTypeEnum;
import com.szzc.facade.wallet.pojo.cond.VirtualCapitaloperationCond;
import com.szzc.facade.wallet.pojo.cond.VirtualCoinTypeCond;
import com.szzc.facade.wallet.pojo.po.VirtualAddress;
import com.szzc.facade.wallet.pojo.po.VirtualCapitaloperation;
import com.szzc.facade.wallet.pojo.po.VirtualCoinType;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 共同充值定时任务
 * Created by lx on 17-5-25.
 */
public interface Recharge extends CoinType {


    /**
     * Query coin type by condition list.
     *
     * @param cond the cond
     * @return the list
     */
    default List<VirtualCoinType> queryCoinTypeByCondition(VirtualCoinTypeCond cond){
        cond.setNoType(CoinTypeEnum.FB_CNY_VALUE);
        cond.setIsRecharge(Boolean.TRUE);
        return WalletBizPool.getInstance().virtualCoinTypeBiz.queryCoinTypeByCondition(cond);
    }

    /**
     * Add fvirtualcaptualoperation.
     *
     * @param virtualAddress  the virtual address
     * @param amount          the amount
     * @param txId            the tx id
     * @param virtualCoinType the virtual coin type
     * @param isContract      the is contract
     */
    default void addFvirtualcaptualoperation(VirtualAddress virtualAddress,Double amount,String txId,VirtualCoinType virtualCoinType,Boolean isContract){

        VirtualCapitaloperationCond cond = new VirtualCapitaloperationCond();
        cond.setTxId(txId);
        cond.setAddress(virtualAddress.getFadderess());
        List<VirtualCapitaloperation> rList = WalletBizPool.getInstance().virtualCapitaloperationBiz.queryListByCondition(cond);
        if(rList != null && rList.size() > 0){
            LOG.d(this,virtualCoinType.getFname() + "-> 该充值记录已经存在(不会新增充值记录)：" + JsonHelper.obj2JsonStr(cond));
            return;
        }
        VirtualCapitaloperation fvirtualcaptualoperation = new VirtualCapitaloperation() ;
        fvirtualcaptualoperation.setFusFid2(virtualAddress.getFuid());
        fvirtualcaptualoperation.setFhasowner(true);
        BigDecimal _amount = new BigDecimal(String.valueOf(amount));
        // 舍弃6位小数以后的
        _amount = _amount.setScale(6,BigDecimal.ROUND_DOWN);
        fvirtualcaptualoperation.setFamount(_amount) ;
        fvirtualcaptualoperation.setFcreatetime(new Date());
        fvirtualcaptualoperation.setFfees(BigDecimal.ZERO) ;
        fvirtualcaptualoperation.setFlastupdatetime(new Date()) ;
        if(StringUtils.equals(virtualCoinType.getFshortname(),CoinType.SC)){
            //sc自动确认
            fvirtualcaptualoperation.setFconfirmations(1) ;
            fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.WAIT_1) ;
        }else{
            fvirtualcaptualoperation.setFconfirmations(0) ;
            fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.WAIT_0) ;
        }



        fvirtualcaptualoperation.setFtradeuniquenumber(txId) ;
        fvirtualcaptualoperation.setRechargeVirtualAddress(virtualAddress.getFadderess()) ;
        fvirtualcaptualoperation.setFtype(VirtualCapitalOperationTypeEnum.COIN_IN);
        // 增加版本号
        fvirtualcaptualoperation.setVersion(0);
        // 虚拟币货币id
        fvirtualcaptualoperation.setFviFid2(virtualAddress.getFviFid());
        fvirtualcaptualoperation.setIsContract(isContract);
        LOG.i(this, virtualCoinType.getFname() + "-> 新增充值记录：" + JsonHelper.obj2JsonStr(fvirtualcaptualoperation));
        WalletBizPool.getInstance().virtualCapitaloperationBiz.insert(fvirtualcaptualoperation);
    }


    /**
     * 新增充值记录（校验数据的充值记录）
     * @param address
     * @param amount
     * @param txId
     * @param virtualCoinType
     * @param isContract
     */
    default void  addFvirtualcaptualoperation(String address,Double amount,String txId,VirtualCoinType virtualCoinType,Boolean isContract){
        List<VirtualAddress> aList = WalletBizPool.getInstance().virtualAddressBiz.queryListByCoinIdAndAddress(virtualCoinType.getFid(),address);
        if (aList != null && aList.size() > 0){
            if (aList.size() == 1){
                // 新增充值记录
                this.addFvirtualcaptualoperation(aList.get(0),amount,txId,virtualCoinType,isContract);
            } else {
                // 存在相同的记录
                LOG.d(this, virtualCoinType.getFname() + "-> 存在相同的两个地址,不能充值,地址:" + address);
            }
        }
    }

    /**
     * Query wait capitaloperation list.
     *
     * @param coinId the coin id
     * @return the list
     */
    default List<VirtualCapitaloperation> queryWaitCapitaloperation(Integer coinId){
        VirtualCapitaloperationCond cond = new VirtualCapitaloperationCond();
        cond.setCoinId(Long.valueOf(coinId));
        cond.setType(VirtualCapitalOperationTypeEnum.COIN_IN);
        // 不等成功的
        cond.setNoStatus(VirtualCapitalOperationInStatusEnum.SUCCESS);
        return WalletBizPool.getInstance().virtualCapitaloperationBiz.queryListByCondition(cond);
    }
}
