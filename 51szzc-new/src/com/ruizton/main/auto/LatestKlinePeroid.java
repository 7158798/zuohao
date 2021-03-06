package com.ruizton.main.auto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;

import com.ruizton.main.model.Fentrustlog;
import com.ruizton.main.model.Fperiod;
import com.ruizton.util.Utils;

public class LatestKlinePeroid {
	@Autowired
	private RealTimeData realTimeData ;
	
	public void init(){}
	
	private Map<Integer, Map<Integer, Fperiod>> periodMap = new HashMap<Integer, Map<Integer,Fperiod>>() ;
	public void clearPeriodMap(int id){
		synchronized (periodMap) {
			periodMap.remove(id) ;
		}
	}
	public void putPeriodMap(int id,int key,Fperiod fperiod){
		synchronized (periodMap) {
			Map<Integer, Fperiod> map = periodMap.get(id) ;
			if(map == null ){
				map = new HashMap<Integer, Fperiod>() ;
			}
			map.put(key, fperiod) ;
			periodMap.put(id, map) ;
		}
	}
	public Fperiod getPeriodMap(int id,int key){
		synchronized (periodMap) {
			Map<Integer, Fperiod> map = periodMap.get(id) ;
			if(map != null ){
				return map.get(key) ;
			}
			return null ;
		}
	}
	
	
	private LinkedList<Fentrustlog> fentrustlogs = new LinkedList<Fentrustlog>() ;
	private Map<Integer, List<Fperiod>> fperiods = new HashMap<Integer, List<Fperiod>>() ;
	
	
	public void cleanTime(long now){ 
		synchronized (fentrustlogs) {
			
			synchronized (fperiods) {
				fperiods.clear() ;
			}
			
			List<Fentrustlog> removelist = new ArrayList<Fentrustlog>() ;
			for (int i = 0; i < fentrustlogs.size(); i++) {
				Fentrustlog fentrustlog = fentrustlogs.get(i) ;
				if(fentrustlog.getFcreateTime().getTime()<now){
					removelist.add(fentrustlog) ;
				}else{
					try {
						calculateFentrustlog(fentrustlog) ;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
			for (Fentrustlog index : removelist) {
				fentrustlogs.remove(index) ;
			}
			
		}
	}
	
	public void pushFentrustlog(Fentrustlog fentrustlog){
		synchronized (fentrustlogs) {
			try {
				fentrustlogs.add(fentrustlog) ;
				calculateFentrustlog(fentrustlog) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm") ;
	public void calculateFentrustlog(Fentrustlog fentrustlog) throws Exception{
		double price = fentrustlog.getFprize() ;
		double count = fentrustlog.getFcount() ;
		
		synchronized (fperiods) {
			List<Fperiod> list = fperiods.get(fentrustlog.getFtrademapping().getFid()) ;
			if(list==null ){
				list = new ArrayList<Fperiod>() ;
			}
			Fperiod fperiod = null ;
			
			boolean flag = false ;
			if(list.size()>0){
				fperiod = list.get(list.size()-1) ;
				
				long time1 = simpleDateFormat.parse(simpleDateFormat.format(fperiod.getFtime())).getTime() ;
				long time2 = simpleDateFormat.parse(simpleDateFormat.format(fentrustlog.getFcreateTime())).getTime() ;
				if(time1==time2){
					fperiod.setFshou(price) ;
					fperiod.setFgao(fperiod.getFgao()>price?fperiod.getFgao():price) ;
					fperiod.setFdi(fperiod.getFdi()<price?fperiod.getFdi():price) ;
					fperiod.setFliang(fperiod.getFliang()+count) ;
					list.remove(list.size()-1) ;
					list.add(fperiod) ;
					flag = true ;
				}
			}
			
			if(flag == false ){
				fperiod = new Fperiod() ;
				fperiod.setFtime(new Timestamp(simpleDateFormat.parse(simpleDateFormat.format(fentrustlog.getFcreateTime())).getTime())) ;
				fperiod.setFgao(price) ;
				fperiod.setFkai(price) ;
				fperiod.setFdi(price) ;
				fperiod.setFshou(price) ;
				fperiod.setFliang(count) ;
				list.add(fperiod) ;
			}
			fperiods.put(fentrustlog.getFtrademapping().getFid(), list) ;
		}
	}
	
	
	public List<Fperiod> getPeriod(int id,int key){
		List<Fperiod> ret = new ArrayList<Fperiod>() ;
		synchronized (fperiods) {
			List<Fperiod> list = fperiods.get(id) ;
			
			Fperiod fperiod = getPeriodMap(id, key) ;
			if(fperiod == null){
				return null ;
			}
			if( list ==null){
				
				long nextTime = fperiod.getFtime().getTime();
				if(ret.size()==0){
					long now = Utils.getTimestamp().getTime() ;
					while(nextTime+key*60*1000L<now){
						nextTime+=key*60*1000L;
					}
					if(nextTime == fperiod.getFtime().getTime()){
						
						ret.add(fperiod) ;
					}else{
						Fperiod p = new Fperiod() ;
						double price = this.realTimeData.getLatestDealPrize(id) ;
						p.setFdi(price) ;
						p.setFgao(price) ;
						p.setFkai(price) ;
						p.setFliang(0) ;
						p.setFshou(price) ;
						p.setFtime(new Timestamp(nextTime)) ;
						ret.add(p) ;
					}
				}
				
				return ret ;
			}
			fperiod = newFperiod(fperiod) ;
			
			
			long nextTime = fperiod.getFtime().getTime()+key*60*1000L ;
			for (Fperiod fperiod2 : list) {
				if(fperiod2.getFtime().getTime()>=nextTime){
					ret.add(fperiod) ;
					fperiod = fperiod2 ;
					fperiod.setFtime(new Timestamp(nextTime) ) ;
					
					nextTime = fperiod.getFtime().getTime()+key*60*1000L ;
				}else{
					fperiod.setFshou(fperiod2.getFshou()) ;
					fperiod.setFgao(fperiod2.getFgao()>fperiod.getFgao()?fperiod2.getFgao():fperiod.getFgao()) ;
					fperiod.setFdi(fperiod2.getFdi()<fperiod.getFdi()?fperiod2.getFdi():fperiod.getFdi()) ;
					fperiod.setFliang(fperiod.getFliang()+fperiod2.getFliang()) ;
				}
				
			}
			ret.add(fperiod) ;
			
			
			return ret ;
		}
		
	}
	
	public Fperiod newFperiod(Fperiod fperiod){
		Fperiod ret = new Fperiod() ;
		ret.setFgao(fperiod.getFgao()) ;
		ret.setFdi(fperiod.getFdi()) ;
		ret.setFkai(fperiod.getFkai()) ;
		ret.setFshou(fperiod.getFshou()) ;
		ret.setFliang(fperiod.getFliang()) ;
		ret.setFtime(fperiod.getFtime()) ;
		return ret ;
	}
}
