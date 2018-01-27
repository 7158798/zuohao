package com.otc.service.virtual.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.virtual.pojo.cond.UserAddressCond;
import com.otc.facade.virtual.pojo.po.UserAddress;
import com.otc.facade.virtual.pojo.vo.UserAddressVo;

import java.util.List;

public interface UserAddressMapper extends BaseMapper<UserAddress,UserAddressVo> {

    /**
     * 查询用户地址
     * @param cond
     * @return
     */
    UserAddress queryUserAddress(UserAddressCond cond);

    /**
     * 查询查询用户地址
     * @param cond
     * @return
     */
    List<UserAddress> queryListUserAddress(UserAddressCond cond);
}