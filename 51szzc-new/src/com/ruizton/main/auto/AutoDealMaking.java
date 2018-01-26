package com.ruizton.main.auto;

import java.util.List;

import com.ruizton.main.model.integral.Fusergrade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.EntrustStatusEnum;
import com.ruizton.main.Enum.EntrustTypeEnum;
import com.ruizton.main.Enum.TrademappingStatusEnum;
import com.ruizton.main.model.Fentrust;
import com.ruizton.main.model.Fentrustlog;
import com.ruizton.main.model.Ffees;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.service.front.FrontTradeService;
import com.ruizton.main.service.front.UtilsService;
import com.ruizton.util.Utils;

public class AutoDealMaking {
	private static final Logger log = LoggerFactory .getLogger(AutoDealMaking.class);
	
	@Autowired
	private RealTimeData realTimeData;
	@Autowired
	private FrontTradeService frontTradeService;
	@Autowired
	private UtilsService utilsService ;
	
	public void init() {
		List<Fusergrade> fusergradeList = this.utilsService.list(0, 0, "", false, Fusergrade.class) ;
		for (Fusergrade fusergrade : fusergradeList) {
			this.frontTradeService.putRates(fusergrade.getFid(), fusergrade.getTradefee().doubleValue()) ;
		}
		
		while(this.realTimeData.getMisInit() == false ){
			try {
				Thread.sleep(10L) ;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		new Work().run() ;
	}

	class Work  {
		public void run() {


				try {
					String filter = "where fstatus="+TrademappingStatusEnum.ACTIVE;
					List<Ftrademapping> alls = AutoDealMaking.this.utilsService.list1(0, 0, filter, false,Ftrademapping.class);
					if (alls != null && alls.size() >0) {
						for (final Ftrademapping ftrademapping : alls) {
							try {
								Thread thread = new Thread(new Runnable() {
									public void run() {
										while(true ){
											try {
												try {
													limitBuyDealMaking(ftrademapping) ;
												} catch (Exception e) {
													e.printStackTrace();
												}
												try {
													limitSellDealMaking(ftrademapping) ;
												} catch (Exception e) {
													e.printStackTrace();
												}
												try {
													// 优先内部撮合
													insideDealMaking(ftrademapping);
													dealMaking(ftrademapping);
												} catch (Exception e) {
													e.printStackTrace();
												}
												
												try {
													limitBuySellDealMaking(ftrademapping);
												} catch (Exception e) {
													e.printStackTrace();
												}
											} catch (Exception e) {
												e.printStackTrace();
											}
											
											try {
												Thread.sleep(1L) ;
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
										}
										
									}
								}) ;
								thread.setPriority(Thread.MAX_PRIORITY) ;
								thread.start() ;
							} catch (Exception e) {
								 e.printStackTrace();
							}
						}
					}

				} catch (Exception e) {
					 e.printStackTrace() ;
				}
				
		}
	}

		private void limitSellDealMaking(Ftrademapping ftrademapping) {
			int id = ftrademapping.getFid() ;
			boolean rehandle = false;

			Fentrust[] sellLimitFentrusts = realTimeData
					.getEntrustLimitSellMap(id,1) ;
			Fentrust[] buyFentrusts = realTimeData.getEntrustBuyMap(id,1) ;

			if (sellLimitFentrusts.length > 0 && buyFentrusts.length > 0) {

				first: for (int i = 0; i < sellLimitFentrusts.length; i++) {
					Fentrust sell = (Fentrust) sellLimitFentrusts[i];

					if (sell.getFstatus() == EntrustStatusEnum.AllDeal
							|| sell.getFstatus() == EntrustStatusEnum.Cancel) {
						realTimeData.removeEntrustLimitSellMap(id, sell);
						continue;
					}

					for (int j = 0; j < buyFentrusts.length; j++) {
						Fentrust buy = (Fentrust) buyFentrusts[j];

						if (buy.getFstatus() == EntrustStatusEnum.AllDeal
								|| buy.getFstatus() == EntrustStatusEnum.Cancel) {
							realTimeData.removeEntrustBuyMap(id, buy);
							continue;
						}

						boolean isbuy =false;
						boolean issell =false;
						if(buy.getFid().intValue() > sell.getFid().intValue()){
							isbuy = true;
						}else if(buy.getFid().intValue() < sell.getFid().intValue()){
							issell = true;
						}else{
							issell = true;
						}

						double sellPrize = buy.getFprize();
						double sellCount = buy.getFleftCount();
						sellCount = sellCount > sell.getFleftCount() ? sell
								.getFleftCount() : sellCount;

						Fentrustlog buyFentrustlog = new Fentrustlog();
						buyFentrustlog.setFamount(sellCount * sellPrize);
						buyFentrustlog.setFcount(sellCount);
						buyFentrustlog.setFcreateTime(Utils.getTimestamp());
						buyFentrustlog.setFprize(sellPrize);
						buyFentrustlog.setIsactive(isbuy);
						buyFentrustlog.setFentrust(buy);
						buyFentrustlog.setfEntrustType(EntrustTypeEnum.BUY);
						buyFentrustlog.setFtrademapping(ftrademapping) ;

						Fentrustlog sellFentrustlog = new Fentrustlog();
						sellFentrustlog.setFamount(sellCount * sellPrize);
						sellFentrustlog.setFcount(sellCount);
						sellFentrustlog.setFcreateTime(Utils.getTimestamp());
						sellFentrustlog.setFprize(sellPrize);
						sellFentrustlog.setIsactive(issell);
						sellFentrustlog.setFentrust(sell);
						sellFentrustlog.setfEntrustType(EntrustTypeEnum.SELL);
						sellFentrustlog.setFtrademapping(ftrademapping) ;

						boolean ret = false;
						try {
							frontTradeService.updateDealMaking(ftrademapping,buy, sell,
									buyFentrustlog, sellFentrustlog, id);
							ret = true;
							//交易添加积分 --
							this.realTimeData.addIntegral(buyFentrustlog);
							this.realTimeData.addIntegral(sellFentrustlog);
						} catch (Exception e) {
						}

						if (ret) {
							realTimeData.addEntrustSuccessMap(id,
									sellFentrustlog);
							realTimeData.addEntrustSuccessMap(id,
									buyFentrustlog);
							

							if (buy.getFstatus() == EntrustStatusEnum.Going
									|| buy.getFstatus() == EntrustStatusEnum.PartDeal) {
								if (buy.isFisLimit()) {
									realTimeData.addEntrustLimitBuyMap(id,
											buy);
								} else {
									realTimeData.addEntrustBuyMap(id, buy);
								}
							} else if (buy.getFstatus() == EntrustStatusEnum.AllDeal) {
								if (buy.isFisLimit()) {
									realTimeData.removeEntrustLimitBuyMap(
											id, buy);
								} else {
									realTimeData.removeEntrustBuyMap(id,
											buy);
								}

							}

							if (sell.getFstatus() == EntrustStatusEnum.Going
									|| sell.getFstatus() == EntrustStatusEnum.PartDeal) {
								if (sell.isFisLimit()) {
									realTimeData.addEntrustLimitSellMap(
											id, sell);
								} else {
									realTimeData.addEntrustSellMap(id,
											sell);
								}
							} else if (sell.getFstatus() == EntrustStatusEnum.AllDeal) {
								if (sell.isFisLimit()) {
									realTimeData
											.removeEntrustLimitSellMap(id, sell);
								} else {
									realTimeData.removeEntrustSellMap(id,
											sell);
								}

							}

							rehandle = true;
							
						} else {
							buy = frontTradeService.findFentrustById(buy
									.getFid());
							sell = frontTradeService.findFentrustById(sell
									.getFid());

							if (buy == null || sell == null) {
								log.error("buy or sell null;");
								continue;
							}

							if (buy.getFstatus() == EntrustStatusEnum.Going
									|| buy.getFstatus() == EntrustStatusEnum.PartDeal) {
								realTimeData.addEntrustBuyMap(id, buy);
							} else {
								realTimeData.removeEntrustBuyMap(id, buy);
							}
							if (sell.getFstatus() == EntrustStatusEnum.Going
									|| sell.getFstatus() == EntrustStatusEnum.PartDeal) {
								realTimeData.addEntrustLimitSellMap(id,
										sell);
							} else {
								realTimeData.removeEntrustLimitSellMap(id,
										sell);
							}
						}

						rehandle = true;

						break first;
					}
				}

				if (rehandle) {
					limitSellDealMaking(ftrademapping);
				}
			}

		}

		private void limitBuyDealMaking(Ftrademapping ftrademapping) {
			int id = ftrademapping.getFid() ;
			boolean rehandle = false;
			
			Fentrust[] buyLimitFentrusts = realTimeData
					.getEntrustLimitBuyMap(id,1) ;
			Fentrust[] sellFentrusts = realTimeData.getEntrustSellMap(id,1) ;
			if (buyLimitFentrusts.length > 0 && sellFentrusts.length > 0) {

				first: for (int i = 0; i < buyLimitFentrusts.length; i++) {
					Fentrust buy = (Fentrust) buyLimitFentrusts[i];

					if (buy.getFstatus() == EntrustStatusEnum.AllDeal
							|| buy.getFstatus() == EntrustStatusEnum.Cancel) {
						realTimeData.removeEntrustLimitBuyMap(id, buy);
						continue;
					}

					for (int j = 0; j < sellFentrusts.length; j++) {
						Fentrust sell = (Fentrust) sellFentrusts[j];

						if (sell.getFstatus() == EntrustStatusEnum.AllDeal
								|| sell.getFstatus() == EntrustStatusEnum.Cancel) {
							realTimeData.removeEntrustSellMap(id, sell);
							continue;
						}

						boolean isbuy =false;
						boolean issell =false;
						if(buy.getFid().intValue() > sell.getFid().intValue()){
							isbuy = true;
						}else if(buy.getFid().intValue() < sell.getFid().intValue()){
							issell = true;
						}else{
							issell = true;
						}

						double buyPrize = sell.getFprize();
						double buyCount = (buy.getFamount() - buy
								.getFsuccessAmount()) / buyPrize;
						buyCount = buyCount > sell.getFleftCount() ? sell
								.getFleftCount() : buyCount;

						buyCount = Utils.getDouble(buyCount, ftrademapping.getFcount2());


						Fentrustlog buyFentrustlog = new Fentrustlog();
						buyFentrustlog.setFamount(buyCount * buyPrize);
						buyFentrustlog.setFcount(buyCount);
						buyFentrustlog.setFcreateTime(Utils.getTimestamp());
						buyFentrustlog.setFprize(buyPrize);
						buyFentrustlog.setIsactive(isbuy);
						buyFentrustlog.setFentrust(buy);
						buyFentrustlog.setfEntrustType(EntrustTypeEnum.BUY);
						buyFentrustlog.setFtrademapping(ftrademapping) ;

						Fentrustlog sellFentrustlog = new Fentrustlog();
						sellFentrustlog.setFamount(buyCount * buyPrize);
						sellFentrustlog.setFcount(buyCount);
						sellFentrustlog.setFcreateTime(Utils.getTimestamp());
						sellFentrustlog.setFprize(buyPrize);
						sellFentrustlog.setIsactive(issell);
						sellFentrustlog.setFentrust(sell);
						sellFentrustlog.setfEntrustType(EntrustTypeEnum.SELL);
						sellFentrustlog.setFtrademapping(ftrademapping) ;

						boolean ret = false;
						try {
							frontTradeService.updateDealMaking(ftrademapping,buy, sell,
									buyFentrustlog, sellFentrustlog, id);
							ret = true;

							//交易添加积分 --
							this.realTimeData.addIntegral(buyFentrustlog);
							this.realTimeData.addIntegral(sellFentrustlog);
						} catch (Exception e) {
						}

						if (ret) {
							realTimeData.addEntrustSuccessMap(id,
									sellFentrustlog);
							realTimeData.addEntrustSuccessMap(id,
									buyFentrustlog);
							

							if (buy.getFstatus() == EntrustStatusEnum.Going
									|| buy.getFstatus() == EntrustStatusEnum.PartDeal) {
								if (buy.isFisLimit()) {
									realTimeData.addEntrustLimitBuyMap(id,
											buy);
								} else {
									realTimeData.addEntrustBuyMap(id, buy);
								}
							} else if (buy.getFstatus() == EntrustStatusEnum.AllDeal) {
								if (buy.isFisLimit()) {
									realTimeData.removeEntrustLimitBuyMap(
											id, buy);
								} else {
									realTimeData.removeEntrustBuyMap(id,
											buy);
								}

							}

							if (sell.getFstatus() == EntrustStatusEnum.Going
									|| sell.getFstatus() == EntrustStatusEnum.PartDeal) {
								if (sell.isFisLimit()) {
									realTimeData.addEntrustLimitSellMap(
											id, sell);
								} else {
									realTimeData.addEntrustSellMap(id,
											sell);
								}
							} else if (sell.getFstatus() == EntrustStatusEnum.AllDeal) {
								if (sell.isFisLimit()) {
									realTimeData
											.removeEntrustLimitSellMap(id, sell);
								} else {
									realTimeData.removeEntrustSellMap(id,
											sell);
								}

							}

							rehandle = true;
							
						} else {
							buy = frontTradeService.findFentrustById(buy
									.getFid());
							sell = frontTradeService.findFentrustById(sell
									.getFid());

							if (buy == null || sell == null) {
								log.error("buy or sell null;");
								continue;
							}

							if (buy.getFstatus() == EntrustStatusEnum.Going
									|| buy.getFstatus() == EntrustStatusEnum.PartDeal) {
								realTimeData
										.addEntrustLimitBuyMap(id, buy);
							} else {
								realTimeData.removeEntrustLimitBuyMap(id,
										buy);
							}
							if (sell.getFstatus() == EntrustStatusEnum.Going
									|| sell.getFstatus() == EntrustStatusEnum.PartDeal) {
								realTimeData.addEntrustSellMap(id, sell);
							} else {
								realTimeData
										.removeEntrustSellMap(id, sell);
							}
						}

						rehandle = true;

						break first;
					}
				}

				if (rehandle) {
					limitBuyDealMaking(ftrademapping);
				}
			}

		}


		private void insideDealMaking(Ftrademapping ftrademapping){

			int id = ftrademapping.getFid();
			boolean rehandle = Boolean.TRUE;
			while (rehandle){
				rehandle = Boolean.FALSE;
				Fentrust[] buyFentrusts = realTimeData.getInsideEntrustBuyMap(id,1) ;
				Fentrust[] sellFentrusts = realTimeData.getInsideEntrustSellMap(id,1) ;
				if (buyFentrusts.length > 0 && sellFentrusts.length > 0) {

					first: for (int i = 0; i < buyFentrusts.length; i++) {
						Fentrust buy = (Fentrust) buyFentrusts[i];

						if (buy.getFstatus() == EntrustStatusEnum.AllDeal
								|| buy.getFstatus() == EntrustStatusEnum.Cancel) {
							realTimeData.removeInsideEntrustBuyMap(id, buy);
							continue;
						}

						second: for (int j = 0; j < sellFentrusts.length; j++) {
							Fentrust sell = (Fentrust) sellFentrusts[j];

							if (sell.getFstatus() == EntrustStatusEnum.AllDeal
									|| sell.getFstatus() == EntrustStatusEnum.Cancel) {
								realTimeData.removeInsideEntrustSellMap(id, sell);
								continue;
							}

							if (buy.getFprize() < sell.getFprize()) {
								continue;
							}

							double buyCount = buy.getFleftCount();
							buyCount = buyCount > sell.getFleftCount() ? sell
									.getFleftCount() : buyCount;
							// 使用卖价撮合
							double buyPrize;
							if (buy.getFid().intValue() < sell.getFid().intValue()) {
								buyPrize = buy.getFprize();
							} else {
								buyPrize = sell.getFprize();
							}

							if (buyCount > buy.getFleftCount()
									|| buyCount > sell.getFleftCount()
									|| buyPrize > buy.getFprize()) {
								log.error("insideDealMaking error!");
								return;
							}

							boolean isbuy =false;
							boolean issell =false;
							if(buy.getFid().intValue() > sell.getFid().intValue()){
								isbuy = true;
							}else if(buy.getFid().intValue() < sell.getFid().intValue()){
								issell = true;
							}else{
								issell = true;
							}

							Fentrustlog buyFentrustlog = new Fentrustlog();
							buyFentrustlog.setFamount(buyCount * buyPrize);
							buyFentrustlog.setFcount(buyCount);
							buyFentrustlog.setFcreateTime(Utils.getTimestamp());
							buyFentrustlog.setFprize(buyPrize);
							buyFentrustlog.setIsactive(isbuy);
							buyFentrustlog.setFentrust(buy);
							buyFentrustlog.setfEntrustType(EntrustTypeEnum.BUY);
							buyFentrustlog.setFtrademapping(ftrademapping) ;

							Fentrustlog sellFentrustlog = new Fentrustlog();
							sellFentrustlog.setFamount(buyCount * buyPrize);
							sellFentrustlog.setFcount(buyCount);
							sellFentrustlog.setFcreateTime(Utils.getTimestamp());
							sellFentrustlog.setFprize(buyPrize);
							sellFentrustlog.setIsactive(issell);
							sellFentrustlog.setFentrust(sell);
							sellFentrustlog.setfEntrustType(EntrustTypeEnum.SELL);
							sellFentrustlog.setFtrademapping(ftrademapping) ;

							boolean ret = false;
							try {
								frontTradeService.updateDealMaking(ftrademapping,buy, sell,
										buyFentrustlog, sellFentrustlog, id);
								ret = true;

								//交易添加积分 --
								this.realTimeData.addIntegral(buyFentrustlog);
								this.realTimeData.addIntegral(sellFentrustlog);
							} catch (Exception e) {
							}

							if (ret) {
								realTimeData.addEntrustSuccessMap(id,
										sellFentrustlog);
								realTimeData.addEntrustSuccessMap(id,
										buyFentrustlog);

								if (buy.getFstatus() == EntrustStatusEnum.Going
										|| buy.getFstatus() == EntrustStatusEnum.PartDeal) {
									realTimeData.addInsideEntrustBuyMap(id, buy);
								} else if (buy.getFstatus() == EntrustStatusEnum.AllDeal) {
									realTimeData.removeInsideEntrustBuyMap(id, buy);

								}

								if (sell.getFstatus() == EntrustStatusEnum.Going
										|| sell.getFstatus() == EntrustStatusEnum.PartDeal) {
									realTimeData.addInsideEntrustSellMap(id, sell);
								} else if (sell.getFstatus() == EntrustStatusEnum.AllDeal) {
									realTimeData.removeInsideEntrustSellMap(id, sell);
								}
								rehandle = true;
							} else {
								buy = frontTradeService.findFentrustById(buy
										.getFid());
								sell = frontTradeService.findFentrustById(sell
										.getFid());

								if (buy == null || sell == null) {
									log.error("buy or sell null;");
									continue;
								}

								if (buy.getFstatus() == EntrustStatusEnum.Going
										|| buy.getFstatus() == EntrustStatusEnum.PartDeal) {
									realTimeData.addInsideEntrustBuyMap(id, buy);
								} else {
									realTimeData.removeInsideEntrustBuyMap(id, buy);
								}
								if (sell.getFstatus() == EntrustStatusEnum.Going
										|| sell.getFstatus() == EntrustStatusEnum.PartDeal) {
									realTimeData.addInsideEntrustSellMap(id, sell);
								} else {
									realTimeData.removeInsideEntrustSellMap(id, sell);
								}
							}

							break first;
						}
					}
				}
			}

		}
		private void dealMaking(Ftrademapping ftrademapping) {
			int id = ftrademapping.getFid() ;
			boolean rehandle = false;

			Fentrust[] buyFentrusts = realTimeData.getEntrustBuyMap(id,1) ;
			Fentrust[] sellFentrusts = realTimeData.getEntrustSellMap(id,1) ;
			if (buyFentrusts.length > 0 && sellFentrusts.length > 0) {

				first: for (int i = 0; i < buyFentrusts.length; i++) {
					Fentrust buy = (Fentrust) buyFentrusts[i];

					if (buy.getFstatus() == EntrustStatusEnum.AllDeal
							|| buy.getFstatus() == EntrustStatusEnum.Cancel) {
						realTimeData.removeEntrustBuyMap(id, buy);
						continue;
					}

					second: for (int j = 0; j < sellFentrusts.length; j++) {
						Fentrust sell = (Fentrust) sellFentrusts[j];

						if (sell.getFstatus() == EntrustStatusEnum.AllDeal
								|| sell.getFstatus() == EntrustStatusEnum.Cancel) {
							realTimeData.removeEntrustSellMap(id, sell);
							continue;
						}

						if (buy.getFprize() < sell.getFprize()) {
							continue;
						}

						double buyCount = buy.getFleftCount();
						buyCount = buyCount > sell.getFleftCount() ? sell
								.getFleftCount() : buyCount;
						// 时间优先原则
						double buyPrize;
						if (buy.getFid().intValue() < sell.getFid().intValue()) {
							buyPrize = buy.getFprize();
						} else {
							buyPrize = sell.getFprize();
						}

						if (buyCount > buy.getFleftCount()
								|| buyCount > sell.getFleftCount()
								|| buyPrize > buy.getFprize()) {
							log.error("dealmaking error!");
							return;
						}
						
						boolean isbuy =false;
						boolean issell =false;
						if(buy.getFid().intValue() > sell.getFid().intValue()){
							isbuy = true;
						}else if(buy.getFid().intValue() < sell.getFid().intValue()){
							issell = true;
						}else{
							issell = true;
						}
						
						Fentrustlog buyFentrustlog = new Fentrustlog();
						buyFentrustlog.setFamount(buyCount * buyPrize);
						buyFentrustlog.setFcount(buyCount);
						buyFentrustlog.setFcreateTime(Utils.getTimestamp());
						buyFentrustlog.setFprize(buyPrize);
						buyFentrustlog.setIsactive(isbuy);
						buyFentrustlog.setFentrust(buy);
						buyFentrustlog.setfEntrustType(EntrustTypeEnum.BUY);
						buyFentrustlog.setFtrademapping(ftrademapping) ;

						Fentrustlog sellFentrustlog = new Fentrustlog();
						sellFentrustlog.setFamount(buyCount * buyPrize);
						sellFentrustlog.setFcount(buyCount);
						sellFentrustlog.setFcreateTime(Utils.getTimestamp());
						sellFentrustlog.setFprize(buyPrize);
						sellFentrustlog.setIsactive(issell);
						sellFentrustlog.setFentrust(sell);
						sellFentrustlog.setfEntrustType(EntrustTypeEnum.SELL);
						sellFentrustlog.setFtrademapping(ftrademapping) ;

						boolean ret = false;
						try {
							frontTradeService.updateDealMaking(ftrademapping,buy, sell,
									buyFentrustlog, sellFentrustlog, id);
							ret = true;
							//交易添加积分 --
							this.realTimeData.addIntegral(buyFentrustlog);
							this.realTimeData.addIntegral(sellFentrustlog);
						} catch (Exception e) {
						}

						if (ret) {
							realTimeData.addEntrustSuccessMap(id,
									sellFentrustlog);
							realTimeData.addEntrustSuccessMap(id,
									buyFentrustlog);
							
							if (buy.getFstatus() == EntrustStatusEnum.Going
									|| buy.getFstatus() == EntrustStatusEnum.PartDeal) {
								if (buy.isFisLimit()) {
									realTimeData.addEntrustLimitBuyMap(id,
											buy);
								} else {
									realTimeData.addEntrustBuyMap(id, buy);
								}
							} else if (buy.getFstatus() == EntrustStatusEnum.AllDeal) {
								if (buy.isFisLimit()) {
									realTimeData.removeEntrustLimitBuyMap(
											id, buy);
								} else {
									realTimeData.removeEntrustBuyMap(id,
											buy);
								}

							}

							if (sell.getFstatus() == EntrustStatusEnum.Going
									|| sell.getFstatus() == EntrustStatusEnum.PartDeal) {
								if (sell.isFisLimit()) {
									realTimeData.addEntrustLimitSellMap(
											id, sell);
								} else {
									realTimeData.addEntrustSellMap(id,
											sell);
								}
							} else if (sell.getFstatus() == EntrustStatusEnum.AllDeal) {
								if (sell.isFisLimit()) {
									realTimeData
											.removeEntrustLimitSellMap(id, sell);
								} else {
									realTimeData.removeEntrustSellMap(id,
											sell);
								}

							}

							rehandle = true;
						} else {
							buy = frontTradeService.findFentrustById(buy
									.getFid());
							sell = frontTradeService.findFentrustById(sell
									.getFid());

							if (buy == null || sell == null) {
								log.error("buy or sell null;");
								continue;
							}

							if (buy.getFstatus() == EntrustStatusEnum.Going
									|| buy.getFstatus() == EntrustStatusEnum.PartDeal) {
								realTimeData.addEntrustBuyMap(id, buy);
							} else {
								realTimeData.removeEntrustBuyMap(id, buy);
							}
							if (sell.getFstatus() == EntrustStatusEnum.Going
									|| sell.getFstatus() == EntrustStatusEnum.PartDeal) {
								realTimeData.addEntrustSellMap(id, sell);
							} else {
								realTimeData
										.removeEntrustSellMap(id, sell);
							}
						}

						break first;
					}
				}
				if (rehandle) {
					dealMaking(ftrademapping);
				}

			}
	}

		private void limitBuySellDealMaking(Ftrademapping ftrademapping) {

			int id = ftrademapping.getFid() ;
			boolean rehandle = false;
			Fentrust[] buyLimitFentrusts = realTimeData
					.getEntrustLimitBuyMap(id,1) ;
			Fentrust[] sellLimitFentrusts = realTimeData.getEntrustLimitSellMap(id,1) ;
			
			
			Fentrust[] buys = realTimeData
					.getEntrustBuyMap(id,1) ;
			Fentrust[] sells = realTimeData.getEntrustSellMap(id,1) ;
			
			
			if ( buys.length==0&&sells.length==0&& buyLimitFentrusts.length > 0 && sellLimitFentrusts.length > 0) {

					Fentrust buy = (Fentrust) buyLimitFentrusts[0];
					Fentrust sell = (Fentrust) sellLimitFentrusts[0];
					

					if (buy.getFstatus() == EntrustStatusEnum.AllDeal
							|| buy.getFstatus() == EntrustStatusEnum.Cancel
							) {
						realTimeData.removeEntrustLimitBuyMap(id, buy);
						return;
					}
					if (sell.getFstatus() == EntrustStatusEnum.AllDeal
							|| sell.getFstatus() == EntrustStatusEnum.Cancel
							) {
						realTimeData.removeEntrustLimitSellMap(id, sell);
						return;
					}

					double buyPrize = this.realTimeData.getLatestDealPrize(id);
					double buyCount = (buy.getFamount() - buy
							.getFsuccessAmount()) / buyPrize;
					buyCount = buyCount > sell.getFleftCount() ? sell
							.getFleftCount() : buyCount;

				    buyCount = Utils.getDouble(buyCount,ftrademapping.getFcount2());
					
					boolean isbuy =false;
					boolean issell =false;
					if(buy.getFid().intValue() > sell.getFid().intValue()){
						isbuy = true;
					}else if(buy.getFid().intValue() < sell.getFid().intValue()){
						issell = true;
					}else{
						issell = true;
					}

					Fentrustlog buyFentrustlog = new Fentrustlog();
					buyFentrustlog.setFamount(buyCount * buyPrize);
					buyFentrustlog.setFcount(buyCount);
					buyFentrustlog.setFcreateTime(Utils.getTimestamp());
					buyFentrustlog.setFprize(buyPrize);
					buyFentrustlog.setIsactive(isbuy);
					buyFentrustlog.setFentrust(buy);
					buyFentrustlog.setfEntrustType(EntrustTypeEnum.BUY);
					buyFentrustlog.setFtrademapping(ftrademapping) ;

					Fentrustlog sellFentrustlog = new Fentrustlog();
					sellFentrustlog.setFamount(buyCount * buyPrize);
					sellFentrustlog.setFcount(buyCount);
					sellFentrustlog.setFcreateTime(Utils.getTimestamp());
					sellFentrustlog.setFprize(buyPrize);
					sellFentrustlog.setIsactive(issell);
					sellFentrustlog.setFentrust(sell);
					sellFentrustlog.setfEntrustType(EntrustTypeEnum.SELL);
					sellFentrustlog.setFtrademapping(ftrademapping) ;

					boolean ret = false;
					try {
						frontTradeService.updateDealMaking(ftrademapping,buy, sell,
								buyFentrustlog, sellFentrustlog, id);
						ret = true;
						//交易添加积分 --
						this.realTimeData.addIntegral(buyFentrustlog);
						this.realTimeData.addIntegral(sellFentrustlog);
					} catch (Exception e) {
					}

					if (ret) {
						realTimeData.addEntrustSuccessMap(id,
								sellFentrustlog);
						realTimeData.addEntrustSuccessMap(id,
								buyFentrustlog);
						

						if (buy.getFstatus() == EntrustStatusEnum.Going
								|| buy.getFstatus() == EntrustStatusEnum.PartDeal) {
							if (buy.isFisLimit()) {
								realTimeData.addEntrustLimitBuyMap(id,
										buy);
							} else {
								realTimeData.addEntrustBuyMap(id, buy);
							}
						} else if (buy.getFstatus() == EntrustStatusEnum.AllDeal) {
							if (buy.isFisLimit()) {
								realTimeData.removeEntrustLimitBuyMap(
										id, buy);
							} else {
								realTimeData.removeEntrustBuyMap(id,
										buy);
							}

						}

						if (sell.getFstatus() == EntrustStatusEnum.Going
								|| sell.getFstatus() == EntrustStatusEnum.PartDeal) {
							if (sell.isFisLimit()) {
								realTimeData.addEntrustLimitSellMap(
										id, sell);
							} else {
								realTimeData.addEntrustSellMap(id,
										sell);
							}
						} else if (sell.getFstatus() == EntrustStatusEnum.AllDeal) {
							if (sell.isFisLimit()) {
								realTimeData
										.removeEntrustLimitSellMap(id, sell);
							} else {
								realTimeData.removeEntrustSellMap(id,
										sell);
							}

						}

						rehandle = true;
						
					} else {
						buy = frontTradeService.findFentrustById(buy
								.getFid());
						sell = frontTradeService.findFentrustById(sell
								.getFid());

						if (buy == null || sell == null) {
							log.error("buy or sell null;");
							return ;
						}

						if (buy.getFstatus() == EntrustStatusEnum.Going
								|| buy.getFstatus() == EntrustStatusEnum.PartDeal) {
							realTimeData
									.addEntrustLimitBuyMap(id, buy);
						} else {
							realTimeData.removeEntrustLimitBuyMap(id,
									buy);
						}
						if (sell.getFstatus() == EntrustStatusEnum.Going
								|| sell.getFstatus() == EntrustStatusEnum.PartDeal) {
							realTimeData.addEntrustSellMap(id, sell);
						} else {
							realTimeData
									.removeEntrustSellMap(id, sell);
						}
					}

			}

		}
		
		
}
