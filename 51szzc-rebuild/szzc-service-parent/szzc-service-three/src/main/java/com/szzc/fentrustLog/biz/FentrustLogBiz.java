package com.szzc.fentrustLog.biz;

import com.szzc.facade.fentrustLog.pojo.dto.TradeVo;
import com.szzc.facade.fentrustLog.pojo.po.CoinShorNameAndFtrademappingId;
import com.szzc.fentrustLog.dao.FentrustLogMapper;
import com.szzc.fentrustLog.dao.VirtualCoinTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zygong on 17-5-26.
 */
@Service
public class FentrustLogBiz {
    @Autowired
    private FentrustLogMapper fentrustLogMapper;
    @Autowired
    private VirtualCoinTypeMapper virtualCoinTypeMapper;

    public List<TradeVo> tradeOther(Long ftrademapping, Long number){
        return fentrustLogMapper.tradeOther(ftrademapping, number);
    }

    /**
     * 获取可用虚拟币
     *
     * @return
     */
    public List<CoinShorNameAndFtrademappingId> getActiveCoin() {
        return virtualCoinTypeMapper.getActiveCoin();
    }
}
