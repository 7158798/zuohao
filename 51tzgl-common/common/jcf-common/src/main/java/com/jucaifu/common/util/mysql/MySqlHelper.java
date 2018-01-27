package com.jucaifu.common.util.mysql;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.jucaifu.common.log.LOG;

/**
 * MySqlHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/25.
 */
public final class MySqlHelper {

    private String database;
    private String username;
    private String password;

    private MySqlHelper(String database, String username, String password) {

        this.database = database;
        this.username = username;
        this.password = password;
    }

    /**
     * Gets instance.
     *
     * @param database the database
     * @param username the username
     * @param password the password
     * @return the instance
     */
    public static final MySqlHelper getInstance(String database, String username, String password) {

        return new MySqlHelper(database, username, password);
    }

    /**
     * Backup void.
     * mysqldump 远程备份
     * mysqldump -h ip -uroot -proot database > c:/data.sql
     *
     * @param ip                 the ip
     * @param backupFileFullPath the backup file full path
     */
    public void backup(String ip, String backupFileFullPath) {

        LOG.d(this, backupFileFullPath);

        BufferedReader bufReader = null;
        BufferedWriter bufWriter = null;

        try {
            String dump = null;
            if (ip == null) {
                dump = "mysqldump ";
            } else {
                dump = "mysqldump -h " + ip;
            }
            String cmd = dump + " -u" + username + " -p" + password + " " + database;

            Process process = Runtime.getRuntime().exec(cmd);

            bufReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            bufWriter = new BufferedWriter(new FileWriter(backupFileFullPath));

            String str = null;
            while ((str = bufReader.readLine()) != null) {
                bufWriter.write(str);
                bufWriter.newLine();
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            closeAll(bufReader, bufWriter);
        }
    }


    /**
     * Resume void.
     *
     * @param ip                 the ip
     * @param backupFileFullPath the backup file full path
     */
    public void resume(String ip, String backupFileFullPath) {

        LOG.d(this, backupFileFullPath);

        BufferedReader bufReader = null;
        BufferedWriter bufWriter = null;
        try {
            String mysql = null;
            if (ip == null) {
                mysql = "mysql ";
            } else {
                mysql = "mysql -h " + ip;
            }

            String cmd = mysql + " -u" + username + " -p" + password + " " + database;

            Process proc = Runtime.getRuntime().exec(cmd);

            bufWriter = new BufferedWriter(new OutputStreamWriter(proc.getOutputStream()));

            bufReader = new BufferedReader(new FileReader(backupFileFullPath));

            String str = null;

            while ((str = bufReader.readLine()) != null) {
                bufWriter.write(str);
                bufWriter.newLine();
            }
            bufReader.close();
            bufWriter.close();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {
            closeAll(bufReader, bufWriter);
        }
    }

    private void closeAll(BufferedReader bufReader, BufferedWriter bufWriter) {
        try {
            if (bufReader != null) {
                bufReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (bufWriter != null) {
                bufWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
