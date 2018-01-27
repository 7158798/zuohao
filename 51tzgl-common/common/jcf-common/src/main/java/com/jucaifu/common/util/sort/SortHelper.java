package com.jucaifu.common.util.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jucaifu.common.enums.EnumSort;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;


/**
 * SortHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/10.
 */
public final class SortHelper {
    private SortHelper() {
    }

    /**
     * Sort list.
     *
     * @param <T>          the type parameter
     * @param needSortList the need sort list
     * @param sortParam    the sort param
     * @param sortType     the sort type
     * @return the list
     */
    public static final <T> List<T> sort(List<T> needSortList, String sortParam, EnumSort sortType) {

        SortItem sortItem = new SortItem(sortParam, sortType);

        return sort(needSortList, sortItem);
    }

    /**
     * Sort list.
     *
     * @param <T>          the type parameter
     * @param needSortList the need sort list
     * @param sortItems    the sort items
     * @return the list
     */
    public static final <T> List<T> sort(List<T> needSortList, SortItem... sortItems) {

        if (sortItems != null) {

            List<SortItem> sortItemList = new ArrayList<SortItem>();

            int length = sortItems.length;

            for (int i = 0; i < length; i++) {
                sortItemList.add(sortItems[i]);
            }

            return sort(needSortList, sortItemList);

        } else {
            return needSortList;
        }
    }

    /**
     * Sort list.
     *
     * @param <T>          the type parameter
     * @param needSortList the need sort list
     * @param sortItemList the sort item list
     * @return the list
     */
    public static final <T> List<T> sort(List<T> needSortList, List<SortItem> sortItemList) {

        if (sortItemList != null) {
            ArrayList<BeanComparator> sortFields = new ArrayList<BeanComparator>();

            Comparator orderComparator = null;
            for (SortItem sortItem : sortItemList) {
                orderComparator = getOrderComparator(sortItem.getSortType());
                sortFields.add(new BeanComparator(sortItem.getSortParam(), orderComparator));
            }

            ComparatorChain multiSort = new ComparatorChain(sortFields);

            Collections.sort(needSortList, multiSort);

            return needSortList;

        } else {

            return needSortList;
        }

    }


    /**
     * Get order comparator.
     *
     * @param sortType the sortType
     * @return the comparator
     */
    public static final Comparator getOrderComparator(EnumSort sortType) {

        Comparator orderComparator = ComparableComparator.getInstance();//默认为正序

        if (sortType == EnumSort.DESC) {
            orderComparator = ComparatorUtils.reversedComparator(orderComparator);
        }

        return orderComparator;

    }

}
