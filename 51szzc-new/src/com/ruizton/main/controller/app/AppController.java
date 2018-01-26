package com.ruizton.main.controller.app;

import com.ruizton.main.auto.KlinePeriodData;
import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Farticle;
import com.ruizton.main.model.Fentrust;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.service.admin.SystemArgsService;
import com.ruizton.main.service.front.FrontOthersService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.util.Utils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController extends BaseController {

	@Autowired
	private FrontOthersService frontOthersService ;
	@Autowired
	private SystemArgsService systemArgsService ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private RealTimeData realTimeData ;
	@Autowired
	private KlinePeriodData klinePeriodData ;
	
	@RequestMapping("/app/article")
	public ModelAndView article(
			@RequestParam(required=true )int id
			) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		
		Farticle farticle = this.frontOthersService.findFarticleById(id) ;
		modelAndView.addObject("farticle",farticle) ;
		modelAndView.setViewName("app/article") ;
		return modelAndView ;
	}
	
	@RequestMapping("/real/appkline")
	public ModelAndView appkline(
			@RequestParam(required=true )int symbol
			) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		modelAndView.addObject("fvirtualcointype", fvirtualcointype) ;
		
		modelAndView.setViewName("app/kline/kline") ;
		return modelAndView ;
	}
	

	@ResponseBody
	@RequestMapping("/real/appdepth")
	public String appdepth(
			@RequestParam(required=true )int symbol
			) throws Exception {
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		JSONArray ret = new JSONArray() ;
		JSONArray buy = new JSONArray() ;
		JSONArray sell = new JSONArray() ;
		
		double val = this.realTimeData.getLatestDealPrize(symbol) ;
		double low = val*0.3D ;
		double high = val*3D ;
		
		Fentrust[] buyArray = this.realTimeData.getBuyDepthMap(fvirtualcointype.getFid(),10) ;
		for (Object object : buyArray) {
			Fentrust fentrust = (Fentrust)object ;
			
			if(fentrust.getFprize()<low||fentrust.getFprize()>high){
				continue ;
			}
			
			JSONArray item = new JSONArray() ;
			item.add(fentrust.getFprize()) ;
			item.add(Utils.getDoubleUp(fentrust.getFleftCount(),4)) ;
			item.add(0) ;
			
			buy.add(item) ;
		}
		
		Fentrust[] sellArray = this.realTimeData.getSellDepthMap(fvirtualcointype.getFid(),10) ;
		for (Object object : sellArray) {
			Fentrust fentrust = (Fentrust)object ;
			
			if(fentrust.getFprize()<low||fentrust.getFprize()>high){
				continue ;
			}
			
			JSONArray item = new JSONArray() ;
			item.add(fentrust.getFprize()) ;
			item.add(Utils.getDoubleUp(fentrust.getFleftCount(),4)) ;
			item.add(0) ;
			
			sell.add(item) ;
		}
		
		
		ret.add(buy) ;
		ret.add(sell) ;
		return ret.toString() ;
	}
	
	@ResponseBody
	@RequestMapping("/real/appperiod")
	public String appperiod(
			@RequestParam(required=true )int symbol
			) throws Exception {
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		
		StringBuffer content = new StringBuffer(
				"chart = {" +
				" symbol : \""+fvirtualcointype.getfShortName()+"_CNY\"," +
				" symbol_view : \""+fvirtualcointype.getfShortName()+"/CNY\"," +
				" ask : 1.2," +
				" time_line :" ) ;

		JSONObject timeLine = new JSONObject() ;
		String m5 = this.klinePeriodData.getJsonString(fvirtualcointype.getFid(), 2) ;
		String m15 = this.klinePeriodData.getJsonString(fvirtualcointype.getFid(), 3) ;
		String m30 = this.klinePeriodData.getJsonString(fvirtualcointype.getFid(), 4) ;
		String h1 = this.klinePeriodData.getJsonString(fvirtualcointype.getFid(), 5) ;
		String h8 = this.klinePeriodData.getJsonString(fvirtualcointype.getFid(), 8) ;
		String d1 = this.klinePeriodData.getJsonString(fvirtualcointype.getFid(), 10) ;
		timeLine.accumulate("5m", m5) ;
		timeLine.accumulate("15m", m15) ;
		timeLine.accumulate("30m", m30) ;
		timeLine.accumulate("1h", h1) ;
		timeLine.accumulate("8h", h8) ;
		timeLine.accumulate("1d", d1) ;
		
		content.append(timeLine.toString()) ;
		
		content.append("};") ;
		
		return content.toString() ;
	}
}
