package com.szzc.wallet.biz;

import com.alibaba.dubbo.config.annotation.Service;
import com.facade.core.wallet.cache.OtherPlatformTickerCacheManager;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.szzc.common.utils.Utils;
import com.szzc.facade.ftrademapping.enums.CoinTypeEnum;
import com.szzc.facade.ftrademapping.enums.VirtualCoinTypeStatusEnum;
import com.szzc.facade.ftrademapping.pojo.dto.FtrademappingDto;
import com.szzc.facade.out.pojo.TickerDateDto;
import com.szzc.facade.wallet.pojo.cond.VirtualWalletCond;
import com.szzc.facade.wallet.pojo.dto.TradeAccountInfo;
import com.szzc.facade.wallet.pojo.dto.TradeAccountInfoTotalMoney;
import com.szzc.facade.wallet.pojo.po.VirtualWallet;
import com.szzc.facade.wallet.pojo.vo.VirtualWalletStaticVo;
import com.szzc.facade.wallet.pojo.vo.VirtualWalletVo;
import com.szzc.pool.ThreeBizPool;
import com.szzc.wallet.dao.VirtualWalletMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lx on 17-5-25.
 */
@Service
public class VirtualWalletBiz extends AbsBaseBiz<VirtualWallet,VirtualWalletVo,VirtualWalletMapper> {

    @Autowired
    private VirtualWalletMapper virtualWalletMapper;
    @Override
    public VirtualWalletMapper getDefaultMapper() {
        return virtualWalletMapper;
    }

    /**
     * 获取用户总资产
     * @param userId
     * @return
     */
    public TradeAccountInfoTotalMoney getUserAccountInfo(Integer userId){
        // 获取所有币种
        List<FtrademappingDto> activeCoin = ThreeBizPool.getInstance().ftrademappingBiz.getActiveCoin();
        if(activeCoin == null){
            return null;
        }
        Map<Integer, FtrademappingDto> map = new HashMap<>();
        for(FtrademappingDto dto : activeCoin){
            map.put(dto.getFvId(), dto);
        }

        // 总金额
        BigDecimal totalMoney = BigDecimal.ZERO;
        VirtualWalletCond cond = new VirtualWalletCond();
        cond.setUserId(userId);
        List<VirtualWallet> virtualWallets = this.queryListByCondition(cond);
        if(virtualWallets == null){
            return null;
        }

        TradeAccountInfoTotalMoney totalMoneyObj = new TradeAccountInfoTotalMoney();
        List<TradeAccountInfo> tradeAccountInfos = new ArrayList<>();
        TradeAccountInfo tradeAccountInfo = null;
        for(VirtualWallet virtualWallet : virtualWallets){
            FtrademappingDto ftrademappingDto = map.get(virtualWallet.getFviFid());
            if(ftrademappingDto == null){
                continue;
            }
            tradeAccountInfo = new TradeAccountInfo();
            tradeAccountInfo.setCnName(ftrademappingDto.getFname());
            tradeAccountInfo.setShortName(ftrademappingDto.getFshortName());


            if(ftrademappingDto.getFtype().intValue() == CoinTypeEnum.FB_CNY_VALUE){
                tradeAccountInfo.setCoin(false);
                BigDecimal total = virtualWallet.getFtotal().setScale(2,BigDecimal.ROUND_DOWN);
                BigDecimal frozen = virtualWallet.getFfrozen().setScale(2, BigDecimal.ROUND_DOWN);
                tradeAccountInfo.setTotal(total.toPlainString());
                tradeAccountInfo.setFrozen(frozen.toPlainString());
                totalMoney = totalMoney.add(total).add(frozen);
            }else {

                Integer fcount1 = ftrademappingDto.getFcount1();
                Integer fcount2 = ftrademappingDto.getFcount2();

                String symbol = "ticker_" + ftrademappingDto.getFshortName().toLowerCase() + "_cny";
                TickerDateDto ticker = OtherPlatformTickerCacheManager.getByKey(symbol);
                if(ticker == null ){
                    continue;
                }
                BigDecimal price = new BigDecimal(ticker.getTicker().getLast());
                tradeAccountInfo.setCoin(true);
                BigDecimal total = virtualWallet.getFtotal().setScale(fcount2,BigDecimal.ROUND_DOWN);
                BigDecimal frozen = virtualWallet.getFfrozen().setScale(fcount2,BigDecimal.ROUND_DOWN);
                tradeAccountInfo.setTotal(String.valueOf(total));
                tradeAccountInfo.setFrozen(String.valueOf(frozen));
                BigDecimal all = total.add(frozen).multiply(price);
                totalMoney = totalMoney.add(all.setScale(fcount1,BigDecimal.ROUND_DOWN));
            }
            tradeAccountInfos.add(tradeAccountInfo);
        }
        totalMoneyObj.setList(tradeAccountInfos);
        totalMoneyObj.setTotalMoney(totalMoney.toPlainString());

        return totalMoneyObj;

    }

    public VirtualWallet queryWalletByCoinAndUser(Integer userId,Integer coinId){
        VirtualWalletCond cond = new VirtualWalletCond();
        cond.setCoinId(coinId);
        cond.setUserId(userId);
        List<VirtualWallet> rList = queryListByCondition(cond);
        if (rList != null && rList.size() == 1){
            return rList.get(0);
        }
        return null;
    }

    public List<VirtualWallet> queryListByCondition(VirtualWalletCond cond){
        return virtualWalletMapper.queryWalletByCondition(cond);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateWallet(VirtualWallet wallet){
        return virtualWalletMapper.updateWallet(wallet);
    }


    /**
     * 汇总统计全平台账户可用、冻结、余额（可用+冻结）
     * @return
     */
    public List<VirtualWalletStaticVo> queryStatisWallet() {
        return virtualWalletMapper.queryStatisWallet();
    }
}
