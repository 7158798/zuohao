package com.otc.service.message.impl;

import com.otc.facade.message.pojo.po.Message;
import com.otc.facade.message.service.MessageFacade;
import com.otc.facade.user.enums.UserMessageConstantEnum;
import com.otc.pool.OTCBizPool;

/**
 * Created by lx on 17-5-9.
 */
public class MessageFacadeImpl implements MessageFacade {

    @Override
    public boolean saveMessage(Message message) {
        return OTCBizPool.getInstance().messageBiz.saveMessage(message);
    }

    @Override
    public void updateMessage(Message message) {
        OTCBizPool.getInstance().messageBiz.update(message);
    }

    @Override
    public Message queryMessage(Long id) {
        return OTCBizPool.getInstance().messageBiz.select(id);
    }

    @Override
    public boolean sendMessage( Long toId, UserMessageConstantEnum constantEnum, String code,Long rationId) {
        return OTCBizPool.getInstance().messageBiz.sendMessage(toId,constantEnum,code,rationId);
    }


}
