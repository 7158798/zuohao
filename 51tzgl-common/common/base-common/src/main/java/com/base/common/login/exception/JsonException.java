package com.base.common.login.exception;

import fr.opensagres.xdocreport.document.json.JSONException;
import fr.opensagres.xdocreport.document.json.JSONObject;

/**
 * Created by zh on 16-10-10.
 */
public class JsonException extends Exception {
    private int statusCode = -1;
    private int errorCode = -1;
    private String request;
    private String error;
    private static final long serialVersionUID = -2623309261327598087L;

    public JsonException(String msg) {
        super(msg);
    }

    public JsonException(Exception cause) {
        super(cause);
    }

    public JsonException(String msg , int statusCode) throws JSONException {
        super(msg);
        this.statusCode = statusCode;
    }

    public JsonException(String msg , JSONObject json, int statusCode) throws JSONException {
        super(msg + "\n error:" + json.getString("error") +" error_code:" + json.getInt("error_code") + json.getString("request"));
        this.statusCode = statusCode;
        this.errorCode = json.getInt("error_code");
        this.error = json.getString("error");
        this.request = json.getString("request");

    }

    public JsonException(String msg, Exception cause) {
        super(msg, cause);
    }

    public JsonException(String msg, Exception cause, int statusCode) {
        super(msg, cause);
        this.statusCode = statusCode;

    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getRequest() {
        return request;
    }

    public String getError() {
        return error;
    }

}