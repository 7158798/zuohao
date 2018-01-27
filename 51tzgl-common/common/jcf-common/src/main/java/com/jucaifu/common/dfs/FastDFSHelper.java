package com.jucaifu.common.dfs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.StreamHelper;
import org.springframework.web.multipart.MultipartFile;

/**
 * FastDFSHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/4.
 */
public class FastDFSHelper {


    /**
     * Upload file.
     *
     * @param file     the file
     * @param fileName the file name
     * @return the dFS file po
     */
    public static DFSFilePo uploadFile(File file, String fileName) {

        DFSFilePo po = null;
        
        String fileId = FastDFSClient.uploadFile(file, fileName);

        if (fileId != null) {
            po = new DFSFilePo();
            po.setFileId(fileId);
            po.setHost(FastDFSClient.getFastdfsHost());
        }

        return po;
    }

    /**
     * Upload file.
     *
     * @param inStream the in stream
     * @param fileName the file name
     * @return the dFS file po
     */
    public static DFSFilePo uploadFile(InputStream inStream, String fileName) {

        DFSFilePo po = null;

        String fileId = null;

        try {
            byte[] bytesFile = StreamHelper.readStreamToBytes(inStream);
            fileId = FastDFSClient.uploadFileByBytes(bytesFile, fileName);
        } catch (IOException e) {
            LOG.e("", "uploadFile", e);
        }

        if (fileId != null) {
            po = new DFSFilePo();
            po.setFileId(fileId);
            po.setHost(FastDFSClient.getFastdfsHost());
        }

        return po;
    }

    /**
     * 上传文件.
     *
     * @param file the file
     * @return the string
     */
    public static DFSFilePo upload(MultipartFile file) {

        DFSFilePo po = null;

        String fileId = null;

        try {
            String originalFileName = file.getOriginalFilename();
            LOG.d(FastDFSHelper.class, originalFileName);
            InputStream inStream = file.getInputStream();

            long length = file.getSize();

            fileId = FastDFSClient.uploadFileByStream(inStream, originalFileName, length);

        } catch (Exception e) {
            LOG.e(FastDFSHelper.class, "upload", e);
        }

        if (fileId != null) {
            po = new DFSFilePo();
            po.setFileId(fileId);
            po.setHost(FastDFSClient.getFastdfsHost());

        }else{
            throw new FastDFSBizException("文件上传失败!");
        }

        return po;
    }

    /**
     * Modify file.
     *
     * @param oldFileId the old file id
     * @param inStream  the in stream
     * @param fileName  the file name
     * @return the dFS file po
     */
    public static DFSFilePo modifyFile(String oldFileId, InputStream inStream, String fileName) {
        DFSFilePo po = null;

        String fileId = FastDFSClient.modifyFileByInStream(oldFileId, inStream, fileName);

        if (fileId != null) {
            po = new DFSFilePo();
            po.setFileId(fileId);
            po.setHost(FastDFSClient.getFastdfsHost());
        }

        return po;
    }

    /**
     * Download file.
     *
     * @param fileId the file id
     * @return the input stream
     */
    public static InputStream downloadFile(String fileId) {

        return FastDFSClient.downloadFile(fileId);
    }

    /**
     * Download file 2 local file.
     *
     * @param fileId    the file id
     * @param localFile the local file
     * @return the boolean
     */
    public static boolean downloadFile2LocalFile(String fileId, File localFile) {

        return FastDFSClient.downloadFile2LocalFile(fileId, localFile);
    }

    /**
     * Gets full path.
     *
     * @param fileId the file id
     * @return the full path
     */
    public static String getFullPath(String fileId) {

        return FastDFSClient.getFullPathWithFileId(fileId);
    }


    /**
     * Gets dfs file po.
     *
     * @param fileId the file id
     * @return the dFS file po
     */
    public static DFSFilePo getDFSFilePo(String fileId) {

        DFSFilePo po = new DFSFilePo();
        po.setFileId(fileId);
        po.setHost(FastDFSClient.getFastdfsHost());

        return po;
    }

    /**
     * Gets fastdfs host.
     *
     * @return the fastdfs host
     */
    public static String getFastdfsHost() {
        return FastDFSClient.getFastdfsHost();
    }

    /**
     * Delete file.
     *
     * @param fileId the file id
     * @return the boolean
     */
    public static boolean deleteFile(String fileId) {
        return FastDFSClient.deleteFile(fileId);
    }
}

