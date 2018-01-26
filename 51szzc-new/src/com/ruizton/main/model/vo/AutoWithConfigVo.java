package com.ruizton.main.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by luwei on 17-5-8.
 */
public class AutoWithConfigVo implements Serializable{

    private Integer fid;

    private String fShortName;;

    //系统自动提币，每日次数
    private String auto_day_count;

    //系统自动提币，单笔额度
    private String auto_single_limit;

    //系统自动提币，每日提现额度
    private String auto_day_limit;

    public AutoWithConfigVo() {

    }

    public AutoWithConfigVo(Integer fid, String fShortName) {
        this.fid = fid;
        this.fShortName = fShortName;
    }

    public AutoWithConfigVo(Integer fid, String fShortName, String auto_day_count, String auto_single_limit, String auto_day_limit) {
        this.fid = fid;
        this.fShortName = fShortName;
        this.auto_day_count = auto_day_count;
        this.auto_single_limit = auto_single_limit;
        this.auto_day_limit = auto_day_limit;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getAuto_day_count() {
        return auto_day_count;
    }

    public void setAuto_day_count(String auto_day_count) {
        this.auto_day_count = auto_day_count;
    }

    public String getAuto_single_limit() {
        return auto_single_limit;
    }

    public void setAuto_single_limit(String auto_single_limit) {
        this.auto_single_limit = auto_single_limit;
    }

    public String getAuto_day_limit() {
        return auto_day_limit;
    }

    public void setAuto_day_limit(String auto_day_limit) {
        this.auto_day_limit = auto_day_limit;
    }

    public String getfShortName() {
        return fShortName;
    }

    public void setfShortName(String fShortName) {
        this.fShortName = fShortName;
    }
}
