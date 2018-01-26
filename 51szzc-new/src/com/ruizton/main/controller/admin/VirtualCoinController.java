package com.ruizton.main.controller.admin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ruizton.main.Enum.CoinType;
import com.ruizton.main.Enum.ModuleConstont;
import com.ruizton.main.aspect.SysLog;
import com.ruizton.main.auto.okcoin.MD5Util;
import com.ruizton.main.model.*;
import com.ruizton.util.alipayUtil.Base64;
import com.ruizton.util.antshare.ANSUtils;
import com.ruizton.util.antshare.resp.BalanceResp;
import com.ruizton.util.zcash.ZECUtils;
import com.ruizton.util.zuohao.PasswordUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.VirtualCoinTypeStatusEnum;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.service.admin.AboutService;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.FeeService;
import com.ruizton.main.service.admin.PoolService;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.admin.WithdrawFeesService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.util.BTCUtils;
import com.ruizton.util.Constant;
import com.ruizton.util.ETHUtils;
import com.ruizton.util.OSSPostObject;
import com.ruizton.util.Utils;

@Controller
public class VirtualCoinController extends BaseController {

	
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/ssadmin/virtualCoinTypeList")
	public ModelAndView Index() throws Exception{
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/virtualCoinTypeList") ;
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
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and (fname like '%"+keyWord+"%' OR \n");
			filter.append("fShortName like '%"+keyWord+"%' OR \n");
			filter.append("fdescription like '%"+keyWord+"%' )\n");
			modelAndView.addObject("keywords", keyWord);
		}
		
		filter.append(" and ftype <>"+CoinTypeEnum.FB_CNY_VALUE+" \n");
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by fid \n");
		}
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("asc \n");
		}
		List<Fvirtualcointype> list = this.virtualCoinService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("virtualCoinTypeList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "virtualCoinTypeList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fvirtualcointype", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/ssadmin/walletAddressList")
	public ModelAndView walletAddressList() throws Exception{
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/walletAddressList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String keyWord = request.getParameter("keywords");
		
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and b.fname like '%"+keyWord+"%'\n");
			modelAndView.addObject("keywords", keyWord);
		}
		filter.append("and (a.fstatus=0 or a.fstatus is null)\n");
		
		List list = this.poolService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("walletAddressList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "walletAddressList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.poolService.list((currentPage-1)*numPerPage, numPerPage,filter+"",false).size());
		return modelAndView ;
	}
	
	
	@RequestMapping("ssadmin/goVirtualCoinTypeJSP")
	public ModelAndView goVirtualCoinTypeJSP() throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fvirtualcointype virtualCoinType = this.virtualCoinService.findById(fid);
			modelAndView.addObject("virtualCoinType", virtualCoinType);
			
			String filter = "where fvirtualcointype.fid="+fid+" order by flevel asc";
			List<Fwithdrawfees> allFees = this.withdrawFeesService.list(0, 0, filter, false);
			modelAndView.addObject("allFees", allFees);
		}
		
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/saveVirtualCoinType")
	//@SysLog(code = ModuleConstont.VIRTUAL_OPERATION, method = "新增虚拟币类型")
	public ModelAndView saveVirtualCoinType(
			@RequestParam(required=false) MultipartFile filedata,
			@RequestParam(required=false) String fdescription,
			@RequestParam(required=false) String fname,
			@RequestParam(required=false) String fShortName,
			@RequestParam(required=false) String faccess_key,
			@RequestParam(required=false) String fsecrt_key,
			@RequestParam(required=false) String fip,
			@RequestParam(required=false) String fport,
			@RequestParam(required=false) String fSymbol,
			@RequestParam(required=false) String FIsWithDraw,
			@RequestParam(required=false) String fweburl,
			@RequestParam(required=false) String fisauto,
			@RequestParam(required=false) String fisEth,
			@RequestParam(required=false,defaultValue="") String mainAddr,
			@RequestParam(required=false) String fisrecharge,
			@RequestParam(required=false) double fmaxqty,
			@RequestParam(required=false) double fminqty
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		Fvirtualcointype virtualCoinType = new Fvirtualcointype();
		String fpictureUrl = "";
		boolean isTrue = false;
		 if(filedata != null && !filedata.isEmpty()){
			InputStream inputStream = filedata.getInputStream() ;
			String fileRealName = filedata.getOriginalFilename() ;
			if(fileRealName != null && fileRealName.trim().length() >0){
				String[] nameSplit = fileRealName.split("\\.") ;
				String ext = nameSplit[nameSplit.length-1] ;
				if(ext!=null 
				 && !ext.trim().toLowerCase().endsWith("jpg")
				 && !ext.trim().toLowerCase().endsWith("png")){
					modelAndView.addObject("statusCode",300);
					modelAndView.addObject("message","非jpg、png文件格式");
					return modelAndView;
				}
				String realPath = request.getSession().getServletContext().getRealPath("/")+new Constant().AdminSYSDirectory;
				String fileName = Utils.getRandomImageName()+"."+ext;
				boolean flag = Utils.saveFile(realPath,fileName, inputStream) ;
				if(flag){
					fpictureUrl = OSSPostObject.URL+"/"+fileName ;
					isTrue = true;
				}
			}
		}
		if(isTrue){
			virtualCoinType.setFurl(fpictureUrl);
		}
		virtualCoinType.setFaddTime(Utils.getTimestamp());
		virtualCoinType.setFdescription(fdescription);
		virtualCoinType.setFname(fname);
		virtualCoinType.setfShortName(fShortName);
		virtualCoinType.setFstatus(VirtualCoinTypeStatusEnum.Abnormal);
		virtualCoinType.setFaccess_key(faccess_key);
		virtualCoinType.setFsecrt_key(fsecrt_key);
		virtualCoinType.setFip(fip);
		virtualCoinType.setFtype(CoinTypeEnum.COIN_VALUE);
		virtualCoinType.setFport(fport);
		virtualCoinType.setfSymbol(fSymbol);
		virtualCoinType.setFmaxqty(fmaxqty);
		virtualCoinType.setFminqty(fminqty);
		// 默认不起用密码拆分
		virtualCoinType.setIsSplitPassword(Boolean.FALSE);
		if(FIsWithDraw != null && FIsWithDraw.trim().length() >0){
			virtualCoinType.setFIsWithDraw(true);
		}else{
			virtualCoinType.setFIsWithDraw(false);
		}
		if(fisauto != null && fisauto.trim().length() >0){
			virtualCoinType.setFisauto(true);
		}else{
			virtualCoinType.setFisauto(false);
		}
		
		if(fisEth != null && fisEth.trim().length() >0){
			virtualCoinType.setFisEth(true);
			
			if("".equals(mainAddr.trim())){
				virtualCoinType.setMainAddr("") ;
			}else{
				boolean valid = ETHUtils.validateaddress(mainAddr.trim()) ;
				if(valid == false ){
					modelAndView.addObject("statusCode",500);
					modelAndView.addObject("message","以太坊汇总地址错误");
					return modelAndView;
				}
				virtualCoinType.setMainAddr(mainAddr) ;
			}
		}else{
			virtualCoinType.setFisEth(false);
			virtualCoinType.setMainAddr("") ;
		}
		
		if(fisrecharge != null && fisrecharge.trim().length() >0){
			virtualCoinType.setFisrecharge(true);
		}else{
			virtualCoinType.setFisrecharge(false);
		}
		this.virtualCoinService.saveObj(virtualCoinType);
		
		for(int i=1;i<=Constant.TYPES;i++){
			Fwithdrawfees fees = new Fwithdrawfees();
			fees.setFlevel(i);
			fees.setFvirtualcointype(virtualCoinType);
			fees.setFfee(0d);
			this.withdrawFeesService.saveObj(fees);
		}
		
		List<Fvirtualcointype> fvirtualcointypes= this.frontVirtualCoinService.findFvirtualCoinType(VirtualCoinTypeStatusEnum.Normal,CoinTypeEnum.COIN_VALUE) ;
		map.put("virtualCoinType", fvirtualcointypes) ;
		
		String xx = "where fstatus=1 and FIsWithDraw=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
		List<Fvirtualcointype> allWithdrawCoins= this.virtualCoinService.list(0, 0, xx, false);
		map.put("allWithdrawCoins", allWithdrawCoins) ;
		
		{
			String filter = "where fstatus=1 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
			List<Fvirtualcointype> allRechargeCoins= this.virtualCoinService.list(0, 0, filter, false);
			map.put("allRechargeCoins", allRechargeCoins) ;
		}

		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","新增成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}

	/**
	 * 修改币种
	 * @param filedata
	 * @param fdescription
	 * @param fname
	 * @param fShortName
	 * @param faccess_key
	 * @param fsecrt_key
	 * @param fip
	 * @param fport
	 * @param fSymbol
	 * @param FIsWithDraw
	 * @param fid
	 * @param fweburl
	 * @param fisauto
	 * @param fisEth
	 * @param mainAddr
	 * @param fisrecharge
     * @param fmaxqty
     * @param fminqty
     * @return
     * @throws Exception
     */
	@RequestMapping("ssadmin/updateVirtualCoinType")
	//@SysLog(code = ModuleConstont.VIRTUAL_OPERATION, method = "修改虚拟币类型")
	public ModelAndView updateVirtualCoinType(
			@RequestParam(required=false) MultipartFile filedata,
			@RequestParam(required=false) String fdescription,
			@RequestParam(required=false) String fname,
			@RequestParam(required=false) String fShortName,
			@RequestParam(required=false) String faccess_key,
			@RequestParam(required=false) String fsecrt_key,
			@RequestParam(required=false) String fip,
			@RequestParam(required=false) String fport,
			@RequestParam(required=false) String fSymbol,
			@RequestParam(required=false) String FIsWithDraw,
			@RequestParam(required=false) Integer fid,
			@RequestParam(required=false,defaultValue="") String fweburl,
			@RequestParam(required=false) String fisauto,
			@RequestParam(required=false,defaultValue="") String fisEth,
			@RequestParam(required=false,defaultValue="") String mainAddr,
			@RequestParam(required=false) String fisrecharge,
			@RequestParam(required=false) Double fmaxqty,
			@RequestParam(required=false) Double fminqty
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		Fvirtualcointype virtualCoinType = this.virtualCoinService.findById(fid);
		String fpictureUrl = "";
		boolean isTrue = false;
		 if(filedata != null && !filedata.isEmpty()){
			InputStream inputStream = filedata.getInputStream() ;
			String fileRealName = filedata.getOriginalFilename() ;
			if(fileRealName != null && fileRealName.trim().length() >0){
				String[] nameSplit = fileRealName.split("\\.") ;
				String ext = nameSplit[nameSplit.length-1] ;
				if(ext!=null
				 && !ext.trim().toLowerCase().endsWith("jpg")
				 && !ext.trim().toLowerCase().endsWith("png")){
					modelAndView.addObject("statusCode",300);
					modelAndView.addObject("message","非jpg、png文件格式");
					return modelAndView;
				}
				String realPath = request.getSession().getServletContext().getRealPath("/")+new Constant().AdminSYSDirectory;
				String fileName = Utils.getRandomImageName()+"."+ext;
				boolean flag = Utils.saveFile(realPath,fileName, inputStream) ;
				if(flag){
//					fpictureUrl = "/"+new Constant().AdminSYSDirectory+"/"+fileName ;
					fpictureUrl = OSSPostObject.URL+"/"+fileName ;
					isTrue = true;
				}
			}
		}
		if(isTrue){
			virtualCoinType.setFurl(fpictureUrl);
		}
		virtualCoinType.setFweburl(fweburl);
		virtualCoinType.setFaddTime(Utils.getTimestamp());
		virtualCoinType.setFdescription(fdescription);
		virtualCoinType.setFname(fname);
		virtualCoinType.setfShortName(fShortName);
		virtualCoinType.setFaccess_key(faccess_key);
		virtualCoinType.setFsecrt_key(fsecrt_key);
		virtualCoinType.setFip(fip);
		virtualCoinType.setFport(fport);
		virtualCoinType.setfSymbol(fSymbol);
		virtualCoinType.setFmaxqty(fmaxqty);
		virtualCoinType.setFminqty(fminqty);
		if(FIsWithDraw != null && FIsWithDraw.trim().length() >0){
			virtualCoinType.setFIsWithDraw(true);
		}else{
			virtualCoinType.setFIsWithDraw(false);
		}
		if(fisauto != null && fisauto.trim().length() >0){
			virtualCoinType.setFisauto(true);
		}else{
			virtualCoinType.setFisauto(false);
		}

		if(fisEth != null && fisEth.trim().length() >0){
			virtualCoinType.setFisEth(true);

			if("".equals(mainAddr.trim())){
				virtualCoinType.setMainAddr("") ;
			}else{
				// 校验主帐号地址是否被用户使用
				List<Fvirtualaddress> list = this.frontVirtualCoinService.findFvirtualaddress(virtualCoinType,mainAddr);
				if (list != null && list.size() > 0){
					// 主地址被用户已经使用
					modelAndView.addObject("statusCode",500);
					modelAndView.addObject("message","以太坊汇总地址已被用户使用,请更换总地址");
					return modelAndView;
				}
				boolean valid = ETHUtils.validateaddress(mainAddr.trim()) ;
				if(valid == false ){
					modelAndView.addObject("statusCode",500);
					modelAndView.addObject("message","以太坊汇总地址错误");
					return modelAndView;
				}
				virtualCoinType.setMainAddr(mainAddr) ;
			}
		}else{
			virtualCoinType.setFisEth(false);
			virtualCoinType.setMainAddr("") ;
		}

		if(fisrecharge != null && fisrecharge.trim().length() >0){
			virtualCoinType.setFisrecharge(true);
		}else{
			virtualCoinType.setFisrecharge(false);
		}

		if(virtualCoinType.getFtype() ==CoinTypeEnum.FB_CNY_VALUE){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","人民币不允许修改");
			return modelAndView;
		}
		this.virtualCoinService.updateObj(virtualCoinType);

		List<Fvirtualcointype> fvirtualcointypes= this.frontVirtualCoinService.findFvirtualCoinType(VirtualCoinTypeStatusEnum.Normal,CoinTypeEnum.COIN_VALUE) ;
		map.put("virtualCoinType", fvirtualcointypes) ;

		String xx = "where fstatus=1 and FIsWithDraw=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
		List<Fvirtualcointype> allWithdrawCoins= this.virtualCoinService.list(0, 0, xx, false);
		map.put("allWithdrawCoins", allWithdrawCoins) ;

		{
			String filter = "where fstatus=1 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
			List<Fvirtualcointype> allRechargeCoins= this.virtualCoinService.list(0, 0, filter, false);
			map.put("allRechargeCoins", allRechargeCoins) ;
		}
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","更新成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/goWallet")
	@SysLog(code = ModuleConstont.VIRTUAL_OPERATION, method = "启用虚拟币类型")
	public ModelAndView goWallet() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		String password = request.getParameter("passWord");
		Fvirtualcointype virtualcointype = this.virtualCoinService.findById(fid);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		boolean flag = false;
		virtualcointype.setFstatus(VirtualCoinTypeStatusEnum.Normal);
		String msg = "";
		try {
			flag = this.virtualCoinService.updateCoinType(virtualcointype,password);
			flag = true;
		} catch (Exception e) {
			 msg =e.getMessage();
		}
		
		List<Fvirtualcointype> fvirtualcointypes= this.frontVirtualCoinService.findFvirtualCoinType(VirtualCoinTypeStatusEnum.Normal,CoinTypeEnum.COIN_VALUE) ;
		map.put("virtualCoinType", fvirtualcointypes) ;
		
		String xx = "where fstatus=1 and FIsWithDraw=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
		List<Fvirtualcointype> allWithdrawCoins= this.virtualCoinService.list(0, 0, xx, false);
		map.put("allWithdrawCoins", allWithdrawCoins) ;
		
		String filter = "where fstatus=1 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
		List<Fvirtualcointype> allRechargeCoins= this.virtualCoinService.list(0, 0, filter, false);
		map.put("allRechargeCoins", allRechargeCoins) ;
		
		if(!flag){
			modelAndView.addObject("message",msg);
			modelAndView.addObject("statusCode",300);
		}else{
			modelAndView.addObject("message","启用成功");
			modelAndView.addObject("statusCode",200);
			modelAndView.addObject("callbackType","closeCurrent");
		}
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/deleteVirtualCoinType")
	@SysLog(code = ModuleConstont.VIRTUAL_OPERATION, method = "禁用虚拟币类型")
	public ModelAndView deleteVirtualCoinType() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fvirtualcointype virtualcointype = this.virtualCoinService.findById(fid);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		
		if(virtualcointype.getFtype() ==CoinTypeEnum.FB_CNY_VALUE){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","人民币不允许禁用");
			return modelAndView;
		}
		
		virtualcointype.setFstatus(VirtualCoinTypeStatusEnum.Abnormal);
		this.virtualCoinService.updateObj(virtualcointype);
		
		List<Fvirtualcointype> fvirtualcointypes= this.frontVirtualCoinService.findFvirtualCoinType(VirtualCoinTypeStatusEnum.Normal,CoinTypeEnum.COIN_VALUE) ;
		map.put("virtualCoinType", fvirtualcointypes) ;
		
		String xx = "where fstatus=1 and FIsWithDraw=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
		List<Fvirtualcointype> allWithdrawCoins= this.virtualCoinService.list(0, 0, xx, false);
		map.put("allWithdrawCoins", allWithdrawCoins) ;
		
		String filter = "where fstatus=1 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
		List<Fvirtualcointype> allRechargeCoins= this.virtualCoinService.list(0, 0, filter, false);
		map.put("allRechargeCoins", allRechargeCoins) ;
		
		modelAndView.addObject("message","禁用成功");
		modelAndView.addObject("statusCode",200);
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/testWallet")
	public ModelAndView testWallet() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fvirtualcointype type = this.virtualCoinService.findById(fid);
		BTCMessage btcMessage = new BTCMessage() ;
		btcMessage.setACCESS_KEY(type.getFaccess_key()) ;
		btcMessage.setIP(type.getFip()) ;
		btcMessage.setPORT(type.getFport()) ;
		btcMessage.setSECRET_KEY(type.getFsecrt_key()) ;
		btcMessage.setCOIN_TYPE(type.getfShortName().toUpperCase());
		if(btcMessage.getACCESS_KEY()==null
				||btcMessage.getIP()==null
				||btcMessage.getPORT()==null
				||btcMessage.getSECRET_KEY()==null){
			modelAndView.addObject("message","钱包连接失败，请检查配置信息");
			modelAndView.addObject("statusCode",300);
		}
		try {
			if(type.isFisEth() == false ){
				double balance;
				if (StringUtils.equals(type.getfShortName(),CoinType.ANS)){
					ANSUtils ansUtils = new ANSUtils(btcMessage);
					BalanceResp resp = ansUtils.ans_getbalance(ANSUtils.ANS_ASSET_ID);
					balance = resp.getResult().getBalance().doubleValue();
				} else {
					BTCUtils btcUtils = new BTCUtils(btcMessage) ;
					balance = btcUtils.getbalanceValue();
				}
				modelAndView.addObject("message","测试成功，钱包余额:"+Utils.double2Str(balance));
				modelAndView.addObject("statusCode",200);
			}else{
				
				ETHUtils ethUtils = new ETHUtils(btcMessage) ;
				double balance = ethUtils.getbalanceValue();
				modelAndView.addObject("message","测试成功，钱包余额:"+Utils.double2Str(balance));
				modelAndView.addObject("statusCode",200);
			}
		} catch (Exception e) {
			modelAndView.addObject("message","钱包连接失败，请检查配置信息");
			modelAndView.addObject("statusCode",300);
		}
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/updateCoinFee")
	@SysLog(code = ModuleConstont.VIRTUAL_OPERATION, method = "修改提现手续费")
	public ModelAndView updateCoinFee() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		int fid = Integer.parseInt(request.getParameter("fid"));
		List<Fwithdrawfees> all = this.withdrawFeesService.findByProperty("fvirtualcointype.fid", fid);
		
		//add by hank
		for (Fwithdrawfees ffees : all) {
			String feeKey = "fee"+ffees.getFid();
			double fee = Double.valueOf(request.getParameter(feeKey));
			
			if(fee>=1 || fee<0){
				modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","手续费率只能是小于1的小数！");
				return modelAndView;
			}
		}
		
		for (Fwithdrawfees ffees : all) {
			String feeKey = "fee"+ffees.getFid();
			double fee = Double.valueOf(request.getParameter(feeKey));
			ffees.setFfee(fee);
			this.withdrawFeesService.updateObj(ffees);
		}
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","更新成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/createWalletAddress")
	@SysLog(code = ModuleConstont.VIRTUAL_OPERATION, method = "生成钱包地址")
	public ModelAndView createWalletAddress() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		type = this.virtualCoinService.findById(fid);
		if(!type.isFIsWithDraw() && !type.isFisrecharge()){
			modelAndView.addObject("message","不允许充值和提现的虚拟币类型不能生成虚拟地址!");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}
		btcMessage = new BTCMessage() ;
		btcMessage.setACCESS_KEY(type.getFaccess_key()) ;
		btcMessage.setIP(type.getFip()) ;
		btcMessage.setPORT(type.getFport()) ;
		btcMessage.setSECRET_KEY(type.getFsecrt_key()) ;
		btcMessage.setPASSWORD(request.getParameter("passWord"));
		btcMessage.setCOIN_TYPE(type.getfShortName().toUpperCase());
		if(btcMessage.getACCESS_KEY()==null
				||btcMessage.getIP()==null
				||btcMessage.getPORT()==null
				||btcMessage.getSECRET_KEY()==null){
			modelAndView.addObject("message","钱包连接失败，请检查配置信息");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}
		
		try {
			Fvirtualcointype fvirtualcointype = this.virtualCoinService.findById(fid) ;
			if(fvirtualcointype.isFisEth()){
				ETHUtils ethUtils = new ETHUtils(btcMessage) ;
				if(fvirtualcointype.getStartBlockId()==0){
					fvirtualcointype.setStartBlockId(ethUtils.eth_blockNumberValue()) ;
					this.virtualCoinService.updateObj(fvirtualcointype) ;
				}
			}
		} catch (Exception e1) {
			modelAndView.addObject("message","钱包异常!");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}
		
		try {
			new Thread(new Work()).start() ;
		} catch (Exception e) {
			modelAndView.addObject("message","钱包异常!");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}
		
		
		modelAndView.addObject("message","后台执行中!");
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("rel", "createWalletAddress");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	private BTCMessage btcMessage;
	private Fvirtualcointype type;
	class Work implements Runnable{
		public void run() {
			createAddress(btcMessage, type);
		}
	}
	
	private void createAddress(BTCMessage btcMessage,Fvirtualcointype type) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
		
		if(type.isFisEth() == false ){

			BTCUtils btcUtils = null;
			try {
				btcUtils = new BTCUtils(btcMessage);
				for(int i=0;i<10000;i++){
					String address = btcUtils.getNewaddressValueForAdmin(sdf.format(Utils.getTimestamp()));
					if(address == null || address.trim().length() ==0){
						break;
					}
					Fpool poolInfo = new Fpool();
					poolInfo.setFaddress(address);
					poolInfo.setFvirtualcointype(type);
					poolService.saveObj(poolInfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					btcUtils.walletlock();
				} catch (Exception e) {}
			}
		}else{
			
			ETHUtils ethUtils = null;
			try {
				// 主帐号
				String mainAdd = type.getMainAddr();
				ethUtils = new ETHUtils(btcMessage) ;
				for(int i=0;i<10000;i++){
					String address = ethUtils.getNewaddressValue() ;
					if(address == null || address.trim().length() ==0){
						break;
					}
					Fpool poolInfo = new Fpool();
					if (StringUtils.isNotEmpty(mainAdd) && mainAdd.toUpperCase().equals(address.toLowerCase())){
						// 主账户1.已使用
						poolInfo.setFstatus(1);
					}
					poolInfo.setFaddress(address);
					poolInfo.setFvirtualcointype(type);
					poolService.saveObj(poolInfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	
	@RequestMapping("ssadmin/etcMainAddr")
	@SysLog(code = ModuleConstont.VIRTUAL_OPERATION, method = "以太坊金额汇总到主地址")
	public ModelAndView etcMainAddr(
			@RequestParam(required=true )Integer uid,
			@RequestParam(required=true )final String password
			) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		final Fvirtualcointype fvirtualcointype = this.virtualCoinService.findById(uid) ;
		if(fvirtualcointype==null ||fvirtualcointype.isFisEth()==false ){
			modelAndView.addObject("message","非以太坊钱包不可汇总");
			modelAndView.addObject("statusCode",500);
			modelAndView.addObject("rel", "etcMainAddr");
			return modelAndView;
		}

		if(fvirtualcointype.getMainAddr()==null||"".equals(fvirtualcointype.getMainAddr().trim())){
			modelAndView.addObject("message","未设置主钱包地址");
			modelAndView.addObject("statusCode",500);
			modelAndView.addObject("rel", "etcMainAddr");
			return modelAndView;
		}
		
		BTCMessage msg = new BTCMessage();
		msg.setACCESS_KEY(fvirtualcointype .getFaccess_key());
		msg.setIP(fvirtualcointype.getFip());
		msg.setPORT(fvirtualcointype .getFport());
		msg.setSECRET_KEY(fvirtualcointype .getFsecrt_key());
		msg.setPASSWORD(password) ;
		final ETHUtils ethUtils = new ETHUtils(msg) ;
		boolean flag = false ;
		try {
			flag = ethUtils.walletpassphrase(fvirtualcointype.getMainAddr().trim());
			ethUtils.lockAccount(fvirtualcointype.getMainAddr().trim()) ;
		} catch (Exception e1) {
			
		}
		if(flag == false ){
			modelAndView.addObject("message","钱包链接错误，或密码错误");
			modelAndView.addObject("statusCode",500);
			modelAndView.addObject("rel", "etcMainAddr");
			return modelAndView;
		}
		
		{
			new Thread(new Runnable() {
				public void run() {
					try {
						
						ethUtils.sendMain(fvirtualcointype.getMainAddr().trim()) ;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start() ;
			modelAndView.addObject("message","后台执行中,执行时间由钱包中地址数量决定，短时间内请不要重复执行该功能!");
			modelAndView.addObject("statusCode",200);
			modelAndView.addObject("rel", "etcMainAddr");
			modelAndView.addObject("callbackType","closeCurrent");
			return modelAndView;
		}
	}


	@RequestMapping("ssadmin/updatePassword")
	@SysLog(code = ModuleConstont.VIRTUAL_OPERATION, method = "更新钱包密码")
	public ModelAndView updateAutoOrder(@RequestParam(required=true )Integer uid) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		String password = (String) request.getParameter("password");
		if (StringUtils.isEmpty(password)){
			modelAndView.addObject("message","钱包密码不能为空");
			modelAndView.addObject("statusCode",500);
			return modelAndView;
		}
		Fvirtualcointype fvirtualcointype = virtualCoinService.findById(uid);
        byte[] salt = PasswordUtils.initSalt();
		fvirtualcointype.setSalt(PasswordUtils.encryptBASE64(salt).trim());
        String ascii = PasswordUtils.stringToAscii(fvirtualcointype.getSalt());
        byte[] result = PasswordUtils.encrypt(password.getBytes(),ascii,salt);
		fvirtualcointype.setPassword(PasswordUtils.encryptBASE64(result).trim());
		virtualCoinService.updateObj(fvirtualcointype);
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","更新成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}

    @RequestMapping("ssadmin/splitPassword")
    public ModelAndView splitPassword() throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("statusCode", 300);
        modelAndView.setViewName("ssadmin/comm/ajaxDone");
        int fid = Integer.parseInt(request.getParameter("uid"));
        Fvirtualcointype fvirtualcointype = this.virtualCoinService.findById(fid);
        String status = request.getParameter("status");
        if ("1".equals(status)){
            fvirtualcointype.setIsSplitPassword(Boolean.TRUE);
            modelAndView.addObject("message","启动密码拆分成功");
            modelAndView.addObject("statusCode",200);
        } else {
            fvirtualcointype.setIsSplitPassword(Boolean.FALSE);
            modelAndView.addObject("statusCode",200);
            modelAndView.addObject("message","停止密码拆分成功");
        }
        virtualCoinService.updateObj(fvirtualcointype);
        return modelAndView;
    }

	/**
	 * 导入钱包地址
	 * @param filedata
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("ssadmin/importWalletAddress")
	public ModelAndView importWalletAddress(
			@RequestParam(required=false) MultipartFile filedata,
			@RequestParam(required=false) Integer fid
	) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		Fvirtualcointype fvirtualcointype = virtualCoinService.findById(fid);
		String shortName = fvirtualcointype.getfShortName();
		if (!StringUtils.equals(shortName, CoinType.ANS)){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","导入地址只支持小蚁股");
			return modelAndView;
		}
		List<String> aList = new ArrayList<>();
		if(filedata != null && !filedata.isEmpty()){
			InputStream inputStream = filedata.getInputStream() ;
			String fileRealName = filedata.getOriginalFilename() ;
			if(fileRealName != null && fileRealName.trim().length() >0){
				String[] nameSplit = fileRealName.split("\\.") ;
				String ext = nameSplit[nameSplit.length-1] ;
				if(ext!=null
						&& !ext.trim().toLowerCase().endsWith("txt")){
					modelAndView.addObject("statusCode",300);
					modelAndView.addObject("message","非txt文件格式");
					return modelAndView;
				}
				String lineTxt;
				InputStreamReader reader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(reader);
				while((lineTxt = bufferedReader.readLine()) != null){
					aList.add(lineTxt);
				}
			}
		}
		int num = 0;
		if (aList.size() > 0){
			for (String address : aList){
				Fpool fpool = new Fpool();
				fpool.setFaddress(address);
				fpool.setFvirtualcointype(fvirtualcointype);
				poolService.saveObj(fpool);
				num++;
			}
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message","导入地址" + num + "个");
			modelAndView.addObject("callbackType","closeCurrent");
		} else {
			modelAndView.addObject("statusCode",200);
			modelAndView.addObject("message","文件中没有读取到可用的地址");
		}
		return modelAndView;
	}



	@RequestMapping("ssadmin/updateAutoWithPwd")
	@SysLog(code = ModuleConstont.VIRTUAL_OPERATION, method = "更新自动提币密码")
	public ModelAndView updateAutoWithPwd(@RequestParam(required=true )Integer uid) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		String password = (String) request.getParameter("password");
		if (StringUtils.isEmpty(password)){
			modelAndView.addObject("message","自动提币密码不能为空");
			modelAndView.addObject("statusCode",500);
			return modelAndView;
		}
		Fvirtualcointype fvirtualcointype = virtualCoinService.findById(uid);
		String decode_password = Base64.encode(password.getBytes());  //加密后的密码
		fvirtualcointype.setAuto_password(decode_password);
		virtualCoinService.updateObj(fvirtualcointype);
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","更新成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}


}
