package com.ruizton.main.controller.front;

import com.ruizton.main.auto.RealTimeData;
import com.ruizton.main.comm.KeyValues;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fentrust;
import com.ruizton.main.model.Fentrustlog;
import com.ruizton.main.model.Fperiod;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.util.Utils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class FrontMarketJsonController extends BaseController {


	
	//交易中心
	@ResponseBody
	@RequestMapping(value="/real/market",produces={JsonEncode})
	public String marketRefresh(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int symbol
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping(symbol) ;
		if(ftrademapping == null ){
			return null ;
		}
		
		jsonObject.accumulate("p_new", Utils.getDouble(this.realTimeData.getLatestDealPrize(ftrademapping.getFid()), ftrademapping.getFcount1()));
		jsonObject.accumulate("high", Utils.getDouble(this.oneDayData.getHighest(ftrademapping.getFid()), ftrademapping.getFcount1()));
		jsonObject.accumulate("low", Utils.getDouble(this.oneDayData.getLowest(ftrademapping.getFid()), ftrademapping.getFcount1()));
		jsonObject.accumulate("vol", Utils.getDouble(this.oneDayData.getTotal(ftrademapping.getFid()), ftrademapping.getFcount2()));
		jsonObject.accumulate("buy1", Utils.getDouble(this.realTimeData.getHighestBuyPrize(ftrademapping.getFid()), ftrademapping.getFcount1()));
		jsonObject.accumulate("sell1", Utils.getDouble(this.realTimeData.getLowestSellPrize(ftrademapping.getFid()), ftrademapping.getFcount1()));
		
		
		Object[] buyEntrusts = null ;
		Object[] sellEntrusts = null ;
		Object[] successEntrusts = this.realTimeData.getEntrustSuccessMap(ftrademapping.getFid(),5) ;
			buyEntrusts = this.realTimeData.getBuyDepthMap(ftrademapping.getFid(), 5);
			sellEntrusts = this.realTimeData.getSellDepthMap(ftrademapping.getFid(), 5);
		JSONArray sellDepthList = new JSONArray() ;
		for (int i = 0; i < sellEntrusts.length && i<5; i++) {
			Fentrust fentrust = (Fentrust) sellEntrusts[i] ;
			JSONObject js1 = new JSONObject();
			js1.accumulate("id",i+1) ;
			js1.accumulate("price",fentrust.getFprize()) ;
			js1.accumulate("amount",Utils.getDoubleS(fentrust.getFleftCount(), ftrademapping.getFcount2())) ;
			sellDepthList.add(js1);
		}
		jsonObject.accumulate("sells", sellDepthList);
		
		
		JSONArray buyDepthList = new JSONArray() ;
		for (int i = 0; i < buyEntrusts.length && i<5; i++) {
			JSONObject js1 = new JSONObject();
			Fentrust fentrust = (Fentrust) buyEntrusts[i] ;
			js1.accumulate("id",i+1) ;
			js1.accumulate("price",fentrust.getFprize()) ;
			js1.accumulate("amount",Utils.getDoubleS(fentrust.getFleftCount(), ftrademapping.getFcount2())) ;
			
			buyDepthList.add(js1);
		}
		jsonObject.accumulate("buys", buyDepthList);
		
		JSONArray recentDealList = new JSONArray() ;
		for (int i = 0; i < successEntrusts.length && i<5; i++) {
			JSONObject js1 = new JSONObject();
			Fentrustlog fentrust = (Fentrustlog) successEntrusts[i] ;
			
			js1.accumulate("id",i+1) ;
			js1.accumulate("time",String.valueOf(new SimpleDateFormat("HH:mm:ss").format(fentrust.getFcreateTime()))) ;
			js1.accumulate("price",String.valueOf(fentrust.getFprize())) ;
			js1.accumulate("amount",Utils.getDoubleS(fentrust.getFcount(), ftrademapping.getFcount2())) ;
			String en_type="";
			String cn_type = "";
			if(fentrust.getfEntrustType() ==0){
				en_type="bid";
//				cn_type="买入";
				cn_type = i18nMsg("trade_buy");
			}else{
				en_type="ask";
//				cn_type="卖出";
				cn_type = i18nMsg("trade_sell");
			}
			js1.accumulate("en_type",en_type) ;
			js1.accumulate("type",cn_type) ;
			recentDealList.add(js1);
		}
		jsonObject.accumulate("trades", recentDealList);
		
		return jsonObject.toString() ;
	}


	//转换交易深度
	private Object[] transferByDepth(float depth, Object[] buyEntrusts, int count, Comparator<Fentrust> comparator) {
		//截取小数位
       for( int i=0;i<buyEntrusts.length;i++ ){
		   Fentrust fentrust= (Fentrust)buyEntrusts[i];
		   if(depth==0.1f){
			   fentrust.setFprize(toDouble(1,BigDecimal.ROUND_DOWN,fentrust.getFprize()));
		   }else{
			   fentrust.setFprize(toDouble(0,BigDecimal.ROUND_DOWN,fentrust.getFprize()));
		   }

		  // buyEntrusts[i]= fentrust;
	   }


      //累加重复值
		Map<String, KeyValues> map = new HashMap<String, KeyValues>() ;
		for (Fentrust fentrust : (Fentrust[]) buyEntrusts) {
			if(fentrust==null){
				continue ;
			}
			String key = String.valueOf(fentrust.getFprize()) ;
			if(!map.containsKey(key)){
				KeyValues	keyValues = new KeyValues() ;
				keyValues.setKey(fentrust.getFprize()) ;
				keyValues.setValue(fentrust.getFleftCount()) ;
				map.put(key, keyValues) ;
			}else{
				map.get(key).setValue((Double)map.get(key).getValue()+fentrust.getFleftCount()) ;
			}
		}

      //排序
		TreeSet<Fentrust> fentrusts = new TreeSet<Fentrust>(comparator) ;

		for (Map.Entry<String, KeyValues> entry : map.entrySet()) {
			Fentrust fentrust = new Fentrust() ;
			fentrust.setFprize((Double) entry.getValue().getKey()) ;
			fentrust.setFleftCount((Double) entry.getValue().getValue()) ;
			fentrusts.add(fentrust) ;
		}



		Fentrust[] newObjs = new Fentrust[0] ;
			int realCount = 0 ;
			if(fentrusts!=null){
				realCount = count>fentrusts.size()?fentrusts.size():count;
				newObjs = new Fentrust[realCount] ;
				Iterator<Fentrust> iterator = fentrusts.iterator() ;
				int index = 0 ;
				while(realCount>0){
					newObjs[index++] = iterator.next() ;
					realCount-- ;
				}
			}

		return newObjs ;

	}




private  double toDouble(int scale,int mode,Double d){

		BigDecimal bigDecimal=new BigDecimal(d.toString());
		bigDecimal= bigDecimal.setScale(scale,mode);//设置小数位
    return     bigDecimal.doubleValue();

}


	//K线中心
	@ResponseBody
	@RequestMapping(value="/real/market2",produces={JsonEncode})
	public String marketRefresh2(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int symbol,@RequestParam(required=false,defaultValue="0.01")float depth
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping(symbol) ;
		if(ftrademapping == null ){
			return null ;
		}
		
		jsonObject.accumulate("p_new", Utils.getDouble(this.realTimeData.getLatestDealPrize(ftrademapping.getFid()), ftrademapping.getFcount1()));
		jsonObject.accumulate("high", Utils.getDouble(this.oneDayData.getHighest(ftrademapping.getFid()), ftrademapping.getFcount1()));
		jsonObject.accumulate("low", Utils.getDouble(this.oneDayData.getLowest(ftrademapping.getFid()), ftrademapping.getFcount1()));
		jsonObject.accumulate("vol", Utils.getDouble(this.oneDayData.getTotal(ftrademapping.getFid()), ftrademapping.getFcount1()));
		jsonObject.accumulate("buy1", Utils.getDouble(this.realTimeData.getHighestBuyPrize(ftrademapping.getFid()), ftrademapping.getFcount1()));
		jsonObject.accumulate("sell1", Utils.getDouble(this.realTimeData.getLowestSellPrize(ftrademapping.getFid()), ftrademapping.getFcount1()));
		
		
		Object[] buyEntrusts = null ;
		Object[] sellEntrusts = null ;
		Object[] successEntrusts = this.realTimeData.getEntrustSuccessMap(ftrademapping.getFid(),20) ;
			if(depth==0.01F) {
				buyEntrusts = this.realTimeData.getBuyDepthMap(ftrademapping.getFid(), 20);
				sellEntrusts = this.realTimeData.getSellDepthMap(ftrademapping.getFid(), 20);
			}

		if(depth==0.1F||depth==1F){
			//取出所有精度为0.01的深度集合
			buyEntrusts = this.realTimeData.getBuyDepthMap(ftrademapping.getFid(), Integer.MAX_VALUE);
			sellEntrusts = this.realTimeData.getSellDepthMap(ftrademapping.getFid(), Integer.MAX_VALUE);
			buyEntrusts=	transferByDepth(depth,buyEntrusts,20,RealTimeData.prizeComparatorDESC);
			sellEntrusts=transferByDepth(depth,sellEntrusts,20,RealTimeData.prizeComparatorASC);
		}

		
		JSONArray sellDepthList = new JSONArray() ;
		for (int i = 0; i < sellEntrusts.length && i<20; i++) {
			Fentrust fentrust = (Fentrust) sellEntrusts[i] ;
			if(fentrust.getFleftCount()< Math.pow(10, -8)){
				continue;
			}
			JSONObject js1 = new JSONObject();
			js1.accumulate("id",i+1) ;
			js1.accumulate("price",fentrust.getFprize()) ;
			js1.accumulate("amount",Utils.getDoubleS(fentrust.getFleftCount(), ftrademapping.getFcount2())) ;
			sellDepthList.add(js1);
		}
		jsonObject.accumulate("sells", sellDepthList);
		
		
		JSONArray buyDepthList = new JSONArray() ;
		for (int i = 0; i < buyEntrusts.length && i<20; i++) {
			JSONObject js1 = new JSONObject();
			Fentrust fentrust = (Fentrust) buyEntrusts[i] ;
			if(fentrust.getFleftCount()< Math.pow(10, -8)){
				continue;
			}
			js1.accumulate("id",i+1) ;
			js1.accumulate("price",fentrust.getFprize()) ;
			js1.accumulate("amount",Utils.getDoubleS(fentrust.getFleftCount(), ftrademapping.getFcount2())) ;
			
			buyDepthList.add(js1);
		}
		jsonObject.accumulate("buys", buyDepthList);
		
		JSONArray recentDealList = new JSONArray() ;
		for (int i = 0; i < successEntrusts.length && i<20; i++) {
			JSONObject js1 = new JSONObject();
			Fentrustlog fentrust = (Fentrustlog) successEntrusts[i] ;

			if(fentrust.getFcount()< Math.pow(10, -8)){
				continue;
			}
			js1.accumulate("id",i+1) ;
			js1.accumulate("time",String.valueOf(new SimpleDateFormat("HH:mm:ss").format(fentrust.getFcreateTime()))) ;
			js1.accumulate("price",String.valueOf(fentrust.getFprize())) ;
			js1.accumulate("amount",Utils.getDoubleS(fentrust.getFcount(), ftrademapping.getFcount2())) ;
			String en_type="";
			String cn_type = "";
			if(fentrust.getfEntrustType() ==0){
				en_type="bid";
//				cn_type="买入";
				cn_type = i18nMsg("trade_buy");
			}else{
				en_type="ask";
//				cn_type="卖出";
				cn_type = i18nMsg("trade_sell");
			}
			js1.accumulate("en_type",en_type) ;
			js1.accumulate("type",cn_type) ;
			recentDealList.add(js1);
		}
		jsonObject.accumulate("trades", recentDealList);
		
		return jsonObject.toString() ;
	}
	
	@ResponseBody
	@RequestMapping("/kline/fullperiod")
	public String period(
			@RequestParam(required=true)int step,
			@RequestParam(required=true)int symbol
			) throws Exception{
		
		int key = 0 ;
		switch (step) {
		case 60:
			key = 0 ;
			break;
		case 60*3:
			key = 1 ;
			break;
		case 60*5:
			key = 2 ;
			break;
		case 60*15:
			key = 3 ;
			break;
		case 60*30:
			key = 4 ;
			break;
		case 60*60:
			key = 5 ;
			break;
		case 60*60*2:
			key = 6 ;
			break;
		case 60*60*4:
			key = 7 ;
			break;
		case 60*60*6:
			key = 8 ;
			break;
		case 60*60*12:
			key = 9 ;
			break;
		case 60*60*24:
			key = 10 ;
			break;
		case 60*60*24*3:
			key = 11 ;
			break;
		case 60*60*24*7:
			key = 12 ;
			break;
		}
		long l= System.currentTimeMillis() ;
		String ret = null;
		try {
			ret = this.klinePeriodData.getJsonString(symbol, key) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret ;
	}
	
	@ResponseBody
	@RequestMapping("/kline/fulldepth")
	public String depth(
			@RequestParam(required=true)int symbol,
			@RequestParam(required=true)int step
			) throws Exception{
		
		
		long l= System.currentTimeMillis() ;
		JSONObject jsonObject = new JSONObject() ;
		
		Fentrust[] buy = null ;
		Fentrust[] sell = null ;
		buy = this.realTimeData.getBuyDepthMap(symbol,10);
		sell = this.realTimeData.getSellDepthMap(symbol,10);
		
		List<List<BigDecimal>> buyList = new ArrayList<List<BigDecimal>>() ;
		List<List<BigDecimal>> sellList = new ArrayList<List<BigDecimal>>() ;
		for (Fentrust fentrust:buy) {
			List<BigDecimal> list = new ArrayList<BigDecimal>() ;
			list.add(new BigDecimal(String.valueOf(fentrust.getFprize())).setScale(6, BigDecimal.ROUND_DOWN)) ;
			list.add(new BigDecimal(String.valueOf(fentrust.getFleftCount())).setScale(4, BigDecimal.ROUND_DOWN)) ;
			buyList.add(list) ;
		}
		for (Fentrust fentrust:sell) {
			List<BigDecimal> list = new ArrayList<BigDecimal>() ;
			list.add(new BigDecimal(String.valueOf(fentrust.getFprize())).setScale(6, BigDecimal.ROUND_DOWN)) ;
			list.add(new BigDecimal(String.valueOf(fentrust.getFleftCount())).setScale(4, BigDecimal.ROUND_DOWN)) ;
			sellList.add(list) ;
		}
		JSONObject askBidJson = new JSONObject() ;
		askBidJson.accumulate("bids", buyList) ;
		askBidJson.accumulate("asks", sellList) ;
		askBidJson.accumulate("date", Utils.getTimestamp().getTime()/1000L) ;
		jsonObject.accumulate("depth", askBidJson) ;
		
		
		JSONObject periodJson = new JSONObject() ;
		periodJson.accumulate("marketFrom", symbol);
		periodJson.accumulate("coinVol", symbol);
		periodJson.accumulate("type", step);
		periodJson.accumulate("data",toArr( this.latestKlinePeroid.getPeriod(symbol, step/60)));
		jsonObject.accumulate("period", periodJson) ;		
		
		String  ret = jsonObject.toString() ;
		return ret ;
	}
	private String toArr(List<Fperiod> list){
		StringBuffer stringBuffer = new StringBuffer() ;
		stringBuffer.append("[") ;
		if(list != null && list.size() >0){
			for (int i=list.size()-1;i<list.size();i++) {
				Fperiod fperiod = list.get(i) ;
				stringBuffer.append("["+(fperiod.getFtime().getTime())
						+","+new BigDecimal(String.valueOf(fperiod.getFkai())).setScale(6, BigDecimal.ROUND_HALF_UP)
						+","+new BigDecimal(String.valueOf(fperiod.getFgao())).setScale(6, BigDecimal.ROUND_HALF_UP)
						+","+new BigDecimal(String.valueOf(fperiod.getFdi())).setScale(6, BigDecimal.ROUND_HALF_UP)
						+","+new BigDecimal(String.valueOf(fperiod.getFshou())).setScale(6, BigDecimal.ROUND_HALF_UP)
						+","+new BigDecimal(String.valueOf(fperiod.getFliang())).setScale(4, BigDecimal.ROUND_HALF_UP)+"]") ;
				if(i!=list.size()-1){
					stringBuffer.append(",") ;
				}
				
			}
		}
		stringBuffer.append("]") ;
		return stringBuffer.toString() ;
	}
	
	@ResponseBody
	@RequestMapping("/kline/trade_json")
	public String trade_json(
			@RequestParam(required=true )int id
			) throws Exception {
		
		StringBuffer content = new StringBuffer() ;
		content.append(
				"chart_1h = {symbol:\"BTC_CNY\",symbol_view:\"CNY/CNY\",ask:1.2,time_line:"+this.klinePeriodData.getIndexJsonString(id, 5) +"};"
				) ;
		
		content.append(
				"chart_5m = {time_line:"+this.klinePeriodData.getIndexJsonString(id, 2) +"};" 
				) ;
		content.append(
				"chart_15m = {time_line:"+this.klinePeriodData.getIndexJsonString(id, 3) +"};"
				) ;
		content.append(
				"chart_30m = {time_line:"+this.klinePeriodData.getIndexJsonString(id, 4) +"};"
				) ;
		content.append(
				"chart_8h = {time_line:"+this.klinePeriodData.getIndexJsonString(id, 8) +"};"
				) ;
		content.append(
				"chart_1d = {time_line:"+this.klinePeriodData.getIndexJsonString(id, 10) +"};"
				) ;
		
		return content.toString() ;
	}
}
