package com.otc.api.console.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by zygong on 17-4-28.
 */
public interface ConsoleApiAnnouncement extends ApiBasePathConstant {

    // 公告
    String PREFIX_ANNOUNCEMENT_CONSOLE_API = "/announcement";
    // 列表
    String  LIST_ANNOUNCEMENT_CONSOLE_API = PREFIX_ANNOUNCEMENT_CONSOLE_API + "/getList";
    // 详情
    String  DETAIL_ANNOUNCEMENT_CONSOLE_API = PREFIX_ANNOUNCEMENT_CONSOLE_API + "/detail" + PATH_QUERY_JSON;
    // 保存
    String  SAVE_ANNOUNCEMENT_CONSOLE_API = PREFIX_ANNOUNCEMENT_CONSOLE_API + "/save";
    // 更新
    String  UPDATE_ANNOUNCEMENT_CONSOLE_API = PREFIX_ANNOUNCEMENT_CONSOLE_API + "/update";
    // 推荐
    String  ISDING_ANNOUNCEMENT_CONSOLE_API = PREFIX_ANNOUNCEMENT_CONSOLE_API + "/isDing";
    // 推荐
    String  DELETE_ANNOUNCEMENT_CONSOLE_API = PREFIX_ANNOUNCEMENT_CONSOLE_API + "/delete" + PATH_QUERY_JSON;
}
