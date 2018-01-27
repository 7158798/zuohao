package com.jucaifu.common.interactive;

import com.jucaifu.common.util.JsonHelper;

/**
 * ModuleInteractiveVo
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/20.
 */
public class ModuleInteractiveVo {

    private String moduleId;

    private String cmd;

    private String queryJsonStr;

    /**
     * Instantiates a new Module interactive vo.
     */
    public ModuleInteractiveVo() {
    }

    /**
     * Instantiates a new Module interactive vo.
     *
     * @param moduleId the module id
     * @param cmd the cmd
     * @param queryJsonStr the query json str
     */
    public ModuleInteractiveVo(String moduleId, String cmd, String queryJsonStr) {
        this.moduleId = moduleId;
        this.cmd = cmd;
        this.queryJsonStr = queryJsonStr;
    }

    /**
     * Gets module id.
     *
     * @return the module id
     */
    public String getModuleId() {
        return moduleId;
    }

    /**
     * Sets module id.
     *
     * @param moduleId the module id
     */
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * Gets cmd.
     *
     * @return the cmd
     */
    public String getCmd() {
        return cmd;
    }

    /**
     * Sets cmd.
     *
     * @param cmd the cmd
     */
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     * Gets query json str.
     *
     * @return the query json str
     */
    public String getQueryJsonStr() {
        return queryJsonStr;
    }

    /**
     * Sets query json str.
     *
     * @param queryJsonStr the query json str
     */
    public void setQueryJsonStr(String queryJsonStr) {
        this.queryJsonStr = queryJsonStr;
    }

    /**
     * Sets query obj.
     *
     * @param obj the obj
     */
    public <T> void setQueryObj(T obj) {
        this.setQueryJsonStr(JsonHelper.obj2JsonStr(obj));
    }
}
