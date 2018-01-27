package com.otc.common.api.interceptor.logger;

import com.jucaifu.common.aop.base.AbsBaseAspect;
import com.jucaifu.common.aop.constant.PointOrderConstant;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * LogControllerAspect
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/24.
 */
@Order(PointOrderConstant.NO_01)
@Aspect
@Component
public class LogControllerAspect extends AbsBaseAspect {

    @Override
    public boolean needPrintLog() {
        return true;
    }

    @Override
    public void beforeMethod(JoinPoint joinPoint) {

    }

    @Override
    public void afterMethod(JoinPoint joinPoint) {

    }

    @Override
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {

    }

    @Override
    public void afterReturning(JoinPoint joinPoint, Object result) {

    }

    @Override
    @Pointcut(PointExpressionConstant.CONTROLLER_ALL_METHOD)
    public void declareJointPointExpression() {

    }

}
