package com.jucaifu.common.util.page;

import java.util.Collections;
import java.util.List;

import com.jucaifu.common.log.LOG;

/**
 * Page.
 *
 * @param <E> the type parameter
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/8/28.
 */
public class Page<E> implements java.io.Serializable {

    private int pageShow = 2;
    private int totalPage;
    private int totalCount;
    private int start;
    private int nowPage;
    private List<E> result = Collections.emptyList();

    private String reservedField;//预留字段
    // 增加预留对象
    private Object object;

    /**
     * 控制层校验Page参数是否合法
     * Validate page.
     *
     * @return the boolean
     */
    public boolean validatePage() {

        LOG.d(this, "当前页nowPage=" + nowPage + "  分页大小pageShow=" + pageShow);

        if (nowPage < 0 || pageShow < 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Gets start.
     *
     * @return the start
     */
    public int getStart() {
        start = (getNowPage() - 1) * getPageShow();
        if (start < 0) {
            start = 0;
        }
        return start;
    }

    /**
     * Sets start.
     *
     * @param start the start
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * Gets page show.
     *
     * @return the page show
     */
    public int getPageShow() {
        return pageShow;
    }

    /**
     * Sets page show.
     *
     * @param pageShow the page show
     */
    public void setPageShow(int pageShow) {
        this.pageShow = pageShow;
    }

    /**
     * Gets total count.
     *
     * @return the total count
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * Sets total count.
     *
     * @param totalCount the total count
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * Gets result.
     *
     * @return the result
     */
    public List<E> getResult() {
        return result;
    }

    /**
     * Sets result.
     *
     * @param result the result
     */
    public void setResult(List<E> result) {
        this.result = result;
    }

    /**
     * Sets total page.
     *
     * @param totalPage the total page
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * Sets now page.
     *
     * @param nowPage the now page
     */
    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    /**
     * Gets total page.
     *
     * @return the total page
     */
    public int getTotalPage() {
        return (int) Math.ceil(totalCount * 1.0 / pageShow);
    }

    /**
     * Gets now page.
     *
     * @return the now page
     */
    public int getNowPage() {
        if (nowPage <= 0)
            nowPage = 1;
        if (nowPage > getTotalPage())
            nowPage = getTotalPage();
        return nowPage;
    }

    public String getReservedField() {
        return reservedField;
    }

    public void setReservedField(String reservedField) {
        this.reservedField = reservedField;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
