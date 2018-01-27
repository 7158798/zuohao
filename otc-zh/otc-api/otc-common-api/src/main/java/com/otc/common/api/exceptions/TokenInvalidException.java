package com.otc.common.api.exceptions;


import com.jucaifu.common.exceptions.BaseException;
import com.jucaifu.common.exceptions.IException;
import com.jucaifu.common.exceptions.general.GeneralExceptionType;

/**
 * TokenInvalidException
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/05.
 */
public class TokenInvalidException extends BaseException implements IException {

    @Override
    public String getCode() {
        return GeneralExceptionType.TOKEN_INVALID_EXCEPTION.getValue();
    }

    @Override
    public String getMessage() {
        return GeneralExceptionType.TOKEN_INVALID_EXCEPTION.getDesc();
    }

}
