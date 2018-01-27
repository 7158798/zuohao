package com.otc.facade.user.service.console;

import com.otc.facade.base.CountVo;
import com.otc.facade.user.pojo.po.User;
import com.otc.facade.user.pojo.vo.UserVo;
import com.otc.facade.user.service.UserFacade;

import java.util.Date;
import java.util.List;

/**
 * Created by a123 on 17-4-20.
 */
public interface UserConsoleFacade extends UserFacade{

    /**
     * 用户管理列表
     * @param vo
     * @return
     */
    UserVo selectUserListByConditionPage(UserVo vo);

    /**
     * 禁用用户
     * @param userId
     */
    void  forbiddenUser(Long userId);


    /**
     * 解禁用户
     * @param userId
     */
    void LiftForbiddenUser(Long userId);

    /**
     *  重置用户密码
     * @param userId
     * @param adminToken
     */
    String  resetUserPwd(Long userId,String adminToken);

    /**
     * 查询用户地址列表
     * @param vo
     * @return
     */
    UserVo selectUserAddressList(UserVo vo);

    /**
     * 查询操作记录
     * @param vo
     * @return
     */
    UserVo selectUserOperationList(UserVo vo);

    /**
     * 查询资产记录
     * @param vo
     * @return
     */
    UserVo selectUserAssetList(UserVo vo);

    /**
     * 综合统计-一天
     * @param dayTime
     * @return
     */
    List<CountVo> countUser(Date dayTime);

    /**
     * 综合统计-全部
     * @return
     */
    List<CountVo> countUser();



}
