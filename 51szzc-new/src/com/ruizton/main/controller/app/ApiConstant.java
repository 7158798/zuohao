package com.ruizton.main.controller.app;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;

public class ApiConstant {
	public static HashMap<String, Integer> actionMap = new HashMap<String, Integer>();
	static {
		int i = 0;
		actionMap.put("Invalidate", i++);// 无效

		actionMap.put("UserLogin", i++);// 登录
		actionMap.put("UserRegister", i++); // 注册
		actionMap.put("SendMessageCode", i++);
		actionMap.put("SendMailCode", i++);
		actionMap.put("FindLoginPassword", i++);

		actionMap.put("GetCoinListInfo", i++);// 每个币种的基础信息
		actionMap.put("GetVersion", i++);// 获取服务端最小版本号
		actionMap.put("GetMarketData", i++);// 行情
		actionMap.put("GetNews", i++);// 新闻
		actionMap.put("GetMyCenter", i++);// 个人中心
		actionMap.put("GetAbout", i++);// 个人中心
		actionMap.put("GetMarketDepth", i++);// 市场深度
		actionMap.put("GetMarketDepth2", i++);// 市场深度
		actionMap.put("RechargeBanks", i++);
		actionMap.put("queryHome", i++);  //app端首页，获取banner横幅广告、最新价格
		actionMap.put("realCncyPrice", i++);  //首页，虚拟币最新价格、涨跌幅信息
		actionMap.put("queryVideo", i++);  //查询视频课程
		actionMap.put("articleDetail", i++);  //资讯详情
		actionMap.put("articleTypeList", i++);  //获取资讯类型;
		actionMap.put("queryArticle", i++);  //行业资讯
		actionMap.put("tradingCenterIndex", i++);  //交易中心首页
		actionMap.put("appperiod", i++);  //行情大k线图
		actionMap.put("appperiod2", i++);  //行情大k线图
		actionMap.put("quotesCenter", i++);  //行情中心数据
		actionMap.put("getCoinList", i++);  //获取币种

		// 需要token
		i = 201;
		actionMap.put("GetAccountInfo", i++); // 账号信息，用户信息+交易账号+放款账号信息
		actionMap.put("GetBtcRechargeListRecord", i++);// 虚拟币充值，返回充值地址和记录
		actionMap.put("GetEntrustInfo", i++);// 委托订单
		actionMap.put("CancelEntrust", i++);// 取消订单
		actionMap.put("BtcTradeSubmit", i++);// 下单
		actionMap.put("TradePassword", i++);// 交易密码
		actionMap.put("GetIntrolinfo", i++);// 提成明细
		actionMap.put("GetIntrolDetail", i++);

		actionMap.put("ViewValidateIdentity", i++);// 查看认证信息

		actionMap.put("ValidateIdentity", i++);  //实名认证

		actionMap.put("RechargeCny", i++);  //人民币充值

		actionMap.put("bindPhone", i++);
		actionMap.put("UnbindPhone", i++);
		actionMap.put("ChangebindPhone", i++);

		actionMap.put("GetWithdrawBankList", i++); //提现银行类别列表
		actionMap.put("SetWithdrawCnyBankInfo", i++);  //设置提现银行卡信息
		actionMap.put("WithDrawCny", i++);  //人民币提现
		actionMap.put("GetWithdrawBtcAddress", i++); //获取虚拟币提现地址
		actionMap.put("SetWithdrawBtcAddr", i++);  //设置虚拟币提现地址
		actionMap.put("WithdrawBtcSubmit", i++); //虚拟币提现
		actionMap.put("changePassword", i++);
		actionMap.put("GetAllRecords", i++); //充值提现记录,type:1人民币充值，2人民币提现，3虚拟币充值，4虚拟币提现


	};

	public synchronized static Integer getInteger(String action) {

		try {
			Integer actionInteger = actionMap.get(action);

			if (actionInteger == null) {
				actionInteger = 0;
			}

			return actionInteger;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int getInt(String param) {
		int ret = 0;
		try {
			ret = Integer.parseInt(param.trim());
		} catch (Exception e) {
		}
		return ret;
	}

	public static double getDouble(String param) {
		double ret = 0;
		try {
			ret = Double.parseDouble(param.trim());
		} catch (Exception e) {
		}
		return ret;
	}

	public static String getUnknownError(Exception e) {
		if (e != null) {
			e.printStackTrace();
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate(APP_API_Controller.Result, true);
		jsonObject.accumulate(APP_API_Controller.ErrorCode, -10003);
		jsonObject.accumulate(APP_API_Controller.Value, "网络错误，请稍后重试");

		return jsonObject.toString();
	}


	public static String getRequestError(String msg) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate(APP_API_Controller.Result, true);
		jsonObject.accumulate(APP_API_Controller.ErrorCode, -1);
		jsonObject.accumulate(APP_API_Controller.Value, msg);

		return jsonObject.toString();
	}

	public static String getRequestError() {

		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate(APP_API_Controller.Result, true);
		jsonObject.accumulate(APP_API_Controller.ErrorCode, -1);
		jsonObject.accumulate(APP_API_Controller.Value, "操作失败");

		return jsonObject.toString();
	}


	public static String getNoLoginError(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate(APP_API_Controller.Result, true);
		jsonObject.accumulate(APP_API_Controller.ErrorCode, -10001);
		jsonObject.accumulate(APP_API_Controller.Value, "用户未登录");

		return jsonObject.toString();
	}

	public static String getSuccessResp(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate(APP_API_Controller.Result, true);
		jsonObject.accumulate(APP_API_Controller.ErrorCode, 0);
		jsonObject.accumulate(APP_API_Controller.Value, "操作成功");

		return jsonObject.toString();
	}

	public static String getSuccessResp(JSONObject jsonObject){
		jsonObject.accumulate(APP_API_Controller.Result, true);
		jsonObject.accumulate(APP_API_Controller.ErrorCode, 0);
		jsonObject.accumulate(APP_API_Controller.Value, "操作成功");

		return jsonObject.toString();

	}

	public static String getKYC1ValidateError(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate(APP_API_Controller.Result, true);
		jsonObject.accumulate(APP_API_Controller.ErrorCode, -100);
		jsonObject.accumulate(APP_API_Controller.Value, "用户未通过KYC1认证");

		return jsonObject.toString();
	}

	public static String getKYC2ValidateError(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate(APP_API_Controller.Result, true);
		jsonObject.accumulate(APP_API_Controller.ErrorCode, -101);
		jsonObject.accumulate(APP_API_Controller.Value, "用户未通过KYC2认证");

		return jsonObject.toString();
	}


}