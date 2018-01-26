package com.ruizton.main.controller.admin;

import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fadmin;
import com.ruizton.main.model.Farticletype;
import com.ruizton.main.model.FhostRecommended;
import com.ruizton.util.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by zygong on 17-3-6.
 */
@Controller
public class HostRecommendedController extends BaseController{

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");

    @RequestMapping("ssadmin/hostRecommendedList")
    public ModelAndView list(){
        ModelAndView mv = new ModelAndView();

        List<FhostRecommended> all = this.hostRecommendedService.findAll();

        mv.setViewName("/ssadmin/hostRecommendedList");
        mv.addObject("hostRecommended", all);
        mv.addObject("totalCount", all.size());
        mv.addObject("rel", "hostRecommendedList");

        return mv;
    }

    @RequestMapping("ssadmin/gohostRecommendedJSP")
    public ModelAndView gohostRecommendedJSP() throws Exception{
        String url = request.getParameter("url");
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName(url) ;

        List<FhostRecommended> all = this.hostRecommendedService.findAll();

        modelAndView.addObject("hostNumber", all.get(0).getfNumber());
        modelAndView.addObject("hostNumberId", all.get(0).getFid());
        modelAndView.addObject("singleNumber", all.get(1).getfNumber());
        modelAndView.addObject("singleNumberId", all.get(1).getFid());

        return modelAndView;
    }



    @RequestMapping("ssadmin/updateHostRecommended")
    public ModelAndView updateHostRecommended(int hostNumber, int singleNumber){
        String dateString = sdf.format(new java.util.Date());
        final int hostNumberId = Integer.parseInt(request.getParameter("hostNumberId") != null ? request.getParameter("hostNumberId") : "1");
        final int singleNumberId = Integer.parseInt(request.getParameter("singleNumberId") != null ? request.getParameter("singleNumberId") : "2");
        Timestamp tm = Timestamp.valueOf(dateString);
        Fadmin fadmin = (Fadmin)request.getSession().getAttribute("login_admin");

        List<FhostRecommended> all = this.hostRecommendedService.findAll();

        //更新内存中的值
        Constant.setHostNumber(hostNumber);
        Constant.setSingleNumber(singleNumber);

        for(FhostRecommended host : all){
            if(host.getFid() == hostNumberId){
                host.setfNumber(hostNumber);
            }else if(host.getFid() == singleNumberId){
                host.setfNumber(singleNumber);
            }
            host.setfUpdateUser(fadmin);
            host.setfUpdateTime(tm);
            this.hostRecommendedService.updateObj(host);
        }

        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
        modelAndView.addObject("statusCode",200);
        modelAndView.addObject("message","修改成功");
        modelAndView.addObject("callbackType","closeCurrent");
        return modelAndView;
    }
}
