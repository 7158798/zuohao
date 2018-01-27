package com.otc.api.console.exceptions;

import com.jucaifu.common.exceptions.biz.BizException;

/**
 * ProductBizException
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/18.
 */
public class ConsoleBizException extends BizException {
    /** 参数值不正确 */
    public static final Integer PARAM_VALUE_INCORRECT = 20010003;

    public ConsoleBizException(String msg) {
        super(msg);
    }

    public ConsoleBizException(Integer code, String msg) {
        super(code, msg);
    }

    public ConsoleBizException(Integer code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }
}
