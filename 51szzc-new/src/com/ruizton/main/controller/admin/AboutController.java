package com.ruizton.main.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ruizton.main.Enum.ModuleConstont;
import com.ruizton.main.aspect.SysLog;
import com.ruizton.main.model.Fvalidatemessage;
import com.ruizton.main.service.admin.ValidatemessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fabout;
import com.ruizton.main.service.admin.AboutService;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.util.Utils;

@Controller
public class AboutController extends BaseController {

	@Autowired
	private ConstantMap map;
	private int numPerPage = Utils.getNumPerPage();
	@Autowired
	private ValidatemessageService validatemessageService;

	
	@RequestMapping("/ssadmin/aboutList")
	public ModelAndView Index() throws Exception{


		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/aboutList") ;
		int currentPage = 1;
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and ftitle like '%"+keyWord+"%' \n");
			modelAndView.addObject("keywords", keyWord);
		}
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by ftype \n");
		}
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		List<Fabout> list = this.aboutService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("aboutList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "aboutList");
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fabout", filter+""));
		return modelAndView ;
	}

	@RequestMapping("/ssadmin/validatemessageList")
	public ModelAndView validatemessageList() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/validatemessageList") ;
		int currentPage = 1;
		String keyWord = request.getParameter("keywords");
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
			filter.append("desc \n");
		}
		List<Fvalidatemessage> list = this.validatemessageService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("validatemessageList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "aboutList");
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fvalidatemessage", filter+""));
		return modelAndView ;
	}


	@RequestMapping("ssadmin/goAboutJSP")
	public ModelAndView goAboutJSP() throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fabout about = this.aboutService.findById(fid);
			modelAndView.addObject("fabout", about);
		}
		
		String[] args = this.map.get("helpType").toString().split("#");
		Map<String,String> type = new HashMap<String,String>();
		for (int i = 0; i < args.length; i++) {
			type.put(args[i], args[i]);
		}
		modelAndView.addObject("type", type);
		
		return modelAndView;
	}

	@RequestMapping("ssadmin/updateAbout")
	@SysLog(code = ModuleConstont.SYSTEM_OPERATION, method = "修改帮助文档")
	public ModelAndView updateAbout() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
        int fid = Integer.parseInt(request.getParameter("fid"));
        Fabout about = this.aboutService.findById(fid);
        about.setFcontent(request.getParameter("fcontent"));
        about.setFtitle(request.getParameter("ftitle"));
        about.setFtype(request.getParameter("ftype"));
        this.aboutService.updateObj(about);
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","修改成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/saveAbout")
	@SysLog(code = ModuleConstont.SYSTEM_OPERATION, method = "增加帮助文档")
	public ModelAndView saveAbout() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
        Fabout about = new Fabout();
        about.setFcontent(request.getParameter("fcontent"));
        about.setFtitle(request.getParameter("ftitle"));
        about.setFtype(request.getParameter("ftype"));
        this.aboutService.saveObj(about);
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","保存成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/deleteAbout")
	@SysLog(code = ModuleConstont.SYSTEM_OPERATION, method = "删除帮助文档")
	public ModelAndView deleteAbout() throws Exception{
		int fid = Integer.parseInt(request.getParameter("uid"));
		this.aboutService.deleteObj(fid);
		
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","删除成功");
		return modelAndView;
	}
}
