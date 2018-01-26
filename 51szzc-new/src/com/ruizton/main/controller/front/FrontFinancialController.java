package com.ruizton.main.controller.front;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.ruizton.main.Enum.EntrustTypeEnum;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.*;
import com.ruizton.main.model.integral.Fusergrade;
import com.ruizton.util.DateHelper;
import com.ruizton.util.JsonHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.BankTypeEnum;
import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.VirtualCoinTypeStatusEnum;
import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontVirtualCoinService;

@Controller
public class FrontFinancialController extends BaseController {


	/**
	 * 个人资产
	 * @param request
	 * @return
	 * @throws Exception
	 * @update 2017-03-06 增加盈亏计算
	 */
	@RequestMapping("/financial/index")
	public ModelAndView index(
			HttpServletRequest request
	) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
		modelAndView.addObject("fuser", fuser);
		modelAndView.setViewName("front/financial/index");

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
			return modelAndView;
		}

		//交易手续费
		BigDecimal tradefee = fusergrade.getTradefee();
//		LOG.i(this, "用户id=" + userId + ",等级=" + level + ",交易手续费=" + tradefee);


		//虚拟钱包
		Map<Integer, Fvirtualwallet> fvirtualwallets_map = this.frontUserService.findVirtualWallet(userId);
		if (fvirtualwallets_map == null) {
			return modelAndView;
		}

		//获取法币匹配信息
		List<Ftrademapping> ftrademappingList = ftradeMappingService.findActiveTradeMapping();
		if (ftrademappingList == null || ftrademappingList.size() == 0) {
			LOG.i(this, "获取法币匹配信息为空");
			return modelAndView;
		}

		//获取各个币种的市价(当前最新价)
		Map<Integer, Integer> point_map = new HashMap<>();  //小数点位数
		Map<Integer, Integer> num_map = new HashMap<>();  //数量小数位数
		Map<Integer, BigDecimal> price_map = new HashMap<>();  //价格map
		String fvi_fid_str = "";
		for (Ftrademapping ftrademapping : ftrademappingList) {
			int fid = ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid();
			fvi_fid_str += fid + ",";
			BigDecimal price = new BigDecimal(realTimeData.getLatestDealPrize(ftrademapping.getFid()) + "").setScale(ftrademapping.getFcount1(), BigDecimal.ROUND_DOWN);
			price_map.put(fid, price);
			point_map.put(fid, ftrademapping.getFcount1());
			num_map.put(fid, ftrademapping.getFcount2());
		}

		if (StringUtils.isNotBlank(fvi_fid_str)) {
			fvi_fid_str = fvi_fid_str.substring(0, fvi_fid_str.length() - 1);
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

//		LOG.i(this, "开始时间=" + startTime + ",结束时间=" + endTime);


		//查询所有币种15点的数量×价格信息
		Map<Integer, Ftimewallet> start_point_price_map = this.ftimewalletService.queryByUserAndTime(userId, startTime, fvi_fid_str);
		Map<Integer, Ftimewallet> end_point_price_map = this.ftimewalletService.queryByUserAndTime(userId, endTime, fvi_fid_str);

		Map<Integer, FentrustlogVo> buyMap = this.frontOthersService.queryLogByTime(startTime, endTime, EntrustTypeEnum.BUY, userId, tradefee, fvi_fid_str);
		Map<Integer, FentrustlogVo> sellMap = this.frontOthersService.queryLogByTime(startTime, endTime, EntrustTypeEnum.SELL, userId, tradefee, fvi_fid_str);


		DecimalFormat decimalFormat = new DecimalFormat("#.####");
		//计算
		List<FvirtualwalletVo> fvirtualwalletVoList = new ArrayList<>();
		for (Map.Entry<Integer, Fvirtualwallet> wallet : fvirtualwallets_map.entrySet()) {
			Integer fvi_fid = wallet.getKey();
			Integer price_point = point_map.get(fvi_fid);  //小数点位数
			Integer num_point = num_map.get(fvi_fid);  //数量小数点
//			LOG.i(this, "虚拟币id= "+ fvi_fid);
			FvirtualwalletVo fvirtualwalletVo = new FvirtualwalletVo();
			fvirtualwalletVo.setFviFid(fvi_fid);
			fvirtualwalletVo.setFviName(wallet.getValue().getFvirtualcointype().getFname());
			fvirtualwalletVo.setShortName(wallet.getValue().getFvirtualcointype().getfShortName());
			fvirtualwalletVo.setFfrozen(new BigDecimal(wallet.getValue().getFfrozen() + "").setScale(num_point, BigDecimal.ROUND_DOWN));
			fvirtualwalletVo.setFable(new BigDecimal(wallet.getValue().getFtotal() + "").setScale(num_point, BigDecimal.ROUND_DOWN));  //数据库total是可用
			fvirtualwalletVo.setFtotal(fvirtualwalletVo.getFable().add(fvirtualwalletVo.getFfrozen()).setScale(num_point, BigDecimal.ROUND_DOWN));
			fvirtualwalletVo.setFprice(price_map.get(fvi_fid));
			//总市价保留2位小数
			fvirtualwalletVo.setFtotalPrice(fvirtualwalletVo.getFtotal().multiply(fvirtualwalletVo.getFprice()).setScale(2, BigDecimal.ROUND_DOWN)); //总市价=总持有量*最新价格
			//小数点后如果是0，则不显示
			fvirtualwalletVo.setFtotalPrice(new BigDecimal(decimalFormat.format(fvirtualwalletVo.getFtotalPrice())));

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


//				LOG.i(this, "区间范围内买入信息："+JsonHelper.obj2JsonStr(buyList));
//				LOG.i(this, "区间范围内卖出信息："+JsonHelper.obj2JsonStr(sellList));
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
//				LOG.i(this, "买进交易额：" + buy_price_sum + "    卖出交易额：" + sell_price_sum + "  交易手续费：" + trade_fees + "买进数量：" + buy_count);
				BigDecimal temp1 = (end_point_price.multiply(end_point_num)).add(sell_price_sum);
//				LOG.i(this, "卖出收益："+temp1);
				BigDecimal temp2 = start_point_price.multiply(start_point_num).add(buy_price_sum);
//				LOG.i(this, "买进支出："+temp2);

				profit_loss = temp1.subtract(temp2).subtract(trade_fees).setScale(2, BigDecimal.ROUND_DOWN);
				//如果temp2=0
				if (temp2.compareTo(BigDecimal.ZERO) == 1) {
					profit_loss_rate = profit_loss.divide(temp2, 4, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).setScale(2);
				}
				if (start_point_num.compareTo(BigDecimal.ZERO) == 1 || buy_count.compareTo(BigDecimal.ZERO) == 1) {
					cost_price = temp2.divide((start_point_num.add(buy_count)), 2, BigDecimal.ROUND_DOWN);
				}

			}

			fvirtualwalletVo.setFprofitLoss(profit_loss);
			fvirtualwalletVo.setFprofitLossRate(profit_loss_rate);
			fvirtualwalletVo.setFcostPrice(cost_price);


			fvirtualwalletVoList.add(fvirtualwalletVo);
		}
		modelAndView.addObject("fvirtualwalletVoList", fvirtualwalletVoList);
		return modelAndView;
	}

	/**
	 * 获取用户绑定的银行卡或支付宝信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/financial/accountbank")
	public ModelAndView accountbank(
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") int type
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.queryById(GetSession(request).getFid()) ;
		modelAndView.addObject("fuser", fuser) ;

		//获取用户绑定的银行卡信息
		if(type == 0) {
			Map<Integer,String> bankTypes = new HashMap<Integer,String>() ;
			for(int i=1;i<=BankTypeEnum.QT;i++){
				if(BankTypeEnum.getEnumString(i) != null && BankTypeEnum.getEnumString(i).trim().length() >0){
					bankTypes.put(i,BankTypeEnum.getEnumString(i)) ;
				}
			}
			modelAndView.addObject("bankTypes", bankTypes) ;

			String filter = "where fuser.fid="+fuser.getFid()+" and fbankType >0";
			List<FbankinfoWithdraw> bankinfos = this.frontUserService.findFbankinfoWithdrawByFuser(0, 0, filter, false);
			for (FbankinfoWithdraw fbankinfoWithdraw : bankinfos) {
				try {
					int length = fbankinfoWithdraw.getFbankNumber().length();
					String number = "**** **** **** "+fbankinfoWithdraw.getFbankNumber().substring(length-4,length);
					fbankinfoWithdraw.setFbankNumber(number);
				} catch (Exception e) {}
			}
			modelAndView.addObject("bankinfos", bankinfos) ;
		}else if(type == 1) {  //获取用户绑定的银行卡信息
			//用户绑定的支付宝账户信息
			List<FalipayBind> alipaybind_list = falipayBindService.findListByUserId(fuser.getFid());
			modelAndView.addObject("alipaybind_list",alipaybind_list) ;
		}

		
		boolean isBindGoogle = fuser.getFgoogleBind() ;
		boolean isBindTelephone = fuser.isFisTelephoneBind() ;
		modelAndView.addObject("isBindGoogle", isBindGoogle) ;
        modelAndView.addObject("isBindTelephone", isBindTelephone) ;
        modelAndView.addObject("type", type) ;
        modelAndView.addObject("bank_account_address1", i18nMsg("bank_account_address1"));
		modelAndView.setViewName("front/financial/accountbank") ;
		return modelAndView ;
	}
/*	
	@RequestMapping("/financial/accountalipay")
	public ModelAndView accountalipay(
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		modelAndView.addObject("fuser", fuser) ;
		
		String filter = "where fuser.fid="+fuser.getFid()+" and fbankType =0";
		List<FbankinfoWithdraw> bankinfos = this.frontUserService.findFbankinfoWithdrawByFuser(0, 0, filter, false);
		for (FbankinfoWithdraw fbankinfoWithdraw : bankinfos) {
			try {
				int length = fbankinfoWithdraw.getFbankNumber().length();
				String number = fbankinfoWithdraw.getFbankNumber().substring(0,3)+"****"+fbankinfoWithdraw.getFbankNumber().substring(length-4,length);
				fbankinfoWithdraw.setFbankNumber(number);
			} catch (Exception e) {}
		}
		modelAndView.addObject("bankinfos", bankinfos) ;
		
		boolean isBindGoogle = fuser.getFgoogleBind() ;
		boolean isBindTelephone = fuser.isFisTelephoneBind() ;
		modelAndView.addObject("isBindGoogle", isBindGoogle) ;
        modelAndView.addObject("isBindTelephone", isBindTelephone) ;
		
		
		modelAndView.setViewName("front/financial/accountalipay") ;
		return modelAndView ;
	}*/
	
	@RequestMapping("/financial/accountcoin")
	public ModelAndView accountcoin(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int symbol
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		if(fvirtualcointype==null ||fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal
				||fvirtualcointype.getFtype()==CoinTypeEnum.FB_CNY_VALUE
				 || !fvirtualcointype.isFIsWithDraw()){
			String filter = "where fstatus=1 and FIsWithDraw=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE+" order by fid asc";
			List<Fvirtualcointype> alls = this.virtualCoinService.list(0, 1, filter, true);
			if(alls==null || alls.size() ==0){
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
			fvirtualcointype = alls.get(0);
		}
		symbol = fvirtualcointype.getFid();
		String coinName = fvirtualcointype.getfShortName();
		
		String filter = "where fuser.fid="+fuser.getFid()+" and fvirtualcointype.fid="+symbol;
		List<FvirtualaddressWithdraw> alls = this.frontVirtualCoinService.findFvirtualaddressWithdraws(0, 0, filter, false);
		modelAndView.addObject("alls", alls) ;
		
		boolean isBindGoogle = fuser.getFgoogleBind() ;
		boolean isBindTelephone = fuser.isFisTelephoneBind() ;
		modelAndView.addObject("isBindGoogle", isBindGoogle) ;
        modelAndView.addObject("isBindTelephone", isBindTelephone) ;
		
		modelAndView.addObject("fuser", fuser) ;
		modelAndView.addObject("symbol", symbol) ;
		modelAndView.addObject("coinName", coinName) ;
		modelAndView.setViewName("front/financial/accountcoin") ;
		return modelAndView ;
	}
}
