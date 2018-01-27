package com.jucaifu.common.exceptions.biz;

import com.jucaifu.common.util.ReflectHelper;

/**
 * ================================================================
 * 格式说明：定义异常时，需要先确定异常所属模块。<br>
 * 例如：添加用户报错 可以定义为 [10010001] 前四位数为系统模块编号，后4位为错误代码 并唯一 <br>
 * ================================================================
 * jcf-facade-user异常 1001 <br>
 * jcf-facade-product异常 1002 <br>
 * jcf-facade-order异常 1003 <br>
 * jcf-facade-system异常 1004 <br>
 * jcf-facade-mobile异常 1005 <br>
 * jcf-facade-web异常 1005 <br>
 * <p>
 * ================================================================
 * jcf-api-console 异常 2001 <br>
 * jcf-api-app 异常 2002 <br>
 * jcf-api-web 异常 2003 <br>
 * jcf-api-wechat 异常 2004 <br>
 * <p>
 * ================================================================
 * BizException 业务异常基类
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/15.
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = -5875371379845226068L;

    /**
     * Token验证不通过
     */
    public static final BizException TOKEN_IS_ILLICIT = new BizException(99910001, "Token验证非法");
    /**
     * 会话超时
     */
    public static final BizException SESSION_IS_OUT_TIME = new BizException(99910002, "会话超时");

    /**
     * 异常信息
     */
    protected String msg;

    /**
     * 异常码
     */
    protected Integer code;

    /**
     * Instantiates a new Biz exception.
     */
    public BizException() {
        super();
    }

    /**
     * Instantiates a new Biz exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Biz exception.
     *
     * @param cause the cause
     */
    public BizException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Biz exception.
     *
     * @param msg the msg
     */
    public BizException(String msg) {
        super(msg);
        this.code = -1;
        this.msg = msg;
    }

    /**
     * Instantiates a new Biz exception.
     *
     * @param code the code
     * @param msg the msg
     */
    public BizException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    /**
     * Instantiates a new Biz exception.
     *
     * @param code the code
     * @param msgFormat the msg format
     * @param args the args
     */
    public BizException(Integer code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
        this.msg = String.format(msgFormat, args);
    }

    /**
     * Gets msg.
     *
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * New instance.
     *
     * @param <T>     the type parameter
     * @param tClazz the t clazz
     * @param code the code
     * @param msg the msg
     * @return the t
     */
    public static <T extends RuntimeException> T newInstance(Class<T> tClazz, Integer code, String msg) {

        T bizException = ReflectHelper.newClassInstance(tClazz.getName(), new Object[]{code, msg});

        return bizException;
    }

    /**
     * New instance.
     *
     * @param <T>        the type parameter
     * @param tClazz the t clazz
     * @param code the code
     * @param msgFormat the msg format
     * @param args the args
     * @return the t
     */
    public static <T extends RuntimeException> T newInstance(Class<T> tClazz, Integer code, String msgFormat, Object... args) {

        T bizException = ReflectHelper.newClassInstance(tClazz.getName(), new Object[]{code, msgFormat, args});

        return bizException;
    }


}
