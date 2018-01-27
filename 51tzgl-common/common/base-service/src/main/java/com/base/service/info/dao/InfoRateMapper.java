package com.base.service.info.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.base.facade.info.pojo.bean.InfoRateBean;
import com.base.facade.info.pojo.po.InfoRate;
import com.base.facade.info.pojo.vo.InfoRateVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InfoRateMapper extends BaseMapper<InfoRate, InfoRateVo> {

    /**
     * 根据银行id查询所有已发布的银行利率
     *
     * @param uuid
     * @param bankId
     * @param type
     * @param status
     * @return
     */
    List<InfoRate> selectByReleased(@Param("uuid") String uuid, @Param("bankId") String bankId,
                                    @Param("type") String type, @Param("status") String status);

    /**
     * 条件查询银行利率
     *
     * @param bean
     * @return
     */
    List<InfoRate> selectByCondition(InfoRateBean bean);

    /**
     * 分页查询银行利率
     *
     * @param vo
     * @return
     */
    List<InfoRate> selectInfoRateByConditionPage(InfoRateVo vo);

    /**
     * 查询所有未发布的批次
     *
     * @return
     */

    List<InfoRate> selectInfoRateAllBatch();





}
