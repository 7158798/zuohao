package com.jucaifu.common.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

/**
 * FileTarAndGzipHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/25.
 */
public final class FileTarAndGzipHelper {
    private FileTarAndGzipHelper() {
    }

    /**
     * The constant SUFFIX_TAR.
     */
    public static final String SUFFIX_TAR = ".tar";
    /**
     * The constant SUFFIX_GZ.
     */
    public static final String SUFFIX_GZ = ".gz";

    /**
     * 使用tar压缩文件到当前目录中
     *
     * @param path the path
     */
    public static final void tarFile(String path) {
        String tarFile = path + SUFFIX_TAR;
        tarFile(path, tarFile);
    }

    /**
     * Tar file.
     *
     * @param path    需要被压缩的文件
     * @param tarFile 压缩后的文件
     */
    public static final void tarFile(String path, String tarFile) {

        TarArchiveOutputStream tarOutStream = null;

        try {
            File file = new File(path);
            int len = file.getParent().length();

            tarOutStream = new TarArchiveOutputStream(new FileOutputStream(tarFile));
            tarOutStream.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);

            tarFile(file, tarOutStream, len);

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } finally {
            try {
                if (tarOutStream != null) tarOutStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        gzipFile(new File(tarFile));
    }

    /**
     * Un tar file.
     *
     * @param tarFile   需要解压的tar文件
     * @param unTarPath 解压后的目录
     */
    public static final void unTarFile(File tarFile, String unTarPath) {

        TarArchiveInputStream tarInStream = null;

        File tf = null;

        try {
            tf = unGzipFile(tarFile);

            tarInStream = new TarArchiveInputStream(new FileInputStream(tf));
            TarArchiveEntry tae = null;

            while ((tae = tarInStream.getNextTarEntry()) != null) {
                if (!tae.isDirectory()) {
                    String name = unTarPath + File.separator + tae.getName();
                    FileOutputStream fos = null;
                    File ff = new File(name);
                    if (!ff.getParentFile().exists()) ff.getParentFile().mkdirs();
                    try {
                        fos = new FileOutputStream(ff);
                        IOUtils.copy(tarInStream, fos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (fos != null) fos.close();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (tarInStream != null) tarInStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (tf != null) {
                tf.delete();
                tf.deleteOnExit();
            }
        }
    }

    /**
     * 解压到当前目录下
     *
     * @param file 需要解压的文件
     * @return the file
     */
    public static final File unGzipFile(File file) {

        GZIPInputStream gis = null;
        FileOutputStream fos = null;
        try {
            gis = new GZIPInputStream(new FileInputStream(file));
            String path = file.getAbsolutePath();
            path = path.substring(0, path.lastIndexOf("."));

            //要返回的文件
            File f = new File(path);
            fos = new FileOutputStream(f);

            IOUtils.copy(gis, fos);

            return f;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (gis != null) gis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * 压缩到当前目录下
     *
     * @param file the file
     */
    public static final void gzipFile(File file) {

        GZIPOutputStream gos = null;
        FileInputStream fis = null;

        try {
            gos = new GZIPOutputStream(new FileOutputStream(file.getAbsolutePath() + SUFFIX_GZ));

            fis = new FileInputStream(file);

            IOUtils.copy(fis, gos);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (gos != null) gos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            file.delete();
        }
    }

    private static void tarFile(File file, TarArchiveOutputStream tarOutStream, int len) {

        if (file.isDirectory()) {

            File[] fs = file.listFiles();
            for (File f : fs) {
                tarFile(f, tarOutStream, len);
            }

        } else {
            FileInputStream fis = null;

            try {

//				System.out.println(file.getAbsolutePath().substring(len)+File.separator+file.getName());

                TarArchiveEntry tae = new TarArchiveEntry(file.getParent().substring(len) + File.separator + file.getName());
                tae.setSize(file.length());
                fis = new FileInputStream(file);

                tarOutStream.putArchiveEntry(tae);

                IOUtils.copy(fis, tarOutStream);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null) fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    tarOutStream.closeArchiveEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
