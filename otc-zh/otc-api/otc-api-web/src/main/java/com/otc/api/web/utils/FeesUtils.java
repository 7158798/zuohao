package com.otc.api.web.utils;

import com.otc.api.web.exceptions.WebBizException;
import com.otc.facade.virtual.pojo.po.VirtualCoin;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by lx on 17-6-15.
 */
public class FeesUtils {

    /**
     * 计算交易手续费
     * @param map       货币配置
     * @param coinId    币种
     * @param amount    数量
     * @return
     */
    public static BigDecimal getFees(Map<Long,VirtualCoin> map,Long coinId,BigDecimal amount){

        BigDecimal result = BigDecimal.ZERO;
        VirtualCoin coin = map.get(coinId);
        if (coin == null){
            throw new WebBizException("获取不存在");
        }
        // 计算交易手续费
        result = amount.multiply(coin.getWithdrawFees().divide(new BigDecimal(100)));
        result = result.setScale(4,BigDecimal.ROUND_UP);
        if (result.compareTo(coin.getLowWithdrawFees()) == -1){
            // 低于最低手续费
            result = coin.getLowWithdrawFees();
        }
        return result;
    }
}
