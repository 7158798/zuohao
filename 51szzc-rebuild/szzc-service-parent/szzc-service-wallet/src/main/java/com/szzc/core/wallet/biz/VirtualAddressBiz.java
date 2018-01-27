package com.szzc.core.wallet.biz;

import com.jucaifu.core.biz.AbsBaseBiz;
import com.szzc.core.wallet.dao.VirtualAddressMapper;
import com.szzc.facade.wallet.pojo.cond.VirtualAddressCond;
import com.szzc.facade.wallet.pojo.po.VirtualAddress;
import com.szzc.facade.wallet.pojo.vo.VirtualAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lx on 17-5-25.
 */
@Service
public class VirtualAddressBiz extends AbsBaseBiz<VirtualAddress,VirtualAddressVo,VirtualAddressMapper> {

    @Autowired
    private VirtualAddressMapper virtualAddressMapper;
    @Override
    public VirtualAddressMapper getDefaultMapper() {
        return virtualAddressMapper;
    }

    public List<VirtualAddress> queryListByCoinId(Long coinId){
        VirtualAddressCond cond = new VirtualAddressCond();
        cond.setCoinId(coinId);
        return virtualAddressMapper.queryListByCondition(cond);
    }

    public List<VirtualAddress> queryListByCoinIdAndAddress(Long coinId,String address){
        VirtualAddressCond cond = new VirtualAddressCond();
        cond.setCoinId(coinId);
        cond.setAddress(address);
        return virtualAddressMapper.queryListByCondition(cond);
    }

}
