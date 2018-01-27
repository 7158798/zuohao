/**
 * Creation Date:Sep 10, 201511:17:24 AM
 * Copyright (c) 2015, 上海佐昊网络科技有限公司 All Rights Reserved.
 */
package com.base.facade.banner.pojo.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * ActivityAddReqBean
 *
 * @author yangyy
 */
public class ActivityConsoleApiVo implements Serializable {

    private String uuid;
    /**
     * 标题
     */
    private String title;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 显示位置，0：首页banner，1：活动列表，2：产品列表头部
     */
    private int showPosition;
    /**
     * 登录状态，true: 需要登录；false: 不需要登录
     */
    private boolean loginRequired;

    /**
     * 链接地址
     */
    private String detailUrl;

    /**
     * 图片url
     */
    private String imgUrl;

    /**
     * 类型，0：产品，1，链接
     */
    private String type;

    /**
     * 产品id
     */
    private String productId;

    /**
     * 启用状态
     */
    private boolean active;

    /**
     * 内容
     */
    private String content;

    /**
     * 排序编号
     */
    private Integer sortNumber;

    private String createUserId;

    private String modifiedUserId;

    private Date startDate;

    private Date endDate;

    private String bannerContent;

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getShowPosition() {
        return showPosition;
    }

    public void setShowPosition(int showPosition) {
        this.showPosition = showPosition;
    }

    public boolean isLoginRequired() {
        return loginRequired;
    }

    public void setLoginRequired(boolean loginRequired) {
        this.loginRequired = loginRequired;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
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

    public String getBannerContent() {
        return bannerContent;
    }

    public void setBannerContent(String bannerContent) {
        this.bannerContent = bannerContent;
    }
}
