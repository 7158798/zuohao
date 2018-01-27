package com.otc.service.user.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.base.CountVo;
import com.otc.facade.user.pojo.po.User;
import com.otc.facade.user.pojo.poex.UserReportEx;
import com.otc.facade.user.pojo.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


public interface UserMapper extends BaseMapper<User,UserVo> {

    //根据条件查询用户
    List<User> selectbycondition(UserVo vo);

    List<Long> selectUserByFilter(@Param("filter")String filter);

    //分页查询用户报表、KYC报表
    List<UserReportEx> getListByConditionPage(UserVo vo);


    //分页查询用户地址报表
    List<UserReportEx> getUserAddressListByConditionPage(UserVo vo);

    //分页查询用户操作日志--后台
    List<UserReportEx> getUserOperationListByConditionPage(UserVo vo);

    //查询用户资产记录 --后台
    List<UserReportEx> getUserAssetListByConditionPage(UserVo vo);

    // 综合统计
    List<CountVo> countUser(@Param("dayTime") String dayTime);

    List<User> queryNonExistentWallet(@Param("coinId") Long coinId);


}