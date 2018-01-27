package com.base.facade.banner.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.util.Date;

public class Activity extends BasePo {

    /**
     * 标题，对应表字段为：tbl_activity.title
     */
    private String title;

    /**
     * 摘要，对应表字段为：tbl_activity.summary
     */
    private String summary;

    /**
     * 类型，0：产品，1，链接，对应表字段为：tbl_activity.link_type
     */
    private String linkType;

    /**
     * 显示位置，0：首页banner，1：活动列表，2：产品列表头部，对应表字段为：tbl_activity.show_position
     */
    private Integer showPosition;

    /**
     * 对应表字段为：tbl_activity.product_id
     */
    private String productId;

    /**
     * 链接参数，以英文逗号分隔，与链接地址配合使用，对应表字段为：tbl_activity.url_param
     */
    private String urlParam;

    /**
     * 登录状态，true: 需要登录；false: 不需要登录，对应表字段为：tbl_activity.login_required
     */
    private Boolean loginRequired;

    /**
     * 排序，对应表字段为：tbl_activity.rank
     */
    private Integer rank;

    /**
     * 是否启用，对应表字段为：tbl_activity.active
     */
    private Boolean active;

    /**
     * 内容，对应表字段为：tbl_activity.content
     */
    private String content;

    /**
     * 图片地址，对应表字段为：tbl_activity.img_url
     */
    private String imgUrl;

    /**
     * 详情地址，对应表字段为：tbl_activity.detail_url
     */
    private String detailUrl;

    /**
     * 对应表字段为：tbl_activity.create_user_id
     */
    private String createUserId;

    /**
     * 对应表字段为：tbl_activity.modified_user_id
     */
    private String modifiedUserId;

    /**
     * 创建时间，对应表字段为：tbl_activity.create_date
     */
    private Date createDate;

    /**
     * 修改时间，对应表字段为：tbl_activity.modified_date
     */
    private Date modifiedDate;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType == null ? null : linkType.trim();
    }

    public Integer getShowPosition() {
        return showPosition;
    }

    public void setShowPosition(Integer showPosition) {
        this.showPosition = showPosition;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam == null ? null : urlParam.trim();
    }

    public Boolean getLoginRequired() {
        return loginRequired;
    }

    public void setLoginRequired(Boolean loginRequired) {
        this.loginRequired = loginRequired;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl == null ? null : detailUrl.trim();
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
