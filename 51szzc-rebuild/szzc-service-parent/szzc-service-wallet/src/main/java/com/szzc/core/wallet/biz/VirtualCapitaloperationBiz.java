package com.szzc.core.wallet.biz;

import com.jucaifu.core.biz.AbsBaseBiz;
import com.szzc.core.wallet.dao.VirtualCapitaloperationMapper;
import com.szzc.core.wallet.pool.WalletBizPool;
import com.szzc.facade.wallet.enums.VirtualCapitalOperationInStatusEnum;
import com.szzc.facade.wallet.pojo.cond.VirtualCapitaloperationCond;
import com.szzc.facade.wallet.pojo.po.VirtualCapitaloperation;
import com.szzc.facade.wallet.pojo.po.VirtualWallet;
import com.szzc.facade.wallet.pojo.vo.VirtualCapitaloperationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by lx on 17-5-25.
 */
@Service
public class VirtualCapitaloperationBiz extends AbsBaseBiz<VirtualCapitaloperation,VirtualCapitaloperationVo,VirtualCapitaloperationMapper> {

    @Autowired
    private VirtualCapitaloperationMapper virtualCapitaloperationMapper;
    @Override
    public VirtualCapitaloperationMapper getDefaultMapper() {
        return virtualCapitaloperationMapper;
    }


    public VirtualCapitaloperation queryLastCapitaloperation(Long coinId,Integer type){
        VirtualCapitaloperationCond cond = new VirtualCapitaloperationCond();
        cond.setCoinId(coinId);
        cond.setType(type);
        List<VirtualCapitaloperation> list = virtualCapitaloperationMapper.queryListByCondition(cond);
        if (list != null && list.size() != 0){
            return list.get(list.size()-1);
        }
        return null;
    }


    public List<VirtualCapitaloperation> queryListByCondition(VirtualCapitaloperationCond cond){
        return virtualCapitaloperationMapper.queryListByCondition(cond);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateFvirtualcaptualoperationCoinIn(VirtualCapitaloperation fvirtualcaptualoperation) throws Exception{
        VirtualCapitaloperation real = virtualCapitaloperationMapper.select(fvirtualcaptualoperation.getFid().longValue());
        if(real != null && real.getFstatus()!= VirtualCapitalOperationInStatusEnum.SUCCESS){
            real.setFstatus(fvirtualcaptualoperation.getFstatus()) ;
            real.setFconfirmations(fvirtualcaptualoperation.getFconfirmations()) ;
            real.setFlastupdatetime(new Date());
            real.setFamount(fvirtualcaptualoperation.getFamount());
            this.update(real);
            if(real.getFstatus()==VirtualCapitalOperationInStatusEnum.SUCCESS && real.getFhasowner()){
                VirtualWallet virtualWallet = WalletBizPool.getInstance().virtualWalletBiz.queryWalletByCoinAndUser(real.getFusFid2(),real.getFviFid2());
                virtualWallet.setFtotal(virtualWallet.getFtotal().add(real.getFamount()));
                virtualWallet.setFlastupdatetime(new Date());
                // 更新钱包数据
                WalletBizPool.getInstance().virtualWalletBiz.updateWallet(virtualWallet);
            }
        }
    }
}
