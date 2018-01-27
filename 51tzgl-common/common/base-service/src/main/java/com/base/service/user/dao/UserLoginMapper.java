package com.base.service.user.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.base.facade.user.pojo.po.UserLogin;
import com.base.facade.user.pojo.vo.UserLoginVo;
import org.apache.ibatis.annotations.Param;

public interface UserLoginMapper extends BaseMapper<UserLogin, UserLoginVo> {
    UserLogin getLastLogin(@Param("userId") Long userId);

    int changeUserId(@Param("userId") Long userId, @Param("thirdId") Long thirdId);

    /**
     * 查询是否app端登录过
     * @param userId
     * @return
     */
    Long queryIsAppLogin(Long userId);
}