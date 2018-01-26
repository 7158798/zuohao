package com.ruizton.main.quartz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qq.connect.javabeans.tenpay.Address;
import com.ruizton.main.auto.okcoin.DepthResponse;
import com.ruizton.main.log.LOG;
import com.ruizton.util.eth.bean.Debug;
import com.ruizton.util.eth.bean.StructLogs;
import com.ruizton.util.eth.resp.DebugResp;
import com.ruizton.util.zuohao.EthAddressUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationInStatusEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationTypeEnum;
import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.auto.RechargeBtcData;
import com.ruizton.main.comm.MultipleValues;
import com.ruizton.main.model.BTCInfo;
import com.ruizton.main.model.BTCMessage;
import com.ruizton.main.model.Fpool;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualaddress;
import com.ruizton.main.model.Fvirtualcaptualoperation;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.main.service.front.UtilsService;
import com.ruizton.util.BTCUtils;
import com.ruizton.util.ETHUtils;
import com.ruizton.util.Utils;
import com.sun.org.apache.xpath.internal.operations.Mult;

public class EthRecharge {
	private static final Logger log = LoggerFactory
			.getLogger(EthRecharge.class);
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
	
	//last hash block,index,
	private Map<Integer, Long> map = new HashMap<Integer, Long>() ;
	public void putMap(Integer key,Long value){
		synchronized (map) {
			map.put(key, value) ;
		}
	}
	public Long getMap(Integer key){
		synchronized (key) {
			return map.get(key) ;
		}
	}
	
	public void init(){
		String filter = "where fstatus=1 and isEth=1 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
		List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
		//获取钱包中新数据
		for (Fvirtualcointype fvirtualcointype : coins) {
			List<Fvirtualcaptualoperation> fvirtualcaptualoperations = this.utilsService.list(0, 1, " where ftype=? and fvirtualcointype.fid=? order by fid desc ", true, Fvirtualcaptualoperation.class,VirtualCapitalOperationTypeEnum.COIN_IN,fvirtualcointype.getFid()) ;
			
			Long block = 0L ;
			if(fvirtualcaptualoperations.size() > 0 ){
				try {
					Fvirtualcaptualoperation fvirtualcaptualoperation = fvirtualcaptualoperations.get(0) ;
					
					BTCMessage msg = new BTCMessage();
					msg.setACCESS_KEY(fvirtualcointype .getFaccess_key());
					msg.setIP(fvirtualcointype.getFip());
					msg.setPORT(fvirtualcointype .getFport());
					msg.setSECRET_KEY(fvirtualcointype .getFsecrt_key());
					ETHUtils ethUtils = new ETHUtils(msg) ;
					BTCInfo btcInfo = ethUtils.eth_getTransactionByHashValue(fvirtualcaptualoperation.getFtradeUniqueNumber()) ;
					
					block = btcInfo.getBlockNumber() ;
				} catch (Exception e) {
					log.error(fvirtualcointype.getFname()+"钱包链接失败，请到后台修改后重启") ;
					e.printStackTrace();
				}
			}else{
				block = (long)fvirtualcointype.getStartBlockId() ;
			}
			
			putMap(fvirtualcointype.getFid(), block) ;
			
		}

		new EthThread().start();
	}


	public class EthThread extends Thread {

		public void run(){
			while (true){
				try {
					LOG.i(this,"EHT记录同步线程Start");
					work();
					// 休眠3分钟
					Thread.sleep(180000l);
					LOG.i(this,"EHT记录同步线程End");
				} catch (Exception e){
					LOG.e(this,e.getMessage(),e);
				}
			}

		}
	}

	public void work(){
		synchronized (this) {
			String filter = "where fstatus=1 and isEth=1 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
			List<Fvirtualcointype> coins = this.virtualCoinService.list(0, 0, filter, false);
			for (Fvirtualcointype fvirtualcointype : coins) {
				// 获取所有eth的可用地址
				List<Fvirtualaddress> list = frontVirtualCoinService.findFvirtualaddressByProperty("fvirtualcointype.fid",fvirtualcointype.getFid());
				List<String> rList = packUserAddress(list);
				try {

					BTCMessage btcMessage = new BTCMessage() ;
					btcMessage.setACCESS_KEY(fvirtualcointype.getFaccess_key()) ;
					btcMessage.setIP(fvirtualcointype.getFip()) ;
					btcMessage.setPORT(fvirtualcointype.getFport()) ;
					btcMessage.setSECRET_KEY(fvirtualcointype.getFsecrt_key()) ;
					
					ETHUtils ethUtils = new ETHUtils(btcMessage) ;
					
					Long block = getMap(fvirtualcointype.getFid()) ;
					while(ethUtils.eth_blockNumberValue()>block){
						
						long count = ethUtils.eth_getBlockTransactionCountByNumberValue(block) ;
						for (int i = 0; i < count; i++) {
							boolean isContract = Boolean.FALSE;
							BTCInfo btcInfo = ethUtils.getTransactionByBlockNumberAndIndexValue(block, i) ;
							String txid = btcInfo.getTxid() ;
							String address = btcInfo.getAddress().trim() ;
							int c = this.utilsService.count(" where ftradeUniqueNumber='"+txid+"' ", Fvirtualcaptualoperation.class) ;
							if(c>0){
								continue ;
							}
							
							List<Fvirtualaddress> fvirtualaddresses = this.frontVirtualCoinService.findFvirtualaddress(fvirtualcointype, address);
							if (fvirtualaddresses.size() == 0 && !"0x".equals(btcInfo.getInput())){
								// 没有找到,有可能是合约转账
								String tAddress = getRechargeAddress(rList,btcInfo.getInput());
								if (tAddress != null){
									// 执行debug指令
									DebugResp response = ethUtils.debug_traceTransaction(txid);
									List<String> stackList = getStack(response);
									if (stackList != null && stackList.size() > 2){
										int index = stackList.size();
										// 获取地址
										String value = stackList.get(index-2);
										value = EthAddressUtils.toHexStringWithPrefixZeroPadded(EthAddressUtils.toBigInt(value));
										if (tAddress.equals(value)){
											// 地址核对成功,充值金额
											String tAmount = stackList.get(index-3);
											double t = EthAddressUtils.parseAmount(tAmount);
											btcInfo.setAmount(t);
											btcInfo.setAddress(tAddress);
											// 使用新的地址
											fvirtualaddresses = this.frontVirtualCoinService.findFvirtualaddress(fvirtualcointype, tAddress);
											// 合同转账
											isContract = Boolean.TRUE;
										}
									}
								}
							}
							if(fvirtualaddresses.size()==1){
								Fvirtualcaptualoperation fvirtualcaptualoperation = new Fvirtualcaptualoperation() ;
								Fvirtualaddress fvirtualaddress = fvirtualaddresses.get(0) ;
								Fuser fuser = fvirtualaddress.getFuser() ;
								fvirtualcaptualoperation.setFuser(fuser) ;
								fvirtualcaptualoperation.setFhasOwner(true) ;
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
								fvirtualcaptualoperation.setIsContract(isContract);
//								fvirtualcaptualoperation.setFisPreAudit(false);
								this.frontVirtualCoinService.addFvirtualcaptualoperation(fvirtualcaptualoperation) ;
							}
							
						}
						block = block+1 ;
						putMap(fvirtualcointype.getFid(), block) ;
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
		}
	}

	/**
	 * 查找充值地址
	 * @param list
	 * @param input
	 * @return
	 */
	public String getRechargeAddress(List<String> list,String input){
		String address = null;
		for (String str:list){
			if (input.indexOf(str) > 0){
				address = "0x" + str;
				break;
			}
		}
		return address;
	}

	/**
	 * 获取debug中的stack
	 * @param resp
	 * @return
	 */
	public List<String> getStack(DebugResp resp){

		Debug debug = resp.getResult();
		if (debug == null){
			return null;
		}
		List<StructLogs> structLogsList = debug.getStructLogs();
		if (structLogsList == null){
			return null;
		}
		for (StructLogs structLogs : structLogsList){
			if ("CALL".equals(structLogs.getOp())){
				return structLogs.getStack();
			}
		}
		return null;

	}

	/**
	 * 包装用户地址
	 * @param list
	 * @return
	 */
	public List<String> packUserAddress(List<Fvirtualaddress> list){
		List<String> rList = new ArrayList<>();
		for (Fvirtualaddress fvirtualaddress : list){
			String temp = fvirtualaddress.getFadderess().substring(2);
			rList.add(temp);
		}
		return rList;
	}
}
