package com.jucaifu.common.util.file;

import java.io.File;
import java.net.URL;

/**
 * FilePathHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/16.
 */
public final class FilePathHelper {
    private FilePathHelper() {
    }

    /**
     * Gets class path.
     *
     * @return the class path
     */
    public static final String getClassPath() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        String path = (String.valueOf(url) + "../../").replaceAll("file:/", "").replaceAll("%20", " ").trim();
        if (path.indexOf(":") != 1) {
            path = File.separator + path;
        }
        return path;
    }

    /**
     * Gets class resources.
     *
     * @return the class resources
     */
    public static final String getClassResources() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        String path = (String.valueOf(url)).replaceAll("file:/", "").replaceAll("%20", " ").trim();
        if (path.indexOf(":") != 1) {
            path = File.separator + path;
        }
        return path;
    }
}
