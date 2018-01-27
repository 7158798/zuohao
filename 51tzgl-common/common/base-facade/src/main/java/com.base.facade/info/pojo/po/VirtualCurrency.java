package com.base.facade.info.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.util.Date;

/**
 * 虚拟货币
 */
public class VirtualCurrency extends BasePo {


    /**
     * 币种中文名称，对应表字段为：t_virtual_currency.cncy_cn_name
     */
    private String cncyCnName;

    /**
     * 币种英文名称，对应表字段为：t_virtual_currency.cncy_en_name
     */
    private String cncyEnName;

    /**
     * 英文短名称，对应表字段为：t_virtual_currency.cncy_en_short_name
     */
    private String cncyEnShortName;

    /**
     * 研发者，对应表字段为：t_virtual_currency.creator
     */
    private String creator;

    /**
     * 核心算法，对应表字段为：t_virtual_currency.core_algorithm
     */
    private String coreAlgorithm;

    /**
     * 发布日期，对应表字段为：t_virtual_currency.release_date
     */
    private Date releaseDate;

    /**
     * 发布数量，对应表字段为：t_virtual_currency.release_num
     */
    private String releaseNum;

    /**
     * 存量，对应表字段为：t_virtual_currency.stock
     */
    private String stock;

    /**
     * 证明方式，对应表字段为：t_virtual_currency.proof_method
     */
    private String proofMethod;

    /**
     * 难度调整，对应表字段为：t_virtual_currency.difficulty_adjust
     */
    private String difficultyAdjust;

    /**
     * 区块速度，对应表字段为：t_virtual_currency.block_speed
     */
    private String blockSpeed;

    /**
     * 区块奖励，对应表字段为：t_virtual_currency.block_reward
     */
    private String blockReward;

    /**
     * 市场代码，对应表字段为：t_virtual_currency.market_code
     */
    private String marketCode;

    /**
     * 币种介绍，对应表字段为：t_virtual_currency.cncy_desc
     */
    private String cncyDesc;

    /**
     * 主要特色，对应表字段为：t_virtual_currency.features
     */
    private String features;

    /**
     * 不足之处，对应表字段为：t_virtual_currency.shortcomming
     */
    private String shortcomming;

    /**
     * 币种图标url，对应表字段为：t_virtual_currency.cncy_icon_url
     */
    private String cncyIconUrl;

    private Integer status;

    /**
     * 删除标识，对应表字段为：t_virtual_currency.deleted
     */
    private Boolean deleted;

    /**
     * 创建人员，对应表字段为：t_virtual_currency.create_user_id
     */
    private Long createUserId;

    /**
     * 创建时间，对应表字段为：t_virtual_currency.create_date
     */
    private Date createDate;

    /**
     * 修改时间，对应表字段为：t_virtual_currency.modified_date
     */
    private Date modifiedDate;

    /**
     * 修改人员，对应表字段为：t_virtual_currency.modified_user_id
     */
    private Long modifiedUserId;

    private String createUserName;

    public String getCncyCnName() {
        return cncyCnName;
    }

    public void setCncyCnName(String cncyCnName) {
        this.cncyCnName = cncyCnName == null ? null : cncyCnName.trim();
    }

    public String getCncyEnName() {
        return cncyEnName;
    }

    public void setCncyEnName(String cncyEnName) {
        this.cncyEnName = cncyEnName == null ? null : cncyEnName.trim();
    }

    public String getCncyEnShortName() {
        return cncyEnShortName;
    }

    public void setCncyEnShortName(String cncyEnShortName) {
        this.cncyEnShortName = cncyEnShortName == null ? null : cncyEnShortName.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getCoreAlgorithm() {
        return coreAlgorithm;
    }

    public void setCoreAlgorithm(String coreAlgorithm) {
        this.coreAlgorithm = coreAlgorithm == null ? null : coreAlgorithm.trim();
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseNum() {
        return releaseNum;
    }

    public void setReleaseNum(String releaseNum) {
        this.releaseNum = releaseNum == null ? null : releaseNum.trim();
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock == null ? null : stock.trim();
    }

    public String getProofMethod() {
        return proofMethod;
    }

    public void setProofMethod(String proofMethod) {
        this.proofMethod = proofMethod == null ? null : proofMethod.trim();
    }

    public String getDifficultyAdjust() {
        return difficultyAdjust;
    }

    public void setDifficultyAdjust(String difficultyAdjust) {
        this.difficultyAdjust = difficultyAdjust == null ? null : difficultyAdjust.trim();
    }

    public String getBlockSpeed() {
        return blockSpeed;
    }

    public void setBlockSpeed(String blockSpeed) {
        this.blockSpeed = blockSpeed == null ? null : blockSpeed.trim();
    }

    public String getBlockReward() {
        return blockReward;
    }

    public void setBlockReward(String blockReward) {
        this.blockReward = blockReward == null ? null : blockReward.trim();
    }

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode == null ? null : marketCode.trim();
    }

    public String getCncyDesc() {
        return cncyDesc;
    }

    public void setCncyDesc(String cncyDesc) {
        this.cncyDesc = cncyDesc == null ? null : cncyDesc.trim();
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features == null ? null : features.trim();
    }

    public String getShortcomming() {
        return shortcomming;
    }

    public void setShortcomming(String shortcomming) {
        this.shortcomming = shortcomming == null ? null : shortcomming.trim();
    }

    public String getCncyIconUrl() {
        return cncyIconUrl;
    }

    public void setCncyIconUrl(String cncyIconUrl) {
        this.cncyIconUrl = cncyIconUrl == null ? null : cncyIconUrl.trim();
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
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

    public Long getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(Long modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
}