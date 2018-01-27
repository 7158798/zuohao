package com.szzc.common.api.exceptions;


import com.jucaifu.common.exceptions.BaseException;
import com.jucaifu.common.exceptions.IException;
import com.jucaifu.common.exceptions.general.GeneralExceptionType;

/**
 * ParseAppRequestException
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/22.
 */
public class ParseAppRequestException extends BaseException implements IException {

    @Override
    public String getCode() {
        return GeneralExceptionType.PARSE_APP_REQUEST_EXCEPTION.getValue();
    }

    @Override
    public String getMessage() {
        return GeneralExceptionType.PARSE_APP_REQUEST_EXCEPTION.getDesc();
    }
}
