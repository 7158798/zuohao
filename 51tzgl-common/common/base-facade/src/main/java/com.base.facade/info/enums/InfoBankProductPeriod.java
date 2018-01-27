package com.base.facade.info.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 产品期限
 * Created by liuxun on 16-9-22.
 */
public enum InfoBankProductPeriod {

    PERIOD_ONE("01","1天", "1","19"),
    PERIOD_THIRTY("02","30天", "20","44"),
    PERIOD_SIXTY("03","60天", "45","74"),
    PERIOD_NINETY("04","90天", "75","104"),
    PERIOD_ONE_TWENTY("05","120天", "105","149"),
    PERIOD_ONE_EIGHTY("06","180天", "150","224"),
    PERIOD_TWO_SEVENTY("07","270天", "225","314"),
    PERIOD_THREE_SIXTY("08","360天+", "315","");


    private String code;

    private String value;

    private String startPeriod;

    private String endPeriod;

    InfoBankProductPeriod(String code,String value,String startPeriod,String endPeriod){

        this.code = code;
        this.value = value;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
    }


    public static Map<String,InfoBankProductPeriod> periodMap;
    static {
        periodMap = new HashMap<String,InfoBankProductPeriod>();
        for (InfoBankProductPeriod period : InfoBankProductPeriod.values()){
            periodMap.put(period.getCode(),period);
        }
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(String startPeriod) {
        this.startPeriod = startPeriod;
    }

    public String getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }
}
