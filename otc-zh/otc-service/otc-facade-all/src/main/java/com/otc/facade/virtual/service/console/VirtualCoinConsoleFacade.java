package com.otc.facade.virtual.service.console;

import com.otc.facade.virtual.pojo.po.VirtualCoin;
import com.otc.facade.virtual.pojo.po.Withdrawfees;
import com.otc.facade.virtual.pojo.vo.VirtualCoinVo;
import com.otc.facade.virtual.service.VirtualCoinFacade;

import java.util.List;

/**
 * Created by lx on 17-4-19.
 */
public interface VirtualCoinConsoleFacade extends VirtualCoinFacade {

    /**
     * Create address.
     *
     * @param coin      the coin
     * @param walletPwd the wallet pwd
     */
    void createAddress(VirtualCoin coin,String walletPwd);

    /**
     * Update status boolean.
     *
     * @param id     the id
     * @param status the status
     * @return the boolean
     */
    boolean updateStatus(Long id,String status,String walletPwd);

    /**
     * Save virtual coin boolean.
     *
     * @param coin the coin
     * @return the boolean
     */
    boolean saveVirtualCoin(VirtualCoin coin);

    /**
     * Update virtual coin boolean.
     *
     * @param coin the coin
     * @return the boolean
     */
    boolean updateVirtualCoin(VirtualCoin coin);

    /**
     * Query count by condition page virtual coin vo.
     *
     * @param vo the vo
     * @return the virtual coin vo
     */
    VirtualCoinVo queryCountByConditionPage(VirtualCoinVo vo);

    /**
     * 增加提现手续费配置
     * @param list
     * @return
     */
    boolean addCoinFee(List<Withdrawfees> list);

    /**
     * 更新提现手续费配置
     * @param list
     * @return
     */
    boolean updateCoinFee(List<Withdrawfees> list);

    /**
     * 判断币种是否已存在
     * @param coin
     * @return
     */
    boolean checkIfExist(VirtualCoin coin);
}
