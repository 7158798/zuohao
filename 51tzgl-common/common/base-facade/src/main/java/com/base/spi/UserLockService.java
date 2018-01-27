package com.base.spi;


import com.base.spi.response.CallbackResp;

/**
 * Created by lx on 16-12-13.
 */
public interface UserLockService {

    /**
     * 锁定用户
     * @param userId　用户ID
     * @return
     */
    CallbackResp lockUser(Long userId);

    /**
     * 用户过滤
     * @param userId　用户id
     * @return
     */
    CallbackResp userFilter(Long userId);
}
