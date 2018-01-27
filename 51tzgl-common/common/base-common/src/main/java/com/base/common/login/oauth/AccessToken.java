package com.base.common.login.oauth;

import java.io.Serializable;

/**
 * Created by zh on 16-10-9.
 */
public  class AccessToken implements Serializable {
    private static final long serialVersionUID = -5499186506535919974L;
    private String accessToken = "";
    private String expireIn = "";
    private String refreshToken = "";
    private String openid;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setExpireIn(String expireIn) {
        this.expireIn = expireIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public AccessToken() {
    }

    public AccessToken(String[] fileds){
        this.accessToken = fileds[0];
        this.expireIn = fileds[1];
        this.refreshToken = fileds[2];
    }

    public AccessToken(String accessToken,String expireIn,String refreshToken){
        this.accessToken = accessToken;
        this.expireIn = expireIn;
        this.refreshToken = refreshToken;
    }

    public AccessToken(String accessToken,String expireIn){
        this.accessToken = accessToken;
        this.expireIn = expireIn;
    }



    public String getAccessToken() {
        return this.accessToken;
    }

    public long getExpireIn() {
        return Long.valueOf(this.expireIn).longValue();
    }

    public int hashCode() {
        boolean prime = true;
        byte result = 1;
        int result1 = 31 * result + (this.accessToken == null?0:this.accessToken.hashCode());
        result1 = 31 * result1 + (this.expireIn == null?0:this.expireIn.hashCode());
        result1 = 31 * result1 + (this.refreshToken == null?0:this.refreshToken.hashCode());
        return result1;
    }

    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } else if(obj == null) {
            return false;
        } else if(this.getClass() != obj.getClass()) {
            return false;
        } else {
            AccessToken other = (AccessToken)obj;
            if(this.accessToken == null) {
                if(other.accessToken != null) {
                    return false;
                }
            } else if(!this.accessToken.equals(other.accessToken)) {
                return false;
            }

            if(this.expireIn == null) {
                if(other.expireIn != null) {
                    return false;
                }
            } else if(!this.expireIn.equals(other.expireIn)) {
                return false;
            }

            if(this.refreshToken == null) {
                if(other.refreshToken != null) {
                    return false;
                }
            } else if(!this.refreshToken.equals(other.refreshToken)) {
                return false;
            }

            return true;
        }
    }

    public String toString() {
        return "AccessToken [accessToken=" + this.accessToken + ", expireIn=" + this.expireIn + "]";
    }
}
