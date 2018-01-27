package com.szzc.common.api.packet.web;

import org.springframework.http.HttpStatus;

/**
 * ApiResponse | 返回没有异常情况下的报文。
 *
 * @param <T> the type parameter
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/7.
 */
public class WebApiResponse<T> {

    private String code;
    private String message;
    private T body;


    /**
     * Instantiates a new Web api response.
     */
    private WebApiResponse() {
    }

    /**
     * Instantiates a new Web api response.
     *
     * @param message the message
     * @param body    the body
     */
    public WebApiResponse(String message, T body) {
        this.code = HttpStatus.OK.toString();
        this.message = message;
        this.body = body;
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
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
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
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets body.
     *
     * @return the body
     */
    public T getBody() {
        return body;
    }

    /**
     * Sets body.
     *
     * @param body the body
     */
    public void setBody(T body) {
        this.body = body;
    }
}
