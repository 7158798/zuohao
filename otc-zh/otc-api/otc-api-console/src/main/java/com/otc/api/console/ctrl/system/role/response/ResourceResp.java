package com.otc.api.console.ctrl.system.role.response;

import com.otc.api.console.base.BaseListResp;

import java.util.List;

/**
 * Created by lx on 17-5-2.
 */
public class ResourceResp extends BaseListResp {

    private String name;

    private String code;

    private String type;

    private Integer orderNo;

    private Long parentId;

    private List<ResourceResp> list;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public List<ResourceResp> getList() {
        return list;
    }

    public void setList(List<ResourceResp> list) {
        this.list = list;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

}
