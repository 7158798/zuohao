package com.otc.api.web.exceptions;

import com.jucaifu.common.exceptions.biz.BizException;

/**
 * ProductBizException
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/18.
 */
public class WebBizException extends BizException {

    public static final WebBizException QR_LOGIN_TIMEOUT = new WebBizException(410, "登陆超时，请重新扫描二维码");


    public WebBizException(String msg) {
        super(msg);
    }

    public WebBizException(Integer code, String msg) {
        super(code, msg);
    }

    public WebBizException(Integer code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }
}
