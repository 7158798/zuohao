package com.szzc.api.app.exceptions;

import com.jucaifu.common.exceptions.biz.BizException;

/**
 * ProductBizException
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/18.
 */
public class AppBizException extends BizException {

    public AppBizException(String msg) {
        super(msg);
    }

    public AppBizException(Integer code, String msg) {
        super(code, msg);
    }

    public AppBizException(Integer code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }
}
