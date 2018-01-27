package com.szzc.common.utils;

import com.jucaifu.common.util.PasswordHelper;
import com.jucaifu.common.util.StringHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	private static final Logger log = LoggerFactory.getLogger(Utils.class);

	//格式化，保留5位小数
	private static DecimalFormat decimalFormat5 = new DecimalFormat("############0.00000");//格式化设置

	//格式化，保留4位小数
	private static DecimalFormat decimalFormat4 = new DecimalFormat("############0.0000");//格式化设置

	//格式化，保留3位小数
	private static DecimalFormat decimalFormat3 = new DecimalFormat("############0.000");

	//格式化，保留2位小数
	private static DecimalFormat decimalFormat2 = new DecimalFormat("############0.00");

	//格式化，保留1位小数
	private static DecimalFormat decimalFormat1 = new DecimalFormat("############0.0");

	private static DecimalFormat decimalFormat_model = new DecimalFormat("########.############");

	/**
	 * double转string，小数位有则显示，没有则不显示
	 * @param value
	 * @return
	 */
	public static String double2Str(double value) {
		return decimalFormat_model.format(value);
	}

	/**
	 * bigdecimal转string，有小数位则显示，没有则不显示
	 * @param value
	 * @return
	 */
	public static String decimal2Str(BigDecimal value) {
		return decimalFormat_model.format(value);
	}

	public static String toS(double val){
		BigDecimal bigDecimal = new BigDecimal(val);
		String result = bigDecimal.toString();
		return result ;
	}

	private static final int DEF_DIV_SCALE = 10;

	public static String wget(String u) throws Exception {
		URL url = new URL(u);
		BufferedReader br = new BufferedReader(new InputStreamReader(
				url.openStream(),"UTF-8"));
		StringBuffer content = new StringBuffer();
		String tmp = null;

		while ((tmp = br.readLine()) != null) {
			content.append(tmp);
		}
		br.close();
		return content.toString();
	}

	// 获得随机字符串
	public static String randomString(int count) {
		String str = "abcdefghigklmnopkrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
		int size = str.length();
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		while (count > 0) {
			sb.append(String.valueOf(str.charAt(random.nextInt(size))));
			count--;
		}
		return sb.toString();
	}

	public static String randomInteger(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(new Random().nextInt(10));
		}
		return sb.toString();
	}

	public static String getRandomImageName() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyyMMddHHmmsss");
		String path = simpleDateFormat.format(new Date());
		path += "_" + randomString(5);
		return path;
	}

	//
	// public static String MD5(String content) throws Exception {
	// MessageDigest md5 = MessageDigest.getInstance("MD5");
	// sun.misc.BASE64Encoder baseEncoder = new sun.misc.BASE64Encoder();
	// String retString = baseEncoder.encode(md5.digest(content.getBytes()));
	// return retString;
	// }

	public static String getMD5_32_xx(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}

	public static String MD5(String content, String salt) throws Exception {
		return PasswordHelper.encryString(content, salt);
	}

	public static int getNumPerPage() {
		return 40;
	}

	public static synchronized String UUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	// return seconds
	public static long timeMinus(Timestamp t1, Timestamp t2) {
		return (t1.getTime() - t2.getTime()) / 1000;
	}

	// 获得今天0点
	public static long getTimesmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	public static String getCurTimeString() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	public static String getCurDateString() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	public static String number2String(double f) {
		DecimalFormat df = new DecimalFormat();
		String style = "0.00000";// 定义要显示的数字的格式
		df.applyPattern(style);
		return df.format(f);
	}


	public static double getDoubleUp(double value, int scale) {
//		return ( (long)(value*Math.pow(10, scale)) ) /Math.pow(10.0, scale) ;
		return Double.valueOf(decimalFormat(value, scale));
	}

	//小数超出部分，截断处理
	public static double getDouble(double value, int scale) {
		return new BigDecimal(String.valueOf(value)).setScale(scale,BigDecimal.ROUND_DOWN).doubleValue();
	}

	//格式化小数，并转换为String
	public static String getDoubleS(double value, int scale) {
		return StringHelper.numberDecimalDigitFormat(value, scale);
	}


	//小数超出部分进位处理，返回String
	public static String getCarry(double value,int scale) {
		//value +=Math.pow(10, -8) ;
		String  temp	=new DecimalFormat("0.0000000000").format(value);

		String left = temp.substring(0, temp.indexOf(".") + 1 + scale);
		String right = temp.substring(temp.indexOf(".") + 1 + scale, temp.length());

		BigDecimal	result = null;
		if (StringUtils.isNotBlank(right) && new BigDecimal(right).compareTo(new BigDecimal(0)) > 0) {
			result = new BigDecimal(left).add(new BigDecimal(String.valueOf(Math.pow(10, -scale))));
		} else {
			result = new BigDecimal(left);
		}

		//LOG.i(Utils.class,result.setScale(scale,BigDecimal.ROUND_DOWN).toString());
		return result.setScale(scale,BigDecimal.ROUND_DOWN).toPlainString();  //toPlainString 避免科学计数法
	}

	//小数超出部分进位处理，返回Double
	public static double getCarry2Double(double value,int scale) {
		String result = getCarry(value, scale);
		return Double.valueOf(result);
	}



	public static String decimalFormat(double value ,int scale) {
		if(scale == 1) {
			decimalFormat1.setRoundingMode(RoundingMode.DOWN);
			return decimalFormat1.format(value);
		}else if(scale == 2) {
			decimalFormat2.setRoundingMode(RoundingMode.DOWN);
			return decimalFormat2.format(value);
		}else if(scale == 3) {
			decimalFormat3.setRoundingMode(RoundingMode.DOWN);
			return decimalFormat3.format(value);
		}else if(scale == 4) {
			decimalFormat4.setRoundingMode(RoundingMode.DOWN);
			return decimalFormat4.format(value);
		}else if(scale == 5) {
			decimalFormat5.setRoundingMode(RoundingMode.DOWN);
			return decimalFormat5.format(value);
		}
		return getDoubleS(value, scale);
	}

	public static String dateFormat(Timestamp timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(timestamp);
	}

	public static boolean isNumeric(String str) {
		if (str == null || str.trim().length() == 0)
			return false;
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "01234567890123456789012345678901234567890123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 功能：设置地区编码
	 *
	 * @return Hashtable 对象
	 */
	public static Hashtable getAreaCode() {
		Hashtable hashtable = new Hashtable();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	public static String getAfterDay(int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, -day);
		Date monday = c.getTime();
		String preMonday = sdf.format(monday);
		return preMonday;
	}

	/**
	 * 功能：判断字符串是否为日期格式
	 *
	 * @param strDate
	 * @return
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 得到本周周一
	 *
	 * @return yyyy-MM-dd
	 */
	public static String getMondayOfThisWeek(int days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, days);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		return sdf.format(c.getTime());
	}

	/**
	 * 得到本周周日
	 *
	 * @return yyyy-MM-dd
	 */
	public static String getSundayOfThisWeek(int days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, days);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 7);
		return sdf.format(c.getTime());
	}

	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}

	public static String getPrototypeUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}


	/**
	 * * 两个Double数相加 *
	 * luwei 新添加方法
	 * @param v1 *
	 * @param v2 *
	 * @return Double
	 */
	public static Double add(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return new Double(b1.add(b2).doubleValue());
	}

	/**
	 * * 两个Double数相减 *
	 * luwei 新添加方法
	 * @param v1 *
	 * @param v2 *
	 * @return Double
	 */
	public static Double sub(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return new Double(b1.subtract(b2).doubleValue());
	}

	/**
	 * * 两个Double数相乘 *
	 * luwei 新添加方法
	 * @param v1 *
	 * @param v2 *
	 * @return Double
	 */
	public static Double mul(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return new Double(b1.multiply(b2).doubleValue());
	}

	/**
	 * * 两个Double数相除 *
	 * luwei 新添加方法
	 * @param v1 *
	 * @param v2 *
	 * @return Double
	 */
	public static Double div(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return new Double(b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP)
				.doubleValue());
	}

	/**
	 * * 两个Double数相除，并保留scale位小数 *
	 * luwei 新添加方法
	 * @param v1 *
	 * @param v2 *
	 * @param scale *
	 * @return Double
	 */
	public static Double div(Double v1, Double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return new Double(b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue());
	}

	public static SortedSet randomSet(int n, int max) {
		Random ran = new Random();
		SortedSet set = new TreeSet();
		while(true){
			set.add(ran.nextInt(max) + 1);
			if(set.size() == n){
				break;
			}
		}
		return set;
	}

	//删除文件和目录
	public static void clearFiles(String workspaceRootPath){
		File file = new File(workspaceRootPath);
		if(file.exists()){
			deleteFile(file);
		}
	}
	private static void deleteFile(File file){
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for(int i=0; i<files.length; i++){
				deleteFile(files[i]);
			}
		}
		file.delete();
	}

	//隐藏银行卡号后四位
	public static String getBankAccountEnd(String account){
		if(StringUtils.isBlank(account) || account.length() < 5){
			return "****";
		}
		return "****"+account.substring(account.length()-4,account.length());
	}


	public static String getBankEnd(String account){
		if(StringUtils.isBlank(account) || account.length() < 5){
			return account;
		}
		return account.substring(account.length()-4,account.length());
	}


	//隐藏支付宝、充值地址/手机号码
	public static String getAccount(String account){
		if(account == null){
			return "";
		}
		int length = account.length();
		if(StringUtils.isBlank(account) || length < 6){
			return "****";
		}
		if(account.contains("@")){
			int index = account.indexOf("@");
			if(index > 4){
				return account.substring(0,3)+"***"+account.substring(index,account.length());
			}else if(index > 0){
				return "***"+account.substring(index,account.length());
			}
		}
		if(length > 11){
			return account.substring(0,4)+"****"+account.substring(length-4,length);
		}else{
			return account.substring(0,3)+"****"+account.substring(length-3,length);
		}

	}

	public static Double getDoubleForAccount(int account){
		String d = "0.";
		for(int i=1;i<=account;i++){
			if(i == account){
				d+="1";
			}else{
				d+="0";
			}
		}
		if(d.equals("0.")){
			d= "1";
		}
		return Double.parseDouble(d);

	}
	public static Timestamp getTimestamp() {
		return new Timestamp(new Date().getTime());
	}
}
