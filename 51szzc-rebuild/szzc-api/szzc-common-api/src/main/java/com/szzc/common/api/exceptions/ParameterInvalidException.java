package com.szzc.common.api.exceptions;


import com.jucaifu.common.exceptions.biz.BizException;

/**
 * ParameterInvalidException
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/09.
 */
public class ParameterInvalidException extends BizException {

    /**
     * 分页请求参数不正确异常
     */
    public static final ParameterInvalidException REQUEST_PAGE_INVALID_EXCEPTION = new ParameterInvalidException(90010001, "请求分页参数不正确.");

    /**
     * Instantiates a new Parameter invalid exception.
     *
     * @param msg the msg
     */
    public ParameterInvalidException(String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Parameter invalid exception.
     *
     * @param code the code
     * @param msg  the msg
     */
    public ParameterInvalidException(Integer code, String msg) {
        super(code, msg);
    }

    /**
     * Instantiates a new Parameter invalid exception.
     *
     * @param code      the code
     * @param msgFormat the msg format
     * @param args      the args
     */
    public ParameterInvalidException(Integer code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }


}
