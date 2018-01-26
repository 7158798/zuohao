package com.ruizton.main.controller.front;

import com.ruizton.main.Enum.TrademappingStatusEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fentrustlog;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualcointype;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class FrontMarketController extends BaseController {


	
	/*
	 * https://www.okcoin.com/market.do?symbol=0
	 * */
	@RequestMapping("/market")
	public ModelAndView market(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int symbol
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		List<Ftrademapping> ftrademappings = this.utilsService.list1(0, 0, " where fstatus=?" , false, Ftrademapping.class,TrademappingStatusEnum.ACTIVE) ;
		if(ftrademapping==null || ftrademapping.getFstatus() != TrademappingStatusEnum.ACTIVE){
			if(ftrademappings.size()>0){
				modelAndView.setViewName("redirect:/market.html?symbol="+ftrademappings.get(0).getFid()) ;
				return modelAndView ;
			}else{
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
		}
		
		modelAndView.addObject("ftrademappings", ftrademappings) ;
		modelAndView.addObject("ftrademapping", ftrademapping) ;
		modelAndView.addObject("symbol", symbol) ;
		modelAndView.setViewName("front/market/market") ;

		modelAndView.addObject("full_screen", i18nMsg("full_screen"));
		modelAndView.addObject("exit_full_screen", i18nMsg("exit_full_screen"));

		return modelAndView ;
	}


	/**
	 * 新版本 行情中心
	 * @param request
	 * @param symbol
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/market_new")
	public ModelAndView market_new(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int symbol
	) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;

		int userid = 0;
		Fuser fuser = null;
		boolean isTelephoneBind =false;
		if(GetSession(request) != null){
			fuser = this.userService.findById(GetSession(request).getFid());
			userid = fuser.getFid();
			isTelephoneBind = fuser.isFisTelephoneBind();
		}

		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		List<Ftrademapping> ftrademappings = this.utilsService.list1(0, 0, " where fstatus=?" , false, Ftrademapping.class,TrademappingStatusEnum.ACTIVE) ;
		if(ftrademapping==null || ftrademapping.getFstatus() != TrademappingStatusEnum.ACTIVE){
			if(ftrademappings.size()>0){
				modelAndView.setViewName("redirect:/market_new.html?symbol="+ftrademappings.get(0).getFid()) ;
				return modelAndView ;
			}else{
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
		}

		Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
		Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;

		boolean isTradePassword = false;
		if(userid !=0 && fuser.getFtradePassword() != null && fuser.getFtradePassword().trim().length() >0){
			isTradePassword = true;
		}

		//取某个币种价格成交信息（价格唯一）
		List<Fentrustlog> fentrustlogList = this.frontTradeService.findNewLatestDeal(ftrademapping.getFid(), 2);
		if(fentrustlogList != null && fentrustlogList.size() >=2 && fentrustlogList.get(0) != null && fentrustlogList.get(1) != null) {
			BigDecimal new_price = new BigDecimal(fentrustlogList.get(0).getFprize()+"");  //最新价
			BigDecimal last_price = new BigDecimal(fentrustlogList.get(1).getFprize()+"");  //上一次的价格
			//涨跌幅度
			BigDecimal rate = (new_price.subtract(last_price)).divide(last_price,4, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN);
			modelAndView.addObject("new_price",new_price) ;
			modelAndView.addObject("last_price",last_price) ;
			modelAndView.addObject("rate",rate) ;
		}

		modelAndView.addObject("coin1",coin1) ;
		modelAndView.addObject("coin2",coin2) ;
		modelAndView.addObject("ftrademappings", ftrademappings) ;
		modelAndView.addObject("ftrademapping", ftrademapping) ;
		modelAndView.addObject("isTradePassword", isTradePassword) ;
		modelAndView.addObject("isTelephoneBind", isTelephoneBind) ;
		//是否需要输入交易密码
		modelAndView.addObject("needTradePasswd", super.isNeedTradePassword(request)) ;
		modelAndView.addObject("symbol", symbol) ;
		modelAndView.setViewName("front/market/market_new") ;
		modelAndView.addObject("full_screen", i18nMsg("full_screen"));
		modelAndView.addObject("exit_full_screen", i18nMsg("exit_full_screen"));
		modelAndView.addObject("comm_error_tips_79", i18nMsg("comm.error.tips.79"));
		return modelAndView ;
	}




	
	@RequestMapping("/kline/fullstart")
	public ModelAndView start(
			@RequestParam(required=false,defaultValue="0")int symbol
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		List<Ftrademapping> ftrademappings = this.utilsService.list1(0, 1, " where fstatus=? " , true, Ftrademapping.class,TrademappingStatusEnum.ACTIVE) ;

		
		if(ftrademapping==null ){
			if(ftrademappings.size()>0){
				modelAndView.setViewName("redirect:/kline/fullstart.html?symbol="+ftrademappings.get(0).getFid()) ;
				return modelAndView ;
			}else{
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
		}
		
		modelAndView.addObject("ftrademapping", ftrademapping) ;
		modelAndView.addObject("ftrademappings", ftrademappings) ;
		modelAndView.setViewName("front/market/start") ;
		return modelAndView ;
	}
	
}
