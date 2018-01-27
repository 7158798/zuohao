package com.otc.api.console.base;

import com.jucaifu.common.pojo.vo.BaseResp;

/**
 * Created by lx on 17-5-1.
 */
public class BaseListResp extends BaseResp {

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
