package com.ruizton.main.controller.admin;

import com.ruizton.main.auto.TestAutoTask;
import com.ruizton.main.auto.TestAutoTask2;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.service.front.FtradeMappingService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lx on 17-1-17.
 */
@Controller
public class TestTaksController extends BaseController {

    @Autowired
    public TestAutoTask testAutoTask;
    @Autowired
    public FtradeMappingService ftradeMappingService;
    @Autowired
    private HttpServletRequest request ;

    @RequestMapping("ssadmin/testTask")
    public ModelAndView testTask() throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
        int fid = Integer.parseInt(request.getParameter("uid"));
        fid = fid == 1?fid:fid -1;
        Ftrademapping ftrademapping = ftradeMappingService.findFtrademapping2(fid);
        String status = request.getParameter("status");
        if (ftrademapping == null){
            modelAndView.addObject("message","货币没有找到");
            modelAndView.addObject("statusCode",300);
        } else {
            if ("1".equals(status)){
                String bUid = request.getParameter("bUid");
                if (StringUtils.isNotEmpty(bUid)){
                    String sUid = request.getParameter("sUid");
                    if (StringUtils.isNotEmpty(sUid)){
                        int[] ids = {Integer.parseInt(bUid),Integer.parseInt(sUid)};
                        //testAutoTask.start(ftrademapping);
                        modelAndView.addObject("message","启动成功");
                    } else {
                        modelAndView.addObject("message","51卖单id不能为空");
                    }
                } else {
                    modelAndView.addObject("message","51买单id不能为空");
                }
            } else {
                //testAutoTask.stop(ftrademapping);
                modelAndView.addObject("message","停止成功");
            }
            modelAndView.addObject("statusCode",200);
        }
        return modelAndView;
    }

    /**
     * 端行提醒
     * @return
     * @throws Exception
     */
    @RequestMapping("ssadmin/message")
    public ModelAndView message() throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
        int fid = Integer.parseInt(request.getParameter("uid"));
        fid = fid == 1?fid:fid -1;
        Ftrademapping ftrademapping = ftradeMappingService.findFtrademapping2(fid);
        String status = request.getParameter("status");
        if (ftrademapping == null){
            modelAndView.addObject("message","货币没有找到");
            modelAndView.addObject("statusCode",300);
        } else {
            if ("1".equals(status)){
                String mobilePhone = request.getParameter("mobilePhone");
                if (StringUtils.isNotEmpty(mobilePhone)){
                    testAutoTask.openMessage(ftrademapping,mobilePhone);
                    modelAndView.addObject("message","启动短信通知成功");
                } else {
                    modelAndView.addObject("message","手机号码不能为空");
                }
            } else {
                testAutoTask.stopMessage(ftrademapping);
                modelAndView.addObject("message","停止短信通知成功");
            }
            modelAndView.addObject("statusCode",200);
        }
        return modelAndView;
    }
}
