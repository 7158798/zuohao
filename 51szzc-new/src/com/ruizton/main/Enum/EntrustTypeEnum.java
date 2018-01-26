package com.ruizton.main.Enum;

import com.ruizton.util.zuohao.LanguageUtil;

public class EntrustTypeEnum {
    public static final int BUY = 0;//买
    public static final int SELL = 1 ;//卖
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == BUY){
			name = "买入";
		}else if(value == SELL){
			name = "卖出";
		}

		return name;
	}

	public static String getEnumString_Eng(int value){
		String name = "";
		if(value == BUY){
			name = "buy";
		}else if(value == SELL){
			name = "sell";
		}

		return name;
	}

	/**
	 * 国际化处理
	 * @param value
	 * @return
	 */
	public static String getEnumString_i18n(int value){
		String name = "";
		if(value == BUY){
//			name = "买入";
			name = LanguageUtil.i18nMsg("trade_buy");
		}else if(value == SELL){
//			name = "卖出";
			name = LanguageUtil.i18nMsg("trade_sell");
		}

		return name;
	}
    
}
