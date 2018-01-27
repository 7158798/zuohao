package com.base.common.login.bean;


import com.base.common.login.Response.UserResponse;
import com.base.common.login.exception.ConnectException;
import fr.opensagres.xdocreport.document.json.JSONException;
import fr.opensagres.xdocreport.document.json.JSONObject;

import java.io.Serializable;

/**
 * Created by zh on 16-10-8.
 */
public class QQUserInfoBean extends UserResponse implements Serializable {
    private static final long serialVersionUID = 5606709876246698659L;
    private Avatar avatar = new Avatar("");
    private String nickname;
    private String gender;
    private boolean vip;
    private int level;
    private boolean yellowYearVip;
    private int ret;
    private String msg;

    public String getNickname() {
        return this.nickname;
    }

    public String getGender() {
        return this.gender;
    }

    public boolean isVip() {
        return this.vip;
    }

    public Avatar getAvatar() {
        return this.avatar;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isYellowYearVip() {
        return this.yellowYearVip;
    }

    public int getRet() {
        return this.ret;
    }

    public String getMsg() {
        return this.msg;
    }

    public QQUserInfoBean(JSONObject json) throws ConnectException {
        this.init(json);
    }

    private void init(JSONObject json) throws ConnectException {
        if(json != null) {
            try {
                this.ret = json.getInt("ret");
                if(0 != this.ret) {
                    this.msg = json.getString("msg");
                } else {
                    this.msg = "";
                    this.nickname = json.getString("nickname");
                    this.gender = json.getString("gender");
                    this.vip = json.getInt("vip") == 1;
                    this.avatar = new Avatar(json.getString("figureurl"), json.getString("figureurl_1"), json.getString("figureurl_2"));
                    this.level = json.getInt("level");
                    this.yellowYearVip = json.getInt("is_yellow_year_vip") == 1;
                }
            } catch (JSONException var3) {
                throw new ConnectException(var3.getMessage() + ":" + json.toString(), var3);
            }
        }

    }

    public int hashCode() {
        boolean prime = true;
        byte result = 1;
        int result1 = 31 * result + (this.nickname == null?0:this.nickname.hashCode());
        return result1;
    }


    public static void main(String args[]){
        String s = "{\"access_token\":\"2.00Ms396GL9XHNE834f0ebf23nyNDUB\",\n" +
                "\"remind_in\":\"157679999\",\"expires_in\":157679999,\n" +
                "\"uid\":\"5872966504\",\"scope\":\n" +
                "\"follow_app_official_microblog\"}";
        JSONObject jonson = new JSONObject(s);
        System.out.print(jonson.getString("access_token"));
    }

    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } else if(obj == null) {
            return false;
        } else if(this.getClass() != obj.getClass()) {
            return false;
        } else {
            QQUserInfoBean other = (QQUserInfoBean)obj;
            if(this.nickname == null) {
                if(other.nickname != null) {
                    return false;
                }
            } else if(!this.nickname.equals(other.nickname)) {
                return false;
            }

            return true;
        }
    }

    public String toString() {
        return "UserInfo [nickname : " + this.nickname + " , " + "figureurl30 : " + this.avatar.getAvatarURL30() + " , " + "figureurl50 : " + this.avatar.getAvatarURL50() + " , " + "figureurl100 : " + this.avatar.getAvatarURL100() + " , " + "gender : " + this.gender + " , " + "vip : " + this.vip + " , " + "level : " + this.level + " , " + "isYellowYeaarVip : " + this.yellowYearVip + "]";
    }
}
