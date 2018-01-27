package com.otc.common.api.packet.app.request;

/**
 * AppApiRequest
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/19.
 */
public class AppApiRequest {

    private AppRequestHeader header;
    private String body;
    private String sign;

    private Object bodyObj;
    private Boolean validateSignResult;

    /**
     * Gets header.
     *
     * @return the header
     */
    public AppRequestHeader getHeader() {
        return header;
    }

    /**
     * Sets header.
     *
     * @param header the header
     */
    public void setHeader(AppRequestHeader header) {
        this.header = header;
    }

    /**
     * Gets body.
     *
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets body.
     *
     * @param body the body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Gets sign.
     *
     * @return the sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * Sets sign.
     *
     * @param sign the sign
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * Gets body obj.
     *
     * @return the body obj
     */
    public <T> T getBodyObj() {
        return (T) bodyObj;
    }

    /**
     * Sets body obj.
     *
     * @param bodyObj the body obj
     */
    public void setBodyObj(Object bodyObj) {
        this.bodyObj = bodyObj;
    }

    /**
     * Gets validate sign result.
     *
     * @return the validate sign result
     */
    public Boolean getValidateSignResult() {
        return validateSignResult;
    }

    /**
     * Sets validate sign result.
     *
     * @param validateSignResult the validate sign result
     */
    public void setValidateSignResult(Boolean validateSignResult) {
        this.validateSignResult = validateSignResult;
    }

    /**
     * Fetch device type.
     *
     * @return the string
     */
    public String fetchDeviceType() {
        String deviceType = null;
        try {
            deviceType = header.getDeviceInfo().getDeviceType();
        } catch (Exception e) {
        }

        return deviceType;
    }

    /**
     * Fetch token.
     *
     * @return the string
     */
    public String fetchToken() {
        String token = null;
        try {
            token = header.getToken();
        } catch (Exception e) {
        }
        return token;
    }
}
