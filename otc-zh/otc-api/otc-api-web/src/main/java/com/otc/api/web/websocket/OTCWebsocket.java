package com.otc.api.web.websocket;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.otc.api.web.ctrl.message.request.ChatReq;
import com.otc.api.web.ctrl.message.response.ChatResp;
import com.otc.api.web.service.WebService;
import com.otc.core.cache.UserCacheManager;
import com.otc.facade.message.enums.MessageType;
import com.otc.facade.message.pojo.cond.MessageCond;
import com.otc.facade.message.pojo.po.Message;
import com.otc.facade.user.pojo.po.User;
import com.otc.facade.user.pojo.poex.CacheUserInfo;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lx on 17-5-10.
 */
@ServerEndpoint("/websocket")
public class OTCWebsocket {

    public static final Map<String, Session> sessionMap;

    static {
        sessionMap = new HashMap<>();
    }

    private String getMapKey(ChatReq req){
        StringBuffer key = new StringBuffer();
        key.append(req.getFrom()==null?req.getTo():req.getFrom());
        key.append("-");
        key.append(req.getRelationId());
        return key.toString();
    }

    private ChatReq getChatReq(Session session){
        Map<String,List<String>> map = session.getRequestParameterMap();
        String token = map.get("token").get(0);
        String relationId = map.get("relationId").get(0);
        Long userId = UserCacheManager.getUidWithToken(token);
        LOG.d(this,"websocket参数,token=" + token);
        LOG.d(this,"websocket参数,userId=" + userId);
        LOG.d(this,"websocket参数,relationId=" + relationId);
        ChatReq req = new ChatReq();
        req.setToken(token);
        req.setFrom(userId);
        req.setRelationId(Long.valueOf(relationId));
        return req;
    }

    @OnOpen
    public void onOpen(Session session) throws IOException {

        ChatReq req = getChatReq(session);
        String key = getMapKey(req);
        if (sessionMap.get(key) == null){
            sessionMap.put(key,session);
        }
        MessageCond cond = new MessageCond();
        cond.setIsRead(Boolean.FALSE);
        cond.setReceive(req.getFrom());
        cond.setRelationId(req.getRelationId());
        cond.setType(MessageType.CHAT.getCode());
        // websocket　链接之后查询是否有未读的消息
        /*List<Message> list = WebService.getInstance().messageWebFacade.queryListByCondition(cond);
        if (list != null && list.size() > 0){
            for (Message message : list){
                sendMessage(message,session,null);
                message.setIsRead(Boolean.TRUE);
                WebService.getInstance().messageWebFacade.updateMessage(message);
            }
        }*/
    }

    @OnClose
    public void onClose(Session session){
        LOG.d(this,"websocket 关闭测试");
        ChatReq req = getChatReq(session);
        String key = getMapKey(req);
        if (sessionMap.get(key.toString()) != null){
            sessionMap.remove(key.toString());
        }
    }

    @OnMessage
    public void onMessage(String message,Session session) throws IOException {
        LOG.d(this,"接收到的消息： " + message);
        // 保存消息
        ChatReq req = JsonHelper.jsonStr2Obj(message, ChatReq.class);
        Long userId = UserCacheManager.getUidWithToken(req.getToken());
        CacheUserInfo cacheUserInfo = UserCacheManager.getCacheObj(userId);
        Message obj = new Message();
        obj.setType(MessageType.CHAT.getCode());
        obj.setReceive(req.getTo());
        obj.setContent(req.getContent());
        obj.setRelationId(req.getRelationId());
        obj.setIsRead(Boolean.FALSE);
        obj.setCreateUser(userId);
        obj.setCreateDate(new Date());
        String key = getMapKey(req);
        Session to = sessionMap.get(key.toString());
        if (to != null){
            obj.setIsRead(Boolean.TRUE);
        }
        // 保存消息到数据库
        WebService.getInstance().messageWebFacade.saveMessage(obj);
        sendMessage(obj,to,cacheUserInfo);
    }

    @OnError
    public void onError(Session session, Throwable error){
        LOG.e(this,"websocket 发生异常", error);
    }

    public void sendMessage(Message message,Session session,CacheUserInfo cache) throws IOException {
        if (session != null){
            ChatResp resp = new ChatResp();
            resp.cpoy(message);
            getUserInfo(resp,cache);
            String json = JsonHelper.obj2JsonStr(resp);
            session.getBasicRemote().sendText(json);
        }
    }

    private void getUserInfo(ChatResp resp,CacheUserInfo cache){
        User user;
        if (cache == null){
            user = WebService.getInstance().userWebFacade.selectById(resp.getFrom());
        } else {
            user = cache.getBaseInfo();
        }
        if (user != null){
            resp.setAvatar(user.getAvatar());
            resp.setFromName(user.getLoginName());
        }
    }
}
