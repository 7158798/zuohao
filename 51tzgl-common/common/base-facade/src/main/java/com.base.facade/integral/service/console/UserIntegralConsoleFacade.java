package com.base.facade.integral.service.console;

import com.base.facade.integral.pojo.po.IntegralTask;
import com.base.facade.integral.pojo.vo.UserIntegralAccountVo;
import com.base.facade.integral.pojo.vo.UserIntegralDetailVo;
import com.base.facade.integral.pojo.vo.UserIntegralOrdersVo;
import com.base.facade.integral.service.UserIntegralFacade;

import java.util.List;

/**
 * Created by zh on 16-10-23.
 */
public interface UserIntegralConsoleFacade extends UserIntegralFacade {

    List<IntegralTask> getAllIntegralTask();

    /**
     * 修改积分配置
     *
     * @param list
     * @throws Exception
     */
    void addOrUpdateTask(List<IntegralTask> list) throws Exception;

    /**
     * 积分查询
     *
     * @param vo
     * @return
     * @throws Exception
     */
    UserIntegralAccountVo getAccountByConditionPage(UserIntegralAccountVo vo) throws Exception;

    UserIntegralDetailVo getDetailListByConditionPage(UserIntegralDetailVo vo);

    /**
     * 获取提现记录
     *
     * @param vo
     * @return
     */
    UserIntegralOrdersVo getListByConditionPage(UserIntegralOrdersVo vo);

    /**
     * 删除积分帐号
     * @param userId
     */
    void deleteAccountById(Long id);

    /**
     * 删除积分明细
     * @param userId
     */
    void deleteDetailByUserId(Long userId);
}
