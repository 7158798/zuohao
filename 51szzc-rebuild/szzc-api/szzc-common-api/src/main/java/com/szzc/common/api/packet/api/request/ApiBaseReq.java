package com.szzc.common.api.packet.api.request;
import com.szzc.common.api.exceptions.ParameterInvalidException;
import com.szzc.common.api.packet.common.DateFilterPo;
import com.szzc.common.api.packet.common.PageFilterPo;

import java.io.Serializable;
import java.util.Date;

/**
 * WebApiBaseReq
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/8.
 */
public class ApiBaseReq implements Serializable {

    private String uuid;

    private String token;

    private Long id;

    /**
     * 日期过滤
     */
    private DateFilterPo dateFilter;

    /**
     * 分页过滤
     */
    private PageFilterPo pageFilter;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApiBaseReq() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public DateFilterPo getDateFilter() {
        return dateFilter;
    }

    public void setDateFilter(DateFilterPo dateFilter) {
        this.dateFilter = dateFilter;
    }

    public void setDateFilter(String name, Date start, Date end) {
        this.dateFilter = new DateFilterPo(name, start, end);
    }

    public void setDateFilter(Date start, Date end) {
        this.dateFilter = new DateFilterPo(start, end);
    }

    public PageFilterPo getPageFilter() {
        return pageFilter;
    }

    public void setPageFilter(PageFilterPo pageFilter) {
        this.pageFilter = pageFilter;
    }

    public void setPageFilter(Integer page, Integer size) {
        this.pageFilter = new PageFilterPo(page, size);
    }

    public Integer fetchPageFilterPage() {
        if (pageFilter == null) {
            throw ParameterInvalidException.REQUEST_PAGE_INVALID_EXCEPTION;
        }
        return pageFilter.fetchPage();
    }

    public Integer fetchPageFilterSize() {
        if (pageFilter == null) {
            throw ParameterInvalidException.REQUEST_PAGE_INVALID_EXCEPTION;
        }
        return pageFilter.fetchSize();
    }

    public Date fetchDateFilterStart() {
        if (dateFilter == null) {
            return null;
        }
        return dateFilter.getStart();
    }

    public Date fetchDateFilterEnd() {
        if (dateFilter == null) {
            return null;
        }
        return dateFilter.getEnd();
    }
}
