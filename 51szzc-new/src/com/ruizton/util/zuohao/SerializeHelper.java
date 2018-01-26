package com.ruizton.util.zuohao;



import com.ruizton.main.log.LOG;

import java.io.*;

/**
 * SerializeHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/19.
 */
public class SerializeHelper {

    /**
     * Serialize byte [ ].
     *
     * @param <T>    the type parameter
     * @param object the object
     * @return byte [ ]
     * @throws Exception the exception
     */
    public static <T extends Serializable> byte[] serialize(T object) throws Exception {

        byte[] bytes;

        if (object == null) {
            return null;
        }

        ObjectOutputStream objOutStream = null;
        ByteArrayOutputStream bytesOutStream = null;

        try {
            bytesOutStream = new ByteArrayOutputStream();
            objOutStream = new ObjectOutputStream(bytesOutStream);
            objOutStream.writeObject(object);
            bytes = bytesOutStream.toByteArray();

        } catch (Exception e) {
            LOG.e(SerializeHelper.class, "serialize", e);
            throw e;
        } finally {
            if (objOutStream != null) {
                try {
                    objOutStream.close();
                } catch (IOException e) {
                }
            }
            if (bytesOutStream != null) {
                try {
                    bytesOutStream.close();
                } catch (IOException e) {
                }
            }
        }

        return bytes;

    }

    /**
     * Un serialize.
     *
     * @param <T>   the type parameter
     * @param bytes the bytes
     * @return object object
     * @throws Exception the exception
     */
    public static <T extends Serializable> T unSerialize(byte[] bytes) throws Exception {

        T t = null;
        if (bytes == null) {
            return null;
        }

        ByteArrayInputStream bytesInStream = null;
        ObjectInputStream objInStream = null;
        try {
            bytesInStream = new ByteArrayInputStream(bytes);
            objInStream = new ObjectInputStream(bytesInStream);
            t = (T) objInStream.readObject();
        } catch (Exception e) {
            LOG.e(SerializeHelper.class, "unSerialize", e);
            throw e;
        } finally {
            if (objInStream != null) {
                try {
                    objInStream.close();
                } catch (IOException e) {
                }
            }
            if (bytesInStream != null) {
                try {
                    bytesInStream.close();
                } catch (IOException e) {
                }
            }
        }
        return t;
    }
}
