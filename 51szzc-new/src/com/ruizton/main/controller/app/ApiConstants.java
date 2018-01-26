package com.ruizton.main.controller.app;

/**
 * Created by a123 on 17-3-22.
 */
public interface ApiConstants {
    String APP_ASSETS_PRE = "/app/assets";

    //绑定支付宝账户
    String APP_ALIPAY_ADD_ACCOUNT = APP_ASSETS_PRE+ "/bindAlipay";

    //充值人民币 初始数据
    String APP_RECHARGE_CNY_INIT = APP_ASSETS_PRE+"/initrechargeCny";


    //添加绑定银行
    String APP_BANK_ADD_ACCOUNT = APP_ASSETS_PRE+"/bindBankAccount";

    //获取充值、充币、提现、提币记录
    String APP_RECHARGE_RECORD= APP_ASSETS_PRE+"/rechargeRecord";


    String APP_KYC_PRE = "/app/kyc";

    //验证银行卡
    String APP_BANK_VALIDATE = APP_KYC_PRE+"/validateBank";

    String APP_KCY_VALIDATE = APP_KYC_PRE+"/kycValidate";

    String APP_KYC_VALIDATE_INFO = APP_KYC_PRE+"/info";



    //获取提现、充值相关提醒文案
    String APP_RECHARGE_REMIND = APP_ASSETS_PRE+"/rechargeremind";

    //充值明细
    String APP_RECHARGE_DETAIL = APP_ASSETS_PRE+"/rechargeDetail";

    String APP_FEEDBACK = "/app/feedback";
    // 保存问题反馈
    String APP_FEEDBACK_ADD = APP_FEEDBACK + "/saveFeedback";
    // 获取问题反馈类型
    String APP_FEEDBACK_TITLE = APP_FEEDBACK + "/getTitle";
    // 获取问题反馈列表
    String APP_FEEDBACK_LIST = APP_FEEDBACK + "/getQuestionList";

    String APP_MEMBERCENTRE = "/app/memberCentre";
    // 获取会员中心数据
    String APP_MEMBERCENTRE_DATA = APP_MEMBERCENTRE + "/getData";

    String APP_PERSONAL = "/app/psersonal";
    // 上传头像
    String APP_PERSONAL_UPLOADAVATAR = APP_PERSONAL + "/uploadAvatar";
    // 获取用户信息
    String APP_PERSONAL_GETUSERINFO = APP_PERSONAL + "/getUserInfo";

    String APP_TRANSACTIONSETTING = "/app/transactionSetting";
    // 添加银行卡
    String APP_TRANSACTIONSETTING_ADDBANK = APP_TRANSACTIONSETTING + "/addBank";
    // 获取银行卡列表
    String APP_TRANSACTIONSETTING_BANKLIST = APP_TRANSACTIONSETTING + "/getBankList";
    // 获取用户银行卡列表
    String APP_TRANSACTIONSETTING_USERBANKLIST = APP_TRANSACTIONSETTING + "/getUserBankList";
    // 删除银行卡
    String APP_TRANSACTIONSETTING_DELETEBANK = APP_TRANSACTIONSETTING + "/deleteBank";
    // 获取银行卡详情
    String APP_TRANSACTIONSETTING_BANKINFO = APP_TRANSACTIONSETTING + "/getBankInfo";
    // 获取用户支付宝列表
    String APP_TRANSACTIONSETTING_USERALIPAYLIST = APP_TRANSACTIONSETTING + "/getUserAlipayList";
    // 获取支付宝详情
    String APP_TRANSACTIONSETTING_ALIPAYINFO = APP_TRANSACTIONSETTING + "/getAlipayInfo";
    // 删除支付宝
    String APP_TRANSACTIONSETTING_DELETEALIPAY = APP_TRANSACTIONSETTING + "/deleteAlipay";
    // 获取虚拟币列表
    String APP_TRANSACTIONSETTING_GETVIRTUALLIST = APP_TRANSACTIONSETTING + "/getVirtualList";
    // 添加虚拟币地址
    String APP_TRANSACTIONSETTING_SAVEVIRTUALADDRESS = APP_TRANSACTIONSETTING + "/saveVirtualAddress";
    // 获取虚拟币地址列表
    String APP_TRANSACTIONSETTING_GETVIRTUALADDRESSLIST = APP_TRANSACTIONSETTING + "/getVirtualAddressList";
    // 获取虚拟币地址详情
    String APP_TRANSACTIONSETTING_VIRTUALADDRESSINFO = APP_TRANSACTIONSETTING + "/getVirtualAddressInfo";
    // 删除虚拟币地址
    String APP_TRANSACTIONSETTING_DELETEVIRTUALADDRESS = APP_TRANSACTIONSETTING + "/deleteVirtualAddress";

    String APP_EMAIL = "/app/email";
    // 绑定邮箱
    String APP_EMAIL_ADDEMAIL = APP_EMAIL + "/addEmail";
    // 发送邮箱
    String APP_EMAIL_SENDEMAIL = APP_EMAIL + "/sendEmail";

    String APP_SECURITYSETTING = "/app/securitysetting";
    // 重置交易密码
    String APP_SECURITYSETTING_RESETTRADEPWD = APP_SECURITYSETTING + "/resetTradePwd";
    // 修改手机号密码
    String APP_SECURITYSETTING_CHANGEPHONE = APP_SECURITYSETTING + "/changePhone";

    String APP_SENDMESSAGE = "/app/messageSend";
    // 短信通知
    String APP_SENDMESSAGE_SENDSMS = APP_SENDMESSAGE + "/sendSMS";
    // 推送通知
    String APP_SENDMESSAGE_PUSHMESSAGE = APP_SENDMESSAGE + "/pushMessage";
    // 推送详情
    String APP_SENDMESSAGE_GETDETAIL = APP_SENDMESSAGE + "/getDetail";



    //支付宝支付，参数进行加密、签名
    String APP_ALIPAY_ORDER = "/app/alipayorder";

    String APP_ALIPAY_H5_ORDER= "/app/alipayorderh5";
}
