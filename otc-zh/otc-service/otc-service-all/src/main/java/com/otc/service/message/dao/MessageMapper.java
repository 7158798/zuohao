package com.otc.service.message.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.message.pojo.cond.MessageCond;
import com.otc.facade.message.pojo.po.Message;
import com.otc.facade.message.pojo.vo.MessageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper extends BaseMapper<Message,MessageVo> {

    List<Message> queryByConditionPage(MessageVo vo);

    List<Message> queryListByCondition(MessageCond cond);

    List<Message> queryChatByConditionPage(MessageVo vo);

    int updateChatMessage(@Param("userId")Long userId,@Param("relationId")Long relationId);

}