package com.ruizton.main.auto.okcoin;

/**
 * Created by lx on 17-2-13.
 */
public class OrderResponse {

    // true撤单请求成功，等待系统执行撤单；false撤单失败(用于单笔订单)
    private Boolean result;
    // 订单ID(用于单笔订单)
    private String order_id;
    // 错误编号
    private String error_code;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }
}
