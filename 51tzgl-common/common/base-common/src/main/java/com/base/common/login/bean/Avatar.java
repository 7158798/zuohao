package com.base.common.login.bean;

import java.io.Serializable;

/**
 * Created by zh on 16-10-8.
 */
public class Avatar implements Serializable {
    private static final long serialVersionUID = -8402565179459840811L;
    private String avatarURL30 = "";
    private String avatarURL50 = "";
    private String avatarURL100 = "";

    public Avatar(String avatarURL) {
        if(!avatarURL.equals("")) {
            this.avatarURL30 = avatarURL + "/30";
            this.avatarURL50 = avatarURL + "/50";
            this.avatarURL100 = avatarURL + "/100";
        }
    }

    public Avatar(String avatarURL30, String avatarURL50, String avatarURL100) {
        this.avatarURL30 = avatarURL30;
        this.avatarURL50 = avatarURL50;
        this.avatarURL100 = avatarURL100;
    }

    public String getAvatarURL30() {
        return this.avatarURL30;
    }

    public String getAvatarURL50() {
        return this.avatarURL50;
    }

    public String getAvatarURL100() {
        return this.avatarURL100;
    }

    public int hashCode() {
        boolean prime = true;
        byte result = 1;
        int result1 = 31 * result + (this.avatarURL30 == null?0:this.avatarURL30.hashCode());
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
            Avatar other = (Avatar)obj;
            if(this.getAvatarURL30() == null) {
                if(other.getAvatarURL30() != null) {
                    return false;
                }
            } else if(!this.getAvatarURL30().equals(other.getAvatarURL30())) {
                return false;
            }

            return true;
        }
    }

    public String toString() {
        return "AvatarInfo [figureurl30 : " + this.getAvatarURL30() + " , " + "figureurl50 : " + this.getAvatarURL50() + " , " + "figureurl100 : " + this.getAvatarURL100() + " , " + "]";
    }
}
