package com.szzc.api.three.exceptions;

import com.jucaifu.common.exceptions.biz.BizException;

/**
 * ProductBizException
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/18.
 */
public class ThreeBizException extends BizException {

    public ThreeBizException(String msg) {
        super(msg);
    }

    public ThreeBizException(Integer code, String msg) {
        super(code, msg);
    }

    public ThreeBizException(Integer code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }
}
