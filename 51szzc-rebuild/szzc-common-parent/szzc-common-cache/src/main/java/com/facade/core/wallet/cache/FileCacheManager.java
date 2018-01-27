package com.facade.core.wallet.cache;

import com.facade.core.wallet.enums.FileCacheType;
import com.facade.core.wallet.pojo.FileCachePo;

/**
 * FileCacheManager
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/17.
 */
public final class FileCacheManager {

    /**
     * Gets file cache pojo.
     *
     * @param fileKey the file key
     * @return the file cache pojo
     */
    public static FileCachePo getFileCachePo(String fileKey) {
        return CacheHelper.getObj(fileKey);
    }

    /**
     * Save file cache pojo.
     *
     * @param fileKey the file key
     * @param po      the pojo
     * @return the boolean
     */
    public static boolean saveFileCachePo(String fileKey, FileCachePo po) {
        return CacheHelper.saveObj(fileKey, po);
    }

    /**
     * Gets file cache pojo.
     *
     * @param fileCacheType the file cache type
     * @param args          the args
     * @return the file cache pojo
     */
    public static FileCachePo getFileCachePo(FileCacheType fileCacheType, String... args) {

        String fileKey = buildFileKey(fileCacheType, args);

        if (fileKey != null) {
            return getFileCachePo(fileKey);
        } else {
            return null;
        }

    }

    /**
     * Save file cache pojo.
     *
     * @param fileCacheType the file cache type
     * @param po            the pojo
     * @param args          the args
     * @return the boolean
     */
    public static boolean saveFileCachePo(FileCacheType fileCacheType, FileCachePo po, String... args) {

        String fileKey = buildFileKey(fileCacheType, args);

        if (fileKey != null && po != null) {
            return saveFileCachePo(fileKey, po);
        } else {
            return false;
        }

    }

    /**
     * Build file key.
     *
     * @param fileCacheType the file cache type
     * @param args          the args
     * @return the string
     */
    public static String buildFileKey(FileCacheType fileCacheType, String... args) {

        String fileKey = null;

        if (fileCacheType != null && args != null) {
            fileKey = String.format(fileCacheType.getKeyFormat(), args);
        }

        return fileKey;
    }

}
