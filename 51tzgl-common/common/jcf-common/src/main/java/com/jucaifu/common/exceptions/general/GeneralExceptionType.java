package com.jucaifu.common.exceptions.general;

import com.jucaifu.common.enums.EnumExceptionLevel;
import com.jucaifu.common.enums.IEnum;

/**
 * =======================================
 * 说明:异常代码格式
 * XXYYY
 * XX  2位固定长度的业务模块代码
 * YYY 3位固定长度的业务异常代码
 * =======================================
 * GeneralExceptionType
 *
 * @author scofieldcai Created by scofieldcai-dev on 15/8/31.
 */
public enum GeneralExceptionType implements IEnum<String> {

    /**
     * The CUSTOMER_EXCEPTION.
     */
    CUSTOMER_EXCEPTION("99000", "用户自定义，非框架内置."),
    /**
     * The NO_PERMISSION_EXCEPTION.
     */
    NO_PERMISSION_EXCEPTION("99001", "无权限访问异常"),
    /**
     * The NOT_SUPPORT_REQUEST_METHOD_EXCEPTION.
     */
    NOT_SUPPORT_REQUEST_METHOD_EXCEPTION("99002", "错误请求,不支持该请求方式."),

    /**
     * The REQUEST_PARAMETER_EXCEPTION.
     */
    REQUEST_PARAMETER_EXCEPTION("99003", "请求参数不正确."),

    /**
     * The UnknownAccountException.
     */
    UnknownAccountException("99004", "账户不存在"),
    /**
     * The IncorrectCredentialsException.
     */
    IncorrectCredentialsException("99005", "账户密码错误"),
    /**
     * The UserNameNotMatchPassword.
     */
    UserNameNotMatchPassword("99006", "账户名或密码错误"),

    /**
     * 账户被锁定
     */
    LOCKED_ACCOUNT_EXCEPTION("990061", "账户被锁定"),

    /**
     * The SIGN_EXCEPTION.
     */
    SIGN_EXCEPTION("99007", "验签失败,报文不完整"),
    /**
     * The PARSE_APP_REQUEST_EXCEPTION.
     */
    PARSE_APP_REQUEST_EXCEPTION("99008", "解析App请求报文异常"),
    /**
     * The BUILD_APP_RESPONSE_EXCEPTION.
     */
    BUILD_APP_RESPONSE_EXCEPTION("99009", "构建App响应报文异常"),

    /**
     * The BUILD_APP_JSON_EXCEPTION.
     */
    BUILD_APP_JSON_EXCEPTION("99010", "App请求报文非JSON格式"),

    /**
     * The APP_FORCE_UPDATE_EXCEPTION.
     */
    APP_FORCE_UPDATE_EXCEPTION("99011", "App强制更新异常"),

    /**
     * The TOKEN_INVALID_EXCEPTION.
     */
    TOKEN_INVALID_EXCEPTION("99012", "Token无效"),

    /**
     * The API_VERSION_INVALID_EXCEPTION.
     */
    API_VERSION_INVALID_EXCEPTION("99013", "访问的Api版本号不正确,请升级到最新!"),

    /**
     * The UNKNOWN_EXCEPTION.
     */
    UNKNOWN_EXCEPTION("99999", "未知异常"),;


    private String value;
    private String desc;
    private EnumExceptionLevel levelEnum;

    /**
     * Instantiates a new General exception type.
     *
     * @param value the value
     * @param desc  the  desc
     */
    GeneralExceptionType(String value, String desc) {
        this.value = value;
        this.desc = desc;
        this.levelEnum = EnumExceptionLevel.FAIL;
    }

    /**
     * Instantiates a new General exception type.
     *
     * @param value     the value
     * @param desc      the desc
     * @param levelEnum the levelEnum
     */
    GeneralExceptionType(String value, String desc, EnumExceptionLevel levelEnum) {
        this.value = value;
        this.desc = desc;
        this.levelEnum = levelEnum;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * Gets desc.
     *
     * @return the desc
     */
    @Override
    public String getDesc() {
        return desc;
    }

    /**
     * Gets levelEnum.
     *
     * @return the levelEnum
     */
    public EnumExceptionLevel getLevelEnum() {
        return levelEnum;
    }

}
