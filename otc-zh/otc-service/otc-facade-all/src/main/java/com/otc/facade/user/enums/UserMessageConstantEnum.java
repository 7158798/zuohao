package com.otc.facade.user.enums;

import com.otc.facade.message.enums.MessageType;

/**
 * Created by fenggq on 17-4-21.
 */
public enum UserMessageConstantEnum {

    REGIST_SUCESS("01",0, MessageType.SYSTEM.getCode(),"注册成功","感谢您注册coinsfriend数字货币交易平台，用心创造价值，我们将用真挚的服务让您满意，在您使用平台的过程中如遇到任何问题也可通过在线客服反馈给我们。"),
    REALNAME_PASS("02",1, MessageType.SYSTEM.getCode(),"实名认证成功","恭喜您，实名认证通过！您可以在平台上发起买入或卖出（需充币）申请了。完成KYC认证可与优质交易员交易数字货币。"),
    KYC_POST("03",1, MessageType.SYSTEM.getCode(),"KYC认证-审核中","您的KYC认证材料已提交，请耐心等待审核，审核时间1-2个工作日。如有疑问可咨询在线客服。"),
    KYC_NO_PASS("04",0, MessageType.SYSTEM.getCode(),"KYC认证-未通过","很抱歉，您的KYC认证未通过，理由：#{code}。请完善或提供正确的材料后重新申请认证。"),
    KYC_PASS("05",0, MessageType.SYSTEM.getCode(),"KYC认证-已通过","恭喜您，KYC认证通过！您可以在广告列表里提交所有交易员发起的交易订单了。"),
    MODIFY_EMAILE_SUCESS("06",0, MessageType.SYSTEM.getCode(),"修改邮箱成功","您好！您与#{time}将邮箱成功修改为#{code}，如非本人操作请联系在线客服。"),
    MODIFY_FISHCODE_SUCESS("07",1, MessageType.SYSTEM.getCode(),"防钓鱼码设置","您好！你刚刚#{code}了防钓鱼码，请牢记或抄写至您的笔记本里，在进行交易确认时可根据防钓鱼码确认链接的安全性。"),
    MODIFY_PWD_SUCESS("08",2, MessageType.SYSTEM.getCode(),"修改登录密码","你好！您与#{time}成功修改了登录密码，如非本人操作请联系在线客服。"),
    RESET_PWD_SUCESS("08",2, MessageType.SYSTEM.getCode(),"后台重置登录密码","您好！您的登录密码已重置，初始密码为【#{code}】，为了账户安全，请及时登录并修改！"),
    FORRIN_USER("09",2, MessageType.SYSTEM.getCode(),"禁用通知","您好！您的coinsfriend交易平台账户存在账户安全风险，系统管理员设置禁用，具体可咨询在线客服。"),
    NO_FORRIN_USER("10",2, MessageType.SYSTEM.getCode(),"解除禁用","您好！您的coinsfriend交易平台账户已解除禁用，您可以正常登录并使用平台了。"),
    TRAE_WAIT_PAY("11",1, MessageType.TRADE.getCode(),"交易-待付款","交易订单#{code}已成功提交，请在90分钟内完成付款，超时未付款系统会代为取消订单"),
    TRADE_WAIT_COMMINT("12",0, MessageType.TRADE.getCode(),"交易-待确认","交易订单#{code}买家已完成付款，请及时处理。 如长时间未处理会影响您的平均放行时间及交易员信誉等级。"),
    TRADE_COMPLETE("13",0, MessageType.TRADE.getCode(),"交易-完成","交易订单#{code}已完成，请及时对本次交易进行评价！"),
    TRADE_APPEAL("14",0, MessageType.TRADE.getCode(),"交易-申诉","交易订单#{code}已发起申诉，您可以与交易员自行沟通协商解决。如需平台介入处理可联系在线客服或致电021-66666666。"),
    TRADE_CANEL("15",1, MessageType.TRADE.getCode(),"交易-取消","交易订单#{code}交易员已取消交易。"),
    TRADE_TIME_OUT("15",1, MessageType.TRADE.getCode(),"交易-取消","交易订单#{code}超时未付款，系统已代为取消交易。"),
    COIN_WITHOUT_AUDIT("16",1, MessageType.FINANCE.getCode(),"提币-审核中","您与#{time}发起一笔提现申请，系统会在24小时内完成审核，请耐心等待"),
    COIN_WITHOUT_FAILD("17",0, MessageType.FINANCE.getCode(),"提币-失败","您好！您与#{time}发起一笔提现申请，系统已为您取消，如非您本人要求则为系统操作错误，您可以重新发起提币申请或联系在线客服。"),
    COIN_WITHOUT_SUCESS("18",0, MessageType.FINANCE.getCode(),"提币-完成","您与#{time}发起的一笔提现申请，提现成功，具体钱包到账时间请前往钱包确认。"),
    JUDGE_RESULT("19",1,MessageType.EVALUATE.getCode(),"对方评价完成","#{code}"),
    COIN_RECHARGE_SUCESS("20", 0, MessageType.FINANCE.getCode(),"充值-完成","您与#{time}向coinsfriend钱包充值#{code}，已到账！请前往钱包账户进行确认"),
    SYS_TRADE_COMPLETE("21", 0, MessageType.TRADE.getCode(),"交易-完成","您好！交易订单#{code}因超时未确认已由系统代为确认放币，请及时对本次交易进行评价！如有疑问可咨询客服！");


    private String code;

    private String title;

    private String content;

    private Integer sendtype;

    private String messageType;

    UserMessageConstantEnum(String code,Integer type,String messageType, String title,String content){
        this.code = code;
        this.title = title;
        this.content = content;
        this.sendtype = type;  //发送类型   0：全部  1：系统内  2：邮箱
        this.messageType = messageType;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSendtype() {
        return sendtype;
    }

    public void setSendtype(Integer sendtype) {
        this.sendtype = sendtype;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
