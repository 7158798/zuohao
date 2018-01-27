package com.base.facade.info.pojo.po;

import java.io.Serializable;

/**
 * LogModulePo
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/8.
 */
public class LogModulePo implements Serializable {

    private String code;
    private String moduleName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
