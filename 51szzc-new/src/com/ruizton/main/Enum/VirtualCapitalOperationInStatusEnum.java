package com.ruizton.main.Enum;

public class VirtualCapitalOperationInStatusEnum {
	public static final int WAIT_0 = 0 ;
	public static final int WAIT_1 = 1 ;
	public static final int WAIT_2 = 2 ;
	public static final int SUCCESS = 3 ;
	public static final int OneTimeAudit = 10;
	public static final int TwoTimeAudit = 11;
	public static final int ThreeTimeAudit = 12;
	
	public static String getEnumString(int value) {
		String name = "";
		if(value == WAIT_0){
			name = "0确认";
		}else if(value == WAIT_1){
			name = "1确认";
		}else if(value == WAIT_2){
			name = "待确认";
		}else if(value == SUCCESS){
			name = "充值成功";
		}else if(value == OneTimeAudit){
			name = "二级审核中" ;
		}else if(value == TwoTimeAudit){
			name = "三级审核中";
		}else if(value == ThreeTimeAudit){
			name = "审核完成";
		}
		return name;
	}


	public static int getFrontStatus(int value){
		int status = 0;  //2  处理中  3 已完成  4 已失效
		switch (value) {
			case SUCCESS:
				status = 3;
				break;
			default:
				status = 2;
		}
		return status;
	}

	public static String getFrontString(int value) {
		String name = "";
		if(value == WAIT_0){
			name = "0确认";
		}else if(value == WAIT_1){
			name = "1确认";
		}else if(value == WAIT_2){
			name = "待确认";
		}else if(value == SUCCESS){
			name = "充值成功";
		}else if(value == OneTimeAudit){
			name = "处理中" ;
		}else if(value == TwoTimeAudit){
			name = "处理中";
		}else if(value == ThreeTimeAudit){
			name = "处理中";
		}
		return name;
	}
}
