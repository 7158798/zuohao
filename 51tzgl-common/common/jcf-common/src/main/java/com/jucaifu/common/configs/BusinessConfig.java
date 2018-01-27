package com.jucaifu.common.configs;

import java.util.HashMap;
import java.util.Map;
import com.jucaifu.common.context.SpringPropReaderHelper;
import org.apache.commons.lang3.StringUtils;

/**
 * SyncConfigConstant
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/1/8.
 */
public class BusinessConfig {


    /**
     * 默认同步时一次最多同步数量
     */
    public static final int SYNC_ONCE_PAGESIZE = 20;

    /**
     * 积分提现最小提现的积分数
     */
    public static final int POINTS_EXTRACT_MIN = Integer.valueOf(SpringPropReaderHelper.getProperty("business.points.extract.min"));

    /**
     * 积分提现手续费
     */
    public static int POINTS_EXTRACT_FEE = Integer.valueOf(SpringPropReaderHelper.getProperty("business.points.extract.fee"));

    /**
     * 用户支付密码错误次数
     */
    public static final int USER_PAY_PWD_ERROR_COUNT = Integer.valueOf(SpringPropReaderHelper.getProperty("business.user.pay.pwd.error.count"));

    /**
     * 用户登录密码错误次数
     */
    public static final int USER_LOGIN_PWD_ERROR_COUNT = Integer.valueOf(SpringPropReaderHelper.getProperty("business.user.login.pwd.error.count"));

    /**
     * 用户登录密码错误次数达上限，密码锁定时间（单位：分钟）
     */
    public static final int USER_LOGIN_PWD_LOCK_TIME = Integer.valueOf(SpringPropReaderHelper.getProperty("business.user.login.pwd.lock.time"));

    /**
     * Web网站注册页面url
     */
    public static final String WEB_REGIST_URL = SpringPropReaderHelper.getProperty("registration.web.link");

    /**
     * APP登陆后的注册页面url
     */
    public static final String APP_REGIST_URL = SpringPropReaderHelper.getProperty("registration.app.haslogin.link");
    /**
     * app未登录的注册url
     */
    public static final String APP_REGIST_UNLOGIN_URL = SpringPropReaderHelper.getProperty("registration.app.notlogin.link");
    /**
     * 体验机构机构代码
     */
    public static final String EXPERIENCE_ORGAN_CODE = SpringPropReaderHelper.getProperty("business.experience.org.code");

    /**
     * 银联认证结果跳转页面
     */
    public static final String CHINAPAY_AUTH_RESULT_PAGE = SpringPropReaderHelper.getProperty("business.chinapay.auth.result.page");

    public static final String DOC_TO_PDF_CHANGE_URL = SpringPropReaderHelper.getProperty("business.doc2pdf.server.url");

    public static final int DOC_TO_PDF_SERVER_PORT = Integer.valueOf(SpringPropReaderHelper.getProperty("business.doc2pdf.server.port"));

    /**
     * 抽奖活动中奖项数量
     */
    public static final int AWARD_COUNT = 6;
    /**
     * 抽奖活动中抽奖盒子中的总数量
     */
    public static final long BASE_NUMBER = 10000L;

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
     * app聚财富日常跳转html5页面地址
     */
    public static final String JUCAIFU_DAILY_URL = SpringPropReaderHelper.getProperty("business.jucaifu.daily.url");

    /**
     * 添加佣金计算只算直属理财师的1%的时间节点
     */
    public static final String POINTS_COUNT_DATE_NODE = SpringPropReaderHelper.getProperty("business.points.count.date.node");

    /**
     * 续投新旧产品映射
     */
    private static final String FOLLOW_PRODUCT_MAPPER = SpringPropReaderHelper.getProperty("business.follow.product.mapper");

    /**
     * 汇率接口地址
     */
    public static final String INFO_EXCHANGE_URL = SpringPropReaderHelper.getProperty("info.exchange.url");
    /**
     * 汇率请求的KEY
     */
    public static final String INFO_EXCHANGE_KEY = SpringPropReaderHelper.getProperty("info.exchange.key");
    /**
     * 汇率接口请求的sign
     */
    public static final String INFO_EXCHANGE_SIGN = SpringPropReaderHelper.getProperty("info.exchange.sign");
    /**
     * 同步接口失败后，通知手机号码
     */
    public static final String INFO_FAIL_PHONENUMBER = SpringPropReaderHelper.getProperty("info.fail.phoneNumber");
    /**
     * PDF中文字体的路径
     */
    public static final String PDF_FONT_PATH = SpringPropReaderHelper.getProperty("pdf.font.path");


    private static final Map<String, String> map = new HashMap<>();

    static {
        if (StringUtils.isNotBlank(FOLLOW_PRODUCT_MAPPER)) {
            String[] productMapper = FOLLOW_PRODUCT_MAPPER.split("\\|");
            for (int i = 0; i < productMapper.length; i++) {
                String[] oldAndNewProductId = productMapper[i].split(":");
                if (oldAndNewProductId.length == 2) {
                    map.put(oldAndNewProductId[0], oldAndNewProductId[1]);
                }
            }
        }
    }

    public static Map<String, String> getProductMapper() {
        return map;
    }

}
