package com.jucaifu.common.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.jucaifu.common.util.ReflectHelper;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.support.AopUtils;

/**
 * AopHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/24.
 */
public class AopHelper {


    /**
     * 获取目标对象
     *
     * @param proxy the proxy
     * @return the target
     * @throws Exception the exception
     */
    public static Object getTarget(Object proxy) throws Exception {

        if (!AopUtils.isAopProxy(proxy)) {
            return proxy;//不是代理对象
        }

        if (AopUtils.isJdkDynamicProxy(proxy)) {

            return getJdkDynamicProxyTargetObject(proxy);

        } else {
            //cglib
            return getCglibProxyTargetObject(proxy);
        }
    }


    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Object dynamicAdvisedInterceptor = ReflectHelper.getValueByFieldName(proxy, "CGLIB$CALLBACK_0");
        Object target = ((AdvisedSupport) ReflectHelper.getValueByFieldName(dynamicAdvisedInterceptor, "advised")).getTargetSource().getTarget();
        return target;

    }


    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(proxy);
        Object target = ((AdvisedSupport) ReflectHelper.getValueByFieldName(invocationHandler, "advised")).getTargetSource().getTarget();
        return target;
    }

}
