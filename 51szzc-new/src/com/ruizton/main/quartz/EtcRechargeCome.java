package com.ruizton.main.quartz;

import java.security.Key;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationInStatusEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationTypeEnum;
import com.ruizton.main.Enum.VirtualCoinTypeStatusEnum;
import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.auto.RechargeBtcData;
import com.ruizton.main.model.BTCInfo;
import com.ruizton.main.model.BTCMessage;
import com.ruizton.main.model.Fvirtualcaptualoperation;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.main.service.front.UtilsService;
import com.ruizton.util.BTCUtils;
import com.ruizton.util.ETHUtils;
import com.ruizton.util.Utils;

public class EtcRechargeCome {
	private static final Logger log = LoggerFactory
			.getLogger(EtcRechargeCome.class);
	
	@Autowired
	private RechargeBtcData rechargeBtcData ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private RealTimeData realTimeData ;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private UtilsService utilsService ;
	
	public void work() {
		synchronized (this) {
				
				try{
					String filter = "where fstatus=1 and isEth=1 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
					List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
					for (Fvirtualcointype fvirtualcointype : coins) {
						try {
							BTCMessage msg = new BTCMessage();
							msg.setACCESS_KEY(fvirtualcointype .getFaccess_key());
							msg.setIP(fvirtualcointype.getFip());
							msg.setPORT(fvirtualcointype .getFport());
							msg.setSECRET_KEY(fvirtualcointype .getFsecrt_key());
							ETHUtils ethUtils = new ETHUtils(msg) ;
							
							List<Fvirtualcaptualoperation> fvirtualcaptualoperations = this.utilsService.list(0, 1, " where ftype=? and fvirtualcointype.fid=? and fstatus<>? order by fid desc ", true, Fvirtualcaptualoperation.class,VirtualCapitalOperationTypeEnum.COIN_IN,fvirtualcointype.getFid(),VirtualCapitalOperationInStatusEnum.SUCCESS) ;
							for (Fvirtualcaptualoperation fvirtualcaptualoperation : fvirtualcaptualoperations) {
								try {
									String txid = fvirtualcaptualoperation.getFtradeUniqueNumber() ;
									BTCInfo btcInfo = ethUtils.eth_getTransactionByHashValue(txid) ;
									if (btcInfo.getConfirmations() >= 3 && !fvirtualcaptualoperation.getIsContract()){
										fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.SUCCESS) ;
									} else if (btcInfo.getConfirmations() > 0){
										fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.WAIT_1);
									}
									fvirtualcaptualoperation.setFconfirmations(btcInfo.getConfirmations());
									fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp()) ;
									this.frontVirtualCoinService.updateFvirtualcaptualoperationCoinIn(fvirtualcaptualoperation);
										
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}catch (Exception e) {
					e.printStackTrace() ;
				}
		
		}

		
	}
	
}
