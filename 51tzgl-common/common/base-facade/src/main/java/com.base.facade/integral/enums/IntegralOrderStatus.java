package com.base.facade.integral.enums;

/**
 * 积分订单状态
 * <p>
 * Created by yangyy on 16-5-3.
 */
public enum IntegralOrderStatus {

    INIT(0, "初始"),

    SUCCESS(1, "成功"),

    FAIL(2, "失败");

    private int status;

    private String desc;

    IntegralOrderStatus(int status, String desc) {
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
