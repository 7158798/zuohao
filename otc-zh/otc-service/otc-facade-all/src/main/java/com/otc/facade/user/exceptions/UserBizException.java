package com.otc.facade.user.exceptions;

import com.jucaifu.common.exceptions.biz.BizException;

/**
 * UserBizException
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/17.
 */
public class UserBizException extends BizException {

    /**
     * 登录密码错误次数超限
     */
    public static final UserBizException LOGIN_PWD_ERROR_COUNT_LIMITED_EXCEPTION = new UserBizException(10019999, "对不起，您已经超过今日登录密码验证次数上限，请明天再来。");

    /**
     * 用户已锁定
     */
    public static final UserBizException LOCKED_USER_EXCEPTION = new UserBizException(10019998, "您的账户已锁定，请明天再来，或联系客服解锁账户。");



    /**
     * 用户输入支付密码错误次数超限:Order中有对应的异常
     */
    public static final UserBizException PAY_PWD_ERROR_COUNT_LIMITED_EXCEPTION = new UserBizException(10039997, "用户输入支付密码错误次数超限.");

    /**
     * 用户支付密码错误
     */
    public static final UserBizException PAY_PWD_ERROR_EXCEPTION = new UserBizException(10039996, "用户支付密码错误.");

    /**
     * 手机号码不存在
     */
    public static final Integer MOBILEPHONE_OR_PASSWOD_NO_SUCCESS = 10010001;
    /**
     * 用户被锁定
     */
    public static final Integer USER_IS_LOCAKED = 10010002;
    /**
     * 手机号已经被注册
     */
    public static final Integer MOBILEPHONE_IS_REGISTED = 10010003;

    /**
     * 手机号格式不正确
     */
    public static final Integer MOBILEPHONE_FORMAT_INCORRECT = 10010004;
    /**
     * 用户密码格式不正确
     */
    public static final Integer PASSWORD_FORMAT_INCORRECT = 10010005;
    /**
     * 手机短信验证码格式不正确
     */
    public static final Integer AUTHCODE_FORMAT_INCORRECT = 10010006;
    /**
     * 用户未登录
     */
    public static final Integer USER_NOT_LOGIN = 10010007;
    /**
     * 用户不存在
     */
    public static final Integer USER_NOT_EXIST = 10010008;
    /**
     * 身份证号格式不正确
     */
    public static final Integer CARDNO_FORMAT_INCORRECT = 10010009;
    /**
     * 参数不能为空
     */
    public static final Integer PARAM_IS_NOT_BLANK = 10010010;
    /**
     * 用户没有实名认证
     */
    public static final Integer USER_CANNOT_REALNAME_AUTH = 10010011;
    /**
     * 参数格式不正确
     */
    public static final Integer PARAM_FORMAT_INCORRECT = 10010012;
    /**
     * 参数值不正确
     */
    public static final Integer PARAM_VALUE_INCORRECT = 10010013;
    /**
     * 手机短信验证码不正确
     */
    public static final Integer AUTHCODE_VALUE_INCORRECT = 10010014;

    /**
     * 操作失败
     */
    public static final Integer OPERATE_IS_FAILURE = 10020015;

    /**
     * 短信动态支付密码错误
     */
    public static final Integer DYNAMIC_PAY_PWD_FAILURE = 10039996;


    /**
     * Instantiates a new User biz exception.
     *
     * @param msg the msg
     */
    public UserBizException(String msg) {
        super(msg);
    }

    /**
     * Instantiates a new User biz exception.
     *
     * @param code the code
     * @param msg the msg
     */
    public UserBizException(Integer code, String msg) {
        super(code, msg);
    }

    /**
     * Instantiates a new User biz exception.
     *
     * @param cause the cause
     */
    public UserBizException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new User biz exception.
     *
     * @param code the code
     * @param msgFormat the msg format
     * @param args the args
     */
    public UserBizException(Integer code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }
}
