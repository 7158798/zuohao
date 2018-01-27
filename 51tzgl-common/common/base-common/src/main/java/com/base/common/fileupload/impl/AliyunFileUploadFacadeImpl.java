package com.base.common.fileupload.impl;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.base.common.fileupload.enums.FilePostfixEnum;
import com.base.common.fileupload.facade.AliyunFileUploadFacade;
import com.base.common.fileupload.facade.response.FileUploadResult;
import com.base.common.fileupload.facade.request.AliyunConfigReq;
import com.base.common.fileupload.impl.request.AliyunOSSReq;
import com.jucaifu.common.constants.StringPool;
import com.jucaifu.common.exceptions.BaseException;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.DateHelper;
import com.jucaifu.common.util.JsonHelper;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 阿里云文件上传
 * @author luwei
 * @Date 16-10-23 下午1:30
 */
public class AliyunFileUploadFacadeImpl implements AliyunFileUploadFacade {

    private final String REGION = "cn-shanghai";

    //当前STS API版本
    private final String STS_API_VERSION = "2015-04-01";


    //数据访问点
    private String endpoint;

    //主账户key
    private String accessKeyId;

    //主账户secret
    private String accessKeySecret;

    //只有RAM子账户才能调用AssumeRole接口,  阿里云主账户不能用于发起AssumeRole请求
    //子账户key
    private String ramAccessKeyId;

    //子账户KeySecret
    private String ramAccessKeySecret;

    //角色arm   roleArm从ram控制台获取
    private String ramRoleArm;


    //构造方法赋值
    public AliyunFileUploadFacadeImpl(AliyunOSSReq req) {
        if (req == null) {
            throw new BaseException("参数不能为空");
        }

        LOG.i(AliyunFileUploadFacadeImpl.class, "req:{}", JsonHelper.obj2JsonStr(req));

        if (StringUtils.isNotBlank(req.getEndpoint())) {
            this.endpoint = req.getEndpoint();
        } else {
            throw new BaseException("参数endpoint不能为空");
        }

        if (StringUtils.isNotBlank(req.getAccessKeyId())) {
            this.accessKeyId = req.getAccessKeyId();
        } else {
            throw new BaseException("参数accessKeyId不能为空");
        }

        if (StringUtils.isNotBlank(req.getAccessKeySecret())) {
            this.accessKeySecret = req.getAccessKeySecret();
        } else {
            throw new BaseException("参数accessKeySecret不能为空");
        }

        if (StringUtils.isNotBlank(req.getRamAccessKeyId())) {
            this.ramAccessKeyId = req.getRamAccessKeyId();
        } else {
            throw new BaseException("参数ramAccessKeyId不能为空");
        }


        if (StringUtils.isNotBlank(req.getRamAccessKeySecret())) {
            this.ramAccessKeySecret = req.getRamAccessKeySecret();
        } else {
            throw new BaseException("参数ramAccessKeySecret不能为空");
        }

        if (StringUtils.isNotBlank(req.getRamRoleArm())) {
            this.ramRoleArm = req.getRamRoleArm();
        } else {
            throw new BaseException("参数ramRoleArm不能为空");
        }
    }


    /**
     * 获取文件url
     *
     * @param fileUrl
     * @param configReq 配置子类
     * @return 文件绝对路径
     */

    public String getFileUrl(String fileUrl, AliyunConfigReq configReq) {
        return this.getFileUrl(fileUrl, configReq, Boolean.FALSE);
    }


    /**
     * 获取文件url
     *
     * @param fileUrl
     * @param configReq 配置子类
     * @param isProcess 是否需要处理
     * @return 文件绝对路径
     */
    public String getFileUrl(String fileUrl, AliyunConfigReq configReq, boolean isProcess) {
        String result = StringPool.BLANK;
        try {

            if(StringUtils.isBlank(fileUrl)) {
                return result;
            }

            //不管是那个系统，存储的什么是什么路径，统一格式化成fileKey，既：存储在阿里云上的fileKey，唯一标识符
            fileUrl = getFileKey(fileUrl);

            if (configReq.getAcl() == 0) {  //私有,返回的路径需带签名
                //获取该object的签名信息
                int read_time = 1 * 60 * 60 * 1000; //生成的路径时效1h
                Date expires = new Date(new Date().getTime() + read_time);
                OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
                String fileKey = URLDecoder.decode(fileUrl, "UTF-8");
//                URL url = client.generatePresignedUrl(configReq.getBucketName(), fileKey, expires);
                GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(configReq.getBucketName(), fileKey, HttpMethod.GET);
                req.setExpiration(expires);
                //特殊处理，带文字水印
                if(isProcess && StringUtils.isNotBlank(configReq.getStyle())) {
                    req.setProcess(configReq.getStyle());
                }
                URL url = client.generatePresignedUrl(req);
                result = url.toString();
                client.shutdown();
            } else {  //不带签名
                result = configReq.getFileLoadPath() + "/" + fileUrl;
            }

        } catch (Exception e) {
            LOG.e(AliyunFileUploadFacadeImpl.class, e.getMessage());
        }
        return result;
    }

    /**
     * 根据fileUrl获取存储在阿里云上的文件key
     * @param fileUrl
     * @return
     */
    public String getFileKey(String fileUrl) {
        String result = StringPool.BLANK;
        if(StringUtils.isBlank(fileUrl)) {
            return result;
        }

        result = fileUrl;
        if(fileUrl.indexOf(".aliyuncs.com") == -1 ) { //不是绝对路径，直接返回
            return result;
        }

        //绝对路径则进行截取处理
        result = fileUrl.substring( fileUrl.indexOf(".aliyuncs.com") +14 ,fileUrl.length());
        //部分数据有双斜杠
        if(result.substring(0,1).equals("/")) {
            result = result.substring( 1 ,result.length());
        }
        return result;
    }

    /**
     * 获取显示在页面的文件url
     *
     * @param fileUrl 文件的相对url，即上传后返回的相对路径
     * @return 截取上传时，给出的yyyyMMddHHmmssSSS格式数字
     */
    public String subStrUrl(String fileUrl) throws Exception {
        if (StringUtils.isBlank(fileUrl)) {
            return fileUrl;
        }
        fileUrl = URLDecoder.decode(fileUrl, "UTF-8");
        fileUrl = fileUrl.substring(fileUrl.lastIndexOf("/") + 1, fileUrl.length());
        //将毫秒数去掉17位
        fileUrl = fileUrl.substring(18, fileUrl.length());
        return fileUrl;
    }

    /**
     * 获取显示在页面的文件url
     *
     * @param fileUrl 文件的相对url，即上传后返回的相对路径
     * @param isSubTime 是否截取，即将文件名称上的日期格式化删除
     * @return 截取上传时，给出的yyyyMMddHHmmssSSS格式数字
     */
    public String subStrUrl(String fileUrl, boolean isSubTime) throws Exception {
        if (StringUtils.isBlank(fileUrl)) {
            return fileUrl;
        }
        fileUrl = URLDecoder.decode(fileUrl, "UTF-8");
        fileUrl = fileUrl.substring(fileUrl.lastIndexOf("/") + 1, fileUrl.length());
        //将毫秒数去掉17位
        if(isSubTime) {
            fileUrl = fileUrl.substring(18, fileUrl.length());
        }
        return fileUrl;
    }

    /**
     * 对转码后的文件路径进行解码,还原文件名称
     *
     * @param url
     * @return
     */
    public String decodeUrl(String url) {
        try {
            if (StringUtils.isBlank(url)) {
                return url;
            }
            url = URLDecoder.decode(url, "UTF-8");
        } catch (Exception e) {
            LOG.e(AliyunFileUploadFacadeImpl.class, e.getMessage());
        }
        return url;
    }

    /**
     * 对中文的文件路径进行转码,斜杠不转
     *
     * @param url
     * @return
     */
    public String encodeUrl(String url) {
        try {
            if (StringUtils.isBlank(url)) {
                LOG.i(AliyunFileUploadFacadeImpl.class, "url:{}", url);
                return url;
            }
            String[] key_arr = url.split("/");
            url = "";
            for (int i = 0; i < key_arr.length; i++) {
                if (i == key_arr.length - 1) {
                    url += URLEncoder.encode(key_arr[i], "UTF-8");
                } else {
                    url += URLEncoder.encode(key_arr[i], "UTF-8") + "/";
                }
            }
        } catch (Exception e) {
            LOG.e(AliyunFileUploadFacadeImpl.class, e.getMessage());
        }
        return url;
    }


    /**
     * 文件上传
     *
     * @param file      文件
     * @param configReq 配置子类
     * @return 默认返回的是fileKey
     * 返回文件在阿里云上的key  51投资攻略系统使用
     */
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
            fileName = configReq.buildFileName(fileName, configReq.isCreateFileName);
            if (StringUtils.isBlank(fileName)) {
                return filePath;
            }
            LOG.i(AliyunFileUploadFacadeImpl.class, "fileName:{}", fileName);
            OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

            //设置上传文件的元信息
            ObjectMetadata meta = new ObjectMetadata();
            // 设置上传文件长度
            meta.setContentLength(f.length());
            meta.setContentType(contentType);  //设置上传内容类型，不填则默认是application/octet-stream，通过地址栏访问时，会直接下载文件

            //putObject，第二个参数fileName是指上传到oss后，显示的Object对象文件名称
            //object不存在文件夹的说法，仅以/标识目录层级
            String folder_name = configReq.getFolder();
            filePath = folder_name + "/" + fileName;
            LOG.i(AliyunFileUploadFacadeImpl.class, "filePath:{}", filePath);
            PutObjectResult resultObj = client.putObject(configReq.getBucketName(), filePath, f, meta);
            LOG.i(AliyunFileUploadFacadeImpl.class, "resultObj:{}", JsonHelper.obj2JsonStr(resultObj));
            //操作完成之后关闭client
            client.shutdown();
            //分开转换，目的/不转，区分文件夹
            filePath = encodeUrl(filePath);
            LOG.i(AliyunFileUploadFacadeImpl.class, "filePath:{}", filePath);
        } catch (Exception e) {
            LOG.i(AliyunFileUploadFacadeImpl.class, "exception:{}", e.getMessage());
            LOG.e(AliyunFileUploadFacadeImpl.class, e.getMessage(), e);
        }
        return filePath;
    }


    /**
     * 文件上传
     *
     * @param file             文件
     * @param configReq        配置子类
     * @param isResultAbsolute 是否返回绝对可预览路径
     * @return
     * relativeUrl 阿里云上文件的key； absoluteUrl 可预览的路径，51投资攻略系统使用
     */
    public FileUploadResult fileUpload(CommonsMultipartFile file, AliyunConfigReq configReq, Boolean isResultAbsolute) {
        FileUploadResult fileUploadResult = new FileUploadResult();
        try {
            String relativeUrl = fileUpload(file, configReq);
            fileUploadResult.setRelativeUrl(relativeUrl);  //存储的是文件key 相对路径，不可预览
            if (isResultAbsolute == true) {
                fileUploadResult.setAbsoluteUrl(getFileUrl(relativeUrl, configReq));
            }
        } catch (Exception e) {
            LOG.e(AliyunFileUploadFacadeImpl.class, e.getMessage());
        }
        return fileUploadResult;
    }


    /**
     * 文件上传
     * @param file              文件
     * @param configReq         配置子类
     * @return
     * 返回文件的绝对路径，如果是公共bucket则可直接预览，私有的不可  51数字资产系统使用
     */
    public String fileUpload_szzc(CommonsMultipartFile file, AliyunConfigReq configReq) {
        String relativeUrl = StringPool.BLANK;
        try {
            relativeUrl = fileUpload(file, configReq);
            relativeUrl =  configReq.getFileLoadPath() + "/" + relativeUrl;
        } catch (Exception e) {
            LOG.e(AliyunFileUploadFacadeImpl.class, e.getMessage());
        }
        return relativeUrl;
    }

    /**
     * 文件上传
     * @param file              文件
     * @param configReq         配置子类
     * @param isResultAbsolute  是否返回绝对可预览路径
     * @return
     * relativeUrl 如果是公共bucket则可直接预览，私有的不可； absoluteUrl 可预览的路径  51数字资产系统使用
     */
    public FileUploadResult fileUpload_szzc(CommonsMultipartFile file, AliyunConfigReq configReq, Boolean isResultAbsolute) {
        FileUploadResult fileUploadResult = new FileUploadResult();
        try {
            String relativeUrl = fileUpload(file, configReq);
            relativeUrl =  configReq.getFileLoadPath() + "/" + relativeUrl;
            fileUploadResult.setRelativeUrl(relativeUrl);
            if (isResultAbsolute == true) {
                fileUploadResult.setAbsoluteUrl(getFileUrl(relativeUrl, configReq));
            }
        } catch (Exception e) {
            LOG.e(AliyunFileUploadFacadeImpl.class, e.getMessage());
        }
        return fileUploadResult;
    }


    /**
     * 删除文件，校验出异常时，使用
     *
     * @param file
     * @param configReq
     */
    public void delFile(MultipartFile file, AliyunConfigReq configReq) {
        try {
            if (file == null) {
                throw new BaseException("删除文件时,文件对象不能为空");
            }
            CommonsMultipartFile cf = (CommonsMultipartFile) file;
            DiskFileItem fi = (DiskFileItem) cf.getFileItem();

            File f = fi.getStoreLocation();
            String fileName = fi.getName();
            String timeStr = DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond_MESC);
            fileName = timeStr + "_" + fileName;
            OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

            String filePath = configReq.getFolder() + "/" + fileName;

            //删除文件信息
            client.deleteObject(configReq.getBucketName(), filePath);
            //操作完成之后关闭client
            client.shutdown();
        } catch (Exception e) {
            LOG.e(AliyunFileUploadFacadeImpl.class, e.getMessage());
        }
    }

    /**
     * 根据文件名删除文件，主要用于删除业务数据时，使用
     * fileName是带%格式的字符
     *
     * @param filePath
     */
    public void delFile(String filePath, AliyunConfigReq configReq) {
        try {

            //为兼容filePath是绝对路径，进行处理
            filePath = getFileKey(filePath);

            if (StringUtils.isBlank(filePath)) {
                throw new BaseException("删除文件时,文件名称不能为空");
            }
            OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

            //删除文件信息
            filePath = URLDecoder.decode(filePath, "UTF-8");
            client.deleteObject(configReq.getBucketName(), filePath);
            //操作完成之后关闭client
            client.shutdown();
        } catch (Exception e) {
            LOG.e(AliyunFileUploadFacadeImpl.class, e.getMessage());
        }
    }

    /**
     * 文件下载，获取文件流
     * @param filePath
     * @param configReq
     * @return
     */
    public byte[] getFileInputStream(String filePath, AliyunConfigReq configReq) {
        byte[] documentBody = null;
        try {

            //为兼容filePath是绝对路径，进行处理
            filePath = getFileKey(filePath);

            if (StringUtils.isBlank(filePath)) {
                throw new BaseException("文件下载时,文件名称不能为空");
            }

            //解码
            filePath = decodeUrl(filePath);

            OSSClient client  = new OSSClient(endpoint, accessKeyId, accessKeySecret);

            // 下载Object
            OSSObject ossObject = client.getObject(configReq.getBucketName(), filePath);

            InputStream inputStream = ossObject.getObjectContent();
            documentBody = IOUtils.toByteArray(inputStream);
            inputStream.close();
            client.shutdown();
        } catch (Exception e) {
            LOG.e(AliyunFileUploadFacadeImpl.class, e.getMessage());
        }

        return documentBody;
    }


    /**
     * 读取文件每行信息
     *
     * @param filePath
     */
    public Set<String> readFileLine(String filePath, AliyunConfigReq configReq) {

        Set<String> set = new HashSet<>();
        try {

            //为兼容filePath是绝对路径，进行处理
            filePath = getFileKey(filePath);

            if (StringUtils.isBlank(filePath)) {
                throw new BaseException("文件下载时,文件名称不能为空");
            }

            //解码
            filePath = decodeUrl(filePath);

            OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

            // 下载Object
            OSSObject ossObject = client.getObject(configReq.getBucketName(), filePath);

            InputStream contentFile = ossObject.getObjectContent();
            if (contentFile != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(contentFile));
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    set.add(line);
                }
                reader.close();
            }
            contentFile.close();
            //操作完成之后关闭client
            client.shutdown();
        } catch (Exception e) {
            LOG.e(AliyunFileUploadFacadeImpl.class, e.getMessage());
        }
        return set;
    }


    /************
     * STS临时授权访问
     ********************/

    private AssumeRoleResponse assumeRole(String ram_access_key_id, String ram_access_key_secret, String roleArn, String roleSessionName, String policy, ProtocolType protocolType) {
        try {
            //创建一个aliyun acs client，用于发送OpenApi请求
            IClientProfile profile = DefaultProfile.getProfile(REGION, ram_access_key_id, ram_access_key_secret);
            DefaultAcsClient client = new DefaultAcsClient(profile);

            //创建一个AssumeRoleRequest 并设置请求参数
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setVersion(STS_API_VERSION);
            request.setMethod(MethodType.POST);
            request.setProtocol(protocolType);

            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy);

            //发起请求，并得到response
            final AssumeRoleResponse response = client.getAcsResponse(request);

            return response;
        } catch (Exception e) {
            LOG.e(AliyunFileUploadFacadeImpl.class, e.getMessage());
        }

        return null;
    }

    public AssumeRoleResponse getStsAssume() {

        //roleSessionName是临时token的会话名称，自己指定用于标识你的用户id，主要用于审计，或者区分token颁发给谁
        //注意RoleSessionName的长度和规则，不要有空格，只能有'-' '_' 字母和数字等字符
        String roleSessionName = "t-001";

        //定制policy
        String policy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                //"                \"oss:GetBucket\", \n" +
                //"                \"oss:putObject\", \n" +
                "                \"oss:*\" \n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:oss:*:*:*\"\n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        //此处必须为htts
        ProtocolType protocolType = ProtocolType.HTTPS;
        final AssumeRoleResponse response = assumeRole(ramAccessKeyId, ramAccessKeySecret, ramRoleArm, roleSessionName, policy, protocolType);
        if (response != null) {
            LOG.i(AliyunFileUploadFacadeImpl.class, "response:{}", JsonHelper.obj2JsonStr(response));
        } else {
            LOG.i(AliyunFileUploadFacadeImpl.class, "response 为空");
        }
        return response;

    }

}
