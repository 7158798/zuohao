package com.base.service.integral.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.base.facade.integral.pojo.bean.IntegralAccountBean;
import com.base.facade.integral.pojo.po.UserIntegralAccount;
import com.base.facade.integral.pojo.vo.UserIntegralAccountVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface UserIntegralAccountMapper extends BaseMapper<UserIntegralAccount, UserIntegralAccountVo> {

    /**
     * 扣除用户账户积分
     *
     * @param userId
     * @param integral
     * @return
     */
    int deductUserIntegral(@Param("userId") Long userId, @Param("integral") BigDecimal integral);

    /**
     * 给用户添加积分
     *
     * @param userId
     * @param integral
     * @return
     */
    int addUserIntegral(@Param("userId") Long userId, @Param("integral") BigDecimal integral);


    List<IntegralAccountBean> getAccountListByConditionPage(UserIntegralAccountVo vo);


    UserIntegralAccount getByUserId(@Param("userId") Long userId);

}