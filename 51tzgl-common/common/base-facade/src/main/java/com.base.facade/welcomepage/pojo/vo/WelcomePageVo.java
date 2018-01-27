package com.base.facade.welcomepage.pojo.vo;

import com.jucaifu.common.pojo.vo.BasePageVo;

import java.util.Date;

/**
 * Created by zh on 16-9-5.
 */
public class WelcomePageVo extends BasePageVo{

    /***创建的开始时间**/
    private Date startDate;

    /***创建的结束时间**/
    private Date endDate;

    /**启用状态**/
    private Boolean status;

    /**启用状态时，生效判断**/
    private Date finishDate;

    /**启用的有效时间,参数为系统时间**/
    private Date validate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Date getValidate() {
        return validate;
    }

    public void setValidate(Date validate) {
        this.validate = validate;
    }
}
