package com.base.service.info.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.base.facade.info.pojo.po.InfoExchange;
import com.base.facade.info.pojo.vo.InfoExchangeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InfoExchangeMapper extends BaseMapper<InfoExchange,InfoExchangeVo> {

    /**
     * 获取货币外汇分页集合
     * @param vo
     * @return
     */
    List<InfoExchange> getByConditionPage(InfoExchangeVo vo);

    /**
     * 更新已发布的数据为已失效
     * @return
     */
    int updateExchangeStatusByPush();

    /**
     * 获取货币汇率
     * @param batchNo   批次号
     * @return
     */
    InfoExchange getExchangeByBatchNo(@Param("batchNo") String batchNo);

    /**
     * 获取货币汇率
     * @param status    状态
     * @return
     */
    InfoExchange getExchangeByStatus(@Param("status") String status);

    /**
     * 查询过期的用户
     * @return
     */
    List<InfoExchange> getExchangeByOverdue();

    /**
     * 获取清除日期之前的数据
     * @param clearDate 清除日期
     * @return
     */
    List<InfoExchange> getClearExchange(@Param("clearDate") String clearDate);
}