package com.szzc.facade.ftrademapping.enums;

public class CoinTypeEnum {
    public static final int FB_CNY_VALUE = 0;//法币-人民币
    public static final int FB_COIN_VALUE = 1;//法币-虚拟币
    public static final int COIN_VALUE = 2;//普通虚拟币
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == FB_CNY_VALUE){
			name = "法币-人民币";
		}else if(value == FB_COIN_VALUE){
			name = "法币-虚拟币";
		}else if(value == COIN_VALUE){
			name = "普通虚拟币";
		}
		return name;
	}
    
}
