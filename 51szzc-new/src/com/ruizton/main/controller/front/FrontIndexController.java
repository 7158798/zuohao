package com.ruizton.main.controller.front;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.UserStatusEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FtradeMappingService;
import com.ruizton.main.service.front.UtilsService;

@Controller
public class FrontIndexController extends BaseController {


	private final static String[] agent = { "Android", "iPhone", "iPod","iPad", "Windows Phone", "MQQBrowser" };
	
	@RequestMapping("/index")
	public ModelAndView index(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int index,
			@RequestParam(required=false,defaultValue="")String forwardUrl,
			@RequestParam(required=false,defaultValue="0")int symbol,
			@RequestParam(required = false,defaultValue ="0") String requestType, //1.直接返回电脑版
			HttpServletResponse resp
			){
		ModelAndView modelAndView = new ModelAndView() ;
		String agent = request.getHeader("User-Agent");

		if(checkAgentIsMobile(agent) && requestType.equals("0")){
			modelAndView.setViewName("front/mobile/index") ;
			return modelAndView ;
		}
		
		//推广注册
		try{
			int id = 0 ;
			id = Integer.parseInt(request.getParameter("r")) ;
			if(id!=0){
				Fuser intro = this.frontUserService.findById(id) ;
				if(intro!=null){
					resp.addCookie(new Cookie("r", String.valueOf(id))) ;
				}
			}
		}catch(Exception e){}
		
		try{
			String sn = HtmlUtils.htmlEscape(request.getParameter("sn").trim()) ;
			if(sn != null && sn.length() >0){
				List<Fuser> services = this.frontUserService.findUserByProperty("fuserNo", sn);
				if(services!=null && services.size() ==1){
					resp.addCookie(new Cookie("sn", services.get(0).getFuserNo().trim())) ;
				}
			}
		}catch(Exception e){}
		
		 int kyclevel = 0;
		if(GetSession(request)==null){
			modelAndView.addObject("forwardUrl",forwardUrl) ;
		}else{
			
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
			if(fuser.getFstatus()==UserStatusEnum.FORBBIN_VALUE){
				CleanSession(request) ;
			 }
			kyclevel = fuser.getKyclevel();
//			modelAndView.addObject("times",fuser.getFscore().getFkillQty()) ;
		}
		
		if(index==1){
			RemoveSecondLoginSession(request) ;
		}
		
		Map<Fvirtualcointype, List<Ftrademapping>> fMap = new TreeMap<Fvirtualcointype, List<Ftrademapping>>(new Comparator<Fvirtualcointype>() {

			public int compare(Fvirtualcointype o1, Fvirtualcointype o2) {
				return o1.getFid().compareTo(o2.getFid());
			}
		}) ;
		List<Fvirtualcointype> fbs =  this.utilsService.list(0, 0, " where ftype=? or ftype=? order by fid asc ", false, Fvirtualcointype.class,CoinTypeEnum.FB_CNY_VALUE,CoinTypeEnum.FB_COIN_VALUE) ;
		for (Fvirtualcointype fvirtualcointype : fbs) {
			List<Ftrademapping> ftrademappings = this.ftradeMappingService.findActiveTradeMappingByFB(fvirtualcointype) ;
			if(ftrademappings.size()>0){
				fMap.put(fvirtualcointype, ftrademappings) ;
			}
		}
		modelAndView.addObject("fMap", fMap) ;
		modelAndView.addObject("kyclevel", kyclevel) ;
		
		int isIndex = 1;
		modelAndView.addObject("isIndex", isIndex) ;
		
		modelAndView.setViewName("front/index") ;

		modelAndView.addObject("fill_phone_email", i18nMsg("fill_phone_email"));
		modelAndView.addObject("fill_pwd", i18nMsg("fill_pwd"));

		return modelAndView ;
	}

	@RequestMapping("/download")
	public ModelAndView downloadApp(){
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("front/download") ;
		return modelAndView ;
	}


	/**
	 * 判断User-Agent 是不是来自于手机
	 * @param ua
	 * @return
	 */
	private boolean checkAgentIsMobile(String ua) {
		boolean flag = false;
		if (!ua.contains("Windows NT") || (ua.contains("Windows NT") && ua.contains("compatible; MSIE 9.0;"))) {
			// 排除 苹果桌面系统
			if (!ua.contains("Windows NT") && !ua.contains("Macintosh")) {
				for (String item : agent) {
					if (ua.contains(item)) {
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}
	
}
