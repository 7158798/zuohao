package com.ruizton.main.controller.admin;

import com.ruizton.main.Enum.*;
import com.ruizton.main.aspect.SysLog;
import com.ruizton.main.auto.okcoin.MD5Util;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.*;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.VirtualCapitaloperationService;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.admin.VirtualWalletService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontValidateService;
import com.ruizton.util.*;
import com.ruizton.util.antshare.ANSUtils;
import com.ruizton.util.antshare.resp.BalanceResp;
import com.ruizton.util.zuohao.PasswordUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class VirtualCapitaloperationController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(VirtualCapitaloperationController.class);

	private static final String CLASS_NAME = VirtualCapitaloperationController.class.getSimpleName();


	// 每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();

	//虚拟币操作列表
	@RequestMapping("/ssadmin/virtualCaptualoperationList")
	public ModelAndView Index() throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/virtualCaptualoperationList1");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}

		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
			}
			modelAndView.addObject("ftype", type);
		} else {
			modelAndView.addObject("ftype", 0);
		}
		filterSQL.append("and fuser.fid is not null \n");

		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		} else {
			filterSQL.append("order by fid \n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		} else {
			filterSQL.append("desc \n");
		}

		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		Map typeMap = new HashMap();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
		}
		typeMap.put(0, "全部");
		modelAndView.addObject("typeMap", typeMap);

		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService
				.list((currentPage - 1) * numPerPage, numPerPage, filterSQL.toString(), true);
		modelAndView.addObject("virtualCapitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "virtualCapitaloperationList");
		// 总数量
		modelAndView.addObject("totalCount", this.adminService.getAllCount(
				"Fvirtualcaptualoperation", filterSQL.toString()));
		return modelAndView;
	}
    //虚拟币充值列表
	@RequestMapping("/ssadmin/virtualCapitalInList")
	public ModelAndView virtualCapitalInList() throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/virtualCapitalInList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		int fstatus = -1;
		if(request.getParameter("fstatus") != null){
			fstatus = Integer.parseInt(request.getParameter("fstatus"));
		}
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 and ftype="
				+ VirtualCapitalOperationTypeEnum.COIN_IN + "\n");

		if(fstatus != -1){
			filterSQL.append("and fstatus ="+fstatus);
		}
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}

		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
			}
			modelAndView.addObject("ftype", type);
		} else {
			modelAndView.addObject("ftype", 0);
		}

		filterSQL.append("and fuser.fid is not null \n");

		//添加日期查询条件
		String startDate = request.getParameter("startDate");
		if(startDate  != null && startDate .trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fCreateTime,'%Y-%m-%d') >= '"+startDate +"' \n");
			modelAndView.addObject("startDate", startDate );
		}

		String endDate = request.getParameter("endDate");
		if(endDate != null && endDate.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fCreateTime,'%Y-%m-%d') <= '"+endDate+"' \n");
			modelAndView.addObject("endDate", endDate);
		}

		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		}

		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		Map typeMap = new HashMap();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
		}

		//获取是否需要登录密码
		Fauditprocess fauditprocess = this.auditProcessService.findByftype(AuditProcessEnum.VIRTUAL_IN);
		int isneedpwd = fauditprocess == null?0:fauditprocess.getfIsneedPwd();
		modelAndView.addObject("isneedpwd",isneedpwd);

		typeMap.put(0, "全部");
		modelAndView.addObject("typeMap", typeMap);

		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService
				.list((currentPage - 1) * numPerPage, numPerPage, filterSQL
						+ "", true);
		modelAndView.addObject("virtualCapitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "virtualCapitalInList");
		modelAndView.addObject("status",fstatus);
		// 总数量
		modelAndView.addObject("totalCount", this.adminService.getAllCount(
				"Fvirtualcaptualoperation", filterSQL + ""));

		List<Fvirtualcaptualoperation> list1=	getVirtualCapitalInList();
		//计算充值数量和手续费合计
		double totalDeposit=0;
		double totalFees=0;
		for( Fvirtualcaptualoperation fvirtualcaptualoperation: list1 ){
			totalDeposit+=fvirtualcaptualoperation.getFamount();
			totalFees+=fvirtualcaptualoperation.getFfees();
		}
		modelAndView.addObject("totalDeposit",totalDeposit);
		modelAndView.addObject("totalFees",totalFees);
		return modelAndView;
	}

	private static enum Export1Filed {
		会员UID,登陆名, 会员昵称, 会员真实姓名, 虚拟币类型, 确认数, 状态,交易类型, 数量, 手续费, 充值地址, 创建时间,最后修改时间;
	}

	//虚拟币充值列表excel导出
	@RequestMapping("/ssadmin/virtualCapitalInListExport")

	public ModelAndView virtualCapitalInListExport(HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition", "attachment;filename=virtualCapitalInListExport.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (VirtualCapitaloperationController.Export1Filed field : VirtualCapitaloperationController.Export1Filed.values()) {
			e.setCell(field.ordinal(), field.toString());
		}

		//

		List<Fvirtualcaptualoperation> lists=	getVirtualCapitalInList();

		for (Fvirtualcaptualoperation fvirtualcaptualoperation : lists) {
			e.createRow(rowIndex++);
			for (VirtualCapitaloperationController.Export1Filed filed : VirtualCapitaloperationController.Export1Filed.values()) {
				switch (filed) {
					case 会员UID:
						if (fvirtualcaptualoperation.getFuser() != null)
							e.setCell(filed.ordinal(),  fvirtualcaptualoperation.getFuser().getFid());
						break;

					case 登陆名:
						if (fvirtualcaptualoperation.getFuser() != null)
							e.setCell(filed.ordinal(), fvirtualcaptualoperation.getFuser()
									.getFloginName());
						break;
					case 会员昵称:
						if (fvirtualcaptualoperation.getFuser() != null)
							e.setCell(filed.ordinal(), fvirtualcaptualoperation.getFuser()
									.getFnickName());
						break;
					case 会员真实姓名:
						if (fvirtualcaptualoperation.getFuser() != null)
							e.setCell(filed.ordinal(), fvirtualcaptualoperation.getFuser()
									.getFrealName());
						break;
					case 虚拟币类型:
						e.setCell(filed.ordinal(), fvirtualcaptualoperation.getFvirtualcointype().getFname());
						break;
					case 确认数:
						e.setCell(filed.ordinal(),fvirtualcaptualoperation.getFconfirmations());
					case 状态:
						e.setCell(filed.ordinal(), fvirtualcaptualoperation.getFstatus_s());
						break;
					case 交易类型:
						e.setCell(filed.ordinal(), fvirtualcaptualoperation.getFtype_s());
						break;
					case 数量:
						e.setText(filed.ordinal(), fvirtualcaptualoperation.getFamount());
						break;
					case 手续费:
						e.setText(filed.ordinal(), fvirtualcaptualoperation.getFfees());
						break;
					case 充值地址:
						e.setCell(filed.ordinal(), fvirtualcaptualoperation.getRecharge_virtual_address());
						break;
					case 创建时间:
						e.setCell(filed.ordinal(), fvirtualcaptualoperation.getFcreateTime());
						break;
					case 最后修改时间:
						e.setCell(filed.ordinal(), fvirtualcaptualoperation.getFlastUpdateTime());
						break;
					default:
						break;
				}
			}
		}

		e.exportXls(response);
		response.getOutputStream().close();

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "导出成功");
		return modelAndView;
	}

	private java.util.List<Fvirtualcaptualoperation> getVirtualCapitalInList() {

		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		int fstatus = -1;
		if(request.getParameter("fstatus") != null){
			fstatus = Integer.parseInt(request.getParameter("fstatus"));
		}
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 and ftype="
				+ VirtualCapitalOperationTypeEnum.COIN_IN + "\n");

		if(fstatus != -1){
			filterSQL.append("and fstatus ="+fstatus);
		}
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");
			}

		}

		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
			}

		}

		filterSQL.append("and fuser.fid is not null \n");

		//添加日期查询条件
		String startDate = request.getParameter("startDate");
		if(startDate  != null && startDate .trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fCreateTime,'%Y-%m-%d') >= '"+startDate +"' \n");

		}

		String endDate = request.getParameter("endDate");
		if(endDate != null && endDate.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fCreateTime,'%Y-%m-%d') <= '"+endDate+"' \n");

		}

		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		}

		//获取是否需要登录密码
		Fauditprocess fauditprocess = this.auditProcessService.findByftype(AuditProcessEnum.VIRTUAL_IN);
		int isneedpwd = fauditprocess == null?0:fauditprocess.getfIsneedPwd();

		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService
				.list(0, 0, filterSQL
						+ "", false);
		return list;
	}


	//虚拟币提现列表
	@RequestMapping("/ssadmin/virtualCapitalOutList")
	public ModelAndView virtualCapitalOutList() throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/virtualCapitalOutList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");

		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		int fstatus = 0;
		if(request.getParameter("fstatus") != null){
			fstatus = Integer.parseInt(request.getParameter("fstatus"));
		}


		StringBuffer filterSQL = new StringBuffer();

		filterSQL.append("where 1=1 and ftype="
				+ VirtualCapitalOperationTypeEnum.COIN_OUT + "\n");
		if(fstatus == 0){
			filterSQL.append("and fstatus IN("
					+ VirtualCapitalOperationOutStatusEnum.WaitForOperation + ","
					+ VirtualCapitalOperationOutStatusEnum.OneTimeAudit + ","
					+ VirtualCapitalOperationOutStatusEnum.TwoTimeAudit + ","
					+ VirtualCapitalOperationOutStatusEnum.ThreeTimeAudit + ","
					+ VirtualCapitalOperationOutStatusEnum.OperationLock + ")\n");
		}else{
			filterSQL.append("and fstatus ="+fstatus);
		}
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("fuser.fid =" + fid + " or \n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");

			}
			modelAndView.addObject("keywords", keyWord);
		}

		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
			}
			modelAndView.addObject("ftype", type);
		} else {
			modelAndView.addObject("ftype", 0);
		}
		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		}

		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		Map typeMap = new HashMap();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
		}
		typeMap.put(0, "全部");
		modelAndView.addObject("typeMap", typeMap);

		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService
				.list((currentPage - 1) * numPerPage, numPerPage, filterSQL
						+ "", true);
		modelAndView.addObject("virtualCapitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "virtualCapitalOutList");
		// 总数量
		modelAndView.addObject("totalCount", this.adminService.getAllCount(
				"Fvirtualcaptualoperation", filterSQL + ""));
		modelAndView.addObject("status",fstatus);
		return modelAndView;
	}



	//虚拟币成功提币列表
	@RequestMapping("/ssadmin/virtualCapitalOutSucList")
	public ModelAndView virtualCapitalOutSucList() throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/virtualCapitalOutSucList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 and ftype="
				+ VirtualCapitalOperationTypeEnum.COIN_OUT + "\n");
		filterSQL.append("and fstatus IN("
				+ VirtualCapitalOperationOutStatusEnum.OperationSuccess +")\n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append(" and fuser.fid =" + fid + "\n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}

		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
			}
			modelAndView.addObject("ftype", type);
		} else {
			modelAndView.addObject("ftype", 0);
		}
      //添加日期查询条件
		String startDate = request.getParameter("startDate");
		if(startDate  != null && startDate .trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(flastUpdateTime,'%Y-%m-%d') >= '"+startDate +"' \n");
			modelAndView.addObject("startDate", startDate );
		}

		String endDate = request.getParameter("endDate");
		if(endDate != null && endDate.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(flastUpdateTime,'%Y-%m-%d') <= '"+endDate+"' \n");
			modelAndView.addObject("endDate", endDate);
		}


		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		}

		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		Map typeMap = new HashMap();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
		}
		typeMap.put(0, "全部");
		modelAndView.addObject("typeMap", typeMap);

		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService
				.list((currentPage - 1) * numPerPage, numPerPage, filterSQL
						+ "", true);
		modelAndView.addObject("virtualCapitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "virtualCapitalOutSucList");
		// 总数量
		modelAndView.addObject("totalCount", this.adminService.getAllCount(
				"Fvirtualcaptualoperation", filterSQL + ""));

		List<Fvirtualcaptualoperation> list1=getVirtualCapitalOutSucList();
		//计算提现数量和手续费合计
        double totalWithdraw=0;
         double totalFees=0;
		for( Fvirtualcaptualoperation fvirtualcaptualoperation: list1 ){
			totalWithdraw+=fvirtualcaptualoperation.getFamount();
			totalFees+=fvirtualcaptualoperation.getFfees();
		}
        modelAndView.addObject("totalWithdraw",totalWithdraw);
		modelAndView.addObject("totalFees",totalFees);
		return modelAndView;
	}


	private static enum Export2Filed {
		会员,登陆名, 会员昵称, 会员真实姓名, 虚拟币类型, 状态, 交易类型, 数量, 手续费, 提现地址, 创建时间,最后修改时间;
	}

	//虚拟币成功提现列表excel导出
	@RequestMapping("ssadmin/virtualCapitalOutSucListExport")

	public ModelAndView virtualCapitalOutSucListExport(HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition", "attachment;filename=virtualCapitalOutSucListExport.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (VirtualCapitaloperationController.Export2Filed field : VirtualCapitaloperationController.Export2Filed.values()) {
			e.setCell(field.ordinal(), field.toString());
		}

		//

		List<Fvirtualcaptualoperation> lists=	getVirtualCapitalOutSucList();

		for (Fvirtualcaptualoperation fvirtualcaptualoperation : lists) {
			e.createRow(rowIndex++);
			for (VirtualCapitaloperationController.Export2Filed filed : VirtualCapitaloperationController.Export2Filed.values()) {
				switch (filed) {
					case 会员:
						if (fvirtualcaptualoperation.getFuser() != null)
							e.setCell(filed.ordinal(),  fvirtualcaptualoperation.getFuser().getFid());
						break;

					case 登陆名:
						if (fvirtualcaptualoperation.getFuser() != null)
							e.setCell(filed.ordinal(), fvirtualcaptualoperation.getFuser()
									.getFloginName());
						break;
					case 会员昵称:
						if (fvirtualcaptualoperation.getFuser() != null)
							e.setCell(filed.ordinal(), fvirtualcaptualoperation.getFuser()
									.getFnickName());
						break;
					case 会员真实姓名:
						if (fvirtualcaptualoperation.getFuser() != null)
							e.setCell(filed.ordinal(), fvirtualcaptualoperation.getFuser()
									.getFrealName());
						break;
					case 虚拟币类型:
						e.setCell(filed.ordinal(), fvirtualcaptualoperation.getFvirtualcointype().getFname());
						break;
					case 状态:
						e.setCell(filed.ordinal(), fvirtualcaptualoperation.getFstatus_s());
						break;
					case 交易类型:
						e.setCell(filed.ordinal(), fvirtualcaptualoperation.getFtype_s());
						break;
					case 数量:
						e.setText(filed.ordinal(), fvirtualcaptualoperation.getFamount());
						break;
					case 手续费:
						e.setText(filed.ordinal(), fvirtualcaptualoperation.getFfees());
						break;
					case 提现地址:
						e.setCell(filed.ordinal(), fvirtualcaptualoperation.getWithdraw_virtual_address());
						break;
					case 创建时间:
						e.setCell(filed.ordinal(), fvirtualcaptualoperation.getFcreateTime());
						break;
					case 最后修改时间:
						e.setCell(filed.ordinal(), fvirtualcaptualoperation.getFlastUpdateTime());
						break;
					default:
						break;
				}
			}
		}

		e.exportXls(response);
		response.getOutputStream().close();

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "导出成功");
		return modelAndView;
	}

	private java.util.List<Fvirtualcaptualoperation> getVirtualCapitalOutSucList() {

// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");

		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 and ftype="
				+ VirtualCapitalOperationTypeEnum.COIN_OUT + "\n");
		filterSQL.append("and fstatus IN("
				+ VirtualCapitalOperationOutStatusEnum.OperationSuccess +")\n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append(" and fuser.fid =" + fid + "\n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");
			}

		}

       if(request.getParameter("ftype")!=null) {
		   int type = Integer.parseInt(request.getParameter("ftype"));
		   if (type != 0) {
			   filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
		   }
	   }

		//添加日期查询条件
		String startDate = request.getParameter("startDate");
		if(startDate  != null && startDate .trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(flastUpdateTime,'%Y-%m-%d') >= '"+startDate +"' \n");

		}

		String endDate = request.getParameter("endDate");
		if(endDate != null && endDate.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(flastUpdateTime,'%Y-%m-%d') <= '"+endDate+"' \n");

		}


		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		}



		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService
				.list(0, 0, filterSQL
						+ "", false);
		return list;

	}


	//虚拟币提币状态修改
	@SysLog(code = ModuleConstont.VIRTUAL_OPERATION, method = "虚拟币提现状态修改")
	@RequestMapping("ssadmin/goVirtualCapitaloperationChangeStatus")
	public ModelAndView goVirtualCapitaloperationChangeStatus(
			@RequestParam(required = true) Integer type,
			@RequestParam(required = true) Integer uid) throws Exception {

		String cancelReason = request.getParameter("cancelReason");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		Fvirtualcaptualoperation fvirtualcaptualoperation = this.virtualCapitaloperationService
				.findById(uid);
		fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp());

		int userId = fvirtualcaptualoperation.getFuser().getFid();
		Fvirtualcointype fvirtualcointype = fvirtualcaptualoperation
				.getFvirtualcointype();
		int coinTypeId = fvirtualcointype.getFid();
		List<Fvirtualwallet> virtualWallet = this.virtualWalletService
				.findByTwoProperty("fuser.fid", userId, "fvirtualcointype.fid",
						coinTypeId);
		if (virtualWallet == null || virtualWallet.size() == 0) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核失败，会员虚拟币钱包信息异常!");
			return modelAndView;
		}
		Fvirtualwallet virtualWalletInfo = virtualWallet.get(0);

		int status = fvirtualcaptualoperation.getFstatus();
		String tips = "";
		switch (type) {
		case 1:
			tips = "锁定";
			if (status != CapitalOperationOutStatus.WaitForOperation) {
				modelAndView.addObject("statusCode", 300);
				String status_s = CapitalOperationOutStatus
						.getEnumString(CapitalOperationOutStatus.WaitForOperation);
				modelAndView.addObject("message", "锁定失败,只有状态为:‘" + status_s
						+ "’的充值记录才允许锁定!");
				return modelAndView;
			}
			fvirtualcaptualoperation
					.setFstatus(VirtualCapitalOperationOutStatusEnum.OperationLock);
			break;
		case 2:
			tips = "取消锁定";
			if (status != CapitalOperationOutStatus.OperationLock) {
				modelAndView.addObject("statusCode", 300);
				String status_s = CapitalOperationOutStatus
						.getEnumString(CapitalOperationOutStatus.OperationLock);
				modelAndView.addObject("message", "取消锁定失败,只有状态为:‘" + status_s
						+ "’的充值记录才允许取消锁定!");
				return modelAndView;
			}
			fvirtualcaptualoperation
					.setFstatus(VirtualCapitalOperationOutStatusEnum.WaitForOperation);
			break;
		case 3:
			tips = "取消提现";
			if(StringUtils.isEmpty(cancelReason)){
				return getWebRequestError(modelAndView,"取消原因不能为空");
			}
			if (status == CapitalOperationOutStatus.Cancel) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "取消提现失败,该记录已处于取消状态!");
				return modelAndView;
			}

			fvirtualcaptualoperation.setCancelReason(cancelReason);
			double fee = fvirtualcaptualoperation.getFfees();
			double famount = fvirtualcaptualoperation.getFamount();
			fvirtualcaptualoperation
					.setFstatus(VirtualCapitalOperationOutStatusEnum.Cancel);
			virtualWalletInfo.setFfrozen(virtualWalletInfo.getFfrozen() - fee
					- famount);
			virtualWalletInfo.setFtotal(virtualWalletInfo.getFtotal() + fee
					+ famount);
			virtualWalletInfo.setFlastUpdateTime(Utils.getTimestamp());
			modelAndView.addObject("callbackType", "closeCurrent");
			break;
		}

		boolean flag = false;
		try {
			this.virtualCapitaloperationService
					.updateObj(fvirtualcaptualoperation);
			this.virtualWalletService.updateObj(virtualWalletInfo);
			flag = true;

            if(type == 3 && StringUtils.isNotBlank(cancelReason)){
				sendMessage(fvirtualcaptualoperation.getFuser(),"您有一笔虚拟货币提现申请已被管理员取消(操作id:"+fvirtualcaptualoperation.getFid()+",取消原因："+cancelReason+"),若有疑问请致电400-900-6615");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (flag) {
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", tips + "成功！");
		} else {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "未知错误，请刷新列表后重试！");
		}

		return modelAndView;
	}

    //虚拟币页面跳转
	@RequestMapping("ssadmin/goVirtualCapitaloperationJSP")
	public ModelAndView goVirtualCapitaloperationJSP() throws Exception {

		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(url);
		String xid = "";
		Fvirtualcaptualoperation virtualcaptualoperation =  null ;
		BTCMessage msg = new BTCMessage();
		if (request.getParameter("uid") != null) {
			int fid = Integer.parseInt(request.getParameter("uid"));
			virtualcaptualoperation = this.virtualCapitaloperationService
					.findById(fid);
			Fadmin admin = (Fadmin) request.getSession().getAttribute("login_admin");
			Fauditprocess fauditprocess = this.auditProcessService.findByftype(AuditProcessEnum.VIRTUAL_OUT);
			int process = getAuditProcess(virtualcaptualoperation.getFstatus(),admin,AuditProcessEnum.VIRTUAL_OUT,fauditprocess);


			xid = virtualcaptualoperation.getFtradeUniqueNumber();
			msg.setACCESS_KEY(virtualcaptualoperation.getFvirtualcointype()
					.getFaccess_key());
			msg.setIP(virtualcaptualoperation.getFvirtualcointype().getFip());
			msg.setPORT(virtualcaptualoperation.getFvirtualcointype()
					.getFport());
			msg.setSECRET_KEY(virtualcaptualoperation.getFvirtualcointype()
					.getFsecrt_key());
			modelAndView.addObject("virtualCapitaloperation",
					virtualcaptualoperation);
			modelAndView.addObject("process",process);
			modelAndView.addObject("fauditprocess",fauditprocess);
		}
		if (request.getParameter("type") != null
				&& request.getParameter("type").equals("ViewStatus")
				&&virtualcaptualoperation!=null
				) {
			//确认数
			if(virtualcaptualoperation.getFvirtualcointype().isFisEth() == true ){
				ETHUtils ethUtils = new ETHUtils(msg);
				BTCInfo btcInfo = ethUtils.eth_getTransactionByHashValue(xid) ;
				modelAndView.addObject("confirmations", btcInfo.getConfirmations());
			}else{
				BTCUtils btc = new BTCUtils(msg);
				BTCInfo btcInfo = btc.gettransactionValue(xid, "send");
				modelAndView.addObject("confirmations", btcInfo.getConfirmations());
			}
		}
		return modelAndView;
	}

    //虚拟币提币审核
	@SysLog(code = ModuleConstont.VIRTUAL_OPERATION, method = "虚拟币提现审核")
	@RequestMapping("ssadmin/virtualCapitalOutAudit")
	public ModelAndView virtualCapitalOutAudit() throws Exception {


		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("uid"));
		String loginpwd = request.getParameter("floginpassword");
		Fvirtualcaptualoperation virtualcaptualoperation = this.virtualCapitaloperationService.findById(fid);
//		int status = virtualcaptualoperation.getFstatus();
//		if (status != VirtualCapitalOperationOutStatusEnum.OperationLock) {
//			modelAndView.addObject("statusCode", 300);
//			String status_s = VirtualCapitalOperationOutStatusEnum
//					.getEnumString(VirtualCapitalOperationOutStatusEnum.OperationLock);
//			modelAndView.addObject("message", "审核失败,只有状态为:" + status_s
//					+ "的提现记录才允许审核!");
//			return modelAndView;
//		}

		Fadmin admin = (Fadmin) request.getSession().getAttribute("login_admin");
		Fauditprocess fauditprocess = this.auditProcessService.findByftype(AuditProcessEnum.VIRTUAL_OUT);

        if(fauditprocess.getfIsneedPwd() == 1){
           if(StringUtils.isEmpty(loginpwd)){
			   modelAndView.addObject("statusCode", -1);
			   modelAndView.addObject("message", "该操作需要输入确认密码");
			   return modelAndView;
		   }
           if(!Utils.MD5(loginpwd,admin.getSalt()).equals(admin.getFpassword())){
               modelAndView.addObject("statusCode",-1);
               modelAndView.addObject("message", "确认密码不正确");
               return modelAndView;
           }
		}
		int result = getAuditProcess(virtualcaptualoperation.getFstatus(),admin,AuditProcessEnum.VIRTUAL_OUT,fauditprocess);
		if(result < 0){
			String message = "";
			if(result == -1){
				message = "没有配置审核权限";
			}else if(result == -2){
				message = "当前状态不正确";
			}else{
				message = "审核失败，没有当前状态审核权限";
			}
			modelAndView.addObject("statusCode", result);
			modelAndView.addObject("message", message);
			return modelAndView;
		}

		if(result != VirtualCapitalOperationOutStatusEnum.OperationSuccess){
			// 充值操作
			virtualcaptualoperation.setFstatus(result);
			virtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp());
			try {
				this.virtualCapitaloperationService.updateObj(virtualcaptualoperation);
			} catch (Exception e) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", e.getMessage());
				return modelAndView;
			}
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("callbackType", "closeCurrent");
			modelAndView.addObject("message", "操作成功");
			return modelAndView;
		}



		// 根据用户查钱包最后修改时间
		int userId = virtualcaptualoperation.getFuser().getFid();
		Fvirtualcointype fvirtualcointype = virtualcaptualoperation
				.getFvirtualcointype();
		int coinTypeId = fvirtualcointype.getFid();

		Fvirtualwallet virtualWalletInfo = frontUserService.findVirtualWalletByUser(userId, coinTypeId);
		double amount = Utils.getDouble(Utils.add(virtualcaptualoperation.getFamount(),virtualcaptualoperation.getFfees()), 4);
		double frozenRmb = Utils.getDouble(virtualWalletInfo.getFfrozen(), 4);
		LOGGER.info(CLASS_NAME + " virtualCapitalOutAudit,虚拟币提现审核，冻结数量:{}，提现数量:{}", frozenRmb, amount);
		if (frozenRmb-amount < -0.0001) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核失败,冻结数量:" + frozenRmb
					+ "小于提现数量:" + amount + "，操作异常!");
			return modelAndView;
		}

		String password = request.getParameter("fpassword");
		if(password == null || password.trim().length() ==0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "请输入审核密码!");
			return modelAndView;
		}

		modelAndView = withdraw(modelAndView, virtualWalletInfo, password, virtualcaptualoperation, fvirtualcointype, userId);
		if(modelAndView.getModel().get("statusCode").toString().equals("300")) {
			return modelAndView;
		}

		modelAndView.addObject("callbackType", "closeCurrent");
		return modelAndView;
	}



    //虚拟币充值审核
	@SysLog(code = ModuleConstont.VIRTUAL_OPERATION, method = "虚拟币充值审核")
	@RequestMapping("ssadmin/auditVCInlog")
	public ModelAndView auditVCInlog() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fvirtualcaptualoperation virtualcaptualoperation = this.virtualCapitaloperationService.findById(fid);
		int status = virtualcaptualoperation.getFstatus();
		int type = virtualcaptualoperation.getFtype();

		if (status == VirtualCapitalOperationInStatusEnum.SUCCESS || type != VirtualCapitalOperationTypeEnum.COIN_IN) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "充值记录已自动审核或该记录非充值记录！");
			return modelAndView;
		}

		if (status == VirtualCapitalOperationInStatusEnum.WAIT_0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "充值记录状态为0确认，不能审核！");
			return modelAndView;
		}

		Fadmin admin = (Fadmin) request.getSession().getAttribute("login_admin");
		Fauditprocess fauditprocess = this.auditProcessService.findByftype(AuditProcessEnum.VIRTUAL_IN);

		String loginpwd = request.getParameter("floginpassword");

		if(fauditprocess.getfIsneedPwd() == 1){
			if(StringUtils.isEmpty(loginpwd)){
				return getWebRequestError(modelAndView, "该操作需要输入确认密码");
			}
			if(!Utils.MD5(loginpwd,admin.getSalt()).equals(admin.getFpassword())){
				return getWebRequestError(modelAndView, "确认密码不正确");
			}
		}

		int result = getAuditProcess(virtualcaptualoperation.getFstatus(),admin,AuditProcessEnum.VIRTUAL_IN,fauditprocess);
		if(result <= 0){
			String message = "";
			if(result == -1){
				message = "没有配置审核权限";
			}else if(result == -2){
				message = "当前状态不正确";
			}else{
				message = "审核失败，没有当前状态审核权限";
			}
			modelAndView.addObject("statusCode", result);
			modelAndView.addObject("message", message);
			return modelAndView;
		}

		if(result != VirtualCapitalOperationInStatusEnum.SUCCESS){
			// 修改状态
			virtualcaptualoperation.setFstatus(result);
			virtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp());
			try {
				this.virtualCapitaloperationService.updateObj(virtualcaptualoperation);
			} catch (Exception e) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", e.getMessage());
				return modelAndView;
			}
			if(fauditprocess.getfIsneedPwd() == 1){
				modelAndView.addObject("callbackType", "closeCurrent");
			}
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", "操作成功");
			return modelAndView;
		}

		Fvirtualcointype fvirtualcointype = virtualcaptualoperation
				.getFvirtualcointype();
		int coinTypeId = fvirtualcointype.getFid();
		// 根据用户查钱包最后修改时间
		int userId = virtualcaptualoperation.getFuser().getFid();
		Fvirtualwallet virtualWalletInfo = frontUserService.findVirtualWalletByUser(userId, coinTypeId);
		// 钱包操作
		virtualWalletInfo.setFlastUpdateTime(Utils.getTimestamp());
		virtualWalletInfo.setFtotal(virtualWalletInfo.getFtotal()+virtualcaptualoperation.getFamount());
		
		
		virtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.SUCCESS);
		try {
			this.virtualCapitaloperationService.updateCapital(
					virtualcaptualoperation, virtualWalletInfo);

		} catch (Exception e) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "网络异常");
			return modelAndView;
		}

		//发送短信通知
		try{
			String content  = "您于"+ DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond)+"成功充值"+virtualcaptualoperation.getFvirtualcointype().getFname()+"币"+virtualcaptualoperation.getFamount()+"个，若有疑问请致电400-900-6615";
			sendMessage(userId,content);
		}catch (Exception e){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核通过，短信发送失败");
			return modelAndView;
		}
		//送积分
		try{
			Ftrademapping ftrademapping = this.ftradeMappingService.findActiveTradeMappingByVB(fvirtualcointype);
			double price = this.realTimeData.getLatestDealPrize(ftrademapping.getFid());
			double money = price*virtualcaptualoperation.getFamount();
			this.integralService.addUserIntegral(IntegralTypeEnum.RECHARGE_BTC,userId,money,virtualcaptualoperation.getFid());
	//		this.integralService.addUserIntegral(IntegralTypeEnum.EVERDAY_ASSES,userId,0,0);
		}catch (Exception e){
			LOG.e(this.getClass(),"送积分失败");
		}

		if(fauditprocess.getfIsneedPwd() == 1){
			modelAndView.addObject("callbackType", "closeCurrent");
		}


		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "审核成功");
		return modelAndView;
	}

}
