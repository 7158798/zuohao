package com.ruizton.main.Enum;


import com.ruizton.util.zuohao.LanguageUtil;
import org.apache.commons.lang.StringUtils;

public class BankTypeEnum {
    public static final int GH = 1;//工商银行
    public static final int HX = 14;//华夏银行
    public static final int PA = 13;//平安银行
    public static final int ZX = 12;//中信银行
    public static final int SHPD = 11;//上海浦东发展银行
    public static final int XY = 10;//兴业银行
    public static final int GD = 9;//中国光大银行
    public static final int MS = 8;//中国民生银行
    public static final int ZG = 7;//中国银行
    public static final int YZXS = 6;//邮政储蓄银行
    public static final int ZS = 5;//招商银行
    public static final int JT = 4;//交通银行
    public static final int NY = 3;//农业银行
    public static final int JX = 2;//建设银行
    public static final int QT = 15;//其它银行
    
    public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case GH:
			name = "工商银行";
			break;
		case HX:
			name = "华夏银行";
			break;
		case PA:
			name = "平安银行";
			break;
		case ZX:
			name = "中信银行";
			break;
		case SHPD:
			name = "上海浦东发展银行";
			break;
		case XY:
			name = "兴业银行";
			break;
		case GD:
			name = "中国光大银行";
			break;
		case MS:
			name = "中国民生银行";
			break;
		case ZG:
			name = "中国银行";
			break;
		case YZXS:
			name = "邮政储蓄银行";
			break;
		case ZS:
			name = "招商银行";
			break;
		case JT:
			name = "交通银行";
			break;
		case NY:
			name = "农业银行";
			break;
		case JX:
			name = "建设银行";
			break;
		case QT:
			name = "其他银行";
			break;
		}
		return name;
	}

	public static String getEnumString_i18n(int value) {
		String name = "";
		switch (value) {
			case GH:
				name = LanguageUtil.i18nMsg("icbc_bank");
				break;
			case HX:
				name = LanguageUtil.i18nMsg("huaxia_bank");
				break;
			case PA:
				name = LanguageUtil.i18nMsg("pingan_bank");
				break;
			case ZX:
				name = LanguageUtil.i18nMsg("citic_bank");
				break;
			case SHPD:
				name = LanguageUtil.i18nMsg("spd_bank");
				break;
			case XY:
				name = LanguageUtil.i18nMsg("industrial_bank");
				break;
			case GD:
				name = LanguageUtil.i18nMsg("ceb_bank");
				break;
			case MS:
				name = LanguageUtil.i18nMsg("minsheng_bank");
				break;
			case ZG:
				name = LanguageUtil.i18nMsg("china_bank");
				break;
			case YZXS:
				name = LanguageUtil.i18nMsg("postal_savings_bank");
				break;
			case ZS:
				name = LanguageUtil.i18nMsg("merchants_bank");
				break;
			case JT:
				name = LanguageUtil.i18nMsg("communications_bank");
				break;
			case NY:
				name = LanguageUtil.i18nMsg("agricultural_bank");
				break;
			case JX:
				name = LanguageUtil.i18nMsg("construction_bank");
				break;
			case QT:
				name = LanguageUtil.i18nMsg("other_bank");
				break;
		}
		return name;
	}

	public static String getBankUrl(int value) {
		String url = "";
		switch (value) {
			case GH:
				url = "ICBC_log@2X.png";
				break;
			case HX:
				url = "HXB_logo@2X.png";
				break;
			case PA:
				url = "PAB_logo@2X.png";
				break;
			case ZX:
				url = "ECITIC_logo@2X.png";
				break;
			case SHPD:
				url = "SPDB_logo@2X.png";
				break;
			case XY:
				url = "CIB_logo@2X.png";
				break;
			case GD:
				url = "CEB_logo@2X.png";
				break;
			case MS:
				url = "CMBC_logo@2X.png";
				break;
			case ZG:
				url = "BOC_logo@2X.png";
				break;
			case YZXS:
				url = "PSBC_logo@2X.png";
				break;
			case ZS:
				url = "CMB_logo@2X.png";
				break;
			case JT:
				url = "BOCOM_logo@2X.png";
				break;
			case NY:
				url = "ABC_logo@2X.png";
				break;
			case JX:
				url = "CCB_logo@2X.png";
				break;
			case QT:
				url = "其他银行";
				break;
		}
		return "static/front/images/banklogo/"+url;
	}

	public static String getBackgroundColor(String bank){
    	String color = "";
		if(StringUtils.isBlank(bank)){
			return color;
		}
    	if(bank.equals("中国银行") ||bank.equals("中国光大银行") ||bank.equals("招商银行") ||bank.equals("中信银行") ||bank.equals("华夏银行") ||bank.equals("工商银行") ||bank.equals("平安银行")){
			color = "red";
		}else if(bank.equals("交通银行") ||bank.equals("建设银行") ||bank.equals("兴业银行") ||bank.equals("上海浦东发展银行")) {
			color = "blue";
		}else if(bank.equals("农业银行") ||bank.equals("中国民生银行") ||bank.equals("邮政储蓄银行")){
			color = "green";
		}else{
			color = "other";
		}
    	return color;
	}

	public static String getBgColor(int value) {
		String name = "";
		switch (value) {
			case GH:
				name = "red";
				break;
			case HX:
				name = "red";
				break;
			case PA:
				name = "red";
				break;
			case ZX:
				name = "red";
				break;
			case SHPD:
				name = "blue";
				break;
			case XY:
				name = "blue";
				break;
			case GD:
				name = "red";
				break;
			case MS:
				name = "green";
				break;
			case ZG:
				name = "red";
				break;
			case YZXS:
				name = "green";
				break;
			case ZS:
				name = "red";
				break;
			case JT:
				name = "blue";
				break;
			case NY:
				name = "green";
				break;
			case JX:
				name = "blue";
				break;
			case QT:
				name = "other";
				break;
		}
		return name;
	}
    
}
