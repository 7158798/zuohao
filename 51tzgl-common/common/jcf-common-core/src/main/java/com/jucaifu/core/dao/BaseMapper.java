package com.jucaifu.core.dao;

import java.util.List;

import com.jucaifu.common.pojo.po.BasePo;

/**
 * 说明：提供给扩展DAO-Mapper基类
 *
 * @param <Po>      the type parameter
 * @param <QueryVo> the type parameter
 * @message Created by scofieldcai-dev on 15/8/27.
 */
public interface BaseMapper<Po extends BasePo, QueryVo> {

    /**
     * Insert int.
     * <p>
     * 说明:返回数据库自动生成的id,不提供外部直接使用,仅仅提供给BaseService使用
     *
     * @param record the record
     * @return the int
     */
    int insert(Po record);

    /**
     * Update by primary key.
     *
     * @param record the record
     * @return the int
     */
    int update(Po record);

    /**
     * Delete by uuid
     *
     * @param uuid the uuid
     * @return the int
     */
    int delete(String uuid);

    /**
     * Delete int.
     *
     * @param id the id
     * @return the int
     */
    int delete(Long id);

    /**
     * Select by uuid
     *
     * @param uuid the uuid
     * @return the po
     */
    Po select(String uuid);


    /**
     * Select po.
     *
     * @param id the id
     * @return the po
     */
    Po select(Long id);

    /**
     * Select all.
     *
     * @return the list
     */
    List<Po> selectAll();

    /**
     * Gets by condition page.
     *
     * @param queryVo the query vo
     * @return the by condition page
     */
    List<Po> getByConditionPage(QueryVo queryVo);

    long countNotBeloneTyProductOrder();
}
