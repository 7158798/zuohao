package com.otc.service.user.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.user.pojo.po.UserAuthentication;
import com.otc.facade.user.pojo.vo.UserAuthenticationVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserAuthenticationMapper extends BaseMapper<UserAuthentication,UserAuthenticationVo> {

    UserAuthentication selectByCondition(@Param("passportNo")String passportNo
            ,@Param("realName") String realName,@Param("userId") Long userId);

    /**
     * 通过姓名
     *
     * @param filter
     * @return
     */
    List<Long> selectUserByFilter(@Param("filter")String filter);
}