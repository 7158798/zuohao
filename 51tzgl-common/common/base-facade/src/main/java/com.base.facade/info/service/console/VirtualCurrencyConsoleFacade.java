package com.base.facade.info.service.console;

import com.base.facade.info.pojo.po.VirtualCurrency;
import com.base.facade.info.pojo.vo.VirtualCurrencyVo;
import com.base.facade.info.service.VirtualCurrencyFacade;

import java.util.List;

/**
 * @author luwei
 * @Date 11/8/16 2:54 PM
 */
public interface VirtualCurrencyConsoleFacade extends VirtualCurrencyFacade {


    /**
     * 查询所有的币种
     * @return
     */
    List<VirtualCurrency> queryAllCncy();

    /**
     * 新增虚拟货币
     * @param virtualCurrency
     */
    void addCncy(VirtualCurrency virtualCurrency);

    /**
     * 修改虚拟货币
     * @param virtualCurrency
     */
    void updateCncy(VirtualCurrency virtualCurrency);


    /**
     * 添加时，判断市场代码是否存在
     * @param marketCode 市场代码
     * @return
     */
    Integer queryExistsCodeByAdd(String marketCode);

    /**
     * 修改时，判断市场代码是否存在
     * @param marketCode 市场代码
     * @param id 主键id流水号
     * @return
     */
    Integer queryExistsCodeByUpdate(String marketCode, Long id);

    /**
     * 修改状态
     * @param id
     * @param status
     */
    void updateStatus(Long id, Integer status);

    /**
     * 删除
     * @param id
     */
    void delete(Long id);

}
