package com.otc.facade.sys.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.util.Date;

public class Resource extends BasePo implements Serializable {

    /**
     * 对应表字段为：t_resource.name
     */
    private String name;

    /**
     * 对应表字段为：t_resource.code
     */
    private String code;

    /**
     * 对应表字段为：t_resource.parent_id
     */
    private Long parentId;

    /**
     * 对应表字段为：t_resource.description
     */
    private String description;

    /**
     * 对应表字段为：t_resource.order_no
     */
    private Integer orderNo;

    /**
     * 对应表字段为：t_resource.level
     */
    private Integer level;

    /**
     * 对应表字段为：t_resource.create_date
     */
    private Date createDate;

    private static final long serialVersionUID = 1L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}