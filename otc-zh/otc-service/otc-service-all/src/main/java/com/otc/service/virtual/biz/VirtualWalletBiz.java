package com.otc.service.virtual.biz;

import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.core.cache.CacheHelper;
import com.otc.facade.base.CountVoEx;
import com.otc.facade.cache.CacheKey;
import com.otc.facade.user.pojo.po.User;
import com.otc.facade.virtual.exceptions.VirtualBizException;
import com.otc.facade.virtual.pojo.cond.VirtualWalletCond;
import com.otc.facade.virtual.pojo.po.VirtualCoin;
import com.otc.facade.virtual.pojo.po.VirtualWallet;
import com.otc.facade.virtual.pojo.poex.VirtualWalletEx;
import com.otc.facade.virtual.pojo.vo.VirtualWalletVo;
import com.otc.pool.OTCBizPool;
import com.otc.service.virtual.dao.VirtualWalletMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lx on 17-4-19.
 */
@Service
@Transactional
public class VirtualWalletBiz extends AbsBaseBiz<VirtualWallet,VirtualWalletVo,VirtualWalletMapper> {
    @Autowired
    private VirtualWalletMapper virtualWalletMapper;
    @Override
    public VirtualWalletMapper getDefaultMapper() {
        return virtualWalletMapper;
    }


    /**
     * 通过用户id创建钱包
     *
     * @param userId the user id
     * @return the boolean
     */
    public boolean createWalletByUser(Long userId){
        boolean flag = false;
        if (userId == null){
            throw new VirtualBizException("用户ID不能为空");
        }
        Map<Long,VirtualCoin> coinMap = OTCBizPool.getInstance().virtualCoinBiz.getVirtualCoin(Boolean.TRUE);
        for (Map.Entry<Long,VirtualCoin> entry: coinMap.entrySet()){
            flag = createWallet(userId,entry.getKey());
        }
        return flag;
    }

    /**
     * 通过币种创建用户钱包
     *
     * @param coinId the coin id
     * @param list   the list
     * @return the boolean
     */
    public boolean createWalletByCoin(Long coinId,List<User> list){
        if (list == null || list.size() == 0)
            return Boolean.TRUE;
        boolean flag = false;
        for (User user : list){
            flag = createWallet(user.getId(),coinId);
        }
        return flag;
    }

    /**
     * 创建钱包
     * @param userId    用户
     * @param coinId    币种
     * @return
     */
    private boolean createWallet(Long userId,Long coinId){
        boolean flag = false;
        if (userId == null){
            throw new VirtualBizException("用户ID不能为空");
        }
        if (coinId == null){
            throw new VirtualBizException("虚拟币ID不能为空");
        }
        // 钱包数据已经存在
        VirtualWallet temp = getByUserIdAndCoinId(userId, coinId);
        if (temp != null){
            return Boolean.TRUE;
        }
        // 新增钱包
        VirtualWallet wallet = new VirtualWallet();
        wallet.setCoinId(coinId);
        wallet.setUserId(userId);
        wallet.setCreateDate(new Date());
        wallet.setModifiedDate(new Date());
        wallet.setFrozen(BigDecimal.ZERO);
        wallet.setTotal(BigDecimal.ZERO);
        int ret = virtualWalletMapper.insert(wallet);
        if (ret != 0)
            flag = Boolean.TRUE;
        return flag;
    }


    /**
     * 根据条件查询钱包数据
     *
     * @param cond the cond
     * @return the list
     */
    public List<VirtualWallet> queryListVirtualWallet(VirtualWalletCond cond){
       return virtualWalletMapper.queryListVirtualWallet(cond);
    }

    /**
     * 查询钱包数据
     *
     * @param cond the cond
     * @return the virtual wallet
     */
    public VirtualWallet queryVirtualWallet(VirtualWalletCond cond) {
        List<VirtualWallet> list = virtualWalletMapper.queryListVirtualWallet(cond);
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }

    /**
     * Is exist virtual wallet.
     *
     * @param coinId the coin id
     * @param userId the user id
     * @return the virtual wallet
     */
    public VirtualWallet isExist(Long coinId,Long userId){
        VirtualWalletCond cond = new VirtualWalletCond();
        cond.setUserId(userId);
        cond.setCoinId(coinId);
        VirtualWallet wallet = queryVirtualWallet(cond);
        if (wallet == null)
            throw new VirtualBizException("用户钱包不存在");

        return wallet;
    }


    /**
     * 分页查询钱包数据
     *
     * @param vo the vo
     * @return the virtual wallet vo
     */
    public VirtualWalletVo queryConsoleByConditionPage(VirtualWalletVo vo){
        List<VirtualWalletEx> list = virtualWalletMapper.queryConsoleByConditionPage(vo);
        if (list != null){
            vo.setResultList(list);
        }
        return vo;
    }

    /**
     * Update core boolean.
     *
     * @param wallet the wallet
     * @return the boolean
     */
    public boolean updateCore(VirtualWallet wallet){
        wallet.setModifiedDate(new Date());
        int ret = virtualWalletMapper.update(wallet);
        if (ret > 0)
            return Boolean.TRUE;
        return Boolean.FALSE;
    }

    /**
     * 冻结钱包币
     *
     * @param userId  the user id
     * @param coinId  the coin id
     * @param account the account
     * @return boolean
     */
    public boolean frozenSeller(Long userId,Long coinId,BigDecimal account){
        return virtualWalletMapper.frozenAccount(userId, coinId, account) == 1;
    }


    /**
     * 减少卖家币
     *
     * @param userId  the user id
     * @param coinId  the coin id
     * @param account the account
     * @return boolean
     */
    public boolean  subtractSellerFrozen(Long userId,Long coinId,BigDecimal account){
        return virtualWalletMapper.subtractFrozenAccount(userId, coinId, account) == 1;
    }

    /**
     * 给买家发放币
     *
     * @param userId  the user id
     * @param coinId  the coin id
     * @param account the account
     * @return boolean
     */
    public boolean addBuyer(Long userId,Long coinId,BigDecimal account){
        return virtualWalletMapper.addAccount(userId, coinId, account) == 1;
    }


    /**
     * 返还卖家币
     *
     * @param userId  the user id
     * @param coinId  the coin id
     * @param account the account
     * @return boolean
     */
    public boolean restoreSellerFrozen(Long userId,Long coinId,BigDecimal account){
        return virtualWalletMapper.restoreFrozenAccount(userId, coinId, account) == 1;
    }

    /**
     * 综合统计
     *
     * @return list
     */
    public List<CountVoEx> countVitralWallet(){
        return virtualWalletMapper.countVitralWallet();
    }

    /**
     * 获取钱包
     *
     * @param userId the user id
     * @param coinId the coin id
     * @return virtual wallet
     */
    public  VirtualWallet getByUserIdAndCoinId(Long userId, Long coinId){
        return virtualWalletMapper.getByUserIdAndCoinId(userId, coinId);
    }
}
