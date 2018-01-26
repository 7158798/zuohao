package com.ruizton.main.Enum;

public class CapitalOperationOutStatus {
	public static final int WaitForOperation = 1 ;//等待提现
	public static final int OperationLock = 2 ;//锁定，等待处理
	public static final int OperationSuccess = 3 ;//提现成功
	public static final int Cancel = 4 ;//用户取消
	public static final int OneTimeAudit = 10;
	public static final int TwoTimeAudit = 11;
	public static final int ThreeTimeAudit = 12;
	
	public static String getEnumString(int value) {
		String name = "";
		if(value == WaitForOperation){
			name = "等待提现";
		}else if(value == OperationLock){
			name = "正在处理";
		}else if(value == OperationSuccess){
			name = "提现成功";
		}else if(value == Cancel){
			name = "用户撤销" ;
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
			case OperationSuccess:
				status = 3;
				break;
			case Cancel:
				status = 4;
				break;
			default:
				status = 2;
		}
		return status;
	}


	public static String getFrontString(int value) {
		String name = "";
		if(value == WaitForOperation){
			name = "等待提现";
		}else if(value == OperationLock){
			name = "正在处理";
		}else if(value == OperationSuccess){
			name = "提现成功";
		}else if(value == Cancel){
			name = "用户撤销" ;
		}else if(value == OneTimeAudit){
			name = "正在处理" ;
		}else if(value == TwoTimeAudit){
			name = "正在处理";
		}else if(value == ThreeTimeAudit){
			name = "审核完成";
		}
		return name;
	}
}
