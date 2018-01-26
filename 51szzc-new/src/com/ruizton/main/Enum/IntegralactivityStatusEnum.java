package com.ruizton.main.Enum;

/**
 * 积分获取途径 状态
 */
public enum  IntegralactivityStatusEnum {

	YSC("0","已删除"),
	DQD("1","待启动"),
	YJX("2","已进行"),
	YZT("3", "已暂停"),
	YJS("4", "已结束");

	private String value;
	private String name;

	private IntegralactivityStatusEnum(String value, String name){
		this.value = value;
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static IntegralactivityStatusEnum getValueByName(String name){
		IntegralactivityStatusEnum[] list = IntegralactivityStatusEnum.values();
		for(IntegralactivityStatusEnum i : list){
			if(i.getName().equals(name)){
				return i;
			}
		}
		return null;
	}

	public static IntegralactivityStatusEnum getNameByValue(String value){
		IntegralactivityStatusEnum[] list = IntegralactivityStatusEnum.values();
		for(IntegralactivityStatusEnum i : list){
			if(i.getValue().equals(value)){
				return i;
			}
		}
		return null;
	}

}
