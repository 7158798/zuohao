package com.szzc.api.app.ctrl;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController extends BaseCtrl {


	@ResponseBody
	@RequestMapping("/real/appdepth.html")
	public String appdepth(
			@RequestParam(required=true )int symbol
			) throws Exception {
		//LOG.dStart(this, "获取KYC认证信息");
		String result = HttpClientHelper.sendGetRequest(buildGetUrl("/real/appdepth.html?symbol="+symbol,null), Boolean.FALSE);
		//LOG.d(this,result);
		//LOG.dEnd(this,"获取KYC认证信息");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/real/appperiod.html")
	public String appperiod(
			@RequestParam(required=true )int symbol
			) throws Exception {
		String result = HttpClientHelper.sendGetRequest(buildGetUrl("/real/appperiod.html?symbol="+symbol,null), Boolean.FALSE);
		//LOG.d(this,result);
		//LOG.dEnd(this,"获取KYC认证信息");
		return result;
	}
}
