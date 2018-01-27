package com.otc.api.web.ctrl.trade.response;

import com.otc.facade.trade.enums.TradeSideEnum;
import com.otc.facade.trade.enums.TradeStatusEnum;
import com.otc.facade.trade.pojo.po.Trade;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by fenggq on 17-5-12.
 */
public class TradeInfoResp{
    private Long tradeId;

    private String tradeNo;

    private BigDecimal tradePrice;

    private BigDecimal tradeNum;

    private BigDecimal tradeMoney;

    private Long leftTimes; //剩余秒数

    private String coinName; //币种名称

    private String statusName; //状态

    private String payType;  //支付方式

    private String  payRemark; //支付方式备注

    private int ownerSide;//自己身份  1：买方  2：卖方

    private Long otherId;//对方id

    private String otherLoginName; //对方用户名

    private Date createTime;//创建时间

    private String fee ;// 手续费

    private Integer status; //状态

    private Integer judgeStatus; //评价状态

    private Date completeTime; //完成时间

    private Boolean otherKyc;

    private Boolean otherReal;

    private String city; //地点

    private Boolean isPublisher;//是否为发布者



    private Integer pageStatus;//前端状态   1.买家待付款 2买方待确认 3.买方申诉  4.卖方待付款 5.卖方待确认 6.卖方申诉 7.交易取消 8.交易完成  9.申诉处理



    //1.买家待付款 2买方待确认 3.买方申诉  4.卖方待付款 5.卖方待确认 6.卖方申诉 7.交易取消 8.交易完成  9.申诉处理

    public void setPageStatus(Integer status,Integer side){
        if(status == null || side == null){
            return;
        }
        if(side != TradeSideEnum.BUY.getCode() && side != TradeSideEnum.SELL.getCode()){
            return;
        }

        if(status == TradeStatusEnum.COMPLETE.getCode()){     //8.交易完成
            setPageStatus(8);
        }else if(status == TradeStatusEnum.CANCEL.getCode()){ //7.交易取消
            setPageStatus(7);
        }else if(status == TradeStatusEnum.INIT.getCode()){
            if(side == TradeSideEnum.BUY.getCode()){
                setPageStatus(1); //1.买家待付款
            }else{
                setPageStatus(4); //4.卖方待付款
            }
        }else if(status == TradeStatusEnum.PAYED.getCode()){
            if(side == TradeSideEnum.BUY.getCode()){
                setPageStatus(2); //2买方待确认
            }else{
                setPageStatus(5); //5.卖方待确认
            }
        }else if(status == TradeStatusEnum.APPEAL.getCode()){
            if(side == TradeSideEnum.BUY.getCode()){
                setPageStatus(3); //3.买方申诉
            }else{
                setPageStatus(6); // 6.卖方申诉
            }
        }else{
            setPageStatus(9);  //申诉处理中
        }
    }


    public Boolean getOtherKyc() {
        return otherKyc;
    }

    public void setOtherKyc(Boolean otherKyc) {
        this.otherKyc = otherKyc;
    }

    public Boolean getOtherReal() {
        return otherReal;
    }

    public void setOtherReal(Boolean otherReal) {
        this.otherReal = otherReal;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public Integer getPageStatus() {
        return pageStatus;
    }

    public void setPageStatus(Integer pageStatus) {
        this.pageStatus = pageStatus;
    }

    public Integer getJudgeStatus() {
        return judgeStatus;
    }

    public void setJudgeStatus(Integer judgeStatus) {
        this.judgeStatus = judgeStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public String getCoinName() {
        return coinName;
    }


    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public BigDecimal getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(BigDecimal tradePrice) {
        this.tradePrice = tradePrice;
    }

    public BigDecimal getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(BigDecimal tradeNum) {
        this.tradeNum = tradeNum;
    }

    public BigDecimal getTradeMoney() {
        return tradeMoney;
    }

    public void setTradeMoney(BigDecimal tradeMoney) {
        this.tradeMoney = tradeMoney;
    }

    public Long getLeftTimes() {
        return leftTimes;
    }

    public void setLeftTimes(Long leftTimes) {
        this.leftTimes = leftTimes;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayRemark() {
        return payRemark;
    }

    public void setPayRemark(String payRemark) {
        this.payRemark = payRemark;
    }

    public int getOwnerSide() {
        return ownerSide;
    }

    public void setOwnerSide(int ownerSide) {
        this.ownerSide = ownerSide;
    }

    public Long getOtherId() {
        return otherId;
    }

    public void setOtherId(Long otherId) {
        this.otherId = otherId;
    }

    public String getOtherLoginName() {
        return otherLoginName;
    }

    public void setOtherLoginName(String otherLoginName) {
        this.otherLoginName = otherLoginName;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public Boolean getPublisher() {
        return isPublisher;
    }

    public void setPublisher(Boolean publisher) {
        isPublisher = publisher;
    }
}
