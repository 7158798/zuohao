package com.jucaifu.common.exceptions;

/**
 * BaseException
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/8/31.
 */
public class BaseException extends RuntimeException {

    /**
     * Instantiates a new Base exception.
     */
    public BaseException() {
        super();
    }

    /**
     * Instantiates a new Base exception.
     *
     * @param message the message
     */
    public BaseException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Base exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Base exception.
     *
     * @param cause the cause
     */
    public BaseException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Base exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
