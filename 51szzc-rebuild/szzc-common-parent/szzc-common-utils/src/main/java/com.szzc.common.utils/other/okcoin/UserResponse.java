package com.szzc.common.utils.other.okcoin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lx on 17-2-15.
 */
public class UserResponse {

    private Boolean result;

    private Map info = new HashMap();

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Map getInfo() {
        return info;
    }

    public void setInfo(Map info) {
        this.info = info;
    }
}
