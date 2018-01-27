package com.jucaifu.common.dfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.csource.fastdfs.UploadCallback;

/**
 * UploadFileSender
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/10.
 */
public class UploadFileSender implements UploadCallback {

    private InputStream inStream;

    public UploadFileSender(InputStream inStream) {
        this.inStream = inStream;
    }

    @Override
    public int send(OutputStream outSteam) throws IOException {

        byte[] buffer = new byte[256 * 1024];

        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }

        return 0;
    }
} 
