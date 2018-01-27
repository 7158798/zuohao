package com.otc.facade.advertisement.exceptions;

import com.jucaifu.common.exceptions.biz.BizException;

/**
 * Created by zygong on 17-5-12.
 */
public class AdvertisementTimeBizException extends BizException {
    /**
     * Instantiates a new User biz exception.
     *
     * @param msg the msg
     */
    public AdvertisementTimeBizException(String msg) {
        super(msg);
    }

    /**
     * Instantiates a new User biz exception.
     *
     * @param code the code
     * @param msg the msg
     */
    public AdvertisementTimeBizException(Integer code, String msg) {
        super(code, msg);
    }

    /**
     * Instantiates a new User biz exception.
     *
     * @param cause the cause
     */
    public AdvertisementTimeBizException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new User biz exception.
     *
     * @param code the code
     * @param msgFormat the msg format
     * @param args the args
     */
    public AdvertisementTimeBizException(Integer code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }
    
}
