package com.jucaifu.common.aop.base;

import com.jucaifu.common.log.LOG;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

/**
 * AbsBaseAroundAspect
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/24.
 */
//@Aspect
//@Component
public abstract class AbsBaseAroundAspect {

    private static final String JOINT_POINT_EXPRESSION_METHOD = "declareJointPointExpression()";

    public abstract Object aroundMethod(ProceedingJoinPoint pjd);

    /**
     * 定义一个方法, 用于声明切入点表达式.
     * 一般地, 该方法中再不需要添入其他的代码.
     * 使用 @Pointcut 来声明切入点表达式.
     * 后面的其他通知直接使用方法名来引用当前的切入点表达式.
     */
//    @Pointcut(PointExpressionConstant.CONTROLLER_ALL_METHOD)
    public abstract void declareJointPointExpression();

    public boolean needPrintLog() {
        return false;
    }

    /**
     * 环绕通知需要携带 ProceedingJoinPoint 类型的参数.
     * 环绕通知类似于动态代理的全过程: ProceedingJoinPoint 类型的参数可以决定是否执行目标方法.
     * 且环绕通知必须有返回值, 返回值即为目标方法的返回值
     *
     * @param pjd the pjd
     * @return the object
     */
    @Around(JOINT_POINT_EXPRESSION_METHOD)
    public Object _aroundMethod(ProceedingJoinPoint pjd) {

        Object result = null;
        String methodName = pjd.getSignature().getName();
        Object[] args = pjd.getArgs();

        try {

            //前置通知
            if (needPrintLog()) {

                LOG.dIpTag(this, "The method <" + methodName + "> begins with below args :");
                LOG.d(this, args);
            }

            //执行目标方法
            result = pjd.proceed();
            //返回通知
            if (needPrintLog()) {
                LOG.dIpTag(this, "The method <" + methodName + "> ends with result:");

                LOG.dTag(this, "result begin");
                LOG.d(this, result);
                LOG.dTag(this, "result end");
            }

        } catch (Throwable ex) {

            //异常通知
            if (needPrintLog()) {
                LOG.dIpTag(this, "The method <" + methodName + "> occurs excetion :");

                LOG.dTag(this, "excetion begin");
                LOG.e(this, methodName, ex);
                LOG.dTag(this, "excetion end");
            }

            throw new RuntimeException(ex);
        }

        //后置通知
        if (needPrintLog()) {
            LOG.dIpTag(this, "The method <" + methodName + "> ends");
        }

        return result;
    }

}
