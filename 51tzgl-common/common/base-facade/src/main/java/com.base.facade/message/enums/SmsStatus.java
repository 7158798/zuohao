package com.base.facade.message.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luwei
 * @Date 12/5/16 10:45 AM
 */
public enum SmsStatus {

    /**
     * 草稿
     */
    DRAFT(0, "草稿"),

    /**
     * 待发送
     */
    WAIT(1, "待发送"),

    /**
     * 发送成功
     */
    SUCCESS(2, "发送成功"),

    /**
     * 发送失败
     */
    FAILED(3, "发送失败");

    private Integer status;

    private String statusName;

    private static final Map<Integer, String> SMS_STATUS_MAP = new HashMap<Integer, String>();

    static {
        for (SmsStatus status : SmsStatus.values()) {
            SMS_STATUS_MAP.put(status.getStatus(), status.getStatusName());
        }
    }

    SmsStatus(Integer status, String name) {
        this.status = status;
        this.statusName = name;
    }

    /**
     * 取得状态码对应的名称
     *
     * @param status 状态
     * @return 名称
     */
    public static String getNameByStatus(Integer status) {
        return SMS_STATUS_MAP.get(status);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
