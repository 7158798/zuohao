package com.base.service.user.biz;

import com.jucaifu.core.biz.AbsBaseBiz;
import com.base.facade.user.pojo.po.UserLogin;
import com.base.facade.user.pojo.vo.UserLoginVo;
import com.base.service.user.dao.UserLoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by zh on 16-10-18.
 */
@Service
public class UserLoginBiz extends AbsBaseBiz<UserLogin, UserLoginVo, UserLoginMapper> {
    @Autowired
    private UserLoginMapper userLoginMapper;

    @Override
    public UserLoginMapper getDefaultMapper() {
        return userLoginMapper;
    }

    public UserLogin getLastLogin(Long userId) {
        return userLoginMapper.getLastLogin(userId);
    }

    public void addUserLogin(Long userId, String loginPlatform, String adressIP, String status) {
        if (userId == null) {
            return;
        }
        Date now = new Date();
        UserLogin userLogin = new UserLogin();
        userLogin.setUserId(userId);
        userLogin.setCreateDate(now);
        userLogin.setLoginDate(now);
        userLogin.setLoginIp(adressIP);
        userLogin.setLoginStatus(status);
        userLogin.setLoginPlatform(loginPlatform);
        userLogin.setDeleted(false);
        userLoginMapper.insert(userLogin);
    }


    public int changeUserId(Long userId, Long thirdId) {
        if (userId == null || thirdId == null) {
            return 0;
        }
        return userLoginMapper.changeUserId(userId, thirdId);
    }

    /**
     * 查询是否app端登录过
     * @param userId
     * @return
     */
    public Boolean queryIsAppLogin(Long userId) {
        Long result = userLoginMapper.queryIsAppLogin(userId);
        return result.intValue() > 0 ? true : false;
    }

}
