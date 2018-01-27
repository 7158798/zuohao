package com.base.service.info.impl.console;

import com.base.facade.info.pojo.po.VirtualCurrency;
import com.base.facade.info.pojo.vo.VirtualCurrencyVo;
import com.base.facade.info.service.console.VirtualCurrencyConsoleFacade;
import com.base.service.info.impl.VirtualCurrencyFacadeImpl;
import com.base.service.pool.BaseServiceBizPool;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author luwei
 * @Date 11/8/16 3:01 PM
 */
@Service("virtualCurrencyConsoleFacade")
public class VirtualCurrencyConsoleFacadeImpl extends VirtualCurrencyFacadeImpl implements VirtualCurrencyConsoleFacade{


    /**
     * 查询所有的币种
     * @return
     */
    public List<VirtualCurrency> queryAllCncy() {
        return BaseServiceBizPool.getInstance().virtualCurrencyBiz.queryAllCncy();
    }

    /**
     * 新增虚拟货币
     * @param virtualCurrency
     */
    public void addCncy(VirtualCurrency virtualCurrency) {
        BaseServiceBizPool.getInstance().virtualCurrencyBiz.addCncy(virtualCurrency);
    }

    /**
     * 修改虚拟货币
     * @param virtualCurrency
     */
    public void updateCncy(VirtualCurrency virtualCurrency) {
        BaseServiceBizPool.getInstance().virtualCurrencyBiz.updateCncy(virtualCurrency);
    }


    /**
     * 添加时，判断市场代码是否存在
     * @param marketCode 市场代码
     * @return
     */
    public Integer queryExistsCodeByAdd(String marketCode) {
        return BaseServiceBizPool.getInstance().virtualCurrencyBiz.queryExistsCodeByAdd(marketCode);
    }

    /**
     * 修改时，判断市场代码是否存在
     * @param marketCode 市场代码
     * @param id 主键id流水号
     * @return
     */
    public Integer queryExistsCodeByUpdate(String marketCode, Long id) {
        return BaseServiceBizPool.getInstance().virtualCurrencyBiz.queryExistsCodeByUpdate(marketCode, id);
    }

    /**
     * 修改状态
     * @param id
     * @param status
     */
    public void updateStatus(Long id, Integer status) {
        BaseServiceBizPool.getInstance().virtualCurrencyBiz.updateStatus(id, status);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(Long id) {
        BaseServiceBizPool.getInstance().virtualCurrencyBiz.delete(id);
    }

}
