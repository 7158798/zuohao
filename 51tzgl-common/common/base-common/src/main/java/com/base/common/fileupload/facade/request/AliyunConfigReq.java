package com.base.common.fileupload.facade.request;

import com.jucaifu.common.constants.StringPool;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.DateHelper;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author luwei
 * @Date 12/2/16 11:34 AM
 */
public class AliyunConfigReq {


     public String folder = "";

     public String bucketName = "";

     //权限 0私有  1公共读   2公共读写
     public int acl = 0;

     //读取文件域(阿里云有， 微软云没有)
     public String fileLoadPath = "";

     //是否创建随机的新文件名称
     public boolean isCreateFileName;

     //是否带图片样式处理
     public String style;

     public AliyunConfigReq(String folder, String bucketName, int acl, String fileLoadPath) {
          this.folder = folder;
          this.bucketName = bucketName;
          this.acl = acl;
          this.fileLoadPath = fileLoadPath;
     }

     public AliyunConfigReq(String folder, String bucketName, int acl, String fileLoadPath, boolean isCreateFileName) {
          this.folder = folder;
          this.bucketName = bucketName;
          this.acl = acl;
          this.fileLoadPath = fileLoadPath;
          this.isCreateFileName = isCreateFileName;
     }

     public AliyunConfigReq(String folder, String bucketName, int acl, String fileLoadPath, boolean isCreateFileName, String style) {
          this.folder = folder;
          this.bucketName = bucketName;
          this.acl = acl;
          this.fileLoadPath = fileLoadPath;
          this.isCreateFileName = isCreateFileName;
          this.style = style;
     }

     /************* get()、set() ************************/


     public String getFolder() {
          return folder;
     }

     public void setFolder(String folder) {
          this.folder = folder;
     }

     public String getBucketName() {
          return bucketName;
     }

     public void setBucketName(String bucketName) {
          this.bucketName = bucketName;
     }

     public int getAcl() {
          return acl;
     }

     public void setAcl(int acl) {
          this.acl = acl;
     }

     public String getFileLoadPath() {
          return fileLoadPath;
     }

     public void setFileLoadPath(String fileLoadPath) {
          this.fileLoadPath = fileLoadPath;
     }


     public boolean isCreateFileName() {
          return isCreateFileName;
     }

     public void setCreateFileName(boolean createFileName) {
          isCreateFileName = createFileName;
     }

     public String getStyle() {
          return style;
     }

     public void setStyle(String style) {
          this.style = style;
     }

     /**
      * 生成随机的文件名称
      * @param ext  文件后缀
      * @return
      */
     public String createFileName(String ext) {
          if(StringUtils.isBlank(ext)) {
               LOG.i(AliyunConfigReq.class, "生成随机的文件名称，后缀为空");
               return null;
          }
          String fileName =  getRandomImageName()+"."+ext; //随机生成的文件名称
          return fileName;
     }

     //时间格式化，生产新的名称
     public String getRandomImageName() {
          SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                  "yyyyMMddHHmmssSSS");
          String path = simpleDateFormat.format(new Date());
          path += "_" + randomString(5);
          return path;
     }

     // 获得随机字符串
     public  String randomString(int count) {
          String str = "abcdefghigklmnopkrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
          int size = str.length();
          StringBuffer sb = new StringBuffer();
          Random random = new Random();
          while (count > 0) {
               sb.append(String.valueOf(str.charAt(random.nextInt(size))));
               count--;
          }
          return sb.toString();
     }

     //根据需求 拼接文件名称
     public String buildFileName(String fileName, boolean isCreateFileName) {
          if (StringUtils.isBlank(fileName)) {
               return StringPool.BLANK;
          } else {  //判断是否带斜杠
               if (isCreateFileName) {  //生成新的文件名称
                    String[] nameSplit = fileName.split("\\.");
                    String ext = nameSplit[nameSplit.length - 1];
                    fileName = createFileName(ext);
               } else {  //不生成文件名称，则使用原始的
                    int index = fileName.lastIndexOf("/");
                    if (index != -1) {
                         fileName = fileName.substring(index + 1);
                    }

                    String timeStr = DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond_MESC);
                    fileName = timeStr + "_" + fileName;
               }
          }
          return fileName;
     }
}
