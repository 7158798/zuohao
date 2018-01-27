package com.jucaifu.common.pojo.po;

import java.io.Serializable;

/**
 * BasePo
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/8/27.
 * <p>
 * 说明:代码生成器数据库Po基类
 */
public class BasePo implements Serializable {

    /**
     * The Id.
     */
    protected Long id;

    /**
     * The Uuid.
     */
    protected String uuid;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets uuid.
     *
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets uuid.
     *
     * @param uuid the uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
