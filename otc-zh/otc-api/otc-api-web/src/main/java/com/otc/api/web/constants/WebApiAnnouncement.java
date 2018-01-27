package com.otc.api.web.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by zygong on 17-4-28.
 */
public interface WebApiAnnouncement extends ApiBasePathConstant {

    // 公告
    String PREFIX_ANNOUNCEMENT_WEB_API = "/announcement";
    // 列表
    String  LIST_ANNOUNCEMENT_WEB_API = PREFIX_ANNOUNCEMENT_WEB_API + "/getList";
    // 获取
    String  INDEXLIST_ANNOUNCEMENT_WEB_API = PREFIX_ANNOUNCEMENT_WEB_API + "/getIndexList" + PATH_QUERY_JSON;
    // 详情
    String  DETAIL_ANNOUNCEMENT_WEB_API = PREFIX_ANNOUNCEMENT_WEB_API + "/detail" + PATH_QUERY_JSON;
}
