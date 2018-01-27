package com.base.service.info.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.base.facade.info.pojo.po.InfoExchangeDetail;
import com.base.facade.info.pojo.vo.InfoExchangeDetailVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InfoExchangeDetailMapper extends BaseMapper<InfoExchangeDetail,InfoExchangeDetailVo> {

    /**
     * 查询货币外汇明细列表
     * @param exchangeId    货币外汇ID
     * @return
     */
    List<InfoExchangeDetail> getExchangeDetailListByExchangeId(@Param("exchangeId") String exchangeId);

    /**
     * 删除汇率详细数据
     * @param exchangeId    货币外汇ID
     * @return
     */
    int deleteByExchangeId(@Param("exchangeId") String exchangeId);


    /**
     *
     * @param curNo
     * @return
     */
    InfoExchangeDetail getExchangeDetailByCurNo(@Param("curNo") String curNo);
}