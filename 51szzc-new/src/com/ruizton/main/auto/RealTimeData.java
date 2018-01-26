package com.ruizton.main.auto;

import com.ruizton.main.Enum.EntrustTypeEnum;
import com.ruizton.main.Enum.TrademappingStatusEnum;
import com.ruizton.main.comm.KeyValues;
import com.ruizton.main.log.LOG;
import com.ruizton.main.comm.MessagePush;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fentrust;
import com.ruizton.main.model.Fentrustlog;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.service.front.*;
import com.ruizton.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

public class RealTimeData {
	
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private LatestKlinePeroid latestKlinePeroid ;
	@Autowired
	private FtradeMappingService ftradeMappingService ;
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private MessagePush messagePush;

	private Set<Integer> blackUser = new HashSet<Integer>() ;
	public void addBlackUser(int uid){
		synchronized (blackUser) {
			blackUser.add(uid) ;
		}
	}
	public void removeBlackUser(int uid){
		synchronized (blackUser) {
			blackUser.remove(uid) ;
		}
	}
	public boolean isBalckUser(int uid){
		synchronized (blackUser) {
			return blackUser.contains(uid) ;
		}
	}
	
	private Map<String, Integer> black = new HashMap<String, Integer>() ;
	public boolean black(String ip,Integer type){
		synchronized (this.black) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
			String key = ip+"_"+type+"_"+ sdf.format(Utils.getTimestamp()) ;
			Integer count = this.black.get(key) ;
			if(count==null){
				count = 0 ;
			}
			if(count>20){
				return false ;
			}
			this.black.put(key, count+1) ;
			return true ;
		}
	}
	
	 private static Map<String, Long> appSessionMap = new HashMap<String, Long>() ;
	 public synchronized String putAppSession(HttpSession session,Fuser fuser){
		 String loginToken = session.getId()+"_"+Utils.getTimestamp().getTime()+"_"+fuser.getFid() ;
		 this.appSessionMap.put(loginToken, Utils.getTimestamp().getTime()) ;
		 return loginToken ;
	 }

	 public String pushTestAppSession(int userId){
	 	String loginToken = "test"+"_"+Utils.getTimestamp().getTime()+"_"+userId;
		 this.appSessionMap.put(loginToken, Utils.getTimestamp().getTime()) ;
		return loginToken;
	 }


	 public boolean isAppLogin(String key,boolean update){
		 Long l = this.appSessionMap.get(key) ;
		 if(l==null){
			 return false ;
		 }else{/*
			 Timestamp time = new Timestamp(l) ;
			 if(Utils.getTimestamp().getTime() - time.getTime() <30*3600*1000L ){
				 if(update==true){
					 this.appSessionMap.put(key, Utils.getTimestamp().getTime()) ;
				 }
				 return true ;
			 }else{
				 return false ;
			 }
		 */
			 return true ;
		 }
	 }

	 //获取登录用户的信息
	 public Fuser getAppFuser(String key){
		 Fuser fuser = null ;
		 try {
			String split[] = key.split("_") ;
			 if(split.length==3){
				 fuser = this.frontUserService.findById(Integer.parseInt(split[2])) ;
			 }
		} catch (Exception e) {}
		 
		 return fuser ;
		 
	 }



	//获取登录用户的用户id
	public int getAppFuserId(String key) {
		Integer userId = null ;
		try {
			String split[] = key.split("_") ;
			if(split.length==3){
				userId = Integer.parseInt(split[2]);
			}
		} catch (Exception e) {}

		return userId ;
	}

	//获取app登录用户的基本信息(简单查询)
	public Fuser getAppSimpleUser(String key) {
		Fuser fuser = null ;
		try {
			String split[] = key.split("_") ;
			if(split.length==3){
				fuser = this.frontUserService.queryById(Integer.parseInt(split[2])) ;
			}
		} catch (Exception e) {}

		return fuser ;
	}

	//获取app登录用户的基本信息(简单查询)
	public Fuser getAppSimpleUserScore(String key) {
		Fuser fuser = null ;
		try {
			String split[] = key.split("_") ;
			if(split.length==3){
				fuser = this.frontUserService.queryUserScoreById(Integer.parseInt(split[2])) ;
			}
		} catch (Exception e) {}

		return fuser ;
	}


	@Autowired
	private FrontTradeService frontTradeService ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private KlinePeriodData klinePeriodData ;
	private boolean m_is_init = false ;
	public boolean getMisInit(){
		return m_is_init ;
	}
	
	Comparator<Fentrustlog> timeComparator = new Comparator<Fentrustlog>() {
		public int compare(Fentrustlog o1, Fentrustlog o2) {
			boolean flag = o1.getFid().intValue()==o2.getFid().intValue() && o1.getFid().intValue()!=0 ;
			if(flag){
				return 0 ;
			}
			int ret = o2.getFcreateTime().compareTo(o1.getFcreateTime()) ;
			if(ret==0){
				return o1.getFid().compareTo(o2.getFid()) ;
			}else{
				return ret ;
			}
		}
	} ;
	
	public static Comparator<Fentrust> prizeComparatorASC = new Comparator<Fentrust>() {
		public int compare(Fentrust o1, Fentrust o2) {
			boolean flag = (o1.getFid().intValue()==o2.getFid().intValue()) && (o1.getFid().intValue()!=0) ;
			if(flag){
				return 0 ;
			}
			int ret = o1.getFprize().compareTo(o2.getFprize()) ;
			if(ret==0){
				return o1.getFid().compareTo(o2.getFid()) ;
			}else{
				return ret ;
			}
		}
	} ;
	public static Comparator<Fentrust> prizeComparatorDESC = new Comparator<Fentrust>() {
		public int compare(Fentrust o1, Fentrust o2) {
			boolean flag = (o1.getFid().intValue()==o2.getFid().intValue()) &&  (o1.getFid().intValue()!=0) ;
			if(flag){
				return 0 ;
			}
			int ret = o2.getFprize().compareTo(o1.getFprize()) ;
			if(ret==0){
				return o1.getFid().compareTo(o2.getFid()) ;
			}else{
				return ret ;
			}
		}
	} ;
	
	
	private Map<Integer, Boolean> refreshBuyDepthData = new HashMap<Integer, Boolean>() ;
	private Map<Integer, Boolean> refreshSellDepthData = new HashMap<Integer, Boolean>() ;
	
	private Map<Integer, TreeSet<Fentrustlog>> entrustSuccessMap = new HashMap<Integer, TreeSet<Fentrustlog>>() ;
	private Map<Integer, TreeSet<Fentrust>> buyDepthMap = new HashMap<Integer, TreeSet<Fentrust>>();
	private Map<Integer, TreeSet<Fentrust>> entrustBuyMap = new HashMap<Integer, TreeSet<Fentrust>>();
	private Map<Integer, TreeSet<Fentrust>> entrustLimitBuyMap = new HashMap<Integer, TreeSet<Fentrust>>();
	private Map<Integer, TreeSet<Fentrust>> sellDepthMap = new HashMap<Integer, TreeSet<Fentrust>>();
	private Map<Integer, TreeSet<Fentrust>> entrustSellMap = new HashMap<Integer, TreeSet<Fentrust>>();
	private Map<Integer, TreeSet<Fentrust>> entrustLimitSellMap = new HashMap<Integer, TreeSet<Fentrust>>();
	private Map<Integer, Double> latestDealPrize = new HashMap<Integer, Double>() ;
	// 内部买入订单，不受外部影响
	private Map<Integer, TreeSet<Fentrust>> insideEntrustBuyMap = new HashMap<>();
	// 内部卖出订单，不受外部影响
	private Map<Integer, TreeSet<Fentrust>> insideEntrustSellMap = new HashMap<>();
	
	private void changeRefreshBuyDepthData(int id,Boolean flag){
		synchronized (refreshBuyDepthData) {
			refreshBuyDepthData.put(id, flag) ;
		}
	}
	private void changeRefreshSellDepthData(int id,Boolean flag){
		synchronized (refreshSellDepthData) {
			refreshSellDepthData.put(id, flag) ;
		}
	}
	
	public void generateDepthData(){
		Object[] ids = refreshBuyDepthData.keySet().toArray() ;
		for (int i = 0; i < ids.length; i++) {
			synchronized (buyDepthMap) {
				try {
					generateBuyDepth((Integer)ids[i]) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		ids = refreshSellDepthData.keySet().toArray() ;
		for (int i = 0; i < ids.length; i++) {
			synchronized (sellDepthMap) {
				try {
					generateSellDepth((Integer)ids[i]) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void generateBuyDepth(int id){
		TreeSet<Fentrust> fentrusts = new TreeSet<Fentrust>(prizeComparatorDESC) ;
		
		Fentrust[] objs = this.getEntrustBuyMap(id,Integer.MAX_VALUE)  ;
		Map<String, KeyValues> map = new HashMap<String, KeyValues>() ;
		for (Fentrust fentrust : objs) {
			if(fentrust==null){
				continue ;
			}
			String key = String.valueOf(fentrust.getFprize()) ;
			KeyValues keyValues = map.get(key) ;
			if(keyValues==null){
				keyValues = new KeyValues() ;
				keyValues.setKey(fentrust.getFprize()) ;
				keyValues.setValue(fentrust.getFleftCount()) ;
			}else{
				keyValues.setValue((Double)keyValues.getValue()+fentrust.getFleftCount()) ;
			}
			map.put(key, keyValues) ;
		}
		
		for (Map.Entry<String, KeyValues> entry : map.entrySet()) {
			Fentrust fentrust = new Fentrust() ;
			fentrust.setFprize((Double) entry.getValue().getKey()) ;
			fentrust.setFleftCount((Double) entry.getValue().getValue()) ;
			fentrusts.add(fentrust) ;
		}
		this.buyDepthMap.put(id, fentrusts) ;
	}
	private void generateSellDepth(int id){
		TreeSet<Fentrust> fentrusts = new TreeSet<Fentrust>(prizeComparatorASC) ;
		
		Fentrust[] objs = this.getEntrustSellMap(id,Integer.MAX_VALUE) ;
		Map<String, KeyValues> map = new HashMap<String, KeyValues>() ;
		for (Object obj : objs) {
			if(obj==null){
				continue ;
			}
			Fentrust fentrust = (Fentrust)obj ;
			String key = String.valueOf(fentrust.getFprize()) ;
			KeyValues keyValues = map.get(key) ;
			if(keyValues==null){
				keyValues = new KeyValues() ;
				keyValues.setKey(fentrust.getFprize()) ;
				keyValues.setValue(fentrust.getFleftCount()) ;
			}else{
				keyValues.setValue((Double)keyValues.getValue()+fentrust.getFleftCount()) ;
			}
			map.put(key, keyValues) ;
		}
		
		for (Map.Entry<String, KeyValues> entry : map.entrySet()) {
			Fentrust fentrust = new Fentrust() ;
			fentrust.setFprize((Double) entry.getValue().getKey()) ;
			fentrust.setFleftCount((Double) entry.getValue().getValue()) ;
			fentrusts.add(fentrust) ;
		}
		this.sellDepthMap.put(id, fentrusts) ;
	}
	
	public double getLatestDealPrize(Integer id){
		if(id==null ){
			return 0 ;
		}
		try {
			Double prize = latestDealPrize.get(id) ;
			if(prize==null){
				return this.ftradeMappingService.findFtrademapping(id).getFprice();
			}else{
				return prize ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0 ;
	}
	public double getLowestSellPrize(int id){
		Fentrust[] fentrusts = this.getEntrustSellMap(id,1) ;
		if(fentrusts == null || fentrusts.length==0){
			return this.getLatestDealPrize(id) ;
		}else{
			return fentrusts[0].getFprize() ;
		}
	}
	public double getHighestBuyPrize(int id){
		Fentrust[] fentrusts = this.getEntrustBuyMap(id,1) ;
		if(fentrusts == null || fentrusts.length==0){
			return this.getLatestDealPrize(id) ;
		}else{
			return fentrusts[0].getFprize() ;
		}
	}
	
	public void init(){
		try {
			readData() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		m_is_init = true ;
	}
	
	private void readData(){

		List<Ftrademapping> list = this.utilsService.list(0, 0, " where fstatus=? ", false, Ftrademapping.class,TrademappingStatusEnum.ACTIVE) ;

		for (Ftrademapping ftrademapping : list) {
			List<Fentrustlog> fentrustlogs = this.frontTradeService.findLatestSuccessDeal24(ftrademapping.getFid() , 24) ;
			if(fentrustlogs != null){
				for (Fentrustlog fentrustlog : fentrustlogs) {
					this.addEntrustSuccessMapInit(ftrademapping.getFid(), fentrustlog) ;
				}
			}
			
			
			Fentrustlog latestDeal = this.frontTradeService.findLatestDeal(ftrademapping.getFid()) ;
			if(latestDeal!=null){
				latestDealPrize.put(ftrademapping.getFid(), latestDeal.getFprize()) ;
			}
			
			List<Fentrust> fentrusts = this.frontTradeService.findAllGoingFentrust(ftrademapping.getFid(), EntrustTypeEnum.BUY,false) ;
			for (Fentrust fentrust : fentrusts) {
				this.addEntrustBuyMap(ftrademapping.getFid(), fentrust) ;
			}
			fentrusts = this.frontTradeService.findAllGoingFentrust(ftrademapping.getFid(), EntrustTypeEnum.BUY,true) ;
			for (Fentrust fentrust : fentrusts) {
				this.addEntrustLimitBuyMap(ftrademapping.getFid(), fentrust) ;
			}
			
			fentrusts = this.frontTradeService.findAllGoingFentrust(ftrademapping.getFid(), EntrustTypeEnum.SELL,false) ;
			for (Fentrust fentrust : fentrusts) {
				this.addEntrustSellMap(ftrademapping.getFid(), fentrust) ;
			}
			fentrusts = this.frontTradeService.findAllGoingFentrust(ftrademapping.getFid(), EntrustTypeEnum.SELL,true) ;
			for (Fentrust fentrust : fentrusts) {
				this.addEntrustLimitSellMap(ftrademapping.getFid(), fentrust) ;
			}
			
		}
		
	}

	public Fentrustlog[] getEntrustSuccessMap(int id,int count) {
		Fentrustlog[] objs = new Fentrustlog[0] ;
		synchronized (entrustSuccessMap) {
			int realCount = 0 ;
			TreeSet<Fentrustlog> fentrusts = entrustSuccessMap.get(id);
			if(fentrusts!=null){
				realCount = count >fentrusts.size()?fentrusts.size():count ;
				objs = new Fentrustlog[realCount] ;
				int index = 0 ;
				Iterator<Fentrustlog> iterator = fentrusts.iterator() ;
				while(realCount>0){
					realCount-- ;
					objs[index++] = iterator.next() ;
				}
			}
		}
		return objs ;
	}
	

	public void addEntrustSuccessMap(int id,Fentrustlog fentrust){
		synchronized (entrustSuccessMap) {
			if(fentrust.isIsactive()==false){
				return ;
			}
			
			TreeSet<Fentrustlog> fentrusts = entrustSuccessMap.get(id) ;
			if(fentrusts==null){
				fentrusts = new TreeSet<Fentrustlog>(this.timeComparator) ;
			}
			if(fentrusts.contains(fentrust)){
				fentrusts.remove(fentrust) ;
			}
			fentrusts.add(fentrust) ;

			entrustSuccessMap.put(id, fentrusts) ;

			latestDealPrize.put(id, fentrust.getFprize()) ;
			// 价格变动推送
			messagePush.start(id, fentrust.getFprize());

			this.latestKlinePeroid.pushFentrustlog(fentrust) ;

		}
	}

	public void addEntrustSuccessMapInit(int id,Fentrustlog fentrust){
		synchronized (entrustSuccessMap) {
			if(fentrust.isIsactive()==false){
				return ;
			}

			TreeSet<Fentrustlog> fentrusts = entrustSuccessMap.get(id) ;
			if(fentrusts==null){
				fentrusts = new TreeSet<Fentrustlog>(this.timeComparator) ;
			}
			if(fentrusts.contains(fentrust)){
				fentrusts.remove(fentrust) ;
			}
			fentrusts.add(fentrust) ;

			entrustSuccessMap.put(id, fentrusts) ;

			latestDealPrize.put(id, fentrust.getFprize()) ;

			this.latestKlinePeroid.pushFentrustlog(fentrust) ;

		}
	}
	
	
	public void removeEntrustSuccessMap(int id,Fentrustlog fentrust) {
		synchronized (entrustSuccessMap) {
		TreeSet<Fentrustlog> fentrusts = entrustSuccessMap.get(id) ;
		if(fentrusts!=null){
			fentrusts.remove(fentrust) ;
		}
		}
	}
	

public Fentrust[] getEntrustBuyMap(int id,int count) {
		

		
		Fentrust[] objs = new Fentrust[0] ;
		synchronized (entrustBuyMap) {
			TreeSet<Fentrust> fentrusts = null;
			fentrusts = entrustBuyMap.get(id);
			int realCount = 0 ;
			if(fentrusts!=null){
				realCount = count>fentrusts.size()?fentrusts.size():count;
				objs = new Fentrust[realCount] ;
				Iterator<Fentrust> iterator = fentrusts.iterator() ;
				int index = 0 ;
				while(realCount>0){
					objs[index++] = iterator.next() ;
					realCount-- ;
				}
			}
		}
		return objs ;
	}
public Fentrust[] getBuyDepthMap(int id,int count) {
		
		Fentrust[] objs = new Fentrust[0] ;
		synchronized (buyDepthMap) {
			TreeSet<Fentrust> fentrusts = null;
			fentrusts = buyDepthMap.get(id);
			int realCount = 0 ;
			if(fentrusts!=null){
				realCount = count>fentrusts.size()?fentrusts.size():count;
				objs = new Fentrust[realCount] ;
				Iterator<Fentrust> iterator = fentrusts.iterator() ;
				int index = 0 ;
				while(realCount>0){
					objs[index++] = iterator.next() ;
					realCount-- ;
				}
			}
		}
		return objs ;
	}
	public Fentrust[] getEntrustLimitBuyMap(int id,int count) {

		
		Fentrust[] objs = new Fentrust[0] ;
		synchronized (entrustLimitBuyMap) {
			TreeSet<Fentrust> fentrusts = null;
			fentrusts = entrustLimitBuyMap.get(id);
			int realCount = 0 ;
			if(fentrusts!=null){
				realCount = count>fentrusts.size()?fentrusts.size():count;
				objs = new Fentrust[realCount] ;
				Iterator<Fentrust> iterator = fentrusts.iterator() ;
				int index = 0 ;
				while(realCount>0){
					objs[index++] = iterator.next() ;
					realCount-- ;
				}
			}
		}
		return objs ;
	
	}
	
	public Integer[] getEntrustBuyMapKeys(){
		Object[] objs = entrustBuyMap.keySet().toArray() ;
		Integer[] ints = new Integer[objs.length] ;
		for (int i = 0; i < objs.length; i++) {
			ints[i]=(Integer) objs[i] ;
		}
		return ints ;
	}
	public Integer[] getEntrustLimitBuyMapKeys(){
		Object[] objs = entrustLimitBuyMap.keySet().toArray() ;
		Integer[] ints = new Integer[objs.length] ;
		for (int i = 0; i < objs.length; i++) {
			ints[i]=(Integer) objs[i] ;
		}
		return ints ;
	}

	public void addEntrustBuyMap(int id,Fentrust fentrust) {
		synchronized (entrustBuyMap) {
			TreeSet<Fentrust> treeSet = entrustBuyMap.get(id) ;
			if(treeSet==null){
				treeSet = new TreeSet<Fentrust>(prizeComparatorDESC) ;
			}
			if(treeSet.contains(fentrust)){
				treeSet.remove(fentrust) ;
			}
			treeSet.add(fentrust) ;
			
			entrustBuyMap.put(id, treeSet) ;
			changeRefreshBuyDepthData(id, true) ;
		}
	}
	public void addEntrustLimitBuyMap(int id,Fentrust fentrust) {
		synchronized (entrustLimitBuyMap) {
		TreeSet<Fentrust> fentrusts = entrustLimitBuyMap.get(id) ;
		if(fentrusts==null){
			fentrusts = new TreeSet<Fentrust>(prizeComparatorDESC) ;
		}
		if(fentrusts.contains(fentrust)){
			fentrusts.remove(fentrust) ;
		}
		fentrusts.add(fentrust) ;
		
		entrustLimitBuyMap.put(id, fentrusts) ;
		}
	}
	
	public  void removeEntrustBuyMap(int id,Fentrust fentrust){
		synchronized (entrustBuyMap) {
			TreeSet<Fentrust> treeSet = entrustBuyMap.get(id) ;
			if(treeSet!=null){
				treeSet.remove(fentrust) ;
				entrustBuyMap.put(id, treeSet) ;
			}
			changeRefreshBuyDepthData(id, true) ;
		}
	}
	public  void removeEntrustLimitBuyMap(int id,Fentrust fentrust){
		synchronized (entrustLimitBuyMap) {
		TreeSet<Fentrust> treeSet = entrustLimitBuyMap.get(id) ;
		if(treeSet!=null){
			treeSet.remove(fentrust) ;
			entrustLimitBuyMap.put(id, treeSet) ;
		}
		}
	}

public Fentrust[] getEntrustSellMap(int id,int count) {
		

		

		Fentrust[] objs = new Fentrust[0] ;
		synchronized (entrustSellMap) {
			TreeSet<Fentrust> fentrusts = null;
			fentrusts = entrustSellMap.get(id);
			int realCount = 0 ;
			if(fentrusts!=null){
				realCount = count>fentrusts.size()?fentrusts.size():count;
				objs = new Fentrust[realCount] ;
				Iterator<Fentrust> iterator = fentrusts.iterator() ;
				int index = 0 ;
				while(realCount>0){
					objs[index++] = iterator.next() ;
					realCount-- ;
				}
			}
		}
		return objs ;
	
	}
public Fentrust[] getSellDepthMap(int id,int count) {
		

		Fentrust[] objs = new Fentrust[0] ;
		synchronized (sellDepthMap) {
			TreeSet<Fentrust> fentrusts = null;
			fentrusts = sellDepthMap.get(id);
			int realCount = 0 ;
			if(fentrusts!=null){
				realCount = count>fentrusts.size()?fentrusts.size():count;
				objs = new Fentrust[realCount] ;
				Iterator<Fentrust> iterator = fentrusts.iterator() ;
				int index = 0 ;
				while(realCount>0){
					objs[index++] = iterator.next() ;
					realCount-- ;
				}
			}
		}
		return objs ;
	
	}
	public Fentrust[] getEntrustLimitSellMap(int id,int count) {
		

		Fentrust[] objs = new Fentrust[0] ;
		synchronized (entrustLimitSellMap) {
			TreeSet<Fentrust> fentrusts = null;
			fentrusts = entrustLimitSellMap.get(id);
			int realCount = 0 ;
			if(fentrusts!=null){
				realCount = count>fentrusts.size()?fentrusts.size():count;
				objs = new Fentrust[realCount] ;
				Iterator<Fentrust> iterator = fentrusts.iterator() ;
				int index = 0 ;
				while(realCount>0){
					objs[index++] = iterator.next() ;
					realCount-- ;
				}
			}
		}
		return objs ;
		
	}
	
	public Integer[] getEntrustSellMapKeys() {
		Object[] objs = entrustSellMap.keySet().toArray();
		Integer[] ints = new Integer[objs.length] ;
		for (int i = 0; i < objs.length; i++) {
			ints[i]=(Integer) objs[i] ;
		}
		return ints ;
	}
	
	public Integer[] getEntrustLimitSellMapKeys() {
		Object[] objs = entrustLimitSellMap.keySet().toArray();
		Integer[] ints = new Integer[objs.length] ;
		for (int i = 0; i < objs.length; i++) {
			ints[i]=(Integer) objs[i] ;
		}
		return ints ;
	}

	public  void addEntrustSellMap(int id,Fentrust fentrust) {
		synchronized (entrustSellMap) {
			TreeSet<Fentrust> fentrusts = entrustSellMap.get(id) ;
			if(fentrusts==null){
				fentrusts = new TreeSet<Fentrust>(prizeComparatorASC) ;
			}
			if(fentrusts.contains(fentrust)){
				fentrusts.remove(fentrust) ;
			}
			fentrusts.add(fentrust) ;
			
			entrustSellMap.put(id, fentrusts) ;
			changeRefreshSellDepthData(id, true) ;
		}
	}
	public  void addEntrustLimitSellMap(int id,Fentrust fentrust) {
		synchronized (entrustLimitSellMap) {
		TreeSet<Fentrust> fentrusts = entrustLimitSellMap.get(id) ;
		if(fentrusts==null){
			fentrusts = new TreeSet<Fentrust>(prizeComparatorASC) ;
		}
		if(fentrusts.contains(fentrust)){
			fentrusts.remove(fentrust) ;
		}
		fentrusts.add(fentrust) ;
		
		entrustLimitSellMap.put(id, fentrusts) ;
		}
	}
	
	public  void removeEntrustSellMap(int id,Fentrust fentrust){
		synchronized (entrustSellMap) {
			TreeSet<Fentrust> treeSet = entrustSellMap.get(id) ;
			if(treeSet!=null){
				treeSet.remove(fentrust) ;
			}
			entrustSellMap.put(id, treeSet) ;
			changeRefreshSellDepthData(id, true) ;
		}
	}
	public  void removeEntrustLimitSellMap(int id,Fentrust fentrust){
		synchronized (entrustLimitSellMap) {
		TreeSet<Fentrust> treeSet = entrustLimitSellMap.get(id) ;
		if(treeSet!=null){
			treeSet.remove(fentrust) ;
		}
		entrustLimitSellMap.put(id, treeSet) ;
		}
	}

	/**
	 * 获取内部买单
	 * @param id
	 * @param count
	 * @return
	 */
	public Fentrust[] getInsideEntrustBuyMap(int id,int count) {
		Fentrust[] objs = new Fentrust[0] ;
		synchronized (insideEntrustBuyMap) {
			TreeSet<Fentrust> fentrusts = null;
			fentrusts = insideEntrustBuyMap.get(id);
			int realCount = 0 ;
			if(fentrusts!=null){
				realCount = count>fentrusts.size()?fentrusts.size():count;
				objs = new Fentrust[realCount] ;
				Iterator<Fentrust> iterator = fentrusts.iterator() ;
				int index = 0 ;
				while(realCount>0){
					objs[index++] = iterator.next() ;
					realCount-- ;
				}
			}
		}
		return objs ;
	}

	/**
	 * 获取内部卖单
	 * @param id
	 * @param count
	 * @return
	 */
	public Fentrust[] getInsideEntrustSellMap(int id,int count) {
		Fentrust[] objs = new Fentrust[0] ;
		synchronized (insideEntrustSellMap) {
			TreeSet<Fentrust> fentrusts = null;
			fentrusts = insideEntrustSellMap.get(id);
			int realCount = 0 ;
			if(fentrusts!=null){
				realCount = count>fentrusts.size()?fentrusts.size():count;
				objs = new Fentrust[realCount] ;
				Iterator<Fentrust> iterator = fentrusts.iterator() ;
				int index = 0 ;
				while(realCount>0){
					objs[index++] = iterator.next() ;
					realCount-- ;
				}
			}
		}
		return objs ;
	}

	/**
	 * 增加内部买单
	 * @param id
	 * @param fentrust
	 */
	public void addInsideEntrustBuyMap(int id,Fentrust fentrust) {
		synchronized (insideEntrustBuyMap) {
			TreeSet<Fentrust> fentrusts = insideEntrustBuyMap.get(id) ;
			if(fentrusts==null){
				fentrusts = new TreeSet<Fentrust>(prizeComparatorDESC) ;
			}
			if(fentrusts.contains(fentrust)){
				fentrusts.remove(fentrust) ;
			}
			fentrusts.add(fentrust) ;
			insideEntrustBuyMap.put(id, fentrusts) ;
		}
	}

	/**
	 * 增加内部卖单
	 * @param id
	 * @param fentrust
	 */
	public  void addInsideEntrustSellMap(int id,Fentrust fentrust) {
		synchronized (insideEntrustSellMap) {
			TreeSet<Fentrust> fentrusts = insideEntrustSellMap.get(id) ;
			if(fentrusts==null){
				fentrusts = new TreeSet<Fentrust>(prizeComparatorASC) ;
			}
			if(fentrusts.contains(fentrust)){
				fentrusts.remove(fentrust) ;
			}
			fentrusts.add(fentrust) ;
			insideEntrustSellMap.put(id, fentrusts) ;
		}
	}

	/**
	 * 移除内部买单
	 * @param id
	 * @param fentrust
	 */
	public  void removeInsideEntrustBuyMap(int id,Fentrust fentrust){
		synchronized (insideEntrustBuyMap) {
			TreeSet<Fentrust> treeSet = insideEntrustBuyMap.get(id);
			if(treeSet!=null){
				treeSet.remove(fentrust);
				insideEntrustBuyMap.put(id, treeSet);
			}
		}
	}

	/**
	 * 移除内部卖单
	 * @param id
	 * @param fentrust
	 */
	public  void removeInsideEntrustSellMap(int id,Fentrust fentrust){
		synchronized (insideEntrustSellMap) {
			TreeSet<Fentrust> treeSet = insideEntrustSellMap.get(id) ;
			if(treeSet!=null){
				treeSet.remove(fentrust) ;
				insideEntrustSellMap.put(id, treeSet) ;
			}
		}
	}


	private LinkedList<Fentrustlog> fentrustlogs = new LinkedList<>();



	public synchronized Fentrustlog getOneIntegralLog() {
		synchronized (fentrustlogs) {
			Fentrustlog fentrustlog = null ;
			if(fentrustlogs.size()>0){
				fentrustlog = fentrustlogs.pop() ;
			}
			return fentrustlog ;
		}
	}

	public synchronized void addIntegral(Fentrustlog fentrustlog){
		synchronized (fentrustlogs) {
			fentrustlogs.add(fentrustlog) ;
			LOG.i(this.getClass(),"当前需");
		}
	}


	
}


