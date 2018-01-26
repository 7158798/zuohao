package com.ruizton.main.controller.admin;

import com.ruizton.main.Enum.*;
import com.ruizton.main.aspect.SysLog;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.*;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.CapitaloperationService;
import com.ruizton.main.service.admin.VirtualCapitaloperationService;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontValidateService;
import com.ruizton.util.DateHelper;
import com.ruizton.util.JsonHelper;
import com.ruizton.util.Utils;
import com.ruizton.util.XlsExport;
import net.chrone.api.ChroneApi;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CapitaloperationController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CapitaloperationController.class);

	private static final String CLASS_NAME = CapitaloperationController.class.getSimpleName();





	// 每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();

	@RequestMapping("/ssadmin/capitaloperationList")
	public ModelAndView Index() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/capitaloperationList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String capitalId = request.getParameter("capitalId");
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
				filterSQL.append("and (fBank like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.floginName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fAccount like '%" + keyWord + "%' OR \n");
				filterSQL.append("fPhone like '%" + keyWord + "%' OR \n");
				filterSQL.append("fPayee like '%" + keyWord + "%' OR \n");
				filterSQL.append("famount like '%" + keyWord + "%') \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		String logDate = request.getParameter("logDate");
		if(logDate != null && logDate.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') = '"+logDate+"' \n");
			modelAndView.addObject("logDate", logDate);
		}

		if (capitalId != null && capitalId.trim().length() > 0) {
			boolean flag = Utils.isNumeric(capitalId);
			if (flag) {
				filterSQL.append("AND fid =" + capitalId + " \n");
				modelAndView.addObject("capitalId", capitalId);
			}
		}

		String status = request.getParameter("fstatus");
		if (status != null && status.trim().length() > 0) {
			String fstatus = status.trim();
			if (!fstatus.equals("0")) {
				if (fstatus.indexOf("充值") != -1) {
					filterSQL.append("AND ftype ="
							+ CapitalOperationTypeEnum.RMB_IN + " \n");
					filterSQL.append("AND fstatus ="
							+ fstatus.replace("充值-", "") + " \n");
				} else if (fstatus.indexOf("提现") != -1) {
					filterSQL.append("AND ftype ="
							+ CapitalOperationTypeEnum.RMB_OUT + " \n");
					filterSQL.append("AND fstatus ="
							+ fstatus.replace("提现-", "") + " \n");
				}
			}
			modelAndView.addObject("fstatus", fstatus);
		} else {
			modelAndView.addObject("fstatus", "0");
		}

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

		Map<String,String> statusMap = new HashMap<String,String>();
		statusMap.put("0", "全部");
		statusMap.put(
				"充值-" + CapitalOperationInStatus.Come,
				"充值-"
						+ CapitalOperationInStatus
								.getEnumString(CapitalOperationInStatus.Come));
		statusMap
				.put("充值-" + CapitalOperationInStatus.Invalidate,
						"充值-"
								+ CapitalOperationInStatus
										.getEnumString(CapitalOperationInStatus.Invalidate));
		statusMap
				.put("充值-" + CapitalOperationInStatus.NoGiven,
						"充值-"
								+ CapitalOperationInStatus
										.getEnumString(CapitalOperationInStatus.NoGiven));
		statusMap
				.put("充值-" + CapitalOperationInStatus.WaitForComing,
						"充值-"
								+ CapitalOperationInStatus
										.getEnumString(CapitalOperationInStatus.WaitForComing));
		statusMap
				.put("提现-" + CapitalOperationOutStatus.Cancel,
						"提现-"
								+ CapitalOperationOutStatus
										.getEnumString(CapitalOperationOutStatus.Cancel));
		statusMap
				.put("提现-" + CapitalOperationOutStatus.OperationLock,
						"提现-"
								+ CapitalOperationOutStatus
										.getEnumString(CapitalOperationOutStatus.OperationLock));
		statusMap
				.put("提现-" + CapitalOperationOutStatus.OperationSuccess,
						"提现-"
								+ CapitalOperationOutStatus
										.getEnumString(CapitalOperationOutStatus.OperationSuccess));
		statusMap
				.put("提现-" + CapitalOperationOutStatus.WaitForOperation,
						"提现-"
								+ CapitalOperationOutStatus
										.getEnumString(CapitalOperationOutStatus.WaitForOperation));
		modelAndView.addObject("statusMap", statusMap);

		List<Fcapitaloperation> list = this.capitaloperationService.list(
				(currentPage - 1) * numPerPage, numPerPage, filterSQL + "",
				true);
		modelAndView.addObject("capitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "capitaloperationList");
		// 总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fcapitaloperation", filterSQL+ ""));
		return modelAndView;
	}

	@RequestMapping("/ssadmin/capitalInList")
	public ModelAndView capitalInList() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		String type = "(" + CapitalOperationTypeEnum.RMB_IN + ")";
//		String status = "(" + CapitalOperationInStatus.WaitForComing + ")";

		String status = request.getParameter("fstatus");


		modelAndView.setViewName("ssadmin/capitalInList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String capitalId = request.getParameter("capitalId");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");

		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filterSQL = new StringBuffer();
		//filterSQL.append("where 1=1 and fremittanceType<>'"+RemittanceTypeEnum.getEnumString(RemittanceTypeEnum.Type3)+"' and fremittanceType<>'"+RemittanceTypeEnum.getEnumString(RemittanceTypeEnum.Type4)+"' \n");
		filterSQL.append(" where  1=1 ");
		filterSQL.append("and ftype IN " + type + "\n");
		if(StringUtils.isEmpty(status) || status.equals("0")){

			filterSQL.append("AND fstatus IN (" + CapitalOperationInStatus.WaitForComing +","
					+ CapitalOperationInStatus.OneTimeAudit +","
					+ CapitalOperationInStatus.TwoTimeAudit + ") \n");
		}else{
			filterSQL.append("AND fstatus = "+status+"\n");
		}


		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append("and (fBank like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.floginName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fAccount like '%" + keyWord + "%' OR \n");
				filterSQL.append("fPhone like '%" + keyWord + "%' OR \n");
				filterSQL.append("fPayee like '%" + keyWord + "%' OR \n");
				filterSQL.append("famount like '%" + keyWord + "%') \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		String logDate = request.getParameter("logDate");
		if(logDate != null && logDate.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') = '"+logDate+"' \n");
			modelAndView.addObject("logDate", logDate);
		}

		if (capitalId != null && capitalId.trim().length() > 0) {
			boolean flag = Utils.isNumeric(capitalId);
			if (flag) {
				filterSQL.append(" AND fid =" + capitalId + "\n");
				modelAndView.addObject("capitalId", capitalId);
			}
		}
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
		
		List<Fcapitaloperation> list = this.capitaloperationService.list(
				(currentPage - 1) * numPerPage, numPerPage, filterSQL + "",
				true);

		//获取是否需要登录密码
		Fauditprocess fauditprocess = this.auditProcessService.findByftype(AuditProcessEnum.CAPITAL_IN);
		int isneedpwd = fauditprocess == null?0:fauditprocess.getfIsneedPwd();
		modelAndView.addObject("isneedpwd",isneedpwd);

		modelAndView.addObject("capitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "capitalInList");
		modelAndView.addObject("status",status);
		// 总数量
		modelAndView.addObject(
				"totalCount",
				this.adminService.getAllCount("Fcapitaloperation", filterSQL
						+ ""));
		return modelAndView;
	}
	
	@RequestMapping("/ssadmin/capitalInSucList")
	public ModelAndView capitalInSucList() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		String type = "(" + CapitalOperationTypeEnum.RMB_IN + ")";
		String status = "(" + CapitalOperationInStatus.Come + ")";
		modelAndView.setViewName("ssadmin/capitalInSucList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String capitalId = request.getParameter("capitalId");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");

		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 \n");
		filterSQL.append("and ftype IN " + type + "\n");
		filterSQL.append("AND fstatus IN " + status + "\n");

		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append("and (fBank like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.floginName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fAccount like '%" + keyWord + "%' OR \n");
				filterSQL.append("fPhone like '%" + keyWord + "%' OR \n");
				filterSQL.append("fPayee like '%" + keyWord + "%' OR \n");
				filterSQL.append("famount like '%" + keyWord + "%') \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		


		//添加日期查询条件
		String startDate = request.getParameter("startDate");
		if(startDate  != null && startDate .trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') >= '"+startDate +"' \n");
			modelAndView.addObject("startDate", startDate );
		}

		String endDate = request.getParameter("endDate");
		if(endDate != null && endDate.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') <= '"+endDate+"' \n");
			modelAndView.addObject("endDate", endDate);
		}
		if (capitalId != null && capitalId.trim().length() > 0) {
			boolean flag = Utils.isNumeric(capitalId);
			if (flag) {
				filterSQL.append(" AND fid =" + capitalId + "\n");
				modelAndView.addObject("capitalId", capitalId);
			}
		}
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
		
		List<Fcapitaloperation> list = this.capitaloperationService.list(
				(currentPage - 1) * numPerPage, numPerPage, filterSQL + "",
				true);
		modelAndView.addObject("capitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "capitalInSucList");
		// 总数量
		modelAndView.addObject(
				"totalCount",
				this.adminService.getAllCount("Fcapitaloperation", filterSQL
						+ ""));

		List<Fcapitaloperation> list1=	getCapitalInSucList();
		double totalDeposit=0;
		double totalFees=0;
		for( Fcapitaloperation fcapitaloperation: list1 ){
			totalDeposit+=fcapitaloperation.getFamount();
			totalFees+=fcapitaloperation.getFfees();
		}
		modelAndView.addObject("totalDeposit",totalDeposit);
		modelAndView.addObject("totalFees",totalFees);

		return modelAndView;
	}


	private static enum Export3Filed {
		会员UID,订单ID,登陆名, 会员昵称, 会员真实姓名,  状态,充值方式, 金额, 手续费, 官方帐号银行, 官方帐号,创建时间,最后修改时间;
	}
	//成功充值人民币列表导出
	@RequestMapping("/ssadmin/capitalInSucListExport")

	public ModelAndView capitalInSucListExport(HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition", "attachment;filename=capitalInSucList.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (CapitaloperationController.Export3Filed field : CapitaloperationController.Export3Filed.values()) {
			e.setCell(field.ordinal(), field.toString());
		}

		//

		List<Fcapitaloperation> lists=	getCapitalInSucList();

		for (Fcapitaloperation fcapitaloperation : lists) {
			e.createRow(rowIndex++);
			for (CapitaloperationController.Export3Filed filed : CapitaloperationController.Export3Filed.values()) {
				switch (filed) {
					case 会员UID:
						if (fcapitaloperation.getFuser() != null)
							e.setCell(filed.ordinal(),  fcapitaloperation.getFuser().getFid());
						break;
					case 订单ID:
						e.setCell(filed.ordinal(),  fcapitaloperation.getFid());
					case 登陆名:
						if (fcapitaloperation.getFuser() != null)
							e.setCell(filed.ordinal(), fcapitaloperation.getFuser()
									.getFloginName());
						break;
					case 会员昵称:
						if (fcapitaloperation.getFuser() != null)
							e.setCell(filed.ordinal(), fcapitaloperation.getFuser()
									.getFnickName());
						break;
					case 会员真实姓名:
						if (fcapitaloperation.getFuser() != null)
							e.setCell(filed.ordinal(), fcapitaloperation.getFuser()
									.getFrealName());
						break;
					case 状态:
						e.setCell(filed.ordinal(), fcapitaloperation.getFstatus_s());
						break;
					case 充值方式:
						e.setCell(filed.ordinal(),fcapitaloperation.getFremittanceType());
						break;
					case 金额:
						e.setText(filed.ordinal(), fcapitaloperation.getFamount());
						break;
					case 手续费:
						e.setText(filed.ordinal(), fcapitaloperation.getFfees());
						break;
					case 官方帐号银行:
						if(fcapitaloperation.getSystembankinfo()!=null) {
							e.setCell(filed.ordinal(), fcapitaloperation.getSystembankinfo().getFbankName());
						}
						break;
					case 官方帐号:
						if(fcapitaloperation.getSystembankinfo()!=null) {
							e.setCell(filed.ordinal(), fcapitaloperation.getSystembankinfo().getFbankNumber());
						}
						break;
					case 创建时间:
						e.setCell(filed.ordinal(), fcapitaloperation.getFcreateTime());
						break;
					case 最后修改时间:
						e.setCell(filed.ordinal(), fcapitaloperation.getfLastUpdateTime());
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

	private java.util.List<Fcapitaloperation> getCapitalInSucList() {
// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String capitalId = request.getParameter("capitalId");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");

		String type = "(" + CapitalOperationTypeEnum.RMB_IN + ")";
		String status = "(" + CapitalOperationInStatus.Come + ")";

		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 \n");
		filterSQL.append("and ftype IN " + type + "\n");
		filterSQL.append("AND fstatus IN " + status + "\n");

		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append("and (fBank like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.floginName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fAccount like '%" + keyWord + "%' OR \n");
				filterSQL.append("fPhone like '%" + keyWord + "%' OR \n");
				filterSQL.append("fPayee like '%" + keyWord + "%' OR \n");
				filterSQL.append("famount like '%" + keyWord + "%') \n");
			}

		}



		//添加日期查询条件
		String startDate = request.getParameter("startDate");
		if(startDate  != null && startDate .trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') >= '"+startDate +"' \n");

		}

		String endDate = request.getParameter("endDate");
		if(endDate != null && endDate.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') <= '"+endDate+"' \n");

		}
		if (capitalId != null && capitalId.trim().length() > 0) {
			boolean flag = Utils.isNumeric(capitalId);
			if (flag) {
				filterSQL.append(" AND fid =" + capitalId + "\n");

			}
		}
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

		List<Fcapitaloperation> list = this.capitaloperationService.list(
				0,0, filterSQL + "",
				false);

       return list;
	}

	@RequestMapping("/ssadmin/capitalOutList")
	public ModelAndView capitalOutList() throws Exception {
		String type = "(" + CapitalOperationTypeEnum.RMB_OUT + ")";
//		String status = "(" + CapitalOperationOutStatus.WaitForOperation + ","
//				+ CapitalOperationOutStatus.OperationLock + ")";
		String status = request.getParameter("fstatus");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/capitalOutList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String capitalId = request.getParameter("capitalId");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}

		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 \n");
		filterSQL.append("and ftype IN " + type + "\n");

		if(StringUtils.isEmpty(status) || status.equals("0")){

			filterSQL.append("AND fstatus IN (" + CapitalOperationOutStatus.WaitForOperation +","
					+ CapitalOperationOutStatus.OperationLock +","
					+ CapitalOperationOutStatus.OneTimeAudit +","
					+ CapitalOperationOutStatus.TwoTimeAudit + ") \n");
		}else{
			filterSQL.append("AND fstatus = "+status+"\n");
		}
//		filterSQL.append("AND fstatus IN " + status + "\n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append(" AND (fBank like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.floginName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fAccount like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fPhone like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fPayee like '%" + keyWord + "%' OR \n");
				filterSQL.append(" famount like '%" + keyWord + "%') \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		String logDate = request.getParameter("logDate");
		if(logDate != null && logDate.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') = '"+logDate+"' \n");
			modelAndView.addObject("logDate", logDate);
		}

		if (capitalId != null && capitalId.trim().length() > 0) {
			boolean flag = Utils.isNumeric(capitalId);
			if (flag) {
				filterSQL.append(" AND fid =" + capitalId + "\n");
				modelAndView.addObject("capitalId", capitalId);
			}
		}
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
		
		List<Fcapitaloperation> list = this.capitaloperationService.list(
				(currentPage - 1) * numPerPage, numPerPage, filterSQL + "",
				true);

		//获取是否需要登录密码
		Fauditprocess fauditprocess = this.auditProcessService.findByftype(AuditProcessEnum.CAPITAL_OUT);
		int isneedpwd = fauditprocess == null?0:fauditprocess.getfIsneedPwd();
		modelAndView.addObject("isneedpwd",isneedpwd);

		modelAndView.addObject("capitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "capitalOutList");
		modelAndView.addObject("status",status);
		// 总数量
		modelAndView.addObject(
				"totalCount",
				this.adminService.getAllCount("Fcapitaloperation", filterSQL
						+ ""));
		return modelAndView;
	}
	
	@RequestMapping("/ssadmin/capitalOutSucList")
	public ModelAndView capitalOutSucList() throws Exception {
		String type = "(" + CapitalOperationTypeEnum.RMB_OUT + ")";
		String status = "(" + CapitalOperationOutStatus.OperationSuccess + ","
				+ CapitalOperationOutStatus.OperationLock + ")";
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/capitalOutSucList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String capitalId = request.getParameter("capitalId");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}

		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 \n");
		filterSQL.append("and ftype IN " + type + "\n");
		filterSQL.append("AND fstatus IN " + status + "\n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append(" AND (fBank like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.floginName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fAccount like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fPhone like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fPayee like '%" + keyWord + "%' OR \n");
				filterSQL.append(" famount like '%" + keyWord + "%') \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}

		//添加日期查询条件
		String startDate = request.getParameter("startDate");
		if(startDate  != null && startDate .trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fLastUpdateTime,'%Y-%m-%d') >= '"+startDate +"' \n");
			modelAndView.addObject("startDate", startDate );
		}

		String endDate = request.getParameter("endDate");
		if(endDate != null && endDate.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fLastUpdateTime,'%Y-%m-%d') <= '"+endDate+"' \n");
			modelAndView.addObject("endDate", endDate);
		}


		if (capitalId != null && capitalId.trim().length() > 0) {
			boolean flag = Utils.isNumeric(capitalId);
			if (flag) {
				filterSQL.append(" AND fid =" + capitalId + "\n");
				modelAndView.addObject("capitalId", capitalId);
			}
		}
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
		
		List<Fcapitaloperation> list = this.capitaloperationService.list(
				(currentPage - 1) * numPerPage, numPerPage, filterSQL + "",
				true);
		modelAndView.addObject("capitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "capitalOutSucList");
		// 总数量
		modelAndView.addObject(
				"totalCount",
				this.adminService.getAllCount("Fcapitaloperation", filterSQL
						+ ""));

		//计算提现数量和手续费合计
		List<Fcapitaloperation> list1=getWithDrawRMB();
		double totalWithdraw=0;
		double totalFees=0;
		for( Fcapitaloperation fcapitaloperation: list1 ){
			totalWithdraw+=fcapitaloperation.getFamount();
			totalFees+=fcapitaloperation.getFfees();
		}
		modelAndView.addObject("totalWithdraw",totalWithdraw);
		modelAndView.addObject("totalFees",totalFees);
		return modelAndView;
	}

	@RequestMapping("ssadmin/goCapitaloperationJSP")
	public ModelAndView goCapitaloperationJSP() throws Exception {
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(url);
		if (request.getParameter("uid") != null) {
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fcapitaloperation capitaloperation = this.capitaloperationService
					.findById(fid);
			modelAndView.addObject("capitaloperation", capitaloperation);
		}
		return modelAndView;
	}

	//取消充值
	@RequestMapping("/ssadmin/capitalInCancel")
	@SysLog(code = ModuleConstont.CYN_OPERATION, method = "取消人民币充值")
	public ModelAndView capitalInCancel(@RequestParam(required = false) String cancelReason) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fcapitaloperation capitalOperation = this.capitaloperationService
				.findById(fid);
		int status = capitalOperation.getFstatus();
		if(StringUtils.isEmpty(cancelReason)){
			return getWebRequestError(modelAndView,"取消原因不能为空");
		}
		if (status == CapitalOperationInStatus.Come || status == CapitalOperationInStatus.Invalidate) {

			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "取消失败");
			return modelAndView;
		}
		capitalOperation.setCancelReason(cancelReason);
		capitalOperation.setFstatus(CapitalOperationInStatus.Invalidate);
		this.capitaloperationService.updateObj(capitalOperation);

		try{
			sendMessage(capitalOperation.getFuser().getFid(), "您的充值失败，失败原因："+cancelReason+",如有疑问请联系客服400-900-6615");
		}catch (Exception e){
            LOG.e(this.getClass(),"取消充值短信发送异常");
		}

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "取消成功");
		modelAndView.addObject("callbackType", "closeCurrent");
		return modelAndView;
	}

	/**
	 * 人民币充值审核
	 * @return
	 * @throws Exception
     */

	//充值审核
	@SysLog(code = ModuleConstont.CYN_OPERATION, method = "人民币充值审核")
	@RequestMapping("ssadmin/capitalInAudit")
	public ModelAndView capitalInAudit() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		LOGGER.info(CLASS_NAME + " capitalInAudit,人民币充值审核开始");
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fcapitaloperation capitalOperation = this.capitaloperationService
				.findById(fid);
		int status = capitalOperation.getFstatus();
		LOGGER.info(CLASS_NAME + " capitalInAudit,充值状态是：" + status);
		Fauditprocess fauditprocess = this.auditProcessService.findByftype(AuditProcessEnum.CAPITAL_IN);
		Fadmin admin = (Fadmin) request.getSession()
				.getAttribute("login_admin");

        String loginpwd = request.getParameter("floginpassword");

        if(fauditprocess.getfIsneedPwd() == 1){
            if(StringUtils.isEmpty(loginpwd)){
                return getWebRequestError(modelAndView, "该操作需要输入确认密码");
            }
            if(!Utils.MD5(loginpwd,admin.getSalt()).equals(admin.getFpassword())){
                return getWebRequestError(modelAndView, "确认密码不正确");
            }
        }

		int result = getAuditProcess(capitalOperation.getFstatus(),admin,AuditProcessEnum.CAPITAL_IN,fauditprocess);
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

		if(result != CapitalOperationInStatus.Come){
			// 修改状态
			capitalOperation.setFstatus(result);
			capitalOperation.setfLastUpdateTime(Utils.getTimestamp());
			try {
				this.capitaloperationService.updateObj(capitalOperation);
			} catch (Exception e) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", e.getMessage());
				return modelAndView;
			}
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", "操作成功");
			if(fauditprocess.getfIsneedPwd() == 1){
				modelAndView.addObject("callbackType", "closeCurrent");
			}
			return modelAndView;
		}


		LOGGER.info(CLASS_NAME + " capitalInAudit,根据会员用户id:{}查询钱包信息", capitalOperation.getFuser().getFid());
		// 根据用户查钱包最后修改时间
		Fvirtualwallet walletInfo = this.frontUserService.findWalletByUser(capitalOperation.getFuser().getFid());
		if (walletInfo == null) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核失败，会员钱包信息异常!");
			LOGGER.info(CLASS_NAME + " capitalInAudit,查询会员钱包信息为空");
			return modelAndView;
		}
		double amount = capitalOperation.getFamount();


		// 充值操作
		capitalOperation.setFstatus(CapitalOperationInStatus.Come);
		capitalOperation.setfLastUpdateTime(Utils.getTimestamp());
		capitalOperation.setfAuditee_id(admin);
		// 钱包操作
		walletInfo.setFlastUpdateTime(Utils.getTimestamp());
		walletInfo.setFtotal(walletInfo.getFtotal() + amount);

		boolean flag = false;
		try {
			LOGGER.info(CLASS_NAME + " capitalInAudit,修改充值状态，钱包金额信息开始");
			this.capitaloperationService.updateCapital(capitalOperation,
					walletInfo, true);
			LOGGER.info(CLASS_NAME + " capitalInAudit,修改充值状态，钱包金额信息结束");
			flag = true;
		} catch (Exception e) {
			LOGGER.info(CLASS_NAME + " capitalInAudit,修改充值状态，钱包金额信息异常,exception:{}",e);
			flag = false;
		}
		if (!flag) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核失败");
			return modelAndView;
		}

		//发送短信通知
		try{
			String content  = "您于"+ DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond)+"成功充值"+amount+"元，若有疑问请致电400-900-6615";
			sendMessage(capitalOperation.getFuser().getFid(),content);

			//增加活动积分
			integralService.addUserIntegral(IntegralTypeEnum.RECHARGE_RMB,capitalOperation.getFuser().getFid(),amount,capitalOperation.getFid());

			//充值===WEB首次送积分
			this.integralService.addUserIntegralFirst(IntegralTypeEnum.CAPTITALIN_FIRST,capitalOperation.getFuser().getFid(),0);
			if(fauditprocess.getfIsneedPwd() == 1){
				modelAndView.addObject("callbackType", "closeCurrent");
			}
		}catch (Exception e){
			LOG.e(this.getClass(),"审核成功，赠送积分以及积分出错");
			e.printStackTrace();
		}

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "审核成功");
		LOGGER.info(CLASS_NAME + " capitalInAudit,人民币充值审核结束");
		return modelAndView;
	}

	//人民币提现审核、锁定
	@RequestMapping("ssadmin/capitalOutAudit")
	@SysLog(code = ModuleConstont.CYN_OPERATION, method = "人民币提现状态修改")
	public ModelAndView capitalOutAudit(@RequestParam(required = true) Integer type)
			throws Exception {
		LOGGER.info(CLASS_NAME + " capitalOutAudit 人民币提现审核、锁定开始,type:{}", type );
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("uid"));
		LOGGER.info(CLASS_NAME + " capitalOutAudit fid:{}", fid );
		Fcapitaloperation capitalOperation = this.capitaloperationService
				.findById(fid);
		Fadmin admin = (Fadmin) request.getSession()
				.getAttribute("login_admin");
		int status = capitalOperation.getFstatus();
		Fauditprocess fauditprocess = null;
		String cancelReason = request.getParameter("cancelReason");

		int result = 0;
		LOGGER.info(CLASS_NAME + " capitalOutAudit 查询提现流水状态status:{}", status );
		switch (type) {
		case 1:
			fauditprocess = this.auditProcessService.findByftype(AuditProcessEnum.CAPITAL_OUT);
			result = getAuditProcess(status,admin,AuditProcessEnum.CAPITAL_OUT,fauditprocess);

			String loginpwd = request.getParameter("floginpassword");
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			if(fauditprocess.getfIsneedPwd() == 1){
				if(StringUtils.isEmpty(loginpwd)){
					return getWebRequestError(modelAndView, "该操作需要输入确认密码");
				}
				if(!Utils.MD5(loginpwd,admin.getSalt()).equals(admin.getFpassword())){
					return getWebRequestError(modelAndView, "确认密码不正确");
				}
			}
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
			break;
		case 2:
			if (status != CapitalOperationOutStatus.WaitForOperation) {
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				String status_s = CapitalOperationOutStatus
						.getEnumString(CapitalOperationOutStatus.WaitForOperation);
				modelAndView.addObject("message", "锁定失败,只有状态为:‘" + status_s
						+ "’的提现记录才允许锁定!");
				return modelAndView;
			}
			break;
		case 3:
			if (status != CapitalOperationOutStatus.OperationLock) {
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				String status_s = CapitalOperationOutStatus
						.getEnumString(CapitalOperationOutStatus.OperationLock);
				modelAndView.addObject("message", "取消锁定失败,只有状态为:‘" + status_s
						+ "’的提现记录才允许取消锁定!");
				return modelAndView;
			}
			break;
		case 4:
			if (status == CapitalOperationOutStatus.Cancel || status == CapitalOperationOutStatus.OperationSuccess) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "取消失败!");
				return modelAndView;
			}
			if(StringUtils.isEmpty(cancelReason)){
				return getWebRequestError(modelAndView,"取消原因不能为空");
			}
			break;
		default:
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "非法提交！");
			return modelAndView;
		}

		LOGGER.info(CLASS_NAME + " capitalOutAudit，根据用户id查询用户钱包开始");
		// 根据用户查钱包最后修改时间
		Fvirtualwallet walletInfo = this.frontUserService.findWalletByUser(capitalOperation.getFuser().getFid());
		LOGGER.info(CLASS_NAME + " capitalOutAudit，根据用户id查询用户钱包结束");
		if (walletInfo == null) {
			LOGGER.info(CLASS_NAME + " capitalOutAudit，查询用户钱包信息为空，用户id:{}", capitalOperation.getFuser().getFid());
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核失败，会员钱包信息异常!");
			return modelAndView;
		}



		double amount = capitalOperation.getFamount();
		double frees = capitalOperation.getFfees();
		double totalAmt = Utils.getDouble(Utils.add(amount , frees),2);
		LOGGER.info(CLASS_NAME + " capitalOutAudit 本次提现金额:{},提现手续费:{}", amount, frees);
		// 充值操作

		capitalOperation.setfLastUpdateTime(Utils.getTimestamp());
		capitalOperation.setfAuditee_id(admin);

		// 钱包操作//1审核,2锁定,3取消锁定,4取消提现
		String tips = "";
		switch (type) {
		case 1:
			if(result != CapitalOperationOutStatus.OperationSuccess){
				capitalOperation
						.setFstatus(result);
				capitalOperation.setfLastUpdateTime(Utils.getTimestamp());
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				try {
					this.capitaloperationService.updateObj(capitalOperation);
				} catch (Exception e) {
					modelAndView.addObject("statusCode", 300);
					modelAndView.addObject("message", e.getMessage());
					return modelAndView;
				}
				modelAndView.addObject("statusCode", 200);
				modelAndView.addObject("message", "操作成功");
				if(fauditprocess.getfIsneedPwd() == 1){
					modelAndView.addObject("callbackType", "closeCurrent");
				}
				return modelAndView;
			}
			capitalOperation
					.setFstatus(CapitalOperationOutStatus.OperationSuccess);

			walletInfo.setFlastUpdateTime(Utils.getTimestamp());
			walletInfo.setFfrozen(Utils.sub(walletInfo.getFfrozen() , totalAmt));
			tips = "审核";
			break;
		case 2:
			capitalOperation
					.setFstatus(CapitalOperationOutStatus.OperationLock);
			tips = "锁定";
			break;
		case 3:
			capitalOperation
					.setFstatus(CapitalOperationOutStatus.WaitForOperation);
			tips = "取消锁定";
			break;
		case 4:
			capitalOperation.setFstatus(CapitalOperationOutStatus.Cancel);
			walletInfo.setFlastUpdateTime(Utils.getTimestamp());
			walletInfo.setFfrozen(Utils.sub(walletInfo.getFfrozen() , totalAmt));
			walletInfo.setFtotal(Utils.add(walletInfo.getFtotal() , totalAmt));
			capitalOperation.setCancelReason(cancelReason);
			tips = "取消";
			modelAndView.addObject("callbackType", "closeCurrent");
			break;
		}

		boolean flag = false;
		try {
			LOGGER.info(CLASS_NAME + " capitalOutAudit 修改用户提现流水，用户钱包信息开始");
			this.capitaloperationService.updateCapital(capitalOperation,
					walletInfo, false);
			LOGGER.info(CLASS_NAME + " capitalOutAudit 修改用户提现流水，用户钱包信息结束");
			flag = true;
		} catch (Exception e) {
			LOGGER.info(CLASS_NAME + " capitalOutAudit 修改用户提现流水，用户钱包信息异常exception:{}", e);
			flag = false;
		}
		if (!flag) {
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", tips + "失败");
			return modelAndView;
		}

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", tips + "成功");
		if(fauditprocess!= null && fauditprocess.getfIsneedPwd() == 1){
			modelAndView.addObject("callbackType", "closeCurrent");
		}
		LOGGER.info(CLASS_NAME + " capitalOutAudit 人民币提现审核、锁定结束");
		if(type == 1){
			//发送短信通知
			try{
				String content  = "您于"+ DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond)+"成功提取"+amount+"元，将在24小时内到账，若有疑问请致电400-900-6615";
				sendMessage(capitalOperation.getFuser().getFid(),content);
			}catch (Exception e){
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "审核通过，短信发送失败");
				return modelAndView;
			}
		}
		if(type == 4){
			//发送短信通知
			try{
				String content  = "您有一笔人民币提现操作已被管理员取消(操作id:"+capitalOperation.getFid()+",取消原因:"+cancelReason+"),若有疑问请致电400-900-6615";
				sendMessage(capitalOperation.getFuser().getFid(),content);
			}catch (Exception e){
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "审核通过，短信发送失败");
				return modelAndView;
			}
		}
		return modelAndView;
	}

	private static enum ExportFiled {
		订单ID,会员UID,会员登陆名, 会员昵称, 会员真实姓名, 状态, 金额, 手续费, 备注, 创建时间, 最后修改时间, 审核人;
	}

	//充值列表
	private List<Fcapitaloperation> getCapitalOperationList(String type,
			String status) {
		String keyWord = request.getParameter("keywords");
		String capitalId = request.getParameter("capitalId");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 \n");
		if (type != null && type.trim().length() > 0) {
			filterSQL.append("and ftype IN " + type + "\n");
		}
		if (status != null && status.trim().length() > 0) {
			filterSQL.append("AND fstatus IN " + status + "\n");
		}
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append(" AND (fBank like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.floginName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fAccount like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fPhone like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fPayee like '%" + keyWord + "%' OR \n");
				filterSQL.append(" famount like '%" + keyWord + "%') \n");
			}
		}
		
		String logDate = request.getParameter("logDate");
		if(logDate != null && logDate.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') = '"+logDate+"' \n");
		}

		if (capitalId != null && capitalId.trim().length() > 0) {
			boolean flag = Utils.isNumeric(capitalId);
			if (flag) {
				filterSQL.append(" AND fid =" + capitalId + "\n");
			}
		}

		try {
			if (request.getParameter("fstatus") != null
					&& request.getParameter("fstatus").trim().length() > 0) {
				String fstatus = new String(request.getParameter("fstatus")
						.getBytes("iso8859-1"), "utf-8");
				if (!fstatus.equals("0")) {
					if (fstatus.indexOf("充值") != -1) {
						filterSQL.append("AND ftype ="
								+ CapitalOperationTypeEnum.RMB_IN + " \n");
						filterSQL.append("AND fstatus ="
								+ fstatus.replace("充值-", "") + " \n");
					} else if (fstatus.indexOf("提现") != -1) {
						filterSQL.append("AND ftype ="
								+ CapitalOperationTypeEnum.RMB_OUT + " \n");
						filterSQL.append("AND fstatus ="
								+ fstatus.replace("提现-", "") + " \n");
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

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
		List<Fcapitaloperation> list = this.capitaloperationService.list(0, 0,
				filterSQL + "", false);
		return list;
	}

	//人民币操作总表导出
	@RequestMapping("ssadmin/capitaloperationExport")
	public ModelAndView capitaloperationExport(HttpServletResponse response)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=capitaloperationList.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (ExportFiled filed : ExportFiled.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}
		String type = null;
		String status = null;
		List<Fcapitaloperation> capitalOperationList = getCapitalOperationList(
				type, status);
		for (Fcapitaloperation capitalOperation : capitalOperationList) {
			e.createRow(rowIndex++);
			for (ExportFiled filed : ExportFiled.values()) {
				switch (filed) {
				case 订单ID:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFid());
					break;
				case 会员UID:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser().getFid());
					break;
				case 会员登陆名:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser()
								.getFloginName());
					break;
				case 会员昵称:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser()
								.getFnickName());
					break;
				case 会员真实姓名:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser()
								.getFrealName());
					break;
				case 状态:
					e.setCell(filed.ordinal(), capitalOperation.getFstatus_s());
					break;
				case 金额:
					e.setCell(filed.ordinal(), capitalOperation.getFamount());
					break;
				case 手续费:
					e.setCell(filed.ordinal(), capitalOperation.getFfees());
					break;
				case 创建时间:
					e.setCell(filed.ordinal(),
							capitalOperation.getFcreateTime());
					break;
				case 最后修改时间:
					e.setCell(filed.ordinal(),
							capitalOperation.getfLastUpdateTime());
					break;
				case 审核人:
					if (capitalOperation.getfAuditee_id() != null)
						e.setCell(filed.ordinal(), capitalOperation
								.getfAuditee_id().getFname());
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


	//财务对账单导出
	@RequestMapping("ssadmin/financeExport")
	public ModelAndView financeExport(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		response.reset();
		response.setContentType("Application/excel");
		String fileName = "对账单.xls";
		String date = request.getParameter("startDate");
		//日期为空，则默认为当天的
		if(StringUtils.isBlank(date)){
			date = DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay);
		}
		fileName = date + fileName;
		response.addHeader("Content-Disposition",
				"attachment;filename="+ new String(fileName.getBytes("GBK"), "ISO8859-1"));

		//导出excel 基础信息
		HSSFWorkbook workbook = new HSSFWorkbook();

		//定义加粗字体
		HSSFFont font_bold = workbook.createFont();
		font_bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle cell_bold_style = workbook.createCellStyle();
		cell_bold_style.setFont(font_bold);

		//定义加粗，大号标题字体
		HSSFFont font_bigMax_bold = workbook.createFont();
		font_bigMax_bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font_bigMax_bold.setFontHeightInPoints((short)100);
		HSSFCellStyle cell_bigMax_style = workbook.createCellStyle();
		cell_bigMax_style.setFont(font_bold);
		cell_bigMax_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//查询数据
		//1、查询虚拟币种信息
		List<Fvirtualcointype> vcoinList = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);

		if (vcoinList != null && vcoinList.size() > 0) {
			//2、查询虚拟币充值、提现流水,每个币种的流水就是一个sheet
			for(Fvirtualcointype vcoin : vcoinList) {

				List<VirtualOperationLogVo> rechargeList = this.virtualCapitaloperationService.virtualOperationLog(1,vcoin.getFid(), date, true);
				List<VirtualOperationLogVo> withdrawList = this.virtualCapitaloperationService.virtualOperationLog(2,vcoin.getFid(), date, false);
				//3、查询虚拟币充值、提现流水，汇总信息
				Map<String, String> rechargeMap = this.virtualCapitaloperationService.virtualOperationLogStats(1, vcoin.getFid(), date, true);
				Map<String, String> withdrawMap = this.virtualCapitaloperationService.virtualOperationLogStats(2, vcoin.getFid(), date, false);
				createRechargeSheet(workbook,vcoin,cell_bigMax_style,cell_bold_style,date,rechargeMap, rechargeList);
				createWithdrawSheet(workbook,vcoin,cell_bigMax_style,cell_bold_style,date,withdrawMap, withdrawList);

			}


		}

		//3、查询人民币充值，提现流水，生成sheet
		List<CapitalOperationLogVo> rechargeRmbList = this.capitaloperationService.capitalOperationLog(1, date, true);
		List<CapitalOperationLogVo> withdrawRmbList = this.capitaloperationService.capitalOperationLog(2, date, false);
		Map<String, String> rechargeRmbMap = this.capitaloperationService.capitalOperationLogStats(1, date ,true);
		Map<String, String> withdrawRmbMap = this.capitaloperationService.capitalOperationLogStats(2, date ,false);
		List<Map<String, String>> recharge_type_map = this.capitaloperationService.rechargeRmbStatsByType(date);
		createRechargeRmbSheet(workbook, cell_bigMax_style, cell_bold_style,date,rechargeRmbMap, rechargeRmbList, recharge_type_map);
		createWithdrawRmbSheet(workbook, cell_bigMax_style, cell_bold_style,date,withdrawRmbMap, withdrawRmbList);


		ServletOutputStream os =  response.getOutputStream();
		workbook.write(os);

		os.flush();
		if(os != null){
			os.close();
		}

		response.getOutputStream().close();

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "导出成功");
		return modelAndView;
	}


	//对账单导出，创建充值人民币sheet
	private void createRechargeRmbSheet(HSSFWorkbook workbook, HSSFCellStyle cell_bigMax_style, HSSFCellStyle cell_bold_style,
										String date, Map<String, String> rechargeRmbMap, List<CapitalOperationLogVo> rechargeRmbList,
										List<Map<String, String>> recharge_type_map ) {
		HSSFSheet sheet = workbook.createSheet("人民币充值");
		sheet.setDefaultColumnWidth(20);
		sheet.setDefaultRowHeightInPoints(30);
		//充值excel begin
		int row = 0;
		//标题行
		HSSFRow row0 = createRow(sheet, row);
		HSSFCell row0_cell0 = row0.createCell(0); //让中间显示，所以从第1列开始
		row0_cell0.setCellValue("人民币成功充值");
		row0_cell0.setCellStyle(cell_bigMax_style);
		sheet.addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)6));
		if(rechargeRmbMap != null && rechargeRmbMap.size() > 0){
			//各支付类型汇总统计
			if(recharge_type_map != null && recharge_type_map.size() > 0){
				for(int i=0; i< recharge_type_map.size(); i++){
					Map<String, String> type_map = recharge_type_map.get(i);
					row = row+1;
					HSSFRow type_row1 = createRow(sheet, row);
					createCell(type_row1, 0, type_map.get("rechargeType")+"-订单笔数", cell_bold_style);
					createCell(type_row1, 1, type_map.get("type_orderNum"), cell_bold_style);
					row = row+1;
					HSSFRow type_row2 = createRow(sheet, row);
					createCell(type_row2, 0, type_map.get("rechargeType")+"-充值总额", cell_bold_style);
					createCell(type_row2, 1, type_map.get("type_amount"), cell_bold_style);
				}
			}

			//汇总 第一行
			row = row+1;
			HSSFRow row1 = createRow(sheet, row);
			createCell(row1, 0, "订单总笔数", cell_bold_style);
			createCell(row1, 1, rechargeRmbMap.get("orderNum"), cell_bold_style);

			//汇总第二行
			row = row+1;
			HSSFRow row2 = createRow(sheet, row);
			createCell(row2, 0, "充值总额", cell_bold_style);
			createCell(row2, 1, rechargeRmbMap.get("amount"), cell_bold_style);

			//汇总第四行
			row = row+1;
			HSSFRow row4 = createRow(sheet, row);
			createCell(row4, 0, "对账日期", cell_bold_style);
			createCell(row4, 1, date, cell_bold_style);

			row = row+1;
			HSSFRow row5 = createRow(sheet, row); //第五行是空行
			//数据标题行
			row = row+1;
			HSSFRow title_row = createRow(sheet, row);
			createCell(title_row, 0, "订单Id", cell_bold_style);
			createCell(title_row, 1, "登录名", cell_bold_style);
			createCell(title_row, 2, "姓名", cell_bold_style);
			createCell(title_row, 3, "手机号", cell_bold_style);
			createCell(title_row, 4, "充值方式", cell_bold_style);
			createCell(title_row, 5, "充值金额", cell_bold_style);
			createCell(title_row, 6, "到帐时间", cell_bold_style);
			//循环数据部分
			if(rechargeRmbList != null && rechargeRmbList.size()>0){
				for(CapitalOperationLogVo log : rechargeRmbList) {
					row = row+1;
					HSSFRow loop_row = createRow(sheet, row);
					createCell(loop_row, 0, log.getOrderId());
					createCell(loop_row, 1, log.getFloginName());
					createCell(loop_row, 2, log.getFrealName());
					createCell(loop_row, 3, log.getFtelephone());
					createCell(loop_row, 4, log.getRechargeType());
					createCell(loop_row, 5, log.getRechargeAmount());
					createCell(loop_row, 6, log.getRechargeDate());
				}
			}
		}

		//充值excel end
	}

	//对账单导出，创建提现人民币sheet
	private void createWithdrawRmbSheet(HSSFWorkbook workbook, HSSFCellStyle cell_bigMax_style, HSSFCellStyle cell_bold_style,
										String date, Map<String, String> withdrawRmbMap, List<CapitalOperationLogVo> withdrawRmbList) {
		HSSFSheet sheet = workbook.createSheet("人民币提现");
		sheet.setDefaultColumnWidth(20);
		sheet.setDefaultRowHeightInPoints(30);
		//提现excel begin
		int row = 0;
		//标题行
		HSSFRow row0 = createRow(sheet, row);
		HSSFCell row0_cell0 = row0.createCell(0); //让中间显示，所以从第1列开始
		row0_cell0.setCellValue("人民币成功提现");
		row0_cell0.setCellStyle(cell_bigMax_style);
		sheet.addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)10));
		if(withdrawRmbMap != null && withdrawRmbMap.size() > 0){
			//汇总 第一行
			row = row+1;
			HSSFRow row1 = createRow(sheet, row);
			createCell(row1, 0, "订单总笔数", cell_bold_style);
			createCell(row1, 1, withdrawRmbMap.get("orderNum"), cell_bold_style);

			//汇总第二行
			row = row+1;
			HSSFRow row2 = createRow(sheet, row);
			createCell(row2, 0, "提现总额(含手续费)", cell_bold_style);
			createCell(row2, 1, withdrawRmbMap.get("total_amtFees"), cell_bold_style);

			//汇总第三行
			row = row+1;
			HSSFRow row3 = createRow(sheet, row);
			createCell(row3, 0, "手续费总额", cell_bold_style);
			createCell(row3, 1, withdrawRmbMap.get("fees"), cell_bold_style);

			//汇总第四行
			row = row+1;
			HSSFRow row4 = createRow(sheet, row);
			createCell(row4, 0, "对账日期", cell_bold_style);
			createCell(row4, 1, date, cell_bold_style);

			row = row+1;
			HSSFRow row5 = createRow(sheet, row); //第五行是空行
			//数据标题行
			row = row+1;
			HSSFRow title_row = createRow(sheet, row);
			createCell(title_row, 0, "订单Id", cell_bold_style);
			createCell(title_row, 1, "登录名", cell_bold_style);
			createCell(title_row, 2, "姓名", cell_bold_style);
			createCell(title_row, 3, "提现金额(含手续费)", cell_bold_style);
			createCell(title_row, 4, "手续费", cell_bold_style);
			createCell(title_row, 5, "用户收到的金额", cell_bold_style);
			createCell(title_row, 6, "手机号", cell_bold_style);
			createCell(title_row, 7, "银行", cell_bold_style);
			createCell(title_row, 8, "收款帐号", cell_bold_style);
			createCell(title_row, 9, "开户行", cell_bold_style);
			createCell(title_row, 10, "到帐时间", cell_bold_style);
			//循环数据部分
			if(withdrawRmbList != null && withdrawRmbList.size()>0){
				for(CapitalOperationLogVo log : withdrawRmbList) {
					row = row+1;
					HSSFRow loop_row = createRow(sheet, row);
					createCell(loop_row, 0, log.getOrderId().toString());
					createCell(loop_row, 1, log.getFloginName());
					createCell(loop_row, 2, log.getFrealName());
					createCell(loop_row, 3, log.getWithdrawAmount());
					createCell(loop_row, 4, log.getWithdrawFees());
					createCell(loop_row, 5, log.getWithdrawUserGetAmount());
					createCell(loop_row, 6, log.getFtelephone());
					createCell(loop_row, 7, log.getBankName());
					createCell(loop_row, 8, log.getBankCardNo());
					createCell(loop_row, 9, log.getBranchName());
					createCell(loop_row, 10, log.getWithdrawDate());
				}
			}
		}

		//提现excel end
	}


	//对账单导出，创建提现sheet
	private void createWithdrawSheet(HSSFWorkbook workbook, Fvirtualcointype vcoin, HSSFCellStyle cell_bigMax_style, HSSFCellStyle cell_bold_style,
								   String date, Map<String, String> withdrawMap, List<VirtualOperationLogVo> withdrawList){
		HSSFSheet sheet = workbook.createSheet(vcoin.getFname()+"提现");
		sheet.setDefaultColumnWidth(20);
		sheet.setDefaultRowHeightInPoints(30);
		//提现excel begin
		int row = 0;
		//标题行
		HSSFRow row0 = createRow(sheet, row);
		HSSFCell row0_cell0 = row0.createCell(0); //让中间显示，所以从第1列开始
		row0_cell0.setCellValue(vcoin.getFname()+"成功提币");
		row0_cell0.setCellStyle(cell_bigMax_style);
		sheet.addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)7));
		if(withdrawMap != null && withdrawMap.size() > 0){
			//汇总 第一行
			row = row+1;
			HSSFRow row1 = createRow(sheet, row);
			createCell(row1, 0, "订单总笔数", cell_bold_style);
			createCell(row1, 1, withdrawMap.get("orderNum"), cell_bold_style);

			//汇总第二行
			row = row+1;
			HSSFRow row2 = createRow(sheet, row);
			createCell(row2, 0, "提币总数(含手续费)", cell_bold_style);
			createCell(row2, 1, withdrawMap.get("total_amtFees"), cell_bold_style);

			//汇总第三行
			row = row+1;
			HSSFRow row3 = createRow(sheet, row);
			createCell(row3, 0, "手续费总额", cell_bold_style);
			createCell(row3, 1, withdrawMap.get("fees"), cell_bold_style);

			//汇总第四行
			row = row+1;
			HSSFRow row4 = createRow(sheet, row);
			createCell(row4, 0, "对账日期", cell_bold_style);
			createCell(row4, 1, date, cell_bold_style);

			row = row+1;
			HSSFRow row5 = createRow(sheet, row); //第五行是空行
			//数据标题行
			row = row+1;
			HSSFRow title_row = createRow(sheet, row);
			createCell(title_row, 0, "登录名", cell_bold_style);
			createCell(title_row, 1, "姓名", cell_bold_style);
			createCell(title_row, 2, "手机号", cell_bold_style);
			createCell(title_row, 3, "提币数量(含手续费)", cell_bold_style);
			createCell(title_row, 4, "手续费", cell_bold_style);
			createCell(title_row, 5, "用户收到的提币数量", cell_bold_style);
			createCell(title_row, 6, "提现地址", cell_bold_style);
			createCell(title_row, 7, "到帐时间", cell_bold_style);
			//循环数据部分
			if(withdrawList != null && withdrawList.size()>0){
				for(VirtualOperationLogVo log : withdrawList) {
					row = row+1;
					HSSFRow loop_row = createRow(sheet, row);
					createCell(loop_row, 0, log.getFloginName());
					createCell(loop_row, 1, log.getFrealName());
					createCell(loop_row, 2, log.getFtelephone());
					createCell(loop_row, 3, log.getWithdrawNum());
					createCell(loop_row, 4, log.getWithdrawFees());
					createCell(loop_row, 5, log.getWithdrawUserGetNum());
					createCell(loop_row, 6, log.getWithdrawAddress());
					createCell(loop_row, 7, log.getWithdrawDate());
				}
			}
		}

		//提现excel end
	}


	//对账单导出，创建充值sheet
	private void createRechargeSheet(HSSFWorkbook workbook, Fvirtualcointype vcoin, HSSFCellStyle cell_bigMax_style, HSSFCellStyle cell_bold_style,
								   String date, Map<String, String> rechargeMap, List<VirtualOperationLogVo> rechargeList){
		HSSFSheet sheet = workbook.createSheet(vcoin.getFname()+"充值");
		sheet.setDefaultColumnWidth(20);
		sheet.setDefaultRowHeightInPoints(30);
		//充值excel begin
		int row = 0;
		//标题行
		HSSFRow row0 = createRow(sheet, row);
		HSSFCell row0_cell0 = row0.createCell(0); //让中间显示，所以从第1列开始
		row0_cell0.setCellValue(vcoin.getFname()+"成功充值");
		row0_cell0.setCellStyle(cell_bigMax_style);
		sheet.addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)5));
		if(rechargeMap != null && rechargeMap.size() > 0){
			//汇总 第一行
			row = row+1;
			HSSFRow row1 =  createRow(sheet, row);
			createCell(row1, 0, "订单总笔数", cell_bold_style);
			createCell(row1, 1, rechargeMap.get("orderNum"), cell_bold_style);

			//汇总第二行
			row = row+1;
			HSSFRow row2 = createRow(sheet, row);
			createCell(row2, 0, "充币总数", cell_bold_style);
			createCell(row2, 1, rechargeMap.get("amount"), cell_bold_style);

			//汇总第三行
			row = row+1;
			HSSFRow row3 = createRow(sheet, row);
			createCell(row3, 0, "对账日期", cell_bold_style);
			createCell(row3, 1, date, cell_bold_style);

			row = row+1;
			HSSFRow row4 = createRow(sheet, row);//第四行是空行
			//数据标题行
			row = row+1;
			HSSFRow title_row = createRow(sheet, row);
			createCell(title_row, 0, "登录名", cell_bold_style);
			createCell(title_row, 1, "姓名", cell_bold_style);
			createCell(title_row, 2, "手机号", cell_bold_style);
			createCell(title_row, 3, "充币数量", cell_bold_style);
			createCell(title_row, 4, "充值地址", cell_bold_style);
			createCell(title_row, 5, "到帐时间", cell_bold_style);
			//循环数据部分
			if(rechargeList != null && rechargeList.size()>0){
				for(VirtualOperationLogVo log : rechargeList) {
					row = row+1;
					HSSFRow loop_row = createRow(sheet, row);
					createCell(loop_row, 0, log.getFloginName());
					createCell(loop_row, 1, log.getFrealName());
					createCell(loop_row, 2, log.getFtelephone());
					createCell(loop_row, 3, log.getRechargeNum());
					createCell(loop_row, 4, log.getRechargeAddress());
					createCell(loop_row, 5, log.getRechargeDate());
				}
			}
		}
		//充值excel end
	}

	private HSSFRow createRow(HSSFSheet sheet, int rowIndex) {
		HSSFRow row = sheet.createRow(rowIndex);
		row.setHeightInPoints(23);
		return row;
	}


	private void createCell(HSSFRow row, int columnIndex, String cellValue){
		HSSFCell cell = row.createCell(columnIndex);
		cell.setCellValue(cellValue);
	}

	private void createCell(HSSFRow row, int columnIndex, String cellValue, HSSFCellStyle cellStyle){
		HSSFCell cell = row.createCell(columnIndex);
		cell.setCellValue(cellValue);
		cell.setCellStyle(cellStyle);
	}


	private static enum Export1Filed {
		订单ID,会员UID,会员登陆名, 会员昵称, 会员真实姓名, 类型, 状态, 金额, 手续费, 银行, 收款帐号, 手机号码, 创建时间, 最后修改时间;
	}

	@RequestMapping("ssadmin/capitalOutExport")
	public ModelAndView capitalOutExport(HttpServletResponse response)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=capitalOutList.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (Export1Filed filed : Export1Filed.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}
		String type = "(" + CapitalOperationTypeEnum.RMB_OUT + ")";
		String status = "(" + CapitalOperationOutStatus.WaitForOperation + ","
				+ CapitalOperationOutStatus.OperationLock + ")";
		List<Fcapitaloperation> capitalOperationList = getCapitalOperationList(
				type, status);
		for (Fcapitaloperation capitalOperation : capitalOperationList) {
			e.createRow(rowIndex++);
			for (Export1Filed filed : Export1Filed.values()) {
				switch (filed) {
				case 订单ID:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFid());
					break;
				case 会员UID:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser().getFid());
					break;
				case 会员登陆名:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser()
								.getFloginName());
					break;
				case 会员昵称:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser()
								.getFnickName());
					break;
				case 会员真实姓名:
					if (capitalOperation.getFuser() != null)
						e.setCell(filed.ordinal(), capitalOperation.getFuser()
								.getFrealName());
					break;
				case 类型:
					e.setCell(filed.ordinal(), capitalOperation.getFtype_s());
					break;
				case 状态:
					e.setCell(filed.ordinal(), capitalOperation.getFstatus_s());
					break;
				case 金额:
					e.setCell(filed.ordinal(), capitalOperation.getFamount());
					break;
				case 手续费:
					e.setCell(filed.ordinal(), capitalOperation.getFfees());
					break;
				case 银行:
					e.setCell(filed.ordinal(), capitalOperation.getfBank());
					break;
				case 收款帐号:
					e.setCell(filed.ordinal(), capitalOperation.getFaccount_s());
					break;
				case 手机号码:
					e.setCell(filed.ordinal(), capitalOperation.getfPhone());
					break;
				case 创建时间:
					e.setCell(filed.ordinal(),
							capitalOperation.getFcreateTime());
					break;
				case 最后修改时间:
					e.setCell(filed.ordinal(),
							capitalOperation.getfLastUpdateTime());
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

	private static enum Export2Filed {
		订单ID,会员UID,登陆名, 会员昵称, 会员真实姓名, 类型, 状态, 金额, 手续费, 银行, 收款帐号, 手机号码,开户行地址, 创建时间, 最后修改时间;
	}
	//人民币提现列表导出
	@RequestMapping("ssadmin/withDrawRMBExport")
	//@SysLog(code = ModuleConstont.USER_OPERATION, method = "导出成功提现人民币列表")
	public ModelAndView withDrawRMBExport(HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition", "attachment;filename=withDrawRMB.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (Export2Filed filed : Export2Filed.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}

		//

		List<Fcapitaloperation> lists=	getWithDrawRMB();

		for (Fcapitaloperation fcapitaloperation : lists) {
			e.createRow(rowIndex++);
			for (Export2Filed filed : Export2Filed.values()) {
				switch (filed) {
					case 订单ID:
						if (fcapitaloperation.getFuser() != null)
							e.setCell(filed.ordinal(), fcapitaloperation.getFid());
						break;
					case 会员UID:
						if (fcapitaloperation.getFuser() != null)
							e.setCell(filed.ordinal(), fcapitaloperation.getFuser().getFid());
						break;
					case 登陆名:
						if (fcapitaloperation.getFuser() != null)
							e.setCell(filed.ordinal(), fcapitaloperation.getFuser()
									.getFloginName());
						break;
					case 会员昵称:
						if (fcapitaloperation.getFuser() != null)
							e.setCell(filed.ordinal(), fcapitaloperation.getFuser()
									.getFnickName());
						break;
					case 会员真实姓名:
						if (fcapitaloperation.getFuser() != null)
							e.setCell(filed.ordinal(), fcapitaloperation.getFuser()
									.getFrealName());
						break;
					case 类型:
						e.setCell(filed.ordinal(), fcapitaloperation.getFtype_s());
						break;
					case 状态:
						e.setCell(filed.ordinal(), fcapitaloperation.getFstatus_s());
						break;
					case 金额:
						e.setCell(filed.ordinal(), fcapitaloperation.getFamount());
						break;
					case 手续费:
						e.setCell(filed.ordinal(), fcapitaloperation.getFfees());
						break;
					case 银行:
						e.setCell(filed.ordinal(), fcapitaloperation.getfBank());
						break;
					case 收款帐号:
						e.setCell(filed.ordinal(), fcapitaloperation.getFaccount_s());
						break;
					case 手机号码:
						e.setCell(filed.ordinal(), fcapitaloperation.getfPhone());
						break;
					case 开户行地址:
						e.setCell(filed.ordinal(),
								fcapitaloperation.getFaddress());
						break;
					case 创建时间:
						e.setCell(filed.ordinal(),
								fcapitaloperation.getFcreateTime());
						break;

					case 最后修改时间:
						e.setCell(filed.ordinal(),
								fcapitaloperation.getfLastUpdateTime());
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


	private List<Fcapitaloperation> getWithDrawRMB() {

		String type = "(" + CapitalOperationTypeEnum.RMB_OUT + ")";
		String status = "(" + CapitalOperationOutStatus.OperationSuccess + ","
				+ CapitalOperationOutStatus.OperationLock + ")";

		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String capitalId = request.getParameter("capitalId");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");


		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 \n");
		filterSQL.append("and ftype IN " + type + "\n");
		filterSQL.append("AND fstatus IN " + status + "\n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append(" AND (fBank like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.floginName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fAccount like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fPhone like '%" + keyWord + "%' OR \n");
				filterSQL.append(" fPayee like '%" + keyWord + "%' OR \n");
				filterSQL.append(" famount like '%" + keyWord + "%') \n");
			}

		}

		//添加日期查询条件
		String startDate = request.getParameter("startDate");
		if(startDate  != null && startDate .trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fLastUpdateTime,'%Y-%m-%d') >= '"+startDate +"' \n");

		}

		String endDate = request.getParameter("endDate");
		if(endDate != null && endDate.trim().length() >0){
			filterSQL.append("and  DATE_FORMAT(fLastUpdateTime,'%Y-%m-%d') <= '"+endDate+"' \n");

		}


		if (capitalId != null && capitalId.trim().length() > 0) {
			boolean flag = Utils.isNumeric(capitalId);
			if (flag) {
				filterSQL.append(" AND fid =" + capitalId + "\n");

			}
		}
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

		List<Fcapitaloperation> list = this.capitaloperationService.list(
				0, 0, filterSQL + "",
				false);
		return list;

	}


	/**
	 * 全部锁定状态
	 * @return
	 * @throws Exception
     */
	@RequestMapping("ssadmin/capitalOutAuditAll")
	@SysLog(code = ModuleConstont.CYN_OPERATION, method = "人民币提现批量锁定")
	public ModelAndView capitalOutAuditAll() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		String ids = request.getParameter("ids");
		String[] idString = ids.split(",");
		// 充值操作
		Fadmin admin = (Fadmin) request.getSession()
				.getAttribute("login_admin");
		for(int i=0;i<idString.length;i++){
			int id = Integer.parseInt(idString[i]);
			Fcapitaloperation capitalOperation = this.capitaloperationService.findById(id);
			int status = capitalOperation.getFstatus();
			if (status != CapitalOperationOutStatus.WaitForOperation) {
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("stssadmin/capitalOutAuditatusCode", 300);
				String status_s = CapitalOperationOutStatus
						.getEnumString(CapitalOperationOutStatus.WaitForOperation);
				modelAndView.addObject("message", "锁定失败,只有状态为:‘" + status_s
						+ "’的提现记录才允许锁定!");
				return modelAndView;
			}
			capitalOperation.setfLastUpdateTime(Utils.getTimestamp());
			capitalOperation.setfAuditee_id(admin);
			capitalOperation.setFstatus(CapitalOperationOutStatus.OperationLock);
			this.capitaloperationService.updateObj(capitalOperation);
		}

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "批量锁定成功");
		return modelAndView;
	}


	//支付状态查询
	@RequestMapping("/ssadmin/capitalInPayStatusQuery")
	public ModelAndView capitalInPayStatusQuery() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("uid"));
		//根据id查询充值流水
		Fcapitaloperation capitalOperation = this.capitaloperationService
				.findById(fid);
		if(capitalOperation != null && !capitalOperation.getFremittanceType().equals(RemittanceTypeEnum.getEnumString(3)) &&
				!capitalOperation.getFremittanceType().equals(RemittanceTypeEnum.getEnumString(4))){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "充值方式为“微信支付”、“支付宝支付”的才可以查询支付状态，请重新选择");
			return modelAndView;
		}
		//去三方平台查询
		Map<String, String> map  = ChroneApi.qrpay_status_query(fid+"");
		if(map != null && map.size() > 0){
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", "支付状态是："+map.get("payStName"));
		}else{
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "查询失败");
		}
		return modelAndView;
	}

}
