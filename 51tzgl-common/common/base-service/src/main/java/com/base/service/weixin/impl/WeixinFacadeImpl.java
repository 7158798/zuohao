package com.base.service.weixin.impl;

import com.base.facade.weixin.pojo.bean.WeixinRedPacket;
import com.base.facade.weixin.response.WeixinResponse;
import com.base.facade.weixin.service.WeixinFacade;
import com.base.service.pool.BaseServiceBizPool;

/**
 * Created by lx on 16-12-22.
 */
public class WeixinFacadeImpl implements WeixinFacade {

    @Override
    public WeixinResponse sendRedPacket(WeixinRedPacket redPack) {
        return BaseServiceBizPool.getInstance().weixinBiz.sendRedPacket(redPack);
    }
}
