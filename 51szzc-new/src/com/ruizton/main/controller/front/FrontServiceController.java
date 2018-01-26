package com.ruizton.main.controller.front;

import java.util.List;

import com.ruizton.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Farticle;
import com.ruizton.main.model.Farticletype;
import com.ruizton.main.service.admin.ArticleService;
import com.ruizton.main.service.front.FrontOthersService;
import com.ruizton.util.PaginUtil;

@Controller
public class FrontServiceController extends BaseController {

	@Autowired
	private FrontOthersService frontOthersService ;
	@Autowired
	private ArticleService articleService;

	private static final int page =10;
	
	@RequestMapping("/service/ourService")
	public ModelAndView ourService(
			@RequestParam(required=false,defaultValue="2")int id,
			@RequestParam(required=false,defaultValue="1")int currentPage,
			String tag, String keyword
			) throws Exception{//12,5,5
		ModelAndView modelAndView = new ModelAndView() ;
		
		
		Farticletype type = this.frontOthersService.findFarticleTypeById(id);
		if(type == null){
			id = 2;
		}

		//热点资讯
		String filter = " where fisding = 1 and fstatus in(2,4) order by fLastModifyDate desc";
		List<Farticle> hotsArticles = this.articleService.list(0, Constant.getHostNumber(), filter, true);
		modelAndView.addObject("hotsArticles", hotsArticles) ;

		List<Farticletype> articletypes = this.frontOthersService.findFarticleTypeAll();
		modelAndView.addObject("articletypes",articletypes) ;

		//搜索资讯
		StringBuffer filterArticle = new StringBuffer();
		filterArticle.append(" where fstatus in(2,4) ");
		if(null != tag && !"".equals(tag)){
			filterArticle.append(" and ftag like '%" + tag +"%' ");
			modelAndView.addObject("tag", tag) ;
		}
		if(null != keyword && !"".equals(keyword)){
			filterArticle.append(" and fTitle like '%" + keyword +"%' ");
			modelAndView.addObject("keyword", keyword) ;
		}
		if((null != tag && !"".equals(tag)) || (null != keyword && !"".equals(keyword))){
			filterArticle.append(" order by fisding desc, fLastModifyDate desc  ");
			List<Farticle> articles = this.articleService.list((currentPage-1)*page, page, filterArticle.toString(), true);
			modelAndView.addObject("farticles", articles) ;
		}else{
			List<Farticle> farticles = this.frontOthersService.findFarticleByTypeOut(id, (currentPage-1)*page, page) ;
			int total = this.frontOthersService.findFarticleCount(id) ;
			String pagin = PaginUtil.generatePagin(total/page+(total%page==0?0:1), currentPage, "/service/ourService.html?id="+id+"&") ;

			modelAndView.addObject("pagin",pagin);
			modelAndView.addObject("farticles", farticles) ;
		}

		modelAndView.addObject("articleTag", Constant.getTagList()) ;
		modelAndView.addObject("hostNumber", Constant.getSingleNumber()) ;
		modelAndView.addObject("id",id) ;
		modelAndView.addObject("isIndex", 1) ;
		modelAndView.addObject("fill_word", i18nMsg("fill_word"));
		modelAndView.setViewName("front/service/index") ;
		return modelAndView ;
	}
	
	
	
	
	@RequestMapping("/service/article")
	public ModelAndView article(
			@RequestParam(required=false,defaultValue="0")int id
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Farticle farticle = this.frontOthersService.findFarticleById(id) ;
		if(farticle == null){
			modelAndView.setViewName("redirect:/service/ourService.html") ;
			return modelAndView;
		}

		//热点资讯
		String filterHost = " where fisding = 1 and fstatus in(2,4) order by fLastModifyDate desc";
		List<Farticle> hotsArticles = this.articleService.list(0, Constant.getHostNumber(), filterHost, true);
		modelAndView.addObject("hotsArticles", hotsArticles) ;

		modelAndView.addObject("articleTag", Constant.getTagList()) ;
		modelAndView.addObject("hostNumber", Constant.getSingleNumber()) ;
		
		modelAndView.addObject("farticle", farticle) ;
		modelAndView.setViewName("front/service/article") ;
		return modelAndView ;
	}

	@RequestMapping("/service/searcheArticle")
	public ModelAndView searcheArticle(String tag, String keyword,
			@RequestParam(required=false,defaultValue="1")int currentPage) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		if(null != tag && !"".equals(tag) && null != keyword && !"".equals(keyword)){
			modelAndView.setViewName("redirect:/service/ourService.html") ;
			return modelAndView;
		}

		// 查询热点资讯
		String filter = " where fisding = 1 and fstatus in(2,4) order by fLastModifyDate desc";
		List<Farticle> hotsArticles = this.articleService.list(0, Constant.getHostNumber(), filter, true);
		modelAndView.addObject("hotsArticles", hotsArticles) ;

		modelAndView.addObject("articleTag", Constant.getTagList()) ;
		modelAndView.addObject("hostNumber", Constant.getSingleNumber()) ;

		StringBuffer filterArticle = new StringBuffer();
		if(null != tag && !"".equals(tag)){
			filterArticle.append(" where fstatus in(2,4) and ftag like '%tag%' order by fisding, fLastModifyDate desc ");
			modelAndView.addObject("tag", tag) ;
		}else if(null != keyword && !"".equals(keyword)){
			filterArticle.append(" where fstatus in(2,4) and fTitle like '%tag%' order by fisding, fLastModifyDate desc ");
			modelAndView.addObject("keyword", keyword) ;
		}
		List<Farticle> articles = this.articleService.list((currentPage-1)*page, page, filterArticle.toString(), true);
		modelAndView.addObject("articles", articles) ;
		modelAndView.setViewName("front/service/index") ;
		return modelAndView ;
	}
}
