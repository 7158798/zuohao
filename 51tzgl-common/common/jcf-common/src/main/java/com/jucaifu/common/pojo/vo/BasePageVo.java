package com.jucaifu.common.pojo.vo;

import java.util.List;

import com.jucaifu.common.util.page.Page;

/**
 * BasePageVo
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/8/27.
 * 说明:所有分页查询的基类
 */
public class BasePageVo extends BaseVo {

    /**
     * The Page.
     */
    protected Page page = new Page();

    /**
     * Gets page.
     *
     * @return the page
     */
    public Page getPage() {
        return page;
    }

    /**
     * Sets page.
     *
     * @param page the page
     */
    public void setPage(Page page) {
        this.page = page;
    }

    /**
     * Set now page.
     *
     * @param nowPage the now page
     */
    public void setNowPage(int nowPage) {
        this.page.setNowPage(nowPage);
    }

    /**
     * Set page show.
     *
     * @param pageShow the page show
     */
    public void setPageShow(int pageShow) {
        this.page.setPageShow(pageShow);
    }

    /**
     * Validate page.
     *
     * @return the boolean
     */
    public boolean validatePage() {
        return this.page.validatePage();
    }

    /**
     * Sets result list.
     *
     * @param list the list
     */
    public void setResultList(List list) {
        this.page.setResult(list);
    }

    /**
     * Fatch transfer list.
     *
     * @return the list
     */
    public List fatchTransferList() {
        return this.page.getResult();
    }
}
