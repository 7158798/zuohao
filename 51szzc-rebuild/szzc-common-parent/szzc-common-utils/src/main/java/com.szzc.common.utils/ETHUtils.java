package com.szzc.common.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.common.utils.eth.resp.DebugResp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ETHUtils implements IWalletUtil{
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
	
	
	public static boolean validateaddress(String address){
		if (address.matches("^(0x)?[0-9a-f]{40}$")==false) {
	        return false;
	    }
		return true ;
	}
	public ETHUtils(BTCMessage btcMessage) {
		this.ACCESS_KEY = btcMessage.getACCESS_KEY();
		this.SECRET_KEY = btcMessage.getSECRET_KEY();
		this.IP = btcMessage.getIP();
		this.PORT = btcMessage.getPORT();
		this.PASSWORD = btcMessage.getPASSWORD();
	}
	
	public JSONObject getbalance(String address) throws Exception {
		String s = main("eth_getBalance", "[\""+address+"\", \"latest\"]");
		JSONObject json = JsonHelper.jsonStr2JsonObj(s);
        return json;
	}
	public JSONObject eth_accounts() throws Exception {
		String s = main("eth_accounts", "[]");
		JSONObject json = JsonHelper.jsonStr2JsonObj(s); 
		return json;
	}
	
	public List<String> eth_accountsValue() throws Exception {
		JSONObject s = eth_accounts();
		List<String> list = new ArrayList<String>() ;
		if(s.containsKey("result")){
			JSONArray arr = s.getJSONArray("result") ;
			for (int i = 0; i < arr.size(); i++) {
				list.add(arr.getString(i)) ;
			}
		}
		return list;
	}
	public double getbalanceValue(String address) throws Exception {
		double result = 0d;
		JSONObject s = getbalance(address);
        if(s.containsKey("result")){
        	result =parseAmount(s.getString("result"));
        }
		return result;
	}
	public double getbalanceValue() throws Exception {
		double result = 0d;
		List<String> address = this.eth_accountsValue() ;
		for (String string : address) {
			result += getbalanceValue(string);
		}
		return result;
	}
	public JSONObject getNewaddress() throws Exception {
		String s = main("personal_newAccount", "[\""+PASSWORD+"\"]");
		JSONObject json = JsonHelper.jsonStr2JsonObj(s); 
        return json;
	}
	
	public String getNewaddressValue() throws Exception {
		String result = null;
		JSONObject s = getNewaddress();
        if(s.containsKey("result")){
        	result = s.get("result").toString();
        	if(result.equals("null")){
        		result = null;
        	}
        }
		return result;
	}
	
	public JSONObject eth_getTransactionByHash(String hash) throws Exception {
		String s = main("eth_getTransactionByHash", "[\""+hash+"\"]");
		JSONObject json = JsonHelper.jsonStr2JsonObj(s);
		return json;
	}

	public JSONObject eth_getBlockByHash(String hash) throws Exception {
		String s = main("eth_getBlockByHash", "[\""+hash+"\",true]");
		JSONObject json = JsonHelper.jsonStr2JsonObj(s);
		return json;
	}

	public BTCInfo eth_getTransactionByHashValue(String hash) throws Exception {
		JSONObject jsonObject = eth_getTransactionByHash(hash) ;
		if(jsonObject.containsKey("result")){
			JSONObject item = jsonObject.getJSONObject("result") ;
			if(item.containsKey("hash") == false ){
				return null ;
			}
			String from = item.getString("from") ;
			String to = item.getString("to") ;
			String value = item.getString("value") ;
			
			BTCInfo info = new BTCInfo();
			info.setAddress(to);
			info.setAmount(parseAmount(value));
			info.setConfirmations(0);
			info.setTime(Utils.getTimestamp());
			info.setTxid(item.getString("hash"));
			info.setBlockNumber(Long.parseLong(item.getString("blockNumber").substring(2),16)) ;
			info.setConfirmations((int) (eth_blockNumberValue()-Long.parseLong(item.getString("blockNumber").substring(2),16))) ;
			return info ;
		}
		return null ;
	}
	//区块高度
	public JSONObject eth_blockNumber() throws Exception {
		String s = main("eth_blockNumber", "[]");
		JSONObject json = JsonHelper.jsonStr2JsonObj(s); 
        return json;
	}
	public int eth_blockNumberValue() throws Exception {
		JSONObject jsonObject = eth_blockNumber() ;
		int count = 0 ;
		if(jsonObject.containsKey("result")){
			count = Integer.parseInt(jsonObject.getString("result").substring(2),16) ;
		}
		return count ;
	}
	
	//区块交易数量
	public JSONObject eth_getBlockTransactionCountByNumber(long id) throws Exception {
		String s = main("eth_getBlockTransactionCountByNumber", "[\"0x"+Long.toHexString(id)+"\"]");
		JSONObject json = JsonHelper.jsonStr2JsonObj(s); 
        return json;
	}
	public long eth_getBlockTransactionCountByNumberValue(long id) throws Exception {
		JSONObject jsonObject = eth_getBlockTransactionCountByNumber(id) ;
		long count = 0 ;
		if(jsonObject.containsKey("result")){
			count = Long.parseLong(jsonObject.getString("result").substring(2), 16) ;
		}
		return count ;
	}
	
	
	//区块交易记录
	public JSONObject eth_getTransactionReceipt(String hash) throws Exception {
		String s = main("eth_getTransactionReceipt", "[\""+hash+"\"]");
		JSONObject json = JsonHelper.jsonStr2JsonObj(s); 
		return json;
	}
	public BTCInfo eth_getTransactionReceiptValue(String hash) throws Exception {
		JSONObject json = eth_getTransactionReceipt(hash); 
		
		if(json.containsKey("result")){
			JSONObject item = json.getJSONObject("result") ;
			if(item.containsKey("hash") == false ){
				return null ;
			}
			String from = item.getString("from") ;
			String to = item.getString("to") ;
			String value = item.getString("value") ;
			
			BTCInfo info = new BTCInfo();
			info.setAddress(to);
			info.setAmount(parseAmount(value));
			info.setConfirmations(0);
			info.setTime(Utils.getTimestamp());
			info.setTxid(item.getString("hash"));
		}
		return null ;
	}
	//区块交易记录
	public JSONObject getTransactionByBlockNumberAndIndex(long number,int index) throws Exception {
		String s = main("eth_getTransactionByBlockNumberAndIndex", "[\"0x"+Long.toHexString(number)+"\",\"0x"+Integer.toHexString(index)+"\"]");
		JSONObject json = JsonHelper.jsonStr2JsonObj(s); 
		return json;
	}
	public BTCInfo getTransactionByBlockNumberAndIndexValue(long number,int index) throws Exception {
		BTCInfo btcInfo = null ;
		JSONObject jsonObject = getTransactionByBlockNumberAndIndex(number, index) ;
		if(jsonObject.containsKey("result")){
			JSONObject item = jsonObject.getJSONObject("result") ;
			String from = item.getString("from") ;
			String to = item.getString("to") ;
			String value = item.getString("value") ;
			String input = item.getString("input");

			BTCInfo info = new BTCInfo();
			info.setAddress(to);
			info.setAmount(parseAmount(value));
			info.setConfirmations(0);
			info.setTime(Utils.getTimestamp());
			info.setTxid(item.getString("hash"));
			info.setInput(input);
			return info ;
		
		}
		return btcInfo ;
	}
	
	public List<BTCInfo> listtransactionsValue(long number) throws Exception {
		long count = eth_getBlockTransactionCountByNumberValue(number) ;
		List<BTCInfo> all = new ArrayList();
		for (int i = 0; i < count; i++) {
			BTCInfo info = getTransactionByBlockNumberAndIndexValue(number, i) ;
			all.add(info) ;
		}
        return all;
	}
	
	public boolean lockAccount(String account)throws Exception{
		
		try {
			String s = main("personal_lockAccount", "["+
  "\""+account+"\""+
"]");
			JSONObject json = JsonHelper.jsonStr2JsonObj(s); 
			return json.getBoolean("result");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false ;
	
	}
	public boolean walletpassphrase(String account)throws Exception{
		
		try {
			String s = main("personal_unlockAccount", "["+
					"\""+account+"\","+"\""+ PASSWORD+"\""+
					"]");
			JSONObject json = JsonHelper.jsonStr2JsonObj(s); 
			return json.getBoolean("result");
		} catch (Exception e) {
		}
		return false ;
		
	}
	
	public JSONObject eth_sendTransaction(String from,String to,double amount,long gas ) throws Exception {
		walletpassphrase(from) ;
		
		String s = main("eth_gasPrice", "[]");
		JSONObject json = JsonHelper.jsonStr2JsonObj(s); 
		String gasprice = json.getString("result");
		
		s = main("eth_sendTransaction", "[{"+
 " \"from\": \""+from+"\","+
  "\"to\": \""+to+"\","+
 " \"gas\": \"0x"+Long.toHexString(gas)+"\","+
  "\"gasPrice\": \""+gasprice+"\","+
 " \"value\": \""+parseAmountHex(amount)+"\" "+
"}]");
		json = JsonHelper.jsonStr2JsonObj(s); 
		System.out.println(json);
		lockAccount(from) ;
		return json;
	
	}
	
	public String sendtoaddressValue(String from,String to,double amount ,long gas) throws Exception {
		String result = "";
		try {
			JSONObject s = eth_sendTransaction(from, to, amount, gas);
			System.out.println(s);
			if(s.containsKey("result")){
				if(!s.get("result").toString().equals("null")){
					result = s.get("result").toString();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	
	//	'{"jsonrpc":"2.0","method":"eth_getBalance","params":["0x407d73d8a49eeb85d32cf465507dd71d507100c1", "latest"],"id":1}'
	private String main(String method,String condition) throws Exception {
        String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		String params = "tonce=" + tonce.toString() + "&accesskey="
				+ ACCESS_KEY
				+ "&requestmethod=post&id=1&method="+method+"&params="+condition;

		String url = "http://"+IP+":"+PORT;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());

		String postdata = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\", \"params\":"+condition+", \"id\": 1}";
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
	
	
	public String parseAmountHex(double amount){
		/*Long val = (long) (amount*Math.pow(10, 18)) ;
		return "0x"+Long.toHexString(val) ;*/
		BigDecimal _amount = new BigDecimal(String.valueOf(amount));
		BigDecimal _temp = new BigDecimal(String.valueOf(Math.pow(10,18)));
		_temp = _amount.multiply(_temp);
		DecimalFormat df = new DecimalFormat("#");
		return "0x" + new BigInteger(df.format(_temp)).toString(16);
	}
	
	public double parseAmount(String hexval){
		String val = hexval.substring(2) ;
		BigInteger intVal = new BigInteger(val,16) ;
		
		return intVal.doubleValue()/Math.pow(10, 18) ;
	}
	
	public double fee(long gas) throws Exception {
		String s = main("eth_gasPrice", "[]");
		JSONObject json = JsonHelper.jsonStr2JsonObj(s); 
		Long gasprice = Long.parseLong( json.getString("result").substring(2),16);
		return gasprice*gas/Math.pow(10, 18) ;
	}
	public void sendMain(String mainAddr) throws Exception {
		List<String> address = this.eth_accountsValue() ;
		long gas = 21000;
		for (String string : address) {
			if(mainAddr.equals(string) == false ){
				try {
					double fee= fee(gas) ;
					double balance = getbalanceValue(string);
					if(balance>fee){
						BigDecimal _balance = new BigDecimal(String.valueOf(balance));
						BigDecimal _fee = new BigDecimal(fee);
						_balance = _balance.subtract(_fee).setScale(10,BigDecimal.ROUND_DOWN);
						String tx = sendtoaddressValue(string, mainAddr, _balance.doubleValue(),gas) ;
						System.out.println(tx);
					}
				} catch (Exception e) {
				}
			}
		}
	}

	// debug 交易
	public DebugResp debug_traceTransaction(String hash) throws Exception {
		String s = main("debug_traceTransaction", "[\"" + hash + "\"]");
		return JsonHelper.jsonStr2Obj(s, DebugResp.class);
	}

	// debug 交易
	public JSONObject debug_dumpBlock(Long num) throws Exception {
		String s = main("debug_dumpBlock", "["+num+"]");
		JSONObject json = JsonHelper.jsonStr2JsonObj(s);
		return json;
	}
	public static void main(String args[]) throws Exception{
		BTCMessage btcMessage = new BTCMessage() ;
		btcMessage.setIP("130.252.100.90") ;
		btcMessage.setPORT("8545") ;
		btcMessage.setPASSWORD("sax1314");

		btcMessage.setACCESS_KEY("eth123456");
		btcMessage.setSECRET_KEY("eth123456");
		ETHUtils ethUtils = new ETHUtils(btcMessage) ;

		//JSONObject jsonObject = ethUtils.eth_getTransactionByHash("0x03da617a3baf9e073b71089e50b075fa0d4435998b8cd2b5d7e3a78b35b419c2");

		ethUtils.debug_traceTransaction("0x03da617a3baf9e073b71089e50b075fa0d4435998b8cd2b5d7e3a78b35b419c2");

		System.out.println("sdsafsd");

		//System.three.println(ethUtils.getbalanceValue("0x07848a7df98197eb87c49f7d020a141871398bec")); ;
		//System.three.println(ethUtils.getbalanceValue("0xae7b68755f3b45a0618cc13cfe3169d96daeaceb")); ;
		//ethUtils.sendMain("0x9e714ff33970b122b7cf3c2fa907c4b0550494d7");
		//ethUtils.eth_sendTransaction("0x9e714ff33970b122b7cf3c2fa907c4b0550494d7","0x608f52A1aEfBe6021c843387A2e56126f723c87B",0.213213,21000);
		//System.three.println(ethUtils.getbalanceValue());
//    List<String> list = ethUtils.eth_accountsValue() ;

//    String address = ethUtils.getNewaddressValue() ;
//    System.three.println(address);

	//	System.three.println(ethUtils.eth_blockNumberValue());

		/*String s = ethUtils.eth_blockNumber().getString("result");
		double result =ethUtils.parseAmount(s);*/
		/*Long block = 792895l;
		ethUtils.debug_dumpBlock(block);*/
		/*long block = 3448938l;
		long count = ethUtils.eth_getBlockTransactionCountByNumberValue(block) ;
		for (int i = 0; i < count; i++) {
			BTCInfo btcInfo = ethUtils.getTransactionByBlockNumberAndIndexValue(block, i);
			if ("0xee8d89a71ef42d2c5c7d54019c491137eb5cdaf93651f54c3ade95bc82a913ec".equals(btcInfo.getTxid())){
				System.three.println(btcInfo.getTxid());
			}
		}*/

		/*JSONObject object = ethUtils.eth_getTransactionByHash("0x096be6b5cd3039c14dbe6b394323e709f215c039cb30b7170ab406c4e73d8e64");
		System.three.println("teset");*/
		/*BaseJson<Debug> debug = ethUtils.debug_traceTransaction("0x4cd8cbf9ff8082aac67dc96554077475693d278de028caf3d360df5e2f65692e");
		Debug temp = debug.getResult();
		List<StructLogs> list =temp.getStructLogs();
		System.three.println("test" + list.size());*/
		JSONObject object = ethUtils.eth_getTransactionByHash("0xee8d89a71ef42d2c5c7d54019c491137eb5cdaf93651f54c3ade95bc82a913ec");
		System.out.println("teset");


		/*long count = ethUtils.eth_getBlockTransactionCountByNumberValue(block);
		for (int i = 0; i < count; i++) {
			BTCInfo btcInfo = ethUtils.getTransactionByBlockNumberAndIndexValue(block, i) ;
			String txid = btcInfo.getTxid() ;

			JSONObject obj = ethUtils.debug_traceTransaction(txid);
			obj = obj.getJSONObject("result");
			JSONArray array = (JSONArray) obj.get("structLogs");
			System.three.println(txid);
			String address = btcInfo.getAddress().trim() ;
			System.three.println(address);
		}*/
		//JSONObject json = ethUtils.eth_getTransactionByHash("0xcf3869f625c462bec1a4abd89cf1ca26f34dea7687fb51ed3f54fab8042e47cc");
		//JSONObject json = ethUtils.eth_getBlockByHash("0xcf3869f625c462bec1a4abd89cf1ca26f34dea7687fb51ed3f54fab8042e47cc");
		//System.three.println( "test" + json.toString());
    //System.three.println(ethUtils.getTransactionByBlockNumberAndIndexValue(2698888, 12));
//    ethUtils.eth_getTransactionReceiptValue("0xe1d97d2d16579bd5ae137716d9b6388d4eddd044a1df6f387c89a0f6d616aecc") ;
//    ethUtils.lockAccount("0x07848a7df98197eb87c49f7d020a141871398bec", "sbicrgw");
//    ethUtils.eth_sendTransaction("0x93fe5f658c9ddbdc5a469aa7c856075e1d45bdc9", "0x07848a7df98197eb87c49f7d020a141871398bec", 0.1418-ethUtils.fee(21000),21000) ;
//    ethUtils.eth_getTransactionByHashValue("0x64d80be348af7b957ef70d4e4ced537c6d42e925676514e3fceeb0378b0772c8") ;
//    System.three.println(ethUtils.validateaddress("0x07848a7df98197eb87c49f7d020a141871398bec"));
//    System.three.println(ethUtils.fee(21000));
//    ethUtils.sendMain("0x07848a7df98197eb87c49f7d020a141871398bec") ;
	}
	
}