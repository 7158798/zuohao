package com.base.common.fileupload.enums;

import com.jucaifu.common.log.LOG;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 文件后缀枚举(后缀大写)
 * 注意：postfix第一个字符不能是点，多个后缀以点号分割
 * Created by luwei on 17-7-10.
 */
public enum FilePostfixEnum {


    IMG("img", "PNG.JPG.JPEG.BMP"),
    IMG2("img", "PNG.JPG"),
    IMG3("img", "PNG.JPG.GIF");


    private String name;

    private String postfix;

    FilePostfixEnum(String name, String postfix) {
        this.name = name;
        this.postfix = postfix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }


    public boolean checkFileType(CommonsMultipartFile file, FilePostfixEnum postfixEnum) {
        boolean flag = false;

        if(file == null || postfixEnum == null) {
            return flag;
        }

        try {
            String fileRealName = file.getOriginalFilename();
            String[] nameSplit = fileRealName.split("\\.");
            String ext = nameSplit[nameSplit.length - 1];
            if(StringUtils.isBlank(ext)) {
                return flag;
            }
            ext = ext.trim().toUpperCase();
            String[] postfix_arr = postfixEnum.getPostfix().split("\\.");
            for(int i=0; i<postfix_arr.length; i++) {
                if(ext.equals(postfix_arr[i])) {
                    flag = true;
                    break;
                }
            }

        } catch (Exception e) {
            LOG.e(this, "校验文件后缀格式异常", e);
            return flag;
        }

        return flag;
    }
}
