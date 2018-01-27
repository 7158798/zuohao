package com.otc.facade.message.service.web;

import com.otc.facade.message.pojo.cond.MessageCond;
import com.otc.facade.message.pojo.po.Message;
import com.otc.facade.message.pojo.vo.MessageVo;
import com.otc.facade.message.service.MessageFacade;

import java.util.List;

/**
 * Created by lx on 17-5-9.
 */
public interface MessageWebFacade extends MessageFacade {

    MessageVo queryByConditionPage(MessageVo vo);

    List<Message> queryListByCondition(MessageCond cond);

    MessageVo queryChatByConditionPage(MessageVo vo);
}
