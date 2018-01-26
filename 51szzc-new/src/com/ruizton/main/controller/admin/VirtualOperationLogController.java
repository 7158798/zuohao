package com.ruizton.main.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.OperationlogEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fadmin;
import com.ruizton.main.model.Fintrolinfo;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.Fvirtualoperationlog;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.UserService;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.admin.VirtualOperationLogService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.util.Utils;

@Controller
public class VirtualOperationLogController extends BaseController {


	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/ssadmin/virtualoperationlogList")
	public ModelAndView Index() throws Exception{
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/virtualoperationlogList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		String logDate = request.getParameter("logDate");
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if(keyWord != null && keyWord.trim().length() >0){
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (fuser.floginName like '%"+keyWord+"%' or \n");
				filter.append("fuser.fnickName like '%"+keyWord+"%' or \n");
				filter.append("fuser.frealName like '%"+keyWord+"%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		if(logDate != null && logDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') = '"+logDate+"' \n");
			modelAndView.addObject("logDate", logDate);
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
		List<Fvirtualoperationlog> list = this.virtualOperationLogService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("virtualoperationlogList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "operationLogList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fvirtualoperationlog", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("ssadmin/goVirtualOperationLogJSP")
	public ModelAndView goVirtualOperationLogJSP() throws Exception{
		
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fvirtualoperationlog virtualoperationlog = this.virtualOperationLogService.findById(fid);
			modelAndView.addObject("virtualoperationlog", virtualoperationlog);
		}
		List<Fvirtualcointype> allType = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		modelAndView.addObject("allType", allType);
		modelAndView.setViewName(url);
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/saveVirtualOperationLog")
	public ModelAndView saveVirtualOperationLog() throws Exception{
		Fvirtualoperationlog virtualoperationlog = new Fvirtualoperationlog();
		int userId = Integer.parseInt(request.getParameter("userLookup.id"));
		Fuser user = this.userService.findById(userId);
		int vid = Integer.parseInt(request.getParameter("vid"));
		Fvirtualcointype coinType = this.virtualCoinService.findById(vid);
		Double fqty = Utils.getDoubleUp(Double.valueOf(request.getParameter("fqty")), 4);
		virtualoperationlog.setFqty(0d);
		virtualoperationlog.setFfrozenQty(fqty);
		virtualoperationlog.setFvirtualcointype(coinType);
		virtualoperationlog.setFuser(user);
		virtualoperationlog.setFstatus(OperationlogEnum.SAVE);
		if(request.getParameter("fisSendMsg") != null){
			virtualoperationlog.setFisSendMsg(true);
		}else{
			virtualoperationlog.setFisSendMsg(false);
		}
		this.virtualOperationLogService.saveObj(virtualoperationlog);
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","新增成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/deleteVirtualOperationLog")
	public ModelAndView deleteVirtualOperationLog() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fvirtualoperationlog virtualoperationlog = this.virtualOperationLogService.findById(fid);
		if(virtualoperationlog.getFstatus() != OperationlogEnum.SAVE){
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","删除失败，记录已审核");
			return modelAndView;
		}
		
		this.virtualOperationLogService.deleteObj(fid);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","删除成功");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/auditVirtualOperationLog")
	public ModelAndView auditVirtualOperationLog() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		Fvirtualoperationlog virtualoperationlog = this.virtualOperationLogService.findById(fid);
		
		if(virtualoperationlog.getFstatus() != OperationlogEnum.SAVE){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","已审核，不允许重复审核");
			return modelAndView;
		}

		try {
			double qty = virtualoperationlog.getFfrozenQty();
			int coinTypeId = virtualoperationlog.getFvirtualcointype().getFid();
			int userId = virtualoperationlog.getFuser().getFid();
			Fintrolinfo introlInfo = null;
			Fvirtualwallet virtualwallet = this.frontUserService.findVirtualWalletByUser(userId, coinTypeId);
			if(virtualoperationlog.isFisSendMsg()){
				if(virtualwallet.getFtotal() < qty){
					modelAndView.addObject("statusCode",300);
					modelAndView.addObject("message","会员余额不足，扣币失败");
					return modelAndView;
				}
				virtualwallet.setFtotal(virtualwallet.getFtotal()-qty);
				
				introlInfo = new Fintrolinfo();
				introlInfo.setFcreatetime(Utils.getTimestamp());
				introlInfo.setFiscny(false);
				introlInfo.setFqty(qty);
				introlInfo.setFname(virtualoperationlog.getFvirtualcointype().getFname());
				introlInfo.setFuser(virtualoperationlog.getFuser());
				introlInfo.setFtitle("冻结"+virtualoperationlog.getFvirtualcointype().getFname()+qty+"个！");
			}
			virtualwallet.setFfrozen(virtualwallet.getFfrozen()+qty);
			
			Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
			virtualoperationlog.setFstatus(OperationlogEnum.FFROZEN);
			virtualoperationlog.setFcreator(sessionAdmin);
			virtualoperationlog.setFcreateTime(Utils.getTimestamp());
			this.virtualOperationLogService.updateVirtualOperationLog(virtualwallet,virtualoperationlog,introlInfo);
		} catch (Exception e) {
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","网络异常");
			return modelAndView;
		}
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","审核成功");
		return modelAndView;
	}
	
	
	@RequestMapping("ssadmin/sendVirtualOperationLog")
	public ModelAndView sendVirtualOperationLog() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		double sendQty = Utils.getDouble(Double.valueOf(request.getParameter("sendQty")), 4);
		if(sendQty<0.0001){
			modelAndView.addObject("statusCode",500);
			modelAndView.addObject("message","最小数量0.0001");
			return modelAndView;
		}
		
		Fvirtualoperationlog virtualoperationlog = this.virtualOperationLogService.findById(fid);
		
		if(virtualoperationlog.getFstatus() != OperationlogEnum.FFROZEN){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","只有状态为冻结，才允许发放！");
			return modelAndView;
		}

		try {
			double last = Utils.getDouble(Utils.sub(virtualoperationlog.getFfrozenQty(),virtualoperationlog.getFqty()), 4);
			if(sendQty > last){
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","此笔记录剩余数量:"+last+"！");
				return modelAndView;
			}
			int coinTypeId = virtualoperationlog.getFvirtualcointype().getFid();
			int userId = virtualoperationlog.getFuser().getFid();
			Fvirtualwallet virtualwallet = this.frontUserService.findVirtualWalletByUser(userId, coinTypeId);
			virtualwallet.setFfrozen(virtualwallet.getFfrozen()-sendQty);
			virtualwallet.setFtotal(virtualwallet.getFtotal()+sendQty);
			
			Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
			virtualoperationlog.setFqty(virtualoperationlog.getFqty()+sendQty);
			if(virtualoperationlog.getFfrozenQty()-virtualoperationlog.getFqty() <=0){
				virtualoperationlog.setFstatus(OperationlogEnum.AUDIT);
			}
			virtualoperationlog.setFcreator(sessionAdmin);
			virtualoperationlog.setFcreateTime(Utils.getTimestamp());
			
			Fintrolinfo introlInfo = new Fintrolinfo();
			introlInfo.setFcreatetime(Utils.getTimestamp());
			introlInfo.setFiscny(false);
			introlInfo.setFqty(sendQty);
			introlInfo.setFname(virtualoperationlog.getFvirtualcointype().getFname());
			introlInfo.setFuser(virtualoperationlog.getFuser());
			introlInfo.setFtitle("解冻"+virtualoperationlog.getFvirtualcointype().getFname()+sendQty+"个！");
			
			this.virtualOperationLogService.updateVirtualOperationLog(virtualwallet,virtualoperationlog,introlInfo);
		} catch (Exception e) {
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","网络异常");
			return modelAndView;
		}
		modelAndView.addObject("callbackType","closeCurrent");
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","发放成功");
		return modelAndView;
	}
}
