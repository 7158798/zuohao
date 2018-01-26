package com.ruizton.main.controller.admin;

import com.ruizton.main.Enum.ModuleConstont;
import com.ruizton.main.Enum.SystemBankInfoEnum;
import com.ruizton.main.Enum.SystemBankTypeEnum;
import com.ruizton.main.aspect.SysLog;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Systembankinfo;
import com.ruizton.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 系统支付宝
 */
@Controller
public class SystemAlipayController extends BaseController {


	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/ssadmin/sysAlipayList")
	public ModelAndView Index() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/sysAlipayList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String keyWord = request.getParameter("keywords");
		
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where ftype = "+ SystemBankTypeEnum.ALIPAY.getCode()+" \n");
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and (fbankName like '%"+keyWord+"%' or\n");
			filter.append("fownerName like '%"+keyWord+"%' or\n");
			filter.append("fbankAddress like '%"+keyWord+"%' or\n");
			filter.append("fbankNumber like '%"+keyWord+"%')\n");
			modelAndView.addObject("keywords", keyWord);
		}
		
		List<Systembankinfo> list = this.systembankService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("systembankList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "sysAlipayList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Systembankinfo", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("ssadmin/goSysAlipayJSP")
	public ModelAndView goSystemBankJSP() throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Systembankinfo systemBank = this.systembankService.findById(fid);
			modelAndView.addObject("systemBank", systemBank);
		}
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/saveSysAlipay")
	@SysLog(code = ModuleConstont.SYSTEM_OPERATION, method = "新增银行账户")
	public ModelAndView saveSystemBank() throws Exception{
		Systembankinfo bankInfo = new Systembankinfo();
		bankInfo.setFbankAddress(request.getParameter("systemBank.fbankAddress"));
		bankInfo.setFbankName(request.getParameter("systemBank.fbankName"));
		bankInfo.setFbankNumber(request.getParameter("systemBank.fbankNumber"));
		bankInfo.setFownerName(request.getParameter("systemBank.fownerName"));
		bankInfo.setFappId(request.getParameter("systemBank.fappId"));
		bankInfo.setFcreateTime(Utils.getTimestamp());
		bankInfo.setFstatus(SystemBankInfoEnum.NORMAL_VALUE);
		bankInfo.setFtype(SystemBankTypeEnum.ALIPAY.getCode());
        this.systembankService.saveObj(bankInfo);
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","新增成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/forbbinSysAlipay")
	@SysLog(code = ModuleConstont.SYSTEM_OPERATION, method = "修改银行账户状态")
	public ModelAndView forbbinSystemBank() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		int fid = Integer.parseInt(request.getParameter("uid"));
        int status = Integer.parseInt(request.getParameter("status"));
        Systembankinfo bankInfo = this.systembankService.findById(fid);
        if(status == 1){
        	bankInfo.setFstatus(SystemBankInfoEnum.ABNORMAL);
        	modelAndView.addObject("message","禁用成功");
        }else{
        	bankInfo.setFstatus(SystemBankInfoEnum.NORMAL_VALUE);
        	modelAndView.addObject("message","解除禁用成功");
        }
        
		this.systembankService.updateObj(bankInfo);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		
		return modelAndView;
	}
	
}