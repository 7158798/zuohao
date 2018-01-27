package com.szzc.facade.fentrustLog.enums;

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
    
}
