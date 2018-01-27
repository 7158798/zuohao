package com.jucaifu.common.dfs;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;

import com.jucaifu.common.constants.FilePathConstant;
import com.jucaifu.common.context.SpringPropReaderHelper;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.ReflectHelper;
import com.jucaifu.common.util.StreamHelper;
import com.jucaifu.common.util.UUIDHelper;
import com.jucaifu.common.util.file.FileHelper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;

/**
 * FastDFSClient
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/20.
 */
public final class FastDFSClient {

    private static final String connect_timeout = SpringPropReaderHelper.getProperty("fastdfs.connect_timeout");
    private static final String network_timeout = SpringPropReaderHelper.getProperty("fastdfs.network_timeout");
    private static final String charset = SpringPropReaderHelper.getProperty("fastdfs.charset");
    private static final String tracker_http_port = SpringPropReaderHelper.getProperty("fastdfs.http.tracker_http_port");
    private static final String anti_steal_token = SpringPropReaderHelper.getProperty("fastdfs.http.anti_steal_token");
    private static final String secret_key = SpringPropReaderHelper.getProperty("fastdfs.http.secret_key");
    private static final String tracker_server = SpringPropReaderHelper.getProperty("fastdfs.tracker_server");
    private static final String fastdfs_host1 = SpringPropReaderHelper.getProperty("fastdfs.host1");
    private static final String fastdfs_http_download_host = SpringPropReaderHelper.getProperty("fastdfs.http.download.host");

    private static final void i(String msg) {
        LOG.i(FastDFSClient.class, msg);
    }

    private static final void e(String msg, Throwable ex) {
        LOG.e(FastDFSClient.class, msg, ex);
    }

    private FastDFSClient() {
    }

    /**
     * Gets storage client.
     *
     * @return the storage client
     */
    public static StorageClient1 getStorageClient() {
        StorageClient1 storageClient = null;
        try {
            TrackerClient trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
            TrackerServer trackerServer = trackerClient.getConnection();
            if (trackerServer == null) {
                e("文件系统Tracker获取链接失败", null);
            }
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            if (storageServer == null) {
                e("文件系统Storage获取失败", null);
            }
            storageClient = new StorageClient1(trackerServer, storageServer);
        } catch (IOException e) {
            LOG.e("", "getStorageClient", e);
        }

        if (storageClient == null) {
            throw new FastDFSBizException("文件系统连接失败.");
        }

        return storageClient;
    }

    /**
     * Close storage client.
     *
     * @param storageClient the storage client
     */
    public static void closeStorageClient(StorageClient1 storageClient) {
        if (storageClient != null) {
            try {
                TrackerServer trackerServer = ReflectHelper.getValueByFieldName(storageClient, "trackerServer");
                trackerServer.close();
            } catch (Exception e) {
            }
        }
    }

    static {

        try {
            ClientGlobal.setG_connect_timeout(Integer.parseInt(connect_timeout) * 1000);
            ClientGlobal.setG_network_timeout(Integer.parseInt(network_timeout) * 1000);
            ClientGlobal.setG_charset(charset);
            ClientGlobal.setG_tracker_http_port(Integer.parseInt(tracker_http_port));
            ClientGlobal.setG_anti_steal_token(Boolean.getBoolean(anti_steal_token));
            ClientGlobal.setG_secret_key(secret_key);

            String[] szTrackerServers = tracker_server.split("\\;");
            String[] parts;

            InetSocketAddress[] tracker_servers = new InetSocketAddress[szTrackerServers.length];

            for (int i = 0; i < szTrackerServers.length; i++) {
                parts = szTrackerServers[i].split("\\:", 2);
                if (parts.length != 2) {
                    throw new MyException("the value of item \"tracker_server\" is invalid, the correct format is host:port");
                }

                tracker_servers[i] = new InetSocketAddress(parts[0].trim(), Integer.parseInt(parts[1].trim()));
            }

            ClientGlobal.g_tracker_group = new TrackerGroup(tracker_servers);

        } catch (Exception ex) {
            e("", ex);
        }
    }

    /**
     * Gets full path with file id.
     *
     * @param fileId the file id
     * @return the full path with file id
     */
    public static String getFullPathWithFileId(String fileId) {
        String fullPath = null;

        if (StringUtils.isNotEmpty(fileId)) {
            fullPath = getFastdfsHost() + fileId;
        }

//        关闭了文件是否实际存在的校验
//        StorageClient1 storageClient = getStorageClient();
//            try {
//                FileInfo fileInfo = storageClient.get_file_info1(fileId);
//                String ipAddress = fileInfo.getSourceIpAddr();
//                if (ipAddress != null) {
//                    fullPath = "http://" + ipAddress + ":8088/" + fileId;
//                    i(fullPath);
//                }
//            } catch (Exception e) {
//                LOG.e(FastDFSClient.class, "getFullPathWithFileId", e);
//            } finally {
//                closeStorageClient(storageClient);

        return fullPath;
    }

    /**
     * Gets fastdfs host.
     *
     * @return the fastdfs host
     */
    public static String getFastdfsHost() {
        return fastdfs_http_download_host;
    }

    /**
     * Upload file.
     *
     * @param inStream the in stream
     * @param fileName the file name
     * @return the string
     */
    @Deprecated
    public static String uploadFile(InputStream inStream, String fileName) {

        String fileId = null;

        File file = null;

        try {
            String tmpPath = FilePathConstant.UPLOAD_TMP_PATH_FILE + UUIDHelper.get32UUID();
            i("temp:" + tmpPath);

            File tmpFile = new File(tmpPath);
            FileHelper.createFileIfNeed(tmpFile);
            FileUtils.copyInputStreamToFile(inStream, tmpFile);
            file = tmpFile;
        } catch (Exception e) {
            e("uploadFile--->inStream", e);
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                }
            }
        }

        if (file != null) {
            fileId = uploadFile(file, fileName);

            FileHelper.deleteFile(file);
        }

        return fileId;
    }

    /**
     * Upload file by bytes.
     *
     * @param fileBuff       the file buff
     * @param uploadFileName the upload file name
     * @return the string
     */
    public static String uploadFileByBytes(byte[] fileBuff, String uploadFileName) {

        String fileId = null;
        StorageClient1 storageClient = getStorageClient();

        try {
            int fileLength = fileBuff.length;
            String fileExtName = "";

            if (uploadFileName.contains(".")) {
                fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
            } else {
                LOG.d("", "文件名字不合法");
                return fileId;
            }
            NameValuePair[] metaList = new NameValuePair[3];
            metaList[0] = new NameValuePair("fileName", uploadFileName);
            metaList[1] = new NameValuePair("fileExtName", fileExtName);
            metaList[2] = new NameValuePair("fileLength", String.valueOf(fileLength));

            fileId = storageClient.upload_file1(fileBuff, fileExtName, metaList);

        } catch (Exception e) {

            LOG.e(FastDFSClient.class, "uploadFileByBytes", e);
        } finally {
            closeStorageClient(storageClient);
        }
        return fileId;
    }

    /**
     * Upload file by stream.
     *
     * @param inStream       the in stream
     * @param uploadFileName the upload file name
     * @param fileLength     the file length
     * @return the string
     * @throws IOException the iO exception
     */
    public static String uploadFileByStream(InputStream inStream, String uploadFileName, long fileLength) throws IOException {

        i("fileLength = " + fileLength);

        String fileId = null;
        StorageClient1 storageClient = getStorageClient();

        try {
            String[] results = null;
            String fileExtName = "";
            if (uploadFileName.contains(".")) {
                fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
            } else {
                LOG.d("", "文件名字不合法");
                return fileId;
            }

            NameValuePair[] metaList = new NameValuePair[3];
            metaList[0] = new NameValuePair("fileName", uploadFileName);
            metaList[1] = new NameValuePair("fileExtName", fileExtName);
            metaList[2] = new NameValuePair("fileLength", String.valueOf(fileLength));

            results = storageClient.upload_file(null, fileLength, new UploadFileSender(inStream), fileExtName, metaList);

            LOG.dTag(FastDFSClient.class, "文件上传结果:" + results);

            if (results != null && results.length == 2) {
                fileId = results[0] + "/" + results[1];
            }

        } catch (Exception e) {

            LOG.e(FastDFSClient.class, "uploadFileByStream", e);

        } finally {
            closeStorageClient(storageClient);
        }

        return fileId;
    }


    /**
     * Upload file.
     *
     * @param file     the file
     * @param fileName the file name
     * @return the string
     */
    public static String uploadFile(File file, String fileName) {

        String fileId = null;
        StorageClient1 storageClient = getStorageClient();

        FileInputStream fileInStream = null;

        try {
            NameValuePair[] meta_list = null;

            fileInStream = new FileInputStream(file);
            byte[] file_buff = null;
            if (fileInStream != null) {
                int len = fileInStream.available();
                file_buff = new byte[len];
                fileInStream.read(file_buff);
            }

            fileId = storageClient.upload_file1(file_buff, getFileExt(fileName), meta_list);

        } catch (Exception ex) {

            e("upload", ex);

        } finally {

            if (fileInStream != null) {
                try {
                    fileInStream.close();
                } catch (IOException e) {
                    e("fileInStream.close()", e);
                }
            }
            closeStorageClient(storageClient);
        }

        return fileId;
    }

    /**
     * Delete file.
     *
     * @param groupName the group name
     * @param fileName  the file name
     * @return the boolean
     */
    public static boolean deleteFile(String groupName, String fileName) {

        boolean result = false;

        StorageClient1 storageClient = getStorageClient();

        try {
            int errorCode = storageClient.delete_file(groupName == null ? "group1" : groupName, fileName);
            result = errorCode == 0 ? true : false;
            if (!result) {
                i("deleteFile-errorCode:" + errorCode);
            }
        } catch (Exception ex) {
            e("deleteFile", ex);
        } finally {
            closeStorageClient(storageClient);
        }

        return result;
    }

    /**
     * Delete file.
     *
     * @param fileId the file id
     * @return the boolean
     */
    public static boolean deleteFile(String fileId) {
        boolean result = false;

        if (StringUtils.isEmpty(fileId)) {
            result = true;
        } else {
            StorageClient1 storageClient = getStorageClient();
            try {
                int errorCode = storageClient.delete_file1(fileId);
                result = errorCode == 0 ? true : false;
                if (!result) {
                    i("deleteFile-errorCode:" + errorCode);
                }
            } catch (Exception ex) {
                e("deleteFile", ex);
            } finally {
                closeStorageClient(storageClient);
            }
        }

        return result;
    }

    /**
     * Modify file.
     *
     * @param oldFileId the old file id
     * @param file      the file
     * @param fileName  the file name
     * @return the string
     */
    public static String modifyFile(String oldFileId, File file, String fileName) {

        String fileId = null;
        try {
            fileId = uploadFile(file, fileName);

            if (fileId != null && StringUtils.isNotEmpty(oldFileId)) {
                boolean delResult = deleteFile(oldFileId);
                if (!delResult) {
                    fileId = null;
                }
            }

        } catch (Exception ex) {
            e("modifyFile", ex);
        }

        return fileId;
    }


    /**
     * Modify file by in stream.
     *
     * @param oldFileId the old file id
     * @param inStream  the in stream
     * @param fileName  the file name
     * @return the string
     */
    public static String modifyFileByInStream(String oldFileId, InputStream inStream, String fileName) {

        String fileId = null;
        try {
            byte[] bytesFile = StreamHelper.readStreamToBytes(inStream);
            fileId = FastDFSClient.uploadFileByBytes(bytesFile, fileName);

            if (fileId != null && oldFileId != null) {
                boolean delResult = deleteFile(oldFileId);
                if (!delResult) {
//                    暂时关闭删除失败的回滚
//                    fileId = null;
                }
            }

        } catch (Exception ex) {
            e("modifyFileByInStream", ex);
        }

        return fileId;
    }

    /**
     * Modify file.
     *
     * @param oldFileId the old file id
     * @param inStream  the in stream
     * @param fileName  the file name
     * @return the string
     */
    @Deprecated
    public static String modifyFile1(String oldFileId, InputStream inStream, String fileName) {
        String fileId = null;

        File file = null;

        try {
            String tmpPath = FilePathConstant.UPLOAD_TMP_PATH_FILE + UUIDHelper.get32UUID();
            i("temp:" + tmpPath);

            File tmpFile = new File(tmpPath);
            FileUtils.copyInputStreamToFile(inStream, tmpFile);
            file = tmpFile;
        } catch (Exception e) {
            e("modifyFile--->inStream", e);
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                }
            }
        }

        if (file != null) {
            fileId = modifyFile(oldFileId, file, fileName);

            FileHelper.deleteFile(file);
        }

        return fileId;
    }

    /**
     * Download file.
     *
     * @param fileId the file id
     * @return the input stream
     */
    public static InputStream downloadFile(String fileId) {

        InputStream inStream = null;
        StorageClient1 storageClient = getStorageClient();
        try {
            byte[] bytes = storageClient.download_file1(fileId);
            inStream = new ByteArrayInputStream(bytes);
            return inStream;
        } catch (Exception ex) {
            e("downloadFile", ex);
        } finally {
            closeStorageClient(storageClient);
        }
        return inStream;
    }

    /**
     * Download file 2 local file.
     *
     * @param fileId    the file id
     * @param localFile the local file
     * @return the boolean
     */
    public static boolean downloadFile2LocalFile(String fileId, File localFile) {

        boolean result = false;

        InputStream inStream = downloadFile(fileId);

        if (inStream != null) {
            try {
                FileUtils.copyInputStreamToFile(inStream, localFile);
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private static String getFileExt(String fileName) {
        if (StringUtils.isBlank(fileName) || !fileName.contains(".")) {
            return "";
        } else {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
    }


}
