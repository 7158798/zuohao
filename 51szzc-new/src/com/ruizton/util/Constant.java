package com.ruizton.util;

import com.ruizton.main.model.Ftag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

public class Constant {
	public final static boolean isRelease = false ;//must change when release
//	public final static String WEBROOT = Configuration.getInstance().getValue("WEBROOT") ;
	public final static String Domain = Configuration.getValue("Domain") ;
	public final static String GoogleAuthName = Configuration.getValue("GoogleAuthName") ;
	public final static String MailName = Configuration.getValue("MailName") ;
	public final static String APPKEY = Configuration .getValue("APPKEY") ;
	public final static String Endpoint = Configuration .getValue("Endpoint") ;
	public final static String AccessKeyId = Configuration .getValue("AccessKeyId") ;
	public final static String AccessKeySecret = Configuration .getValue("AccessKeySecret") ;
	public final static String BucketName = Configuration .getValue("BucketName") ;
	public final static String OSSURL = Configuration .getValue("OSSURL") ;
	public final static String IS_OPEN_OSS = Configuration .getValue("IS_OPEN_OSS") ;
	public final static String BANKKEY = Configuration .getValue("BANKKEY") ;
	public final static String ARTICLE_SHARE = Configuration.getValue("article.share.url");
	public final static String ALIPAY_LOGFILE_URL = Configuration.getValue("alipay.logfile.url");
	public final static String ALIPAY_NOTIFY_URl = Configuration.getValue("alipay.notify.url");
	public final static String ALIPAY_QRNOTIFY_URl = Configuration.getValue("alipay.qrreturn.url");
	public final static String APP_ALIPAY_NOTIFY_URl = Configuration.getValue("app.alipay.notify.url");
	public final static boolean OPEN_REAL_ID = Boolean.valueOf(Configuration.getValue("OPEN_REAL_ID"));
	public final static boolean closeLimit = false ;
	
	
	public final static int VIP = 6;//VIP等级
	public final static int TYPES = 10;//虚拟币提现手续费
	
	public static Long messageTime = 3*60*1000L ;//短信有效时间
	public static Long mailTime = 30*60*1000L ;//邮件有效时间
	public static Long twiceTime = 1*60*1000L ;//两次请求的间隔时间
	
	/*
	 * 分页数量
	 * */
	public final static int RecordPerPage = 20 ;//充值记录分页
	public final static int AppRecordPerPage = 10 ;//app分页数量
	
	public final static int VirtualCoinWithdrawTimes = 10 ;//虚拟币每天提现次数
	public final static int CnyWithdrawTimes = 10 ;//人民币每天体现次数
	public static final boolean TradeSelf = true ;//
	

	public final static String AdminSYSDirectory = "upload"+"/"+"system" ;
	public final static String UserGoUpDirectory = "upload"+"/"+"user";
	public final static String IdentityPicDirectory =  "upload"+"/"+"identity_picture" ;
	public final static String AdminArticleDirectory = "upload"+"/"+"admin_article" ;
	public final static String DataBaseDirectory = "upload"+"/"+"dataBase" ;
	public static final String EmailReg = "^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";//邮箱正则
	public static final String PhoneReg = "^((1[0-9]{2})|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$";
	public final static int ErrorCountLimit = 10 ;//错误N次之后需要等待2小时才能重试
	public final static int ErrorCountAdminLimit = 5;//后台登陆错误

	public static final String num1Reg = "^\\d+((\\.\\d{1,6})|\\d*)$";   //整数或小数
	public static final String num2Reg = "^\\d+$";  //整数

	public static int HostNumber;
	public static int SingleNumber;

	public static List<String> tagList;


	public static void main(String[] args) {
		int t = new Constant().ErrorCountAdminLimit;
		System.out.println(t);
	}

	public static boolean isIsRelease() {
		return isRelease;
	}

	public static int getHostNumber() {
		return HostNumber;
	}

	public static void setHostNumber(int hostNumber) {
		HostNumber = hostNumber;
	}

	public static int getSingleNumber() {
		return SingleNumber;
	}

	public static void setSingleNumber(int singleNumber) {
		SingleNumber = singleNumber;
	}

	public static List<String> getTagList() {
		List<String> list;
		if(null != tagList && tagList.size() > 0){
			if(tagList.size() > 15){
				list = new ArrayList<String>();
				SortedSet sortedSet = Utils.randomSet(15, tagList.size());
				for(Iterator iter = sortedSet.iterator(); iter.hasNext();){
					list.add(tagList.get((Integer)iter.next() - 1));
				}
				return list;
			}
		}
		return tagList;
	}

	public static void setTagList(List<String> tagList) {
		Constant.tagList = tagList;
	}
}
