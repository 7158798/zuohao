package com.szzc.api.app.interceptor;

import com.szzc.api.app.utils.AppTokenValidate;
import com.jucaifu.common.annotation.token.TokenValidateAnno;
import com.jucaifu.common.aop.base.AbsBaseAspect;
import com.jucaifu.common.aop.constant.PointOrderConstant;
import com.szzc.common.api.controller.AbsAppController;
import com.szzc.common.api.interceptor.logger.PointExpressionConstant;
import com.szzc.common.api.packet.app.request.AppApiRequest;
import com.jucaifu.common.log.LOG;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;

/**
 * AppTokenCtrlAspect
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/09.
 */
@Order(PointOrderConstant.NO_03)
@Aspect
@Component
public class AppTokenCtrlAspect extends AbsBaseAspect {

    @Override
    public void beforeMethod(JoinPoint joinPoint) {
       // uniformValidate(joinPoint);
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
    @Pointcut(PointExpressionConstant.GLOBAL_RESPONSEBODY)
    public void declareJointPointExpression() {

    }


    private void uniformValidate(JoinPoint joinPoint) {

        ResponseBody responseBodyAnno = null;

        Class targetClass = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        Class[] parameterTypes = null;

        if (arguments != null) {
            int length = arguments.length;
            parameterTypes = new Class[length];

            if (length != 1) {
                return;
            }

            for (int i = 0; i < length; ++i) {
                parameterTypes[i] = arguments[i].getClass();
                LOG.dTag(this, arguments[i].getClass().getName());
            }
        }

        Method method = null;
        try {
            method = targetClass.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
        }

        if (method != null) {
            responseBodyAnno = method.getAnnotation(ResponseBody.class);
            TokenValidateAnno tokenValidateAnno = method.getAnnotation(TokenValidateAnno.class);
            boolean skip = false;
            if (tokenValidateAnno != null) {
                skip = tokenValidateAnno.skip();
            }

            if (!skip) {
                if (responseBodyAnno != null) {
                    Object obj1 = arguments[0];
                    if (obj1 instanceof AppApiRequest) {
                        AppApiRequest req = (AppApiRequest) obj1;
                        AppTokenValidate.validateToken(req);
                    }else if(obj1 instanceof String){
                        String queryJson = (String) obj1;
                        AppApiRequest req = AbsAppController.encodeHexStr2Obj(queryJson, AppApiRequest.class);
                        AppTokenValidate.validateToken(req);
                    }
                }
            }

        }

    }

}
