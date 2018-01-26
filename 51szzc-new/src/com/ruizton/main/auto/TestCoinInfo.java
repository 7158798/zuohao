package com.ruizton.main.auto;

/**
 * Created by lx on 17-1-18.
 */
public enum TestCoinInfo {
    // 比特币
    BTC(1,15,0.0026,25,0.0001,2),
    // 莱特币
    LTB(3,0.7,0.025,25,0.001,10);

    // 货币类型id
    private int code;
    // 人民币最小下限
    private double rmb;
    // 虚拟币最小下线
    private double xnb;
    // 数量
    private double quantity;
    // 挂单比例
    private double bl;
    // 撤单分钟数
    private int minutes;

    TestCoinInfo(int code, double rmb, double xnb,double quantity,double bl,int minutes){
        this.code = code;
        this.rmb = rmb;
        this.xnb = xnb;
        this.quantity = quantity;
        this.bl = bl;
        this.minutes = minutes;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public double getRmb() {
        return rmb;
    }

    public void setRmb(double rmb) {
        this.rmb = rmb;
    }

    public double getXnb() {
        return xnb;
    }

    public void setXnb(double xnb) {
        this.xnb = xnb;
    }

    public double getBl() {
        return bl;
    }

    public void setBl(double bl) {
        this.bl = bl;
    }

    public int getMinutes() {
        return minutes;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public static TestCoinInfo getCoinInfo(int code){

        for (TestCoinInfo temp : TestCoinInfo.values()){
            if (code == temp.getCode()){
                return temp;
            }
        }
        return null;
    }

    /**
     * 计算挂单的上线
     * @param price
     */
    public void calcLimit(double price){
        // 计算虚拟币的下限
        double tQaun = (this.quantity + 1) * this.bl;
        this.xnb = tQaun;
        // 计算人民币的下限
        double tRmb = this.xnb * price;
        this.rmb = tRmb;
    }
}
