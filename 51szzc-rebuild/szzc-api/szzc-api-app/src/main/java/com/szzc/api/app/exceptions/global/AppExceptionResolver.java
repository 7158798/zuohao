package com.szzc.api.app.exceptions.global;

import com.alibaba.dubbo.rpc.RpcException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.szzc.common.api.exceptions.AppForceUpdateException;
import com.szzc.common.api.exceptions.TokenInvalidException;
import com.szzc.common.api.exceptions.global.ErrorResponse;
import com.szzc.common.api.packet.app.response.AppApiResponse;
import com.jucaifu.common.configs.ApiVersionManager;
import com.jucaifu.common.constants.ApiType;
import com.jucaifu.common.exceptions.biz.BizException;
import com.jucaifu.common.exceptions.general.GeneralException;
import com.jucaifu.common.exceptions.general.GeneralExceptionType;
import com.jucaifu.common.log.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AppExceptionResolver
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/5.
 */
@ControllerAdvice
public class AppExceptionResolver {

    //@Autowired
    //private AppMobileVersionCtrl appMobileVersionCtrl;


    @ExceptionHandler(RpcException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AppErrorResponse errorResponse(HttpServletRequest request, HttpServletResponse response, RpcException ex) {

        LOG.e(this, "RpcException", ex);

        return new AppErrorResponse("服务网络异常，异常代码：" + ex.getCode() + "，如有疑问，请联系管理员！");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AppErrorResponse errorResponse(HttpServletRequest request, HttpServletResponse response, Exception ex) {

        if (ex instanceof MissingServletRequestParameterException || ex instanceof UnrecognizedPropertyException) {
            return new AppErrorResponse(GeneralExceptionType.REQUEST_PARAMETER_EXCEPTION, ex);
        } else if (ex instanceof BizException) {
            String errorCode = String.valueOf(((BizException) ex).getCode());
            return new AppErrorResponse(errorCode, ex.getMessage());
        } else {
            LOG.e(this, "未知异常-App-Api", ex);
            return new AppErrorResponse("服务器异常.");
        }
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AppErrorResponse errorResponse(HttpServletRequest request, HttpServletResponse response, HttpRequestMethodNotSupportedException ex) {
        if (ApiVersionManager.validateApiVersion(ApiType.APP, request)) {
            return new AppErrorResponse(GeneralExceptionType.NOT_SUPPORT_REQUEST_METHOD_EXCEPTION, ex);
        } else {
            return new AppErrorResponse(GeneralExceptionType.API_VERSION_INVALID_EXCEPTION, ex);
        }
    }


    @ExceptionHandler(GeneralException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ErrorResponse errorResponse(HttpServletRequest request, HttpServletResponse response, GeneralException ex) {
        return new ErrorResponse(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(TokenInvalidException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse buildErrorResponse(HttpServletRequest request, HttpServletResponse response, TokenInvalidException ex) {
        return new ErrorResponse(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(AppForceUpdateException.class)
    @ResponseStatus(HttpStatus.HTTP_VERSION_NOT_SUPPORTED)
    @ResponseBody
    public AppApiResponse buildForceUpdateErrorResponse(HttpServletRequest request, HttpServletResponse response, AppForceUpdateException ex) {
        //return appMobileVersionCtrl.buildVersionAppApiResponse(ex.getHeader());
        return null;
    }
}
