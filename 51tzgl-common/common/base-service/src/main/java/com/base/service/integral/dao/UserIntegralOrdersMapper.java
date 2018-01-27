package com.base.service.integral.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.base.facade.integral.pojo.bean.IntegeralOrderListBean;
import com.base.facade.integral.pojo.po.UserIntegralOrders;
import com.base.facade.integral.pojo.vo.UserIntegralOrdersVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserIntegralOrdersMapper extends BaseMapper<UserIntegralOrders, UserIntegralOrdersVo> {


    UserIntegralOrders getUserIntegralOrderByOrderNumAndAppKey(@Param("orderNum") String orderNum, @Param("appKey") String appKey);

    /**
     * 提现记录list
     */
    List<IntegeralOrderListBean>  getListByConditionPage(UserIntegralOrdersVo vo);
}