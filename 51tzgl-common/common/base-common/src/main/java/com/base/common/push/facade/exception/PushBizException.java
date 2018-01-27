package com.base.common.push.facade.exception;

/**
 * Created by liuxun on 16-9-30.
 */
public class PushBizException extends RuntimeException{

    public PushBizException(String message){
        super(message);
    }

    public PushBizException(String message, Throwable cause){
        super(message,cause);
    }
}
