package com.otc.api.web.ctrl.message;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.DateHelper;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.web.base.BaseWebCtrl;
import com.otc.api.web.constants.WebApiMessage;
import com.otc.api.web.ctrl.message.request.ChatReq;
import com.otc.api.web.ctrl.message.request.MessageReq;
import com.otc.api.web.ctrl.message.response.ChatResp;
import com.otc.api.web.ctrl.message.response.MessageResp;
import com.otc.api.web.ctrl.message.response.MessageTypeResp;
import com.otc.api.web.exceptions.WebBizException;
import com.otc.common.api.packet.web.WebApiResponse;
import com.otc.core.cache.UserCacheManager;
import com.otc.facade.message.enums.MessageType;
import com.otc.facade.message.pojo.po.Message;
import com.otc.facade.message.pojo.vo.MessageVo;
import com.otc.facade.user.pojo.po.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lx on 17-5-9.
 */
@RestController
public class MessageCtrl extends BaseWebCtrl implements WebApiMessage {

    @RequestMapping(value = LIST_TYPE_WEB_API,method = RequestMethod.GET)
    public WebApiResponse getTypeList(@PathVariable String queryJson){
        LOG.dStart(this, "获取消息类型开始");
        JsonHelper.obj2JsonStr(queryJson);
        List<MessageTypeResp> respList = new ArrayList<>();
        MessageTypeResp resp;
        for (MessageType type : MessageType.values()){
            resp = new MessageTypeResp();
            resp.setType(type.getCode());
            resp.setTypeName(type.getDesc());
            respList.add(resp);
        }
        LOG.d(this,respList);
        LOG.dEnd(this, "获取消息类型结束");
        return buildWebApiResponse(SUCCESS,respList);
    }

    @RequestMapping(value = LIST_MESSAGE_WEB_API,method = RequestMethod.GET)
    public WebApiResponse getMessageList(@PathVariable String queryJson){
        LOG.dStart(this, "获取消息列表开始");
        MessageReq req = JsonHelper.jsonStr2Obj(queryJson, MessageReq.class);
        if (req == null){
            throw new WebBizException("请求参数不能为空");
        }
        Long userId = UserCacheManager.getUidWithToken(req.getToken());
        MessageVo vo = new MessageVo();
        vo.setType(req.getType());
        vo.setReceive(userId);
        //vo.setType(MessageType.CHAT.getCode());
        vo.setPageShow(req.fetchPageFilterSize());
        vo.setNowPage(req.fetchPageFilterPage());
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MONTH, -3);
        String s = DateHelper.date2String(instance.getTime(), DateHelper.DateFormatType.YearMonthDay);
        vo.setTimeStr(s);
        vo = otc.messageWebFacade.queryByConditionPage(vo);
        List<Message> list = vo.fatchTransferList();
        List<MessageResp> respList = new ArrayList<>();
        MessageResp resp;
        for (Message message : list){
            resp = new MessageResp();
            resp.copy(message);
            if(message.getType().equals(MessageType.CHAT.getCode())){
                User user = otc.userWebFacade.selectById(message.getCreateUser());
                if(user != null){
                    resp.setSendUserName(user.getLoginName());
                }
            }
            respList.add(resp);
        }
        LOG.d(this,respList);
        LOG.dEnd(this, "获取消息列表结束");
        return buildWebApiPageResponse(vo,respList);
    }

    @RequestMapping(value = LIST_CHAT_WEB_API,method = RequestMethod.GET)
    public WebApiResponse getChatList(@PathVariable String queryJson){
        LOG.dStart(this, "获取消息列表开始");
        MessageReq req = JsonHelper.jsonStr2Obj(queryJson, MessageReq.class);
        if (req == null){
            throw new WebBizException("请求参数不能为空");
        }
        if (req.getRelationId() == null){
            throw new WebBizException("关联id不能为空");
        }

        Long userId = UserCacheManager.getUidWithToken(req.getToken());
        MessageVo vo = new MessageVo();
        vo.setType(MessageType.CHAT.getCode());
        vo.setRelationId(req.getRelationId());
        vo.setPageShow(req.fetchPageFilterSize());
        vo.setNowPage(req.fetchPageFilterPage());
        vo.setReceive(userId);
        vo = otc.messageWebFacade.queryChatByConditionPage(vo);
        List<Message> list = vo.fatchTransferList();
        List<ChatResp> respList = new ArrayList<>();
        ChatResp resp;
        for (Message message : list){
            resp = new ChatResp();
            resp.cpoy(message);
            getUserInfo(resp);
            respList.add(resp);
        }
        LOG.d(this,respList);
        LOG.dEnd(this, "获取消息列表结束");
        return buildWebApiPageResponse(vo,respList);
    }

    @RequestMapping(value = UPDATE_CHAT_WEB_API,method = RequestMethod.POST)
    public WebApiResponse updateChat(@RequestBody ChatReq req){
        LOG.dStart(this, "更新消息为已读开始");
        if (req == null)
            throw new WebBizException("请求参数不能为空");
        if (req.getId() == null)
            throw new WebBizException("消息ID不能为空");
        Message message = otc.messageWebFacade.queryMessage(req.getId());
        if (message == null)
            throw new WebBizException("消息不存在");
        Long userId = UserCacheManager.getUidWithToken(req.getToken());
        if (message.getReceive() != userId)
            throw new WebBizException("消息不存在");
        message.setIsRead(Boolean.TRUE);
        otc.messageWebFacade.updateMessage(message);
        LOG.dEnd(this, "更新消息为已读结束");
        return buildWebApiResponse(SUCCESS,null);
    }

    private void getUserInfo(ChatResp resp){
        User user = otc.userWebFacade.selectById(resp.getFrom());
        if (user != null){
            resp.setAvatar(user.getAvatar());
            resp.setFromName(user.getLoginName());
        }
    }
}
