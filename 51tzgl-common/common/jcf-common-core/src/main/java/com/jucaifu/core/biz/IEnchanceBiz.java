package com.jucaifu.core.biz;

import java.util.List;

import com.jucaifu.common.pojo.po.BasePo;
import com.jucaifu.common.pojo.vo.BaseVo;
import com.jucaifu.common.util.UUIDHelper;
import com.jucaifu.core.dao.BaseMapper;

/**
 * IEnchanceBiz
 *
 * @param <Po>  the type parameter
 * @param <Mapper>  the type parameter
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/1/12.
 */
public interface IEnchanceBiz<Po extends BasePo, Mapper extends BaseMapper<Po, BaseVo>> {

    /**
     * Insert enhance.
     *
     * @param mapper the mapper
     * @param record the record
     * @return the int
     */
    default int insertEnhance(Mapper mapper, Po record) {
        record.setUuid(UUIDHelper.getUUID());
        return mapper.insert(record);
    }

    /**
     * Update enhance.
     *
     * @param mapper the mapper
     * @param record the record
     * @return the int
     */
    default int updateEnhance(Mapper mapper, Po record) {
        return mapper.update(record);
    }

    /**
     * Delete enhance.
     *
     * @param mapper the mapper
     * @param uuid the uuid
     * @return the int
     */
    default int deleteEnhance(Mapper mapper, String uuid) {
        return mapper.delete(uuid);
    }

    /**
     * Select enhance.
     *
     * @param mapper the mapper
     * @param uuid the uuid
     * @return the po
     */
    default Po selectEnhance(Mapper mapper, String uuid) {
        return mapper.select(uuid);
    }

    /**
     * Select all enhance.
     *
     * @param mapper the mapper
     * @return the list
     */
    default List<Po> selectAllEnhance(Mapper mapper) {
        return mapper.selectAll();
    }

    /**
     * Gets by condition page enhance.
     *
     * @param mapper the mapper
     * @param vo the vo
     * @return the by condition page enhance
     */
    default List<Po> getByConditionPageEnhance(Mapper mapper, BaseVo vo) {
        return mapper.getByConditionPage(vo);
    }
}
