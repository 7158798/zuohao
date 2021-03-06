package com.szzc.facade.wallet.exceptions;

import com.jucaifu.common.exceptions.biz.BizException;

/**
 * UserBizException
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/17.
 */
public class WalletBizException extends BizException {

    /**
     * Instantiates a new User biz exception.
     *
     * @param msg the msg
     */
    public WalletBizException(String msg) {
        super(msg);
    }

    /**
     * Instantiates a new User biz exception.
     *
     * @param code the code
     * @param msg the msg
     */
    public WalletBizException(Integer code, String msg) {
        super(code, msg);
    }

    /**
     * Instantiates a new User biz exception.
     *
     * @param cause the cause
     */
    public WalletBizException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new User biz exception.
     *
     * @param code the code
     * @param msgFormat the msg format
     * @param args the args
     */
    public WalletBizException(Integer code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }
}
