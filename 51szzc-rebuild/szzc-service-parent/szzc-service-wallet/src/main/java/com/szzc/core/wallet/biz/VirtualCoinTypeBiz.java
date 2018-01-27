package com.szzc.core.wallet.biz;

import com.jucaifu.core.biz.AbsBaseBiz;
import com.szzc.core.wallet.dao.VirtualCoinTypeMapper;
import com.szzc.facade.wallet.pojo.cond.VirtualCoinTypeCond;
import com.szzc.facade.wallet.pojo.po.VirtualCoinType;
import com.szzc.facade.wallet.pojo.vo.VirtualCoinTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lx on 17-5-25.
 */
@Service
public class VirtualCoinTypeBiz extends AbsBaseBiz<VirtualCoinType,VirtualCoinTypeVo,VirtualCoinTypeMapper> {

    @Autowired
    private VirtualCoinTypeMapper virtualCoinTypeMapper;
    @Override
    public VirtualCoinTypeMapper getDefaultMapper() {
        return virtualCoinTypeMapper;
    }

    public List<VirtualCoinType> queryCoinTypeByCondition(VirtualCoinTypeCond cond){
        return virtualCoinTypeMapper.queryCoinTypeByCondition(cond);
    }



}
