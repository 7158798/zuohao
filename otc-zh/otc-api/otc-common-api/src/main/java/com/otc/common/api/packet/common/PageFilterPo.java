package com.otc.common.api.packet.common;

import com.otc.common.api.exceptions.ParameterInvalidException;

import java.io.Serializable;

/**
 * PageFilterPo
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/18.
 */
public class PageFilterPo implements Serializable {

    private Integer page;

    private Integer size;

    /**
     * Instantiates a new Page filter po.
     */
    public PageFilterPo() {
    }

    /**
     * Instantiates a new Page filter po.
     *
     * @param page the page
     * @param size the size
     */
    public PageFilterPo(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    /**
     * Fetch page.
     *
     * @return the integer
     */
    public Integer fetchPage() {
        validatePage();
        return page;
    }

    /**
     * Sets page.
     *
     * @param page the page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * Gets page.
     *
     * @return the page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * Fetch size.
     *
     * @return the integer
     */
    public Integer fetchSize() {
        validatePage();
        return size;
    }

    /**
     * Sets size.
     *
     * @param size the size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    private void validatePage() {

        if (skipValidate()) {
            return;
        }

        if (page == null || size == null || page <= 0 || size <= 0) {
            throw ParameterInvalidException.REQUEST_PAGE_INVALID_EXCEPTION;
        }
    }

    /**
     * Skip validate.
     *
     * @return the boolean
     */
    protected boolean skipValidate() {
        return false;
    }

}
