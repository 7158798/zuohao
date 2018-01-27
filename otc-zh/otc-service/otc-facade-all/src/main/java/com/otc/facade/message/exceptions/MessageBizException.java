package com.otc.facade.message.exceptions;

import com.jucaifu.common.exceptions.biz.BizException;

/**
 * UserBizException
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/17.
 */
public class MessageBizException extends BizException {

    /**
     * Instantiates a new User biz exception.
     *
     * @param msg the msg
     */
    public MessageBizException(String msg) {
        super(msg);
    }

    /**
     * Instantiates a new User biz exception.
     *
     * @param code the code
     * @param msg the msg
     */
    public MessageBizException(Integer code, String msg) {
        super(code, msg);
    }

    /**
     * Instantiates a new User biz exception.
     *
     * @param cause the cause
     */
    public MessageBizException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new User biz exception.
     *
     * @param code the code
     * @param msgFormat the msg format
     * @param args the args
     */
    public MessageBizException(Integer code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }
}
