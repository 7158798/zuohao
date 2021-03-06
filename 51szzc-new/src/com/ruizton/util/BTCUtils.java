package com.ruizton.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.ruizton.main.Enum.CoinType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ruizton.main.model.BTCInfo;
import com.ruizton.main.model.BTCMessage;

public class BTCUtils implements IWalletUtil {
	//用户名
	private  String ACCESS_KEY = null;
	//密码
	private  String SECRET_KEY = null;
	//钱包IP地址
	private  String IP = null;
	//端口
	private  String PORT = null;
	//比特币钱包密码
	private  String PASSWORD = null;
	// 虚拟币类型
	private String COIN_TYPE = null;

	private Boolean zecFlag = Boolean.FALSE;
	
	private  String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	
	public BTCUtils(BTCMessage btcMessage) {
		this.ACCESS_KEY = btcMessage.getACCESS_KEY();
		this.SECRET_KEY = btcMessage.getSECRET_KEY();
		this.IP = btcMessage.getIP();
		this.PORT = btcMessage.getPORT();
		this.PASSWORD = btcMessage.getPASSWORD();
		this.COIN_TYPE = btcMessage.getCOIN_TYPE();
		if (CoinType.ZEC.equals(this.COIN_TYPE)){
			zecFlag = Boolean.TRUE;
		}
	}
	
	public String getSignature(String data, String key) throws Exception {
		// get an hmac_sha1 key from the raw key bytes
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(),
				HMAC_SHA1_ALGORITHM);

		// get an hmac_sha1 Mac instance and initialize with the signing key
		Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		mac.init(signingKey);

		// compute the hmac on input data bytes
		byte[] rawHmac = mac.doFinal(data.getBytes());
		return bytArrayToHex(rawHmac);
	}

	private String bytArrayToHex(byte[] a) {
		StringBuilder sb = new StringBuilder();
		for (byte b : a)
			sb.append(String.format("%02x", b & 0xff));
		return sb.toString();
	}
	
	//The easiest way to tell Java to use HTTP Basic authentication is to set a default Authenticator: 
	private void authenticator() {
		Authenticator.setDefault(new Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		          return new PasswordAuthentication (ACCESS_KEY, SECRET_KEY.toCharArray());
		      }
		});
	}
	
	/***
	 * 取得钱包相关信息
	 * {"result":{"version":80300,"protocolversion":70001,"walletversion":60000,
	 * "balance":0.00009500,"blocks":284795,"timeoffset":-1,"connections":6,
	 * "proxy":"","difficulty":2621404453.06461525,"testnet":false,
	 * "keypoololdest":1388131357,"keypoolsize":102,"paytxfee":0.00000000,
	 * "errors":""},"error":null,"id":1}
	 * 若获取失败，result为空，error信息为错误信息的编码
	 * */
	public JSONObject getInfo() throws Exception {
		String s = main("getinfo", "[]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	/***
	 * 取得钱包余额
	 * {"result":9.5E-5,"error":null,"id":1}
	 * {"result":0,"error":null,"id":1}
	 * 若获取失败，result为空，error信息为错误信息的编码
	 * */
	public JSONObject getbalance() throws Exception {
		String s = main("getbalance", "[]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	public JSONObject isValidateaddress(String address) throws Exception {
		String s = main("validateaddress", "[\""+address+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	public double getbalanceValue() throws Exception {
		double result = 0d;
		JSONObject s = getbalance();
        if(s.containsKey("result")){
        	result =Double.valueOf(s.get("result").toString());
        }
		return result;
	}
	
	//判断地址是否有效
	public boolean validateaddress(String address) {
		boolean result = false;
		try {
			JSONObject s = isValidateaddress(address);
			if(s.containsKey("result")){
				String xx =JSONObject.fromObject(s.get("result")).getString("isvalid");
				if(xx.equals("true")){
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/***
	 * 根据用户ID，生成交易地址
	 * @throws Exception 
	 * {"result":"1MySAsi6bzPLY3HbCcrDd7qgiG7CYnQn3k","error":null,"id":1}
	 * 若获取失败，result为空，error信息为错误信息的编码
	 * */
	public JSONObject getNewaddress(int userId) throws Exception {
		String s = main("getnewaddress", "[\""+userId+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	public String getNewaddressValue(int userId) throws Exception {
		String result = null;
		if(PASSWORD != null && PASSWORD.trim().length() >0){
			walletpassphrase(30);
		}
		JSONObject s = getNewaddress(userId);
		if(PASSWORD != null && PASSWORD.trim().length() >0){
			walletlock();
		}
        if(s.containsKey("result")){
        	result = s.get("result").toString();
        	if(result.equals("null")){
        		result = null;
        	}
        }
		return result;
	}
	
	public JSONObject getNewaddressForAdmin(String userId) throws Exception {
		String s;
		if (zecFlag){
			// zec的场景
			s = main("getnewaddress", "[]");
		} else {
			s = main("getnewaddress", "[\""+userId+"\"]");
		}
		JSONObject json = JSONObject.fromObject(s);
        return json;
	}
	
	public String getNewaddressValueForAdmin(String userId) throws Exception {
		String result = null;
		if(PASSWORD != null && PASSWORD.trim().length() >0){
			walletpassphrase(30);
		}
		JSONObject s = getNewaddressForAdmin(userId);
		if(PASSWORD != null && PASSWORD.trim().length() >0){
			walletlock();
		}
        if(s.containsKey("result")){
        	result = s.get("result").toString();
        	if(result.equals("null")){
        		result = null;
        	}
        }
        
		return result;
	}
	
	/**
	 * 根据用户ID，获取交易记录
	 *  {"result":[{"account":"test","address":"i6W5Ng4X49gJDAVnPafFm3rQwEAfU28SUo",
	 *  "category":"receive","amount":10,"confirmations":166,
	 *  "blockhash":"9b227a05cce30b33d991199af734cdd72171c6db608ec36aa71f48952ad1a639",
	 *  "blockindex":1,"txid":"4ed4de64672cb86cc3458a12a8a1d1c4a79478627a536ec62033367ba180ffc9",
	 *  "time":1391943409,"comment":"","from":"","message":"","to":""}],"error":null,"id":1}
		没有交易记录的结果：{"result":[],"error":null,"id":1}
		若获取失败，result为空，error信息为错误信息的编码
	 * */
	public JSONObject listtransactions(int userId) throws Exception {
		String s = main("listtransactions", "[\""+userId+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	/**
	 * 查所有
	 * **/
	public JSONObject listtransactions(int count,int from) throws Exception {
		String s = main("listtransactions", "[\"*\","+count+","+from+"]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	/**
	 * 取得所有的收到的交易记录
	 * **/
	public List<BTCInfo> listtransactionsValue(int count,int from) throws Exception {
		JSONObject json = listtransactions(count, from);
		List<BTCInfo> all = new ArrayList();
        if(json.containsKey("result") && !json.get("result").toString().equals("null")){
        	List allResult = (List)json.get("result");
        	Iterator it = allResult.iterator();
        	while(it.hasNext()){
        		Map map = (Map)it.next();
        		if(map.get("category").toString().equals("receive")){
        			BTCInfo info = new BTCInfo();
        			info.setAccount(map.get("account")+"");
        			info.setAddress(map.get("address")+"");
        			info.setAmount(Double.valueOf(map.get("amount").toString()));
        			info.setCategory(map.get("category")+"");
        			info.setComment(map.get("comment")+"");
        			try {
						if(map.get("confirmations") != null 
								&& map.get("confirmations").toString().trim().length() >0){
							info.setConfirmations(Integer.parseInt(map.get("confirmations").toString()));
						}
					} catch (Exception e) {
						info.setConfirmations(0);
					}
        			try {
						long time = Long.parseLong(map.get("time").toString());
						info.setTime(new Date(time));
					} catch (Exception e) {
						info.setTime(new Date());
					}
        			info.setTxid(map.get("txid")+"");
        			all.add(info);
        		}
        	}
        }
        Collections.reverse(all);
        return all;
	}
	
	/***
	 * 以地址GROUP BY
	 * 查出所有收到的币以地址
	 * [
		{
		"address" : "YBjKaMR7XC3yLck2aaNmkJiy2cbr37jezD",
		"account" : "fdafdsa",
		"amount" : 0.40000000,
		"confirmations" : 210
		},
		]
	 * */
	public JSONObject listreceivedbyaddress() throws Exception {
		String s = main("listreceivedbyaddress", "[]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	/***
	 * 
	 * 根据交易ID取得交易明细
	 * */
	private JSONObject gettransaction(String xid) throws Exception {
		String s = main("gettransaction", "[\""+xid+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	/**
	 * category receive or send
	 * **/
	public BTCInfo gettransactionValue(String xid,String category) throws Exception {
		JSONObject json = gettransaction(xid);
		BTCInfo btcInfo = null ;
        if(json.containsKey("result") && !json.get("result").toString().equals("null")){
        	btcInfo = new BTCInfo() ;
        	
        	Map map = (Map)json.get("result");
        	List xList = (List)map.get("details");
        	Iterator it = xList.iterator();
        	while(it.hasNext()){
        		Map xMap = (Map)it.next();
        		if(xMap.get("category").toString().equals(category)){
                	btcInfo.setAccount(xMap.get("account")+"");
        			btcInfo.setAddress(xMap.get("address")+"");
        			btcInfo.setAmount(Double.valueOf(xMap.get("amount").toString()));
        			break;
        		}
        	}
			btcInfo.setCategory(category);
			btcInfo.setComment(map.get("comment")+"");
			try {
				if(map.get("confirmations") != null 
						&& map.get("confirmations").toString().trim().length() >0){
					btcInfo.setConfirmations(Integer.parseInt(map.get("confirmations").toString()));
				}
			} catch (Exception e) {
				btcInfo.setConfirmations(0);
			}
			try {
				long time = Long.parseLong(map.get("time").toString());
				btcInfo.setTime(new Date(time));
			} catch (Exception e) {
				btcInfo.setTime(new Date());
			}
			btcInfo.setTxid(map.get("txid")+"");
        }
        return btcInfo;
	}
	
	/***
	 * 查出所有的币以标签
	 * 
		[
		{
		"account" : "dfasf",
		"amount" : 0.50100000,
		"confirmations" : 575
		},
		]
	 * */
	public JSONObject listreceivedbyaccount() throws Exception {
		String s = main("listreceivedbyaddress", "[]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	
	/***
	 * 根据收款地址，提现比特币
	 * Returns the transaction ID <txid> if successful.
	 * {"result":"2278857ca4ab04b41b36fe0f5ec8415d8e72c66a4688c60d8d2eefe994d3042c","error":null,"id":1}
	 * 若获取失败，result为空，error信息为错误信息的编码
	 * {"result":null,"error":500,"id":1}
	 * */
	public JSONObject sendtoaddress(String address,double account,String comment) throws Exception {
		if(PASSWORD != null && PASSWORD.trim().length() >0){
			walletpassphrase(30);
		}
		String s;
		if (zecFlag){
			// zec
			s = main("sendtoaddress", "[\""+address+"\","+account+","+"\""+comment+"\",\"" + comment +"\"]");
		} else {
			s = main("sendtoaddress", "[\""+address+"\","+account+","+"\""+comment+"\"]");
		}
		if(PASSWORD != null && PASSWORD.trim().length() >0){
			walletlock();
		}
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	public String sendtoaddressValue(String address,double account,double ffees,String comment) throws Exception {
		String result = "";
//		settxfee(ffees);
		JSONObject s = sendtoaddress(address, account, comment);
        if(s.containsKey("result")){
        	if(!s.get("result").toString().equals("null")){
        		result = s.get("result").toString();
        	}
        }
		return result;
	}
	
	//设置手续费
	public void settxfee(double ffee) throws Exception {
		JSONArray js = new JSONArray();
		js.add(ffee);
		main("settxfee",js.toString());
	}
	
	//解锁
	//解锁
	public boolean walletpassphrase(int times) throws Exception {
		boolean flag = false;
		try {
			String s =main("walletpassphrase","[\""+PASSWORD+"\","+times+"]");
			JSONObject json = JSONObject.fromObject(s); 
			if(json.containsKey("error")){
				String error = json.get("error").toString();
				if(error.equals("null") || error == null || error == "" || error.trim().length() ==0){
					flag = true;
				}
			}
		} catch (Exception e) {}
		return flag;
	}
	
	//锁
	public void walletlock() throws Exception {
		// 非zec
		main("walletlock","[]");
	}
	
	private String main(String method,String condition) throws Exception {
        String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		authenticator();
		
		String params = "tonce=" + tonce.toString() + "&accesskey="
				+ ACCESS_KEY
				+ "&requestmethod=post&id=1&method="+method+"&params="+condition;
		String hash;
		if (zecFlag){
			// ZEC的场景
			hash = SECRET_KEY;
		} else {
			hash = getSignature(params, SECRET_KEY);
		}

		String url = "http://"+ACCESS_KEY+":"+SECRET_KEY+"@"+IP+":"+PORT;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		String userpass = ACCESS_KEY + ":" + hash;
		String basicAuth = "Basic "
				+ DatatypeConverter.printBase64Binary(userpass.getBytes());

		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());
		con.setRequestProperty("Authorization", basicAuth);

		String postdata = "{\"method\":\""+method+"\", \"params\":"+condition+", \"id\": 1}";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(postdata);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		if(responseCode != 200){
			return "{\"result\":null,\"error\":"+responseCode+",\"id\":1}";
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		inputLine = in.readLine();
		response.append(inputLine);
		in.close();
		result = response.toString();
		return result;
	}
	
	public static void main(String args[]) throws Exception{

		BTCMessage btcMessage = new BTCMessage() ;
        /*btcMessage.setIP("130.252.101.153") ;
		btcMessage.setPORT("8888") ;
		btcMessage.setPASSWORD("sax1314");
		btcMessage.setACCESS_KEY("51szzc");
		btcMessage.setSECRET_KEY("szzc2017") ;*/
		btcMessage.setIP("localhost") ;
		btcMessage.setPORT("8232") ;
		btcMessage.setPASSWORD("123456781");
		btcMessage.setACCESS_KEY("szzc");
		btcMessage.setSECRET_KEY("szzc2017");
		btcMessage.setCOIN_TYPE(CoinType.ZEC);
		//
		BTCUtils btcUtils = new BTCUtils(btcMessage);
		boolean flag = btcUtils.walletpassphrase(60);
		System.out.print("flag " + flag);
		//String address = btcUtils.getNewaddressValueForAdmin("test");


		/*double value = btcUtils.getbalanceValue();
		System.out.println("value = " + value);*/
		// List<BTCInfo>  list =  btcUtils.listtransactionsValue(100, 0);
		/*BTCInfo list =  btcUtils.gettransactionValue("79458b4fdad10c8c1dd1677b802403a121342a2d22eb84ff211e82ed3b336675","receive");
		System.out.print("test");*/
		//boolean flag = btcUtils.validateaddress("ztLgktp9FRULSnMd9hTwDXJkQ5sVCJwiP3LvuxLM7pNrXxHScAP53aE2GRb1EiB42VQg3w3cYQ6JuSFQxnYtmz93GPrdvBh");
		//System.out.print("test" + address);
	}
	
}