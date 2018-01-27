package com.szzc.common.api.interceptor;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.HttpServletHelper;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CORSInterceptor
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/14.
 */
public class CORSInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        LOG.dTag(this, "Web跨域请求处理");

        HttpServletHelper.logAllRequestHeaders(httpServletRequest);

        HttpServletHelper.addCORSResponseHeader(httpServletResponse);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
