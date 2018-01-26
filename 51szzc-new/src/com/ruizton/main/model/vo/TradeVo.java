package com.ruizton.main.model.vo;

import com.ruizton.main.Enum.EntrustTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by zygong on 17-4-19.
 */
public class TradeVo implements Serializable {

    private String amount;  // 数量
    private Timestamp date; // 时间
    private Long date_ms; //时间戳
    private String price; // 单价
    private int fid; // id
    private int type; //类型

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Long getDate() {
        return date.getTime()/1000;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Long getDate_ms() {
        return date.getTime();
    }

    public void setDate_ms(Long date_ms) {
        this.date_ms = date_ms;
    }

    public String getPrice() {
        return price.substring(0, price.length() - 4);
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getType() {
        return EntrustTypeEnum.getEnumString_Eng(type);
    }

    public void setType(int type) {
        this.type = type;
    }
}
