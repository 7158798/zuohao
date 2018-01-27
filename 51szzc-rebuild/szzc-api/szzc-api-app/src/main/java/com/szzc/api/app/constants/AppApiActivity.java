package com.szzc.api.app.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by yangyy on 15-12-5.
 */
public interface AppApiActivity extends ApiBasePathConstant {

    String PREFIX_ACTIVITY_API = APP_API + "/activity";

    /** 根据显示位置查询活动列表 */
    String LIST_ACTIVITY_POSITION_API = PREFIX_ACTIVITY_API + "/position" + PATH_QUERY_JSON;

    /** 分页查询活动列表 */
    String LIST_ACTIVITY_API = PREFIX_ACTIVITY_API + PATH_LIST + PATH_QUERY_JSON;

    /** 查询app每日休闲文章列表 */
    String LIST_LEISURE_ARTICLE_API = PREFIX_ACTIVITY_API + "/leisureArticlelist" + PATH_QUERY_JSON;

    /**获取当前欢迎页url **/
    String GET_WELCOME_PAGE_API = APP_API+"/welcomepage/geturl" + PATH_QUERY_JSON;
}
