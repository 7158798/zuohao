package com.otc.api.console.ctrl.system.login.response;

import java.util.List;

/**
 * Created by zhaiyz on 15-11-3.
 */
public class Menu {

    private String name;

    private String code;

    private Long id;

    private List<Menu> nodes;

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

    public List<Menu> getNodes() {
        return nodes;
    }

    public void setNodes(List<Menu> nodes) {
        this.nodes = nodes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
