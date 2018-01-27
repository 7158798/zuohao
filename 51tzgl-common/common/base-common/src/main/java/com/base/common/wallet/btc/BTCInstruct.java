package com.base.common.wallet.btc;

/**
 * json-rpc 指令
 * Created by lx on 17-4-17.
 */
public interface BTCInstruct {
    // 获取一个btc的地址
    String GET_NEW_ADDRESS = "getnewaddress";
    // 获取钱包中的余额
    String GET_BALANCE = "getbalance";
}
