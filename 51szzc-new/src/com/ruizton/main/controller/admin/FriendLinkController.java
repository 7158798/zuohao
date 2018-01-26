package com.ruizton.main.controller.admin;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ruizton.main.Enum.ModuleConstont;
import com.ruizton.main.aspect.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.LinkTypeEnum;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Ffriendlink;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.FriendLinkService;
import com.ruizton.util.Constant;
import com.ruizton.util.OSSPostObject;
import com.ruizton.util.Utils;

@Controller
public class FriendLinkController extends BaseController {

	
	
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/ssadmin/linkList")
	public ModelAndView linkList() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/linkList") ;
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
        filter.append("where ftype="+LinkTypeEnum.LINK_VALUE+" \n");
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and (fname like '%"+keyWord+"%' or \n");
			filter.append("furl like '%"+keyWord+"%' ) \n");
			modelAndView.addObject("keywords", keyWord);
		}
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by fcreateTime \n");
		}
		
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		List<Ffriendlink> list = this.friendLinkService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("linkList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "linkList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Ffriendlink", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("ssadmin/goLinkJSP")
	public ModelAndView goLinkJSP(String url) throws Exception{
	//	String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Ffriendlink friendlink = this.friendLinkService.findById(fid);
			modelAndView.addObject("friendlink", friendlink);
		}
		Map map = new HashMap();
		map.put(LinkTypeEnum.LINK_VALUE, LinkTypeEnum.getEnumString(LinkTypeEnum.LINK_VALUE));
		modelAndView.addObject("linkTypeMap", map);
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/deleteLink")
	@SysLog(code = ModuleConstont.SYSTEM_OPERATION, method = "删除友情连接")
	public ModelAndView deleteLink() throws Exception{
		int fid = Integer.parseInt(request.getParameter("uid"));
		this.friendLinkService.deleteObj(fid);
		
		String filter = "where ftype="+LinkTypeEnum.LINK_VALUE;
		List<Ffriendlink> ffriendlinks = this.friendLinkService.list(0, 0, filter, false);
		constantMap.put("ffriendlinks", ffriendlinks) ;
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","删除成功");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/saveLink")
	//@SysLog(code = ModuleConstont.SYSTEM_OPERATION, method = "新增友情连接")
	public ModelAndView saveLink(
			@RequestParam(required=false) MultipartFile filedata,
			@RequestParam(required=false) String fdescription,
			@RequestParam(required=false) String fname,
			@RequestParam(required=false) Integer forder,
			@RequestParam(required=false) String furl
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		Ffriendlink link = new Ffriendlink();
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
			link.setFimages(fpictureUrl);
		}

		link.setFdescription(fdescription);
		link.setFname(fname);
		link.setForder(forder);
		link.setFurl(furl);
		link.setFcreateTime(Utils.getTimestamp());
		link.setFtype(LinkTypeEnum.LINK_VALUE);
		this.friendLinkService.saveObj(link);
		
		String filter = "where ftype="+LinkTypeEnum.LINK_VALUE+" order by forder asc";
		List<Ffriendlink> ffriendlinks = this.friendLinkService.list(0, 0, filter, false);
		constantMap.put("ffriendlinks", ffriendlinks) ;

		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","新增成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/updateLink")
	//@SysLog(code = ModuleConstont.SYSTEM_OPERATION, method = "修改友情连接")
	public ModelAndView updateLink(
			@RequestParam(required=false) MultipartFile filedata,
			@RequestParam(required=false) String fdescription,
			@RequestParam(required=false) String fname,
			@RequestParam(required=false) int forder,
			@RequestParam(required=false) int fid,
			@RequestParam(required=false) String furl
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		Ffriendlink link = this.friendLinkService.findById(fid);
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
			link.setFimages(fpictureUrl);
		}
		
		link.setFdescription(fdescription);
		link.setFname(fname);
		link.setForder(forder);
		link.setFurl(furl);
		link.setFcreateTime(Utils.getTimestamp());
		link.setFtype(LinkTypeEnum.LINK_VALUE);
		this.friendLinkService.updateObj(link);
		
		String filter = "where ftype="+LinkTypeEnum.LINK_VALUE+" order by forder asc";
		List<Ffriendlink> ffriendlinks = this.friendLinkService.list(0, 0, filter, false);
		constantMap.put("ffriendlinks", ffriendlinks) ;

		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","修改成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
}
