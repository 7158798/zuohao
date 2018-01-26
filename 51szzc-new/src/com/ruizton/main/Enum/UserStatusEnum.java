package com.ruizton.main.Enum;

public class UserStatusEnum {
    public static final int NORMAL_VALUE = 1;//正常
    public static final int FORBBIN_VALUE = 2;//禁用
	public static final int FREEZE_VALUE = 3; //冻结
	public static final int FREEZE_VALIDATE = 4;//冻结审核中
	public static final int FREEZE_VALIDATE_FAILED = 5;

	public static final int OneTimeAudit = 10;
	public static final int TwoTimeAudit = 11;
	public static final int ThreeTimeAudit = 12;
    
    public static String getEnumString(int value) {
		String name = "";
		if (value == NORMAL_VALUE) {
			name = "正常";
		} else if (value == FORBBIN_VALUE) {
			name = "禁用";
		} else if (value == FREEZE_VALUE){
			name = "冻结";
	     }else if (value == FREEZE_VALIDATE){
	     	name = "认证";
		}else if (value == FREEZE_VALIDATE_FAILED){
			name = "认证失败";
		}else if(value == OneTimeAudit){
			name = "二级审核中";
		}else if(value == TwoTimeAudit){
			name = "三级审核中";
		}
		return name;
	}
    
}
