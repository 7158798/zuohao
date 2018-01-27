package com.jucaifu.common.configs;

import com.jucaifu.common.context.SpringPropReaderHelper;

/**
 * SyncConfigConstant
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/1/8.
 */
public class OtcBusinessConfig {

    public static final String BUCKET_NAME = SpringPropReaderHelper.getProperty("aws_bucket_name");

    public static final String AWS_ACCESS_KEY_ID = SpringPropReaderHelper.getProperty("aws_access_key_id");

    public static final String  AWS_SECRET_ASSESS_KEY = SpringPropReaderHelper.getProperty("aws_secret_access_key");

    public static final String AWS_REGION_FILE_LOAD =  SpringPropReaderHelper.getProperty("aws_region_file_load");

    public static final String FROM_EMAIL = SpringPropReaderHelper.getProperty("aws_from_email");

    public static final String  DISABLED_SMS = SpringPropReaderHelper.getProperty("aws_disabled_sms");

    public static final String MAIL_NAME = SpringPropReaderHelper.getProperty("MailName");






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

}
