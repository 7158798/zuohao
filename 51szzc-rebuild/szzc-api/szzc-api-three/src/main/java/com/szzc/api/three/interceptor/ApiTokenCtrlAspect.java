package com.szzc.api.three.interceptor;

import com.jucaifu.common.annotation.token.TokenValidateAnno;
import com.jucaifu.common.aop.base.AbsBaseAspect;
import com.jucaifu.common.aop.constant.PointOrderConstant;
import com.jucaifu.common.log.LOG;
import com.szzc.api.three.utils.ApiValidate;
import com.szzc.common.api.interceptor.logger.PointExpressionConstant;
import com.szzc.common.api.packet.api.request.ApiBaseReq;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

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
public class ApiTokenCtrlAspect extends AbsBaseAspect {

    @Override
    public void beforeMethod(JoinPoint joinPoint) {
        uniformValidate(joinPoint);
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

        RequestMapping responseBodyAnno = null;

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
            responseBodyAnno = method.getAnnotation(RequestMapping.class);

            TokenValidateAnno tokenValidateAnno = method.getAnnotation(TokenValidateAnno.class);
            boolean skip = false;
            if (tokenValidateAnno != null) {
                skip = tokenValidateAnno.skip();
            }

            if (responseBodyAnno != null) {

                Object req = arguments[0];
                System.out.println("test");

//                if (!skip) {
//                    if (req instanceof ApiBaseReq) {
//                        ApiBaseReq reqBean = (ApiBaseReq) req;
//                        ApiValidate.validateToken(reqBean);
//                    } else if (req instanceof String) {
//                        String queryJson = (String) req;
//                        ApiValidate.validateQueryJson(queryJson);
//                    }
//                }
            }
        }

    }

}
