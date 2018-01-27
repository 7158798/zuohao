package com.base.common.login.Response;



import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by zh on 16-10-7.
 */
public class UserResponse implements Serializable {
    private static final long serialVersionUID = 3519962197957449562L;

    protected String userNiceName;
    protected Integer userGender;
    protected String userAvatar;
    protected String userSignature;
    protected String userRegion;
    protected String userAccount;
    protected String jsonValue;

    public String getJsonValue() {
        return jsonValue;
    }

    public void setJsonValue(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    public String getUserNiceName() {
        return userNiceName;
    }

    public void setUserNiceName(String userNiceName) {
        this.userNiceName = userNiceName;
    }


    public Integer getUserGender() {
        return userGender;
    }

    public void setUserGender(Integer userGender) {
        this.userGender = userGender;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserSignature() {
        return userSignature;
    }

    public void setUserSignature(String userSignature) {
        this.userSignature = userSignature;
    }

    public String getUserRegion() {
        return userRegion;
    }

    public void setUserRegion(String userRegion) {
        this.userRegion = userRegion;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public UserResponse() {
    }

//    public UserResponse(Response res) {
//    }
//
//    protected static String getString(String name, JSONObject json, boolean decode) {
//        String returnValue = null;
//
//        try {
//            returnValue = json.getString(name);
//            if(decode) {
//                try {
//                    returnValue = URLDecoder.decode(returnValue, "UTF-8");
//                } catch (UnsupportedEncodingException var5) {
//                    ;
//                }
//            }
//        } catch (JSONException var6) {
//            ;
//        }
//
//        return returnValue;
//    }
//
//    protected static int getInt(String key, JSONObject json) throws JSONException {
//        String str = json.getString(key);
//        return null != str && !"".equals(str) && !"null".equals(str)?Integer.parseInt(str):-1;
//    }
}
