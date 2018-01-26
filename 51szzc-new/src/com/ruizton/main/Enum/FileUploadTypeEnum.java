package com.ruizton.main.Enum;

import org.apache.commons.lang.StringUtils;

/**
 * Created by luwei on 17-2-28.
 */
public enum FileUploadTypeEnum {

    VIDEO_FIRST_IMG("1", "视频封面", "upload/firstimg"),

    VIDEO("2", "视频", "upload/video"),

    INFOMATION_TXT("3", "资讯标签文档", "upload/informationTxt"),

    BANNER("4", "banner", "upload/banner"),

    AVATAR("5", "头像", "upload/avatar"),

    KYC_IDEBTITY_IMG("6","kyc认证图片","upload/kcyImage"),

    KYC_IDEBTITY_VIDEO("7","kyc认证视频","upload/kcyImage");



    private  String code;

    private String name;

    //上传文件的oss路径
    private String folderName;

    FileUploadTypeEnum(String code, String name, String folderName) {
        this.code = code;
        this.name = name;
        this.folderName = folderName;
    }


    public static FileUploadTypeEnum getObjectByCode(String code){
        for (FileUploadTypeEnum value : FileUploadTypeEnum.values()){
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

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
