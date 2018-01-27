package com.szzc.api.app.exceptions.global;

import com.jucaifu.common.exceptions.BaseExceptionInfo;
import com.jucaifu.common.exceptions.general.GeneralExceptionType;
import com.jucaifu.common.log.LOG;

/**
 * AppErrorResponse
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/5.
 */
public class AppErrorResponse extends BaseExceptionInfo {

    public AppErrorResponse(String code, String message) {
        super(code, message);
    }

    public AppErrorResponse(String message) {
        super(message);
    }

    public AppErrorResponse(GeneralExceptionType exceptionType, Exception ex) {
        this(exceptionType.getValue(), exceptionType.getDesc());
        LOG.d(AppErrorResponse.class, "ex", ex);
    }
}
