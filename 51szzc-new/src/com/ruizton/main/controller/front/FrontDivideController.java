package com.ruizton.main.controller.front;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ruizton.main.Enum.IntegralTypeEnum;
import com.ruizton.main.Enum.RegTypeEnum;
import com.ruizton.main.controller.front.response.IntrolResp;
import com.ruizton.main.model.integral.Fintegralactivity;
import com.ruizton.util.DateHelper;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.SharePlanLogStatusEnum;
import com.ruizton.main.Enum.SharePlanTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fintrolinfo;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.IntrolinfoService;
import com.ruizton.main.service.admin.UserService;
import com.ruizton.main.service.admin.VirtualWalletService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.util.Constant;
import com.ruizton.util.PaginUtil;

@Controller
public class FrontDivideController extends BaseController {



	@Autowired
	private VirtualWalletService virtualWalletService;
	
	
	
	
	@RequestMapping("/introl/mydivide")
	public ModelAndView introl(
			HttpServletRequest request,
			@RequestParam(required=true,defaultValue="1")int type,
			@RequestParam(required=false,defaultValue="1")int currentPage
			) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		String url = new Constant().Domain+"invite/invite.html?r="+fuser.getFid();
		if(fuser.getFuserNo() != null && fuser.getFuserNo().trim().length() >0){
			url = url+"&sn="+fuser.getFuserNo().trim();
		}
		modelAndView.addObject("spreadLink", url) ;
		if(type == 1){
			String filter = "where fIntroUser_id.fid="+fuser.getFid()+" order by fid desc";
			int total = this.adminService.getAllCount("Fuser", filter);
			int totalPage = total/Constant.RecordPerPage + ((total%Constant.RecordPerPage) ==0?0:1) ;
			List<Fuser> fusers = this.userService.list((currentPage-1)*Constant.RecordPerPage, Constant.RecordPerPage,filter,true) ;
			String pagin = PaginUtil.generatePagin(totalPage, currentPage, "/introl/mydivide.html?type=1&") ;
			
			modelAndView.addObject("fusers", fusers) ;
			modelAndView.addObject("pagin", pagin) ;
			modelAndView.setViewName("front/introl/index") ;
		}else if(type ==2){
			String filter = "where fuser.fid="+fuser.getFid()+" order by fid desc";
			int total = this.adminService.getAllCount("Fintrolinfo", filter);
			int totalPage = total/Constant.RecordPerPage + ((total%Constant.RecordPerPage)  ==0?0:1) ;
			List<Fintrolinfo> fintrolinfos = this.introlinfoService.list((currentPage-1)*Constant.RecordPerPage, Constant.RecordPerPage,filter,true) ;
			String pagin = PaginUtil.generatePagin(totalPage, currentPage, "/introl/mydivide.html?type=2&") ;
			
			modelAndView.addObject("fintrolinfos", fintrolinfos) ;
			modelAndView.addObject("pagin", pagin) ;
			modelAndView.setViewName("front/introl/index2") ;
		}
		modelAndView.addObject("type", type) ;
		
		return modelAndView ;
	}

	/**
	 * 好友邀请
	 * @param request
	 * @param currentPage
	 * @return
	 * @throws Exception
     */
	@RequestMapping("/user/myintrol")
	public ModelAndView myintrol(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage
	) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		String url = new Constant().Domain+"invite/invite.html?r="+fuser.getFid();
		modelAndView.addObject("spreadLink", url) ;

		String filter = "where fIntroUser_id.fid="+fuser.getFid()+" order by fid desc";
		int total = this.adminService.getAllCount("Fuser", filter);
		int totalPage = total/Constant.RecordPerPage + ((total%Constant.RecordPerPage) ==0?0:1) ;
		List<Fuser> fusers = this.userService.list((currentPage-1)*Constant.RecordPerPage, Constant.RecordPerPage,filter,true) ;

        int integral;
		//已实名人数

		List<IntrolResp> resps =new ArrayList<>();
		for(Fuser fuser1:fusers){
			IntrolResp resp = new IntrolResp();
			resp.setLogName(this.getLoginName(fuser1));
            integral = this.integralService.getIntegralByRalationId(fuser.getFid(),IntegralTypeEnum.INVITE_FRIENDS.getCode(),fuser1.getFid());
			resp.setIntegral(integral);
			resp.setIsRealValid(fuser1.getFhasRealValidate()?"已完成":"未完成");
			resp.setCreateTime(DateHelper.date2String(fuser1.getFregisterTime(), DateHelper.DateFormatType.YearMonthDay_HourMinute));
			resps.add(resp);

		}
		//赠送积分数
        Fintegralactivity fintegralactivity = this.integralService.getIntegralActivy(IntegralTypeEnum.INVITE_FRIENDS);
        int inviteIntegral = fintegralactivity == null?0:fintegralactivity.getIntegral();
        modelAndView.addObject("inviteIntegral", inviteIntegral) ;

		//获取积分数
		int allIntegral = this.integralService.getIntegralByRalationId(fuser.getFid(),IntegralTypeEnum.INVITE_FRIENDS.getCode(),0);
		//已实名人数
		filter = "where fIntroUser_id.fid="+fuser.getFid()+" and fhasRealValidate = 1 ";
		int realAccount = this.adminService.getAllCount("Fuser", filter);


		String pagin = PaginUtil.generatePagin(totalPage, currentPage, "/user/myintrol.html?") ;

		modelAndView.addObject("fusers", resps) ;
		modelAndView.addObject("allIntegral",allIntegral);
		modelAndView.addObject("realAccount",realAccount);

		modelAndView.addObject("pagin", pagin) ;
		modelAndView.setViewName("front/user/user_invite") ;

		return modelAndView ;
	}


	private String getLoginName(Fuser fuser){
		String loginName = "";
		if(fuser.getFregType() != RegTypeEnum.EMAIL_VALUE){
			loginName = fuser.getFloginName().substring(0, fuser.getFloginName().length()-5)+"****";
		}else{
			String[] args = fuser.getFloginName().split("@");
			loginName = args[0].substring(0, args[0].length()-(args[0].length()>=3?3:1))+"****"+args[1];
		}
		return loginName;

	}
}
