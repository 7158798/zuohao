package com.otc.service.virtual.impl.console;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.virtual.pojo.po.VirtualCoin;
import com.otc.facade.virtual.pojo.po.Withdrawfees;
import com.otc.facade.virtual.pojo.vo.VirtualCoinVo;
import com.otc.facade.virtual.service.console.VirtualCoinConsoleFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.virtual.impl.VirtualCoinFacadeImpl;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lx on 17-4-19.
 */
@Component
@Service
public class VirtualCoinConsoleFacadeImpl extends VirtualCoinFacadeImpl implements VirtualCoinConsoleFacade {

    @Override
    public void createAddress(VirtualCoin coin,String walletPwd) {
        OTCBizPool.getInstance().virtualCoinBiz.createAddress(coin,walletPwd);
    }

    @Override
    public boolean updateStatus(Long id, String status,String walletPwd) {
        return OTCBizPool.getInstance().virtualCoinBiz.updateStatus(id, status,walletPwd);
    }

    @Override
    public boolean saveVirtualCoin(VirtualCoin coin) {
        return OTCBizPool.getInstance().virtualCoinBiz.saveVirtualCoin(coin);
    }

    @Override
    public boolean updateVirtualCoin(VirtualCoin coin) {
        return OTCBizPool.getInstance().virtualCoinBiz.updateVirtualCoin(coin);
    }

    @Override
    public VirtualCoinVo queryCountByConditionPage(VirtualCoinVo vo) {
        return OTCBizPool.getInstance().virtualCoinBiz.queryCountByConditionPage(vo);
    }

    @Override
    public boolean addCoinFee(List<Withdrawfees> list) {
        return OTCBizPool.getInstance().virtualCoinBiz.addCoinFee(list);
    }

    @Override
    public boolean updateCoinFee(List<Withdrawfees> list) {
        return OTCBizPool.getInstance().virtualCoinBiz.updateCoinFee(list);
    }

    @Override
    public boolean checkIfExist(VirtualCoin coin) {
        return OTCBizPool.getInstance().virtualCoinBiz.checkIfExist(coin);
    }
}
