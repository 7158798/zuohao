package com.ruizton.main.controller.admin;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ruizton.main.Enum.ModuleConstont;
import com.ruizton.main.aspect.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fsystemargs;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.SystemArgsService;
import com.ruizton.util.Constant;
import com.ruizton.util.OSSPostObject;
import com.ruizton.util.Utils;

@Controller
public class SystemArgsController extends BaseController {

	// 每页显示多少条数据
	private int numPerPage = 50;

	@RequestMapping("/ssadmin/systemArgsList")
	public ModelAndView Index() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/systemArgsList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			filter.append("and fkey like '%"+keyWord+"%' \n");
			modelAndView.addObject("keywords", keyWord);
		}
//		if(orderField != null && orderField.trim().length() >0){
//			filter.append("order by "+orderField+"\n");
//		}else{
//			filter.append("order by fid \n");
//		}
//		
//		if(orderDirection != null && orderDirection.trim().length() >0){
//			filter.append(orderDirection+"\n");
//		}else{
//			filter.append("desc \n");
//		}
		List<Fsystemargs> list = this.systemArgsService.list((currentPage - 1)
				* numPerPage, numPerPage, filter+"", true);
		modelAndView.addObject("systemArgsList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "systemArgsList");
		modelAndView.addObject("currentPage", currentPage);
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fsystemargs", filter+""));
		return modelAndView;
	}


	@RequestMapping("/ssadmin/operateArgsList")
	public ModelAndView Index1() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/operateArgsList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where type=1 \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			filter.append("and fkey like '%"+keyWord+"%' \n");
			modelAndView.addObject("keywords", keyWord);
		}
		List<Fsystemargs> list = this.systemArgsService.list((currentPage - 1)
				* numPerPage, numPerPage, filter+"", true);
		modelAndView.addObject("systemArgsList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "systemArgsList");
		modelAndView.addObject("currentPage", currentPage);
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fsystemargs", filter+""));
		return modelAndView;
	}

	@RequestMapping("ssadmin/goSystemArgsJSP")
	public ModelAndView goSystemArgsJSP() throws Exception {
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(url);
		if (request.getParameter("uid") != null) {
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fsystemargs systemargs = this.systemArgsService.findById(fid);
			modelAndView.addObject("systemargs", systemargs);
		}
		if(request.getParameter("fileType") != null){
			modelAndView.addObject("fileType", request.getParameter("fileType"));
		}
		if(request.getParameter("maxSize") != null){
			modelAndView.addObject("maxSize", request.getParameter("maxSize"));
		}
		return modelAndView;
	}

	@RequestMapping("ssadmin/saveSystemArgs")
	public ModelAndView saveSystemArgs(
			@RequestParam(required=false) MultipartFile filedata,
			@RequestParam(required=true) String key,
			@RequestParam(required=false) String value,
			@RequestParam(required=false) String desc
			) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		
		Fsystemargs systemArgs = new Fsystemargs();
		systemArgs.setFkey(key);
		systemArgs.setFvalue(value);
		systemArgs.setFdescription(desc);
		String fpictureUrl = "";
		boolean isTrue = false;
		 if(filedata != null && !filedata.isEmpty()){
			InputStream inputStream = filedata.getInputStream() ;
			String fileRealName = filedata.getOriginalFilename() ;
			if(fileRealName != null && fileRealName.trim().length() >0){
				String[] nameSplit = fileRealName.split("\\.") ;
				String ext = nameSplit[nameSplit.length-1] ;
				if(ext!=null 
				 && !ext.trim().toLowerCase().endsWith("jpg")
				 && !ext.trim().toLowerCase().endsWith("png")){
					modelAndView.addObject("statusCode",300);
					modelAndView.addObject("message","非jpg、png文件格式");
					return modelAndView;
				}
				String realPath = request.getSession().getServletContext().getRealPath("/")+new Constant().IdentityPicDirectory;
				String fileName = Utils.getRandomImageName()+"."+ext;
				boolean flag = Utils.saveFile(realPath,fileName, inputStream) ;
				if(flag){
//					fpictureUrl = "/"+new Constant().IdentityPicDirectory+"/"+fileName ;
					fpictureUrl = OSSPostObject.URL+"/"+fileName ;
					isTrue = true;
				}
			}
		}
		if(isTrue){
			systemArgs.setFvalue(fpictureUrl);
		}
		if(!isTrue && (value==null || value.trim().length()==0)){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "参数值或图片链接不能全为空");
			return modelAndView;
		}
		 
		this.systemArgsService.saveObj(systemArgs);
		
		constantMap.put(systemArgs.getFkey(), systemArgs.getFvalue());
		
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "新增成功");
		modelAndView.addObject("callbackType", "closeCurrent");
		return modelAndView;
	}

    /**
	 * 修改系统参数
	 * @param key
	 * @param value
	 * @param fid
	 * @param desc
	 * @return
     * @throws Exception
     */
	@RequestMapping("ssadmin/updateSystemArgs")
	@SysLog(code = ModuleConstont.SYSTEM_OPERATION, method = "修改系统参数")
	public ModelAndView updateSystemArgs(
			//@RequestParam(required=false) MultipartFile filedata,
			@RequestParam(required=true) String key,
			@RequestParam(required=true) String value,
			@RequestParam(required=true) Integer fid,
			@RequestParam(required=false) String desc
			) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		Fsystemargs systemArgs = this.systemArgsService.findById(fid);
//		systemArgs.setFkey(key);
		systemArgs.setFvalue(value);
		systemArgs.setFdescription(desc);
		String fpictureUrl = "";
		MultipartFile filedata = null;
		boolean isTrue = false;
		 if(filedata != null && !filedata.isEmpty()){
			InputStream inputStream = filedata.getInputStream() ;
			String fileRealName = filedata.getOriginalFilename() ;
			if(fileRealName != null && fileRealName.trim().length() >0){
				String[] nameSplit = fileRealName.split("\\.") ;
				String ext = nameSplit[nameSplit.length-1] ;
				if(ext!=null 
				 && !ext.trim().toLowerCase().endsWith("jpg")
				 && !ext.trim().toLowerCase().endsWith("gif")
				 && !ext.trim().toLowerCase().endsWith("png")){
					modelAndView.addObject("statusCode",300);
					modelAndView.addObject("message","非jpg、png文件格式");
					return modelAndView;
				}
				String realPath = request.getSession().getServletContext().getRealPath("/")+new Constant().AdminSYSDirectory;
				String fileName = Utils.getRandomImageName()+"."+ext;
				boolean flag = Utils.saveFile(realPath,fileName, inputStream) ;
				if(flag){
//					fpictureUrl = "/"+new Constant().AdminSYSDirectory+"/"+fileName ;
					fpictureUrl = OSSPostObject.URL+"/"+fileName ;
					isTrue = true;
				}
			}
		}
		if(isTrue){
			systemArgs.setFvalue(fpictureUrl);
		}
		
		if(!isTrue && (value==null || value.trim().length()==0)){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "参数值或图片链接不能全为空");
			return modelAndView;
		}
		
		this.systemArgsService.updateObj(systemArgs);
		
		constantMap.put(systemArgs.getFkey(), systemArgs.getFvalue());
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "更新成功");
		modelAndView.addObject("callbackType", "closeCurrent");
		return modelAndView;
	}

}
