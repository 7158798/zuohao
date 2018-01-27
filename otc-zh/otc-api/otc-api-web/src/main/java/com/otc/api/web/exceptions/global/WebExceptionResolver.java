package com.otc.api.web.exceptions.global;

import com.alibaba.dubbo.rpc.RpcException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.jucaifu.common.configs.ApiVersionManager;
import com.jucaifu.common.constants.ApiType;
import com.jucaifu.common.exceptions.biz.BizException;
import com.jucaifu.common.exceptions.general.GeneralException;
import com.jucaifu.common.exceptions.general.GeneralExceptionType;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.HttpServletHelper;
import com.otc.common.api.exceptions.TokenInvalidException;
import com.otc.common.api.exceptions.global.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ConsoleExceptionResolver
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/7.
 */
@ControllerAdvice
public class WebExceptionResolver {

    @ExceptionHandler(TokenInvalidException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public WebErrorResponse errorResponse(HttpServletRequest request, HttpServletResponse response, TokenInvalidException ex) {

        return new WebErrorResponse(ex.getCode(), ex.getMessage());
    }


    @ExceptionHandler(RpcException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public WebErrorResponse errorResponse(HttpServletRequest request, HttpServletResponse response, RpcException ex) {

        LOG.e(this, "RpcException", ex);

        return new WebErrorResponse("服务网络异常，异常代码：" + ex.getCode() + "，如有疑问，请联系管理员！");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public WebErrorResponse errorResponse(HttpServletRequest request, HttpServletResponse response, Exception ex) {

        if (ex instanceof MissingServletRequestParameterException || ex instanceof UnrecognizedPropertyException) {
            LOG.dTag(this, "全局异常:请求参数不正确");
            return new WebErrorResponse(GeneralExceptionType.REQUEST_PARAMETER_EXCEPTION, ex);
        } else if (ex instanceof BizException) {
            String errorCode = String.valueOf(((BizException) ex).getCode());
            return new WebErrorResponse(errorCode, ex.getMessage());
        } else {
            LOG.e(this, "运行-未知异常", ex);
            return new WebErrorResponse("服务器异常.");
        }
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public WebErrorResponse errorResponse(HttpServletRequest request, HttpServletResponse response, HttpRequestMethodNotSupportedException ex) {
        if (ApiVersionManager.validateApiVersion(ApiType.WEB, request)) {
            return new WebErrorResponse(GeneralExceptionType.NOT_SUPPORT_REQUEST_METHOD_EXCEPTION, ex);
        } else {
            return new WebErrorResponse(GeneralExceptionType.API_VERSION_INVALID_EXCEPTION, ex);
        }
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public WebErrorResponse errorResponse(HttpServletRequest request, HttpServletResponse response, HttpMediaTypeNotSupportedException ex) {
        LOG.dTag(this, "(日志忽略-主要解决跨越)");
        return new WebErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public WebErrorResponse errorResponse(HttpServletRequest request, HttpServletResponse response, HttpMessageNotReadableException ex) {

        if (HttpServletHelper.isOptionRequest(request)) {
            LOG.dTag(this, "(日志忽略-主要解决跨越)");
            return new WebErrorResponse("Could not read document");
        } else {
            LOG.dTag(this, "(全局异常:请求参数不正确)");
            return new WebErrorResponse(GeneralExceptionType.REQUEST_PARAMETER_EXCEPTION, ex);
        }
    }

    @ExceptionHandler(GeneralException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public WebErrorResponse errorResponse(HttpServletRequest request, HttpServletResponse response, GeneralException ex) {

        return new WebErrorResponse(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleValidation(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException e) {
        LOG.dTag(this, "全局异常:请求参数不正确");

        ObjectError error = e.getBindingResult().getAllErrors().get(0);

        return new ErrorResponse(GeneralExceptionType.REQUEST_PARAMETER_EXCEPTION.getValue(), error.getDefaultMessage());
    }

}
