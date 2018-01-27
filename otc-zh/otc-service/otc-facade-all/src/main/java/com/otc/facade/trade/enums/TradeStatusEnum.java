package com.otc.facade.trade.enums;

import com.otc.facade.sys.enums.ProcessNode;
import com.otc.facade.user.enums.UserAutStatusEnum;
import com.otc.facade.virtual.enums.VirtualRecordOutStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fenggq on 17-5-10.
 */
public enum  TradeStatusEnum implements ProcessNode {

    INIT(0,"待支付","未支付"),

    PAYED(1,"待确认","未确认"),

    CANCEL(99,"已取消","买家取消"),

    COMPLETE(4,"已完成","完成交易"),

    CONFIRM_THREE_TIME_ADUIT(11,"申诉处理中","三级审核(申诉放币)",2,TradeStatusEnum.COMPLETE),

    CONFIRM_TWO_TIME_ADUIT(10,"申诉处理中","二级审核(申诉放币)",2,TradeStatusEnum.CONFIRM_THREE_TIME_ADUIT),

    APPEAL_CONFIRM(2,"申诉中","卖家已申诉",1,CONFIRM_TWO_TIME_ADUIT),

    CANCEL_THREE_TIME_ADUIT(21,"申诉处理中","三级审核(取消)",2,TradeStatusEnum.COMPLETE),

    CANCEL_TWO_TIME_ADUIT(20,"申诉处理中","二级审核(取消)",2,TradeStatusEnum.CANCEL_THREE_TIME_ADUIT),

    APPEAL_CANCEL(2,"申诉中","卖家已申诉",1,CANCEL_TWO_TIME_ADUIT),

    RUN_CONFIRM_THREE_TIME_ADUIT(31,"客服处理","三级审核(强制放币)",2,TradeStatusEnum.COMPLETE),

    RUN_CONFIRM_TWO_TIME_ADUIT(30,"客服处理","二级审核(强制放币)",2,TradeStatusEnum.RUN_CONFIRM_THREE_TIME_ADUIT),

    RUN_CONFIRM(1,"待确认","待确认",1,RUN_CONFIRM_TWO_TIME_ADUIT),

    APPEAL(2,"申诉中","卖家已申诉",1,null);

    private Integer code;

    private String desc;

    private String consoleDesc;

    private Integer processNo;

    private TradeStatusEnum nextNode;

    TradeStatusEnum(Integer code, String desc,String consoleDesc){
        this.code = code;
        this.desc = desc;
        this.consoleDesc = consoleDesc;
    }

    TradeStatusEnum(Integer code, String desc,String consoleDesc,Integer processNo,TradeStatusEnum nextNode){
        this.code = code;
        this.desc = desc;
        this.consoleDesc = consoleDesc;
        this.processNo = processNo;
        this.nextNode = nextNode;
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

    public static String getNameByCode(Integer code) {
        return map.get(code);
    }

    private static Map<Integer, String> map = new HashMap<>();

    static {
        for (TradeStatusEnum tradeStatusEnum : TradeStatusEnum.values()) {
            map.put( tradeStatusEnum.code,tradeStatusEnum.desc);
        }
    }

    public static Map<Integer, String> getMap() {
        return map;
    }

    public static Map<Integer,TradeStatusEnum> statusMap = new HashMap<>();

    static {
        for (TradeStatusEnum statusEnum : TradeStatusEnum.values()){
            statusMap.put(statusEnum.getCode(),statusEnum);
        }
    }

    public static Map<Integer, TradeStatusEnum> getStatusMap() {
        return statusMap;
    }

    /**
     *  校验Web状态
     * @param before
     * @param after
     * @return
     */
    public static boolean validateStatus(Integer before,Integer after){
        if(after == PAYED.getCode()){
            return before == INIT.code;
        }else if(after == COMPLETE.getCode()){
            return before == PAYED.code || before == APPEAL.code ;
        }else if(after == APPEAL.getCode()){
            return before == PAYED.code ;
        }else if(after == CANCEL.getCode()){
            return before == INIT.code || before == APPEAL.code ;
        }else{
            return false;
        }
    }

    /**
     * 判断申诉中交易放币
     * @param before
     * @return
     */
    public static boolean validateConsoleAppealConfirm(Integer before){
        return before == APPEAL.code ||
                before == CONFIRM_TWO_TIME_ADUIT.getCode() ||
                before == CANCEL_THREE_TIME_ADUIT.getCode();
    }


    /**
     * 判断申诉中交易取消
     * @param before
     * @return
     */
    public static boolean validateConsoleAppealCancel(Integer before){
        return before == APPEAL.code ||
                before == CANCEL_TWO_TIME_ADUIT.getCode() ||
                before == CANCEL_THREE_TIME_ADUIT.getCode();
    }


    /**
     * 判断进行中交易放币
     * @param before
     * @return
     */
    public static boolean validateConsoleRunConfrim(Integer before){
        return before == RUN_CONFIRM.getCode() ||
                before == RUN_CONFIRM_TWO_TIME_ADUIT.getCode() ||
                before == RUN_CONFIRM_THREE_TIME_ADUIT.getCode();
    }

    /**
     * 获取正在进行中的交易状态
     * @return
     */
    public static List<Integer> getRunStatus(){
        List<Integer> runStatus = new ArrayList<>();
        runStatus.add(INIT.getCode());
        runStatus.add(PAYED.getCode());
        runStatus.add(APPEAL.getCode());
        return runStatus;
    }


    public String getConsoleDesc() {
        return consoleDesc;
    }

    public void setConsoleDesc(String consoleDesc) {
        this.consoleDesc = consoleDesc;
    }

    public Integer getProcessNo() {
        return processNo;
    }

    public void setProcessNo(Integer processNo) {
        this.processNo = processNo;
    }

    public static boolean isComplete(Integer status){
        if(status == null) {
            return false;
        }
        return status == COMPLETE.getCode();
    }

    public TradeStatusEnum getNextNode() {
        return nextNode;
    }

    public void setNextNode(TradeStatusEnum nextNode) {
        this.nextNode = nextNode;
    }

    @Override
    public String getNextNode(ProcessNode node) {
        TradeStatusEnum status = (TradeStatusEnum) node;
        return status.getNextNode().getCode().toString();
    }

    @Override
    public int getNodeNo(ProcessNode node) {
        TradeStatusEnum status = (TradeStatusEnum) node;
        return status == null?-1:status.getProcessNo();
    }

    @Override
    public String getEndNode() {
        return COMPLETE.getCode().toString();
    }

}
