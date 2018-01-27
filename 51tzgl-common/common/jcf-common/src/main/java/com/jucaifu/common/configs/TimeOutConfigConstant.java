package com.jucaifu.common.configs;

import com.jucaifu.common.constants.TimeConstant;

/**
 * TimeOutConfigConstant
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/1/8.
 */
public interface TimeOutConfigConstant {

    /**
     * 步骤依赖超时时间
     */
    Integer STEP_DEPEND_TOKEN_TIMEOUT = TimeConstant.ONE_MINUTE_UNIT_SECONDS * 5;

    /**
     * 用户Token缓存有效的时间： 15天
     */
    Integer USER_TOKEN_TIMEOUT = TimeConstant.ONE_DAY_UNIT_SECONDS * 15;

    /**
     * 绑定uid与用户userId有效时间： 1分钟
     */
    Integer BIND_UID_USERID_TIMEOUT = TimeConstant.ONE_MINUTE_UNIT_SECONDS;

}
