package com.base.service.info.biz;

import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.info.pojo.po.InfoExchangeDetail;
import com.base.facade.info.pojo.vo.InfoExchangeDetailVo;
import com.base.service.info.dao.InfoExchangeDetailMapper;
import com.jucaifu.core.biz.AbsBaseBiz;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by liuxun on 16-8-22.
 */
@Service
public class InfoExchangeDetailBiz extends AbsBaseBiz<InfoExchangeDetail,InfoExchangeDetailVo,InfoExchangeDetailMapper> {

    @Autowired
    private InfoExchangeDetailMapper infoExchangeDetailMapper;
    @Override
    public InfoExchangeDetailMapper getDefaultMapper() {

        return infoExchangeDetailMapper;
    }

    /**
     * 查询货币外汇明细列表
     * @param exchangeId    货币外汇ID
     * @return
     */
    public List<InfoExchangeDetail> getExchangeDetailListByExchangeId(String exchangeId){

        if(StringUtils.isBlank(exchangeId)){
            throw new BaseCommonBizException("货币外汇ID不能为空");
        }
        return infoExchangeDetailMapper.getExchangeDetailListByExchangeId(exchangeId);
    }

    /**
     * 新增货币汇率信息
     * @param detail
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void addExchangeDetail(InfoExchangeDetail detail){
        validateExchangeDetail(detail);
        Date date = new Date();
        detail.setCreateDatetime(date);
        detail.setModifedDatetime(date);
        this.insert(detail);
    }

    /**
     *  校验货币汇率明细数据
     * @param detail
     */
    private void validateExchangeDetail(InfoExchangeDetail detail){
        if (detail == null){
            throw new BaseCommonBizException("货币外汇明细不能为空");
        }
        if (StringUtils.isBlank(detail.getCurrencyNo())){
            throw new BaseCommonBizException("货币编号不能为空");
        }
        if (StringUtils.isBlank(detail.getCurrencyName())){
            throw new BaseCommonBizException("货币名称不能为空");
        }
        if (detail.getTradeUnit() == null){
            throw new BaseCommonBizException("货币交易单位不能为空");
        }
    }

    /**
     * 删除货币明细数据
     * @param exchangeId
     */
    public void deleteExchangeDetail(String exchangeId){
        if (StringUtils.isBlank(exchangeId)){
            throw new BaseCommonBizException("货币外汇明细不能为空");
        }
        infoExchangeDetailMapper.deleteByExchangeId(exchangeId);
    }

    /**
     *
     * @param curNo
     * @return
     */
    public InfoExchangeDetail getExchangeDetailByCurNo(String curNo){
        return infoExchangeDetailMapper.getExchangeDetailByCurNo(curNo);
    }
}
