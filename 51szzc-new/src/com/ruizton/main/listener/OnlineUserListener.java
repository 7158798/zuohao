package com.ruizton.main.listener;

import com.alibaba.fastjson.JSON;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fadmin;
import com.ruizton.main.model.Flog;
import com.ruizton.main.service.admin.LogService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.web.session.mgt.DefaultWebSessionContext;
import org.apache.shiro.web.subject.support.DefaultWebSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zygong on 17-3-13.
 */
public class OnlineUserListener implements HttpSessionListener {
    private Map<Integer, Long> map;

    public OnlineUserListener() {
        super();
    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        LOG.d(this, "===================sessionId: " + httpSessionEvent.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        // 获取service
        LogService logService = WebApplicationContextUtils.getWebApplicationContext(httpSessionEvent.getSession().getServletContext()).getBean(LogService.class);
        List<Flog> flogs = logService.findByPropertyByKey4((String) httpSessionEvent.getSession().getId());
        if(null != flogs && flogs.size() > 0){
            Flog log = flogs.get(0);
            long lastAccessedTime = httpSessionEvent.getSession().getLastAccessedTime();
            long mmm = lastAccessedTime - log.getFcreateTime().getTime();
            long s = mmm / 1000;
            long mm = s / 60;
            long ss = s % 60;
            String mm_str = "";
            if(mm > 0 && ss > 0) {
                mm_str = mm + "分"+ss+"秒";
            }else if(mm>0 && ss == 0) {
                mm_str = mm + "分钟";
            }else if(mm==0 && ss>0) {
                mm_str = ss +"秒";
            }

            log.setFkey5(mm_str);
            logService.updateObj(log);
        }

    }
}
