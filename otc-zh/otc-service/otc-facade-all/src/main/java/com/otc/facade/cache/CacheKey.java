package com.otc.facade.cache;

/**
 * Created by lx on 17-4-20.
 */
public class CacheKey {
    // 充值定时任务的key前缀
    public static String RECHARGE_KEY = "RECHARGE_KEY_";
    // 可用的币种key
    public static String VIRTUAL_COIN_KEY = "VIRTUAL_COIN_KEY_";
    // 可用的币种key
    public static String VIRTUAL_COIN_ALL_KEY = "VIRTUAL_COIN_ALL_KEY_";
    // 提现地址key
    public static String WITHDRAW_ADDRESS_KEY = "WITHDRAW_ADDRESS_KEY_";
    // 充值地址key
    public static String RECHARGE_ADDRESS_KEY = "RECHARGE_ADDRESS_KEY_";
    // 第三方平台数据
    public static String OTHERPLATFORM_PRICE_KEY = "OTHERPLATFORM_PRICE_KEY";
    // 钱包提币工具类
    public static final String WITHDRAW_WALLET_UTIL_KEY = "WITHDRAW_WALLET_UTIL_KEY_";

    public static String buildWithdrawKey(Long key){
        return WITHDRAW_WALLET_UTIL_KEY + "_" + key;
    }

}
