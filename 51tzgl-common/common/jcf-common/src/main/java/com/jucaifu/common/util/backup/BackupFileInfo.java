package com.jucaifu.common.util.backup;

import java.util.Date;

/**
 * The type Backup file info.
 */
public class BackupFileInfo implements Comparable<BackupFileInfo> {

    private String name;//文件名称
    private Date time;//备份时间
    private int size;//文件大小
    private String filetype;//文件类型

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets size.
     *
     * @param size the size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Gets filetype.
     *
     * @return the filetype
     */
    public String getFiletype() {
        return filetype;
    }

    /**
     * Sets filetype.
     *
     * @param filetype the filetype
     */
    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    @Override
    public int compareTo(BackupFileInfo other) {
        return other.getTime().compareTo(this.getTime());
    }

}
