package com.jucaifu.common.aop.base;

import java.util.Arrays;
import java.util.List;

import com.jucaifu.common.log.LOG;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;

/**
 * 说明：可以使用 @Order 注解指定切面的优先级, 值越小优先级越高
 * AbsBaseAspect
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/24.
 */
//@Aspect
//@Component
public abstract class AbsBaseAspect {


    /**
     * Before method.
     *
     * @param joinPoint the join point
     */
    public abstract void beforeMethod(JoinPoint joinPoint);


    /**
     * After throwing.
     *
     * @param joinPoint the join point
     * @param ex        the ex
     */
    public abstract void afterThrowing(JoinPoint joinPoint, Exception ex);

    /**
     * After returning.
     *
     * @param joinPoint the join point
     * @param result    the result
     */
    public abstract void afterReturning(JoinPoint joinPoint, Object result);

    /**
     * After method.
     *
     * @param joinPoint the join point
     */
    public abstract void afterMethod(JoinPoint joinPoint);


    private static final String JOINT_POINT_EXPRESSION_METHOD = "declareJointPointExpression()";

    /**
     * 定义一个方法, 用于声明切入点表达式.
     * 一般地, 该方法中再不需要添入其他的代码.
     * 使用 @Pointcut 来声明切入点表达式.
     * 后面的其他通知直接使用方法名来引用当前的切入点表达式.
     */
//    @Pointcut(PointExpressionConstant.CONTROLLER_ALL_METHOD)
    public abstract void declareJointPointExpression();

    /**
     * Need print log.
     *
     * @return the boolean
     */
    public boolean needPrintLog() {
        return false;
    }

    /**
     * 在每一个方法开始之前执行
     *
     * @param joinPoint the join point
     */
    @Before(JOINT_POINT_EXPRESSION_METHOD)
    private void _beforeMethod(JoinPoint joinPoint) {

        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();

        Object[] args = joinPoint.getArgs();

        if (needPrintLog()) {
            LOG.dIpTag(this, "The method <" + methodName + "> begins with below args :");

            try {
                LOG.d(this, Arrays.asList(args));
            } catch (Exception e) {

                List list = Arrays.asList(args);
                if (list != null) {
                    for (Object obj : list) {
                        System.out.println(obj.toString());
                    }
                }
            }
        }

        //Transfer to subClass
        beforeMethod(joinPoint);
    }


    /**
     * 在方法执行之后执行的代码. 无论该方法是否出现异常
     *
     * @param joinPoint the join point
     */
    @After(JOINT_POINT_EXPRESSION_METHOD)
    public void _afterMethod(JoinPoint joinPoint) {

        String methodName = joinPoint.getSignature().getName();

        if (needPrintLog()) {
            LOG.dIpTag(this, "The method <" + methodName + "> ends");
        }

        //Transfer to subClass
        afterMethod(joinPoint);
    }

    /**
     * 在方法法正常结束后执行的代码
     * 返回通知是可以访问到方法的返回值的!
     *
     * @param joinPoint the join point
     * @param result    the result
     */
    @AfterReturning(value = JOINT_POINT_EXPRESSION_METHOD, returning = "result")
    public void _afterReturning(JoinPoint joinPoint, Object result) {

        String methodName = joinPoint.getSignature().getName();

        if (needPrintLog()) {
            LOG.dIpTag(this, "The method <" + methodName + "> ends with result:");

            LOG.dTag(this, "result begin");
            LOG.d(this, result);
            LOG.dTag(this, "result end");
        }

        //Transfer to subClass
        afterReturning(joinPoint, result);
    }

    /**
     * 在目标方法出现异常时会执行的代码.
     * 可以访问到异常对象;
     * 且可以指定在出现特定异常时在执行通知代码
     *
     * @param joinPoint the join point
     * @param ex        the ex
     */
    @AfterThrowing(value = JOINT_POINT_EXPRESSION_METHOD, throwing = "ex")
    public void _afterThrowing(JoinPoint joinPoint, Exception ex) {

        String methodName = joinPoint.getSignature().getName();

        if (needPrintLog()) {
            LOG.dIpTag(this, "The method <" + methodName + "> occurs excetion :");

            LOG.dTag(this, "excetion begin");
            LOG.e(this,methodName, ex);
            LOG.dTag(this, "excetion end");
        }

        //Transfer to subClass
        afterThrowing(joinPoint, ex);
    }

}
