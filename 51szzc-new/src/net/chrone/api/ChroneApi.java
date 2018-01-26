package net.chrone.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import net.chrone.model.Merchant;
import net.chrone.model.Order;
import net.chrone.util.HttpClientHelper;
import net.chrone.util.HttpResponse;
import net.chrone.util.MyRSAUtils;
import net.chrone.util.SdkUtil;
import net.chrone.util.SignatureUtil;


/**
 * 乾恩扫码收款API
 * @author Jerry
 *
 */
public class ChroneApi {
	
	private final static Logger logger = Logger.getLogger(ChroneApi.class);
	
	public static final String SUCCESS_CODE = "200";//成功

	public static final Map<String, String> quick_pay_status = new HashMap<String, String>() {
		{
			put("1", "待支付");
			put("2", "支付完成");
			put("3", "支付失败");
			put("4", "已关闭");
		}
	};

	/**
	 * 商户注册
	 * @param merchant
	 */
	public static boolean regist(Merchant merchant){
		try{
			Map<String,String> params = new HashMap<String,String>();
			params.put("cardType", merchant.getCardType());
			params.put("pmsBankNo", merchant.getPmsBankNo());
			params.put("certNo", merchant.getCertNo());
			params.put("mobile", merchant.getMobile());
			params.put("password", merchant.getPassword());
			params.put("cardNo", merchant.getCardNo());
			params.put("orgId", SdkUtil.getStringValue("chroneOrgId"));
			params.put("realName", merchant.getRealName());
			params.put("certType", merchant.getCertType());
			params.put("account", merchant.getAccount());
			params.put("mchntName", merchant.getMchntName());
			String bigStr = SignatureUtil.hex(params);
			params.put("signature", MyRSAUtils.sign(SdkUtil.getStringValue("chronePrivateKey"), bigStr, MyRSAUtils.MD5_SIGN_ALGORITHM));
			String postData = JSON.toJSONString(params);
			System.out.println(bigStr);
			System.out.println(postData);
//			List<String[]> headers = new ArrayList<>();
//			headers.add(new String[]{"Content-Type","application/json"});
//			HttpResponse response = HttpClientHelper.doHttp(SdkUtil.getStringValue("chroneRegistUrl"), HttpClientHelper.POST, headers, "utf-8", postData, "60000");
//			if(StringUtils.isNotEmpty(response.getRspStr())){
//				logger.debug("chrone regist result:"+response.getRspStr());
//				Map<String,String> retMap = JSON.parseObject(response.getRspStr(), new TypeReference<Map<String,String>>(){});
//				if(SUCCESS_CODE.equals(retMap.get("respCode"))){
//					return true;
//				}
//			}
		}catch (Exception e) {
			logger.error("商户注册请求失败");
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 扫码支付
	 * @param order
	 */
	public static String qrpay(Order order){
		try{
			Map<String,String> params = new HashMap<String,String>();
			params.put("orgId", SdkUtil.getStringValue("chroneOrgId"));
			params.put("source", order.getSource()+"");
			params.put("settleAmt", order.getSettleAmt()+"");
			params.put("account", order.getAccount());
			params.put("amount", order.getAmount()+"");
			params.put("notifyUrl", SdkUtil.getStringValue("chroneNotifyurl"));
			params.put("tranTp", order.getTranTp()+"");
			params.put("orgOrderNo", order.getOrderNo());
			String bigStr = SignatureUtil.hex(params);
			params.put("signature", MyRSAUtils.sign(SdkUtil.getStringValue("chronePrivateKey"), bigStr, MyRSAUtils.MD5_SIGN_ALGORITHM));
			String postData = JSON.toJSONString(params);
			logger.info("请求报文:" + postData);
			List<String[]> headers = new ArrayList<String[]>();
			headers.add(new String[]{"Content-Type","application/json"});
			HttpResponse response = HttpClientHelper.doHttp(SdkUtil.getStringValue("chroneQrpayUrl"), HttpClientHelper.POST, headers, "utf-8", postData, "60000");
			if(StringUtils.isNotEmpty(response.getRspStr())){
				logger.info("三方响应报文:"+response.getRspStr());
				Map<String,String> retMap = JSON.parseObject(response.getRspStr(), new TypeReference<Map<String,String>>(){});
				if(SUCCESS_CODE.equals(retMap.get("respCode"))){
					return retMap.get("qrcode");
				}
			}
		}catch (Exception e) {
			logger.error("获取二维码失败");
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 机构订单号
	 * @param orgOrderNo
	 * @return
	 */
	public static Map<String, String> qrpay_status_query(String orgOrderNo){
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("orgId", SdkUtil.getStringValue("chroneOrgId"));
		requestParams.put("orgOrderNo", orgOrderNo);  //机构订单号
		String bigStr = SignatureUtil.hex(requestParams);
		String signStr = MyRSAUtils.sign(SdkUtil.getStringValue("chronePrivateKey"), bigStr, MyRSAUtils.MD5_SIGN_ALGORITHM);
		requestParams.put("signature", signStr);
		String postData = JSON.toJSONString(requestParams);
		List<String[]> headers = new ArrayList<String[]>();
		headers.add(new String[]{"Content-Type","application/json"});
		HttpResponse response = HttpClientHelper.doHttp(SdkUtil.getStringValue("chroneOrderQueryUrl"), HttpClientHelper.POST, headers, "UTF-8", postData, "60000");
		if (StringUtils.isNotEmpty(response.getRspStr())) {
			logger.info("chrone 收款订单查询 result:" + response.getRspStr());
			Map<String, String> retMap = JSON.parseObject(response.getRspStr(), new TypeReference<Map<String, String>>() {
			});
			// map里面含平台订单号，金额等信息
			if (SUCCESS_CODE.equals(retMap.get("respCode"))) {
				retMap.put("payStName", quick_pay_status.get(retMap.get("paySt")));
				return retMap;
			}else{
				return null;
			}
		}

		return null;
	}


	/**
	 * 扫码支付
	 * @param order
	 */
	public static Map<String,String> fqrpay(Order order){
		Map<String,String> params = new HashMap<String,String>();
		params.put("orgId", SdkUtil.getStringValue("chroneOrgId"));
		params.put("source", order.getSource()+"");
		params.put("settleAmt", order.getSettleAmt()+"");
		params.put("account", order.getAccount());
		params.put("amount", order.getAmount()+"");
		params.put("notifyUrl", SdkUtil.getStringValue("chroneNotifyurl"));
		params.put("callbackUrl", SdkUtil.getStringValue("chroneCallbackUrl"));
		params.put("tranTp", order.getTranTp()+"");
		params.put("orgOrderNo", order.getOrderNo());
		String bigStr = SignatureUtil.hex(params);
		params.put("signature", MyRSAUtils.sign(SdkUtil.getStringValue("chronePrivateKey"), bigStr, MyRSAUtils.MD5_SIGN_ALGORITHM));
		return params;
	}
	
	/**
	 * 付款结果查询
	 * @param orderNo:源收款訂單號
	 * @return
	 */
	public static Map<String,String> payforQuery(String orderNo){
		try{
			Map<String,String> params = new HashMap<String,String>();
			params.put("orgId", SdkUtil.getStringValue("chroneOrgId"));
			params.put("orderNo", orderNo);
			String bigStr = SignatureUtil.hex(params);
			params.put("signature", MyRSAUtils.sign(SdkUtil.getStringValue("chronePrivateKey"), bigStr, MyRSAUtils.MD5_SIGN_ALGORITHM));
			String postData = JSON.toJSONString(params);
			List<String[]> headers = new ArrayList<String[]>();
			headers.add(new String[]{"Content-Type","application/json"});
			HttpResponse response = HttpClientHelper.doHttp(SdkUtil.getStringValue("chronePayforQueryUrl"), HttpClientHelper.POST, headers, "utf-8", postData, "60000");
			if(StringUtils.isNotEmpty(response.getRspStr())){
				logger.debug("chrone regist result:"+response.getRspStr());
				Map<String,String> retMap = JSON.parseObject(response.getRspStr(), new TypeReference<Map<String,String>>(){});
				return retMap;
			}
		}catch (Exception e) {
			logger.error("付款查询接口失败");
			e.printStackTrace();
		}
		return null;
	}
}
