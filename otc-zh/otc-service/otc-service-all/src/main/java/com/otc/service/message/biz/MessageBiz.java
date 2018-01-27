package com.otc.service.message.biz;

import com.base.common.email.client.AzureEmailUtil;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.DateHelper;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.facade.message.enums.MessageType;
import com.otc.facade.message.exceptions.MessageBizException;
import com.otc.facade.message.pojo.cond.MessageCond;
import com.otc.facade.message.pojo.po.Message;
import com.otc.facade.message.pojo.vo.MessageVo;
import com.otc.facade.user.enums.UserMessageConstantEnum;
import com.otc.facade.user.pojo.po.User;
import com.otc.pool.OTCBizPool;
import com.otc.service.message.dao.MessageMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by lx on 17-5-9.
 */
@Service
public class MessageBiz extends AbsBaseBiz<Message,MessageVo,MessageMapper> {

    @Autowired
    private MessageMapper messageMapper;
    @Override
    public MessageMapper getDefaultMapper() {
        return messageMapper;
    }

    /**
     * Query by condition page message vo.
     *
     * @param vo the vo
     * @return the message vo
     */
    public MessageVo queryByConditionPage(MessageVo vo) {
        List<Message> list = messageMapper.queryByConditionPage(vo);
        if (list != null)
            vo.setResultList(list);
        return vo;
    }

    /**
     * Message filter.
     *
     * @param message the message
     */
    public void messageFilter(Message message){
        if (message == null){
            throw new MessageBizException("消息对象不能为空");
        }
        if (StringUtils.isEmpty(message.getType())){
            throw new MessageBizException("消息类型不能为空");
        }
        MessageType type = MessageType.typeMap.get(message.getType());
        if (type == null){
            throw new MessageBizException("消息类型不正确");
        }
        if (StringUtils.isEmpty(message.getContent())){
            throw new MessageBizException("消息内容不能为空");
        }
    }

    /**
     * 新增消息
     *
     * @param from        发送方
     * @param to          接收方
     * @param content     内容
     * @param messageType the message type
     * @param rationId    the ration id
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveChatMessage(Long from,Long to,String content,String messageType,Long rationId, String contentCode){
        Message message = new Message();
        message.setCreateUser(from);
        message.setReceive(to);
        message.setContent(content);
        message.setIsRead(Boolean.FALSE);
        message.setType(messageType);
        message.setRelationId(rationId);
        message.setContentCode(contentCode);
        saveMessage(message);
    }

    /**
     * 新增交易消息
     *
     * @param from    the from
     * @param to      the to
     * @param content the content
     * @param tradeId the trade id
     */
    @Transactional(rollbackFor = Exception.class)
    public void sendTradeMessage(Long from,Long to,String content,Long tradeId){
        this.saveChatMessage(from,to,content,MessageType.CHAT.getCode(),tradeId, null);
    }

    /**
     * 新增后台交易操作消息
     *
     * @param adminId  the admin id
     * @param buyerId  the buyer id
     * @param sellerId the seller id
     * @param tradeId  the trade id
     * @param content  the content
     */
    @Transactional(rollbackFor = Exception.class)
    public void sendConsoleMessage(Long adminId,Long buyerId, Long sellerId,Long tradeId, String content){
         this.saveChatMessage(adminId,buyerId,content,MessageType.SYSTEM.getCode(),tradeId, null);
         this.saveChatMessage(adminId,sellerId,content,MessageType.SYSTEM.getCode(),tradeId, null);
    }


    /**
     *  发送通知消息
     * @param receiveId
     * @param constantEnum
     * @param code
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean sendMessage(Long receiveId, UserMessageConstantEnum constantEnum,String code,Long rationId){
         if(constantEnum == null){
             return false;
         }

         try{
             String content = constantEnum.getContent();
             if(StringUtils.isNotBlank(code)){
                 content = content.replace("#{code}",code);
             }
             if(content.contains("#{time}")){
                 String now = DateHelper.getCurrentStrDate(DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond);
                 content = content.replace("#{time}",now);
             }
             if(constantEnum.getSendtype() == 0 || constantEnum.getSendtype() == 1){
                 this.saveChatMessage(null,receiveId,content,constantEnum.getMessageType(),rationId, constantEnum.getCode());
             }
             if(constantEnum.getSendtype() == 0 || constantEnum.getSendtype() == 2){
                 User user = OTCBizPool.getInstance().userBiz.select(receiveId);
                 if(user != null){
                     AzureEmailUtil.sendEmail(user.getEmailAddress(),constantEnum.getTitle(),content);
                 }
             }
         }catch (Exception e){
             LOG.d(this,"通知发送失败");
             e.printStackTrace();
             return false;
         }
         return true;

    }





    /**
     * Save message boolean.
     *
     * @param message the message
     * @return the boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveMessage(Message message) {
        Boolean flag = false;
        messageFilter(message);
        message.setCreateDate(new Date());
        int ret = messageMapper.insert(message);
        if (ret != 0)
            flag = Boolean.TRUE;
        return flag;
    }

    /**
     * Query list by condition list.
     *
     * @param cond the cond
     * @return the list
     */
    public List<Message> queryListByCondition(MessageCond cond) {
        return messageMapper.queryListByCondition(cond);
    }


    /**
     * Query chat by condition page message vo.
     *
     * @param vo the vo
     * @return the message vo
     */
    @Transactional(rollbackFor = Exception.class)
    public MessageVo queryChatByConditionPage(MessageVo vo){
        List<Message> list = messageMapper.queryChatByConditionPage(vo);
        if (list != null && list.size() > 0){
            vo.setResultList(list);
        }
        // 更新
        messageMapper.updateChatMessage(vo.getReceive(), vo.getRelationId());
        return vo;
    }

}
