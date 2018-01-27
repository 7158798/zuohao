package com.otc.api.console.exceptions.global;

import com.jucaifu.common.exceptions.BaseExceptionInfo;
import com.jucaifu.common.exceptions.general.GeneralExceptionType;
import com.jucaifu.common.log.LOG;

/**
 * ConsoleErrorResponse
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/7.
 */
public class ConsoleErrorResponse extends BaseExceptionInfo {

    public ConsoleErrorResponse(String code, String message) {
        super(code, message);
    }

    public ConsoleErrorResponse(String message) {
        super(message);
    }

    public ConsoleErrorResponse(GeneralExceptionType exceptionType, Exception ex) {
        this(exceptionType.getValue(), exceptionType.getDesc());
        LOG.d(ConsoleErrorResponse.class, "ex", ex);
    }
}
