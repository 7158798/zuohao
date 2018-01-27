package com.otc.facade.advertisement.pojo.dto;

import java.io.Serializable;

/**
 * Created by zygong on 17-5-11.
 */
public class CalculatePricedto implements Serializable {
    private String formula;
    private String price;

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
