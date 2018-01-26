package com.ruizton.main.comm;

import java.text.SimpleDateFormat;
import java.util.*;

import com.ruizton.main.Enum.TrademappingStatusEnum;
import com.ruizton.main.model.*;
import com.ruizton.main.service.admin.*;
import com.ruizton.util.Configuration;
import com.ruizton.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.LinkTypeEnum;
import com.ruizton.main.Enum.VirtualCoinTypeStatusEnum;
import com.ruizton.main.service.front.FrontOthersService;
import com.ruizton.main.service.front.FrontSystemArgsService;
import com.ruizton.main.service.front.FrontVirtualCoinService;

public class ConstantMap {
	private static final Logger log = LoggerFactory.getLogger(ConstantMap.class);
	@Autowired
	private FrontSystemArgsService frontSystemArgsService ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private FriendLinkService friendLinkService;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private TradehistoryService tradehistoryService;
	@Autowired
	private FrontOthersService frontOtherService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private TradeMappingService tradeMappingService;
	@Autowired
	private FrontOthersService  frontOthersService;

	// 推送集合
	public static Map<Integer, TreeMap<Integer, Fapppush>> pushMap = new HashMap<Integer, TreeMap<Integer, Fapppush>>();

	
	
	private static Map<String, Object> map = new HashMap<String, Object>() ;
	public void init(){
		log.info("Init SystemArgs ==> ConstantMap.") ;
		Map<String, String> tMap = this.frontSystemArgsService.findAllMap() ;
		for (Map.Entry<String, String> entry : tMap.entrySet()) {
			this.put(entry.getKey(), entry.getValue()) ;
		}
		log.info("Init virtualCoinType ==> ConstantMap.") ;
		
		String sql = "where fstatus=1";
		List<Fvirtualcointype> allCoins= this.virtualCoinService.list(0, 0, sql, false);
		map.put("allCoins", allCoins) ;
		
		List<Fvirtualcointype> fvirtualcointypes= this.frontVirtualCoinService.findFvirtualCoinType(VirtualCoinTypeStatusEnum.Normal,CoinTypeEnum.COIN_VALUE) ;
		map.put("virtualCoinType", fvirtualcointypes) ;
		
		{
			String filter = "where fstatus=1 and FIsWithDraw=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
			List<Fvirtualcointype> allWithdrawCoins= this.virtualCoinService.list(0, 0, filter, false);
			map.put("allWithdrawCoins", allWithdrawCoins) ;
		}
		
		{
			String filter = "where fstatus=1 and fisrecharge=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE;
			List<Fvirtualcointype> allRechargeCoins= this.virtualCoinService.list(0, 0, filter, false);
			map.put("allRechargeCoins", allRechargeCoins) ;
		}
		
		{
			String filter = "where ftype="+LinkTypeEnum.LINK_VALUE+" order by forder asc";
			List<Ffriendlink> ffriendlinks = this.friendLinkService.list(0, 0, filter, false);
			map.put("ffriendlinks", ffriendlinks) ;
		}
		
		{
			String filter = "where ftype="+LinkTypeEnum.QQ_VALUE+" order by forder asc";
			List<Ffriendlink> ffriendlinks = this.friendLinkService.list(0, 0, filter, false);
			map.put("quns", ffriendlinks) ;
		}
		
		map.put("webinfo", this.frontSystemArgsService.findFwebbaseinfoById(1)) ;
		
		{
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String key = sdf.format(c.getTime());
			String xx = "where DATE_FORMAT(fdate,'%Y-%m-%d') ='"+key+"'";
			List<Ftradehistory> ftradehistorys = this.tradehistoryService.list(0, 0, xx, false);
			map.put("tradehistory", ftradehistorys);
		}
		//网站帮助中心显示的滚动新闻
		List<Farticle> farticles = this.frontOtherService.findFarticle(2, 0, 6) ;
		if(farticles != null && farticles.size() >0){
			map.put("news", farticles);
		}
		List<Farticle> hostNews = this.frontOtherService.findHostArticle(0, 6) ;
		if(hostNews != null && hostNews.size() >0){
			map.put("hostNews", hostNews);
		}

		List<Farticle> notices = this.frontOtherService.findFarticle(3, 0, 5) ;
		if(notices != null && notices.size() >0){
			map.put("notices", notices);
		}

		List<Farticle> outArticle = this.frontOtherService.findOutArticle(0, 5) ;
		if(notices != null && notices.size() >0){
			map.put("outArticle", outArticle);
		}
		
		StringBuffer sf = new StringBuffer();
		sf.append("SELECT a.fvirtualcointype1,b.fName from ftrademapping a \n");
		sf.append("LEFT OUTER JOIN fvirtualcointype b on a.fvirtualcointype1=b.fid \n");
		sf.append("where a.fstatus=1 \n");
		sf.append("GROUP BY a.fvirtualcointype1,b.fName \n");
		Map fbs = this.adminService.getSQLValue1(sf.toString());
		map.put("fbs", fbs);
		
		Map<Integer,Integer> tradeMappings = new HashMap<Integer,Integer>();
		List<Ftrademapping> mappings = this.tradeMappingService.findAll();
		for (Ftrademapping ftrademapping : mappings) {
			tradeMappings.put(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid(), ftrademapping.getFid());
		}
		map.put("tradeMappings", tradeMappings);

		map.put("Domain", Configuration.getValue("Domain"));

		{

			List<Ftrademapping> all = this.tradeMappingService.list(0, 0, " where fstatus="+ TrademappingStatusEnum.ACTIVE+" ", false) ;
			Map<Integer,List> ftradehistory7D = new HashMap<Integer,List>();
			// 币种-对应id
			Map<String, Integer> coinTypeMap = new HashMap<String, Integer>();
			for (Ftrademapping ftrademapping : all) {
				coinTypeMap.put(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getfShortName().toLowerCase(), ftrademapping.getFid());
				List<String> day7String = Day7UpsDowns.getDays(7) ;
				List day7 = new ArrayList();
				for (String s : day7String) {
					String sql2 = "where DATE_FORMAT(fdate,'%Y-%m-%d') ='"+s+"' and ftrademapping.fid="+ftrademapping.getFid();
					List<Ftradehistory> ss = this.tradehistoryService.list(0, 1, sql2, true);
					if(ss != null && ss.size() >0){
						day7.add(ss.get(0).getFprice()) ;
					}else{
						day7.add(0d);
					}
				}
				System.out.print(day7);
				ftradehistory7D.put(ftrademapping.getFid().intValue(), day7);
			}
			map.put("ftradehistory7D", ftradehistory7D);
			map.put("activetradeMapping",all);
			map.put("coinType", coinTypeMap);
		}

		List<Farticletype> articletypes = this.frontOthersService.findFarticleTypeAll();
		map.put("articletypes",articletypes);
	}
	
	public Map<String, Object> getMap(){
		return this.map ;
	}
	
	public synchronized void put(String key,Object value){
		log.info("ConstantMap put key:"+key+",value:"+value+".") ;
		map.put(key, value) ;
	}
	
	public static Object get(String key){
		return map.get(key) ;
	}
	
	public static String getString(String key){
		return (String)map.get(key) ;
	}
}
