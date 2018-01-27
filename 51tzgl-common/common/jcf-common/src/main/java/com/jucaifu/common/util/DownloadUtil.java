package com.jucaifu.common.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by yong on 16-3-7.
 */
public class DownloadUtil {

    /**
     * 自定义下载文件
     *
     * @param inputStream：文件输入流
     * @param fileName：文件名
     * @param endDate：时间
     * @param fileType:文件类型
     * @return
     * @throws IOException
     */
    public static HttpEntity<byte[]> downloadFile(InputStream inputStream, String fileName, Date endDate, String fileType) throws IOException {

        byte[] documentBody = IOUtils.toByteArray(inputStream);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", fileType));
        if (endDate == null) {
            endDate = new Date();
        }
        fileName += "_" + DateFormatUtils.format(endDate, "yyyyMMdd");
        fileName = URLEncoder.encode(fileName, "UTF-8");
        fileName = URLDecoder.decode(fileName, "ISO8859_1");
        header.set("Content-Disposition", "attachment; filename=" + fileName + "." + fileType);
        header.setContentLength(documentBody.length);


        return new HttpEntity<>(documentBody, header);
    }

    /**
     * 自定义下载文件
     *
     * @param inputStream：文件输入流
     * @param fileName：文件名
     * @param fileType:文s件类型
     * @return
     * @throws IOException
     */
    public static HttpEntity<byte[]> downloadFile(InputStream inputStream, String fileName, String fileType) throws IOException {

        byte[] documentBody = IOUtils.toByteArray(inputStream);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", fileType));
        fileName = URLEncoder.encode(fileName, "UTF-8");
        fileName = URLDecoder.decode(fileName, "ISO8859_1");
        header.set("Content-Disposition", "attachment; filename=" + fileName + "." + fileType);
        header.setContentLength(documentBody.length);


        return new HttpEntity<>(documentBody, header);
    }
}
