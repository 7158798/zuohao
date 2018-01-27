package com.jucaifu.common.util.sort;

import com.jucaifu.common.enums.EnumSort;

/**
 * SortItem
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/10.
 */
public class SortItem {

    private String sortParam;

    private EnumSort sortType;


    /**
     * Instantiates a new Sort item.
     *
     * @param sortParam the sortType param
     * @param sortType  the sortType
     */
    public SortItem(String sortParam, EnumSort sortType) {
        this.sortParam = sortParam;
        this.sortType = sortType;
    }

    /**
     * Instantiates a new Sort item.
     *
     * @param sortParam the sortType param
     */
    public SortItem(String sortParam) {
        this(sortParam, EnumSort.ASC);
    }

    /**
     * Gets sortType param.
     *
     * @return the sortType param
     */
    public String getSortParam() {
        return sortParam;
    }

    /**
     * Sets sortType param.
     *
     * @param sortParam the sortType param
     */
    public void setSortParam(String sortParam) {
        this.sortParam = sortParam;
    }

    /**
     * Gets sortType.
     *
     * @return the sortType
     */
    public EnumSort getSortType() {
        return sortType;
    }

    /**
     * Sets sortType.
     *
     * @param sortType the sortType
     */
    public void setSortType(EnumSort sortType) {
        this.sortType = sortType;
    }
}
