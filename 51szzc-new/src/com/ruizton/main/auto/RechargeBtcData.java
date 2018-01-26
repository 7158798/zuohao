package com.ruizton.main.auto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationInStatusEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationTypeEnum;
import com.ruizton.main.model.Fvirtualcaptualoperation;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.front.UtilsService;

public class RechargeBtcData {
	
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private VirtualCoinService virtualCoinService ;
	
	private Map<Integer, String> tradeRecordMap = new HashMap<Integer, String>() ;
	public String getLastTradeRecordMap(Integer key){
		return this.tradeRecordMap.get(key) ;
	}
	public void setTradeRecordMap(Integer key,String value){
		synchronized (tradeRecordMap) {
			this.tradeRecordMap.put(key, value) ;
		}
	}
	
	private Map<Integer, Map<String, Fvirtualcaptualoperation>> map = new HashMap<Integer, Map<String,Fvirtualcaptualoperation>>() ;
	
	public Integer[] getKeys(){
		Object[] objs = map.keySet().toArray() ;
		Integer[] ints = new Integer[objs.length] ;
		for (int i = 0; i < objs.length; i++) {
			ints[i] = (Integer) objs[i] ;
		}
		return ints ;
	}
	
	public String[] getSubKeys(int id){
		Map<String, Fvirtualcaptualoperation> subMap = this.map.get(id) ;
		if(subMap==null){
			subMap = new HashMap<String, Fvirtualcaptualoperation>() ;
		}
		Object[] objs = subMap.keySet().toArray() ;
		String[] rets = new String[objs.length] ;
		for (int i = 0; i < objs.length; i++) {
			rets[i] = (String)objs[i] ;
		}
		return rets ;
	}
	
	public void put(int id,Map<String, Fvirtualcaptualoperation> subMap){
		synchronized (this) {
			this.map.put(id, subMap) ;
		}
	}
	
	public Fvirtualcaptualoperation subGet(int id,String key){
		Map<String, Fvirtualcaptualoperation> subMap = this.map.get(id) ;
		if(subMap!=null){
			return subMap.get(key) ;
		}
		
		return null ;
	}
	
	public void subPut(int id,String key,Fvirtualcaptualoperation Fvirtualcaptualoperation){
		synchronized (this) {
			Map<String, Fvirtualcaptualoperation> subMap = this.map.get(id) ;
			if(subMap==null){
				subMap = new HashMap<String, Fvirtualcaptualoperation>() ;
			}
			subMap.put(key, Fvirtualcaptualoperation) ;
			this.map.put(id, subMap) ;
		}
	}
	
	public void subRemove(int id,String key){
		synchronized (this) {
			Map<String, Fvirtualcaptualoperation> subMap = this.map.get(id) ;
			if(subMap!=null){
				subMap.remove(key) ;
				this.map.put(id, subMap) ;
			}
		}
	}
	
	
	public void init(){
		readData() ;
	}
	private void readData(){
		String sql = "where fstatus=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
		List<Fvirtualcointype> fvirtualcointypes = this.virtualCoinService.list(0, 0, sql, false);
		for (Fvirtualcointype fvirtualcointype : fvirtualcointypes) {
			int id = fvirtualcointype.getFid() ;
			Map<String, Fvirtualcaptualoperation> tMap = new HashMap<String, Fvirtualcaptualoperation>() ;
			String filter = "where ftype="+VirtualCapitalOperationTypeEnum.COIN_IN+" and fvirtualcointype.fid="+id
					+" and fstatus in ("+VirtualCapitalOperationInStatusEnum.WAIT_0+","
					+VirtualCapitalOperationInStatusEnum.WAIT_1+","+
					VirtualCapitalOperationInStatusEnum.WAIT_2+")";
			List<Fvirtualcaptualoperation> fvirtualcaptualoperations = 
					this.utilsService.list(0, 0, filter, false, Fvirtualcaptualoperation.class) ;
					
			for (Fvirtualcaptualoperation fvirtualcaptualoperation : fvirtualcaptualoperations) {
				// 交易id为key
				// 交易id和地址为key
				String key = fvirtualcaptualoperation.getFtradeUniqueNumber() + fvirtualcaptualoperation.getRecharge_virtual_address();
				tMap.put(key, fvirtualcaptualoperation) ;
			}
			this.put(id, tMap) ;
		}
	}
	
	public void clear(){
		this.map = null ;
	}
}
