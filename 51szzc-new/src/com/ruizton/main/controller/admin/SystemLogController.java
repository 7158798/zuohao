package com.ruizton.main.controller.admin;

import com.ruizton.main.Enum.ModuleEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.dao.FsyslogDAO;
import com.ruizton.main.model.Fcountlimit;
import com.ruizton.main.model.Fsystemoperatorlog;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.SysLogService;
import com.ruizton.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by a123 on 17-2-8.
 */
@Controller
public class SystemLogController extends BaseController{
    //每页显示多少条数据
    private int numPerPage = Utils.getNumPerPage();



    @RequestMapping("/ssadmin/sysOperatorlog")
    public ModelAndView Index() throws Exception{
        ModelAndView modelAndView = new ModelAndView() ;
        modelAndView.setViewName("ssadmin/systemOperatorlog") ;
        //当前页
        int currentPage = 1;
        //搜索关键字
        String keyWord = request.getParameter("keywords");
        String orderField = request.getParameter("orderField");
        String orderDirection = request.getParameter("orderDirection");
        String model = request.getParameter("model");
        if(request.getParameter("pageNum") != null){
            currentPage = Integer.parseInt(request.getParameter("pageNum"));
        }
        StringBuffer filter = new StringBuffer();
        filter.append("where 1=1 \n");
     //   filter.append("and (unix_timestamp(now())-unix_timestamp(fcreatetime)) <2*60*60 \n");
        if(keyWord != null && keyWord.trim().length() >0){
            filter.append("and ip like '%"+keyWord+"%' \n");
            modelAndView.addObject("keywords", keyWord);
        }
        if(StringUtils.isNotEmpty(model) && !model.equals("全部")){
            filter.append("and module like '%"+model+"%' ");
            modelAndView.addObject("model",model);
        }

        if(orderField != null && orderField.trim().length() >0){
            filter.append("order by "+orderField+"\n");
        }else{
            filter.append("order by id \n");
        }

        if(orderDirection != null && orderDirection.trim().length() >0){
            filter.append(orderDirection+"\n");
        }else{
            filter.append("desc \n");
        }

        List<Fsystemoperatorlog> list = this.sysLogService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);

        List<String> models = new ArrayList<String>();
        models.add("全部");
        models.addAll(ModuleEnum.getAllDesc());



        modelAndView.addObject("syslogList", list);
        modelAndView.addObject("numPerPage", numPerPage);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("rel", "syslogList");
        modelAndView.addObject("models",models);
        //总数量
        modelAndView.addObject("totalCount",this.adminService.getAllCount("Fsystemoperatorlog", filter+""));
        return modelAndView ;
    }
}
