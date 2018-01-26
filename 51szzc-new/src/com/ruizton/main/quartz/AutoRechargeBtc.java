package com.ruizton.main.quartz;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.ruizton.main.Enum.*;
import com.ruizton.main.service.front.UtilsService;
import com.ruizton.main.log.LOG;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.auto.RechargeBtcData;
import com.ruizton.main.model.BTCInfo;
import com.ruizton.main.model.BTCMessage;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualaddress;
import com.ruizton.main.model.Fvirtualcaptualoperation;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.util.BTCUtils;
import com.ruizton.util.Utils;

public class AutoRechargeBtc {
	private static final Logger log = LoggerFactory
			.getLogger(AutoRechargeBtc.class);
	@Autowired
	private RechargeBtcData rechargeBtcData ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private RealTimeData realTimeData ;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private UtilsService utilsService;
	private boolean m_sync_flag = false ;
	
	public void work() {
		synchronized (this) {
				
				try{
					LOG.i(this,"比特币同步开始");
						String filter = "where fstatus=1 and fShortName != '" + CoinType.ANS +"' and isEth=0 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
						List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
						//获取钱包中新数据
						for (Fvirtualcointype fvirtualcointype : coins) {
							try{
                                int id = fvirtualcointype.getFid();
								String lastAddress = this.rechargeBtcData.getLastTradeRecordMap(id) ;
								int begin = 0 ;
								int step = 200;//lastAddress==null?Integer.MAX_VALUE:200 ;
								boolean is_continue = true ;
								
								if(fvirtualcointype==null || fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal){
									continue ;
								}
								BTCMessage btcMessage = new BTCMessage() ;
								btcMessage.setACCESS_KEY(fvirtualcointype.getFaccess_key()) ;
								btcMessage.setIP(fvirtualcointype.getFip()) ;
								btcMessage.setPORT(fvirtualcointype.getFport()) ;
								btcMessage.setSECRET_KEY(fvirtualcointype.getFsecrt_key()) ;
								// 过滤zec
								btcMessage.setCOIN_TYPE(fvirtualcointype.getfShortName().toUpperCase());
								
								String firstAddress = null ;
								BTCUtils btcUtils = new BTCUtils(btcMessage) ;
								List<BTCInfo> btcInfos = new ArrayList<BTCInfo>() ;
								
								while(is_continue){
									try {
										btcInfos = btcUtils.listtransactionsValue(step, begin) ;
										begin+=step ;
										if(firstAddress==null && btcInfos.size()>0){
											firstAddress = btcInfos.get(0).getTxid().trim() ;
										}
										is_continue = false ;
									} catch (Exception e1) {
										break;
									}
									
									for (BTCInfo btcInfo : btcInfos) {
										
										String txid = btcInfo.getTxid().trim() ;
										if(txid.equals(lastAddress)){
											is_continue = false ;
										}
										Fvirtualcaptualoperation fvirtualcaptualoperation = new Fvirtualcaptualoperation() ;
										boolean hasOwner = true ;
										String address = btcInfo.getAddress().trim() ;

										// 交易id和地址统计
										int c = this.utilsService.count(" where ftradeUniqueNumber='" + txid + "' and recharge_virtual_address = '" + address + "' ", Fvirtualcaptualoperation.class) ;
										if(c > 0){
											continue;
										}
										List<Fvirtualaddress> fvirtualaddresses = this.frontVirtualCoinService.findFvirtualaddress(fvirtualcointype, address) ;
										if(fvirtualaddresses.size()==0){
											hasOwner = false ;//没有这个地址，充错进来了？没收！
										}else if(fvirtualaddresses.size()>1){
											log.error("Dumplicate Fvirtualaddress for address:"+address+" ,Fvirtualcointype:"+fvirtualcointype.getFid()) ;
											continue ;
										}else{
											Fvirtualaddress fvirtualaddress = fvirtualaddresses.get(0) ;
											Fuser fuser = fvirtualaddress.getFuser() ;
											fvirtualcaptualoperation.setFuser(fuser) ;
										}
						
										fvirtualcaptualoperation.setFhasOwner(hasOwner) ;
										fvirtualcaptualoperation.setFamount(btcInfo.getAmount()) ;
										fvirtualcaptualoperation.setFcreateTime(Utils.getTimestamp()) ;
										fvirtualcaptualoperation.setFfees(0F) ;
										fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp()) ;
						
										fvirtualcaptualoperation.setFconfirmations(0) ;
										fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.WAIT_0) ;
						
										fvirtualcaptualoperation.setFtradeUniqueNumber(btcInfo.getTxid().trim()) ;
										fvirtualcaptualoperation.setRecharge_virtual_address(btcInfo.getAddress().trim()) ;
										fvirtualcaptualoperation.setFtype(VirtualCapitalOperationTypeEnum.COIN_IN);
										fvirtualcaptualoperation.setFvirtualcointype(fvirtualcointype);
										try {
											this.frontVirtualCoinService.addFvirtualcaptualoperation(fvirtualcaptualoperation);
											// 交易id和地址为key
											String key = fvirtualcaptualoperation.getFtradeUniqueNumber() + fvirtualcaptualoperation.getRecharge_virtual_address();
											this.rechargeBtcData.subPut(id, key, fvirtualcaptualoperation) ;
										} catch (Exception e) {
											e.printStackTrace();
										}
										
									}//for
									
									
								}//while
								if(firstAddress!=null){
									this.rechargeBtcData.setTradeRecordMap(id, firstAddress) ;
								}
								
							
							}catch(Exception e){
								e.printStackTrace() ;
							}
							
						}//for
					LOG.i(this,"比特币同步结束");

				}catch (Exception e) {
					e.printStackTrace() ;
				}
		
		}

		
	}
	
	
	
}
