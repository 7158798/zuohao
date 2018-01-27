package com.szzc.api.three.interceptor;

import com.facade.core.wallet.cache.CacheHelper;
import com.jucaifu.common.annotation.token.TokenValidateAnno;
import com.jucaifu.common.aop.base.AbsBaseAspect;
import com.jucaifu.common.aop.constant.PointOrderConstant;
import com.jucaifu.common.context.SpringPropReaderHelper;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.DateHelper;
import com.jucaifu.common.util.IpHelper;
import com.szzc.api.three.annotation.ApiValidateAnno;
import com.szzc.api.three.exceptions.ThreeBizException;
import com.szzc.api.three.pojo.BaseApiReq;
import com.szzc.common.api.interceptor.logger.PointExpressionConstant;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.reflect.Method;
import java.util.*;

/**
 * AppTokenCtrlAspect
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/09.
 */
@Order(PointOrderConstant.NO_03)
@Aspect
@Component
public class ApiSafeCtrlAspect extends AbsBaseAspect {

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
    @Pointcut(PointExpressionConstant.GLOBAL_APIVALIDATEANNO)
    public void declareJointPointExpression() {

    }


    private void uniformValidate(JoinPoint joinPoint) {


        //ThreadInfo[] threadInfos = ManagementFactory.getThreadMXBean().dumpAllThreads(true, true);
        /*Map<Thread, StackTraceElement[]> threadInfos = Thread.getAllStackTraces();
        LOG.i(this,"线程的数量 " + threadInfos.size());*/

        String key = "count-thread-key-" + UUID.randomUUID();
        Date date = new Date();
        //LOG.dStart(this,"开始时间" + DateHelper.date2String(date, DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond_Custom));
        Set<byte[]> list = CacheHelper.keys("count-thread-key-");
        Date end = new Date();
        //LOG.dEnd(this, "结束时间" + DateHelper.date2String(end, DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond_Custom));
        if (list != null && list.size() > 0){
            Integer maxThread = getMaxThread();
            List<byte[]> rList = new ArrayList<>();
            Iterator<byte[]> it = list.iterator();
            while (it.hasNext()) {
                rList.add(it.next());
            }
            if (rList != null && rList.size() >= maxThread.intValue()){
                throw new ThreeBizException("系统太繁忙");
            }
        }
        CacheHelper.saveObj(key,key,1);
        ApiValidateAnno apiValidateAnno;

        Class targetClass = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        Class[] parameterTypes = null;

        if (arguments != null) {
            int length = arguments.length;
            parameterTypes = new Class[length];
            /*if (length != 1) {
                return;
            }*/

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
            apiValidateAnno = method.getAnnotation(ApiValidateAnno.class);
            if (apiValidateAnno != null){
                boolean skip = apiValidateAnno.skip();
                if (skip){
                    int time = apiValidateAnno.time();
                    String ipAddress = IpHelper.getRequestIp();
                    LOG.d(this,"请求的ip=" + ipAddress);
                    filter(ipAddress + method.getName(), time);
                    Object req = arguments[0];
                    if (req instanceof BaseApiReq){
                        BaseApiReq apiReq = (BaseApiReq) req;
                        String api_key = apiReq.getApi_key();
                        LOG.d(this,"请求的api_key=" + api_key);
                        if (StringUtils.isNotEmpty(api_key)){
                            filter(api_key + method.getName(), time);
                        }
                    }
                }
            }
        }
    }

    public void filter(String key,int time){
        String value = CacheHelper.getObj(key);
        if (StringUtils.isNotEmpty(value)){
            throw new ThreeBizException("请求太频繁");
        } else {
            CacheHelper.saveObj(key,key,time);
        }
    }


    public int getMaxThread(){
        return Integer.parseInt(SpringPropReaderHelper.getProperty("max_thread_num"));
    }

}
