package com.jucaifu.common.configs;

import com.jucaifu.common.context.SpringPropReaderHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * SyncConfigConstant
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/1/8.
 */
public class TzglBusinessConfig {

    /**
     * 兑吧MD5签名密钥
     */
    public static final String DUIBA_MD5_SIGN_SECRET = SpringPropReaderHelper.getProperty("business.duiba.md5.sign.secret");

    /**
     * 兑吧app应用KEY
     */
    public static final String DUIBA_APP_KEY = SpringPropReaderHelper.getProperty("business.duiba.app.key");

    /**
     * 会员等级划分金额
     */
    public static final String DUIBA_USER_RANK_DIVIDE_AMOUNT = SpringPropReaderHelper.getProperty("business.duiba.user.rank.divide.amount");

    /**
     * 兑吧自动登录跳转页面
     */
    public static final String DUIBA_PAGE_REDIRECT_URL = SpringPropReaderHelper.getProperty("business.duiba.page.redirect.url");


    /**
     * 攻略分享地址
     */
    public static final String RAIDERS_SHARE_URL = SpringPropReaderHelper.getProperty("raiders.share.url");

    /**
     * 微信商户号
     */
    public static final String WX_MCH_ID = SpringPropReaderHelper.getProperty("weixin.mch.id");
    /**
     * 公众账号appid
     */
    public static final String WX_APPID = SpringPropReaderHelper.getProperty("weixin.appid");

    /**
     * 微信商户名称
     */
    public static final String WX_MCH_NAME = SpringPropReaderHelper.getProperty("weixin.mch.name");

    /**
     * 密钥
     */
    public static final String WX_KEY = SpringPropReaderHelper.getProperty("weixin.key");

    /**
     * 公众号的唯一标识
     */
    public static final String WEIXIN_APPID = SpringPropReaderHelper.getProperty("weixin.appid");

    /**
     * 公众号的appsecret
     */
    public static final String WEIXIN_SECRET = SpringPropReaderHelper.getProperty("weixin.secret");




}
