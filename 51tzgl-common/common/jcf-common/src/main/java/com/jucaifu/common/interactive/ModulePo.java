package com.jucaifu.common.interactive;

import com.jucaifu.common.util.ValueHelper;

/**
 * ModulePo
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/17.
 */
public class ModulePo {

    private String uuid;
    private String moduleId;
    private String moduleName;

    private String deploySchema;
    private String deployIP;
    private String deployPort;
    private String interactiveUrl;

    private String depends;

    /**
     * Gets uuid.
     *
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets uuid.
     *
     * @param uuid the uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
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
     * Gets module name.
     *
     * @return the module name
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * Sets module name.
     *
     * @param moduleName the module name
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * Gets deploy iP.
     *
     * @return the deploy iP
     */
    public String getDeployIP() {
        return deployIP;
    }

    /**
     * Sets deploy iP.
     *
     * @param deployIP the deploy iP
     */
    public void setDeployIP(String deployIP) {
        this.deployIP = deployIP;
    }

    /**
     * Gets deploy port.
     *
     * @return the deploy port
     */
    public String getDeployPort() {
        return deployPort;
    }

    /**
     * Sets deploy port.
     *
     * @param deployPort the deploy port
     */
    public void setDeployPort(String deployPort) {
        this.deployPort = deployPort;
    }

    /**
     * Gets interactive url.
     *
     * @return the interactive url
     */
    public String getInteractiveUrl() {
        return interactiveUrl;
    }

    /**
     * Sets interactive url.
     *
     * @param interactiveUrl the interactive url
     */
    public void setInteractiveUrl(String interactiveUrl) {
        this.interactiveUrl = interactiveUrl;
    }

    /**
     * Gets depends.
     *
     * @return the depends
     */
    public String getDepends() {
        return depends;
    }

    /**
     * Sets depends.
     *
     * @param depends the depends
     */
    public void setDepends(String depends) {
        this.depends = depends;
    }

    /**
     * Gets deploy schema.
     *
     * @return the deploy schema
     */
    public String getDeploySchema() {
        return deploySchema;
    }

    /**
     * Sets deploy schema.
     *
     * @param deploySchema the deploy schema
     */
    public void setDeploySchema(String deploySchema) {
        this.deploySchema = deploySchema;
    }

    /**
     * Gets full invoke url.
     *
     * @param queryJsonStr the query json str
     * @return the full invoke url
     */
    public String getFullInvokeUrl(String queryJsonStr) {

        StringBuffer sb = new StringBuffer();
        sb.append(deploySchema);
        sb.append(deployIP);

        if (!ValueHelper.checkStringIsEmpty(deployPort)) {
            sb.append(":");
            sb.append(deployPort);
        }

        if (!ValueHelper.checkStringIsEmpty(interactiveUrl)) {
            sb.append(interactiveUrl);
        }

        if (!ValueHelper.checkStringIsEmpty(queryJsonStr)) {
            sb.append("/");
            sb.append(queryJsonStr);
        }

        return sb.toString();
    }
}
