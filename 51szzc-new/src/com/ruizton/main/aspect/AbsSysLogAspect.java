package com.ruizton.main.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Method;

/**
 * AbsSysLogAspect
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/12.
 */
//@Order(PointOrderConstant.NO_03)
//@Aspect
//@Component
public abstract class AbsSysLogAspect extends AbsBaseAspect {



    @Override
    public void beforeMethod(JoinPoint joinPoint) {

      //  LOG.d(this, "SysLogAspect---->beforeMethod");
        printSysLogAnnoLog(joinPoint);
    }

    @Override
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
     //   LOG.d(this, "SysLogAspect---->afterThrowing");
        printSysLogAnnoLog(joinPoint);
    }

    @Override
    public void afterReturning(JoinPoint joinPoint, Object result) {
      //  LOG.d(this, "SysLogAspect---->afterReturning");
        printSysLogAnnoLog(joinPoint);
    }

    @Override
    public void afterMethod(JoinPoint joinPoint) {
      //  LOG.d(this, "SysLogAspect---->afterMethod");
        printSysLogAnnoLog(joinPoint);
    }

    @Override
    @Pointcut(PointExpressionConstant.GLOBAL_SYS_LOG)
    public void declareJointPointExpression() {

    }

    private SysLog getSysLogAnnoWithException(JoinPoint joinPoint) throws ClassNotFoundException, NoSuchMethodException {

        SysLog sysLogAnno = null;

        Class targetClass = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        Class[] parameterTypes = null;

        if (arguments != null) {
            int length = arguments.length;
            parameterTypes = new Class[length];

            for (int i = 0; i < length; ++i) {
                parameterTypes[i] = arguments[i].getClass();
            }
        }

        Method method = targetClass.getMethod(methodName, parameterTypes);
        //String

        if (method != null) {
            sysLogAnno = method.getAnnotation(SysLog.class);
        }

        return sysLogAnno;
    }

    /**
     * Gets method sys log anno.
     *
     * @param joinPoint the join point
     * @return the method sys log anno
     */
    public SysLog getSysLogAnno(JoinPoint joinPoint) {

        SysLog sysLogAnno = null;

        try {
            sysLogAnno = getSysLogAnnoWithException(joinPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sysLogAnno;
    }

    public void printSysLogAnnoLog(JoinPoint joinPoint) {

       // SysLog sysLogAnno = getSysLogAnno(joinPoint);

//        if (sysLogAnno != null) {
//            LOG.d(this, getSysLogAnno(joinPoint).code());
//        }
    }

}
