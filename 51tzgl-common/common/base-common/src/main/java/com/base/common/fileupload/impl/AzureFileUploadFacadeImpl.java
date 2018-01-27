package com.base.common.fileupload.impl;

import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.base.common.fileupload.enums.FilePostfixEnum;
import com.base.common.fileupload.facade.AliyunFileUploadFacade;
import com.base.common.fileupload.facade.request.AliyunConfigReq;
import com.base.common.fileupload.facade.response.FileUploadResult;
import com.base.common.fileupload.impl.request.AliyunOSSReq;
import com.jucaifu.common.exceptions.BaseException;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.DateHelper;
import com.jucaifu.common.util.JsonHelper;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Set;

/**
 * 微软云文件上传
 * Created by luwei on 17-6-19.
 */
public class AzureFileUploadFacadeImpl implements AliyunFileUploadFacade{

    private String storageConnectionString = null;

    public AzureFileUploadFacadeImpl(AliyunOSSReq req) {
        if(req == null) {
            throw new BaseException("参数不能为空");
        }

        LOG.i(AzureFileUploadFacadeImpl.class, "req:{}", JsonHelper.obj2JsonStr(req));

        if(StringUtils.isBlank(req.getAccessKeyId())) {
            throw new BaseException("参数accessKeyId不能为空");
        }

        if(StringUtils.isBlank(req.getAccessKeySecret())) {
            throw new BaseException("参数accountKeySecret不能为空");
        }

        storageConnectionString = "DefaultEndpointsProtocol=https;" +
                "AccountName=" + req.getAccessKeyId() + ";" +
                "AccountKey=" + req.getAccessKeySecret() + ";" +
                "EndpointSuffix=core.windows.net";

    }

    @Override
    public String getFileUrl(String fileUrl, AliyunConfigReq configReq) {
        return null;
    }


    @Override
    public String getFileUrl(String fileUrl, AliyunConfigReq configReq, boolean isProcess) {
        return null;
    }

    @Override
    public String subStrUrl(String fileUrl) throws Exception {
        return null;
    }

    @Override
    public String subStrUrl(String fileUrl, boolean isSubTime) throws Exception {
        return null;
    }

    @Override
    public String decodeUrl(String url) {
        return null;
    }

    @Override
    public String encodeUrl(String url) {
        return null;
    }

    //文件上传  如果是公有bucket则是公共可访问的绝对路径
    @Override
    public String fileUpload(CommonsMultipartFile file, AliyunConfigReq configReq) {
        String filePath = "";
        try {
            if (file == null) {
                throw new BaseException("文件上传时,文件对象不能为空");
            }
            DiskFileItem fi = (DiskFileItem) file.getFileItem();
            String contentType = file.getContentType();

            File f = fi.getStoreLocation();
            //判断文件的格式，采用不同的对象读取,不同的浏览器，获取的名称不同，如火狐，返回/fileName.jpg，是路径+文件名
            String fileName = fi.getName();
            configReq.buildFileName(fileName, configReq.isCreateFileName);
            if (StringUtils.isBlank(fileName)) {
                return filePath;
            }
            LOG.i(AzureFileUploadFacadeImpl.class, "fileName:{}", fileName);

            //连接到Azure 存储帐号
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
            //创建blob client
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            //获取blob下的容器(目录)
            CloudBlobContainer container = blobClient.getContainerReference(configReq.getBucketName());
            //上传图片
            String blobName = configReq.getFolder()+"/"+fileName;
            CloudBlockBlob blob = container.getBlockBlobReference(blobName);
            blob.upload(new FileInputStream(f), f.length());

            filePath = blob.getUri().toString();
            LOG.i(AzureFileUploadFacadeImpl.class, "filePath:{}", filePath);
        } catch (Exception e) {
            LOG.e(AzureFileUploadFacadeImpl.class, e.getMessage(), e);
        }
        return filePath;
    }

    @Override
    public FileUploadResult fileUpload(CommonsMultipartFile file, AliyunConfigReq configReq, Boolean isResultAbsolute) {
        return null;
    }

    @Override
    public String fileUpload_szzc(CommonsMultipartFile file, AliyunConfigReq configReq) {
        return null;
    }

    @Override
    public FileUploadResult fileUpload_szzc(CommonsMultipartFile file, AliyunConfigReq configReq, Boolean isResultAbsolute) {
        return null;
    }

    @Override
    public void delFile(MultipartFile file, AliyunConfigReq configReq) {

    }

    @Override
    public void delFile(String filePath, AliyunConfigReq configReq) {

    }

    @Override
    public byte[] getFileInputStream(String filePath, AliyunConfigReq configReq) {
        return new byte[0];
    }

    @Override
    public Set<String> readFileLine(String filePath, AliyunConfigReq configReq) {
        return null;
    }

    @Override
    public AssumeRoleResponse getStsAssume() {
        return null;
    }
}
