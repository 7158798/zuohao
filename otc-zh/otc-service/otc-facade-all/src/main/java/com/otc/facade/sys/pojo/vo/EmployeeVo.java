package com.otc.facade.sys.pojo.vo;

import com.jucaifu.common.pojo.vo.BasePageVo;

/**
 * Created by lx on 17-4-26.
 */
public class EmployeeVo extends BasePageVo {

    private String employeeName;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}