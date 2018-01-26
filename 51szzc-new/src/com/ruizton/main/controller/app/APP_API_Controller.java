package com.ruizton.main.controller.app;

import com.google.gson.Gson;
import com.ruizton.main.Enum.*;
import com.ruizton.main.auto.OneDayData;
import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.cache.biz.FquoteCacheManager;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.comm.MessageValidate;
import com.ruizton.main.comm.ValidateMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.*;
import com.ruizton.main.model.OtherPlatform.Fquotes;
import com.ruizton.main.model.integral.Fusergrade;
import com.ruizton.main.service.admin.*;
import com.ruizton.main.service.front.*;
import com.ruizton.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Scope("prototype")
public class APP_API_Controller extends BaseController {

	@Autowired
	protected FtradeMappingService ftradeMappingService ;
	@Autowired
	protected FrontUserService frontUserService ;
	@Autowired
	protected RealTimeData realTimeData ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private FrontTradeService frontTradeService ;
	@Autowired
	protected OneDayData oneDayData ;
	@Autowired
	private AdminService adminService;
	@Autowired
	protected ConstantMap constantMap;
	@Autowired
	private UserService userService;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private IntrolinfoService introlinfoService;
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private FrontAccountService frontAccountService ;
	@Autowired
	private FrontValidateService frontValidateService ;
	@Autowired
	private CapitaloperationService capitaloperationService ;
	@Autowired
	private ValidateMap validateMap;
	@Autowired
	private SystemArgsService systemArgsService ;
	@Autowired
	private WithdrawFeesService withdrawFeesService ;
	@Autowired
	private FrontBankInfoService frontBankInfoService ;
	@Autowired
	private FquotesService fquotesService;

	private static final Logger LOGGER = LoggerFactory.getLogger(APP_API_Controller.class);
	private static final String CLASS_NAME = APP_API_Controller.class.getSimpleName();
	public final static String Result = "result" ;
	public final static String ErrorCode = "code" ;
	public final static String Value = "value" ;
	public final static String LoginToken = "loginToken" ;
	public final static String CurrentPage = "currentPage" ;
	public final static String TotalPage = "totalPage" ;
	public final static String TotalCount = "totalCount";
	public final static String LastUpdateTime = "lastUpdateTime" ;
	public final static String Fid = "Fid" ;
	public final static String List = "list";
	public final static String PageSize = "pageSize";


	//格式化，保留4未小数
	protected DecimalFormat decimalFormat4 = new DecimalFormat("####0.0000");//格式化设置

	//格式化，保留2未小数
	protected DecimalFormat decimalFormat2 = new DecimalFormat("####0.00");


	protected String curLoginToken = null ;
	int maxResult = Constant.AppRecordPerPage ;

	public void setLoginToken(int userId){
		this.curLoginToken = realTimeData.pushTestAppSession(userId);
	}
	
	@ResponseBody
	@RequestMapping(value="/appApi",produces=JsonEncode)
	public String appApi(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")String action
			) throws Exception {
		request.setCharacterEncoding("UTF-8") ;

		curLoginToken = request.getParameter(LoginToken) ;

		if(StringUtils.isNotBlank(curLoginToken) && curLoginToken.length() == 2){
//			curLoginToken =realTimeData.pushTestAppSession(Integer.parseInt(curLoginToken));
		}

		Integer actionInteger = ApiConstant.getInteger(action) ;
		
		if(actionInteger / 100==2){
			boolean isLogin = this.realTimeData.isAppLogin(this.curLoginToken, false) ;
			if(isLogin==false){
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(Result, false) ;
				jsonObject.accumulate(ErrorCode , -10001) ;
				jsonObject.accumulate(Value, "未登录") ;
				return jsonObject.toString() ;
			}
		}
		
		String ret = "" ;
		switch (actionInteger) {
		case 0 :
			{
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(Result, false) ;
				jsonObject.accumulate(ErrorCode , -10000) ;
				jsonObject.accumulate(Value, "API不存在") ;
				ret = jsonObject.toString() ;
			}
			break ;
			
		default:
			try {
				Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class) ;
				ret = (String)method.invoke(this, request) ;
			} catch (Exception e) {
				ret = ApiConstant.getUnknownError(e) ;
			}
			break ;	
		}
		
		if(Constant.isRelease == false )
		{
//			System.out.println(ret);
		}
		return ret ;
	}
	
	public String TradePassword(HttpServletRequest request) throws Exception {
		try{
			JSONObject jsonObject = new JSONObject() ;
			
			String passwd = request.getParameter("passwd").trim() ;
			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;
			if(fuser.getFtradePassword()==null){
				jsonObject.accumulate(Result, true) ;
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "未设置交易密码") ;
				return jsonObject.toString() ;
			}else if(fuser.getFtradePassword().endsWith(Utils.MD5(passwd, fuser.getSalt()))){
				jsonObject.accumulate(Result, true) ;
				jsonObject.accumulate(ErrorCode , 0) ;
				jsonObject.accumulate(Value, "交易密码正确") ;
				return jsonObject.toString() ;
			}else{
				jsonObject.accumulate(Result, true) ;
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "未设置交易密码") ;
				return jsonObject.toString() ;
			}
			
		}catch(Exception e){
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	public String BtcTradeSubmit(HttpServletRequest request) throws Exception {
		try{
			int type = Integer.parseInt(request.getParameter("type")) ;
			if(type==0){
				return this.buyBtcSubmit(request) ;
			}else if(type==1){
				return this.sellBtcSubmit(request) ;
			}else{
				throw new Exception() ;
			}
		}catch(Exception e){
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	public String GetEntrustInfo(HttpServletRequest request) throws Exception {
		try{
			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;
			int type = Integer.parseInt(request.getParameter("type")) ;
			int symbol = Integer.parseInt(request.getParameter("symbol")) ;
			int currentPage = 1 ;
			int totalPage = 0 ;
			try{
				currentPage = Integer.parseInt(request.getParameter("currentPage")) ;
				currentPage = currentPage<1?1:currentPage ;
			}catch(Exception e){}
			
			StringBuffer fstatus = new StringBuffer() ;
			if(type==0){
				//未成交
				fstatus.append(" (fstatus="+ EntrustStatusEnum.Going+" or fstatus="+ EntrustStatusEnum.PartDeal+") ") ;
			}else{
				//成交
				fstatus.append(" (fstatus="+ EntrustStatusEnum.AllDeal+" or fstatus="+ EntrustStatusEnum.Cancel+") ") ;
			}
			String filter = " where ftrademapping.fid="+symbol+" and "+fstatus.toString() +" and fuser.fid="+fuser.getFid()+" order by fid desc ";
			
			List<Fentrust> list = this.frontTradeService.findFentrustByParam((currentPage-1)*maxResult, maxResult, filter, true) ;
			int total = this.frontTradeService.findFentrustByParamCount(filter) ;
			totalPage = total/maxResult + (total%maxResult==0?0:1) ;
			
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			jsonObject.accumulate("totalPage", totalPage) ;
			jsonObject.accumulate("currentPage", currentPage) ;
			jsonObject.accumulate("lastUpdateTime", Utils.dateFormat(Utils.getTimestamp())) ;
			
			JSONArray jsonArray = new JSONArray() ;
			for (int i = 0; i < list.size(); i++) {
				JSONObject item = new JSONObject() ;
				Fentrust fentrust = list.get(i) ;
				String title = null ;
				if(fentrust.isFisLimit()==true){
					if(fentrust.getFentrustType()== EntrustTypeEnum.BUY){
						title = "市价买入" ;
					}else{
						title = "市价卖出" ;
					}
				}else{
					if(fentrust.getFentrustType()== EntrustTypeEnum.BUY){
						title = "限价买入" ;
					}else{
						title = "限价卖出" ;
					}
				}
				
				item.accumulate("fid", fentrust.getFid()) ;
				item.accumulate("type", fentrust.getFentrustType()) ;
				item.accumulate("title", title) ;
				
				item.accumulate("fstatus", fentrust.getFstatus()) ;
				item.accumulate("fstatus_s", fentrust.getFstatus_s()) ;
				
				item.accumulate("flastUpdatTime", Utils.dateFormat(fentrust.getFlastUpdatTime())) ;
				
				item.accumulate("fprice", new BigDecimal(fentrust.getFprize()).setScale(2, BigDecimal.ROUND_DOWN).toString()) ;
				item.accumulate("fcount", new BigDecimal(fentrust.getFcount()).setScale(4, BigDecimal.ROUND_DOWN).toString()) ;
				item.accumulate("fsuccessCount", new BigDecimal(fentrust.getFcount()-fentrust.getFleftCount()).setScale(4, BigDecimal.ROUND_DOWN).toString()) ;
				if(fentrust.getFcount()-fentrust.getFleftCount()<0.0001D){
					item.accumulate("fsuccessprice", 0D) ;
				}else{
					item.accumulate("fsuccessprice", new BigDecimal(fentrust.getFsuccessAmount()/(fentrust.getFcount()-fentrust.getFleftCount())).setScale(4, BigDecimal.ROUND_DOWN).toString()) ;
				}
				
				jsonArray.add(item) ;
			}
			jsonObject.accumulate("list", jsonArray) ;
			
			return jsonObject.toString() ;
		}catch(Exception e){
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	public String CancelEntrust(HttpServletRequest request) throws Exception {
		try {
			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;
			int id = Integer.parseInt(request.getParameter("id")) ;
			
			Fentrust fentrust = this.frontTradeService.findFentrustById(id) ;
			
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			if(fentrust!=null
					&&(fentrust.getFstatus()== EntrustStatusEnum.Going || fentrust.getFstatus()== EntrustStatusEnum.PartDeal )
					&&fentrust.getFuser().getFid() == fuser.getFid() ){
				boolean flag = false ;
				try {
					this.frontTradeService.updateCancelFentrust(fentrust, fuser) ;
					flag = true ;
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(flag==true){
					if(fentrust.getFentrustType()== EntrustTypeEnum.BUY){
						
						//买
						if(fentrust.isFisLimit()){
							this.realTimeData.removeEntrustLimitBuyMap(fentrust.getFtrademapping().getFid(), fentrust) ;
						}else{
							this.realTimeData.removeEntrustBuyMap(fentrust.getFtrademapping().getFid(), fentrust) ;
						}
					}else{
						
						//卖
						if(fentrust.isFisLimit()){
							this.realTimeData.removeEntrustLimitSellMap(fentrust.getFtrademapping().getFid(), fentrust) ;
						}else{
							this.realTimeData.removeEntrustSellMap(fentrust.getFtrademapping().getFid(), fentrust) ;
						}
						
					}
					
					jsonObject.accumulate(ErrorCode , 0) ;//成功
					jsonObject.accumulate(Value, "取消成功") ;//成功
					return jsonObject.toString() ;
				}else{
					jsonObject.accumulate(ErrorCode , -1) ;//失敗
					jsonObject.accumulate(Value, "取消失败") ;//成功
					return jsonObject.toString() ;
				}
			}else{
				jsonObject.accumulate(ErrorCode , -1) ;//失敗
				jsonObject.accumulate(Value, "取消失败") ;//成功
				return jsonObject.toString() ;
			}
			
		} catch (Exception e) {
			return ApiConstant.getUnknownError(e) ;
		}
	}

	/**
	 * 人民币提现
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String GetMarketData(HttpServletRequest request) throws Exception {
		try{
			
			String filter = "where fstatus="+ TrademappingStatusEnum.ACTIVE+" order by fid asc";
			List<Ftrademapping> list = this.utilsService.list1(0, 0, filter,false,Ftrademapping.class);
			
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			jsonObject.accumulate("lastUpdateTime", Utils.dateFormat(Utils.getTimestamp())) ;
			
			JSONArray jsonArray = new JSONArray() ;
			for (int i = 0; i < list.size(); i++) {
				Ftrademapping ftrademapping = list.get(i) ;
				int id = ftrademapping.getFid() ;
				int count1 = ftrademapping.getFcount1();//小数点
				int count2 = ftrademapping.getFcount2();//小数点
				
				JSONObject item = new JSONObject() ;
				item.accumulate("id", id) ;
				item.accumulate("cnyName", ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFname()) ;
				if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype()== CoinTypeEnum.FB_CNY_VALUE){
					item.accumulate("vir_id1", 0) ;
					item.accumulate("vir_id1_isWithDraw", true) ;
				}else{
					item.accumulate("vir_id1", ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid()) ;
					item.accumulate("vir_id1_isWithDraw", ftrademapping.getFvirtualcointypeByFvirtualcointype1().isFIsWithDraw()) ;
				}
				item.accumulate("vir_id2", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid()) ;
				item.accumulate("vir_id2_isWithDraw", ftrademapping.getFvirtualcointypeByFvirtualcointype2().isFIsWithDraw()) ;
				item.accumulate("coinName", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getfShortName()) ;
				item.accumulate("cnName", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname()) ;
				item.accumulate("currency", ftrademapping.getFvirtualcointypeByFvirtualcointype1().getfShortName()) ;
				item.accumulate("currencySymbol", ftrademapping.getFvirtualcointypeByFvirtualcointype1().getfSymbol()) ;
				item.accumulate("title", this.constantMap.get("webName"));
				item.accumulate("hasKline", true) ;
				item.accumulate("count1", count1) ;
				item.accumulate("count2", count2) ;
				
				//成交量，买一，卖一，最高，最低，现价
				item.accumulate("LatestDealPrice", Utils.getDoubleS(this.realTimeData.getLatestDealPrize(id), 2)) ;
				item.accumulate("SellOnePrice", Utils.getDoubleS(this.realTimeData.getLowestSellPrize(id), 2)) ;
				item.accumulate("BuyOnePrice", Utils.getDoubleS(this.realTimeData.getHighestBuyPrize(id), 2)) ;
				item.accumulate("OneDayLowest", Utils.getDoubleS(this.oneDayData.getLowest(id), 2)) ;
				item.accumulate("OneDayHighest", Utils.getDoubleS(this.oneDayData.getHighest(id), 2)) ;
				item.accumulate("OneDayTotal", Utils.getDoubleS(this.oneDayData.getTotal(id), 4)) ;

				double s = this.oneDayData.get24Price(ftrademapping.getFid());
				List<Ftradehistory> ftradehistorys = (List<Ftradehistory>)constantMap.get("tradehistory");
				for (Ftradehistory ftradehistory : ftradehistorys) {
					if(ftradehistory.getFtrademapping().getFid().intValue() == ftrademapping.getFid().intValue()){
						s= ftradehistory.getFprice();
						break;
					}
				}
				double last = 0d;
				try {
					last = Utils.getDouble((this.realTimeData.getLatestDealPrize(ftrademapping.getFid())-s)/s*100, 2);
				} catch (Exception e) {}
				
				item.accumulate("priceRaiseRate", last) ;

				//logo
				String shortName = ftrademapping.getFvirtualcointypeByFvirtualcointype2().getfShortName();
				item.accumulate("logo", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFurl2());
				
				//七日涨跌幅数据
				JSONArray day7 = new JSONArray() ;
			
				Map ftradehistory7D = (Map)this.constantMap.get("ftradehistory7D");
				if(ftradehistory7D.containsKey(ftrademapping.getFid().intValue())){
					List ss = (List)ftradehistory7D.get(ftrademapping.getFid().intValue());
					Iterator it = ss.iterator();
					int index = 0 ;
					while(it.hasNext()){
						if(index++ == 6){
							day7.add(this.realTimeData.getLatestDealPrize(ftrademapping.getFid())) ;
						}else{
							day7.add(it.next());
						}
					}
				}
				item.accumulate("day7", day7) ;
				
				jsonArray.add(item) ;
			}
			
			
			jsonObject.accumulate("list", jsonArray) ;
			jsonObject.accumulate("withdrawCNYDesc", constantMap.get("withdrawCNYDesc"));
			return jsonObject.toString() ;
			
		}catch(Exception e){
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
		
	}
	
	//虚拟币交易
	public String GetMarketDepth(HttpServletRequest request) throws Exception {
		
		try{
			int symbol = Integer.parseInt(request.getParameter("id")) ;
			Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
			int count1 = ftrademapping.getFcount1() ;
			int count2 = ftrademapping.getFcount2() ;
			if(ftrademapping==null || ftrademapping.getFstatus()== TrademappingStatusEnum.FOBBID){
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(Result, false) ;
				jsonObject.accumulate(ErrorCode , -10000) ;//虚拟币不存在
				return jsonObject.toString() ;
			}
			
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			jsonObject.accumulate("lastUpdateTime", Utils.dateFormat(Utils.getTimestamp())) ;
			
			
			jsonObject.accumulate("cnyName", ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFname()) ;
			if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype()== CoinTypeEnum.FB_CNY_VALUE){
				jsonObject.accumulate("vir_id1", 0) ;
				jsonObject.accumulate("vir_id1_isWithDraw", true) ;
			}else{
				jsonObject.accumulate("vir_id1", ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid()) ;
				jsonObject.accumulate("vir_id1_isWithDraw", ftrademapping.getFvirtualcointypeByFvirtualcointype1().isFIsWithDraw()) ;
			}
			jsonObject.accumulate("vir_id2", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid()) ;
			jsonObject.accumulate("vir_id2_isWithDraw", ftrademapping.getFvirtualcointypeByFvirtualcointype2().isFIsWithDraw()) ;
			jsonObject.accumulate("coinName", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getfShortName()) ;
			jsonObject.accumulate("cnName", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname()) ;
			jsonObject.accumulate("currency", ftrademapping.getFvirtualcointypeByFvirtualcointype1().getfShortName()) ;
			jsonObject.accumulate("currencySymbol", ftrademapping.getFvirtualcointypeByFvirtualcointype1().getfSymbol()) ;
			Object[] buyMap = this.realTimeData.getBuyDepthMap(symbol,5) ;
			Object[] sellMap = this.realTimeData.getSellDepthMap(symbol,5) ;

			JSONArray buyArray = new JSONArray() ;
			JSONArray sellArray = new JSONArray() ;
			for (int i = 0; i < buyMap.length ; i++) {
				Fentrust fentrust = (Fentrust)buyMap[i] ;
				JSONObject item = new JSONObject() ;
				item.accumulate("price", decimalFormat2.format(fentrust.getFprize())) ;
				item.accumulate("amount",decimalFormat4.format(fentrust.getFleftCount())) ;
				buyArray.add(item) ;
			}
			for (int i = 0; i < sellMap.length ; i++) {
				Fentrust fentrust = (Fentrust)sellMap[i] ;
				JSONObject item = new JSONObject() ;
				item.accumulate("price", decimalFormat2.format(fentrust.getFprize())) ;
				item.accumulate("amount", decimalFormat4.format(fentrust.getFleftCount())) ;
				sellArray.add(item) ;
			}

			jsonObject.accumulate("sellDepth", sellArray) ;
			jsonObject.accumulate("buyDepth", buyArray) ;
			
			
			//quotation
			JSONObject quotation = new JSONObject() ;
			//成交量，买一，卖一，最高，最低，现价
			BigDecimal SellOnePrice = new BigDecimal(this.realTimeData.getLowestSellPrize(symbol)).setScale(2, BigDecimal.ROUND_DOWN) ;
			BigDecimal BuyOnePrice = new BigDecimal(this.realTimeData.getHighestBuyPrize(symbol)).setScale(2, BigDecimal.ROUND_DOWN) ;

			quotation.accumulate("LatestDealPrice", Utils.decimalFormat(this.realTimeData.getLatestDealPrize(symbol), 2)) ;
			quotation.accumulate("SellOnePrice", Utils.decimalFormat(this.realTimeData.getLowestSellPrize(symbol), 2)) ;
			quotation.accumulate("BuyOnePrice", Utils.decimalFormat(this.realTimeData.getHighestBuyPrize(symbol), 2)) ;
			quotation.accumulate("OneDayLowest", Utils.decimalFormat(this.oneDayData.getLowest(symbol), 2)) ;
			quotation.accumulate("OneDayHighest", Utils.decimalFormat(this.oneDayData.getHighest(symbol), 2)) ;
			quotation.accumulate("OneDayTotal", Utils.decimalFormat(this.oneDayData.getTotal(symbol), 4)) ;

			
			List<Ftradehistory> ftradehistorys = (List<Ftradehistory>)constantMap.get("tradehistory");
			Ftradehistory ftradehistory = null;
			for (Ftradehistory th : ftradehistorys) {
				if(th.getFtrademapping().getFid() == ftrademapping.getFid().intValue()){
					ftradehistory = th ;
					break;
				}
			}
			
			//24小时涨跌幅
			double priceRaiseRate = 0 ;
			double sx = this.oneDayData.get24Price(ftrademapping.getFid());
			if(ftradehistory!=null ){
				sx= ftradehistory.getFprice();
			}
			try {
				priceRaiseRate = Utils.getDouble((this.realTimeData.getLatestDealPrize(ftrademapping.getFid())-sx)/sx*100, 2);
			} catch (Exception e) {}
			
			quotation.accumulate("priceRaiseRate", priceRaiseRate) ;
			
			jsonObject.accumulate("quotation", quotation) ;
			
			//coinInfo
			JSONObject coinInfo = new JSONObject() ;
			coinInfo.accumulate("id", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid()) ;
			coinInfo.accumulate("fname", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname()) ;
			coinInfo.accumulate("fShortName", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getfShortName()) ;
			coinInfo.accumulate("fSymbol", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getfSymbol()) ;
			jsonObject.accumulate("coinInfo", coinInfo) ;
			
			//asset
			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;
			if(fuser!=null){
				JSONObject asset = new JSONObject() ;
				Fvirtualwallet fwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid());
				Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid()) ;
				
				JSONObject rmb = new JSONObject() ;
				rmb.accumulate("total", Utils.decimalFormat(fwallet.getFtotal(), 2)) ;  //可用人民币
				rmb.accumulate("frozen", Utils.decimalFormat(fwallet.getFfrozen(), 4)) ;
				rmb.accumulate("canBuy", Utils.decimalFormat((fwallet.getFtotal()/SellOnePrice.doubleValue()), 4)) ;
				
				JSONObject coin = new JSONObject() ;
				coin.accumulate("total", Utils.decimalFormat(fvirtualwallet.getFtotal(), 4)) ;
				coin.accumulate("frozen", Utils.decimalFormat(fvirtualwallet.getFfrozen(), 4)) ;
				coin.accumulate("canSell", Utils.decimalFormat(fvirtualwallet.getFtotal()*BuyOnePrice.doubleValue(), 4)) ;
				
				asset.accumulate("totalAsset", Utils.decimalFormat((new BigDecimal((fwallet.getFtotal()+fwallet.getFfrozen())).add(this.getVirtualCoinAsset(fuser))).doubleValue(), 2)) ;
				asset.accumulate("rmb", rmb) ;
				asset.accumulate("coin", coin) ;
				jsonObject.accumulate("asset", asset) ;
				
				//其他信息
				JSONObject others = new JSONObject() ;
				others.accumulate("isNeedPasswd", fuser.getFtradePassword()!=null) ;
				others.accumulate("minTradePrice", Utils.decimalFormat((new BigDecimal(1/(Math.pow(10, count2))).doubleValue()), 2)) ;
				others.accumulate("minTradeCount", 0.0001D) ;
				others.accumulate("maxDecimal", count2) ;
				jsonObject.accumulate("others", others) ;
			}
			
			
			return jsonObject.toString() ;
			
		}catch(Exception e){
			e.printStackTrace();
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, false) ;
			jsonObject.accumulate(ErrorCode , -10000) ;//未知错误
			return jsonObject.toString() ;
		}
		
	}

	//获取app首页信息
	public String GetNews(HttpServletRequest request) throws Exception {
		
		try{
			
			int currentPage = ApiConstant.getInt(request.getParameter(CurrentPage)) ;
			
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			jsonObject.accumulate("lastUpdateTime", Utils.dateFormat(Utils.getTimestamp())) ;

			//banner图片
			JSONArray banners = new JSONArray() ;
			
			for (int i = 0; i < 4; i++) {
				String banner = constantMap.getString("bigImage"+(i+1)) ;
				String bigImageURL = constantMap.getString("bigImageURL"+(i+1)) ;
				String title = constantMap.getString("bigImageTitle"+(i+1)) ;
				banner="".equals(banner)?null:(banner) ;
				
				JSONObject item = new JSONObject() ;
				item.accumulate("img", banner) ;
				item.accumulate("url", bigImageURL.trim()) ;
				item.accumulate("title", title) ;
				banners.add(item) ;
				
			}
			
			jsonObject.accumulate("banners", banners) ;
			
			//新闻
			
			String appNameNews = this.constantMap.getString("webName") ;
			List<Farticle> farticles = this.utilsService.list4((currentPage-1)*maxResult, maxResult, "  order by fid desc ", true) ;
			int total = this.utilsService.count("", Farticle.class) ;
			JSONArray jsonArray = new JSONArray() ;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (Farticle farticle : farticles) {
				JSONObject item = new JSONObject() ;
				int aid = farticle.getFid() ; 
				item.accumulate("id", aid);
				item.accumulate("title", farticle.getFtitle()) ;
				item.accumulate("date", sdf.format(farticle.getFlastModifyDate())) ;
				item.accumulate("from", appNameNews) ;
				item.accumulate("img", farticle.getFurl()) ;
				item.accumulate("type", farticle.getFarticletype().getFname()) ;
				jsonArray.add(item) ;
			}
			jsonObject.accumulate("news", jsonArray) ;
			jsonObject.accumulate(CurrentPage, currentPage) ;
			jsonObject.accumulate(TotalPage, totalPage(total, maxResult)) ;
			
			
			
			return jsonObject.toString() ;
			
		}catch(Exception e){
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
		
	}

	protected BigDecimal getVirtualCoinAsset(Fuser fuser){
		double total = 0D ;
		String filter = "where fstatus="+ VirtualCoinTypeStatusEnum.Normal+" and ftype<>"+ CoinTypeEnum.FB_CNY_VALUE+" order by fid asc";
		List<Fvirtualcointype> fvirtualcointypes = this.virtualCoinService.list(0, 0, filter, false);
		Map<Integer,Integer> tradeMappings = (Map)this.constantMap.get("tradeMappings");
		for (int i = 0; i < fvirtualcointypes.size(); i++) {
			int trademapping_id = tradeMappings.get(fvirtualcointypes.get(i).getFid()) ;
			Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointypes.get(i).getFid()) ;
			
			double curPrice = this.realTimeData.getLatestDealPrize(trademapping_id) ;
			double asset = curPrice*(fvirtualwallet.getFtotal()+fvirtualwallet.getFfrozen()) ;
			
			total+=asset ;
		}
		return new BigDecimal(total).setScale(4, BigDecimal.ROUND_HALF_UP) ;
	}
	
	public String GetVersion(HttpServletRequest request) throws Exception {
		String android_version = this.constantMap.getString("android_version");
		String ios_version = this.constantMap.getString("ios_version");
		JSONObject jsonObject = new JSONObject() ;
		jsonObject.accumulate(Result, true) ;
		jsonObject.accumulate("ios_version", ios_version) ;
		jsonObject.accumulate("android_version", android_version) ;
		jsonObject.accumulate("android_downurl", this.constantMap.getString("android_downurl")) ;
		jsonObject.accumulate("ios_downurl", this.constantMap.getString("ios_downurl")) ;
		return jsonObject.toString() ;
	}
	
	public String GetAbout(HttpServletRequest request) throws Exception {
		String webName = this.constantMap.getString("webName");
		String telephone = this.constantMap.getString("telephone");
		String serviceQQ = this.constantMap.getString("serviceQQ");
		String email = this.constantMap.getString("email");
		String fulldomain = this.constantMap.getString("fulldomain");
		Fwebbaseinfo webinfo = (Fwebbaseinfo)this.constantMap.get("webinfo");
		
		
		JSONObject jsonObject = new JSONObject() ;
		jsonObject.accumulate(Result, true) ;
		jsonObject.accumulate("fulldomain", fulldomain) ;
		jsonObject.accumulate("copyRights", webinfo.getFcopyRights()) ;
		jsonObject.accumulate("webName", webName) ;
		jsonObject.accumulate("telephone", telephone) ;
		jsonObject.accumulate("serviceQQ", serviceQQ) ;
		jsonObject.accumulate("email", email) ;
		return jsonObject.toString() ;
	}
//	
//	public String GetMyCenter(HttpServletRequest request) throws Exception {
//		JSONObject jsonObject = new JSONObject() ;
//		jsonObject.accumulate(Result, true) ;
//		return jsonObject.toString() ;
//	}
	
	public String GetBtcRechargeListRecord(HttpServletRequest request) throws Exception{
		
		try{
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			int fvirtualCointypeId = Integer.parseInt(request.getParameter("id")) ;
			int currentPage = 1 ;
			int totalPage = 0 ;
			try{
				currentPage = Integer.parseInt(request.getParameter("currentPage")) ;
				currentPage = currentPage<1?1:currentPage ;
			}catch(Exception e){}
			
			Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(fvirtualCointypeId) ;
			if(fvirtualcointype==null || fvirtualcointype.getFstatus()== VirtualCoinTypeStatusEnum.Abnormal){
				return ApiConstant.getUnknownError(null) ;
			}
			
			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;
			Fvirtualaddress fvirtualaddress = this.frontVirtualCoinService.findFvirtualaddress(fuser, fvirtualcointype) ;
			
			jsonObject.accumulate("address", fvirtualaddress.getFadderess()) ;
			
			JSONArray jsonArray = new JSONArray() ;
			String filter = " where ftype="+ VirtualCapitalOperationTypeEnum.COIN_IN+" and fvirtualcointype.fid="+fvirtualcointype.getFid()+" order by fid desc ";
			int totalCount = this.frontVirtualCoinService.findFvirtualcaptualoperationsCount(filter) ;
			totalPage = totalCount / maxResult +(totalCount%maxResult==0?0:1) ;
			
			jsonObject.accumulate("totalPage", totalPage) ;
			jsonObject.accumulate("currentPage", currentPage) ;
			if(currentPage>totalPage){
				jsonObject.accumulate("list", jsonArray) ;
				return jsonObject.toString() ;
			}
			
			List<Fvirtualcaptualoperation> list = this.frontVirtualCoinService.findFvirtualcaptualoperations((currentPage-1)*maxResult, maxResult, filter, true) ;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
			for (int i = 0; i < list.size(); i++) {
				Fvirtualcaptualoperation fvirtualcaptualoperation = list.get(i) ;
				
				JSONObject item = new JSONObject() ;
				item.accumulate("id", fvirtualcaptualoperation.getFid()) ;
				item.accumulate("fvi_id", fvirtualcaptualoperation.getFvirtualcointype().getFid()) ;
				item.accumulate("fcreateTime", sdf.format(fvirtualcaptualoperation.getFcreateTime())) ;
				item.accumulate("flastUpdateTime", sdf.format(fvirtualcaptualoperation.getFlastUpdateTime())) ;
				item.accumulate("famount", fvirtualcaptualoperation.getFamount()) ;
				item.accumulate("ffees", fvirtualcaptualoperation.getFfees()) ;
				item.accumulate("ftype", fvirtualcaptualoperation.getFtype()) ;
				if(fvirtualcaptualoperation.getFtype()== VirtualCapitalOperationTypeEnum.COIN_IN){
					item.accumulate("fstatus", VirtualCapitalOperationInStatusEnum.getEnumString(fvirtualcaptualoperation.getFstatus())) ;
				}else{
					item.accumulate("fstatus", VirtualCapitalOperationOutStatusEnum.getEnumString(fvirtualcaptualoperation.getFstatus())) ;
				}
				item.accumulate("recharge_virtual_address", fvirtualcaptualoperation.getRecharge_virtual_address()) ;
				jsonArray.add(item) ;
			}
			jsonObject.accumulate("list", jsonArray) ;
			
			return jsonObject.toString() ;
		}catch(Exception e){
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
/*	public String GetAccountRecord(HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject() ;
		jsonObject.accumulate(Result, true) ;
		try{
			int currentPage = 1 ;
			int type = 0 ;
			int symbol = 0 ;
			Fvirtualcointype fvirtualcointype = null ;
			try{
				symbol = Integer.parseInt(request.getParameter("symbol")) ;
				fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
				type = Integer.parseInt(request.getParameter("type")) ;
				currentPage = Integer.parseInt(request.getParameter("currentPage")) ;
				currentPage = currentPage < 1?1:currentPage ;
			}catch (Exception e) {}
			
			//内容
			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;
			int maxReulsts = maxResult ;
			StringBuffer sb = new StringBuffer(" where fuser.fid="+fuser.getFid()+" and fvirtualcointype.fid="+symbol+"  ") ;
			if(type == 0 ){
				//未成交
				sb.append(" and (fstatus="+EntrustStatusEnum.PartDeal+"  or fstatus="+EntrustStatusEnum.Going+") ") ;
			}else{
				sb.append(" and (fstatus="+EntrustStatusEnum.Cancel+"  or fstatus="+EntrustStatusEnum.AllDeal+") ") ;
			}
			sb.append(" order by fid desc ") ;
			List<Fentrust> fentrusts = this.utilsService.list((currentPage-1)*maxReulsts, maxReulsts, sb.toString(), true, Fentrust.class) ;
			int count = this.utilsService.count(sb.toString(), Fentrust.class) ;
			jsonObject.accumulate("currentPage", currentPage) ;
			jsonObject.accumulate("totalPage", count/maxReulsts+(count%maxReulsts==0?0:1)) ;
			
			JSONArray jsonArray = new JSONArray() ;
			for (int i = 0; i < fentrusts.size(); i++) {
				Fentrust fentrust = fentrusts.get(i) ;
				
				JSONObject item = new JSONObject() ;
				item.accumulate("fid", fentrust.getFid()) ;
				item.accumulate("date", Utils.dateFormat(fentrust.getFcreateTime())) ;
				item.accumulate("title", fvirtualcointype.getfShortName()+fentrust.getFentrustType_s()) ;
				item.accumulate("count", fentrust.getFcount()) ;
				item.accumulate("leftCount", new BigDecimal(fentrust.getFleftCount(), 4)) ;
				item.accumulate("price", fentrust.getFprize()) ;
				item.accumulate("fee", fentrust.getFfees()) ;
				item.accumulate("status", fentrust.getFstatus()) ;
				item.accumulate("status_s", fentrust.getFstatus_s()) ;
				item.accumulate("type", fentrust.getFentrustType()) ;
				item.accumulate("type_s", fentrust.getFentrustType_s()) ;
				
				jsonArray.add(item) ;
			}
			
			jsonObject.accumulate("list", jsonArray) ;
			return jsonObject.toString() ;
			
			
		}catch(Exception e){
			e.printStackTrace() ;
		}
		return null ;
	}
*/
	
	public String GetCoinListInfo(HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject() ;
		jsonObject.accumulate(Result, true) ;
		try{
			JSONArray jsonArray = new JSONArray() ;
			String filter = "where fstatus="+ VirtualCoinTypeStatusEnum.Normal+" and ftype<>"+ CoinTypeEnum.FB_CNY_VALUE+" order by fid asc";
			List<Fvirtualcointype> fvirtualcointypes = this.virtualCoinService.list(0, 0, filter, false);
			for (int i = 0; i < fvirtualcointypes.size(); i++) {
				JSONObject item = new JSONObject();
				Fvirtualcointype fvirtualcointype = fvirtualcointypes.get(i) ;
				
				item.accumulate("id", fvirtualcointype.getFid()) ;
				item.accumulate("fname", fvirtualcointype.getFname()) ;
				item.accumulate("fShortName", fvirtualcointype.getfShortName()) ;
				item.accumulate("fSymbol", fvirtualcointype.getfSymbol()) ;
				jsonArray.add(item) ;
			}
			jsonObject.accumulate("coinList", jsonArray) ;
			return jsonObject.toString() ;
 		}catch(Exception e){
 			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
 		}
	}
	
	//用户信息、资产
	public String GetAccountInfo(HttpServletRequest request) throws Exception {
		
		try{

			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			
			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;

			JSONObject userInfoObject = new JSONObject() ;
			userInfoObject.accumulate("nickName", fuser.getFnickName()) ;
			userInfoObject.accumulate("isGoogleBind", fuser.getFgoogleBind()) ;
			userInfoObject.accumulate("isSecondValidate", fuser.getFopenSecondValidate()) ;
			userInfoObject.accumulate("fid", fuser.getFid()) ;
			userInfoObject.accumulate("keyLevel", fuser.getKyclevel());
			//真实姓名，手机号码
			userInfoObject.accumulate("isRealName", fuser.getFpostRealValidate()) ;
			if(fuser.getFpostRealValidate() == true ){
				userInfoObject.accumulate("realName", fuser.getFrealName()) ;
			}else{
				userInfoObject.accumulate("realName", null) ;
			}
			userInfoObject.accumulate("isBindMobil", fuser.isFisTelephoneBind()) ;
			if(fuser.isFisTelephoneBind() == true ){
				userInfoObject.accumulate("tel", fuser.getFtelephone()) ;
			}else{
				userInfoObject.accumulate("tel", null) ;
			}
			if(fuser.getFtradePassword() == null || fuser.getFtradePassword().trim().length() ==0){
				userInfoObject.accumulate("isHasTradePWD",0) ;
			}else{
				userInfoObject.accumulate("isHasTradePWD",1) ;
			}
			
			jsonObject.accumulate("userInfo", userInfoObject) ;
			
			JSONObject assetInfoObject = new JSONObject() ;
			JSONArray tradeAccountArray = new JSONArray() ;
			JSONArray marginAccountArray = new JSONArray() ;
			
			JSONObject tradeCnyOjbect = new JSONObject() ;
			JSONObject marginCnyObject = new JSONObject() ;
			
			Fvirtualwallet fwallet = this.frontUserService.findWalletByUser(fuser.getFid()) ;
			tradeCnyOjbect.accumulate("id", 0) ;
			tradeCnyOjbect.accumulate("cnName", "人民币") ;
			tradeCnyOjbect.accumulate("shortName", "CNY") ;
			tradeCnyOjbect.accumulate("total", Utils.decimalFormat(fwallet.getFtotal(), 2));
			tradeCnyOjbect.accumulate("frozen", Utils.decimalFormat(fwallet.getFfrozen(), 2)) ;
			tradeCnyOjbect.accumulate("isCoin", false) ;
			tradeCnyOjbect.accumulate("logo", OSSPostObject.URL + "//static/app/images/logo_rmb.png") ;

			tradeAccountArray.add(tradeCnyOjbect) ;
			
			marginCnyObject.accumulate("id", 0) ;
			marginCnyObject.accumulate("total", 0) ;
			marginCnyObject.accumulate("frozen",0) ;
			marginCnyObject.accumulate("borrow",0) ;
			marginAccountArray.add(marginCnyObject) ;
			
			double totalAsset = 0D;//总资产
			double netAsset = 0D ;//净资产
			double tradeTotalAsset = 0D ;//交易总资产
			double tradeNetAsset = 0D ;//交易净资产
			double marginTotalAsset = 0D ;//放款总资产
			//总借金额
			double totalBorrow = 0d;
			
			totalAsset+=fwallet.getFtotal()+fwallet.getFfrozen()+marginTotalAsset ;
			netAsset+=fwallet.getFtotal()+fwallet.getFfrozen()+marginTotalAsset ;
			
			tradeTotalAsset+=fwallet.getFtotal()+fwallet.getFfrozen() ;
			tradeNetAsset+=fwallet.getFtotal()+fwallet.getFfrozen() ;
			
			//虚拟币地址：
			JSONArray coinAddress = new JSONArray() ;
			
			String filter = "where fstatus="+ VirtualCoinTypeStatusEnum.Normal+" and ftype<>"+ CoinTypeEnum.FB_CNY_VALUE+" order by fid asc";
			List<Fvirtualcointype> fvirtualcointypes = this.virtualCoinService.list(0, 0, filter, false);
			Map<Integer,Integer> tradeMappings = (Map)this.constantMap.get("tradeMappings");
			for (int i = 0; i < fvirtualcointypes.size(); i++) {
				Fvirtualcointype fvirtualcointype = fvirtualcointypes.get(i) ;
				int ftrademapping_id = tradeMappings.get(fvirtualcointype.getFid()) ;
				
				Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid()) ;
				JSONObject tradeObj = new JSONObject() ;
				JSONObject marginObj = new JSONObject() ;

				double curPrice = this.realTimeData.getLatestDealPrize(ftrademapping_id) ;
				double asset = curPrice*(fvirtualwallet.getFtotal()+fvirtualwallet.getFfrozen()) ;
				
				tradeObj.accumulate("id", fvirtualcointype.getFid()) ;
				tradeObj.accumulate("logo", Utils.curl(fvirtualcointype.getFurl())) ;
				tradeObj.accumulate("cnName", fvirtualcointype.getFname()) ;
				tradeObj.accumulate("shortName", fvirtualcointype.getfShortName()) ;
				tradeObj.accumulate("total", Utils.decimalFormat(fvirtualwallet.getFtotal(), 4)) ;
				tradeObj.accumulate("frozen", Utils.decimalFormat(fvirtualwallet.getFfrozen(), 4)) ;
				tradeObj.accumulate("borrow", 0) ;
				tradeObj.accumulate("zhehe", Utils.decimalFormat(asset, 2)) ;
				tradeObj.accumulate("curPrice", Utils.decimalFormat(curPrice, 2)) ;
				tradeObj.accumulate("isCoin", true) ;
				tradeObj.accumulate("isWithDraw", fvirtualcointype.isFIsWithDraw()) ;
				tradeAccountArray.add(tradeObj) ;
				
				marginObj.accumulate("id", fvirtualcointype.getFid()) ;
				marginObj.accumulate("total", 0) ;
				marginObj.accumulate("frozen",0) ;
				marginObj.accumulate("borrow",0) ;
				marginAccountArray.add(marginObj) ;
				
				totalAsset = totalAsset + asset;
				netAsset = netAsset + asset;
				tradeTotalAsset+=asset ;
				tradeNetAsset+=asset ;
				
				
				//虚拟币地址
				if(fvirtualcointype.isFIsWithDraw()){
					Fvirtualaddress fvirtualaddress = this.frontVirtualCoinService.findFvirtualaddress(fuser, fvirtualcointype) ;
					JSONObject item = new JSONObject() ;
					item.accumulate("coinId", fvirtualcointype.getFid()) ;
					item.accumulate("coinName", fvirtualcointype.getFname()) ;
					item.accumulate("coinShortName", fvirtualcointype.getfShortName()) ;
					item.accumulate("address", fvirtualaddress.getFadderess()) ;
					coinAddress.add(item) ;
				}
			}
			assetInfoObject.accumulate("totalAsset", Utils.decimalFormat(totalAsset, 2)) ;
			
			JSONObject tradeAccountObject = new JSONObject() ;
			tradeAccountObject.accumulate("coins", tradeAccountArray) ;
			tradeAccountObject.accumulate("totalAsset", Utils.decimalFormat(tradeTotalAsset, 2)) ;
			tradeAccountObject.accumulate("netAsset", Utils.decimalFormat(tradeNetAsset-totalBorrow, 2)) ;
			assetInfoObject.accumulate("tradeAccount", tradeAccountObject) ;
			
			
			JSONObject marginAccountObject = new JSONObject() ;
			marginAccountObject.accumulate("coins", marginAccountArray) ;
			marginAccountObject.accumulate("totalAsset", Utils.decimalFormat(marginTotalAsset, 2)) ;
			assetInfoObject.accumulate("marginAccount", marginAccountObject) ;
			
			jsonObject.accumulate("asset", assetInfoObject) ;
			
			//提现银行卡信息
			List<FbankinfoWithdraw> fbankinfoWithdraw = this.utilsService.list(0, 0, " where fuser.fid="+fuser.getFid()+" and  fstatus="+ BankInfoWithdrawStatusEnum.NORMAL_VALUE+" ", false, FbankinfoWithdraw.class) ;
			JSONArray withdrawInfos = new JSONArray() ;
			for (FbankinfoWithdraw withdrawInfo : fbankinfoWithdraw) {
				JSONObject item = new JSONObject() ;
				item.accumulate("id", withdrawInfo.getFid()) ;
				item.accumulate("bankType", withdrawInfo.getFbankType()) ;
				item.accumulate("address", withdrawInfo.getFaddress()) ;
				item.accumulate("bankNumber", BankTypeEnum.getEnumString(withdrawInfo.getFbankType())+" 尾号"+(withdrawInfo.getFbankNumber().length()>=4?(withdrawInfo.getFbankNumber().substring(withdrawInfo.getFbankNumber().length()-4)):withdrawInfo.getFbankNumber())) ;
				
				withdrawInfos.add(item) ;
			}
			jsonObject.accumulate("withdrawInfos", withdrawInfos) ;
			
			//虚拟币地址
			jsonObject.accumulate("coinAddress", coinAddress) ;
			jsonObject.accumulate("rechageBankDesc", constantMap.get("rechageBankDesc"));
			jsonObject.accumulate("btcRechageDesc", constantMap.get("btcRechageDesc"));
			jsonObject.accumulate("withdrawCOINDesc", constantMap.get("withdrawCOINDesc"));
			jsonObject.accumulate("withdrawCNYDesc", constantMap.get("withdrawCNYDesc"));
			return jsonObject.toString() ;
			
		}catch(Exception e){
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
		
	}
	
	//注册
	public String UserRegister(HttpServletRequest request) throws Exception {
		try{

			String areaCode = "86" ;
			String password = request.getParameter("password") ;
			String regName = request.getParameter("email") ;
			int regType = Integer.parseInt( request.getParameter("type") ) ;
			String ecode = request.getParameter("code") ;
			String phoneCode = request.getParameter("code") ;
			
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			String phone = HtmlUtils.htmlEscape(regName);
			phoneCode = HtmlUtils.htmlEscape(phoneCode);
			String isOpenReg = constantMap.get("isOpenReg").toString().trim();
			if(!isOpenReg.equals("1")){
				jsonObject.accumulate(ErrorCode, -888) ;
				jsonObject.accumulate(Value, "暂停注册") ;
				return jsonObject.toString() ;
			}
			
//			password = HtmlUtils.htmlEscape(password.trim());
//			if(password == null || password.length() <6){
//				jsonObject.accumulate(ErrorCode, -11) ;
//				jsonObject.accumulate(Value, "登陆短信长度有误") ;
//				return jsonObject.toString() ;
//			}

			password = HtmlUtils.htmlEscape(password.trim());
			if(validateLoginPassword(password)){
				jsonObject.accumulate("code", -11) ;
				jsonObject.accumulate(APP_API_Controller.Value, "密码格式不正确") ;
				return jsonObject.toString() ;
			}
			//邮箱
			if(regType==1){
				//手机注册
				
				boolean flag1 = this.frontUserService.isTelephoneExists(regName) ;
				if(flag1){
					jsonObject.accumulate(ErrorCode, -22) ;
					jsonObject.accumulate(Value, "手机号码已经被注册") ;
					return jsonObject.toString() ;
				}
				
				if(!phone.matches(new Constant().PhoneReg)){
					jsonObject.accumulate(ErrorCode, -22) ;
					jsonObject.accumulate(Value, "手机格式错误") ;
					return jsonObject.toString() ;
				}
				
				boolean mobilValidate = validateMessageCode(getIpAddr(request),areaCode,phone, MessageTypeEnum.REG_CODE, phoneCode) ;
				if(!mobilValidate){
					jsonObject.accumulate(ErrorCode, -20) ;
					jsonObject.accumulate(Value, "短信验证码错误") ;
					return jsonObject.toString() ;
				}
				
			}else{
				//邮箱注册
				boolean flag = this.frontUserService.isEmailExists(regName) ;
				if(flag){
					jsonObject.accumulate(ErrorCode, -12) ;
					jsonObject.accumulate(Value, "邮箱已经存在") ;
					return jsonObject.toString() ;
				}
				
				boolean mailValidate = validateMailCode(getIpAddr(request), phone, SendMailTypeEnum.RegMail, ecode);
				if(!mailValidate){
					jsonObject.accumulate(ErrorCode, -10) ;
					jsonObject.accumulate(Value, "邮箱验证码错误") ;
					return jsonObject.toString() ;
				}
				
				if(!regName.matches(new Constant().EmailReg)){
					jsonObject.accumulate(ErrorCode, -12) ;
					jsonObject.accumulate(Value, "邮箱格式错误") ;
					return jsonObject.toString() ;
				}
				
			}
			
			
			//推广
			Fuser intro = null ;
			String intro_user = request.getParameter("intro_user") ;
			
			try {
				if(intro_user!=null && !"".equals(intro_user.trim())){
					intro = this.frontUserService.findById(Integer.parseInt(intro_user.trim())) ;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(intro==null){
				String isMustIntrol = constantMap.get("isMustIntrol").toString().trim();
				if(isMustIntrol.equals("1")){
					jsonObject.accumulate(ErrorCode, -200) ;
					jsonObject.accumulate(Value, "请填写正确的邀请码") ;
					return jsonObject.toString() ;
				}
			}
			
			
			Fuser fuser = new Fuser() ;
			if(intro!=null){
				fuser.setfIntroUser_id(intro) ;
			}
			fuser.setFintrolUserNo(null);
			
			if(regType == 1){
				//手机注册
				fuser.setFregType(RegTypeEnum.TEL_VALUE);
				fuser.setFtelephone(phone);
				fuser.setFareaCode(areaCode);
				fuser.setFisTelephoneBind(true);
				
				fuser.setFnickName(phone) ;
				fuser.setFloginName(phone) ;
			}else{
				fuser.setFregType(RegTypeEnum.EMAIL_VALUE);
				fuser.setFemail(regName) ;
				fuser.setFisMailValidate(true) ;
				fuser.setFnickName(regName.split("@")[0]) ;
				fuser.setFloginName(regName) ;
			}
			
			fuser.setSalt(Utils.getUUID());
			fuser.setFregisterTime(Utils.getTimestamp()) ;
			fuser.setFloginPassword(Utils.MD5(password,fuser.getSalt())) ;
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
			}finally{
				if(regType==1){
					String key1 = ip+"message_"+ MessageTypeEnum.REG_CODE ;
					String key2 = ip+"_"+phone+"_"+ MessageTypeEnum.REG_CODE ;
					this.validateMap.removeMessageMap(key1);
					this.validateMap.removeMessageMap(key2);
				}else{
					String key1 = ip+"mail_"+ SendMailTypeEnum.RegMail ;
					String key2 = ip+"_"+phone+"_"+ SendMailTypeEnum.RegMail ;
					this.validateMap.removeMailMap(key1);
					this.validateMap.removeMailMap(key2);
				}
			}
			
			if(saveFlag){
				String loginToken = this.realTimeData.putAppSession(getSession(request), fuser);
				jsonObject.accumulate(ErrorCode, 0);// 注册成功
				jsonObject.accumulate(Value, "注册成功");// 注册成功
				jsonObject.accumulate(LoginToken, loginToken);
				jsonObject.accumulate("postRealValidate", fuser.getFpostRealValidate());
				jsonObject.accumulate(Fid, fuser.getFid());
				return jsonObject.toString();
			
			}else{
				jsonObject.accumulate(ErrorCode, -10) ;
				jsonObject.accumulate(Value, "网络错误，请稍后再试") ;
				return jsonObject.toString() ;
			}
		
		}catch(Exception e){
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	//登陆
	public String UserLogin(HttpServletRequest request) throws Exception {
		Fapppush fapppush = null;
		try {
			String email = request.getParameter("email").trim().toLowerCase() ;
			String password = request.getParameter("password").trim() ;
			String phoneCode = request.getParameter("phonecode".trim());
			String phoneType = request.getParameter("phoneType");
			String googleCode = request.getParameter("googleCode");
			int phoneTypeInt = 0;
			if(StringUtils.isNotBlank(phoneType)){
				phoneTypeInt = Integer.parseInt(phoneType);
			}
			
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			
			List<Fuser> fusers = this.frontUserService.findUserByProperty("floginName", email);
			if(fusers == null || fusers.size() != 1){
				jsonObject.accumulate(ErrorCode, -4) ;
				jsonObject.accumulate(Value, "用户名不存在") ;
				return jsonObject.toString() ;
			}
			String ip = getIpAddr(request) ;
			if(fusers.get(0).getFgoogleBind()){
				if(StringUtils.isBlank(googleCode)){
					jsonObject.accumulate(ErrorCode, -5) ;
					jsonObject.accumulate(Value, "需要验证google") ;
					return jsonObject.toString() ;
				}else{

					int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE ) ;
					if(google_limit<=0){
						jsonObject.accumulate("code", -4) ;
						jsonObject.accumulate(Value, "此ip操作频繁，请2小时后再试!");
						return jsonObject.toString() ;
					}

					boolean googleValidate = GoogleAuth.auth(Long.parseLong(googleCode.trim()), fusers.get(0).getFgoogleAuthenticator()) ;
					if(!googleValidate){
						jsonObject.accumulate("code", -4) ;
						jsonObject.accumulate(Value, "谷歌验证码有误，您还有"+google_limit+"次机会");
						this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
						return jsonObject.toString() ;
					}else if(google_limit<new Constant().ErrorCountLimit){
						this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
					}
				}
			}
			Fuser fuser = new Fuser() ;
			fuser.setFloginName(email);
			fuser.setFloginPassword(password) ;
			fuser.setSalt(fusers.get(0).getSalt());
			fuser.setFloginPassword(password) ;

			fuser = this.frontUserService.updateCheckLogin(fuser, ip,email.matches(new Constant().EmailReg),true) ;
			if(fuser!=null){
				String isCanlogin = constantMap.get("isCanlogin").toString().trim();
				if(!isCanlogin.equals("1")){
					boolean isCanLogin = false;
					String[] canLoginUsers = constantMap.get("canLoginUsers").toString().split("#");
					for(int i=0;i<canLoginUsers.length;i++){
						if(canLoginUsers[i].equals(String.valueOf(fuser.getFid()))){
							isCanLogin = true;
							break;
						}
					}
					if(!isCanLogin){
						jsonObject.accumulate(ErrorCode , -3);//用户被禁用
						jsonObject.accumulate(Value, " 系统升级中，暂停登录 ") ;
						return jsonObject.toString() ;
					}
				}
				
				if(fuser.getFstatus()== UserStatusEnum.NORMAL_VALUE){
					jsonObject.accumulate(ErrorCode , 0);//登陆成功
					jsonObject.accumulate(Value, "登陆成功") ;
					String loginToken = this.realTimeData.putAppSession(getSession(request),fuser) ;
					jsonObject.accumulate(LoginToken, loginToken) ;
					jsonObject.accumulate("postRealValidate", fuser.getFpostRealValidate()) ;
					if(fuser.getFtradePassword() == null || fuser.getFtradePassword().trim().length() ==0){
						jsonObject.accumulate("isHasTradePWD",0) ;
					}else{
						jsonObject.accumulate("isHasTradePWD",1) ;
					}
					
					jsonObject.accumulate(Fid, fuser.getFid()) ;

					// 更新用户手机信息
					fapppush = new Fapppush();
					fapppush.setFuser(fuser.getFid());
					fapppush.setFphonecode(phoneCode);
					fapppush.setFphonetype(phoneTypeInt);
					this.fapppushService.updatePhoneInfo(fapppush);

					return jsonObject.toString() ;
				}else{
					jsonObject.accumulate(ErrorCode , -3);//用户被禁用
					jsonObject.accumulate(Value, "用户被禁用") ;
					return jsonObject.toString() ;
				}
			}else{
				jsonObject.accumulate(ErrorCode , -1);//错误的用户名或密码
				jsonObject.accumulate(Value, "错误的用户名或密码") ;
				return jsonObject.toString() ;
			}	
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	
	//买币
	public String buyBtcSubmit(HttpServletRequest request) throws Exception{
		try{
			int limited=0;
			int symbol = Integer.parseInt(request.getParameter("symbol")) ;
			double tradeAmount = Double.parseDouble(request.getParameter("tradeAmount")) ;
			double tradeCnyPrice = Double.parseDouble(request.getParameter("tradeCnyPrice")) ;
			String tradePwd = request.getParameter("tradePwd") ;

			
			JSONObject jsonObject = new JSONObject() ;

			Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
			if(ftrademapping==null  ||ftrademapping.getFstatus()== TrademappingStatusEnum.FOBBID){
				jsonObject.accumulate(ErrorCode, -100) ;
				jsonObject.accumulate(Value, "该币暂时不能交易");
				return jsonObject.toString() ;
			}
			
			//限制法币为人民币和比特币
			if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_CNY_VALUE
					&& ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_COIN_VALUE){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "网络错误");
				return jsonObject.toString() ;
			}

			Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
			Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;
			double minBuyCount = Utils.getDouble(ftrademapping.getFminBuyCount() ,ftrademapping.getFcount2()) ;
			double minBuyPrice = Utils.getDouble(ftrademapping.getFminBuyPrice(),ftrademapping.getFcount1()) ;
			double minBuyAmount = Utils.getDouble(ftrademapping.getFminBuyAmount(),ftrademapping.getFcount1()) ;
			
			//是否开放交易
			if(Utils.openTrade(ftrademapping, Utils.getTimestamp())==false){
				jsonObject.accumulate(ErrorCode, -400) ;
				jsonObject.accumulate(Value, "现在不是交易时间");
				return jsonObject.toString() ;
			}
			
			tradeAmount = Utils.getDouble(tradeAmount, ftrademapping.getFcount2()) ;
			tradeCnyPrice = Utils.getDouble(tradeCnyPrice, ftrademapping.getFcount1()) ;
			
			//定价单
			if(limited == 0 ){
				
				if(tradeAmount<minBuyCount){
					DecimalFormat decimalFormat = new DecimalFormat("#,##0.00000");
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "最小交易数量："+coin1.getfSymbol()+decimalFormat.format(minBuyCount));
					return jsonObject.toString() ;
				}
				
				if(tradeCnyPrice < minBuyPrice)
				{
					jsonObject.accumulate(ErrorCode, -3) ;
					jsonObject.accumulate(Value, "最小挂单价格："+coin1.getfSymbol()+minBuyPrice);
					return jsonObject.toString() ;
				}
				
				double total = Utils.getDouble(tradeAmount*tradeCnyPrice,ftrademapping.getFcount1());
				if(total < minBuyAmount){
					jsonObject.accumulate(ErrorCode, -3) ;
					jsonObject.accumulate(Value, "最小挂单金额："+coin1.getfSymbol()+minBuyAmount);
					return jsonObject.toString() ;
				}
				
				
			}else{
				if(tradeCnyPrice <minBuyAmount){
					jsonObject.accumulate(ErrorCode, -33) ;
					jsonObject.accumulate(Value, "最小交易金额："+minBuyAmount+coin1.getFname());
					return jsonObject.toString() ;
				}
			}
			
			
			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;
			if(!ValidateKycLevelEnum.validateKYC1(fuser.getKyclevel())){
				return ApiConstant.getKYC1ValidateError();
			}

			double totalTradePrice = 0F ;
			if(limited==0){
				totalTradePrice = tradeAmount*tradeCnyPrice ;
			}else{
				totalTradePrice = tradeAmount ;
			}
			Fvirtualwallet fwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(),coin1.getFid());
			if(fwallet.getFtotal()<totalTradePrice){
				jsonObject.accumulate(ErrorCode, -4) ;
				jsonObject.accumulate(Value, coin1.getFname()+"余额不足");
				return jsonObject.toString() ;
			}
			
				if(tradePwd == null || tradePwd.trim().length() == 0){
					jsonObject.accumulate(ErrorCode, -50) ;
					jsonObject.accumulate(Value, "交易密码错误");
					 return jsonObject.toString() ;
				}
				
				if(fuser.getFtradePassword() == null){
					 jsonObject.accumulate(ErrorCode, -5) ;
					 jsonObject.accumulate(Value, "您还没有设置交易密码，请到安全中心设置");
					 return jsonObject.toString() ;
			    }
				if(!Utils.MD5(tradePwd,fuser.getSalt()).equals(fuser.getFtradePassword())){
					jsonObject.accumulate(ErrorCode, -2) ;
					jsonObject.accumulate(Value, "交易密码错误");
					return jsonObject.toString() ;
				}
			
			
			boolean flag = false ;
			Fentrust fentrust = null ;
			try {
				fentrust = this.frontTradeService.updateEntrustBuy(symbol, tradeAmount, tradeCnyPrice, fuser, limited==1,request) ;
				flag = true ;
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(flag && fentrust != null){
				fentrust = this.frontTradeService.findFentrustById(fentrust.getFid()) ;
				if (limited==1) {
					this.realTimeData.addEntrustLimitBuyMap(symbol, fentrust);
				} else {
					this.realTimeData.addEntrustBuyMap(symbol, fentrust);
				}
				
				jsonObject.accumulate(ErrorCode, 0) ;
				jsonObject.accumulate(Value, "下单成功");
			}else{
				jsonObject.accumulate(ErrorCode, -200) ;
				jsonObject.accumulate(Value, "网络错误，请稍后再试");
			}
			
			return jsonObject.toString() ;
		
		}catch(Exception e){
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	//卖币
	public String sellBtcSubmit(HttpServletRequest request) throws Exception{
		try{

			int limited=0;
			int symbol = Integer.parseInt(request.getParameter("symbol")) ;
			double tradeAmount = Double.parseDouble(request.getParameter("tradeAmount")) ;
			double tradeCnyPrice = Double.parseDouble(request.getParameter("tradeCnyPrice")) ;
			String tradePwd = request.getParameter("tradePwd") ;
			JSONObject jsonObject = new JSONObject() ;
			
			Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
			if(ftrademapping==null  || ftrademapping.getFstatus()!= TrademappingStatusEnum.ACTIVE){
				jsonObject.accumulate(ErrorCode, -100) ;
				jsonObject.accumulate(Value, "该币暂时不能交易");
				return jsonObject.toString() ;
			}
			
			//限制法币为人民币和比特币
			if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_CNY_VALUE
					&& ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_COIN_VALUE){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "网络错误");
				return jsonObject.toString() ;
			
			}
			
			tradeAmount = Utils.getDouble(tradeAmount, ftrademapping.getFcount2()) ;
			tradeCnyPrice = Utils.getDouble(tradeCnyPrice, ftrademapping.getFcount1()) ;
			Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
			Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;
			double minBuyCount = ftrademapping.getFminBuyCount() ;
			double minBuyPrice = ftrademapping.getFminBuyPrice() ;
			double minBuyAmount = ftrademapping.getFminBuyAmount() ;
			
			//是否开放交易
			if(Utils.openTrade(ftrademapping, Utils.getTimestamp())==false){
				jsonObject.accumulate(ErrorCode, -400) ;
				jsonObject.accumulate(Value, "现在不是交易时间");
				return jsonObject.toString() ;
			}
			
			if(limited == 0 ){
				
				if(tradeAmount<minBuyCount){

					DecimalFormat decimalFormat = new DecimalFormat("#,##0.00000");
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "最小交易数量："+coin1.getfSymbol()+decimalFormat.format(minBuyCount));
					return jsonObject.toString() ;
				}
				
				if(tradeCnyPrice < minBuyPrice){
					jsonObject.accumulate(ErrorCode, -3) ;
					jsonObject.accumulate(Value, "最小挂单价格："+coin1.getfSymbol()+minBuyPrice);
					return jsonObject.toString() ;
				}
				double total = Utils.getDouble(tradeAmount*tradeCnyPrice,ftrademapping.getFcount1());
				if(total < minBuyAmount){
					jsonObject.accumulate(ErrorCode, -3) ;
					jsonObject.accumulate(Value, "最小挂单金额："+coin1.getfSymbol()+minBuyAmount);
					return jsonObject.toString() ;
				}
				
			}else{
				if(tradeAmount <minBuyCount){
					jsonObject.accumulate(ErrorCode, -33) ;
					jsonObject.accumulate(Value, "最小交易数量："+minBuyCount+coin2.getFname());
					return jsonObject.toString() ;
				}
			}
			
			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;
			if(!ValidateKycLevelEnum.validateKYC1(fuser.getKyclevel())){
				return ApiConstant.getKYC1ValidateError();
			}


			Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), coin2.getFid()) ;
			if(fvirtualwallet==null){
				jsonObject.accumulate(ErrorCode, -200) ;
				jsonObject.accumulate(Value, "系统错误，请联系管理员");
				return jsonObject.toString() ;
			}
			if(fvirtualwallet.getFtotal()<tradeAmount){
				jsonObject.accumulate(ErrorCode, -4) ;
				jsonObject.accumulate(Value, coin2.getFname()+"余额不足");
				return jsonObject.toString() ;
			}
			
				if(tradePwd == null || tradePwd.trim().length() == 0){
					jsonObject.accumulate(ErrorCode, -50) ;
					jsonObject.accumulate(Value, "交易密码错误");
					 return jsonObject.toString() ;
				}
				
				if(fuser.getFtradePassword() == null){
					 jsonObject.accumulate(ErrorCode, -5) ;
					 jsonObject.accumulate(Value, "您还没有设置交易密码，请到安全中心设置");
					 return jsonObject.toString() ;
			    }
				if(!Utils.MD5(tradePwd,fuser.getSalt()).equals(fuser.getFtradePassword())){
					jsonObject.accumulate(ErrorCode, -2) ;
					jsonObject.accumulate(Value, "交易密码错误");
					return jsonObject.toString() ;
				}
			
			
			boolean flag = false ;
			Fentrust fentrust = null ;
			try {
				fentrust = this.frontTradeService.updateEntrustSell(symbol, tradeAmount, tradeCnyPrice, fuser, limited==1,request) ;
				flag = true ;
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(flag && fentrust != null){
				fentrust = this.frontTradeService.findFentrustById(fentrust.getFid()) ;
				if (limited==1) {
					this.realTimeData.addEntrustLimitSellMap(symbol, fentrust);
				} else {
					this.realTimeData.addEntrustSellMap(symbol, fentrust);
				}
				
				
				jsonObject.accumulate(ErrorCode, 0) ;
				jsonObject.accumulate(Value, "下单成功");
			}else{
				jsonObject.accumulate(ErrorCode, -200) ;
				jsonObject.accumulate(Value, "网络错误，请稍后再试");
			}
			
			return jsonObject.toString() ;
		
		}catch(Exception e){
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	//推广收益明细
	public String GetIntrolinfo(HttpServletRequest request) throws Exception{
		try {
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int currentPage = 1 ;
			try{
				currentPage = Integer.parseInt(request.getParameter("currentPage")) ;
				currentPage = currentPage < 1?1:currentPage ;
			}catch (Exception e) {}

			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;

			String filter = "where fuser.fid="+fuser.getFid()+" order by fid desc";
			int total = this.adminService.getAllCount("Fintrolinfo", filter);
			int totalPage = total/maxResult + ((total%maxResult)  ==0?0:1) ;
			List<Fintrolinfo> fintrolinfos = this.introlinfoService.list((currentPage-1)*maxResult, maxResult,filter,true) ;

			jsonObject.accumulate("totalPage", totalPage) ;
			jsonObject.accumulate("currentPage", currentPage) ;
			JSONArray jsonArray = new JSONArray() ;
			if(currentPage>totalPage){
				jsonObject.accumulate("list", jsonArray) ;
				return jsonObject.toString() ;
			}
			for (int i=0;i<fintrolinfos.size();i++) {
				Fintrolinfo fintrolinfo = fintrolinfos.get(i);
				JSONObject item = new JSONObject();
				item.accumulate("createTime", sdf.format(fintrolinfo.getFcreatetime()));
				item.accumulate("title", fintrolinfo.getFtitle());
				jsonArray.add(item);
			}
			jsonObject.accumulate("list", jsonArray);
			return jsonObject.toString() ;
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	//取得线下明细
	public String GetIntrolDetail(HttpServletRequest request) throws Exception {
		try {
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			int currentPage = 1 ;
			try{
				currentPage = Integer.parseInt(request.getParameter("currentPage")) ;
				currentPage = currentPage < 1?1:currentPage ;
			}catch (Exception e) {}
			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;
			String filter = "where fIntroUser_id.fid="+fuser.getFid()+" order by fid desc";
			int total = this.adminService.getAllCount("Fuser", filter);
			int totalPage = total/maxResult + ((total%maxResult) ==0?0:1) ;
			List<Fuser> fusers = this.userService.list((currentPage-1)*maxResult, maxResult,filter,true) ;
			jsonObject.accumulate("totalPage", totalPage) ;
			jsonObject.accumulate("currentPage", currentPage) ;
			JSONArray jsonArray = new JSONArray() ;
			if(currentPage>totalPage){
				jsonObject.accumulate("list", jsonArray) ;
				return jsonObject.toString() ;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (int i=0;i<fusers.size();i++) {
				Fuser user = fusers.get(i);
				JSONObject item = new JSONObject();
				item.accumulate("id",user.getFid());
				item.accumulate("loginName",user.getFloginName());
				item.accumulate("time", sdf.format(user.getFregisterTime()));
				jsonArray.add(item);
			}
			jsonObject.accumulate("list", jsonArray);
			
			return jsonObject.toString() ;
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	
	//实名认证
	public String ValidateIdentity(HttpServletRequest request) throws Exception {
		try {

			String identityNo = request.getParameter("identityNo") ;
			int identityType = Integer.parseInt(request.getParameter("identityType")) ;
			String realName = request.getParameter("realName") ;
			
			LOGGER.info(CLASS_NAME + " validateIdentity,identityNo:{},realName:{}", identityNo, realName);
			JSONObject js = new JSONObject();
			js.accumulate(Result, true) ;
			
//			realName = HtmlUtils.htmlEscape(realName);  //实名认证，新疆人姓名中带点，因此不能进行转义处理
			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;
			identityNo = identityNo.toLowerCase();
			 String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",  
		                "3", "2" };  
		        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",  
		                "9", "10", "5", "8", "4", "2" };  
				JSONObject jsonObject = new JSONObject();
				jsonObject.accumulate(Result, true) ;
				
				if (identityNo.trim().length() != 15 && identityNo.trim().length() != 18) {
					jsonObject.accumulate(ErrorCode, -1);
					jsonObject.accumulate(Value, "身份证号码长度应该为15位或18位!");
					LOGGER.info(CLASS_NAME + " validateIdentity,身份证号码长度应该为15位或18位");
					return jsonObject.toString();
				}
				
				String Ai = "";
		        if (identityNo.length() == 18) {  
		            Ai = identityNo.substring(0, 17);  
		        } else if (identityNo.length() == 15) {  
		            Ai = identityNo.substring(0, 6) + "19" + identityNo.substring(6, 15);  
		        }
				LOGGER.info(CLASS_NAME + " validateIdentity,Ai:{}", Ai);
		        if (Utils.isNumeric(Ai) == false) {
		            jsonObject.accumulate(ErrorCode, -1);
					jsonObject.accumulate(Value, "身份证号码有误!");
					return jsonObject.toString();
		        }
		        // ================ 出生年月是否有效 ================  
		        String strYear = Ai.substring(6, 10);// 年份  
		        String strMonth = Ai.substring(10, 12);// 月份  
		        String strDay = Ai.substring(12, 14);// 月份  
		        if (Utils.isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
		        	jsonObject.accumulate(ErrorCode, -1);
					jsonObject.accumulate(Value, "身份证号码有误!");
					return jsonObject.toString();
		        }  
		        GregorianCalendar gc = new GregorianCalendar();  
		        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");  
		        try {
		            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150  
		                    || (gc.getTime().getTime() - s.parse(  
		                            strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
		            	jsonObject.accumulate(ErrorCode, -1);
						jsonObject.accumulate(Value, "身份证号码有误!");
						return jsonObject.toString();
		            }
		        } catch (NumberFormatException e) {
		            e.printStackTrace();
		        } catch (java.text.ParseException e) {
		            e.printStackTrace();
		        }
		        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
		        	jsonObject.accumulate(ErrorCode, -1);
					jsonObject.accumulate(Value, "身份证号码有误!");
					return jsonObject.toString();
		        }
		        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
		        	jsonObject.accumulate(ErrorCode, -1);
					jsonObject.accumulate(Value, "身份证号码有误!");
					return jsonObject.toString();
		        }  
		        // =====================(end)=====================  
		  
		        // ================ 地区码时候有效 ================  
		        Hashtable h = Utils.getAreaCode();
		        if (h.get(Ai.substring(0, 2)) == null) {
		        	jsonObject.accumulate(ErrorCode, -1);
					jsonObject.accumulate(Value, "身份证号码有误!");
					return jsonObject.toString();
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
		            	jsonObject.accumulate(ErrorCode, -1);
						jsonObject.accumulate(Value, "身份证号码有误!");
						return jsonObject.toString();
		            }
		        } else {
		            return "";  
		        }
		        
				if (realName.trim().length() > 50) {
					jsonObject.accumulate(ErrorCode, -1);
					jsonObject.accumulate(Value, "真实姓名不合法!");
					return jsonObject.toString();
				}
				
				String sql = "where fidentityNo='"+identityNo+"'";
				int count = this.adminService.getAllCount("Fuser", sql);
				LOGGER.info(CLASS_NAME + " validateIdentity,从数据库中查询身份证号，返回结果集count:{}", count);
				if(count >0){
					jsonObject.accumulate(ErrorCode, -1);
					jsonObject.accumulate(Value, "身份证号码已存在!");
					return jsonObject.toString();
				}
				
			IDCardVerifyUtil verifyUtil = new IDCardVerifyUtil();
			boolean isTrue = verifyUtil.isRealPerson(realName, identityNo);
			LOGGER.info(CLASS_NAME + " validateIdentity,调verifyUtil.isRealPerson进行实名认证，返回结果isTrue:{}", isTrue);
			if(!isTrue){
				jsonObject.accumulate(ErrorCode, -1);
				jsonObject.accumulate(Value, "您的姓名与身份证号有误，请核对!");
				return jsonObject.toString();
			}
			LOGGER.info(CLASS_NAME + " validateIdentity,实名认证通过");
			Fscore fscore = fuser.getFscore();
			Fuser fintrolUser = null;
			Fintrolinfo introlInfo = null;
			Fvirtualwallet fvirtualwallet = null;
			LOGGER.info(CLASS_NAME + " validateIdentity,从系统参数表查询实名认证配置auditSendCoin  begin");
			String[] auditSendCoin = this.systemArgsService.getValue("auditSendCoin").split("#");
			LOGGER.info(CLASS_NAME + " validateIdentity,从系统参数表查询实名认证配置auditSendCoin:{}", new Gson().toJson(auditSendCoin));
			int coinID = Integer.parseInt(auditSendCoin[0]);
			double coinQty = Double.valueOf(auditSendCoin[1]);
			if(!fscore.isFissend() && fuser.getfIntroUser_id() != null){
				fintrolUser = this.frontUserService.findById(fuser.getfIntroUser_id().getFid());
				fintrolUser.setfInvalidateIntroCount(fintrolUser.getfInvalidateIntroCount()+1);
				fscore.setFissend(true);
			}
			LOGGER.info(CLASS_NAME + " validateIdentity,coinQty:{}",coinQty);
			if(coinQty >0){
				fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), coinID);
				fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+coinQty);
				introlInfo = new Fintrolinfo();
				introlInfo.setFcreatetime(Utils.getTimestamp());
				introlInfo.setFiscny(false);
				introlInfo.setFqty(coinQty);
				introlInfo.setFuser(fuser);
				introlInfo.setFname(fvirtualwallet.getFvirtualcointype().getFname());
				introlInfo.setFtitle("实名认证成功，奖励"+fvirtualwallet.getFvirtualcointype().getFname()+coinQty+"个！");
			}
			fuser.setFidentityType(0) ;
			fuser.setFidentityNo(identityNo) ;
			fuser.setFrealName(realName) ;
			fuser.setFpostRealValidate(true) ;
			fuser.setFpostRealValidateTime(Utils.getTimestamp()) ;
			fuser.setFhasRealValidate(true);
			fuser.setFhasRealValidateTime(Utils.getTimestamp());
			fuser.setKyclevel(ValidateKycLevelEnum.IDENTITY_VALIDATE.getKey());
			fuser.setFisValid(true);
			try {
				String ip = getIpAddr(request) ;
				LOGGER.info(CLASS_NAME + " validateIdentity,修改用户信息,实名认证成功");
				this.frontUserService.updateFUser(fuser, getSession(request), LogTypeEnum.User_CERT,ip) ;
				this.userService.updateObj(fuser, fscore, fintrolUser, fvirtualwallet, introlInfo);
				//实名认证===APP首次送积分
				this.integralService.addUserIntegralFirst(IntegralTypeEnum.REALNAME_FIRST,fuser.getFid(),0);
				LOGGER.info(CLASS_NAME + " validateIdentity,修改用户钱包信息成功，增加了奖励金额");
				this.SetSession(fuser,request) ;
			} catch (Exception e) {
				js.accumulate(ErrorCode, -1);
				js.accumulate(Value, "证件号码已存在");
				return js.toString();
			}
			js.accumulate(Value, "证件验证成功");
			js.accumulate(ErrorCode, 0);
			return js.toString();
		
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	//查看实名认证信息
	public String ViewValidateIdentity(HttpServletRequest request) throws Exception {
		try {
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			jsonObject.accumulate(ErrorCode , 0) ;
			
			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;
			if(fuser.getFpostRealValidate()){
				jsonObject.accumulate("realName", fuser.getFrealName()) ;
				jsonObject.accumulate("identityNo", fuser.getFidentityNo()) ;
				jsonObject.accumulate("postRealValidate", fuser.getFpostRealValidate()) ;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				jsonObject.accumulate("postRealValidateTime", sdf.format(fuser.getFpostRealValidateTime())) ;
			}else{
				jsonObject.accumulate("realName", null) ;
				jsonObject.accumulate("identityNo",null) ;
				jsonObject.accumulate("postRealValidate", fuser.getFpostRealValidate()) ;
				jsonObject.accumulate("postRealValidateTime",null) ;
			}
			return jsonObject.toString() ;
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	//支付宝充值
	public String CnyRecharge(HttpServletRequest request) throws Exception {
		try {
			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;
			
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;

			String ZFBImages = this.constantMap.getString("ZFBImages") ;
			String rechargeZFB = this.constantMap.getString("rechargeZFB") ;
			String rechargeZFN = this.constantMap.getString("rechargeZFN") ;
			String ZFBRemark = this.constantMap.getString("ZFBRemark") ;
			
			jsonObject.accumulate("rechargeZFB" ,rechargeZFB) ;
			jsonObject.accumulate("rechargeZFN" , rechargeZFN) ;
			jsonObject.accumulate("ZFBRemark" , ZFBRemark) ;
			jsonObject.accumulate("userid" , fuser.getFid()) ;
			jsonObject.accumulate("ZFBImages" ,ZFBImages) ;

			jsonObject.accumulate(ErrorCode , 0) ;
			return jsonObject.toString() ;
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}

	
	//发送手机验证码
	public String SendValidateCode(
			HttpServletRequest request
			) throws Exception{
		try {
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			
			int type = Integer.parseInt(request.getParameter("type")) ;
			if(type!=MessageTypeEnum.BEGIN && type!=MessageTypeEnum.END){
				jsonObject.accumulate(ErrorCode , 1) ;
				jsonObject.accumulate(Value, "类型错误") ;
				return jsonObject.toString() ;
			}
			
			String phone = request.getParameter("phone") ;
			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;
			if(fuser!=null && fuser.isFisTelephoneBind()){
				phone = fuser.getFtelephone() ;
			}
			
			
			if(phone.matches("^\\d{10,14}$")){
				if(type == MessageTypeEnum.BANGDING_MOBILE){
					boolean isPhoneExists = this.frontUserService.isTelephoneExists(phone) ;
					if(isPhoneExists){
						jsonObject.accumulate(ErrorCode , 2) ;
						jsonObject.accumulate(Value, "手机号码已经存在") ;
						return jsonObject.toString() ;
					}
				}else{
					if(fuser.isFisTelephoneBind() == false ){
						jsonObject.accumulate(ErrorCode , 3) ;
						jsonObject.accumulate(Value, "该账号没有绑定手机号码") ;
						return jsonObject.toString() ;
					}
				}
				super.SendMessage(fuser,fuser.getFid(), "86" ,phone, type) ;
				
				jsonObject.accumulate(ErrorCode , 0) ;
				jsonObject.accumulate(Value, "验证码发送成功！") ;
				return jsonObject.toString() ;
				
			}else{
				jsonObject.accumulate(ErrorCode , 4) ;
				jsonObject.accumulate(Value, "手机号码格式错误") ;
				return jsonObject.toString() ;
			}
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	//绑定手机号码
	public String bindPhone(
			HttpServletRequest request
			) throws Exception{
		try {
			String areaCode = "86" ;
			
			String phone = request.getParameter("phone") ;
			String code = request.getParameter(ErrorCode) ;
			
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			
			if(!phone.matches("^\\d{10,14}$")){//手機格式不對
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "手机格式错误") ;
				return jsonObject.toString() ;
			}
			
			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;
			if(fuser.isFisTelephoneBind()){//已經綁定過手機了
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "您的账号已经绑定手机了") ;
				return jsonObject.toString() ;
			}
			
			String ip = getIpAddr(request) ;
			int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
			if(tel_limit<=0){
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "短信验证码错误次数超限，请稍后再试！") ;
				return jsonObject.toString() ;
			}
			
			
			if(validateMessageCode(fuser, areaCode, phone, MessageTypeEnum.BANGDING_MOBILE, code)){
				//判斷手機是否被綁定了
				List<Fuser> fusers = this.frontUserService.findUserByProperty("ftelephone", phone) ;
				if(fusers.size()>0){
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "该手机号码已经绑定到其他账号了") ;
					return jsonObject.toString() ;
				}
				
				fuser.setFareaCode(areaCode) ;
				fuser.setFtelephone(phone) ;
				fuser.setFisTelephoneBind(true) ;
				fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
				this.frontUserService.updateFuser(fuser) ;
				
				//推广数量+1
				Fuser introFuser = fuser.getfIntroUser_id() ;
				Fintrolinfo introlInfo = null;
				Fscore fintrolscore = null;
				Fscore fscore = fuser.getFscore();
				this.frontUserService.updateFuser(introFuser,introlInfo,fintrolscore,fscore) ;
                //绑定手机===APP首次送积分
				this.integralService.addUserIntegralFirst(IntegralTypeEnum.PHONE_FIRST,fuser.getFid(),0);
				//成功
				this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
				jsonObject.accumulate(ErrorCode , 0) ;
				jsonObject.accumulate(Value, "绑定手机成功") ;
				return jsonObject.toString() ;
			}else{
				//手機驗證錯誤
				 this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "验证码错误") ;
				return jsonObject.toString() ;
			}
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	
	//解除绑定
	public String UnbindPhone(
			HttpServletRequest request
			) throws Exception{
		try {
			String phoneCode = request.getParameter("phoneCode") ;
			
			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			
			if(fuser.getFregType()==1){
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "您通过手机注册，不可解绑") ;
				return jsonObject.toString() ;
			}
			
			if(fuser.isFisTelephoneBind()){
				String ip = getIpAddr(request) ;
				int mobil_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
				if(mobil_limit<=0){
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "验证码错误次数超限，请稍后再试！") ;
					return jsonObject.toString() ;
				}else{
					if(super.validateMessageCode(fuser, "86", fuser.getFtelephone(), MessageTypeEnum.JIEBANG_MOBILE, phoneCode)){
						boolean flag = false ;
						try {
							fuser.setFisTelephoneBind(false) ;
							fuser.setFtelephone(null) ;
							fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
							this.frontUserService.updateFuser(fuser) ;
							
							//推广数量-1
							Fuser introFuser = fuser.getfIntroUser_id() ;
							if(introFuser!=null){
								introFuser = this.frontUserService.findById(fuser.getfIntroUser_id().getFid()) ;
								introFuser.setfInvalidateIntroCount(introFuser.getfInvalidateIntroCount()-1) ;
								this.frontUserService.updateFuser(introFuser) ;
							}
							
							flag = true ;
						} catch (Exception e) {
							e.printStackTrace();
						}
						if(flag){
							
							jsonObject.accumulate(ErrorCode , 0) ;
							jsonObject.accumulate(Value, "解除绑定成功！") ;
							return jsonObject.toString() ;
						}else{
							jsonObject.accumulate(ErrorCode , -1) ;
							jsonObject.accumulate(Value, "网络错误，请稍后再试！") ;
							return jsonObject.toString() ;
						}
					}else{
						this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
						jsonObject.accumulate(ErrorCode , -1) ;
						jsonObject.accumulate(Value, "验证码错误") ;
						return jsonObject.toString() ;
					}
				}
			}else{
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "您的账号没有绑定手机号码") ;
				return jsonObject.toString() ;
			}
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
		
	}
	
	//提现银行列表
	public String GetWithdrawBankList(
			HttpServletRequest request
			) throws Exception {
		try {
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;

			JSONArray jsonArray = new JSONArray() ;
			for(int i = BankTypeEnum.GH; i<= BankTypeEnum.QT; i++){
				JSONObject item = new JSONObject() ;
				item.accumulate("id", i) ;
				item.accumulate("name", BankTypeEnum.getEnumString(i)) ;
				jsonArray.add(item) ;
				
			}
			jsonObject.accumulate("list", jsonArray) ;
			return jsonObject.toString() ;
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
//	http://test.ruizton.com:8080/user/updateOutAddress.html?random=4&account=3333&address=55555&cnyOutType=1&openBankType=1&phoneCode=333333&totpCode=0&type=1
	//设置提现银行卡信息
	public String SetWithdrawCnyBankInfo(
			HttpServletRequest request
			) throws Exception {
            Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;
			try {
				String account = request.getParameter("account");
				String phoneCode = request.getParameter("phoneCode") ;
				int openBankType = Integer.parseInt(request.getParameter("openBankType"));
				String address = request.getParameter("address") ;
				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(Result, true) ;

				boolean isTelephoneBind = fuser.isFisTelephoneBind() ;
				if( !isTelephoneBind){
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "没有绑定手机") ;
					return jsonObject.toString() ;
				}
				address = HtmlUtils.htmlEscape(address);
				
				String ip = getIpAddr(request) ;
				int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
				
				if(fuser.isFisTelephoneBind()){
					if(tel_limit<=0){
						jsonObject.accumulate(ErrorCode , -1) ;
						jsonObject.accumulate(Value, "短信验证码错误次数超限，请稍后再试！") ;
						return jsonObject.toString() ;
					}else{
						if(!validateMessageCode(fuser, "86", fuser.getFtelephone(), MessageTypeEnum.CNY_ACCOUNT_WITHDRAW, phoneCode)){
							//手機驗證錯誤
							this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
							jsonObject.accumulate(ErrorCode , -1) ;
							jsonObject.accumulate(Value, "手机验证码错误") ;
							return jsonObject.toString() ;

						}else{
							this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
						}
					}
				}

				int count = this.utilsService.count(" where fuser.fid="+fuser.getFid()+" and fbankType="+openBankType+" and fbankNumber='"+account+"' and fstatus="+BankInfoWithdrawStatusEnum.NORMAL_VALUE+" ", FbankinfoWithdraw.class) ;
				if(count>0){
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate(APP_API_Controller.Value, "银行卡号码已经存在") ;
					return jsonObject.toString();
				}

				count = this.utilsService.count(" where fuser.fid="+fuser.getFid()+" and fstatus="+BankInfoWithdrawStatusEnum.NORMAL_VALUE+" ", FbankinfoWithdraw.class) ;
				if(count>4){
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate(APP_API_Controller.Value, "'绑定银行卡数量已超过5张限制！") ;
					return jsonObject.toString();
				}

				//成功
				try {
					FbankinfoWithdraw fbankinfoWithdraw = new FbankinfoWithdraw() ;
					fbankinfoWithdraw.setFbankNumber(account) ;
					fbankinfoWithdraw.setFbankType(openBankType) ;
					fbankinfoWithdraw.setFcreateTime(Utils.getTimestamp()) ;
					fbankinfoWithdraw.setFname(BankTypeEnum.getEnumString(openBankType)) ;
					fbankinfoWithdraw.setFstatus(BankInfoStatusEnum.NORMAL_VALUE) ;
					fbankinfoWithdraw.setFaddress(address);
					fbankinfoWithdraw.setFuser(fuser) ;
					this.frontUserService.updateBankInfoWithdraw(fbankinfoWithdraw) ;

					//绑定银行卡===APP首次送积分
					this.integralService.addUserIntegralFirst(IntegralTypeEnum.INFOBANK_FIRST,fuser.getFid(),0);
					LOGGER.info(CLASS_NAME + " validateIdentity,修改用户钱包信息成功，增加了奖励金额");

					jsonObject.accumulate(ErrorCode , 0) ;
					jsonObject.accumulate(Value, "提现银行卡信息设置成功") ;
					return jsonObject.toString() ;
				
				} catch (Exception e) {
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "网络错误，请稍后再试") ;
					return jsonObject.toString() ;
				}finally {
					this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.CNY_ACCOUNT_WITHDRAW);
				}
			} catch (Exception e) {
				e.printStackTrace() ;
				return ApiConstant.getUnknownError(e) ;
			}
			
	}
	
	
	
	//人民币提现
	public String WithDrawCny(
			HttpServletRequest request
			) throws Exception {
		
			try {
				int withdrawBank = Integer.parseInt(request.getParameter("withdrawBank")) ;
				String tradePwd = request.getParameter("tradePwd") ;
				double withdrawBalance = Utils.getDouble(Double.parseDouble(request.getParameter("withdrawBalance")), 2) ;
				String phoneCode = request.getParameter("phoneCode") ;

				JSONObject jsonObject = new JSONObject() ;
				jsonObject.accumulate(Result, true) ;
				//最大提现人民币
				double max_double = Double.parseDouble(this.constantMap.getString("maxwithdrawcny")) ;
				double min_double = Double.parseDouble(this.constantMap.getString("minwithdrawcny")) ;
				
//				if(withdrawBalance <= 0 || withdrawBalance%100 != 0){
//					jsonObject.accumulate(ErrorCode , -1) ;
//					jsonObject.accumulate(Value, "提现金额必须为100的整数倍") ;
//					return jsonObject.toString() ;
//				}
				
				if(withdrawBalance<min_double){
					//提现金额不能小于10
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "最小提现金额：￥"+min_double) ;
					return jsonObject.toString() ;
				}
				
				if(withdrawBalance>max_double){
					//提现金额不能大于指定值
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "最大提现金额￥："+max_double) ;
					return jsonObject.toString() ;
				}
				
				
				Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;


				/*if(!ValidateKycLevelEnum.validateKYC2(fuser.getKyclevel())){
					return ApiConstant.getKYC2ValidateError();
				}*/

				Fvirtualwallet fwallet = this.frontUserService.findWalletByUser(fuser.getFid()) ;
				FbankinfoWithdraw fbankinfoWithdraw = this.frontUserService.findFbankinfoWithdraw(withdrawBank) ;
				if(fbankinfoWithdraw == null ){
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "请先设置提现银行卡信息") ;
					return jsonObject.toString() ;
				}
				
				
				if(fwallet.getFtotal()<withdrawBalance){
					//资金不足
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "余额不足") ;
					return jsonObject.toString() ;
				}
				
				if(fuser.getFtradePassword()==null){
					//没有设置交易密码
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "交易密码错误") ;
					return jsonObject.toString() ;
				}
				
				if( !fuser.isFisTelephoneBind()){
					//没有绑定谷歌或者手机
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "请先绑定手机") ;
					return jsonObject.toString() ;
				}
				
				
				String ip = getIpAddr(request) ;
				if(fuser.getFtradePassword()!=null){
					int trade_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
					if(trade_limit<=0){
						jsonObject.accumulate(ErrorCode , -1) ;
						jsonObject.accumulate(Value, "交易密码错误超限，请稍后再试！") ;
						return jsonObject.toString() ;
					}else{
						boolean flag = fuser.getFtradePassword().equals(Utils.MD5(tradePwd, fuser.getSalt())) ;
						if(!flag){
							this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
							jsonObject.accumulate(ErrorCode , -1) ;
							jsonObject.accumulate(Value, "交易密码错误") ;
							return jsonObject.toString() ;
						}else if(trade_limit<new Constant().ErrorCountLimit){
							this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
						}
					}
				}
				
				
				if(fuser.isFisTelephoneBind()){
					int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
					if(tel_limit<=0){
						jsonObject.accumulate(ErrorCode , -1) ;
						jsonObject.accumulate(Value, "手机验证码错误超限，请稍后再试") ;
						return jsonObject.toString() ;
					}else{
						boolean flag = validateMessageCode(fuser, "86", fuser.getFtelephone(), MessageTypeEnum.CNY_TIXIAN, phoneCode) ;
							
						if(!flag){
							this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
							jsonObject.accumulate(ErrorCode , -1) ;
							jsonObject.accumulate(Value, "手机验证码错误") ;
							return jsonObject.toString() ;
						}else if(tel_limit<new Constant().ErrorCountLimit){
							this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
						}
					}
				}
				
				int time = this.frontAccountService.getTodayCnyWithdrawTimes(fuser) ;
				if(time>=new Constant().CnyWithdrawTimes){
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "每天只能提现"+new Constant().CnyWithdrawTimes+"次，您已经超限") ;
					return jsonObject.toString() ;
				}



				int kyc_level = 0;
				if(ValidateKycLevelEnum.validateKYC2(fuser.getKyclevel()))
				{ kyc_level = 2; }
				else if(ValidateKycLevelEnum.validateKYC1(fuser.getKyclevel()) )
				{ kyc_level = 1;
				}else{
					jsonObject.accumulate(CODE, -1) ;
					jsonObject.accumulate(APP_API_Controller.Value, "您还未完成KYC1认证,不能交易");
					return jsonObject.toString() ;
				}


				jsonObject.accumulate("kyc_level", kyc_level);
				long day_count;//每日提现总次数
				double day_limit;//每日提现额度
				double month_limit;//每月提现额度
				Fvirtualcointype fvirtualcointype= 	virtualCoinService.findByProperty("fShortName","CNY").get(0);
				//如果有kyc2权限,通过kyc2配置权限判断用户操作
				if(ValidateKycLevelEnum.validateKYC2(fuser.getKyclevel())){
					//判断单笔提现额度
					if(fvirtualcointype.getKyc2_single_limit()<withdrawBalance){
						jsonObject.accumulate(CODE, -2) ;
						jsonObject.accumulate(APP_API_Controller.Value,"单笔提现额度超出限制" );
						return jsonObject.toString() ;
					}
					//获取每日提现总次数
					day_count=   capitaloperationService.getDayCount(fuser.getFid(),CapitalOperationTypeEnum.RMB_OUT);

					if(fvirtualcointype.getKyc2_day_count()<=day_count){
						jsonObject.accumulate(CODE, -2) ;
						jsonObject.accumulate(APP_API_Controller.Value,"你当日提现次数已用完" );
						return jsonObject.toString() ;

					}
					//判断每日提现额度是否超
					day_limit=capitaloperationService.getDayLimit(fuser.getFid(),CapitalOperationTypeEnum.RMB_OUT);

					if(fvirtualcointype.getKyc2_day_limit()<Utils.add(day_limit,withdrawBalance)){

						jsonObject.accumulate(CODE, -2) ;
						jsonObject.accumulate(APP_API_Controller.Value,"你当前提现额度已用完" );
						return jsonObject.toString() ;

					}
					//判断每月提现额度是否超

					month_limit=capitaloperationService.getMonthLimit(fuser.getFid(),CapitalOperationTypeEnum.RMB_OUT);
					if (fvirtualcointype.getKyc2_month_limit()<Utils.add(month_limit,withdrawBalance)){

						jsonObject.accumulate(CODE, -2) ;
						jsonObject.accumulate(APP_API_Controller.Value,"您当月提现额度已用完" );
						return jsonObject.toString() ;
					}
				}else {

					//如果有kyc1权限,通过kyc1配置权限判断用户操作
					if (ValidateKycLevelEnum.validateKYC1(fuser.getKyclevel())) {
						//判断单笔提现额度
						if (fvirtualcointype.getKyc1_single_limit() < withdrawBalance) {

							jsonObject.accumulate(CODE, -2);
							jsonObject.accumulate(APP_API_Controller.Value, "单笔提现额度超出限制");
							return jsonObject.toString();
						}
						//获取每日提现总次数
						day_count = capitaloperationService.getDayCount(fuser.getFid(), CapitalOperationTypeEnum.RMB_OUT);

						if (fvirtualcointype.getKyc1_day_count() <=day_count) {

							jsonObject.accumulate(CODE, -2);
							jsonObject.accumulate(APP_API_Controller.Value, "你当日提现次数已用完");
							return jsonObject.toString();

						}
						//判断每日提现额度是否超
						day_limit = capitaloperationService.getDayLimit(fuser.getFid(), CapitalOperationTypeEnum.RMB_OUT);

						if (fvirtualcointype.getKyc1_day_limit() < Utils.add(day_limit,withdrawBalance)) {

							jsonObject.accumulate(CODE, -2);
							jsonObject.accumulate(APP_API_Controller.Value, "你当前提现额度已用完");
							return jsonObject.toString();

						}
						//判断每月提现额度是否超

						month_limit = capitaloperationService.getMonthLimit(fuser.getFid(), CapitalOperationTypeEnum.RMB_OUT);
						if (fvirtualcointype.getKyc1_month_limit() <Utils.add( month_limit,withdrawBalance)) {

							jsonObject.accumulate(CODE, -2);
							jsonObject.accumulate(APP_API_Controller.Value, "您当月提现额度已用完");
							return jsonObject.toString();
						}
					}

				}


				boolean withdraw = false ;
				try {
					int cnyfeeLimit = Integer.valueOf(this.constantMap.get("cnyfeeLimit").toString());
					withdraw = this.frontAccountService.updateWithdrawCNY(withdrawBalance, fuser,fbankinfoWithdraw, cnyfeeLimit) ;
				} catch (Exception e) {
					e.printStackTrace() ;
				}finally {
					this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.CNY_TIXIAN);
				}
				
				if(withdraw){
					jsonObject.accumulate(ErrorCode , 0) ;
					jsonObject.accumulate(Value, "提现请求成功，请耐心等待管理员处理") ;

					jsonObject.accumulate("bankName",fbankinfoWithdraw.getFaddress());
					jsonObject.accumulate("bankNumber",Utils.getAccount(fbankinfoWithdraw.getFbankNumber()));

					return jsonObject.toString() ;
				}else{
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "网络错误，请稍后再试") ;
					return jsonObject.toString() ;
				}
			} catch (Exception e) {
				e.printStackTrace() ;
				return ApiConstant.getUnknownError(e) ;
			}
			
	}
	
	//获取虚拟币提现地址
	public String GetWithdrawBtcAddress(
			HttpServletRequest request
			) throws Exception {
		try {
			Integer coinId = Integer.parseInt(request.getParameter("coinId")) ;
			
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			
			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;
			Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(coinId) ;
			List<FvirtualaddressWithdraw> fvirtualaddressWithdraws = this.utilsService.list(0, 0, " where fvirtualcointype.fid="+fvirtualcointype.getFid()+" and fuser.fid="+fuser.getFid()+" ", false, FvirtualaddressWithdraw.class) ;
			JSONArray jsonArray = new JSONArray() ;
			for (FvirtualaddressWithdraw fvirtualaddressWithdraw : fvirtualaddressWithdraws) {
				JSONObject item = new JSONObject() ;
				item.accumulate("id", fvirtualaddressWithdraw.getFid()) ;
				item.accumulate("address",  fvirtualaddressWithdraw.getFadderess()) ;
				jsonArray.add(item) ;
			}
			jsonObject.accumulate("list", jsonArray) ;
			
			String xx = "where fvirtualcointype.fid="+fvirtualcointype.getFid()+" and (ffee >0 or flevel=5)  order by flevel asc";
			List<Fwithdrawfees> withdrawFees = this.withdrawFeesService.list(0, 0, xx, false);
			
			jsonArray = new JSONArray() ;
			for (int i = 0; i < withdrawFees.size(); i++) {
				Fwithdrawfees fee = withdrawFees.get(i) ;
				JSONObject item = new JSONObject() ;
				item.accumulate("level", fee.getFlevel()) ;
				item.accumulate("fee", Utils.decimalFormat(fee.getFfee(),4)) ;
				
				jsonArray.add(item) ;
			}
			jsonObject.accumulate("fee_list", jsonArray) ;

			//获取虚拟币提币最大、最小提币数
			double max_double = fvirtualcointype.getFmaxqty();
			double min_double = fvirtualcointype.getFminqty();
			jsonObject.accumulate("minAmount",Utils.decimalFormat(min_double,4));
			jsonObject.accumulate("maxAmount",Utils.decimalFormat(max_double,4));
			return jsonObject.toString()  ;
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	//设置虚拟币提现地址
	public String SetWithdrawBtcAddr(
			HttpServletRequest request
			) throws Exception{
		
		try {
			String phoneCode = request.getParameter("phoneCode");
			int symbol  = Integer.parseInt(request.getParameter("symbol"));
			String withdrawAddr  = request.getParameter("withdrawAddr");

			LOG.i(this,"请求参数："+phoneCode+" "+symbol+" "+withdrawAddr);

			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;
			if( !fuser.isFisTelephoneBind()){
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "请先绑定手机") ;
				return jsonObject.toString() ;
			}
			
			Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
			if(fvirtualcointype==null || fvirtualcointype.getFstatus()== VirtualCoinTypeStatusEnum.Abnormal){
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "该虚拟币不支持提现") ;
				return jsonObject.toString() ;
			}

			List<Fvirtualaddress> list = this.frontVirtualCoinService.findFvirtualaddress(fvirtualcointype,withdrawAddr);
			if (list != null && list.size() > 0){
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "提现地址不能是51平台的地址") ;
				return jsonObject.toString() ;
			}


			//判断同一同用户下提现地址是否存在，如果存在，不让添加
			if(frontVirtualCoinService.isExistsFaddress(withdrawAddr, fuser.getFid() )){
				jsonObject.accumulate(ErrorCode,-1) ;
				jsonObject.accumulate(Value,"不能重复添加相同的提现地址") ;
				return jsonObject.toString();
			}

			String ip = getIpAddr(request) ;
			
			if(fuser.isFisTelephoneBind()){
				int mobil_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
				if(mobil_limit<=0){
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "手机验证码错误超限，请稍后再试！") ;
					return jsonObject.toString() ;
				}else if(!validateMessageCode(fuser, "86", fuser.getFtelephone(), MessageTypeEnum.VIRTUAL_WITHDRAW_ACCOUNT, phoneCode)){
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "短信验证码错误！") ;
					return jsonObject.toString() ;
				}else if(mobil_limit<new Constant().ErrorCountLimit){
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
				}
			}

			if (!CoinType.ANS.equals(fvirtualcointype.getfShortName().toUpperCase())){
				// 钱包校验地址是否合法
				BTCMessage btcMsg = new BTCMessage();
				btcMsg.setACCESS_KEY(fvirtualcointype.getFaccess_key());
				btcMsg.setSECRET_KEY(fvirtualcointype.getFsecrt_key());
				btcMsg.setIP(fvirtualcointype.getFip());
				btcMsg.setPORT(fvirtualcointype.getFport());
				btcMsg.setCOIN_TYPE(fvirtualcointype.getfShortName().toUpperCase());
				try{
					Boolean flag;
					if (fvirtualcointype.isFisEth()){
						ETHUtils ethUtils = new ETHUtils(btcMsg);
						flag = ethUtils.validateaddress(withdrawAddr);
						// eth
					} else {
						// btc ltc zec
						BTCUtils btcUtils = new BTCUtils(btcMsg);
						flag = btcUtils.validateaddress(withdrawAddr);
					}
					if (!flag){
						return ApiConstant.getRequestError("提现地址非法");
					}
				} catch (Exception ex){
					LOG.e(this,ex.getMessage(),ex);
					return ApiConstant.getRequestError("交易提现地址失败,请联系客服");
				}
			}
			
			FvirtualaddressWithdraw fvirtualaddressWithdraw = new FvirtualaddressWithdraw() ;
			fvirtualaddressWithdraw.setFadderess(withdrawAddr) ;
			fvirtualaddressWithdraw.setFcreateTime(Utils.getTimestamp());

			fvirtualaddressWithdraw.setFuser(fuser) ;
			fvirtualaddressWithdraw.setFvirtualcointype(fvirtualcointype) ;
			try {
				this.frontVirtualCoinService.updateFvirtualaddressWithdraw(fvirtualaddressWithdraw) ;
				jsonObject.accumulate(ErrorCode , 0) ;
				jsonObject.accumulate(Value, "地址设置成功") ;
				return jsonObject.toString() ;
			} catch (Exception e) {
				e.printStackTrace();
				jsonObject.accumulate(ErrorCode, -1);
				jsonObject.accumulate(Value, "网络错误，请稍后再试") ;
				return jsonObject.toString() ;
			}finally {
				this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.VIRTUAL_WITHDRAW_ACCOUNT);
			}
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
 		
	}
	
	//虚拟币提现
	public String WithdrawBtcSubmit(
			HttpServletRequest request
			) throws Exception{
		
		try {
			int virtualaddres = Integer.parseInt(request.getParameter("virtualaddres")) ;
			double withdrawAmount = Utils.getDouble(Double.parseDouble(request.getParameter("withdrawAmount")), 4) ;
			String tradePwd = request.getParameter("tradePwd").trim();
			String phoneCode= request.getParameter("phoneCode") ;
			int symbol = Integer.parseInt(request.getParameter("symbol"));
			int level = Integer.parseInt(request.getParameter("level"));
			
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;

			int kyc_level = 0;
			if(ValidateKycLevelEnum.validateKYC2(fuser.getKyclevel())) {
				kyc_level = 2;
			}else if(ValidateKycLevelEnum.validateKYC1(fuser.getKyclevel()) ) {
				kyc_level = 1;
			}else{
				jsonObject.accumulate(CODE, -1) ;
				jsonObject.accumulate(APP_API_Controller.Value, "还未进行KYC认证");
				return jsonObject.toString() ;
			}

			jsonObject.accumulate("kyc_level", kyc_level);

			Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
			if(fvirtualcointype==null || !fvirtualcointype.isFIsWithDraw() || fvirtualcointype.getFstatus()== VirtualCoinTypeStatusEnum.Abnormal){
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "该虚拟币不支持提现") ;
				return jsonObject.toString() ;
			}
			Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid()) ;
			FvirtualaddressWithdraw fvirtualaddressWithdraw = this.frontVirtualCoinService.findFvirtualaddressWithdraw(virtualaddres) ;
			if(fvirtualaddressWithdraw==null ){
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "请设置提现地址") ;
				return jsonObject.toString() ;
			}
			if(  !fuser.isFisTelephoneBind()){
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "请先绑定手机") ;
				return jsonObject.toString() ;
			}
			
			String ip = getIpAddr(request) ;
			int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE ) ;
			int mobil_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
			
			if(fuser.getFtradePassword()==null){
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "交易密码错误") ;
				return jsonObject.toString() ;
			}else{
				int trade_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
				if(trade_limit<=0){
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "交易密码错误次数超限，请稍后再试") ;
					return jsonObject.toString() ;
				}
				
				if(!fuser.getFtradePassword().equals(Utils.MD5(tradePwd, fuser.getSalt()))){
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "交易密码错误") ;
					return jsonObject.toString() ;
				}else if(trade_limit<new Constant().ErrorCountLimit){
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
				}
			}
			
			if(fuser.isFisTelephoneBind()){
				if(mobil_limit<=0){
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "短信验证码错误超限，请稍后再试") ;
					return jsonObject.toString() ;
				}
				
				boolean mobilValidate = validateMessageCode(fuser, "86", fuser.getFtelephone(), MessageTypeEnum.VIRTUAL_TIXIAN, phoneCode) ;
				if(!mobilValidate){
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "短信验证码错误") ;
					return jsonObject.toString() ;
				}else if(mobil_limit<new Constant().ErrorCountLimit){
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
				}
			}
			
			//最大提现人民币
			double max_double = fvirtualcointype.getFmaxqty();
			double min_double = fvirtualcointype.getFminqty();
			
			if(withdrawAmount<min_double){
				//提现金额不能小于10
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "最小提现数量："+min_double) ;
				return jsonObject.toString() ;
			}
			
			if(withdrawAmount>max_double){
				//提现金额不能大于指定值
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "最大提现数量："+max_double) ;
				return jsonObject.toString() ;
			}
			
			//余额不足
			if(fvirtualwallet.getFtotal()<withdrawAmount){
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "余额不足") ;
				return jsonObject.toString() ;
			}
			
			if(fvirtualaddressWithdraw==null ){
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "请先设置提现地址") ;
				return jsonObject.toString() ;
			}


			int time = this.frontAccountService.getTodayVirtualCoinWithdrawTimes(fuser) ;
			if(time>=new Constant().VirtualCoinWithdrawTimes){
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "每天最多提现"+new Constant().VirtualCoinWithdrawTimes+"次，您已经超限") ;
				return jsonObject.toString() ;
			}
			
			String sql = "where flevel="+level+" and fvirtualcointype.fid="+symbol;
			List<Fwithdrawfees> alls = this.withdrawFeesService.list(0, 0, sql, false);
			if(alls == null || alls.size() ==0){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "手续费异常") ;
			}
			double ffees = alls.get(0).getFfee();
			if(ffees ==0 && alls.get(0).getFlevel() != 5){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "手续费有误") ;
				return jsonObject.toString();
			}


			//-----------------------------kyc判断  开始---------------------------------------
			//查询当日已提币额度
			Map<String, String> userNum_day_map = this.frontVirtualCoinService.queryUserWithNum (fuser.getFid(), DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay), symbol);
			if (userNum_day_map == null || userNum_day_map.get("amount") == null || userNum_day_map.get("num") == null) {
				LOGGER.info("查询用户当日的提币次数、总额度信息，返回结果为空");
				jsonObject.accumulate(CODE, -1) ;
				jsonObject.accumulate(APP_API_Controller.Value, "系统异常");
				return jsonObject.toString() ;
			}

			BigDecimal day_quota = new BigDecimal(userNum_day_map.get("amount"));
			int day_num = Integer.valueOf(userNum_day_map.get("num"));

			//根据kyc等级，比对kyc权限配置
			int compare_kyc_day_count = 0;
			double compare_kyc_single_limit = 0;
			double compare_kyc_day_limit = 0;
			double compare_kyc_month_limit = 0;
			if(kyc_level == 1) {
				compare_kyc_day_count = fvirtualcointype.getKyc1_day_count();
				compare_kyc_single_limit = fvirtualcointype.getKyc1_single_limit();
				compare_kyc_day_limit = fvirtualcointype.getKyc1_day_limit();
				compare_kyc_month_limit = fvirtualcointype.getKyc1_month_limit();
			}else{
				compare_kyc_day_count = fvirtualcointype.getKyc2_day_count();
				compare_kyc_single_limit = fvirtualcointype.getKyc2_single_limit();
				compare_kyc_day_limit = fvirtualcointype.getKyc2_day_limit();
				compare_kyc_month_limit = fvirtualcointype.getKyc2_month_limit();
			}

			//判断当日提币是否超过次数
			if ((day_num+1) > compare_kyc_day_count) {
				jsonObject.accumulate(CODE, -2);
				jsonObject.accumulate(APP_API_Controller.Value, "您当日提现次数已用完");
				return jsonObject.toString();
			}

			//判断单笔额度是否超限
			if (withdrawAmount > compare_kyc_single_limit) {
				jsonObject.accumulate(CODE, -2);
				jsonObject.accumulate(APP_API_Controller.Value, "单笔提现额度超出限制");
				return jsonObject.toString();
			}

			//判断当日额度是否超限
			if(day_quota.add(new BigDecimal(withdrawAmount)).doubleValue() > compare_kyc_day_limit   ) {
				jsonObject.accumulate(CODE, -2);
				jsonObject.accumulate(APP_API_Controller.Value, "您当前提现额度已用完");
				return jsonObject.toString();
			}


			//查询当月已提币额度
			Map<String, String> userNum_month_map = this.frontVirtualCoinService.queryUserWithNum (fuser.getFid(), DateHelper.getFirstDayByMonth(), DateHelper.getLastDayByMonth(), symbol);
			if (userNum_month_map == null || userNum_month_map.get("amount") == null) {
				LOGGER.info("查询用户当月的提币次数、总额度信息，返回结果为空");
				jsonObject.accumulate(CODE, -1) ;
				jsonObject.accumulate(APP_API_Controller.Value, "系统异常");
				return jsonObject.toString() ;
			}

			BigDecimal month_quota = new BigDecimal(userNum_month_map.get("amount"));

			//判断当月额度是否超限
			if(month_quota.add(new BigDecimal(withdrawAmount)).doubleValue() > compare_kyc_month_limit   ) {
				jsonObject.accumulate(CODE, -2);
				jsonObject.accumulate(APP_API_Controller.Value, "您当月提现(币)额度已用完");
				return jsonObject.toString();
			}


			//-----------------------------kyc判断  结束---------------------------------------
			
			try{
				this.frontVirtualCoinService.updateWithdrawBtc(fvirtualaddressWithdraw,fvirtualcointype, fvirtualwallet, withdrawAmount,ffees, fuser, false , null) ;
				jsonObject.accumulate(ErrorCode , 0) ;
				jsonObject.accumulate(Value, "提现请求成功，请耐心等待管理员处理") ;
				jsonObject.accumulate("virtualaddres",fvirtualaddressWithdraw.getFadderess());
				jsonObject.accumulate("remark",fvirtualaddressWithdraw.getFremark());

				return jsonObject.toString() ;
			}catch(Exception e){
				e.printStackTrace() ;
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "网络错误，请稍后再试") ;
				return jsonObject.toString() ;
			}finally{
				this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.VIRTUAL_TIXIAN);
			}
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
		
	}
	
	
	public String changePassword(
			HttpServletRequest request
			) throws Exception {
		
		try {
			Integer type = Integer.parseInt(request.getParameter("type")) ;
			String password1 = request.getParameter("password1") ;
			String password2 = request.getParameter("password2") ;
			String vcode = request.getParameter("vcode") ;
			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;



			
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			
			if(type == 1){
				if(super.validateMessageCode(fuser, "86", fuser.getFtelephone(), MessageTypeEnum.CHANGE_LOGINPWD, vcode) == false ){
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "短信验证码错误") ;
					return jsonObject.toString() ;
				}


				if(validateLoginPassword(password2)){
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "密码格式不正确") ;
					return jsonObject.toString() ;
				}
				
				//登录密码
				if(fuser.getFloginPassword().equals(Utils.MD5(password1,fuser.getSalt()))){
					fuser.setFloginPassword(Utils.MD5(password2,fuser.getSalt())) ;
					this.frontUserService.updateFuser(fuser) ;
					this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.CHANGE_LOGINPWD);
					jsonObject.accumulate(ErrorCode , 0) ;
					jsonObject.accumulate(Value, "登录密码修改成功") ;
					return jsonObject.toString() ;
				}else{
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "原始登录密码错误") ;
					return jsonObject.toString() ;
				}
			}else{
				if(super.validateMessageCode(fuser, "86", fuser.getFtelephone(), MessageTypeEnum.CHANGE_TRADEPWD, vcode) == false ){
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "短信验证码错误") ;
					return jsonObject.toString() ;
				}

				if(validateLoginPassword(password2)){
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "密码格式不正确") ;
					return jsonObject.toString() ;
				}
				
				//交易
				if(StringUtils.isBlank(fuser.getFtradePassword()) || Utils.MD5(password1,fuser.getSalt()).equals(fuser.getFtradePassword())){
					fuser.setFtradePassword(Utils.MD5(password2,fuser.getSalt())) ;
					this.frontUserService.updateFuser(fuser) ;
					this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.CHANGE_TRADEPWD);
					
					jsonObject.accumulate(ErrorCode , 0) ;
					jsonObject.accumulate(Value, "交易密码修改成功") ;
					return jsonObject.toString() ;
				}else{
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "原始交易密码错误") ;
					return jsonObject.toString() ;
				}
			}
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
	}
	
	
	//充值提现记录,type:1人民币充值，2人民币提现，3虚拟币充值，4虚拟币提现
	public String GetAllRecords(
			HttpServletRequest request
			) throws Exception {
		try {
			Integer type = Integer.parseInt(request.getParameter("type")) ;
			Integer currentPage = Integer.parseInt(request.getParameter("currentPage")) ;
			Integer symbol = 0 ;
			try {
				symbol = Integer.parseInt(request.getParameter("symbol")) ;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken) ;
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			
			
			try {
				if(type == 1){//人民币充值
					StringBuffer filter = new StringBuffer();
					filter.append("where fuser.fid="+fuser.getFid()+" \n");
					filter.append("and ftype ="+ CapitalOperationTypeEnum.RMB_IN+"\n");
					filter.append(" order by fid desc \n");
					List<Fcapitaloperation> list = this.capitaloperationService.list((currentPage-1)*maxResult, maxResult, filter.toString(), true);
					int totalCount = this.adminService.getAllCount("Fcapitaloperation", filter.toString());
					int totalPage = totalCount/maxResult+( (totalCount%maxResult)==0?0:1) ;
					
					
					jsonObject.accumulate("currentPage", currentPage) ;
					jsonObject.accumulate("totalPage", totalPage) ;
					
					JSONArray jsonArray = new JSONArray() ;
					
					for (int i = 0; i < list.size(); i++) {
						Fcapitaloperation fcapitaloperation = list.get(i) ;
						
						JSONObject item = new JSONObject() ;
						String bank_address = "";
						if(StringUtils.isNotBlank(fcapitaloperation.getfBank())) {
							bank_address = fcapitaloperation.getfBank();
						}
						if(StringUtils.isNotBlank(fcapitaloperation.getFaddress())) {
							bank_address += fcapitaloperation.getFaddress();
						}
						item.accumulate("bank", bank_address) ;
						item.accumulate("amount", fcapitaloperation.getFamount()) ;
						item.accumulate("date", Utils.dateFormat(fcapitaloperation.getFcreateTime())) ;
						item.accumulate("status", fcapitaloperation.getFstatus_s()) ;
						jsonArray.add(item) ;
					}
					
					jsonObject.accumulate("list", jsonArray) ;
				}else if(type ==2){//人民币提现

					StringBuffer filter = new StringBuffer();
					filter.append("where fuser.fid="+fuser.getFid()+" \n");
					filter.append("and ftype ="+ CapitalOperationTypeEnum.RMB_OUT+"\n");
					filter.append(" order by fid desc \n");
					List<Fcapitaloperation> list = this.capitaloperationService.list((currentPage-1)*maxResult, maxResult, filter.toString(), true);
					int totalCount = this.adminService.getAllCount("Fcapitaloperation", filter.toString());
					int totalPage = totalCount/maxResult+( (totalCount%maxResult)==0?0:1) ;
					
					
					jsonObject.accumulate("currentPage", currentPage) ;
					jsonObject.accumulate("totalPage", totalPage) ;
					
					JSONArray jsonArray = new JSONArray() ;
					
					for (int i = 0; i < list.size(); i++) {
						Fcapitaloperation fcapitaloperation = list.get(i) ;
						
						JSONObject item = new JSONObject() ;
						item.accumulate("bank", fcapitaloperation.getfBank()) ;
						item.accumulate("amount", fcapitaloperation.getFamount()) ;
						item.accumulate("date", Utils.dateFormat(fcapitaloperation.getFcreateTime())) ;
						item.accumulate("status", fcapitaloperation.getFstatus_s()) ;
						jsonArray.add(item) ;
					}
					
					jsonObject.accumulate("list", jsonArray) ;
				
				}else if(type ==3){
					//虚拟币充值
					Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
					StringBuffer filter = new StringBuffer();
					filter.append("where fuser.fid="+fuser.getFid()+" \n");
					filter.append("and ftype ="+ VirtualCapitalOperationTypeEnum.COIN_IN+"\n");
					filter.append(" order by fid desc \n");
					
					List<Fvirtualcaptualoperation> list =
							this.utilsService.list(
									(currentPage-1)*maxResult, maxResult,
									" where fvirtualcointype.fid="+fvirtualcointype.getFid()+" and fuser.fid="+fuser.getFid()+" and ftype="+ VirtualCapitalOperationTypeEnum.COIN_IN+" order by  fid desc",false, Fvirtualcaptualoperation.class) ;
					int totalCount = this.adminService.getAllCount("Fvirtualcaptualoperation", filter.toString());
					int totalPage = totalCount/maxResult+( (totalCount%maxResult)==0?0:1) ;
					
					
					jsonObject.accumulate("currentPage", currentPage) ;
					jsonObject.accumulate("totalPage", totalPage) ;
					
					JSONArray jsonArray = new JSONArray() ;
					
					for (int i = 0; i < list.size(); i++) {
						Fvirtualcaptualoperation fvirtualcaptualoperation = list.get(i) ;
						
						JSONObject item = new JSONObject() ;
						item.accumulate("bank", fvirtualcaptualoperation.getRecharge_virtual_address()) ;
						item.accumulate("amount", fvirtualcaptualoperation.getFamount()) ;
						item.accumulate("date", Utils.dateFormat(fvirtualcaptualoperation.getFcreateTime())) ;
						item.accumulate("status", fvirtualcaptualoperation.getFstatus_s()) ;
						jsonArray.add(item) ;
					}
					
					jsonObject.accumulate("list", jsonArray) ;
				}else if(type ==4){

					//虚拟币提现
					Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
					StringBuffer filter = new StringBuffer();
					filter.append("where fuser.fid="+fuser.getFid()+" \n");
					filter.append("and ftype ="+ VirtualCapitalOperationTypeEnum.COIN_OUT+"\n");
					filter.append(" order by fid desc \n");
					
					List<Fvirtualcaptualoperation> list =
							this.utilsService.list(
									 (currentPage-1)*maxResult, maxResult,
									" where fvirtualcointype.fid="+fvirtualcointype.getFid()+" and fuser.fid="+fuser.getFid()+" and ftype="+ VirtualCapitalOperationTypeEnum.COIN_OUT+" order by fid desc",true,Fvirtualcaptualoperation.class) ;
					int totalCount = this.adminService.getAllCount("Fvirtualcaptualoperation", filter.toString());
					int totalPage = totalCount/maxResult+( (totalCount%maxResult)==0?0:1) ;
					
					
					jsonObject.accumulate("currentPage", currentPage) ;
					jsonObject.accumulate("totalPage", totalPage) ;
					
					JSONArray jsonArray = new JSONArray() ;
					
					for (int i = 0; i < list.size(); i++) {
						Fvirtualcaptualoperation fvirtualcaptualoperation = list.get(i) ;
						
						JSONObject item = new JSONObject() ;
						item.accumulate("bank", fvirtualcaptualoperation.getWithdraw_virtual_address()) ;
						item.accumulate("amount", fvirtualcaptualoperation.getFamount()) ;
						item.accumulate("date", Utils.dateFormat(fvirtualcaptualoperation.getFcreateTime())) ;
						item.accumulate("status", fvirtualcaptualoperation.getFstatus_s()) ;
						jsonArray.add(item) ;
					}
					
					jsonObject.accumulate("list", jsonArray) ;
				
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			return jsonObject.toString() ;
		} catch (Exception e) {
			e.printStackTrace() ;
			return ApiConstant.getUnknownError(e) ;
		}
		
	}
	
	// 发送手机验证码
	public String SendMessageCode(HttpServletRequest request) throws Exception {
		try {
			int type = Integer.parseInt(request.getParameter("type"));
			String phone = request.getParameter("phone");
			String ip = getIpAddr(request) ;
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate(Result, true);

			boolean isLogin = this.realTimeData.isAppLogin(this.curLoginToken, false);

			if(type<= MessageTypeEnum.BEGIN || type>= MessageTypeEnum.END){
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "类型错误") ;
				return  jsonObject.toString() ; 
			}
			
			if( (type== MessageTypeEnum.FIND_PASSWORD || type == MessageTypeEnum.REG_CODE  )  ){
				if(phone.matches(Constant.PhoneReg) == false ){
					jsonObject.accumulate(ErrorCode , -1) ;
					jsonObject.accumulate(Value, "手机格式错误") ;
					return  jsonObject.toString() ; 
				}
				
			}else if(isLogin == false ){
				jsonObject.accumulate(ErrorCode , -1) ;
				jsonObject.accumulate(Value, "请登录后继续操作") ;
				return  jsonObject.toString() ; 
			}

			Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken);

			//注册，绑定手机
			if( ( type == MessageTypeEnum.REG_CODE ||type == MessageTypeEnum.BANGDING_MOBILE )  ){
				boolean isPhoneExists = this.frontUserService.isTelephoneExists(phone);
				if (isPhoneExists) {
					jsonObject.accumulate(ErrorCode, -1);
					jsonObject.accumulate(Value, "手机号码已经存在");
					return jsonObject.toString();
				}
			}else{
				if (fuser != null && fuser.isFisTelephoneBind()) {
					phone = fuser.getFtelephone();
				}
			}

			if(StringUtils.isBlank(phone)){
				jsonObject.accumulate(ErrorCode, -1);
				jsonObject.accumulate(Value, "手机号码为空！");
				return jsonObject.toString();
			}
			//找回密码
			boolean isPhoneExists = this.frontUserService.isTelephoneExists(phone);
			if(type== MessageTypeEnum.FIND_PASSWORD ){
				if (isPhoneExists == false ) {
					jsonObject.accumulate(ErrorCode, -1);
					jsonObject.accumulate(Value, "该账户不存在");
					return jsonObject.toString();
				}
			}
			
			
			
			if (phone.matches(Constant.PhoneReg)) {
				if (type == MessageTypeEnum.BANGDING_MOBILE) {
					if (isPhoneExists) {
						jsonObject.accumulate(ErrorCode, 2);
						jsonObject.accumulate(Value, "手机号码已经存在");
						return jsonObject.toString();
					}
				} else {
					if (isLogin&&fuser.isFisTelephoneBind() == false) {
						jsonObject.accumulate(ErrorCode, 3);
						jsonObject.accumulate(Value, "该账号没有绑定手机号码");
						return jsonObject.toString();
					}
				}
				if(isLogin){
					super.SendMessage(fuser, fuser.getFid(), "86", phone, type);
				}else{
					super.SendMessage(ip, "86", phone, type) ;
				}
				
				jsonObject.accumulate(ErrorCode, 0);
				jsonObject.accumulate(Value, "验证码发送成功！");
				jsonObject.accumulate("phone",Utils.getAccount(phone));

				try{
					if(Utils.isDevEnvironment()){
						String key = isLogin?fuser.getFid()+"_"+type:ip+"_"+phone+"_"+type;
						MessageValidate messageValidate = this.validateMap.getMessageMap(key);
						if(messageValidate != null){
							jsonObject.accumulate("phoneCode",messageValidate.getCode());
						}
					}
				}catch (Exception e){}


				String jdbcUrl = Configuration.getValue("");
				return jsonObject.toString();

			} else {
				jsonObject.accumulate(ErrorCode, -1);
				jsonObject.accumulate(Value, "手机号码格式错误");
				return jsonObject.toString();
			}
		} catch (Exception e) {

			return ApiConstant.getUnknownError(e);
		}
	}

	/**
	 * 通过邮件发送验证码
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String SendMailCode(HttpServletRequest request) throws Exception {
		try {
			String email = request.getParameter("email").toLowerCase() ;
			int type = Integer.parseInt(request.getParameter("type")) ;
			
			String ip = getIpAddr(request) ;
			//注册类型免登陆可以发送
			JSONObject jsonObject = new JSONObject() ;
			
			if(type<= SendMailTypeEnum.BEGIN||type>= SendMailTypeEnum.END){
				jsonObject.accumulate(ErrorCode, -1);
				jsonObject.accumulate(Result, false) ;
				jsonObject.accumulate(Value, "非法提交");
				return jsonObject.toString() ;
			}
			
			if(email.matches(Constant.EmailReg) == false ){
				jsonObject.accumulate(ErrorCode, -1);
				jsonObject.accumulate(Result, false) ;
				jsonObject.accumulate(Value, "邮箱格式错误");
				return jsonObject.toString() ;
			}
			
			boolean flag = this.frontUserService.isEmailExists(email) ;
			if(type == SendMailTypeEnum.FindPassword){
				if(flag == false ){
					jsonObject.accumulate(ErrorCode, -1);
					jsonObject.accumulate(Result, false) ;
					jsonObject.accumulate(Value, "邮箱不存在");
					return jsonObject.toString() ;
				}
			}
			
			if(type == SendMailTypeEnum.RegMail||type == SendMailTypeEnum.ValidateMail){
				if(flag == true ){
					jsonObject.accumulate(ErrorCode, -1);
					jsonObject.accumulate(Result, false) ;
					jsonObject.accumulate(Value, "邮箱已经存在");
					return jsonObject.toString() ;
				}
			}
			
			SendMail(getIpAddr(request), email, type) ;
			jsonObject.accumulate(ErrorCode, 0);
			jsonObject.accumulate(Result, true) ;
			jsonObject.accumulate(Value, "验证码已经发送，请查收");
			return jsonObject.toString() ;
			
		}catch(Exception e){
			return ApiConstant.getUnknownError(e);
		}
			
	}

	
	public String FindLoginPassword(HttpServletRequest request) throws Exception {
		try {
			int type = Integer.parseInt(request.getParameter("type").trim());//1phone2mail
			String email = request.getParameter("email").trim().toLowerCase();
			String password = request.getParameter("newpassword").trim();
			String code = request.getParameter("code");
			String areaCode = "86" ;
			String ip = getIpAddr(request) ;
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate(Result, true);
			
			if(password == null || password.length()<6||password.length()>20){
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "密码长度必须为6~20位") ;
				return jsonObject.toString() ;
			}
			
			if(type==1){
				//手机
				
				if(!email.matches(Constant.PhoneReg)){
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "手机格式错误") ;
					return jsonObject.toString() ;
				}
				
				boolean flag1 = this.frontUserService.isTelephoneExists(email) ;
				if(flag1 == false ){
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "手机不存在") ;
					return jsonObject.toString() ;
				}
				
				
				
				boolean mobilValidate = validateMessageCode(ip,areaCode,email, MessageTypeEnum.FIND_PASSWORD, code) ;
				if(!mobilValidate){
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "短信验证码错误") ;
					return jsonObject.toString() ;
				}
				
			}else{
				//邮箱注册
				boolean flag = this.frontUserService.isEmailExists(email) ;
				
				if(email.matches(Constant.EmailReg) == false ){
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "邮箱格式错误") ;
					return jsonObject.toString() ;
				}
				
				if(flag == false ){
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "邮箱不存在") ;
					return jsonObject.toString() ;
				}
				
				boolean mailValidate = validateMailCode(ip, email, SendMailTypeEnum.FindPassword, code);
				if(!mailValidate){
					jsonObject.accumulate(ErrorCode, -1) ;
					jsonObject.accumulate(Value, "邮箱验证码错误") ;
					return jsonObject.toString() ;
				}
				
				
			}

			if(validateLoginPassword(password)){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate(APP_API_Controller.Value, "密码格式不正确") ;
				return jsonObject.toString() ;
			}

			boolean flag = false ;
			Fuser fuser = null ;
			
			fuser = this.frontUserService.findUserByProperty(type==1?"ftelephone":"femail", email).get(0) ; 
			
			if(fuser!=null ){
				fuser.setFloginPassword(Utils.MD5(password,fuser.getSalt()));
				this.frontUserService.updateFuser(fuser);
				flag = true ;
			}
			if(flag == true ){
				this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.FIND_PASSWORD);
				jsonObject.accumulate(ErrorCode, 0) ;
				jsonObject.accumulate(Value, "密码修改成功") ;
				return jsonObject.toString() ;
			}else{
				jsonObject.accumulate(ErrorCode, -1) ;
				jsonObject.accumulate(Value, "网络错误，请稍后再试") ;
				return jsonObject.toString() ;
			}
			
		} catch (Exception e) {
			
			return ApiConstant.getUnknownError(e);
		}
	}

	/**
	 * 充值
	 * @param request
	 * @return
	 * @throws Exception
     */
	public String RechargeCny(HttpServletRequest request) throws Exception {
		try {
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, true) ;
			
			double money = Utils.getDoubleUp(Double.parseDouble(request.getParameter("money")), 2) ;
			String account = request.getParameter("account");
			int type = 0;
			if(StringUtils.isNotBlank(request.getParameter("type"))){
				 type = Integer.parseInt(request.getParameter("type"));
			}

			Fuser fuser = this.realTimeData.getAppFuser(curLoginToken);


			if(!ValidateKycLevelEnum.validateKYC1(fuser.getKyclevel())){
				return ApiConstant.getKYC1ValidateError();
			}

			if(type == RemittanceTypeEnum.Type3){
				int alipayrgcnum = Integer.parseInt(this.constantMap.get("alipayrgcnum").toString().trim());
				int user_alipay_num = this.capitaloperationService.queryCountByRemittanceType(RemittanceTypeEnum.getEnumString(type), fuser.getFid());
				if (user_alipay_num >= alipayrgcnum) {
					jsonObject.accumulate("code", -1);
					jsonObject.accumulate(APP_API_Controller.Value, "充值次数超限，支付宝充值次数为" + alipayrgcnum);
					return jsonObject.toString();
				}

				double minalipayrgc = Double.parseDouble(this.constantMap.get("minalipayrgc").toString().trim());
				double maxalipayrgc = Double.parseDouble(this.constantMap.get("maxalipayrgc").toString().trim());
				if(money < minalipayrgc) {
					jsonObject.accumulate("code", -1);
					jsonObject.accumulate(APP_API_Controller.Value, "支付宝最小充值金额为￥"+minalipayrgc);
					return jsonObject.toString();
				}

				if(money > maxalipayrgc) {
					jsonObject.accumulate("code", -1);
					jsonObject.accumulate(APP_API_Controller.Value, "支付宝最大充值金额为￥"+maxalipayrgc);
					return jsonObject.toString();
				}
			}else if(type == RemittanceTypeEnum.Type1){
				double minRecharge = Double.parseDouble(this.constantMap.get("minrechargecny").toString()) ;
				if(money < minRecharge){
					//非法
					jsonObject.accumulate(ErrorCode, -1);
					jsonObject.accumulate(Value, "最小充值金额为￥"+minRecharge);
					return jsonObject.toString();
				}
			}else{
				return ApiConstant.getRequestError("充值方式不正确");
			}


//			Systembankinfo systembankinfo = this.frontBankInfoService.findSystembankinfoById(2) ;
//			LOGGER.info(CLASS_NAME + " alipayManual,查询银行账户信息systembankinfo:{}", systembankinfo);
//			if(systembankinfo==null || systembankinfo.getFstatus()== SystemBankInfoEnum.ABNORMAL ){
//				LOGGER.info(CLASS_NAME + " alipayManual,查询银行账户信息为空或账户停用");
//				//银行账号停用
//				jsonObject.accumulate(ErrorCode, -1);
//				jsonObject.accumulate(Value, "银行帐户不存在");
//				return jsonObject.toString();
//			}

			List<Systembankinfo> systembankinfos = this.frontBankInfoService.findAllSystemBankInfo(type == 3 ? 1 : 0) ;
			Systembankinfo systembankinfo;
			if(systembankinfos.size() > 0){
				systembankinfo = systembankinfos.get(0);
			}else{
				LOGGER.info(CLASS_NAME + " alipayTransfer,查询账户信息为空或账户停用");
				//银行账号停用
				jsonObject.accumulate("code", -1);
				jsonObject.accumulate(APP_API_Controller.Value, "系统帐户不存在");
				return jsonObject.toString();
			}
			
			Fcapitaloperation fcapitaloperation = new Fcapitaloperation() ;
			fcapitaloperation.setFamount(money) ;
			fcapitaloperation.setSystembankinfo(systembankinfo) ;
			fcapitaloperation.setFcreateTime(Utils.getTimestamp()) ;

			fcapitaloperation.setFtype(CapitalOperationTypeEnum.RMB_IN) ;
			fcapitaloperation.setFuser(fuser) ;
			fcapitaloperation.setfAccount(account) ;
			fcapitaloperation.setFstatus(CapitalOperationInStatus.WaitForComing) ;

			fcapitaloperation.setFremittanceType(systembankinfo.getFbankName());

			if(type == RemittanceTypeEnum.Type3){
				fcapitaloperation.setFremittanceType(RemittanceTypeEnum.getEnumString(type));
			}
			fcapitaloperation.setfBank(RemittanceTypeEnum.getEnumString(type));

			//LOGGER.info(CLASS_NAME + " alipayManual,保存到数据库的人民币充值流水fcapitaloperation:{}", new Gson().toJson(fcapitaloperation));
			this.frontAccountService.addFcapitaloperation(fcapitaloperation) ;
			LOGGER.info(CLASS_NAME + " alipayManual,保存完毕，开始拼装返回对象");
			jsonObject.accumulate(ErrorCode, 0);
			jsonObject.accumulate("money", String.valueOf(fcapitaloperation.getFamount())) ;
			jsonObject.accumulate("operationId", fcapitaloperation.getFid()) ;
			jsonObject.accumulate("fbankName", systembankinfo.getFbankName()) ;
			jsonObject.accumulate("fownerName", systembankinfo.getFownerName()) ;
			jsonObject.accumulate("fbankAddress", systembankinfo.getFbankAddress()) ;
			jsonObject.accumulate("account", account) ;
			jsonObject.accumulate("fbankNumber", systembankinfo.getFbankNumber()) ;
			jsonObject.accumulate("type",type);
			jsonObject.accumulate("remark",fcapitaloperation.getFid());
			LOGGER.info(CLASS_NAME + " alipayManual,返回对象jsonObject:{}", jsonObject.toString());
			return jsonObject.toString() ;  

		} catch (Exception e) {
			
			return ApiConstant.getUnknownError(e);
		}
	}

	/**
	 *
	 * 查询首页信息(banner、资讯)
	 * @param request
	 * @return
	 */
	public  String queryHome(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject() ;
		jsonObject.accumulate(Result, true) ;
		String type = request.getParameter("type");
		if(type == null) {
			jsonObject.accumulate(ErrorCode, -1) ;
			jsonObject.accumulate(Value, "参数type不能为空");
			return jsonObject.toString();
		}

		// banner 广告
		String current_date = DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay);
		StringBuffer filter = new StringBuffer();
		filter.append(" where 1=1 ");
		filter.append(" and fstatus = " + BannerStatusEnum.USE.getCode());
		filter.append(" and fseat = " + type);
		filter.append(" and fendDate >= '"+current_date+"' ");
		filter.append(" order by fpriority asc");

		List<Fbanner> fbannerList = this.fbannerService.list(0, 0, filter.toString(), false);
		if(fbannerList != null && fbannerList.size() > 0) {
			JSONArray jsonArray = new JSONArray();
			for(Fbanner fbanner : fbannerList) {
				JSONObject js = new JSONObject();
				js.accumulate("fimgurl", fbanner.getFimgurl());
				js.accumulate("flinkurl", fbanner.getFlinkurl());
				jsonArray.add(js);
			}
			jsonObject.accumulate(ErrorCode, 0);
			jsonObject.accumulate("banner", jsonArray);
		}

		//资讯  最新5条
		JSONArray articleList = commonArticleList(null, 0, 5, true, jsonObject);
		jsonObject.accumulate("articleList", articleList);

		//查询视频
		JSONArray videoList = commonVideoList(null, 0, 4, true);
		jsonObject.accumulate("videoList", videoList);

		return jsonObject.toString();
	}


	/**
	 * 首页查询虚拟币最新价格、涨跌幅信息
	 * @param request
	 * @return
	 */
	public String realCncyPrice(HttpServletRequest request) {
		LOG.i(this, "realCncyPrice start");
		JSONObject jsonObject = new JSONObject();
		//获取可交易的法币匹配信息
		List<Ftrademapping>  ftrademappingList =  this.ftradeMappingService.findActiveTradeMappingByLazy();
		if(ftrademappingList == null || ftrademappingList.size() == 0) {
			jsonObject.accumulate(ErrorCode, -1) ;
			jsonObject.accumulate(Value, "获取法币信息失败");
			return jsonObject.toString();
		}
		//取某个币种价格成交信息
		JSONArray jsonArray = new JSONArray();
		int index = 1;
		for( Ftrademapping ftrademapping : ftrademappingList){
			if(index >3 ) {
				break;
			}
			JSONObject js = new JSONObject();
			String fname =  ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname();
			String logo = ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFurl();
			js.accumulate("cncy_name",fname);
			js.accumulate("cncy_logo", logo);
			List<Fentrustlog> fentrustlogList = this.frontTradeService.findNewLatestDeal(ftrademapping.getFid(), 2);
			if(fentrustlogList != null && fentrustlogList.size() >= 2 && fentrustlogList.get(0) != null && fentrustlogList.get(1) != null) {
				BigDecimal new_price = new BigDecimal(fentrustlogList.get(0).getFprize()+"");  //最新价
				BigDecimal last_price = new BigDecimal(fentrustlogList.get(1).getFprize()+"");  //上一次的价格
				//涨跌幅度
				BigDecimal rate = (new_price.subtract(last_price)).divide(last_price,4, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN);
				js.accumulate("new_price", Utils.decimalFormat(new_price.doubleValue(), 2));
				js.accumulate("last_price", Utils.decimalFormat(last_price.doubleValue(), 2));
				js.accumulate("rate",Utils.decimalFormat(rate.doubleValue(), 2)) ;
			}else {
				js.accumulate("new_price", Utils.decimalFormat(0d, 2));
				js.accumulate("last_price", Utils.decimalFormat(0d, 2));
				js.accumulate("rate",Utils.decimalFormat(0d, 2)) ;
			}
			jsonArray.add(js);
			index++;
		}

		jsonObject.accumulate(ErrorCode, 0);
		jsonObject.accumulate("cncyList", jsonArray.toArray());

		return jsonObject.toString();
	}


	/**
	 * 查询视频信息
	 * @param request
	 * @return
	 */
	public String queryVideo(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		//查询视频类别信息
		List<Fvideotype> fvideotypeList = this.fvideoService.findAllVideoType();
		if(fvideotypeList == null || fvideotypeList.size() == 0) {
			jsonObject.accumulate(ErrorCode, -1) ;
			jsonObject.accumulate(Value, "获取视频类别信息为空");
			return jsonObject.toString();
		}

		JSONArray jsonArray = new JSONArray();
		for(Fvideotype fvideotype : fvideotypeList) {
			JSONObject js_1 = new JSONObject();
			js_1.accumulate("fid", fvideotype.getFid());
			js_1.accumulate("fname", fvideotype.getfName());
			JSONArray jarr_1 = this.commonVideoList(fvideotype.getFid(), 0, 10, true);
			js_1.accumulate("videoList", jarr_1);
			jsonArray.add(js_1);

		}

		jsonObject.accumulate("videoTypeList", jsonArray);

		return jsonObject.toString();
	}


    /**
     * 查询资讯详情
     * @param request
     * @return
     */
	public String articleDetail(HttpServletRequest request) {
	    JSONObject jsonObject = new JSONObject();
        String fid = request.getParameter("fid");
        if(StringUtils.isBlank(fid)) {
            jsonObject.accumulate(ErrorCode, -1) ;
            jsonObject.accumulate(Value, "参数fid不能为空");
            return jsonObject.toString();
        }

        Date currentDate = new Date();
        Farticle farticle = this.articleService.findById(Integer.valueOf(fid));
        String admin_name = farticle.getFadminByFcreateAdmin().getFname();
        jsonObject.accumulate("author", admin_name);
        jsonObject.accumulate("description", farticle.getFdescription());
        jsonObject.accumulate("content", farticle.getFcontent());
        jsonObject.accumulate("title", farticle.getFtitle());
        jsonObject.accumulate("title_short", farticle.getFtitle_short());
        jsonObject.accumulate("tag", farticle.getFtag());
        jsonObject.accumulate("imgurl", farticle.getFurl());
        jsonObject.accumulate("isout", farticle.isFisout());
        jsonObject.accumulate("outurl", farticle.getFoutUrl());
        jsonObject.accumulate("pushtime", DateHelper.date2String(farticle.getFlastModifyDate(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
        Date lastModifyDate = farticle.getFlastModifyDate();

        String text = this.returnTimeBetweenText(currentDate, lastModifyDate);
        jsonObject.accumulate("pushtimeText", text);

        jsonObject.accumulate(ErrorCode, 0);
        return jsonObject.toString();
    }

	/**
	 * 获取资讯类型列表
	 * @param request
	 * @return
	 */
	public String articleTypeList(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		List<Farticletype> farticletypeList = this.articleTypeService.findAll();
		if(farticletypeList == null || farticletypeList.size() == 0) {
			jsonObject.accumulate(ErrorCode, -1) ;
			jsonObject.accumulate(Value, "查询资讯类型为空");
			return jsonObject.toString();
		}

		JSONArray jsonArray = new JSONArray();
		for(Farticletype farticletype : farticletypeList) {
			if(farticletype.getFname().indexOf("公告") != -1 || farticletype.getFname().indexOf("新闻") != -1) {
				continue;
			}
			JSONObject js = new JSONObject();
			js.accumulate("fid", farticletype.getFid());
			js.accumulate("fname", farticletype.getFname());
			jsonArray.add(js);
		}
		jsonObject.accumulate(ErrorCode, 0);
		jsonObject.accumulate("farticletypeList", jsonArray);

		return jsonObject.toString();
	}


	/**
	 * 行业资讯列表(带分页)
	 * @param request
	 * @return
	 */
	public String queryArticle(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();

		//当前页
		int currentPage = 1;
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}

		int firstResult = (currentPage -1) * maxResult;

		String ftypeId = request.getParameter("ftypeId");
		JSONArray articleList = this.commonArticleList(ftypeId != null ? Integer.valueOf(ftypeId) : null, firstResult, maxResult, true, jsonObject);


		jsonObject.accumulate(PageSize, maxResult);
		jsonObject.accumulate(CurrentPage, currentPage);
		jsonObject.accumulate(Result, true);
		jsonObject.accumulate(List, articleList);
		jsonObject.accumulate(ErrorCode, 0);
		return jsonObject.toString();
	}


	/**
	 * 查询资讯信息列表
	 * @param ftypeid  类型id
	 * @return
	 */
	public JSONArray commonArticleList(Integer ftypeid, int firstResult, int maxResults, boolean isFy, JSONObject jsonObject ) {
		JSONArray arr_article = new JSONArray();
		Date currentDate = new Date();
		//资讯类型封装
		/*String articleType_str = "";
		if(ftypeid == null) {
			//查询资讯类别
			List<Farticletype> farticletypeList = this.articleTypeService.findAll();
			if(farticletypeList == null || farticletypeList.size() == 0) {
				return null;
			}

			for(Farticletype farticletype : farticletypeList) {
				if(farticletype.getFname().indexOf("公告") != -1 || farticletype.getFname().indexOf("新闻") != -1) {
					continue;
				}
				articleType_str += farticletype.getFid() + ",";
			}

			if(StringUtils.isNotBlank(articleType_str)) {
				articleType_str = articleType_str.substring(0, articleType_str.length() -1);
			}
		}*/


		List<Farticle> farticleList =  null;
		StringBuffer filter2 = new StringBuffer();
		if(ftypeid != null) {  //类型不为空
			filter2.append(" where 1=1 ");
			filter2.append(" and farticletype.fid = " + ftypeid);
			filter2.append(" and fstatus in(2,4) ");
			filter2.append(" order by flastModifyDate desc ");
			farticleList = this.articleService.list(firstResult, maxResults, filter2.toString(), isFy);
		}else{
//			filter2.append(" and farticletype.fid in ("+articleType_str+")");
			farticleList = this.frontOtherService.findHostArticle(firstResult, maxResults);
		}





		if(farticleList != null && farticleList.size() > 0) {
			String share_url = new Constant().ARTICLE_SHARE;
			for(Farticle farticle : farticleList) {
				JSONObject js2 = new JSONObject();
				js2.accumulate("fid", farticle.getFid());
				js2.accumulate("share_url", share_url + farticle.getFid());
				js2.accumulate("title", farticle.getFtitle());
				js2.accumulate("title_short", farticle.getFtitle_short());
				js2.accumulate("imgurl", farticle.getFurl());
				js2.accumulate("isout", farticle.isFisout());
				js2.accumulate("outurl", farticle.getFoutUrl());
				js2.accumulate("pushtime", DateHelper.date2String(farticle.getFlastModifyDate(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
				Date lastModifyDate = farticle.getFlastModifyDate();
				//标签拆分
				if(StringUtils.isNotBlank(farticle.getFtag())) {
					String[] tag = farticle.getFtag().split(",");
					js2.accumulate("tag", tag);
				}else{
					js2.accumulate("tag", new String[0]);
				}

				String text = this.returnTimeBetweenText(currentDate, lastModifyDate);
				js2.accumulate("pushtimeText", text);
				arr_article.add(js2);
			}
		}


		//总数量
		if(isFy) {
			int totalCount = 0;
			if(ftypeid != null) {
				totalCount = this.adminService.getAllCount("Farticle", filter2+"");
				jsonObject.accumulate(TotalCount, totalCount);
			}else{
				StringBuffer filter3 = new StringBuffer();
				filter3.append(" where fisding = 1");
				totalCount = this.adminService.getAllCount("Farticle", filter3+"");
				jsonObject.accumulate(TotalCount, totalCount);
			}
			int totalPage = totalCount / maxResult + ((totalCount % maxResult) == 0 ? 0 : 1);
			jsonObject.accumulate(TotalPage, totalPage);

		}
		return arr_article;
	}

	/**
	 * 查询视频列表
	 * @param ftypeid
	 * @param firstResult
	 * @param maxResults
	 * @param isFy
	 * @return
	 */
	public JSONArray commonVideoList(Integer ftypeid, int firstResult, int maxResults, boolean isFy) {
		JSONArray jarr_1 = new JSONArray();
		StringBuffer filter = new StringBuffer();
		filter.append(" where 1=1 ");
		if(ftypeid != null) {
			filter.append(" and fvideotype.fid = " + ftypeid);
		}
		filter.append(" order by fPriority asc");
		List<Fvideo> fvideoList =  this.fvideoService.list(firstResult, maxResults , filter.toString(), isFy);
		if(fvideoList != null && fvideoList.size() > 0) {
			for(Fvideo fvideo : fvideoList) {
				JSONObject js2 = new JSONObject();
				js2.accumulate("video_fid", fvideo.getFid());
				js2.accumulate("priority", fvideo.getfPriority());
				js2.accumulate("pictureurl", fvideo.getfPictureUrl());
				js2.accumulate("title", fvideo.getfTitle());
				js2.accumulate("videourl", fvideo.getfVideoUrl());
				jarr_1.add(js2);
			}
		}

		return jarr_1;
	}

	/**
	 * 交易中心首页
	 * @return
	 */
	public String tradingCenterIndex(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		List<FvirtualwalletVo> fvirtualwalletVoList = new ArrayList<>();
		FvirtualwalletVo fvirtualwalletVo = null;

		//获取各个币种的市价(当前最新价)
		List<Ftrademapping> ftrademappingList = ftradeMappingService.findActiveTradeMappingByLazy();
		if (ftrademappingList == null || ftrademappingList.size() == 0) {
			LOG.i(this, "获取法币匹配信息为空");
			jsonObject.accumulate(ErrorCode, -1);
			jsonObject.accumulate(Value, "获取法币匹配信息为空");
			return jsonObject.toString();
		}

		Map<Integer, Integer> point_map = new HashMap<>();  //小数点位数
		Map<Integer, Integer> num_map = new HashMap<>();  //数量小数位数
		Map<Integer, String> price_map = new HashMap<>();  //价格map
		Map<Integer, Integer> oldIdMap = new HashMap<>();  //之前接口id(Ftrademapping)
		String fvi_fid_str = "";
		for (Ftrademapping ftrademapping : ftrademappingList) {
			int fid = ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid();
			fvi_fid_str += fid + ",";
			String price = Utils.decimalFormat(realTimeData.getLatestDealPrize(ftrademapping.getFid()), ftrademapping.getFcount1());
			price_map.put(fid, price);
			point_map.put(fid, ftrademapping.getFcount1());
			num_map.put(fid, ftrademapping.getFcount2());
			oldIdMap.put(fid, ftrademapping.getFid());

			// 没有登录返回值
			fvirtualwalletVo = new FvirtualwalletVo();
			fvirtualwalletVo.setFviFid(ftrademapping.getFid());// 对应老的id
			fvirtualwalletVo.setFviName(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname() + " (CNY)");
			fvirtualwalletVo.setShortName(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getfShortName());
			fvirtualwalletVo.setLogo(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFurl());
			fvirtualwalletVo.setFtotalPriceStr(Utils.decimalFormat(0d, 2));
			fvirtualwalletVo.setFableStr(Utils.decimalFormat(0d, 4));
			fvirtualwalletVo.setFtotalStr(Utils.decimalFormat(0d, 4));
			fvirtualwalletVo.setFpriceStr(price);
			fvirtualwalletVo.setFprofitLossStr(Utils.decimalFormat(0d, 2));
			fvirtualwalletVo.setFprofitLossRate(new BigDecimal("0").setScale(2, BigDecimal.ROUND_DOWN));
			fvirtualwalletVo.setFcostPriceStr(Utils.decimalFormat(0d, 2));
			fvirtualwalletVoList.add(fvirtualwalletVo);
		}

		if (StringUtils.isNotBlank(fvi_fid_str)) {
			fvi_fid_str = fvi_fid_str.substring(0, fvi_fid_str.length() - 1);
		}

		Fuser fuser = this.realTimeData.getAppFuser(this.curLoginToken);
		if (null != fuser) {
			fvirtualwalletVoList = new ArrayList<>();

			//用户id
			Integer userId = fuser.getFid();

			//会员等级
			Integer level = fuser.getFscore().getFlevel();
			if (level == null) {
				LOG.i(this, "会员等级为空,用户id=" + userId);
				level = 1;  //等级为空时，则默认为1级
			}

			//根据会员等级取交易手续费(不同币种，交易手续费一样)
			Fusergrade fusergrade = this.userGradeService.findById(level);
			if (fusergrade == null) {
				LOG.i(this, "根据会员等级查询交易手续费为空,会员等级=" + level);
				jsonObject.accumulate(ErrorCode, -1);
				jsonObject.accumulate(Value, "获取失败");
				return jsonObject.toString();
			}

			//交易手续费
			BigDecimal tradefee = fusergrade.getTradefee();
//			LOG.i(this, "用户id=" + userId + ",等级=" + level + ",交易手续费=" + tradefee);

			//虚拟钱包
			Map<Integer, Fvirtualwallet> fvirtualwallets_map = this.frontUserService.findVirtualWallet(userId);
			if (fvirtualwallets_map == null) {
				LOG.i(this, "获取虚拟钱包为空");
				jsonObject.accumulate(ErrorCode, -1);
				jsonObject.accumulate(Value, "获取虚拟钱包为空");
				return jsonObject.toString();
			}

			//判断时间，如果是今天15点后，则取昨日15点和今天15点之间的数据，反之则取前天15点和昨天15点之间的数据
			Date current_date = new Date();
			Calendar rule_cal = Calendar.getInstance();
			rule_cal.set(Calendar.HOUR_OF_DAY, 15);
			rule_cal.set(Calendar.MINUTE, 0);
			rule_cal.set(Calendar.SECOND, 0);

			String startTime = "";
			String endTime;
			boolean isToday = true; //默认是超过15点

			if (current_date.getTime() > rule_cal.getTime().getTime()) {  //超过15点
				endTime = DateHelper.date2String(rule_cal.getTime(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond);
			} else {  //未超过15点
				isToday = false;
				rule_cal.add(Calendar.DATE, -1);  // 结束时间为昨天的15点
				endTime = DateHelper.date2String(rule_cal.getTime(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond);

			}
			rule_cal.add(Calendar.DATE, -1);  //开始时间为前天的15点
			startTime = DateHelper.date2String(rule_cal.getTime(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond);

//			LOG.i(this, "开始时间=" + startTime + ",结束时间=" + endTime);


			//查询所有币种15点的数量×价格信息
			Map<Integer, Ftimewallet> start_point_price_map = this.ftimewalletService.queryByUserAndTime(userId, startTime, fvi_fid_str);
			Map<Integer, Ftimewallet> end_point_price_map = this.ftimewalletService.queryByUserAndTime(userId, endTime, fvi_fid_str);

			Map<Integer, FentrustlogVo> buyMap = this.frontOthersService.queryLogByTime(startTime, endTime, EntrustTypeEnum.BUY, userId, tradefee, fvi_fid_str);
			Map<Integer, FentrustlogVo> sellMap = this.frontOthersService.queryLogByTime(startTime, endTime, EntrustTypeEnum.SELL, userId, tradefee, fvi_fid_str);

			DecimalFormat decimalFormat = new DecimalFormat("#.####");
			//计算

			for (Map.Entry<Integer, Fvirtualwallet> wallet : fvirtualwallets_map.entrySet()) {
				Integer fvi_fid = wallet.getKey();
				Integer price_point = point_map.get(fvi_fid);  //小数点位数
				Integer num_point = num_map.get(fvi_fid);  //数量小数点
//				LOG.i(this, "虚拟币id= " + fvi_fid);
				fvirtualwalletVo = new FvirtualwalletVo();
				//logo
				Fvirtualcointype fvirtualcointype = virtualCoinService.findById(fvi_fid);
				fvirtualwalletVo.setLogo(fvirtualcointype.getFurl());
				fvirtualwalletVo.setFviFid(oldIdMap.get(fvi_fid));// 对应老的id
				fvirtualwalletVo.setFviName(wallet.getValue().getFvirtualcointype().getFname() + " (CNY)");
				fvirtualwalletVo.setShortName(wallet.getValue().getFvirtualcointype().getfShortName());
				fvirtualwalletVo.setFfrozenStr(Utils.decimalFormat(wallet.getValue().getFfrozen(), num_point));
				fvirtualwalletVo.setFableStr(Utils.decimalFormat(wallet.getValue().getFtotal(), num_point));  //数据库total是可用
				fvirtualwalletVo.setFtotalStr(Utils.decimalFormat(new BigDecimal(fvirtualwalletVo.getFableStr()).add(new BigDecimal(fvirtualwalletVo.getFfrozenStr())).doubleValue(), num_point));
				fvirtualwalletVo.setFpriceStr(price_map.get(fvi_fid));
				//总市价保留2位小数
				fvirtualwalletVo.setFtotalPriceStr(Utils.decimalFormat(new BigDecimal(fvirtualwalletVo.getFtotalStr()).multiply(new BigDecimal(fvirtualwalletVo.getFpriceStr())).doubleValue(), 2)); //总市价=总持有量*最新价格

				BigDecimal profit_loss = BigDecimal.ZERO;
				BigDecimal profit_loss_rate = BigDecimal.ZERO;
				BigDecimal cost_price = BigDecimal.ZERO;


				//计算成本、盈亏、盈亏比

				//查询区间段的买、卖交易价格总和、手续费
				if (start_point_price_map != null && !start_point_price_map.isEmpty()
						&& end_point_price_map != null && !end_point_price_map.isEmpty()) {
					BigDecimal start_point_price = start_point_price_map.get(fvi_fid).getFprice();
					BigDecimal start_point_num = start_point_price_map.get(fvi_fid).getFtotal();
//				LOG.i(this, "时间="+endTime+",价格="+start_point_price+",数量="+start_point_num);

					BigDecimal end_point_price = end_point_price_map.get(fvi_fid).getFprice();
					BigDecimal end_point_num = end_point_price_map.get(fvi_fid).getFtotal();
//				LOG.i(this, "时间="+startTime+",价格="+end_point_price+",数量="+end_point_num);
//					LOG.i(this, "区间范围内买入信息：" + JsonHelper.obj2JsonStr(buyList));
//					LOG.i(this, "区间范围内卖出信息：" + JsonHelper.obj2JsonStr(sellList));
					BigDecimal buy_price_sum = BigDecimal.ZERO; //买进交易额
					BigDecimal sell_price_sum = BigDecimal.ZERO;   //卖出交易额
					BigDecimal trade_fees = BigDecimal.ZERO;  //买进卖出的交易手续费
					BigDecimal buy_count = BigDecimal.ZERO;  //买进数量
					if (buyMap != null && !buyMap.isEmpty()) {
						FentrustlogVo buyVo = buyMap.get(fvi_fid);
						buy_price_sum = buyVo.getAmount();
						trade_fees = trade_fees.add(buyVo.getFees());
						buy_count = buyVo.getCount();
					}
					if (sellMap != null && !sellMap.isEmpty()) {
						sell_price_sum = sellMap.get(fvi_fid).getAmount();
						trade_fees = trade_fees.add(sellMap.get(fvi_fid).getFees());
					}
//					LOG.i(this, "买进交易额：" + buy_price_sum + "    卖出交易额：" + sell_price_sum + "  交易手续费：" + trade_fees + "买进数量：" + buy_count);
					BigDecimal temp1 = (end_point_price.multiply(end_point_num)).add(sell_price_sum);
//					LOG.i(this, "卖出收益：" + temp1);
					BigDecimal temp2 = start_point_price.multiply(start_point_num).add(buy_price_sum);
//					LOG.i(this, "买进支出：" + temp2);

					profit_loss = temp1.subtract(temp2).subtract(trade_fees).setScale(2, BigDecimal.ROUND_DOWN);
					//如果temp2=0
					if (temp2.compareTo(BigDecimal.ZERO) == 1) {
						profit_loss_rate = profit_loss.divide(temp2, 4, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).setScale(2);
					}
					if (start_point_num.compareTo(BigDecimal.ZERO) == 1 || buy_count.compareTo(BigDecimal.ZERO) == 1) {
						cost_price = temp2.divide((start_point_num.add(buy_count)), 2, BigDecimal.ROUND_DOWN);
					}

				}

				fvirtualwalletVo.setFprofitLossStr(Utils.decimalFormat(profit_loss.doubleValue(), 2));
				fvirtualwalletVo.setFprofitLossRate(profit_loss_rate);
				fvirtualwalletVo.setFcostPriceStr(Utils.decimalFormat(cost_price.doubleValue(), 2));


				fvirtualwalletVoList.add(fvirtualwalletVo);
			}
		}
		jsonObject.accumulate(List, fvirtualwalletVoList);
		jsonObject.accumulate(ErrorCode, 0);
		jsonObject.accumulate(Value, "获取成功");
		return jsonObject.toString();
	}

	/**
	 * 行情中心货币信息
	 * @param symbol
	 * @return
	 */
	public JSONObject GetMarketDepth2(int symbol) {

		try{
			Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol);
			if(ftrademapping==null || ftrademapping.getFstatus()== TrademappingStatusEnum.FOBBID){
				return null;
			}
			int count1 = ftrademapping.getFcount1() ;
			int count2 = ftrademapping.getFcount2() ;
			JSONObject jsonObject = new JSONObject() ;

			//quotation
			JSONObject quotation = new JSONObject() ;
			//成交量，买一，卖一，最高，最低，现价
			quotation.accumulate("latestDealPrice", Utils.decimalFormat(this.realTimeData.getLatestDealPrize(symbol), 2)) ;
			quotation.accumulate("sellOnePrice", Utils.decimalFormat(this.realTimeData.getLowestSellPrize(symbol), 2)) ;
			quotation.accumulate("buyOnePrice", Utils.decimalFormat(this.realTimeData.getHighestBuyPrize(symbol), 2)) ;
			quotation.accumulate("oneDayLowest", Utils.decimalFormat(this.oneDayData.getLowest(symbol), 2)) ;
			quotation.accumulate("oneDayHighest", Utils.decimalFormat(this.oneDayData.getHighest(symbol), 2)) ;
			quotation.accumulate("oneDayTotal", Utils.decimalFormat(this.oneDayData.getTotal(symbol), 4)) ;

			quotation.accumulate("name", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname() + " (CNY)") ;
			quotation.accumulate("shortName", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getfShortName());
			quotation.accumulate("logo", ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFurl()) ;


			java.util.List<Ftradehistory> ftradehistorys = (List<Ftradehistory>)constantMap.get("tradehistory");
			Ftradehistory ftradehistory = null;
			for (Ftradehistory th : ftradehistorys) {
				if(th.getFtrademapping().getFid() == ftrademapping.getFid().intValue()){
					ftradehistory = th ;
					break;
				}
			}

			//24小时涨跌幅
			double priceRaiseRate = 0 ;
			double sx = this.oneDayData.get24Price(ftrademapping.getFid());
			if(ftradehistory!=null ){
				sx= ftradehistory.getFprice();
			}
			try {
				if(sx > 0){
					priceRaiseRate = Utils.getDouble((this.realTimeData.getLatestDealPrize(ftrademapping.getFid())-sx)/sx*100, 2);
				}else{
					priceRaiseRate = Utils.getDouble(priceRaiseRate, 2);
				}
			} catch (Exception e) {
				e.printStackTrace();
				jsonObject = new JSONObject() ;
				jsonObject.accumulate(Result, false) ;
				return jsonObject;
			}
			if(priceRaiseRate > 0){
				quotation.accumulate("priceRaiseRate", "+" + priceRaiseRate) ;
			}else{
				quotation.accumulate("priceRaiseRate", priceRaiseRate) ;
			}

			return quotation;

		}catch(Exception e){
			e.printStackTrace();
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.accumulate(Result, false) ;
			return jsonObject;
		}

	}

	/**
	 * k线图接口
	 * @param request
	 * @return
	 */
	public String appperiod2(HttpServletRequest request) {
		JSONObject kline = new JSONObject();
		try {
			String m1;
			String m5;
			String m30;
			String h1;
			String d1;
			String w1;
			int symbol = Integer.parseInt(request.getParameter("symbol"));
			Calendar monthCalendar = Calendar.getInstance();
			monthCalendar.add(Calendar.MONTH, -1);
			Calendar tenDayCalendarr = Calendar.getInstance();
			tenDayCalendarr.add(Calendar.DATE, -10);
			Calendar fiveDayCalendar = Calendar.getInstance();
			fiveDayCalendar.add(Calendar.DATE, -5);
			Calendar oneDayCalendar = Calendar.getInstance();
			oneDayCalendar.add(Calendar.DATE, -1);

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			List<Fperiod> allFperiod = this.frontOthersService.findAllFperiod(monthCalendar.getTimeInMillis(), symbol);
			StringBuffer oneMinuteStringBuffer = new StringBuffer() ;
			StringBuffer fiveMinuteStringBuffer = new StringBuffer() ;
			StringBuffer thiryMinuteStringBuffer = new StringBuffer() ;
			StringBuffer oneHourStringBuffer = new StringBuffer() ;

			oneMinuteStringBuffer.append("[") ;
			fiveMinuteStringBuffer.append("[") ;
			thiryMinuteStringBuffer.append("[") ;
			oneHourStringBuffer.append("[") ;
			long oneMinuteParse = sdf1.parse(sdf1.format(oneDayCalendar.getTime())).getTime();
			long fiveMinuteParse = sdf1.parse(sdf1.format(fiveDayCalendar.getTime())).getTime();
			long thirtyMinuteParse = sdf1.parse(sdf1.format(tenDayCalendarr.getTime())).getTime();
			long oneHourParse = sdf1.parse(sdf1.format(monthCalendar.getTime())).getTime();
			for (Fperiod fperiod : allFperiod) {
//				Timestamp ftime = new Timestamp(sdf1.parse(sdf1.format(fperiod.getFtime())).getTime());
				long time = fperiod.getFtime().getTime();
				if (time >= oneMinuteParse) {
					oneMinuteStringBuffer.append("[" + (fperiod.getFtime().getTime())
							+ "," + new BigDecimal(String.valueOf(fperiod.getFkai())).setScale(6, BigDecimal.ROUND_HALF_UP)
							+ "," + new BigDecimal(String.valueOf(fperiod.getFgao())).setScale(6, BigDecimal.ROUND_HALF_UP)
							+ "," + new BigDecimal(String.valueOf(fperiod.getFdi())).setScale(6, BigDecimal.ROUND_HALF_UP)
							+ "," + new BigDecimal(String.valueOf(fperiod.getFshou())).setScale(6, BigDecimal.ROUND_HALF_UP)
							+ "," + new BigDecimal(String.valueOf(fperiod.getFliang())).setScale(4, BigDecimal.ROUND_HALF_UP) + "]");
					oneMinuteStringBuffer.append(",");
				}
				if(fperiod.getFtime().getTime() >= fiveMinuteParse && time%(5*60*1000) == 0){
					fiveMinuteStringBuffer.append("[" + (fperiod.getFtime().getTime())
							+ "," + new BigDecimal(String.valueOf(fperiod.getFkai())).setScale(6, BigDecimal.ROUND_HALF_UP)
							+ "," + new BigDecimal(String.valueOf(fperiod.getFgao())).setScale(6, BigDecimal.ROUND_HALF_UP)
							+ "," + new BigDecimal(String.valueOf(fperiod.getFdi())).setScale(6, BigDecimal.ROUND_HALF_UP)
							+ "," + new BigDecimal(String.valueOf(fperiod.getFshou())).setScale(6, BigDecimal.ROUND_HALF_UP)
							+ "," + new BigDecimal(String.valueOf(fperiod.getFliang())).setScale(4, BigDecimal.ROUND_HALF_UP) + "]");
					fiveMinuteStringBuffer.append(",");
				}
				if(fperiod.getFtime().getTime() >= thirtyMinuteParse && time%(30*60*1000) == 0){
					thiryMinuteStringBuffer.append("[" + (fperiod.getFtime().getTime())
							+ "," + new BigDecimal(String.valueOf(fperiod.getFkai())).setScale(6, BigDecimal.ROUND_HALF_UP)
							+ "," + new BigDecimal(String.valueOf(fperiod.getFgao())).setScale(6, BigDecimal.ROUND_HALF_UP)
							+ "," + new BigDecimal(String.valueOf(fperiod.getFdi())).setScale(6, BigDecimal.ROUND_HALF_UP)
							+ "," + new BigDecimal(String.valueOf(fperiod.getFshou())).setScale(6, BigDecimal.ROUND_HALF_UP)
							+ "," + new BigDecimal(String.valueOf(fperiod.getFliang())).setScale(4, BigDecimal.ROUND_HALF_UP) + "]");
					thiryMinuteStringBuffer.append(",");
				}
				if(fperiod.getFtime().getTime() >= oneHourParse && time%(60*60*1000) == 0){
					oneHourStringBuffer.append("[" + (fperiod.getFtime().getTime())
							+ "," + new BigDecimal(String.valueOf(fperiod.getFkai())).setScale(6, BigDecimal.ROUND_HALF_UP)
							+ "," + new BigDecimal(String.valueOf(fperiod.getFgao())).setScale(6, BigDecimal.ROUND_HALF_UP)
							+ "," + new BigDecimal(String.valueOf(fperiod.getFdi())).setScale(6, BigDecimal.ROUND_HALF_UP)
							+ "," + new BigDecimal(String.valueOf(fperiod.getFshou())).setScale(6, BigDecimal.ROUND_HALF_UP)
							+ "," + new BigDecimal(String.valueOf(fperiod.getFliang())).setScale(4, BigDecimal.ROUND_HALF_UP) + "]");
					oneHourStringBuffer.append(",");
				}
			}

			m1 = oneMinuteStringBuffer.delete(oneMinuteStringBuffer.length()-1, oneMinuteStringBuffer.length()).toString() + "]";
			m5 = fiveMinuteStringBuffer.delete(fiveMinuteStringBuffer.length()-1, fiveMinuteStringBuffer.length()).toString() + "]";
			m30 = thiryMinuteStringBuffer.delete(thiryMinuteStringBuffer.length()-1, thiryMinuteStringBuffer.length()).toString() + "]";
			h1 = oneHourStringBuffer.delete(oneHourStringBuffer.length()-1, oneHourStringBuffer.length()).toString() + "]";

			Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol);


			kline.accumulate("symbol", fvirtualcointype.getfShortName() + "_CNY");
			kline.accumulate("symbol_view", fvirtualcointype.getfShortName() + "/CNY");
			kline.accumulate("ask", 1.2);

			JSONObject timeLine = new JSONObject();
			d1 = this.klinePeriodData.getJsonString(fvirtualcointype.getFid(), 10);
			w1 = this.klinePeriodData.getJsonString(fvirtualcointype.getFid(), 12);
			timeLine.accumulate("oneMinute", m1);
			timeLine.accumulate("fiveMinute", m5);
			timeLine.accumulate("thirtyMinute", m30);
			timeLine.accumulate("oneHour", h1);
			timeLine.accumulate("oneDay", d1);
			timeLine.accumulate("oneWeek", w1);


			kline.accumulate("timeLine", timeLine);

			//行情中心货币信息
			JSONObject quotation = GetMarketDepth2(symbol);
			if (null == quotation.get("result") || !(boolean) quotation.get("result")) {
				kline.accumulate("quotation", quotation);
			} else {
				kline.accumulate(Result, false);
				kline.accumulate(ErrorCode, -1);
				return kline.toString();
			}


			kline.accumulate(Result, true);
			kline.accumulate(ErrorCode, 0);
		}catch (Exception e){
			kline.accumulate(Result, false);
			kline.accumulate(ErrorCode, -1);
		}

		return kline.toString();
	}

	/**
	 * k线图接口
	 * @param request
	 * @return
	 */
	public String appperiod(HttpServletRequest request) {
		JSONObject kline = new JSONObject();
		try {
			String m1;

			int symbol = Integer.parseInt(request.getParameter("symbol"));
			Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol);

			kline.accumulate("symbol", fvirtualcointype.getfShortName() + "_CNY");
			kline.accumulate("symbol_view", fvirtualcointype.getfShortName() + "/CNY");
			kline.accumulate("ask", 1.2);

			JSONObject timeLine = new JSONObject();
			timeLine.accumulate("oneMinute", this.klinePeriodData.getJsonString(fvirtualcointype.getFid(), 13));
			timeLine.accumulate("fiveMinute", this.klinePeriodData.getJsonString(fvirtualcointype.getFid(), 14));
			timeLine.accumulate("thirtyMinute", this.klinePeriodData.getJsonString(fvirtualcointype.getFid(), 15));
			timeLine.accumulate("oneHour", this.klinePeriodData.getJsonString(fvirtualcointype.getFid(), 16));
			timeLine.accumulate("oneDay", this.klinePeriodData.getJsonString(fvirtualcointype.getFid(), 10));
			timeLine.accumulate("oneWeek", this.klinePeriodData.getJsonString(fvirtualcointype.getFid(), 12));


			kline.accumulate("timeLine", timeLine);

			//行情中心货币信息
			JSONObject quotation = GetMarketDepth2(symbol);
			if (null == quotation.get("result") || !(boolean) quotation.get("result")) {
				kline.accumulate("quotation", quotation);
			} else {
				kline.accumulate(Result, false);
				kline.accumulate(ErrorCode, -1);
				return kline.toString();
			}


			kline.accumulate(Result, true);
			kline.accumulate(ErrorCode, 0);
		}catch (Exception e){
			kline.accumulate(Result, false);
			kline.accumulate(ErrorCode, -1);
		}

		return kline.toString();
	}


	/**
	 * 交易中心小k线图
	 */
	public JSONObject appperiodLittle(int symbol) {
		String m1 = "";
		JSONObject jsonObject = new JSONObject();

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		StringBuffer oneMinuteStringBuffer = new StringBuffer();
		oneMinuteStringBuffer.append("[") ;

		StringBuffer filter = new StringBuffer();
		filter.append(" where ftrademapping.fid= " + symbol);
		filter.append(" order by ftime desc ");
		List<Fperiod> allFperiod = this.frontOthersService.findFperiodList(0, 61, filter.toString(), true);

		Collections.sort(allFperiod, new Comparator<Fperiod>() {
			@Override
			public int compare(Fperiod fperiod, Fperiod t1) {
				return fperiod.getFtime().compareTo(t1.getFtime());
			}
		});

		if(null != allFperiod && allFperiod.size() > 0){
			for (Fperiod fperiod : allFperiod) {
				oneMinuteStringBuffer.append("[" + (fperiod.getFtime().getTime())
						+ "," + new BigDecimal(String.valueOf(fperiod.getFkai())).setScale(6, BigDecimal.ROUND_HALF_UP)
						+ "," + new BigDecimal(String.valueOf(fperiod.getFgao())).setScale(6, BigDecimal.ROUND_HALF_UP)
						+ "," + new BigDecimal(String.valueOf(fperiod.getFdi())).setScale(6, BigDecimal.ROUND_HALF_UP)
						+ "," + new BigDecimal(String.valueOf(fperiod.getFshou())).setScale(6, BigDecimal.ROUND_HALF_UP)
						+ "," + new BigDecimal(String.valueOf(fperiod.getFliang())).setScale(4, BigDecimal.ROUND_HALF_UP) + "]");
				oneMinuteStringBuffer.append(",");
			}

			m1 = oneMinuteStringBuffer.delete(oneMinuteStringBuffer.length()-1, oneMinuteStringBuffer.length()).toString() + "]";
		}else {
			return jsonObject.accumulate("timeLine", "[]");
		}
		return jsonObject.accumulate("timeLine", m1);
	}

	/**
	 * 行情中心数据
	 * @param request
	 * @return
	 */
	public String quotesCenter(HttpServletRequest request){
		int symbol = Integer.parseInt(request.getParameter("symbol")) ;

		JSONObject jsonObject = new JSONObject();

		// 交易中心小k线图
		jsonObject.accumulate("kline", appperiodLittle(symbol));

		//行情中心货币信息
		JSONObject quotation = GetMarketDepth2(symbol);
		jsonObject.accumulate("quotation", quotation) ;

		// 获取其他平台的行情  (从缓存中获取)
		List<Fquotes> last  = FquoteCacheManager.getCacheObj().getList();
		List<Fquotes> temp_list = new ArrayList<>();
		if(last != null && last.size() > 0) {
			for(Fquotes vo : last) {
				if(vo.getFvirtualcointypeid() == symbol ) {
					temp_list.add(vo);
				}
			}
		}
//		List<Fquotes> last = fquotesService.findLast(symbol);
		jsonObject.accumulate("otherPlatformData", temp_list) ;

		jsonObject.accumulate(Result, true) ;
		jsonObject.accumulate(ErrorCode , 0) ;
		return jsonObject.toString() ;
	}

	/**
	 * 获取币种
	 * @param request
	 * @return
	 */
	public String getCoinList(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject() ;
		List<Ftrademapping> activetradeMappingList = (List<Ftrademapping>)ConstantMap.get("activetradeMapping");
		List<IdAndName> list = null;
		IdAndName idAndName = null;
		if(null != activetradeMappingList && activetradeMappingList.size() > 0){
			list = new ArrayList<IdAndName>();
			for(Ftrademapping ftrademapping : activetradeMappingList){
				idAndName = new IdAndName();
				idAndName.setId(ftrademapping.getFid());
				idAndName.setName(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getfShortName());
				list.add(idAndName);
			}
		}
		jsonObject.accumulate(List, list);
		jsonObject.accumulate(Result, true) ;
		jsonObject.accumulate(ErrorCode , 0) ;
		return jsonObject.toString() ;
	}

	public static void main(String args[])  {
		double a = 0.00001;

		BigDecimal b = new BigDecimal(String.valueOf(a));
		System.out.print(b.toString());
	}
}
