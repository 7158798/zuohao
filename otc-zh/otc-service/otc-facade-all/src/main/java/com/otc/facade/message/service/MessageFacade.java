package com.otc.facade.message.service;

import com.otc.facade.message.pojo.po.Message;
import com.otc.facade.user.enums.UserMessageConstantEnum;

/**
 * Created by lx on 17-5-9.
 */
public interface MessageFacade {

    boolean saveMessage(Message message);

    void updateMessage(Message message);

    Message queryMessage(Long id);

    /**
     * 发送通知消息
     * @param constantEnum
     * @param code 替换内容
     * @return
     */
    boolean sendMessage(Long toId, UserMessageConstantEnum constantEnum,String code,Long rationId);
}
