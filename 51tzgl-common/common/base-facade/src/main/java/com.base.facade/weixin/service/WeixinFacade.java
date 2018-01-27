package com.base.facade.weixin.service;

import com.base.facade.weixin.pojo.bean.WeixinRedPacket;
import com.base.facade.weixin.response.WeixinResponse;

/**
 * Created by lx on 16-12-22.
 */
public interface WeixinFacade {

    /**
     * 发送微信红包
     * @param redPack
     */
    WeixinResponse sendRedPacket(WeixinRedPacket redPack);
}
