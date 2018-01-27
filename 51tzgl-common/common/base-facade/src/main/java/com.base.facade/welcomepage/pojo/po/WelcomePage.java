package com.base.facade.welcomepage.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.util.Date;

public class WelcomePage extends BasePo {

    /**
     * 组号，对应表字段为：tbl_welcome_page.team_no
     */
    private String teamNo;

    /**
     * 欢迎页类型 1：半屏 2:全屏 3：多屏，对应表字段为：tbl_welcome_page.page_type
     */
    private Integer pageType;

    /**
     * 关闭方式，1：点击跳过，2，倒计时跳过，对应表字段为：tbl_welcome_page.close_type
     */
    private Integer closeType;

    /**
     * 关闭时间 单位:秒，对应表字段为：tbl_welcome_page.close_times
     */
    private Integer closeTimes;

    /**
     * 链接地址，对应表字段为：tbl_welcome_page.page_url
     */
    private String pageUrl;

    /**
     * 是否启用，对应表字段为：tbl_welcome_page.active
     */
    private Boolean active;

    /**
     * 开始时间，对应表字段为：tbl_welcome_page.start_date
     */
    private Date startDate;

    /**
     * 结束时间，对应表字段为：tbl_welcome_page.end_date
     */
    private Date endDate;

    /**
     * 创建时间，对应表字段为：tbl_welcome_page.create_date
     */
    private Date createDate;

    /**
     * 修改时间，对应表字段为：tbl_welcome_page.modified_date
     */
    private Date modifiedDate;

    /**
     * 创建人，对应表字段为：tbl_welcome_page.create_user_id
     */
    private String createUserId;

    /**
     * 修改人，对应表字段为：tbl_welcome_page.modified_user_id
     */
    private String modifiedUserId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getTeamNo() {
        return teamNo;
    }

    public void setTeamNo(String teamNo) {
        this.teamNo = teamNo == null ? null : teamNo.trim();
    }

    public Integer getPageType() {
        return pageType;
    }

    public void setPageType(Integer pageType) {
        this.pageType = pageType;
    }

    public Integer getCloseType() {
        return closeType;
    }

    public void setCloseType(Integer closeType) {
        this.closeType = closeType;
    }

    public Integer getCloseTimes() {
        return closeTimes;
    }

    public void setCloseTimes(Integer closeTimes) {
        this.closeTimes = closeTimes;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl == null ? null : pageUrl.trim();
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId == null ? null : modifiedUserId.trim();
    }
}