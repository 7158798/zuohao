package com.ruizton.main.controller.admin;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.jpush.api.utils.StringUtils;
import com.google.gson.Gson;
import com.opensymphony.oscache.util.StringUtil;
import com.ruizton.main.Enum.*;
import com.ruizton.main.aspect.SysLog;
import com.ruizton.main.auto.order.User;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.*;
import com.ruizton.main.service.front.UtilsService;
import com.ruizton.util.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.CapitaloperationService;
import com.ruizton.main.service.admin.ScoreService;
import com.ruizton.main.service.admin.SystemArgsService;
import com.ruizton.main.service.admin.UserService;
import com.ruizton.main.service.admin.UsersettingService;
import com.ruizton.main.service.front.FrontUserService;
import org.springframework.web.util.HtmlUtils;

@Controller
public class UserController extends BaseController {

	
	// 每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();

	@RequestMapping("/ssadmin/userList")
	public ModelAndView Index() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/userList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String uid = request.getParameter("uid");
		String startDate = request.getParameter("startDate");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (floginName like '%" + keyWord + "%' or \n");
				filter.append("fnickName like '%" + keyWord + "%'  or \n");
				filter.append("frealName like '%" + keyWord + "%'  or \n");
				filter.append("ftelephone like '%" + keyWord + "%'  or \n");
				filter.append("femail like '%" + keyWord + "%'  or \n");
				filter.append("fidentityNo like '%" + keyWord + "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}

		Map<Integer,String> typeMap = new HashMap<Integer,String>();
		typeMap.put(0, "全部");
		typeMap.put(UserStatusEnum.NORMAL_VALUE,
				UserStatusEnum.getEnumString(UserStatusEnum.NORMAL_VALUE));
		typeMap.put(UserStatusEnum.FORBBIN_VALUE,
				UserStatusEnum.getEnumString(UserStatusEnum.FORBBIN_VALUE));
		typeMap.put(UserStatusEnum.FREEZE_VALIDATE,
				UserStatusEnum.getEnumString(UserStatusEnum.FREEZE_VALIDATE));
		typeMap.put(UserStatusEnum.FREEZE_VALIDATE_FAILED,
				UserStatusEnum.getEnumString(UserStatusEnum.FREEZE_VALIDATE_FAILED));
		typeMap.put(UserStatusEnum.FREEZE_VALUE,
				UserStatusEnum.getEnumString(UserStatusEnum.FREEZE_VALUE));
		modelAndView.addObject("typeMap", typeMap);

		if (request.getParameter("ftype") != null
				&& request.getParameter("ftype").trim().length() > 0) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filter.append("and fstatus=" + request.getParameter("ftype")
						+ " \n");
			}
			modelAndView.addObject("ftype", request.getParameter("ftype"));
		}

		try {
			if (request.getParameter("troUid") != null
					&& request.getParameter("troUid").trim().length() > 0) {
				int troUid = Integer.parseInt(request.getParameter("troUid"));
				filter.append("and fIntroUser_id.fid=" + troUid + " \n");
				modelAndView.addObject("troUid", troUid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (request.getParameter("troNo") != null
					&& request.getParameter("troNo").trim().length() > 0) {
				String troNo = request.getParameter("troNo").trim();
				filter.append("and fuserNo like '%" + troNo + "%' \n");
				modelAndView.addObject("troNo", troNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}

		List<Fuser> list = this.userService.list(
				(currentPage - 1) * numPerPage, numPerPage, filter + "", true);
		modelAndView.addObject("startDate", startDate);
		modelAndView.addObject("userList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "listUser");
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fuser", filter + ""));
		return modelAndView;
	}
	
	@RequestMapping("/ssadmin/viewUser1")
	public ModelAndView viewUser1() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/viewUser1");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			filter.append("and (floginName like '%" + keyWord + "%' or \n");
			filter.append("fnickName like '%" + keyWord + "%'  or \n");
			filter.append("frealName like '%" + keyWord + "%'  or \n");
			filter.append("ftelephone like '%" + keyWord + "%'  or \n");
			filter.append("femail like '%" + keyWord + "%'  or \n");
			filter.append("fidentityNo like '%" + keyWord + "%' )\n");
			modelAndView.addObject("keywords", keyWord);
		}

		Map typeMap = new HashMap();
		typeMap.put(0, "全部");
		typeMap.put(UserStatusEnum.NORMAL_VALUE,
				UserStatusEnum.getEnumString(UserStatusEnum.NORMAL_VALUE));
		typeMap.put(UserStatusEnum.FORBBIN_VALUE,
				UserStatusEnum.getEnumString(UserStatusEnum.FORBBIN_VALUE));
		modelAndView.addObject("typeMap", typeMap);

		if (request.getParameter("ftype") != null
				&& request.getParameter("ftype").trim().length() > 0) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filter.append("and fstatus=" + request.getParameter("ftype")
						+ " \n");
			}
			modelAndView.addObject("ftype", request.getParameter("ftype"));
		}

		try {
			if (request.getParameter("troUid") != null
					&& request.getParameter("troUid").trim().length() > 0) {
				int troUid = Integer.parseInt(request.getParameter("troUid"));
				filter.append("and fIntroUser_id.fid=" + troUid + " \n");
				modelAndView.addObject("troUid", troUid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(request.getParameter("cid") != null){
			int cid =Integer.parseInt(request.getParameter("cid"));
			Fcapitaloperation c = this.capitaloperationService.findById(cid);
			filter.append("and fid ="+c.getFuser().getFid()+" \n");
			modelAndView.addObject("cid",cid);
		}

		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by fregisterTime \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}

		List<Fuser> list = this.userService.list(
				(currentPage - 1) * numPerPage, numPerPage, filter + "", true);
		modelAndView.addObject("userList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "viewUser1");
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fuser", filter + ""));
		return modelAndView;
	}

	
	@RequestMapping("/ssadmin/userLookup")
	public ModelAndView userLookup() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/userLookup");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");

		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (floginName like '%" + keyWord + "%' or \n");
				filter.append("fnickName like '%" + keyWord + "%'  or \n");
				filter.append("frealName like '%" + keyWord + "%'  or \n");
				filter.append("ftelephone like '%" + keyWord + "%'  or \n");
				filter.append("femail like '%" + keyWord + "%'  or \n");
				filter.append("fidentityNo like '%" + keyWord + "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		List<Fuser> list = this.userService.list(
				(currentPage - 1) * numPerPage, numPerPage, filter + "", true);
		modelAndView.addObject("userList1", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "operationLogList");
		modelAndView.addObject("currentPage", currentPage);
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fuser", filter + ""));
		return modelAndView;
	}

	@RequestMapping("/ssadmin/userAuditList")
	public ModelAndView userAuditList() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/userAuditList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (floginName like '%" + keyWord + "%' or \n");
				filter.append("fnickName like '%" + keyWord + "%'  or \n");
				filter.append("frealName like '%" + keyWord + "%'  or \n");
				filter.append("ftelephone like '%" + keyWord + "%'  or \n");
				filter.append("femail like '%" + keyWord + "%'  or \n");
				filter.append("fidentityNo like '%" + keyWord + "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		filter.append("and fpostRealValidate=1 and fhasRealValidate=0 \n");

		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}
		List<Fuser> list = this.userService.listUserForAudit((currentPage - 1)
				* numPerPage, numPerPage, filter + "", true);
		modelAndView.addObject("userList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "listUser");
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fuser", filter + ""));
		return modelAndView;
	}

	@RequestMapping("/ssadmin/ajaxDone")
	public ModelAndView ajaxDone() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		return modelAndView;
	}

    /**
	 * 禁用用户  status：1禁用  2解除禁用 3.重设密码  4 重设交易密码  5 冻结
	 * @return
	 * @throws Exception
     */
	@RequestMapping("/ssadmin/userForbbin")
	@SysLog(code = ModuleConstont.USER_OPERATION, method = "修改用户禁用状态/密码信息")
	public ModelAndView userForbbin() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		// modelAndView.setViewName("redirect:/pages/ssadmin/comm/ajaxDone.jsp")
		// ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		int status = Integer.parseInt(request.getParameter("status"));
		Fuser user = this.userService.findById(fid);
		String content = "";
		if (status == 1) {
			if (user.getFstatus() == UserStatusEnum.FORBBIN_VALUE) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "会员已禁用，无需做此操作");
				return modelAndView;
			}
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", "禁用成功");
			user.setFstatus(UserStatusEnum.FORBBIN_VALUE);
			
			this.realTimeData.addBlackUser(user.getFid()) ;
		} else if (status == 2) {
			if (user.getFstatus() != UserStatusEnum.FORBBIN_VALUE) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "会员状态不是禁用，无需做此操作");
				return modelAndView;
			}
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", "解除禁用成功");
			user.setFstatus(UserStatusEnum.NORMAL_VALUE);
			
			this.realTimeData.removeBlackUser(user.getFid()) ;
		} else if (status == 3) {// 重设登陆密码
			String pwd = generatePassword();
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", "重设登陆密码成功");

			user.setFloginPassword(Utils.MD5(pwd,user.getSalt()));
			content = "您的登录密码已被重置为："+pwd+",若有疑问请致电400-900-6615";
		} else if (status == 4) {// 重设交易密码
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", "会员交易密码重置成功");
			user.setFtradePassword(null);
			content = "您的交易密码已被重置，请进入个人中心进行设置,若有疑问请致电400-900-6615";
		}else if(status == 5){
			if (user.getFstatus() != UserStatusEnum.NORMAL_VALUE ) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "会员状态已不是正常，无需做此操作");
				return modelAndView;
			}
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", "会员交易密码重置成功");
			user.setFstatus(UserStatusEnum.FREEZE_VALUE);
		}

		//发送短信通知
       if(StringUtils.isNotEmpty(content)){
		   try{
			   sendMessage(user.getFid(),content);
		   }catch (Exception e){
			   modelAndView.addObject("statusCode", 300);
			   modelAndView.addObject("message", "重置成功，短信发送失败");
			   return modelAndView;
		   }
	   }

		this.userService.updateObj(user);
		return modelAndView;
	}



	public static String generatePassword() {
		String[] pa = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
				"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < 4; i++) {
			sb.append(pa[(Double.valueOf(Math.random() * pa.length).intValue())]);
		}
		sb.append((int)(Math.random()*100));
		return sb.toString();
	}


	@RequestMapping("/ssadmin/auditUser")
	public ModelAndView auditUser() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int status = Integer.parseInt(request.getParameter("status"));
		int fid = Integer.parseInt(request.getParameter("uid"));

		Fuser user = this.userService.findById(fid);
		Fscore fscore = user.getFscore();
		Fuser fintrolUser = null;
		Fintrolinfo introlInfo = null;
		Fvirtualwallet fvirtualwallet = null;
		String[] auditSendCoin = this.systemArgsService.getValue("auditSendCoin").split("#");
		int coinID = Integer.parseInt(auditSendCoin[0]);
		double coinQty = Double.valueOf(auditSendCoin[1]);
		if (status == 1) {
			user.setFhasRealValidateTime(Utils.getTimestamp());
			user.setFhasRealValidate(true);
			user.setFisValid(true);
			if(!fscore.isFissend() && user.getfIntroUser_id() != null){
				fintrolUser = this.userService.findById(user.getfIntroUser_id().getFid());
				fintrolUser.setfInvalidateIntroCount(fintrolUser.getfInvalidateIntroCount()+1);
				fscore.setFissend(true);
			}
			if(coinQty >0){
				fvirtualwallet = this.frontUserService.findVirtualWalletByUser(user.getFid(), coinID);
				fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+coinQty);
				introlInfo = new Fintrolinfo();
				introlInfo.setFcreatetime(Utils.getTimestamp());
				introlInfo.setFiscny(false);
				introlInfo.setFqty(coinQty);
				introlInfo.setFuser(user);
				introlInfo.setFname(fvirtualwallet.getFvirtualcointype().getFname());
				introlInfo.setFtitle("实名认证成功，奖励"+fvirtualwallet.getFvirtualcointype().getFname()+coinQty+"个！");
			}
		} else {
			user.setFhasRealValidate(false);
			user.setFpostRealValidate(false);
			user.setFidentityNo(null);
			user.setFrealName(null);
		}
		try {
			this.userService.updateObj(user,fscore,fintrolUser,fvirtualwallet,introlInfo);
		} catch (Exception e) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "网络异常");
			return modelAndView;
		}
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType", "closeCurrent");
		modelAndView.addObject("message", "审核成功");

		return modelAndView;
	}

	@RequestMapping("ssadmin/goUserJSP")
	public ModelAndView goUserJSP() throws Exception {
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(url);
		if (request.getParameter("uid") != null) {
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fuser user = this.userService.findById(fid);
			modelAndView.addObject("fuser", user);
			
			List<Fusersetting> alls = this.usersettingService.list(0, 0, "where fuser.fid="+fid, false);
			Fusersetting usersetting = alls.get(0);
			modelAndView.addObject("usersetting", usersetting);

			Map<Integer,String> map = new HashMap<Integer,String>();
			map.put(IdentityTypeEnum.SHENFENZHENG, IdentityTypeEnum
					.getEnumString(IdentityTypeEnum.SHENFENZHENG));
			map.put(IdentityTypeEnum.JUNGUANGZHEN, IdentityTypeEnum
					.getEnumString(IdentityTypeEnum.JUNGUANGZHEN));
			map.put(IdentityTypeEnum.HUZHAO,
					IdentityTypeEnum.getEnumString(IdentityTypeEnum.HUZHAO));
			map.put(IdentityTypeEnum.TAIWAN,
					IdentityTypeEnum.getEnumString(IdentityTypeEnum.TAIWAN));
			map.put(IdentityTypeEnum.GANGAO,
					IdentityTypeEnum.getEnumString(IdentityTypeEnum.GANGAO));
			modelAndView.addObject("identityTypeMap", map);
		}
		if(request.getParameter("img") != null){
			modelAndView.addObject("imgurl",request.getParameter("img"));
		}
		
		Map<Integer,String> map = new HashMap<Integer,String>();
		for(int i=1;i<=6;i++){
			map.put(i, "VIP"+i);
		}
		modelAndView.addObject("typeMap", map);


		Map<Integer,String> bankTypes = new HashMap<Integer,String>() ;
		for(int i=1;i<=BankTypeEnum.QT;i++){
			if(BankTypeEnum.getEnumString(i) != null && BankTypeEnum.getEnumString(i).trim().length() >0){
				bankTypes.put(i,BankTypeEnum.getEnumString(i)) ;
			}
		}
		modelAndView.addObject("bankTypes", bankTypes) ;
		
		return modelAndView;
	}

	@RequestMapping("ssadmin/updateUserLevel")
	public ModelAndView updateUserLevel() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		int fid = Integer.parseInt(request.getParameter("fid"));
		Fuser user = this.userService.findById(fid);
		Fscore score = user.getFscore();
		int newLevel = Integer.parseInt(request.getParameter("newLevel"));
		score.setFlevel(newLevel);
		this.scoreService.updateObj(score);

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType", "closeCurrent");
		modelAndView.addObject("message", "修改成功");
		return modelAndView;
	}

	@RequestMapping("ssadmin/updateIntroCount")
	public ModelAndView updateIntroCount(
			@RequestParam(required = true) int fInvalidateIntroCount)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fuser user = this.userService.findById(fid);
		user.setfInvalidateIntroCount(fInvalidateIntroCount);
		this.userService.updateObj(user);

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType", "closeCurrent");
		modelAndView.addObject("message", "修改成功");
		return modelAndView;
	}

	/**
	 * 修改用户等级
	 * @return
	 * @throws Exception
     */
	@RequestMapping("ssadmin/updateUserGrade")
	@SysLog(code = ModuleConstont.USER_OPERATION, method = "修改用户等级")
	public ModelAndView updateUserGrade()
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fuser user = this.userService.findById(fid);
		Fscore fscore = user.getFscore();
		fscore.setFlevel(Integer.parseInt(request.getParameter("fuserGrade")));
		this.scoreService.updateObj(fscore);

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType", "closeCurrent");
		modelAndView.addObject("message", "修改成功");
		return modelAndView;
	}

	/**
	 * 修改用户推荐人
	 * @return
	 * @throws Exception
     */
	@RequestMapping("ssadmin/updateIntroPerson")
	@SysLog(code = ModuleConstont.USER_OPERATION, method = "修改用户推荐人")
	public ModelAndView updateIntroPerson()
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fuser user = this.userService.findById(fid);
		String id = request.getParameter("fintrolId");

		if(id != null && id.trim().length() >0){
			int fintrolId = Integer.parseInt(id.trim());
			Fuser fintrolUser = this.userService.findById(fintrolId);
			if(fintrolUser == null){
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "用户不存在");
				return modelAndView;
			}
			user.setfIntroUser_id(fintrolUser);
		}else{
			user.setfIntroUser_id(null);
		}

		
		this.userService.updateObj(user);
		
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType", "closeCurrent");
		modelAndView.addObject("message", "修改成功");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/updateUserScore")
	public ModelAndView updateUserScore()
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fusersetting usersetting = this.usersettingService.findById(fid);
		usersetting.setFscore(Double.valueOf(request.getParameter("fscore")));
		this.usersettingService.updateObj(usersetting);

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType", "closeCurrent");
		modelAndView.addObject("message", "修改成功");
		return modelAndView;
	}

	@RequestMapping("/ssadmin/cancelGoogleCode")
	@SysLog(code = ModuleConstont.USER_OPERATION, method = "重置用户GOOGLE")
	public ModelAndView cancelGoogleCode() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fuser user = this.userService.findById(fid);
        Boolean isbind = user.getFgoogleBind();

		user.setFgoogleAuthenticator(null);
		user.setFgoogleBind(false);
		user.setFgoogleurl(null);
		user.setFgoogleValidate(false);
		user.setFopenSecondValidate(false);
		this.userService.updateObj(user);

		try{
			if(isbind){
               sendMessage(user,"您绑定的GOOGLE验证已被管理员重置,若有疑问请致电400-900-6615");
			}
		}catch (Exception e){
			LOG.e(this.getClass(),"重置Google，发送验证码失败");
		}

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "重置谷歌认证成功");
		return modelAndView;
	}

	/**
	 * 重设手机号
	 * @return
	 * @throws Exception
     */
	@RequestMapping("/ssadmin/cancelTel")
	@SysLog(code = ModuleConstont.USER_OPERATION, method = "重置用户手机号")
	public ModelAndView cancelTel() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fuser user = this.userService.findById(fid);

		try{
            sendMessage(user,"您绑定的手机已被管理员重置,若有疑问请致电400-900-6615");
		}catch (Exception e){
			LOG.i(this.getClass(),"取消手机发送代码失败");
		}
		user.setFtelephone(null);
		user.setFisTelephoneBind(false);
		user.setFareaCode(null);
		this.userService.updateObj(user);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "重置手机号码成功");
		return modelAndView;
	}


	 
	private static enum ExportFiled {
		会员UID,推荐人UID,会员登陆名,会员状态,昵称,真实姓名,会员等级,累计推荐注册数,电话号码,
		邮箱地址,证件类型,证件号码,注册时间,上次登陆时间;
	}

	private List<Fuser> getUserList() {
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		String uid = request.getParameter("uid");
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (floginName like '%" + keyWord + "%' or \n");
				filter.append("fnickName like '%" + keyWord + "%'  or \n");
				filter.append("frealName like '%" + keyWord + "%'  or \n");
				filter.append("ftelephone like '%" + keyWord + "%'  or \n");
				filter.append("femail like '%" + keyWord + "%'  or \n");
				filter.append("fidentityNo like '%" + keyWord + "%' )\n");
			}
		}
		if (uid != null && uid.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(uid);
				filter.append("and fid =" + fid + " \n");
			} catch (Exception e) {}
		}

		if (request.getParameter("ftype") != null
				&& request.getParameter("ftype").trim().length() > 0) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filter.append("and fstatus=" + request.getParameter("ftype")
						+ " \n");
			}
		}

		try {
			if (request.getParameter("troUid") != null
					&& request.getParameter("troUid").trim().length() > 0) {
				int troUid = Integer.parseInt(request.getParameter("troUid"));
				filter.append("and fIntroUser_id.fid=" + troUid + " \n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}

		List<Fuser> list = this.userService.list(0, 0, filter + "", false);
		return list;
	}

	@RequestMapping("ssadmin/userExport")
	@SysLog(code = ModuleConstont.USER_OPERATION, method = "导出会员信息")
	public ModelAndView userExport(HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=userList.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (ExportFiled filed : ExportFiled.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}

		List<Fuser> userList = getUserList();
		for (Fuser user : userList) {
			e.createRow(rowIndex++);
			for (ExportFiled filed : ExportFiled.values()) {
				switch (filed) {
				case 会员UID:
					e.setCell(filed.ordinal(), user.getFid());
					break;
				case 推荐人UID:
					if(user.getfIntroUser_id() != null)
					e.setCell(filed.ordinal(), user.getfIntroUser_id().getFid());
					break;
				case 会员登陆名:
					e.setCell(filed.ordinal(), user.getFloginName());
					break;
				case 会员状态:
					e.setCell(filed.ordinal(), user.getFstatus_s());
					break;
				case 昵称:
					e.setCell(filed.ordinal(), user.getFnickName());
					break;
				case 真实姓名:
					e.setCell(filed.ordinal(), user.getFrealName());
					break;
				case 会员等级:
					if(user.getFscore() != null)
					e.setCell(filed.ordinal(), "VIP"
							+ user.getFscore().getFlevel());
					break;
				case 累计推荐注册数:
					e.setCell(filed.ordinal(), user.getfInvalidateIntroCount());
					break;
				case 电话号码:
					e.setCell(filed.ordinal(), user.getFtelephone());
					break;
				case 邮箱地址:
					e.setCell(filed.ordinal(), user.getFemail());
					break;
				case 证件类型:
					e.setCell(filed.ordinal(), user.getFidentityType_s());
					break;
				case 证件号码:
					e.setCell(filed.ordinal(), user.getFidentityNo());
					break;
				case 注册时间:
					e.setCell(filed.ordinal(), DateHelper.date2String(user.getFregisterTime(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
					break;
				case 上次登陆时间:
					e.setCell(filed.ordinal(), DateHelper.date2String(user.getFlastLoginTime(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
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
	
	@RequestMapping("/ssadmin/setUserNo")
	public ModelAndView setUserNo() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("fid"));
		Fuser user = this.userService.findById(fid);
		String userNo = request.getParameter("fuserNo");
		if(userNo == null || userNo.trim().length() ==0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "服务中心号不能为空");
			return modelAndView;
		}else if(userNo.trim().length() >100){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "服务中心号长度不能大于100个字符");
			return modelAndView;
		}
		
		if(user.getFuserNo() != null && user.getFuserNo().trim().length() > 0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "该用户已存在服务中心号，不允许修改！");
			return modelAndView;
		}
		
		String filter = "where fuserNo='"+userNo+"'";
		List<Fuser> fusers = this.userService.list(0, 0, filter, false);
		if(fusers.size() >0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "该服务中心号已存在！");
			return modelAndView;
		}

		user.setFuserNo(userNo);
		this.userService.updateObj(user);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType","closeCurrent");
		modelAndView.addObject("message", "服务中心号设置成功");
		return modelAndView;
	}
	
	@RequestMapping("/ssadmin/cancelPhone")
	public ModelAndView cancelPhone() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fuser user = this.userService.findById(fid);
		user.setFtelephone(null);
		user.setFisTelephoneBind(false);
		user.setFisTelValidate(false);
		this.userService.updateObj(user);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "重置手机绑定成功");
		return modelAndView;
	}
	
	@RequestMapping("/ssadmin/addUsers")
	@SysLog(code = ModuleConstont.USER_OPERATION, method = "增加10个会员")
	public ModelAndView addUsers() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		
		for (int i=0;i<10;i++) {
			Fuser fuser = new Fuser() ;
			
			String regName = Utils.getRandomString(10)+"@jucaifu.com";
			fuser.setSalt(Utils.getUUID());
			fuser.setFrealName("系统生成");
			fuser.setfIntroUser_id(null) ;
			fuser.setFregType(RegTypeEnum.EMAIL_VALUE);
			fuser.setFemail(regName) ;
			fuser.setFisMailValidate(true) ;
			fuser.setFnickName(regName.split("@")[0]) ;
			fuser.setFloginName(regName) ;
			
			
			fuser.setFregisterTime(Utils.getTimestamp()) ;
			fuser.setFloginPassword(Utils.MD5("123456abc",fuser.getSalt())) ;
			fuser.setFtradePassword(null) ;
			String ip = getIpAddr(request) ;
			fuser.setFregIp(ip);
			fuser.setFlastLoginIp(ip);
			fuser.setFstatus(UserStatusEnum.NORMAL_VALUE) ;
			fuser.setFlastLoginTime(Utils.getTimestamp()) ;
			fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
			boolean saveFlag = false ;
			try {
				saveFlag = this.frontUserService.saveRegister(fuser) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "操作成功");
		return modelAndView;
	}
	
	@RequestMapping("/ssadmin/setTiger")
	public ModelAndView setTiger() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fuser user = this.userService.findById(fid);
		if(user.isFistiger()){
			user.setFistiger(false);
		}else{
			user.setFistiger(true);
		}
		this.userService.updateObj(user);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "设置成功");
		return modelAndView;
	}
	
	
	@RequestMapping("/ssadmin/auditUserALL")
	public ModelAndView auditUserALL() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		String ids = request.getParameter("ids");
		String[] idString = ids.split(",");
		int type = Integer.parseInt(request.getParameter("type"));
		for(int i=0;i<idString.length;i++){
			int id = Integer.parseInt(idString[i]);
			Fuser user = this.userService.findById(id);
			Fscore fscore = user.getFscore();
			Fuser fintrolUser = null;
			Fvirtualwallet fvirtualwallet = null;
			String[] auditSendCoin = this.systemArgsService.getValue("auditSendCoin").split("#");
			int coinID = Integer.parseInt(auditSendCoin[0]);
			double coinQty = Double.valueOf(auditSendCoin[1]);
			Fintrolinfo introlInfo = null;
			if (type == 1) {
				user.setFhasRealValidateTime(Utils.getTimestamp());
				user.setFhasRealValidate(true);
				user.setFisValid(true);
				if(!fscore.isFissend() && user.getfIntroUser_id() != null){
					fintrolUser = this.userService.findById(user.getfIntroUser_id().getFid());
					fintrolUser.setfInvalidateIntroCount(fintrolUser.getfInvalidateIntroCount()+1);
					fscore.setFissend(true);
				}
				if(coinQty >0){
					fvirtualwallet = this.frontUserService.findVirtualWalletByUser(user.getFid(), coinID);
					fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+coinQty);
					introlInfo = new Fintrolinfo();
					introlInfo.setFcreatetime(Utils.getTimestamp());
					introlInfo.setFiscny(false);
					introlInfo.setFqty(coinQty);
					introlInfo.setFuser(user);
					introlInfo.setFname(fvirtualwallet.getFvirtualcointype().getFname());
					introlInfo.setFtitle("实名认证成功，奖励"+fvirtualwallet.getFvirtualcointype().getFname()+coinQty+"个！");
				}
			} else {
				user.setFhasRealValidate(false);
				user.setFpostRealValidate(false);
				user.setFidentityNo(null);
				user.setFrealName(null);
			}
			try {
				this.userService.updateObj(user,fscore,fintrolUser,fvirtualwallet,introlInfo);
			} catch (Exception e) {
				continue;
			}
		}
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "审核成功");
		return modelAndView;
	}


    /**
	 *
	 *后台实名认证
	 * @return
     * @throws Exception
     */
	@RequestMapping(value="/ssadmin/validateIdentity")
	@SysLog(code = ModuleConstont.USER_OPERATION, method = "实名认证")
	public ModelAndView validateIdentity(@RequestParam String identityNo,
										@RequestParam String realName,
										 @RequestParam Integer fid) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");

		Fuser fuser = this.frontUserService.findById(fid) ;
		if(fuser.getFpostRealValidate()){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "该用户已通过实名认证");
			return modelAndView;
		}
		identityNo = identityNo.toLowerCase();
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",
				"3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
				"9", "10", "5", "8", "4", "2" };
		if (identityNo.trim().length() != 15 && identityNo.trim().length() != 18) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "身份证号码长度应该为15位或18位");
			return modelAndView;
		}


		String Ai = "";
		if (identityNo.length() == 18) {
			Ai = identityNo.substring(0, 17);
		} else if (identityNo.length() == 15) {
			Ai = identityNo.substring(0, 6) + "19" + identityNo.substring(6, 15);
		}
		if (Utils.isNumeric(Ai) == false) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "身份证号码有误");
			return modelAndView;

		}
		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (Utils.isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "身份证号码有误");
			return modelAndView;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(
					strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "身份证号码有误");
				return modelAndView;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "身份证号码有误");
			return modelAndView;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "身份证号码有误");
			return modelAndView;
		}
		// =====================(end)=====================

		// ================ 地区码时候有效 ================
		Hashtable h = Utils.getAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "身份证号码有误");
			return modelAndView;
		}
		// ==============================================

		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi
					+ Integer.parseInt(String.valueOf(Ai.charAt(i)))
					* Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (identityNo.length() == 18) {
			if (Ai.equals(identityNo) == false) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "身份证号码有误");
				return modelAndView;
			}
		} else {
			return modelAndView;
		}

		if (realName.trim().length() > 50) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "真实姓名不合法");
			return modelAndView;
		}

		IDCardVerifyUtil verifyUtil = new IDCardVerifyUtil();
		boolean isTrue = verifyUtil.isRealPerson(realName, identityNo);
		if(!isTrue){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "姓名与身份证号有误，请核对!");
			return modelAndView;
		}

		String phone = request.getParameter("phone");
		if(StringUtils.isNotEmpty(phone)){
			//验证手机号
			if(!phone.matches("^\\d{10,14}$")){//手機格式不對
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "手机格式有误，请核对!");
				return modelAndView;
			}
			fuser.setFareaCode("86") ;
			fuser.setFtelephone(phone) ;
			fuser.setFisTelephoneBind(true) ;
			fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
		}
		String tradePwd = request.getParameter("tradePwd");
		if(StringUtils.isNotEmpty(tradePwd)){
			if(tradePwd.length() < 6){
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "交易密码必须为6位数或以上");
				return modelAndView;
			}
			fuser.setFtradePassword(Utils.MD5(tradePwd,fuser.getSalt())) ;
		}

//		Fscore fscore = fuser.getFscore();
//		Fuser fintrolUser = null;
//		Fintrolinfo introlInfo = null;
//		Fvirtualwallet fvirtualwallet = null;
//		String[] auditSendCoin = this.systemArgsService.getValue("auditSendCoin").split("#");
//		int coinID = Integer.parseInt(auditSendCoin[0]);
//		double coinQty = Double.valueOf(auditSendCoin[1]);
//		if(!fscore.isFissend() && fuser.getfIntroUser_id() != null){
//			fintrolUser = this.frontUserService.findById(fuser.getfIntroUser_id().getFid());
//			fintrolUser.setfInvalidateIntroCount(fintrolUser.getfInvalidateIntroCount()+1);
//			fscore.setFissend(true);
//		}
//		if(coinQty >0){
//			fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), coinID);
//			fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+coinQty);
//			introlInfo = new Fintrolinfo();
//			introlInfo.setFcreatetime(Utils.getTimestamp());
//			introlInfo.setFiscny(false);
//			introlInfo.setFqty(coinQty);
//			introlInfo.setFuser(fuser);
//			introlInfo.setFname(fvirtualwallet.getFvirtualcointype().getFname());
//			introlInfo.setFtitle("实名认证成功，奖励"+fvirtualwallet.getFvirtualcointype().getFname()+coinQty+"个！");
//		}
		fuser.setFidentityType(0) ;
		fuser.setFidentityNo(identityNo) ;
		fuser.setFrealName(realName) ;
		fuser.setFpostRealValidate(true) ;
		fuser.setFpostRealValidateTime(Utils.getTimestamp()) ;
		fuser.setFhasRealValidate(true);
		fuser.setFhasRealValidateTime(Utils.getTimestamp());
		fuser.setFisValid(true);
		fuser.setKyclevel(ValidateKycLevelEnum.IDENTITY_VALIDATE.getKey());
		try {
			String ip = getIpAddr(request) ;
			this.frontUserService.updateFuser(fuser) ;
		//	this.userService.updateObj(fuser, fscore, fintrolUser, fvirtualwallet, introlInfo);
		//	this.SetSession(fuser,request) ;
		} catch (Exception e) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "保存数据库失败!");
			return modelAndView;
		}
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "认证成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}




	/**
	 * 增加提现银行
	 * */
	@RequestMapping("/ssadmin/addBankAccount")
	@SysLog(code = ModuleConstont.USER_OPERATION, method = "绑定银行卡")
	public ModelAndView updateOutAddress(@RequestParam Integer fid,
										@RequestParam String account,
										@RequestParam String address,
	                                    @RequestParam Integer openBankType ) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");

		Fuser fuser = this.frontUserService.findById(fid) ;
		String city = request.getParameter("city");
		if(account.length() < 10){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "银行帐号不合法");
			return modelAndView;
		}

		if(address.length() > 300){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "详细地址太长");
			return modelAndView;
		}

		if(city.length() > 50){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "开户城市太长");
			return modelAndView;


		}

//		if(fuser.getFrealName().equals(payeeAddr)){
//			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "银行卡账号名必须与您的实名认证姓名一致") ;
//			return jsonObject.toString();
//		}

		String bankName = BankTypeEnum.getEnumString(openBankType);
		if(bankName == null || bankName.trim().length() ==0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "开户银行不存在");
			return modelAndView;
		}

		//校验银行卡
		try {
			String ret = BankUtil.validate(account) ;
			JSONObject retj = JSONObject.fromObject(ret) ;
			boolean flag = false ;
			if(retj.getInt("error_code")!=0){
				flag = true ;
			}else{
				String retBank = retj.getJSONObject("result").getString("bank") ;
				if(retBank.indexOf(bankName)==-1 && openBankType!=BankTypeEnum.QT){
					flag = true ;
				}
			}
			if(flag == true ){
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "银行卡号码或者开户行不匹配");
				return modelAndView;
			}
		} catch (Exception e1) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "银行卡号码或者开户行不匹配");
			return modelAndView;
		}

		int count = this.utilsService.count(" where fuser.fid="+fuser.getFid()+" and fbankType="+openBankType+" and fbankNumber='"+account+"' and fstatus="+BankInfoWithdrawStatusEnum.NORMAL_VALUE+" ", FbankinfoWithdraw.class) ;
		if(count>0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "银行卡号码已经存在");
			return modelAndView;

		}


		//成功
		try {
			FbankinfoWithdraw fbankinfoWithdraw = new FbankinfoWithdraw();
			fbankinfoWithdraw.setFbankNumber(account) ;
			fbankinfoWithdraw.setFbankType(openBankType) ;
			fbankinfoWithdraw.setFcreateTime(Utils.getTimestamp()) ;
			fbankinfoWithdraw.setFname(bankName) ;
			fbankinfoWithdraw.setFstatus(BankInfoStatusEnum.NORMAL_VALUE) ;
			fbankinfoWithdraw.setFaddress(address);
			fbankinfoWithdraw.setFothers(address);
			fbankinfoWithdraw.setFuser(fuser);
			this.frontUserService.updateBankInfoWithdraw(fbankinfoWithdraw) ;
		} catch (Exception e) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "网络异常");
			return modelAndView;
		}finally{
		}

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "添加成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}


	/**
	 * 后台设置交易密码
     * @return
     * @throws Exception
     */
	@RequestMapping(value="/ssadmin/updateTradePwd")
	public ModelAndView setTradePassword(@RequestParam String pwd,
								@RequestParam int fid
	) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");

		Fuser fuser = this.frontUserService.findById(fid) ;

		if(fuser.isFisTelephoneBind()){

			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "该用户已绑定了手机，不能在后台设置交易密码");
			return modelAndView;
		}

		if(StringUtils.isEmpty(pwd) || pwd.length() < 6){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "交易密码必须为6位数或以上");
			return modelAndView;
		}
		if(StringUtils.isNotEmpty(fuser.getFtradePassword())){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "该用户已设置了交易密码");
			return  modelAndView;
		}

		fuser.setFtradePassword(Utils.MD5(pwd,fuser.getSalt())) ;

		this.frontUserService.updateFuser(fuser) ;

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "设置成功");
		return modelAndView;

	}


	/**
	 * 用户升级认证列表
	 * @return
	 * @throws Exception
     */
	@RequestMapping("/ssadmin/userGoUpList")
	public ModelAndView goUpList() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/userGoUpList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
//		String uid = request.getParameter("uid");
//		String startDate = request.getParameter("startDate");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		int fstatus = 0;
		if(request.getParameter("fstatus") != null){
			fstatus = Integer.parseInt(request.getParameter("fstatus"));
		}
		StringBuffer filter = new StringBuffer();

		if(fstatus == 0){
			filter.append("where fstatus in (4,10,11) \n");
		}else{
			filter.append("where fstatus = "+fstatus+" \n");
		}

		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (floginName like '%" + keyWord + "%' or \n");
				filter.append("fnickName like '%" + keyWord + "%'  or \n");
				filter.append("frealName like '%" + keyWord + "%'  or \n");
				filter.append("ftelephone like '%" + keyWord + "%'  or \n");
				filter.append("femail like '%" + keyWord + "%'  or \n");
				filter.append("fidentityNo like '%" + keyWord + "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}


		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}

		List<Fuser> list = this.userService.list(
				(currentPage - 1) * numPerPage, numPerPage, filter + "", true);

		Fauditprocess fauditprocess = this.auditProcessService.findByftype(AuditProcessEnum.GOUP);
		int isneedPwd = fauditprocess == null?0:fauditprocess.getfIsneedPwd();
		//modelAndView.addObject("startDate", startDate);
		modelAndView.addObject("userList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "listUser");
		modelAndView.addObject("isneedPwd",isneedPwd);
		modelAndView.addObject("status",fstatus);
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fuser", filter + ""));
		return modelAndView;
	}


    /**
     *  用户升级认证
     * @return
     * @throws Exception
     */
    @RequestMapping("/ssadmin/userGoUpValidate")
    @SysLog(code = ModuleConstont.USER_OPERATION, method = "用户升级认证审核")
    public ModelAndView userGoUp(@RequestParam Integer uid) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ssadmin/comm/ajaxDone");
        // modelAndView.setViewName("redirect:/pages/ssadmin/comm/ajaxDone.jsp")
        Fuser user = this.userService.findById(uid);

//        if (user.getFstatus() != UserStatusEnum.FREEZE_VALIDATE) {
//            modelAndView.addObject("statusCode", 300);
//            modelAndView.addObject("message", "用户状态错误");
//            return modelAndView;
//        }
		Fadmin admin = (Fadmin) request.getSession().getAttribute("login_admin");
		Fauditprocess fauditprocess = this.auditProcessService.findByftype(AuditProcessEnum.GOUP);


		String loginpwd = request.getParameter("floginpassword");

		if(fauditprocess.getfIsneedPwd() == 1){
			if(StringUtils.isEmpty(loginpwd)){
				return getWebRequestError(modelAndView, "该操作需要输入确认密码");
			}
			if(!Utils.MD5(loginpwd,admin.getSalt()).equals(admin.getFpassword())){
				return getWebRequestError(modelAndView, "确认密码不正确");
			}
		}

		int result = getAuditProcess(user.getFstatus(),admin,AuditProcessEnum.GOUP,fauditprocess);
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
		// 修改状态
		user.setFstatus(result);
		user.setFlastUpdateTime(Utils.getTimestamp());

		if(fauditprocess.getfIsneedPwd() == 1){
			modelAndView.addObject("callbackType", "closeCurrent");
		}
		if(result != VirtualCapitalOperationInStatusEnum.SUCCESS){
			try {
				this.userService.updateObj(user);
			} catch (Exception e) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", e.getMessage());
				return modelAndView;
			}

			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", "审核成功");
			return modelAndView;
		}

		modelAndView.addObject("statusCode", 200);
        modelAndView.addObject("message", "审核成功");
        user.setFstatus(UserStatusEnum.NORMAL_VALUE);

       // this.realTimeData.addBlackUser(user.getFid()) ;

        this.userService.updateObj(user);
        return modelAndView;
    }

	/**
	 *  批量审核用户升级认证
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ssadmin/userGoUpValidateAll")
	@SysLog(code = ModuleConstont.USER_OPERATION, method = "用户升级认证审核")
	public ModelAndView userGoUp(@RequestParam int status) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");

		String ids = request.getParameter("ids");
		if(StringUtils.isEmpty(ids)){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "未选择需要审核的用户");
			return modelAndView;
		}
		String[] fids = ids.split(",");
		for(String uid:fids){
			Fuser user = this.userService.findById(Integer.parseInt(uid));

			if(user == null){
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "用户不存在");
				return modelAndView;
			}

			if (user.getFstatus() != UserStatusEnum.FREEZE_VALIDATE
				&& user.getFstatus() != UserStatusEnum.OneTimeAudit
				&& user.getFstatus() != UserStatusEnum.TwoTimeAudit	) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "用户状态错误");
				return modelAndView;
			}

			user.setFstatus(status);
			this.userService.updateObj(user);
		}
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "审核成功");

		return modelAndView;
	}





	/**
     * 冻结用户
     * @return
     * @throws Exception
     */
    @RequestMapping("/ssadmin/frozenUser")
    @SysLog(code = ModuleConstont.USER_OPERATION, method = "冻结用户")
    public ModelAndView userForbbin(@RequestParam Integer uid,
                                    @RequestParam String frozenReason) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ssadmin/comm/ajaxDone");
        // modelAndView.setViewName("redirect:/pages/ssadmin/comm/ajaxDone.jsp")
        // ;
        Fuser user = this.userService.findById(uid);

        if (user.getFstatus() != UserStatusEnum.NORMAL_VALUE ) {
            modelAndView.addObject("statusCode", 300);
            modelAndView.addObject("message", "会员状态不是正常，不能冻结");
            return modelAndView;
        }
        if(StringUtils.isEmpty(frozenReason)){
            modelAndView.addObject("statusCode", 300);
            modelAndView.addObject("message", "冻结原因不能为空");
            return modelAndView;
        }
        modelAndView.addObject("statusCode", 200);
        modelAndView.addObject("message", "会员冻结成功");
        user.setFstatus(UserStatusEnum.FREEZE_VALUE);
        modelAndView.addObject("callbackType","closeCurrent");
        user.setFrozenReason(frozenReason);

        this.userService.updateObj(user);
        return modelAndView;
    }



	/**
	 * 重设安全问题验证
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ssadmin/cancelsafe")
	@SysLog(code = ModuleConstont.USER_OPERATION, method = "重置用户安全验证问题")
	public ModelAndView cancelsafe() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fuser user = this.userService.findById(fid);

		try{
			sendMessage(user,"您设置的安全验证问题已被管理员重置,若有疑问请致电400-900-6615");
		}catch (Exception e){
			LOG.i(this.getClass(),"取消手机发送代码失败");
		}
		user.setFsafebind(false);  //修改fuser表的标识位
		//修改fquestionvalidate表，删除该用户相关的安全问题
		List<Fquestionvalidate> list = fquestionvalidateService.findByProperty("fuser.fid", user.getFid());
		if(list != null && list.size() > 0) {
			for(Fquestionvalidate vo :list) {
				fquestionvalidateService.delete(vo);
			}
		}
		this.userService.updateObj(user);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "重置安全验证问题成功");
		return modelAndView;
	}

}
