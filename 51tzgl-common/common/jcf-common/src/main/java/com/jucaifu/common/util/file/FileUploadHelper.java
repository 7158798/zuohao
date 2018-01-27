package com.jucaifu.common.util.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.jucaifu.common.constants.FilePathConstant;
import com.jucaifu.common.util.UUIDHelper;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * FileUploadHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/16.
 */
public final class FileUploadHelper implements FilePathConstant {
    private FileUploadHelper() {
    }

    /**
     * Handle upload file.
     *
     * @param uploadFile                 the upload file
     * @param saveDir                    the save dir
     * @param saveFileNameWithoutExtName the save file name without ext name
     * @return the string
     * @throws IOException the iO exception
     */
    public static final String handleUploadFile(MultipartFile uploadFile, String saveDir, String saveFileNameWithoutExtName) throws IOException {

        if (saveDir == null) {
            throw new IOException("服务器保存目录为空异常!");
        }

        String originalFileName = uploadFile.getOriginalFilename();

        String saveFileName = originalFileName;

        if (saveFileNameWithoutExtName != null) {
            String extName = FileHelper.getFileExtName(originalFileName);
            saveFileName = saveFileNameWithoutExtName + extName;
        }

        File file = new File(saveDir, saveFileName);

        FileHelper.createFileIfNeed(file);

        InputStream inStream = uploadFile.getInputStream();

        FileUtils.copyInputStreamToFile(inStream, file);

        return saveFileName;
    }


    /**
     * Handle upload file.
     *
     * @param uploadFile                 the upload file
     * @param saveFileNameWithoutExtName the save file name without ext name
     * @return the string
     * @throws IOException the iO exception
     */
    public static final String handleUploadFile(MultipartFile uploadFile, String saveFileNameWithoutExtName) throws IOException {
        String saveDir = FilePathHelper.getClassPath() + UPLOAD_PATH_FILE;
        return UPLOAD_PATH_FILE + handleUploadFile(uploadFile, saveDir, saveFileNameWithoutExtName);
    }

    /**
     * Handle upload file.
     *
     * @param uploadFile the upload file
     * @return the string
     * @throws IOException the iO exception
     */
    public static final String handleUploadFile(MultipartFile uploadFile) throws IOException {
        return handleUploadFile(uploadFile, UUIDHelper.getUUID());
    }
}
