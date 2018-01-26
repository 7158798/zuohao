package com.ruizton.main.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.oscache.util.StringUtil;
import com.ruizton.main.Enum.ModuleConstont;
import com.ruizton.main.aspect.SysLog;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.EntrustStatusEnum;
import com.ruizton.main.Enum.EntrustTypeEnum;
import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fentrust;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.EntrustService;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.front.FrontTradeService;
import com.ruizton.util.Utils;

@Controller
public class EntrustController extends BaseController {

	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/ssadmin/entrustList")
	public ModelAndView Index() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/entrustList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		String entrustFilter = request.getParameter("entrustFilter");
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if(keyWord != null && keyWord.trim().length() >0){
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (fuser.floginName like '%"+keyWord+"%' OR \n");
				filter.append("fuser.frealName like '%"+keyWord+"%' OR \n");
				filter.append("fuser.fnickName like '%"+keyWord+"%' ) \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}

		if (StringUtils.isNotEmpty(entrustFilter)){
			String userIds = (String) constantMap.getMap().get("entrustFilter");
			if (StringUtils.isNotEmpty(userIds))
				filter.append("and fuser.fid not in(" + userIds + ") \n");
			modelAndView.addObject("entrustFilter", entrustFilter);
		}
		
		if(request.getParameter("ftype") != null){
			int type = Integer.parseInt(request.getParameter("ftype"));
			if(type != 0){
				filter.append("and ftrademapping.fvirtualcointypeByFvirtualcointype2.fid="+type+"\n");
			}
			modelAndView.addObject("ftype", type);
		}else{
			modelAndView.addObject("ftype", 0);
		}
		
		if(request.getParameter("ftype1") != null){
			int type1 = Integer.parseInt(request.getParameter("ftype1"));
			if(type1 != 0){
				filter.append("and ftrademapping.fvirtualcointypeByFvirtualcointype1.fid="+type1+"\n");
			}
			modelAndView.addObject("ftype1", type1);
		}else{
			modelAndView.addObject("ftype1", 0);
		}
		
		String status = request.getParameter("status");
		if(status != null && status.trim().length() >0){
			if(!status.equals("0")){
				filter.append("and fstatus="+status+" \n");
			}
			modelAndView.addObject("status", status);
		}else{
			modelAndView.addObject("status", 0);
		}
		
		String entype = request.getParameter("entype");
		if(entype != null && entype.trim().length() >0){
			if(!entype.equals("-1")){
				filter.append("and fentrustType="+entype+" \n");
			}
			modelAndView.addObject("entype", entype);
		}else{
			modelAndView.addObject("entype", -1);
		}
		
		try {
			String price = request.getParameter("price");
			if(price != null && price.trim().length() >0){
				double p = Double.valueOf(price);
				filter.append("and fprize >="+p+" \n");
			}
			modelAndView.addObject("price", price);
		} catch (Exception e) {
		}
		
		try {
			String price = request.getParameter("price1");
			if(price != null && price.trim().length() >0){
				double p = Double.valueOf(price);
				filter.append("and fprize <="+p+" \n");
			}
			modelAndView.addObject("price1", price);
		} catch (Exception e) {
		}
		
		String logDate = request.getParameter("logDate");
		if(logDate != null && logDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d %H:%i:%s') >= '"+logDate+"' \n");
			modelAndView.addObject("logDate", logDate);
		}
		
		String logDate1 = request.getParameter("logDate1");
		if(logDate1 != null && logDate1.trim().length() >0){
			filter.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d %H:%i:%s') <= '"+logDate1+"' \n");
			modelAndView.addObject("logDate1", logDate1);
		}
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by fid \n");
		}
		
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		
		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.COIN_VALUE,0);
		Map<Integer,String> typeMap = new HashMap<Integer,String>();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
		}
		typeMap.put(0, "全部");
		modelAndView.addObject("typeMap", typeMap);
		
		List<Fvirtualcointype> type1 = this.virtualCoinService.findAll(CoinTypeEnum.COIN_VALUE,1);
		Map<Integer,String> typeMap1 = new HashMap<Integer,String>();
		for (Fvirtualcointype fvirtualcointype : type1) {
			typeMap1.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
		}
		typeMap1.put(0, "全部");
		modelAndView.addObject("typeMap1", typeMap1);
		
		Map<Integer,String> statusMap = new HashMap<Integer,String>();
		statusMap.put(EntrustStatusEnum.AllDeal, EntrustStatusEnum.getEnumString(EntrustStatusEnum.AllDeal));
		statusMap.put(EntrustStatusEnum.Cancel, EntrustStatusEnum.getEnumString(EntrustStatusEnum.Cancel));
		statusMap.put(EntrustStatusEnum.Going, EntrustStatusEnum.getEnumString(EntrustStatusEnum.Going));
		statusMap.put(EntrustStatusEnum.PartDeal, EntrustStatusEnum.getEnumString(EntrustStatusEnum.PartDeal));
		statusMap.put(0,"全部");
		modelAndView.addObject("statusMap", statusMap);
		
		Map<Integer,String> entypeMap = new HashMap<Integer,String>();
		entypeMap.put(EntrustTypeEnum.BUY, EntrustTypeEnum.getEnumString(EntrustTypeEnum.BUY));
		entypeMap.put(EntrustTypeEnum.SELL, EntrustTypeEnum.getEnumString(EntrustTypeEnum.SELL));
		entypeMap.put(-1,"全部");
		modelAndView.addObject("entypeMap", entypeMap);
		
		double fees = this.adminService.getSQLValue2("select sum(ffees-fleftfees) from Fentrust "+filter.toString());
		double totalAmt = this.adminService.getSQLValue2("select sum(fcount-fleftCount) from Fentrust "+filter.toString());
		double totalQty = this.adminService.getSQLValue2("select sum(fsuccessAmount) from Fentrust "+filter.toString());
		
		
		modelAndView.addObject("fees", Utils.getDouble(fees, 2));
		modelAndView.addObject("totalAmt", Utils.getDouble(totalAmt, 2));
		modelAndView.addObject("totalQty", Utils.getDouble(totalQty, 2));
		
		
		List<Fentrust> list = this.entrustService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("entrustList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "entrustList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fentrust", filter+""));
		return modelAndView ;
	}

	/**
	 * 取消交易
	 * @return
	 * @throws Exception
     */
	@RequestMapping("/ssadmin/cancelEntrust")
	@SysLog(code = ModuleConstont.USER_OPERATION, method = "取消委托交易")
	public ModelAndView cancelEntrust(String ids) throws Exception{
		//String ids = request.getParameter("ids");
		String[] idString = ids.split(",");
		for (int i=0;i<idString.length;i++) {
			Fentrust fentrust2 = this.entrustService.findById(Integer.parseInt(idString[i]));
			if(fentrust2!=null
					&&(fentrust2.getFstatus()==EntrustStatusEnum.Going || fentrust2.getFstatus()==EntrustStatusEnum.PartDeal )){
				boolean flag = false ;
				try {
					this.frontTradeService.updateCancelFentrust(fentrust2, fentrust2.getFuser()) ;
					flag = true ;
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(flag==true){
					if(fentrust2.getFentrustType()==EntrustTypeEnum.BUY){
						if(fentrust2.isFisLimit()){
							this.realTimeData.removeEntrustLimitBuyMap(fentrust2.getFtrademapping().getFid(), fentrust2) ;
						}else{
							this.realTimeData.removeEntrustBuyMap(fentrust2.getFtrademapping().getFid(), fentrust2) ;
						}
					}else{
						if(fentrust2.isFisLimit()){
							this.realTimeData.removeEntrustLimitSellMap(fentrust2.getFtrademapping().getFid(), fentrust2) ;
						}else{
							this.realTimeData.removeEntrustSellMap(fentrust2.getFtrademapping().getFid(), fentrust2) ;
						}
						
					}
				}
			}
		}
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","取消成功");
		return modelAndView;
	}
	
}
