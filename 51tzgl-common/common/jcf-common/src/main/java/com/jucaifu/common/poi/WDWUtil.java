package com.jucaifu.common.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by zh on 16-8-24.
 */
public class WDWUtil  {
    public static boolean isExcel2003(String filePath)
    {

        return filePath.matches("^.+\\.(?i)(xls)$");

    }

    /**
     *
     * @描述：是否是2007的excel，返回true是2007
     *
     * @author zhijun
     *
     * @参数：@param filePath　文件完整路径
     *
     * @参数：@return
     *
     * @返回值：boolean
     */

    public static boolean isExcel2007(String filePath)
    {

        return filePath.matches("^.+\\.(?i)(xlsx)$");

    }



}

