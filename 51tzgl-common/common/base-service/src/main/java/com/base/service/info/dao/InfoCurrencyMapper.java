package com.base.service.info.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.base.facade.info.pojo.po.InfoCurrency;
import com.base.facade.info.pojo.vo.InfoCurrencyVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InfoCurrencyMapper extends BaseMapper<InfoCurrency,InfoCurrencyVo> {

    /**
     * 获取货币信息集合
     * @param gainType  获取外汇汇率0:不获取 1：获取
     * @return
     */
    List<InfoCurrency> getCurrencyListByGainType(@Param("gainType") String gainType);

    /**
     *
     * @return
     */
    List<InfoCurrency> getCurrencyListByCalc();
}