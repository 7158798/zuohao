package com.jucaifu.common.util;

import java.io.*;

/**
 * StreamHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/1.
 */
public class StreamHelper {

    private StreamHelper() {
    }

    /*****************************************
     * @param inStream
     * @return 字节数组
     * @throws IOException
     * @author : scofieldcai@126.com
     * @Title : readStreamToBytes
     * @returnType : byte[]
     * @Description: 将流读取成byte[]
     *****************************************/
    public static byte[] readStreamToBytes(InputStream inStream) throws IOException {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }


    /*****************************************
     * @param inStream
     * @return
     * @throws Exception
     * @author : scofieldcai@126.com
     * @Title : readStreamToString
     * @returnType : String
     * @Description: 将流读取成字符串
     *****************************************/
    public static String readStreamToString(InputStream inStream) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        inStream.close();
        return sb.toString();
    }
}
