package com.ruizton.main.quartz;

import java.security.Key;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.ruizton.main.Enum.CoinType;
import com.ruizton.main.log.LOG;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationInStatusEnum;
import com.ruizton.main.Enum.VirtualCoinTypeStatusEnum;
import com.ruizton.main.auto.RechargeBtcData;
import com.ruizton.main.model.BTCInfo;
import com.ruizton.main.model.BTCMessage;
import com.ruizton.main.model.Fvirtualcaptualoperation;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.util.BTCUtils;
import com.ruizton.util.Utils;

public class AutoRechargeBtcCome {
	private static final Logger log = LoggerFactory
			.getLogger(AutoRechargeBtcCome.class);
	@Autowired
	private RechargeBtcData rechargeBtcData ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private VirtualCoinService virtualCoinService ;
	@Autowired
	private FrontUserService frontUserService ;
	private boolean m_sync_flag = false ;
	
	public void work() {
		synchronized (this) {
				
				try{
					String filter = "where fstatus=1 and fShortName != '" + CoinType.ANS +"' and isEth=0 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
					List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
					//获取钱包中新数据
					for (Fvirtualcointype fvirtualcointype : coins) {
						int id = fvirtualcointype.getFid();
							try{

								if(fvirtualcointype==null || fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal){
									continue ;
								}
								
								BTCMessage btcMessage = new BTCMessage() ;
								btcMessage.setACCESS_KEY(fvirtualcointype.getFaccess_key()) ;
								btcMessage.setIP(fvirtualcointype.getFip()) ;
								btcMessage.setPORT(fvirtualcointype.getFport()) ;
								btcMessage.setSECRET_KEY(fvirtualcointype.getFsecrt_key()) ;
								// 用于过滤zec
								btcMessage.setCOIN_TYPE(fvirtualcointype.getfShortName().toUpperCase());
								
								BTCUtils btcUtils = new BTCUtils(btcMessage) ;
								String[] tradeNumbers = this.rechargeBtcData.getSubKeys(id) ;
								for (String tradeNo : tradeNumbers) {
									try {
										Fvirtualcaptualoperation fvirtualcaptualoperation = this.rechargeBtcData.subGet(id, tradeNo) ;
										if(fvirtualcaptualoperation!=null){
											fvirtualcaptualoperation = this.frontVirtualCoinService.findFvirtualcaptualoperationById(fvirtualcaptualoperation.getFid()) ;
											if(fvirtualcaptualoperation.getFstatus()!=VirtualCapitalOperationInStatusEnum.SUCCESS){
												BTCInfo btcInfo = null ;
												try {
													btcInfo = btcUtils.gettransactionValue(fvirtualcaptualoperation.getFtradeUniqueNumber(), "receive") ;
												} catch (Exception e1) {
												}
												if(btcInfo==null){
													break ;
												}
												if(btcInfo.getConfirmations()>=0){
													fvirtualcaptualoperation.setFamount(btcInfo.getAmount());
													fvirtualcaptualoperation.setFconfirmations(btcInfo.getConfirmations()) ;
													switch (btcInfo.getConfirmations()) {
													case VirtualCapitalOperationInStatusEnum.WAIT_0:
														fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.WAIT_0) ;
														break;
													case VirtualCapitalOperationInStatusEnum.WAIT_1:
														fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.WAIT_1);
														break;
													case VirtualCapitalOperationInStatusEnum.WAIT_2:
														fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.WAIT_2) ;
														break;
													default:
														if(fvirtualcointype.isFisauto()){
															if (CoinType.ZEC.equals(btcMessage.getCOIN_TYPE())){
																// ZEC 大于的确认数大于6的时候才是成功
																if (btcInfo.getConfirmations()>=6){
																	fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.SUCCESS) ;
																}
															} else {
																fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.SUCCESS) ;
															}
														}
														break;
													}
													fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp()) ;
													try{
														this.frontVirtualCoinService.updateFvirtualcaptualoperationCoinIn(fvirtualcaptualoperation) ;
														if(fvirtualcaptualoperation.getFstatus()==VirtualCapitalOperationInStatusEnum.SUCCESS){
															this.rechargeBtcData.subRemove(id, tradeNo) ;
														}
													}catch(Exception e){
														e.printStackTrace() ;
													}
												}
												
											}else{
												this.rechargeBtcData.subRemove(id, tradeNo) ;
											}
										}else{
											this.rechargeBtcData.subRemove(id, tradeNo) ;
										}
									} catch (Exception e) {
										LOG.e(this,"虚拟币确认发生异常",e);
										continue;
									}
					 			}
							
							}catch(Exception e){
								LOG.e(this,"虚拟币确认发生异常",e);
								e.printStackTrace() ;
							}
							
						}
					
				}catch (Exception e) {
					e.printStackTrace() ;
				}
		
		}

		
	}
	
}
