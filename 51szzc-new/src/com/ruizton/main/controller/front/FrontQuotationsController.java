package com.ruizton.main.controller.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fentrust;
import com.ruizton.main.model.Fentrustlog;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.service.front.FrontVirtualCoinService;

@Controller
public class FrontQuotationsController extends BaseController {

	
	
	@RequestMapping("/json")
	public ModelAndView json() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("front/real/json") ;
		return modelAndView ;
	}
}
