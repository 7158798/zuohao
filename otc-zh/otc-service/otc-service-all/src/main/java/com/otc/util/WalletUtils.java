package com.otc.util;

import com.base.wallet.utils.BTCMessage;
import com.base.wallet.utils.BTCUtils;
import com.base.wallet.utils.IWalletUtil;
import com.otc.core.cache.CacheHelper;
import com.otc.facade.cache.CacheKey;
import com.otc.facade.virtual.pojo.po.VirtualCoin;

import java.util.Map;

/**
 * 钱包对象生成
 * Created by lx on 17-5-9.
 */
public class WalletUtils {

    public static IWalletUtil getWalletObj(Long coinId,String walletPwd){

        Map<Long,VirtualCoin> coinMap = CacheHelper.getObj(CacheKey.VIRTUAL_COIN_KEY);
        if (coinMap != null){
            VirtualCoin coin = coinMap.get(coinId);
            return getWalletObj(coin,walletPwd);
        }
        return null;
    }


    public static IWalletUtil getWalletObj(VirtualCoin coin,String walletPwd){
        if (coin == null){
            return null;
        }
        BTCMessage btcMessage = new BTCMessage() ;
        btcMessage.setACCESS_KEY(coin.getAccessKey()) ;
        btcMessage.setIP(coin.getIp()) ;
        btcMessage.setPORT(coin.getPort()) ;
        btcMessage.setSECRET_KEY(coin.getSecretKey()) ;
        btcMessage.setPASSWORD(walletPwd);
        return new BTCUtils(btcMessage);
    }
}
