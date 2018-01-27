package com.base.common.email.impl.request;

import com.base.common.email.facade.request.EmailParamBean;
import com.base.common.email.facade.request.SendEmailReq;

/**
 * Created by luwei on 17-6-16.
 */
public class AzureEmailParamBean extends EmailParamBean{

    private String apiKey;

    public AzureEmailParamBean(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
