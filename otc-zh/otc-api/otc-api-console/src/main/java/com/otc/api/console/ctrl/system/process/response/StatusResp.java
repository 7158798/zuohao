package com.otc.api.console.ctrl.system.process.response;

import java.util.Map;

/**
 * Created by fenggq on 17-5-23.
 */
public class StatusResp {
    private Map<String,String> statusMap;

    public Map<String, String> getStatusMap() {
        return statusMap;
    }

    public void setStatusMap(Map<String, String> statusMap) {
        this.statusMap = statusMap;
    }
}
