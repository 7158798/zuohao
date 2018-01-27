package com.base.service.integral.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.base.facade.integral.pojo.bean.IntegralDetailBean;
import com.base.facade.integral.pojo.po.UserIntegralDetail;
import com.base.facade.integral.pojo.vo.UserIntegralDetailVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface UserIntegralDetailMapper extends BaseMapper<UserIntegralDetail, UserIntegralDetailVo> {


    List<IntegralDetailBean> getDetailListByConditionPage(UserIntegralDetailVo vo);

    List<UserIntegralDetail> getUserIntegralListByConditionPage(UserIntegralDetailVo vo);


    Long getUserIntegralCountByType(@Param("userId") String userId, @Param("type") int type);

    int deleteByUserId(@Param("userId") Long userId);

    List<UserIntegralDetail> getDetailByGroup(@Param("userId") Long userId, @Param("operateType") int operateType);

    /**
     *获取某天某类型积分获取数
     * @param userId
     * @param type
     * @param strDate
     * @return
     */
    BigDecimal getAccountByDateAndType(@Param("userId") Long userId, @Param("type") Integer type, @Param("strDate") String strDate);
}