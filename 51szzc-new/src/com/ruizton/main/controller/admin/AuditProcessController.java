package com.ruizton.main.controller.admin;

import com.ruizton.main.Enum.IntegralTypeEnum;
import com.ruizton.main.Enum.ModuleConstont;
import com.ruizton.main.aspect.SysLog;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fadmin;
import com.ruizton.main.model.Fauditprocess;
import com.ruizton.main.model.Frole;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.integral.Fusergrade;
import com.ruizton.main.model.integral.Fuserintegraldetail;
import com.ruizton.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fenggq on 17-2-22.
 */
@Controller
public class AuditProcessController extends BaseController {

    private int numPerPage = Utils.getNumPerPage();

    @RequestMapping("ssadmin/goAuditProcessJSP")
    public ModelAndView goUserJSP() throws Exception {
        String url = request.getParameter("url");
        int fid = Integer.parseInt(request.getParameter("uid"));

        Fauditprocess fauditprocess = this.auditProcessService.findById(fid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(url);
        List<Frole> roleList = this.roleService.findAll();
        Map map = new HashMap();
        for (Frole frole : roleList) {
            map.put(frole.getFid(),frole.getFname());
        }
        modelAndView.addObject("fauditprocess",fauditprocess);
        modelAndView.addObject("roleMap",map);
        return modelAndView;
    }

    /**
     * 修改审核流程配置
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping("ssadmin/updateAuditProcess")
    public ModelAndView updateGradeSet(Fauditprocess req) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ssadmin/comm/ajaxDone");

        String role1 = request.getParameter("role1");
        String role2 = request.getParameter("role2");
        String role3 = request.getParameter("role3");

        int fid = Integer.parseInt(request.getParameter("uid"));
        Fauditprocess fauditprocess = this.auditProcessService.findById(fid);

        Frole frole1 = null;
        Frole frole2 = null;
        Frole frole3 = null;


        if(StringUtils.isNotEmpty(role1)){
            frole1 = this.roleService.findById(Integer.parseInt(role1));
        }
        if(StringUtils.isNotEmpty(role2)){
            frole2 = this.roleService.findById(Integer.parseInt(role2));
        }
        if(StringUtils.isNotEmpty(role1)){
            frole3 = this.roleService.findById(Integer.parseInt(role3));
        }

        if(frole1 == null ){
            modelAndView.addObject("statusCode", -1);
            modelAndView.addObject("message", "一级审核不能为空");
            return modelAndView;
        }

        if(frole2 == null && frole3 != null){
            modelAndView.addObject("statusCode", -1);
            modelAndView.addObject("message", "二级审核不能为空");
            return modelAndView;
        }

        int isneedPassword = Integer.parseInt(request.getParameter("fIsneedPwd"));

        fauditprocess.setFrole1(frole1);
        fauditprocess.setFrole2(frole2);
        fauditprocess.setFrole3(frole3);
        fauditprocess.setfIsneedPwd(isneedPassword);

        fauditprocess.setfLastUpdateTime(Utils.getTimestamp());

        Fadmin admin = (Fadmin)request.getSession().getAttribute("login_admin");
        fauditprocess.setFmodify_id(admin);

        this.auditProcessService.updateObj(fauditprocess);

        modelAndView.addObject("statusCode", 200);
        modelAndView.addObject("callbackType", "closeCurrent");
        modelAndView.addObject("message", "修改成功");
        return modelAndView;
    }


    /**
     * 审核流程配置列表
     * @return
     * @throws Exception
     */
    @RequestMapping("/ssadmin/auditProcessList")
    public ModelAndView Index() throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/auditProcessList") ;
        int currentPage = 1;
        String orderField = request.getParameter("orderField");
        String orderDirection = request.getParameter("orderDirection");
        if(request.getParameter("pageNum") != null){
            currentPage = Integer.parseInt(request.getParameter("pageNum"));
        }
        StringBuffer filter = new StringBuffer();
        filter.append("where 1=1 \n");

        if(orderField != null && orderField.trim().length() >0){
            filter.append("order by "+orderField+"\n");
        }else{
            filter.append("order by fid \n");
        }
        if(orderDirection != null && orderDirection.trim().length() >0){
            filter.append(orderDirection+"\n");
        }else{
            filter.append("asc \n");
        }
        List<Fauditprocess> list = this.auditProcessService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);


        modelAndView.addObject("auditProcessList", list);
        modelAndView.addObject("numPerPage", numPerPage);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("rel", "aboutList");
        modelAndView.addObject("totalCount",this.adminService.getAllCount("Fauditprocess", filter+""));
        return modelAndView ;
    }


}
