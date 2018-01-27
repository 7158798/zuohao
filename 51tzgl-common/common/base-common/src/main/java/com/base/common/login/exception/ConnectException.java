package com.base.common.login.exception;



/**
 * Created by zh on 16-10-7.
 */
public class ConnectException extends Exception{
    private int statusCode = -1;
    private static final long serialVersionUID = -2623309261327598087L;

    public ConnectException(String msg) {
        super(msg);
    }

    public ConnectException(Exception cause) {
        super(cause);
    }

    public ConnectException(String msg, int statusCode)  {
        super(msg);
        this.statusCode = statusCode;
    }


    public ConnectException(String msg, Exception cause) {
        super(msg, cause);
    }

    public ConnectException(String msg, Exception cause, int statusCode) {
        super(msg, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}

