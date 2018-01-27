package com.jucaifu.core.biz;

import java.util.List;

import com.jucaifu.common.pojo.po.BasePo;
import com.jucaifu.common.pojo.vo.BasePageVo;
import com.jucaifu.common.util.UUIDHelper;
import com.jucaifu.core.dao.BaseMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * AbsBaseBiz
 *
 * @param <Po>      the type parameter
 * @param <QueryVo> the type parameter
 * @param <Mapper>  the type parameter
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/1/12.
 */
public abstract class AbsBaseBiz<Po extends BasePo, QueryVo extends BasePageVo, Mapper extends BaseMapper<Po, QueryVo>>
        implements IDefaultBiz<Mapper>, IEnchanceBiz {

    /**
     * Gets by condition page.
     *
     * @param queryVo the query vo
     * @return the by condition page
     */
    public List<Po> getByConditionPage(QueryVo queryVo) {
        return getDefaultMapper().getByConditionPage(queryVo);
    }

    /**
     * Insert int.
     *
     * @param record the record
     * @return the int
     */
    @Transactional(rollbackFor = Exception.class)
    public int insert(Po record) {
        record.setUuid(UUIDHelper.getUUID());
        return getDefaultMapper().insert(record);
    }

    /**
     * Update int.
     *
     * @param record the record
     * @return the int
     */
    @Transactional(rollbackFor = Exception.class)
    public int update(Po record) {
        return getDefaultMapper().update(record);
    }

    /**
     * Delete int.
     *
     * @param uuid the uuid
     * @return the int
     */
    @Transactional(rollbackFor = Exception.class)
    public int delete(String uuid) {
        return getDefaultMapper().delete(uuid);
    }

    /**
     * Delete int.
     *
     * @param id the id
     * @return the int
     */
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id) {
        return getDefaultMapper().delete(id);
    }

    /**
     * Select po.
     *
     * @param uuid the uuid
     * @return the po
     */
    public Po select(String uuid) {
        return (Po) getDefaultMapper().select(uuid);
    }


    /**
     * Select po.
     *
     * @param id the id
     * @return the po
     */
    public Po select(Long id) {
        return (Po) getDefaultMapper().select(id);
    }

    /**
     * Select all.
     *
     * @return the list
     */
    public List<Po> selectAll() {
        return getDefaultMapper().selectAll();
    }
}
