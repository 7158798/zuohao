package com.otc.api.web.exceptions.global;

import com.jucaifu.common.exceptions.BaseExceptionInfo;
import com.jucaifu.common.exceptions.general.GeneralExceptionType;
import com.jucaifu.common.log.LOG;

/**
 * ConsoleErrorResponse
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/15.
 */
public class WebErrorResponse extends BaseExceptionInfo {

    public WebErrorResponse(String code, String message) {
        super(code, message);
    }

    public WebErrorResponse(String message) {
        super(message);
    }

    public WebErrorResponse(GeneralExceptionType exceptionType, Exception ex) {
        this(exceptionType.getValue(), exceptionType.getDesc());
        LOG.d(WebErrorResponse.class, "ex", ex);
    }
}
