package com.base.facade.integral.enums;

/**
 * 积分状态
 * <p>
 * Created by yangyy on 16-5-3.
 */
public enum IntegralStatus {

    INIT(0, "预扣"),

    SUCCESS(1, "成功"),

    ROLLBACK(2, "返还");

    private int status;

    private String desc;

    IntegralStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
