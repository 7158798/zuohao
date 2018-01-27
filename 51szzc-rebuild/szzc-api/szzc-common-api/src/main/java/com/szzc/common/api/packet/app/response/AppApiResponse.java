package com.szzc.common.api.packet.app.response;

/**
 * AppApiResponse
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/19.
 */
public class AppApiResponse {

    private AppResponseHeader header;
    private String code;
    private String message;
    private String body;
    private String sign;

    /**
     * Build for app file upload.
     *
     * @param code    the code
     * @param message the message
     * @return the app api response
     */
    public static AppApiResponse buildForAppFileUpload(String code, String message, String body) {
        AppApiResponse apiResponse = new AppApiResponse();
        apiResponse.setCode(code);
        apiResponse.setMessage(message);
        apiResponse.setBody(body);
        return apiResponse;
    }

    /**
     * Gets header.
     *
     * @return the header
     */
    public AppResponseHeader getHeader() {
        return header;
    }

    /**
     * Sets header.
     *
     * @param header the header
     */
    public void setHeader(AppResponseHeader header) {
        this.header = header;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
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
}
