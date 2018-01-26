package net.chrone.demo;

import net.chrone.api.ChroneApi;
import net.chrone.model.Merchant;
import net.chrone.model.Order;

/**
 * 注意：本demo的所有参数均为测试环境,上线之后请替换为正式环境参数
 * @author Jerry
 */
public class Demo {
	
	public static void main(String[] args) {
		//商户注册
//		Merchant merchant = new Merchant();
//		merchant.setAccount("18201868348");
//		merchant.setCardType("1");
//		merchant.setCardNo("6268228092275687");
//		merchant.setMchntName("测试商户");
//		merchant.setRealName("张三");
//		merchant.setPmsBankNo("308584000013");
//		merchant.setCertType("00");
//		merchant.setCertNo("110723198809242346");
//		merchant.setPassword("123456");
//		merchant.setMobile("18201868338");
//		boolean flag = ChroneApi.regist(merchant);
//		System.out.println("注册结果："+flag);
		
		/*Merchant merchant = new Merchant();
		merchant.setAccount("13832400537");
		merchant.setCardNo("6212260200081292837");
		merchant.setMchntName("北京神马科技有限公司5");
		merchant.setRealName("张萌鑫");
		merchant.setPmsBankNo("102100023657");
		merchant.setCertNo("220625199111221511");
		merchant.setPassword("123456");
		merchant.setCardType("1");		
		merchant.setCertType("00");		
		merchant.setMobile("15011126537");
		boolean flag = ChroneApi.regist(merchant);
		System.out.println("注册结果："+flag);*/
		
		//扫码支付
		Order order = new Order();
		order.setSource(1);
		order.setAmount(1000);
		order.setAccount("18201868342");
		order.setSettleAmt(999);
		order.setTranTp(1);
		order.setOrderNo(System.currentTimeMillis()+"");
		String qrcode = ChroneApi.qrpay(order);
		System.out.println(qrcode);
	}
}
