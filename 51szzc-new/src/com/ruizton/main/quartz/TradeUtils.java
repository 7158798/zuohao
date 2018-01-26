package com.ruizton.main.quartz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.EntrustStatusEnum;
import com.ruizton.main.Enum.EntrustTypeEnum;
import com.ruizton.main.Enum.TrademappingStatusEnum;
import com.ruizton.main.auto.OneDayData;
import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.model.Fentrust;
import com.ruizton.main.model.Flimittrade;
import com.ruizton.main.model.Ftradehistory;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.service.admin.EntrustService;
import com.ruizton.main.service.admin.LimittradeService;
import com.ruizton.main.service.admin.TradeMappingService;
import com.ruizton.main.service.admin.TradehistoryService;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.front.FrontTradeService;
import com.ruizton.main.service.front.FtradeMappingService;
import com.ruizton.util.Utils;

public class TradeUtils {
	@Autowired
	private TradehistoryService tradehistoryService;
	@Autowired
	private RealTimeData realTimeData;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private LimittradeService limittradeService;
	@Autowired
	private ConstantMap map;
	@Autowired
	private OneDayData oneDayData;
	@Autowired
	private FrontTradeService frontTradeService;
	@Autowired
	private EntrustService entrustService;
	@Autowired
	private TradeMappingService tradeMappingService;
	
	
	public void work() {
		String sql = "where fstatus="+TrademappingStatusEnum.ACTIVE;
		List<Ftrademapping> mappings = this.tradeMappingService.list(0, 0, sql, false);
		for (Ftrademapping ftrademapping : mappings) {
			double price = Utils.getDouble(this.realTimeData.getLatestDealPrize(ftrademapping.getFid()),ftrademapping.getFcount1());
			Ftradehistory tradehistory = new Ftradehistory();
			tradehistory.setFdate(new Date());
			tradehistory.setFprice(price);
			tradehistory.setFtotal(this.oneDayData.get24Total(ftrademapping.getFid()));
			tradehistory.setFtrademapping(ftrademapping);
			this.tradehistoryService.saveObj(tradehistory);
		}
		
		List<Flimittrade> trades = this.limittradeService.list(0, 0, "", false);
		for (Flimittrade flimittrade : trades) {
			Ftrademapping ftrademapping = this.tradeMappingService.findById(flimittrade.getFtrademapping().getFid()) ;
			try {
				double price = Utils.getDouble(this.realTimeData.getLatestDealPrize(ftrademapping.getFid()),ftrademapping.getFcount1());
				flimittrade.setFdownprice(price);
				flimittrade.setFupprice(price);
				this.limittradeService.updateObj(flimittrade);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				//限价交易0点自动撤单
				String filter = "where (fstatus="+EntrustStatusEnum.Going+" or fstatus="+EntrustStatusEnum.PartDeal+") and ftrademapping.fid="+ftrademapping.getFid();
				List<Fentrust> fentrust = this.entrustService.list(0, 0, filter, false);
				for (Fentrust fentrust2 : fentrust) {
					if(fentrust2.getFstatus()==EntrustStatusEnum.Going || fentrust2.getFstatus()==EntrustStatusEnum.PartDeal){
						boolean flag = false ;
						try {
							this.frontTradeService.updateCancelFentrust(fentrust2, fentrust2.getFuser()) ;
							flag = true ;
						} catch (Exception e) {
							e.printStackTrace();
						}
						if(flag==true){
							if(fentrust2.getFentrustType()==EntrustTypeEnum.BUY){
								//买
								if(fentrust2.isFisLimit()){
									this.realTimeData.removeEntrustLimitBuyMap(fentrust2.getFtrademapping().getFid(), fentrust2) ;
								}else{
									this.realTimeData.removeEntrustBuyMap(fentrust2.getFtrademapping().getFid(), fentrust2) ;
								}
							}else{
								//卖
								if(fentrust2.isFisLimit()){
									this.realTimeData.removeEntrustLimitSellMap(fentrust2.getFtrademapping().getFid(), fentrust2) ;
								}else{
									this.realTimeData.removeEntrustSellMap(fentrust2.getFtrademapping().getFid(), fentrust2) ;
								}
								
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
//		try {
//			while(true){
//				//限价交易0点自动撤单
//				String filter = "where (fstatus="+EntrustStatusEnum.Going+" or fstatus="+EntrustStatusEnum.PartDeal+")";
//				List<Fentrust> fentrust = this.entrustService.list(0, 0, filter, false);
//				if(fentrust == null || fentrust.size() ==0) break;
//				for (Fentrust fentrust2 : fentrust) {
//					if(fentrust2.getFstatus()==EntrustStatusEnum.Going || fentrust2.getFstatus()==EntrustStatusEnum.PartDeal){
//						boolean flag = false ;
//						try {
//							this.frontTradeService.updateCancelFentrust(fentrust2, fentrust2.getFuser()) ;
//							flag = true ;
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//						if(flag==true){
//							if(fentrust2.getFentrustType()==EntrustTypeEnum.BUY){
//								//买
//								if(fentrust2.isFisLimit()){
//									this.realTimeData.removeEntrustLimitBuyMap(fentrust2.getFtrademapping().getFid(), fentrust2) ;
//								}else{
//									this.realTimeData.removeEntrustBuyMap(fentrust2.getFtrademapping().getFid(), fentrust2) ;
//								}
//							}else{
//								//卖
//								if(fentrust2.isFisLimit()){
//									this.realTimeData.removeEntrustLimitSellMap(fentrust2.getFtrademapping().getFid(), fentrust2) ;
//								}else{
//									this.realTimeData.removeEntrustSellMap(fentrust2.getFtrademapping().getFid(), fentrust2) ;
//								}
//								
//							}
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		
		{
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String key = sdf.format(c.getTime());
			String xx = "where DATE_FORMAT(fdate,'%Y-%m-%d') ='"+key+"'";
			List<Ftradehistory> ftradehistorys = this.tradehistoryService.list(0, 0, xx, false);
			map.put("tradehistory", ftradehistorys);
		}
		
	}
}