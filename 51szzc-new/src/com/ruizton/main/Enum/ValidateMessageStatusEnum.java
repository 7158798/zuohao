package com.ruizton.main.Enum;

public class ValidateMessageStatusEnum {
    public static final int Not_send = 1;//未验证
    public static final int Send = 2;//已验证
	public static final int ERROR_send = -1;
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == Not_send){
			name = "未发送";
		}else if(value == Send){
			name = "已发送";
		}else if(value == ERROR_send) {
			name = "发送失败";
		}
		return name;
	}
    
}
