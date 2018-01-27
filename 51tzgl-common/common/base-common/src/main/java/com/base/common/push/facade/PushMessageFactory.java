package com.base.common.push.facade;

import com.base.common.push.facade.messsage.PushBean;
import com.base.common.push.facade.messsage.PushMessage;
import com.base.common.push.facade.response.PushMessageResp;

/**
 * 推送工厂
 * Created by liuxun on 16-9-26.
 */
public interface PushMessageFactory {

    /**
     * 加载推送实例
     * @param bean
     * @return
     */
    PushMessageFactory bulid(PushBean bean);

    /**
     * 推送消息
     * @param message       消息bean
     * @return
     */
    <T extends PushMessage> PushMessageResp pushMessage(T message);
}
