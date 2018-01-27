package com.base.common.wallet;

import java.math.BigDecimal;

/**
 * Created by lx on 17-4-17.
 */
public interface Wallet {
    /**
     * 生成地址
     * @param temp
     * @return
     */
    String createAddress(String ...temp);

    /**
     * 获取钱包的余额
     * @return
     */
    BigDecimal getBalance(String ...temp);


}
