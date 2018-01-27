package com.otc.common.api.exceptions;


import com.jucaifu.common.exceptions.BaseException;
import com.jucaifu.common.exceptions.IException;
import com.jucaifu.common.exceptions.general.GeneralExceptionType;
import com.otc.common.api.packet.app.request.AppRequestHeader;

/**
 * AppForceUpdateException
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/22.
 */
public class AppForceUpdateException extends BaseException implements IException {

    private AppRequestHeader header;

    public AppRequestHeader getHeader() {
        return header;
    }

    public void setHeader(AppRequestHeader header) {
        this.header = header;
    }

    public AppForceUpdateException(AppRequestHeader header) {
        this.header = header;
    }

    @Override
    public String getCode() {
        return GeneralExceptionType.APP_FORCE_UPDATE_EXCEPTION.getValue();
    }

    @Override
    public String getMessage() {
        return GeneralExceptionType.APP_FORCE_UPDATE_EXCEPTION.getDesc();
    }

}
