package com.otc.facade.trade.enums;

/**
 * Created by fenggq on 17-5-10.
 */
public enum TradeJudgeEnum {
    INIT(0,"初始"),

    BUY(1,"买家已评价"),

    SELL(2,"买家已评价"),

    COMPLETE(3,"已完成评价");

    private Integer code;

    private String desc;

    TradeJudgeEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



    /**
     *  获取评价状态
     * @param before
     * @param after
     * @return
     */
    public static Integer getJudgeStatus(Integer before,Integer after){
        if(after != SELL.getCode() && after != BUY.getCode()){
            return -1;
        }
        if(before == after ){
            return -1;
        }
        if(before == INIT.getCode()){
            return after;
        }
        if(before == SELL.getCode() || before == BUY.getCode()){
            return COMPLETE.getCode();
        }
        return -1;
    }

}
