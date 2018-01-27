package com.jucaifu.common.util.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.jucaifu.common.constants.FilePathConstant;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.UUIDHelper;
import com.jucaifu.common.util.ValueHelper;
import org.apache.commons.io.FileUtils;

/**
 * FileHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/2.
 */
public final class FileHelper {

    private FileHelper() {
    }

    /*****************************************
     * @param path the path
     * @return boolean boolean
     * @author  : scofieldcai@126.com
     * @Title  : fileIsExist
     * @returnType  : boolean
     * @Description: 检查文件是否存在 。
     */
    public static final boolean checkFileIsExist(String path) {
        if (ValueHelper.checkStringIsEmpty(path)) {
            return false;
        } else {
            return new File(path).exists();
        }
    }

    /*****************************************
     * @param filePath the file path
     * @author  : scofieldcai@126.com
     * @Title  : deleteFileWithPath
     * @returnType  : void
     * @Description: 根据完整路径删除文件
     */
    public void deleteFileWithPath(String filePath) {
        if (checkFileIsExist(filePath)) {
            File needDelFile = new File(filePath);
            deleteFile(needDelFile);
        }
    }

    /*****************************************
     * @param file the file
     * @return file file
     * @throws IOException the iO exception
     * @author  : scofieldcai@126.com
     * @Title  : createFileIfNeed
     * @returnType  : File
     * @Description: 创建文件
     */
    public static final File createFileIfNeed(File file) throws IOException {
        if (!file.exists()) {
            //1- 创建文件目录
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            //2- 创建文件
            if (file.createNewFile()) {
                return file;
            } else {
                return null;
            }
        } else {
            return file;
        }
    }


    /*****************************************
     * @param filePath the file path
     * @author  : scofieldcai@126.com
     * @Title  : deletePhoneFile
     * @returnType  : void
     * @Description: 删除某个文件
     */
    public static final void deleteFile(String filePath) {
        File file = new File(filePath);
        deleteFile(file);
    }

    /*****************************************
     * @param needDelFile the need del file
     * @author  : scofieldcai@126.com
     * @Title  : deleteFile
     * @returnType  : void
     * @Description: 删除文件 或 删除文件夹下的全部文件和文件夹
     */
    public static final void deleteFile(File needDelFile) {

        if (!needDelFile.exists()) {
            return;
        } else {
            if (needDelFile.isFile()) {
                needDelFile.delete();
                return;
            } else {
                File[] files = needDelFile.listFiles();
                for (File file : files) {
                    deleteFile(file);
                }
                needDelFile.delete();
            }
        }
    }

    /*****************************************
     * @param oldPath the old path
     * @param newPath the new path
     * @author  : scofieldcai@126.com
     * @Title  : renameFile
     * @returnType  : void
     * @Description: 重命名文件名为新的名称 。
     */
    public static final void renameFile(String oldPath, String newPath) {
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);
        oldFile.renameTo(newFile);
    }


    /*****************************************
     * @param filePath the file path
     * @return the file name
     * @author  : scofieldcai@126.com
     * @Title  : getFileName
     * @returnType  : String
     * @Description: 获取文件的名称
     */
    public static final String getFileName(String filePath) {
        File file = new File(filePath);
        return file.getName();
    }

    /**
     * Get file ext name.
     *
     * @param fileName the file name
     * @return the string
     */
    public static final String getFileExtName(String fileName) {

        String extName = "";

        if (fileName != null && fileName.lastIndexOf(".") >= 0) {
            extName = fileName.substring(fileName.lastIndexOf("."));
        }

        return extName;
    }


    /**
     * Gets temp file.
     *
     * @param fileName the file name
     * @param inStream the in stream
     * @return the temp file
     */
    public static final File getTempFile(String fileName, InputStream inStream) {

        File tmpFile = null;

        try {
            String tmpPath = FilePathConstant.UPLOAD_TMP_PATH_FILE + UUIDHelper.get32UUID() + "/" + fileName;

            tmpFile = new File(tmpPath);
            FileHelper.createFileIfNeed(tmpFile);
            FileUtils.copyInputStreamToFile(inStream, tmpFile);

        } catch (Exception e) {

            LOG.e("", "getTempFile", e);

        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                }
            }
        }
        return tmpFile;
    }
}
