package com.base.common.fileupload.client;

import com.base.common.fileupload.facade.AliyunFileUploadFacade;
import com.base.common.fileupload.impl.AzureFileUploadFacadeImpl;
import com.base.common.fileupload.impl.request.AliyunOSSReq;
import com.jucaifu.common.property.PropertiesUtils;

/**
 * Created by luwei on 17-6-20.
 */
public class AzureFileUploadUtils {

    //服务帐号的名称
    private static final String accountName = PropertiesUtils.getProperty("azure.file.accountName", "");

    //服务帐号的key
    private static final String accountKey = PropertiesUtils.getProperty("azure.file.accountKey", "");


    public static AliyunFileUploadFacade azureFileUploadFacade;

    public static AliyunFileUploadFacade getFileUploadObject() {
        if(azureFileUploadFacade != null) {
            return azureFileUploadFacade;
        }
        AliyunOSSReq ossReq = new AliyunOSSReq(accountName, accountKey);

        azureFileUploadFacade = new AzureFileUploadFacadeImpl(ossReq);
        return azureFileUploadFacade;
    }


}
