package com.jucaifu.common.exceptions;


import com.jucaifu.common.exceptions.general.GeneralExceptionType;

/**
 * UserNameNotMatchPasswordException
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/7.
 */
public class UserNameNotMatchPasswordException extends BaseException implements IException {

    @Override
    public String getCode() {
        return GeneralExceptionType.UserNameNotMatchPassword.getValue();
    }

    @Override
    public String getMessage() {
        return GeneralExceptionType.UserNameNotMatchPassword.getDesc();
    }
}
