package com.otc.service.virtual.biz;

import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.facade.virtual.enums.PoolStatus;
import com.otc.facade.virtual.exceptions.VirtualBizException;
import com.otc.facade.virtual.pojo.cond.UserAddressCond;
import com.otc.facade.virtual.pojo.po.Pool;
import com.otc.facade.virtual.pojo.po.UserAddress;
import com.otc.facade.virtual.pojo.vo.UserAddressVo;
import com.otc.pool.OTCBizPool;
import com.otc.service.virtual.dao.UserAddressMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by lx on 17-4-19.
 */
@Service
public class UserAddressBiz extends AbsBaseBiz<UserAddress,UserAddressVo,UserAddressMapper> {

    @Autowired
    private UserAddressMapper userAddressMapper;
    @Override
    public UserAddressMapper getDefaultMapper() {
        return userAddressMapper;
    }

    public UserAddress queryUserAddress(UserAddressCond cond){
        return userAddressMapper.queryUserAddress(cond);
    }

    public List<UserAddress> queryListUserAddress(UserAddressCond cond){
        return userAddressMapper.queryListUserAddress(cond);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserAddress bindUserAddress(Long userId, Long coinId){
        Pool pool = OTCBizPool.getInstance().poolBiz.queryOnePool(coinId);
        if (pool == null){
            throw new VirtualBizException("生成地址失败,请联系客服");
        }
        UserAddress address =  new UserAddress();
        address.setCoinId(coinId);
        address.setUserId(userId);
        address.setAddress(pool.getAddress());
        address.setCreateDate(new Date());
        int ret = userAddressMapper.insert(address);
        if (ret != 0){
            // 更新状态
            pool.setStatus(PoolStatus.USED.getCode());
            OTCBizPool.getInstance().poolBiz.updateCore(pool);
        }
        return address;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean saveUserAddress(UserAddress userAddress){
        if (userAddress == null){
            throw new VirtualBizException("请求对象不能为空");
        }
        if (userAddress.getCoinId() == null){
            throw new VirtualBizException("虚拟币类型不能为空");
        }
        if (StringUtils.isEmpty(userAddress.getAddress())){
            throw new VirtualBizException("虚拟币地址不能为空");
        }
        if (userAddress.getUserId() == null){
            throw new VirtualBizException("用户id不能为空");
        }
        boolean flag = false;
        userAddress.setCreateDate(new Date());
        int ret = userAddressMapper.insert(userAddress);
        if (ret != 0)
            flag = Boolean.TRUE;
        return flag;
    }
}
