package com.ruizton.main.Enum;

/**
 * 积分获取途径 状态
 */
public enum KYCAuditingStatusEnum {

	INIT(0,"初始"),
	AUDITING(1,"待审核"),
	PASS(2,"审核通过"),
	NO_PASS(3, "已拒绝");

	private int value;
	private String name;

	private KYCAuditingStatusEnum(int value, String name){
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static KYCAuditingStatusEnum getValueByName(String name){
		KYCAuditingStatusEnum[] list = KYCAuditingStatusEnum.values();
		for(KYCAuditingStatusEnum i : list){
			if(i.getName().equals(name)){
				return i;
			}
		}
		return null;
	}

	public static String getNameByValue(int value){
		KYCAuditingStatusEnum[] list = KYCAuditingStatusEnum.values();
		for(KYCAuditingStatusEnum i : list){
			if(i.getValue() == value){
				return i.getName();
			}
		}
		return "";
	}

}
