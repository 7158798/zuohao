package com.ruizton.main.controller.admin;

import com.ruizton.main.Enum.CountLimitTypeEnum;
import com.ruizton.main.Enum.ModuleConstont;
import com.ruizton.main.aspect.SysLog;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fadmin;
import com.ruizton.util.DateHelper;
import com.ruizton.util.MessagesUtils;
import com.ruizton.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class IndexController extends BaseController {


	public static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping("/ssadmin/index")
	public ModelAndView Index() throws Exception {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/index");
		modelAndView.addObject("dateTime", sdf1.format(new Date()));
		modelAndView.addObject("login_admin", request.getSession()
				.getAttribute("login_admin"));
		return modelAndView;
	}


	@RequestMapping("/ssadmin/2bcf8d4e-e2aa-11e6-bddf-005056ab18e8")
	public ModelAndView login() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/login");
		return modelAndView;
	}


	@RequestMapping("/ssadmin/95afee23-e2ab-11e6-bddf-005056ab18e8")
	public ModelAndView submitLogin(
			HttpServletRequest request,
			@RequestParam(required = true) String name,
			@RequestParam(required = true) String password,
			@RequestParam(required = true) String vcode) throws Exception {

		ModelAndView modelAndView = new ModelAndView();

		if (name == null || "".equals(name.trim()) || password == null
				|| "".equals(password.trim()) || vcode == null
				|| "".equals(vcode.trim())) {
			modelAndView.setViewName("redirect:/ssadmin/2bcf8d4e-e2aa-11e6-bddf-005056ab18e8.html");
			return modelAndView;
		} else {
			String ip = getIpAddr(request);
			int admin_limit = this.frontValidateService.getLimitCount(ip,
					CountLimitTypeEnum.AdminLogin);
			if (admin_limit <= 0) {
				logger.info("请求密码超过限制 ip =" + ip);
				modelAndView.addObject("error", "连续登陆错误5次，为安全起见，禁止登陆半小时！");
				modelAndView.setViewName("/ssadmin/login");
				return modelAndView;
			}

			if (!vcode.equalsIgnoreCase((String) getSession(request).getAttribute(
					"checkcode"))) {
				modelAndView.addObject("error", "验证码错误！");
				modelAndView.setViewName("/ssadmin/login");
				return modelAndView;
			}

			List<Fadmin> admins = this.adminService.findByProperty("fname", name);
			if(admins == null || admins.size() !=1){
				modelAndView.addObject("error", "管理员不存在！");
				modelAndView.setViewName("/ssadmin/login");
				return modelAndView;
			}
			Subject admin = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(name,
					Utils.MD5(password,admins.get(0).getSalt()));
			token.setRememberMe(true);
			token.setHost(getIpAddr(request));
			try {
				admin.login(token);
				//admin超级管理员登录时，发送短信通知
				if(name.toLowerCase().equals("admin")) {
					adminLoginNotice(ip);
				}else{
					adminLoginNotice(ip,admins.get(0));
				}
			} catch (Exception e) {
				token.clear();
				this.frontValidateService.updateLimitCount(ip,
						CountLimitTypeEnum.AdminLogin);
				modelAndView.addObject("error", e.getMessage());
				modelAndView.setViewName("/ssadmin/login");
				return modelAndView;
			}
		}
		modelAndView.setViewName("redirect:/ssadmin/index.html");
		return modelAndView;
	}

	public void adminLoginNotice(String ip){
		String flag = constantMap.getString("isnoticesms");
		if(StringUtils.isNotBlank(ip)) {
			ip = ip.replace(".", ":");
		}
		if(StringUtils.isBlank(flag) || !flag.equals("1")) {
			return;
		}

		String date = DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond);
		String content = date + " admin帐号正在登录，登录ip为 "+ip+"，请确认是否本人操作";

		//开启短信通知
		String phone = constantMap.getString("adminlogin");
		String[] phone_arr = phone.split(",");
		for(int i=0; i<phone_arr.length;i++) {
			int retCode = MessagesUtils.send(
					map.getString("messageName"),
					map.getString("messagePassword"),
					map.getString("messageURL"),
					"SMS_49190190",
					map.getString("webName"),
					content,
					phone_arr[i]);
			if(retCode == 1) {
				LOG.i(this, "admin超级管理员登录，短信通知成功，手机号："+phone_arr[i]);
			}else{
				LOG.i(this, "admin超级管理员登录，短信通知失败，手机号："+phone_arr[i]);
			}
		}

	}

    /**
	 * 后台用户登录短信
	 * @param ip
	 * @param fadmin
     */
	private void adminLoginNotice(String ip,Fadmin fadmin){
		if(StringUtils.isEmpty(fadmin.getfTelephone()) || fadmin.getfTelephone().length() < 11) return;

		String date = DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond);
		String content = fadmin.getFname() + "正在后台登录，登录ip为 "+ip+"，登录时间："+date+",请确认是否本人操作";
		int retCode = MessagesUtils.send(
				map.getString("messageName"),
				map.getString("messagePassword"),
				map.getString("messageURL"),
				"SMS_49190190",
				map.getString("webName"),
				content,
				fadmin.getfTelephone());
		if(retCode == 1) {
			LOG.i(this, "admin超级管理员登录，短信通知成功，手机号："+fadmin.getfTelephone());
		}else{
			LOG.i(this, "admin超级管理员登录，短信通知失败，手机号："+fadmin.getfTelephone());
		}

	}

}
