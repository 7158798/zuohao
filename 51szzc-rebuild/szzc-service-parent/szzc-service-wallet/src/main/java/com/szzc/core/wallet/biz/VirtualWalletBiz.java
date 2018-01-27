package com.szzc.core.wallet.biz;

import com.jucaifu.core.biz.AbsBaseBiz;
import com.szzc.core.wallet.dao.VirtualWalletMapper;
import com.szzc.facade.wallet.pojo.cond.VirtualWalletCond;
import com.szzc.facade.wallet.pojo.po.VirtualWallet;
import com.szzc.facade.wallet.pojo.vo.VirtualWalletStaticVo;
import com.szzc.facade.wallet.pojo.vo.VirtualWalletVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
