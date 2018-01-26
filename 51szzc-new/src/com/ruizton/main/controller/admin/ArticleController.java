package com.ruizton.main.controller.admin;

import com.ruizton.main.Enum.FarticleStatusEnum;
import com.ruizton.main.Enum.ModuleConstont;
import com.ruizton.main.aspect.SysLog;
import com.ruizton.main.comm.ParamArray;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fadmin;
import com.ruizton.main.model.Farticle;
import com.ruizton.main.model.Farticletype;
import com.ruizton.util.Constant;
import com.ruizton.util.DateHelper;
import com.ruizton.util.OSSPostObject;
import com.ruizton.util.Utils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ArticleController extends BaseController {

	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/ssadmin/articleList")
	public ModelAndView Index() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/articleList") ;
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
		filter.append("where 1=1 \n");
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and (fTitle like '%"+keyWord+"%' OR \n");
			filter.append("fkeyword like '%"+keyWord+"%' ) \n");
			modelAndView.addObject("keywords", keyWord);
		}
		if(request.getParameter("ftype") != null){
			int type = Integer.parseInt(request.getParameter("ftype"));
			if(type != 0){
				filter.append(" and farticletype.fid="+request.getParameter("ftype")+"\n");
			}
			modelAndView.addObject("ftype", request.getParameter("ftype"));
		}
		String beginTimeStr = request.getParameter("beginTimeStr");
		String endTimeStr = request.getParameter("endTimeStr");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = sdf.format(new java.util.Date());
		Timestamp tm = Timestamp.valueOf(dateString);
		if(null != beginTimeStr && !"".equals(beginTimeStr)){
			Timestamp beginTime = Timestamp.valueOf(beginTimeStr + " 00:00:00 ");
			filter.append(" and DATE_FORMAT(flastModifyDate,'%Y-%m-%d %H:%i:%S')  > '" + (beginTimeStr + " 00:00:00 ") + "' \n");
			modelAndView.addObject("beginTimeStr", beginTimeStr);
		}
		if(null != endTimeStr && !"".equals(endTimeStr)){
			Timestamp endTime = Timestamp.valueOf(endTimeStr + " 23:59:59 ");
			filter.append(" and DATE_FORMAT(flastModifyDate,'%Y-%m-%d %H:%i:%S') < '" + (endTimeStr + " 23:59:59 ") + "' \n");
			modelAndView.addObject("endTimeStr", endTimeStr);
		}

		//添加状态条件
		filter.append(" and fstatus in (2,4) ");

		if(orderField != null && orderField.trim().length() >0){
			filter.append(" order by "+orderField+"\n");
		}else{
			filter.append(" order by fcreateDate \n");
		}
		
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append(" desc \n");
		}
		
		Map typeMap = new HashMap();
		typeMap.put(0, "全部");
		List<Farticletype> all = this.articleTypeService.findAll();
		for (Farticletype farticletype : all) {
			typeMap.put(farticletype.getFid(), farticletype.getFname());
		}
		modelAndView.addObject("typeMap", typeMap);
		
		List<Farticle> list = this.articleService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("articleList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "articleList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Farticle", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("ssadmin/goArticleJSP")
	public ModelAndView goArticleJSP() throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Farticle article = this.articleService.findById(fid);
			modelAndView.addObject("farticle", article);
		}
		return modelAndView;
	}
	
	@RequestMapping(value="ssadmin/upload")
	@ResponseBody
	public String upload(ParamArray param) throws Exception{
		MultipartFile multipartFile = param.getFiledata() ;
		InputStream inputStream = multipartFile.getInputStream() ;
		String realName = multipartFile.getOriginalFilename() ;
		
		if(realName!=null && realName.trim().toLowerCase().endsWith("jsp")){
			return "" ;
		}
		
		String[] nameSplit = realName.split("\\.") ;
		String ext = nameSplit[nameSplit.length-1] ;
		String realPath = request.getSession().getServletContext().getRealPath("/")+new Constant().AdminArticleDirectory;
		String fileName = Utils.getRandomImageName()+"."+ext;
		boolean flag = Utils.saveFile(realPath,fileName, inputStream) ;
		String result = "";
		if(!flag){
			result = "上传失败";
		}
		JSONObject resultJson = new JSONObject() ;
		resultJson.accumulate("err",result) ;
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//		resultJson.accumulate("msg",basePath+new Constant().AdminArticleDirectory+"/"+fileName) ;
		resultJson.accumulate("msg",OSSPostObject.URL+"/"+fileName) ;
		return resultJson.toString();
	}

	/**
	 * 新增资讯
	 * @param filedata
	 * @param ftitle
	 * @param fKeyword
	 * @param articleLookupid
	 * @param fcontent
	 * @param fisout
	 * @param foutUrl
     * @return
     * @throws Exception
     */

	//@SysLog(code = ModuleConstont.ARTICLE_OPERATION, method = "新增资讯")
	@RequestMapping("ssadmin/saveArticle")
	public ModelAndView saveArticle(
			@RequestParam(required=false) MultipartFile filedata,
			@RequestParam(required=false) String ftitle,
			@RequestParam(required=false) String fKeyword,
			@RequestParam("articleLookup.id") int articleLookupid,
			@RequestParam("tag.tagName") String tagName,
			@RequestParam(required=false) String fcontent,
			@RequestParam(required=false) String fisout,
			@RequestParam(required=false) String forigin,
			@RequestParam(required=false) String foutUrl
			) throws Exception{
		Farticle article = new Farticle();
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		Farticletype articletype = this.articleTypeService.findById(articleLookupid);
		article.setFarticletype(articletype);
		article.setFtitle(ftitle);
		article.setFkeyword(fKeyword);
		article.setFoutUrl(foutUrl);
		article.setFstatus(FarticleStatusEnum.FREE_AUDIT.getCode());
		if(null != tagName && !"".equals(tagName)){
			String[] arrTagNames = tagName.split(",");
			if(arrTagNames.length > 3){
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","标签项最多选择三个");
				return modelAndView;
			}
			article.setFtag(tagName);
		}else{
			article.setFtag("");
		}
		if(fisout == null || fisout.trim().length() ==0){
			article.setFisout(false);
		}else{
			article.setFisout(true);
		}
		article.setFcontent(fcontent);
		article.setForigin(forigin);
		article.setFlastModifyDate(Utils.getTimestamp());
		article.setFcreateDate(Utils.getTimestamp());
		Fadmin admin = (Fadmin)request.getSession().getAttribute("login_admin");
		article.setFadminByFcreateAdmin(admin);
		article.setFadminByFmodifyAdmin(admin);
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
			article.setFurl(fpictureUrl);
		}
		this.articleService.saveObj(article);

		List<Farticle> hostNews = this.frontOtherService.findHostArticle(0, 6) ;
		if(hostNews != null && hostNews.size() >0){
			map.put("hostNews", hostNews);
		}

		List<Farticle> notices = this.frontOtherService.findFarticle(3, 0, 5) ;
		if(notices != null && notices.size() >0){
			map.put("notices", notices);
		}

		List<Farticle> outArticle = this.frontOtherService.findOutArticle(0, 5) ;
		if(notices != null && notices.size() >0){
			map.put("outArticle", outArticle);
		}


		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","保存成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/deleteArticle")
	@SysLog(code = ModuleConstont.ARTICLE_OPERATION, method = "删除资讯")
	public ModelAndView deleteArticle() throws Exception{
		int fid = Integer.parseInt(request.getParameter("uid"));
		this.articleService.deleteObj(fid);

		List<Farticle> hostNews = this.frontOtherService.findHostArticle(0, 6) ;
		if(hostNews != null && hostNews.size() >0){
			map.put("hostNews", hostNews);
		}

		List<Farticle> notices = this.frontOtherService.findFarticle(3, 0, 5) ;
		if(notices != null && notices.size() >0){
			map.put("notices", notices);
		}

		List<Farticle> outArticle = this.frontOtherService.findOutArticle(0, 5) ;
		if(notices != null && notices.size() >0){
			map.put("outArticle", outArticle);
		}
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","删除成功");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/dingArticle")
	public ModelAndView dingArticle() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		Farticle a = this.articleService.findById(fid);
		int typeId = a.getFarticletype().getFid();

		if(a.isFisding()){
			a.setFisding(false);
			modelAndView.addObject("message","取消推荐操作成功");
		}else{
			// 判断全部的推荐总数
			int allCount = this.articleService.getAllCount(" Farticle ", " where fisding = 1 ");
			if(allCount >= Constant.getHostNumber()){
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","推荐数量已超出上线");
				return modelAndView;
			}

			// 判断同一类型的推荐的数量
			int typeAllCount = this.articleService.getAllCount(" Farticle ", " where  farticletype.fid= " + typeId + " and fisding = 1 ");
			if(typeAllCount > Constant.getSingleNumber()){
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","咨询类型推荐数量已超出上线");
				return modelAndView;
			}else if(a.isFisout() || a.getFarticletype().getFname().equals("官方公告")){
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","不可设为推荐");
				return modelAndView;
			}

			a.setFisding(true);
			modelAndView.addObject("message","设置推荐操作成功");
		}
		
		this.articleService.updateObj(a);

		List<Farticle> hostNews = this.frontOtherService.findHostArticle(0, 6) ;
		if(hostNews != null && hostNews.size() >0){
			map.put("hostNews", hostNews);
		}
		

		modelAndView.addObject("statusCode",200);
		return modelAndView;
	}

	/**
	 * 修改资讯内容
	 * @param filedata
	 * @param ftitle
	 * @param fKeyword
	 * @param articleLookupid
	 * @param fid
	 * @param fcontent
	 * @param fisout
	 * @param foutUrl
     * @return
     * @throws Exception
     */
	@RequestMapping("ssadmin/updateArticle")
	public ModelAndView updateArticle(
			@RequestParam(required=false) MultipartFile filedata,
			@RequestParam(required=false) String ftitle,
			@RequestParam(required=false) String fKeyword,
			@RequestParam("articleLookup.id") int articleLookupid,
			@RequestParam("tag.tagName") String tagName,
			@RequestParam(required=false) int fid,
			@RequestParam(required=false) String fcontent,
			@RequestParam(required=false) String forigin,
			@RequestParam(required=false) String fisout,
			@RequestParam(required=false) String foutUrl
			) throws Exception{
		Farticle article = this.articleService.findById(fid);
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		Farticletype articletype = this.articleTypeService.findById(articleLookupid);
		article.setFarticletype(articletype);
		article.setFtitle(ftitle);
		article.setFoutUrl(foutUrl);
		if(null != tagName && !"".equals(tagName)){
			String[] arrTagNames = tagName.split(",");
			if(arrTagNames.length > 3){
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","标签项最多选择三个");
				return modelAndView;
			}
			article.setFtag(tagName);
		}else{
			article.setFtag("");
		}
		if(fisout == null || fisout.trim().length() ==0){
			article.setFisout(false);
		}else{
			article.setFisout(true);
		}
		article.setFkeyword(fKeyword);
		article.setFcontent(fcontent);
		article.setForigin(forigin);
		article.setFlastModifyDate(Utils.getTimestamp());
		article.setFcreateDate(Utils.getTimestamp());
		Fadmin admin = (Fadmin)request.getSession().getAttribute("login_admin");
		article.setFadminByFcreateAdmin(admin);
		article.setFadminByFmodifyAdmin(admin);
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
			article.setFurl(fpictureUrl);
		}
		this.articleService.updateObj(article);

		List<Farticle> hostNews = this.frontOtherService.findHostArticle(0, 6) ;
		if(hostNews != null && hostNews.size() >0){
			map.put("hostNews", hostNews);
		}

		List<Farticle> notices = this.frontOtherService.findFarticle(3, 0, 5) ;
		if(notices != null && notices.size() >0){
			map.put("notices", notices);
		}

		List<Farticle> outArticle = this.frontOtherService.findOutArticle(0, 5) ;
		if(notices != null && notices.size() >0){
			map.put("outArticle", outArticle);
		}

		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","修改成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}




	/**
	 * 查询资讯详情
	 * @param request
	 * @return  H5页面
	 */
	@RequestMapping(value = "/articledetail")
	public ModelAndView articleDetail(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("/front/about/article_detail");
		String fid = request.getParameter("fid");
		if(StringUtils.isBlank(fid)) {
			modelAndView.addObject("message","fid为空");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}

		Date currentDate = new Date();
		Farticle farticle = this.articleService.findById(Integer.valueOf(fid));
		Date lastModifyDate = farticle.getFlastModifyDate();

		//标签拆分
		if(StringUtils.isNotBlank(farticle.getFtag())) {
			String[] tag = farticle.getFtag().split(",");
			modelAndView.addObject("tag", tag);
		}else{
			modelAndView.addObject("tag", "");
		}

		String text = this.returnTimeBetweenText(currentDate, lastModifyDate);
		modelAndView.addObject("createDate", DateHelper.date2String(farticle.getFlastModifyDate(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
		modelAndView.addObject("pushtimeText", text);
		modelAndView.addObject("farticle", farticle);
		modelAndView.addObject("statusCode", 200);
		return modelAndView;
	}


	/**
	 * web网站录入的资讯列表
	 * @return
	 */
	@RequestMapping(value = "ssadmin/webArticleList")
	public ModelAndView webArticleList() {
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/webArticleList") ;
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
		filter.append("where 1=1 \n");
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and (fTitle like '%"+keyWord+"%' OR \n");
			filter.append("fkeyword like '%"+keyWord+"%' ) \n");
			modelAndView.addObject("keywords", keyWord);
		}
		if(StringUtils.isNotBlank(request.getParameter("fstatus"))){
			int type = Integer.parseInt(request.getParameter("fstatus"));
			filter.append(" and fstatus=" + type + "\n");
			modelAndView.addObject("fstatus", type);
		}else{
			filter.append(" and fstatus in (1,2,3) \n");
		}
		String beginTimeStr = request.getParameter("beginTimeStr");
		String endTimeStr = request.getParameter("endTimeStr");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = sdf.format(new java.util.Date());
		Timestamp tm = Timestamp.valueOf(dateString);
		if(null != beginTimeStr && !"".equals(beginTimeStr)){
			Timestamp beginTime = Timestamp.valueOf(beginTimeStr + " 00:00:00 ");
			filter.append(" and DATE_FORMAT(flastModifyDate,'%Y-%m-%d %H:%i:%S')  > '" + (beginTimeStr + " 00:00:00 ") + "' \n");
			modelAndView.addObject("beginTimeStr", beginTimeStr);
		}
		if(null != endTimeStr && !"".equals(endTimeStr)){
			Timestamp endTime = Timestamp.valueOf(endTimeStr + " 23:59:59 ");
			filter.append(" and DATE_FORMAT(flastModifyDate,'%Y-%m-%d %H:%i:%S') < '" + (endTimeStr + " 23:59:59 ") + "' \n");
			modelAndView.addObject("endTimeStr", endTimeStr);
		}

		if(orderField != null && orderField.trim().length() >0){
			filter.append(" order by "+orderField+"\n");
		}else{
			filter.append(" order by fcreateDate \n");
		}

		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append(" desc \n");
		}

		List<Farticle> list = this.articleService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("articleList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "webArticleList");
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("statusEnum", FarticleStatusEnum.statusList);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Farticle", filter+""));
		return modelAndView ;
	}


	@RequestMapping(value = "ssadmin/updateAreStatus")
	public ModelAndView updateAreStatus(Farticle req) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");

		String uid = request.getParameter("uid");
		if(StringUtils.isNotBlank(uid)) {
			req.setFid(Integer.valueOf(uid));
		}

		if(req.getFstatus() == null) {
			modelAndView.addObject("message","参数审核状态错误");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}

		if(req.getFstatus().intValue() == FarticleStatusEnum.REJECT.getCode() && StringUtils.isBlank(req.getFrejectCause())) {
			modelAndView.addObject("message","参数异常");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}

		Farticle farticle = this.articleService.findById(req.getFid());
		if(farticle == null) {
			modelAndView.addObject("message","根据id查询资讯为空");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}

		if(req.getFstatus().intValue() == FarticleStatusEnum.REJECT.getCode()) {
			farticle.setFrejectCause(req.getFrejectCause());
			modelAndView.addObject("callbackType", "closeCurrent");  //如果是拒绝，则方法结束后，关闭弹出框
		}

		farticle.setFstatus(req.getFstatus());
		farticle.setFlastModifyDate(Utils.getTimestamp());
		Fadmin admin = (Fadmin)request.getSession().getAttribute("login_admin");
		farticle.setFadminByFmodifyAdmin(admin);
		this.articleService.updateObj(farticle);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "审核成功");
		return modelAndView;
	}





	@RequestMapping(value = "ssadmin/initAudit")
	public ModelAndView initAudit() {
		ModelAndView modelAndView = new ModelAndView("/ssadmin/auditArticle");
		String uid = request.getParameter("uid");
		if(StringUtils.isBlank(uid)) {
			modelAndView.addObject("message","参数异常");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}

		modelAndView.addObject("uid", uid);
		return modelAndView;
	}

	/**
	 * 资讯阅览
	 * @return
	 */
	@RequestMapping(value = "ssadmin/viewArticle")
	public ModelAndView viewArticle() {
		ModelAndView modelAndView = new ModelAndView("/ssadmin/viewArticle");
		String uid = request.getParameter("uid");
		if(StringUtils.isBlank(uid)) {
			modelAndView.addObject("message","参数异常");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}

		Date currentDate = new Date();
		Farticle farticle = this.articleService.findById(Integer.valueOf(uid));
		if(farticle == null) {
			modelAndView.addObject("message","查询资讯异常");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}
		Date lastModifyDate = farticle.getFlastModifyDate();

		//标签拆分
		if(StringUtils.isNotBlank(farticle.getFtag())) {
			String[] tag = farticle.getFtag().split(",");
			modelAndView.addObject("tag", tag);
		}else{
			modelAndView.addObject("tag", "");
		}

		String text = this.returnTimeBetweenText(currentDate, lastModifyDate);
		modelAndView.addObject("createDate", DateHelper.date2String(farticle.getFlastModifyDate(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
		modelAndView.addObject("pushtimeText", text);
		modelAndView.addObject("farticle", farticle);
		modelAndView.addObject("statusCode", 200);
		return modelAndView;
	}


}
