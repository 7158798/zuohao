package com.otc.facade.virtual.enums;

import com.otc.facade.sys.enums.ProcessNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lx on 17-4-18.
 */
public enum VirtualRecordOutStatus implements ProcessNode {

    APPLY("00","申请中"),
    REVOKE("02","撤销") ,
    SUCCESS("04","提现成功"),
    WAIT_SYSTEM_PROCESS("13","等待系统处理"),
    SYSTEM_LOCK("14","系统锁定"),
    SYSTEM_PROCESS_FAIL("15","系统锁定"),
    THREE_TIME_AUDIT("12","三级审核",3,VirtualRecordOutStatus.WAIT_SYSTEM_PROCESS),
    TWO_TIME_AUDIT("11","二级审核",2,VirtualRecordOutStatus.THREE_TIME_AUDIT),
    PROCESSING("03","处理中",1,VirtualRecordOutStatus.TWO_TIME_AUDIT);


    private String code;
    private String desc;
    private Integer processNo;
    private VirtualRecordOutStatus nextNode;

    public static Map<String,VirtualRecordOutStatus> statusMap = new HashMap<>();
    public static Map<String, String> webShowMap = new HashMap<>();

    static {
        for (VirtualRecordOutStatus virtualRecordStatus : VirtualRecordOutStatus.values()){
            statusMap.put(virtualRecordStatus.getCode(),virtualRecordStatus);
            if(virtualRecordStatus.getCode().equals("00")){
                webShowMap.put(virtualRecordStatus.getCode(), "申请中");
            }else if(virtualRecordStatus.getCode().equals("02")){
                webShowMap.put(virtualRecordStatus.getCode(), "提现失败");
            }else if(virtualRecordStatus.getCode().equals("04")){
                webShowMap.put(virtualRecordStatus.getCode(), "提现成功");
            }else if(virtualRecordStatus.getCode().equals("03") || virtualRecordStatus.getCode().equals("12") || virtualRecordStatus.getCode().equals("11")
                    || virtualRecordStatus.getCode().equals("13") || virtualRecordStatus.getCode().equals("14") || virtualRecordStatus.getCode().equals("15")){
                webShowMap.put(virtualRecordStatus.getCode(), "审核中");
            }else {
                webShowMap.put(virtualRecordStatus.getCode(),virtualRecordStatus.getDesc());
            }
        }
    }

    VirtualRecordOutStatus(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    VirtualRecordOutStatus(String code, String desc,Integer processNo,VirtualRecordOutStatus nextNode){
        this.code = code;
        this.desc = desc;
        this.processNo = processNo;
        this.nextNode = nextNode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getProcessNo() {
        return processNo;
    }

    public void setProcessNo(Integer processNo) {
        this.processNo = processNo;
    }

    public VirtualRecordOutStatus getNextNode() {
        return nextNode;
    }

    public void setNextNode(VirtualRecordOutStatus nextNode) {
        this.nextNode = nextNode;
    }

    public static String getDescByCode(String code){
        String desc = null;
        VirtualRecordOutStatus status = statusMap.get(code);
        if (status != null){
            desc = status.desc;
        }
        return desc;
    }

    public static String getWebDescByCode(String code){
        String desc = "";
        desc = webShowMap.get(code);
        return desc;
    }


    @Override
    public String getNextNode(ProcessNode node) {
        VirtualRecordOutStatus status = (VirtualRecordOutStatus) node;
        return status.getNextNode().getCode();
    }

    @Override
    public int getNodeNo(ProcessNode node) {
        VirtualRecordOutStatus status = (VirtualRecordOutStatus) node;
        return status.getProcessNo();
    }

    @Override
    public String getEndNode() {
        return WAIT_SYSTEM_PROCESS.getCode();
    }


}
