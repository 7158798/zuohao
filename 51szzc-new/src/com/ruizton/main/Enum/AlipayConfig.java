package com.ruizton.main.Enum;

import com.ruizton.util.Constant;

/**
 * 支付宝参数
 * Created by luwei on 17-3-15.
 */
public class AlipayConfig {


    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    public static String partner = "2088621544608408";

    public static String private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKwDevlt9Q9HwcjnNjXxvrd41KlKOy/HEWU/77O4wrFpwm4vxSk+HGSct3DIrAeNmgnd/UBjPro5KAGZOBnXtfgu3vxQKeJT6bqJkgT2Bg8uvRSUPVJCgqErOFlemkT67BazOMxjt0pgRSFvP1WXTVaP2qUiCZEQ6FVKAD4MISArAgMBAAECgYB5h8/3sJ9ml10rs2fSvyTu/djKbt7YR75bmcuiX9R2gnFTZj7Xf8GRuEPG1JDumTYO6J+IQVZNPhqs3nMLlyNBt8ofEPqDEnNu2soAiCrs5G2Rtd50b7OsGAa2ZzxshvkinWNPKnTKVpzn6YB/wmUgBU/ISRTJ/hl16EOCh6jmMQJBAN9XqU/+1bm8kEL9PL7sJbNrL+dQIvmwbWhzr16zTEwQTMOngKyixJ1VO+20/LaLcZWcN/DjjTCABd/xIPpLrDMCQQDFKm+HWYHoMMYU4jM5Zg9TC6pqhlNCbeCyouF2/lfMXhxEjoL/uSpvPaO7sKegD14BjGmX7xpKfGyHzTpdp0QpAkEArg7AahKdeCInf72iEN0zSI/ZhnkiuNsxePzniHNNm938JWMuWdyERGV/zfKGHLGx9LoJstd0Wn77lRpz6/z7lwJBAKEkdLC/k+/sZQhOc6U259Fs2GRl0oiZeysk+nchmyp5xEq32xMcCDWQwFA3KlkkFiXX17mIfwlftegr8Mb4XTkCQFEA4M5sddL3fSq81DFycvTE/Swwy1W4r4KHfnKBfAOPtu3gj3tRqukP0I+gJ1TgpKTp41jLI5UlQfAcTVVB5PU=";

    // 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm  mapi网关产品的公钥
    public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

    //app端集成，服务器异步通知
    public static String app_notify_url = Constant.APP_ALIPAY_NOTIFY_URl;

    // 电脑端集成，服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = Constant.ALIPAY_NOTIFY_URl;

    // 电脑端集成，页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = Constant.ALIPAY_QRNOTIFY_URl;

    // 签名方式
    public static String sign_type = "RSA";

    // 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
    public static String log_path = Constant.ALIPAY_LOGFILE_URL;

    // 字符编码格式 目前支持 gbk 或 utf-8
    public static String input_charset = "utf-8";

    // 支付类型 ，无需修改
    public static String payment_type = "1";

    // 调用的接口名，无需修改
    public static String service = "create_direct_pay_by_user";

    //2017-04-10修改，屏蔽cartoon^卡通，情况：其它银行信用卡无法支付，但民生信用卡可以，经测试是卡通引起的
    // 可用的支付渠道：指定支付的方式，如借记卡、支付宝余额等(可排除信用卡)
    public static String enable_paymethod = "directPay^bankPay^debitCardExpress";

    //禁用的支付渠道
//    public static String disable_paymethod = "creditCardExpress^coupon^point^voucher^cash";

    //超时时间(设置为1天)
    public static String it_b_pay = "24h";

    //扫码支付方式
    public static String qr_pay_mode = "4";

    //二维码宽度
    public static String qrcode_width = "140";

    //测试使用，默认的支付金额为0.01
    public static String default_money = "0.01";

    //↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑


    //↓↓↓↓↓↓↓↓↓↓ 请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // 防钓鱼时间戳  若要使用请调用类文件submit中的query_timestamp函数
    public static String anti_phishing_key = "";

    // 客户端的IP地址 非局域网的外网IP地址，如：221.0.0.1
    public static String exter_invoke_ip = "";
    //↑↑↑↑↑↑↑↑↑↑请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑




    /**************************为app支付、h5支付新添加的变量 start************************************************/
    //支付宝应用appid
    public static String app_id = "2017031006152668";

    //支付宝网关url
    public static String mapi_url = "https://openapi.alipay.com/gateway.do";

    //参数返回格式，目前只支持json
    public static String format = "json";

    // 销售产品码 必填
    public static String product_code="QUICK_WAP_PAY";

    //应用的支付宝公钥
    public static String app_alipay_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

    //app可用支付区间
    public static String app_enable_pay_channels = "bankPay,debitCardExpress,balance";


    /**************************为app支付、h5支付新添加的变量 end************************************************/
}
