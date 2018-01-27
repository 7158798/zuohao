package com.otc.facade.sys.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;

public class SystemArgs extends BasePo implements Serializable {

    /**
     * id，对应表字段为：t_system_args.id
     */
    private Long id;

    /**
     * 参数key，对应表字段为：t_system_args.key_code
     */
    private String keyCode;

    /**
     * 参数值，对应表字段为：t_system_args.value
     */
    private String value;

    /**
     * 描述，对应表字段为：t_system_args.description
     */
    private String description;

    /**
     * 参数类型  预留，对应表字段为：t_system_args.type
     */
    private String type;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode == null ? null : keyCode.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}