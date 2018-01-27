package com.base.facade.integral.enums;

/**
 * 积分操作类型
 * <p>
 * Created by yangyy on 16-5-3.
 */
public enum IntegralOperateType {

    INCOME(1, "收入"),
    EXPENSE(0, "支出");

    private int type;

    private String desc;

    IntegralOperateType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
