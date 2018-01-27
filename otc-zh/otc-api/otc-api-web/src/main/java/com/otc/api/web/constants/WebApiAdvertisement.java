package com.otc.api.web.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by zygong on 17-4-26.
 */
public interface WebApiAdvertisement extends ApiBasePathConstant {

    String PREFIX_ADVERTISEMENT_WEB_API = "/advertisement";
    // 获取首页广告列表
    String  GETINDEXLIST_ADVERTISEMENT_WEB_API = PREFIX_ADVERTISEMENT_WEB_API + "/getIndexList" + PATH_QUERY_JSON;
    // 获取用户广告列表
    String  GETUSERLIST_ADVERTISEMENT_WEB_API = PREFIX_ADVERTISEMENT_WEB_API + "/getUserList" + PATH_QUERY_JSON;
    // 获取用户其他交易广告
    String  GETUSEROTHERLIST_ADVERTISEMENT_WEB_API = PREFIX_ADVERTISEMENT_WEB_API + "/getUserOtherList" + PATH_QUERY_JSON;
    // 保存
    String  SAVE_ADVERTISEMENT_WEB_API = PREFIX_ADVERTISEMENT_WEB_API + "/save";
    // 详情
    String  DETAIL_ADVERTISEMENT_WEB_API = PREFIX_ADVERTISEMENT_WEB_API + "/detail" + PATH_QUERY_JSON;
    // 更新状态
    String  UPDATESTATUS_ADVERTISEMENT_WEB_API = PREFIX_ADVERTISEMENT_WEB_API + "/updateStatus";
    // 获取付款方式
    String PAYTYPELIST_ADVERTISEMENT_WEB_API = PREFIX_ADVERTISEMENT_WEB_API + "/payTypeList";
    // 获取计算公式
    String CALCULATIONFORMULA_ADVERTISEMENT_WEB_API = PREFIX_ADVERTISEMENT_WEB_API + "/calculationFormula";
    // 获取其他平台数据
    String OTHERPLATFORMPRICE_ADVERTISEMENT_WEB_API = PREFIX_ADVERTISEMENT_WEB_API + "/otherPlatformPrice" + PATH_QUERY_JSON;
    // 人民币和币种价格转换
    String TRANSFORM_ADVERTISEMENT_WEB_API = PREFIX_ADVERTISEMENT_WEB_API + "/transform";
    // 判断用户币的数量
    String USERCOINNUMBER_ADVERTISEMENT_WEB_API = PREFIX_ADVERTISEMENT_WEB_API + "/userCoinNumber" + PATH_QUERY_JSON;
    // 溢价动态获取价格
    String NEWPRICE_ADVERTISEMENT_WEB_API = PREFIX_ADVERTISEMENT_WEB_API + "/newPrice" + PATH_QUERY_JSON;

    // 广告时间设置
    String PREFIX_ADVERTISEMENTTIME_WEB_API = "/advertisementTime";
    // 保存
    String  SAVEORUPDATE_ADVERTISEMENTTIME_WEB_API = PREFIX_ADVERTISEMENTTIME_WEB_API + "/saveOrUpdate";
    // 详情
    String  DETAIL_ADVERTISEMENTTIME_WEB_API = PREFIX_ADVERTISEMENTTIME_WEB_API + "/detail" + PATH_QUERY_JSON;
    // 删除
    String  DELETE_ADVERTISEMENTTIME_WEB_API = PREFIX_ADVERTISEMENTTIME_WEB_API + "/delete" + PATH_QUERY_JSON;

    // 交易说明
    String PREFIX_TRANSACTIONDESC_WEB_API = "/transactionDes";
    // 详情
    String  DETAIL_TRANSACTIONDESC_WEB_API = PREFIX_TRANSACTIONDESC_WEB_API + "/detail" + PATH_QUERY_JSON;

}
