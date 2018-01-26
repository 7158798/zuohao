package com.ruizton.util;

import net.sf.json.JSONObject;

public class BankUtil {

	public static final String BANK_VALIDATE_JH_URL = "http://v.juhe.cn/verifybankcard4/query.php";

	public static final String BANK_VALIDATE_JH_KEY = "ff229d419f62b467a3c0d3d9304d958c";

	public static String validate(String num) throws Exception {
		String ret = Utils.wget("http://bankcardsilk.api.juhe.cn/bankcardsilk/query.php?key="+Constant.BANKKEY+"&num="+num) ;
		System.out.println(ret);
		return ret ;
	}

	public static JSONObject validate(String realname,String idcard,String bankcard,String mobile){
		JSONObject jsonResult = new JSONObject();
		if (Constant.OPEN_REAL_ID) {
			String url = BANK_VALIDATE_JH_URL + "?key=" + BANK_VALIDATE_JH_KEY + "&bankcard=" + bankcard + "&realname=" + realname + "&idcard=" + idcard + "&mobile=" + mobile;
			try {
				String json = HttpClientHelper.sendGetRequest(url, false);
				JSONObject retj = JSONObject.fromObject(json);
				if (retj.getInt("error_code") != 0) {
					jsonResult.accumulate("result", -1);
					jsonResult.accumulate("msg", "没有查询到相关信息");
				} else if (retj.getJSONObject("result").getInt("res") != 1) {
					jsonResult.accumulate("result", -2);
					jsonResult.accumulate("msg", "银行卡信息不匹配");
				} else {
					jsonResult.accumulate("result", 0);
					jsonResult.accumulate("msg", "成功");
				}
			} catch (Exception e) {
				jsonResult.accumulate("result", -1);
				jsonResult.accumulate("msg", "没有查询到相关信息");
			}
		} else {
			jsonResult.accumulate("result", 0);
			jsonResult.accumulate("msg", "成功");
		}

		return jsonResult;

	}
	
	public static void main(String args[]) throws Exception {
		JSONObject jsonObject= validate("","610431199402281927","6217710202870689","18292827259");
        System.out.print(jsonObject);
	}
}
