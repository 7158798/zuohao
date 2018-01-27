package com.base.service.integral.impl.console;

import com.base.facade.integral.pojo.bean.IntegeralOrderListBean;
import com.base.facade.integral.pojo.bean.IntegralAccountBean;
import com.base.facade.integral.pojo.bean.IntegralDetailBean;
import com.base.facade.integral.pojo.po.IntegralTask;
import com.base.facade.integral.pojo.vo.UserIntegralAccountVo;
import com.base.facade.integral.pojo.vo.UserIntegralDetailVo;
import com.base.facade.integral.pojo.vo.UserIntegralOrdersVo;
import com.base.facade.integral.service.console.UserIntegralConsoleFacade;
import com.base.service.integral.impl.UserIntegralFacadeImpl;
import com.base.service.pool.BaseServiceBizPool;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zh on 16-10-23.
 */

@Service("userIntegralConsoleFacade")
public class UserIntegralConsoleFacadeImpl extends UserIntegralFacadeImpl implements UserIntegralConsoleFacade {

    @Override
    public List<IntegralTask> getAllIntegralTask() {
        return BaseServiceBizPool.getInstance().integralTaskBiz.getAllIntegralTasks();
    }

    @Override
    public void addOrUpdateTask(List<IntegralTask> list) {
        BaseServiceBizPool.getInstance().integralTaskBiz.updateOrAddTask(list);
    }

    @Override
    public UserIntegralAccountVo getAccountByConditionPage(UserIntegralAccountVo vo) throws Exception {
        List<IntegralAccountBean> list
                = BaseServiceBizPool.getInstance().userIntegralAccountBiz.getAccountListByConditionPage(vo);
        vo.setResultList(list);
        return vo;
    }

    public UserIntegralDetailVo getDetailListByConditionPage(UserIntegralDetailVo vo) {
        List<IntegralDetailBean> list
                = BaseServiceBizPool.getInstance().userIntegralDetailBiz.getDetailListByConditionPage(vo);
        vo.setResultList(list);
        return vo;
    }

    @Override
    public UserIntegralOrdersVo getListByConditionPage(UserIntegralOrdersVo vo) {
        List<IntegeralOrderListBean> list = BaseServiceBizPool.getInstance().userIntegralOrdersBiz.getListByConditionPage(vo);
        vo.setResultList(list);
        return vo;
    }

    @Override
    public void deleteAccountById(Long id) {
        BaseServiceBizPool.getInstance().userIntegralAccountBiz.delete(id);
    }

    @Override
    public void deleteDetailByUserId(Long userId) {
        BaseServiceBizPool.getInstance().userIntegralDetailBiz.deleteByUserId(userId);
    }
}
