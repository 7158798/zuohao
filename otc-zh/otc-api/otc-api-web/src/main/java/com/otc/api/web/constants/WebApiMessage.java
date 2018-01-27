package com.otc.api.web.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by zygong on 17-4-25.
 */
public interface WebApiMessage extends ApiBasePathConstant {

    String PREFIX_MESSAGE_WEB_API = WEB_API + "/message";

    // 消息列表
    String LIST_MESSAGE_WEB_API = PREFIX_MESSAGE_WEB_API + PATH_LIST + PATH_QUERY_JSON;
    // 消息类型列表
    String LIST_TYPE_WEB_API = PREFIX_MESSAGE_WEB_API + "/type" + PATH_LIST + PATH_QUERY_JSON;
    // 聊天消息列表
    String LIST_CHAT_WEB_API = PREFIX_MESSAGE_WEB_API + "/chat" + PATH_LIST + PATH_QUERY_JSON;
    // 更新聊天
    String UPDATE_CHAT_WEB_API = PREFIX_MESSAGE_WEB_API + "/chat" + PATH_UPDATE;

}
