package com.ruizton.main.Enum;

public class CapitalOperationInStatus {
	public static final int NoGiven = 1 ;//建立了充值，为补全充值信息
	public static final int WaitForComing = 2 ;//等待银行到账
	public static final int Come = 3 ;//到账
	public static final int Invalidate = 4 ;//失效
	public static final int OneTimeAudit = 10;
	public static final int TwoTimeAudit = 11;
	public static final int ThreeTimeAudit = 12;
	
	public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case NoGiven:
			name = "尚未付款" ;
			break;
		case WaitForComing:
			name = "等待银行到账" ;	
			break;
		case Come:
			name = "已经到账" ;
			break;
		case Invalidate:
			name = "用户撤销" ;
			break;
		case OneTimeAudit:
			name = "二级审核中";
			break;
		case TwoTimeAudit:
			name = "三级审核中";
			break;
		case ThreeTimeAudit:
			name = "审核完成(状态异常)";
		}
		return name;
	}

	public static int getFrontStatus(int value){
		int status = 0;  //2  处理中  3 已完成  4 已失效
		switch (value) {
			case Come:
				status = 3;
				break;
			case Invalidate:
				status = 4;
				break;
			default:
				status = 2;
		}
		return status;
	}


	public static String getFrontString(int value) {
		String name = "";
		switch (value) {
			case NoGiven:
				name = "尚未付款" ;
				break;
			case WaitForComing:
				name = "等待银行到账" ;
				break;
			case Come:
				name = "已经到账" ;
				break;
			case Invalidate:
				name = "用户撤销" ;
				break;
			case OneTimeAudit:
				name = "正在处理";
				break;
			case TwoTimeAudit:
				name = "正在处理";
				break;
			case ThreeTimeAudit:
				name = "正在处理";
		}
		return name;
	}

}
