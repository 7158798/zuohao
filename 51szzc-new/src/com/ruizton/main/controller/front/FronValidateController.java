package com.ruizton.main.controller.front;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.ruizton.main.Enum.IntegralTypeEnum;
import com.ruizton.main.model.Fquestionvalidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.UserStatusEnum;
import com.ruizton.main.comm.MultipleValues;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Emailvalidate;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontValidateService;
import com.ruizton.util.Utils;

@Controller
public class FronValidateController extends BaseController {
	
	@RequestMapping("/validate/reset")//密码重置请求
	public ModelAndView reset(
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		if(GetSession(request) != null){
			Fuser fuser = this.frontUserService.queryById(GetSession(request).getFid()) ;
			if(fuser.getFstatus()==UserStatusEnum.FORBBIN_VALUE){
				CleanSession(request) ;
			}
			
			modelAndView.setViewName("redirect:/") ;
			return modelAndView;
		}
		
		modelAndView.setViewName("front/validate/reset") ;
		return modelAndView ;
	}
	
	@RequestMapping("/validate/resetEmail")//密码重置请求
	public ModelAndView resetEmail(
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		if(GetSession(request) != null){
			Fuser fuser = this.frontUserService.queryById(GetSession(request).getFid()) ;
			if(fuser.getFstatus()==UserStatusEnum.FORBBIN_VALUE){
				CleanSession(request) ;
			}
			
			modelAndView.setViewName("redirect:/") ;
			return modelAndView;
		}
		modelAndView.addObject("account_cert_desc", i18nMsg("account_cert_desc"));
		modelAndView.setViewName("front/validate/resetEmail") ;
		return modelAndView ;
	}
	
	@RequestMapping("/validate/resetPhone")//密码重置请求
	public ModelAndView resetPhone(
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		if(GetSession(request) != null){
			Fuser fuser = this.frontUserService.queryById(GetSession(request).getFid()) ;
			if(fuser.getFstatus()==UserStatusEnum.FORBBIN_VALUE){
				CleanSession(request) ;
			}
			
			modelAndView.setViewName("redirect:/") ;
			return modelAndView;
		}
		modelAndView.addObject("account_cert_desc", i18nMsg("account_cert_desc"));
		modelAndView.setViewName("front/validate/resetPhone") ;
		return modelAndView ;
	}

	//通过邮件找回密码第二步
	@RequestMapping("validate/resetPwd")
	public ModelAndView resetPwd(
			@RequestParam(required=true)int uid,
			@RequestParam(required=true)String uuid
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Emailvalidate emailvalidate = null ;
		try {
			emailvalidate = this.frontValidateService.updateFindPasswordMailValidate(uid, uuid) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(emailvalidate!=null){
			Fuser fuser = this.frontUserService.findById(emailvalidate.getFuser().getFid()) ;
			//2017-04-10 修改，查询用户是否已进行安全问题验证
			boolean isQuestionValidate = this.fquestionvalidateService.isQuestionVal(fuser.getFid());
			modelAndView.addObject("isQuestionValidate", isQuestionValidate);
			if(isQuestionValidate) {
				//获取随机问题
				queryRandQuestion(fuser.getFid(), modelAndView);
			}
			modelAndView.addObject("emailvalidate", emailvalidate) ; 
			modelAndView.addObject("fuser", fuser) ; 
		}else{
			modelAndView.setViewName("redirect:/validate/resetEmail.html") ;
			return modelAndView ;
		}
		
		modelAndView.setViewName("front/validate/resetPwd") ;
		return modelAndView ;
	}
	
	//通过手机找回密码第二步
	@RequestMapping("validate/resetPwdPhone")
	public ModelAndView resetPwd(
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		Object resetPasswordByPhone = request.getSession().getAttribute("resetPasswordByPhone") ;
		if(resetPasswordByPhone!=null ){
			MultipleValues values = (MultipleValues)resetPasswordByPhone ;
			Integer fuserid = (Integer)values.getValue1() ;
			Timestamp time = (Timestamp)values.getValue2() ;
			
			if(Utils.timeMinus(Utils.getTimestamp(), time)<300){
				Fuser fuser = this.frontUserService.findById(fuserid) ;
				//2017-04-10 修改，查询用户是否已进行安全问题验证
				boolean isQuestionValidate = this.fquestionvalidateService.isQuestionVal(fuser.getFid());
				modelAndView.addObject("isQuestionValidate", isQuestionValidate);
				if(isQuestionValidate) {
					//获取随机问题
					queryRandQuestion(fuserid, modelAndView);
				}
				modelAndView.addObject("fuser", fuser) ;
				modelAndView.setViewName("front/validate/resetPwdPhone") ;
				return modelAndView ;
			}
		}
		
		modelAndView.setViewName("redirect:/validate/resetPhone.html") ;
		return modelAndView ;
	}

	/**
	 * 随机获取一个用户已设置的问题
	 * @param userId
	 * @return
	 */
	private Map<String, String> queryRandQuestion(Integer userId, ModelAndView modelAndView) {
		Map<String, String> map = new HashMap<>();
		//根据用户id获取用户已设置的验证问题
		List<Fquestionvalidate> list = this.fquestionvalidateService.findByProperty("fuser.fid", userId);
		if(list  == null || list.size() == 0) {
			return map;
		}
		//随机获取一个
		Random random = new Random();
		int max =  list.size();
		int min = 1;
		int s = random.nextInt(max)%(max-min+1) + min;
		s = s -1;
		String question = list.get(s).getFquestion();
		String answer = list.get(s).getFanswer();
		String fid = list.get(s).getFid().toString();

		map.put("fid", fid);
		map.put("question", question);
		map.put("answer", answer);

		if(modelAndView != null) {
			modelAndView.addObject("fid", fid);
			modelAndView.addObject("question", question);
		}

		return map;
	}
	
	
	/*
	 * 邮件验证点击
	 * */
	@RequestMapping(value="/validate/mail_validate")
	public ModelAndView mailValidate(
			HttpServletRequest request,
			@RequestParam(required=true)int uid,
			@RequestParam(required=true)String uuid
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		boolean flag = false ;
		String ip = getIpAddr(request) ;
		try {
			flag = this.frontValidateService.updateMailValidate(uid, uuid,ip) ;
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(flag){
			//绑定邮箱===WEB首次送积分
			this.integralService.addUserIntegralFirst(IntegralTypeEnum.EMAIL_FIRST,uid,0);
		}
		
		modelAndView.addObject("validate", flag) ;
		modelAndView.setViewName("front/user/reg_regconfirm") ;
		return modelAndView ;
	}
	
}
