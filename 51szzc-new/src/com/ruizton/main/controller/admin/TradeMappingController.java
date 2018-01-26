package com.ruizton.main.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ruizton.main.Enum.ModuleConstont;
import com.ruizton.main.aspect.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.TrademappingStatusEnum;
import com.ruizton.main.Enum.VirtualCoinTypeStatusEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Ffees;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.Fwithdrawfees;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.FfeesService;
import com.ruizton.main.service.admin.TradeMappingService;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.front.FtradeMappingService;
import com.ruizton.util.Constant;
import com.ruizton.util.Utils;

@Controller
public class TradeMappingController extends BaseController {

	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/ssadmin/tradeMappingList")
	public ModelAndView tradeMappingList() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/tradeMappingList") ;
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
//		if(keyWord != null && keyWord.trim().length() >0){
//			filter.append("and (fname like '%"+keyWord+"%' or \n");
//			filter.append("furl like '%"+keyWord+"%' ) \n");
//			modelAndView.addObject("keywords", keyWord);
//		}
//		if(orderField != null && orderField.trim().length() >0){
//			filter.append("order by "+orderField+"\n");
//		}else{
//			filter.append("order by fcreateTime \n");
//		}
//		
//		if(orderDirection != null && orderDirection.trim().length() >0){
//			filter.append(orderDirection+"\n");
//		}else{
//			filter.append("desc \n");
//		}
		
		List<Ftrademapping> list = this.tradeMappingService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("tradeMappingList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "tradeMappingList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Ftrademapping", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("ssadmin/goTradeMappingJSP")
	public ModelAndView goLimittradeJSP() throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Ftrademapping ftradeMapping = this.tradeMappingService.findById(fid);
			modelAndView.addObject("ftradeMapping", ftradeMapping);
			
			String filter = "where ftrademapping.fid="+fid+" order by flevel asc";
			List<Ffees> allFees = this.feesService.list(0, 0, filter, false);
			modelAndView.addObject("allFees", allFees);
		}
		
		List<Fvirtualcointype> fvirtualcointypes = this.virtualCoinService.findAll(CoinTypeEnum.COIN_VALUE,0);
		modelAndView.addObject("fvirtualcointypes", fvirtualcointypes);
		
		List<Fvirtualcointype> fvirtualcointypes_fb = this.virtualCoinService.findAll(CoinTypeEnum.COIN_VALUE,1);
		modelAndView.addObject("fvirtualcointypes_fb", fvirtualcointypes_fb);
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/saveTradeMapping")
	@SysLog(code = ModuleConstont.VIRTUAL_OPERATION, method = "新增法币类型匹配信息")
	public ModelAndView saveTradeMapping() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		
		int fvirtualcointype1 = Integer.parseInt(request.getParameter("fvirtualcointype1"));
	    int fvirtualcointype2 = Integer.parseInt(request.getParameter("fvirtualcointype2"));
		String filter = "where fvirtualcointypeByFvirtualcointype1.fid="+fvirtualcointype1+" and fvirtualcointypeByFvirtualcointype2.fid="+fvirtualcointype2;
		int count = this.adminService.getAllCount("Ftrademapping", filter);
		if(count >0){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","匹配记录重复，请更正");
			return modelAndView;
		}
		int count1 = Integer.parseInt(request.getParameter("fcount1"));
		int count2 = Integer.parseInt(request.getParameter("fcount2"));
		if(count1 >6 || count1 <1 || count2 >6 || count2 <1){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","小数位最小1位，最大6位");
			return modelAndView;
		}
		Ftrademapping tradeMapping = new Ftrademapping();
		tradeMapping.setFcount1(count1);
		tradeMapping.setFcount2(count2);
		tradeMapping.setFisLimit(false);
		tradeMapping.setFminBuyAmount(Double.valueOf(request.getParameter("fminBuyAmount")));
		tradeMapping.setFminBuyCount(Double.valueOf(request.getParameter("fminBuyCount")));
		tradeMapping.setFminBuyPrice(Double.valueOf(request.getParameter("fminBuyPrice")));
		tradeMapping.setFprice(Double.valueOf(request.getParameter("fprice")));
		tradeMapping.setFstatus(TrademappingStatusEnum.FOBBID);
		tradeMapping.setFtradeTime(request.getParameter("ftradeTime"));
		tradeMapping.setFvirtualcointypeByFvirtualcointype1(this.virtualCoinService.findById(fvirtualcointype1));
		tradeMapping.setFvirtualcointypeByFvirtualcointype2(this.virtualCoinService.findById(fvirtualcointype2));
		this.tradeMappingService.saveObj(tradeMapping);
		
		for(int i=1;i<=Constant.VIP;i++){
			Ffees fees = new Ffees();
			fees.setFlevel(i);
			fees.setFbuyfee(0d);
			fees.setFtrademapping(tradeMapping);
			fees.setFfee(0d);
			this.feesService.saveObj(fees);
		}
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","新增成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/deleteTradeMapping")
	@SysLog(code = ModuleConstont.VIRTUAL_OPERATION, method = "禁用法币类型匹配信息")
	public ModelAndView deleteTradeMapping() throws Exception{
		int fid = Integer.parseInt(request.getParameter("uid"));
		Ftrademapping tradeMapping = this.tradeMappingService.findById(fid);
		tradeMapping.setFstatus(TrademappingStatusEnum.FOBBID);
		this.tradeMappingService.updateObj(tradeMapping);
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","禁用成功，请重启TOMCAT");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/goTradeMapping")
	@SysLog(code = ModuleConstont.VIRTUAL_OPERATION, method = "启用法币类型匹配信息")
	public ModelAndView goTradeMapping() throws Exception{
		int fid = Integer.parseInt(request.getParameter("uid"));
		Ftrademapping tradeMapping = this.tradeMappingService.findById(fid);
		tradeMapping.setFstatus(TrademappingStatusEnum.ACTIVE);
		this.tradeMappingService.updateObj(tradeMapping);
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","启用成功，请重启TOMCAT");
		return modelAndView;
	}
	
	
	@RequestMapping("ssadmin/updateTradeMapping")
	@SysLog(code = ModuleConstont.VIRTUAL_OPERATION, method = "修改法币类型匹配信息")
	public ModelAndView updateTradeMapping() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		int count1 = Integer.parseInt(request.getParameter("fcount1"));
		int count2 = Integer.parseInt(request.getParameter("fcount2"));
		if(count1 >6 || count1 <1 || count2 >6 || count2 <1){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","小数位最小1位，最大6位");
			return modelAndView;
		}
		Ftrademapping tradeMapping = this.tradeMappingService.findById(fid);
		tradeMapping.setFcount1(count1);
		tradeMapping.setFcount2(count2);
		tradeMapping.setFisLimit(false);
		tradeMapping.setFminBuyAmount(Double.valueOf(request.getParameter("fminBuyAmount")));
		tradeMapping.setFminBuyCount(Double.valueOf(request.getParameter("fminBuyCount")));
		tradeMapping.setFminBuyPrice(Double.valueOf(request.getParameter("fminBuyPrice")));
		tradeMapping.setFprice(Double.valueOf(request.getParameter("fprice")));
		tradeMapping.setFstatus(TrademappingStatusEnum.FOBBID);
		tradeMapping.setFtradeTime(request.getParameter("ftradeTime"));

		this.tradeMappingService.updateObj(tradeMapping);
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","修改成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/updateTradeFee")
	@SysLog(code = ModuleConstont.VIRTUAL_OPERATION, method = "修改交易手续费")
	public ModelAndView updateTradeFee() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		int fid = Integer.parseInt(request.getParameter("fid"));
		List<Ffees> all = this.feesService.findByProperty("ftrademapping.fid", fid);
		
		//add by hank
		for (Ffees ffees : all) {
			String feeKey = "fee"+ffees.getFid();
			String buyfeeKey = "fbuyfee"+ffees.getFid();
			double fee = Double.valueOf(request.getParameter(feeKey));
			double buyfee = Double.valueOf(request.getParameter(buyfeeKey));
			
			if(fee>=1 || fee<0 || buyfee>=1 || buyfee<0){
				modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","手续费率只能是小于1的小数！");
				return modelAndView;
			}
		}
		
		for (Ffees ffees : all) {
			String feeKey = "fee"+ffees.getFid();
			String buyfeeKey = "fbuyfee"+ffees.getFid();
			double fee = Double.valueOf(request.getParameter(feeKey));
			double buyfee = Double.valueOf(request.getParameter(buyfeeKey));
			ffees.setFfee(fee);
			ffees.setFbuyfee(buyfee);
			this.feesService.updateObj(ffees);
		}
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","更新成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
}
