package com.otc.common.api.exceptions;


import com.jucaifu.common.exceptions.BaseException;
import com.jucaifu.common.exceptions.IException;
import com.jucaifu.common.exceptions.general.GeneralExceptionType;

/**
 * BuildAppResponseException
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/22.
 */
public class BuildAppResponseException extends BaseException implements IException {

    public BuildAppResponseException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getCode() {
        return GeneralExceptionType.BUILD_APP_RESPONSE_EXCEPTION.getValue();
    }

    @Override
    public String getMessage() {
        return GeneralExceptionType.BUILD_APP_RESPONSE_EXCEPTION.getDesc();
    }

}
