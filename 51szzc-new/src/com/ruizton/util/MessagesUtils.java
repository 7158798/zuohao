package com.ruizton.util;

import org.springframework.scheduling.annotation.Async;

import java.io.IOException;

@Async
public class MessagesUtils {

	public static final String PROFIX_CODE = "你的验证码为：";
	
	/*
	 * 100			发送成功
		101			验证失败
		102			手机号码格式不正确
		103			会员级别不够
		104			内容未审核
		105			内容过多
		106			账户余额不足
		107			Ip受限
		108			手机号码发送太频繁
		120			系统升级
	 * 
	 * */
	
    public static int send(String appkey,String secret,String url,String sms_template_code,String webName,String code,String tel){
    	int result = -1;
    	try {
			if(Utils.isNumeric(code)){
				code = PROFIX_CODE+code+"，请妥善保管你的验证码";
			}
			result = sendSMS.send(appkey,secret,url,sms_template_code,webName,code,tel) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result ;

//    	int retCode = ReturnCode.FAIL ;
//    	try {
//    		String u = "http://sdk2.zucp.net:8060/z_mdsmssend.aspx?sn="+name.trim()+"&pwd="+password.trim()+"&mobile="+phone.trim()+"&content="+URLEncoder.encode(content,"GB2312")+"&ext=&rrid=&stime=";
//    		URL url = new URL(u) ;
//			System.out.println(url.toString());
//    		BufferedReader br = new BufferedReader(new InputStreamReader( url.openStream()) ) ;
//			StringBuffer sb = new StringBuffer() ;
//			String tmp = null ;
//			while((tmp=br.readLine())!=null){
//				sb.append(tmp) ;
//			}
//			if(sb.toString().indexOf("100")!=-1){
//				retCode = ReturnCode.SUCCESS ;
//			}
//			System.out.println(sb.toString());
//			br.close() ;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	return retCode ;
    }


    public static void main(String args[]) throws Exception{
    	System.out.println(MessagesUtils.send("LTAICMRKhJxluiht", "8RMerphVUvWVwGXDFwLHa0LwXoihjZ", "sms.aliyuncs.com", "SMS_38460096", "51数字资产", "343434", "13725558294"));
    }
  
}
