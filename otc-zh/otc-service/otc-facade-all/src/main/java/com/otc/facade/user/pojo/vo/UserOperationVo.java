package com.otc.facade.user.pojo.vo;

import com.jucaifu.common.pojo.vo.BasePageVo;

/**
 * Created by a123 on 17-5-2.
 */
public class UserOperationVo extends BasePageVo{
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
