package com.base.common.wallet.eth;

import com.base.common.wallet.AbsWallet;
import com.base.common.wallet.WalletParam;

import java.math.BigDecimal;

/**
 * Created by lx on 17-4-17.
 */
public class ETHWallet extends AbsWallet{

    private ETHWallet(){

    }

    public ETHWallet(WalletParam param){
        this.param = param;
    }

    @Override
    public String createAddress(String... temp) {
        return null;
    }

    @Override
    public BigDecimal getBalance(String... temp) {
        return null;
    }
}
