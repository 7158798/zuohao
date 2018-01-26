package com.ruizton.main.controller.admin;

import com.ruizton.main.Enum.LogTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Flog;
import com.ruizton.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LogController extends BaseController {


	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/ssadmin/logList")
	public ModelAndView logList() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/logList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String keyWord = request.getParameter("keywords");
		String ftype = request.getParameter("ftype");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and fkey1 = '"+keyWord+"' \n");
			modelAndView.addObject("keywords", keyWord);
		}

		if (ftype != null
				&& ftype.trim().length() > 0) {
			int type = Integer.parseInt(ftype);
			if (type != 0) {
				filter.append("and ftype=" + ftype + " \n");
			}
			modelAndView.addObject("ftype", ftype);
		}else{
			modelAndView.addObject("ftype", 0);
		}

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

		List<Flog> list = this.logService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);

		Map<Integer,String> logType = new HashMap<Integer,String>();
		for(int i=1;i<=14;i++){
			logType.put(i, LogTypeEnum.getEnumString(i));
		}
		logType.put(0, "全部");
		modelAndView.addObject("logType", logType);

		modelAndView.addObject("logList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "logList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Flog", filter+""));
		return modelAndView ;
	}

	@RequestMapping("/ssadmin/sysLoginLog")
	public ModelAndView sysLoginLog() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/sysLoginLog") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append(" where 1=1 and ftype = 11 \n");
		//   filter.append("and (unix_timestamp(now())-unix_timestamp(fcreatetime)) <2*60*60 \n");
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append(" and fkey3 like '%"+keyWord+"%' \n");
			modelAndView.addObject("keywords", keyWord);
		}

		if(orderField != null && orderField.trim().length() >0){
			filter.append(" order by "+orderField+" \n");
		}else{
			filter.append(" order by fId  \n");
		}

		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append(" desc \n");
		}

		List<Flog> list = this.logService.list((currentPage - 1) * numPerPage, numPerPage, filter + "", true);

		modelAndView.addObject("sysLoginLogList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "sysLoginLog");
		//总数量
		modelAndView.addObject("totalCount",this.logService.getAllCount("Flog", filter+""));
		return modelAndView ;
	}
}
