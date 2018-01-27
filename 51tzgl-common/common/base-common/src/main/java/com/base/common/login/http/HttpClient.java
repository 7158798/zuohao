package com.base.common.login.http;

/**
 * Created by zh on 16-10-8.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.base.common.login.exception.ConnectException;
import com.jucaifu.common.network.HttpClientHelper;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;


public class HttpClient implements Serializable {
    private static final long serialVersionUID = 1458439729090743687L;

    private String token;
    private String openID;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);

    public void setOpenID(String openID) {
        this.openID = openID;
    }

    public String getOpenID() throws ConnectException {
        if(this.openID != null && !this.openID.equals("")) {
            return this.openID;
        } else {
            throw new ConnectException("please invoke the setOpenID and setToken first!");
        }
    }

    public String setToken(String token) {
        this.token = token;
        return this.token;
    }

    public String getToken() throws ConnectException {
        if(this.token != null && !this.token.equals("")) {
            return this.token;
        } else {
            throw new ConnectException("please invoke the setToken first !");
        }
    }

    public String post(String url,Map<String,String> params) throws ConnectException{
        String result = "" ;
        try{
            result =  HttpClientHelper.post(url,params);
        }catch (Exception e){
            throw new ConnectException("please invoke the setOpenID and setToken first!");
        }
        return result;
    }

    public String get(String url) {
        return HttpClientHelper.sendGetRequest(url,false);
    }
}
