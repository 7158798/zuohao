package com.base.common.fileupload.client;

import com.base.common.fileupload.facade.AliyunFileUploadFacade;
import com.base.common.fileupload.impl.AliyunFileUploadFacadeImpl;
import com.base.common.fileupload.impl.request.AliyunOSSReq;
import com.jucaifu.common.property.PropertiesUtils;

/**
 * @author luwei
 * @Date 12/2/16 2:29 PM
 */
public class AliyunFileUploadUtils {

    // 数据访问的点
    public static final String ENDPOINT = PropertiesUtils.getProperty("aliyun.endpoint", "");

    // 主账户key id 、secrent
    private static final String ACCESS_KEY_ID = PropertiesUtils.getProperty("aliyun.access_key_id", "");
    private static final String ACCESS_KEY_SECRET = PropertiesUtils.getProperty("aliyun.access_key_secret", "");

    //ram子账户
    private static final String RAM_ACCESS_KEY_ID = PropertiesUtils.getProperty("aliyun.ram.access_key_id", "");
    private static final String RAM_ACCESS_KEY_SECRET = PropertiesUtils.getProperty("aliyun.ram.access_key_secret", "");
    private static final String RAM_ROLE_ARM = PropertiesUtils.getProperty("aliyun.ram.role_arm", "");


    //bucket名称
    public static final String PUBLIC_BUCKET = PropertiesUtils.getProperty("aliyun.bucket_name_pub", "");
    public static final String PRIVATE_BUCKET = PropertiesUtils.getProperty("aliyun.bucket_name_pri", "");

    //域名
    public static final String PUBLIC_FILE_LOAD_PATH = PropertiesUtils.getProperty("aliyun.obj_load_path_pub", "");
    public static final String PRIVATE_FILE_LOAD_PATH = PropertiesUtils.getProperty("aliyun.obj_load_path_pri", "");


    private static AliyunFileUploadFacade fileUploadFacade;

    public static AliyunFileUploadFacade getFileUploadObject() {
        if(fileUploadFacade != null){
            return fileUploadFacade;
        }else{
            AliyunOSSReq req = new AliyunOSSReq(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET, RAM_ACCESS_KEY_ID, RAM_ACCESS_KEY_SECRET, RAM_ROLE_ARM);
            fileUploadFacade = new AliyunFileUploadFacadeImpl(req);
        }
        return fileUploadFacade;
    }



}
