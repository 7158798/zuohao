package com.otc.service.virtual.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.base.CountVoEx;
import com.otc.facade.virtual.pojo.cond.VirtualWalletCond;
import com.otc.facade.virtual.pojo.po.VirtualWallet;
import com.otc.facade.virtual.pojo.poex.VirtualWalletEx;
import com.otc.facade.virtual.pojo.vo.VirtualWalletVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * The interface Virtual wallet mapper.
 */
public interface VirtualWalletMapper extends BaseMapper<VirtualWallet,VirtualWalletVo> {

    /**
     * Query list virtual wallet list.
     *
     * @param cond the cond
     * @return the list
     */
    List<VirtualWallet> queryListVirtualWallet(VirtualWalletCond cond);

    /**
     * Query console by condition page list.
     *
     * @param vo the vo
     * @return the list
     */
    List<VirtualWalletEx> queryConsoleByConditionPage(VirtualWalletVo vo);

    /**
     * 冻结币
     *
     * @param userId  the user id
     * @param coinId  the coin id
     * @param account the account
     * @return int
     */
    int frozenAccount(@Param("userId") Long userId, @Param("coinId") Long coinId, @Param("account") BigDecimal account);

    /**
     * 增加币
     *
     * @param userId  the user id
     * @param coinId  the coin id
     * @param account the account
     * @return int
     */
    int addAccount(@Param("userId") Long userId, @Param("coinId") Long coinId, @Param("account") BigDecimal account);

    /**
     * 扣除冻结币
     *
     * @param userId  the user id
     * @param coinId  the coin id
     * @param account the account
     * @return int
     */
    int subtractFrozenAccount(@Param("userId") Long userId, @Param("coinId") Long coinId, @Param("account") BigDecimal account);


    /**
     * 返还冻结币
     *
     * @param userId  the user id
     * @param coinId  the coin id
     * @param account the account
     * @return int
     */
    int restoreFrozenAccount(@Param("userId") Long userId, @Param("coinId") Long coinId, @Param("account") BigDecimal account);


    /**
     * 综合统计
     *
     * @return list
     */
    List<CountVoEx> countVitralWallet();

    /**
     * 获取钱包
     *
     * @param userId the user id
     * @param coinId the coin id
     * @return by user id and coin id
     */
    VirtualWallet getByUserIdAndCoinId(@Param("userId") Long userId, @Param("coinId") Long coinId);
}



















