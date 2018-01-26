package com.ruizton.main.aspect;


import com.ruizton.main.Enum.ModuleEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.dao.FsyslogDAO;
import com.ruizton.main.model.Fadmin;
import com.ruizton.main.model.Fsystemoperatorlog;
import com.ruizton.main.service.admin.SysLogService;
import com.ruizton.util.JsonHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.apache.commons.lang.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * SysLogAspect
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/08.
 */
@Order(PointOrderConstant.NO_04)
@Aspect
@Component
public class SysLogAspect extends AbsSysLogAspect{


    @Autowired
    private HttpServletRequest request ;

    @Autowired
    private SysLogService sysLogService;

    @Override
    public void beforeMethod(JoinPoint joinPoint) {
        super.beforeMethod(joinPoint);

    }

    @Override
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        super.afterThrowing(joinPoint, ex);
  //      doSaveLog(joinPoint, null, ex);
    }

    @Override
    public void afterReturning(JoinPoint joinPoint, Object result) {
        super.afterReturning(joinPoint, result);

        doSaveLog(joinPoint, result, null);
    }

    public static void doSaveLog() {
        SysLogAspect sysLogAspect = ApplicationContextHelper.getInstance().getBean(SysLogAspect.class);
        if (sysLogAspect!=null){
//            sysLogAspect.doSaveLog();
        }
    }

    /**
     * Do save log.
     *
     * @param joinPoint the join point
     * @param result    the result
     * @param ex        the ex
     */
    public void doSaveLog(JoinPoint joinPoint, Object result, Exception ex) {

    //    LOG.dTag(this,"doSaveLog");

        String requestIp = BaseController.getIpAddr(request);
        Class targetClass = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        String className = targetClass.getName();

        String requestParameters = "";
        Long uid = 0l;
        String loginName = "";

        Fadmin admin = (Fadmin)request.getSession().getAttribute("login_admin");

        if (arguments != null && arguments.length != 0) {
            for(Object obj : arguments){
                if (obj instanceof HttpServletRequest || obj instanceof ShiroHttpServletRequest) {
                    continue;
                } else if (obj instanceof String || obj instanceof Long) {
                    requestParameters += obj.toString() + ",";
                } else if(obj instanceof CommonsMultipartFile) { //如果是文件上传，则不需要byte，否则16进制内容太长，而且没有含义，直接存储名称即可
                    CommonsMultipartFile file = (CommonsMultipartFile) obj;
                    requestParameters += "{fileName="+file.getFileItem().getName()+"},";
                }else {
                    requestParameters += JsonHelper.obj2JsonStr(obj)+",";
                }
            }
        }
        SysLog sysLog = getSysLogAnno(joinPoint);
        String code = sysLog.code();

        Fsystemoperatorlog po = new Fsystemoperatorlog();

        po.setOperatorDate(new Date());
        po.setUserId(admin.getFid());
        po.setLoginName(admin.getFname());
        po.setModule(code);
        po.setMethodName(methodName);
        po.setIp(requestIp);
        po.setModule(ModuleEnum.getMap().get(code));

        po.setOperatorCode(code);
        po.setOperatorName(sysLog.method());
        po.setClassName(className);
        po.setMethodName(methodName);

        String resultInfo = "成功";
        Boolean isSucess = Boolean.TRUE;
        if (ex != null) {
            po.setIssuccess(false);
            resultInfo = "失败！";
            isSucess = Boolean.FALSE;
        } else {
            //如果是ajax异步请求，并且返回的是CommonResult
            if (result instanceof ModelAndView) {
                ModelAndView commonResult = (ModelAndView) result;
                ModelMap s = commonResult.getModelMap();

                Object statusCode = s.get("statusCode");
                Object message = s.get("message");
                if (message != null ) {
                    resultInfo = message.toString();
                }

                if(statusCode != null && (Integer)statusCode != 200){
                    isSucess = Boolean.FALSE;
                }
            } else {
                resultInfo = "成功！";
                po.setIssuccess(true);
            }
        }
        po.setResponseResult(resultInfo);

        String operatorContent = "用户:" + loginName + "执行(" + sysLog.method() + ")操作 " + resultInfo;
        po.setOperatorContent(operatorContent);

        //参数不为空，拼接后的最后一个是逗号
        if (StringUtils.isNotEmpty(requestParameters) && requestParameters.substring(requestParameters.length() - 1).equals(",")) {
            requestParameters = requestParameters.substring(0, requestParameters.length() - 1);
        }

        po.setRequestParameters(requestParameters);
        po.setIssuccess(isSucess);

        sysLogService.saveObj(po);
    }

}
