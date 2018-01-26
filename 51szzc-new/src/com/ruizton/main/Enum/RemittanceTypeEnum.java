package com.ruizton.main.Enum;

import org.apache.commons.lang.StringUtils;

public class RemittanceTypeEnum {//汇款方式：柜台、网银、atm
    public static final int Type1 = 0;//柜台 转款
    public static final int Type2 = 2;//ATM机
    public static final int Type3 = 3;//网上银行
    public static final int Type4 = 4;//网上银行
   
    public static String getEnumString(int value) {
		String name = "";
		if(value == Type1){
			name = "银行卡转账";
		}else if(value == Type2){
			name = "网银直充";
		}else if(value == Type3){
			name = "支付宝转账";
		}else if(value == Type4){
			name = "微信转账";
		}
		return name;
	}

	//判断是否为支付宝充值
	public static int getIsAliypay(String name){
		if(StringUtils.isEmpty(name)){
			return  -1;
		}
		if(name.contains("支付宝")){
			return 1;
		}else{
			return 0;
		}
	}
}
