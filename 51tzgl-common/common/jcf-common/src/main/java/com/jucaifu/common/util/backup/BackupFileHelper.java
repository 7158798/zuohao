package com.jucaifu.common.util.backup;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.jucaifu.common.util.file.FileTarAndGzipHelper;
import com.jucaifu.common.util.mysql.MySqlHelper;
import org.apache.commons.io.FileUtils;

/**
 * BackupFileHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/25.
 */
public class BackupFileHelper implements BackupConstant {

    private String backupDir;
    private List<String> backupFileList;

    private String dbName;
    private String username;
    private String password;

    private String realPath;


    public BackupFileHelper(String backupDir, String realPath) {
        this.backupDir = backupDir;
        this.realPath = realPath;
    }

    public void setDbConfig(String dbName, String username, String password) {
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }

    /**
     * 列表所以的备份文件
     *
     * @return list
     */
    public List<BackupFileInfo> listBackupList() {

        File[] files = new File(backupDir).listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {

                if (pathname.isDirectory()) {
                    return false;
                } else {
                    return true;
                }
            }
        });

        //构建backupFileList
        List<BackupFileInfo> backupFileList = new ArrayList<BackupFileInfo>();

        BackupFileInfo backupFileInfo = null;

        for (File f : files) {
            backupFileInfo = new BackupFileInfo();
            backupFileInfo.setName(f.getName());
            backupFileInfo.setSize((int) (f.length() / 1024));
            backupFileInfo.setTime(new Date(f.lastModified()));
            backupFileInfo.setFiletype(f.getName().substring(f.getName().lastIndexOf(".") + 1));

            backupFileList.add(backupFileInfo);
        }

        Collections.sort(backupFileList);

        return backupFileList;
    }

    /**
     * Backup void.
     *
     * @param name the name
     */
    public void backup(String name) {

        String backupPath = backupDir + File.separator + BACKUP_NAME;

        try {
            //1、创建备份文件夹对象
            File bpf = new File(backupPath);
            bpf.mkdirs();

            //1、导出数据库
            MySqlHelper msu = MySqlHelper.getInstance(dbName, username, password);
            msu.backup(null, backupPath);

            //2、将要备份的文件夹拷贝到目标文件夹中
            for (String fileName : backupFileList) {
                String src = realPath + File.separator + fileName;
                String dest = backupPath + fileName;

                FileUtils.copyDirectory(new File(src), new File(dest));
            }

            //3、tar和gz
            FileTarAndGzipHelper.tarFile(backupPath, backupDir + File.separator + new Date().getTime() + "_" + name + ".tar");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                FileUtils.deleteDirectory(new File(backupPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Resume void.
     *
     * @param name the name
     */
//恢复的name就是整个文件的名称
    public void resume(String name) {
        String op = backupDir + File.separator + BACKUP_NAME;
        try {
            //1、将文件进行解压缩
            String fp = backupDir + File.separator + name;
            FileTarAndGzipHelper.unTarFile(new File(fp), backupDir);

            //2、拷贝并且覆盖相应的文件夹
            for (String f : backupFileList) {
                //先删除原有的文件夹
                String src = op + f;
                String dest = realPath + File.separator + f;

                File dfd = new File(dest);

                if (!dfd.exists()) {
                    dfd.mkdirs();
                }

                FileUtils.deleteDirectory(dfd);

                FileUtils.copyDirectory(new File(src), dfd);
            }

            //3、恢复数据库
            MySqlHelper msu = MySqlHelper.getInstance(dbName, username, password);
            msu.resume(null, backupDir);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                FileUtils.deleteDirectory(new File(op));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 要删除的文件的名称
     *
     * @param name the name
     */
    public void delete(String name) {
        File f = new File(backupDir + File.separator + name);
        f.delete();
    }
}
