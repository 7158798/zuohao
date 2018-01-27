package com.otc.facade.utils.enums;

import com.base.common.fileupload.facade.request.AliyunConfigReq;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by luwei on 17-6-20.
 */
public enum  FileUploadType {

    KYC("01", "kyc认证", new AliyunConfigReq("user/kyc", "otcsecret", 0, "", true));

    private String code;

    private String name;

    private AliyunConfigReq configReq;

    FileUploadType(String code, String name, AliyunConfigReq configReq) {
        this.code = code;
        this.name = name;
        this.configReq = configReq;
    }



    public static FileUploadType getObjectByCode(String code){
        for (FileUploadType value : FileUploadType.values()){
            if (StringUtils.equals(code, value.code)){
                return  value;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AliyunConfigReq getConfigReq() {
        return configReq;
    }

    public void setConfigReq(AliyunConfigReq configReq) {
        this.configReq = configReq;
    }

}
