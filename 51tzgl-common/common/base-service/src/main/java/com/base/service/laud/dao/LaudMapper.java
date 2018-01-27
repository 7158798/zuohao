package com.base.service.laud.dao;


import com.base.facade.laud.pojo.cond.LaudCond;
import com.base.facade.laud.pojo.po.Laud;
import com.base.facade.laud.pojo.vo.LaudVo;
import com.jucaifu.core.dao.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * The interface Laud mapper.
 */
public interface LaudMapper extends BaseMapper<Laud,LaudVo> {


    /**
     * Gets laud by condition.
     *
     * @param cond the cond
     * @return the laud by condition
     */
    List<Laud> getLaudByCondition(LaudCond cond);

    /**
     * Merge laud int.
     *
     * @param sourcesUserId the sources user id
     * @param targetUserId  the target user id
     * @return the int
     */
    int mergeLaud(@Param("sourcesUserId") Long sourcesUserId, @Param("targetUserId") Long targetUserId);
}