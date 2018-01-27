package com.otc.core.token;

import com.jucaifu.common.exceptions.biz.BizException;

/**
 * TokenGeneratorException
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/1/6.
 */
public class TokenGeneratorException extends BizException {

    public TokenGeneratorException(Integer code, String msg) {
        super(code, msg);
    }

    public TokenGeneratorException() {
    }

    public TokenGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenGeneratorException(Throwable cause) {
        super(cause);
    }

    public TokenGeneratorException(String msg) {
        super(msg);
    }
}
