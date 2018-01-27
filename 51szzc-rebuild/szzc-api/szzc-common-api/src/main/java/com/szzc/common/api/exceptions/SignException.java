package com.szzc.common.api.exceptions;

import com.jucaifu.common.exceptions.BaseException;
import com.jucaifu.common.exceptions.IException;
import com.jucaifu.common.exceptions.general.GeneralExceptionType;

/**
 * SignException
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/21.
 */
public class SignException extends BaseException implements IException {

    @Override
    public String getCode() {
        return GeneralExceptionType.SIGN_EXCEPTION.getValue();
    }

    @Override
    public String getMessage() {
        return GeneralExceptionType.SIGN_EXCEPTION.getDesc();
    }
}
