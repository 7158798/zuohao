package com.otc.facade.trade.pojo.vo;

import com.jucaifu.common.pojo.vo.BasePageVo;
import com.otc.facade.trade.enums.TradeStatusEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fenggq on 17-5-10.
 */
public class TradeVo extends BasePageVo{
    private Long userId;

    private String tradeNo;

    private Integer status;

    private Integer isAppeal;

    private Date createStart;

    private Date createEnd;

    private Date completeStart;

    private Date completeEnd;

    private List<Integer> statuList;

    private List<Long> userIds;

    private Long coinId;

    private Integer tradeType; //0 全部  1、买  2、卖

    private Integer orderType;  //排序方式  1.下单时间  2.最后更新时间


    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Integer> getStatuList() {
        return statuList;
    }

    public void setStatuList(List<Integer> statuList) {
        this.statuList = statuList;
    }


    public Date getCreateStart() {
        return createStart;
    }

    public void setCreateStart(Date createStart) {
        this.createStart = createStart;
    }

    public Date getCreateEnd() {
        return createEnd;
    }

    public void setCreateEnd(Date createEnd) {
        this.createEnd = createEnd;
    }

    public Date getCompleteStart() {
        return completeStart;
    }

    public void setCompleteStart(Date completeStart) {
        this.completeStart = completeStart;
    }

    public Date getCompleteEnd() {
        return completeEnd;
    }

    public void setCompleteEnd(Date completeEnd) {
        this.completeEnd = completeEnd;
    }


    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsAppeal() {
        return isAppeal;
    }

    public void setIsAppeal(Integer isAppeal) {
        this.isAppeal = isAppeal;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }


    public void setDate(Date start, Date end, int type){
        if(type == 1 || type == 2){
            setCreateStart(start);
            setCreateEnd(end);
        }else{
            setCompleteStart(start);
            setCreateEnd(end);
        }
    }

    public void setQueryStatus(Integer status, int type){
        List<Integer> list = new ArrayList<>();
        if(status == null){
            status = 0;
        }
        switch (type){
            case 1:
                if(status == null || status == -1){
                    list.add(TradeStatusEnum.INIT.getCode());
                    list.add(TradeStatusEnum.PAYED.getCode());
                    list.add(TradeStatusEnum.RUN_CONFIRM_TWO_TIME_ADUIT.getCode());
                    list.add(TradeStatusEnum.RUN_CONFIRM_THREE_TIME_ADUIT.getCode());
                }else{
                    setStatus(status);
                }
                break;
            case 2:
                if(status == TradeStatusEnum.APPEAL.getCode()){
                    list.add(TradeStatusEnum.APPEAL.getCode());

                    list.add(TradeStatusEnum.CONFIRM_TWO_TIME_ADUIT.getCode());
                    list.add(TradeStatusEnum.CONFIRM_THREE_TIME_ADUIT.getCode());

                    list.add(TradeStatusEnum.CANCEL_TWO_TIME_ADUIT.getCode());
                    list.add(TradeStatusEnum.CANCEL_THREE_TIME_ADUIT.getCode());
                }else{
                    list.add(status);
                }
                setIsAppeal(1);
                break;
            case 3:
                setStatus(TradeStatusEnum.COMPLETE.getCode());
                break;
            case 4:
                setStatus(TradeStatusEnum.CANCEL.getCode());
                break;
        }
        if(list.size() > 0){
            setStatuList(list);
        }
    }
}
