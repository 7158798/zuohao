package com.otc.service.message.impl.web;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.message.pojo.cond.MessageCond;
import com.otc.facade.message.pojo.po.Message;
import com.otc.facade.message.pojo.vo.MessageVo;
import com.otc.facade.message.service.web.MessageWebFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.message.impl.MessageFacadeImpl;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lx on 17-5-9.
 */
@Component
@Service
public class MessageWebFacadeImpl extends MessageFacadeImpl implements MessageWebFacade {

    @Override
    public MessageVo queryByConditionPage(MessageVo vo) {
        return OTCBizPool.getInstance().messageBiz.queryByConditionPage(vo);
    }

    @Override
    public List<Message> queryListByCondition(MessageCond cond) {
        return OTCBizPool.getInstance().messageBiz.queryListByCondition(cond);
    }

    @Override
    public MessageVo queryChatByConditionPage(MessageVo vo) {
        return OTCBizPool.getInstance().messageBiz.queryChatByConditionPage(vo);
    }
}
