package com.base.service.info.biz;

import com.base.service.info.dao.InfoCurrencyMapper;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.base.facade.info.pojo.po.InfoCurrency;
import com.base.facade.info.pojo.vo.InfoCurrencyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuxun on 16-8-22.
 */
@Service
public class InfoCurrencyBiz extends AbsBaseBiz<InfoCurrency,InfoCurrencyVo,InfoCurrencyMapper> {

    @Autowired
    private InfoCurrencyMapper infoCurrencyMapper;

    @Override
    public InfoCurrencyMapper getDefaultMapper() {
        return infoCurrencyMapper;
    }

    /**
     * 获取货币信息集合
     * @param gainType  获取外汇汇率0:不获取 1：获取
     * @return
     */
    public List<InfoCurrency> getCurrencyListByGainType(String gainType){

        return infoCurrencyMapper.getCurrencyListByGainType(gainType);
    }

    /**
     * 获取汇率计算器的币种
     * @return
     */
    public List<InfoCurrency> getCurrencyListByCalc(){

        return infoCurrencyMapper.getCurrencyListByCalc();
    }
}
