package com.szzc.common.api.interceptor.logger;

import com.jucaifu.common.aop.base.AbsBaseAspect;
import com.jucaifu.common.aop.constant.PointOrderConstant;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.HttpServletHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * LogControllerAspect
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/24.
 */
@Order(PointOrderConstant.NO_02)
@Aspect
@Component
public class GlobalExceptionAspect extends AbsBaseAspect {

    public static boolean sNeedPrintExceptionLog = true;

    @Override
    public boolean needPrintLog() {
        return false;
    }

    @Override
    public void beforeMethod(JoinPoint joinPoint) {

    }

    @Override
    public void afterMethod(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        if (args != null && args.length == 3) {
            HttpServletRequest request = (HttpServletRequest) args[0];
            HttpServletResponse response = (HttpServletResponse) args[1];
            Exception ex = (Exception) args[2];


            HttpServletHelper.logAllRequestHeaders(request);
            HttpServletHelper.addCORSResponseHeader(response);

            if (sNeedPrintExceptionLog) {
                LOG.dIpTag(this, "ExceptionLog");
                LOG.e(this, "ExceptionInfo", ex);
            }

//            response.addHeader("testAgent","MacBook-scofieldcai");
        }
    }


    @Override
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {

    }

    @Override
    public void afterReturning(JoinPoint joinPoint, Object result) {

    }

    @Override
    @Pointcut(PointExpressionConstant.GLOBAL_EXCEPTION_ALL_METHOD)
    public void declareJointPointExpression() {

    }

}
