package com.jucaifu.common.exceptions;

import com.jucaifu.common.constants.WebConstant;
import org.springframework.http.HttpStatus;

/**
 * RefuseException
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/21.
 */
public class RefuseException extends BaseException implements IException {

    @Override
    public String getCode() {
        return HttpStatus.UNAUTHORIZED + "";
    }

    @Override
    public String getMessage() {
        return WebConstant.REFUSE;
    }
}
